package teammoemobs.moemobs.client.screen;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.BaseComponent;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.TextComponent;
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
import java.util.LinkedList;
import java.util.Map;

public class MoeMobDialogScreen extends Screen implements IDialogChangeListener {
	public static boolean preventDialogControllerClose;

	private final TalkableController controller;

	private final LinkedList<Object> buttons = Lists.newLinkedList();

	private Button bottomTextBox, namePlate;

	private double nextArrowAnim, prevTime;

	private boolean canApplyNextAction = true;

	private IDialogNode node;

	private IDialogTalker speaker;

	private IDialog slide;

	private IDialogRenderer renderer;

	private int currentScroll, maxScroll;

	private ISceneInstance sceneInstance;

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
		super.render(poseStack, p_96563_, p_96564_, p_96565_);
		this.renderBackground(poseStack);
		this.renderWife(poseStack);
	}

	private void renderWife(PoseStack poseStack) {
		Tesselator tesselator = Tesselator.getInstance();
		BufferBuilder bufferbuilder = tesselator.getBuilder();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, BookViewScreen.BOOK_LOCATION);
		bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);

		tesselator.end();
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
			this.buttons.clear();

			int index = 0;

			for (final IDialogButton btn : beforeConditionButtons)
			{
				if (this.controller.conditionsMet(btn))
				{
					Button button = new DialogButton(index, (this.width / 2) - (baseBoxSize / 2), this.height - 85 + (index * 20), btn,(button2) -> {
						this.controller.activateButton(btn);
					});

					button.visible = false;

					this.renderables.add(button);
					this.buttons.add(button);

					index++;
				}
			}
		}

		this.currentScroll = 0;

		final IDialogLine line = this.controller.getCurrentLine();


		this.bottomTextBox = this.addRenderableWidget(new Button(
				this.buttons.size() + 1, resize ? (this.width / 2) - (baseBoxSize / 2) : 20, this.height - 85, baseBoxSize, line.getLocalizedBody(), (button) -> {

		}));


		this.maxScroll = Math.max(0, this.buttons.size() - 4);


		/*if (this.controller.isNodeFinished() && this.controller.getCurrentNode().getButtons().size() > 0)
		{
			this.topTextBox.setText(line.getLocalizedBody());
		}
		else
		{
			this.bottomTextBox.setText(line.getLocalizedBody());
		}*/

		if (this.controller.getCurrentLine().getSpeaker().isPresent())
		{
			final ResourceLocation speakerPath = this.controller.getCurrentLine().getSpeaker().get();

			this.speaker = ContentRegistry.getDialogManager().getTalker(speakerPath).orElseThrow(() ->
					new IllegalArgumentException("Couldn't getByte speaker: " + speakerPath));

			final String address;

			// Check if the speaker resourcelocation has a slide address
			if (speakerPath.getPath().contains("#"))
			{
				// Obtain the slide address from the Speaker resourcelocation
				address = speakerPath.getPath().substring(speakerPath.getPath().indexOf("#") + 1);

				this.slide = ContentRegistry.getDialogManager().findDialog(address, this.speaker).orElseThrow(() ->
						new IllegalArgumentException("Couldn't find slide: " + address));
			}
			else if (this.speaker.getDialogs().isPresent())
			{
				final Map<String, IDialog> slides = this.speaker.getDialogs().get();

				if (!slides.isEmpty() && slides.containsKey("default"))
				{
					this.slide = slides.get("default");
				}
			}

			if (this.slide != null && this.slide.getRenderer().isPresent())
			{
				final String renderType = this.slide.getRenderer().get();

				this.renderer = ContentRegistry.getDialogManager().findRenderer(renderType).orElseThrow(() ->
						new IllegalArgumentException("Couldn't find slide renderer: " + renderType));

				this.renderer.setup(this.slide);
			}

			final boolean topText = this.controller.isNodeFinished() && this.controller.getCurrentNode().getButtons().size() > 0;

			final String name = I18n.get(this.speaker.getUnlocalizedName());

			final BaseComponent textComponent = new TranslatableComponent(name);

			this.namePlate = this.addRenderableWidget(new Button(
					this.buttons.size() + 2,
					resize ? (this.width / 2) - (baseBoxSize / 2) : 20,
					this.height - (topText ? 122 + this.bottomTextBox.getHeight() : 107),
					20, textComponent, (button) -> {
			}));
			this.renderables.add(this.namePlate);
		}

		this.renderables.add(this.bottomTextBox);
	}

	private void resetGui()
	{
		this.renderables.clear();
	}

	private void nextAction()
	{
		if (!this.canApplyNextAction)
		{
			this.canApplyNextAction = true;

			return;
		}

		this.controller.advance();
	}
}
