package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.LSOperationContext;
import io.siddhi.langserver.completion.providers.snippet.SnippetProvider;
import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;


import java.util.ArrayList;
import java.util.List;

public class QueryInputContextProvider extends LSCompletionProvider {
    public QueryInputContextProvider() {
        this.attachmentContext = SiddhiQLParser.Query_inputContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {
        return null;
    }

}
