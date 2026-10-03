package de.reiner.toolcasemc.block;

import de.reiner.toolcasemc.UltimatedToolCaseMC;
import de.reiner.toolcasemc.item.ModItems;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;

public class ModBlocks {
    public static ResourceKey<Block> createID(String name){
        return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(UltimatedToolCaseMC.MOD_ID, name));
    }

    public static Block register(String name, Function<BlockBehaviour.Properties, Block> blockFunction, BlockBehaviour.Properties properties){
        Block block=blockFunction.apply(properties.setId(createID(name)));
        Registry.register(BuiltInRegistries.BLOCK, createID(name), block);
        return block;
    }

    public static void init(){}

    public static final Block TOOL_CASE=register("tool_case", ToolCaseBlock::new, BlockBehaviour.Properties.of().noOcclusion());
    public static final Item TOOL_CASE_ITEM=ModItems.register("tool_case", properties1 -> new BlockItem(TOOL_CASE, properties1), new Item.Properties().stacksTo(1));
}
