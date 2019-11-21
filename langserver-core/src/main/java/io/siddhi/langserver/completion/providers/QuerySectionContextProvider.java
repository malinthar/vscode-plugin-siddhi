package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.LSOperationContext;
import io.siddhi.langserver.completion.providers.snippet.SnippetBlock;
import io.siddhi.langserver.completion.providers.snippet.SnippetProvider;
import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;

public class QuerySectionContextProvider extends LSCompletionProvider {

    public QuerySectionContextProvider() {

        this.attachmentContext = SiddhiQLParser.Query_sectionContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {
        //todo: contexts that can come under query section context
        // output_attribute_context,group_by, having,order_by,limit,offset.
        return generateCompletionList(SnippetBlock.QUERY_SECTION_KEYWORDS);
    }
}
