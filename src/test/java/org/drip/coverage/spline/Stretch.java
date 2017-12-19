
package org.drip.coverage.spline;

import org.drip.sample.stretch.ATMTTESurface2D;
import org.drip.sample.stretch.CurvatureLengthRoughnessPenalty;
import org.drip.sample.stretch.CurvatureRoughnessPenaltyFit;
import org.drip.sample.stretch.CustomDiscountCurveBuilder;
import org.drip.sample.stretch.KnotInsertionPolynomialEstimator;
import org.drip.sample.stretch.KnotInsertionSequenceAdjuster;
import org.drip.sample.stretch.KnotInsertionTensionEstimator;
import org.drip.sample.stretch.KnottedRegressionSplineEstimator;
import org.drip.sample.stretch.MultiSpanAggregationEstimator;

import org.junit.Test;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *   	you may not use this file except in compliance with the License.
 *   
 *  You may obtain a copy of the License at
 *  	http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  	distributed under the License is distributed on an "AS IS" BASIS,
 *  	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  
 *  See the License for the specific language governing permissions and
 *  	limitations under the License.
 */

/**
 * Stretch holds the JUnit Code Coverage Tests for the Stretch Spline Module.
 *
 * @author Lakshmi Krishnamurthy
 */

public class Stretch
{
	@Test public void codeCoverageTest() throws Exception
	{
		ATMTTESurface2D.main (null);

		CurvatureLengthRoughnessPenalty.main (null);

		CurvatureRoughnessPenaltyFit.main (null);

		CustomDiscountCurveBuilder.main (null);

		KnotInsertionPolynomialEstimator.main (null);

		KnotInsertionSequenceAdjuster.main (null);

		KnotInsertionTensionEstimator.main (null);

		KnottedRegressionSplineEstimator.main (null);

		MultiSpanAggregationEstimator.main (null);
    }
}