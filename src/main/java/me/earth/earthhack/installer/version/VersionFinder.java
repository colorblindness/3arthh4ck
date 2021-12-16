/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 */
package me.earth.earthhack.installer.version;

import com.google.gson.JsonObject;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import me.earth.earthhack.api.config.Jsonable;
import me.earth.earthhack.installer.main.MinecraftFiles;
import me.earth.earthhack.installer.version.Version;

public class VersionFinder {
    public List<Version> findVersions(MinecraftFiles files) throws IOException {
        File[] versionFolders = new File(files.getVersions()).listFiles();
        if (versionFolders == null) {
            throw new IllegalStateException("Version folder was empty!");
        }
        ArrayList<Version> result = new ArrayList<Version>();
        for (File file : versionFolders) {
            File[] contained;
            if (!file.getName().startsWith("1.12.2") || !file.isDirectory() || (contained = file.listFiles()) == null) continue;
            for (File json : contained) {
                if (!json.getName().endsWith("json")) continue;
                Version version = this.readJson(file.getName(), json);
                result.add(version);
            }
        }
        return result;
    }

    private Version readJson(String name, File jsonFile) throws IOException {
        JsonObject object = Jsonable.PARSER.parse((Reader)new InputStreamReader(jsonFile.toURI().toURL().openStream())).getAsJsonObject();
        return new Version(name, jsonFile, object);
    }
}

