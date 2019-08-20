package io.siddhi.langserver;


import io.siddhi.langserver.completion.ContextTreeGenerator;
import io.siddhi.query.compiler.internal.SiddhiQLLangServerBaseVisitorImpl;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.List;
public class LSContext {
    private  int[] position;
    private  List<Object> parserContextTree=new ArrayList<>();
    private CurrentContext currentContext=new CurrentContext();
    private  String sourceContent;
    private static LSContext INSTANCE=new LSContext();

    public void setPosition(int[] position) {

        this.position = position;
    }


    public int[] getPosition() {
        return position;
    }

    public void setParserContextTree(Object tree) {
       this.parserContextTree=(ArrayList)tree;
    }

    public List<Object> getParserContextTree(){
        return this.parserContextTree;
    }

    public void setCurrentParserContext(Object currentContext){
        this.currentContext.context=(ParserRuleContext)currentContext;

    }
    public void setCurrentErrorNode(Object currentErrorNode){
        this.currentContext.errorNode=(ParserRuleContext)currentErrorNode;
    }

    public ParserRuleContext getCurrentContext(){
        return this.currentContext.context;
    }
    public ParserRuleContext getCurrentErrorNode(){
        return this.currentContext.errorNode;
    }

    public static LSContext getInstance(){

        return INSTANCE;
    }

    public void setSourceContent(String sourceContent){
        this.sourceContent=sourceContent;
    }

    public String getSourceContent(){

        return this.sourceContent;
    }
    public  class CurrentContext{
        ParserRuleContext context;
        ParserRuleContext errorNode;
    }
}


    /*String query="@app:name('Temperature-Analytics')\n" +
            "\n" +
            "define stream TempStream (deviceID long, roomNo int,temp double);\n"+
            "\n" +
            "@name('Avg5im')\n" +
            "from TempStream#window.time(5 min)\n" +
            "select roomNo, avg(temp) as avgTemp\n" +
            "  group by roomNo\n" +
            "insert into output ";*/
