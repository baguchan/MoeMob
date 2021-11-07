package teammoemobs.moemobs.api.dialog.scene;

import teammoemobs.moemobs.api.dialog.IDialogLine;
import teammoemobs.moemobs.api.dialog.IDialogNode;
import teammoemobs.moemobs.api.dialog.IDialogScene;

import java.util.Map;
import java.util.UUID;

public interface ISceneInstance {
	IDialogScene getScene();

	IDialogNode getNode();

	IDialogLine getLine();

	void navigate(String nodeId);

	void forwards();

	Map<UUID, Boolean> getConditionsMet();

	void setConditionsMet(Map<UUID, Boolean> conditionsMet);

	boolean isDoneReading();
}