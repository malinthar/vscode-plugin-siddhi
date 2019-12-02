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

public class QuerySectionContextProvider extends LSCompletionProvider {

    public QuerySectionContextProvider() {

        this.attachmentContext = SiddhiQLParser.Query_sectionContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {
        //todo: contexts that can come under query section context
        // output_attribute_context,group_by, having,order_by,limit,offset.
        List<CompletionItem> completions;
        List<Object[]> suggestions;
        completions = LSOperationContext.FACTORY.getProvider(
                SiddhiQLParser.Output_attributeContext.class.getName())
                .getCompletions();
        suggestions = new ArrayList<>();
        ParserRuleContext querySectionContext = (ParserRuleContext) LSOperationContext.INSTANCE.getContextTree().get(
                SiddhiQLParser.Query_sectionContext.class.getName());
        if (querySectionContext != null) {
            List<ParseTree> outputAttributeContexts = ContextTreeVisitor.findFromChildren(querySectionContext,
                    SiddhiQLParser.Output_attributeContext.class);
            if (outputAttributeContexts.size() > 0) {
                suggestions.addAll(SnippetBlock.QUERY_SECTION_KEYWORDS);
            }
        } else {
            List<ParseTree> querySectionContextList =
                    ContextTreeVisitor.INSTANCE.findFromChildren(
                            (ParserRuleContext) LSOperationContext.INSTANCE.getCurrentContext().parent,
                            SiddhiQLParser.Query_sectionContext.class);
            if (querySectionContextList.size() == 1) {
                List<ParseTree> outputAttributeContexts =
                        ContextTreeVisitor.findFromChildren((ParserRuleContext) querySectionContextList.get(0),
                                SiddhiQLParser.Output_attributeContext.class);
                if (outputAttributeContexts.size() > 0) {
                    suggestions.addAll(SnippetBlock.QUERY_SECTION_KEYWORDS);
                }
            }
        }
        completions.addAll(generateCompletionList(suggestions));
        return completions;
    }
}
