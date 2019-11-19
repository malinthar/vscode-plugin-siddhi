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

    public static List<ParserRuleContext> findFromChildren(ParserRuleContext parentContext, Class childContext) {

        List<ParserRuleContext> ruleContexts = new ArrayList<>();
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
}
