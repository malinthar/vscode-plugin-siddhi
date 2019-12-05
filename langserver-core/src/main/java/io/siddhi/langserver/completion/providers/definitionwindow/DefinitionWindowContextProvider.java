package io.siddhi.langserver.completion.providers.definitionwindow;

import io.siddhi.langserver.LSCompletionContext;
import io.siddhi.langserver.utils.SnippetBlockUtil;
import io.siddhi.langserver.completion.providers.CompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides completions for DefinitionWindowContext.
 * {@link io.siddhi.query.compiler.SiddhiQLParser.Definition_windowContext}.
 */
public class DefinitionWindowContextProvider extends CompletionProvider {

    public DefinitionWindowContextProvider() {

        this.providerName = SiddhiQLParser.Definition_windowContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {

        List<Object[]> suggestions = new ArrayList<>();
        ParserRuleContext definitionWindowContext = (ParserRuleContext) LSCompletionContext.INSTANCE.getParseTreeMap()
                .get(SiddhiQLParser.Definition_windowContext.class.getName());
        int childCount = definitionWindowContext.getChildCount();
        if (childCount > 0) {
            if (definitionWindowContext.getChild(childCount - 1) instanceof SiddhiQLParser.SourceContext) {
                suggestions.add(SnippetBlockUtil.ATTRIBUTE_LIST_SNIPPET);
            }
        }
        return generateCompletionList(suggestions);
    }
}
