package com.github.upcraftlp.foolslib.api.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RegistryCreate {

    /**
     * The mod id to register the item with
     */
    String modid();

    Class value();
}
