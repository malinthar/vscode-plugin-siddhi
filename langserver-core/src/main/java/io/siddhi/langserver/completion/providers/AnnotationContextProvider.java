package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.completion.providers.snippet.SnippetBlock;
import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * {@code AnnoatationContextProvider} Provide completions for AnnotationContext.
 */
public class AnnotationContextProvider extends LSCompletionProvider {

    public AnnotationContextProvider() {
        this.attachmentContext = SiddhiQLParser.AnnotationContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {
        List<Object[]> suggestions = new ArrayList<>();
        suggestions.addAll(Arrays.asList(SnippetBlock.ANNOTATION_ASYNC_DEFINITION,
                SnippetBlock.ANNOTATION_INDEX_DEFINITION,SnippetBlock.ANNOTATION_PRIMARY_KEY_DEFINITION,
                SnippetBlock.ANNOTATION_QUERYINFO_DEFINITION,
                SnippetBlock.APP_ANNOTATION_ELEMENT_DESCRIPTION_DEFINITION,
                SnippetBlock.APP_ANNOTATION_ELEMENT_NAME_DEFINITION,SnippetBlock.APP_NAME_ANNOTATION_DEFINITION,
                SnippetBlock.APP_STATISTICS_ANNOTATION_DEFINITION,SnippetBlock.APP_DESCRIPTION_ANNOTATION_DEFINITION));
        suggestions.addAll(SnippetBlock.getStoreAnnotations());
        suggestions.addAll(SnippetBlock.getSourceAnnotations());
        suggestions.addAll(SnippetBlock.getSinkAnnotations());
        return generateCompletionList(suggestions);
    }

}

//todo: I can provide a list of completions items that should be there in a context