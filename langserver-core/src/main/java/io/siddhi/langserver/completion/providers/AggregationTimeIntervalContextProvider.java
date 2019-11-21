package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.LSOperationContext;
import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.List;

public class AggregationTimeIntervalContextProvider extends LSCompletionProvider {

    public AggregationTimeIntervalContextProvider() {

        this.attachmentContext = SiddhiQLParser.Aggregation_time_intervalContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {

        List<CompletionItem> completions;
        completions =
                LSOperationContext.INSTANCE.FACTORY
                        .getProvider(SiddhiQLParser.Aggregation_time_durationContext.class.getName()).getCompletions();
        return completions;
    }
}
