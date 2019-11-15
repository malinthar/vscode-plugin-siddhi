package io.siddhi.langserver;

import org.eclipse.lsp4j.TextDocumentPositionParams;

/**
 * Keys for Document Service.
 */
public class DocumentServiceKeys {
    public static final LSContext.Key<String> FILE_URI_KEY
            = new LSContext.Key<>();
    public static final LSContext.Key<TextDocumentPositionParams> POSITION_KEY
            = new LSContext.Key<>();
}

