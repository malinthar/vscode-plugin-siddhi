package io.siddhi.langserver.completion.providers.definitionfunction;

import io.siddhi.langserver.utils.SnippetBlockUtil;
import io.siddhi.langserver.completion.providers.CompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides completions for FunctionBodyContext.
 * {@link io.siddhi.query.compiler.SiddhiQLParser.Function_bodyContext}.
 */
public class FunctionBodyContextProvider extends CompletionProvider {

    public FunctionBodyContextProvider() {

        this.providerName = SiddhiQLParser.Function_bodyContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {

        List<Object[]> suggestions = new ArrayList<>();
        suggestions.add(SnippetBlockUtil.FUNCTION_BODY_SNIPPET);
        return generateCompletionList(suggestions);
    }
}
