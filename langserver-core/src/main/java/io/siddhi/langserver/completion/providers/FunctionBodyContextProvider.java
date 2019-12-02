package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.completion.providers.snippet.SnippetBlock;
import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;

public class FunctionBodyContextProvider extends LSCompletionProvider {

    public FunctionBodyContextProvider(){
        this.attachmentContext = SiddhiQLParser.Function_bodyContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {
        List<Object[]> suggestions = new ArrayList<>();
        suggestions.add(SnippetBlock.FUNCTION_BODY_SNIPPET);
        return generateCompletionList(suggestions);
    }
}
