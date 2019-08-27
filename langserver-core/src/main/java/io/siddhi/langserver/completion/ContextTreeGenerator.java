package io.siddhi.langserver.completion;

import io.siddhi.langserver.LSContext;
import io.siddhi.query.compiler.SiddhiCompiler;
import io.siddhi.query.compiler.SiddhiQLParser;
import io.siddhi.query.compiler.internal.ErrorNode;
import io.siddhi.query.compiler.internal.SiddhiQLLangServerBaseVisitorImpl;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import java.util.HashMap;
import java.util.Map;

public class ContextTreeGenerator {
    public static final  ContextTreeGenerator INSTANCE=new ContextTreeGenerator();
    private static SiddhiCompiler compiler=new SiddhiCompiler();
    private static SiddhiQLLangServerBaseVisitorImpl visitor=new SiddhiQLLangServerBaseVisitorImpl();
    private ContextTreeGenerator(){

    }
    public void generateContextTree(){
        LSContext lsContext=LSContext.INSTANCE;
        Object parseTree=compiler.parse(lsContext.getSourceContent());
        if(parseTree instanceof SiddhiQLParser.ParseContext) {
            visitor.setPosition(lsContext.getPosition());
            Map<String,Object> contextTree=(HashMap)visitor.visit((ParseTree) parseTree);
            lsContext.setContextTree(contextTree);
            lsContext.setCurrentParserContext((ParserRuleContext)((TerminalNodeImpl)contextTree.get(TerminalNodeImpl.class.toString())).parent);
        }
        else{
            lsContext.setContextTree((HashMap)parseTree);
            lsContext.setCurrentParserContext((ParserRuleContext)((ErrorNode)(((HashMap)parseTree).get(ErrorNode.class.toString()))).parent);
            lsContext.setCurrentErrorNode(((HashMap) parseTree).get(ErrorNode.class.toString()));
        }

    }
}
