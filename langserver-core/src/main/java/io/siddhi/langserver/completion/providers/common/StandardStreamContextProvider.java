package io.siddhi.langserver.completion.providers.common;

import io.siddhi.langserver.LSCompletionContext;
import io.siddhi.langserver.completion.providers.CompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.List;

/**
 * Provide completions for StandardStreamContext.
 * {@link io.siddhi.query.compiler.SiddhiQLParser.Standard_streamContext}.
 */
public class StandardStreamContextProvider extends CompletionProvider {

    public StandardStreamContextProvider() {
        this.providerName = SiddhiQLParser.Standard_streamContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {
        SourceContextProvider sourceContextProvider = (SourceContextProvider) LSCompletionContext.INSTANCE
                .getProvider(SiddhiQLParser.SourceContext.class.getName());
       return  sourceContextProvider.getDefaultCompletions();
    }
}
