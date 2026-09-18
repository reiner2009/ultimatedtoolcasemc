package de.reiner.toolcasemc.item;

import de.reiner.toolcasemc.UltimatedToolCaseMC;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;

import java.util.function.Function;

public class ModItems {
    public static ResourceKey<Item> createID(String name){
        return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(UltimatedToolCaseMC.MOD_ID, name));
    }

    public static Item register(ResourceKey<Item> itemResourceKey, Function<Item.Properties, Item> itemFunction, Item.Properties properties){
        Item item=itemFunction.apply(properties.setId(itemResourceKey));
        Registry.register(BuiltInRegistries.ITEM, itemResourceKey, item);
        return item;
    }

    public static void init(){}

    public static final Item WOODEN_HAMMER=register(createID("hammer"), HammerItem::new, new Item.Properties()
            .shovel(ToolMaterial.WOOD, 1.5F, -3.0F)
            .pickaxe(ToolMaterial.WOOD, 1.0F, -2.8F)
            .axe(ToolMaterial.WOOD, 6.0F, -3.2F)
            .hoe(ToolMaterial.WOOD, 0.0F, -3.0F)
    );
}
