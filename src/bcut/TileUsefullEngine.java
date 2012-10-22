package bcut;

import buildcraft.api.core.Orientations;
import buildcraft.api.liquids.LiquidStack;
import buildcraft.energy.Engine;
import buildcraft.energy.EngineIron;
import buildcraft.energy.EngineWood;
import buildcraft.energy.TileEngine;

public class TileUsefullEngine extends TileEngine {

	public Engine newEngine(int meta) {
		if (meta == 0) {
			return new EngineCooledCombustion(this);
		} else if (meta == 1) {
			return new EngineEfficient(this);
		} else {
			return null;
		}
	}
	
	@Override
	public boolean isPipeConnected(Orientations with) {
		return with.ordinal() != orientation;
	}
	/* ILIQUIDCONTAINER */
	@Override
	public int fill(Orientations from, LiquidStack resource, boolean doFill) {
		if (engine instanceof EngineCooledCombustion) {
			return ((EngineCooledCombustion) engine).fill(from, resource, doFill);
		} else if (engine instanceof EngineEfficient) {
			return ((EngineEfficient) engine).fill(from, resource, doFill);
		} else {
			return 0;
		}

	}
}