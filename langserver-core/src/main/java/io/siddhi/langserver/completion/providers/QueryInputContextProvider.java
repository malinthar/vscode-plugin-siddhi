package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.LSOperationContext;
import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import io.siddhi.query.compiler.langserver.LSErrorNode;
import org.antlr.v4.runtime.ParserRuleContext;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;

public class QueryInputContextProvider extends LSCompletionProvider {

    public QueryInputContextProvider() {

        this.attachmentContext = SiddhiQLParser.Query_inputContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {
        //first check whether there is a query input context on context tree and then try to provide completions
        // based on the factor whether  a context exist or not.
        ParserRuleContext queryInputContextProvider =
                (ParserRuleContext) LSOperationContext.INSTANCE.getContextTree().get(
                        SiddhiQLParser.Query_inputContext.class.getName());
        if (LSOperationContext.INSTANCE.getContextTree().containsKey(LSErrorNode.class.getName())) {
            LSErrorNode errorNode =
                    (LSErrorNode) LSOperationContext.INSTANCE.getContextTree().get(LSErrorNode.class.getName());
            if (errorNode.getSymbol().contains("#[")) {
                List<CompletionItem> completions = new ArrayList<>();
                completions.addAll(LSOperationContext.INSTANCE.FACTORY
                        .getProvider(SiddhiQLParser.FilterContext.class.getName())
                        .getCompletions());
                return completions;
            }
            else if(errorNode.getSymbol().contains("#")){
                List<CompletionItem> completions = new ArrayList<>();
                completions.addAll(LSOperationContext.INSTANCE.FACTORY
                        .getProvider(SiddhiQLParser.WindowContext.class.getName()).getCompletions());
                completions.addAll(LSOperationContext.INSTANCE.FACTORY
                        .getProvider(SiddhiQLParser.Stream_functionContext.class.getName()).getCompletions());

                return completions;
            }
        }
        return LSOperationContext.INSTANCE.FACTORY.getProvider(SiddhiQLParser.Standard_streamContext.class.getName())
                .getCompletions();

    }

}
