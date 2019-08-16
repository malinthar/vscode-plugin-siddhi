package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.LSContext;
import io.siddhi.langserver.completion.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;

public class AttributeNameContextProvider extends LSCompletionProvider {
    public AttributeNameContextProvider(){
        this.attachmentPoints.add(SiddhiQLParser.Attribute_nameContext.class);
    }
    public List<CompletionItem> getCompletions(LSContext context){
        List<CompletionItem> completionItems=new ArrayList<>();
        return completionItems;
    }
}
