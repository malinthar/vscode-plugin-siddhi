package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.LSOperationContext;
import io.siddhi.langserver.completion.providers.snippet.SnippetBlock;
import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.langserver.completion.util.ContextTreeVisitor;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;

public class FunctionOperationContextProvider extends LSCompletionProvider {

    public FunctionOperationContextProvider() {
        this.scopes = new ArrayList<>();
        this.scopes.add(SiddhiQLParser.Definition_windowContext.class.getName());
        this.scopes.add(SiddhiQLParser.Definition_aggregationContext.class.getName());
        this.scopes.add(SiddhiQLParser.QueryContext.class.getName());
        this.scopes.add(SiddhiQLParser.Query_outputContext.class.getName());
        this.attachmentContext = SiddhiQLParser.Function_operationContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {
        ParserRuleContext scopeContext = findScope();
        if(scopeContext instanceof SiddhiQLParser.Definition_aggregationContext){
            List<Object[]> suggestions = new ArrayList<>();
             suggestions.addAll(SnippetBlock.getAggregatorFunctions());
             return  generateCompletionList(suggestions);
        } else if(scopeContext instanceof SiddhiQLParser.QueryContext){
            List<Object[]> suggestions = new ArrayList<>();
            suggestions.addAll(SnippetBlock.getFunctions());
            return  generateCompletionList(suggestions);
        }
        ParserRuleContext context =
                (ParserRuleContext) LSOperationContext.INSTANCE.getContextTree().get(attachmentContext);
        List<ParseTree> contexts = (ArrayList) ContextTreeVisitor.findFromChildren(context,
                SiddhiQLParser.Function_idContext.class);
        if(contexts.size() == 1){
                LSCompletionProvider child =
                        LSOperationContext.INSTANCE.FACTORY.getProvider(SiddhiQLParser.Attribute_listContext.class.getName());
                return child.getCompletions();
        }
        else if(contexts.size() == 0){
            List<Object[]> suggestions = SnippetBlock.getFunctions();
            return generateCompletionList(suggestions);
        }
        else{
            return null;
        }

    }

    @Override
    public List<CompletionItem> getDefaultCompletions(){
        List<Object[]> suggestions = SnippetBlock.getFunctions();
        return generateCompletionList(suggestions);
    }

}
