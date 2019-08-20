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
import java.util.Arrays;

public class Snippet {
    private String[] ruleNames=SiddhiQLLexer.ruleNames;
    private ContextTreeVisitor visitor=new ContextTreeVisitor();
    public Object getDefalutKeyWords(LSContext lsContext){

        String defaultKeyWordstring=(((ErrorNode)lsContext.getCurrentErrorNode()).getExpectedSymbols()).replaceAll("\\{", "").replaceAll("\\}", "");;
        defaultKeyWordstring=defaultKeyWordstring.toLowerCase();
        String[] defualtKeyWords = defaultKeyWordstring.split("\\s*,\\s*");

        List<String> list = new ArrayList<>(Arrays.asList(defualtKeyWords));
        return list;
    }
    public Object getAttributetype(LSContext lsContext) {
        return getDefalutKeyWords(lsContext);
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
        if(refs.size()!=0)
         return refs;
        else{
            return this.getDefalutKeyWords(lsContext);
        }
    }
    public Object getQueryInput(LSContext lsContext){
        List<String> instreams=new ArrayList<>();
        List<ParserRuleContext> sourceContexts=this.visitor.findRuleContexts((SiddhiQLParser.ParseContext)((ArrayList)lsContext.getParserContextTree()).get(0), SiddhiQLParser.SourceContext.class);
        for(ParserRuleContext ctx:sourceContexts){
            instreams.add(((SiddhiQLParser.SourceContext) ctx).getText());
        }
        if(instreams.size()!=0){
            return instreams;
        }
        else{
            return this.getDefalutKeyWords(lsContext);
        }

    }
    public Object getQuerySection(LSContext lsContext){
        List<String> refs=new ArrayList<>();
        List<ParserRuleContext> attributeContexts=this.visitor.findRuleContexts((SiddhiQLParser.ParseContext)((ArrayList)lsContext.getParserContextTree()).get(0), SiddhiQLParser.Attribute_nameContext.class);
        for(ParserRuleContext ctx:attributeContexts){
            refs.add(ctx.getText());
        }
        if(refs.size()!=0)
            return refs;
        else{
            return this.getDefalutKeyWords(lsContext);
        }
    }
    public Object getQueryContext(LSContext lsContext){
        return this.getDefalutKeyWords(lsContext);
    }

}