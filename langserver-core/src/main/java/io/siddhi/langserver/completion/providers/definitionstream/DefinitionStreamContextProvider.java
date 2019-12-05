package io.siddhi.langserver.completion.providers.definitionstream;

import io.siddhi.langserver.LSCompletionContext;
import io.siddhi.langserver.utils.SnippetBlockUtil;
import io.siddhi.langserver.completion.providers.CompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser.Definition_streamContext;
import org.antlr.v4.runtime.ParserRuleContext;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides completions for DefinitionStreamContext.
 * {@link io.siddhi.query.compiler.SiddhiQLParser.Definition_streamContext}.
 */
public class DefinitionStreamContextProvider extends CompletionProvider {

    public DefinitionStreamContextProvider() {

        this.providerName = Definition_streamContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {

        List<Object[]> suggestions = new ArrayList<>();
        ParserRuleContext definitionStreamContext = (ParserRuleContext) LSCompletionContext.INSTANCE.getParseTreeMap()
                .get(Definition_streamContext.class.getName());
        int childCount = definitionStreamContext.getChildCount();
        if (childCount > 0) {
            suggestions.add(SnippetBlockUtil.ATTRIBUTE_LIST_SNIPPET);
        }
        return generateCompletionList(suggestions);
    }
}
