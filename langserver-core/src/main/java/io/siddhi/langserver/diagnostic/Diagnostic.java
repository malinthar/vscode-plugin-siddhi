package io.siddhi.langserver.diagnostic;

/**
 * The interface for Ballerina diagnostics. A diagnostic represents an error,
 * a warning or a message at a specific position in a source file.
 *
 * @since 0.94
 */
public interface Diagnostic {

    /**
     * Kind of the diagnostic.
     */
    enum Kind {
        ERROR,
        WARNING,
        NOTE,
    }

    /**
     * The interface that represents the source file in a diagnostic.
     */
    interface DiagnosticSource {

        String getPackageName();

        String getPackageVersion();

        String getCompilationUnitName();
    }

    /**
     * The interface that represents the source position in a diagnostic.
     *
     * Source position is a combination of the source file, start and end line numbers,
     * and start and end column numbers.
     *
     * @since 0.94
     */
    interface DiagnosticPosition {

        DiagnosticSource getSource();

        int getStartLine();

        int getEndLine();

        int getStartColumn();

        int getEndColumn();
    }

    Kind getKind();

    DiagnosticSource getSource();

    DiagnosticPosition getPosition();

    String getMessage();

    //DiagnosticCode getCode();
}
