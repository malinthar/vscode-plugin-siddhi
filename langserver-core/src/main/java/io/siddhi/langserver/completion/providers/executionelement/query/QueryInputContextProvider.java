package io.siddhi.langserver.completion.providers.executionelement.query;

import io.siddhi.langserver.LSCompletionContext;
import io.siddhi.langserver.completion.providers.CompletionProvider;
import io.siddhi.langserver.beans.LSErrorNode;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Provide completions for queryInputContext {@link io.siddhi.query.compiler.SiddhiQLParser.Query_inputContext}.
 */
public class QueryInputContextProvider extends CompletionProvider {

    public QueryInputContextProvider() {

        this.providerName = SiddhiQLParser.Query_inputContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {
        //first check whether there is a query input context on context tree and then try to provide completions
        // based on the factor whether  a context exist or not.
        if (LSCompletionContext.INSTANCE.getParseTreeMap().containsKey(LSErrorNode.class.getName())) {
            LSErrorNode errorNode =
                    (LSErrorNode) LSCompletionContext.INSTANCE.getParseTreeMap().get(LSErrorNode.class.getName());
            if (errorNode.getErroneousSymbol().contains("#[")) {
                List<CompletionItem> completions = new ArrayList<>();
                completions.addAll(LSCompletionContext.INSTANCE
                        .getProvider(SiddhiQLParser.FilterContext.class.getName())
                        .getCompletions());
                return completions;
            } else if (errorNode.getErroneousSymbol().contains("#")) {
                List<CompletionItem> completions = new ArrayList<>();
                completions.addAll(LSCompletionContext.INSTANCE
                        .getProvider(SiddhiQLParser.WindowContext.class.getName()).getCompletions());
                completions.addAll(LSCompletionContext.INSTANCE
                        .getProvider(SiddhiQLParser.Stream_functionContext.class.getName()).getCompletions());

                return completions;
            }
        }
        return LSCompletionContext.INSTANCE
                .getProvider(SiddhiQLParser.Standard_streamContext.class.getName())
                .getCompletions();
    }

}
