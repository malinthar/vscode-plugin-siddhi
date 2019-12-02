package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.LSOperationContext;
import io.siddhi.langserver.completion.providers.snippet.SnippetBlock;
import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.langserver.completion.util.ContextTreeVisitor;
import io.siddhi.query.compiler.SiddhiQLParser;
import io.siddhi.query.compiler.langserver.LSErrorNode;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttributeReferenceContextProvider extends LSCompletionProvider {

    public AttributeReferenceContextProvider() {

        this.scopes = new ArrayList<>();
        this.scopes.add(SiddhiQLParser.Definition_windowContext.class.getName());
        this.scopes.add(SiddhiQLParser.Definition_aggregationContext.class.getName());
        this.scopes.add(SiddhiQLParser.QueryContext.class.getName());
        this.scopes.add(SiddhiQLParser.Query_outputContext.class.getName());
        this.attachmentContext = SiddhiQLParser.Attribute_referenceContext.class.getName();
    }

    /**
     * identified scopes: DefinitionWindowContext, Aggregation Context.
     *
     * @return
     */
    @Override
    public List<CompletionItem> getCompletions() {
        //todo: provide (#|!)? attribute_name[attribute_index]?(#attribute_name([attribute_index])?.attribute_name)?
        // snippets based on attribute_name
        //todo: first find out attribute names and provide completions based on whether it refers to a list, object
        // (# what is meant by hash and what is meant by !)
        ParserRuleContext scopeContext = findScope();
        if (scopeContext == null) {
            return generateCompletionList(null);
        } else {
            if (scopeContext instanceof SiddhiQLParser.Definition_windowContext) {
                List<ParseTree> providerContexts = ContextTreeVisitor.INSTANCE.findFromChildren(scopeContext,
                        SiddhiQLParser.Attribute_nameContext.class);
                //todo:improve here Object is too general
                List<Object> attributeNameTerminals = new ArrayList<>();
                for (ParseTree providerContext : providerContexts) {
                    attributeNameTerminals.addAll(ContextTreeVisitor.INSTANCE
                            .findRuleContexts((ParserRuleContext) providerContext, TerminalNodeImpl.class));
                }
                return generateCompletionList(SnippetBlock.generateAttributeReferences(attributeNameTerminals));

            } else if (scopeContext instanceof SiddhiQLParser.Definition_aggregationContext) {
                try {
                    List<Object> sourceTerminals = new ArrayList<>();
                    List<Object> attributeNameTerminals = new ArrayList<>();
                    List<Object> providerContexts = ContextTreeVisitor.INSTANCE.findRuleContexts(scopeContext,
                            SiddhiQLParser.SourceContext.class);
                    for (Object providerContext : providerContexts) {
                        sourceTerminals
                                .addAll(ContextTreeVisitor.INSTANCE
                                        .findRuleContexts((ParserRuleContext) providerContext,
                                                TerminalNodeImpl.class));

                    }
                    ParserRuleContext siddhiAppContext =
                            (ParserRuleContext) LSOperationContext.INSTANCE.getContextTree()
                                    .get(SiddhiQLParser.Siddhi_appContext.class.getName());
                    List<Object> streamDefinitionContexts =
                            ContextTreeVisitor.INSTANCE.findRuleContexts(siddhiAppContext,
                                    SiddhiQLParser.Definition_streamContext.class);
                    for (Object streamDefinitionContext : streamDefinitionContexts) {
                        List<Object> streamIdContext =
                                ContextTreeVisitor.INSTANCE
                                        .findRuleContexts((ParserRuleContext) streamDefinitionContext,
                                                SiddhiQLParser.Stream_idContext.class);
                        List<Object> sourceName =
                                ContextTreeVisitor.INSTANCE.findRuleContexts((ParserRuleContext) streamIdContext.get(0),
                                        TerminalNodeImpl.class);
                        Object source1 = sourceName.get(0);
                        Object source2 = sourceTerminals.get(0);
                        if (((TerminalNodeImpl) source1).getText().equals(((TerminalNodeImpl) source2).getText())) {
                            List<ParseTree> attributeNameProviders =
                                    ContextTreeVisitor.findFromChildren((ParserRuleContext) streamDefinitionContext,
                                            SiddhiQLParser.Attribute_nameContext.class);
                            for (ParseTree attributeNameProvider : attributeNameProviders) {
                                attributeNameTerminals.addAll(ContextTreeVisitor.INSTANCE
                                        .findRuleContexts((ParserRuleContext) attributeNameProvider,
                                                TerminalNodeImpl.class));
                            }

                        }
                    }
                    List<Object[]> suggestions;
                    suggestions = SnippetBlock.generateAttributeReferences(attributeNameTerminals);
                    return generateCompletionList(suggestions);
                } catch (Throwable e) {
                    List<Object> sourceContexts = new ArrayList<>();
                    String error = "error";
                    return generateCompletionList(null);
                }
            } else if (scopeContext instanceof SiddhiQLParser.QueryContext) {

                List<Object[]> suggestions = new ArrayList<>();
                Map<String, String> sources = new HashMap<>();
                sources.putAll(findSourcesInScope(scopeContext, SiddhiQLParser.Query_inputContext.class.getName()));
                List<Object> sourceProviderContexts = findSourceProviderContexts();
                Map<String, List<String>> attributeNameMap = findAttributesOfSources(sourceProviderContexts, sources);
                suggestions.addAll(SnippetBlock.generateAttributeReferences(attributeNameMap));
                return generateCompletionList(suggestions);

            } else if (scopeContext instanceof SiddhiQLParser.Query_outputContext) {
                List<Object[]> suggestions = new ArrayList<>();
                Map<String, String> sources = new HashMap<>();
                sources.putAll(findSourcesInScope(scopeContext, SiddhiQLParser.TargetContext.class.getName()));
                List<Object> sourceProviderContexts = findSourceProviderContexts();
                Map<String, List<String>> attributeNameMap = findAttributesOfSources(sourceProviderContexts, sources);
                suggestions.addAll(SnippetBlock.generateAttributeReferences(attributeNameMap));
                return generateCompletionList(suggestions);
            } else {
                return generateCompletionList(null);
            }
        }

    }

    private Map<String, String> findSourcesInScope(ParserRuleContext scopeContext,
                                                   String sourceContainerContext) {

        List<Object> sourceContexts = new ArrayList<>();
        Map<String, String> sourceToAliasMap = new HashMap<>();
        if (sourceContainerContext.equalsIgnoreCase(SiddhiQLParser.Query_inputContext.class.getName())) {
            Object queryInputContext = ContextTreeVisitor.INSTANCE.findOneFromChildren(scopeContext,
                    SiddhiQLParser.Query_inputContext.class);
            if (queryInputContext != null) {
                if (LSOperationContext.INSTANCE.getContextTree().containsKey(LSErrorNode.class.getName())) {
                    LSErrorNode errorNode =
                            ((LSErrorNode) LSOperationContext.INSTANCE.getContextTree()
                                    .get(LSErrorNode.class.getName()));
                    if (errorNode.getSymbol().contains("#")) {
                        if (errorNode.getPreviousSymbol() != "") {
                            sourceToAliasMap.put(errorNode.getPreviousSymbol(), errorNode.getPreviousSymbol());
                        }
                    }
                }
                sourceContexts.addAll(ContextTreeVisitor.INSTANCE
                        .findRuleContexts((ParserRuleContext) queryInputContext,
                                SiddhiQLParser.Join_sourceContext.class));
                if (sourceContexts.size() == 0) {
                    sourceContexts.addAll(ContextTreeVisitor.INSTANCE
                            .findRuleContexts((ParserRuleContext) queryInputContext,
                                    SiddhiQLParser.SourceContext.class));
                }
                //todo:check if this is needed.
            }
        } else if (sourceContainerContext.equalsIgnoreCase(SiddhiQLParser.TargetContext.class.getName())) {
            Object targetContext = ContextTreeVisitor.findOneFromChildren(scopeContext,
                    SiddhiQLParser.TargetContext.class);
            if (targetContext!= null) {
                sourceContexts.addAll(ContextTreeVisitor.INSTANCE
                        .findRuleContexts((ParserRuleContext) targetContext,
                                SiddhiQLParser.SourceContext.class));
            }
        }
        for (Object sourceContext : sourceContexts) {
            if (sourceContext instanceof SiddhiQLParser.Join_sourceContext) {
                Object alias = ContextTreeVisitor.findOneFromChildren((ParserRuleContext) sourceContext,
                        SiddhiQLParser.AliasContext.class);
                Object joinSource =
                        ContextTreeVisitor.INSTANCE.findOneFromChildren((ParserRuleContext) sourceContext,
                                SiddhiQLParser.SourceContext.class);
                if (alias != null) {
                    sourceToAliasMap.put(((ParserRuleContext) joinSource).getText(),
                            ((ParserRuleContext) alias).getText());
                } else {
                    sourceToAliasMap.put(((ParserRuleContext) joinSource).getText(), null);
                }

            } else {
                sourceToAliasMap.put(((ParserRuleContext) sourceContext).getText(),
                        ((ParserRuleContext) sourceContext).getText());
            }
        }
        return sourceToAliasMap;
    }

    public List<Object> findSourceProviderContexts() {

        ParserRuleContext siddhiAppContext =
                (ParserRuleContext) LSOperationContext.INSTANCE.getContextTree()
                        .get(SiddhiQLParser.Siddhi_appContext.class.getName());
        List<Object> sourceProviderContexts = new ArrayList<>();
        List<Object> streamDefinitionContexts =
                ContextTreeVisitor.INSTANCE.findRuleContexts(siddhiAppContext,
                        SiddhiQLParser.Definition_streamContext.class);
        List<Object> aggregationDefinitionContexts =
                ContextTreeVisitor.INSTANCE.findRuleContexts(siddhiAppContext,
                        SiddhiQLParser.Definition_aggregationContext.class);
        List<Object> windowDefinitionContexts =
                ContextTreeVisitor.INSTANCE.findRuleContexts(siddhiAppContext,
                        SiddhiQLParser.Definition_windowContext.class);
        List<Object> tableDefinitionContexts =
                ContextTreeVisitor.INSTANCE.findRuleContexts(siddhiAppContext,
                        SiddhiQLParser.Definition_tableContext.class);
        sourceProviderContexts.addAll(streamDefinitionContexts);
        sourceProviderContexts.addAll(windowDefinitionContexts);
        sourceProviderContexts.addAll(tableDefinitionContexts);
        sourceProviderContexts.addAll(aggregationDefinitionContexts);
        return sourceProviderContexts;
    }

    private Map<String, List<String>> findAttributesOfSources(List<Object> sourceProviderContexts,
                                                              Map<String, String> sourceToAliasMap) {

        Map<String, List<String>> attributeNameMap = new HashMap<>();
        ArrayList<String> sources = new ArrayList(sourceToAliasMap.keySet());
        for (Object sourceProviderContext : sourceProviderContexts) {
            Object streamIdContext;
            if (sourceProviderContext instanceof SiddhiQLParser.Definition_aggregationContext) {
                streamIdContext =
                        ContextTreeVisitor.INSTANCE
                                .findRuleContexts((ParserRuleContext) sourceProviderContext,
                                        SiddhiQLParser.Aggregation_nameContext.class).get(0);
            } else {
                streamIdContext =
                        ContextTreeVisitor.INSTANCE
                                .findRuleContexts((ParserRuleContext) sourceProviderContext,
                                        SiddhiQLParser.Stream_idContext.class).get(0);
            }
            //todo:here use an alogorithm to find first occurrance
            if (streamIdContext instanceof SiddhiQLParser.Stream_idContext ||
                    streamIdContext instanceof SiddhiQLParser.Aggregation_nameContext) {
                String sourceName = ((ParserRuleContext) streamIdContext).getText();
                List<String> attributeNames = new ArrayList<>();
                if (sources.contains(sourceName)) {
                    String sourceAlias;
                    if (sourceToAliasMap.get(sourceName) != null) {
                        sourceAlias = sourceToAliasMap.get(sourceName);
                    } else {
                        sourceAlias = sourceName;
                    }
                    List<Object> attributeNameProviders = new ArrayList<>();
                    if (sourceProviderContext instanceof SiddhiQLParser.Definition_aggregationContext) {
                        List<Object> outputAttributeContexts = new ArrayList<>();
                        List<ParseTree> groupByQuerySelectionContexts =
                                ContextTreeVisitor.findFromChildren((ParserRuleContext) sourceProviderContext,
                                        SiddhiQLParser.Group_by_query_selectionContext.class);
                        for (ParseTree groupByQuerySelectionContext : groupByQuerySelectionContexts) {
                            outputAttributeContexts.addAll(ContextTreeVisitor
                                    .findFromChildren((ParserRuleContext) groupByQuerySelectionContext,
                                            SiddhiQLParser.Output_attributeContext.class));
                        }
                        for (Object outputAttributeContext : outputAttributeContexts) {
                            attributeNameProviders.addAll(
                                    ContextTreeVisitor.INSTANCE
                                            .findFromChildren((ParserRuleContext) outputAttributeContext,
                                                    SiddhiQLParser.Attribute_referenceContext.class));
                            attributeNameProviders.addAll(ContextTreeVisitor
                                    .findFromChildren((ParserRuleContext) outputAttributeContext,
                                            SiddhiQLParser.Attribute_nameContext.class));
                        }
                    } else {
                        attributeNameProviders.addAll(
                                ContextTreeVisitor.findFromChildren((ParserRuleContext) sourceProviderContext,
                                        SiddhiQLParser.Attribute_nameContext.class));
                    }
                    for (Object attributeNameProvider : attributeNameProviders) {
                        attributeNames.add(((ParserRuleContext) attributeNameProvider).getText());
                    }
                    attributeNameMap.put(sourceAlias, attributeNames);
                }
            }
        }
        return attributeNameMap;
    }

}

//todo: use a stack for tree
//todo: set clause select target's source.

