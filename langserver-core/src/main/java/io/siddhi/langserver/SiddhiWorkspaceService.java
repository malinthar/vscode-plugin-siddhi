package io.siddhi.langserver;
import io.siddhi.langserver.diagnostic.DiagnosticProvider;
import org.eclipse.lsp4j.DidChangeConfigurationParams;
import org.eclipse.lsp4j.DidChangeWatchedFilesParams;
import org.eclipse.lsp4j.DidChangeWorkspaceFoldersParams;
import org.eclipse.lsp4j.ExecuteCommandParams;
import org.eclipse.lsp4j.SymbolInformation;
import org.eclipse.lsp4j.WorkspaceSymbolParams;
import org.eclipse.lsp4j.services.WorkspaceService;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Implementation of LS$J Workspace Service
 */
public class SiddhiWorkspaceService implements WorkspaceService {
    private DiagnosticProvider diagnosticProvider;
    SiddhiWorkspaceService(){
             this.diagnosticProvider= LSContext.INSTANCE.getDiagnosticProvider();
    }
    @Override
    public CompletableFuture<Object> executeCommand(ExecuteCommandParams params) {
        return null;
    }

    @Override
    public CompletableFuture<List<? extends SymbolInformation>> symbol(WorkspaceSymbolParams
                                                                                   workspaceSymbolParams) {
        return null;
    }

    @Override
    public void didChangeConfiguration(DidChangeConfigurationParams params) {
        if (!(params.getSettings() instanceof JsonObject)) {
            return;
        }
    }

    @Override
    public void didChangeWatchedFiles(DidChangeWatchedFilesParams didChangeWatchedFilesParams) {

    }

    @Override
    public void didChangeWorkspaceFolders(DidChangeWorkspaceFoldersParams params) {

    }
}