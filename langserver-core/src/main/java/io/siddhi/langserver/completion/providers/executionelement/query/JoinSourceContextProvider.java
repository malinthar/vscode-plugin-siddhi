package io.siddhi.langserver.completion.providers.executionelement.query;

import io.siddhi.langserver.LSCompletionContext;
import io.siddhi.langserver.utils.SnippetBlockUtil;
import io.siddhi.langserver.completion.providers.CompletionProvider;
import io.siddhi.langserver.completion.ParseTreeMapVisitor;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;

public class JoinSourceContextProvider extends CompletionProvider {

    public JoinSourceContextProvider() {

        this.providerName = SiddhiQLParser.Join_sourceContext.class.getName();
    }

    //todo:don't have to produce terminals can straightly get text out of source
    @Override
    public List<CompletionItem> getCompletions() {
        ParseTreeMapVisitor parseTreeMapVisitor = LSCompletionContext.INSTANCE.getParseTreeMapVisitor();
        ParserRuleContext joinStreamContext = getParent();
        List<ParseTree> joinSources = parseTreeMapVisitor.findSuccessorContexts(joinStreamContext,
                SiddhiQLParser.Stream_idContext.class);
        List<Object> sourceNameTerminals = new ArrayList<>();
        List<String> sourceNames = new ArrayList<>();
        for (ParseTree joinSource : joinSources) {
            sourceNameTerminals.addAll(parseTreeMapVisitor.findSuccessorContexts((ParserRuleContext) joinSource,
                    TerminalNodeImpl.class));

        }
        for (Object sourceNameTerminal : sourceNameTerminals) {
            sourceNames.add(((TerminalNodeImpl) sourceNameTerminal).getText());
        }
        List<ParseTree> streamIdContexts = new ArrayList<>();
        List<ParseTree> sourceProviderContexts =
                parseTreeMapVisitor
                        .findSuccessorContexts((ParserRuleContext) LSCompletionContext.INSTANCE.getParseTreeMap().get(
                                SiddhiQLParser.Siddhi_appContext.class.getName()),
                                SiddhiQLParser.Definition_streamContext.class);

        sourceProviderContexts.addAll(parseTreeMapVisitor
                .findSuccessorContexts((ParserRuleContext) LSCompletionContext.INSTANCE.getParseTreeMap().get(
                        SiddhiQLParser.Siddhi_appContext.class.getName()),
                        SiddhiQLParser.Definition_tableContext.class));

        sourceProviderContexts.addAll(parseTreeMapVisitor
                .findSuccessorContexts((ParserRuleContext) LSCompletionContext.INSTANCE.getParseTreeMap().get(
                        SiddhiQLParser.Siddhi_appContext.class.getName()),
                        SiddhiQLParser.Definition_windowContext.class));

        List<ParseTree> definitionAggregationContexts = parseTreeMapVisitor
                        .findSuccessorContexts((ParserRuleContext) LSCompletionContext.INSTANCE.getParseTreeMap().get(
                                SiddhiQLParser.Siddhi_appContext.class.getName()),
                                SiddhiQLParser.Definition_aggregationContext.class);

        for (Object sourceProviderContext : sourceProviderContexts) {
            streamIdContexts.addAll(parseTreeMapVisitor
                    .findSuccessorContexts((ParserRuleContext) sourceProviderContext,
                            SiddhiQLParser.Stream_idContext.class));
        }
        for (Object definitionAggregationContext : definitionAggregationContexts) {
            streamIdContexts.addAll(parseTreeMapVisitor
                    .findSuccessorContexts((ParserRuleContext) definitionAggregationContext,
                            SiddhiQLParser.Aggregation_nameContext.class));
        }
        List<String> sources = new ArrayList<>();
        for (Object streamIdContext : streamIdContexts) {
            String source = ((ParserRuleContext) streamIdContext).getText();
            if (!sourceNames.contains(source)) {
                sources.add(source);
            }
        }
        List<Object[]> suggestions = SnippetBlockUtil.generateSourceReferences(sources);
        return generateCompletionList(suggestions);
    }
}
