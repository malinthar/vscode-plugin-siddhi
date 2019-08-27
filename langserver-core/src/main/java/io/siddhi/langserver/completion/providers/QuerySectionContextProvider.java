package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.LSContext;
import io.siddhi.langserver.completion.snippet.SinppetProvider;
import io.siddhi.langserver.completion.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;


import java.util.ArrayList;
import java.util.List;

public class QuerySectionContextProvider extends LSCompletionProvider {
    public QuerySectionContextProvider(){
        this.attachmentPoints.add(SiddhiQLParser.Query_sectionContext.class);
    }
    public List<CompletionItem> getCompletions(LSContext lsContext){
        SinppetProvider sinppetProvider=new SinppetProvider();
        return (ArrayList)sinppetProvider.getSnippets((SiddhiQLParser.Query_sectionContext) lsContext.getCurrentContext(),lsContext);
    }
}
