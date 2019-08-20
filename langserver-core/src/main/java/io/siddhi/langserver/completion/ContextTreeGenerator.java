package io.siddhi.langserver.completion;

import io.siddhi.langserver.LSContext;
import io.siddhi.query.compiler.SiddhiCompiler;
import io.siddhi.query.compiler.SiddhiQLParser;
import io.siddhi.query.compiler.internal.SiddhiQLLangServerBaseVisitorImpl;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import java.util.ArrayList;
import java.util.List;

public class ContextTreeGenerator {
    private static  ContextTreeGenerator INSTANCE=new ContextTreeGenerator();
    private static SiddhiCompiler compiler=new SiddhiCompiler();
    private static SiddhiQLLangServerBaseVisitorImpl visitor=new SiddhiQLLangServerBaseVisitorImpl();
    private ContextTreeGenerator(){

    }
    public static ContextTreeGenerator getInstance(){
        return INSTANCE;
    }
    public void generateContextTree(){
        LSContext lsContext=LSContext.getInstance();
        Object Ptree=compiler.parse(lsContext.getSourceContent());
        if(Ptree instanceof SiddhiQLParser.ParseContext) {
            visitor.setPosition(lsContext.getPosition());
            Object outputarray=visitor.visit((ParseTree) Ptree);
            ArrayList<Object>  ctxarray= (ArrayList) (outputarray);
            lsContext.setParserContextTree(ctxarray);
            lsContext.setCurrentParserContext((ParserRuleContext)ctxarray.get(ctxarray.size()-2));
        }
        else{
            lsContext.setParserContextTree(Ptree);
            Ptree=(ArrayList)Ptree;
            lsContext.setCurrentParserContext(((ArrayList) Ptree).get(((ArrayList) Ptree).size()-2));
            lsContext.setCurrentErrorNode(((ArrayList) Ptree).get(((ArrayList) Ptree).size()-1));
        }

    }
}
