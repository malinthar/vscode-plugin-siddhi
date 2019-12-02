package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.completion.providers.snippet.SnippetBlock;
import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;

public class AttributeNameContextProvider extends LSCompletionProvider {

    public AttributeNameContextProvider() {

        this.attachmentContext = SiddhiQLParser.Attribute_nameContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {

        ParserRuleContext parent = visitParent();
        if (parent instanceof SiddhiQLParser.Attribute_referenceContext) {
            return generateCompletionList(null);

        }
        else if(parent instanceof SiddhiQLParser.Output_attributeContext){
            List<Object[]> suggestions = new ArrayList<>();
            suggestions.add(SnippetBlock.ALIAS_SNIPPET);
            return generateCompletionList(suggestions);
        }
        else {
            //todo: change this initialization
            List<Object[]> suggestions = new ArrayList<>();
            suggestions.add(SnippetBlock.ATTRIBUTE_NAME_TYPE_SNIPPET);
            return generateCompletionList(suggestions);
        }

    }

}

//todo store without calling Arrays.aslist everytime. each time an array object is created. It is not healthy