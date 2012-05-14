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
package models;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
@Entity
public class Rating extends AbstractModel
{
    @Column(nullable = false)
    public Integer oneStar;

    @Column(nullable = false)
    public Integer twoStars;

    @Column(nullable = false)
    public Integer threeStars;

    @Column(nullable = false)
    public Integer fourStars;

    @Column(nullable = false)
    public Integer fiveStars;

    // this will be calculated using the funky anti-cheating algorithm
    @Column(nullable = false)
    public Float averageRating;

    public Rating()
    {
        // no-op
    }

    public Rating(boolean init)
    {
        this.oneStar = 0;
        this.twoStars = 0;
        this.threeStars = 0;
        this.fourStars = 0;
        this.fiveStars = 0;
        calculateAverage();
    }

    public Integer totalRatings()
    {
        return oneStar + twoStars + threeStars + fourStars + fiveStars;
    }

    public Integer positiveRatings()
    {
        return threeStars + fourStars + fiveStars;
    }

    public void calculateAverage()
    {
        // this will use the RatingCalculator when I've taken a better look at it
        Integer totalRatings = totalRatings();
        if (Float.compare(totalRatings, 0.0f) == 0)
        {
            this.averageRating = 0f;
        }
        else
        {
            float total = oneStar + (2 * twoStars) + (3 * threeStars) + (4 * fourStars) + (5 * fiveStars);
            this.averageRating = total/ totalRatings;
        }
    }

    public void subtract(Rate rate)
    {
        switch (rate.rating)
        {
            case 1:
                if (isPositive(oneStar)) { --oneStar; }
                break;
            case 2:
                if (isPositive(twoStars)) { --twoStars; }
                break;
            case 3:
                if (isPositive(threeStars)) { --threeStars; }
                break;
            case 4:
                if (isPositive(fourStars)) { --fourStars; }
                break;
            case 5:
                if (isPositive(fiveStars)) { --fiveStars; }
                break;
        }
    }

    public void add(Rate rate)
    {
        switch (rate.rating)
        {
            case 1:
                ++oneStar;
                break;
            case 2:
                ++twoStars;
                break;
            case 3:
                ++threeStars;
                break;
            case 4:
                ++fourStars;
                break;
            case 5:
                ++fiveStars;
                break;
        }
    }

    private boolean isPositive(int i)
    {
        return i > 0;
    }
}
