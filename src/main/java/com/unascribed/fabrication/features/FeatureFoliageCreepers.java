package com.unascribed.fabrication.features;

import com.unascribed.fabrication.support.MixinConfigPlugin;
import com.unascribed.fabrication.support.feature.ResourcePackFeature;
import com.unascribed.fabrication.util.GrayscaleResourcePack;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.resource.ResourcePackSource;
import net.minecraft.resource.metadata.PackResourceMetadata;
import net.minecraft.text.LiteralText;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class FeatureFoliageCreepers extends ResourcePackFeature {
	public FeatureFoliageCreepers() {
		super("foliage_creepers");
	}
	@Override
	public void register(Consumer<ResourcePackProfile> consumer, ResourcePackProfile.Factory factory) {
		if (active) {
			Supplier<ResourcePack> f = GrayscaleResourcePack::new;
			consumer.accept(factory.create(MixinConfigPlugin.MOD_NAME+" grayscale", true, f, f.get(),
					new PackResourceMetadata(new LiteralText("Internal "+MixinConfigPlugin.MOD_NAME+" resources"), 6),
					ResourcePackProfile.InsertionPosition.TOP, ResourcePackSource.PACK_SOURCE_BUILTIN));
		}
	}
}