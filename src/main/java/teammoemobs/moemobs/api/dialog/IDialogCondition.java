package teammoemobs.moemobs.api.dialog;

import teammoemobs.moemobs.api.TalkableController;

public interface IDialogCondition
{
	/**
	 * Called when the condition needs to be met.
	 */
	boolean isMet(TalkableController controller);
}