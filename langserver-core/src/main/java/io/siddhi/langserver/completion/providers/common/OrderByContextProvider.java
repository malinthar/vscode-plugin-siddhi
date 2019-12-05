package io.siddhi.langserver.completion.providers.common;

import io.siddhi.langserver.LSCompletionContext;
import io.siddhi.langserver.completion.providers.CompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides completions for orderByContext.
 * {@link io.siddhi.query.compiler.SiddhiQLParser.Order_byContext}.
 */
public class OrderByContextProvider extends CompletionProvider {

    public OrderByContextProvider() {
        this.providerName = SiddhiQLParser.Order_byContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {
        List<CompletionItem> completions = new ArrayList<>();
        completions.addAll(LSCompletionContext.INSTANCE
                .getProvider(SiddhiQLParser.Order_by_referenceContext.class.getName()).getCompletions());
        return completions;
    }
}
