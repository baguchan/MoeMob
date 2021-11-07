package teammoemobs.moemobs.api.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import teammoemobs.moemobs.api.TalkableController;

import java.util.Optional;
import java.util.UUID;

public interface TalkableMob {

	ResourceLocation getTalker();

	TalkableController getTalkableController();

	void setTalkingEntity(Player player);

	Optional<UUID> getTalkingUUID();
}
