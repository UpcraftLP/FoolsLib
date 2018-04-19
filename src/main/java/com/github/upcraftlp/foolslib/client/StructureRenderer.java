package com.github.upcraftlp.foolslib.client;

import com.github.upcraftlp.foolslib.FoolsLib;
import com.github.upcraftlp.foolslib.api.item.ItemInstantStructure;
import com.github.upcraftlp.foolslib.api.util.EventBusSubscriber;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

import java.util.List;

@EventBusSubscriber(side = Side.CLIENT, value = FoolsLib.MODID, forge = true)
public class StructureRenderer {

    @SuppressWarnings("unchecked")
    @SubscribeEvent
    public void onRenderLast(RenderWorldLastEvent event) {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if(player == null) return;
        ItemStack stack = player.getHeldItem();
        if(stack != null && stack.getItem() instanceof ItemInstantStructure) {
            Vec3 eyePos = player.getPositionEyes(event.partialTicks);
            Vec3 look = player.getLook(event.partialTicks);
            Vec3 target = eyePos.addVector(look.xCoord * ItemInstantStructure.REACH_DISTANCE, look.yCoord * ItemInstantStructure.REACH_DISTANCE, look.zCoord * ItemInstantStructure.REACH_DISTANCE);
            MovingObjectPosition result = player.worldObj.rayTraceBlocks(eyePos, target, true, true, true);
            BlockPos pos;
            if(result != null && result.getBlockPos() != null) pos = result.getBlockPos().offset(result.sideHit);
            else pos = new BlockPos(target);

            ItemInstantStructure itemStructure = (ItemInstantStructure) stack.getItem();
            List<BlockPos> posList = itemStructure.getBlocks(player, player.worldObj, pos, true);
            BlockRendererDispatcher rendererDispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
            IBakedModel model = rendererDispatcher.getBlockModelShapes().getModelForState(itemStructure.getBlockState(player)); //TODO rotation
            float brightness = 0.55F;

            GlStateManager.enableBlend();
            GlStateManager.enableAlpha();
            GlStateManager.enableCull();
            GlStateManager.blendFunc(GL11.GL_SRC_COLOR, GL11.GL_CONSTANT_COLOR);
            GL14.glBlendColor(0.2F, 0.9F, 0.9F, 0.7F);
            GlStateManager.pushMatrix();
            {
                double dX = player.prevPosX + (player.posX - player.prevPosX) * event.partialTicks;
                double dY = player.prevPosY + (player.posY - player.prevPosY) * event.partialTicks;
                double dZ = player.prevPosZ + (player.posZ - player.prevPosZ) * event.partialTicks;
                GlStateManager.translate(-dX, -dY, -dZ); //get back to (0, 0, 0)

                //first pass
                GlStateManager.cullFace(GL11.GL_BACK);
                GlStateManager.colorMask(false, false, false, false);
                for(BlockPos pos1 : posList) {
                    if(!player.worldObj.getBlockState(pos1).getBlock().isReplaceable(player.worldObj, pos1)) continue;
                    GlStateManager.pushMatrix();
                    {
                        GlStateManager.translate(pos1.getX(), pos1.getY(), pos1.getZ());
                        rendererDispatcher.getBlockModelRenderer().renderModelBrightnessColor(model, brightness, 1.0F, 1.0F, 1.0F);
                    }
                    GlStateManager.popMatrix();
                }

                //second pass
                GlStateManager.cullFace(GL11.GL_BACK);
                GlStateManager.colorMask(true, true, true, true);
                for(BlockPos pos1 : posList) {
                    if(!player.worldObj.getBlockState(pos1).getBlock().isReplaceable(player.worldObj, pos1)) continue;
                    GlStateManager.pushMatrix();
                    {
                        GlStateManager.translate(pos1.getX(), pos1.getY(), pos1.getZ());
                        rendererDispatcher.getBlockModelRenderer().renderModelBrightnessColor(model, brightness, 1.0F, 1.0F, 1.0F);
                    }
                    GlStateManager.popMatrix();
                }
            }
            GlStateManager.popMatrix();
            GlStateManager.disableCull();
            GlStateManager.disableAlpha();
            GlStateManager.disableBlend();
        }
    }
}
