package io.siddhi.langserver;

import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.eclipse.lsp4j.TextDocumentClientCapabilities;
import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.DidCloseTextDocumentParams;
import org.eclipse.lsp4j.DidSaveTextDocumentParams;
import org.eclipse.lsp4j.TextDocumentPositionParams;
import org.eclipse.lsp4j.ReferenceParams;
import org.eclipse.lsp4j.CodeActionParams;
import org.eclipse.lsp4j.CodeLensParams;
import org.eclipse.lsp4j.DocumentFormattingParams;
import org.eclipse.lsp4j.DocumentOnTypeFormattingParams;
import org.eclipse.lsp4j.DocumentRangeFormattingParams;
import org.eclipse.lsp4j.RenameParams;
import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.SignatureHelp;
import org.eclipse.lsp4j.DocumentHighlight;
import org.eclipse.lsp4j.SymbolInformation;
import org.eclipse.lsp4j.DocumentSymbolParams;
import org.eclipse.lsp4j.DocumentSymbol;
import org.eclipse.lsp4j.CompletionParams;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionList;
import org.eclipse.lsp4j.CodeAction;
import org.eclipse.lsp4j.Command;
import org.eclipse.lsp4j.CodeLens;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.WorkspaceEdit;

import io.siddhi.langserver.diagnostic.DiagnosticProvider;
import io.siddhi.langserver.completion.CompletionUtil;

import java.util.List;
import java.util.ArrayList;
import java.net.URI;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
    /**
     * SiddhiQL Text Document Service
     */
    public class SiddhiTextDocumentService implements TextDocumentService {

        private DocumentManager documentManager;
        private TextDocumentClientCapabilities clientCapabilities;
        private SiddhiLanguageServer siddhiLanguageServer;
        private DiagnosticProvider diagnosticProvider;

        public SiddhiTextDocumentService() {
            this.documentManager = DocumentManagerImpl.getInstance();
            this.siddhiLanguageServer=LSContext.INSTANCE.getSiddhiLanguageServer();
            this.diagnosticProvider=LSContext.INSTANCE.getDiagnosticProvider();
        }
        public void setClientCapabilities(TextDocumentClientCapabilities clientCapabilities) {
            this.clientCapabilities = clientCapabilities;
        }

        @Override
        public CompletableFuture<Either<List<CompletionItem>, CompletionList>>completion(
                CompletionParams completionParams) {
            return CompletableFuture.supplyAsync(() -> {
                List<CompletionItem> completionItems;
                try {
                    completionItems = CompletionUtil.getCompletions(completionParams);
                } catch (Exception e) {
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
        public CompletableFuture<List<? extends Location>> definition(TextDocumentPositionParams
                                                                              textDocumentPositionParams) {
            return null;
        }

        @Override
        public CompletableFuture<List<? extends Location>> references(ReferenceParams referenceParams) {
            return null;
        }

        @Override
        public CompletableFuture<List<? extends DocumentHighlight>> documentHighlight
                (TextDocumentPositionParams textDocumentPositionParams) {
            return null;
        }

        @Override
        public CompletableFuture<List<Either<SymbolInformation, DocumentSymbol>>> documentSymbol(DocumentSymbolParams documentSymbolParams) {
            return null;
        }

        @Override
        public CompletableFuture<List<Either<Command, CodeAction>>>  codeAction(CodeActionParams params) {
            /*TextDocumentIdentifier identifier = params.getTextDocument();
            List<CodeAction> actions = new ArrayList<>();
            String fileUri= identifier.getUri();
            try {
                List<Diagnostic> diagnostics = params.getContext().getDiagnostics();
            } catch (Exception e) {
                return null;
            }
            return Either.forLeft();*/
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
            String uri = didOpenTextDocumentParams.getTextDocument().getUri();
            String content = didOpenTextDocumentParams.getTextDocument().getText();
            this.documentManager.openFile(Paths.get(URI.create(uri)), content);
            this.diagnosticProvider.compileAndSendDiagnostics(siddhiLanguageServer.getClient(),uri,content);

        }

        @Override
        public void didChange(DidChangeTextDocumentParams didChangeTextDocumentParams) {
            String uri = didChangeTextDocumentParams.getTextDocument().getUri();
            String content = didChangeTextDocumentParams.getContentChanges().get(0).getText();
            this.diagnosticProvider.compileAndSendDiagnostics(siddhiLanguageServer.getClient(),uri,content);
            this.documentManager.updateFile(Paths.get(URI.create(uri)), content);
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

