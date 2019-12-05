package io.siddhi.langserver.completion.providers.trigger;

import io.siddhi.langserver.LSCompletionContext;
import io.siddhi.langserver.utils.SnippetBlockUtil;
import io.siddhi.langserver.completion.providers.CompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides completions for DefinitionTriggerContext.
 * {@link io.siddhi.query.compiler.SiddhiQLParser.Definition_triggerContext}.
 */
public class DefinitionTriggerContextProvider extends CompletionProvider {

    public DefinitionTriggerContextProvider() {

        this.providerName = SiddhiQLParser.Definition_triggerContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {

        List<Object[]> suggestions = new ArrayList<>();
        ParserRuleContext definitionTriggerContext = (ParserRuleContext)
                LSCompletionContext.INSTANCE.getParseTreeMap()
                        .get(SiddhiQLParser.Definition_triggerContext.class.getName());
        int childCount = definitionTriggerContext.getChildCount();
        ParseTree lastChild = definitionTriggerContext.getChild(childCount - 1);
        if (lastChild instanceof TerminalNodeImpl) {
            if (lastChild.getText().equalsIgnoreCase("at")) {
                suggestions.add(SnippetBlockUtil.KEYWORD_EVERY);
            } else {
                suggestions.add(SnippetBlockUtil.KEYWORD_AT);
            }
        } else {
            suggestions.add(SnippetBlockUtil.KEYWORD_AT);
        }

        return generateCompletionList(suggestions);
    }
}
