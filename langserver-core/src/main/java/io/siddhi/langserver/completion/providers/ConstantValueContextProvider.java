package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.LSOperationContext;
import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.List;

public class ConstantValueContextProvider extends LSCompletionProvider {

    public ConstantValueContextProvider(){
        this.attachmentContext = SiddhiQLParser.Constant_valueContext.class.getName();
    }
    @Override
    public List<CompletionItem> getCompletions() {
        List<CompletionItem> completions;
        completions =
                LSOperationContext.INSTANCE.FACTORY.getProvider(SiddhiQLParser.Bool_valueContext.class.getName()).getCompletions();
        return completions;
    }
}
