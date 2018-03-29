
package org.drip.xva.evolver;

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
 * LatentStateDynamicsContainer holds the Latent State Labels for a variety of Latent States and their
 * 	Evolvers.  The References are:<br><br>
 *  
 *  - Burgard, C., and M. Kjaer (2013): Funding Strategies, Funding Costs <i>Risk</i> <b>24 (12)</b>
 *  	82-87.<br><br>
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19.<br><br>
 *  
 *  - Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75.<br><br>
 *  
 *  - Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b>
 *  	86-90.<br><br>
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing
 *  	<i>Risk</i> <b>21 (2)</b> 97-102.<br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class LatentStateDynamicsContainer
{
	private java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> _fxEvolver = null;
	private java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> _csaEvolver = null;
	private java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> _repoEvolver = null;
	private java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> _customEvolver = null;
	private java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> _govvieEvolver = null;
	private java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> _ratingEvolver = null;
	private java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> _forwardEvolver = null;
	private java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> _fundingEvolver = null;
	private java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> _payDownEvolver = null;
	private java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> _overnightEvolver =
		null;
	private java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> _collateralEvolver =
		null;
	private java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> _volatilityEvolver = 
		null;
	private java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> _otcFixFloatEvolver =
		null;
	private java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> _entityCreditEvolver =
		null;
	private java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> _entityEquityEvolver =
		null;
	private java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> _entityHazardEvolver =
		null;
	private java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> _entityFundingEvolver =
		null;
	private java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> _entityRecoveryEvolver
		= null;

	/**
	 * Empty LatentStateDynamicsContainer Constructor
	 */

	public LatentStateDynamicsContainer()
	{
		_fxEvolver = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.xva.evolver.TerminalLatentState>();

		_csaEvolver = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.xva.evolver.TerminalLatentState>();

		_repoEvolver = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.xva.evolver.TerminalLatentState>();

		_customEvolver = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.xva.evolver.TerminalLatentState>();

		_govvieEvolver = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.xva.evolver.TerminalLatentState>();

		_ratingEvolver = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.xva.evolver.TerminalLatentState>();

		_forwardEvolver = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.xva.evolver.TerminalLatentState>();

		_fundingEvolver = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.xva.evolver.TerminalLatentState>();

		_payDownEvolver = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.xva.evolver.TerminalLatentState>();

		_overnightEvolver = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.xva.evolver.TerminalLatentState>();

		_collateralEvolver = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.xva.evolver.TerminalLatentState>();

		_volatilityEvolver = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.xva.evolver.TerminalLatentState>();

		_otcFixFloatEvolver = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.xva.evolver.TerminalLatentState>();

		_entityCreditEvolver = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.xva.evolver.TerminalLatentState>();

		_entityEquityEvolver = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.xva.evolver.TerminalLatentState>();

		_entityHazardEvolver = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.xva.evolver.TerminalLatentState>();

		_entityFundingEvolver = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.xva.evolver.TerminalLatentState>();

		_entityRecoveryEvolver = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.xva.evolver.TerminalLatentState>();
	}

	/**
	 * Retrieve the Equity Evolver Map
	 * 
	 * @return The Equity Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> entityEquityMap()
	{
		return _entityEquityEvolver;
	}

	/**
	 * Add the Entity Equity Latent State Evolver
	 * 
	 * @param terminalLatentState The Equity Entity Terminal Latent State
	 * 
	 * @return TRUE - The Entity Equity Latent Successfully added
	 */

	public boolean addEntityEquity (
		final org.drip.xva.evolver.TerminalLatentState terminalLatentState)
	{
		if (null == terminalLatentState)
		{
			return false;
		}

		_entityEquityEvolver.put (
			terminalLatentState.label().fullyQualifiedName(),
			terminalLatentState
		);

		return true;
	}

	/**
	 * Indicate if the Entity Equity Latent State Exists
	 * 
	 * @param entityEquityLabel The Entity Equity Latent State Label
	 * 
	 * @return TRUE - The Entity Equity Latent State Exists
	 */

	public boolean entityEquityExists (
		final org.drip.state.identifier.EntityEquityLabel entityEquityLabel)
	{
		return null == entityEquityLabel ? false : _entityEquityEvolver.containsKey
			(entityEquityLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve the Entity Equity Latent State
	 * 
	 * @param entityEquityLabel The Entity Equity Latent State Label
	 * 
	 * @return The Entity Equity Latent State
	 */

	public org.drip.xva.evolver.TerminalLatentState entityEquity (
		final org.drip.state.identifier.EntityEquityLabel entityEquityLabel)
	{
		return entityEquityExists (entityEquityLabel) ? _entityEquityEvolver.get
			(entityEquityLabel.fullyQualifiedName()) : null;
	}

	/**
	 * Retrieve the Funding Evolver Map
	 * 
	 * @return The Funding Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> fundingMap()
	{
		return _fundingEvolver;
	}

	/**
	 * Add the Funding Latent State Evolver
	 * 
	 * @param terminalLatentState The Funding Terminal Latent State
	 * 
	 * @return TRUE - The Funding Latent State Successfully added
	 */

	public boolean addFunding (
		final org.drip.xva.evolver.TerminalLatentState terminalLatentState)
	{
		if (null == terminalLatentState)
		{
			return false;
		}

		_fundingEvolver.put (
			terminalLatentState.label().fullyQualifiedName(),
			terminalLatentState
		);

		return true;
	}

	/**
	 * Indicate if the Funding Latent State Exists
	 * 
	 * @param fundingLabel The Funding Latent State Label
	 * 
	 * @return TRUE - The Funding Latent State Exists
	 */

	public boolean fundingExists (
		final org.drip.state.identifier.FundingLabel fundingLabel)
	{
		return null == fundingLabel ? false : _fundingEvolver.containsKey
			(fundingLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve the Funding Latent State
	 * 
	 * @param fundingLabel The Funding Latent State Label
	 * 
	 * @return The Funding Latent State
	 */

	public org.drip.xva.evolver.TerminalLatentState funding (
		final org.drip.state.identifier.FundingLabel fundingLabel)
	{
		return fundingExists (fundingLabel) ? _fundingEvolver.get (fundingLabel.fullyQualifiedName()) : null;
	}

	/**
	 * Retrieve the Govvie Evolver Map
	 * 
	 * @return The Govvie Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> govvieMap()
	{
		return _govvieEvolver;
	}

	/**
	 * Add the Govvie Latent State Evolver
	 * 
	 * @param terminalLatentState The Govvie Terminal Latent State
	 * 
	 * @return TRUE - The Govvie Latent State Successfully added
	 */

	public boolean addGovvie (
		final org.drip.xva.evolver.TerminalLatentState terminalLatentState)
	{
		if (null == terminalLatentState)
		{
			return false;
		}

		_govvieEvolver.put (
			terminalLatentState.label().fullyQualifiedName(),
			terminalLatentState
		);

		return true;
	}

	/**
	 * Indicate if the Govvie Latent State Exists
	 * 
	 * @param govvieLabel The Govvie Latent State Label
	 * 
	 * @return TRUE - The Govvie Latent State Exists
	 */

	public boolean govvieExists (
		final org.drip.state.identifier.GovvieLabel govvieLabel)
	{
		return null == govvieLabel ? false : _govvieEvolver.containsKey (govvieLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve the Govvie Latent State
	 * 
	 * @param govvieLabel The Govvie Latent State Label
	 * 
	 * @return The Govvie Latent State
	 */

	public org.drip.xva.evolver.TerminalLatentState govvie (
		final org.drip.state.identifier.GovvieLabel govvieLabel)
	{
		return govvieExists (govvieLabel) ? _govvieEvolver.get (govvieLabel.fullyQualifiedName()) : null;
	}

	/**
	 * Retrieve the FX Evolver Map
	 * 
	 * @return The FX Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> fxMap()
	{
		return _fxEvolver;
	}

	/**
	 * Add the FX Latent State Evolver
	 * 
	 * @param terminalLatentState The FX Terminal Latent State
	 * 
	 * @return TRUE - The FX Latent State Successfully added
	 */

	public boolean addFX (
		final org.drip.xva.evolver.TerminalLatentState terminalLatentState)
	{
		if (null == terminalLatentState)
		{
			return false;
		}

		_fxEvolver.put (
			terminalLatentState.label().fullyQualifiedName(),
			terminalLatentState
		);

		return true;
	}

	/**
	 * Indicate if the FX Latent State Exists
	 * 
	 * @param fxLabel The FX Latent State Label
	 * 
	 * @return TRUE - The FX Latent State Exists
	 */

	public boolean fxExists (
		final org.drip.state.identifier.FXLabel fxLabel)
	{
		return null == fxLabel ? false : _fxEvolver.containsKey (fxLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve the FX Latent State
	 * 
	 * @param fxLabel The FX Latent State Label
	 * 
	 * @return The FX Latent State
	 */

	public org.drip.xva.evolver.TerminalLatentState fx (
		final org.drip.state.identifier.FXLabel fxLabel)
	{
		return fxExists (fxLabel) ? _fxEvolver.get (fxLabel.fullyQualifiedName()) : null;
	}

	/**
	 * Retrieve the Forward Evolver Map
	 * 
	 * @return The Forward Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> forwardMap()
	{
		return _forwardEvolver;
	}

	/**
	 * Add the Forward Latent State Evolver
	 * 
	 * @param terminalLatentState The Forward Terminal Latent State
	 * 
	 * @return TRUE - The Forward Latent State Successfully added
	 */

	public boolean addForward (
		final org.drip.xva.evolver.TerminalLatentState terminalLatentState)
	{
		if (null == terminalLatentState)
		{
			return false;
		}

		_forwardEvolver.put (
			terminalLatentState.label().fullyQualifiedName(),
			terminalLatentState
		);

		return true;
	}

	/**
	 * Indicate if the Forward Latent State Exists
	 * 
	 * @param forwardLabel The Forward Latent State Label
	 * 
	 * @return TRUE - The Forward Latent State Exists
	 */

	public boolean forwardExists (
		final org.drip.state.identifier.ForwardLabel forwardLabel)
	{
		return null == forwardLabel ? false : _forwardEvolver.containsKey
			(forwardLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve the Forward Latent State
	 * 
	 * @param forwardLabel The Forward Latent State Label
	 * 
	 * @return The Forward Latent State
	 */

	public org.drip.xva.evolver.TerminalLatentState forward (
		final org.drip.state.identifier.ForwardLabel forwardLabel)
	{
		return forwardExists (forwardLabel) ? _forwardEvolver.get (forwardLabel.fullyQualifiedName()) : null;
	}

	/**
	 * Retrieve the OTC Fix Float Evolver Map
	 * 
	 * @return The OTC Fix Float Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> otcFixFloatMap()
	{
		return _otcFixFloatEvolver;
	}

	/**
	 * Add the OTC Fix Float Latent State Evolver
	 * 
	 * @param terminalLatentState The OTC Fix Float Terminal Latent State
	 * 
	 * @return TRUE - The OTC Fix Float Latent State Successfully added
	 */

	public boolean addOTCFixFloat (
		final org.drip.xva.evolver.TerminalLatentState terminalLatentState)
	{
		if (null == terminalLatentState)
		{
			return false;
		}

		_otcFixFloatEvolver.put (
			terminalLatentState.label().fullyQualifiedName(),
			terminalLatentState
		);

		return true;
	}

	/**
	 * Indicate if the OTC Fix Float Latent State Exists
	 * 
	 * @param otcFixFloatLabel The OTC Fix Float Latent State Label
	 * 
	 * @return TRUE - The OTC Fix Float Latent State Exists
	 */

	public boolean otcFixFloatExists (
		final org.drip.state.identifier.OTCFixFloatLabel otcFixFloatLabel)
	{
		return null == otcFixFloatLabel ? false : _otcFixFloatEvolver.containsKey
			(otcFixFloatLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve the OTC Fix Float Latent State
	 * 
	 * @param otcFixFloatLabel The OTC Fix Float Latent State Label
	 * 
	 * @return The OTC Fix Float Latent State
	 */

	public org.drip.xva.evolver.TerminalLatentState otcFixFloat (
		final org.drip.state.identifier.OTCFixFloatLabel otcFixFloatLabel)
	{
		return otcFixFloatExists (otcFixFloatLabel) ? _otcFixFloatEvolver.get
			(otcFixFloatLabel.fullyQualifiedName()) : null;
	}

	/**
	 * Retrieve the Overnight Evolver Map
	 * 
	 * @return The Overnight Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> overnightMap()
	{
		return _overnightEvolver;
	}

	/**
	 * Add the Overnight Latent State Evolver
	 * 
	 * @param terminalLatentState The Overnight Terminal Latent State
	 * 
	 * @return TRUE - The Overnight Latent State Successfully added
	 */

	public boolean addOvernight (
		final org.drip.xva.evolver.TerminalLatentState terminalLatentState)
	{
		if (null == terminalLatentState)
		{
			return false;
		}

		_overnightEvolver.put (
			terminalLatentState.label().fullyQualifiedName(),
			terminalLatentState
		);

		return true;
	}

	/**
	 * Indicate if the Overnight Latent State Exists
	 * 
	 * @param overnightLabel The Overnight Latent State Label
	 * 
	 * @return TRUE - The Overnight Latent State Exists
	 */

	public boolean overnightExists (
		final org.drip.state.identifier.OvernightLabel overnightLabel)
	{
		return null == overnightLabel ? false : _overnightEvolver.containsKey
			(overnightLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve the Overnight Latent State
	 * 
	 * @param overnightLabel The Overnight Latent State Label
	 * 
	 * @return The Overnight Latent State
	 */

	public org.drip.xva.evolver.TerminalLatentState overnight (
		final org.drip.state.identifier.OvernightLabel overnightLabel)
	{
		return overnightExists (overnightLabel) ? _overnightEvolver.get (overnightLabel.fullyQualifiedName())
			: null;
	}

	/**
	 * Retrieve the Collateral Evolver Map
	 * 
	 * @return The Collateral Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> collateralMap()
	{
		return _collateralEvolver;
	}

	/**
	 * Add the Collateral Latent State Evolver
	 * 
	 * @param terminalLatentState The Collateral Terminal Latent State
	 * 
	 * @return TRUE - The Collateral Latent State Successfully added
	 */

	public boolean addCollateral (
		final org.drip.xva.evolver.TerminalLatentState terminalLatentState)
	{
		if (null == terminalLatentState)
		{
			return false;
		}

		_collateralEvolver.put (
			terminalLatentState.label().fullyQualifiedName(),
			terminalLatentState
		);

		return true;
	}

	/**
	 * Indicate if the Collateral Latent State Exists
	 * 
	 * @param collateralLabel The Collateral Latent State Label
	 * 
	 * @return TRUE - The Collateral Latent State Exists
	 */

	public boolean collateralExists (
		final org.drip.state.identifier.CollateralLabel collateralLabel)
	{
		return null == collateralLabel ? false : _collateralEvolver.containsKey
			(collateralLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve the Collateral Latent State
	 * 
	 * @param collateralLabel The Collateral Latent State Label
	 * 
	 * @return The Collateral Latent State
	 */

	public org.drip.xva.evolver.TerminalLatentState collateral (
		final org.drip.state.identifier.CollateralLabel collateralLabel)
	{
		return collateralExists (collateralLabel) ? _collateralEvolver.get
			(collateralLabel.fullyQualifiedName()) : null;
	}

	/**
	 * Retrieve the CSA Evolver Map
	 * 
	 * @return The CSA Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> csaMap()
	{
		return _csaEvolver;
	}

	/**
	 * Add the CSA Latent State Evolver
	 * 
	 * @param terminalLatentState The CSA Terminal Latent State
	 * 
	 * @return TRUE - The CSA Latent State Successfully added
	 */

	public boolean addCSA (
		final org.drip.xva.evolver.TerminalLatentState terminalLatentState)
	{
		if (null == terminalLatentState)
		{
			return false;
		}

		_csaEvolver.put (
			terminalLatentState.label().fullyQualifiedName(),
			terminalLatentState
		);

		return true;
	}

	/**
	 * Indicate if the CSA Latent State Exists
	 * 
	 * @param csaLabel The CSA Latent State Label
	 * 
	 * @return TRUE - The CSA Latent State Exists
	 */

	public boolean csaExists (
		final org.drip.state.identifier.CSALabel csaLabel)
	{
		return null == csaLabel ? false : _csaEvolver.containsKey (csaLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve the CSA Latent State
	 * 
	 * @param csaLabel The CSA Latent State Label
	 * 
	 * @return The CSA Latent State
	 */

	public org.drip.xva.evolver.TerminalLatentState csa (
		final org.drip.state.identifier.CSALabel csaLabel)
	{
		return csaExists (csaLabel) ? _csaEvolver.get (csaLabel.fullyQualifiedName()) : null;
	}

	/**
	 * Retrieve the Entity Hazard Evolver Map
	 * 
	 * @return The Entity Hazard Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> entityHazardMap()
	{
		return _entityHazardEvolver;
	}

	/**
	 * Add the Entity Hazard Latent State Evolver
	 * 
	 * @param terminalLatentState The Entity Hazard Terminal Latent State
	 * 
	 * @return TRUE - The Entity Hazard Latent State Successfully added
	 */

	public boolean addEntityHazard (
		final org.drip.xva.evolver.TerminalLatentState terminalLatentState)
	{
		if (null == terminalLatentState)
		{
			return false;
		}

		_entityHazardEvolver.put (
			terminalLatentState.label().fullyQualifiedName(),
			terminalLatentState
		);

		return true;
	}

	/**
	 * Indicate if the Entity Hazard Latent State Exists
	 * 
	 * @param entityHazardLabel The Entity Hazard Latent State Label
	 * 
	 * @return TRUE - The Entity Hazard Latent State Exists
	 */

	public boolean entityHazardExists (
		final org.drip.state.identifier.EntityHazardLabel entityHazardLabel)
	{
		return null == entityHazardLabel ? false : _entityHazardEvolver.containsKey
			(entityHazardLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve the Entity Hazard Latent State
	 * 
	 * @param entityHazardLabel The Entity Hazard Latent State Label
	 * 
	 * @return The Entity Hazard Latent State
	 */

	public org.drip.xva.evolver.TerminalLatentState entityHazard (
		final org.drip.state.identifier.EntityHazardLabel entityHazardLabel)
	{
		return entityHazardExists (entityHazardLabel) ? _entityHazardEvolver.get
			(entityHazardLabel.fullyQualifiedName()) : null;
	}

	/**
	 * Retrieve the Entity Credit Evolver Map
	 * 
	 * @return The Entity Credit Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> entityCreditMap()
	{
		return _entityCreditEvolver;
	}

	/**
	 * Add the Entity Credit Latent State Evolver
	 * 
	 * @param terminalLatentState The Entity Credit Terminal Latent State
	 * 
	 * @return TRUE - The Entity Credit Latent State Successfully added
	 */

	public boolean addEntityCredit (
		final org.drip.xva.evolver.TerminalLatentState terminalLatentState)
	{
		if (null == terminalLatentState)
		{
			return false;
		}

		_entityCreditEvolver.put (
			terminalLatentState.label().fullyQualifiedName(),
			terminalLatentState
		);

		return true;
	}

	/**
	 * Indicate if the Entity Credit Latent State Exists
	 * 
	 * @param entityCreditLabel The Entity Credit Latent State Label
	 * 
	 * @return TRUE - The Entity Credit Latent State Exists
	 */

	public boolean entityCreditExists (
		final org.drip.state.identifier.EntityCDSLabel entityCreditLabel)
	{
		return null == entityCreditLabel ? false : _entityCreditEvolver.containsKey
			(entityCreditLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve the Entity Credit Latent State
	 * 
	 * @param entityCreditLabel The Entity Credit Latent State Label
	 * 
	 * @return The Entity Credit Latent State
	 */

	public org.drip.xva.evolver.TerminalLatentState entityCredit (
		final org.drip.state.identifier.EntityCDSLabel entityCreditLabel)
	{
		return entityCreditExists (entityCreditLabel) ? _entityCreditEvolver.get
			(entityCreditLabel.fullyQualifiedName()) : null;
	}

	/**
	 * Retrieve the Entity Recovery Evolver Map
	 * 
	 * @return The Entity Recovery Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> entityRecoveryMap()
	{
		return _entityRecoveryEvolver;
	}

	/**
	 * Add the Entity Recovery Latent State Evolver
	 * 
	 * @param terminalLatentState The Entity Recovery Terminal Latent State
	 * 
	 * @return TRUE - The Entity Recovery Latent State Successfully added
	 */

	public boolean addEntityRecovery (
		final org.drip.xva.evolver.TerminalLatentState terminalLatentState)
	{
		if (null == terminalLatentState)
		{
			return false;
		}

		_entityRecoveryEvolver.put (
			terminalLatentState.label().fullyQualifiedName(),
			terminalLatentState
		);

		return true;
	}

	/**
	 * Indicate if the Entity Recovery Latent State Exists
	 * 
	 * @param entityRecoveryLabel The Entity Recovery Latent State Label
	 * 
	 * @return TRUE - The Entity Recovery Latent State Exists
	 */

	public boolean entityRecoveryExists (
		final org.drip.state.identifier.EntityRecoveryLabel entityRecoveryLabel)
	{
		return null == entityRecoveryLabel ? false : _entityRecoveryEvolver.containsKey
			(entityRecoveryLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve the Entity Recovery Latent State
	 * 
	 * @param entityRecoveryLabel The Entity Recovery Latent State Label
	 * 
	 * @return The Entity Recovery Latent State
	 */

	public org.drip.xva.evolver.TerminalLatentState entityRecovery (
		final org.drip.state.identifier.EntityRecoveryLabel entityRecoveryLabel)
	{
		return entityRecoveryExists (entityRecoveryLabel) ? _entityRecoveryEvolver.get
			(entityRecoveryLabel.fullyQualifiedName()) : null;
	}

	/**
	 * Retrieve the Entity Funding Evolver Map
	 * 
	 * @return The Entity Funding Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> entityFundingMap()
	{
		return _entityFundingEvolver;
	}

	/**
	 * Add the Entity Funding Latent State Evolver
	 * 
	 * @param terminalLatentState The Entity Funding Terminal Latent State
	 * 
	 * @return TRUE - The Entity Funding Latent State Successfully added
	 */

	public boolean addEntityFunding (
		final org.drip.xva.evolver.TerminalLatentState terminalLatentState)
	{
		if (null == terminalLatentState)
		{
			return false;
		}

		_entityFundingEvolver.put (
			terminalLatentState.label().fullyQualifiedName(),
			terminalLatentState
		);

		return true;
	}

	/**
	 * Indicate if the Entity Funding Latent State Exists
	 * 
	 * @param entityFundingLabel The Entity Funding Latent State Label
	 * 
	 * @return TRUE - The Entity Funding Latent State Exists
	 */

	public boolean entityFundingExists (
		final org.drip.state.identifier.EntityFundingLabel entityFundingLabel)
	{
		return null == entityFundingLabel ? false : _entityFundingEvolver.containsKey
			(entityFundingLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve the Entity Funding Latent State
	 * 
	 * @param entityFundingLabel The Entity Funding Latent State Label
	 * 
	 * @return The Entity Funding Latent State
	 */

	public org.drip.xva.evolver.TerminalLatentState entityFunding (
		final org.drip.state.identifier.EntityFundingLabel entityFundingLabel)
	{
		return entityFundingExists (entityFundingLabel) ? _entityFundingEvolver.get
			(entityFundingLabel.fullyQualifiedName()) : null;
	}

	/**
	 * Retrieve the Volatility Evolver Map
	 * 
	 * @return The Volatility Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> volatilityMap()
	{
		return _volatilityEvolver;
	}

	/**
	 * Add the Volatility Latent State Evolver
	 * 
	 * @param terminalLatentState The Volatility Terminal Latent State
	 * 
	 * @return TRUE - The Volatility Latent State Successfully added
	 */

	public boolean addVolatility (
		final org.drip.xva.evolver.TerminalLatentState terminalLatentState)
	{
		if (null == terminalLatentState)
		{
			return false;
		}

		_volatilityEvolver.put (
			terminalLatentState.label().fullyQualifiedName(),
			terminalLatentState
		);

		return true;
	}

	/**
	 * Indicate if the Volatility Latent State Exists
	 * 
	 * @param volatilityLabel The Volatility Latent State Label
	 * 
	 * @return TRUE - The Volatility Latent State Exists
	 */

	public boolean volatilityExists (
		final org.drip.state.identifier.VolatilityLabel volatilityLabel)
	{
		return null == volatilityLabel ? false : _volatilityEvolver.containsKey
			(volatilityLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve the Volatility Latent State
	 * 
	 * @param volatilityLabel The Volatility Latent State Label
	 * 
	 * @return The Volatility Latent State
	 */

	public org.drip.xva.evolver.TerminalLatentState volatility (
		final org.drip.state.identifier.VolatilityLabel volatilityLabel)
	{
		return volatilityExists (volatilityLabel) ? _volatilityEvolver.get
			(volatilityLabel.fullyQualifiedName()) : null;
	}

	/**
	 * Retrieve the Ratings Evolver Map
	 * 
	 * @return The Ratings Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> ratingMap()
	{
		return _ratingEvolver;
	}

	/**
	 * Add the Rating Latent State Evolver
	 * 
	 * @param terminalLatentState The Rating Terminal Latent State
	 * 
	 * @return TRUE - The Rating Latent State Successfully added
	 */

	public boolean addRating (
		final org.drip.xva.evolver.TerminalLatentState terminalLatentState)
	{
		if (null == terminalLatentState)
		{
			return false;
		}

		_ratingEvolver.put (
			terminalLatentState.label().fullyQualifiedName(),
			terminalLatentState
		);

		return true;
	}

	/**
	 * Indicate if the Rating Latent State Exists
	 * 
	 * @param ratingLabel The Rating Latent State Label
	 * 
	 * @return TRUE - The Rating Latent State Exists
	 */

	public boolean ratingExists (
		final org.drip.state.identifier.RatingLabel ratingLabel)
	{
		return null == ratingLabel ? false : _ratingEvolver.containsKey (ratingLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve the Rating Latent State
	 * 
	 * @param ratingLabel The Rating Latent State Label
	 * 
	 * @return The Rating Latent State
	 */

	public org.drip.xva.evolver.TerminalLatentState rating (
		final org.drip.state.identifier.RatingLabel ratingLabel)
	{
		return ratingExists (ratingLabel) ? _ratingEvolver.get (ratingLabel.fullyQualifiedName()) : null;
	}

	/**
	 * Retrieve the Repo Evolver Map
	 * 
	 * @return The Repo Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> repoMap()
	{
		return _repoEvolver;
	}

	/**
	 * Add the Repo Latent State Evolver
	 * 
	 * @param terminalLatentState The Repo Terminal Latent State
	 * 
	 * @return TRUE - The Repo Latent State Successfully added
	 */

	public boolean addRepo (
		final org.drip.xva.evolver.TerminalLatentState terminalLatentState)
	{
		if (null == terminalLatentState)
		{
			return false;
		}

		_repoEvolver.put (
			terminalLatentState.label().fullyQualifiedName(),
			terminalLatentState
		);

		return true;
	}

	/**
	 * Indicate if the Repo Latent State Exists
	 * 
	 * @param repoLabel The Repo Latent State Label
	 * 
	 * @return TRUE - The Repo Latent State Exists
	 */

	public boolean repoExists (
		final org.drip.state.identifier.RepoLabel repoLabel)
	{
		return null == repoLabel ? false : _repoEvolver.containsKey (repoLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve the Repo Latent State
	 * 
	 * @param repoLabel The Repo Latent State Label
	 * 
	 * @return The Repo Latent State
	 */

	public org.drip.xva.evolver.TerminalLatentState repo (
		final org.drip.state.identifier.RepoLabel repoLabel)
	{
		return repoExists (repoLabel) ? _repoEvolver.get (repoLabel.fullyQualifiedName()) : null;
	}

	/**
	 * Retrieve the Pay Down Evolver Map
	 * 
	 * @return The Pay Down Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> payDownMap()
	{
		return _payDownEvolver;
	}

	/**
	 * Add the Pay Down Latent State Evolver
	 * 
	 * @param terminalLatentState The Pay Down Terminal Latent State
	 * 
	 * @return TRUE - The Repo Latent State Successfully added
	 */

	public boolean addPayDown (
		final org.drip.xva.evolver.TerminalLatentState terminalLatentState)
	{
		if (null == terminalLatentState)
		{
			return false;
		}

		_payDownEvolver.put (
			terminalLatentState.label().fullyQualifiedName(),
			terminalLatentState
		);

		return true;
	}

	/**
	 * Indicate if the Pay Down Latent State Exists
	 * 
	 * @param payDownLabel The Pay Down Latent State Label
	 * 
	 * @return TRUE - The Pay Down Latent State Exists
	 */

	public boolean payDownExists (
		final org.drip.state.identifier.PaydownLabel payDownLabel)
	{
		return null == payDownLabel ? false : _payDownEvolver.containsKey
			(payDownLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve the Pay Down Latent State
	 * 
	 * @param payDownLabel The Pay Down Latent State Label
	 * 
	 * @return The Pay Down Latent State
	 */

	public org.drip.xva.evolver.TerminalLatentState payDown (
		final org.drip.state.identifier.PaydownLabel payDownLabel)
	{
		return payDownExists (payDownLabel) ? _payDownEvolver.get (payDownLabel.fullyQualifiedName()) : null;
	}

	/**
	 * Retrieve the Custom Evolver Map
	 * 
	 * @return The Custom Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.xva.evolver.TerminalLatentState> customMap()
	{
		return _customEvolver;
	}

	/**
	 * Add the Custom Latent State Evolver
	 * 
	 * @param terminalLatentState The Custom Terminal Latent State
	 * 
	 * @return TRUE - The Custom Latent State Successfully added
	 */

	public boolean addCustom (
		final org.drip.xva.evolver.TerminalLatentState terminalLatentState)
	{
		if (null == terminalLatentState)
		{
			return false;
		}

		_customEvolver.put (
			terminalLatentState.label().fullyQualifiedName(),
			terminalLatentState
		);

		return true;
	}

	/**
	 * Indicate if the Custom Latent State Exists
	 * 
	 * @param customLabel The Custom Latent State Label
	 * 
	 * @return TRUE - The Custom Latent State Exists
	 */

	public boolean customExists (
		final org.drip.state.identifier.CustomLabel customLabel)
	{
		return null == customLabel ? false : _customEvolver.containsKey (customLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve the Custom Latent State
	 * 
	 * @param customLabel The Custom Latent State Label
	 * 
	 * @return The Custom Latent State
	 */

	public org.drip.xva.evolver.TerminalLatentState custom (
		final org.drip.state.identifier.CustomLabel customLabel)
	{
		return customExists (customLabel) ? _customEvolver.get (customLabel.fullyQualifiedName()) : null;
	}

	/**
	 * Add the Terminal Latent State
	 * 
	 * @param terminalLatentState The Terminal Latent State
	 * 
	 * @return TRUE - The Terminal Latent State Successfully added
	 */

	public boolean addTerminalLatentState (
		final org.drip.xva.evolver.TerminalLatentState terminalLatentState)
	{
		if (null == terminalLatentState)
		{
			return false;
		}

		org.drip.state.identifier.LatentStateLabel label = terminalLatentState.label();

		if (label instanceof org.drip.state.identifier.EntityEquityLabel)
		{
			return addEntityEquity (terminalLatentState);
		}

		if (label instanceof org.drip.state.identifier.FundingLabel)
		{
			return addFunding (terminalLatentState);
		}

		if (label instanceof org.drip.state.identifier.GovvieLabel)
		{
			return addGovvie (terminalLatentState);
		}

		if (label instanceof org.drip.state.identifier.FXLabel)
		{
			return addFX (terminalLatentState);
		}

		if (label instanceof org.drip.state.identifier.ForwardLabel)
		{
			return addForward (terminalLatentState);
		}

		if (label instanceof org.drip.state.identifier.OTCFixFloatLabel)
		{
			return addOTCFixFloat (terminalLatentState);
		}

		if (label instanceof org.drip.state.identifier.OvernightLabel)
		{
			return addOvernight (terminalLatentState);
		}

		if (label instanceof org.drip.state.identifier.CollateralLabel)
		{
			return addCollateral (terminalLatentState);
		}

		if (label instanceof org.drip.state.identifier.CSALabel)
		{
			return addCSA (terminalLatentState);
		}

		if (label instanceof org.drip.state.identifier.EntityHazardLabel)
		{
			return addEntityHazard (terminalLatentState);
		}

		if (label instanceof org.drip.state.identifier.EntityRecoveryLabel)
		{
			return addEntityRecovery (terminalLatentState);
		}

		if (label instanceof org.drip.state.identifier.EntityFundingLabel)
		{
			return addEntityFunding (terminalLatentState);
		}

		if (label instanceof org.drip.state.identifier.EntityCDSLabel)
		{
			return addEntityCredit (terminalLatentState);
		}

		if (label instanceof org.drip.state.identifier.VolatilityLabel)
		{
			return addVolatility (terminalLatentState);
		}

		if (label instanceof org.drip.state.identifier.RatingLabel)
		{
			return addRating (terminalLatentState);
		}

		if (label instanceof org.drip.state.identifier.RepoLabel)
		{
			return addRepo (terminalLatentState);
		}

		if (label instanceof org.drip.state.identifier.PaydownLabel)
		{
			return addPayDown (terminalLatentState);
		}

		if (label instanceof org.drip.state.identifier.CustomLabel)
		{
			return addCustom (terminalLatentState);
		}

		return false;
	}

	/**
	 * Indicate if the Label exists
	 * 
	 * @param label The Latent State Label
	 * 
	 * @return TRUE - The Latent State Label exists
	 */

	public boolean labelExists (
		final org.drip.state.identifier.LatentStateLabel label)
	{
		if (null == label)
		{
			return false;
		}

		if (label instanceof org.drip.state.identifier.EntityEquityLabel)
		{
			return entityEquityExists ((org.drip.state.identifier.EntityEquityLabel) label);
		}

		if (label instanceof org.drip.state.identifier.FundingLabel)
		{
			return fundingExists((org.drip.state.identifier.FundingLabel) label);
		}

		if (label instanceof org.drip.state.identifier.GovvieLabel)
		{
			return govvieExists ((org.drip.state.identifier.GovvieLabel) label);
		}

		if (label instanceof org.drip.state.identifier.FXLabel)
		{
			return fxExists((org.drip.state.identifier.FXLabel) label);
		}

		if (label instanceof org.drip.state.identifier.ForwardLabel)
		{
			return forwardExists ((org.drip.state.identifier.ForwardLabel) label);
		}

		if (label instanceof org.drip.state.identifier.OTCFixFloatLabel)
		{
			return otcFixFloatExists ((org.drip.state.identifier.OTCFixFloatLabel) label);
		}

		if (label instanceof org.drip.state.identifier.OvernightLabel)
		{
			return overnightExists ((org.drip.state.identifier.OvernightLabel) label);
		}

		if (label instanceof org.drip.state.identifier.CollateralLabel)
		{
			return collateralExists ((org.drip.state.identifier.CollateralLabel) label);
		}

		if (label instanceof org.drip.state.identifier.CSALabel)
		{
			return csaExists ((org.drip.state.identifier.CSALabel) label);
		}

		if (label instanceof org.drip.state.identifier.EntityHazardLabel)
		{
			return entityHazardExists ((org.drip.state.identifier.EntityHazardLabel) label);
		}

		if (label instanceof org.drip.state.identifier.EntityCDSLabel)
		{
			return entityCreditExists ((org.drip.state.identifier.EntityCDSLabel) label);
		}

		if (label instanceof org.drip.state.identifier.EntityRecoveryLabel)
		{
			return entityRecoveryExists ((org.drip.state.identifier.EntityRecoveryLabel) label);
		}

		if (label instanceof org.drip.state.identifier.EntityFundingLabel)
		{
			return entityFundingExists ((org.drip.state.identifier.EntityFundingLabel) label);
		}

		if (label instanceof org.drip.state.identifier.VolatilityLabel)
		{
			return volatilityExists ((org.drip.state.identifier.VolatilityLabel) label);
		}

		if (label instanceof org.drip.state.identifier.RatingLabel)
		{
			return ratingExists ((org.drip.state.identifier.RatingLabel) label);
		}

		if (label instanceof org.drip.state.identifier.RepoLabel)
		{
			return repoExists ((org.drip.state.identifier.RepoLabel) label);
		}

		if (label instanceof org.drip.state.identifier.PaydownLabel)
		{
			return payDownExists ((org.drip.state.identifier.PaydownLabel) label);
		}

		if (label instanceof org.drip.state.identifier.CustomLabel)
		{
			return customExists ((org.drip.state.identifier.CustomLabel) label);
		}

		return false;
	}
}