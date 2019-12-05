package io.siddhi.langserver.completion.providers.definitionaggregation;

import io.siddhi.langserver.LSCompletionContext;
import io.siddhi.langserver.completion.providers.CompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.List;

/**
 * Provides completions for AggregationTimeInterval context {@link io.siddhi.query.compiler.SiddhiQLParser.Aggregation_time_intervalContext}.
 */
public class AggregationTimeIntervalContextProvider extends CompletionProvider {

    public AggregationTimeIntervalContextProvider() {

        this.providerName = SiddhiQLParser.Aggregation_time_intervalContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {

        List<CompletionItem> completions;
        completions = LSCompletionContext.INSTANCE
                .getProvider(SiddhiQLParser.Aggregation_time_durationContext.class.getName()).getCompletions();
        return completions;
    }
}
