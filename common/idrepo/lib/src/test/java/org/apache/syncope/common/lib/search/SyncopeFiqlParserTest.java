package org.apache.syncope.common.lib.search;

import org.apache.cxf.jaxrs.ext.search.SearchBean;
import org.apache.cxf.jaxrs.ext.search.SearchParseException;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class SyncopeFiqlParserTest {

    private static SyncopeFiqlParser<SearchBean> parser = new SyncopeFiqlParser<>(SearchBean.class, AbstractFiqlSearchConditionBuilder.CONTEXTUAL_PROPERTIES);

    private final String field;

    @Parameters
    public static Collection<String> getParameters(){
        return Arrays.asList("fieldName=~value", "fieldName!~value", "");
    }

    public SyncopeFiqlParserTest(String field){
        this.field = field;
    }

    @Test
    public void parseComparisonTest(){
        try {
            Assert.assertNotNull(parser.parseComparison(field));
        } catch (SearchParseException e){
            Assert.assertEquals(field, "");
        }
    }

}