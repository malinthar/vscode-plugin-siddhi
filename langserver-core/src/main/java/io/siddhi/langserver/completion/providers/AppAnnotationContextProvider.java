package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.LSContext;
import io.siddhi.langserver.completion.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.antlr.v4.runtime.ParserRuleContext;
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
        Object tree=lsContext.getParserContextTree();
        ParserRuleContext currctx=lsContext.getCurrentContext();
        CompletionItem completionItem = new CompletionItem();
        completionItem.setInsertText(currctx.getClass().toString());
        completionItem.setLabel(currctx.getClass().toString());
        completionItem.setKind(CompletionItemKind.Text);
        completionItem.setDetail("completion test");
        List<CompletionItem> completionItems=new ArrayList<>();
        completionItems.add(completionItem);
        return completionItems;
    }


}
