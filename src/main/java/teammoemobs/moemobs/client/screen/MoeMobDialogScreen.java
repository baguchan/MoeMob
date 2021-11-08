package teammoemobs.moemobs.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import teammoemobs.moemobs.api.TalkableController;
import teammoemobs.moemobs.api.dialog.*;
import teammoemobs.moemobs.api.dialog.scene.ISceneInstance;
import teammoemobs.moemobs.api.entity.TalkableMob;
import teammoemobs.moemobs.client.screen.button.DialogButton;
import teammoemobs.moemobs.register.ContentRegistry;

import java.util.Collection;
import java.util.Map;

public class MoeMobDialogScreen extends Screen implements IDialogChangeListener {
	public static boolean preventDialogControllerClose;

	private final TalkableController controller;

	private double nextArrowAnim, prevTime;

	private boolean canApplyNextAction = true;

	private IDialogNode node;

	private IDialogTalker talker;

	private IDialog dialog;

	private IDialogRenderer renderer;


	private ISceneInstance sceneInstance;

	private ResourceLocation talkerTexture;

	public MoeMobDialogScreen(Player player, TalkableMob living) {
		super(NarratorChatListener.NO_TITLE);

		this.controller = living.getTalkableController();
	}

	@Override
	protected void init() {
		super.init();
		this.onDialogChanged();
	}



	@Override
	public void render(PoseStack poseStack, int p_96563_, int p_96564_, float p_96565_) {
		this.renderBackground(poseStack);
		int baseBoxSize = 350;
		final boolean resize = this.width - 40 > baseBoxSize;

		if (!resize) {
			baseBoxSize = this.width - 40;
		}

		if (this.dialog != null && this.renderer != null) {
			this.renderer.draw(this.dialog, poseStack, this.width, this.height, p_96563_, p_96564_, p_96565_);
		}

		final String name = I18n.get(this.talker.getUnlocalizedName());

		final TranslatableComponent textComponent = new TranslatableComponent(name);

		this.font.draw(poseStack, textComponent, resize ? (this.width / 2) - (baseBoxSize / 2) : 20,
				this.height - (107), 0xFAEB95);

		final IDialogLine line = this.controller.getCurrentLine();


		final TranslatableComponent textComponent2 = new TranslatableComponent(((TranslatableComponent) line.getLocalizedBody()).getKey(), Minecraft.getInstance().getUser().getName());

		this.font.draw(poseStack, textComponent2, resize ? (this.width / 2) - (baseBoxSize / 2) : 20, this.height - 85, 0xFFFFFF);

		super.render(poseStack, p_96563_, p_96564_, p_96565_);
	}


	@Override
	public void onDialogChanged() {
		this.buildGui(this.controller.getSceneInstance().getNode());
	}

	@Override
	public void beforeSceneCloses() {

	}

	private void buildGui(final IDialogNode node)
	{
		if (this.node != node)
		{
			this.canApplyNextAction = false;
		}

		this.node = node;

		this.resetGui();

		final Collection<IDialogButton> beforeConditionButtons = node.getButtons();

		int baseBoxSize = 350;
		final boolean resize = this.width - 40 > baseBoxSize;

		if (!resize)
		{
			baseBoxSize = this.width - 40;
		}

		if (this.controller.isNodeFinished())
		{
			int index = 0;

			for (final IDialogButton btn : beforeConditionButtons)
			{
				if (this.controller.conditionsMet(btn)) {
					Button button = new DialogButton((this.width / 2) - (baseBoxSize / 2), this.height - 65 + (index * 20), 200, 20, btn, (button2) -> {
						this.controller.activateButton(btn);
					});

					this.addRenderableWidget(button);
					button.visible = true;

					index++;
				}
			}
		}

		/*if (this.controller.isNodeFinished() && this.controller.getCurrentNode().getButtons().size() > 0)
		{
			this.topTextBox.setText(line.getLocalizedBody());
		}
		else
		{
			this.bottomTextBox.setText(line.getLocalizedBody());
		}*/

		if (this.controller.getCurrentLine().getSpeaker().isPresent()) {
			final ResourceLocation talkerPath = this.controller.getCurrentLine().getSpeaker().get();

			this.talker = ContentRegistry.getDialogManager().getTalker(talkerPath).orElseThrow(() ->
					new IllegalArgumentException("Couldn't getByte talker: " + talkerPath));

			final String address;

			// Check if the talker resourcelocation has a dialog address
			if (talkerPath.getPath().contains("_")) {
				// Obtain the dialog address from the Speaker resourcelocation
				address = talkerPath.getPath().substring(talkerPath.getPath().indexOf("_") + 1);

				this.dialog = ContentRegistry.getDialogManager().findDialog(address, this.talker).orElseThrow(() ->
						new IllegalArgumentException("Couldn't find dialog: " + address));
			} else if (this.talker.getDialogs().isPresent()) {
				final Map<String, IDialog> dialogs = this.talker.getDialogs().get();

				if (!dialogs.isEmpty() && dialogs.containsKey("default")) {
					this.dialog = dialogs.get("default");
				}
			}

			if (this.dialog != null && this.dialog.getRenderer().isPresent()) {
				final String renderType = this.dialog.getRenderer().get();

				this.renderer = ContentRegistry.getDialogManager().findRenderer(renderType).orElseThrow(() ->
						new IllegalArgumentException("Couldn't find dialog renderer: " + renderType));

				this.renderer.setup(this.dialog);
			}

			final boolean topText = this.controller.isNodeFinished() && this.controller.getCurrentNode().getButtons().size() > 0;


		}
	}

	private void resetGui()
	{
		this.clearWidgets();
	}

	private void nextAction() {
		if (!this.canApplyNextAction) {
			this.canApplyNextAction = true;

			return;
		}

		this.controller.advance();
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}
}
