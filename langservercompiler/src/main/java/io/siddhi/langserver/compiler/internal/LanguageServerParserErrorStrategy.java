package io.siddhi.langserver.compiler.internal;

import org.antlr.v4.runtime.DefaultErrorStrategy;
import org.antlr.v4.runtime.InputMismatchException;
import org.antlr.v4.runtime.NoViableAltException;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.misc.IntervalSet;

import java.util.HashMap;
import java.util.LinkedHashMap;


/**
 * {@code LanguageServerParserErrorStrategy} Handles exceptions thrown by parser strategically.
 */
public class LanguageServerParserErrorStrategy extends DefaultErrorStrategy {
    public boolean triggered;
    public HashMap<String, ParserRuleContext> contextTree = new LinkedHashMap<>();
    @Override
    public void reportMissingToken(Parser recognizer) {
        this.triggered = true;
        ErrorNode errorNode = new ErrorNode();
        errorNode.setParent(recognizer.getContext());
        IntervalSet expecting = getExpectedTokens(recognizer);
        errorNode.setExpectedSymbols(expecting.toString(recognizer.getVocabulary()));
        contextTree.put(ErrorNode.class.toString(), errorNode);
        contextTree.put(recognizer.getContext().getClass().toString(), recognizer.getContext());
        fillContext(recognizer.getContext());
        Token t = recognizer.getCurrentToken();
        String tokenName = getTokenErrorDisplay(t);
        String msg = "extraneous input " + tokenName + " expecting "+
                expecting.toString(recognizer.getVocabulary());
        recognizer.notifyErrorListeners(t, msg, null);
    }
    @Override
    protected void reportNoViableAlternative(Parser recognizer, NoViableAltException e) {
        this.triggered = true;
        ErrorNode errorNode = new ErrorNode();
        errorNode.setParent(recognizer.getContext());
        IntervalSet expecting = getExpectedTokens(recognizer);
        errorNode.setExpectedSymbols(expecting.toString(recognizer.getVocabulary()));
        contextTree.put(ErrorNode.class.toString(),  errorNode);
        contextTree.put(recognizer.getContext().getClass().toString(), recognizer.getContext());
        fillContext(recognizer.getContext());
        TokenStream tokens = recognizer.getInputStream();
        String input;
        if ( tokens != null ) {
            if ( e.getStartToken().getType() == Token.EOF ) input = "<EOF>";
            else input = tokens.getText(e.getStartToken(), e.getOffendingToken());
        } else {
            input = "<unknown input>";
        }
        String msg = "no viable alternative at input " + escapeWSAndQuote(input);
        recognizer.notifyErrorListeners(e.getOffendingToken(), msg, e);
    }

    @Override
    protected void reportUnwantedToken(Parser recognizer) {
        this.triggered = true;
        ErrorNode errorNode = new ErrorNode();
        errorNode.setParent(recognizer.getContext());
        IntervalSet expecting = getExpectedTokens(recognizer);
        errorNode.setExpectedSymbols(expecting.toString(recognizer.getVocabulary()));
        contextTree.put(ErrorNode.class.toString(), errorNode);
        contextTree.put(recognizer.getContext().getClass().toString(), recognizer.getContext());
        fillContext(recognizer.getContext());
        beginErrorCondition(recognizer);
        Token t = recognizer.getCurrentToken();
        String tokenName = getTokenErrorDisplay(t);
        String msg = "extraneous input " + tokenName+" expecting " +
                expecting.toString(recognizer.getVocabulary());
        recognizer.notifyErrorListeners(t, msg, null);
    }
    @Override
    protected void reportInputMismatch(Parser recognizer, InputMismatchException e) {
        this.triggered = true;
        ErrorNode errorNode = new ErrorNode();
        errorNode.setParent(recognizer.getContext());
        IntervalSet expecting = getExpectedTokens(recognizer);
        errorNode.setExpectedSymbols(expecting.toString(recognizer.getVocabulary()));
        contextTree.put(ErrorNode.class.toString(),errorNode);
        contextTree.put(recognizer.getContext().getClass().toString(), recognizer.getContext());
        fillContext(recognizer.getContext());
        RuleContext ctx = e.getCtx();
        String msg = "mismatched input "+getTokenErrorDisplay(e.getOffendingToken()) +
                " expecting "+e.getExpectedTokens().toString(recognizer.getVocabulary());
        recognizer.notifyErrorListeners(e.getOffendingToken(), msg, e);

    }
    public void fillContext(ParserRuleContext context){
        if (context.parent != null) {
            contextTree.put(context.parent.getClass().toString(), (ParserRuleContext)context.parent);
            fillContext((ParserRuleContext)context.parent);
        }

    }

}
