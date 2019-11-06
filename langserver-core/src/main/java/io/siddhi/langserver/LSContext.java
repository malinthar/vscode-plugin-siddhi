package io.siddhi.langserver;

import io.siddhi.core.SiddhiManager;
import io.siddhi.langserver.completion.LSCompletionProviderFactory;
import io.siddhi.langserver.diagnostic.DiagnosticProvider;
import io.siddhi.query.compiler.SiddhiCompiler;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.HashMap;
import java.util.Map;

/**
 * {@code LSContext} Context for Siddhi language server.
 */
public class LSContext {
    private  int[] position = {1, 0};
    private  Map<String, Object> contextTree = new HashMap<>();
    private  CurrentContext currentParserContext = new CurrentContext();
    private  String sourceContent;
    private  String fileUri;
    public static final LSContext INSTANCE = new LSContext();
    private SiddhiLanguageServer siddhiLanguageServer;
    private SiddhiManager siddhiManager;
    private DiagnosticProvider diagnosticProvider;
    private SiddhiCompiler siddhiCompiler;
    public static final LSCompletionProviderFactory FACTORY = LSCompletionProviderFactory.getInstance();

    public void setPosition(int line, int col) {
        this.position[0] = line;
        this.position[1] = col;
    }

    public int[] getPosition() {
        return position;
    }

    public void setContextTree(Map<String, Object> tree) {
       this.contextTree = tree;
    }

    public Map<String, Object> getContextTree() {
        return this.contextTree;
    }

    public void setCurrentParserContext(Object currentContext) {
        this.currentParserContext.context = (ParserRuleContext) currentContext;
    }
    public void setCurrentErrorNode(Object currentErrorNode) {
        this.currentParserContext.errorNode = (ParserRuleContext) currentErrorNode;
    }

    public ParserRuleContext getCurrentContext() {
        return this.currentParserContext.context;
    }
    public ParserRuleContext getCurrentErrorNode() {
        return this.currentParserContext.errorNode;
    }

    public void setSourceContent(String sourceContent) {
        this.sourceContent = sourceContent;
    }

    public String getSourceContent() {
        return this.sourceContent;
    }
    /**
     * {@code SnippetProvider} provide snippets based on context.
     */
    //todo:check if this is needed
    public  class CurrentContext {
        ParserRuleContext context;
        ParserRuleContext errorNode;
    }

    public void setSiddhiLanguageServer(SiddhiLanguageServer siddhiLanguageServer) {
        this.siddhiLanguageServer = siddhiLanguageServer;
    }
    public SiddhiLanguageServer getSiddhiLanguageServer() {
        return this.siddhiLanguageServer;
    }
    public void setFileUri(String  uri) {
        this.fileUri = fileUri;
    }
    public  String getFileUri() {
        return this.fileUri;
    }
    public void setSiddhiManager(SiddhiManager siddhiManager) {
        this.siddhiManager = siddhiManager;
    }
    public SiddhiManager getSiddhiManager() {
        return this.siddhiManager;
    }

    public DiagnosticProvider getDiagnosticProvider() {
        return diagnosticProvider;
    }

    public void setDiagnosticProvider(DiagnosticProvider diagnosticProvider) {
        this.diagnosticProvider = diagnosticProvider;
    }
    public void setSiddhiCompiler(SiddhiCompiler compiler) {
      this.siddhiCompiler = compiler;
    }
    public SiddhiCompiler getSiddhiCompiler() {
        return this.siddhiCompiler;
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
