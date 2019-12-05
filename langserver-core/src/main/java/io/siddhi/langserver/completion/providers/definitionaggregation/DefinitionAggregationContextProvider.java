package io.siddhi.langserver.completion.providers.definitionaggregation;

import io.siddhi.langserver.LSCompletionContext;
import io.siddhi.langserver.utils.SnippetBlockUtil;
import io.siddhi.langserver.completion.providers.CompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides completions for DefinitionAggregationContext.
 * {@link io.siddhi.query.compiler.SiddhiQLParser.Definition_aggregationContext}.
 */
public class DefinitionAggregationContextProvider extends CompletionProvider {

    public DefinitionAggregationContextProvider() {
        this.providerName = SiddhiQLParser.Definition_aggregationContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {
        List<Object[]> suggestions = new ArrayList<>();
        List<CompletionItem> completions = new ArrayList<>();
        ParserRuleContext aggregationDefinitionContext = (ParserRuleContext) LSCompletionContext.INSTANCE
                .getParseTreeMap().get(SiddhiQLParser.Definition_aggregationContext.class.getName());
        if (aggregationDefinitionContext != null) {
            int childCount = aggregationDefinitionContext.getChildCount();
            if (childCount > 0) {
                if (aggregationDefinitionContext
                        .getChild(childCount - 1) instanceof SiddhiQLParser.Attribute_referenceContext) {
                    completions.addAll(LSCompletionContext.INSTANCE
                            .getProvider(SiddhiQLParser.Attribute_referenceContext.class.getName()).getCompletions());
                } else if (aggregationDefinitionContext
                        .getChild(childCount - 1) instanceof SiddhiQLParser.Group_byContext) {
                    suggestions.add(SnippetBlockUtil.KEYWORD_AGGREGATE_BY);
                } else if (aggregationDefinitionContext
                        .getChild(childCount - 1) instanceof SiddhiQLParser.Group_by_query_selectionContext) {
                    suggestions.add(SnippetBlockUtil.KEYWORD_GROUP_BY);
                    suggestions.add(SnippetBlockUtil.KEYWORD_AGGREGATE_BY);
                }
            }

        }
        suggestions.add(SnippetBlockUtil.KEYWORD_EVERY);
        suggestions.add(SnippetBlockUtil.KEYWORD_FROM);
        completions.addAll(generateCompletionList(suggestions));
        return completions;
    }
}
