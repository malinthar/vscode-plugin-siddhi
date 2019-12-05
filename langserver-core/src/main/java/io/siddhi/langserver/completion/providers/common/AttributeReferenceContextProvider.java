/*
 * Copyright (c) 2019, WSO2 Inc. (http://wso2.com) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.siddhi.langserver.completion.providers.common;

import io.siddhi.langserver.LSCompletionContext;
import io.siddhi.langserver.completion.providers.ScopeCompletionProvider;
import io.siddhi.langserver.utils.SnippetBlockUtil;
import io.siddhi.langserver.completion.providers.CompletionProvider;
import io.siddhi.langserver.completion.ParseTreeMapVisitor;
import io.siddhi.langserver.beans.LSErrorNode;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Provide completions for AttributeReferenceContext {@link io.siddhi.query.compiler.SiddhiQLParser.AnnotationContext}.
 */
public class AttributeReferenceContextProvider extends ScopeCompletionProvider {

    public AttributeReferenceContextProvider() {
        this.scopes = Arrays.asList(SiddhiQLParser.Definition_windowContext.class.getName(),
                SiddhiQLParser.Definition_aggregationContext.class.getName(),
                SiddhiQLParser.QueryContext.class.getName(),
                SiddhiQLParser.Query_outputContext.class.getName());
        this.providerName = SiddhiQLParser.Attribute_referenceContext.class.getName();
    }

    /**
     * Returns completion items for attribute references depending on the scope in which the current context is
     * present.
     *
     * @return {@link List<CompletionItem> } list of CompletionItem instances.
     */
    @Override
    public List<CompletionItem> getCompletions() {
        //todo: move the return to the bottom.
        ParserRuleContext scopeContext = findScope();
        if (scopeContext == null) {
            return generateCompletionList(null);
        } else {
            Map<String, List<String>> attributeNames = new HashMap<>();
            Map<String, String> sources = new HashMap<>();
            List<Object[]> suggestions = new ArrayList<>();
            if (scopeContext instanceof SiddhiQLParser.Definition_windowContext) {
                List<String> sourceProviderContextsForWindowDef = new ArrayList<>();
                //todo: collection.singletonList(), use this for passing an object instead of an array.
                sourceProviderContextsForWindowDef.add(SiddhiQLParser.Definition_windowContext.class.getName());
                // finding the available source names in the window definition.
                sources.putAll(findSourcesInScope(scopeContext, SiddhiQLParser.Stream_idContext.class.getName()));
                List<ParseTree> sourceProviderContexts = findSourceProviderContexts(sourceProviderContextsForWindowDef);
                //todo: have a method to generate attribute reference for a given source.
                attributeNames.putAll(findAttributesOfSources(sourceProviderContexts, sources));
                suggestions.addAll(SnippetBlockUtil.generateAttributeReferences(attributeNames));
                return generateCompletionList(suggestions);
                //todo: don't use put all pass them in the constructor.
            } else if (scopeContext instanceof SiddhiQLParser.Definition_aggregationContext) {
                List<String> sourceProviderContextsForAggregationDef = new ArrayList<>();
                sourceProviderContextsForAggregationDef.add(SiddhiQLParser.Definition_streamContext.class.getName());
                ParseTreeMapVisitor parseTreeMapVisitor = LSCompletionContext.INSTANCE.getParseTreeMapVisitor();
                Map<String, ParseTree> contextTree = LSCompletionContext.INSTANCE.getParseTreeMap();

                // finding the available source names in the aggregation definition.
                sources.putAll(findSourcesInScope(scopeContext, SiddhiQLParser.Standard_streamContext.class.getName()));
                List<ParseTree> sourceProviderContexts =
                        findSourceProviderContexts(sourceProviderContextsForAggregationDef);
                attributeNames.putAll(findAttributesOfSources(sourceProviderContexts, sources));
                suggestions.addAll(SnippetBlockUtil.generateAttributeReferences(attributeNames));
                return generateCompletionList(suggestions);

            } else if (scopeContext instanceof SiddhiQLParser.QueryContext) {
                List<String> sourceProviderContextsForQuery = new ArrayList<>();
                sourceProviderContextsForQuery
                        .addAll(Arrays.asList(SiddhiQLParser.Definition_streamContext.class.getName(),
                                SiddhiQLParser.Definition_aggregationContext.class.getName(),
                                SiddhiQLParser.Definition_tableContext.class.getName(),
                                SiddhiQLParser.Definition_windowContext.class.getName()));

                // finding the available source names in the query context.
                sources.putAll(findSourcesInScope(scopeContext, SiddhiQLParser.Query_inputContext.class.getName()));
                List<ParseTree> sourceProviderContexts = findSourceProviderContexts(sourceProviderContextsForQuery);
                attributeNames.putAll(findAttributesOfSources(sourceProviderContexts, sources));
                suggestions.addAll(SnippetBlockUtil.generateAttributeReferences(attributeNames));
                return generateCompletionList(suggestions);

            } else if (scopeContext instanceof SiddhiQLParser.Query_outputContext) {
                List<String> sourceProviderContextsForQueryOutput = new ArrayList<>();
                sourceProviderContextsForQueryOutput
                        .addAll(Arrays.asList(SiddhiQLParser.Definition_streamContext.class.getName(),
                                SiddhiQLParser.Definition_aggregationContext.class.getName(),
                                SiddhiQLParser.Definition_tableContext.class.getName(),
                                SiddhiQLParser.Definition_windowContext.class.getName()));

                sources.putAll(findSourcesInScope(scopeContext, SiddhiQLParser.TargetContext.class.getName()));
                List<ParseTree> sourceProviderContexts =
                        findSourceProviderContexts(sourceProviderContextsForQueryOutput);
                attributeNames.putAll(findAttributesOfSources(sourceProviderContexts, sources));
                suggestions.addAll(SnippetBlockUtil.generateAttributeReferences(attributeNames));
                return generateCompletionList(suggestions);

            } else {
                return generateCompletionList(null);
            }
        }
    }

    /**
     * Given a context in a particular scope, finds the contained sources.
     *
     * @param scopeContext           scope that can be seen by the current context.
     * @param sourceContainerContext context that contains child source contexts.
     * @return
     */
    private Map<String, String> findSourcesInScope(ParserRuleContext scopeContext, String sourceContainerContext) {
        //todo: visitor improvement
        /**
         * {@link io.siddhi.langserver.visitor.SiddhiQLLSVisitorImpl}
         * should be improved to visit a certain context specifically if needed.
         */
        List<ParseTree> sourceContexts = new ArrayList<>();
        ParseTreeMapVisitor parseTreeMapVisitor = LSCompletionContext.INSTANCE.getParseTreeMapVisitor();
        Map<String, ParseTree> contextTree = LSCompletionContext.INSTANCE.getParseTreeMap();
        Map<String, String> sourceToAliasMap = new HashMap<>();
        if (SiddhiQLParser.Query_inputContext.class.getName().equalsIgnoreCase(sourceContainerContext)) {
            ParseTree queryInputContext = parseTreeMapVisitor
                    .findOneFromImmediateSuccessors(scopeContext, SiddhiQLParser.Query_inputContext.class);
            if (queryInputContext != null) {
                //when a partially completed window or a filter or a streamFunction is appended to the end of
                // streamName steamName is not considered as a valid one. Therefore having analyzed the ErrorNode it
                // can be found whether a stream name is defined or not.
                if (contextTree.containsKey(LSErrorNode.class.getName())) {
                    LSErrorNode errorNode = ((LSErrorNode) contextTree.get(LSErrorNode.class.getName()));
                    if (errorNode.getErroneousSymbol().contains("#")) {
                        if (!errorNode.getPreviousSymbol().isEmpty()) {
                            sourceToAliasMap.put(errorNode.getPreviousSymbol(), errorNode.getPreviousSymbol());
                        }
                    }
                }
                //first check if the query input contains a join source, if so adding join sources is enough as
                // source context is contained by join source context.
                sourceContexts.addAll(parseTreeMapVisitor.findSuccessorContexts((ParserRuleContext) queryInputContext,
                        SiddhiQLParser.Join_sourceContext.class));
                if (!sourceContexts.isEmpty()) {
                    sourceContexts.addAll(parseTreeMapVisitor
                            .findSuccessorContexts((ParserRuleContext) queryInputContext,
                                    SiddhiQLParser.SourceContext.class));
                }
            }
        } else if (SiddhiQLParser.TargetContext.class.getName().equalsIgnoreCase(sourceContainerContext)) {
            ParseTree targetContext = parseTreeMapVisitor.findOneFromImmediateSuccessors(scopeContext,
                    SiddhiQLParser.TargetContext.class);
            if (targetContext != null) {
                sourceContexts.addAll(parseTreeMapVisitor
                        .findSuccessorContexts((ParserRuleContext) targetContext, SiddhiQLParser.SourceContext.class));
            }
        } else if (SiddhiQLParser.Standard_streamContext.class.getName().equalsIgnoreCase(sourceContainerContext)) {
            ParseTree targetContext = parseTreeMapVisitor.findOneFromImmediateSuccessors(scopeContext,
                    SiddhiQLParser.Stream_idContext.class);
            if (targetContext != null) {
                sourceContexts.addAll(parseTreeMapVisitor
                        .findSuccessorContexts((ParserRuleContext) targetContext, SiddhiQLParser.SourceContext.class));
            }
        }
        for (ParseTree sourceContext : sourceContexts) {
            if (sourceContext instanceof SiddhiQLParser.Join_sourceContext) {
                ParseTree alias = parseTreeMapVisitor.findOneFromImmediateSuccessors((ParserRuleContext) sourceContext,
                        SiddhiQLParser.AliasContext.class);
                ParseTree joinSource = parseTreeMapVisitor
                        .findOneFromImmediateSuccessors((ParserRuleContext) sourceContext,
                                SiddhiQLParser.SourceContext.class);
                if (alias != null) {
                    sourceToAliasMap.put(joinSource.getText(), alias.getText());
                } else {
                    sourceToAliasMap.put(joinSource.getText(), joinSource.getText());
                }

            } else {
                sourceToAliasMap.put(sourceContext.getText(), sourceContext.getText());
            }
        }
        return sourceToAliasMap;
    }

    /**
     * Finds all the definition of sources in siddhi App context
     *
     * @return {@link List<ParseTree> } list of definitions of sources.
     */
    public List<ParseTree> findSourceProviderContexts(List<String> sourceDefinitionContexts) {

        //todo: find the stream name here it self Map<String, ParseTree>
        List<ParseTree> sourceProviderContexts = new ArrayList<>();
        ParseTreeMapVisitor parseTreeMapVisitor = LSCompletionContext.INSTANCE.getParseTreeMapVisitor();
        Map<String, ParseTree> contextTree = LSCompletionContext.INSTANCE.getParseTreeMap();
        ParserRuleContext siddhiAppContext =
                (ParserRuleContext) contextTree.get(SiddhiQLParser.Siddhi_appContext.class.getName());

        if (siddhiAppContext != null) {
            if (sourceDefinitionContexts.contains(SiddhiQLParser.Definition_streamContext.class.getName())) {
                List<ParseTree> streamDefinitionContexts = parseTreeMapVisitor
                        .findSuccessorContexts(siddhiAppContext, SiddhiQLParser.Definition_streamContext.class);
                sourceProviderContexts.addAll(streamDefinitionContexts);
            }
            if (sourceDefinitionContexts.contains(SiddhiQLParser.Definition_windowContext.class.getName())) {
                List<ParseTree> windowDefinitionContexts = parseTreeMapVisitor
                        .findSuccessorContexts(siddhiAppContext, SiddhiQLParser.Definition_windowContext.class);
                sourceProviderContexts.addAll(windowDefinitionContexts);
            }
            if (sourceDefinitionContexts.contains(SiddhiQLParser.Definition_aggregationContext.class.getName())) {
                List<ParseTree> aggregationDefinitionContexts = parseTreeMapVisitor
                        .findSuccessorContexts(siddhiAppContext, SiddhiQLParser.Definition_aggregationContext.class);
                sourceProviderContexts.addAll(aggregationDefinitionContexts);
            }
            if (sourceDefinitionContexts.contains(SiddhiQLParser.Definition_tableContext.class.getName())) {
                List<ParseTree> tableDefinitionContexts = parseTreeMapVisitor
                        .findSuccessorContexts(siddhiAppContext, SiddhiQLParser.Definition_tableContext.class);
                sourceProviderContexts.addAll(tableDefinitionContexts);
            }
        }
        return sourceProviderContexts;
    }

    /**
     * Finds the list of attributes of defined sources in Siddhi App context  and returns a map of source
     * to list of attributes given the possible source names which has been defined in the scope of the
     * sourceProvider Contexts.
     *
     * @param sourceProviderContexts possible sources container contexts that could be referenced from a particular
     *                               scope.
     * @param sourceToAliasMap       Map of sources and aliases that has been defined in a particular scope.
     * @return
     */
    private Map<String, List<String>> findAttributesOfSources(List<ParseTree> sourceProviderContexts,
                                                              Map<String, String> sourceToAliasMap) {
        //todo: return Stream1.attribute1 mapping only when there are two streams.
        Map<String, List<String>> attributeNameMap = new HashMap<>();
        ParseTreeMapVisitor parseTreeMapVisitor = LSCompletionContext.INSTANCE.getParseTreeMapVisitor();
        ArrayList<String> sources = new ArrayList(sourceToAliasMap.keySet());
        for (ParseTree sourceProviderContext : sourceProviderContexts) {
            ParseTree streamIdContext;
            if (sourceProviderContext instanceof SiddhiQLParser.Definition_aggregationContext) {
                streamIdContext =
                        parseTreeMapVisitor.findOneFromSuccessors((ParserRuleContext) sourceProviderContext,
                                SiddhiQLParser.Aggregation_nameContext.class);
            } else {
                streamIdContext =
                        parseTreeMapVisitor.findOneFromSuccessors((ParserRuleContext) sourceProviderContext,
                                SiddhiQLParser.Stream_idContext.class);
            }

            List<String> attributeNames = new ArrayList<>();
            String sourceName = streamIdContext.getText();
            if (sources.contains(sourceName)) {
                String sourceAlias;
                if (sourceToAliasMap.get(sourceName) != null) {
                    sourceAlias = sourceToAliasMap.get(sourceName);
                } else {
                    sourceAlias = sourceName;
                }
                List<ParseTree> attributeNameContexts = new ArrayList<>();
                if (sourceProviderContext instanceof SiddhiQLParser.Definition_aggregationContext) {
                    List<ParseTree> outputAttributeContexts = new ArrayList<>();
                    List<ParseTree> groupByQuerySelectionContexts = parseTreeMapVisitor
                            .findFromImmediateSuccessors((ParserRuleContext) sourceProviderContext,
                                    SiddhiQLParser.Group_by_query_selectionContext.class);
                    for (ParseTree groupByQuerySelectionContext : groupByQuerySelectionContexts) {
                        outputAttributeContexts.addAll(parseTreeMapVisitor
                                .findFromImmediateSuccessors((ParserRuleContext) groupByQuerySelectionContext,
                                        SiddhiQLParser.Output_attributeContext.class));
                    }
                    for (Object outputAttributeContext : outputAttributeContexts) {
                        attributeNameContexts.addAll(parseTreeMapVisitor
                                .findFromImmediateSuccessors((ParserRuleContext) outputAttributeContext,
                                        SiddhiQLParser.Attribute_referenceContext.class));
                        attributeNameContexts.addAll(parseTreeMapVisitor
                                .findFromImmediateSuccessors((ParserRuleContext) outputAttributeContext,
                                        SiddhiQLParser.Attribute_nameContext.class));
                    }
                } else {
                    attributeNameContexts.addAll(parseTreeMapVisitor
                            .findFromImmediateSuccessors((ParserRuleContext) sourceProviderContext,
                                    SiddhiQLParser.Attribute_nameContext.class));
                }
                for (Object attributeNameProvider : attributeNameContexts) {
                    attributeNames.add(((ParserRuleContext) attributeNameProvider).getText());
                }
                attributeNameMap.put(sourceAlias, attributeNames);
            }
            }

        return attributeNameMap;
    }

    /**
     * Provide completions for WindowContext.
     * {@link SiddhiQLParser.WindowContext}.
     */
    public static class WindowContextProvider extends CompletionProvider {

        public WindowContextProvider() {

            this.providerName = SiddhiQLParser.WindowContext.class.getName();
        }

        @Override
        public List<CompletionItem> getCompletions() {

            List<Object[]> suggestions;
            suggestions = SnippetBlockUtil.getWindowProcessorFunctions();
            return generateCompletionList(suggestions);
        }
    }
}

//todo: remove equalsIgnoreCase function.
