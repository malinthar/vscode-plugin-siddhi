package io.siddhi.langserver.completion.util;

import io.siddhi.langserver.DocumentManagerImpl;
import io.siddhi.langserver.LSOperationContext;
import io.siddhi.langserver.completion.providers.ParseContextProvider;
import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;
import io.siddhi.query.compiler.SiddhiCompiler;
import org.antlr.v4.runtime.ParserRuleContext;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionParams;
import org.eclipse.lsp4j.Position;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * {@code CompletionUtil} Util class for completion.
 */
public class CompletionUtil {

    //todo: avoid compiling the document everytime
    public static List<CompletionItem> getCompletions(CompletionParams completionParams) throws URISyntaxException {

        Path path = Paths.get(new URI(completionParams.getTextDocument().getUri()));
        String sourceContent = DocumentManagerImpl.getInstance().getFileContent(path);
        Position cursorPosition = completionParams.getPosition();
        LSOperationContext.INSTANCE.setPosition(cursorPosition.getLine() + 1, cursorPosition.getCharacter());
        LSOperationContext.INSTANCE.setSourceContent(sourceContent);
        getContextTree();
        return resolveCompletionItems();
    }

    public static void getContextTree() {

        //todo change the parseTree type
        try {
            Object parseTree = SiddhiCompiler.languageServerParse(LSOperationContext.INSTANCE.getSourceContent(),
                    LSOperationContext.INSTANCE.getPosition());
            LSOperationContext.INSTANCE.setContextTree((LinkedHashMap) parseTree);
        } catch (Throwable e) {
            String msg = "parse error";
            LSOperationContext.INSTANCE.setContextTree(null);
        }
    }

    public static List<CompletionItem> resolveCompletionItems() {

        List<CompletionItem> completionItems = new ArrayList<>();
        Map<String, LSCompletionProvider> providers = LSOperationContext.INSTANCE.FACTORY.getProviders();
        LSCompletionProvider currentContextProvider =
                providers.get(LSOperationContext.INSTANCE.getCurrentContext().getClass().getName());

        //todo: add more sensible logic to context providers, should it be based on context or should I create multiple scopes.
        // example: In queries from InputStream select I should only display visible attribute names + functions.
        // In addition, in stream joins completions  should be provided as streamA.attribute streamB.attribute,look into this.
        //todo: first remove unnecessary contexts. Only keep providers for critical contexts.Or follow the grammar?
        if (currentContextProvider != null) {
            try {
                completionItems = traverseContextTreeUpward(currentContextProvider);
                //todo: error handling here
            } catch (RuntimeException e) {
                //todo: change error message
                String msg = "Runtime exception" + e.getMessage();
            }
        }
        return completionItems;
    }

    public static List<CompletionItem> traverseContextTreeUpward(LSCompletionProvider currentContextProvider) {

        List<CompletionItem> completionItems = currentContextProvider.getCompletions();
        //todo: check whether the completions are empty/ Arraylist of zero length-check, what if parent is null?
       //todo: Null point could come here
        if (completionItems.isEmpty() && !(currentContextProvider instanceof ParseContextProvider)) {
            ParserRuleContext parentContext =
                    (ParserRuleContext) LSOperationContext.INSTANCE.getCurrentContext().parent;
            LSOperationContext.INSTANCE.setCurrentContext(parentContext);
            currentContextProvider =
                    LSOperationContext.INSTANCE.FACTORY.getProviders().get(parentContext.getClass().getName());
            completionItems = traverseContextTreeUpward(currentContextProvider);
            return completionItems;
        } else {
            return completionItems;
        }
    }
    //todo error here:

}
