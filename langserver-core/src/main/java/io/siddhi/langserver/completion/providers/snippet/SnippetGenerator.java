package io.siddhi.langserver.completion.providers.snippet;

import io.siddhi.langserver.LSOperationContext;
import io.siddhi.langserver.completion.util.ContextTreeVisitor;
import io.siddhi.query.compiler.SiddhiQLLexer;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code SnippetGenerator} provide snippets based on context.
 */

public class SnippetGenerator {
    /**default keywords defined in the siddhi lexer.*/
    private String[] ruleNames = SiddhiQLLexer.ruleNames;
    /** context visitor to find specific tokens given the context.*/
    private ContextTreeVisitor visitor = ContextTreeVisitor.INSTANCE;
    /**default keywords suggested by error node.*/
    public Object getDefalutKeyWords(LSOperationContext lsContext) {
        String defaultKeyWordstring = "keyword";
        defaultKeyWordstring = defaultKeyWordstring.toLowerCase();
        String[] defualtKeyWords = defaultKeyWordstring.split("\\s*,\\s*");
        return defualtKeyWords;
    }
    /**
     *annotation snippets.
      */
    public Object getAnnotations(LSOperationContext lsContext) {
        List<String[]> annotations = new ArrayList<>();
        annotations.add(SnippetBlock.KW_APP);
        return annotations;
    }
    /**name context snippets.*/
    public Object getName(LSOperationContext lsContext) {
      return SnippetBlock.KW_NAME;
    }
    /**attribute type context snippets.*/
    public Object getAttributetype(LSOperationContext lsContext) {
        SnippetBlock.getBuiltInFuncions();
        return SnippetBlock.attributeTypes;
    }
    /**annotation element context snippets.*/
    public Object getAnnotationElements(LSOperationContext lsContext) {
        List<String[]> elements = new ArrayList<>();
        elements.add(SnippetBlock.KW_NAME);
        return elements;
    }
    /** attribute reference context snippets.*/
    public Object getAttributeReference(LSOperationContext lsContext) {
        List<String> refs = new ArrayList<>();
        List<ParserRuleContext> namecontexts = this.visitor.findRuleContexts((ParserRuleContext) lsContext.getContextTree().get(SiddhiQLParser.Siddhi_appContext.class.toString()), SiddhiQLParser.Attribute_nameContext.class);
        for (ParserRuleContext ctx:namecontexts) {
            if (ctx.getParent().getClass().equals(SiddhiQLParser.Attribute_referenceContext.class) || ctx.getParent().getClass().equals(SiddhiQLParser.Output_attributeContext.class)) {
                refs.add(ctx.getText());
            }
        }
        if (refs.size() != 0) {
            return refs;
        } else {
            return this.getDefalutKeyWords(lsContext);
        }
    }
    /** output attribute context snippets.*/
    public Object getOutputAttribute(LSOperationContext lsContext) {
        return this.getQuerySection(lsContext);
    }

    /** query input context snippets.*/
    public Object getQueryInput(LSOperationContext lsContext) {
        List<String> instreams = new ArrayList<>();
        List<ParserRuleContext> sourceContexts = this.visitor.findRuleContexts((ParserRuleContext) lsContext.getContextTree().get(SiddhiQLParser.Siddhi_appContext.class.toString()), SiddhiQLParser.SourceContext.class);
        for (ParserRuleContext ctx:sourceContexts) {
            instreams.add(((SiddhiQLParser.SourceContext) ctx).getText());
        }
        if (instreams.size() != 0) {
            return instreams;
        } else {
            return this.getDefalutKeyWords(lsContext);
        }

    }
    /**query section context snippets.*/
    public Object getQuerySection(LSOperationContext lsContext) {
        List<String[]> refs = new ArrayList<>();

        // todo:change lscontext getMethod; return a map instead of an array list from the ErrorStrategy

        /**retrieve builtin functions.*/
        List<String[]> builtinfunctions = new ArrayList<>();
        builtinfunctions.addAll(SnippetBlock.getAttributeRefFunctions());

        /**retrive attribute references in the app context*/
        List<ParserRuleContext> attributeContexts = this.visitor.findRuleContexts((ParserRuleContext) lsContext.getContextTree().get(SiddhiQLParser.Siddhi_appContext.class.toString()), SiddhiQLParser.Attribute_nameContext.class);
        for (ParserRuleContext ctx:attributeContexts) {
            String[] var = {ctx.getText(), ctx.getText(), "Attribute name", ctx.getText()};
            refs.add(var);
        }
        refs.addAll(builtinfunctions);
        return refs;
    }

    /**query context snippets.*/
    public Object getQueryContext(LSOperationContext lsContext) {
        List<String[]> snips = new ArrayList<>();
        snips.addAll((ArrayList) this.getQuerySection(lsContext));
        snips.addAll(SnippetBlock.getQueryContextKWs());
        SnippetBlock.getBuiltInFuncions();
        return snips;
    }
    /**source context snippets.*/
    public Object getSourceContext(LSOperationContext lsContext) {
        List<String[]> snips = new ArrayList<>();
        return snips;
    }
    /**DefinitionFunctionContext snippets.*/
    public Object getDefFuncContext(LSOperationContext lsContext) {
        List<Object[]> snips = new ArrayList<>();
        snips.add(SnippetBlock.FUNC_DEFINITION);
        return snips;
    }
    /**completion item generation.*/
    public Object getSiddhiAppSnippets(LSOperationContext lsContext) {
        List<Object[]> siddhiAppContextSnippets = new ArrayList<>();
        siddhiAppContextSnippets.addAll(SnippetBlock.APP_ANNOTATION_DEFINITION);
        siddhiAppContextSnippets.add(SnippetBlock.QUERY_DEFINITION);
        siddhiAppContextSnippets.add(SnippetBlock.STREAM_DEFINITION);
        siddhiAppContextSnippets.add(SnippetBlock.AGGREGATION_DEFINITION);
        siddhiAppContextSnippets.add(SnippetBlock.FUNC_DEFINITION);
        siddhiAppContextSnippets.add(SnippetBlock.PARTITION_DEFINITION);
        siddhiAppContextSnippets.add(SnippetBlock.TRIGGER_DEFINITION);
        siddhiAppContextSnippets.add(SnippetBlock.TABLE_DEFINITION);
        siddhiAppContextSnippets.add(SnippetBlock.KW_DEFINE);
        return siddhiAppContextSnippets;
    }
}
