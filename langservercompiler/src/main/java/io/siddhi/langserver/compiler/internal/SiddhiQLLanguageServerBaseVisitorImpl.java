package io.siddhi.langserver.compiler.internal;

import io.siddhi.langserver.compiler.SiddhiQLBaseVisitor;
import io.siddhi.langserver.compiler.SiddhiQLParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * SiddhiQLLangServerBaseVisitorImpl.
 */
public class SiddhiQLLanguageServerBaseVisitorImpl extends SiddhiQLBaseVisitor {
    public CurrentPosition currentPosition = new CurrentPosition();
    int[] position;
    List<Object> output = new ArrayList<>();
    public void setPosition(int[] position) {
        this.position = position;
    }
    public Object visitParse(SiddhiQLParser.ParseContext ctx) {
        visit(ctx.siddhi_app());
        return  currentPosition.contextTree;
    }
    public Object visit(ParserRuleContext tree) {
        if (findCursorPosition(tree)) {
            tree.accept(this);
            if (currentPosition.isFound) {
                return output;
            } else {
                return false;
            }
        }
        return false;
    }
    @Override
    public Boolean visitChildren(RuleNode node) {
        Boolean result = true;
        int n = node.getChildCount();
        for (int i = 0; i < n; i++) {
                if (!node.getChild(i).getClass().equals(TerminalNodeImpl.class)) {
                    ParserRuleContext child = (ParserRuleContext) node.getChild(i);
                    if (findCursorPosition(child)) {
                        child.accept(this);
                    }
                    if (currentPosition.isFound) {
                        break;
                    }
               }
        }
        return currentPosition.isFound;
    }
    @Override
    public Boolean visitTerminal(TerminalNode node) {
        currentPosition.setCurrentStartLine(node.getSymbol().getLine());
        currentPosition.setCurrentEndLine(node.getSymbol().getLine());
        currentPosition.setStartPositionInLine(node.getSymbol().getCharPositionInLine());
        currentPosition.setEndPositionInLine(node.getSymbol().getCharPositionInLine() + node.getSymbol().getStopIndex() - node.getSymbol().getStartIndex());
        if (currentPosition.getPosition()[0] <= position[0] && position[0] <= currentPosition.getPosition()[1] && currentPosition.getPosition()[2] <= position[1] && position[1] <= currentPosition.getPosition()[3]) {
            return  true;
        } else {
            return false;
        }
    }
    private boolean findCursorPosition(ParserRuleContext ctx) {
        boolean direction = false;
        boolean positionFound = false;
        Class clss = ctx.getClass();
        currentPosition.setCurrentStartLine(ctx.getStart().getLine());
        currentPosition.setCurrentEndLine(ctx.getStop().getLine());
        currentPosition.setStartPositionInLine(ctx.getStart().getCharPositionInLine());
        currentPosition.setEndPositionInLine(ctx.getStart().getCharPositionInLine() + ctx.getStop().getStopIndex() - ctx.getStart().getStartIndex());
        if (currentPosition.getPosition()[0] <= position[0] && position[0] <= currentPosition.getPosition()[1] && currentPosition.getPosition()[2] <= position[1] && position[1] <= currentPosition.getPosition()[3]) {
            direction = true;
            currentPosition.addContext(ctx);
            if (ctx.getChildCount() == 1 && ctx.getChild(0).getClass().equals(TerminalNodeImpl.class)) {
                positionFound = true;
                currentPosition.setIsFound();
                currentPosition.setTerminal(ctx.getChild(0));
            } else if (ctx.getChildCount() > 1) {
                for (int i = 0; i < ctx.getChildCount(); i++) {
                    if (ctx.getChild(i).getClass().equals(TerminalNodeImpl.class)) {
                        if (visitTerminal((TerminalNodeImpl) ctx.getChild(i))) {
                            ParseTree terminal = ctx.getChild(i);
                            currentPosition.addContext(terminal);
                            positionFound = true;
                            currentPosition.setIsFound();
                            break;
                        }
                    }
                }
            }

        }
        return direction;
    }
    private class CurrentPosition {

        private int currentStartLine;
        private int currentEndLine;
        private int startPositionInLine;
        private int endPositionInLine;
        private boolean isFound = false;
        private HashMap<String, Object> contextTree = new LinkedHashMap<>();
        private ParseTree terminal;
        public int[] getPosition() {
            int[] position = new int[]{currentStartLine, currentEndLine, startPositionInLine, endPositionInLine};
            return position;
        }
        public void setCurrentStartLine(int newLine) {
            this.currentStartLine = newLine;
        }
        public void setCurrentEndLine(int newLine) {
            this.currentEndLine = newLine;
        }
        public void setEndPositionInLine(int newEndPosition) {
            this.endPositionInLine = newEndPosition;
        }
        public void setStartPositionInLine(int newStartPosition) {
            this.startPositionInLine = newStartPosition;
        }
        public void addContext(Object ctx) {
            contextTree.put(ctx.getClass().toString(), ctx);
        }
        public void setTerminal(ParseTree terminal) {
            this.terminal = terminal;
        }
        public ParseTree getTerminal() {
            return this.terminal;
        }

        public void setIsFound()  {
            this.isFound = true;
        }
    }

}
