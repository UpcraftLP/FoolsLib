package com.github.upcraftlp.foolslib.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.Iterator;

@IFMLLoadingPlugin.MCVersion(Loader.MC_VERSION)
public class TransformPlayerRender implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if("net.minecraft.client.renderer.entity.RenderPlayer".equals(transformedName)) {
            ClassNode cn = new ClassNode();
            ClassReader cr = new ClassReader(basicClass);
            cr.accept(cn, 0);
            Iterator<MethodNode> methods = cn.methods.iterator();
            FoolsLibLoadingPlugin.log.info("transforming class {} ({})", name, transformedName);
            {
                String methodName = FoolsLibLoadingPlugin.isNormalEnvironment() ? "func_180594_a" : "getEntityTexture";
                while(methods.hasNext()) {
                    MethodNode method = methods.next();
                    if(methodName.equals(method.name) && "(Lnet/minecraft/client/entity/AbstractClientPlayer;)Lnet/minecraft/util/ResourceLocation;".equals(method.desc)) {
                        FoolsLibLoadingPlugin.log.info("transforming method {} ({})", methodName, "getEntityTexture");
                        for(int i = 0; i < method.instructions.size(); i++) {
                            AbstractInsnNode current = method.instructions.get(i);
                            if(current instanceof MethodInsnNode) {
                                method.instructions.insert(current, new MethodInsnNode(Opcodes.INVOKESTATIC, "com/github/upcraftlp/foolslib/client/SkinArmorRenderer", "getTexture", "(Lnet/minecraft/client/entity/AbstractClientPlayer;)Lnet/minecraft/util/ResourceLocation;", false));
                                method.instructions.remove(current);
                                i++;
                            }
                        }
                    }
                }
            }
            FoolsLibLoadingPlugin.log.info("successfully transformed {}", transformedName);

            // asm specific for cleaning up and returning the final bytes for JVM processing.
            ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            cn.accept(writer);
            return writer.toByteArray();
        }
        else return basicClass;
    }

}
