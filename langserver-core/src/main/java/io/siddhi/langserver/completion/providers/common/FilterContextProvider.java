package io.siddhi.langserver.completion.providers.common;

import io.siddhi.langserver.LSCompletionContext;
import io.siddhi.langserver.completion.providers.CompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.List;

/**
 * Provides completions for FilterContext.
 * {@link io.siddhi.query.compiler.SiddhiQLParser.Execution_elementContext}.
 */
public class FilterContextProvider extends CompletionProvider {

    public  FilterContextProvider(){
        this.providerName = SiddhiQLParser.FilterContext.class.getName();
    }
    @Override
    public List<CompletionItem> getCompletions() {
        //MathOperationContext is the only context that can be contained by filter context.
        return LSCompletionContext.INSTANCE
                .getProvider(SiddhiQLParser.Math_operationContext.class.getName())
                .getCompletions();
    }
}
