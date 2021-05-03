package data.scripts.upgradeconditions;

import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.impl.campaign.ids.Conditions;


public class UpgradeOrganicsTrace extends BaseIndustry {

	@Override
	public void apply() {
		super.apply(true);
	}

	@Override
	protected void buildingFinished(){
		super.buildingFinished();
		getMarket().addCondition(Conditions.ORGANICS_TRACE);
		getMarket().getCondition(Conditions.ORGANICS_TRACE).setSurveyed(true);
		getMarket().reapplyConditions();
		for(Industry industry: getMarket().getIndustries()){
			industry.doPreSaveCleanup();
			industry.doPostSaveRestore();
		}
		getMarket().removeIndustry("upgradeorganicstrace", null, false);
	}

	@Override
	public boolean isAvailableToBuild() {
		if(
			getMarket().hasCondition(Conditions.ORGANICS_TRACE) ||
			getMarket().hasCondition(Conditions.ORGANICS_COMMON) ||
			getMarket().hasCondition(Conditions.ORGANICS_ABUNDANT) ||
			getMarket().hasCondition(Conditions.ORGANICS_PLENTIFUL)
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
