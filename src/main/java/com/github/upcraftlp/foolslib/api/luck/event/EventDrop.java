package com.github.upcraftlp.foolslib.api.luck.event;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

public class EventDrop extends LuckyEvent {

    protected final List<ItemStack> stacks;
    protected final double maxRadius;
    protected double chance;

    public EventDrop(double maxSpreadRadius, List<ItemStack> stacks, double chance) {
        this.maxRadius = maxSpreadRadius;
        this.stacks = stacks;
    }

    public EventDrop(double maxSpreadRadius, List<ItemStack> stacks) {
        this(maxSpreadRadius, stacks, 1.0D);
    }

    public EventDrop(double maxSpreadRadius, ItemStack... stacks) {
        this(maxSpreadRadius, Arrays.asList(stacks));
    }

    public EventDrop(ItemStack... stacks) {
        this(2.0D, stacks);
    }

    @Override
    public void activate(ResourceLocation block, World world, BlockPos pos, EntityPlayerMP player, int luck) {
        stacks.forEach(stack -> {
            //see http://mathworld.wolfram.com/DiskPointPicking.html
            double angle = Math.toRadians(360 * RANDOM.nextDouble());
            double sqrt_r = Math.sqrt(RANDOM.nextDouble() * maxRadius);
            double xOffset = sqrt_r * Math.cos(angle);
            double zOffset = sqrt_r * Math.sin(angle);
            EntityItem item = new EntityItem(world, pos.getX() + xOffset, pos.getY() + RANDOM.nextDouble(), pos.getZ() + zOffset, stack.copy());
            world.spawnEntityInWorld(item);
        });
    }
}
