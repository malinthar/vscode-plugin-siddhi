package io.siddhi.langserver.completion;

import io.siddhi.langserver.completion.providers.spi.LSCompletionProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * {@code CompletionProviderFactory} factory for completion providers.
 */
public class LSCompletionProviderFactory  {
    private static final LSCompletionProviderFactory INSTANCE = new LSCompletionProviderFactory();

    private Map<String, LSCompletionProvider> providers;

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
               this.providers.put(provider.getAttachmentContext(), provider);
            }
        }
        isInitialized = true;
    }

    public void register(LSCompletionProvider provider) {
        this.providers.put(provider.getAttachmentContext(), provider);
    }

    public void unregister(LSCompletionProvider provider) {
        this.providers.remove(provider.getAttachmentContext(), provider);
    }

    public Map<String, LSCompletionProvider> getProviders() {
        return this.providers;
    }

    public LSCompletionProvider getProvider(Class key) {
        return this.providers.get(key);
    }
}
