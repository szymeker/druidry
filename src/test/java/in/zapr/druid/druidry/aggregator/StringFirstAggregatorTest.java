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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class StringFirstAggregatorTest {

    private static ObjectMapper objectMapper;

    @BeforeClass
    public void init() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testRequiredFields() throws JsonProcessingException, JSONException {
        String name = "stringFirstNameRequired";
        String fieldName = "stringFirstFieldNameRequired";

        DruidAggregator stringFirstAggregator = StringFirstAggregator.builder()
                .name(name)
                .fieldName(fieldName)
                .build();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "stringFirst");
        jsonObject.put("name", name);
        jsonObject.put("fieldName", fieldName);

        String actualJSON = objectMapper.writeValueAsString(stringFirstAggregator);
        String expectedJSON = jsonObject.toString();
        JSONAssert.assertEquals(expectedJSON, actualJSON, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test
    public void testAllFields() throws JsonProcessingException, JSONException {
        String name = "stringFirstNameAll";
        String fieldName = "stringFirstFieldNameAll";
        int maxStringBytes = 300;

        DruidAggregator stringFirstAggregator = StringFirstAggregator.builder()
                .name(name)
                .fieldName(fieldName)
                .maxStringBytes(maxStringBytes)
                .build();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "stringFirst");
        jsonObject.put("name", name);
        jsonObject.put("fieldName", fieldName);
        jsonObject.put("maxStringBytes", maxStringBytes);

        String actualJSON = objectMapper.writeValueAsString(stringFirstAggregator);
        String expectedJSON = jsonObject.toString();
        JSONAssert.assertEquals(expectedJSON, actualJSON, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void tryToCreateWithoutName() {
        StringFirstAggregator.builder()
                .fieldName("fieldName")
                .build();
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void tryToCreateWithoutFieldName() {
        StringFirstAggregator.builder()
                .name("name")
                .build();
    }

}
