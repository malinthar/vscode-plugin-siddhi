package io.siddhi.langserver.completion.providers.common;

import io.siddhi.langserver.completion.providers.CompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.List;

public class NameContextProvider extends CompletionProvider {

    public NameContextProvider() {

        this.providerName = SiddhiQLParser.NameContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {
        //Name context doesn't have specific completion items.
        return generateCompletionList(null);
    }
}