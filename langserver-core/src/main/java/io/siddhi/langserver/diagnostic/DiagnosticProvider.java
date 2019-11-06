package io.siddhi.langserver.diagnostic;

import io.siddhi.langserver.LSContext;
import io.siddhi.query.api.exception.SiddhiAppContextException;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.DiagnosticSeverity;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.services.LanguageClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@code DiagnosticProvider} Push diagnostics provided by SiddhiManager to client .
 */

public class DiagnosticProvider {
    Map<String, Class> map = new HashMap();

    public DiagnosticProvider() {
        map.putAll(LSContext.INSTANCE.getSiddhiManager().getExtensions());
    }
    public void compileAndSendDiagnostics(LanguageClient client, String fileUri, String sourceContent) {
        try {
            LSContext.INSTANCE.getSiddhiManager().validateSiddhiApp(sourceContent);
            client.publishDiagnostics(new PublishDiagnosticsParams(fileUri, new ArrayList<>(0)));
        } catch (Throwable exception) {
            client.publishDiagnostics(new PublishDiagnosticsParams(fileUri, generateDiagnostics((SiddhiAppContextException) (exception))));
        }
    }
    private List<Diagnostic> generateDiagnostics(SiddhiAppContextException exception) {
        try {
            List<Diagnostic> clientDiagnostics = new ArrayList<>();
            int startLine = (exception.getQueryContextStartIndex() != null) ? exception.getQueryContextStartIndex()[0] - 1 : 0; // LSP diagnostics range is 0 based
            int startChar = (exception.getQueryContextStartIndex() != null) ? exception.getQueryContextStartIndex()[1] - 1 : 0;
            int endLine = (exception.getQueryContextEndIndex() != null) ? exception.getQueryContextEndIndex()[0] - 1 : 0;
            int endChar = (exception.getQueryContextEndIndex() != null) ? exception.getQueryContextEndIndex()[1] - 1 : 50;
            if (exception.getQueryContextStartIndex() != null && exception.getQueryContextEndIndex() != null) {
                startLine = (startLine < 0) ? startLine + 1 : startLine;
                startChar = (startChar < 0) ? startChar + 1 : startChar;
                endLine = (endLine <= 0) ? startLine : endLine;
                endChar = (endChar <= 0) ? startChar + 1 : endChar;
            }
            Range range = new Range(new Position(startLine, startChar), new Position(endLine, endChar));
            Diagnostic diagnostic = new Diagnostic(range, exception.getMessageWithOutContext());
            diagnostic.setSeverity(DiagnosticSeverity.Error);
            clientDiagnostics.add(diagnostic);
            return clientDiagnostics;
        } catch (Throwable e) {
            Throwable error = e;
            return new ArrayList<>();
            //TODO:check behaviour
        }
    }
}
