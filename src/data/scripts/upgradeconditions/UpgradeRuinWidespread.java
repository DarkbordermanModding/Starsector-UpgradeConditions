package data.scripts.upgradeconditions;

import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.impl.campaign.ids.Conditions;


public class UpgradeRuinWidespread extends BaseIndustry {

	@Override
	public void apply() {
		super.apply(true);
	}

	@Override
	protected void buildingFinished(){
		super.buildingFinished();

        MemoryAPI mem = market.getMemoryWithoutUpdate();
		if (mem.contains("$hasUnexploredRuins")) {
            mem.set("$hasUnexploredRuins", false);
		}

		getMarket().removeCondition(Conditions.RUINS_SCATTERED);
		getMarket().addCondition(Conditions.RUINS_WIDESPREAD);
		getMarket().getCondition(Conditions.RUINS_WIDESPREAD).setSurveyed(true);
		getMarket().reapplyConditions();
		for(Industry industry: getMarket().getIndustries()){
			industry.doPreSaveCleanup();
			industry.doPostSaveRestore();
		}
		getMarket().removeIndustry("updateruinwidespread", null, false);
	}

	@Override
	public boolean isAvailableToBuild() {
		if(getMarket().hasCondition(Conditions.RUINS_SCATTERED)) return true;
		return false;
	}

	@Override
	public boolean showWhenUnavailable(){
		return false;
	}
}
