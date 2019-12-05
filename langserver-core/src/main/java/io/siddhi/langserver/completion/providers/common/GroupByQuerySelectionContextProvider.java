package io.siddhi.langserver.completion.providers.common;

import io.siddhi.langserver.LSCompletionContext;
import io.siddhi.langserver.utils.SnippetBlockUtil;
import io.siddhi.langserver.completion.providers.CompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;

public class GroupByQuerySelectionContextProvider extends CompletionProvider {

    public GroupByQuerySelectionContextProvider() {

        this.providerName = SiddhiQLParser.Group_by_query_selectionContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {

        List<Object[]> suggestions = new ArrayList<>();
        //todo there should be select and an attribute in-order to provide group by
        //todo: not sure whether current context is changed in traverseUpwardmethod.
        ParserRuleContext currentContext = LSCompletionContext.INSTANCE.getCurrentContext();
        ParseTree firstChild = currentContext.getChild(0);
        if (firstChild instanceof TerminalNodeImpl) {
            if (firstChild.getText().equalsIgnoreCase("select")) {
                List<CompletionItem> completions;
                completions = LSCompletionContext.INSTANCE.getProvider(
                        SiddhiQLParser.Output_attributeContext.class.getName())
                        .getCompletions();
                suggestions = new ArrayList<>();
                suggestions.add(SnippetBlockUtil.KEYWORD_GROUP_BY);
                completions.addAll(generateCompletionList(suggestions));
                return completions;
            } else {
                suggestions = new ArrayList<>();
                suggestions.add(SnippetBlockUtil.KEYWORD_SELECT);
                return generateCompletionList(suggestions);
            }
        } else {
            suggestions.add(SnippetBlockUtil.KEYWORD_SELECT);
        }
        return generateCompletionList(suggestions);
    }
}
