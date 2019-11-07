package io.siddhi.langserver.common.utils;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * {@code CommonUtil} Util for all the features of language server.
 */
public class CommonUtil {
    /**
     * Get the path from given string URI.
     *
     * @param uri file uri
     * @return {@link Optional} Path from the URI
     */
    public static Optional<Path> getPathFromURI(String uri) {
        try {
            return Optional.ofNullable(Paths.get(new URL(uri).toURI()));
        } catch (URISyntaxException | MalformedURLException e) {
            // ignore
            //TODO:check
        }
        return Optional.empty();
    }
}
