package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.completion.providers.snippet.SnippetBlock;
import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.eclipse.lsp4j.CompletionItem;

import java.util.Arrays;
import java.util.List;

/**
 * {@code AnnoatationElementContextProvider} Provide completions for AnnotationElementContext.
 */
public class AnnotationElementContextProvider extends LSCompletionProvider{
    public AnnotationElementContextProvider() {
        this.attachmentContext = SiddhiQLParser.Annotation_elementContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {
        ParserRuleContext parent = visitParent();
        if (parent instanceof SiddhiQLParser.App_annotationContext) {
            //todo: whether to add Namecontext check
            List<Object[]> suggestions = Arrays.asList(SnippetBlock.APP_ANNOTATION_ELEMENT_NAME_DEFINITION,
                    SnippetBlock.APP_ANNOTATION_ELEMENT_DESCRIPTION_DEFINITION);
            return  generateCompletionList(suggestions);
        } else {
            return generateCompletionList(null);
        }
    }

}
