package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.completion.providers.snippet.SnippetBlock;
import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.List;

public class FunctionOperationContextProvider extends LSCompletionProvider {

    public FunctionOperationContextProvider() {
        this.attachmentContext = SiddhiQLParser.Function_operationContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {
        List<Object[]> suggestions = SnippetBlock.getFunctions();
        return generateCompletionList(suggestions);
    }

}


//todo: check why the Terminal Node added for function operation Context