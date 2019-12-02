package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.LSOperationContext;
import io.siddhi.langserver.completion.providers.snippet.SnippetBlock;
import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.langserver.completion.util.ContextTreeVisitor;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PartitionWithStreamContextProvider extends LSCompletionProvider {

    public PartitionWithStreamContextProvider() {

        this.attachmentContext = SiddhiQLParser.Partition_with_streamContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {
        //attribute of stream
        //condition_ranges of stream_id
        //condition_range: expression as string_value
        ParserRuleContext siddhiAppContext =
                (ParserRuleContext) LSOperationContext.INSTANCE.getContextTree()
                        .get(SiddhiQLParser.Siddhi_appContext.class.getName());
        List<Object> streamDefinitionContexts =
                ContextTreeVisitor.INSTANCE.findRuleContexts(siddhiAppContext,
                        SiddhiQLParser.Definition_streamContext.class);
        Map<String, List<String>> map = new LinkedHashMap<>();
        for (Object streamDefinitionContext : streamDefinitionContexts) {
            List<String> attributeNames = new ArrayList<>();
            List<Object> streamIdContext =
                    ContextTreeVisitor.INSTANCE
                            .findRuleContexts((ParserRuleContext) streamDefinitionContext,
                                    SiddhiQLParser.Stream_idContext.class);
            List<Object> sourceName =
                    ContextTreeVisitor.INSTANCE.findRuleContexts((ParserRuleContext) streamIdContext.get(0),
                            TerminalNodeImpl.class);
            Object source1 = sourceName.get(0);
            String source = ((TerminalNodeImpl) source1).getText();
            List<Object> attributeNameContexts = ContextTreeVisitor.INSTANCE
                    .findRuleContexts((ParserRuleContext) streamDefinitionContext,
                            SiddhiQLParser.Attribute_nameContext.class);
            for (Object attributeNameContext : attributeNameContexts) {
                String name =
                        ((TerminalNodeImpl) (ContextTreeVisitor.INSTANCE
                                .findRuleContexts((ParserRuleContext) attributeNameContext,
                                        TerminalNodeImpl.class).get(0))).getText();
                attributeNames.add(name);
            }
            map.put(source, attributeNames);

        }
        return generateCompletionList(SnippetBlock.generatePartitionKeys(map));
    }
}