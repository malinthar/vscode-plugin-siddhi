package io.siddhi.langserver.completion.providers.trigger;

import io.siddhi.langserver.utils.SnippetBlockUtil;
import io.siddhi.langserver.completion.providers.CompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Provide completions for TriggerNameContext.
 * {@link io.siddhi.query.compiler.SiddhiQLParser.Trigger_nameContext}.
 */
public class TriggerNameContextProvider extends CompletionProvider {
    public TriggerNameContextProvider() {
        this.providerName = SiddhiQLParser.Trigger_nameContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {
        List<Object[]> suggestions = new ArrayList<>();
        suggestions.add(SnippetBlockUtil.TRIGGER_NAME_SNIPPET);
        return generateCompletionList(suggestions);
    }
}
