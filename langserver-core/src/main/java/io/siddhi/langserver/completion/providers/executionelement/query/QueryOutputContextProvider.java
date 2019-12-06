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

package io.siddhi.langserver.completion.providers.executionelement.query;

import io.siddhi.langserver.LSOperationContext;
import io.siddhi.langserver.beans.LSErrorNode;
import io.siddhi.langserver.completion.ParseTreeMapVisitor;
import io.siddhi.langserver.completion.providers.CompletionProvider;
import io.siddhi.langserver.completion.providers.common.SourceContextProvider;
import io.siddhi.langserver.utils.SnippetBlockUtil;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Provide completions for QueryOutputContext
 * {@link io.siddhi.query.compiler.SiddhiQLParser.AnnotationContext}.
 */
public class QueryOutputContextProvider extends CompletionProvider {

    public QueryOutputContextProvider() {

        this.providerName = SiddhiQLParser.Query_outputContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {

        ParseTreeMapVisitor parseTreeMapVisitor = LSOperationContext.INSTANCE.getParseTreeMapVisitor();
        List<Object[]> suggestions = new ArrayList<>();
        ParserRuleContext queryContext =
                (ParserRuleContext) LSOperationContext.INSTANCE.getParseTreeMap()
                        .get(SiddhiQLParser.QueryContext.class.getName());
        ParserRuleContext queryInputContext =
                (ParserRuleContext) parseTreeMapVisitor.findOneFromImmediateSuccessors(queryContext,
                        SiddhiQLParser.Query_inputContext.class);
        ParserRuleContext querySectionContext =
                (ParserRuleContext) parseTreeMapVisitor.findOneFromImmediateSuccessors(queryContext,
                        SiddhiQLParser.Query_sectionContext.class);
        ParserRuleContext queryOutputContext =
                (ParserRuleContext) parseTreeMapVisitor.findOneFromImmediateSuccessors(queryContext,
                        SiddhiQLParser.Query_outputContext.class);

        if (queryOutputContext != null) {
            if (queryOutputContext.children != null) {
                List<ParseTree> children = queryOutputContext.children;
                if (children.size() == 1 && children.get(0).getText().equalsIgnoreCase("insert")) {
                    List<CompletionItem> completionItems = new ArrayList<>();
                    suggestions.add(SnippetBlockUtil.KEYWORD_INTO);
                    completionItems.addAll(generateCompletionList(suggestions));
                    completionItems.addAll(LSOperationContext.INSTANCE
                            .getProvider(SiddhiQLParser.Output_event_typeContext.class.getName()).getCompletions());
                    return completionItems;
                } else if (children.size() > 1 && children.get(0).getText().equalsIgnoreCase("insert") &&
                        !queryOutputContext.getText().contains("into")) {
                    suggestions.add(SnippetBlockUtil.KEYWORD_INTO);
                } else if (children.size() > 1 && children.get(0).getText().equalsIgnoreCase("delete")) {
                    if (!queryOutputContext.getText().contains("for")) {
                        suggestions.add(SnippetBlockUtil.KEYWORD_FOR);
                    }
                    suggestions.add(SnippetBlockUtil.KEYWORD_ON);
                    return generateCompletionList(suggestions);
                } else if (queryOutputContext.getText().replace(" ", "").toLowerCase().contains(("update or " +
                        "insert into").replace(
                        " ", ""))) {
                    if (!queryOutputContext.getText().contains("set")) {
                        suggestions.add(SnippetBlockUtil.KEYWORD_SET);
                        if (!queryOutputContext.getText().contains("for")) {
                            suggestions.add(SnippetBlockUtil.KEYWORD_FOR);
                        }
                    }
                    suggestions.add(SnippetBlockUtil.KEYWORD_ON);
                    return generateCompletionList(suggestions);
                }
            } else {
                if (LSOperationContext.INSTANCE.getParseTreeMap()
                        .get(LSErrorNode.class.getName()) instanceof LSErrorNode) {
                    LSErrorNode errorNode =
                            (LSErrorNode) LSOperationContext.INSTANCE.getParseTreeMap()
                                    .get(LSErrorNode.class.getName());
                    if (errorNode.getErroneousSymbol().equalsIgnoreCase("update") ||
                            errorNode.getPreviousSymbol().equalsIgnoreCase("update")) {
                        List<String> sourceNames = new ArrayList<>();
                        List<CompletionItem> completionItems = new ArrayList<>();
                        suggestions.add(SnippetBlockUtil.KEYWORD_UPDATE_OR_INSERT_INTO);
                        sourceNames.addAll(((SourceContextProvider) LSOperationContext.INSTANCE
                                .getProvider(SiddhiQLParser.SourceContext.class.getName())).getDefinedSources());
                        completionItems
                                .addAll(generateCompletionList(SnippetBlockUtil.generateSourceReferences(sourceNames)));
                        completionItems.addAll(generateCompletionList(suggestions));
                        return completionItems;
                    }
                }
                if (querySectionContext != null) {
                    List<CompletionItem> completionItems = new ArrayList<>();
                    suggestions.addAll(Arrays.asList(SnippetBlockUtil.KEYWORD_INSERT_INTO,
                            SnippetBlockUtil.KEYWORD_DELETE,
                            SnippetBlockUtil.KEYWORD_UPDATE,
                            SnippetBlockUtil.KEYWORD_UPDATE_OR_INSERT_INTO, SnippetBlockUtil.KEYWORD_INSERT,
                            SnippetBlockUtil.KEYWORD_RETURN, SnippetBlockUtil.KEYWORD_OUTPUT));
                    completionItems.addAll(generateCompletionList(suggestions));
                    completionItems.addAll(LSOperationContext.INSTANCE
                            .getProvider(SiddhiQLParser.Query_sectionContext.class.getName()).getCompletions());
                    return completionItems;
                } else if (queryInputContext != null) {
                    List<ParseTree> children = queryInputContext.children;
                    int childCount = queryInputContext.getChildCount();
                    if (queryInputContext.getChild(childCount - 1) instanceof
                            SiddhiQLParser.Standard_streamContext) {
                        List<Object> sourceNames = new ArrayList<>();
                        sourceNames.addAll(((SourceContextProvider) LSOperationContext.INSTANCE
                                .getProvider(SiddhiQLParser.SourceContext.class.getName())).getDefinedSources());
                        List<ParseTree> queryInputContextSources =
                                LSOperationContext.INSTANCE.getParseTreeMapVisitor()
                                        .findSuccessorContexts((ParserRuleContext) queryInputContext,
                                                SiddhiQLParser.SourceContext.class);
                        AtomicReference<Boolean> status = new AtomicReference<>(true);
                        queryInputContextSources.forEach(queryInputContextSource -> {
                            if (!sourceNames.contains(((ParserRuleContext) queryInputContextSource).getText())) {
                                status.set(false);
                            }
                        });
                        if (!status.get()) {
                            return LSOperationContext.INSTANCE
                                    .getProvider(SiddhiQLParser.Query_inputContext.class.getName()).getCompletions();
                        } else {
                            suggestions.addAll(Arrays
                                    .asList(SnippetBlockUtil.KEYWORD_JOIN, SnippetBlockUtil.CLAUSE_LEFT_OUTER_JOIN,
                                            SnippetBlockUtil.CLAUSE_RIGHT_OUTER_JOIN,
                                            SnippetBlockUtil.CLAUSE_FULL_OUTER_JOIN,
                                            SnippetBlockUtil.KEYWORD_FULL, SnippetBlockUtil.KEYWORD_LEFT,
                                            SnippetBlockUtil.KEYWORD_RIGHT, SnippetBlockUtil.KEYWORD_SELECT,
                                            SnippetBlockUtil.KEYWORD_UNIDIRECTIONAL, SnippetBlockUtil.KEYWORD_OUTER));
                            return generateCompletionList(suggestions);
                        }

                    } else if (queryInputContext
                            .getChild(childCount - 1) instanceof SiddhiQLParser.Join_streamContext) {
                        suggestions.add(SnippetBlockUtil.KEYWORD_SELECT);
                        return generateCompletionList(suggestions);
                    }
                }
            }
        }

        return generateCompletionList(suggestions);
    }
}

