package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.eclipse.lsp4j.CompletionItem;

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
        return generateCompletionList(null);
    }
}