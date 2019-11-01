package io.siddhi.langserver;

import io.siddhi.core.SiddhiManager;
import io.siddhi.langserver.compiler.exception.SiddhiParserException;
import io.siddhi.langserver.completion.LSCompletionProviderFactory;
import io.siddhi.langserver.completion.spi.LSCompletionProvider;
import io.siddhi.langserver.diagnostic.DiagnosticProvider;
import org.antlr.v4.runtime.ParserRuleContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LSContext {
    private  int[] position={1,0};
    private  Map<String,Object> ContextTree=new HashMap<>();
    private CurrentContext currentParserContext=new CurrentContext();
    private  String sourceContent;
    private  String fileUri;
    public static final LSContext INSTANCE=new LSContext();
    private SiddhiLanguageServer siddhiLanguageServer;
    private SiddhiManager siddhiManager;
    private DiagnosticProvider diagnosticProvider;
    public static final LSCompletionProviderFactory factory=LSCompletionProviderFactory.getInstance();

    public void setPosition(int line,int col) {
        this.position[0]=line;
        this.position[1]=col;
    }

    public int[] getPosition() {
        return position;
    }

    public void setContextTree(Map<String,Object> tree) {
       this.ContextTree=tree;
    }

    public Map<String,Object> getContextTree(){
        return this.ContextTree;
    }

    public void setCurrentParserContext(Object currentContext){
        this.currentParserContext.context=(ParserRuleContext)currentContext;

    }
    public void setCurrentErrorNode(Object currentErrorNode){
        this.currentParserContext.errorNode=(ParserRuleContext)currentErrorNode;
    }

    public ParserRuleContext getCurrentContext(){
        return this.currentParserContext.context;
    }
    public ParserRuleContext getCurrentErrorNode(){
        return this.currentParserContext.errorNode;
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

    public void setSiddhiLanguageServer(SiddhiLanguageServer siddhiLanguageServer) {
        this.siddhiLanguageServer = siddhiLanguageServer;
    }
    public SiddhiLanguageServer getSiddhiLanguageServer(){
        return this.siddhiLanguageServer;
    }
    public void setFileUri(String  uri){
        this.fileUri=fileUri;
    }
    public  String getFileUri(){
        return this.fileUri;
    }
    public void setSiddhiManager(SiddhiManager siddhiManager){
        this.siddhiManager=siddhiManager;
    }
    public SiddhiManager getSiddhiManager(){
        return this.siddhiManager;
    }

    public DiagnosticProvider getDiagnosticProvider() {
        return diagnosticProvider;
    }

    public void setDiagnosticProvider(DiagnosticProvider diagnosticProvider) {
        this.diagnosticProvider = diagnosticProvider;
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
