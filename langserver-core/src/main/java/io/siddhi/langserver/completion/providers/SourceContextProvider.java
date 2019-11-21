package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.LSOperationContext;
import io.siddhi.langserver.completion.providers.snippet.SnippetBlock;
import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.langserver.completion.util.ContextTreeVisitor;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;

public class SourceContextProvider extends LSCompletionProvider {

    public SourceContextProvider() {
        this.attachmentContext = SiddhiQLParser.SourceContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {

        List<Object> streamIdContexts = new ArrayList<>();
        List<Object> definitionStreamContexts =
                ContextTreeVisitor.INSTANCE.findRuleContexts((ParserRuleContext) LSOperationContext.INSTANCE.getContextTree().get(
                        SiddhiQLParser.Siddhi_appContext.class.getName()),
                        SiddhiQLParser.Definition_streamContext.class);

        for(Object definitionStreamContext: definitionStreamContexts){
            streamIdContexts.addAll(ContextTreeVisitor.INSTANCE.findRuleContexts((ParserRuleContext) definitionStreamContext,
                    SiddhiQLParser.Stream_idContext.class));
        }
        List<Object> sourceTerminals = new ArrayList<>();
        for(Object streamIdContext:streamIdContexts){
            sourceTerminals.addAll(ContextTreeVisitor.INSTANCE.findRuleContexts((ParserRuleContext) streamIdContext,
                    TerminalNodeImpl.class));
        }
        List<Object[]> suggestions = SnippetBlock.generateSourceReferences(sourceTerminals);
        return generateCompletionList(suggestions);
    }

}
