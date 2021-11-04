package teammoemobs.moemobs.api.entity;

import net.minecraft.world.entity.player.Player;

import java.util.Map;

public interface LoveableTalker {
	/**
	 * Called when needs Players Favorability.
	 */
	Map<Player, Integer> getFavorabilityMap();

	void addFavorabilityMap(Player player, int valve);

	void resetFavorability(Player player);

	void setFavorability(Player player, int valve, boolean force);
}
