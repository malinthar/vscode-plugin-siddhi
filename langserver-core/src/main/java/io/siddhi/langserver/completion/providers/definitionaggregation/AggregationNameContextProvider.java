package io.siddhi.langserver.completion.providers.definitionaggregation;

import io.siddhi.langserver.completion.providers.CompletionProvider;
import io.siddhi.query.compiler.SiddhiQLParser;
import org.eclipse.lsp4j.CompletionItem;

import java.util.List;

/**
 * Provides Completions for AggregationNameContext.{@link io.siddhi.query.compiler.SiddhiQLParser.Aggregation_nameContext}
 */
public class AggregationNameContextProvider extends CompletionProvider {

    public AggregationNameContextProvider() {

        this.providerName = SiddhiQLParser.Aggregation_nameContext.class.getName();
    }

    @Override
    public List<CompletionItem> getCompletions() {

        return generateCompletionList(null);
    }
}
