package io.siddhi.langserver.completion.providers.definitiontable;

import io.siddhi.langserver.LSCompletionContext;
import io.siddhi.langserver.utils.SnippetBlockUtil;
import io.siddhi.langserver.completion.providers.CompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides completions for DefinitionTableContext.
 * {@link io.siddhi.query.compiler.SiddhiQLParser.Definition_tableContext}.
 */
public class DefinitionTableContextProvider extends CompletionProvider {

    public DefinitionTableContextProvider() {

        this.providerName = SiddhiQLParser.Definition_tableContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {

        List<Object[]> suggestions = new ArrayList<>();
        ParserRuleContext definitionTableContext = (ParserRuleContext) LSCompletionContext.INSTANCE.getParseTreeMap()
                .get(SiddhiQLParser.Definition_tableContext.class.getName());
        int childCount = definitionTableContext.getChildCount();
        if (childCount > 0) {
            if (definitionTableContext.getChild(childCount - 1) instanceof SiddhiQLParser.SourceContext) {
                suggestions.add(SnippetBlockUtil.ATTRIBUTE_LIST_SNIPPET);
            }
        }
        return generateCompletionList(suggestions);
    }
}
