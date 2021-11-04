package teammoemobs.moemobs.utils;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import teammoemobs.moemobs.api.TalkableController;
import teammoemobs.moemobs.api.dialog.IDialog;
import teammoemobs.moemobs.api.dialog.IDialogRenderer;
import teammoemobs.moemobs.api.dialog.IDialogTalker;
import teammoemobs.moemobs.register.ContentRegistry;

import java.util.Map;
/*
 * Special Thanks from Aether's Dialog code
 * https://github.com/Gilded-Games/The-Aether-II/blob/afba695b4edfbe06d23b4dff62c45f114c0aa6d8/src/main/java/com/gildedgames/aether/common/dialog/DialogUtil.java
 */
public class DialogUtils {
	public static final Component GUI_DONE = new TranslatableComponent("gui.done");
	public static final Component GUI_CANCEL = new TranslatableComponent("gui.cancel");
	public static final Component GUI_YES = new TranslatableComponent("gui.yes");
	public static final Component GUI_NO = new TranslatableComponent("gui.no");
	public static final Component GUI_OK = new TranslatableComponent("gui.ok");

	public static IDialogTalker getCurrentTalker(TalkableController controller)
	{
		if (controller.getTalker() == null)
		{
			return null;
		}

		final ResourceLocation talkerPath = controller.getTalker();

		return ContentRegistry.getDialogManager().getTalker(talkerPath).orElseThrow(() ->
				new IllegalArgumentException("Couldn't getByte talker: " + talkerPath));
	}

	public static IDialog getDialog(TalkableController controller)
	{
		if (controller.getTalker() == null)
		{
			return null;
		}

		final ResourceLocation talkerPath = controller.getTalker();

		IDialogTalker talker = ContentRegistry.getDialogManager().getTalker(talkerPath).orElseThrow(() ->
				new IllegalArgumentException("Couldn't getByte talker: " + talkerPath));

		final String address;
		IDialog dialog = null;

		// Check if the talker resourcelocation has a dialog address
		if (talkerPath.getPath().contains("#"))
		{
			// Obtain the dialog address from the Talker resourcelocation
			address = talkerPath.getPath().substring(talkerPath.getPath().indexOf("#") + 1);

			dialog = ContentRegistry.getDialogManager().findDialog(address, talker).orElseThrow(() ->
					new IllegalArgumentException("Couldn't find dialog: " + address));
		}
		else if (talker.getDialogs().isPresent())
		{
			final Map<String, IDialog> dialogs = talker.getDialogs().get();

			if (!dialogs.isEmpty() && dialogs.containsKey("default"))
			{
				dialog = dialogs.get("default");
			}
		}

		return dialog;
	}

	public static IDialogRenderer getRenderer(IDialog dialog)
	{
		IDialogRenderer renderer = null;

		if (dialog != null && dialog.getRenderer().isPresent())
		{
			final String renderType = dialog.getRenderer().get();

			renderer = ContentRegistry.getDialogManager().findRenderer(renderType).orElseThrow(() ->
					new IllegalArgumentException("Couldn't find dialog renderer: " + renderType));

			renderer.setup(dialog);
		}

		return renderer;
	}
}
