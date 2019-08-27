package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.LSContext;
import io.siddhi.langserver.completion.snippet.SinppetProvider;
import io.siddhi.langserver.completion.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;
import java.util.ArrayList;
import java.util.List;

public class AnnotationElementContextProvider extends LSCompletionProvider {
    public AnnotationElementContextProvider(){
        this.attachmentPoints.add(SiddhiQLParser.Annotation_elementContext.class);
    }
    public List<CompletionItem> getCompletions(LSContext lsContext){
            SinppetProvider sinppetProvider=new SinppetProvider();
            return (ArrayList)sinppetProvider.getSnippets((SiddhiQLParser.Annotation_elementContext) lsContext.getCurrentContext(),lsContext);
        }
}
