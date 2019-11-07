package io.siddhi.langserver.compiler.internal;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * Error Node.
 */
public class ErrorNode extends ParserRuleContext {
    private String expectedSymbols;

    public void setExpectedSymbols(String expectedSymbols) {
        this.expectedSymbols = expectedSymbols;
    }
    public String getExpectedSymbols() {
        return expectedSymbols;
    }
}
