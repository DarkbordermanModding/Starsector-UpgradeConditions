package mod.upgradeconditions;

import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.impl.campaign.ids.Conditions;


public class UpgradeRareOreSparse extends BaseIndustry {

	@Override
	public void apply() {
		super.apply(true);
	}

	@Override
	protected void buildingFinished(){
		super.buildingFinished();
		getMarket().addCondition(Conditions.RARE_ORE_SPARSE);
		getMarket().getCondition(Conditions.RARE_ORE_SPARSE).setSurveyed(true);
		getMarket().reapplyConditions();
		for(Industry industry: getMarket().getIndustries()){
			industry.doPreSaveCleanup();
			industry.doPostSaveRestore();
		}
		getMarket().removeIndustry("upgraderareoresparse", null, false);
	}

	@Override
	public boolean isAvailableToBuild() {
		if(
			getMarket().hasCondition(Conditions.RARE_ORE_SPARSE) ||
			getMarket().hasCondition(Conditions.RARE_ORE_ABUNDANT) ||
			getMarket().hasCondition(Conditions.RARE_ORE_MODERATE) ||
			getMarket().hasCondition(Conditions.RARE_ORE_RICH) ||
			getMarket().hasCondition(Conditions.RARE_ORE_ULTRARICH)
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
