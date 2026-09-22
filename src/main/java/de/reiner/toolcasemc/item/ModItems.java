package de.reiner.toolcasemc.item;

import de.reiner.toolcasemc.UltimatedToolCaseMC;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;

import java.util.function.Function;

public class ModItems {
    public static ResourceKey<Item> createID(String name){
        return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(UltimatedToolCaseMC.MOD_ID, name));
    }

    public static Item register(String name, Function<Item.Properties, Item> itemFunction, Item.Properties properties){
        Item item=itemFunction.apply(properties.setId(createID(name)));
        Registry.register(BuiltInRegistries.ITEM, createID(name), item);
        return item;
    }

    public static void init(){}

    public static final Item STONE_HAMMER=register("stone_hammer", properties ->
            new HammerItem(properties, BlockTags.MINEABLE_WITH_PICKAXE, 0xB8C7D9),
            new Item.Properties()
                    .pickaxe(ToolMaterial.IRON, 1.0F, -1.0F)
    );
    public static final Item DIRT_HAMMER=register("dirt_hammer", properties ->
            new HammerItem(properties, BlockTags.MINEABLE_WITH_SHOVEL, 0x55FF55),
            new Item.Properties()
                    .shovel(ToolMaterial.IRON, 1.0F, -1.0F)
    );
    public static final Item WOOD_HAMMER=register("wood_hammer", properties ->
            new HammerItem(properties, BlockTags.MINEABLE_WITH_AXE, 0xFFAA55),
            new Item.Properties()
                    .axe(ToolMaterial.IRON, 1.0F, -1.0F)
    );

    public static final Item CHISEL=register("chisel", ChiselItem::new, new Item.Properties().stacksTo(1).durability(250));
    public static final Item SICKLE=register("sickle", SickleItem::new, new Item.Properties().sword(ToolMaterial.IRON, 3.0F, -2.4F));

}
