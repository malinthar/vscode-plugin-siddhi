package io.siddhi.langserver.completion.providers.common;

import io.siddhi.langserver.completion.providers.CompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.List;

/**
 * Provides completions for MathOperationContext
 * {@link io.siddhi.query.compiler.SiddhiQLParser.IdContext}.
 */
public class IdContextProvider extends CompletionProvider {

    public IdContextProvider() {

        this.providerName = SiddhiQLParser.IdContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {
        //Id context doesn't have specific completion items.parent of this context will provide completions.
        return generateCompletionList(null);
    }

}
//todo: add licence.