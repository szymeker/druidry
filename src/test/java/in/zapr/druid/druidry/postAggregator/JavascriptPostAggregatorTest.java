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

package in.zapr.druid.druidry.postAggregator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class JavascriptPostAggregatorTest {

    private static ObjectMapper objectMapper;

    @BeforeClass
    public void init() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testAllFields() throws JsonProcessingException, JSONException {

        List<String> fields = Arrays.asList("Cardinal", "Aggregator");

        JavaScriptPostAggregator javaScriptPostAggregator = JavaScriptPostAggregator.builder()
                .name("Hello")
                .fieldNames(fields)
                .function("fn")
                .build();

        JSONArray fieldJsonArray = new JSONArray(fields);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "javascript");
        jsonObject.put("name", "Hello");
        jsonObject.put("fieldNames", fieldJsonArray);
        jsonObject.put("function", "fn");

        String actualJSON = objectMapper.writeValueAsString(javaScriptPostAggregator);
        String expectedJSON = jsonObject.toString();
        JSONAssert.assertEquals(expectedJSON, actualJSON, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testMissingNameField() {

        List<String> fields = Arrays.asList("Cardinal", "Aggregator");

        JavaScriptPostAggregator javaScriptPostAggregator = JavaScriptPostAggregator.builder()
                .fieldNames(fields)
                .function("fn")
                .build();
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testMissingFieldsField() {

        JavaScriptPostAggregator javaScriptPostAggregator = JavaScriptPostAggregator.builder()
                .name("Hello")
                .function("fn")
                .build();
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testMissingFunctionField() {

        List<String> fields = Arrays.asList("Cardinal", "Aggregator");

        JavaScriptPostAggregator javaScriptPostAggregator = JavaScriptPostAggregator.builder()
                .name("Hello")
                .fieldNames(fields)
                .build();
    }

    @Test
    public void testEqualsPositive() {
        JavaScriptPostAggregator aggregator1 = JavaScriptPostAggregator.builder()
                .name("Hello")
                .fieldNames(Collections.singletonList("Field"))
                .function("fn")
                .build();

        JavaScriptPostAggregator aggregator2 = JavaScriptPostAggregator.builder()
                .name("Hello")
                .fieldNames(Collections.singletonList("Field"))
                .function("fn")
                .build();

        Assert.assertEquals(aggregator1, aggregator2);
    }

    @Test
    public void testEqualsNegative() {
        JavaScriptPostAggregator aggregator1 = JavaScriptPostAggregator.builder()
                .name("Hello")
                .fieldNames(Collections.singletonList("Field"))
                .function("fn")
                .build();

        JavaScriptPostAggregator aggregator2 = JavaScriptPostAggregator.builder()
                .name("Hello")
                .fieldNames(Collections.singletonList("Field"))
                .function("helloWordd()")
                .build();

        Assert.assertNotEquals(aggregator1, aggregator2);
    }

    @Test
    public void testEqualsWithAnotherSubClass() {
        JavaScriptPostAggregator aggregator1 = JavaScriptPostAggregator.builder()
                .name("Hello")
                .fieldNames(Collections.singletonList("Field"))
                .function("fn")
                .build();
        FieldAccessPostAggregator aggregator2
                = new FieldAccessPostAggregator("Hello", "Yaha");

        Assert.assertNotEquals(aggregator1, aggregator2);
    }
}