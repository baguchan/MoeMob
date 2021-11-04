package teammoemobs.moemobs.client.render;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class EmptyRenderer extends EntityRenderer<Entity> {
	public EmptyRenderer(EntityRendererProvider.Context p_174008_) {
		super(p_174008_);
		this.shadowRadius = 0.5F;
	}

	@Override
	public ResourceLocation getTextureLocation(Entity p_114482_) {
		return null;
	}
}
