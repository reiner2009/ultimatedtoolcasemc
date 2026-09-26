package de.reiner.toolcasemc.tag;

import de.reiner.toolcasemc.UltimatedToolCaseMC;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModBlockTags {

    private static TagKey<Block> create(String name) {
        return TagKey.create(
                Registries.BLOCK,
                Identifier.fromNamespaceAndPath(
                        UltimatedToolCaseMC.MOD_ID,
                        name
                )
        );
    }

    public static final TagKey<Block> MINEABLE_WITH_POINTED_CHISEL =
            create("mineable_with_pointed_chisel");

    public static void init() {}
}