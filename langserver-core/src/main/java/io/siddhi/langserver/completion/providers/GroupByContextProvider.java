package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.LSOperationContext;
import io.siddhi.langserver.completion.providers.snippet.SnippetBlock;
import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;

public class GroupByContextProvider extends LSCompletionProvider {

    AttributeReferenceContextProvider attributeReferenceContextProvider;
    public GroupByContextProvider() {
        this.attachmentContext = SiddhiQLParser.Group_byContext.class.getName();

    }

    //have method call is ancestor to figue whether the class is a decendent of a particular class
    @Override
    public List<CompletionItem> getCompletions() {
        List<CompletionItem> completions =
                LSOperationContext.INSTANCE.FACTORY.getProvider(SiddhiQLParser.Attribute_referenceContext.class.getName()).getCompletions();
        if(visitParent() instanceof SiddhiQLParser.Group_by_query_selectionContext){
            if(visitParent().getParent() instanceof SiddhiQLParser.Definition_aggregationContext) {
                List<Object[]> suggestions = new ArrayList<>();
                suggestions.add(SnippetBlock.KEYWORD_AGGREGATE_BY);
                completions.addAll(generateCompletionList(suggestions));
            }
        }
        return completions;
    }
}
//todo: format correctly