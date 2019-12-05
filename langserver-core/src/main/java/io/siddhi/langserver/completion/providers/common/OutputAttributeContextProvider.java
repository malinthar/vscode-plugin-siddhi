package io.siddhi.langserver.completion.providers.common;

import io.siddhi.langserver.LSCompletionContext;
import io.siddhi.langserver.utils.SnippetBlockUtil;
import io.siddhi.langserver.completion.providers.CompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides completions for OutputAttributeContext.
 * {@link io.siddhi.query.compiler.SiddhiQLParser.Output_attributeContext}.
 */
public class OutputAttributeContextProvider  extends CompletionProvider {

    public OutputAttributeContextProvider() {
        this.providerName = SiddhiQLParser.Output_attributeContext.class.getName();
    }
    @Override
    public List<CompletionItem> getCompletions() {
        List<CompletionItem> completions = new ArrayList<>();
        List<Object[]> suggestions = new ArrayList<>();
        completions.addAll(LSCompletionContext.INSTANCE
                       .getProvider(SiddhiQLParser.AttributeContext.class.getName()).getCompletions());
        suggestions.add(SnippetBlockUtil.KEYWORD_AS);
        completions.addAll(generateCompletionList(suggestions));
        return completions;
    }

}
