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

package in.zapr.druid.druidry.extensions.datasketches.postAggregator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import in.zapr.druid.druidry.postAggregator.FieldAccessPostAggregator;
import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class QuantilesSketchToQuantilePostAggregatorTest {

    private static ObjectMapper objectMapper;

    private FieldAccessPostAggregator starAgeQuantilesSketch;

    @BeforeClass
    public void init() {
        objectMapper = new ObjectMapper();
        starAgeQuantilesSketch = new FieldAccessPostAggregator("star_age_quantiles_sketch");
    }

    private JSONObject getQuantilesSketchToQuantilePostAggregatorJSON() throws JSONException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "quantilesDoublesSketchToQuantile");
        jsonObject.put("name", "star_age_quantile");
        return jsonObject;
    }

    private JSONObject getFieldAccessPostAggregatorJSON(String fieldName) throws JSONException {
        JSONObject fieldAccess = new JSONObject();
        fieldAccess.put("type", "fieldAccess");
        fieldAccess.put("fieldName", fieldName);

        return fieldAccess;
    }

    @Test
    public void testAllFields() throws JsonProcessingException, JSONException {

        QuantilesSketchToQuantilePostAggregator quantilesSketchToQuantilePostAggregator =
                QuantilesSketchToQuantilePostAggregator.builder()
                        .name("star_age_quantile")
                        .field(starAgeQuantilesSketch)
                        .fraction(1.1D)
                        .build();

        JSONObject jsonObject = getQuantilesSketchToQuantilePostAggregatorJSON();
        jsonObject.put("field", getFieldAccessPostAggregatorJSON("star_age_quantiles_sketch"));
        jsonObject.put("fraction", 1.1D);

        String actualJSON = objectMapper.writeValueAsString(quantilesSketchToQuantilePostAggregator);
        String expectedJSON = jsonObject.toString();
        JSONAssert.assertEquals(expectedJSON, actualJSON, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testNullName() {

        QuantilesSketchToQuantilePostAggregator quantilesSketchToQuantilePostAggregator =
                QuantilesSketchToQuantilePostAggregator.builder()
                        .field(starAgeQuantilesSketch)
                        .fraction(1.1D)
                        .build();
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testNullField() {

        QuantilesSketchToQuantilePostAggregator quantilesSketchToQuantilePostAggregator =
                QuantilesSketchToQuantilePostAggregator.builder()
                        .name("star_age_quantile")
                        .fraction(1.1D)
                        .build();
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testNullFraction() {

        QuantilesSketchToQuantilePostAggregator quantilesSketchToQuantilePostAggregator =
                QuantilesSketchToQuantilePostAggregator.builder()
                        .name("star_age_quantile")
                        .field(starAgeQuantilesSketch)
                        .build();
    }
}