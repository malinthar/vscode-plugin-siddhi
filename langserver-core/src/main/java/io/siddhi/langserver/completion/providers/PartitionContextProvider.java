package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.LSOperationContext;
import io.siddhi.langserver.completion.providers.snippet.SnippetBlock;
import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.eclipse.lsp4j.CompletionItem;

import java.util.Arrays;
import java.util.List;

public class PartitionContextProvider extends LSCompletionProvider {

    public PartitionContextProvider() {
        this.attachmentContext = SiddhiQLParser.PartitionContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {
        //todo:using current context instead of getConte
        ParserRuleContext partitionContext = (ParserRuleContext) LSOperationContext.INSTANCE.getContextTree().get(
                SiddhiQLParser.PartitionContext.class.getName());
        int childCount = partitionContext.getChildCount();
        List<ParseTree> children = partitionContext.children;
        if (childCount > 1 && children.get(childCount - 1) instanceof TerminalNodeImpl) {
            if (children.get(childCount - 1).getText().equalsIgnoreCase("with")) {
                return LSOperationContext.INSTANCE.FACTORY
                        .getProvider(SiddhiQLParser.Partition_with_streamContext.class.getName()).getCompletions();
            } else if(children.get(childCount-2) instanceof SiddhiQLParser.Partition_with_streamContext) {
                return generateCompletionList(Arrays.asList(SnippetBlock.PARTITION_BLOCK_SNIPPET,
                        SnippetBlock.KEYWORD_BEGIN));
            }
            else if (children.get(childCount-1) instanceof TerminalNodeImpl && children.get(childCount-1).getText().equalsIgnoreCase("begin")){
                List<Object[]> suggestions = Arrays.asList(
                        SnippetBlock.KEYWORD_FROM,
                        SnippetBlock.QUERY_DEFINITION,
                        SnippetBlock.QUERY_FILTER,
                        SnippetBlock.QUERY_JOIN,
                        SnippetBlock.QUERY_PATTERN,
                        SnippetBlock.QUERY_TABLE_JOIN,
                        SnippetBlock.QUERY_WINDOW,
                        SnippetBlock.QUERY_WINDOW_FILTER);
                return generateCompletionList(suggestions);
            }
        }
        return generateCompletionList(null);

    }
}
