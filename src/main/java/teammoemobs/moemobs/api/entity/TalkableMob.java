package teammoemobs.moemobs.api.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import teammoemobs.moemobs.api.TalkableController;
import teammoemobs.moemobs.client.screen.MoeMobDialogScreen;
import teammoemobs.moemobs.world.entity.At2;

import javax.annotation.Nullable;

public interface TalkableMob {

	ResourceLocation getTalker();

	TalkableController getTalkableController();

	void setTalkingEntity(At2 at2);
}
