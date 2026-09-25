package de.reiner.toolcasemc.item;

import de.reiner.toolcasemc.UltimatedToolCaseMC;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModItemGroups {
    public static final ResourceKey<CreativeModeTab> TOOL_ITEM_GROUP_KEY = ResourceKey.create(
            BuiltInRegistries.CREATIVE_MODE_TAB.key(), UltimatedToolCaseMC.id("tools")
    );
    public static final CreativeModeTab TOOL_ITEM_GROUP = FabricCreativeModeTab.builder()
            .icon(() -> new ItemStack(ModItems.STONE_HAMMER))
            .title(Component.translatable("creativeTab."+UltimatedToolCaseMC.MOD_ID))
            .displayItems((params, output) -> {
                output.accept(ModItems.STONE_HAMMER);
                output.accept(ModItems.DIRT_HAMMER);
                output.accept(ModItems.WOOD_HAMMER);
                output.accept(ModItems.SICKLE);
                output.accept(ModItems.CHISEL);
                output.accept(ModItems.MULTI_TOOL);
                output.accept(ModItems.WARDEN_STAR);
            })
            .build();
    public static void init(){
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, TOOL_ITEM_GROUP_KEY, TOOL_ITEM_GROUP);
    }
}
