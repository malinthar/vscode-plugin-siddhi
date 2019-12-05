package io.siddhi.langserver.completion.providers.constant;

import io.siddhi.langserver.utils.SnippetBlockUtil;
import io.siddhi.langserver.completion.providers.CompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.List;

/**
 * Provide completions for TimeValueContext.
 * {@link io.siddhi.query.compiler.SiddhiQLParser.Time_valueContext}.
 */
public class TimeValueContextProvider  extends CompletionProvider {

    public TimeValueContextProvider(){
        this.providerName = SiddhiQLParser.Time_valueContext.class.getName();
    }
    @Override
    public List<CompletionItem> getCompletions() {
        return generateCompletionList(SnippetBlockUtil.TIME_VALUE);
    }
}
