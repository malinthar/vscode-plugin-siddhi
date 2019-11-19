package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.completion.providers.snippet.SnippetBlock;
import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.Arrays;
import java.util.List;
import javax.net.ssl.SNIHostName;

public class SiddhiAppContextProvider extends LSCompletionProvider {

    public SiddhiAppContextProvider() {

        this.attachmentContext = SiddhiQLParser.Siddhi_appContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {

        //todo: store what can come under siddhi_app_ccontext in adifferent file and filter out completion items
        // based on error node.

        List<Object[]> suggestions = Arrays.asList(
                SnippetBlock.APP_DESCRIPTION_ANNOTATION_DEFINITION,
                SnippetBlock.APP_NAME_ANNOTATION_DEFINITION,
                SnippetBlock.STREAM_DEFINITION,
                SnippetBlock.PARTITION_DEFINITION,
                SnippetBlock.TABLE_DEFINITION,
                SnippetBlock.TRIGGER_DEFINITION,
                SnippetBlock.WINDOW_DEFINITION,
                SnippetBlock.AGGREGATION_DEFINITION,
                SnippetBlock.ANNOTATION_ASYNC_DEFINITION,
                SnippetBlock.ANNOTATION_INDEX_DEFINITION,
                SnippetBlock.ANNOTATION_PRIMARY_KEY_DEFINITION,
                SnippetBlock.ANNOTATION_QUERYINFO_DEFINITION,
                SnippetBlock.APP_STATISTICS_ANNOTATION_DEFINITION,
                SnippetBlock.QUERY_DEFINITION,
                SnippetBlock.QUERY_FILTER,
                SnippetBlock.QUERY_JOIN,
                SnippetBlock.QUERY_PATTERN,
                SnippetBlock.QUERY_TABLE_JOIN,
                SnippetBlock.QUERY_WINDOW,
                SnippetBlock.QUERY_WINDOW_FILTER,
                SnippetBlock.SINK_DEFINITION,
                SnippetBlock.SOURCE_DEFINITION,
                SnippetBlock.FUNCTION_DEFINITION,
                SnippetBlock.KEYWORD_FROM,
                SnippetBlock.KEYWORD_DEFINE,
                SnippetBlock.KEYWORD_STREAM,
                SnippetBlock.KEYWORD_AGGREGATION,
                SnippetBlock.KEYWORD_FUNCTION,
                SnippetBlock.KEYWORD_PARTITION,
                SnippetBlock.KEYWORD_TABLE,
                SnippetBlock.KEYWORD_WINDOW,
                SnippetBlock.KEYWORD_TRIGGER);
        return generateCompletionList(suggestions);
    }

}
