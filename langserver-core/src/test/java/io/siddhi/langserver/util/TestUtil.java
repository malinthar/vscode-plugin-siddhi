package io.siddhi.langserver.util;

import com.google.gson.Gson;
import io.siddhi.langserver.SiddhiLanguageServer;
import org.eclipse.lsp4j.ClientCapabilities;
import org.eclipse.lsp4j.CompletionCapabilities;
import org.eclipse.lsp4j.CompletionItemCapabilities;
import org.eclipse.lsp4j.CompletionParams;
import org.eclipse.lsp4j.DidCloseTextDocumentParams;
import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.TextDocumentClientCapabilities;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.TextDocumentItem;
import org.eclipse.lsp4j.jsonrpc.Endpoint;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseError;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseMessage;
import org.eclipse.lsp4j.jsonrpc.services.ServiceEndpoints;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * (TestUtil) Utility class for test cases.
 */
public class TestUtil {
    private static final String COMPLETION = "textDocument/completion";
    private static final Gson GSON = new Gson();
    private TestUtil() {
    }
    public static Endpoint initializeLanguageServer() {
        Endpoint endpoint = ServiceEndpoints.toEndpoint(new SiddhiLanguageServer());
        InitializeParams params = new InitializeParams();
        ClientCapabilities capabilities = new ClientCapabilities();
        TextDocumentClientCapabilities textDocumentClientCapabilities = new TextDocumentClientCapabilities();
        CompletionCapabilities completionCapabilities = new CompletionCapabilities();
        completionCapabilities.setCompletionItem(new CompletionItemCapabilities(true));
        textDocumentClientCapabilities.setCompletion(completionCapabilities);
        capabilities.setTextDocument(textDocumentClientCapabilities);
        params.setCapabilities(capabilities);
        endpoint.request("initialize", params);
        return endpoint;
    }
    public static void openDocument(Endpoint serviceEndpoint, Path filePath) throws IOException {
        DidOpenTextDocumentParams documentParams = new DidOpenTextDocumentParams();
        TextDocumentItem textDocumentItem = new TextDocumentItem();
        TextDocumentIdentifier identifier = new TextDocumentIdentifier();

        byte[] encodedContent = Files.readAllBytes(filePath);
        identifier.setUri(filePath.toUri().toString());
        textDocumentItem.setUri(identifier.getUri());
        textDocumentItem.setText(new String(encodedContent));
        documentParams.setTextDocument(textDocumentItem);
        serviceEndpoint.notify("textDocument/didOpen", documentParams);
    }
    public static String getCompletionResponse(String filePath, Position position, Endpoint endpoint) {
        CompletableFuture result = endpoint.request(COMPLETION, getCompletionParams(filePath, position));
        return getResponseString(result);
    }
    private static CompletionParams getCompletionParams(String filePath, Position position) {
        CompletionParams completionParams = new CompletionParams();
        completionParams.setTextDocument(getTextDocumentIdentifier(filePath));
        completionParams.setPosition(new Position(position.getLine(), position.getCharacter()));

        return completionParams;
    }

    public static String getResponseString(CompletableFuture completableFuture) {
        ResponseMessage jsonrpcResponse = new ResponseMessage();
        try {
            jsonrpcResponse.setId("324");
            jsonrpcResponse.setResult(completableFuture.get());
        } catch (InterruptedException e) {
            ResponseError responseError = new ResponseError();
            responseError.setCode(-32002);
            responseError.setMessage("Attempted to retrieve the result of a task/s" +
                    "that was aborted by throwing an exception");
            jsonrpcResponse.setError(responseError);
        } catch (ExecutionException e) {
            ResponseError responseError = new ResponseError();
            responseError.setCode(-32001);
            responseError.setMessage("Current thread was interrupted");
            jsonrpcResponse.setError(responseError);
        }

        return GSON.toJson(jsonrpcResponse);
    }
    public static TextDocumentIdentifier getTextDocumentIdentifier(String filePath) {
        TextDocumentIdentifier identifier = new TextDocumentIdentifier();
        identifier.setUri(Paths.get(filePath).toUri().toString());

        return identifier;
    }

    public static void closeDocument(Endpoint serviceEndpoint, Path filePath) {
        TextDocumentIdentifier documentIdentifier = new TextDocumentIdentifier();
        documentIdentifier.setUri(filePath.toUri().toString());
        serviceEndpoint.notify("textDocument/didClose", new DidCloseTextDocumentParams(documentIdentifier));
    }
    public static void shutdownLanguageServer(Endpoint serviceEndpoint) {
        serviceEndpoint.notify("shutdown", null);
    }

}
