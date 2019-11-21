package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.LSOperationContext;
import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.List;

public class OrderByContextProvider extends LSCompletionProvider {

    public OrderByContextProvider() {
        this.attachmentContext = SiddhiQLParser.Order_byContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {
        List<CompletionItem> completions =
                LSOperationContext.INSTANCE.FACTORY.getProvider(SiddhiQLParser.Order_by_referenceContext.class.getName()).getCompletions();
        return completions;
    }
}
