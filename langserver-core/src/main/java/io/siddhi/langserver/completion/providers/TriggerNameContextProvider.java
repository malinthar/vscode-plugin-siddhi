package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.LSContext;
import io.siddhi.langserver.completion.spi.LSCompletionProvider;
import io.siddhi.langserver.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;

public class TriggerNameContextProvider extends LSCompletionProvider {
    public TriggerNameContextProvider(){
        this.attachmentPoints.add(SiddhiQLParser.Trigger_nameContext.class);
    }
    public List<CompletionItem> getCompletions(LSContext context){
        List<CompletionItem> completionItems=new ArrayList<>();
        return completionItems;
    }
}
