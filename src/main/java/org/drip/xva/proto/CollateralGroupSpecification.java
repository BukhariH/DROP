
package org.drip.xva.proto;

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
 * CollateralGroupSpecification contains the Specifications of a Collateral Group. The References are:
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs, Journal of Credit Risk, 7 (3) 1-19.
 *  
 *  - Burgard, C., and M. Kjaer (2014): In the Balance, Risk, 24 (11) 72-75.
 *  
 *  - Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk, Risk 20 (2) 86-90.
 *  
 *  - Li, B., and Y. Tang (2007): Quantitative Analysis, Derivatives Modeling, and Trading Strategies in the
 *  	Presence of Counter-party Credit Risk for the Fixed Income Market, World Scientific Publishing,
 *  	Singapore.
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing, Risk
 *  	21 (2) 97-102.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CollateralGroupSpecification extends org.drip.xva.proto.ObjectSpecification
{
	private org.drip.state.identifier.CSALabel _csaLabel = null;
	private org.drip.state.identifier.OvernightLabel _overnightLabel = null;

	/**
	 * CollateralGroupSpecification Constructor
	 * 
	 * @param id The Collateral Group ID
	 * @param name The Collateral Group Name
	 * @param overnightLabel The Overnight Latent State Label
	 * @param csaLabel The CSA Latent State Label
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CollateralGroupSpecification (
		final java.lang.String id,
		final java.lang.String name,
		final org.drip.state.identifier.OvernightLabel overnightLabel,
		final org.drip.state.identifier.CSALabel csaLabel)
		throws java.lang.Exception
	{
		super (
			id,
			name
		);

		if (null == (_overnightLabel = overnightLabel) ||
			null == (_csaLabel = csaLabel))
		{
			throw new java.lang.Exception ("CollateralGroupSpecification Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Overnight Label
	 * 
	 * @return The Overnight Label
	 */

	public org.drip.state.identifier.OvernightLabel overnightLabel()
	{
		return _overnightLabel;
	}

	/**
	 * Retrieve the CSA Label
	 * 
	 * @return The CSA Label
	 */

	public org.drip.state.identifier.CSALabel csaLabel()
	{
		return _csaLabel;
	}
}
