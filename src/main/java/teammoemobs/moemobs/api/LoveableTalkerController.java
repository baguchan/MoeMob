package teammoemobs.moemobs.api;

import com.google.common.collect.Maps;
import net.minecraft.world.entity.player.Player;
import teammoemobs.moemobs.api.entity.LoveableTalker;
import teammoemobs.moemobs.api.entity.TalkableMob;

import java.util.Map;
import java.util.UUID;

public class LoveableTalkerController extends TalkableController implements LoveableTalker {
	private final Map<UUID, Integer> favorabilityMap = Maps.newHashMap();

	public LoveableTalkerController(TalkableMob talkableMob) {
		super(talkableMob);
	}

	/*
	 * This map is used to check for love events
	 */
	@Override
	public Map<UUID, Integer> getFavorabilityMap() {
		return favorabilityMap;
	}

	@Override
	public void addFavorabilityMap(Player player, int valve) {
		if (this.getFavorabilityMap().containsKey(player.getUUID())) {
			this.getFavorabilityMap().replace(player.getUUID(), this.getFavorabilityMap().get(player) + valve);
		}
	}

	@Override
	public void resetFavorability(Player player) {
		this.getFavorabilityMap().remove(player);
	}

	@Override
	public void setFavorability(Player player, int valve, boolean force) {
		if (this.getFavorabilityMap().containsKey(player.getUUID())) {
			this.getFavorabilityMap().replace(player.getUUID(), valve);
		} else if (force) {
			this.getFavorabilityMap().put(player.getUUID(), valve);
		}
	}
}
