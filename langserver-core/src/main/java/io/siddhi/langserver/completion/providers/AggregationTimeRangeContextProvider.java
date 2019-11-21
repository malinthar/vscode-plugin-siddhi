package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.LSOperationContext;
import io.siddhi.langserver.completion.providers.snippet.SnippetBlock;
import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;

public class AggregationTimeRangeContextProvider extends LSCompletionProvider {

    public AggregationTimeRangeContextProvider(){
        this.attachmentContext = SiddhiQLParser.Aggregation_time_rangeContext.class.getName();
    }
    @Override
    public List<CompletionItem> getCompletions() {
        List<CompletionItem> completions;
        List<Object[]> suggestions = new ArrayList<>();
        suggestions.add(SnippetBlock.TRIPLE_DOT);
        completions =
                LSOperationContext.INSTANCE.FACTORY.getProvider(SiddhiQLParser.Aggregation_time_durationContext.class.getName()).getCompletions();
        completions.addAll(generateCompletionList(suggestions));
        return completions;
    }
}
