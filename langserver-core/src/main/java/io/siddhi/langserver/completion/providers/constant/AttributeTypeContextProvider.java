package io.siddhi.langserver.completion.providers.constant;

import io.siddhi.langserver.LSCompletionContext;
import io.siddhi.langserver.utils.SnippetBlockUtil;
import io.siddhi.langserver.completion.providers.CompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides completions for AttributeTypeContext {@link io.siddhi.query.compiler.SiddhiQLParser.Attribute_typeContext}.
 */
public class AttributeTypeContextProvider extends CompletionProvider {

    public AttributeTypeContextProvider() {
        this.providerName = SiddhiQLParser.Attribute_typeContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {
        List<Object[]> suggestions = SnippetBlockUtil.attributeTypes;
        return generateCompletionList(suggestions);
    }

    /**
     * Provides completions for GroupByContext.
     * {@link SiddhiQLParser.Group_byContext}.
     */
    public static class GroupByContextProvider extends CompletionProvider {

        public GroupByContextProvider() {
            this.providerName = SiddhiQLParser.Group_byContext.class.getName();
        }

        @Override
        public List<CompletionItem> getCompletions() {
            List<CompletionItem> completions = LSCompletionContext.INSTANCE
                    .getProvider(SiddhiQLParser.Attribute_referenceContext.class.getName()).getCompletions();
            if (getParent() instanceof SiddhiQLParser.Group_by_query_selectionContext) {
                if (getParent().getParent() instanceof SiddhiQLParser.Definition_aggregationContext) {
                    List<Object[]> suggestions = new ArrayList<>();
                    suggestions.add(SnippetBlockUtil.KEYWORD_AGGREGATE_BY);
                    completions.addAll(generateCompletionList(suggestions));
                }
            }
            return completions;
        }
    }
}
