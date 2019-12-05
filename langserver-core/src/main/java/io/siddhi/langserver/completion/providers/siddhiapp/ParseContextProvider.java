package io.siddhi.langserver.completion.providers.siddhiapp;

import io.siddhi.langserver.LSCompletionContext;
import io.siddhi.langserver.completion.providers.CompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.List;

public class ParseContextProvider extends CompletionProvider {

    public ParseContextProvider() {

        this.providerName = SiddhiQLParser.ParseContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {
        /**
         *   parse context is taken as the current context when the source code above the cursor position is a valid
         *   siddhi app.
         */
        return LSCompletionContext.INSTANCE.getProvider(SiddhiQLParser.Siddhi_appContext.class.getName())
                .getCompletions();
    }
}
