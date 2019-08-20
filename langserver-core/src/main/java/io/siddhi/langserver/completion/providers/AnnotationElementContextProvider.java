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

public class AnnotationElementContextProvider extends LSCompletionProvider {
    public AnnotationElementContextProvider(){

        this.attachmentPoints.add(SiddhiQLParser.Annotation_elementContext.class);
    }
    public List<CompletionItem> getCompletions(LSContext lsContext){
        List<CompletionItem> completionItems=new ArrayList<>();
        Snippet snippet=new Snippet();
        String[] atrtypes= snippet.getAnnotationElements(lsContext);
        for(String atr:atrtypes){
            CompletionItem completionItem = new CompletionItem();
            completionItem.setInsertText(atr);
            completionItem.setLabel(atr);
            completionItem.setKind(CompletionItemKind.Text);
            completionItem.setDetail("AnnotationElementContext");
            completionItems.add(completionItem);
        }
        return completionItems;
    }
}
