package io.siddhi.langserver.completion.providers.constant;

import io.siddhi.langserver.utils.SnippetBlockUtil;
import io.siddhi.langserver.completion.providers.CompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.List;

/**
 * Provides completions for BoolValueContext {@link io.siddhi.query.compiler.SiddhiQLParser.Bool_valueContext}.
 */
public class BoolValueContextProvider extends CompletionProvider {

    public BoolValueContextProvider() {

        this.providerName = SiddhiQLParser.Bool_valueContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {

        return generateCompletionList(SnippetBlockUtil.BOOLEAN_CONSTANTS);
    }
}
