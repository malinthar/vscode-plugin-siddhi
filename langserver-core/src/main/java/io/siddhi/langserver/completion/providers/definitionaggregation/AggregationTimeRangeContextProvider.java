package io.siddhi.langserver.completion.providers.definitionaggregation;

import io.siddhi.langserver.LSCompletionContext;
import io.siddhi.langserver.utils.SnippetBlockUtil;
import io.siddhi.langserver.completion.providers.CompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides completions for AggregationTimeRangeContext {@link io.siddhi.query.compiler.SiddhiQLParser.Aggregation_time_rangeContext}.
 */
public class AggregationTimeRangeContextProvider extends CompletionProvider {

    public AggregationTimeRangeContextProvider() {
        this.providerName = SiddhiQLParser.Aggregation_time_rangeContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {
        List<CompletionItem> completions;
        List<Object[]> suggestions = new ArrayList<>();
        suggestions.add(SnippetBlockUtil.TRIPLE_DOT);
        completions = LSCompletionContext.INSTANCE
                .getProvider(SiddhiQLParser.Aggregation_time_durationContext.class.getName()).getCompletions();
        completions.addAll(generateCompletionList(suggestions));
        return completions;
    }
}
