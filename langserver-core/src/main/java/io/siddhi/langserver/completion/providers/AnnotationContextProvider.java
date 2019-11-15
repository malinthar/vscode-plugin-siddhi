package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.LSOperationContext;
import io.siddhi.langserver.completion.providers.snippet.SnippetBlock;
import io.siddhi.langserver.completion.providers.snippet.SnippetProvider;
import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code AnnoatationContextProvider} Provide completions for AnnotationContext.
 */
public class AnnotationContextProvider extends LSCompletionProvider {

    public AnnotationContextProvider() {
        this.attachmentContext = SiddhiQLParser.App_annotationContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {
        List<Object[]> suggestions = SnippetBlock.attributeTypes;
        return generateCompletionList(suggestions);
    }

}

//todo: I can provide a list of completions items that should be there in a context