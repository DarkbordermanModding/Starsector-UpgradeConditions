package data.scripts.upgradeconditions;

import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.impl.campaign.ids.Conditions;


public class UpgradeVolatilesTrace extends BaseIndustry {

	@Override
	public void apply() {
		super.apply(true);
	}

	@Override
	protected void buildingFinished(){
		super.buildingFinished();
		getMarket().addCondition(Conditions.VOLATILES_TRACE);
		getMarket().getCondition(Conditions.VOLATILES_TRACE).setSurveyed(true);
		getMarket().reapplyConditions();
		for(Industry industry: getMarket().getIndustries()){
			industry.doPreSaveCleanup();
			industry.doPostSaveRestore();
		}
		getMarket().removeIndustry("upgradevolatilestrace", null, false);
	}

	@Override
	public boolean isAvailableToBuild() {
		if(
			getMarket().hasCondition(Conditions.VOLATILES_TRACE) ||
			getMarket().hasCondition(Conditions.VOLATILES_DIFFUSE) ||
			getMarket().hasCondition(Conditions.VOLATILES_ABUNDANT) ||
			getMarket().hasCondition(Conditions.VOLATILES_PLENTIFUL)
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
