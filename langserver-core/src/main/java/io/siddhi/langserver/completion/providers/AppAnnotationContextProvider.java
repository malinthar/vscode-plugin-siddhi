package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.completion.providers.snippet.SnippetBlock;
import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.List;

/**
 * {@code AppAnnoatatioContextProvider} Provide completions for AnnotationContext.
 */
public class AppAnnotationContextProvider extends LSCompletionProvider {

    public AppAnnotationContextProvider() {

        this.attachmentContext = SiddhiQLParser.App_annotationContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {
        List<Object[]> suggestions = SnippetBlock.APP_ANNOTATION_DEFINITION;
        //suggestions.add(SnippetBlock.ANNOTATION_ELEMENT_DEFINITION);
        return generateCompletionList(suggestions);
    }

}
