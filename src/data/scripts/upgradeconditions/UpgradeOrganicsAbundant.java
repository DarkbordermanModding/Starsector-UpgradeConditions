package data.scripts.upgradeconditions;

import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.impl.campaign.ids.Conditions;


public class UpgradeOrganicsAbundant extends BaseIndustry {

	@Override
	public void apply() {
		super.apply(true);
	}

	@Override
	protected void buildingFinished(){
		super.buildingFinished();
		getMarket().removeCondition(Conditions.ORGANICS_COMMON);
		getMarket().addCondition(Conditions.ORGANICS_ABUNDANT);
		getMarket().getCondition(Conditions.ORGANICS_ABUNDANT).setSurveyed(true);
		getMarket().reapplyConditions();
		for(Industry industry: getMarket().getIndustries()){
			industry.doPreSaveCleanup();
			industry.doPostSaveRestore();
		}
		getMarket().removeIndustry("upgradeorganicsabundant", null, false);
	}

	@Override
	public boolean isAvailableToBuild() {
		if(getMarket().hasCondition(Conditions.ORGANICS_COMMON)) return true;
		return false;
	}

	@Override
	public boolean showWhenUnavailable(){
		return false;
	}
}
