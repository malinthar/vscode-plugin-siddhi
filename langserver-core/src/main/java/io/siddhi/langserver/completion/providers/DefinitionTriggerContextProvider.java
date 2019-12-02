package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.LSOperationContext;
import io.siddhi.langserver.completion.providers.snippet.SnippetBlock;
import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;

public class DefinitionTriggerContextProvider extends LSCompletionProvider {

    public  DefinitionTriggerContextProvider(){
        this.attachmentContext = SiddhiQLParser.Definition_triggerContext.class.getName();
    }
    @Override
    public List<CompletionItem> getCompletions() {
        List<Object[]> suggestions = new ArrayList<>();
        ParserRuleContext definitionTriggerContext = (ParserRuleContext)
                LSOperationContext.INSTANCE.getContextTree().get(SiddhiQLParser.Definition_triggerContext.class.getName());
        int childCount = definitionTriggerContext.getChildCount();
        ParseTree lastChild = definitionTriggerContext.getChild(childCount-1);
        if(lastChild instanceof TerminalNodeImpl){
            if(((TerminalNodeImpl)lastChild).getText().equalsIgnoreCase("at")){
                suggestions.add(SnippetBlock.KEYWORD_EVERY);
            }
            else{
                suggestions.add(SnippetBlock.KEYWORD_AT);
            }
        }
        else {
            suggestions.add(SnippetBlock.KEYWORD_AT);
        }

        return  generateCompletionList(suggestions);
    }
}
