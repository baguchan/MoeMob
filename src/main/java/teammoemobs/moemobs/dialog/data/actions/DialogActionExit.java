package teammoemobs.moemobs.dialog.data.actions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import teammoemobs.moemobs.api.TalkableController;
import teammoemobs.moemobs.api.dialog.IDialogAction;

import java.lang.reflect.Type;

public class DialogActionExit implements IDialogAction {
	private DialogActionExit()
	{
	}

	@Override
	public void performAction(TalkableController controller)
	{
	}

	public static class Deserializer implements JsonDeserializer<DialogActionExit>
	{
		@Override
		public DialogActionExit deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			return new DialogActionExit();
		}
	}
}