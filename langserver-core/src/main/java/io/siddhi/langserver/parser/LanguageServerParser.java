package io.siddhi.langserver.parser;

import io.siddhi.langserver.visitor.LanguageServerParserErrorStrategy;
import io.siddhi.langserver.visitor.SiddhiQLLSVisitorImpl;
import io.siddhi.query.compiler.SiddhiQLLexer;
import io.siddhi.query.compiler.SiddhiQLParser;
import io.siddhi.query.compiler.exception.SiddhiParserException;
import io.siddhi.query.compiler.internal.SiddhiErrorListener;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.Map;

public class LanguageServerParser {
    /**
     * Used at the Siddhi Language server to parse source content and obtain a parseTreeMap.
     *
     * @param source
     * @param goalPosition
     * @return
     */
    public static Map<String, ParseTree> parse(String source, int[] goalPosition) {
        ANTLRInputStream input = new ANTLRInputStream(source);
        SiddhiQLLexer lexer = new SiddhiQLLexer(input);
        lexer.removeErrorListeners();
        lexer.addErrorListener(SiddhiErrorListener.INSTANCE);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SiddhiQLParser parser = new SiddhiQLParser(tokens);
        parser.setErrorHandler(new LanguageServerParserErrorStrategy());
        parser.removeErrorListeners();
        parser.addErrorListener(SiddhiErrorListener.INSTANCE);
        try {
            ParseTree parseTree = parser.parse();
            SiddhiQLLSVisitorImpl visitor = new SiddhiQLLSVisitorImpl(goalPosition);
            parseTree.accept(visitor);
            return visitor.getParseTreeMap();
        } catch (SiddhiParserException ignored) {
            //todo: e has been ignored until it will be written to a log file.
            return ((LanguageServerParserErrorStrategy)
                    parser.getErrorHandler()).getParseTreeMap();

        }
    }
}
