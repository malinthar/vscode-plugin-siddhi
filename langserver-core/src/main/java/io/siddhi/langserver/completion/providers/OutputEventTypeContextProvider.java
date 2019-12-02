package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.completion.providers.snippet.SnippetBlock;
import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.Arrays;
import java.util.List;

public class OutputEventTypeContextProvider extends LSCompletionProvider {

    public OutputEventTypeContextProvider() {
        this.attachmentContext = SiddhiQLParser.Output_event_typeContext.class.getName();
    }
    @Override
    public List<CompletionItem> getCompletions() {
        List<Object[]> suggestions = Arrays.asList(SnippetBlock.KEYWORD_ALL_EVENTS,
                SnippetBlock.KEYWORD_CURRENT_EVENTS,SnippetBlock.KEYWORD_EXPIRED_EVENTS,SnippetBlock.KEYWORD_CURRENT,
                SnippetBlock.KEYWORD_EVENTS);
        return generateCompletionList(suggestions);
    }
}
