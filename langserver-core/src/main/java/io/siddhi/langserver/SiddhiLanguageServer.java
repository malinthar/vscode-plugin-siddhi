package io.siddhi.langserver;

import  org.eclipse.lsp4j.CompletionOptions;
import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.InitializeResult;
import org.eclipse.lsp4j.ServerCapabilities;
import org.eclipse.lsp4j.TextDocumentSyncKind;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.LanguageServer;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.eclipse.lsp4j.services.WorkspaceService;
import java.util.concurrent.CompletableFuture;

public class SiddhiLanguageServer implements LanguageServer{

    /**reference to client returned by the launcher*/
    private LanguageClient client;
    /**from textDocumentService interface of lsp4j*/
    private SiddhiTextDocumentService textDocumentService;
    private SiddhiWorkspaceService workspaceService;
    /**Thread status:Terminated/Not terminated*/
    private int shutDownStatus = 1;
    /**Initialization of Server*/
    public SiddhiLanguageServer(){
        this.textDocumentService=new SiddhiTextDocumentService();
        this.workspaceService=new SiddhiWorkspaceService();
    }
    /**launcher invoke this method to establish client connection with server*/
    public void connect(LanguageClient languageClient) {
        this.client = languageClient;
    }

    //capabilities of the server 
    @Override
    public CompletableFuture<InitializeResult> initialize(InitializeParams initializeParams) {

        /**refer ServerCapabilities,InitializeResult classes
           https://github.com/eclipse/lsp4j/blob/master/org.eclipse.lsp4j/src/main/xtend-gen/org/eclipse/lsp4j/ServerCapabilities.java
           https://github.com/eclipse/lsp4j/blob/master/org.eclipse.lsp4j/src/main/xtend-gen/org/eclipse/lsp4j/InitializeResult.java
        */
        final InitializeResult initializeResult = new InitializeResult(new ServerCapabilities());
        /**full document syncing enabled*/
        initializeResult.getCapabilities().setTextDocumentSync(TextDocumentSyncKind.Full);
        /**return a CompletableFuture Object
          lambda expression  described
             refer:https://www.callicoder.com/java-8-completablefuture-tutorial/
             .supplyAsync(new Supplier<T> (){InitializeResult})
        completion support to be added*/
        CompletionOptions completionOptions = new CompletionOptions();
        initializeResult.getCapabilities().setCompletionProvider(completionOptions);
        return CompletableFuture.supplyAsync(() -> initializeResult);
    }

    @Override
    public CompletableFuture<Object> shutdown() {
        this.shutDownStatus = 1;
        return null;
    }

    @Override
    public void exit() {
        System.exit(shutDownStatus);
    }

    @Override
    public TextDocumentService getTextDocumentService() {
        return this.textDocumentService;
    }

    @Override
    public WorkspaceService getWorkspaceService() {
        return this.workspaceService;
}
    
}