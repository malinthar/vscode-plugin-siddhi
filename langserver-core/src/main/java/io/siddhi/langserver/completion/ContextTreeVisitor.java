package io.siddhi.langserver.completion;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.List;
public class ContextTreeVisitor {
    private List<ParserRuleContext> ruleContexts;
    private Class context;
    public List<ParserRuleContext> findRuleContexts(ParserRuleContext rootContext, Class context){
          ruleContexts=new ArrayList<>();
          this.context=context;
          if(rootContext.getChildCount()!=0){
              visitChildren(rootContext);
          }

          return this.ruleContexts;
    }
    public void visitChildren(ParserRuleContext ctx){
       if(ctx.getChildCount()!=0){
        List<ParserRuleContext> children=ctx.getRuleContexts(ParserRuleContext.class);
        for(ParserRuleContext childCtx:children){
            if(childCtx.getClass().equals(this.context)){
                this.ruleContexts.add(childCtx);
            }
            visitChildren((ParserRuleContext)childCtx);
        }
       }
    }
}
