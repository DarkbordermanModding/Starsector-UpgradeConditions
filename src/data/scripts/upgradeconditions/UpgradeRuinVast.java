package data.scripts.upgradeconditions;

import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.impl.campaign.ids.Conditions;


public class UpgradeRuinVast extends BaseIndustry {

	@Override
	public void apply() {
		super.apply(true);
	}

	@Override
	protected void buildingFinished(){
		super.buildingFinished();

		getMarket().removeCondition(Conditions.RUINS_EXTENSIVE);
		getMarket().addCondition(Conditions.RUINS_VAST);
		getMarket().getCondition(Conditions.RUINS_VAST).setSurveyed(true);
		getMarket().reapplyConditions();
		for(Industry industry: getMarket().getIndustries()){
			industry.doPreSaveCleanup();
			industry.doPostSaveRestore();
		}
		getMarket().removeIndustry("upgraderuinvast", null, false);
	}

	@Override
	public boolean isAvailableToBuild() {
		if(getMarket().hasCondition(Conditions.RUINS_EXTENSIVE)) return true;
		return false;
	}

	@Override
	public boolean showWhenUnavailable(){
		return false;
	}
}
