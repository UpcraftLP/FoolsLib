package com.github.upcraftlp.foolslib.api.luck.event;

import com.github.upcraftlp.foolslib.FoolsLib;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EventSpawn extends LuckyEvent {

    //FIXME living entities do not spawn

    /**
     * maximum spawn attempts per entity that is trying to spawn
     */
    private static final int MAX_SPAWN_ATTEMPTS = 12;
    private final int minCount;
    private final int maxCount;
    private final double maxRadius;
    private final Class<? extends Entity>[] types;

    public EventSpawn(int minCount, int maxCount, Class<? extends Entity>... entityTypes) {
        this(minCount, maxCount, 5.0D, entityTypes);
    }

    public EventSpawn(int minCount, int maxCount, double maxRadius, Class<? extends Entity>... entityTypes) {
        this.minCount = minCount;
        this.maxCount = maxCount;
        this.maxRadius = maxRadius;
        types = entityTypes;
    }

    @Override
    public void activate(ResourceLocation block, World world, BlockPos pos, EntityPlayerMP player, int luck) {
        if(types.length == 0) return;

        for(int i = 0; i < minCount + RANDOM.nextInt(maxCount - minCount + 1); i++) {
            Class<? extends Entity> clazz = this.types[RANDOM.nextInt(types.length)];
            try {
                spawnLoop:
                for(int count = RANDOM.nextInt(4); count > 0; count--) {
                    Entity entity = clazz.getConstructor(new Class[] {World.class}).newInstance(world);
                    for(int j = 0; j < MAX_SPAWN_ATTEMPTS; j++) {
                        //see http://mathworld.wolfram.com/DiskPointPicking.html
                        double angle = Math.toRadians(360 * RANDOM.nextDouble());
                        double sqrt_r = Math.sqrt(RANDOM.nextDouble() * maxRadius);
                        double xOffset = sqrt_r * Math.cos(angle);
                        double zOffset = sqrt_r * Math.sin(angle);
                        double x = pos.getX() + 0.5D + xOffset;
                        double y = pos.getY() + 0.02D;
                        double z = pos.getZ() + 0.5D + zOffset;
                        BlockPos spawnPos = new BlockPos(x, y, z);
                        if(world.isBlockLoaded(spawnPos) && world.isAirBlock(spawnPos) && world.isAirBlock(spawnPos.up())) {
                            entity.setLocationAndAngles(x, y, z, RANDOM.nextFloat(), 0.0F);
                            if(entity instanceof EntityLiving) {
                                EntityLiving entityLiving = (EntityLiving) entity;
                                entityLiving.onInitialSpawn(world.getDifficultyForLocation(spawnPos), null);
                                entityLiving.setAttackTarget(player);
                            }
                            world.spawnEntityInWorld(entity);
                            continue spawnLoop;
                        }
                        entity.setDead();
                    }
                }
            } catch(Exception e) {
                FoolsLib.getLogger().error("unable to spawn entity from class " + clazz.getCanonicalName(), e);
            }
        }
    }
}
