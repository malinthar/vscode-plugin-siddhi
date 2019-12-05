package io.siddhi.langserver.utils.completion;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.eclipse.lsp4j.CompletionItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Message utils for manipulating the Message objects.
 */
public class CompletionTestUtil {

    private static final Gson GSON = new Gson();

    private static String getCompletionItemPropertyString(CompletionItem completionItem) {

        // TODO: Need to add kind and sort text as well
        // Here we replace the Windows specific \r\n to \n for evaluation only
        String additionalTextEdits = "";
        if (completionItem.getAdditionalTextEdits() != null && !completionItem.getAdditionalTextEdits().isEmpty()) {
            additionalTextEdits = "," + GSON.toJson(completionItem.getAdditionalTextEdits());
        }
        return ("{" +
                completionItem.getInsertText() + "," +
                completionItem.getDetail() + "," +
                completionItem.getLabel() + "," + additionalTextEdits + completionItem.getDocumentation() +
                "}").replace("\r\n", "\n").replace("\\r\\n", "\\n");
    }

    public static List<String> getStringListForEvaluation(List<CompletionItem> completionItems) {
        List<String> evalList = new ArrayList<>();
        completionItems.forEach(completionItem -> evalList.add(getCompletionItemPropertyString(completionItem)));
        return evalList;
    }

    public static List<CompletionItem> getExpectedItemList(JsonArray expectedItems) {
        List<CompletionItem> expectedList = new ArrayList<>();
        expectedItems.forEach(jsonElement -> {
            CompletionItem completionItem = GSON.fromJson(jsonElement, CompletionItem.class);
            expectedList.add(completionItem);
        });

        return expectedList;
    }

    /**
     * Check whether list1 is a subset of list2.
     *
     * @param list1 - completion item list being checked
     * @param list2 - completion item list being checked against
     * @return whether list1 is a subset of list2
     */
    public static boolean isSubList(List<CompletionItem> list1, List<CompletionItem> list2) {
        return getStringListForEvaluation(list2).containsAll(getStringListForEvaluation(list1))
                && list1.size() == list2.size();
    }

    /**
     * Check whether list2 does not contains all the elements in list1.
     *
     * @param list1 - negative completion item list being checked
     * @param list2 - completion item list being checked against
     * @return whether list1 is a subset of list2
     */
    public static boolean containsAtLeastOne(List<CompletionItem> list1, List<CompletionItem> list2) {
        List<String> pivotList = getStringListForEvaluation(list2);
        for (String negativeItem : getStringListForEvaluation(list1)) {
            if (pivotList.contains(negativeItem)) {
                return true;
            }
        }
        return false;
    }
}
