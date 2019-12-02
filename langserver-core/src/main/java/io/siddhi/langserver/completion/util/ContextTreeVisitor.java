package io.siddhi.langserver.completion.util;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code ContextTreeVisitor} Visits parse tree to find a given context.
 */

public class ContextTreeVisitor {

    private List<Object> ruleContexts;
    private Class context;
    public static final ContextTreeVisitor INSTANCE = new ContextTreeVisitor();

    public List<Object> findRuleContexts(ParserRuleContext rootContext, Class context) {

        ruleContexts = new ArrayList<>();
        this.context = context;
        if (rootContext.getChildCount() != 0) {
            visitChildren(rootContext);
        }
        return this.ruleContexts;
    }

    public void visitChildren(ParserRuleContext ctx) {

        if (ctx.getChildCount() != 0) {
            List<ParseTree> children = ctx.children;
            for (ParseTree childCtx : children) {
                if (childCtx.getClass().equals(this.context)) {
                    this.ruleContexts.add(childCtx);
                }
                if (!childCtx.getClass().equals(TerminalNodeImpl.class)) {
                    visitChildren((ParserRuleContext) childCtx);
                }

            }
        }
    }
//    public static ParseTree visitChildrenToFindOne(ParserRuleContext context, String descendantContext){
//        if (context.getChildCount() != 0) {
//            List<ParseTree> children = context.children;
//            for (ParseTree childCtx : children) {
//                if (childCtx.getClass().getName().equalsIgnoreCase(descendantContext)) {
//                    return childCtx;
//                }
//                else {
//                    visitChildrenToFindOne((ParserRuleContext)childCtx,descendantContext);
//                }
//            }
//        }
//        else{
//            return null;
//        }
//    }

    public static List<ParseTree> findFromChildren(ParserRuleContext parentContext, Class childContext) {

        List<ParseTree> ruleContexts = new ArrayList<>();
        if (parentContext.getChildCount() != 0) {
            List<ParserRuleContext> children = parentContext.getRuleContexts(ParserRuleContext.class);
            for (ParserRuleContext childCtx : children) {
                if (childCtx.getClass().equals(childContext)) {
                    ruleContexts.add(childCtx);
                }
            }
        }
        return ruleContexts;
    }
    public static ParseTree findOneFromChildren(ParserRuleContext parentContext, Class childContext) {

        if (parentContext.getChildCount() != 0) {
            List<ParserRuleContext> children = parentContext.getRuleContexts(ParserRuleContext.class);
            for (ParserRuleContext childCtx : children) {
                if (childCtx.getClass().equals(childContext)) {
                    return childCtx;
                }
            }
        }
        return null;
    }
    //todo:change name as something like find first occurance.
//    public static ParseTree findOneFromDescendants(ParserRuleContext parentContext, String descendantContext) {
//        if (parentContext.getChildCount() != 0) {
//            return visitChildrenToFindOne(parentContext,descendantContext);
//        }
//    }
}
