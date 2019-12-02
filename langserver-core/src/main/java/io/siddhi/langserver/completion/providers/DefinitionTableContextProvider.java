package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.LSOperationContext;
import io.siddhi.langserver.completion.providers.snippet.SnippetBlock;
import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;

public class DefinitionTableContextProvider extends LSCompletionProvider {

    public DefinitionTableContextProvider(){
        this.attachmentContext = SiddhiQLParser.Definition_tableContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {
        ParserRuleContext definitionTableContext =
                (ParserRuleContext) LSOperationContext.INSTANCE.getContextTree().get(
                        SiddhiQLParser.Definition_tableContext.class.getName());
        int childCount = definitionTableContext.getChildCount();
        if(childCount>0){
            if(definitionTableContext.getChild(childCount-1) instanceof SiddhiQLParser.SourceContext){
                //todo: decalare default snippet getters as static
                List<Object[]> suggestions = new ArrayList<>();
                suggestions.add(SnippetBlock.ATTRIBUTE_LIST_SNIPPET);
                return generateCompletionList(suggestions);
            }
        }
        return generateCompletionList(null);
    }
}
