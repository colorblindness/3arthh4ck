package me.earth.earthhack.impl;

import me.earth.earthhack.api.util.interfaces.Globals;
import me.earth.earthhack.impl.core.ducks.IMinecraft;
import me.earth.earthhack.impl.managers.Managers;
import me.earth.earthhack.impl.managers.thread.GlobalExecutor;
import me.earth.earthhack.impl.util.discord.DiscordPresence;
import me.earth.earthhack.impl.util.math.geocache.Sphere;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

public class Earthhack
        implements Globals {
    private static final Logger LOGGER = LogManager.getLogger((String)"3arthh4ck");
    public static final String NAME = "3arthh4ck";
    public static final String VERSION = "1.3.1-09dc40abfd7b";

    public static void preInit() {
        GlobalExecutor.EXECUTOR.submit(() -> Sphere.cacheSphere(LOGGER));
    }

    private void log4j2() {
        System.setProperty("log4j2.formatMsgNoLookups", "true"); // not used by mc, causes issues with older log4j2 versions
    }

    public static void init() {
        LOGGER.info("\n\nInitializing 3arthh4ck.");
        Display.setTitle((String)"3arthh4ck - 1.3.1");
        DiscordPresence.start();
        Managers.load();
        LOGGER.info("\n3arthh4ck initialized.\n");
    }

    public static void postInit() {
    }


    public static Logger getLogger() {
        return LOGGER;
    }

    public static boolean isRunning() {
        return ((IMinecraft)mc).isEarthhackRunning();
    }
}