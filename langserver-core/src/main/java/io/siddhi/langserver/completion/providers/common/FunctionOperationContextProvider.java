package io.siddhi.langserver.completion.providers.common;

import io.siddhi.langserver.LSCompletionContext;
import io.siddhi.langserver.utils.SnippetBlockUtil;
import io.siddhi.langserver.completion.providers.CompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides completions for FunctionOperationContext.
 * {@link io.siddhi.query.compiler.SiddhiQLParser.Function_operationContext}.
 */
public class FunctionOperationContextProvider extends CompletionProvider {

    public FunctionOperationContextProvider() {

        this.scopes = new ArrayList<>();
        this.scopes.add(SiddhiQLParser.Definition_windowContext.class.getName());
        this.scopes.add(SiddhiQLParser.Definition_aggregationContext.class.getName());
        this.scopes.add(SiddhiQLParser.QueryContext.class.getName());
        this.scopes.add(SiddhiQLParser.Query_outputContext.class.getName());
        this.providerName = SiddhiQLParser.Function_operationContext.class.getName();
        //todo: add super()
    }

    @Override
    public List<CompletionItem> getCompletions() {

        List<Object[]> suggestions = new ArrayList<>();
        ParserRuleContext scopeContext = findScope();
        if (scopeContext != null) {
            if (scopeContext instanceof SiddhiQLParser.Definition_aggregationContext) {
                suggestions.addAll(SnippetBlockUtil.getAggregatorFunctions());
                return generateCompletionList(suggestions);
            } else if (scopeContext instanceof SiddhiQLParser.QueryContext) {
                suggestions.addAll(SnippetBlockUtil.getFunctions());
                return generateCompletionList(suggestions);
            }
            ParserRuleContext functionOperationContext =
                    (ParserRuleContext) LSCompletionContext.INSTANCE.getParseTreeMap().get(providerName);
            if (functionOperationContext != null) {
                List<ParseTree> contexts = LSCompletionContext.INSTANCE.getParseTreeMapVisitor()
                        .findFromImmediateSuccessors(functionOperationContext, SiddhiQLParser.Function_idContext.class);
                if (contexts.size() == 1) {
                    CompletionProvider attributeListContextProvider = LSCompletionContext.INSTANCE
                            .getProvider(SiddhiQLParser.Attribute_listContext.class.getName());
                    return attributeListContextProvider.getCompletions();
                } else if (contexts.size() == 0) {
                    suggestions.addAll(SnippetBlockUtil.getFunctions());
                }
            }
        }
        return generateCompletionList(suggestions);
    }

}
