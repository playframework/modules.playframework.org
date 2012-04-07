/*
 * Copyright 2012 The Play! Framework
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
package utils;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

import org.junit.*;

/**
 * @author Ed Southey (me@edsouthey.com)
 */
public class RatingCalculatorTest {

	private static final int LESS_THAN = -1;
	private static final int GREATER_THAN = 1;

	@Test
	public void checkConfidence() throws Exception {
		assertThat(RatingCalculator.confidence(0, 0), is(0d));
		assertThat(RatingCalculator.confidence(1, 0), is(0d));
		assertThat(RatingCalculator.confidence(999999, 0), is(0d));
		
		assertThat(RatingCalculator.confidence(0, 1), is(0d));
		assertThat(RatingCalculator.confidence(0, 999999), is(0d));

		//Check a better percentage beats sheer weight of numbers
		Double higherScoreLowerPercentage = RatingCalculator.confidence(5500, 10000);
		Double lowerScoreHigherPercentage = RatingCalculator.confidence(600, 1000);
		
		assertThat(lowerScoreHigherPercentage.compareTo(higherScoreLowerPercentage), is(GREATER_THAN));
		
		//Check bigger sample beats smaller sample
		Double perfectSmallSample = RatingCalculator.confidence(1, 1);
		Double almostPerfectBiggerSample = RatingCalculator.confidence(99, 100);
		
		assertThat(perfectSmallSample.compareTo(almostPerfectBiggerSample), is(LESS_THAN));
	}
	
	@Test
	public void checkScoring() throws Exception {
		assertThat(RatingCalculator.score(0, 0), is(0));
		assertThat(RatingCalculator.score(0, 1), is(0));

		assertThat(RatingCalculator.score(1, 1), is(100));
		assertThat(RatingCalculator.score(1, 2), is(50));
		assertThat(RatingCalculator.score(1, 3), is(33));

		assertThat(RatingCalculator.score(0, 5, 5), is(0));
		assertThat(RatingCalculator.score(1, 5, 5), is(1));
		assertThat(RatingCalculator.score(5, 5, 5), is(5));

		assertThat(RatingCalculator.score(0, 5, 10), is(0));
		assertThat(RatingCalculator.score(1, 5, 10), is(2));
		assertThat(RatingCalculator.score(5, 5, 10), is(10));
	}
	
}
