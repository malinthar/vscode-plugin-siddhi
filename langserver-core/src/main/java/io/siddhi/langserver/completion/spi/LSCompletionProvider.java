package io.siddhi.langserver.completion.spi;

import io.siddhi.langserver.LSContext;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class LSCompletionProvider {
    protected List<Class> attachmentPoints = new ArrayList<>();


    public abstract List<CompletionItem> getCompletions(LSContext context);


    public List<Class> getAttachmentPoints() {
        return this.attachmentPoints;
    }


}
