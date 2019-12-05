package io.siddhi.langserver.completion.providers.executionelement.query;

import io.siddhi.langserver.LSCompletionContext;
import io.siddhi.langserver.utils.SnippetBlockUtil;
import io.siddhi.langserver.completion.providers.CompletionProvider;
import io.siddhi.langserver.completion.ParseTreeMapVisitor;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Provide completions for QuerySectionContext {@link io.siddhi.query.compiler.SiddhiQLParser.Query_sectionContext}.
 */
public class QuerySectionContextProvider extends CompletionProvider {

    public QuerySectionContextProvider() {
        this.providerName = SiddhiQLParser.Query_sectionContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {
        //todo: contexts that can come under query section context
        // output_attribute_context,group_by, having,order_by,limit,offset.
        List<CompletionItem> completions;
        List<Object[]> suggestions;
        ParseTreeMapVisitor parseTreeMapVisitor = LSCompletionContext.INSTANCE.getParseTreeMapVisitor();
        completions = LSCompletionContext.INSTANCE.getProvider(
                SiddhiQLParser.Output_attributeContext.class.getName())
                .getCompletions();
        suggestions = new ArrayList<>();
        ParserRuleContext querySectionContext = (ParserRuleContext) LSCompletionContext.INSTANCE.getParseTreeMap().get(
                SiddhiQLParser.Query_sectionContext.class.getName());
        if (querySectionContext != null) {
            List<ParseTree> outputAttributeContexts =
                    parseTreeMapVisitor.findFromImmediateSuccessors(querySectionContext,
                    SiddhiQLParser.Output_attributeContext.class);
            if (outputAttributeContexts.size() > 0) {
                suggestions.addAll(SnippetBlockUtil.QUERY_SECTION_KEYWORDS);
            }
        } else {
            List<ParseTree> querySectionContextList =
                    parseTreeMapVisitor.findFromImmediateSuccessors(
                            (ParserRuleContext) LSCompletionContext.INSTANCE.getCurrentContext().parent,
                            SiddhiQLParser.Query_sectionContext.class);
            if (querySectionContextList.size() == 1) {
                List<ParseTree> outputAttributeContexts =
                        parseTreeMapVisitor
                                .findFromImmediateSuccessors((ParserRuleContext) querySectionContextList.get(0),
                                SiddhiQLParser.Output_attributeContext.class);
                if (outputAttributeContexts.size() > 0) {
                    suggestions.addAll(SnippetBlockUtil.QUERY_SECTION_KEYWORDS);
                }
            }
        }
        completions.addAll(generateCompletionList(suggestions));
        return completions;
    }
}
