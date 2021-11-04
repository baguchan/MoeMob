package teammoemobs.moemobs.register;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import teammoemobs.moemobs.MoeMobs;
import teammoemobs.moemobs.world.entity.At2;
import teammoemobs.moemobs.utils.MoeMobUtils;

@Mod.EventBusSubscriber(modid = MoeMobs.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities {

	public static final EntityType<At2> AT2 = EntityType.Builder.of(At2::new, MobCategory.CREATURE).sized(0.6F, 0.85F).build(MoeMobUtils.prefixStringId("at2"));

	@SubscribeEvent
	public static void registerEntity(RegistryEvent.Register<EntityType<?>> event) {
		event.getRegistry().register(AT2.setRegistryName("at2"));
	}

	@SubscribeEvent
	public static void registerEntityAttribute(EntityAttributeCreationEvent event) {
		event.put(AT2, At2.createAttributes().build());
	}
}