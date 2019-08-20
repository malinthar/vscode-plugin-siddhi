package io.siddhi.langserver.completion.snippet;

import io.siddhi.langserver.LSContext;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionItemKind;

import java.util.ArrayList;
import  java.util.List;

public class SinppetGenerator {
    public Object getSnippets(SiddhiQLParser.QueryContext context, LSContext lsContext){
        Snippet snippet=new Snippet();
        List<String> snips= (ArrayList)snippet.getQueryContext(lsContext);
        return this.generateCompletions(snips,lsContext);
    }
    public Object getSnippets(SiddhiQLParser.Query_inputContext context, LSContext lsContext){
        Snippet snippet=new Snippet();
        List<String> snips= (ArrayList)snippet.getQueryInput(lsContext);
        return this.generateCompletions(snips,lsContext);

    }
    public  Object getSnippets(SiddhiQLParser.Query_sectionContext context,LSContext lsContext){
        Snippet snippet=new Snippet();
        List<String> snips= (ArrayList)snippet.getQuerySection(lsContext);
        return this.generateCompletions(snips,lsContext);
    }
    public Object getSnippets(SiddhiQLParser.Attribute_referenceContext context,LSContext lsContext){
        Snippet snippet=new Snippet();
        List<String> snips= (ArrayList)snippet.getAttributeReference(lsContext);
        return this.generateCompletions(snips,lsContext);
    }
    public  Object getSnippets(SiddhiQLParser.Attribute_typeContext context,LSContext lsContext){
        Snippet snippet=new Snippet();
        List<String> snips= (ArrayList)snippet.getAttributetype(lsContext);
        return this.generateCompletions(snips,lsContext);
    }
    public List<CompletionItem> generateCompletions(List<String> suggestions,LSContext lsContext){
        List<CompletionItem> completionItems=new ArrayList<>();
        for(String suggestion:suggestions){
            CompletionItem completionItem = new CompletionItem();
            completionItem.setInsertText(suggestion);
            completionItem.setLabel(suggestion);
            completionItem.setKind(CompletionItemKind.Text);
            completionItem.setDetail(lsContext.getCurrentContext().getClass().getName());
            completionItems.add(completionItem);
        }
        return completionItems;
    }


}
