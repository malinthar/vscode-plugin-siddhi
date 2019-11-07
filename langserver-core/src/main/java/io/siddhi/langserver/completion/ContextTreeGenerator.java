package io.siddhi.langserver.completion;

import io.siddhi.langserver.LSContext;
import io.siddhi.query.compiler.SiddhiQLParser;
import io.siddhi.query.compiler.internal.ErrorNode;
import io.siddhi.query.compiler.internal.SiddhiQLLanguageServerBaseVisitorImpl;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * {@code ContextTreeGenerator} Compiles the source code and generate parse(context) tree.
 */

public class ContextTreeGenerator {
    public static final  ContextTreeGenerator INSTANCE = new ContextTreeGenerator();
    private static SiddhiQLLanguageServerBaseVisitorImpl visitor = new SiddhiQLLanguageServerBaseVisitorImpl();
    //TODO:check whether the class to be private
    private ContextTreeGenerator(){ }
    public void generateContextTree() {
        LSContext lsContext = LSContext.INSTANCE;
        try {
            Object parseTree = LSContext.INSTANCE.getSiddhiCompiler().languageServerParse(lsContext.getSourceContent());
            if (parseTree instanceof SiddhiQLParser.ParseContext) {
                visitor.setPosition(lsContext.getPosition());
                Map<String, Object> contextTree = (HashMap) visitor.visit((ParseTree) parseTree);
                lsContext.setContextTree(contextTree);
                lsContext.setCurrentParserContext((ParserRuleContext) ((TerminalNodeImpl) contextTree.get(TerminalNodeImpl.class.toString())).parent);
            } else {
                lsContext.setContextTree((HashMap) parseTree);
                lsContext.setCurrentParserContext((ParserRuleContext) ((ErrorNode) (((HashMap) parseTree).get(ErrorNode.class.toString()))).parent);
                lsContext.setCurrentErrorNode(((HashMap) parseTree).get(ErrorNode.class.toString()));
            }
        } catch (Throwable e) {
           String msg = "parse error";
        }

    }
}
