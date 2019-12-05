package io.siddhi.langserver.completion.providers.common;

import io.siddhi.langserver.utils.SnippetBlockUtil;
import io.siddhi.langserver.completion.providers.CompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides completions for OrderContext.
 * {@link io.siddhi.query.compiler.SiddhiQLParser.OrderContext}.
 */
public class OrderContextProvider extends CompletionProvider {
    public OrderContextProvider() {
        this.providerName = SiddhiQLParser.OrderContext.class.getName();
    }
    @Override
    public List<CompletionItem> getCompletions() {
        List<Object[]> suggestions = new ArrayList<>();
        suggestions.addAll(SnippetBlockUtil.ORDER_KEYWORDS);
        return generateCompletionList(suggestions);
    }
}
