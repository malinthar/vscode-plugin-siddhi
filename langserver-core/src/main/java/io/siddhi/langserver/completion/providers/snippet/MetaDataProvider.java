package io.siddhi.langserver.completion.providers.snippet;

import io.siddhi.langserver.completion.providers.snippet.util.Constants;
import io.siddhi.langserver.completion.providers.snippet.util.MetaDataProviderUtil;
import io.siddhi.langserver.completion.providers.snippet.util.metadata.MetaData;
import io.siddhi.langserver.completion.providers.snippet.util.metadata.ProcessorMetaData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * {@code MetaDataProvider}  Provider of Extension MetaData.
 */
public class MetaDataProvider {

    private static List<ProcessorMetaData> functions = new ArrayList<>();
    private static List<ProcessorMetaData> windowProcessors = new ArrayList<>();
    private static List<ProcessorMetaData> streamProcessors = new ArrayList<>();
    private static List<ProcessorMetaData> aggregatorFunctions = new ArrayList<>();
    private static List<ProcessorMetaData> sinks = new ArrayList<>();
    private static List<ProcessorMetaData> sources =  new ArrayList<>();
    private static List<ProcessorMetaData> sourceMaps = new ArrayList<>();
    private static List<ProcessorMetaData> sinkMaps = new ArrayList<>();
    private static List<ProcessorMetaData> stores = new ArrayList<>();
    private static Map<String, MetaData> extensionMetaData = new HashMap<>();

    /**
     * @return {@link List<ProcessorMetaData>} list of function metadata of all the extensions.
     */
    public static List<ProcessorMetaData> getFunctionMetaData() {

        if (extensionMetaData.isEmpty()) {
            extensionMetaData = MetaDataProviderUtil.getExtensionProcessorMetaData();
        }
        if (functions.isEmpty()) {
            for (Map.Entry<String, MetaData> entry : extensionMetaData.entrySet()) {
                if(!entry.getKey().equalsIgnoreCase("IncrementalAggregator")){
                    functions.addAll(entry.getValue().getFunctions());
                }
            }
        }
        return functions;
    }

    /**
     * @return {@link List<ProcessorMetaData>} list of window processor metadata of all the extensions.
     */
    public static List<ProcessorMetaData> getWindowProcessorFunctions() {

        if (extensionMetaData.isEmpty()) {
            extensionMetaData = MetaDataProviderUtil.getExtensionProcessorMetaData();
        }
        if (windowProcessors.isEmpty()) {
            for (Map.Entry<String, MetaData> entry : extensionMetaData.entrySet()) {
                windowProcessors.addAll(entry.getValue().getWindowProcessors());
            }
        }
        return windowProcessors;
    }

    public static List<ProcessorMetaData> getStreamProcessorFunctions(){
        if(extensionMetaData.isEmpty()){
            extensionMetaData = MetaDataProviderUtil.getExtensionProcessorMetaData();
        }
        if(streamProcessors.isEmpty()){
            for (Map.Entry<String, MetaData> entry : extensionMetaData.entrySet()) {
                streamProcessors.addAll(entry.getValue().getStreamProcessors());
            }
        }
        return streamProcessors;
    }

    public static List<ProcessorMetaData> getSources(){
        if(extensionMetaData.isEmpty()){
            extensionMetaData = MetaDataProviderUtil.getExtensionProcessorMetaData();
        }
        if(sources.isEmpty()){
            for (Map.Entry<String, MetaData> entry : extensionMetaData.entrySet()) {
                sources.addAll(entry.getValue().getSources());
            }
        }
        return sources;
    }

    public static List<ProcessorMetaData> getSinks(){
        if(extensionMetaData.isEmpty()){
            extensionMetaData = MetaDataProviderUtil.getExtensionProcessorMetaData();
        }
        if(sinks.isEmpty()){
            for (Map.Entry<String, MetaData> entry : extensionMetaData.entrySet()) {
                sinks.addAll(entry.getValue().getSinks());
            }
        }
        return sinks;
    }

    public static List<ProcessorMetaData> getSourceMaps(){
        if(extensionMetaData.isEmpty()){
            extensionMetaData = MetaDataProviderUtil.getExtensionProcessorMetaData();
        }
        if(sourceMaps.isEmpty()){
            for (Map.Entry<String, MetaData> entry : extensionMetaData.entrySet()) {
                sourceMaps.addAll(entry.getValue().getSourceMaps());
            }
        }
        return sourceMaps;
    }

    public static List<ProcessorMetaData> getSinkMaps(){
        if(extensionMetaData.isEmpty()){
            extensionMetaData = MetaDataProviderUtil.getExtensionProcessorMetaData();
        }
        if(sinkMaps.isEmpty()){
            for (Map.Entry<String, MetaData> entry : extensionMetaData.entrySet()) {
                sinkMaps.addAll(entry.getValue().getSinkMaps());
            }
        }
        return sinkMaps;
    }

    public static List<ProcessorMetaData> getStores(){
        if(extensionMetaData.isEmpty()){
            extensionMetaData = MetaDataProviderUtil.getExtensionProcessorMetaData();
        }
        if(stores.isEmpty()){
            for (Map.Entry<String, MetaData> entry : extensionMetaData.entrySet()) {
                stores.addAll(entry.getValue().getStores());
            }
        }
        return stores;
    }

    public static List<ProcessorMetaData> getAggregatorFunctions(){
        if(extensionMetaData.isEmpty()){
            extensionMetaData = MetaDataProviderUtil.getExtensionProcessorMetaData();
        }
        if(aggregatorFunctions.isEmpty()){
            aggregatorFunctions = extensionMetaData.get("incrementalAggregator").getFunctions();
        }
        return aggregatorFunctions;
    }

    static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {

        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
