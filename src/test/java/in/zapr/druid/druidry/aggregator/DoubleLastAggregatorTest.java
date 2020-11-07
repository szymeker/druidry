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


public class DoubleLastAggregatorTest {

    private static ObjectMapper objectMapper;

    @BeforeClass
    public void init() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testAllFields() throws JsonProcessingException, JSONException {

        DoubleLastAggregator doubleLastAggregator = new DoubleLastAggregator("CarpeDiem",
                "Hey");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "doubleLast");
        jsonObject.put("name", "CarpeDiem");
        jsonObject.put("fieldName", "Hey");

        String actualJSON = objectMapper.writeValueAsString(doubleLastAggregator);
        String expectedJSON = jsonObject.toString();
        JSONAssert.assertEquals(expectedJSON, actualJSON, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testNullName() throws JsonProcessingException, JSONException {

        DoubleLastAggregator doubleLastAggregator = new DoubleLastAggregator(null, "Hey");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testFieldName() throws JsonProcessingException, JSONException {

        DoubleLastAggregator doubleLastAggregator = new DoubleLastAggregator("CarpeDiem", null);
    }

    @Test
    public void testEqualsPositive() {
        DoubleLastAggregator aggregator1 = new DoubleLastAggregator("name", "field");
        DoubleLastAggregator aggregator2 = new DoubleLastAggregator("name", "field");

        Assert.assertEquals(aggregator1, aggregator2);
    }

    @Test
    public void testEqualsNegative() {
        DoubleLastAggregator aggregator1 = new DoubleLastAggregator("name", "field");
        DoubleLastAggregator aggregator2 = new DoubleLastAggregator("name1", "field1");

        Assert.assertNotEquals(aggregator1, aggregator2);
    }

    @Test
    public void testEqualsWithAnotherSubClass() {
        DoubleLastAggregator aggregator1 = new DoubleLastAggregator("name", "field");
        CountAggregator aggregator2 = new CountAggregator("countAgg1");

        Assert.assertNotEquals(aggregator1, aggregator2);
    }

}
