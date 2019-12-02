package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.LSOperationContext;
import io.siddhi.langserver.completion.providers.snippet.SnippetBlock;
import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.langserver.completion.util.ContextTreeVisitor;
import io.siddhi.query.compiler.SiddhiQLParser;
import io.siddhi.query.compiler.langserver.LSErrorNode;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class QueryOutputContextProvider extends LSCompletionProvider {

    public QueryOutputContextProvider() {

        this.attachmentContext = SiddhiQLParser.Query_outputContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {

        List<Object[]> suggestions = new ArrayList<>();
        ParserRuleContext queryContext =
                (ParserRuleContext) LSOperationContext.INSTANCE.getContextTree()
                        .get(SiddhiQLParser.QueryContext.class.getName());
        ParserRuleContext queryInputContext = (ParserRuleContext) ContextTreeVisitor.findOneFromChildren(queryContext,
                SiddhiQLParser.Query_inputContext.class);
        ParserRuleContext querySectionContext = (ParserRuleContext) ContextTreeVisitor.findOneFromChildren(queryContext,
                SiddhiQLParser.Query_sectionContext.class);
        ParserRuleContext queryOutputContext = (ParserRuleContext) ContextTreeVisitor.findOneFromChildren(queryContext,
                SiddhiQLParser.Query_outputContext.class);

        if (queryOutputContext != null) {
            if (queryOutputContext.children != null) {
                List<ParseTree> children = queryOutputContext.children;
                if (children.size() == 1 && children.get(0).getText().equalsIgnoreCase("insert")) {
                    List<CompletionItem> completionItems = new ArrayList<>();
                    suggestions.add(SnippetBlock.KEYWORD_INTO);
                    completionItems.addAll(generateCompletionList(suggestions));
                    completionItems.addAll(LSOperationContext.INSTANCE.FACTORY
                            .getProvider(SiddhiQLParser.Output_event_typeContext.class.getName()).getCompletions());
                    return completionItems;
                } else if (children.size() > 1 && children.get(0).getText().equalsIgnoreCase("insert") &&
                        !queryOutputContext.getText().contains("into")) {
                    suggestions.add(SnippetBlock.KEYWORD_INTO);
                } else if (children.size() > 1 && children.get(0).getText().equalsIgnoreCase("delete")) {
                    if (!queryOutputContext.getText().contains("for")) {
                        suggestions.add(SnippetBlock.KEYWORD_FOR);
                    }
                    suggestions.add(SnippetBlock.KEYWORD_ON);
                    return generateCompletionList(suggestions);
                } else if (queryOutputContext.getText().replace(" ", "").toLowerCase().contains(("update or " +
                        "insert into").replace(
                        " ", ""))) {
                    if (!queryOutputContext.getText().contains("set")) {
                        suggestions.add(SnippetBlock.KEYWORD_SET);
                        if (!queryOutputContext.getText().contains("for")) {
                            suggestions.add(SnippetBlock.KEYWORD_FOR);
                        }
                    }
                    suggestions.add(SnippetBlock.KEYWORD_ON);
                    return generateCompletionList(suggestions);
                }
            } else {
                if (LSOperationContext.INSTANCE.getContextTree()
                        .get(LSErrorNode.class.getName()) instanceof LSErrorNode) {
                    LSErrorNode errorNode =
                            (LSErrorNode) LSOperationContext.INSTANCE.getContextTree().get(LSErrorNode.class.getName());
                    if (errorNode.getSymbol().equalsIgnoreCase("update") ||
                            errorNode.getPreviousSymbol().equalsIgnoreCase("update")) {
                        List<String> sourceNames = new ArrayList<>();
                        List<CompletionItem> completionItems = new ArrayList<>();
                        suggestions.add(SnippetBlock.KEYWORD_UPDATE_OR_INSERT_INTO);
                        sourceNames.addAll(((SourceContextProvider) LSOperationContext.INSTANCE.FACTORY
                                .getProvider(SiddhiQLParser.SourceContext.class.getName())).getDefinedSources());
                        completionItems
                                .addAll(generateCompletionList(SnippetBlock.generateSourceReferences(sourceNames)));
                        completionItems.addAll(generateCompletionList(suggestions));
                        return completionItems;
                    }
                }
                if (querySectionContext != null) {
                    List<CompletionItem> completionItems = new ArrayList<>();
                    suggestions.addAll(Arrays.asList(SnippetBlock.KEYWORD_INSERT_INTO, SnippetBlock.KEYWORD_DELETE,
                            SnippetBlock.KEYWORD_UPDATE,
                            SnippetBlock.KEYWORD_UPDATE_OR_INSERT_INTO, SnippetBlock.KEYWORD_INSERT,
                            SnippetBlock.KEYWORD_RETURN));
                    completionItems.addAll(generateCompletionList(suggestions));
                    completionItems.addAll(LSOperationContext.INSTANCE.FACTORY
                            .getProvider(SiddhiQLParser.Query_sectionContext.class.getName()).getCompletions());
                    return completionItems;
                } else if (queryInputContext != null) {
                    List<ParseTree> children = queryInputContext.children;
                    int childCount = queryInputContext.getChildCount();
                    if (queryInputContext.getChild(childCount - 1) instanceof SiddhiQLParser.Standard_streamContext) {
                        List<Object> sourceNames = new ArrayList<>();
                        sourceNames.addAll(((SourceContextProvider) LSOperationContext.INSTANCE.FACTORY
                                .getProvider(SiddhiQLParser.SourceContext.class.getName())).getDefinedSources());
                        List<Object> queryInputContextSources =
                                ContextTreeVisitor.INSTANCE.findRuleContexts((ParserRuleContext) queryInputContext,
                                        SiddhiQLParser.SourceContext.class);
                        AtomicReference<Boolean> status = new AtomicReference<>(true);
                        queryInputContextSources.forEach(queryInputContextSource -> {
                            if (!sourceNames.contains(((ParserRuleContext) queryInputContextSource).getText())) {
                                status.set(false);
                            }
                        });
                        if (!status.get()) {
                            return LSOperationContext.INSTANCE.FACTORY
                                    .getProvider(SiddhiQLParser.Query_inputContext.class.getName()).getCompletions();
                        } else {
                            suggestions.addAll(Arrays
                                    .asList(SnippetBlock.KEYWORD_JOIN, SnippetBlock.CLAUSE_LEFT_OUTER_JOIN,
                                            SnippetBlock.CLAUSE_RIGHT_OUTER_JOIN, SnippetBlock.CLAUSE_FULL_OUTER_JOIN,
                                            SnippetBlock.KEYWORD_FULL, SnippetBlock.KEYWORD_LEFT,
                                            SnippetBlock.KEYWORD_RIGHT, SnippetBlock.KEYWORD_SELECT,
                                            SnippetBlock.KEYWORD_UNIDIRECTIONAL));
                            return generateCompletionList(suggestions);
                        }

                    } else if (queryInputContext
                            .getChild(childCount - 1) instanceof SiddhiQLParser.Join_streamContext) {
                        suggestions.add(SnippetBlock.KEYWORD_SELECT);
                        return generateCompletionList(suggestions);
                    }
                }
            }
        }

        return generateCompletionList(suggestions);
    }
}
//todo:reduce complexity.