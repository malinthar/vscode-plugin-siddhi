package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.LSOperationContext;
import io.siddhi.langserver.completion.providers.snippet.SnippetBlock;
import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;

public class DefinitionWindowContextProvider extends LSCompletionProvider {

    public DefinitionWindowContextProvider(){
        this.attachmentContext = SiddhiQLParser.Definition_windowContext.class.getName();
    }
    @Override
    public List<CompletionItem> getCompletions() {

        ParserRuleContext definitionWindowContext =
                (ParserRuleContext) LSOperationContext.INSTANCE.getContextTree().get(
                SiddhiQLParser.Definition_windowContext.class.getName());
        int childCount = definitionWindowContext.getChildCount();
        if(childCount>0){
            if(definitionWindowContext.getChild(childCount-1) instanceof SiddhiQLParser.SourceContext){
                //todo: decalare default snippet getters as static
                List<Object[]> suggestions = new ArrayList<>();
                suggestions.add(SnippetBlock.ATTRIBUTE_LIST_SNIPPET);
                return generateCompletionList(suggestions);
            }
        }
        return generateCompletionList(null);
    }
}
