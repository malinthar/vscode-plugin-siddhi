package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.List;

public class AggregationNameContextProvider extends LSCompletionProvider {

    public AggregationNameContextProvider(){
        this.attachmentContext = SiddhiQLParser.Aggregation_nameContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {
        return null;
    }
}
