package com.github.upcraftlp.foolslib.api.capabilities;

import net.minecraft.entity.Entity;
import net.minecraftforge.common.IExtendedEntityProperties;

public class CapabilityHelper {

    @SuppressWarnings("unchecked")
    public static <T extends IExtendedEntityProperties> T getCapability(Entity entity, String capabilityName) {
        return (T) entity.getExtendedProperties(capabilityName);
    }

}
