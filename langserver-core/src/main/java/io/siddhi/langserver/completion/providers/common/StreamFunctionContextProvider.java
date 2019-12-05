package io.siddhi.langserver.completion.providers.common;

import io.siddhi.langserver.utils.SnippetBlockUtil;
import io.siddhi.langserver.completion.providers.CompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.List;

/**
 * Provide completions for StreamFunctionContext.
 * {@link io.siddhi.query.compiler.SiddhiQLParser.Stream_functionContext}.
 */
public class StreamFunctionContextProvider extends CompletionProvider {

    public StreamFunctionContextProvider() {

        this.providerName = SiddhiQLParser.Stream_functionContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {

        return generateCompletionList(SnippetBlockUtil.getStreamProcessorFunctions());
    }
}
