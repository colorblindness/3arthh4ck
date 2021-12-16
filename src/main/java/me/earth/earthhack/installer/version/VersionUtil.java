/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 */
package me.earth.earthhack.installer.version;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.earth.earthhack.api.config.Jsonable;
import me.earth.earthhack.installer.version.Version;

public class VersionUtil {
    public static final String MAIN = "net.minecraft.launchwrapper.Launch";
    public static final String EARTH = "--tweakClass me.earth.earthhack.tweaker.EarthhackTweaker";
    public static final String FORGE = "--tweakClass net.minecraftforge.fml.common.launcher.FMLTweaker";
    public static final String ARGS = "minecraftArguments";
    public static final String LIBS = "libraries";

    public static boolean hasEarthhack(Version version) {
        return VersionUtil.containsArgument(version, EARTH);
    }

    public static boolean hasForge(Version version) {
        return VersionUtil.containsArgument(version, FORGE);
    }

    public static boolean hasLaunchWrapper(Version version) {
        JsonElement element = version.getJson().get("mainClass");
        if (element != null) {
            return element.getAsString().equals(MAIN);
        }
        return false;
    }

    public static boolean containsArgument(Version version, String tweaker) {
        JsonElement element = version.getJson().get(ARGS);
        if (element != null) {
            return element.getAsString().contains(tweaker);
        }
        return false;
    }

    public static JsonElement getOrElse(String name, JsonObject object, String returnElse) {
        JsonElement element = object.get(name);
        if (element == null) {
            return Jsonable.PARSER.parse(returnElse);
        }
        return element;
    }
}

