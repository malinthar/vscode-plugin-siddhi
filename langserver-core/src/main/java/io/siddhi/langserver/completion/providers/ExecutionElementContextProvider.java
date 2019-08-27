package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.LSContext;
import io.siddhi.langserver.completion.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionItemKind;

import java.util.ArrayList;
import java.util.List;

public class ExecutionElementContextProvider extends LSCompletionProvider {
    public ExecutionElementContextProvider(){
        this.attachmentPoints.add(SiddhiQLParser.Execution_elementContext.class);
    }
    public List<CompletionItem> getCompletions(LSContext lsContext){
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
