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

package in.zapr.druid.druidry.dimension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;

public class ListFilteredDimensionTest {
    private static ObjectMapper objectMapper;

    @BeforeClass
    public void init() {
        objectMapper = new ObjectMapper();
    }


    @Test
    public void testListFilteredDimension() throws JsonProcessingException, JSONException {
        DimensionSpec dimensionSpec = DefaultDimension.builder()
                .dimension("system_label_values")
                .outputName("system_label_values")
                .build();

        ListFilteredDimension listFilteredDimension = ListFilteredDimension.builder()
                .dimensionSpec(dimensionSpec)
                .values(Arrays.asList("compute.googleapis.com/cores`1"))
                .whitelist(true)
                .build();

        String jsonOutput = objectMapper.writeValueAsString(listFilteredDimension);
        String expectedJSONString = "{\n" +
                "      \"type\": \"listFiltered\",\n" +
                "      \"delegate\": {\n" +
                "        \"type\": \"default\",\n" +
                "        \"dimension\": \"system_label_values\",\n" +
                "        \"outputName\": \"system_label_values\"\n" +
                "      },\n" +
                "      \"values\": [\"compute.googleapis.com/cores`1\"],\n" +
                "      \"isWhitelist\":true\n" +
                "    }";
        JSONAssert.assertEquals(expectedJSONString, jsonOutput, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test
    public void testListFilteredDimensionIsWhiteListedFalse() throws JsonProcessingException, JSONException {
        DimensionSpec dimensionSpec = DefaultDimension.builder()
                .dimension("system_label_values")
                .outputName("system_label_values")
                .build();

        ListFilteredDimension listFilteredDimension = ListFilteredDimension.builder()
                .dimensionSpec(dimensionSpec)
                .values(Arrays.asList("compute.googleapis.com/cores`1"))
                .whitelist(false)
                .build();

        String jsonOutput = objectMapper.writeValueAsString(listFilteredDimension);
        String expectedJSONString = "{\n" +
                "      \"type\": \"listFiltered\",\n" +
                "      \"delegate\": {\n" +
                "        \"type\": \"default\",\n" +
                "        \"dimension\": \"system_label_values\",\n" +
                "        \"outputName\": \"system_label_values\"\n" +
                "      },\n" +
                "      \"values\": [\"compute.googleapis.com/cores`1\"],\n" +
                "      \"isWhitelist\":false\n" +
                "    }";
        JSONAssert.assertEquals(expectedJSONString, jsonOutput, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test
    public void testListFilteredDimensionIsWhiteListedDefault() throws JsonProcessingException, JSONException {
        DimensionSpec dimensionSpec = DefaultDimension.builder()
                .dimension("system_label_values")
                .outputName("system_label_values")
                .build();

        ListFilteredDimension listFilteredDimension = ListFilteredDimension.builder()
                .dimensionSpec(dimensionSpec)
                .values(Arrays.asList("compute.googleapis.com/cores`1"))
                .build();

        String jsonOutput = objectMapper.writeValueAsString(listFilteredDimension);
        String expectedJSONString = "{\n" +
                "      \"type\": \"listFiltered\",\n" +
                "      \"delegate\": {\n" +
                "        \"type\": \"default\",\n" +
                "        \"dimension\": \"system_label_values\",\n" +
                "        \"outputName\": \"system_label_values\"\n" +
                "      },\n" +
                "      \"values\": [\"compute.googleapis.com/cores`1\"]\n" +
                "    }";
        JSONAssert.assertEquals(expectedJSONString, jsonOutput, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testListFilteredDimensionWithNullDimension() throws JsonProcessingException {
        ListFilteredDimension listFilteredDimension = ListFilteredDimension.builder()
                .values(Arrays.asList("compute.googleapis.com/cores`1"))
                .build();
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testListFilteredDimensionWithNullList() throws JsonProcessingException {
        DimensionSpec dimensionSpec = DefaultDimension.builder()
                .dimension("system_label_values")
                .outputName("system_label_values")
                .build();

        ListFilteredDimension listFilteredDimension = ListFilteredDimension.builder()
                .dimensionSpec(dimensionSpec)
                .build();
    }


}
