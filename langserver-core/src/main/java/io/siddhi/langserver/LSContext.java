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
    private  ParserRuleContext currentContext;
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
        this.currentContext=(ParserRuleContext)currentContext;
    }

    public ParserRuleContext getCurrentContext(){
        return this.currentContext;
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
