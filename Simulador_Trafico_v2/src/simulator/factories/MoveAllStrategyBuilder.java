package simulator.factories;

import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.MoveAllStrategy;

public class MoveAllStrategyBuilder extends Builder<DequeuingStrategy> {

	public MoveAllStrategyBuilder() {
		super("move_all_dqs", "Move All");
	}

	@Override
	protected DequeuingStrategy create_instance(JSONObject data) {
		return new MoveAllStrategy();
	}

}
