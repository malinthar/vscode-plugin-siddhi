package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.LSContext;
import io.siddhi.langserver.completion.ContextTreeGenerator;
import io.siddhi.langserver.completion.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser.Definition_streamContext;
import io.siddhi.query.compiler.internal.SiddhiQLLangServerBaseVisitorImpl;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionItemKind;

import java.util.ArrayList;
import java.util.List;

public class DefinitionStreamContextProvider extends LSCompletionProvider {
    public DefinitionStreamContextProvider(){
        this.attachmentPoints.add(Definition_streamContext.class);
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
