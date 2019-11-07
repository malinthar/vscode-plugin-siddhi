package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.completion.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;
import io.siddhi.langserver.LSContext;

public class DefinitionAggregationContextProvider  extends LSCompletionProvider{
    public DefinitionAggregationContextProvider(){
        this.attachmentPoints.add(SiddhiQLParser.Definition_aggregationContext.class);
    }
    public List<CompletionItem> getCompletions(LSContext context){
        List<CompletionItem> completionItems=new ArrayList<>();
        return completionItems;
    }
}
