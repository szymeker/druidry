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

package in.zapr.druid.druidry.query.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class IntervalTest {

    @Test(expectedExceptions = NullPointerException.class)
    public void testMissingStartField() {

        ZonedDateTime startTime = ZonedDateTime.now();
        Interval interval = new Interval(startTime, null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testMissingEndField() {
        ZonedDateTime endTime = ZonedDateTime.now();
        Interval interval = new Interval(null, endTime);
    }

    @Test
    public void intervalEqualityTest() {
        ZonedDateTime startTime = ZonedDateTime.now();
        ZonedDateTime endTime = startTime.plusDays(1);

        Interval interval1 = new Interval(startTime, endTime);
        Interval interval2 = new Interval(startTime, endTime);
        Assert.assertEquals(interval1, interval2);

        Interval interval3 = new Interval(startTime, endTime.plusDays(1));

        Assert.assertNotEquals(interval1, interval3);

        ZonedDateTime otherStartTime = ZonedDateTime.from(startTime);
        ZonedDateTime otherEndTime = ZonedDateTime.from(endTime);

        Interval interval4 = new Interval(otherStartTime, otherEndTime);
        Assert.assertEquals(interval1, interval4);
    }

    @Test
    public void shouldFormatIntervalAsIsoString() throws JsonProcessingException {
        ZonedDateTime startTime = ZonedDateTime.of(2020, 3, 14, 15, 30, 0, 0, ZoneOffset.UTC);
        ZonedDateTime endTime = ZonedDateTime.of(2020, 3, 15, 15, 30, 0, 0, ZoneOffset.UTC);

        Interval interval = new Interval(startTime, endTime);
        String intervalJson = new ObjectMapper().writeValueAsString(interval);

        Assert.assertEquals(intervalJson, "\"2020-03-14T15:30:00Z/2020-03-15T15:30:00Z\"");
    }

    @Test
    public void shouldFormatIntervalAsIsoStringWhenMillis() throws JsonProcessingException {
        ZonedDateTime startTime = ZonedDateTime.of(2020, 3, 14, 15, 30, 0, 10000000, ZoneOffset.UTC);
        ZonedDateTime endTime = ZonedDateTime.of(2020, 3, 15, 15, 30, 0, 10000000, ZoneOffset.UTC);

        Interval interval = new Interval(startTime, endTime);
        String intervalJson = new ObjectMapper().writeValueAsString(interval);

        Assert.assertEquals(intervalJson, "\"2020-03-14T15:30:00.01Z/2020-03-15T15:30:00.01Z\"");
    }
}
