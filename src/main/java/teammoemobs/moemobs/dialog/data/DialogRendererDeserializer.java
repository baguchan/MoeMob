package teammoemobs.moemobs.dialog.data;

import com.google.gson.*;
import teammoemobs.moemobs.dialog.data.render.DialogRendererStatic;
import teammoemobs.moemobs.api.dialog.IDialogRenderer;

import java.lang.reflect.Type;
import java.util.HashMap;

public class DialogRendererDeserializer implements JsonDeserializer<IDialogRenderer>
{
	private final HashMap<String, Class<? extends IDialogRenderer>> renderers = new HashMap<>();

	public DialogRendererDeserializer()
	{
		this.renderers.put("static", DialogRendererStatic.class);
	}

	@Override
	public IDialogRenderer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
		JsonObject root = json.getAsJsonObject();

		if (!root.has("type"))
		{
			throw new JsonParseException("Missing required field 'type' for action");
		}

		String type = root.get("type").getAsString();

		if (!this.renderers.containsKey(type))
		{
			throw new JsonParseException("Invalid action type " + type);
		}

		return context.deserialize(json, this.renderers.get(type));
	}
}