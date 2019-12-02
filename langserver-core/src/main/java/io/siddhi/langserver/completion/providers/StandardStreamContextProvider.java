package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.LSOperationContext;
import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.List;

public class StandardStreamContextProvider extends LSCompletionProvider {

    public StandardStreamContextProvider() {

        this.attachmentContext = SiddhiQLParser.Standard_streamContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {
       return  LSOperationContext.INSTANCE.FACTORY.getProvider(SiddhiQLParser.SourceContext.class.getName()).getDefaultCompletions();
    }
}
