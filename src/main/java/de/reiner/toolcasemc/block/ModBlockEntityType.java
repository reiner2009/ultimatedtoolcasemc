package de.reiner.toolcasemc.block;

import de.reiner.toolcasemc.UltimatedToolCaseMC;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntityType {
    private static <T extends BlockEntity> BlockEntityType<T> register(String name, FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory, Block... blocks) {
        Identifier id = UltimatedToolCaseMC.id(name);
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
    }

    public static void init(){}

    public static final BlockEntityType<ToolCaseBlockEntity> TOOLCASE_BLOCK_ENTITY = register("tool_case", ToolCaseBlockEntity::new, ModBlocks.TOOL_CASE);
}
