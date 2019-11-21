package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.LSOperationContext;
import io.siddhi.langserver.completion.providers.snippet.SnippetBlock;
import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;

public class OutputAttributeContextProvider  extends LSCompletionProvider {

    public OutputAttributeContextProvider() {
        this.attachmentContext = SiddhiQLParser.Output_attributeContext.class.getName();
    }
    //add_attribute as well
    @Override
    public List<CompletionItem> getCompletions() {

        List<CompletionItem> completions =
                LSOperationContext.INSTANCE.FACTORY.getProvider(SiddhiQLParser.Attribute_referenceContext.class.getName()).getCompletions();
        completions.addAll(LSOperationContext.INSTANCE.FACTORY.getProvider(SiddhiQLParser.AttributeContext.class.getName()).getCompletions());
        List<Object[]> suggestions = new ArrayList<>();
        suggestions.add(SnippetBlock.KEYWORD_AS);
        completions.addAll(generateCompletionList(suggestions));
        return completions;
    }

}
