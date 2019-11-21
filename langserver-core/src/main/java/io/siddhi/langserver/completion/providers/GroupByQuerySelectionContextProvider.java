package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.LSOperationContext;
import io.siddhi.langserver.completion.providers.snippet.SnippetBlock;
import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.langserver.completion.util.ContextTreeVisitor;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GroupByQuerySelectionContextProvider extends LSCompletionProvider {

    public GroupByQuerySelectionContextProvider() {

        this.attachmentContext = SiddhiQLParser.Group_by_query_selectionContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {

        List<Object[]> suggestions = new ArrayList<>();
//        //todo there should be select and an attribute in-order to provide group by
//        //todo: not sure whether current context is changed in traverseUpwardmethod.
//        List<ParseTree> terminals =
//                ContextTreeVisitor.findFromChildren(LSOperationContext.INSTANCE.getCurrentContext(),
//                        TerminalNodeImpl.class);
//        for (ParseTree terminal : terminals) {
//            if (terminal.getText().equalsIgnoreCase("select")) {
//                return LSOperationContext.FACTORY.getProvider(AttributeReferenceContextProvider.class.getName())
//                        .getCompletions();
//            }
//        }
        ParserRuleContext currentContext = LSOperationContext.INSTANCE.getCurrentContext();
        ParseTree firstChild = currentContext.getChild(0);
        if (firstChild instanceof TerminalNodeImpl) {
            if (firstChild.getText().equalsIgnoreCase("select")) {
                List<CompletionItem> completions;
                completions = LSOperationContext.FACTORY.getProvider(
                        SiddhiQLParser.Output_attributeContext.class.getName())
                        .getCompletions();
                suggestions = new ArrayList<>();
                suggestions.add(SnippetBlock.KEYWORD_GROUP_BY);
                completions.addAll(generateCompletionList(suggestions));
                return completions;
            } else {
                suggestions = new ArrayList<>();
                suggestions.add(SnippetBlock.KEYWORD_SELECT);
                return generateCompletionList(suggestions);
            }
        }
        else {
            suggestions.add(SnippetBlock.KEYWORD_SELECT);
        }
        return generateCompletionList(suggestions);
    }
}

//todo: adding streamName infrontOfAttribute.
