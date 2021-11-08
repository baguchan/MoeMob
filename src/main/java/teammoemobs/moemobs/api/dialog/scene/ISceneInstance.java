package teammoemobs.moemobs.api.dialog.scene;

import teammoemobs.moemobs.api.dialog.IDialogLine;
import teammoemobs.moemobs.api.dialog.IDialogNode;
import teammoemobs.moemobs.api.dialog.IDialogScene;

public interface ISceneInstance {
	IDialogScene getScene();

	IDialogNode getNode();

	IDialogLine getLine();

	void navigate(String nodeId);

	void forwards();

	boolean isDoneReading();
}