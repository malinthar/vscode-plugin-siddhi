package io.siddhi.langserver.completion.providers.common;

import io.siddhi.langserver.LSCompletionContext;
import io.siddhi.langserver.completion.providers.CompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides completions for OrderByReferenceContext.
 * {@link io.siddhi.query.compiler.SiddhiQLParser.Order_by_referenceContext}.
 */
public class OrderByReferenceContextProvider extends CompletionProvider {

    public OrderByReferenceContextProvider() {
        this.providerName = SiddhiQLParser.Order_by_referenceContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {
        List<CompletionItem> completions = new ArrayList<>();
                completions.addAll(LSCompletionContext.INSTANCE
                        .getProvider(SiddhiQLParser.Attribute_referenceContext.class.getName()).getCompletions());
        if (!completions.isEmpty()) {
            completions.addAll(LSCompletionContext.INSTANCE
                    .getProvider(SiddhiQLParser.OrderContext.class.getName()).getCompletions());
        }
        return completions;
    }
}
