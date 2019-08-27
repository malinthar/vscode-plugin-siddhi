package io.siddhi.langserver.completion;

import io.siddhi.langserver.completion.spi.LSCompletionProvider;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

public class LSCompletionProviderFactory  {
    private static final LSCompletionProviderFactory INSTANCE = new LSCompletionProviderFactory();

    private Map<Class, LSCompletionProvider> providers;

    private boolean isInitialized = false;

    private LSCompletionProviderFactory() {
        initiate();
    }

    public static LSCompletionProviderFactory getInstance() {
        return INSTANCE;
    }

    public void initiate() {
        if (isInitialized) {
            return;
        }
        this.providers = new HashMap<>();
        ServiceLoader<LSCompletionProvider> providerServices = ServiceLoader.load(LSCompletionProvider.class);
        for (LSCompletionProvider provider : providerServices) {
           if (provider != null) {
               for (Class attachmentPoint : provider.getAttachmentPoints()) {
                   this.providers.put(attachmentPoint, provider);
                }
            }
        }
        isInitialized = true;
    }
    public void register(LSCompletionProvider provider) {
        for (Class attachmentPoint : provider.getAttachmentPoints()) {
            this.providers.put(attachmentPoint, provider);
        }
    }
    public void unregister(LSCompletionProvider provider) {
        for (Class attachmentPoint : provider.getAttachmentPoints()) {
            this.providers.remove(attachmentPoint, provider);
        }
    }

    public Map<Class, LSCompletionProvider> getProviders() {
        return this.providers;
    }


    public LSCompletionProvider getProvider(Class key) {
        return this.providers.get(key);
    }




}
