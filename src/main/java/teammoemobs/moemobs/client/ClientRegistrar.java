package teammoemobs.moemobs.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import teammoemobs.moemobs.MoeMobs;
import teammoemobs.moemobs.client.render.EmptyRenderer;
import teammoemobs.moemobs.register.ContentRegistry;
import teammoemobs.moemobs.register.ModEntities;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = MoeMobs.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegistrar {
	@SubscribeEvent
	public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(ModEntities.AT2, EmptyRenderer::new);
	}

	@SubscribeEvent
	public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
	}

	public static void setup(FMLCommonSetupEvent event) {
		ContentRegistry.getDialogManager().attachReloadListener();
	}
}