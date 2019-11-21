package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.LSOperationContext;
import io.siddhi.langserver.completion.providers.snippet.SnippetBlock;
import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;

public class DefinitionAggregationContextProvider extends LSCompletionProvider {

    public DefinitionAggregationContextProvider() {

        this.attachmentContext = SiddhiQLParser.Definition_aggregationContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {

        List<Object[]> suggestions = new ArrayList<>();
        List<CompletionItem> completions = new ArrayList<>();
        ParserRuleContext aggregationDefinitionContext =
                (ParserRuleContext) LSOperationContext.INSTANCE.getContextTree().get(
                        SiddhiQLParser.Definition_aggregationContext.class.getName());
        int childCount = aggregationDefinitionContext.getChildCount();
        if (childCount > 0) {
            if (aggregationDefinitionContext
                    .getChild(childCount - 1) instanceof SiddhiQLParser.Attribute_referenceContext) {
                completions.addAll(
                        LSOperationContext.INSTANCE.FACTORY
                                .getProvider(SiddhiQLParser.Attribute_referenceContext.class.getName())
                                .getCompletions());
            }
        }
        suggestions.add(SnippetBlock.KEYWORD_EVERY);
        completions.addAll(generateCompletionList(suggestions));
        return completions;
        //todo: what else should be provided.
    }
}
