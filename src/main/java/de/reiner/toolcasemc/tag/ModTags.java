package de.reiner.toolcasemc.tag;

import de.reiner.toolcasemc.UltimatedToolCaseMC;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {

    private static TagKey<Block> createBlockTag(String name) {
        return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(UltimatedToolCaseMC.MOD_ID, name));
    }

    private static TagKey<Item> createItemTag(String name) {
        return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(UltimatedToolCaseMC.MOD_ID, name));
    }

    public static final TagKey<Block> MINEABLE_WITH_POINTED_CHISEL = createBlockTag("mineable_with_pointed_chisel");

    public static final TagKey<Item> TOOLS=createItemTag("tools");

    public static void init() {}
}