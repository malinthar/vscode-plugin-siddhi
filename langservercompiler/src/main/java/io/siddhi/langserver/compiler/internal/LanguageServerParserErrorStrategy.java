package io.siddhi.langserver.compiler.internal;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.IntervalSet;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class LanguageServerParserErrorStrategy extends DefaultErrorStrategy {
    public boolean triggered;
    public HashMap<String,ParserRuleContext> contextTree =new LinkedHashMap<>();
    @Override
    public void reportMissingToken(Parser recognizer) {
        /* build the context hierarchy*/
        this.triggered=true;
        ErrorNode errorNode=new ErrorNode();
        errorNode.setParent(recognizer.getContext());
        IntervalSet expecting = getExpectedTokens(recognizer);
        errorNode.setExpectedSymbols(expecting.toString(recognizer.getVocabulary()));
        contextTree.put(ErrorNode.class.toString(),errorNode);
        contextTree.put(recognizer.getContext().getClass().toString(),recognizer.getContext());
        fillContext(recognizer.getContext());
        /*error message generation*/
        Token t = recognizer.getCurrentToken();
        String tokenName = getTokenErrorDisplay(t);
        String msg = "extraneous input "+tokenName+" expecting "+
                expecting.toString(recognizer.getVocabulary());
        recognizer.notifyErrorListeners(t, msg, null);

    }

    @Override
    protected void reportNoViableAlternative(Parser recognizer, NoViableAltException e) {
        /* build the context hierarchy*/
        this.triggered=true;
        ErrorNode errorNode=new ErrorNode();
        errorNode.setParent(recognizer.getContext());
        IntervalSet expecting = getExpectedTokens(recognizer);
        errorNode.setExpectedSymbols(expecting.toString(recognizer.getVocabulary()));
        contextTree.put(ErrorNode.class.toString(),errorNode);
        contextTree.put(recognizer.getContext().getClass().toString(),recognizer.getContext());
        fillContext(recognizer.getContext());

        /*error message generation*/
        TokenStream tokens = recognizer.getInputStream();
        String input;
        if ( tokens!=null ) {
            if ( e.getStartToken().getType()==Token.EOF ) input = "<EOF>";
            else input = tokens.getText(e.getStartToken(), e.getOffendingToken());
        }
        else {
            input = "<unknown input>";
        }
        String msg = "no viable alternative at input "+escapeWSAndQuote(input);
        recognizer.notifyErrorListeners(e.getOffendingToken(), msg, e);

    }

    @Override
    protected void reportUnwantedToken(Parser recognizer) {
        /* build the context hierarchy*/
        this.triggered=true;
        ErrorNode errorNode=new ErrorNode();
        errorNode.setParent(recognizer.getContext());
        IntervalSet expecting = getExpectedTokens(recognizer);
        errorNode.setExpectedSymbols(expecting.toString(recognizer.getVocabulary()));
        contextTree.put(ErrorNode.class.toString(),errorNode);
        contextTree.put(recognizer.getContext().getClass().toString(),recognizer.getContext());
        fillContext(recognizer.getContext());

        /*error message generation*/
        beginErrorCondition(recognizer);
        Token t = recognizer.getCurrentToken();
        String tokenName = getTokenErrorDisplay(t);
        String msg = "extraneous input "+tokenName+" expecting "+
                expecting.toString(recognizer.getVocabulary());
        recognizer.notifyErrorListeners(t, msg, null);
    }
    @Override
    protected void reportInputMismatch(Parser recognizer, InputMismatchException e) {
        /* build the context hierarchy*/
        this.triggered=true;
        ErrorNode errorNode=new ErrorNode();
        errorNode.setParent(recognizer.getContext());
        IntervalSet expecting = getExpectedTokens(recognizer);
        errorNode.setExpectedSymbols(expecting.toString(recognizer.getVocabulary()));
        contextTree.put(ErrorNode.class.toString(),errorNode);
        contextTree.put(recognizer.getContext().getClass().toString(),recognizer.getContext());
        fillContext(recognizer.getContext());
        /*to be used for completion providing*/




        /*error message generation*/
          RuleContext ctx=e.getCtx();
          String msg = "mismatched input "+getTokenErrorDisplay(e.getOffendingToken())+
                " expecting "+e.getExpectedTokens().toString(recognizer.getVocabulary());
          recognizer.notifyErrorListeners(e.getOffendingToken(), msg, e);

    }
    public void fillContext(ParserRuleContext context){
        if(context.parent!=null){
            contextTree.put(context.parent.getClass().toString(),(ParserRuleContext)context.parent);
            fillContext((ParserRuleContext)context.parent);
        }

    }

}
