package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.LSOperationContext;
import io.siddhi.langserver.completion.providers.snippet.SnippetBlock;
import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.langserver.completion.util.ContextTreeVisitor;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;

public class SourceContextProvider extends LSCompletionProvider {

    public SourceContextProvider() {

        this.attachmentContext = SiddhiQLParser.SourceContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {

        Object parentContext =
                (ParserRuleContext) LSOperationContext.INSTANCE.getContextTree()
                        .get(SiddhiQLParser.SourceContext.class.getName())
                        .getParent();
        if (parentContext instanceof SiddhiQLParser.Definition_streamContext) {
            List<Object[]> suggestions = new ArrayList<>();
            suggestions.add(SnippetBlock.STREAM_NAME_SNIPPET);
            return generateCompletionList(suggestions);
        } else if (parentContext instanceof SiddhiQLParser.Definition_windowContext) {
            List<Object[]> suggestions = new ArrayList<>();
            suggestions.add(SnippetBlock.WINDOW_NAME_SNIPPET);
            return generateCompletionList(suggestions);

        } else if (parentContext instanceof SiddhiQLParser.Definition_tableContext) {
            List<Object[]> suggestions = new ArrayList<>();
            suggestions.add(SnippetBlock.TABLE_NAME_SNIPPET);
            return generateCompletionList(suggestions);
        } else if (parentContext instanceof SiddhiQLParser.Join_sourceContext) {
            return generateCompletionList(null);
        } else if (parentContext instanceof SiddhiQLParser.TargetContext) {
            List<Object> sourceContexts = new ArrayList<>();
            List<String> sourceNames = new ArrayList<>();
            List<Object> sourceProviderContexts = new ArrayList<>();
            sourceProviderContexts.addAll(ContextTreeVisitor.INSTANCE
                    .findRuleContexts((ParserRuleContext) LSOperationContext.INSTANCE.getContextTree().get(
                            SiddhiQLParser.Siddhi_appContext.class.getName()),
                            SiddhiQLParser.Definition_streamContext.class));

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
                sourceContexts.addAll(ContextTreeVisitor.INSTANCE
                        .findRuleContexts((ParserRuleContext) sourceProviderContext,
                                SiddhiQLParser.SourceContext.class));
            }
            for (Object definitionAggregationContext : definitionAggregationContexts) {
                sourceContexts.addAll(ContextTreeVisitor.INSTANCE
                        .findRuleContexts((ParserRuleContext) definitionAggregationContext,
                                SiddhiQLParser.Aggregation_nameContext.class));
            }
            sourceContexts.forEach(sourceContext -> {
                sourceNames.add(((ParserRuleContext) sourceContext).getText());
            });
            return generateCompletionList(SnippetBlock.generateSourceReferences(sourceNames));
        } else {
            List<Object> streamIdContexts = new ArrayList<>();
            List<String> sources = new ArrayList<>();
            List<Object> definitionStreamContexts =
                    ContextTreeVisitor.INSTANCE
                            .findRuleContexts((ParserRuleContext) LSOperationContext.INSTANCE.getContextTree().get(
                                    SiddhiQLParser.Siddhi_appContext.class.getName()),
                                    SiddhiQLParser.Definition_streamContext.class);

            for (Object definitionStreamContext : definitionStreamContexts) {
                streamIdContexts.addAll(ContextTreeVisitor.INSTANCE
                        .findRuleContexts((ParserRuleContext) definitionStreamContext,
                                SiddhiQLParser.Stream_idContext.class));
            }
            for (Object streamIdContext : streamIdContexts) {
                sources.add(((ParserRuleContext) streamIdContext).getText());
            }
            List<Object[]> suggestions = SnippetBlock.generateSourceReferences(sources);
            return generateCompletionList(suggestions);
        }

    }

    public List<CompletionItem> getDefaultCompletions() {

        List<Object> streamIdContexts = new ArrayList<>();
        List<String> sources = new ArrayList<>();
        List<Object> definitionStreamContexts =
                ContextTreeVisitor.INSTANCE
                        .findRuleContexts((ParserRuleContext) LSOperationContext.INSTANCE.getContextTree().get(
                                SiddhiQLParser.Siddhi_appContext.class.getName()),
                                SiddhiQLParser.Definition_streamContext.class);

        for (Object definitionStreamContext : definitionStreamContexts) {
            streamIdContexts.addAll(ContextTreeVisitor.INSTANCE
                    .findRuleContexts((ParserRuleContext) definitionStreamContext,
                            SiddhiQLParser.Stream_idContext.class));
        }

        for (Object streamIdContext : streamIdContexts) {
            sources.add(((ParserRuleContext) streamIdContext).getText());
        }
        List<Object[]> suggestions = SnippetBlock.generateSourceReferences(sources);
        return generateCompletionList(suggestions);
    }

    public List<String> getDefinedSources() {

        List<Object> sourceProviderContexts = new ArrayList<>();
        List<Object> sources = new ArrayList<>();
        List<String> sourceNames = new ArrayList<>();
        ParserRuleContext siddhiAppContext =
                (ParserRuleContext) LSOperationContext.INSTANCE.getContextTree()
                        .get(SiddhiQLParser.Siddhi_appContext.class.getName());
        sourceProviderContexts.addAll(ContextTreeVisitor.INSTANCE.findRuleContexts(siddhiAppContext,
                SiddhiQLParser.Definition_aggregationContext.class));
        sourceProviderContexts.addAll(ContextTreeVisitor.INSTANCE.findRuleContexts(siddhiAppContext,
                SiddhiQLParser.Definition_streamContext.class));
        sourceProviderContexts.addAll(ContextTreeVisitor.INSTANCE.findRuleContexts(siddhiAppContext,
                SiddhiQLParser.Definition_windowContext.class));
        sourceProviderContexts.addAll(ContextTreeVisitor.INSTANCE.findRuleContexts(siddhiAppContext,
                SiddhiQLParser.Definition_aggregationContext.class));
        sourceProviderContexts.forEach(sourceProviderContext -> {
            if (sourceProviderContext instanceof SiddhiQLParser.Definition_aggregationContext) {
                sources.addAll(
                        ContextTreeVisitor.INSTANCE.findRuleContexts((ParserRuleContext) sourceProviderContext,
                                SiddhiQLParser.Aggregation_nameContext.class));
            } else {
                sources.addAll(
                        ContextTreeVisitor.INSTANCE.findRuleContexts((ParserRuleContext) sourceProviderContext,
                                SiddhiQLParser.SourceContext.class));
            }

        });
        sources.forEach(source -> {
            sourceNames.add(((ParserRuleContext) source).getText());
        });
        return sourceNames;
    }

}
