package io.siddhi.langserver;

import io.siddhi.core.SiddhiManager;
import io.siddhi.langserver.completion.LSCompletionProviderFactory;
import io.siddhi.langserver.completion.providers.snippet.SnippetProvider;
import io.siddhi.langserver.diagnostic.DiagnosticProvider;
import io.siddhi.query.compiler.langserver.LSErrorNode;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import java.util.ArrayList;
import java.util.Map;

/**
 * {@code LSContext} Context for Siddhi language server.
 */

public class LSOperationContext {

    private int[] position = {1, 0};
    private Map<String, ParseTree> contextTree;
    private ParserRuleContext currentParserContext;
    private TerminalNodeImpl currentTerminalNode;
    private LSErrorNode currentErrorNode;
    private String sourceContent;
    private String fileUri;
    public static final LSOperationContext INSTANCE = new LSOperationContext();
    private SiddhiLanguageServer siddhiLanguageServer;
    private SiddhiManager siddhiManager;
    private DiagnosticProvider diagnosticProvider;
    private SnippetProvider snippetProvider;
    public static final LSCompletionProviderFactory FACTORY = LSCompletionProviderFactory.getInstance();
    //todo:chnage here

    public void setPosition(int line, int col) {

        this.position[0] = line;
        this.position[1] = col;
    }

    public int[] getPosition() {

        return position;
    }

    public void setContextTree(Map<String, ParseTree> tree) {

        try {
            this.contextTree = tree;
            if (this.contextTree.containsKey(LSErrorNode.class.getName())) {
                this.currentErrorNode = (LSErrorNode) this.contextTree.get(LSErrorNode.class.getName());
                this.currentParserContext = (ParserRuleContext) this.currentErrorNode.getParent();
            } else if (this.contextTree.containsKey(TerminalNodeImpl.class.getName())) {
                this.currentTerminalNode = (TerminalNodeImpl) this.contextTree.get(TerminalNodeImpl.class.getName());
                this.currentParserContext = (ParserRuleContext)
                        this.contextTree.get(this.currentTerminalNode.getParent().getClass().getName());
                //todo:check whether puting to an array is better or not / or we can traverse here it self.
                //todo:change all maps can't contain duplicate keys
            } else {
                //todo: issue here
                ArrayList<String> keys = new ArrayList<>(this.contextTree.keySet());
                String currentContextKey = keys.get(contextTree.size() - 1);
                this.currentParserContext =
                        (ParserRuleContext) this.contextTree.get(currentContextKey);
            }
        } catch (RuntimeException e) {
            //todo: change error message
            String msg = "Error setting context tree";
        }
    }

    public Map<String, ParseTree> getContextTree() {

        return this.contextTree;
    }

    public ParserRuleContext getCurrentContext() {

        return this.currentParserContext;
    }

    public void setCurrentContext(ParserRuleContext currentContext) {

        this.currentParserContext = currentContext;
    }

    public ParserRuleContext getCurrentErrorNode() {

        return this.currentErrorNode;
    }

    public void setSourceContent(String sourceContent) {

        this.sourceContent = sourceContent;
    }

    public String getSourceContent() {

        return this.sourceContent;
    }

    //todo:check if this is needed, change comment

    /**
     * doc comment.
     */
    public class CurrentContext {

        ParserRuleContext context;
        ParserRuleContext errorNode;
    }

    public void setSiddhiLanguageServer(SiddhiLanguageServer siddhiLanguageServer) {

        this.siddhiLanguageServer = siddhiLanguageServer;
    }

    public SiddhiLanguageServer getSiddhiLanguageServer() {

        return this.siddhiLanguageServer;
    }

    public void setFileUri(String uri) {

        this.fileUri = fileUri;
    }

    public String getFileUri() {

        return this.fileUri;
    }

    public void setSiddhiManager(SiddhiManager siddhiManager) {

        this.siddhiManager = siddhiManager;
        //todo: if completion is enabled, check  condition
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

    public void setSnippetProvider(SnippetProvider snippetProvider) {

        this.snippetProvider = snippetProvider;
    }

    public SnippetProvider getSnippetProvider() {

        return this.snippetProvider;
    }

}

