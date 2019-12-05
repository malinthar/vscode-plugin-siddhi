package io.siddhi.langserver.completion.providers.constant;

import io.siddhi.langserver.LSCompletionContext;
import io.siddhi.langserver.completion.providers.CompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.List;

/**
 * Provides completions for ConstantValueContext.
 * {@link io.siddhi.query.compiler.SiddhiQLParser.Constant_valueContext}.
 */
public class ConstantValueContextProvider extends CompletionProvider {

    public ConstantValueContextProvider() {
        this.providerName = SiddhiQLParser.Constant_valueContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {
        List<CompletionItem> completions;
        completions = LSCompletionContext.INSTANCE
                .getProvider(SiddhiQLParser.Bool_valueContext.class.getName()).getCompletions();
        return completions;
    }
}
