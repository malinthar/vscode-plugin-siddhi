package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.completion.providers.snippet.SnippetBlock;
import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.List;

public class BoolValueContextProvider extends LSCompletionProvider {
    public BoolValueContextProvider(){
        this.attachmentContext = SiddhiQLParser.Bool_valueContext.class.getName();
    }
    @Override
    public List<CompletionItem> getCompletions() {
        return generateCompletionList(SnippetBlock.BOOLEAN_CONSTANTS);
    }
}
