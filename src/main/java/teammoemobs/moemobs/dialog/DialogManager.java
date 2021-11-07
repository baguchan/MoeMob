package teammoemobs.moemobs.dialog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import teammoemobs.moemobs.MoeMobs;
import teammoemobs.moemobs.api.dialog.*;
import teammoemobs.moemobs.dialog.data.DialogActionDeserializer;
import teammoemobs.moemobs.dialog.data.DialogDeserializer;
import teammoemobs.moemobs.dialog.data.DialogRendererDeserializer;
import teammoemobs.moemobs.dialog.data.actions.DialogActionExit;
import teammoemobs.moemobs.dialog.data.render.DialogRendererNOOP;
import teammoemobs.moemobs.dialog.data.render.DialogRendererStatic;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Optional;

public class DialogManager implements IDialogManager {
	private final HashMap<ResourceLocation, IDialogScene> cachedScenes = new HashMap<>();
	private final HashMap<ResourceLocation, IDialogTalker> cachedTalkers = new HashMap<>();
	private final HashMap<String, IDialogRenderer> registeredRenders = new HashMap<>();


	private final Gson gson;
	private final boolean allowCaching;


	public DialogManager(final boolean allowCaching) {
		this.allowCaching = allowCaching;

		this.gson = this.buildDeserializer().create();
		this.registerRenders();
	}

	private GsonBuilder buildDeserializer() {
		final GsonBuilder builder = new GsonBuilder();

		builder.registerTypeAdapter(IDialogAction.class, new DialogActionDeserializer());
		//builder.registerTypeAdapter(IDialogCondition.class, new DialogConditionDeserializer());

		builder.registerTypeAdapter(DialogActionExit.class, new DialogActionExit.Deserializer());


		builder.registerTypeAdapter(IDialogRenderer.class, new DialogRendererDeserializer());
		builder.registerTypeAdapter(DialogRendererStatic.class, new DialogRendererStatic.Deserializer());
		builder.registerTypeAdapter(DialogRendererNOOP.class, new DialogRendererNOOP.Deserializer());
		builder.registerTypeAdapter(IDialog.class, new DialogDeserializer());
		return builder;
	}

	protected void registerRenders() {
		this.registeredRenders.put("static", new DialogRendererStatic());
		this.registeredRenders.put("noop", new DialogRendererNOOP());
	}

	@Override
	public Optional<IDialogTalker> getTalker(final ResourceLocation resource) {
		if (this.allowCaching && this.cachedTalkers.containsKey(resource)) {
			return Optional.of(this.cachedTalkers.get(resource));
		}

		final IDialogTalker talker;

		try {
			talker = this.loadTalker(resource);

			if (this.allowCaching) {
				this.cachedTalkers.put(resource, talker);
			}
		} catch (final IOException e) {
			MoeMobs.LOGGER.error("Failed to load dialog talker: {}", resource, e);

			return Optional.empty();
		}

		return Optional.of(talker);
	}

	@Override
	public Optional<IDialogScene> getScene(final ResourceLocation resource) {
		if (this.allowCaching && this.cachedScenes.containsKey(resource)) {
			return Optional.of(this.cachedScenes.get(resource));
		}

		final IDialogScene scene;

		try {
			scene = this.loadScene(resource);

			if (this.allowCaching) {
				this.cachedScenes.put(resource, scene);
			}
		} catch (final IOException e) {
			MoeMobs.LOGGER.error("Failed to load dialog scene: {}", resource, e);

			return Optional.empty();
		}

		return Optional.of(scene);
	}


	public IDialogTalker loadTalker(ResourceLocation resource) throws IOException {
		final String path = resource.getPath();
		String pathWithoutSlide = path;

		if (path.contains("#")) {
			pathWithoutSlide = path.replace(path.substring(path.indexOf("#")), "");
		}

		final String talkerPath = "/assets/" + resource.getNamespace() + "/mob_dialog/talkers/" + pathWithoutSlide + ".json";

		MoeMobs.LOGGER.info("Loading dialog talker from file {}", talkerPath);

		try (InputStream stream = MoeMobs.class.getResourceAsStream(talkerPath)) {
			if (stream != null) {
				try (InputStreamReader reader = new InputStreamReader(stream)) {
					return this.gson.fromJson(reader, DialogTalker.class);
				}
			} else {
				MoeMobs.LOGGER.error("dialog talker loaded : {}", path);
				return null;
			}
		}
	}

	private IDialogScene loadScene(final ResourceLocation resource) throws IOException {
		final String path = "/assets/" + resource.getNamespace() + "/mob_dialog/" + resource.getPath() + ".json";

		MoeMobs.LOGGER.info("Loading dialog scene from file {}", path);

		try (InputStream stream = MoeMobs.class.getResourceAsStream(path)) {
			if (stream != null) {
				try (InputStreamReader reader = new InputStreamReader(stream)) {
					return this.gson.fromJson(reader, DialogSchema.class);
				}
			} else {
				MoeMobs.LOGGER.error("dialog cannot loaded : {}", path);
				return null;
			}
		}
	}

	@Override
	public Optional<IDialog> findDialog(final String slideAddress, final IDialogTalker speaker) {
		if (speaker == null || slideAddress == null || !speaker.getDialogs().isPresent() || !speaker.getDialogs().get().containsKey(slideAddress)) {
			return Optional.empty();
		}

		return Optional.of(speaker.getDialogs().get().get(slideAddress));
	}

	@Override
	public Optional<IDialogRenderer> findRenderer(final String type) {
		if (type == null || !this.registeredRenders.containsKey(type)) {
			return Optional.empty();
		}

		return Optional.of(this.registeredRenders.get(type));
	}

	@OnlyIn(Dist.CLIENT)
	public void attachReloadListener() {
		final ResourceManager resManager = Minecraft.getInstance().getResourceManager();

		if (resManager instanceof ReloadableResourceManager) {
			((ReloadableResourceManager) resManager).registerReloadListener(new ReloadListener(this));
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static class ReloadListener implements ResourceManagerReloadListener {
		private final DialogManager manager;

		public ReloadListener(final DialogManager manager) {
			this.manager = manager;
		}

		@Override
		public void onResourceManagerReload(final ResourceManager resourceManager) {
			this.manager.cachedScenes.clear();
		}
	}
}
