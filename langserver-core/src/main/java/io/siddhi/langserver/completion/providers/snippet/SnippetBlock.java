package io.siddhi.langserver.completion.providers.snippet;

import io.siddhi.core.SiddhiManager;
import io.siddhi.langserver.completion.providers.snippet.util.SnippetProviderUtil;
import io.siddhi.langserver.completion.providers.snippet.util.metadata.MetaData;
import io.siddhi.langserver.completion.providers.snippet.util.metadata.ProcessorMetaData;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.eclipse.lsp4j.CompletionItemKind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * {@code SnippetBlock provide snippets based on context.
 */
public class SnippetBlock {

    /**
     * snippets.
     */
    //todo : filter word is the word filtered when inserting for example if
    // @app is already there it is filtered and inserted into the file.

    /**
     * Each completion snippet consists of an array of
     * {inserted-text,label,completion-item-kind,detail,filter-word,sort-text,}
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

    public static final Object[] STREAM_DEFINITION =
            {"define stream stream_name (attr1 Type1, attrN TypeN)", "define-stream", CompletionItemKind.Snippet,
                    "define stream stream_name ($attrname $Type1, $nextattrname $TypeN)", "stream"};

    public static final Object[] QUERY_DEFINITION = {
            "from stream_name\n" +
                    "select attribute1 , attribute2\n" +
                    "insert into output_stream", "define-query", CompletionItemKind.Snippet, "\n" +
            "from stream_name\n" +
            "select $attribute1 $attribute2\n" +
            "insert into output_stream", "from"};

    public static final Object[] FUNCTION_DEFINITION =
            {"define function function_name[lang_name] return return_type { \n" +
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

    public static final Object[] PARTITION_DEFINITION = {"partition with (attribute_name of stream_name)\n" +
            "begin\n" +
            "    queries\n" +
            "end;", "define-partition", CompletionItemKind.Snippet,
            "partition with (${1:attribute_name} of ${2:stream_name})\n" +
                    "begin\n" +
                    "\t${3:queries}\n" +
                    "end;", "partition"};

    public static final Object[] TRIGGER_DEFINITION = {"define trigger trigger_name at time;", "define-trigger",
            CompletionItemKind.Snippet, "define trigger ${1:trigger_name} at ${2:time};", "define"};

    public static final Object[] WINDOW_DEFINITION = {"define window window_name (attr1 Type1, attN TypeN) " +
            "window_type output event_type events;", "define-window", CompletionItemKind.Snippet, "define-Window\n" +
            "define window ${1:window_name} (${2:attr1} ${3:Type1}, ${4:attN} ${5:TypeN}) ${6:window_type} ${7:output" +
            " ${8:event_type} events};", "define"};

    public static final Object[] QUERY_TABLE_JOIN = {"from stream_name as reference join table_name as reference\n" +
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

    public static final Object[] QUERY_PATTERN = {"from every stream_reference=stream_name[filter_condition] -> \n" +
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

    public static final Object[] QUERY_JOIN =
            {"from stream_name[filter_condition]#window.window_name(args) as reference\n" +
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

    public static final Object[] QUERY_WINDOW_FILTER =
            {"from stream_name[filter_condition]#window.namespace:window_name(args)\n" +
                    "select attribute1 , attribute2\n" +
                    "insert into output_stream;", "query-windowFilter", CompletionItemKind.Snippet,
                    "query-WindowFilter\n" +
                            "from ${1:stream_name}[${2:filter_condition}]#window.${3:namespace}:${4:window_name}(${5:args})\n" +
                            "select ${6:attribute1} , ${7:attribute2}\n" +
                            "insert into ${8:output_stream}", "from"};

    public static final Object[] QUERY_WINDOW = {"\n" +
            "from stream_name#window.namespace:window_name(args)\n" +
            "select attribute1, attribute2\n" +
            "insert into output_stream\n", "query-window", CompletionItemKind.Snippet, "query-Window\n" +
            "from ${1:stream_name}#window.${2:namespace}:${3:window_name}(${4:args})\n" +
            "select ${5:attribute1};, ${6:attribute2}\n" +
            "insert into ${7:output_stream}", "from"};

    public static final Object[] QUERY_FILTER = {"from stream_name[filter_condition]\n" +
            "select attribute1, attribute2\n" +
            "insert into output_stream;", "query-filter", CompletionItemKind.Snippet, "\n" +
            "from stream_name[filter_condition]\n" +
            "select attribute1, attribute2\n" +
            "insert into output_stream;", "query-Filter\n" +
            "from ${1:stream_name}[${2:filter_condition}]\n" +
            "select ${3:attribute1}, ${4:attribute2}\n" +
            "insert into ${5:output_stream}", "from"};

    public static final Object[] SINK_DEFINITION =
            {"@sink(type='sink_type', option_key='option_value', dynamic_option_key='{{dynamic_option_value}}',\n" +
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

    public static final Object[] SOURCE_DEFINITION = {"@source(type='source_type', option_key='option_value',\n" +
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

    public static final Object[] ANNOTATION_ASYNC_DEFINITION =
            {"@async(buffer.size=\"64\", workers='2', batch.size.max='10')", "annotate-Async",
                    CompletionItemKind.Snippet,
                    "annotate-Async\n" + "@async(buffer.size=\"${1:64}\", workers='${2:2}', batch.size.max='${3:10}')",
                    "@async"};

    public static final Object[] ANNOTATION_QUERYINFO_DEFINITION =
            {"@info(name = \"Query_Name\")", "annotate" + "-queryInfo", CompletionItemKind.Snippet,
                    "@async(buffer" + ".size=\"64\", " + "workers='2', batch" + ".size" + ".max='10')", "@info"};

    public static final Object[] ANNOTATION_PRIMARY_KEY_DEFINITION =
            {"@primaryKey('attribute_name')", "annotate-primarykey", CompletionItemKind.Snippet,
                    "annotate-PrimaryKey\n" + "@primaryKey('${1:attribute_name}')", "@primaryKey"};

    public static final Object[] ANNOTATION_INDEX_DEFINITION = {"@index('attribute_name')", "annotate-index",
            CompletionItemKind.Snippet, "annotate" +
            "-Index\n" +
            "@index('${1:attribute_name}')", "@index"};

    public static final Object[] ATTRIBUTE_NAME_TYPE_SNIPPET = {"attribute_name attribute_type", "attribute_name " +
            "attribute_type",
            CompletionItemKind.Snippet, "$attributeName <BOOL,FLOAT,DOUBLE,OBJECT,STRING,INT,LONG>", ""};

    //todo : add other definitions.
    /**
     * Siddhi app context's keywords
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
            {"partition", "partition", CompletionItemKind.Keyword, "keyword", "partition"};
    public static final Object[] KEYWORD_TABLE = {"table", "table", CompletionItemKind.Keyword, "keyword", "table"};
    public static final Object[] KEYWORD_FROM = {"from", "from", CompletionItemKind.Keyword, "keyword", "from"};

    /**
     * Query context keywords.
     */

    public static final Object[] KEYWORD_SELECT = {"select", "select", CompletionItemKind.Keyword, "keyword", "select"};
    public static final Object[] KEYWORD_INSERT = {"insert", "insert", CompletionItemKind.Keyword, "keyword", "insert"};
    public static final Object[] KEYWORD_DELETE = {"delete", "delete", CompletionItemKind.Keyword, "keyword", "delete"};
    public static final Object[] KEYWORD_UPDATE = {"update", "update", CompletionItemKind.Keyword, "keyword", "update"};
    public static final Object[] KEYWORD_RETURN = {"return", "return", CompletionItemKind.Keyword, "keyword", "return"};
    public static final Object[] KEYWORD_OUTPUT = {"output", "output", CompletionItemKind.Keyword, "keyword", "output"};

    /**
     * Siddhi attribute-type context's keywords.
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

    public static List<Object[]> suggestions = new ArrayList<>();

    public static List<Object[]> getFunctions() {

        if (suggestions.isEmpty()) {
            List<ProcessorMetaData> functions = SnippetProviderUtil.getFunctionMetaData();
            for (ProcessorMetaData function : functions) {
                suggestions.add(generateFunctionCompletionItem(function));
            }
        }
        return suggestions;
    }

    /**
     * Attribute reference context's completion generation
     */

    public static List<Object[]> generateAttributeReferences(List<Object> terminals) {
        List<Object[]> suggestions = new ArrayList<>();
        for (Object terminal : terminals) {
            String attributeValue = ((TerminalNodeImpl) terminal).getText();
            if ( attributeValue != null) {
                suggestions.add( new Object[] {attributeValue, attributeValue, CompletionItemKind.Reference,
                        "attribute-reference",attributeValue});
            }

        }
        return suggestions;
    }

    public static Object[] generateFunctionCompletionItem(ProcessorMetaData function) {
        //todo: when it has multiple parameter overloads
        StringBuilder insertText = new StringBuilder(function.getName());
        if (function.getParameterOverloads().isEmpty()) {
            insertText.append("()");
        } else {
            insertText.append("(").append(String.join(",", function.getParameterOverloads().get(0))).append(")");
        }
        Object[] functionCompletionItem = {insertText.toString(), function.getName(), CompletionItemKind.Function,
                function.getDescription(),
                function.getName()};
        return functionCompletionItem;
    }

    /**
     * by context getters.
     */

    public static List<String[]> getQueryContextKWs() {

        List<String[]> kws = new ArrayList<>();
//        kws.add(KW_SELECT);
//        kws.add(KW_INSERT);
//        kws.add(KW_OUTPUT);
//        kws.add(KW_RETURN);
//        kws.add(KW_DELETE);
//        kws.add(KW_UPDATE);
        return kws;
    }
    //todo: adding in multiple rows: is it necessary?

    //todo: provide functions genereated by Snippet provider util.
    public static List<String[]> getAttributeRefFunctions() {

        List<String[]> kws = new ArrayList<>();
//        kws.add(FUNC_AVG);
//        kws.add(FUNC_COALESCE);
//        kws.add(FUNC_IF_THEN_ELSE);
//        kws.add(FUNC_COUNT);
//        kws.add(FUNC_MAX);
//        kws.add(FUNC_MAXIMUM);
//        kws.add(FUNC_MAXFOREVER);
//        kws.add(FUNC_MINFOREVER);
//        kws.add(FUNC_MINIMUM);
//        kws.add(FUNC_STDEV);
//        kws.add(FUNC_SUM);
        return kws;
    }

    public static void getBuiltInFuncions() {

        SiddhiManager manger = new SiddhiManager();
        Map<String, Class> extensionsMap = manger.getExtensions();
        //Map<String,MetaData> metadataMap = generateExtensionsMetaData(extensionsMap);
        MetaData metaData = SnippetProviderUtil.getInBuiltProcessorMetaData();
        Map<String, MetaData> metaDataMap = SnippetProviderUtil.getExtensionProcessorMetaData();
        String app = "tets";
    }
}


/*
    private String label;
    private CompletionItemKind kind;
    private String detail;
    private Either<String, MarkupContent> documentation;
    private Boolean deprecated;
    private Boolean preselect;
    private String sortText;
    private String filterText;
    private String insertText;
    private InsertTextFormat insertTextFormat;
    private TextEdit textEdit;
    private List<TextEdit> additionalTextEdits;
    private List<String> commitCharacters;
    private Command command;
 */

//todo after all things have been done look at these things and complete all the completion items
