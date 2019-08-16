package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.LSContext;
import io.siddhi.langserver.completion.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionItemKind;

import java.util.ArrayList;
import java.util.List;

public class AttributeTypeContextProvider extends LSCompletionProvider {
    public AttributeTypeContextProvider(){
        this.attachmentPoints.add(SiddhiQLParser.Attribute_typeContext.class);
    }
    public List<CompletionItem> getCompletions(LSContext context){
        String[] atrtypes={"long","double","int"};
        List<CompletionItem> completionItems=new ArrayList<>();
        for(String atr:atrtypes){
            CompletionItem completionItem = new CompletionItem();
            completionItem.setInsertText(atr);
            completionItem.setLabel(atr);
            completionItem.setKind(CompletionItemKind.Text);
            completionItem.setDetail("AttributeTypeContext");
            completionItems.add(completionItem);
        }
        return completionItems;
    }
}
