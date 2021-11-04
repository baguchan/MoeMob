package teammoemobs.moemobs.world.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import teammoemobs.moemobs.MoeMobs;
import teammoemobs.moemobs.api.TalkableController;
import teammoemobs.moemobs.api.entity.TalkableMob;

public class At2 extends PathfinderMob implements TalkableMob {
	private final TalkableController talkableController;

	public At2(EntityType<? extends At2> p_21683_, Level p_21684_) {
		super(p_21683_, p_21684_);
		this.talkableController = new TalkableController(this);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes();
	}

	@Override
	public InteractionResult mobInteract(Player p_27584_, InteractionHand p_27585_) {
		ItemStack itemstack = p_27584_.getItemInHand(p_27585_);
		boolean flag = super.mobInteract(p_27584_, p_27585_) == InteractionResult.PASS;
		if (flag)
		{
			if (!p_27584_.level.isClientSide()) {
				this.setTalkingEntity(this);
				String node = "start";

				this.getTalkableController().openScene(p_27584_, this, new ResourceLocation(MoeMobs.MODID, "at2/new_comer"), node);
				this.gameEvent(GameEvent.MOB_INTERACT, this.eyeBlockPosition());
				return InteractionResult.SUCCESS;
			}
		}

		return InteractionResult.FAIL;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new FloatGoal(this));
		//this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
	}

	@Override
	public ResourceLocation getTalker() {
		return new ResourceLocation(MoeMobs.MODID, "at2");
	}

	@Override
	public TalkableController getTalkableController() {
		return talkableController;
	}

	@Override
	public void setTalkingEntity(At2 at2) {

	}
}
