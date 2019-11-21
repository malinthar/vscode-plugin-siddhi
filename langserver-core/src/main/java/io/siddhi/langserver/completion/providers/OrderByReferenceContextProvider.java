package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.LSOperationContext;
import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.List;

public class OrderByReferenceContextProvider extends LSCompletionProvider {

    public OrderByReferenceContextProvider() {
        this.attachmentContext = SiddhiQLParser.Order_by_referenceContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {

        List<CompletionItem> completions =
                LSOperationContext.INSTANCE.FACTORY
                        .getProvider(SiddhiQLParser.Attribute_referenceContext.class.getName()).getCompletions();
        if (!completions.isEmpty()) {
           completions.addAll(LSOperationContext.INSTANCE.FACTORY
                    .getProvider(SiddhiQLParser.OrderContext.class.getName()).getCompletions());
        }
        return completions;
    }
}
