package io.siddhi.langserver.completion.attributetype;

import io.siddhi.langserver.completion.CompletionTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;

/**
 * AttributeTypeCompletionTestCase.
 */
public class AttributeTypeCompletionTestCase extends CompletionTest {
    private static final Logger log = LoggerFactory.getLogger(AttributeTypeCompletionTestCase.class);

    @DataProvider(name = "completion-data-provider")
    @Override
    public Object[][] dataProvider() {
        log.info("Test textDocument/completion for attribute type");
        return new Object[][]{
                {"AttributeTypeCompletionTest.json", "attributetype"},
        };
    }

}
