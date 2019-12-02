package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.completion.providers.snippet.SnippetBlock;
import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.List;

public class WindowContextProvider extends LSCompletionProvider {

    public WindowContextProvider() {

        this.attachmentContext = SiddhiQLParser.WindowContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {

        List<Object[]> suggestions;
        suggestions = SnippetBlock.getWindowProcessorFunctions();
        return generateCompletionList(suggestions);
    }
}
