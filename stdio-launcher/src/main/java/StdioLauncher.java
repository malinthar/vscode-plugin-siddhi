

import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.launch.LSPLauncher;
import org.eclipse.lsp4j.services.LanguageClient;
import io.siddhi.langserver.SiddhiLanguageServer;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;


/* To launche the language server 
   Language server is imported
   It provides the capabilities it has
*/
public class StdioLauncher {
    public static void main(String args[]) throws InterruptedException, ExecutionException {
        //Launcher is started by the trigger event of client
        LogManager.getLogManager().reset();
        Logger globalLogger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        globalLogger.setLevel(Level.OFF);
       // keyboard inputs System.in and System.out
        startServer(System.in, System.out);
    }

    public static void startServer(InputStream in, OutputStream out) throws InterruptedException, ExecutionException {
        /**create server instance*/
        SiddhiLanguageServer server = new SiddhiLanguageServer();
        /**create server launcher (wire end points ).i.e:returns the initialted Builder<LanguageClient> which wires up all
        components for JSON-RPC communication.*/ 
        Launcher<LanguageClient> l = LSPLauncher.createServerLauncher(server, in, out);
        /**client instance*/
        LanguageClient client = l.getRemoteProxy();
        /**connect the server and client/set the stream up*/
        server.connect(client);
        //;i.e: Future Object(CompletableFuture)for asyncrhonous process is returned
        Future<?> startListening = l.startListening();
        //returns the resul from the actual computation occurs on the thread
        startListening.get();
    }

}

/**for more details refer:
https://github.com/eclipse/lsp4j/blob/master/org.eclipse.lsp4j.jsonrpc/src/main/java/org/eclipse/lsp4j/jsonrpc/Launcher.java
https://www.baeldung.com/java-future
*/