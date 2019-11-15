package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.completion.providers.snippet.SnippetBlock;
import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.eclipse.lsp4j.CompletionItem;

import java.util.Arrays;
import java.util.List;

public class NameContextProvider extends LSCompletionProvider {
    public NameContextProvider() {
        this.attachmentContext = SiddhiQLParser.NameContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {
        /**
         * as this name context doesn't have specific completion items visit parent.
         */
        ParserRuleContext parent = visitParent();
        if (parent instanceof SiddhiQLParser.App_annotationContext) {
            List<Object[]> suggestions = Arrays.asList(SnippetBlock.KEYWORD_ANNOTATION_NAME,
                    SnippetBlock.KEYWORD_ANNOTATION_DESCRIPTION);
            return  generateCompletionList(suggestions);
        } else {
            return generateCompletionList(null);
        }
    }
}