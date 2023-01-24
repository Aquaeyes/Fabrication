package com.unascribed.fabrication.mixin.b_utility.show_bee_count_on_item;

import com.unascribed.fabrication.FabConf;
import com.unascribed.fabrication.support.injection.FabInject;
import com.unascribed.fabrication.util.forgery_nonsense.ForgeryMatrix;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.unascribed.fabrication.support.EligibleIf;
import com.unascribed.fabrication.support.Env;

import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

@Mixin(ItemRenderer.class)
@EligibleIf(configAvailable="*.show_bee_count_on_item", envMatches=Env.CLIENT)
public class MixinItemRenderer {

	@Shadow
	private float zOffset;

	@FabInject(at=@At("TAIL"), method="renderGuiItemOverlay(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V")
	public void renderGuiItemOverlay(TextRenderer renderer, ItemStack stack, int x, int y, String countLabel, CallbackInfo ci) {
		if (!(FabConf.isEnabled("*.show_bee_count_on_item") && stack.hasTag())) return;
		NbtCompound tag = stack.getTag().getCompound("BlockEntityTag");
		if (tag == null || !tag.contains("Bees", NbtType.LIST)) return;

		MatrixStack matrixStack = ForgeryMatrix.getStack();
		matrixStack.translate(0, 0, zOffset + 200);
		VertexConsumerProvider.Immediate vc = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());
		String count = String.valueOf(((NbtList)tag.get("Bees")).size());
		renderer.draw(count, x + 19 - 2 - renderer.getWidth(count), (y), 16777045, true, matrixStack.peek().getModel(), vc, false, 0, 15728880);
		vc.draw();
	}
}
