package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.LSOperationContext;
import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import io.siddhi.query.compiler.langserver.LSErrorNode;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;

public class ExecutionElementContextProvider extends LSCompletionProvider {

    public ExecutionElementContextProvider() {

        this.attachmentContext = SiddhiQLParser.Execution_elementContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {
        //todo: using equals and ===
        LSErrorNode errorNode =
                (LSErrorNode) LSOperationContext.INSTANCE.getContextTree().get(LSErrorNode.class.getName());
        if (errorNode.getPreviousSymbol().contains("@")) {
            return LSOperationContext.INSTANCE.FACTORY.getProvider(SiddhiQLParser.AnnotationContext.class.getName())
                    .getCompletions();

        }
        return generateCompletionList(null);
    }
}
