
package org.drip.exposure.regression;

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
 * AndersenPykhtinSokolStretch generates the Regression Based Path Exposures off of the Pillar Vertexes using
 *  the Pykhtin (2009) Scheme. Eventual Unadjusted Variation Margin Calculation follows Andersen, Pykhtin,
 *  and Sokol (2017). The References are:
 *  
 *  - Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Re-thinking Margin Period of Risk,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2902737, eSSRN.
 *  
 *  - Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial Margin,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156, eSSRN.
 *  
 *  - Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and the
 *  	Re-Hypothecation Option, eSSRN, https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955.
 *  
 *  - Burgard, C., and M. Kjaer (2017): Derivatives Funding, Netting, and Accounting, eSSRN,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2534011.
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing, Risk
 *  	21 (2) 97-102.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class AndersenPykhtinSokolStretch
{
	private int[] _sparseDateArray = null;
	private double[] _sparseExposureArray = null;
	private org.drip.exposure.mpor.TradePayment[] _denseTradePaymentArray = null;
	private org.drip.function.definition.R1ToR1[] _sparseLocalVolatilityArray = null;

	/**
	 * AndersenPykhtinSokolStretch Constructor
	 * 
	 * @param sparseDateArray Array of Sparse Exposure Dates
	 * @param sparseExposureArray Array of Sparse Exposures
	 * @param sparseLocalVolatilityArray Array of Sparse Local Volatility R1 To R1 Functions
	 * @param denseTradePaymentArray Array of Dense Trade Payments
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public AndersenPykhtinSokolStretch (
		final int[] sparseDateArray,
		final double[] sparseExposureArray,
		final org.drip.function.definition.R1ToR1[] sparseLocalVolatilityArray,
		final org.drip.exposure.mpor.TradePayment[] denseTradePaymentArray)
		throws java.lang.Exception
	{
		if (null == (_sparseDateArray = sparseDateArray) ||
			null == (_sparseExposureArray = sparseExposureArray) ||
			null == (_sparseLocalVolatilityArray = sparseLocalVolatilityArray) ||
			null == (_denseTradePaymentArray = denseTradePaymentArray))
		{
			throw new java.lang.Exception ("AndersenPykhtinSokolStretch Constructor => Invalid Inputs");
		}

		int sparseExposureDateCount = _sparseDateArray.length;
		int denseExposureDateCount = _denseTradePaymentArray.length;

		if (0 == sparseExposureDateCount ||
			sparseExposureDateCount != _sparseExposureArray.length ||
			!org.drip.quant.common.NumberUtil.IsValid (_sparseExposureArray) ||
			sparseExposureDateCount != _sparseLocalVolatilityArray.length ||
			sparseExposureDateCount > denseExposureDateCount)
		{
			throw new java.lang.Exception ("AndersenPykhtinSokolStretch Constructor => Invalid Inputs");
		}

		for (int sparseExposureDateIndex = 0;
			sparseExposureDateIndex < sparseExposureDateCount;
			++sparseExposureDateIndex)
		{
			if (null == _sparseLocalVolatilityArray[sparseExposureDateIndex])
			{
				throw new java.lang.Exception ("AndersenPykhtinSokolStretch Constructor => Invalid Inputs");
			}
		}

		for (int denseExposureDateIndex = 0;
			denseExposureDateIndex < denseExposureDateCount;
			++denseExposureDateIndex)
		{
			if (null == _denseTradePaymentArray[denseExposureDateIndex])
			{
				throw new java.lang.Exception ("AndersenPykhtinSokolStretch Constructor => Invalid Inputs");
			}
		}
	}

	/**
	 * Retrieve the Sparse Exposure Date Array
	 * 
	 * @return The Sparse Exposure Date Array
	 */

	public int[] sparseDateArray()
	{
		return _sparseDateArray;
	}

	/**
	 * Retrieve the Sparse Exposure Array
	 * 
	 * @return The Sparse Exposure Array
	 */

	public double[] sparseExposureArray()
	{
		return _sparseExposureArray;
	}

	/**
	 * Retrieve the Sparse Local Volatility R1 To R1 Array
	 * 
	 * @return The Sparse Local Volatility R1 To R1 Array
	 */

	public org.drip.function.definition.R1ToR1[] sparseLocalVolatilityArray()
	{
		return _sparseLocalVolatilityArray;
	}

	/**
	 * Retrieve the Dense Trade Payment Array
	 * 
	 * @return The Dense Trade Payment Array
	 */

	public org.drip.exposure.mpor.TradePayment[] denseTradePaymentArray()
	{
		return _denseTradePaymentArray;
	}

	/**
	 * Generate the Dense (Complete) Segment Exposures
	 * 
	 * @param wanderTrajectory The Wander Date Trajectory
	 * 
	 * @return The Dense (Complete) Segment Exposures
	 */

	public double[] denseExposure (
		final double[] wanderTrajectory)
	{
		int epochDate = _sparseDateArray[0];
		int sparseExposureDateCount = _sparseDateArray.length;
		int denseExposureDateCount = _denseTradePaymentArray.length;
		double[] denseExposureTrajectory = new double[denseExposureDateCount];

		for (int sparseExposureDateIndex = 1;
			sparseExposureDateIndex < sparseExposureDateCount;
			++sparseExposureDateIndex)
		{
			try
			{
				new AndersenPykhtinSokolSegment (
					epochDate,
					new org.drip.exposure.regression.PillarVertex (
						_sparseDateArray[sparseExposureDateIndex - 1],
						_sparseExposureArray[sparseExposureDateIndex - 1]
					),
					new org.drip.exposure.regression.PillarVertex (
						_sparseDateArray[sparseExposureDateIndex],
						_sparseExposureArray[sparseExposureDateIndex]
					),
					_sparseLocalVolatilityArray[sparseExposureDateIndex]
				).denseExposureTrajectoryUpdate (
					denseExposureTrajectory,
					wanderTrajectory
				);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}
		}

		for (int denseExposureDateIndex = 0;
			denseExposureDateIndex < denseExposureDateCount;
			++denseExposureDateIndex)
		{
			org.drip.exposure.mpor.TradePayment tradePayment =
				_denseTradePaymentArray[denseExposureDateIndex];

			denseExposureTrajectory[denseExposureDateIndex] = denseExposureTrajectory[denseExposureDateIndex]
				+ tradePayment.dealer() - tradePayment.client();
		}

		return denseExposureTrajectory;
	}
}