package data.scripts.upgradeconditions;

import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.impl.campaign.ids.Conditions;


public class UpgradeOreAbundant extends BaseIndustry {

	@Override
	public void apply() {
		super.apply(true);
	}

	@Override
	protected void buildingFinished(){
		super.buildingFinished();
 		getMarket().removeCondition(Conditions.ORE_MODERATE);
		getMarket().addCondition(Conditions.ORE_ABUNDANT);
		getMarket().getCondition(Conditions.ORE_ABUNDANT).setSurveyed(true);
		getMarket().reapplyConditions();
		for(Industry industry: getMarket().getIndustries()){
			industry.doPreSaveCleanup();
			industry.doPostSaveRestore();
		}
		getMarket().removeIndustry("upgradeoreabundant", null, false);
	}

	@Override
	public boolean isAvailableToBuild() {
		if(getMarket().hasCondition(Conditions.ORE_MODERATE)) return true;
		return false;
	}

	@Override
	public boolean showWhenUnavailable(){
		return false;
	}
}
