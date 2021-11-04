package teammoemobs.moemobs.api.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import teammoemobs.moemobs.api.dialog.IDialogChangeListener;
import teammoemobs.moemobs.api.dialog.IDialogScene;
import teammoemobs.moemobs.api.dialog.scene.ISceneInstance;

import java.util.List;
import java.util.Map;

public interface NormalTalker {
	IDialogScene getCurrentScene();

	ISceneInstance getCurrentSceneInstance();


	void addListener(IDialogChangeListener listener);

	ResourceLocation getTalker();

	void setConditionsMetData(Map<String, Boolean> conditionsMet);
}
