package teammoemobs.moemobs.client.screen.button;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import teammoemobs.moemobs.api.dialog.IDialogButton;

public class DialogButton extends Button {
	private final IDialogButton buttonData;

	public DialogButton(int index, int x, int y, IDialogButton buttonData, Button.OnPress onPress) {
		super(index, x, y, 15, new TranslatableComponent(""), onPress);

		this.buttonData = buttonData;
	}

	public IDialogButton getButtonData()
	{
		return this.buttonData;
	}

	@Override
	public Component getMessage() {
		return this.buttonData.getLocalizedLabel();
	}
}
