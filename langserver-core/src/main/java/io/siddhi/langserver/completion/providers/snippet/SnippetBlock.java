package io.siddhi.langserver.completion.providers.snippet;

import io.siddhi.langserver.completion.providers.snippet.util.metadata.AttributeMetaData;
import io.siddhi.langserver.completion.providers.snippet.util.metadata.ParameterMetaData;
import io.siddhi.langserver.completion.providers.snippet.util.metadata.ProcessorMetaData;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.eclipse.lsp4j.CompletionItemKind;
import org.eclipse.lsp4j.InsertTextFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * {@code SnippetBlock} provide constant snippets
 * <p>
 * Each completion snippet consists of an array of
 * {insertText,label,completionItemKind,details,filterText}.
 */

public class SnippetBlock {

    /**
     * siddhi_app context's snippets.
     */
    public static final Object[] APP_NAME_ANNOTATION_DEFINITION =
            {"@App:Name(\"App_Name\")", "annotate-appName", CompletionItemKind.Snippet, "annotate-AppName\n" +
                    "@App:Name(\"${1:App_Name}\")", "@app"};
    public static final Object[] APP_DESCRIPTION_ANNOTATION_DEFINITION =
            {"@App:description(\"App_Description\")", "annotate-appDescription", CompletionItemKind.Snippet,
                    "annotate-AppDescription\n" +
                            "@App:description(\"${1:App_Description}\")", "@app"};
    public static final Object[] APP_STATISTICS_ANNOTATION_DEFINITION =
            {"@app:description('Plan description')", "annotate-appStatistics", CompletionItemKind.Snippet,
                    "@App:statistics(\"Is_Enabled\")", "@app"};
    public static final List<Object[]> APP_ANNOTATION_DEFINITION = Arrays.asList(APP_NAME_ANNOTATION_DEFINITION,
            APP_DESCRIPTION_ANNOTATION_DEFINITION);
    public static final Object[] KEYWORD_ANNOTATION_NAME =
            {"name", "name", CompletionItemKind.Snippet, "app-annotation-name", "@app:"};
    public static final Object[] KEYWORD_ANNOTATION_DESCRIPTION =
            {"description", "description", CompletionItemKind.Snippet, "app-annotation-description", "@app:"};
    public static final Object[] KEYWORD_ANNOTATION_STATISTICS =
            {"statistics", "statistics", CompletionItemKind.Snippet, "app-annotation-statistics", "@app:"};
    public static final Object[] APP_ANNOTATION_ELEMENT_NAME_DEFINITION =
            {"\"plan name\"", "plan name", CompletionItemKind.Snippet, "app-annotation-element", "@app:name"};
    public static final Object[] APP_ANNOTATION_ELEMENT_DESCRIPTION_DEFINITION =
            {"\"plan description\"", "plan description", CompletionItemKind.Snippet, "app-annotation-element", "@app" +
                    ":description"};
    public static final Object[] APP_ANNOTATION_ELEMENT_STATISTICS_DEFINITION =
            {"\"statistic\"", "statistic", CompletionItemKind.Snippet, "app-annotation-element", "@app:statistics"};
    public static final Object[] STORE_SNIPPET =
            {"\"statistic\"", "statistic", CompletionItemKind.Snippet, "app-annotation-element", "@app:statistics"};
    public static final Object[] STREAM_DEFINITION =
            {"define stream ${1:stream_name} (${2:attr1} ${3:Type1}, ${4:attN} ${5:TypeN});", "define-stream",
                    CompletionItemKind.Snippet,
                    "define stream $stream_name ($attrname $Type1, $nextattrname $TypeN)", "stream",
                    InsertTextFormat.Snippet};
    public static final Object[] QUERY_DEFINITION = {
            "from $stream_name\n" +
                    "select $attribute1 $attribute2\n" +
                    "insert into output_stream", "define-query", CompletionItemKind.Snippet, "\n" +
            "from stream_name\n" +
            "select $attribute1 $attribute2\n" +
            "insert into output_stream", "from"};
    public static final Object[] FUNCTION_DEFINITION =
            {"define function function_name[lang_name] return return_type { \n" +
                    "    function_body \n" +
                    "    function_body \n" +
                    "};", "define-function", CompletionItemKind.Snippet,
                    "define-Function\n" +
                            "define function ${1:function_name}[${2:lang_name}] return ${3:return_type} { \n" +
                            "\t${4:function_body} \n" +
                            "};", "function"};
    public static final Object[] TABLE_DEFINITION =
            {"define table table_name (attr1 Type1, attN TypeN);", "define-table", CompletionItemKind.Snippet,
                    "define table ${1:table_name} (${2:attr1} ${3:Type1}, ${4:attN} ${5:TypeN});", "table"};
    public static final Object[] AGGREGATION_DEFINITION = {"define aggregation aggregator_name\n" +
            "from input_stream\n" +
            "select attribute1, aggregate_function(attribute2) as attribute3,aggregate_function(attribute4) as attribute5\n" +
            "    group by attribute6\n" +
            "    aggregate by timestamp_attribute every time_periods;", "define-aggregation",
            CompletionItemKind.Snippet, "define aggregation ${1:aggregator_name}\n" + "from ${2:input_stream}\n" +
            "select ${3:attribute1}, ${4:aggregate_function}(${5:attribute2}) as ${6:attribute3},${7:aggregate_function}(${8:attribute4}) as ${9:attribute5}\n" +
            "\tgroup by ${10:attribute6}\n" + "\taggregate by ${11:timestamp_attribute} every ${12:time_periods};",
            "define aggregation"};
    public static final Object[] PARTITION_DEFINITION = {
            "partition with (attribute_name of stream_name)\n" +
                    "begin\n" +
                    "    queries\n" +
                    "end;", "define-partition", CompletionItemKind.Snippet,
            "partition with (${1:attribute_name} of ${2:stream_name})\n" +
                    "begin\n" +
                    "\t${3:queries}\n" +
                    "end;", "partition"};
    public static final Object[] TRIGGER_DEFINITION = {
            "define trigger trigger_name at time;", "define-trigger",
            CompletionItemKind.Snippet, "define trigger ${1:trigger_name} at ${2:time};", "define"};
    public static final Object[] WINDOW_DEFINITION = {
            "define window window_name (attr1 Type1, attN TypeN) " +
                    "window_type output event_type events;", "define-window", CompletionItemKind.Snippet,
            "define-Window\n" +
                    "define window ${1:window_name} (${2:attr1} ${3:Type1}, ${4:attN} ${5:TypeN}) ${6:window_type} ${7:output" +
                    " ${8:event_type} events};", "define"};
    public static final Object[] QUERY_TABLE_JOIN = {
            "from stream_name as reference join table_name as reference\n" +
                    "    on join_condition\n" +
                    "select attribute1, attribute2\n" +
                    "limit limit_value\n" +
                    "offset offset_value\n" +
                    "insert into output_stream;", "query-tableJoin", CompletionItemKind.Snippet, "query-tableJoin\n" +
            "from ${1:stream_name} as ${2:reference} join ${3:table_name} as ${4:reference}\n" +
            "\ton ${5:join_condition}\n" +
            "select ${6:attribute1}, ${7:attribute2}\n" +
            "limit ${8:limit_value}\n" +
            "offset ${9:offset_value}\n" +
            "insert into ${10:output_stream}", "from"};
    public static final Object[] QUERY_PATTERN = {
            "from every stream_reference=stream_name[filter_condition] -> \n" +
                    "    every stream_reference2=stream_name2[filter_condition2]\n" +
                    "    within  time_gap\n" +
                    "select stream_reference.attribute1, stream_reference.attribute1\n" +
                    "insert into output_stream;", "query-pattern", CompletionItemKind.Snippet, "\n" +
            "query-Pattern\n" +
            "from every ${1:stream_reference}=${2:stream_name}[${3:filter_condition}] -> \n" +
            "\tevery ${4:stream_reference2}=${5:stream_name2}[${6:filter_condition2}]\n" +
            "\twithin ${7: time_gap}\n" +
            "select ${8:stream_reference}.${9:attribute1}, ${10:stream_reference}.${11:attribute1}\n" +
            "insert into ${12:output_stream}", "from"};
    public static final Object[] QUERY_JOIN = {
            "from stream_name[filter_condition]#window.window_name(args) as reference\n" +
                    "    join stream_name[filter_condition]#window.window_name(args) as reference\n" +
                    "    on join_condition\n" +
                    "    within  time_gap\n" +
                    "select attribute1, attribute2\n" +
                    "insert into output_stream;", "query-join", CompletionItemKind.Snippet, "query-Join\n" +
            "from ${1:stream_name}[${2:filter_condition}]#window.${3:window_name}(${4:args}) as ${5:reference}\n" +
            "\tjoin ${6:stream_name}[${7:filter_condition}]#window.${8:window_name}(${9:args}) as ${10:reference}\n" +
            "\ton ${11:join_condition}\n" +
            "\twithin ${12: time_gap}\n" +
            "select ${13:attribute1}, ${14:attribute2}\n" +
            "insert into ${15:output_stream}", "from"};
    public static final Object[] QUERY_WINDOW_FILTER = {
            "from stream_name[filter_condition]#window.namespace:window_name(args)\n" +
                    "select attribute1 , attribute2\n" +
                    "insert into output_stream;", "query-windowFilter", CompletionItemKind.Snippet,
            "query-WindowFilter\n" +
                    "from ${1:stream_name}[${2:filter_condition}]#window.${3:namespace}:${4:window_name}(${5:args})\n" +
                    "select ${6:attribute1} , ${7:attribute2}\n" +
                    "insert into ${8:output_stream}", "from"};
    public static final Object[] QUERY_WINDOW = {
            "from stream_name#window.namespace:window_name(args)\n" +
                    "select attribute1, attribute2\n" +
                    "insert into output_stream\n", "query-window", CompletionItemKind.Snippet, "query-Window\n" +
            "from ${1:stream_name}#window.${2:namespace}:${3:window_name}(${4:args})\n" +
            "select ${5:attribute1};, ${6:attribute2}\n" +
            "insert into ${7:output_stream}", "from"};
    public static final Object[] QUERY_FILTER = {
            "from stream_name[filter_condition]\n" +
                    "select attribute1, attribute2\n" +
                    "insert into output_stream;", "query-filter", CompletionItemKind.Snippet, "\n" +
            "from stream_name[filter_condition]\n" +
            "select attribute1, attribute2\n" +
            "insert into output_stream;", "query-Filter\n" +
            "from ${1:stream_name}[${2:filter_condition}]\n" +
            "select ${3:attribute1}, ${4:attribute2}\n" +
            "insert into ${5:output_stream}", "from"};
    public static final Object[] SINK_DEFINITION = {
            "@sink(type='sink_type', option_key='option_value', dynamic_option_key='{{dynamic_option_value}}',\n" +
                    "    @map(type='map_type', option_key='option_value', dynamic_option_key='{{dynamic_option_value}}',\n" +
                    "        @payload( 'payload_mapping')\n" +
                    "    )\n" +
                    ")\n" +
                    "define stream stream_name (attribute1 Type1, attributeN TypeN);", "define-sink",
            CompletionItemKind.Snippet, "define-Sink\n" +
            "@sink(type='${1:sink_type}', ${2:option_key}='${3:option_value}', ${4:dynamic_option_key}='{{${5:dynamic_option_value}}}',\n" +
            "\t@map(type='${6:map_type}', ${7:option_key}='${8:option_value}', ${9:dynamic_option_key}='{{${10:dynamic_option_value}}}',\n" +
            "\t\t@payload( '${11:payload_mapping}')\n" +
            "\t)\n" +
            ")\n" +
            "define stream ${12:stream_name} (${13:attribute1} ${14:Type1}, ${15:attributeN} ${16:TypeN});",
            "@sink"};
    public static final Object[] SOURCE_DEFINITION = {
            "@source(type='source_type', option_key='option_value',\n" +
                    "    @map(type='map_type', option_key='option_value',\n" +
                    "        @attributes('attribute_mapping_1', 'attribute_mapping_N')\n" +
                    "    )\n" +
                    ")\n" +
                    "define stream stream_name (attribute1 Type1, attributeN TypeN);", "define-source",
            CompletionItemKind.Snippet, "define-Source\n" +
            "@source(type='${1:source_type}', ${2:option_key}='${3:option_value}',\n" +
            "\t@map(type='${4:map_type}', ${5:option_key}='${6:option_value}',\n" +
            "\t\t@attributes('${7:attribute_mapping_1}', '${8:attribute_mapping_N}')\n" +
            "\t)\n" +
            ")\n" +
            "define stream ${9:stream_name} (${10:attribute1} ${11:Type1}, ${12:attributeN} ${13:TypeN});", "@source"};
    public static final Object[] ANNOTATION_ASYNC_DEFINITION = {
            "@async(buffer.size=\"64\", workers='2', batch.size.max='10')", "annotate-Async",
            CompletionItemKind.Snippet,
            "annotate-Async\n" + "@async(buffer.size=\"${1:64}\", workers='${2:2}', batch.size.max='${3:10}')",
            "@async"};
    public static final Object[] ANNOTATION_QUERYINFO_DEFINITION = {
            "@info(name = \"Query_Name\")", "annotate" + "-queryInfo", CompletionItemKind.Snippet,
            "@async(buffer" + ".size=\"64\", " + "workers='2', batch" + ".size" + ".max='10')", "@info"};
    public static final Object[] ANNOTATION_PRIMARY_KEY_DEFINITION = {
            "@primaryKey('attribute_name')", "annotate-primarykey", CompletionItemKind.Snippet,
            "annotate-PrimaryKey\n" + "@primaryKey('${1:attribute_name}')", "@primaryKey"};
    public static final Object[] ANNOTATION_INDEX_DEFINITION = {
            "@index('attribute_name')", "annotate-index",
            CompletionItemKind.Snippet, "annotate" +
            "-Index\n" +
            "@index('${1:attribute_name}')", "@index"};

    /**
     * siddhi_app context's keywords
     */
    public static final Object[] KEYWORD_DEFINE = {"define", "define", CompletionItemKind.Keyword, "keyword", "define"};
    public static final Object[] KEYWORD_STREAM = {"stream", "stream", CompletionItemKind.Keyword, "keyword", "stream"};
    public static final Object[] KEYWORD_AGGREGATION =
            {"aggregation", "aggregation", CompletionItemKind.Keyword, "keyword", "aggregation"};
    public static final Object[] KEYWORD_TRIGGER =
            {"trigger", "trigger", CompletionItemKind.Keyword, "keyword", "trigger"};
    public static final Object[] KEYWORD_FUNCTION =
            {"function", "function", CompletionItemKind.Keyword, "keyword", "function"};
    public static final Object[] KEYWORD_WINDOW = {"window", "window", CompletionItemKind.Keyword, "keyword", "window"};
    public static final Object[] KEYWORD_PARTITION =
            {"partition with", "partition", CompletionItemKind.Keyword, "keyword", "partition"};
    public static final Object[] KEYWORD_TABLE = {"table", "table", CompletionItemKind.Keyword, "keyword", "table"};
    public static final Object[] KEYWORD_FROM = {"from", "from", CompletionItemKind.Keyword, "keyword", "from"};
    public static final Object[] KEYWORD_INSERT_INTO = {"insert into", "insert into", CompletionItemKind.Keyword,
            "keyword",
            "insert into"};
    public static final Object[] KEYWORD_UPDATE_OR_INSERT_INTO = {"update or insert into", "update or insert into",
            CompletionItemKind.Keyword,
            "keyword",
            "update or insert into"};
    public static final Object[] KEYWORD_INTO = {"into", "into", CompletionItemKind.Keyword,
            "keyword",
            "into"};
    public static final Object[] KEYWORD_FOR = {"for", "for", CompletionItemKind.Keyword,
            "keyword",
            "for"};
    public static final Object[] KEYWORD_ON = {"on", "on", CompletionItemKind.Keyword,
            "keyword",
            "on"};
    public static final Object[] KEYWORD_SET = {"set", "set", CompletionItemKind.Keyword,
            "keyword",
            "set"};
    /**
     * Output EventType Keywords
     */
    public static final Object[] KEYWORD_ALL_EVENTS = {"all events", "all events", CompletionItemKind.Keyword,
            "keyword",
            "all events"};
    public static final Object[] KEYWORD_EXPIRED_EVENTS = {"expired events", "expired events",
            CompletionItemKind.Keyword,
            "keyword",
            "expired events"};
    public static final Object[] KEYWORD_CURRENT_EVENTS = {"current events", "current events",
            CompletionItemKind.Keyword,
            "keyword",
            "current events"};
    public static final Object[] KEYWORD_CURRENT = {"current", "current",
            CompletionItemKind.Keyword,
            "keyword",
            "current"};
    public static final Object[] KEYWORD_EVENTS = {"events", "events",
            CompletionItemKind.Keyword,
            "keyword",
            "events"};

    /**
     *
     */
    public static final Object[] ATTRIBUTE_NAME_TYPE_SNIPPET = {"attribute_name attribute_type", "attribute_name " +
            "attribute_type",
            CompletionItemKind.Snippet, "$attributeName <BOOL,FLOAT,DOUBLE,OBJECT,STRING,INT,LONG>", ""};
    public static final Object[] ALIAS_SNIPPET =
            {"alias", "alias", CompletionItemKind.Snippet, "attribute-name", "alias"};
    //todo: do I need this?
    public static final Object[] ATTRIBUTE_LIST_SNIPPET = {"(attr1 Type1, attN TypeN)", "(attr1 Type1, attN TypeN)",
            CompletionItemKind.Snippet, "attribute list", "(attr1 Type1, attN TypeN);"};

    public static final Object[] STREAM_NAME_SNIPPET = {"streamName", "streamName", CompletionItemKind.Snippet
            , "stream name:source", "streamName"};
    public static final Object[] TRIGGER_NAME_SNIPPET = {"triggerName", "triggerName", CompletionItemKind.Snippet
            , "trigger name:source", "triggerName"};
    public static final Object[] WINDOW_NAME_SNIPPET = {"windowName", "windowName", CompletionItemKind.Snippet
            , "window name:source", "windowName"};
    public static final Object[] TABLE_NAME_SNIPPET = {"tableName", "tableName", CompletionItemKind.Snippet
            , "table name:source", "tableName"};
    public static final Object[] FUNCTION_NAME_SNIPPET = {"functionName", "functionName", CompletionItemKind.Snippet
            , "function name:id", "functionName"};
    public static final Object[] LANGUAGE_NAME_SNIPPET = {"[languageName]", "languageName", CompletionItemKind.Snippet
            , "language name", "languageName"};
    public static final Object[] FUNCTION_BODY_SNIPPET = {"{\n\t\tbody\n};", "function-body", CompletionItemKind.Snippet
            , "block", "{function-body}"};

    /**
     * Constants
     */
    public static final Object[] TRIPLE_DOT = {"...", "...", CompletionItemKind.Constant, "triple-dot", "..."};

    /**
     * Query context keywords.
     */
    public static final Object[] KEYWORD_SELECT = {"select", "select", CompletionItemKind.Keyword, "keyword", "select"};
    public static final Object[] KEYWORD_INSERT = {"insert", "insert", CompletionItemKind.Keyword, "keyword", "insert"};
    public static final Object[] KEYWORD_DELETE = {"delete", "delete", CompletionItemKind.Keyword, "keyword", "delete"};
    public static final Object[] KEYWORD_UPDATE = {"update", "update", CompletionItemKind.Keyword, "keyword", "update"};
    public static final Object[] KEYWORD_RETURN = {"return", "return", CompletionItemKind.Keyword, "keyword", "return"};
    public static final Object[] KEYWORD_OUTPUT = {"output", "output", CompletionItemKind.Keyword, "keyword", "output"};
    public static final Object[] KEYWORD_EVERY = {"every", "every", CompletionItemKind.Keyword, "keyword", "every"};

    /**
     * attribute_type context's keywords.
     */
    //todo:have more descriptive explanation : adding keyword thing seprately
    public static final Object[] KEYWORD_INT = {"int", "int", CompletionItemKind.Keyword, "attribute type:int",
            "int"};
    public static final Object[] KEYWORD_LONG = {"long", "long", CompletionItemKind.Keyword, "attribute type:long",
            "long"};
    public static final Object[] KEYWORD_DOUBLE =
            {"double", "double", CompletionItemKind.Keyword, "attribute type:double", "double"};
    public static final Object[] KEYWORD_FLOAT =
            {"float", "float", CompletionItemKind.Keyword, "attribute type:float", "float"};
    public static final Object[] KEYWORD_STRING =
            {"string", "string", CompletionItemKind.Keyword, "attribute type:string", "string"};
    public static final Object[] KEYWORD_BOOL = {"bool", "bool", CompletionItemKind.Keyword, "attribute type:bool",
            "bool"};
    public static final Object[] KEYWORD_OBJECT =
            {"object", "object", CompletionItemKind.Keyword, "attribute type:object", "object"};

    public static final List<Object[]> attributeTypes = Arrays.asList(
            KEYWORD_STRING,
            KEYWORD_INT,
            KEYWORD_FLOAT,
            KEYWORD_DOUBLE,
            KEYWORD_LONG,
            KEYWORD_BOOL,
            KEYWORD_OBJECT);

    /**
     * Function operation context's function completions
     */
    //todo:adding namespace to functions
    private static List<Object[]> functionSuggestions = new ArrayList<>();

    public static List<Object[]> getFunctions() {

        if (functionSuggestions.isEmpty()) {
            List<ProcessorMetaData> functions = MetaDataProvider.getFunctionMetaData();
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

    private static List<Object[]> windowSuggestions = new ArrayList<>();

    public static List<Object[]> getWindowProcessorFunctions() {

        if (windowSuggestions.isEmpty()) {
            List<ProcessorMetaData> windowProcessorFunctions = MetaDataProvider.getWindowProcessorFunctions();
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
            List<ProcessorMetaData> streamProcessorFunctions = MetaDataProvider.getStreamProcessorFunctions();
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

    public static Object[] generateFunctionSuggestion(String functionName,
                                                      String functionNameSpace, String[] parameterOverload,
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

    private static List<Object[]> aggregatorFunctionSuggestions = new ArrayList<>();

    public static List<Object[]> getAggregatorFunctions() {

        if (aggregatorFunctionSuggestions.isEmpty()) {
            List<ProcessorMetaData> aggregatorFunctions = MetaDataProvider.getAggregatorFunctions();
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

    private static List<Object[]> streamFunctionSuggestions = new ArrayList<>();

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

    private static Predicate<ParameterMetaData> mandatoryPredicate =
            (parameterMetaData -> parameterMetaData.getOptional());
    private static List<Object[]> storeSuggestions = new ArrayList<>();

    public static List<Object[]> getStoreAnnotations() {

        if (storeSuggestions.isEmpty()) {
            List<ProcessorMetaData> stores = MetaDataProvider.getStores();
            for (ProcessorMetaData store : stores) {
                List<ParameterMetaData> parameters = store.getParameters().stream().filter(mandatoryPredicate).collect(
                        Collectors.toList());
                storeSuggestions.add(generateAnnotation(store.getName(), store.getNamespace(), parameters,
                        store.getDescription()));
            }
        }
        return storeSuggestions;
    }

    private static List<Object[]> sinkSuggestions = new ArrayList<>();

    public static List<Object[]> getSinkAnnotations() {

        if (sinkSuggestions.isEmpty()) {
            List<ProcessorMetaData> sinks = MetaDataProvider.getSinks();
            for (ProcessorMetaData sink : sinks) {
                List<ParameterMetaData> parameters = sink.getParameters().stream().filter(mandatoryPredicate).collect(
                        Collectors.toList());
                sinkSuggestions.add(generateAnnotation(sink.getName(), sink.getNamespace(), parameters,
                        sink.getDescription()));
            }
        }
        return sinkSuggestions;
    }

    private static List<Object[]> sourceSuggestions = new ArrayList<>();

    public static List<Object[]> getSourceAnnotations() {

        if (sourceSuggestions.isEmpty()) {
            List<ProcessorMetaData> sources = MetaDataProvider.getSources();
            for (ProcessorMetaData source : sources) {
                List<ParameterMetaData> parameters = source.getParameters().stream().filter(mandatoryPredicate).collect(
                        Collectors.toList());
                sourceSuggestions.add(generateAnnotation(source.getName(), source.getNamespace(), parameters,
                        source.getDescription()));
            }
        }
        return sourceSuggestions;
    }

    public static Object[] generateAnnotation(String name, String nameSpace, List<ParameterMetaData> parameters,
                                              String description) {

        StringBuilder insertText = new StringBuilder("@" + nameSpace + "( " + "type = " + "\"" + name + "\"");
        parameters.forEach(parameter -> {
            insertText.append(", " + parameter.getName() + " = " + "\""+parameter.getDefaultValue()+"\"");
        });
        insertText.append(")");
        Object[] annotationSuggestion = {insertText.toString(), insertText.toString(), CompletionItemKind.Snippet,
                description,
                insertText.toString()};
        return annotationSuggestion;
    }

    /**
     * Attribute reference context's completion generation
     */
    public static final Object[] KEYWORD_HAVING = {"having", "having", CompletionItemKind.Keyword, "keyword",
            "having"};
    public static final Object[] KEYWORD_ORDER_BY = {"order by", "order by", CompletionItemKind.Keyword, "keyword",
            "order"};
    //todo: how to include spaces order_by,aggregate_by,group_by
    public static final Object[] KEYWORD_LIMIT =
            {"limit", "limit", CompletionItemKind.Keyword, "keyword", "limit"};
    public static final Object[] KEYWORD_OFFSET =
            {"offset", "offset", CompletionItemKind.Keyword, "keyword", "offset"};
    public static final Object[] KEYWORD_GROUP_BY =
            {"group by", "group by", CompletionItemKind.Keyword, "keyword", "group"};
    public static final Object[] KEYWORD_AGGREGATE_BY =
            {"aggregate by", "aggregate by", CompletionItemKind.Keyword, "keyword", "aggregate"};

    public static final Object[] KEYWORD_AS = {"as", "as", CompletionItemKind.Keyword, "keyword", "as"};
    public static final Object[] KEYWORD_AT = {"at", "at", CompletionItemKind.Keyword, "keyword", "at"};

    public static final List<Object[]> QUERY_SECTION_KEYWORDS = Arrays.asList(
            KEYWORD_GROUP_BY, KEYWORD_OFFSET, KEYWORD_HAVING, KEYWORD_ORDER_BY, KEYWORD_LIMIT, KEYWORD_INSERT,
            KEYWORD_INSERT_INTO, KEYWORD_DELETE, KEYWORD_UPDATE_OR_INSERT_INTO, KEYWORD_RETURN, KEYWORD_UPDATE);

    public static List<Object[]> generateAttributeReferences(Map<String, List<String>> attributeNameMap) {

        List<Object[]> suggestions = new ArrayList<>();
        for (Map.Entry entry : attributeNameMap.entrySet()) {
            String source = (String) entry.getKey();
            List<String> values = (ArrayList) entry.getValue();
            values.forEach(value -> {

                suggestions.add(new Object[]{source + "." + value, source + "." + value,
                        CompletionItemKind.Reference,
                        "attribute-reference", source + "." + value});
            });
        }
        return suggestions;
    }

    public static List<Object[]> generateAttributeReferences(List<Object> terminals) {

        List<Object[]> suggestions = new ArrayList<>();
        for (Object terminal : terminals) {
            String attributeValue = ((TerminalNodeImpl) terminal).getText();
            if (attributeValue != null) {
                suggestions.add(new Object[]{attributeValue, attributeValue,
                        CompletionItemKind.Reference,
                        "attribute-reference", attributeValue});
            }
        }
        return suggestions;
    }

    /**
     *QuerySection context's Keywords
     */

    /**
     * OrderContext's keywords
     */
    public static final Object[] KEYWORD_ASC = {"asc", "asc", CompletionItemKind.Keyword, "keyword", "asc"};
    public static final Object[] KEYWORD_DESC = {"desc", "desc", CompletionItemKind.Keyword, "keyword", "desc"};

    public static final List<Object[]> ORDER_KEYWORDS = Arrays.asList(KEYWORD_ASC, KEYWORD_DESC);

    /**
     * Source context suggestion generation
     */
    public static List<Object[]> generateSourceReferences(List<String> sources) {

        List<Object[]> suggestions = new ArrayList<>();
        for (String source : sources) {
            suggestions.add(new Object[]{source, source, CompletionItemKind.Reference,
                    "source", source});
        }
        return suggestions;
    }

    /**
     * AggregationTimeDuration context's constants
     */

    public static final Object[] MILLISECONDS = {"milliseconds", "milliseconds", CompletionItemKind.Constant,
            "constant", "milliseconds"};
    public static final Object[] SECONDS =
            {"seconds", "seconds", CompletionItemKind.Constant, "constant", "seconds"};
    public static final Object[] MINUTES =
            {"minutes", "minutes", CompletionItemKind.Constant, "constant", "minutes"};
    public static final Object[] HOURS = {"hours", "hours", CompletionItemKind.Constant, "constant", "hours"};
    public static final Object[] DAYS = {"days", "days", CompletionItemKind.Constant, "constant", "days"};
    public static final Object[] WEEKS = {"weeks", "weeks", CompletionItemKind.Constant, "constant", "weeks"};
    public static final Object[] MONTHS = {"months", "months", CompletionItemKind.Constant, "constant", "months"};
    public static final Object[] YEARS = {"years", "years", CompletionItemKind.Constant, "constant", "years"};

    public static List<Object[]> AGGREGATION_TIME_DURATION = Arrays.asList(SECONDS, MINUTES, HOURS, DAYS, MONTHS,
            YEARS);
    public static List<Object[]> TIME_VALUE = Arrays.asList(SECONDS, MINUTES, HOURS, DAYS, MONTHS,
            YEARS, MILLISECONDS);

    /**
     * Constant Value context
     */
    public static final Object[] CONSTANT_TRUE = {"true", "true", CompletionItemKind.Constant, "constant", "true"};
    public static final Object[] CONSTANT_FALSE =
            {"false", "false", CompletionItemKind.Constant, "constant", "false"};

    public static List<Object[]> BOOLEAN_CONSTANTS = Arrays.asList(CONSTANT_TRUE, CONSTANT_FALSE);

    /**
     * Partition completion Objects
     */

    public static List<Object[]> generatePartitionKeys(Map<String, List<String>> attributeMap) {

        List<Object[]> suggestions = new ArrayList<>();
        for (Map.Entry entry : attributeMap.entrySet()) {
            String key = (String) entry.getKey();
            List<String> attributes = attributeMap.get(key);
            for (String attribute : attributes) {
                Object[] suggestion = new Object[]{attribute + " of " + key, attribute + " of " + key,
                        CompletionItemKind.Reference, "key-selection", attribute + " of " + key};
                //todo: completionItemKind is it right and filter word containing spaces.
                suggestions.add(suggestion);
            }

        }
        return suggestions;
    }

    public static final Object[] PARTITION_BLOCK_SNIPPET = {
            "\nbegin\n" + "\tqueries\n" + "end;", "partition-block",
            CompletionItemKind.Snippet
            , "partition-block: " +

            "\nbegin\n" + "  queries\n" + "end;", "begin"};

    public static final Object[] KEYWORD_BEGIN = {"begin", "begin", CompletionItemKind.Keyword, "keyword", "begin"};
    public static final Object[] KEYWORD_END = {"end", "end", CompletionItemKind.Keyword, "keyword", "end"};

    /**
     * Query Input Context clauses and keywords.
     */
    public static final Object[] KEYWORD_JOIN = {"join", "join", CompletionItemKind.Keyword, "keyword", "join"};
    public static final Object[] KEYWORD_LEFT = {"left", "left", CompletionItemKind.Keyword, "keyword", "left"};
    public static final Object[] KEYWORD_RIGHT = {"right", "right", CompletionItemKind.Keyword, "keyword", "right"};
    public static final Object[] KEYWORD_OUTER = {"outer", "outer", CompletionItemKind.Keyword, "keyword", "outer"};
    public static final Object[] KEYWORD_FULL = {"full", "full", CompletionItemKind.Keyword, "keyword", "full"};
    public static final Object[] KEYWORD_UNIDIRECTIONAL = {"unidirectional", "unidirectional",
            CompletionItemKind.Keyword,
            "keyword",
            "unidirectional"};
    public static final Object[] CLAUSE_LEFT_OUTER_JOIN = {"left outer join", "left outer join",
            CompletionItemKind.Keyword,
            "keyword",
            "left\touter\tjoin"};
    public static final Object[] CLAUSE_RIGHT_OUTER_JOIN = {"right outer join", "right outer join",
            CompletionItemKind.Keyword,
            "keyword",
            "right\touter\tjoin"};
    public static final Object[] CLAUSE_FULL_OUTER_JOIN = {"full outer join", "full outer join",
            CompletionItemKind.Keyword,
            "keyword",
            "full\touter\tjoin"};

}

