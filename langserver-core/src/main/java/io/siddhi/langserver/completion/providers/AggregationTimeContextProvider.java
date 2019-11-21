package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.LSOperationContext;
import io.siddhi.langserver.completion.providers.snippet.SnippetBlock;
import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.langserver.completion.util.ContextTreeVisitor;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;

public class AggregationTimeContextProvider extends LSCompletionProvider {

    public AggregationTimeContextProvider(){
        this.attachmentContext = SiddhiQLParser.Aggregation_timeContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {
        List<CompletionItem> completions;
        completions =
                LSOperationContext.INSTANCE.FACTORY.getProvider(SiddhiQLParser.Aggregation_time_rangeContext.class.getName()).getCompletions();
        return completions;
    }
}
