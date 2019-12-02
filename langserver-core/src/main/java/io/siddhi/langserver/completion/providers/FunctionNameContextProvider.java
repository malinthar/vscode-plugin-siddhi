package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.completion.providers.snippet.SnippetBlock;
import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;

public class FunctionNameContextProvider extends LSCompletionProvider {

    public FunctionNameContextProvider(){
        this.attachmentContext = SiddhiQLParser.Function_nameContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {
        List<Object[]> suggestions = new ArrayList<>();
        suggestions.add(SnippetBlock.FUNCTION_NAME_SNIPPET);
        return generateCompletionList(suggestions);
    }
}
