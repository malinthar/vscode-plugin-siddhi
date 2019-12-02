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

public class JoinSourceContextProvider extends LSCompletionProvider {

    public JoinSourceContextProvider() {

        this.attachmentContext = SiddhiQLParser.Join_sourceContext.class.getName();
    }

    //todo:don't have to produce terminals can straightly get text out of source
    @Override
    public List<CompletionItem> getCompletions() {

        ParserRuleContext joinStreamContext = visitParent();
        List<Object> joinSources = ContextTreeVisitor.INSTANCE.findRuleContexts(joinStreamContext,
                SiddhiQLParser.Stream_idContext.class);
        List<Object> sourceNameTerminals = new ArrayList<>();
        List<String> sourceNames = new ArrayList<>();
        for (Object joinSource : joinSources) {
            sourceNameTerminals.addAll(ContextTreeVisitor.INSTANCE.findRuleContexts((ParserRuleContext) joinSource,
                    TerminalNodeImpl.class));

        }
        for (Object sourceNameTerminal : sourceNameTerminals) {
            sourceNames.add(((TerminalNodeImpl) sourceNameTerminal).getText());
        }
        List<Object> streamIdContexts = new ArrayList<>();
        List<Object> sourceProviderContexts =
                ContextTreeVisitor.INSTANCE
                        .findRuleContexts((ParserRuleContext) LSOperationContext.INSTANCE.getContextTree().get(
                                SiddhiQLParser.Siddhi_appContext.class.getName()),
                                SiddhiQLParser.Definition_streamContext.class);

        sourceProviderContexts.addAll(ContextTreeVisitor.INSTANCE
                .findRuleContexts((ParserRuleContext) LSOperationContext.INSTANCE.getContextTree().get(
                        SiddhiQLParser.Siddhi_appContext.class.getName()),
                        SiddhiQLParser.Definition_tableContext.class));

        sourceProviderContexts.addAll(ContextTreeVisitor.INSTANCE
                .findRuleContexts((ParserRuleContext) LSOperationContext.INSTANCE.getContextTree().get(
                        SiddhiQLParser.Siddhi_appContext.class.getName()),
                        SiddhiQLParser.Definition_windowContext.class));

        List<Object> definitionAggregationContexts =
                ContextTreeVisitor.INSTANCE
                        .findRuleContexts((ParserRuleContext) LSOperationContext.INSTANCE.getContextTree().get(
                                SiddhiQLParser.Siddhi_appContext.class.getName()),
                                SiddhiQLParser.Definition_aggregationContext.class);

        for (Object sourceProviderContext : sourceProviderContexts) {
            streamIdContexts.addAll(ContextTreeVisitor.INSTANCE
                    .findRuleContexts((ParserRuleContext) sourceProviderContext,
                            SiddhiQLParser.Stream_idContext.class));
        }
        for (Object definitionAggregationContext : definitionAggregationContexts) {
            streamIdContexts.addAll(ContextTreeVisitor.INSTANCE
                    .findRuleContexts((ParserRuleContext) definitionAggregationContext,
                            SiddhiQLParser.Aggregation_nameContext.class));
        }
        List<String> sources = new ArrayList<>();
        for (Object streamIdContext : streamIdContexts) {
            String source = ((ParserRuleContext) streamIdContext).getText();
            if (!sourceNames.contains(source)) {
                sources.add(source);
            }
        }
        List<Object[]> suggestions = SnippetBlock.generateSourceReferences(sources);
        return generateCompletionList(suggestions);
    }
}
