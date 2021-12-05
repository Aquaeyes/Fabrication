package com.unascribed.fabrication.mixin.c_tweaks.legible_signs;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.unascribed.fabrication.support.EligibleIf;
import com.unascribed.fabrication.support.Env;
import com.unascribed.fabrication.support.MixinConfigPlugin;

import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
import net.minecraft.util.DyeColor;

@Mixin(SignBlockEntityRenderer.class)
@EligibleIf(configAvailable="*.legible_signs", envMatches=Env.CLIENT)
public class MixinSignBlockEntityRenderer {

	@Inject(at=@At("HEAD"), method= "getColor(Lnet/minecraft/block/entity/SignBlockEntity;)I", cancellable = true)
	private static void modifySignTextColor(SignBlockEntity sign, CallbackInfoReturnable<Integer> cir) {
		if (MixinConfigPlugin.isEnabled("*.legible_signs") && !sign.isGlowingText()){
			DyeColor dc = sign.getTextColor();
			
			int res;
			switch (dc) {
				case BLACK:
					res = 0x000000;
					break;
				case GRAY:
					res = 0x333333;
					break;
				case BROWN:
					res = dc.getSignColor();
					break;
				default: {
					float[] bgr = dc.getColorComponents();
					res = Math.round(bgr[0]*255.0F) << 16 | Math.round(bgr[1]*255.0F) << 8 | Math.round(bgr[2]*255);
				}
			}
			cir.setReturnValue(res);
		}
	}
}
