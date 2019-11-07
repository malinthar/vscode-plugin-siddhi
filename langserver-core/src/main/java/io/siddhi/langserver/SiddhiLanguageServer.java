package io.siddhi.langserver;

import io.siddhi.core.SiddhiManager;
import io.siddhi.langserver.diagnostic.DiagnosticProvider;
import io.siddhi.query.compiler.SiddhiCompiler;
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

/**
 * {@code SiddhiLanguageServer} language server for Siddhi.
 */
public class SiddhiLanguageServer implements LanguageServer {
    private LanguageClient client;
    private SiddhiTextDocumentService textDocumentService;
    private SiddhiWorkspaceService workspaceService;
    private int shutDownStatus = 1;
    public SiddhiLanguageServer() {
        LSContext.INSTANCE.setSiddhiLanguageServer(this);
        LSContext.INSTANCE.setSiddhiManager(new SiddhiManager());
        LSContext.INSTANCE.setDiagnosticProvider(new DiagnosticProvider());
        LSContext.INSTANCE.setSiddhiCompiler(new SiddhiCompiler());
        this.textDocumentService = new SiddhiTextDocumentService();
        this.workspaceService = new SiddhiWorkspaceService();
    }
    public void connect(LanguageClient languageClient) {
        this.client = languageClient;
    }

    //capabilities of the server 
    @Override
    public CompletableFuture<InitializeResult> initialize(InitializeParams initializeParams) {
        final InitializeResult initializeResult = new InitializeResult(new ServerCapabilities());
        initializeResult.getCapabilities().setTextDocumentSync(TextDocumentSyncKind.Full);
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

    public LanguageClient getClient() {
        return this.client;
    }
}
