package gg.steve.mc.frodo.eggs.framework.nbt.utils;

import org.bukkit.Bukkit;

import java.util.logging.Logger;

/**
 * This class acts as the "Brain" of the NBTApi. It contains the main logger for
 * other classes,registers bStats and checks rather Maven shading was done
 * correctly.
 *
 * @author tr7zw
 */
@SuppressWarnings("javadoc")
public enum MinecraftVersion {
    UNKNOWN(Integer.MAX_VALUE), // Use the newest known mappings
    MC1_7_R4(174),
    MC1_8_R3(183),
    MC1_9_R1(191),
    MC1_9_R2(192),
    MC1_10_R1(1101),
    MC1_11_R1(1111),
    MC1_12_R1(1121),
    MC1_13_R1(1131),
    MC1_13_R2(1132),
    MC1_14_R1(1141),
    MC1_15_R1(1151),
    MC1_16_R1(1161),
    MC1_16_R2(1162),
    MC1_16_R3(1163),
    MC1_16_R4(1164),
    MC1_16_R5(1165),
    MC1_16_R6(1166),
    MC1_16_R7(1167),
    MC1_16_R8(1168),
    MC1_16_R9(1169),
    MC1_17_R1(1171),
    MC1_17_R2(1172),
    MC1_17_R3(1173),
    MC1_17_R4(1174),
    MC1_17_R5(1175),
    MC1_17_R6(1176),
    MC1_17_R7(1177),
    MC1_17_R8(1178),
    MC1_17_R9(1179);

    private static MinecraftVersion version;
    private static Boolean hasGsonSupport;
    private static boolean bStatsDisabled = false;
    private static boolean disablePackageWarning = false;
    private static boolean updateCheckDisabled = false;
    /**
     * Logger used by the api
     */
    public static final Logger logger = Logger.getLogger("NBTAPI");

    // NBT-API Version
    protected static final String VERSION = "2.3.0-SNAPSHOT";

    private final int versionId;

    MinecraftVersion(int versionId) {
        this.versionId = versionId;
    }

    /**
     * @return A simple comparable Integer, representing the version.
     */
    public int getVersionId() {
        return versionId;
    }

    /**
     * Getter for this servers MinecraftVersion. Also init's bStats and checks the
     * shading.
     *
     * @return The enum for the MinecraftVersion this server is running
     */
    public static MinecraftVersion getVersion() {
        if (version != null) {
            return version;
        }
        final String ver = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            version = MinecraftVersion.valueOf(ver.replace("v", "MC"));
        } catch (IllegalArgumentException ex) {
            version = MinecraftVersion.UNKNOWN;
        }
        return version;
    }

    /**
     * @return True, if Gson is usable
     */
    public static boolean hasGsonSupport() {
        if (hasGsonSupport != null) {
            return hasGsonSupport;
        }
        try {
            hasGsonSupport = true;
        } catch (Exception ex) {
            hasGsonSupport = false;
        }
        return hasGsonSupport;
    }

    /**
     * Calling this function before the NBT-Api is used will disable bStats stats
     * collection. Please consider not to do that, since it won't affect your plugin
     * and helps the NBT-Api developer to see api's demand.
     */
    public static void disableBStats() {
        bStatsDisabled = true;
    }

    /**
     * Disables the update check. Uses Spiget to get the current version and
     * prints a warning when outdated.
     */
    public static void disableUpdateCheck() {
        updateCheckDisabled = true;
    }

    /**
     * Forcefully disables the log message for plugins not shading the API to
     * another location. This may be helpful for networks or development
     * environments, but please don't use it for plugins that are uploaded to
     * Spigotmc.
     */
    public static void disablePackageWarning() {
        disablePackageWarning = true;
    }

}
