package io.siddhi.langserver.completion.snippet;
import io.siddhi.langserver.LSContext;
import io.siddhi.langserver.completion.ContextTreeGenerator;
import io.siddhi.langserver.completion.ContextTreeVisitor;
import io.siddhi.query.compiler.SiddhiCompiler;
import io.siddhi.query.compiler.SiddhiQLLexer;
import io.siddhi.query.compiler.SiddhiQLParser;
import io.siddhi.query.compiler.internal.ErrorNode;
import org.antlr.v4.runtime.ParserRuleContext;
import java.util.List;
import java.util.ArrayList;

public class Snippet {
    private String[] ruleNames=SiddhiQLLexer.ruleNames;
    private ContextTreeVisitor visitor=new ContextTreeVisitor();
    public String[] getAttributetype(LSContext lsContext) {
        String types=((ErrorNode)lsContext.getCurrentErrorNode()).getExpectedSymbols();
        types=types.toLowerCase();
        String[] atrtypes = types.split("\\s*,\\s*");
        return atrtypes;
    }
    public String[] getAnnotationElements(LSContext lsContext){
        String[] arr=this.ruleNames;
        String[] atrtypes={"Temperature-Analytics"};
        return atrtypes;
    }
    public Object getAttributeReference(LSContext lsContext){
        List<String> refs=new ArrayList<>();
        List<ParserRuleContext> namecontexts=this.visitor.findRuleContexts((SiddhiQLParser.ParseContext)((ArrayList)lsContext.getParserContextTree()).get(0), SiddhiQLParser.Attribute_nameContext.class);
        for(ParserRuleContext ctx:namecontexts){
            if(ctx.getParent().getClass().equals(SiddhiQLParser.Attribute_referenceContext.class)||ctx.getParent().getClass().equals(SiddhiQLParser.Output_attributeContext.class)){
                refs.add(ctx.getText());
            }

        }
        return refs;
    }
    public Object getQueryInput(LSContext lsContext){
        List<String> instreams=new ArrayList<>();
        List<ParserRuleContext> sourceContexts=this.visitor.findRuleContexts((SiddhiQLParser.ParseContext)((ArrayList)lsContext.getParserContextTree()).get(0), SiddhiQLParser.SourceContext.class);
        for(ParserRuleContext ctx:sourceContexts){
            instreams.add(((SiddhiQLParser.SourceContext) ctx).getText());
        }
        return instreams;

    }
    public Object getQuerySection(LSContext lsContext){
        List<String> refs=new ArrayList<>();
        List<ParserRuleContext> attributeContexts=this.visitor.findRuleContexts((SiddhiQLParser.ParseContext)((ArrayList)lsContext.getParserContextTree()).get(0), SiddhiQLParser.Attribute_nameContext.class);
        for(ParserRuleContext ctx:attributeContexts){
            refs.add(ctx.getText());
        }
        return refs;
    }

}