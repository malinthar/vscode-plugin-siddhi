package io.siddhi.langserver.completion.util;

import io.siddhi.langserver.LSOperationContext;
import io.siddhi.query.compiler.SiddhiCompiler;

import java.util.HashMap;

/**
 * {@code ContextTreeGenerator} .
 */
public class ContextTreeGeneratorUtil {
    public static void getContextTree() {
        try {
            Object parseTree = SiddhiCompiler.languageServerParse(LSOperationContext.INSTANCE.getSourceContent(),
                    LSOperationContext.INSTANCE.getPosition());
                LSOperationContext.INSTANCE.setContextTree((HashMap) parseTree);
        } catch (Throwable e) {
           String msg = "parse error";
           LSOperationContext.INSTANCE.setContextTree(null);
        }
    }
}
