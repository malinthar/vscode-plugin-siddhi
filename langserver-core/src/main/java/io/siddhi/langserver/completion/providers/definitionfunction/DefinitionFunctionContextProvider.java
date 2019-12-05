package io.siddhi.langserver.completion.providers.definitionfunction;

import io.siddhi.langserver.LSCompletionContext;
import io.siddhi.langserver.utils.SnippetBlockUtil;
import io.siddhi.langserver.completion.providers.CompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides completions for DefinitionFunctionContext.
 * {@link io.siddhi.query.compiler.SiddhiQLParser.Definition_functionContext}.
 */
public class DefinitionFunctionContextProvider extends CompletionProvider {

    public DefinitionFunctionContextProvider() {
        this.providerName = SiddhiQLParser.Definition_functionContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {
        List<Object[]> suggestions = new ArrayList<>();
        ParserRuleContext definitionFunctionContext = (ParserRuleContext) LSCompletionContext.INSTANCE.getParseTreeMap()
                .get(SiddhiQLParser.Definition_functionContext.class.getName());
        int childCount = definitionFunctionContext.getChildCount();
        if (childCount > 0) {
            if (definitionFunctionContext.getChild(childCount - 1) instanceof SiddhiQLParser.Function_nameContext) {
                suggestions.add(SnippetBlockUtil.LANGUAGE_NAME_SNIPPET);
                return generateCompletionList(suggestions);
            } else if (childCount > 1 &&
                    definitionFunctionContext.getChild(childCount - 2) instanceof SiddhiQLParser.Language_nameContext) {
                suggestions.add(SnippetBlockUtil.KEYWORD_RETURN);
            }
        }
        return generateCompletionList(suggestions);
    }

}
