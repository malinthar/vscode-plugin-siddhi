package io.siddhi.langserver;

import io.siddhi.langserver.common.utils.CommonUtil;
import io.siddhi.langserver.completion.CompletionUtil;
import io.siddhi.langserver.diagnostic.DiagnosticProvider;
import org.eclipse.lsp4j.CodeAction;
import org.eclipse.lsp4j.CodeActionParams;
import org.eclipse.lsp4j.CodeLens;
import org.eclipse.lsp4j.CodeLensParams;
import org.eclipse.lsp4j.Command;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionList;
import org.eclipse.lsp4j.CompletionParams;
import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.DidCloseTextDocumentParams;
import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import org.eclipse.lsp4j.DidSaveTextDocumentParams;
import org.eclipse.lsp4j.DocumentFormattingParams;
import org.eclipse.lsp4j.DocumentHighlight;
import org.eclipse.lsp4j.DocumentOnTypeFormattingParams;
import org.eclipse.lsp4j.DocumentRangeFormattingParams;
import org.eclipse.lsp4j.DocumentSymbol;
import org.eclipse.lsp4j.DocumentSymbolParams;
import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.ReferenceParams;
import org.eclipse.lsp4j.RenameParams;
import org.eclipse.lsp4j.SignatureHelp;
import org.eclipse.lsp4j.SymbolInformation;
import org.eclipse.lsp4j.TextDocumentClientCapabilities;
import org.eclipse.lsp4j.TextDocumentContentChangeEvent;
import org.eclipse.lsp4j.TextDocumentPositionParams;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.WorkspaceEdit;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.services.TextDocumentService;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * {@code SiddhiQLTextDocumentServive} Text document service implementation for Siddhi.
 */
public class SiddhiTextDocumentService implements TextDocumentService {
    // indicates the frequency to send diagnostics to server upon document did change
    private static final int DIAG_PUSH_DEBOUNCE_DELAY = 750;
    private DocumentManager documentManager;
    //TODO:change to document manager implementation
    private TextDocumentClientCapabilities clientCapabilities;
    private SiddhiLanguageServer siddhiLanguageServer;
    private DiagnosticProvider diagnosticProvider;
    //TODO:Add debouncer
    public SiddhiTextDocumentService() {
        this.documentManager = DocumentManagerImpl.getInstance();
        this.siddhiLanguageServer = LSContext.INSTANCE.getSiddhiLanguageServer();
        this.diagnosticProvider = LSContext.INSTANCE.getDiagnosticProvider();
    }

    /**
     * Set the Text Document Capabilities.
     *
     * @param clientCapabilities Client's Text Document Capabilities
     */
    public void setClientCapabilities(TextDocumentClientCapabilities clientCapabilities) {
        this.clientCapabilities = clientCapabilities;
    }

    @Override
    public CompletableFuture<Either<List<CompletionItem>, CompletionList>> completion(CompletionParams completionParams) {
        final List<CompletionItem> completions = new ArrayList<>();
        return CompletableFuture.supplyAsync(() -> {
            String fileUri = completionParams.getTextDocument().getUri();
            Optional<Path> completionPath = CommonUtil.getPathFromURI(fileUri);
            if (!completionPath.isPresent()) {
                return Either.forLeft(completions);
            }
            List<CompletionItem> completionItems;
            try {
                completionItems = CompletionUtil.getCompletions(completionParams);
            } catch (Throwable e) {
                String msg = "Operation 'text/completion' failed!";
               //logError(msg, e, position.getTextDocument(), position.getPosition());
                completionItems = new ArrayList<>();
            }
            return Either.forLeft(completionItems);
        });
    }

    @Override
    public CompletableFuture<CompletionItem> resolveCompletionItem(CompletionItem completionItem) {
        return null;
    }

    @Override
    public CompletableFuture<Hover> hover(TextDocumentPositionParams textDocumentPositionParams) {
        return null;
    }

    @Override
    public CompletableFuture<SignatureHelp> signatureHelp(TextDocumentPositionParams textDocumentPositionParams) {
        return null;
    }

    @Override
    public CompletableFuture<List<? extends Location>> definition(TextDocumentPositionParams textDocumentPositionParams) {
        return null;
    }

    @Override
    public CompletableFuture<List<? extends Location>> references(ReferenceParams referenceParams) {
        return null;
    }

    @Override
    public CompletableFuture<List<? extends DocumentHighlight>> documentHighlight(TextDocumentPositionParams textDocumentPositionParams) {
        return null;
    }

    @Override
    public CompletableFuture<List<Either<SymbolInformation, DocumentSymbol>>> documentSymbol(DocumentSymbolParams documentSymbolParams) {
        return null;
    }

    @Override
    public CompletableFuture<List<Either<Command, CodeAction>>>  codeAction(CodeActionParams params) {
        return null;
    }

    @Override
    public CompletableFuture<List<? extends CodeLens>> codeLens(CodeLensParams codeLensParams) {
        return null;
    }

    @Override
    public CompletableFuture<CodeLens> resolveCodeLens(CodeLens codeLens) {
        return null;
    }

    @Override
    public CompletableFuture<List<? extends TextEdit>> formatting(DocumentFormattingParams documentFormattingParams) {
        return null;
    }

    @Override
    public CompletableFuture<List<? extends TextEdit>> rangeFormatting(DocumentRangeFormattingParams documentRangeFormattingParams) {
        return null;
    }

    @Override
    public CompletableFuture<List<? extends TextEdit>> onTypeFormatting(DocumentOnTypeFormattingParams documentOnTypeFormattingParams) {
        return null;
    }

    @Override
    public CompletableFuture<WorkspaceEdit> rename(RenameParams renameParams) {
        return CompletableFuture.supplyAsync(()-> {
            String fileUri = renameParams.getTextDocument().getUri();
            Position position = renameParams.getPosition();
            return null;
        });
    }

    @Override
    public void didOpen(DidOpenTextDocumentParams didOpenTextDocumentParams) {
        //todo: handle errors
        String uri = didOpenTextDocumentParams.getTextDocument().getUri();
        String content = didOpenTextDocumentParams.getTextDocument().getText();
        this.documentManager.openFile(Paths.get(URI.create(uri)), content);
        if (siddhiLanguageServer.getClient() != null) {
            this.diagnosticProvider.compileAndSendDiagnostics(siddhiLanguageServer.getClient(), uri, content);
        }

    }

    @Override
    public void didChange(DidChangeTextDocumentParams didChangeTextDocumentParams) {
        String fileUri = didChangeTextDocumentParams.getTextDocument().getUri();
        Optional<Path> changedPath = CommonUtil.getPathFromURI(fileUri);
        if (!changedPath.isPresent()) {
            return;
        }
        try {
            List<TextDocumentContentChangeEvent> changes = didChangeTextDocumentParams.getContentChanges();
            for (TextDocumentContentChangeEvent changeEvent : changes) {
                documentManager.updateFile(Paths.get(URI.create(fileUri)), changeEvent.getText());
            }
            try {
                this.diagnosticProvider.compileAndSendDiagnostics(siddhiLanguageServer.getClient(), fileUri, documentManager.getFileContent(Paths.get(URI.create(fileUri))));
            } catch (Throwable e) {
                String msg = "Computing 'diagnostics' failed!";
                //logError(msg, e, params.getTextDocument(), (Position) null);
            }
        } catch (Throwable e) {
            String msg = "Operation 'text/didChange' failed!";
            //logError(msg, e, params.getTextDocument(), (Position) null);
        }
    }

    @Override
    public void didClose(DidCloseTextDocumentParams didCloseTextDocumentParams) {
        String uri = didCloseTextDocumentParams.getTextDocument().getUri();
        this.documentManager.closeFile(Paths.get(URI.create(uri)));
    }

    @Override
    public void didSave(DidSaveTextDocumentParams didSaveTextDocumentParams) {
    }
}

