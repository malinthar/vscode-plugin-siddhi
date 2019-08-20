package io.siddhi.langserver.completion;

import io.siddhi.langserver.completion.providers.AppAnnotationContextProvider;
import io.siddhi.query.compiler.SiddhiCompiler;
import io.siddhi.query.compiler.SiddhiQLParser;
import joptsimple.internal.Strings;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionItemKind;
import org.eclipse.lsp4j.CompletionParams;
import org.eclipse.lsp4j.Position;
import io.siddhi.langserver.DocumentManagerImpl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.lang.reflect.InvocationTargetException;

import java.util.*;

import io.siddhi.langserver.LSContext;
import io.siddhi.langserver.completion.spi.LSCompletionProvider;

import java.util.ArrayList;
import java.util.List;

import io.siddhi.langserver.completion.providers.DefinitionStreamContextProvider;

/**siddhi compiler**/

public class CompletionUtil
{
   private static final String LINE_SEPARATOR=System.lineSeparator();

    /**
     * Get the modified document content.
     *
     * Note: Here replace the line content at the cursor with spaces to avoid parser issues
     *
     * @param completionParams          Original file content to modify
     * @return {@link ModifiedContent}  Modified content
     * @throws IOException              IOException if the file read fails
     * @throws URISyntaxException       URI Syntax Exception
     */
     public static ModifiedContent getModifiedContent(CompletionParams completionParams) throws IOException,
           URISyntaxException {
       /** current cursor position*/
       Position position = completionParams.getPosition();
       /** current line */
       int cursorLine = position.getLine();
       /**get the document uri*/
       Path path = Paths.get(new URI(completionParams.getTextDocument().getUri()));
       /** save original content*/
       String originalContent = DocumentManagerImpl.getInstance().getFileContent(path);
       /** split the document content into separate lines*/
       String[] lines = originalContent.split("\\r?\\n");
       lines[cursorLine] = lines[cursorLine].replaceAll("\\S", "");

       /**new position after removing the new line chars */
       Position modifiedPosition = new Position(position.getLine()+1,position.getCharacter());
       return new ModifiedContent(originalContent, modifiedPosition);

   }
   public static List<CompletionItem> getCompletions(CompletionParams completionParams) throws NoSuchMethodException,
           InvocationTargetException, IllegalAccessException, URISyntaxException, IOException{
       /**refer https://www.geeksforgeeks.org/deque-interface-java-example/ */
       /**convert java objects to yml and vise versa */


       /** get modified( Here replace the line content at the cursor with spaces to avoid parser issues) content from the document with cursor position parameter*/
       Path path = Paths.get(new URI(completionParams.getTextDocument().getUri()));
       String sourceContent = DocumentManagerImpl.getInstance().getFileContent(path);
       Position modifiedPosition = completionParams.getPosition();
       List<CompletionItem> completionitems=new ArrayList<>();

       /** get the completions(no scope dependencies) from siddhi completion engine(parser)*/
       int[] position={modifiedPosition.getLine()+1,modifiedPosition.getCharacter()};
       SiddhiCompiler compiler=new SiddhiCompiler();
       LSContext.getInstance().setPosition(position);
       LSContext.getInstance().setSourceContent(sourceContent);
       ContextTreeGenerator.getInstance().generateContextTree();
       LSCompletionProviderFactory factory=LSCompletionProviderFactory.getInstance();
       Map<Class,LSCompletionProvider> providers=new HashMap<>();
       providers=factory.getProviders();
       LSCompletionProvider contextProvider=providers.get(LSContext.getInstance().getCurrentContext().getClass());
       if(contextProvider!=null){
           completionitems=contextProvider.getCompletions(LSContext.getInstance());
           return completionitems;
       }
       else{
           return completionitems;
       }


   }

    private static class ModifiedContent {
        private String content;
        private Position position;

        private ModifiedContent(String content, Position position) {
            this.content = content;
            this.position = position;
        }

        String getContent() {
            return content;
        }

        Position getPosition() {
            return position;
        }
    }

}
