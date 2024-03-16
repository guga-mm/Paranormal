package io.github.qMartinz.paranormal.registry;

import io.github.qMartinz.paranormal.Paranormal;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroupRegistry {
	public static ItemGroup PARANORMAL;

	public static void registerItemGroup() {
		PARANORMAL = FabricItemGroup.builder()
				.name(Text.translatable("itemgroup.paranormal.items"))
				.icon(() -> new ItemStack(ModItemRegistry.ORGAN)).build();
	}
}
