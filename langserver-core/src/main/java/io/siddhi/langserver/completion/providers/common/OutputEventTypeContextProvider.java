package io.siddhi.langserver.completion.providers.common;

import io.siddhi.langserver.utils.SnippetBlockUtil;
import io.siddhi.langserver.completion.providers.CompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.Arrays;
import java.util.List;

/**
 * Provides completions for outputEventTypeContext.
 * {@link io.siddhi.query.compiler.SiddhiQLParser.Output_event_typeContext}.
 */
public class OutputEventTypeContextProvider extends CompletionProvider {

    public OutputEventTypeContextProvider() {
        this.providerName = SiddhiQLParser.Output_event_typeContext.class.getName();
    }
    @Override
    public List<CompletionItem> getCompletions() {
        List<Object[]> suggestions = Arrays.asList(SnippetBlockUtil.KEYWORD_ALL_EVENTS,
                SnippetBlockUtil.KEYWORD_CURRENT_EVENTS, SnippetBlockUtil.KEYWORD_EXPIRED_EVENTS, SnippetBlockUtil.KEYWORD_CURRENT,
                SnippetBlockUtil.KEYWORD_EVENTS);
        return generateCompletionList(suggestions);
    }
}
