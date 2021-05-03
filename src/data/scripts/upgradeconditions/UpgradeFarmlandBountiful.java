package data.scripts.upgradeconditions;

import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.impl.campaign.ids.Conditions;


public class UpgradeFarmlandBountiful extends BaseIndustry {

	@Override
	public void apply() {
		super.apply(true);
	}

	@Override
	protected void buildingFinished(){
		super.buildingFinished();
		getMarket().removeCondition(Conditions.FARMLAND_RICH);
		getMarket().addCondition(Conditions.FARMLAND_BOUNTIFUL);
		getMarket().getCondition(Conditions.FARMLAND_BOUNTIFUL).setSurveyed(true);
		getMarket().reapplyConditions();
		for(Industry industry: getMarket().getIndustries()){
			industry.doPreSaveCleanup();
			industry.doPostSaveRestore();
		}
		getMarket().removeIndustry("upgradefarmlandbountiful", null, false);
	}

	@Override
	public boolean isAvailableToBuild() {
		if(getMarket().hasCondition(Conditions.FARMLAND_RICH)) return true;
		return false;
	}

	@Override
	public boolean showWhenUnavailable(){
		return false;
	}
}
