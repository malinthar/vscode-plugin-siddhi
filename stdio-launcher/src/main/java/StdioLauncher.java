

import io.siddhi.langserver.SiddhiLanguageServer;
import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.launch.LSPLauncher;
import org.eclipse.lsp4j.services.LanguageClient;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;


/** To launch the language server
   Language server is imported
   It provides its capabilities.
*/
public class StdioLauncher {

    // To avoid multiple slf4j binding error printed to I/O which breaks LS protocol.


    public static void main(String args[]) throws InterruptedException, ExecutionException {
        /**Launcher is started by the trigger event of client.*/
        LogManager.getLogManager().reset();
        Logger globalLogger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        globalLogger.setLevel(Level.OFF);
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
    /**stdio used as the transport.*/
//        PrintStream stream =new PrintStream();
//    PrintStream old = System.out;
//    PrintStream myStream = new PrintStream(System.out) {
//        @Override
//        public void println(String x) {
//            old.println(x);
//        }
//        @Override
//        public void print(String x) {
//            old.print(x);
//        }
//
//        @Override
//        public void write(int b) {
//            old.write(b);
//        }
//
//        @Override
//        public void write(byte[] b) throws IOException {
//            old.write(b);
//        }
//
//        @Override
//        public void write(byte[] buf, int off, int len) {
//            old.write(buf, off, len);
//        }
//    };
//        System.setOut(myStream);
