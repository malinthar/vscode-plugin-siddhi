package io.siddhi.langserver.completion.providers.definitionaggregation;

import io.siddhi.langserver.utils.SnippetBlockUtil;
import io.siddhi.langserver.completion.providers.CompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.List;

/**
 * Provides completions for AggregationTimeDurationContext
 * {@link io.siddhi.query.compiler.SiddhiQLParser.Aggregation_time_durationContext}.
 */
public class AggregationTimeDurationContextProvider extends CompletionProvider {

    public AggregationTimeDurationContextProvider() {

        this.providerName = SiddhiQLParser.Aggregation_time_durationContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {

        return generateCompletionList(SnippetBlockUtil.AGGREGATION_TIME_DURATION);
    }
}
