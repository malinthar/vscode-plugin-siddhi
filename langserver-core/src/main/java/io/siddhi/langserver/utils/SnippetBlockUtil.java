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

package io.siddhi.langserver.utils;

import io.siddhi.langserver.LSCompletionContext;
import io.siddhi.langserver.utils.metadata.AttributeMetaData;
import io.siddhi.langserver.utils.metadata.ParameterMetaData;
import io.siddhi.langserver.utils.metadata.ProcessorMetaData;
import org.eclipse.lsp4j.CompletionItemKind;
import org.eclipse.lsp4j.InsertTextFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * SnippetBlock class provides pre defined  snippet block objects to be generated to completion items.
 * Each completion snippet consists of an array of
 * {insertText,label,completionItemKind,details,filterText,insertTextFormat}.
 */
//todo: improve the implementation to have a yaml file to contain all the snippets.
public class SnippetBlockUtil {

    public static final Object[] APP_NAME_ANNOTATION_DEFINITION =
            new Object[]{"@App:description(\"${1:App_Name}\")", "annotate-appName", CompletionItemKind.Snippet,
                    "annotate-AppName\n@App:Name(\"${1:App_Name}\")", "@app:name",InsertTextFormat.Snippet};
    public static final Object[] APP_DESCRIPTION_ANNOTATION_DEFINITION =
            new Object[]{"@App:description(\"${1:App_Description}\")", "annotate-appDescription",
        CompletionItemKind.Snippet, "annotate-AppDescription\n@App:description(\"${1:App_Description}\")", "@app:description",
                    InsertTextFormat.Snippet};
    public static final Object[] APP_STATISTICS_ANNOTATION_DEFINITION =
            new Object[]{"@App:statistics(\"${1:Is_Enabled}\")", "annotate-appStatistics", CompletionItemKind.Snippet,
                    "@App:statistics(\"Is_Enabled\")", "@app:statistics", InsertTextFormat.Snippet};
    public static final List<Object[]> APP_ANNOTATION_DEFINITION = Arrays.asList(APP_NAME_ANNOTATION_DEFINITION,
            APP_DESCRIPTION_ANNOTATION_DEFINITION);
    public static final Object[] APP_ANNOTATION_ELEMENT_NAME_DEFINITION =
            new Object[]{"\"${1:plan name}\"", "plan name", CompletionItemKind.Snippet, "app-annotation-element",
                    "@app:name",
                    InsertTextFormat.Snippet};
    public static final Object[] APP_ANNOTATION_ELEMENT_DESCRIPTION_DEFINITION =
            new Object[]{"\"${1:plan description}\"", "plan description", CompletionItemKind.Snippet, "app-annotation" +
        "-element",
                    "@app:description", InsertTextFormat.Snippet};
    public static final Object[] APP_ANNOTATION_ELEMENT_STATISTICS_DEFINITION =
            new Object[]{"\"${1:statistics}\"", "statistics", CompletionItemKind.Snippet, "app-annotation-element",
                    "@app:statistics", InsertTextFormat.Snippet};

    public static final Object[] STREAM_DEFINITION =
            new Object[]{"define stream ${1:stream_name} (${2:attr1} ${3:Type1}, ${4:attN} ${5:TypeN});", "define" +
                    "-stream",
                    CompletionItemKind.Snippet,
                    "define stream $stream_name ($attrname $Type1, $nextattrname $TypeN)", "stream",
                    InsertTextFormat.Snippet};
    public static final Object[] QUERY_DEFINITION =
            new Object[]{"from ${1:stream_name}\nselect ${2:attribute1} , ${3:attribute2}\ninsert into " +
                    "${4:output_stream}",
                    CompletionItemKind.Snippet, "from ${1:stream_name}\nselect ${2:attribute1} , ${3:attribute2}\n" +
                    "insert into ${4:output_stream}", "from", InsertTextFormat.Snippet};
    public static final Object[] FUNCTION_DEFINITION =
            new Object[]{"define function ${1:function_name}[${2:lang_name}] return ${3:return_type} { \n" +
                    "\t${4:function_body} \n};", "define-function", CompletionItemKind.Snippet,
                    "define function ${1:function_name}[${2:lang_name}] return ${3:return_type} { \n" +
                            "\t${4:function_body} \n};", "define function", InsertTextFormat.Snippet};
    public static final Object[] TABLE_DEFINITION =
            new Object[]{"define table ${1:table_name} (${2:attr1} ${3:Type1}, ${4:attN} ${5:TypeN});", "define-table",
                    CompletionItemKind.Snippet,
                    "define table ${1:table_name} (${2:attr1} ${3:Type1}, ${4:attN} ${5:TypeN});", "define table",
                    InsertTextFormat.Snippet};
    public static final Object[] AGGREGATION_DEFINITION =
            new Object[]{"@Purge(enable = '${1:enable_purge}', interval= '${2:purge_interval}',\t\t@retentionPeriod(.." +
        ".)" +
        ")" +
                    "\ndefine aggregation ${3:aggregator_name}\nfrom ${4:input_stream}" +
                    "\nselect ${5:attribute1}, ${6:aggregate_function}(${7:attribute2}) as ${8:attribute3}," +
                    " ${9:aggregate_function}(${10:attribute4}) as ${11:attribute5}\n" +
                    "\tgroup by ${12:attribute6}\n" +
                    "\taggregate by ${13:timestamp_attribute} every ${14:time_periods};",
                    CompletionItemKind.Snippet,
                    "define aggregation ${1:aggregator_name}\n" + "from ${2:input_stream}\n" +
                            "select ${3:attribute1}, ${4:aggregate_function}(${5:attribute2}) as ${6:attribute3}," +
                            "${7:aggregate_function}(${8:attribute4}) as ${9:attribute5}\n" +
                            "\tgroup by ${10:attribute6}\n" +
                            "\taggregate by ${11:timestamp_attribute} every ${12:time_periods};",
                    "define aggregation", InsertTextFormat.Snippet};
    public static final Object[] PARTITION_DEFINITION = new Object[]{
            "partition with (${1:attribute_name} of ${2:stream_name})\n" +
                    "begin\n" +
                    "\t${3:queries}\n" +
                    "end;", CompletionItemKind.Snippet, "partition with (${1:attribute_name} of ${2:stream_name})\n" +
            "begin\n" +
            "\t${3:queries}\n" +
            "end;", "define partition", InsertTextFormat.Snippet};
    public static final Object[] TRIGGER_DEFINITION = new Object[]{
            "define trigger ${1:trigger_name} at ${2:time};", "define-trigger",
            CompletionItemKind.Snippet, "define trigger ${1:trigger_name} at ${2:time};", "define trigger",
            InsertTextFormat.Snippet};
    public static final Object[] WINDOW_DEFINITION = new Object[]{
            "define window ${1:window_name} (${2:attr1} ${3:Type1}," +
                    " ${4:attN} ${5:TypeN}) ${6:window_type} ${7:output ${8:event_type} events};",
            CompletionItemKind.Snippet,
            "define-Window\ndefine window ${1:window_name} (${2:attr1} ${3:Type1}," +
                    " ${4:attN} ${5:TypeN}) ${6:window_type} ${7:output" +
                    " ${8:event_type} events};", "define window", InsertTextFormat.Snippet};
    public static final Object[] QUERY_TABLE_JOIN = new Object[]{
            "define table ${1:table_name} (${2:attr1} ${3:Type1}, ${4:attN} ${5:TypeN});", "query-tableJoin",
            CompletionItemKind.Snippet,
            "query-tableJoin\n" +
                    "from ${1:stream_name} as ${2:reference} join ${3:table_name} as ${4:reference}\n" +
                    "\ton ${5:join_condition}\n" +
                    "select ${6:attribute1}, ${7:attribute2}\n" +
                    "limit ${8:limit_value}\n" +
                    "offset ${9:offset_value}\n" +
                    "insert into ${10:output_stream}", "from", InsertTextFormat.Snippet};
    public static final Object[] QUERY_PATTERN = new Object[]{
            "from every ${1:stream_reference}=${2:stream_name}[${3:filter_condition}] -> \n" +
                    "\tevery ${4:stream_reference2}=${5:stream_name2}[${6:filter_condition2}]\n" +
                    "\twithin ${7: time_gap}\n" +
                    "select ${8:stream_reference}.${9:attribute1}, ${10:stream_reference}.${11:attribute1}\n" +
                    "insert into ${12:output_stream}", "query-pattern", CompletionItemKind.Snippet, "\n" +
            "query-Pattern\n" +
            "from every ${1:stream_reference}=${2:stream_name}[${3:filter_condition}] -> \n" +
            "\tevery ${4:stream_reference2}=${5:stream_name2}[${6:filter_condition2}]\n" +
            "\twithin ${7: time_gap}\n" +
            "select ${8:stream_reference}.${9:attribute1}, ${10:stream_reference}.${11:attribute1}\n" +
            "insert into ${12:output_stream}", "from", InsertTextFormat.Snippet};
    public static final Object[] QUERY_JOIN = new Object[]{
            "from ${1:stream_name}[${2:filter_condition}]#window.${3:window_name}(${4:args}) as ${5:reference}\n" +
                    "\tjoin ${6:stream_name}[${7:filter_condition}]#window.${8:window_name}(${9:args}) as ${10:reference}\n" +
                    "\ton ${11:join_condition}\n" +
                    "\twithin ${12: time_gap}\n" +
                    "select ${13:attribute1}, ${14:attribute2}\n" +
                    "insert into ${15:output_stream}", "query-join", CompletionItemKind.Snippet,
            "query-Join\n" +
                    "from ${1:stream_name}[${2:filter_condition}]#window.${3:window_name}(${4:args}) as ${5:reference}\n" +
                    "\tjoin ${6:stream_name}[${7:filter_condition}]#window.${8:window_name}(${9:args}) as ${10:reference}\n" +
                    "\ton ${11:join_condition}\n" +
                    "\twithin ${12: time_gap}\n" +
                    "select ${13:attribute1}, ${14:attribute2}\n" +
                    "insert into ${15:output_stream}", "from", InsertTextFormat.Snippet};
    public static final Object[] QUERY_WINDOW_FILTER = new Object[]{
            "from stream_name[filter_condition]#window.namespace:window_name(args)\n" +
                    "select attribute1 , attribute2\n" +
                    "insert into output_stream;", "query-windowFilter", CompletionItemKind.Snippet,
            "query-WindowFilter\n" +
                    "from ${1:stream_name}[${2:filter_condition}]#window.${3:namespace}:${4:window_name}(${5:args})\n" +
                    "select ${6:attribute1} , ${7:attribute2}\n" +
                    "insert into ${8:output_stream}", "from"};
    public static final Object[] QUERY_WINDOW = new Object[]{
            "from ${1:stream_name}#window.${2:namespace}:${3:window_name}(${4:args})\n" +
                    "select ${5:attribute1}, ${6:attribute2}\n" +
                    "insert into ${7:output_stream}", "query-window", CompletionItemKind.Snippet,
            "query-Window\n" +
                    "from ${1:stream_name}#window.${2:namespace}:${3:window_name}(${4:args})\n" +
                    "select ${5:attribute1};, ${6:attribute2}\n" +
                    "insert into ${7:output_stream}", "from", InsertTextFormat.Snippet};
    public static final Object[] QUERY_FILTER = new Object[]{
            "from ${1:stream_name}[${2:filter_condition}]\n" +
                    "select ${3:attribute1}, ${4:attribute2}\n" +
                    "insert into ${5:output_stream}", CompletionItemKind.Snippet,
            "query-Filter\n" +
                    "from ${1:stream_name}[${2:filter_condition}]\n" +
                    "select ${3:attribute1}, ${4:attribute2}\n" +
                    "insert into ${5:output_stream}", "from", InsertTextFormat.Snippet};
    public static final Object[] SINK_DEFINITION = new Object[]{
            "@sink(type='${1:sink_type}', ${2:option_key}='${3:option_value}', " +
                    "${4:dynamic_option_key}='{{${5:dynamic_option_value}}}',\n" +
                    "\t@map(type='${6:map_type}', ${7:option_key}='${8:option_value}'," +
                    " ${9:dynamic_option_key}='{{${10:dynamic_option_value}}}',\n" +
                    "\t\t@payload( '${11:payload_mapping}')\n" + "\t)\n" + ")\n" +
                    "define stream ${12:stream_name} (${13:attribute1} ${14:Type1}, ${15:attributeN} ${16:TypeN});",
            "define-sink",
            CompletionItemKind.Snippet,
            "define-Sink\n" +
                    "@sink(type='${1:sink_type}', ${2:option_key}='${3:option_value}', ${4:dynamic_option_key}='{{${5:dynamic_option_value}}}',\n" +
                    "\t@map(type='${6:map_type}', ${7:option_key}='${8:option_value}', ${9:dynamic_option_key}='{{${10:dynamic_option_value}}}',\n" +
                    "\t\t@payload( '${11:payload_mapping}')\n" +
                    "\t)\n" +
                    ")\n" +
                    "define stream ${12:stream_name} (${13:attribute1} ${14:Type1}, ${15:attributeN} ${16:TypeN});",
            "@sink", InsertTextFormat.Snippet};
    public static final Object[] SOURCE_DEFINITION = new Object[]{
            "@source(type='${1:source_type}', ${2:option_key}='${3:option_value}',\n" +
                    "\t@map(type='${4:map_type}', ${5:option_key}='${6:option_value}',\n" +
                    "\t\t@attributes('${7:attribute_mapping_1}', '${8:attribute_mapping_N}')\n" + "\t)\n" + ")\n" +
                    "define stream ${9:stream_name} (${10:attribute1} ${11:Type1}, ${12:attributeN} ${13:TypeN});",
            "define-source",
            CompletionItemKind.Snippet,
            "define-Source\n" +
                    "@source(type='${1:source_type}', ${2:option_key}='${3:option_value}',\n" +
                    "\t@map(type='${4:map_type}', ${5:option_key}='${6:option_value}',\n" +
                    "\t\t@attributes('${7:attribute_mapping_1}', '${8:attribute_mapping_N}')\n" + "\t)\n" + ")\n" +
                    "define stream ${9:stream_name} (${10:attribute1} ${11:Type1}, ${12:attributeN} ${13:TypeN});",
            "@source"
            , InsertTextFormat.Snippet};
    public static final Object[] ANNOTATION_ASYNC_DEFINITION = new Object[]{
            "@async(buffer.size=\"${1:64}\", workers='${2:2}', batch.size.max='${3:10}')", "annotate-Async",
            CompletionItemKind.Snippet,
            "annotate-Async\n" + "@async(buffer.size=\"${1:64}\", workers='${2:2}', batch.size.max='${3:10}')",
            "@async", InsertTextFormat.Snippet};
    public static final Object[] ANNOTATION_QUERY_INFO_DEFINITION = new Object[]{
            "@info(name = \"${1:Query_Name}\")", "annotate" + "-queryInfo", CompletionItemKind.Snippet,
            "@info(name = \"${1:Query_Name}\")", "@info", InsertTextFormat.Snippet};
    public static final Object[] ANNOTATION_PRIMARY_KEY_DEFINITION = new Object[]{
            "@primaryKey('${1:attribute_name}')", "annotate-primaryKey", CompletionItemKind.Snippet,
            "annotate-PrimaryKey\n" + "@primaryKey('${1:attribute_name}')", "@primaryKey", InsertTextFormat.Snippet};
    public static final Object[] ANNOTATION_INDEX_DEFINITION = new Object[]{
            "@index('${1:attribute_name}')", "annotate-index",
            CompletionItemKind.Snippet, "annotate-Index\n" +
            "@index('${1:attribute_name}')", "@index", InsertTextFormat.Snippet};

    public static final Object[] ATTRIBUTE_NAME_AND_TYPE_SNIPPET = new Object[]{"${1:attr1} ${2:Type1}", "${1:attr1} " +
        "${2:Type1}",
            CompletionItemKind.Snippet, "attribute", InsertTextFormat.Snippet};
    public static final Object[] ALIAS_SNIPPET =
            {"${1:alias}", "alias", CompletionItemKind.Snippet, "attribute-name", "alias",InsertTextFormat.Snippet};
    public static final Object[] ATTRIBUTE_LIST_SNIPPET = new Object[]{"(${1:attr1} ${2:Type1}, ${3:attrN} ${4:TypeN})",
        "(attr1 " +
            "Type1, attN TypeN)",
            CompletionItemKind.Snippet, "attribute list", "(attr1 Type1, attN TypeN)",InsertTextFormat.Snippet};
    public static final Object[] STREAM_NAME_SNIPPET = new Object[]{"${1:streamName} ", "streamName",
        CompletionItemKind.Snippet
            , "stream name:source", "streamName",InsertTextFormat.Snippet};
    public static final Object[] TRIGGER_NAME_SNIPPET = new Object[]{"${1:triggerName}", "triggerName",
        CompletionItemKind.Snippet
            , "trigger name:source", "triggerName",InsertTextFormat.Snippet};
    public static final Object[] WINDOW_NAME_SNIPPET = new Object[]{"${1:windowName}", "windowName",
            CompletionItemKind.Snippet
            , "window name:source", "windowName",InsertTextFormat.Snippet};
    public static final Object[] TABLE_NAME_SNIPPET = new Object[]{"${1:tableName}", "tableName",
            CompletionItemKind.Snippet
            , "table name:source", "tableName",InsertTextFormat.Snippet};
    public static final Object[] FUNCTION_NAME_SNIPPET = new Object[]{"${1:functionName}", "functionName",
            CompletionItemKind.Snippet, "function name:id", "functionName",InsertTextFormat.Snippet};
    public static final Object[] LANGUAGE_NAME_SNIPPET = new Object[]{"[${1:languageName}]", "languageName",
        CompletionItemKind.Snippet
            , "language name", "languageName",InsertTextFormat.Snippet};
    public static final Object[] FUNCTION_BODY_SNIPPET = new Object[]{"{\n\t\t${1:body}\n};", "function-body",
            CompletionItemKind.Snippet, "block", "{function-body}",InsertTextFormat.Snippet};
    public static final Object[] PARTITION_BLOCK_SNIPPET = new Object[]{
            "begin\n" + "\t${3:queries}\n" + "end;", "partition-block",
            CompletionItemKind.Snippet
            , "partition-block: " +
            "\nbegin\n" + "  queries\n" + "end;", "begin",InsertTextFormat.Snippet};

    public static final Object[] KEYWORD_DEFINE =
            new Object[]{"define", "define", CompletionItemKind.Keyword, "keyword", "define",
                    InsertTextFormat.PlainText};
    public static final Object[] KEYWORD_STREAM =
            new Object[]{"stream", "stream", CompletionItemKind.Keyword, "keyword", "stream",
                    InsertTextFormat.PlainText};
    public static final Object[] KEYWORD_AGGREGATION =
            new Object[]{"aggregation", "aggregation", CompletionItemKind.Keyword, "keyword", "aggregation",
                    InsertTextFormat.PlainText};
    public static final Object[] KEYWORD_TRIGGER =
            new Object[]{"trigger", "trigger", CompletionItemKind.Keyword, "keyword", "trigger",
                    InsertTextFormat.PlainText};
    public static final Object[] KEYWORD_FUNCTION =
            new Object[]{"function", "function", CompletionItemKind.Keyword, "keyword", "function"};
    public static final Object[] KEYWORD_WINDOW =
            new Object[]{"window", "window", CompletionItemKind.Keyword, "keyword", "window",
                    InsertTextFormat.PlainText};
    public static final Object[] KEYWORD_PARTITION =
            new Object[]{"partition with", "partition", CompletionItemKind.Keyword, "keyword", "partition",
                    InsertTextFormat.PlainText};
    public static final Object[] KEYWORD_TABLE =
            new Object[]{"table", "table", CompletionItemKind.Keyword, "keyword", "table", InsertTextFormat.PlainText};
    public static final Object[] KEYWORD_FROM =
            new Object[]{"from", "from", CompletionItemKind.Keyword, "keyword", "from", InsertTextFormat.PlainText};
    public static final Object[] KEYWORD_INSERT_INTO =
            new Object[]{"insert into", "insert into", CompletionItemKind.Keyword, "keyword", "insert into",
                    InsertTextFormat.PlainText};
    public static final Object[] KEYWORD_UPDATE_OR_INSERT_INTO =
            new Object[]{"update or insert into", "update or insert into", CompletionItemKind.Keyword, "keyword",
                    "update or insert into", InsertTextFormat.PlainText};
    public static final Object[] KEYWORD_INTO =
            new Object[]{"into", "into", CompletionItemKind.Keyword, "keyword", "into", InsertTextFormat.PlainText};
    public static final Object[] KEYWORD_FOR =
            new Object[]{"for", "for", CompletionItemKind.Keyword, "keyword", "for", InsertTextFormat.PlainText};
    public static final Object[] KEYWORD_ON =
            new Object[]{"on", "on", CompletionItemKind.Keyword, "keyword", "on", InsertTextFormat.PlainText};
    public static final Object[] KEYWORD_SET = new Object[]{"set", "set", CompletionItemKind.Keyword, "keyword", "set",
            InsertTextFormat.PlainText};
    public static final Object[] KEYWORD_ALL_EVENTS = new Object[]{"all events", "all events",
            CompletionItemKind.Keyword,
        "keyword",
            "all events",InsertTextFormat.PlainText};
    public static final Object[] KEYWORD_EXPIRED_EVENTS = new Object[]{"expired events", "expired events",
            CompletionItemKind.Keyword, "keyword", "expired events",InsertTextFormat.PlainText};
    public static final Object[] KEYWORD_CURRENT_EVENTS = new Object[]{"current events", "current events",
        CompletionItemKind.Keyword, "keyword", "current events",InsertTextFormat.PlainText};
    public static final Object[] KEYWORD_CURRENT = new Object[]{"current", "current", CompletionItemKind.Keyword,
            "keyword",
        "current",InsertTextFormat.PlainText};
    public static final Object[] KEYWORD_EVENTS = new Object[]{"events", "events", CompletionItemKind.Keyword,
            "keyword",
        "events",InsertTextFormat.PlainText};
    public static final Object[] TRIPLE_DOT = new Object[]{"...", "...", CompletionItemKind.Constant, "triple-dot", "." +
            ".."};

    public static final Object[] KEYWORD_SELECT = new Object[]{"select", "select", CompletionItemKind.Keyword,
            "keyword",
        "select",InsertTextFormat.PlainText};
    public static final Object[] KEYWORD_INSERT = new Object[]{"insert", "insert", CompletionItemKind.Keyword,
            "keyword",
        "insert",InsertTextFormat.PlainText};
    public static final Object[] KEYWORD_DELETE = new Object[]{"delete", "delete", CompletionItemKind.Keyword,
            "keyword",
        "delete",InsertTextFormat.PlainText};
    public static final Object[] KEYWORD_UPDATE = new Object[]{"update", "update", CompletionItemKind.Keyword,
            "keyword",
        "update",InsertTextFormat.PlainText};
    public static final Object[] KEYWORD_RETURN = new Object[]{"return", "return", CompletionItemKind.Keyword,
            "keyword",
        "return",InsertTextFormat.PlainText};
    public static final Object[] KEYWORD_OUTPUT = new Object[]{"output", "output", CompletionItemKind.Keyword,
            "keyword",
        "output",InsertTextFormat.PlainText};
    public static final Object[] KEYWORD_EVERY = new Object[]{"every", "every", CompletionItemKind.Keyword, "keyword",
            "every",
        InsertTextFormat.PlainText};
    public static final Object[] KEYWORD_INT = new Object[]{"int", "int", CompletionItemKind.Keyword, "attribute " +
            "type:int",
        "int",InsertTextFormat.PlainText};
    public static final Object[] KEYWORD_LONG = new Object[]{"long", "long", CompletionItemKind.Keyword, "attribute " +
        "type:long",
        "long",InsertTextFormat.PlainText};
    public static final Object[] KEYWORD_DOUBLE = new Object[]{"double", "double", CompletionItemKind.Keyword,
            "attribute " +
        "type:double", "double",InsertTextFormat.PlainText};
    public static final Object[] KEYWORD_FLOAT = new Object[]{"float", "float", CompletionItemKind.Keyword, "attribute" +
            " " +
        "type:float", "float",InsertTextFormat.PlainText};
    public static final Object[] KEYWORD_STRING = new Object[]{"string", "string", CompletionItemKind.Keyword,
            "attribute " +
        "type:string", "string",InsertTextFormat.PlainText};
    public static final Object[] KEYWORD_BOOL = new Object[]{"bool", "bool", CompletionItemKind.Keyword, "attribute " +
        "type:bool",
        "bool",InsertTextFormat.PlainText};
    public static final Object[] KEYWORD_OBJECT = new Object[]{"object", "object", CompletionItemKind.Keyword,
            "attribute " +
        "type:object", "object",InsertTextFormat.PlainText};
    public static final Object[] KEYWORD_HAVING = new Object[]{"having", "having", CompletionItemKind.Keyword,
            "keyword",
        "having",InsertTextFormat.PlainText};
    public static final Object[] KEYWORD_ORDER_BY = new Object[]{"order by", "order by", CompletionItemKind.Keyword,
        "keyword",
        "order",InsertTextFormat.PlainText};
    public static final Object[] KEYWORD_LIMIT = new Object[]{"limit", "limit", CompletionItemKind.Keyword, "keyword",
            "limit",
        InsertTextFormat.PlainText};
    public static final Object[] KEYWORD_OFFSET = new Object[]{"offset", "offset", CompletionItemKind.Keyword,
            "keyword",
        "offset",InsertTextFormat.PlainText};
    public static final Object[] KEYWORD_GROUP_BY = new Object[]{"group by", "group by", CompletionItemKind.Keyword,
        "keyword",
        "group",InsertTextFormat.PlainText};
    public static final Object[] KEYWORD_AGGREGATE_BY = new Object[]{"aggregate by", "aggregate by",
        CompletionItemKind.Keyword,
        "keyword", "aggregate",InsertTextFormat.PlainText};
    public static final Object[] KEYWORD_AS = new Object[]{"as", "as", CompletionItemKind.Keyword, "keyword", "as",
        InsertTextFormat.PlainText};
    public static final Object[] KEYWORD_AT = new Object[]{"at", "at", CompletionItemKind.Keyword, "keyword", "at",
        InsertTextFormat.PlainText};

    public static final List<Object[]> QUERY_SECTION_KEYWORDS = Arrays.asList(
            KEYWORD_GROUP_BY, KEYWORD_OFFSET, KEYWORD_HAVING, KEYWORD_ORDER_BY, KEYWORD_LIMIT, KEYWORD_INSERT,
            KEYWORD_INSERT_INTO, KEYWORD_DELETE, KEYWORD_UPDATE_OR_INSERT_INTO, KEYWORD_RETURN, KEYWORD_UPDATE);
    public static final List<Object[]> attributeTypes = Arrays.asList(
            KEYWORD_STRING,
            KEYWORD_INT,
            KEYWORD_FLOAT,
            KEYWORD_DOUBLE,
            KEYWORD_LONG,
            KEYWORD_BOOL,
            KEYWORD_OBJECT);

    public static final Object[] KEYWORD_JOIN = new Object[]{"join", "join", CompletionItemKind.Keyword, "keyword",
            "join",
        InsertTextFormat.PlainText};
    public static final Object[] KEYWORD_LEFT = new Object[]{"left", "left", CompletionItemKind.Keyword, "keyword",
            "left",
        InsertTextFormat.PlainText};
    public static final Object[] KEYWORD_RIGHT = new Object[]{"right", "right", CompletionItemKind.Keyword, "keyword",
        "right",
        InsertTextFormat.PlainText};
    public static final Object[] KEYWORD_OUTER = new Object[]{"outer", "outer", CompletionItemKind.Keyword, "keyword",
        "outer",
        InsertTextFormat.PlainText};
    public static final Object[] KEYWORD_FULL = new Object[]{"full", "full", CompletionItemKind.Keyword, "keyword",
            "full",
        InsertTextFormat.PlainText};
    public static final Object[] KEYWORD_UNIDIRECTIONAL = new Object[]{"unidirectional", "unidirectional",
        CompletionItemKind.Keyword, "keyword", "unidirectional",InsertTextFormat.PlainText};
    public static final Object[] CLAUSE_LEFT_OUTER_JOIN = new Object[]{"left outer join", "left outer join",
        CompletionItemKind.Keyword, "keyword", "left outer join",InsertTextFormat.PlainText};
    public static final Object[] CLAUSE_RIGHT_OUTER_JOIN = new Object[]{"right outer join", "right outer join",
        CompletionItemKind.Keyword, "keyword", "right outer join",InsertTextFormat.PlainText};
    public static final Object[] CLAUSE_FULL_OUTER_JOIN = new Object[]{"full outer join", "full outer join",
        CompletionItemKind.Keyword, "keyword", "full outer join",InsertTextFormat.PlainText};
    public static final Object[] KEYWORD_ASC = new Object[]{"asc", "asc", CompletionItemKind.Keyword, "keyword", "asc",
        InsertTextFormat.PlainText};
    public static final Object[] KEYWORD_DESC = new Object[]{"desc", "desc", CompletionItemKind.Keyword, "keyword",
            "desc",
            InsertTextFormat.PlainText};

    public static final List<Object[]> ORDER_KEYWORDS = Arrays.asList(KEYWORD_ASC, KEYWORD_DESC);

    public static final Object[] KEYWORD_BEGIN = new Object[]{"begin", "begin", CompletionItemKind.Keyword, "keyword",
        "begin",
        InsertTextFormat.PlainText};
    public static final Object[] KEYWORD_END = new Object[]{"end", "end", CompletionItemKind.Keyword, "keyword", "end",
        InsertTextFormat.PlainText};
    public static final Object[] MILLISECONDS = new Object[]{"milliseconds", "milliseconds",
            CompletionItemKind.Constant,
        "constant", "milliseconds",InsertTextFormat.PlainText};
    public static final Object[] SECONDS = new Object[]{"seconds", "seconds", CompletionItemKind.Constant, "constant",
        "seconds"
        ,InsertTextFormat.PlainText};
    public static final Object[] MINUTES = new Object[]{"minutes", "minutes", CompletionItemKind.Constant, "constant",
        "minutes"
        ,InsertTextFormat.PlainText};
    public static final Object[] HOURS = new Object[]{"hours", "hours", CompletionItemKind.Constant, "constant", "hours",
        InsertTextFormat.PlainText};
    public static final Object[] DAYS = new Object[]{"days", "days", CompletionItemKind.Constant, "constant", "days",
        InsertTextFormat.PlainText};
    public static final Object[] MONTHS = new Object[]{"months", "months", CompletionItemKind.Constant, "constant",
        "months",
        InsertTextFormat.PlainText};
    public static final Object[] YEARS = new Object[]{"years", "years", CompletionItemKind.Constant, "constant", "years",
        InsertTextFormat.PlainText};

    public static List<Object[]> AGGREGATION_TIME_DURATION = Arrays.asList(SECONDS, MINUTES, HOURS, DAYS, MONTHS, YEARS);
    public static List<Object[]> TIME_VALUE = Arrays.asList(SECONDS, MINUTES, HOURS, DAYS, MONTHS, YEARS, MILLISECONDS);

    public static final Object[] CONSTANT_TRUE = new Object[]{"true", "true", CompletionItemKind.Constant, "constant",
            "true",
        InsertTextFormat.PlainText};
    public static final Object[] CONSTANT_FALSE = new Object[]{"false", "false", CompletionItemKind.Constant,
            "constant",
        "false",InsertTextFormat.PlainText};

    public static List<Object[]> BOOLEAN_CONSTANTS = Arrays.asList(CONSTANT_TRUE, CONSTANT_FALSE);

    private static List<Object[]> functionSuggestions = new ArrayList<>();
    private static List<Object[]> windowSuggestions = new ArrayList<>();
    private static List<Object[]> aggregatorFunctionSuggestions = new ArrayList<>();
    private static List<Object[]> streamFunctionSuggestions = new ArrayList<>();
    private static List<Object[]> sinkSuggestions = new ArrayList<>();
    private static List<Object[]> storeSuggestions = new ArrayList<>();
    private static List<Object[]> sourceSuggestions = new ArrayList<>();

    public static List<Object[]> getFunctions() {
        if (functionSuggestions.isEmpty()) {
            List<ProcessorMetaData> functions = LSCompletionContext.INSTANCE.getMetaDataProvider().getFunctionMetaData();
            for (ProcessorMetaData function : functions) {
                if (!function.getParameterOverloads().isEmpty()) {
                    for (String[] parameterOverload : function.getParameterOverloads()) {
                        functionSuggestions.add(generateFunctionSuggestion(function.getName(),
                                function.getNamespace(), parameterOverload,
                                function.getDescription(), function.getReturnAttributes()));
                    }
                } else {
                    functionSuggestions
                            .add(generateFunctionSuggestion(function.getName(), function.getNamespace(), null,
                                    function.getDescription(),
                                    function.getReturnAttributes()));
                }
            }
        }
        return functionSuggestions;
    }

    public static List<Object[]> getWindowProcessorFunctions() {
        if (windowSuggestions.isEmpty()) {
            List<ProcessorMetaData> windowProcessorFunctions = LSCompletionContext.INSTANCE.getMetaDataProvider().getWindowProcessorFunctions();
            for (ProcessorMetaData function : windowProcessorFunctions) {
                if (!function.getParameterOverloads().isEmpty()) {
                    for (String[] parameterOverload : function.getParameterOverloads()) {
                        windowSuggestions.add(generateFunctionSuggestion(function.getName(), parameterOverload,
                                function.getDescription(), function.getReturnAttributes()));
                    }
                } else {
                    windowSuggestions
                            .add(generateFunctionSuggestion(function.getName(), null, function.getDescription(),
                                    function.getReturnAttributes()));
                }

            }
        }
        return windowSuggestions;
    }

    public static List<Object[]> getStreamProcessorFunctions() {
        if (streamFunctionSuggestions.isEmpty()) {
            List<ProcessorMetaData> streamProcessorFunctions = LSCompletionContext.INSTANCE.getMetaDataProvider().getStreamProcessorFunctions();
            for (ProcessorMetaData function : streamProcessorFunctions) {
                if (!function.getParameterOverloads().isEmpty()) {
                    for (String[] parameterOverload : function.getParameterOverloads()) {
                        streamFunctionSuggestions
                                .add(generateFunctionSuggestion(function.getName(), function.getNamespace(),
                                        parameterOverload, function.getDescription(), function.getReturnAttributes()));
                    }
                } else {
                    streamFunctionSuggestions
                            .add(generateFunctionSuggestion(function.getName(), null, function.getDescription(),
                                    function.getReturnAttributes()));
                }

            }
        }
        return streamFunctionSuggestions;
    }

    public static Object[] generateFunctionSuggestion(String functionName, String functionNameSpace, String[] parameterOverload,
                                                      String description, List<AttributeMetaData> returnAttributes) {
        StringBuilder insertText = new StringBuilder();
        if (!functionNameSpace.equalsIgnoreCase("")) {
            insertText.append(functionNameSpace + ":");
        }
        insertText.append(functionName);
        if (parameterOverload == null) {
            insertText.append("()");
        } else {
            insertText.append("(").append(String.join(",", parameterOverload)).append(")");
        }
        StringBuilder descriptionText = new StringBuilder();
        descriptionText.append("functionName: " + functionName + "\n\nNameSpace: " + functionNameSpace +
                "\n\ndescription: " + description);
        if (returnAttributes != null) {
            descriptionText.append("\n\nreturnAttributes:");
            returnAttributes.forEach(returnAttribute -> {
                descriptionText.append(returnAttribute.getName()).append("\n" + returnAttribute.getDescription());
            });
        }

        Object[] functionSuggestion = {insertText.toString(), insertText.toString(), CompletionItemKind.Function,
                descriptionText.toString(),
                insertText.toString()};
        return functionSuggestion;
    }

    public static List<Object[]> getAggregatorFunctions() {
        if (aggregatorFunctionSuggestions.isEmpty()) {
            List<ProcessorMetaData> aggregatorFunctions = LSCompletionContext.INSTANCE.getMetaDataProvider().getAggregatorFunctions();
            for (ProcessorMetaData function : aggregatorFunctions) {
                if (!function.getParameterOverloads().isEmpty()) {
                    for (String[] parameterOverload : function.getParameterOverloads()) {
                        aggregatorFunctionSuggestions.add(generateFunctionSuggestion(function.getName(),
                                parameterOverload,
                                function.getDescription(), function.getReturnAttributes()));
                    }
                } else {
                    aggregatorFunctionSuggestions
                            .add(generateFunctionSuggestion(function.getName(), null,
                                    function.getDescription(), function.getReturnAttributes()));
                }

            }
        }
        return aggregatorFunctionSuggestions;
    }

    public static Object[] generateFunctionSuggestion(String functionName, String[] parameterOverload,
                                                      String description, List<AttributeMetaData> returnAttributes) {
        StringBuilder insertText = new StringBuilder(functionName);
        if (parameterOverload == null) {
            insertText.append("()");
        } else {
            insertText.append("(").append(String.join(",", parameterOverload)).append(")");
        }
        StringBuilder descriptionText = new StringBuilder();
        descriptionText.append("functionName: " + functionName + "\n\ndescription: " + description);
        if (returnAttributes != null) {
            descriptionText.append("\n\nreturnAttributes:");
            returnAttributes.forEach(returnAttribute -> {
                descriptionText.append(returnAttribute.getName()).append("\n" + returnAttribute.getDescription());
            });
        }

        Object[] functionSuggestion = {insertText.toString(), insertText.toString(), CompletionItemKind.Function,
                descriptionText.toString(),
                insertText.toString()};
        return functionSuggestion;
    }

    public static List<Object[]> getStoreAnnotations() {
        if (storeSuggestions.isEmpty()) {
            List<ProcessorMetaData> stores = LSCompletionContext.INSTANCE.getMetaDataProvider().getStores();
            for (ProcessorMetaData store : stores) {
                List<ParameterMetaData> parameters = store.getParameters().stream().filter(mandatoryPredicate).collect(
                        Collectors.toList());
                storeSuggestions.add(generateAnnotation(store.getName(), store.getNamespace(), parameters,
                        store.getDescription()));
            }
        }
        return storeSuggestions;
    }

    public static List<Object[]> getSinkAnnotations() {
        if (sinkSuggestions.isEmpty()) {
            List<ProcessorMetaData> sinks = LSCompletionContext.INSTANCE.getMetaDataProvider().getSinks();
            for (ProcessorMetaData sink : sinks) {
                List<ParameterMetaData> parameters = sink.getParameters().stream().filter(mandatoryPredicate).collect(
                        Collectors.toList());
                sinkSuggestions.add(generateAnnotation(sink.getName(), sink.getNamespace(), parameters,
                        sink.getDescription()));
            }
        }
        return sinkSuggestions;
    }

    public static List<Object[]> getSourceAnnotations() {
        if (sourceSuggestions.isEmpty()) {
            List<ProcessorMetaData> sources = LSCompletionContext.INSTANCE.getMetaDataProvider().getSources();
            for (ProcessorMetaData source : sources) {
                List<ParameterMetaData> parameters = source.getParameters().stream().filter(mandatoryPredicate).collect(
                        Collectors.toList());
                sourceSuggestions.add(generateAnnotation(source.getName(), source.getNamespace(), parameters,
                        source.getDescription()));
            }
        }
        return sourceSuggestions;
    }

    private static Predicate<ParameterMetaData> mandatoryPredicate =
            (parameterMetaData -> parameterMetaData.getOptional());

    public static Object[] generateAnnotation(String name, String nameSpace, List<ParameterMetaData> parameters,
                                              String description) {
        StringBuilder insertText = new StringBuilder("@" + nameSpace + "( " + "type = " + "\"" + name + "\"");
        parameters.forEach(parameter -> {
            insertText.append(", " + parameter.getName() + " = " + "\"" + parameter.getDefaultValue() + "\"");
        });
        insertText.append(")");
        Object[] annotationSuggestion = {insertText.toString(), insertText.toString(), CompletionItemKind.Snippet,
                description,
                insertText.toString()};
        return annotationSuggestion;
    }

    public static List<Object[]> generateAttributeReferences(Map<String, List<String>> attributeNameMap) {
        List<Object[]> suggestions = new ArrayList<>();
        for (Map.Entry entry : attributeNameMap.entrySet()) {
            String source = (String) entry.getKey();
            List<String> values = (ArrayList) entry.getValue();
            if (attributeNameMap.size() == 1) {
                values.forEach(value -> {
                    suggestions.add(new Object[]{value, value,
                            CompletionItemKind.Reference,
                            "attribute-reference", value});
                });
            } else {
                values.forEach(value -> {
                    suggestions.add(new Object[]{source + "." + value, source + "." + value,
                            CompletionItemKind.Reference,
                            "attribute-reference", source + "." + value});
                });
            }
        }
        return suggestions;
    }

    public static List<Object[]> generateSourceReferences(List<String> sources) {
        List<Object[]> suggestions = new ArrayList<>();
        for (String source : sources) {
            suggestions.add(new Object[]{source, source, CompletionItemKind.Reference,
                    "source", source});
        }
        return suggestions;
    }

    public static List<Object[]> generatePartitionKeys(Map<String, List<String>> attributeMap) {
        List<Object[]> suggestions = new ArrayList<>();
        for (Map.Entry entry : attributeMap.entrySet()) {
            String key = (String) entry.getKey();
            List<String> attributes = attributeMap.get(key);
            for (String attribute : attributes) {
                Object[] suggestion = new Object[]{attribute + " of " + key, attribute + " of " + key,
                        CompletionItemKind.Reference, "key-selection", attribute + " of " + key};
                suggestions.add(suggestion);
            }

        }
        return suggestions;
    }
}
