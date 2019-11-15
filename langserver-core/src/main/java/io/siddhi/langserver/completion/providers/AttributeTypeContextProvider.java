package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.completion.providers.snippet.SnippetBlock;
import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionItemKind;

import java.util.List;

/**
 * {@code AttributeTypeContextProvider} Provide completions within attribute_type context.
 */
public class AttributeTypeContextProvider extends LSCompletionProvider {
    public AttributeTypeContextProvider() {
        this.attachmentContext = SiddhiQLParser.Attribute_typeContext.class.getName();
        this.completionItemKind = CompletionItemKind.Keyword;
    }

    @Override
    public List<CompletionItem> getCompletions() {
        List<Object[]> suggestions = SnippetBlock.attributeTypes;
        return generateCompletionList(suggestions);
    }
}

//todo: get all the completions provided by child classes and filter them out based on the parent contexts.