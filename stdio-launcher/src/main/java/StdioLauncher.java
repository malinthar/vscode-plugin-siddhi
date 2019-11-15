
import io.siddhi.langserver.SiddhiLanguageServer;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.launch.LSPLauncher;
import org.eclipse.lsp4j.services.LanguageClient;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/** To launch the language server.
*/
public class StdioLauncher {

    public static void main(String args[]) throws InterruptedException, ExecutionException {
        /**Launcher is started by the trigger event of client.*/
        // To avoid logs printed to I/O which breaks LS protocol.
        Logger.getRootLogger().setLevel(Level.OFF);
        startServer(System.in, System.out);
    }

    private static void startServer(InputStream in, OutputStream out) throws InterruptedException, ExecutionException {
        /**create server instance.*/
        SiddhiLanguageServer server = new SiddhiLanguageServer();
        /**create server launcher (wire end points ).i.e:returns the initialted Builder<LanguageClient> which wires up all
        components for JSON-RPC communication.*/ 
        Launcher<LanguageClient> launcher = LSPLauncher.createServerLauncher(server, in, out);
        /**client instance.*/
        LanguageClient client = launcher.getRemoteProxy();
        /**connect the server and client/set the stream up.*/
        server.connect(client);
        /**promise.*/
        Future<?> startListening = launcher.startListening();
        startListening.get();
    }

}

/**for more details refer:
https://github.com/eclipse/lsp4j/blob/master/org.eclipse.lsp4j.jsonrpc/src/main/java/org/eclipse/lsp4j/jsonrpc/Launcher.java
https://www.baeldung.com/java-future.
*/
