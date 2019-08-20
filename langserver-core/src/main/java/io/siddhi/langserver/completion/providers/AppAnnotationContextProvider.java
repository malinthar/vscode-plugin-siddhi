package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.LSContext;
import io.siddhi.langserver.completion.snippet.Snippet;
import io.siddhi.langserver.completion.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNState;
import org.antlr.v4.runtime.misc.IntervalSet;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionItemKind;
import io.siddhi.query.compiler.SiddhiCompiler;
import io.siddhi.query.compiler.SiddhiQLLexer;
import io.siddhi.query.compiler.SiddhiQLParser;

import java.util.ArrayList;
import java.util.List;

public class AppAnnotationContextProvider extends LSCompletionProvider {
    public AppAnnotationContextProvider(){
        this.attachmentPoints.add(SiddhiQLParser.App_annotationContext.class);
    }
     public List<CompletionItem> getCompletions(LSContext lsContext){
         List<CompletionItem> completionItems=new ArrayList<>();
         Object tree=lsContext.getParserContextTree();
         ParserRuleContext currcontext=lsContext.getCurrentContext();
         Snippet snippet=new Snippet();
         String[] atrtypes= snippet.getAnnotationElements(lsContext);
         for(String atr:atrtypes){
             CompletionItem completionItem = new CompletionItem();
             completionItem.setInsertText(atr);
             completionItem.setLabel(atr);
             completionItem.setKind(CompletionItemKind.Text);
             completionItem.setDetail("AppAnnotationContext");
             completionItems.add(completionItem);
         }
         return completionItems;
    }


}
