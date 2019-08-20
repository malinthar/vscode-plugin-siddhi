package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.LSContext;
import io.siddhi.langserver.completion.snippet.SinppetGenerator;
import io.siddhi.langserver.completion.snippet.Snippet;
import io.siddhi.langserver.completion.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.IntervalSet;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionItemKind;

import java.util.ArrayList;
import java.util.List;

public class AttributeReferenceContextProvider extends LSCompletionProvider {
    public AttributeReferenceContextProvider(){
        this.attachmentPoints.add(SiddhiQLParser.Attribute_referenceContext.class);
    }
    public List<CompletionItem> getCompletions(LSContext lsContext){
        SinppetGenerator sinppetGenerator=new SinppetGenerator();
        return (ArrayList)sinppetGenerator.getSnippets((SiddhiQLParser.Attribute_referenceContext) lsContext.getCurrentContext(),lsContext);
    }
}

