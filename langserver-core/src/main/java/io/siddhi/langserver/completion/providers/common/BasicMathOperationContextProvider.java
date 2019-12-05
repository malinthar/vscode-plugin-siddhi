package io.siddhi.langserver.completion.providers.common;

import io.siddhi.langserver.LSCompletionContext;
import io.siddhi.langserver.completion.providers.CompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.List;

/**
 * Provides completions for BasicMathOperationContext
 * {@link io.siddhi.query.compiler.SiddhiQLParser.Attribute_typeContext}.
 */
public class BasicMathOperationContextProvider extends CompletionProvider {

    public BasicMathOperationContextProvider() {
        this.providerName = SiddhiQLParser.Basic_math_operationContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {

        List<CompletionItem> completions;
        completions = LSCompletionContext.INSTANCE
                .getProvider(SiddhiQLParser.Attribute_referenceContext.class.getName()).getCompletions();
        return completions;
    }
}
