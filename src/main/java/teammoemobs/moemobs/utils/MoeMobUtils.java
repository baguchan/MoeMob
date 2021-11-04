package teammoemobs.moemobs.utils;

import net.minecraft.resources.ResourceLocation;
import teammoemobs.moemobs.MoeMobs;

public class MoeMobUtils {
	public static String prefixStringId(String id){
		return MoeMobs.MODID + ":" + id;
	}

	public static ResourceLocation prefixId(String id){
		return new ResourceLocation(MoeMobs.MODID, id);
	}
}
