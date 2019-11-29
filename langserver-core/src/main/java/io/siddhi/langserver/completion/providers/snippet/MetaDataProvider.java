package io.siddhi.langserver.completion.providers.snippet;
//
//import io.siddhi.langserver.LSOperationContext;
//import io.siddhi.query.compiler.SiddhiQLParser;
//import org.eclipse.lsp4j.CompletionItem;
//import org.eclipse.lsp4j.CompletionItemKind;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * {@code SnippetProvider} provide snippets based on context.
// */
public class SnippetProvider {
//    public Object getSnippets(SiddhiQLParser.QueryContext context, LSOperationContext lsContext) {
//        SnippetGenerator snippet = new SnippetGenerator();
//        List<String[]> snips = (ArrayList) snippet.getQueryContext(lsContext);
//        return this.generateCompletions(snips, lsContext);
//    }
//
//    public Object getSnippets(SiddhiQLParser.Query_inputContext context, LSOperationContext lsContext) {
//        SnippetGenerator snippet = new SnippetGenerator();
//        List<String[]> snips = (ArrayList) snippet.getQueryInput(lsContext);
//        return this.generateCompletions(snips, lsContext);
//
//    }
//
//    public  Object getSnippets(SiddhiQLParser.Query_sectionContext context, LSOperationContext lsContext) {
//        SnippetGenerator snippet = new SnippetGenerator();
//        List<String[]> snips = (ArrayList) snippet.getQuerySection(lsContext);
//        return this.generateCompletions(snips, lsContext);
//    }
//    public Object getSnippets(SiddhiQLParser.Attribute_referenceContext context, LSOperationContext lsContext) {
//        SnippetGenerator snippet = new SnippetGenerator();
//        List<String[]> snips = (ArrayList) snippet.getAttributeReference(lsContext);
//        return this.generateCompletions(snips, lsContext);
//    }
//
//    public  Object getSnippets(SiddhiQLParser.Attribute_typeContext context, LSOperationContext lsContext) {
//        SnippetGenerator snippet = new SnippetGenerator();
//        List<String[]> snips = (ArrayList) snippet.getAttributetype(lsContext);
//        return this.generateCompletions(snips, lsContext);
//    }
//
//    public Object getSnippets(SiddhiQLParser.Siddhi_appContext context, LSOperationContext lsContext) {
//        SnippetGenerator snippet = new SnippetGenerator();
//        List<String[]> snips = (ArrayList) snippet.getSiddhiAppSnippets(lsContext);
//        return this.generateCompletions(snips, lsContext);
//    }
//
//    public Object getSnippets(SiddhiQLParser.Output_attributeContext context, LSOperationContext lsContext) {
//        SnippetGenerator snippet = new SnippetGenerator();
//        List<String[]> snips = (ArrayList) snippet.getOutputAttribute(lsContext);
//        return this.generateCompletions(snips, lsContext);
//    }
//
//    public Object getSnippets(SiddhiQLParser.NameContext context, LSOperationContext lsContext) {
//        SnippetGenerator snippet = new SnippetGenerator();
//        List<String[]> snips = (ArrayList) snippet.getName(lsContext);
//        return this.generateCompletions(snips, lsContext);
//    }
//
//    public Object getSnippets(SiddhiQLParser.App_annotationContext context, LSOperationContext lsContext) {
//        SnippetGenerator snippet = new SnippetGenerator();
//        List<String[]> snips = (ArrayList) snippet.getAnnotationElements(lsContext);
//        return this.generateCompletions(snips, lsContext);
//    }
//
//    public Object getSnippets(SiddhiQLParser.Annotation_elementContext context, LSOperationContext lsContext) {
//        SnippetGenerator snippet = new SnippetGenerator();
//        List<String[]> snips = (ArrayList) snippet.getAnnotationElements(lsContext);
//        return this.generateCompletions(snips, lsContext);
//    }
//
//    public Object getSnippets(SiddhiQLParser.AnnotationContext context, LSOperationContext lsContext) {
//        SnippetGenerator snippet = new SnippetGenerator();
//        List<String[]> snips = (ArrayList) snippet.getAnnotations(lsContext);
//        return this.generateCompletions(snips, lsContext);
//    }
//
//    public Object getSnippets(SiddhiQLParser.SourceContext context, LSOperationContext lsContext) {
//        SnippetGenerator snippet = new SnippetGenerator();
//        List<String[]> snips = (ArrayList) snippet.getSourceContext(lsContext);
//        return this.generateCompletions(snips, lsContext);
//    }
//
//    public  Object getSnippets(SiddhiQLParser.Definition_functionContext context, LSOperationContext lsContext) {
//        SnippetGenerator snippet = new SnippetGenerator();
//        List<String[]> snips = (ArrayList) snippet.getDefFuncContext(lsContext);
//        return this.generateCompletions(snips, lsContext);
//    }
//
//    public List<CompletionItem> generateCompletions(List<String[]> suggestions, LSOperationContext lsContext) {
//        List<CompletionItem> completionItems = new ArrayList<>();
//        for (String[] suggestion:suggestions) {
//            CompletionItem completionItem = new CompletionItem();
//            completionItem.setInsertText(suggestion[0]);
//            completionItem.setLabel(suggestion[1]);
//            completionItem.setKind(CompletionItemKind.Snippet);
//            completionItem.setDetail(suggestion[2]);
//            completionItem.setFilterText(suggestion[3]);
//            completionItems.add(completionItem);
//        }
//        return completionItems;
//    }
//
//
}
