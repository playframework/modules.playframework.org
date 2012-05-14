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

/**
 * @author Ed Southey (me@edsouthey.com)
 */
public class RatingCalculator {
	
	private static final double CHANCE = 1.96;

	public static double confidence(int positiveRatings,
                                    int totalRatings)
    {
        double result = 0.0;
		if (totalRatings != 0)
        {
            double z = CHANCE;
            double phat = 1.0 * positiveRatings / totalRatings;
            result = (phat + z*z/(2*totalRatings) - z * Math.sqrt((phat*(1-phat)+z*z/(4*totalRatings))/totalRatings))/(1+z*z/totalRatings);
		}
		return result;
	}
	
	public static int score(int positiveRatings,
                            int totalRatings)
    {
		return score(positiveRatings,
                     totalRatings,
                     100);
	}
	
	public static int score(int positiveRatings,
                            int totalRatings,
                            int granularity)
    {
        int result = 0;
		if (totalRatings != 0)
        {
			result = (positiveRatings*granularity)/totalRatings;
		}
		return result;
	}
	

}
