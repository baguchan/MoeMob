package teammoemobs.moemobs.api.dialog;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Optional;

public interface IDialogTalker {
	/**
	 * @return The name of the speaker.
	 */
	@Nonnull
	String getUnlocalizedName();

	Optional<Map<String, IDialog>> getDialogs();
}
