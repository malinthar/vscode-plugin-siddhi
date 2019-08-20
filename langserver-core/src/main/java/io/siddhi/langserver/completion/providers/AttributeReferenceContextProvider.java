package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.LSContext;
import io.siddhi.langserver.completion.snippet.Snippet;
import io.siddhi.langserver.completion.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.IntervalSet;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionItemKind;

import java.util.ArrayList;
import java.util.List;

public class AttributeReferenceContextProvider extends LSCompletionProvider {
    public AttributeReferenceContextProvider(){
        this.attachmentPoints.add(SiddhiQLParser.Attribute_referenceContext.class);
    }
    public List<CompletionItem> getCompletions(LSContext lsContext){
        List<CompletionItem> completionItems=new ArrayList<>();
        ParserRuleContext currcontext=lsContext.getCurrentContext();
        int st=currcontext.invokingState;
        Snippet snippet=new Snippet();

        ((SiddhiQLParser.Attribute_referenceContext)lsContext.getCurrentContext()).attribute_name();
        List<String> refs= (ArrayList)snippet.getAttributeReference(lsContext);
        for(String atr:refs){
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
