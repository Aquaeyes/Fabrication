package com.unascribed.fabrication.mixin.c_tweaks.scares_creepers;

import com.unascribed.fabrication.FabConf;
import com.unascribed.fabrication.support.ConfigPredicates;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import com.unascribed.fabrication.support.injection.FabInject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.unascribed.fabrication.FabRefl;
import com.unascribed.fabrication.support.EligibleIf;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import java.util.function.Predicate;

@Mixin(CreeperEntity.class)
@EligibleIf(configAvailable="*.scares_creepers")
public abstract class MixinCreeperEntity extends HostileEntity {

	protected MixinCreeperEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}

	private static final Predicate<PlayerEntity> fabrication$scaresCreepersPredicate = ConfigPredicates.getFinalPredicate("*.scares_creepers");
	@FabInject(at=@At("TAIL"), method="initGoals()V")
	protected void initGoals(CallbackInfo ci) {
		FleeEntityGoal<ServerPlayerEntity> goal = new FleeEntityGoal<>(this, ServerPlayerEntity.class,
				spe -> FabConf.isEnabled("*.scares_creepers") && fabrication$scaresCreepersPredicate.test((PlayerEntity)spe), 8, 1, 2,
				EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR::test);
		TargetPredicate withinRangePredicate = FabRefl.getWithinRangePredicate(goal);
		FabRefl.setAttackable(withinRangePredicate, false);
		goalSelector.add(3, goal);
	}

}
