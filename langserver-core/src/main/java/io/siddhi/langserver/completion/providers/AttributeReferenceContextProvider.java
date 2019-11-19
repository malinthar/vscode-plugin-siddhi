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
import java.util.List;

public class AttributeReferenceContextProvider extends LSCompletionProvider {

    public AttributeReferenceContextProvider() {

        this.scopes = new ArrayList<>();
        this.scopes.add(SiddhiQLParser.Definition_windowContext.class.getName());
        this.scopes.add(SiddhiQLParser.Definition_aggregationContext.class.getName());
        this.scopes.add(SiddhiQLParser.QueryContext.class.getName());
        this.attachmentContext = SiddhiQLParser.Attribute_referenceContext.class.getName();
    }

    /**
     * identified scopes: DefinitionWindowContext, Aggregation Context.
     *
     * @return
     */
    @Override
    public List<CompletionItem> getCompletions() {
        //todo: provide (#|!)? attribute_name[attribute_index]?(#attribute_name([attribute_index])?.attribute_name)?
        // snippets based on attribute_name
        //todo: first find out attribute names and provide completions based on whether it refers to a list, object
        // (# what is meant by hash and what is meant by !)
        ParserRuleContext scopeContext = findScope();
        if (scopeContext == null) {
            return generateCompletionList(null);
        } else {
            if (scopeContext instanceof SiddhiQLParser.Definition_windowContext) {

                List<ParserRuleContext> providerContexts = ContextTreeVisitor.INSTANCE.findFromChildren(scopeContext,
                        SiddhiQLParser.Attribute_nameContext.class);
                //todo:improve here Object is too general
                List<Object> attributeNameTerminals = new ArrayList<>();
                for (ParserRuleContext providerContext : providerContexts) {
                    attributeNameTerminals.addAll(ContextTreeVisitor.INSTANCE
                            .findRuleContexts(providerContext, TerminalNodeImpl.class));
                }
                return generateCompletionList(SnippetBlock.generateAttributeReferences(attributeNameTerminals));

            } else if (scopeContext instanceof SiddhiQLParser.Definition_aggregationContext) {
                try {
                    List<Object> sourceTerminals = new ArrayList<>();
                    List<Object> attributeNameTerminals = new ArrayList<>();
                    List<Object> providerContexts = ContextTreeVisitor.INSTANCE.findRuleContexts(scopeContext,
                            SiddhiQLParser.SourceContext.class);
                    for (Object providerContext : providerContexts) {
                        sourceTerminals
                                .addAll(ContextTreeVisitor.INSTANCE
                                        .findRuleContexts((ParserRuleContext) providerContext,
                                                TerminalNodeImpl.class));

                    }
                    ParserRuleContext siddhiAppContext =
                            (ParserRuleContext) LSOperationContext.INSTANCE.getContextTree()
                                    .get(SiddhiQLParser.Siddhi_appContext.class.getName());
                    List<Object> streamDefinitionContexts =
                            ContextTreeVisitor.INSTANCE.findRuleContexts(siddhiAppContext,
                                    SiddhiQLParser.Definition_streamContext.class);
                    for (Object streamDefinitionContext : streamDefinitionContexts) {
                        List<Object> streamIdContext =
                                ContextTreeVisitor.INSTANCE
                                        .findRuleContexts((ParserRuleContext) streamDefinitionContext,
                                                SiddhiQLParser.Stream_idContext.class);
                        List<Object> sourceName =
                                ContextTreeVisitor.INSTANCE.findRuleContexts((ParserRuleContext) streamIdContext.get(0),
                                        TerminalNodeImpl.class);
                        Object source1 = sourceName.get(0);
                        Object source2 = sourceTerminals.get(0);
                        if (((TerminalNodeImpl) source1).getText().equals(((TerminalNodeImpl) source2).getText())) {
                            List<ParserRuleContext> attributeNameProviders =
                                    ContextTreeVisitor.findFromChildren((ParserRuleContext) streamDefinitionContext,
                                            SiddhiQLParser.Attribute_nameContext.class);
                            for (ParserRuleContext attributeNameProvider : attributeNameProviders) {
                                attributeNameTerminals.addAll(ContextTreeVisitor.INSTANCE
                                        .findRuleContexts(attributeNameProvider, TerminalNodeImpl.class));
                            }

                        }
                    }
                    List<Object[]> suggestions;
                    suggestions = SnippetBlock.generateAttributeReferences(attributeNameTerminals);
                    return generateCompletionList(suggestions);
                } catch (Throwable e) {
                    String error = "error";
                    return generateCompletionList(null);
                }
            } else if (scopeContext instanceof SiddhiQLParser.QueryContext) {
                List<Object> sourceContexts = new ArrayList<>();
                List<Object> sourceTerminals = new ArrayList<>();
                List<ParserRuleContext> providerContexts = ContextTreeVisitor.INSTANCE.findFromChildren(scopeContext,
                        SiddhiQLParser.Query_inputContext.class);
                List<Object> attributeNameTerminals = new ArrayList<>();
                for (Object providerContext : providerContexts) {
                    sourceContexts.addAll(ContextTreeVisitor.INSTANCE
                            .findRuleContexts((ParserRuleContext) providerContext,
                                    SiddhiQLParser.SourceContext.class));

                }
                for (Object sourceContext : sourceContexts) {
                    sourceTerminals.addAll(ContextTreeVisitor.INSTANCE
                            .findRuleContexts((ParserRuleContext) sourceContext,
                                    TerminalNodeImpl.class));

                }
                ParserRuleContext siddhiAppContext =
                        (ParserRuleContext) LSOperationContext.INSTANCE.getContextTree()
                                .get(SiddhiQLParser.Siddhi_appContext.class.getName());
                List<Object> streamDefinitionContexts =
                        ContextTreeVisitor.INSTANCE.findRuleContexts(siddhiAppContext,
                                SiddhiQLParser.Definition_streamContext.class);
                for (Object streamDefinitionContext : streamDefinitionContexts) {
                    List<Object> streamIdContext =
                            ContextTreeVisitor.INSTANCE
                                    .findRuleContexts((ParserRuleContext) streamDefinitionContext,
                                            SiddhiQLParser.Stream_idContext.class);
                    List<Object> sourceName =
                            ContextTreeVisitor.INSTANCE.findRuleContexts((ParserRuleContext) streamIdContext.get(0),
                                    TerminalNodeImpl.class);
                    Object source1 = sourceName.get(0);
                    Object source2 = sourceTerminals.get(0);
                    if (((TerminalNodeImpl) source1).getText().equals(((TerminalNodeImpl) source2).getText())) {
                        List<ParserRuleContext> attributeNameProviders =
                                ContextTreeVisitor.findFromChildren((ParserRuleContext) streamDefinitionContext,
                                        SiddhiQLParser.Attribute_nameContext.class);
                        for (ParserRuleContext attributeNameProvider : attributeNameProviders) {
                            attributeNameTerminals.addAll(ContextTreeVisitor.INSTANCE
                                    .findRuleContexts(attributeNameProvider, TerminalNodeImpl.class));
                        }

                    }
                }
                List<Object[]> suggestions;
                suggestions = SnippetBlock.generateAttributeReferences(attributeNameTerminals);
                return generateCompletionList(suggestions);

            } else {
                return generateCompletionList(null);
            }
        }

    }

}

