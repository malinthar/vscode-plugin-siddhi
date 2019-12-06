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

import io.siddhi.langserver.LSOperationContext;
import io.siddhi.langserver.completion.ParseTreeMapVisitor;
import io.siddhi.langserver.completion.providers.CompletionProvider;
import io.siddhi.langserver.utils.SnippetBlockUtil;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Provide completions for AttributeReferenceContext {@link io.siddhi.query.compiler.SiddhiQLParser.AnnotationContext}.
 */
public class SourceContextProvider extends CompletionProvider {

    public SourceContextProvider() {
        this.providerName = SiddhiQLParser.SourceContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {

        ParseTreeMapVisitor parseTreeMapVisitor = LSOperationContext.INSTANCE.getParseTreeMapVisitor();
        ParseTree parentContext = LSOperationContext.INSTANCE.getParseTreeMap()
                .get(SiddhiQLParser.SourceContext.class.getName())
                .getParent();
        if (parentContext instanceof SiddhiQLParser.Definition_streamContext) {
            List<Object[]> suggestions = new ArrayList<>();
            suggestions.add(SnippetBlockUtil.STREAM_NAME_SNIPPET);
            return generateCompletionList(suggestions);
        } else if (parentContext instanceof SiddhiQLParser.Definition_windowContext) {
            List<Object[]> suggestions = new ArrayList<>();
            suggestions.add(SnippetBlockUtil.WINDOW_NAME_SNIPPET);
            return generateCompletionList(suggestions);

        } else if (parentContext instanceof SiddhiQLParser.Definition_tableContext) {
            List<Object[]> suggestions = new ArrayList<>();
            suggestions.add(SnippetBlockUtil.TABLE_NAME_SNIPPET);
            return generateCompletionList(suggestions);
        } else if (parentContext instanceof SiddhiQLParser.Join_sourceContext) {
            return generateCompletionList(null);
        } else if (parentContext instanceof SiddhiQLParser.TargetContext) {
            List<Object> sourceContexts = new ArrayList<>();
            List<String> sourceNames = new ArrayList<>();
            List<Object> sourceProviderContexts = new ArrayList<>();

            sourceProviderContexts.addAll(parseTreeMapVisitor
                    .findSuccessorContexts((ParserRuleContext) LSOperationContext.INSTANCE.getParseTreeMap().get(
                            SiddhiQLParser.Siddhi_appContext.class.getName()),
                            SiddhiQLParser.Definition_streamContext.class));

            sourceProviderContexts.addAll(parseTreeMapVisitor
                    .findSuccessorContexts((ParserRuleContext) LSOperationContext.INSTANCE.getParseTreeMap().get(
                            SiddhiQLParser.Siddhi_appContext.class.getName()),
                            SiddhiQLParser.Definition_tableContext.class));

            sourceProviderContexts.addAll(parseTreeMapVisitor
                    .findSuccessorContexts((ParserRuleContext) LSOperationContext.INSTANCE.getParseTreeMap().get(
                            SiddhiQLParser.Siddhi_appContext.class.getName()),
                            SiddhiQLParser.Definition_windowContext.class));

            List<ParseTree> definitionAggregationContexts = parseTreeMapVisitor
                    .findSuccessorContexts((ParserRuleContext) LSOperationContext.INSTANCE.getParseTreeMap().get(
                            SiddhiQLParser.Siddhi_appContext.class.getName()),
                            SiddhiQLParser.Definition_aggregationContext.class);
            for (Object sourceProviderContext : sourceProviderContexts) {
                sourceContexts.addAll(parseTreeMapVisitor
                        .findSuccessorContexts((ParserRuleContext) sourceProviderContext,
                                SiddhiQLParser.SourceContext.class));
            }
            for (Object definitionAggregationContext : definitionAggregationContexts) {
                sourceContexts.addAll(parseTreeMapVisitor
                        .findSuccessorContexts((ParserRuleContext) definitionAggregationContext,
                                SiddhiQLParser.Aggregation_nameContext.class));
            }
            sourceContexts.forEach(sourceContext -> {
                sourceNames.add(((ParserRuleContext) sourceContext).getText());
            });
            return generateCompletionList(SnippetBlockUtil.generateSourceReferences(sourceNames));
        } else {
            List<ParseTree> streamIdContexts = new ArrayList<>();
            List<String> sources = new ArrayList<>();
            List<ParseTree> definitionStreamContexts =
                    parseTreeMapVisitor.findSuccessorContexts(
                            (ParserRuleContext) LSOperationContext.INSTANCE.getParseTreeMap().get(
                                    SiddhiQLParser.Siddhi_appContext.class.getName()),
                            SiddhiQLParser.Definition_streamContext.class);

            for (Object definitionStreamContext : definitionStreamContexts) {
                streamIdContexts.addAll(parseTreeMapVisitor
                        .findSuccessorContexts((ParserRuleContext) definitionStreamContext,
                                SiddhiQLParser.Stream_idContext.class));
            }
            for (Object streamIdContext : streamIdContexts) {
                sources.add(((ParserRuleContext) streamIdContext).getText());
            }
            List<Object[]> suggestions = SnippetBlockUtil.generateSourceReferences(sources);
            return generateCompletionList(suggestions);
        }

    }

    public List<String> getDefinedSources() {

        List<Object> sourceProviderContexts = new ArrayList<>();
        List<Object> sources = new ArrayList<>();
        List<String> sourceNames = new ArrayList<>();
        ParseTreeMapVisitor parseTreeMapVisitor = LSOperationContext.INSTANCE.getParseTreeMapVisitor();
        ParserRuleContext siddhiAppContext =
                (ParserRuleContext) LSOperationContext.INSTANCE.getParseTreeMap()
                        .get(SiddhiQLParser.Siddhi_appContext.class.getName());
        sourceProviderContexts.addAll(parseTreeMapVisitor.findSuccessorContexts(siddhiAppContext,
                SiddhiQLParser.Definition_aggregationContext.class));
        sourceProviderContexts.addAll(parseTreeMapVisitor.findSuccessorContexts(siddhiAppContext,
                SiddhiQLParser.Definition_streamContext.class));
        sourceProviderContexts.addAll(parseTreeMapVisitor.findSuccessorContexts(siddhiAppContext,
                SiddhiQLParser.Definition_windowContext.class));
        sourceProviderContexts.addAll(parseTreeMapVisitor.findSuccessorContexts(siddhiAppContext,
                SiddhiQLParser.Definition_aggregationContext.class));
        sourceProviderContexts.forEach(sourceProviderContext -> {
            if (sourceProviderContext instanceof SiddhiQLParser.Definition_aggregationContext) {
                sources.addAll(
                        parseTreeMapVisitor.findSuccessorContexts((ParserRuleContext) sourceProviderContext,
                                SiddhiQLParser.Aggregation_nameContext.class));
            } else {
                sources.addAll(
                        parseTreeMapVisitor.findSuccessorContexts((ParserRuleContext) sourceProviderContext,
                                SiddhiQLParser.SourceContext.class));
            }

        });
        sources.forEach(source -> {
            sourceNames.add(((ParserRuleContext) source).getText());
        });
        return sourceNames;
    }

    public List<CompletionItem> getDefaultCompletions() {

        ParseTreeMapVisitor parseTreeMapVisitor = LSOperationContext.INSTANCE.getParseTreeMapVisitor();
        List<Object> streamIdContexts = new ArrayList<>();
        List<String> sources = new ArrayList<>();
        List<ParseTree> definitionStreamContexts = parseTreeMapVisitor.findSuccessorContexts(
                (ParserRuleContext) LSOperationContext.INSTANCE.getParseTreeMap()
                        .get(SiddhiQLParser.Siddhi_appContext.class.getName()),
                SiddhiQLParser.Definition_streamContext.class);

        for (ParseTree definitionStreamContext : definitionStreamContexts) {
            streamIdContexts.addAll(parseTreeMapVisitor
                    .findSuccessorContexts((ParserRuleContext) definitionStreamContext,
                            SiddhiQLParser.Stream_idContext.class));
        }

        for (Object streamIdContext : streamIdContexts) {
            sources.add(((ParserRuleContext) streamIdContext).getText());
        }
        List<Object[]> suggestions = SnippetBlockUtil.generateSourceReferences(sources);
        return generateCompletionList(suggestions);
    }

}
