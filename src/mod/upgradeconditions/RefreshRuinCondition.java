package mod.upgradeconditions;

import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.impl.campaign.ids.Conditions;


public class RefreshRuinCondition extends BaseIndustry {

	@Override
	public void apply() {
		super.apply(true);
	}

	@Override
	protected void buildingFinished(){
		super.buildingFinished();

        MemoryAPI mem = market.getMemoryWithoutUpdate();
		if (mem.contains("$core_techMiningMult")) {
            mem.set("$core_techMiningMult", 1f);
		}

		getMarket().reapplyConditions();
		for(Industry industry: getMarket().getIndustries()){
			industry.doPreSaveCleanup();
			industry.doPostSaveRestore();
		}
		getMarket().removeIndustry("refreshruincondition", null, false);
	}

	@Override
	public boolean isAvailableToBuild() {
		if(
            getMarket().hasCondition(Conditions.RUINS_SCATTERED) ||
            getMarket().hasCondition(Conditions.RUINS_WIDESPREAD) ||
            getMarket().hasCondition(Conditions.RUINS_EXTENSIVE) ||
            getMarket().hasCondition(Conditions.RUINS_VAST)
        ) return true;
		return false;
	}

	@Override
	public boolean showWhenUnavailable(){
		return false;
	}
}
