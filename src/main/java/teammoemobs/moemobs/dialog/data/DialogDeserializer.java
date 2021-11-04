package teammoemobs.moemobs.dialog.data;

import com.google.gson.*;
import teammoemobs.moemobs.dialog.Dialog;
import teammoemobs.moemobs.api.dialog.IDialog;

import java.lang.reflect.Type;

public class DialogDeserializer implements JsonDeserializer<IDialog>
{
	@Override
	public IDialog deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
		return context.deserialize(json, Dialog.class);
	}
}