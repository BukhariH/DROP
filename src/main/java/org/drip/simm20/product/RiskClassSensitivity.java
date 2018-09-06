
package org.drip.simm20.product;

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
 * RiskClassSensitivity holds the Risk Class Bucket Sensitivities for a single Risk Class. The References
 *  are:
 *  
 *  - Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial Margin,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156, eSSRN.
 *  
 *  - Albanese, C., S. Caenazzo, and O. Frankel (2017): Regression Sensitivities for Initial Margin
 *  	Calculations, https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2763488, eSSRN.
 *  
 *  - Anfuso, F., D. Aziz, P. Giltinan, and K. Loukopoulus (2017): A Sound Modeling and Back-testing
 *  	Framework for Forecasting .Initial Margin Requirements,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2716279, eSSRN.
 *  
 *  - Caspers, P., P. Giltinan, R. Lichters, and N. Nowaczyk (2017): Forecasting Initial Margin Requirements
 *  	- A Model Evaluation https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2911167, eSSRN.
 *  
 *  - International Swaps and Derivatives Association (2017): SIMM v2.0 Methodology,
 *  	https://www.isda.org/a/oFiDE/isda-simm-v2.pdf.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class RiskClassSensitivity
{
	private java.util.Map<java.lang.String, org.drip.simm20.product.BucketSensitivity> _bucketSensitivityMap
		= null;

	/**
	 * RiskClassSensitivity Constructor
	 * 
	 * @param bucketSensitivityMap The Risk Class Bucket Sensitivity Map
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public RiskClassSensitivity (
		final java.util.Map<java.lang.String, org.drip.simm20.product.BucketSensitivity>
			bucketSensitivityMap)
		throws java.lang.Exception
	{
		if (null == (_bucketSensitivityMap = bucketSensitivityMap) || 0 == _bucketSensitivityMap.size())
		{
			throw new java.lang.Exception ("RiskClassSensitivity Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Risk Class Bucket Sensitivity Map
	 * 
	 * @return The Risk Class Bucket Sensitivity Map
	 */

	public java.util.Map<java.lang.String, org.drip.simm20.product.BucketSensitivity> bucketSensitivityMap()
	{
		return _bucketSensitivityMap;
	}

	/**
	 * Generate the Risk Class Aggregate
	 * 
	 * @param riskClassSensitivitySettings The Risk Class Sensitivity Settings
	 * 
	 * @return The Risk Class Aggregate
	 */

	public org.drip.simm20.margin.RiskClassAggregate aggregate (
		final org.drip.simm20.parameters.RiskClassSensitivitySettings riskClassSensitivitySettings)
	{
		if (null == riskClassSensitivitySettings)
		{
			return null;
		}

		double coreDeltaSBAVariance = 0.;

		java.util.Map<java.lang.String, org.drip.simm20.margin.BucketAggregate> bucketAggregateMap = new
			java.util.TreeMap<java.lang.String, org.drip.simm20.margin.BucketAggregate>();

		java.util.Map<java.lang.String, org.drip.simm20.parameters.BucketSensitivitySettings>
			bucketSensitivitySettingsMap = riskClassSensitivitySettings.bucketSensitivitySettingsMap();

		org.drip.measure.stochastic.LabelCorrelation crossBucketCorrelation =
			riskClassSensitivitySettings.crossBucketCorrelation();

		for (java.util.Map.Entry<java.lang.String, org.drip.simm20.product.BucketSensitivity>
			bucketSensitivityMapEntry : _bucketSensitivityMap.entrySet())
		{
			java.lang.String bucketIndex = bucketSensitivityMapEntry.getKey();

			org.drip.simm20.product.BucketSensitivity bucketSensitivity =
				bucketSensitivityMapEntry.getValue();

			org.drip.simm20.margin.BucketAggregate bucketAggregate = bucketSensitivity.aggregate
				(bucketSensitivitySettingsMap.get (bucketIndex));

			if (null == bucketAggregate)
			{
				return null;
			}

			bucketAggregateMap.put (
				"" + bucketIndex,
				bucketAggregate
			);
		}

		try
		{
			for (java.util.Map.Entry<java.lang.String, org.drip.simm20.margin.BucketAggregate>
				bucketAggregateMapOuterEntry : bucketAggregateMap.entrySet())
			{
				java.lang.String outerKey = bucketAggregateMapOuterEntry.getKey();

				org.drip.simm20.margin.BucketAggregate bucketAggregateOuter =
					bucketAggregateMapOuterEntry.getValue();

				double weightedSensitivityVarianceOuter = bucketAggregateOuter.sensitivityMarginVariance();

				double boundedWeightedSensitivityOuter = bucketAggregateOuter.boundedSensitivityMargin();

				for (java.util.Map.Entry<java.lang.String, org.drip.simm20.margin.BucketAggregate>
					bucketAggregateMapInnerEntry : bucketAggregateMap.entrySet())
				{
					java.lang.String innerKey = bucketAggregateMapInnerEntry.getKey();

					if (!"-1".equalsIgnoreCase (outerKey) && !"-1".equalsIgnoreCase (innerKey))
					{
						coreDeltaSBAVariance = coreDeltaSBAVariance + (outerKey.equalsIgnoreCase (innerKey) ?
							weightedSensitivityVarianceOuter : crossBucketCorrelation.entry (
								"" + outerKey,
								"" + innerKey
							) * boundedWeightedSensitivityOuter *
							bucketAggregateMapInnerEntry.getValue().boundedSensitivityMargin()
						);
					}
				}
			}

			return new org.drip.simm20.margin.RiskClassAggregate (
				bucketAggregateMap,
				coreDeltaSBAVariance,
				bucketAggregateMap.containsKey ("-1") ?
					bucketAggregateMap.get ("-1").sensitivityMarginVariance() : 0.
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}