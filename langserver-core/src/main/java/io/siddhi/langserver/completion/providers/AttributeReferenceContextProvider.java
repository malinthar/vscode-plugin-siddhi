package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.LSOperationContext;
import io.siddhi.langserver.completion.providers.snippet.SnippetProvider;
import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;


import java.util.ArrayList;
import java.util.List;

public class AttributeReferenceContextProvider extends LSCompletionProvider {
    public AttributeReferenceContextProvider(){
        this.attachmentContext = SiddhiQLParser.Attribute_referenceContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {
        return null;
    }

    public List<CompletionItem> getCompletions(LSOperationContext lsContext){
        SnippetProvider sinppetProvider=new SnippetProvider();
        return (ArrayList)sinppetProvider.getSnippets((SiddhiQLParser.Attribute_referenceContext) lsContext.getCurrentContext(),lsContext);
    }
}

