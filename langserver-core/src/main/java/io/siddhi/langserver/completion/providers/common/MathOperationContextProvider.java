package io.siddhi.langserver.completion.providers.common;

import io.siddhi.langserver.LSCompletionContext;
import io.siddhi.langserver.completion.providers.CompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides completions for MathOperationContext
 * {@link io.siddhi.query.compiler.SiddhiQLParser.Math_operationContext}.
 */
public class MathOperationContextProvider extends CompletionProvider {

    public MathOperationContextProvider() {
        this.providerName = SiddhiQLParser.Math_operationContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {
        List<CompletionItem> completions = new ArrayList<>();
        completions.addAll(LSCompletionContext.INSTANCE
                        .getProvider(SiddhiQLParser.Function_operationContext.class.getName()).getCompletions());
        completions.addAll(LSCompletionContext.INSTANCE
                .getProvider(SiddhiQLParser.Attribute_referenceContext.class.getName()).getCompletions());
        completions.addAll(LSCompletionContext.INSTANCE
                .getProvider(SiddhiQLParser.Constant_valueContext.class.getName()).getCompletions());
        return completions;
    }
}
