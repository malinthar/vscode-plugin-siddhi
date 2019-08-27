package io.siddhi.langserver.completion;

import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionParams;
import org.eclipse.lsp4j.Position;
import io.siddhi.langserver.DocumentManagerImpl;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import io.siddhi.langserver.LSContext;
import io.siddhi.langserver.completion.spi.LSCompletionProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/** */

public class CompletionUtil
{

   public static List<CompletionItem> getCompletions(CompletionParams completionParams) throws URISyntaxException{

       /** resolve the position*/
       Path path = Paths.get(new URI(completionParams.getTextDocument().getUri()));
       String sourceContent = DocumentManagerImpl.getInstance().getFileContent(path);
       Position cursorPosition = completionParams.getPosition();
       List<CompletionItem> completionitems=new ArrayList<>();

       /** retrieve the completions from siddhi completion engine */
       LSContext.INSTANCE.setPosition(cursorPosition.getLine()+1,cursorPosition.getCharacter());
       LSContext.INSTANCE.setSourceContent(sourceContent);
       ContextTreeGenerator.INSTANCE.generateContextTree();
       LSCompletionProviderFactory factory=LSCompletionProviderFactory.getInstance();
       Map<Class,LSCompletionProvider> providers=new HashMap<>();
       providers=factory.getProviders();
       LSCompletionProvider contextProvider=providers.get(LSContext.INSTANCE.getCurrentContext().getClass());
       if(contextProvider!=null) {
           completionitems = contextProvider.getCompletions(LSContext.INSTANCE);
       }
       return completionitems;


   }



}

















   /**private static final String LINE_SEPARATOR=System.lineSeparator();
    public static ModifiedContent getModifiedContent(CompletionParams completionParams) throws IOException,
            URISyntaxException {
        /** current cursor position
        Position position = completionParams.getPosition();
        /** current line
        int cursorLine = position.getLine();
        /**get the document uri
        Path path = Paths.get(new URI(completionParams.getTextDocument().getUri()));
        /** save original content
        String originalContent = DocumentManagerImpl.getInstance().getFileContent(path);
        /** split the document content into separate lines
        String[] lines = originalContent.split("\\r?\\n");
        lines[cursorLine] = lines[cursorLine].replaceAll("\\S", "");

        /**new position after removing the new line chars
        Position modifiedPosition = new Position(position.getLine()+1,position.getCharacter());
        return new ModifiedContent(originalContent, modifiedPosition);
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
    }*/
