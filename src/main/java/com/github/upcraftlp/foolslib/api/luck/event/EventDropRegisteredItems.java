package com.github.upcraftlp.foolslib.api.luck.event;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class EventDropRegisteredItems extends LuckyEvent {

    protected final List<ItemStack> stacks;
    protected final double maxRadius;
    protected final double chance;

    public EventDropRegisteredItems(double chance, String... domains) {
        this.chance = chance;
        List<String> allowedDomains = Arrays.asList(domains);
        //yes, this will indeed result in every single item in the registry dropped.
        Iterator registryKeys = Item.itemRegistry.getKeys().iterator();
        List<ItemStack> stacks = new ArrayList<>();
        while (registryKeys.hasNext()) {
            ResourceLocation name = (ResourceLocation) registryKeys.next();
            if(allowedDomains.contains(name.getResourceDomain())) {
                stacks.add(new ItemStack((Item) Item.itemRegistry.getObject(name)));
            }
        }
        this.stacks = stacks;
        this.maxRadius = Math.sqrt(stacks.size() / Math.PI) / 2;
    }

    @Override
    public void activate(ResourceLocation block, World world, BlockPos pos, EntityPlayerMP player, int luck) {
        stacks.forEach(stack -> {
            if(RANDOM.nextDouble() < chance) {
                //see http://mathworld.wolfram.com/DiskPointPicking.html
                double angle = Math.toRadians(360 * RANDOM.nextDouble());
                double sqrt_r = Math.sqrt(RANDOM.nextDouble() * maxRadius);
                double xOffset = sqrt_r * Math.cos(angle);
                double zOffset = sqrt_r * Math.sin(angle);

                EntityItem item = new EntityItem(world, pos.getX() + xOffset, pos.getY() + RANDOM.nextDouble(), pos.getZ() + zOffset, stack.copy());
                world.spawnEntityInWorld(item);
            }
        });
    }
}
