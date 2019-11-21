package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.completion.providers.snippet.SnippetBlock;
import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.List;

public class AggregationTimeDurationContextProvider extends LSCompletionProvider {

    public AggregationTimeDurationContextProvider(){
        this.attachmentContext = SiddhiQLParser.Aggregation_time_durationContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {
        return generateCompletionList(SnippetBlock.AGGREGATION_TIME_DURATION);
    }
}
