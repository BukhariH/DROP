
package org.drip.simm.commodity;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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
 * CTSettingsContainer21 holds the ISDA SIMM 2.1 Commodity Buckets and their Correlations. The References
 *  are:
 *  
 *  - Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial Margin,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156, eSSRN.
 *  
 *  - Albanese, C., S. Caenazzo, and O. Frankel (2017): Regression Sensitivities for Initial Margin
 *  	Calculations, https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2763488, eSSRN.
 *  
 *  - Anfuso, F., D. Aziz, P. Giltinan, and K. Loukopoulus (2017): A Sound Modeling and Back-testing
 *  	Framework for Forecasting Initial Margin Requirements,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2716279, eSSRN.
 *  
 *  - Caspers, P., P. Giltinan, R. Lichters, and N. Nowaczyk (2017): Forecasting Initial Margin Requirements
 *  	- A Model Evaluation https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2911167, eSSRN.
 *  
 *  - International Swaps and Derivatives Association (2018): SIMM v2.1 Methodology,
 *  	https://www.isda.org/a/zSpEE/ISDA-SIMM-v2.1-PUBLIC.pdf.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CTSettingsContainer21
{
	private static org.drip.measure.stochastic.LabelCorrelation s_CrossBucketCorrelation = null;

	private static final java.util.Map<java.lang.Integer, org.drip.simm.commodity.CTBucket> s_BucketMap =
		new java.util.TreeMap<java.lang.Integer, org.drip.simm.commodity.CTBucket>();

	private static final boolean SetUpCrossBucketCorrelation()
	{
		java.util.List<java.lang.String> bucketList = new java.util.ArrayList<java.lang.String>();

		bucketList.add ("1");

		bucketList.add ("2");

		bucketList.add ("3");

		bucketList.add ("4");

		bucketList.add ("5");

		bucketList.add ("6");

		bucketList.add ("7");

		bucketList.add ("8");

		bucketList.add ("9");

		bucketList.add ("10");

		bucketList.add ("11");

		bucketList.add ("12");

		bucketList.add ("13");

		bucketList.add ("14");

		bucketList.add ("15");

		bucketList.add ("16");

		bucketList.add ("17");

		try
		{
			s_CrossBucketCorrelation = new org.drip.measure.stochastic.LabelCorrelation (
				bucketList,
				new double[][]
				{
					{ 1.00, 0.16, 0.11, 0.19, 0.22, 0.12, 0.22, 0.02, 0.27, 0.08, 0.11, 0.05, 0.04, 0.06, 0.01, 0.00, 0.10}, // #01
					{ 0.16, 1.00, 0.89, 0.94, 0.93, 0.32, 0.24, 0.19, 0.21, 0.06, 0.39, 0.23, 0.39, 0.29, 0.13, 0.00, 0.66}, // #02
					{ 0.11, 0.89, 1.00, 0.87, 0.88, 0.17, 0.17, 0.13, 0.12, 0.03, 0.24, 0.04, 0.27, 0.19, 0.08, 0.00, 0.61}, // #03
					{ 0.19, 0.94, 0.87, 1.00, 0.92, 0.37, 0.27, 0.21, 0.21, 0.03, 0.36, 0.16, 0.27, 0.28, 0.09, 0.00, 0.64}, // #04
					{ 0.22, 0.93, 0.88, 0.92, 1.00, 0.29, 0.26, 0.19, 0.23, 0.10, 0.40, 0.27, 0.38, 0.30, 0.15, 0.00, 0.64}, // #05
					{ 0.12, 0.32, 0.17, 0.37, 0.29, 1.00, 0.19, 0.60, 0.18, 0.09, 0.22, 0.09, 0.14, 0.16, 0.10, 0.00, 0.37}, // #06
					{ 0.22, 0.24, 0.17, 0.27, 0.26, 0.19, 1.00, 0.06, 0.68, 0.16, 0.21, 0.10, 0.24, 0.25,-0.01, 0.00, 0.27}, // #07
					{ 0.02, 0.19, 0.13, 0.21, 0.19, 0.60, 0.06, 1.00, 0.12, 0.01, 0.10, 0.03, 0.02, 0.07, 0.10, 0.00, 0.21}, // #08
					{ 0.27, 0.21, 0.12, 0.21, 0.23, 0.18, 0.68, 0.12, 1.00, 0.05, 0.16, 0.03, 0.19, 0.16,-0.01, 0.00, 0.19}, // #09
					{ 0.08, 0.06, 0.03, 0.03, 0.10, 0.09, 0.16, 0.01, 0.05, 1.00, 0.08, 0.04, 0.05, 0.11, 0.02, 0.00, 0.00}, // #10
					{ 0.11, 0.39, 0.24, 0.36, 0.40, 0.22, 0.21, 0.10, 0.16, 0.08, 1.00, 0.34, 0.19, 0.22, 0.15, 0.00, 0.34}, // #11
					{ 0.05, 0.23, 0.04, 0.16, 0.27, 0.09, 0.10, 0.03, 0.03, 0.04, 0.34, 1.00, 0.14, 0.26, 0.09, 0.00, 0.20}, // #12
					{ 0.04, 0.39, 0.27, 0.27, 0.38, 0.14, 0.24, 0.02, 0.19, 0.05, 0.19, 0.14, 1.00, 0.30, 0.16, 0.00, 0.40}, // #13
					{ 0.06, 0.29, 0.19, 0.28, 0.30, 0.16, 0.25, 0.07, 0.16, 0.11, 0.22, 0.26, 0.30, 1.00, 0.09, 0.00, 0.30}, // #14
					{ 0.01, 0.13, 0.08, 0.09, 0.15, 0.10,-0.01, 0.10,-0.01, 0.02, 0.15, 0.09, 0.16, 0.09, 1.00, 0.00, 0.16}, // #15
					{ 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00}, // #16
					{ 0.10, 0.66, 0.61, 0.64, 0.64, 0.37, 0.27, 0.21, 0.19, 0.00, 0.34, 0.20, 0.40, 0.30, 0.16, 0.00, 1.00}  // #17
				}
			);

			return true;
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Initialize the Commodity Settings Container
	 * 
	 * @return TRUE - Commodity Settings Container successfully initialized
	 */

	public static final boolean Init()
	{
		try
		{
			s_BucketMap.put (
				1,
				new org.drip.simm.commodity.CTBucket (
					1,
					"Coal",
					19.,
					0.27
				)
			);

			s_BucketMap.put (
				2,
				new org.drip.simm.commodity.CTBucket (
					2,
					"Crude",
					20.,
					0.97
				)
			);

			s_BucketMap.put (
				3,
				new org.drip.simm.commodity.CTBucket (
					3,
					"Light Ends",
					17.,
					0.92
				)
			);

			s_BucketMap.put (
				4,
				new org.drip.simm.commodity.CTBucket (
					4,
					"Middle Distillates",
					19.,
					0.97
				)
			);

			s_BucketMap.put (
				5,
				new org.drip.simm.commodity.CTBucket (
					5,
					"Heavy Distillates",
					24.,
					0.99
				)
			);

			s_BucketMap.put (
				6,
				new org.drip.simm.commodity.CTBucket (
					6,
					"North American Natural Gas",
					22.,
					1.00
				)
			);

			s_BucketMap.put (
				7,
				new org.drip.simm.commodity.CTBucket (
					7,
					"European Natural Gas",
					26.,
					1.00
				)
			);

			s_BucketMap.put (
				8,
				new org.drip.simm.commodity.CTBucket (
					8,
					"North American Power",
					50.,
					0.40
				)
			);

			s_BucketMap.put (
				9,
				new org.drip.simm.commodity.CTBucket (
					9,
					"European Power",
					27.,
					0.73
				)
			);

			s_BucketMap.put (
				10,
				new org.drip.simm.commodity.CTBucket (
					10,
					"Freight",
					54.,
					0.13
				)
			);

			s_BucketMap.put (
				11,
				new org.drip.simm.commodity.CTBucket (
					11,
					"Base Metals",
					20.,
					0.53
				)
			);

			s_BucketMap.put (
				12,
				new org.drip.simm.commodity.CTBucket (
					12,
					"Precious Metals",
					20.,
					0.64
				)
			);

			s_BucketMap.put (
				13,
				new org.drip.simm.commodity.CTBucket (
					13,
					"Grains",
					17.,
					0.63
				)
			);

			s_BucketMap.put (
				14,
				new org.drip.simm.commodity.CTBucket (
					14,
					"Softs",
					14.,
					0.26
				)
			);

			s_BucketMap.put (
				15,
				new org.drip.simm.commodity.CTBucket (
					15,
					"Livestock",
					10.,
					0.26
				)
			);

			s_BucketMap.put (
				16,
				new org.drip.simm.commodity.CTBucket (
					16,
					"Other",
					54.,
					0.00
				)
			);

			s_BucketMap.put (
				17,
				new org.drip.simm.commodity.CTBucket (
					17,
					"Indexes",
					16.,
					0.38
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return false;
		}

		return SetUpCrossBucketCorrelation();
	}

	/**
	 * Retrieve the Set of Bucket Indexes available
	 * 
	 * @return The Set of Bucket Indexes available
	 */

	public static final java.util.Set<java.lang.Integer> BucketSet()
	{
		return s_BucketMap.keySet();
	}

	/**
	 * Indicate if the Bucket denoted by the Number is available
	 * 
	 * @param bucketNumber The Bucket Number
	 * 
	 * @return TRUE - The Bucket denoted by the Number is available
	 */

	public static final boolean ContainsBucket (
		final int bucketNumber)
	{
		return s_BucketMap.containsKey (bucketNumber);
	}

	/**
	 * Retrieve the Bucket denoted by the Number
	 * 
	 * @param bucketNumber The Bucket Number
	 * 
	 * @return The Bucket denoted by the Number
	 */

	public static final org.drip.simm.commodity.CTBucket Bucket (
		final int bucketNumber)
	{
		return ContainsBucket (bucketNumber) ? s_BucketMap.get (bucketNumber) : null;
	}

	/**
	 * Retrieve the Cross Bucket Correlation
	 * 
	 * @return The Cross Bucket Correlation
	 */

	public static final org.drip.measure.stochastic.LabelCorrelation CrossBucketCorrelation()
	{
		return s_CrossBucketCorrelation;
	}

	/**
	 * Retrieve the Bucket Map
	 * 
	 * @return The Bucket Map
	 */

	public static final java.util.Map<java.lang.Integer, org.drip.simm.commodity.CTBucket> BucketMap()
	{
		return s_BucketMap;
	}
}