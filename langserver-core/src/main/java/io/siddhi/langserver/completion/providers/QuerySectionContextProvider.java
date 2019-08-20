package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.LSContext;
import io.siddhi.langserver.completion.snippet.Snippet;
import io.siddhi.langserver.completion.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionItemKind;

import java.util.ArrayList;
import java.util.List;

public class QuerySectionContextProvider extends LSCompletionProvider {
    public QuerySectionContextProvider(){
        this.attachmentPoints.add(SiddhiQLParser.Query_sectionContext.class);
    }
    public List<CompletionItem> getCompletions(LSContext lsContext){
        List<CompletionItem> completionItems=new ArrayList<>();
        ParserRuleContext currcontext=lsContext.getCurrentContext();
        Snippet snippet=new Snippet();
        List<String> refs= (ArrayList)snippet.getQuerySection(lsContext);
        for(String atr:refs){
            CompletionItem completionItem = new CompletionItem();
            completionItem.setInsertText(atr);
            completionItem.setLabel(atr);
            completionItem.setKind(CompletionItemKind.Text);
            completionItem.setDetail("QuerySectionContext");
            completionItems.add(completionItem);
        }
        return completionItems;
    }
}
