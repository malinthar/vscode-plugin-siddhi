package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.completion.providers.snippet.SnippetBlock;
import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.List;

public class StreamFunctionContextProvider extends LSCompletionProvider {

    public StreamFunctionContextProvider(){
        this.attachmentContext = SiddhiQLParser.Stream_functionContext.class.getName();
    }
    @Override
    public List<CompletionItem> getCompletions() {
        return  generateCompletionList(SnippetBlock.getStreamProcessorFunctions());
    }
}
