package teammoemobs.moemobs.dialog;

import com.google.gson.annotations.SerializedName;
import teammoemobs.moemobs.api.dialog.IDialog;

import java.util.Map;
import java.util.Optional;

public class Dialog implements IDialog
{
	@SerializedName("render")
	private String render;

	@SerializedName("data")
	private Map<String, String> dialogData;

	@Override
	public Optional<String> getRenderer()
	{
		return this.render != null ? Optional.of(this.render) : Optional.empty();
	}

	@Override
	public Optional<Map<String, String>> getDialogData()
	{
		return this.dialogData != null ? Optional.of(this.dialogData) : Optional.empty();
	}
}