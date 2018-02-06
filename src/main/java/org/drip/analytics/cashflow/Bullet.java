
package org.drip.analytics.cashflow;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * Bullet is designed to hold the Point Realizations of the Latent States relevant to Terminal Valuation of a
 *  Bullet Cash Flow. Current it contains the Period Dates, Period Latent State Identifiers, and the
 *  "Extensive" Fields.
 * 
 *
 * @author Lakshmi Krishnamurthy
 */

public class Bullet {

	/*
	 * Date Fields
	 */

	private int _iPayDate = java.lang.Integer.MIN_VALUE;
	private int _iFXFixingDate = java.lang.Integer.MIN_VALUE;
	private int _iTerminalDate = java.lang.Integer.MIN_VALUE;

	/*
	 * Period Latent State Identification Support Fields
	 */

	private java.lang.String _strPayCurrency = "";
	private java.lang.String _strCouponCurrency = "";
	private org.drip.state.identifier.EntityCreditLabel _creditLabel = null;

	/*
	 * Period Cash Extensive Fields
	 */

	private double _dblBaseNotional = java.lang.Double.NaN;
	private org.drip.quant.common.Array2D _a2DNotionalSchedule = null;

	private org.drip.analytics.output.ConvexityAdjustment convexityAdjustment (
		final int iValueDate,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc)
	{
		org.drip.state.identifier.FXLabel fxLabel = fxLabel();

		org.drip.state.identifier.EntityCreditLabel creditLabel = creditLabel();

		org.drip.state.identifier.FundingLabel fundingLabel = fundingLabel();

		org.drip.analytics.output.ConvexityAdjustment convAdj = new
			org.drip.analytics.output.ConvexityAdjustment();

		try {
			if (
				!convAdj.setCreditFunding (
					null != csqc ? java.lang.Math.exp (
						org.drip.analytics.support.OptionHelper.IntegratedCrossVolQuanto (
							csqc.creditVolatility (creditLabel),
							csqc.fundingVolatility (fundingLabel),
							csqc.creditFundingCorrelation (
								creditLabel,
								fundingLabel
							),
							iValueDate,
							_iPayDate
						)
					) : 1.
				))
				return null;

			if (
				!convAdj.setCreditFX (
					null != csqc && isFXMTM() ? java.lang.Math.exp (
						org.drip.analytics.support.OptionHelper.IntegratedCrossVolQuanto (
							csqc.creditVolatility (creditLabel),
							csqc.fxVolatility (fxLabel),
							csqc.creditFXCorrelation (
								creditLabel,
								fxLabel
							),
							iValueDate,
							_iPayDate
						)
					) : 1.
				))
				return null;

			if (
				!convAdj.setFundingFX (
					null != csqc && isFXMTM() ? java.lang.Math.exp (
						org.drip.analytics.support.OptionHelper.IntegratedCrossVolQuanto (
							csqc.fundingVolatility (fundingLabel),
							csqc.fxVolatility (fxLabel),
							csqc.fundingFXCorrelation (
								fundingLabel,
								fxLabel
							),
							iValueDate,
							_iPayDate
						)
					) : 1.
				))
				return null;

			return convAdj;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Bullet Instance from the specified Parameters
	 * 
	 * @param iTerminalDate Period End Date
	 * @param iPayDate Period Pay Date
	 * @param iFXFixingDate FX Fixing Date for non-MTM Cash-flow
	 * @param dblBaseNotional Coupon Period Base Notional
	 * @param a2DNotionalSchedule Coupon Period Notional Schedule
	 * @param strPayCurrency Pay Currency
	 * @param strCouponCurrency Coupon Currency
	 * @param creditLabel Credit Label
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public Bullet (
		final int iTerminalDate,
		final int iPayDate,
		final int iFXFixingDate,
		final double dblBaseNotional,
		final org.drip.quant.common.Array2D a2DNotionalSchedule,
		final java.lang.String strPayCurrency,
		final java.lang.String strCouponCurrency,
		final org.drip.state.identifier.EntityCreditLabel creditLabel)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblBaseNotional = dblBaseNotional) ||
			null == (_strPayCurrency = strPayCurrency) || _strPayCurrency.isEmpty() ||
			null == (_strCouponCurrency = strCouponCurrency) || _strCouponCurrency.isEmpty())
		throw new java.lang.Exception ("Bullet Constructor => Invalid inputs");

		_iPayDate = iPayDate;
		_creditLabel = creditLabel;
		_iFXFixingDate = iFXFixingDate;
		_iTerminalDate = iTerminalDate;

		if (null == (_a2DNotionalSchedule = a2DNotionalSchedule))
			_a2DNotionalSchedule = org.drip.quant.common.Array2D.BulletSchedule();
	}

	/**
	 * Retrieve the Terminal Date
	 * 
	 * @return Terminal Date
	 */

	public int terminalDate()
	{
		return _iTerminalDate;
	}

	/**
	 * Retrieve the Period Pay Date
	 * 
	 * @return Period Pay Date
	 */

	public int payDate()
	{
		return _iPayDate;
	}

	/**
	 * Retrieve the Period FX Fixing Date
	 * 
	 * @return Period FX Fixing Date
	 */

	public int fxFixingDate()
	{
		return _iFXFixingDate;
	}

	/**
	 * Coupon Period FX
	 * 
	 * @param csqc Market Parameters
	 * 
	 * @return Period FX
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double fx (
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc)
		throws java.lang.Exception
	{
		org.drip.state.identifier.FXLabel fxLabel = fxLabel();

		if (null == fxLabel) return 1.;

		if (null == csqc) throw new java.lang.Exception ("Bullet::fx => Invalid Inputs");

		if (!isFXMTM()) return csqc.fixing (_iFXFixingDate, fxLabel);

		org.drip.state.fx.FXCurve fxfc = csqc.fxState (fxLabel);

		if (null == fxfc)
			throw new java.lang.Exception ("Bullet::fx => No Curve for " + fxLabel.fullyQualifiedName());

		return fxfc.fx (_iPayDate);
	}

	/**
	 * Is the Cash Flow FX MTM?
	 * 
	 * @return true - FX MTM is on (i.e., FX is not driven by FX Fixing)
	 */

	public boolean isFXMTM()
	{
		return java.lang.Integer.MIN_VALUE != _iFXFixingDate;
	}

	/**
	 * Retrieve the Pay Currency
	 * 
	 * @return The Pay Currency
	 */

	public java.lang.String payCurrency()
	{
		return _strPayCurrency;
	}

	/**
	 * Retrieve the Coupon Currency
	 * 
	 * @return The Coupon Currency
	 */

	public java.lang.String couponCurrency()
	{
		return _strCouponCurrency;
	}

	/**
	 * Get the Base Notional
	 * 
	 * @return Base Notional
	 */

	public double baseNotional()
	{
		return _dblBaseNotional;
	}

	/**
	 * Get the Notional Schedule
	 * 
	 * @return Notional Schedule
	 */

	public org.drip.quant.common.Array2D notionalSchedule()
	{
		return _a2DNotionalSchedule;
	}

	/**
	 * Notional corresponding to the specified Date
	 * 
	 * @param iDate The Specified Date
	 * 
	 * @return The Corresponding to the specified Date
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double notional (
		final int iDate)
		throws java.lang.Exception
	{
		return _dblBaseNotional * (null == _a2DNotionalSchedule ? 1. : _a2DNotionalSchedule.y (iDate));
	}

	/**
	 * Notional Aggregated over the specified Dates
	 * 
	 * @param iDate1 The Date #1
	 * @param iDate2 The Date #2
	 * 
	 * @return The Notional Aggregated over the specified Dates
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double notional (
		final int iDate1,
		final int iDate2)
		throws java.lang.Exception
	{
		return _dblBaseNotional * (
			null == _a2DNotionalSchedule ? 1. : _a2DNotionalSchedule.y (
				iDate1,
				iDate2
			)
		);
	}

	/**
	 * Return the Collateral Label
	 * 
	 * @return The Collateral Label
	 */

	public org.drip.state.identifier.CollateralLabel collateralLabel()
	{
		return org.drip.state.identifier.CollateralLabel.Standard (_strPayCurrency);
	}

	/**
	 * Return the Credit Label
	 * 
	 * @return The Credit Label
	 */

	public org.drip.state.identifier.EntityCreditLabel creditLabel()
	{
		return _creditLabel;
	}

	/**
	 * Return the Funding Label
	 * 
	 * @return The Funding Label
	 */

	public org.drip.state.identifier.FundingLabel fundingLabel()
	{
		return org.drip.state.identifier.FundingLabel.Standard (_strPayCurrency);
	}

	/**
	 * Return the FX Label
	 * 
	 * @return The FX Label
	 */

	public org.drip.state.identifier.FXLabel fxLabel()
	{
		return _strPayCurrency.equalsIgnoreCase (_strCouponCurrency) ? null :
			org.drip.state.identifier.FXLabel.Standard (_strPayCurrency + "/" + _strCouponCurrency);
	}

	/**
	 * Compute the Metrics at the specified Valuation Date
	 * 
	 * @param iValueDate Valuation Date
	 * @param csqc The Market Curve Surface/Quote Set
	 * 
	 * @return The Metrics at the specified Valuation Date
	 */

	public org.drip.analytics.output.BulletMetrics metrics (
		final int iValueDate,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc)
	{
		double dblDF = 1.;
		double dblSurvival = 1.;

		org.drip.state.identifier.FXLabel fxLabel = fxLabel();

		org.drip.state.identifier.FundingLabel fundingLabel = fundingLabel();

		org.drip.state.credit.CreditCurve cc = null == csqc ? null :
			csqc.creditState (_creditLabel);

		org.drip.state.discount.MergedDiscountForwardCurve dcFunding = null == csqc ? null :
			csqc.fundingState (fundingLabel);

		try {
			double dblFX = fx (csqc);

			if (null != dcFunding) dblDF = dcFunding.df (_iPayDate);

			if (null != cc) dblSurvival = cc.survival (_iPayDate);

			return new org.drip.analytics.output.BulletMetrics (
				_iTerminalDate,
				_iPayDate,
				notional (_iTerminalDate),
				dblSurvival,
				dblDF,
				dblFX,
				convexityAdjustment (
					iValueDate,
					csqc
				),
				_creditLabel,
				fundingLabel,
				fxLabel
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Funding Predictor/Response Constraint
	 * 
	 * @param iValueDate Valuation Date
	 * @param csqc Market Curve Surface/Quote Set
	 * @param pqs Calibration Product Quote Set
	 * 
	 * @return The Funding Predictor/Response Constraint
	 */

	public org.drip.state.estimator.PredictorResponseWeightConstraint fundingPRWC (
		final int iValueDate,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.product.calib.ProductQuoteSet pqs)
	{
		if (null == pqs) return null;

		double dblPV = 0.;

		org.drip.state.estimator.PredictorResponseWeightConstraint prwc = new
			org.drip.state.estimator.PredictorResponseWeightConstraint();

		org.drip.analytics.output.BulletMetrics bm = metrics (
			iValueDate,
			csqc
		);

		if (null == bm) return null;

		java.util.Map<java.lang.Integer, java.lang.Double> mapDiscountFactorLoading =
			bm.discountFactorFundingLoading (pqs.fundingLabel());

		if (null != mapDiscountFactorLoading && 0 != mapDiscountFactorLoading.size()) {
			for (java.util.Map.Entry<java.lang.Integer, java.lang.Double> meDiscountFactorLoading :
				mapDiscountFactorLoading.entrySet()) {
				int iDateAnchor = meDiscountFactorLoading.getKey();

				double dblDiscountFactorFundingLoading = meDiscountFactorLoading.getValue();

				if (
					!prwc.addPredictorResponseWeight (
						iDateAnchor,
						dblDiscountFactorFundingLoading
					)
				)
					return null;

				if (
					!prwc.addDResponseWeightDManifestMeasure (
						"PV",
						iDateAnchor,
						dblDiscountFactorFundingLoading
					)
				)
					return null;
			}
		} else
			dblPV -= bm.annuity();

		if (!prwc.updateValue (dblPV)) return null;

		if (
			!prwc.updateDValueDManifestMeasure (
				"PV",
				1.
			)
		)
			return null;

		return prwc;
	}
}
