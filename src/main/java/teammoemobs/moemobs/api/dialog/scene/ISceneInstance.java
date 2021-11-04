package teammoemobs.moemobs.api.dialog.scene;

import teammoemobs.moemobs.api.dialog.IDialogLine;
import teammoemobs.moemobs.api.dialog.IDialogNode;
import teammoemobs.moemobs.api.dialog.IDialogScene;

import java.util.Map;

public interface ISceneInstance {
	IDialogScene getScene();

	IDialogNode getNode();

	IDialogLine getLine();

	void navigate(String nodeId);

	void forwards();

	Map<String, Boolean> getConditionsMet();

	void setConditionsMet(Map<String, Boolean> conditionsMet);

	boolean isDoneReading();
}