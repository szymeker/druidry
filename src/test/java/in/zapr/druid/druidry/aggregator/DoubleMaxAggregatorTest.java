/*
 * Copyright 2018-present Red Brick Lane Marketing Solutions Pvt. Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package in.zapr.druid.druidry.aggregator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class DoubleMaxAggregatorTest {

    private static ObjectMapper objectMapper;

    @BeforeClass
    public void init() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testAllFields() throws JsonProcessingException, JSONException {

        DoubleMaxAggregator doubleMaxAggregator = new DoubleMaxAggregator("CarpeDiem",
                "Hey");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "doubleMax");
        jsonObject.put("name", "CarpeDiem");
        jsonObject.put("fieldName", "Hey");

        String actualJSON = objectMapper.writeValueAsString(doubleMaxAggregator);
        String expectedJSON = jsonObject.toString();
        JSONAssert.assertEquals(expectedJSON, actualJSON, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test
    public void testAllButFieldName() throws JSONException, JsonProcessingException {

        DoubleMaxAggregator doubleMaxAggregator =
                DoubleMaxAggregator.builder()
                        .name("CarpeDiem")
                        .expression("(\"foo\" / \"bar\")")
                        .build();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "doubleMax");
        jsonObject.put("name", "CarpeDiem");
        jsonObject.put("expression", "(\"foo\" / \"bar\")");

        String actualJSON = objectMapper.writeValueAsString(doubleMaxAggregator);
        String expectedJSON = jsonObject.toString();
        JSONAssert.assertEquals(expectedJSON, actualJSON, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testNullName() {
        DoubleMaxAggregator doubleMaxAggregator =
                DoubleMaxAggregator.builder()
                        .fieldName("Haha")
                        .build();
    }

    @Test
    public void testEqualsPositive() {
        DoubleMaxAggregator aggregator1 =
                DoubleMaxAggregator.builder()
                        .name("name")
                        .fieldName("field")
                        .build();

        DoubleMaxAggregator aggregator2 =
                DoubleMaxAggregator.builder()
                        .name("name")
                        .fieldName("field")
                        .build();

        DoubleMaxAggregator aggregator3 =
                DoubleMaxAggregator.builder()
                        .name("name")
                        .expression("(\"foo\" / \"bar\")")
                        .build();

        DoubleMaxAggregator aggregator4 =
                DoubleMaxAggregator.builder()
                        .name("name")
                        .expression("(\"foo\" / \"bar\")")
                        .build();

        Assert.assertEquals(aggregator1, aggregator2);
        Assert.assertEquals(aggregator3, aggregator4);
    }

    @Test
    public void testEqualsNegative() {
        DoubleMaxAggregator aggregator1 =
                DoubleMaxAggregator.builder()
                        .name("name")
                        .fieldName("field")
                        .build();

        DoubleMaxAggregator aggregator2 =
                DoubleMaxAggregator.builder()
                        .name("name1")
                        .fieldName("field1")
                        .build();

        DoubleMaxAggregator aggregator3 =
                DoubleMaxAggregator.builder()
                        .name("name")
                        .expression("(\"foo\" / \"bar\")")
                        .build();

        DoubleMaxAggregator aggregator4 =
                DoubleMaxAggregator.builder()
                        .name("name")
                        .expression("(\"foo\" / \"baz\")")
                        .build();

        Assert.assertNotEquals(aggregator1, aggregator2);
        Assert.assertNotEquals(aggregator3, aggregator4);
    }

    @Test
    public void testEqualsWithAnotherSubClass() {
        DoubleMaxAggregator aggregator1 =
                DoubleMaxAggregator.builder()
                        .name("name")
                        .fieldName("field")
                        .build();

        CountAggregator aggregator2 = new CountAggregator("countAgg1");

        Assert.assertNotEquals(aggregator1, aggregator2);
    }

}
