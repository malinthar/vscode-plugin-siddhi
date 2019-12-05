/*
 * Copyright (c) 2019, WSO2 Inc. (http://wso2.com) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.siddhi.langserver.utils;

import io.siddhi.langserver.DocumentManager;
import io.siddhi.langserver.LSCompletionContext;
import io.siddhi.langserver.completion.providers.CompletionProvider;
import io.siddhi.langserver.completion.providers.siddhiapp.ParseContextProvider;
import io.siddhi.langserver.visitor.LanguageServerParserErrorStrategy;
import io.siddhi.langserver.visitor.SiddhiQLLSVisitorImpl;
import io.siddhi.query.compiler.SiddhiQLLexer;
import io.siddhi.query.compiler.SiddhiQLParser;
import io.siddhi.query.compiler.exception.SiddhiParserException;
import io.siddhi.query.compiler.internal.SiddhiErrorListener;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionParams;
import org.eclipse.lsp4j.Position;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Provides the interface for the processes of compilation of source code and retrieval of
 * respective completion items from context providers.
 */
public class CompletionUtil {

    /**
     * Integrates compilation and completionItem generation, returns a list of completion items given the source
     * content and current cursor position {@link CompletionParams}.
     *
     * @param completionParams
     * @return {@link List<CompletionItem>} list of completion items.
     * @throws URISyntaxException
     */
    public static List<CompletionItem> getCompletions(CompletionParams completionParams) throws URISyntaxException {
        Path path = Paths.get(new URI(completionParams.getTextDocument().getUri()));
        String sourceContent = DocumentManager.INSTANCE.getFileContent(path);
        Position cursorPosition = completionParams.getPosition();
        //In VSCode line number m is indexed as m-1. Therefore line number should be added 1 in order to set the line
        // number correctly. Note: This should be changed so that it fits other editors as well.
        LSCompletionContext.INSTANCE.setPosition(cursorPosition.getLine() + 1, cursorPosition.getCharacter());
        LSCompletionContext.INSTANCE.setSourceContent(sourceContent);
        generateContextTree();
        return resolveCompletionItems();
    }

    /**
     * Set the compiled result of the source code by retrieving the compiled source code as a map of context's name to
     * ParseTree.
     * {@link Map<String, ParseTree>} and
     * store it in the {@link LSCompletionContext}
     */
    public static void generateContextTree() {
        try {
            Map<String, ParseTree> parseTree =
                    parse(LSCompletionContext.INSTANCE.getSourceContent(),
                            LSCompletionContext.INSTANCE.getPosition());
            LSCompletionContext.INSTANCE.setCompletionContext(parseTree);
        } catch (SiddhiParserException e) {
            LSCompletionContext.INSTANCE.setCompletionContext(null);
        }
    }

    //todo:move to a package named parser

    /**
     * Used at the Siddhi Language server to parse source content and obtain a parseTreeMap.
     *
     * @param source
     * @param goalPosition
     * @return
     * @throws RecognitionException
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
        //todo: setCompletionContext here.
    }

    /**
     * Returns the completion items, given the current context.
     *
     * @return {@link List<CompletionItem>} list of completion items generated by the context providers.
     */
    public static List<CompletionItem> resolveCompletionItems() {

        List<CompletionItem> completionItems;
        CompletionProvider currentContextProvider = LSCompletionContext.INSTANCE.
                getCurrentContextProvider();
        if (currentContextProvider != null && !(currentContextProvider instanceof ParseContextProvider)) {
            completionItems = traverseUp(currentContextProvider);
            return completionItems;
        } else {
            return null;
        }
    }

    /**
     * traverse contextTree {@link Map<String, ParseTree>} upward once stepif the current context provider hasn't
     * returned any completion items.
     *
     * @param currentContextProvider
     * @return
     */
    public static List<CompletionItem> traverseUp(CompletionProvider currentContextProvider) {
        List<CompletionItem> completionItems = new ArrayList<>();
        if (currentContextProvider != null) {
            completionItems.addAll(currentContextProvider.getCompletions());
            if (completionItems.isEmpty()) {
                ParserRuleContext parentContext =
                        (ParserRuleContext) LSCompletionContext.INSTANCE.getCurrentContext().parent;
                LSCompletionContext.INSTANCE.setCurrentContext(parentContext);
                currentContextProvider =
                        LSCompletionContext.INSTANCE.getProvider(parentContext.getClass().getName());
                completionItems.addAll(traverseUp(currentContextProvider));
            }
        }
        return completionItems;
    }
}

