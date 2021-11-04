package teammoemobs.moemobs.dialog;

import com.google.gson.annotations.SerializedName;
import teammoemobs.moemobs.api.dialog.IDialog;
import teammoemobs.moemobs.api.dialog.IDialogTalker;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Optional;

public class DialogTalker implements IDialogTalker {
	@SerializedName("name")
	private String name;

	@SerializedName("dialogs")
	private Map<String, IDialog> dialogs;

	@Nonnull
	@Override
	public String getUnlocalizedName()
	{
		return this.name != null ? this.name : "";
	}

	@Override
	public Optional<Map<String, IDialog>> getDialogs()
	{
		return this.dialogs != null ? Optional.of(this.dialogs) : Optional.empty();
	}
}
