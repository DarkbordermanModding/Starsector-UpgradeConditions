package data.scripts.upgradeconditions;

import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.impl.campaign.ids.Conditions;


public class UpgradeFarmlandPoor extends BaseIndustry {

	@Override
	public void apply() {
		super.apply(true);
	}

	@Override
	protected void buildingFinished(){
		super.buildingFinished();
		getMarket().addCondition(Conditions.FARMLAND_POOR);
		getMarket().getCondition(Conditions.FARMLAND_POOR).setSurveyed(true);
		getMarket().reapplyConditions();
		for(Industry industry: getMarket().getIndustries()){
			industry.doPreSaveCleanup();
			industry.doPostSaveRestore();
		}
		getMarket().removeIndustry("upgradefarmlandpoor", null, false);
	}

	@Override
	public boolean isAvailableToBuild() {
		if(
			getMarket().hasCondition(Conditions.FARMLAND_POOR) ||
			getMarket().hasCondition(Conditions.FARMLAND_ADEQUATE) ||
			getMarket().hasCondition(Conditions.FARMLAND_RICH) ||
			getMarket().hasCondition(Conditions.FARMLAND_BOUNTIFUL)
		){
			return false;
		}
		return true;
	}

	@Override
	public boolean showWhenUnavailable(){
		return false;
	}
}
