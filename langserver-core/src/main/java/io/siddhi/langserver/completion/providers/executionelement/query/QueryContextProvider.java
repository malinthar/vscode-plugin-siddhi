package io.siddhi.langserver.completion.providers.executionelement.query;

import io.siddhi.langserver.completion.providers.CompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.List;

/**
 * Provide completions for QueryContext {@link io.siddhi.query.compiler.SiddhiQLParser.QueryContext}.
 */
public class QueryContextProvider extends CompletionProvider {

    public QueryContextProvider() {

        this.providerName = SiddhiQLParser.QueryContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {

        return null;
    }

}
