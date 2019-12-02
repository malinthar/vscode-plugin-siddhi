package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.LSOperationContext;
import io.siddhi.langserver.completion.providers.snippet.SnippetBlock;
import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import io.siddhi.query.compiler.SiddhiQLParser.Definition_streamContext;
import org.antlr.v4.runtime.ParserRuleContext;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;

public class DefinitionStreamContextProvider extends LSCompletionProvider {

    public DefinitionStreamContextProvider() {

        this.attachmentContext = Definition_streamContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {

        ParserRuleContext definitionStreamContext =
                (ParserRuleContext) LSOperationContext.INSTANCE.getContextTree()
                        .get(Definition_streamContext.class.getName());
        int childCount = definitionStreamContext.getChildCount();
        if (childCount > 0) {
            if (definitionStreamContext.getChild(childCount - 1) instanceof SiddhiQLParser.SourceContext) {
                List<Object[]> suggestions = new ArrayList<>();
                suggestions.add(SnippetBlock.ATTRIBUTE_LIST_SNIPPET);
                return generateCompletionList(suggestions);
            }
            else {
                return generateCompletionList(null);
            }
        } else {
            return generateCompletionList(null);
        }
    }

}
