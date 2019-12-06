/*
 * Copyright (c) 2019, WSO2 Inc. (http://wso2.com) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
 * Copyright (c) 2019, WSO2 Inc. (http://wso2.com) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.siddhi.langserver.completion.providers;

import io.siddhi.langserver.LSOperationContext;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionItemKind;
import org.eclipse.lsp4j.InsertTextFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * {@code LSCompletionProvider} CompletionProvider SPI.
 */
public abstract class CompletionProvider {

    protected String providerName;

    public abstract List<CompletionItem> getCompletions();

    public String getProviderName() {
        return this.providerName;
    }

    public ParserRuleContext getParent() {
        Map<String, ParseTree> map = LSOperationContext.INSTANCE.getParseTreeMap();
        ParserRuleContext parent = (ParserRuleContext) map.get(this.providerName).getParent();
        return parent;
    }

    public static List<CompletionItem> generateCompletionList(List<Object[]> suggestions) {
        List<CompletionItem> completionItems = new ArrayList<>();
        if (suggestions != null) {
            for (Object[] suggestion : suggestions) {
                if (suggestion.length == 6) {
                    CompletionItem completionItem = new CompletionItem();
                    completionItem.setInsertText((String) suggestion[0]);
                    completionItem.setLabel((String) suggestion[1]);
                    completionItem.setKind((CompletionItemKind) suggestion[2]);
                    completionItem.setDetail((String) suggestion[3]);
                    completionItem.setFilterText((String) suggestion[4]);
                    completionItem.setInsertTextFormat((InsertTextFormat) suggestion[5]);
                    completionItems.add(completionItem);
                } else {
                    //todo: log the whatever that is dropped, when the logging framework is implemented.
                }
            }
        }
        return completionItems;
    }
}
