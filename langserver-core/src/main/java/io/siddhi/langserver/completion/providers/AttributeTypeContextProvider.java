package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.LSContext;

import io.siddhi.langserver.completion.snippet.SinppetProvider;
import io.siddhi.langserver.completion.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.misc.IntervalSet;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.checkerframework.checker.units.qual.A;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionItemKind;

import java.util.ArrayList;
import java.util.List;



public class AttributeTypeContextProvider extends LSCompletionProvider {
    public AttributeTypeContextProvider(){

        this.attachmentPoints.add(SiddhiQLParser.Attribute_typeContext.class);
    }
    public List<CompletionItem> getCompletions(LSContext lsContext){
        SinppetProvider sinppetProvider=new SinppetProvider();
        return (ArrayList)sinppetProvider.getSnippets((SiddhiQLParser.Attribute_typeContext) lsContext.getCurrentContext(),lsContext);
    }
}

