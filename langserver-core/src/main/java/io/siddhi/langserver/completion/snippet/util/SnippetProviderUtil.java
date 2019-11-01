package io.siddhi.langserver.completion.snippet.util;

import io.siddhi.langserver.completion.snippet.util.metadata.MetaData;
import io.siddhi.langserver.completion.snippet.util.metadata.AttributeMetaData;
import io.siddhi.langserver.completion.snippet.util.metadata.ParameterMetaData;
import io.siddhi.langserver.completion.snippet.util.metadata.ProcessorMetaData;

import io.siddhi.annotation.Extension;
import io.siddhi.annotation.Parameter;
import io.siddhi.annotation.ParameterOverload;
import io.siddhi.annotation.ReturnAttribute;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.io.IOException;
import io.siddhi.core.SiddhiManager;
import java.io.File;
import java.io.FileInputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;


public class SnippetProviderUtil {


    public static MetaData getInBuiltProcessorMetaData() {

        Map<String, Set<Class<?>>> processorClassMap = getClassesInClassPathFromPackages();
        return generateInBuiltMetaData(processorClassMap);
    }
    public static Map<String, MetaData> getExtensionProcessorMetaData() {
        SiddhiManager  manager=new SiddhiManager();
        Map<String, Class> extensionsMap = manager.getExtensions();
        return generateExtensionsMetaData(extensionsMap);
    }

    /**
     * Returns the extension processor meta data.
     * Gets the meta data from the siddhi manager
     *
     * @return Extension processor meta data
     */


    /**
     * Returns processor types to Classes map with classes in the packages in processor type to package name map.
     *
     * @return Processor types to Classes map
     */
    private static Map<String, Set<Class<?>>> getClassesInClassPathFromPackages() {

        String[] classPathNames = System.getProperty("java.class.path").split(File.pathSeparator);
        Map<String, Set<Class<?>>> classSetMap = new HashMap<>();
        // Looping the jars
        for (String classPathName : classPathNames) {
            if (classPathName.endsWith(".jar")) {
                JarInputStream stream = null;
                try {
                    stream = new JarInputStream(new FileInputStream(classPathName));
                    JarEntry jarEntry = stream.getNextJarEntry();
                    // Looping the classes in jar to get classes in the specified package
                    while (jarEntry != null) {
                        /*
                         * Path separator for linux and windows machines needs to be replaces separately
                         * The path separator in the jar entries depends on the machine where the jar was built
                         */
                        String jarEntryName = jarEntry.getName().replace("/", ".");
                        jarEntryName = jarEntryName.replace("\\", ".");

                        try {
                            // Looping the set of packages
                            for (Map.Entry<String, String> entry : Constants.PACKAGE_NAME_MAP.entrySet()) {
                                if (jarEntryName.endsWith(".class") && jarEntryName.startsWith(entry.getValue())) {
                                    Set<Class<?>> classSet = classSetMap.get(entry.getKey());
                                    if (classSet == null) {
                                        classSet = new HashSet<>();
                                        classSetMap.put(entry.getKey(), classSet);
                                    }
                                    classSet.add(Class.forName(jarEntryName.substring(0, jarEntryName.length() - 6)));
                                }
                            }
                        } catch (ClassNotFoundException e) {
                           // LOGGER.debug("Failed to load class " +
                                    //jarEntryName.substring(0, jarEntryName.length() - 6), e);
                        }
                        jarEntry = stream.getNextJarEntry();
                    }
                } catch (IOException e) {
                   // LOGGER.debug("Failed to open the jar input stream for " + classPathName, e);
                } finally {
                    if (stream != null) {
                        try {
                            stream.close();
                        } catch (IOException e) {
                           // LOGGER.debug("Failed to close the jar input stream for " + classPathName, e);
                        }
                    }
                }
            }
        }
        return classSetMap;
    }
    private static MetaData generateInBuiltMetaData(Map<String, Set<Class<?>>> classMap) {

        MetaData metaData = new MetaData();

        // Generating the function meta data list containing function executors and attribute aggregators
        List<ProcessorMetaData> functionMetaData = new ArrayList<>();
        populateInBuiltProcessorMetaDataList(functionMetaData, classMap, Constants.FUNCTION_EXECUTOR);
        populateInBuiltProcessorMetaDataList(functionMetaData, classMap, Constants.ATTRIBUTE_AGGREGATOR);
        metaData.setFunctions(functionMetaData);

        // Generating the stream processor meta data list containing stream processor and stream function
        List<ProcessorMetaData> streamProcessorMetaData = new ArrayList<>();
        populateInBuiltProcessorMetaDataList(streamProcessorMetaData, classMap, Constants.STREAM_FUNCTION_PROCESSOR);
        populateInBuiltProcessorMetaDataList(streamProcessorMetaData, classMap, Constants.STREAM_PROCESSOR);
        metaData.setStreamProcessors(streamProcessorMetaData);

        // Generating the window processor meta data list
        List<ProcessorMetaData> windowProcessorMetaData = new ArrayList<>();
        populateInBuiltProcessorMetaDataList(windowProcessorMetaData, classMap, Constants.WINDOW_PROCESSOR);
        metaData.setWindowProcessors(windowProcessorMetaData);

        return metaData;
    }
    private static void populateInBuiltProcessorMetaDataList(List<ProcessorMetaData> targetProcessorMetaDataList,
                                                             Map<String, Set<Class<?>>> classMap,
                                                             String processorType) {

        Set<Class<?>> classSet = classMap.get(processorType);
        if (classSet != null) {
            for (Class<?> processorClass : classSet) {
                ProcessorMetaData processorMetaData = generateProcessorMetaData(processorClass, processorType);
                if (processorMetaData != null) {
                    targetProcessorMetaDataList.add(processorMetaData);
                }
            }
        }
    }
    private static ProcessorMetaData generateProcessorMetaData(Class<?> processorClass,
                                                               String processorType) {

        String processorName = processorClass.getName();
        // Getting the class name
        processorName = processorName.substring(processorName.lastIndexOf('.') + 1);
        // Removing the super class postfix
        processorName = processorName.replace(processorType, "");

        // Check if the processor class is a subclass of the super class and not the superclass itself
        // This check is important because the inbuilt processor scan retrieves the super classes as well
        if (!Constants.SUPER_CLASS_MAP.get(processorType).equals(processorClass)) {
            processorName = processorName.substring(0, 1).toLowerCase(Locale.getDefault()) + processorName.substring(1);
            return generateProcessorMetaData(processorClass, processorType, processorName);
        } else {
            return null;
        }
    }
    private static ProcessorMetaData generateProcessorMetaData(Class<?> processorClass, String processorType,
                                                               String processorName) {

        ProcessorMetaData processorMetaData = null;

        Extension extensionAnnotation = processorClass.getAnnotation(Extension.class);

        if (extensionAnnotation != null) {
            processorMetaData = new ProcessorMetaData();
            processorMetaData.setName(processorName);

            // Adding Description annotation data
            processorMetaData.setDescription(extensionAnnotation.description());

            // Adding Namespace annotation data
            processorMetaData.setNamespace(extensionAnnotation.namespace());

            // Adding Parameter annotation data
            if (extensionAnnotation.parameters().length > 0) {
                // When multiple parameters are present
                List<ParameterMetaData> parameterMetaDataList = new ArrayList<>();
                for (Parameter parameter : extensionAnnotation.parameters()) {
                    ParameterMetaData parameterMetaData = new ParameterMetaData();
                    parameterMetaData.setName(parameter.name());
                    parameterMetaData.setType(Arrays.asList(parameter.type()));
                    parameterMetaData.setOptional(parameter.optional());
                    parameterMetaData.setDescription(parameter.description());
                    parameterMetaData.setDefaultValue(parameter.defaultValue());
                    parameterMetaDataList.add(parameterMetaData);
                }
                processorMetaData.setParameters(parameterMetaDataList);
            }

            // Adding ReturnEvent annotation data
            // Adding return event additional attributes
            if (Constants.WINDOW_PROCESSOR.equals(processorType) ||
                    Constants.STREAM_PROCESSOR.equals(processorType) ||
                    Constants.STREAM_FUNCTION_PROCESSOR.equals(processorType) ||
                    Constants.FUNCTION_EXECUTOR.equals(processorType)) {
                List<AttributeMetaData> attributeMetaDataList = new ArrayList<>();
                if (extensionAnnotation.returnAttributes().length > 0) {
                    for (ReturnAttribute additionalAttribute : extensionAnnotation.returnAttributes()) {
                        AttributeMetaData attributeMetaData = new AttributeMetaData();
                        attributeMetaData.setName(additionalAttribute.name());
                        attributeMetaData.setType(Arrays.asList(additionalAttribute.type()));
                        attributeMetaData.setDescription(additionalAttribute.description());
                        attributeMetaDataList.add(attributeMetaData);
                    }
                }
                processorMetaData.setReturnAttributes(attributeMetaDataList);
            }

            // Adding Example annotation data
            if (extensionAnnotation.examples().length > 0) {
                String examples[] = new String[extensionAnnotation.examples().length];
                for (int i = 0; i < extensionAnnotation.examples().length; i++) {
                    examples[i] = "syntax: " + extensionAnnotation.examples()[i].syntax() + "\n" +
                            "description: " + extensionAnnotation.examples()[i].description();
                }
                processorMetaData.setExamples(examples);
            }

            //Adding parameter overloads data
            if (extensionAnnotation.parameterOverloads().length > 0) {
                List<String[]> parameterOverloads = new ArrayList<>();
                for (ParameterOverload parameterOverload : extensionAnnotation.parameterOverloads()) {
                    parameterOverloads.add(parameterOverload.parameterNames());
                }
                processorMetaData.setParameterOverloads(parameterOverloads);
            }
        }
        return processorMetaData;
    }
    private static Map<String, MetaData> generateExtensionsMetaData(Map<String, Class> extensionsMap) {

        Map<String, MetaData> metaDataMap = new HashMap<>();
        for (Map.Entry<String, Class> entry : extensionsMap.entrySet()) {
            String namespace = "";
            String processorName;
            if (entry.getKey().contains(":")) {
                namespace = entry.getKey().split(":")[0];
                processorName = entry.getKey().split(":")[1];
            } else {
                processorName = entry.getKey();
            }

            MetaData metaData = metaDataMap.computeIfAbsent(namespace, k -> new MetaData());

            Class<?> extensionClass = entry.getValue();
            String processorType = null;
            List<ProcessorMetaData> processorMetaDataList = null;
            if (Constants.SUPER_CLASS_MAP.get(Constants.FUNCTION_EXECUTOR)
                    .isAssignableFrom(extensionClass)) {
                processorType = Constants.FUNCTION_EXECUTOR;
                processorMetaDataList = metaData.getFunctions();
            } else if (Constants.SUPER_CLASS_MAP.get(Constants.ATTRIBUTE_AGGREGATOR)
                    .isAssignableFrom(extensionClass)) {
                processorType = Constants.ATTRIBUTE_AGGREGATOR;
                processorMetaDataList = metaData.getFunctions();
            } else if (Constants.SUPER_CLASS_MAP.get(Constants.INCREMENTAL_AGGREGATOR)
                    .isAssignableFrom(extensionClass)) {
                processorType = Constants.INCREMENTAL_AGGREGATOR;
                processorMetaDataList = metaData.getFunctions();
            } else if (Constants.SUPER_CLASS_MAP.get(Constants.STREAM_FUNCTION_PROCESSOR)
                    .isAssignableFrom(extensionClass)) {
                processorType = Constants.STREAM_FUNCTION_PROCESSOR;
                processorMetaDataList = metaData.getStreamProcessors();
            } else if (Constants.SUPER_CLASS_MAP.get(Constants.STREAM_PROCESSOR)
                    .isAssignableFrom(extensionClass)) {
                processorType = Constants.STREAM_PROCESSOR;
                processorMetaDataList = metaData.getStreamProcessors();
            } else if (Constants.SUPER_CLASS_MAP.get(Constants.WINDOW_PROCESSOR)
                    .isAssignableFrom(extensionClass)) {
                processorType = Constants.WINDOW_PROCESSOR;
                processorMetaDataList = metaData.getWindowProcessors();
            } else if (Constants.SUPER_CLASS_MAP.get(Constants.SOURCE).isAssignableFrom(extensionClass)) {
                processorType = Constants.SOURCE;
                processorMetaDataList = metaData.getSources();
            } else if (Constants.SUPER_CLASS_MAP.get(Constants.SINK).isAssignableFrom(extensionClass)) {
                processorType = Constants.SINK;
                processorMetaDataList = metaData.getSinks();
            } else if (Constants.SUPER_CLASS_MAP.get(Constants.SOURCEMAP).isAssignableFrom(extensionClass)) {
                processorType = Constants.SOURCEMAP;
                processorMetaDataList = metaData.getSourceMaps();
            } else if (Constants.SUPER_CLASS_MAP.get(Constants.SINKMAP).isAssignableFrom(extensionClass)) {
                processorType = Constants.SINKMAP;
                processorMetaDataList = metaData.getSinkMaps();
            } else if (Constants.SUPER_CLASS_MAP.get(Constants.STORE).isAssignableFrom(extensionClass)) {
                processorType = Constants.STORE;
                processorMetaDataList = metaData.getStores();
            }

            if (processorMetaDataList != null) {
                ProcessorMetaData processorMetaData =
                        generateProcessorMetaData(extensionClass, processorType, processorName);

                if (processorMetaData != null) {
                    processorMetaDataList.add(processorMetaData);
                }
            }
            /*
            else {
                LOGGER.warn("Discarded extension " + extensionClass.getCanonicalName() +
                        " belonging to an unknown type ");
            }
            */
        }
        return metaDataMap;
    }

}
