package io.siddhi.langserver.completion.providers.executionelement;

import io.siddhi.langserver.LSCompletionContext;
import io.siddhi.langserver.completion.providers.CompletionProvider;
import io.siddhi.langserver.beans.LSErrorNode;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides completions for ExecutionElementContext.
 * {@link io.siddhi.query.compiler.SiddhiQLParser.Execution_elementContext}.
 */
public class ExecutionElementContextProvider extends CompletionProvider {

    public ExecutionElementContextProvider() {
        this.providerName = SiddhiQLParser.Execution_elementContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {

        List<Object[]> suggestions = new ArrayList<>();
        LSErrorNode errorNode = (LSErrorNode) LSCompletionContext.INSTANCE
                .getParseTreeMap().get(LSErrorNode.class.getName());
        if (errorNode.getPreviousSymbol().contains("@")) {
            return LSCompletionContext.INSTANCE
                    .getProvider(SiddhiQLParser.AnnotationContext.class.getName()).getCompletions();

        }
        return generateCompletionList(suggestions);
    }
}
