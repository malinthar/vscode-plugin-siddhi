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

package io.siddhi.langserver.completion.providers.siddhiapp;

import io.siddhi.langserver.completion.providers.CompletionProvider;
import io.siddhi.langserver.utils.SnippetBlockUtil;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.Arrays;
import java.util.List;

/**
 * Provide completions for SiddhiAppContext {@link io.siddhi.query.compiler.SiddhiQLParser.AnnotationContext}.
 */
public class SiddhiAppContextProvider extends CompletionProvider {

    public SiddhiAppContextProvider() {
        this.providerName = SiddhiQLParser.Siddhi_appContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {
        List<Object[]> suggestions = Arrays.asList(
                SnippetBlockUtil.APP_DESCRIPTION_ANNOTATION_DEFINITION,
                SnippetBlockUtil.APP_NAME_ANNOTATION_DEFINITION,
                SnippetBlockUtil.STREAM_DEFINITION,
                SnippetBlockUtil.PARTITION_DEFINITION,
                SnippetBlockUtil.TABLE_DEFINITION,
                SnippetBlockUtil.TRIGGER_DEFINITION,
                SnippetBlockUtil.WINDOW_DEFINITION,
                SnippetBlockUtil.AGGREGATION_DEFINITION,
                SnippetBlockUtil.ANNOTATION_ASYNC_DEFINITION,
                SnippetBlockUtil.ANNOTATION_INDEX_DEFINITION,
                SnippetBlockUtil.ANNOTATION_PRIMARY_KEY_DEFINITION,
                SnippetBlockUtil.ANNOTATION_QUERY_INFO_DEFINITION,
                SnippetBlockUtil.APP_STATISTICS_ANNOTATION_DEFINITION,
                SnippetBlockUtil.QUERY_DEFINITION,
                SnippetBlockUtil.QUERY_FILTER,
                SnippetBlockUtil.QUERY_JOIN,
                SnippetBlockUtil.QUERY_PATTERN,
                SnippetBlockUtil.QUERY_TABLE_JOIN,
                SnippetBlockUtil.QUERY_WINDOW,
                SnippetBlockUtil.QUERY_WINDOW_FILTER,
                SnippetBlockUtil.SINK_DEFINITION,
                SnippetBlockUtil.SOURCE_DEFINITION,
                SnippetBlockUtil.FUNCTION_DEFINITION,
                SnippetBlockUtil.KEYWORD_FROM,
                SnippetBlockUtil.KEYWORD_DEFINE,
                SnippetBlockUtil.KEYWORD_STREAM,
                SnippetBlockUtil.KEYWORD_AGGREGATION,
                SnippetBlockUtil.KEYWORD_FUNCTION,
                SnippetBlockUtil.KEYWORD_PARTITION,
                SnippetBlockUtil.KEYWORD_TABLE,
                SnippetBlockUtil.KEYWORD_WINDOW,
                SnippetBlockUtil.KEYWORD_TRIGGER);
        return generateCompletionList(suggestions);
    }

}
