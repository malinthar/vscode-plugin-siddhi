package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.LSContext;

import io.siddhi.langserver.completion.snippet.SnippetProvider;
import io.siddhi.langserver.completion.spi.LSCompletionProvider;
import io.siddhi.langserver.compiler.SiddhiQLParser;


import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;



public class AttributeTypeContextProvider extends LSCompletionProvider {
    public AttributeTypeContextProvider(){

        this.attachmentPoints.add(SiddhiQLParser.Attribute_typeContext.class);
    }
    public List<CompletionItem> getCompletions(LSContext lsContext){
        SnippetProvider sinppetProvider=new SnippetProvider();
        return (ArrayList)sinppetProvider.getSnippets((SiddhiQLParser.Attribute_typeContext) lsContext.getCurrentContext(),lsContext);
    }
}

