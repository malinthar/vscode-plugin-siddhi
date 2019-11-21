package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.LSOperationContext;
import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.List;

public class MathOperationContextProvider extends LSCompletionProvider {

    public MathOperationContextProvider(){
        this.attachmentContext = SiddhiQLParser.Math_operationContext.class.getName();
    }
    @Override
    public List<CompletionItem> getCompletions() {
        List<CompletionItem> completions;
        completions =
                LSOperationContext.INSTANCE.FACTORY.getProvider(SiddhiQLParser.Function_operationContext.class.getName()).getCompletions();
        completions.addAll(LSOperationContext.INSTANCE.FACTORY.getProvider(SiddhiQLParser.Attribute_referenceContext.class.getName()).getCompletions());
        completions.addAll(LSOperationContext.INSTANCE.FACTORY.getProvider(SiddhiQLParser.Constant_valueContext.class.getName()).getCompletions());
        return completions;
    }
}
