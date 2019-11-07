package io.siddhi.langserver.completion.snippet;

import io.siddhi.core.SiddhiManager;
import io.siddhi.langserver.completion.snippet.util.SnippetProviderUtil;
import io.siddhi.langserver.completion.snippet.util.metadata.MetaData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * {@code SnippetBlock provide snippets based on context.
 */

public class SnippetBlock {
    /**snippets.*/
    public static final String[] APP_ANNOTATION = {"@app:name('SiddhiAppName')", "app-annotation", "@app:name($name)", "@app"};
    public static final String[] STREAM_DEFINITION = {"define stream stream_name (attr1 Type1, attrN TypeN)", "define-stream", "define stream stream_name ($attrname $Type1, $nextattrname $TypeN)", "define"};

    public static final String[] QUERY_DEFINITION = {
            "from stream_name\n" +
            "select attribute1 , attribute2\n" +
            "insert into output_stream", "define-query", "\n" +
            "from stream_name\n" +
            "select $attribute1 $attribute2\n" +
            "insert into output_stream", "from"};

    public static final String[] FUNC_DEFINITION = {"define function function_name[lang_name] return return_type { \n" +
            "    function_body \n" +
            "};", "define-function",
            "define-Function\n" +
            "define function ${1:function_name}[${2:lang_name}] return ${3:return_type} { \n" +
            "\t${4:function_body} \n" +
            "};", "function"};

    public  static final String[] TABLE_DEFINITION = {"define table table_name (attr1 Type1, attN TypeN);", "define-table",
            "define table ${1:table_name} (${2:attr1} ${3:Type1}, ${4:attN} ${5:TypeN});", "table"};

    public static final String[] AGGREGATION_DEFINITION = {"define aggregation aggregator_name\n" +
            "from input_stream\n" +
            "select attribute1, aggregate_function(attribute2) as attribute3,aggregate_function(attribute4) as attribute5\n" +
            "    group by attribute6\n" +
            "    aggregate by timestamp_attribute every time_periods;", "define-aggregation",
            "define aggregation ${1:aggregator_name}\n" +
            "from ${2:input_stream}\n" +
            "select ${3:attribute1}, ${4:aggregate_function}(${5:attribute2}) as ${6:attribute3},${7:aggregate_function}(${8:attribute4}) as ${9:attribute5}\n" +
            "\tgroup by ${10:attribute6}\n" +
            "\taggregate by ${11:timestamp_attribute} every ${12:time_periods};", "aggregation"};

    public static final String[] PARTITION_DEFINITION = {"partition with (attribute_name of stream_name)\n" +
            "begin\n" +
            "    queries\n" +
            "end;", "define-partition",
            "partition with (${1:attribute_name} of ${2:stream_name})\n" +
            "begin\n" +
            "\t${3:queries}\n" +
            "end;", "partition"};
    public static final String[] TRIGGER_DEFINITION = {"define trigger trigger_name at time;", "define-trigger",
            "define trigger ${1:trigger_name} at ${2:time};", "trigger"};




    /**functions.*/
    public static final String[] FUNC_AVG = {"avg(arg)", "avg()", "avg\n" +
            "Calculates the average for all the events.\n" +
            "\n" +
            "Parameters - \n" +
            "arg - INT | LONG | DOUBLE | FLOAT - The value that need to be averaged.", "avg"};

    public static final String[] FUNC_SUM = {"sum()", "sum(arg)", "sum\nReturns the sum for all the events.\nParameters -\n" +
            "arg - INT | LONG | DOUBLE | FLOAT - The value that needs to be summed.", "sum"};

    public  static final String[] FUNC_MAXFOREVER = {"maxForever(arg)", "maxForever", "maxForever\n" +
            "This is the attribute aggregator to store the maximum value for a given attribute throughout the lifetime of the query regardless of any windows in-front.\n" +
            "\n" +
            "Parameters - \n" +
            "arg - INT | LONG | DOUBLE | FLOAT - The value that needs to be compared to find the maximum value.", "maxForever"};

    public static final String[] FUNC_MAXIMUM = {"maximum(arg)", "maximum", "maximum\n" +
            "Returns the maximum value of the input parameters.\n" +
            "\n" +
            "Parameters - \n" +
            "arg - INT | LONG | DOUBLE | FLOAT - This function accepts one or more parameters. They can belong to any one of the available types. All the specified parameters should be of the same type.\n" +
            "Return Attributes - \n" +
            "Attribute 1 - INT | LONG | DOUBLE | FLOAT - This will be the same as the type of the first input parameter.", "maximum"};
    public static final String[] FUNC_MAX = {"max(arg)", "max", "max\n" +
            "Returns the maximum value for all the events.\n" +
            "\n" +
            "Parameters - \n" +
            "arg - INT | LONG | DOUBLE | FLOAT - The value that needs to be compared to find the maximum value.", "max"};

    public static final String[] FUNC_MINIMUM = {"minimum(arg)", "minimum", "minimum\n" +
            "Returns the minimum value of the input parameters.\n" +
            "\n" +
            "Parameters - \n" +
            "arg - INT | LONG | DOUBLE | FLOAT - This function accepts one or more parameters. They can belong to any one of the available types. All the specified parameters should be of the same type.\n" +
            "Return Attributes - \n" +
            "Attribute 1 - INT | LONG | DOUBLE | FLOAT - This will be the same as the type of the first input parameter.", "minimum"};

    public static final String[] FUNC_COUNT = {"count(arg)", "count", "count\n" +
            "Returns the count of all the events.", "count"};

    public static final String[] FUNC_IF_THEN_ELSE = {" ifThenElse(condition, if.expression, else.expression)", "ifThenElse", "ifThenElse\n" +
            "Evaluates the 'condition' parameter and returns value of the 'if.expression' parameter if the condition is true, or returns value of the 'else.expression' parameter if the condition is false. Here both 'if.expression' and 'else.expression' should be of the same type.\n" +
            "\n" +
            "Parameters - \n" +
            "condition - BOOL - This specifies the if then else condition value.\n" +
            "if.expression - INT | LONG | DOUBLE | FLOAT | STRING | BOOL | OBJECT - This specifies the value to be returned if the value of the condition parameter is true.\n" +
            "else.expression - INT | LONG | DOUBLE | FLOAT | STRING | BOOL | OBJECT - This specifies the value to be returned if the value of the condition parameter is false.\n" +
            "Return Attributes - \n" +
            "Attribute 1 - INT | LONG | DOUBLE | FLOAT | STRING | BOOL | OBJECT - Returned type will be same as the 'if.expression' and 'else.expression' type.", "ifThenElse"};
    public static final String[] FUNC_STDEV = {"stdDev(arg)", "stDev", "\n" +
            "stdDev\n" +
            "Returns the calculated standard deviation for all the events.\n" +
            "\n" +
            "Parameters - \n" +
            "arg - INT | LONG | DOUBLE | FLOAT - The value that should be used to calculate the standard deviation.", ""};

    public static final String[] FUNC_COALESCE = {"coalesce(args)", "coalesce", "coalesce\n" +
            "Returns the value of the first input parameter that is not null, and all input parameters have to be on the same type.\n" +
            "\n" +
            "Parameters - \n" +
            "args - INT | LONG | DOUBLE | FLOAT | STRING | BOOL | OBJECT - This function accepts one or more parameters. They can belong to any one of the available types. All the specified parameters should be of the same type.\n" +
            "Return Attributes - \n" +
            "Attribute 1 - INT | LONG | DOUBLE | FLOAT | STRING | BOOL | OBJECT - This will be the same as the type of the first input parameter.", "coalesce"};

    public static final String[] FUNC_MINFOREVER = {"minForever()", "minForever", "\n" +
            "minForever\n" +
            "This is the attribute aggregator to store the minimum value for a given attribute throughout the lifetime of the query regardless of any windows in-front.\n" +
            "\n" +
            "Parameters - \n" +
            "arg - INT | LONG | DOUBLE | FLOAT - The value that needs to be compared to find the minimum value.", "minForever"};

    /**default keywords.*/
    public static final String[] KW_DEFINE = {"define", "define", "keyword", "define"};
    public static final String[] KW_STREAM =  {"stream", "stream", "keyword", "stream"};
    public static final String[] KW_SELECT = {"select", "select", "keyword", "select"};
    public static final String[] KW_INSERT = {"insert", "insert", "keyword", "insert"};
    public static final String[] KW_DELETE = {"delete", "delete", "keyword", "delete"};
    public static final String[] KW_UPDATE = {"update", "update", "keyword", "update"};
    public static final String[] KW_RETURN = {"return", "return", "keyword", "return"};
    public static final String[] KW_OUTPUT = {"output", "output", "keyword", "output"};
    public static final String[] KW_FROM = {"from", "from", "keyword", "from"};
    public static final String[] KW_NAME =  {"name", "name", "keyword", "name"};
    public static final String[] KW_APP = {"app", "app", "keyword", "app"};

    /**atribute type keywords.*/
    public static final String[] KW_INT = {"int", "int", "attribute type:int", "int"};
    public static final String[] KW_LONG = { "long", "long", "attribute type:long", "long"};
    public static final String[] KW_DOUBLE = {"double", "double", "attribute type:double", "double"};
    public static final String[] KW_FLOAT = {"float", "float", "attribute type:float", "float"};
    public static final String[] KW_STRING = {"string", "string", "attribute type:string", "string"};
    public static final String[] KW_BOOL = {"bool", "bool", "attribute type:bool", "bool"};
    public static final String[] KW_OBJECT = {"object", "object", "attribute type:object", "object"};


    /**by context getters.*/
    public static List<String[]> getQueryContextKWs() {
        List<String[]> kws = new ArrayList<>();
        kws.add(KW_SELECT);
        kws.add(KW_INSERT);
        kws.add(KW_OUTPUT);
        kws.add(KW_RETURN);
        kws.add(KW_DELETE);
        kws.add(KW_UPDATE);
        return kws;
    }
    public static  List<String[]> getAttributeTypeContextKWs() {
        List<String[]> kws = new ArrayList<>();
        kws.add(KW_STRING);
        kws.add(KW_INT);
        kws.add(KW_FLOAT);
        kws.add(KW_DOUBLE);
        kws.add(KW_LONG);
        kws.add(KW_BOOL);
        kws.add(KW_OBJECT);
        return kws;
    }
    public static  List<String[]> getAttributeRefFunctions() {
        List<String[]> kws = new ArrayList<>();
        kws.add(FUNC_AVG);
        kws.add(FUNC_COALESCE);
        kws.add(FUNC_IF_THEN_ELSE);
        kws.add(FUNC_COUNT);
        kws.add(FUNC_MAX);
        kws.add(FUNC_MAXIMUM);
        kws.add(FUNC_MAXFOREVER);
        kws.add(FUNC_MINFOREVER);
        kws.add(FUNC_MINIMUM);
        kws.add(FUNC_STDEV);
        kws.add(FUNC_SUM);
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

