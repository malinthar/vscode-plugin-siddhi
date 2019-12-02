package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.LSOperationContext;
import io.siddhi.langserver.completion.providers.snippet.SnippetBlock;
import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;

public class DefinitionFunctionContextProvider extends LSCompletionProvider {
    public DefinitionFunctionContextProvider(){
        this.attachmentContext = SiddhiQLParser.Definition_functionContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {
        ParserRuleContext definitionFunctionContext =
                (ParserRuleContext) LSOperationContext.INSTANCE.getContextTree().get(
                        SiddhiQLParser.Definition_functionContext.class.getName());
        int childCount = definitionFunctionContext.getChildCount();
        if(childCount>0){
            if(definitionFunctionContext.getChild(childCount-1) instanceof SiddhiQLParser.Function_nameContext){
                //todo: decalare default snippet getters as static
                List<Object[]> suggestions = new ArrayList<>();
                suggestions.add(SnippetBlock.LANGUAGE_NAME_SNIPPET);
                return generateCompletionList(suggestions);
            }
            //todo: find a better way to do this
            else if(childCount >1 && definitionFunctionContext.getChild(childCount-2) instanceof SiddhiQLParser.Language_nameContext){
                List<Object[]> suggestions = new ArrayList<>();
                suggestions.add(SnippetBlock.KEYWORD_RETURN);
                return generateCompletionList(suggestions);
            }
            else {
                return generateCompletionList(null);
            }
        }
        return generateCompletionList(null);
    }


}
