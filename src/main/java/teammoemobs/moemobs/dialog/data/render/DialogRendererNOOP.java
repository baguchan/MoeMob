package teammoemobs.moemobs.dialog.data.render;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.blaze3d.vertex.PoseStack;
import teammoemobs.moemobs.api.dialog.IDialog;
import teammoemobs.moemobs.api.dialog.IDialogRenderer;

import java.lang.reflect.Type;

public class DialogRendererNOOP implements IDialogRenderer
{

	@Override
	public void setup(IDialog slide)
	{
	}

	@Override
	public void draw(IDialog slide, PoseStack poseStack, double screenWidth, double screenHeight, int mouseX, int mouseY, float partialTicks)
	{
	}

	public static class Deserializer implements JsonDeserializer<DialogRendererNOOP>
	{
		@Override
		public DialogRendererNOOP deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			return new DialogRendererNOOP();
		}
	}
}