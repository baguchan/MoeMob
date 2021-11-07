package teammoemobs.moemobs.api.entity;

import net.minecraft.resources.ResourceLocation;
import teammoemobs.moemobs.api.dialog.IDialogChangeListener;
import teammoemobs.moemobs.api.dialog.IDialogScene;
import teammoemobs.moemobs.api.dialog.scene.ISceneInstance;

import java.util.Map;
import java.util.UUID;

public interface NormalTalker {
	IDialogScene getCurrentScene();

	ISceneInstance getCurrentSceneInstance();


	void addListener(IDialogChangeListener listener);

	ResourceLocation getTalker();

	void setConditionsMetData(Map<UUID, Boolean> conditionsMet);
}
