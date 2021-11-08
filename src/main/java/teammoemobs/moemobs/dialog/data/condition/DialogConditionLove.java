package teammoemobs.moemobs.dialog.data.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import teammoemobs.moemobs.api.LoveableTalkerController;
import teammoemobs.moemobs.api.TalkableController;
import teammoemobs.moemobs.api.dialog.IDialogCondition;

import java.lang.reflect.Type;

public class DialogConditionLove implements IDialogCondition {

	private final int loveLevel;

	public DialogConditionLove(int loveLevel) {
		this.loveLevel = loveLevel;
	}

	@Override
	public boolean isMet(TalkableController controller) {
		return controller instanceof LoveableTalkerController && controller.getTalkableMob().getTalkingUUID().isPresent() && ((LoveableTalkerController) controller).getFavorabilityMap().get(controller.getTalkableMob().getTalkingUUID().get()) >= loveLevel;
	}

	public static class Deserializer implements JsonDeserializer<DialogConditionLove> {
		@Override
		public DialogConditionLove deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			return new DialogConditionLove(json.getAsJsonObject().get("love_level").getAsInt());
		}
	}
}
