package gg.steve.mc.frodo.eggs.framework.nbt.utils.annotations;

import gg.steve.mc.frodo.eggs.framework.nbt.utils.MinecraftVersion;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({METHOD})
public @interface AvaliableSince {

    MinecraftVersion version();

}