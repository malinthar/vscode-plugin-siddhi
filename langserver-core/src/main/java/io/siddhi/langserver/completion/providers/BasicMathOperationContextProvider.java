package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.List;

public class BasicMathOperationContextProvider extends LSCompletionProvider {

    public BasicMathOperationContextProvider(){
        this.attachmentContext = SiddhiQLParser.Basic_math_operationContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {
        return null;
    }
}
