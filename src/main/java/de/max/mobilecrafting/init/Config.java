package de.max.mobilecrafting.init;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Objects;

public final class Config extends JavaPlugin {
    public static HashMap<String, HashMap<String, Object>> files = new HashMap<>() {{
        put("storage", new HashMap<>() {{
            put("file", null);
            put("config", null);
        }});
        put("de_DE", new HashMap<>() {{
            put("file", null);
            put("config", null);
        }});
        put("en_US", new HashMap<>() {{
            put("file", null);
            put("config", null);
        }});
        put("custom_lang", new HashMap<>() {{
            put("file", null);
            put("config", null);
        }});
    }};

    /**
     * Entnimmt die verlangte Configdatei der HashMap
     * <p>
     * Retrieves the config file from the HashMap
     *
     * @return Datei <p> File
     * @author ItsLeMax (StorageTerminal)
     * @see #files
     */
    public static File getFile(String configName) {
        return (File) files.get(configName).get("file");
    }

    /**
     * Entnimmt die verlangte Config der HashMap
     * <p>
     * Retrieves the config from the HashMap
     *
     * @return FileConfiguration Config
     * @author ItsLeMax (StorageTerminal)
     * @see #files
     */
    public static FileConfiguration getConfig(String configName) {
        return (FileConfiguration) files.get(configName).get("config");
    }

    /**
     * Ermöglicht das initialisieren einer Variable der HashMap
     * <p>
     * Allows the initialization of a variable of the HashMap
     *
     * @param data Datei oder Config
     *             <p>
     *             File or Config
     * @author ItsLeMax (StorageTerminal)
     * @see #files
     */
    private static void put(String configName, Object data) {
        files.get(configName).put(data instanceof File ? "file" : data instanceof FileConfiguration ? "config" : "error", data);
    }

    /**
     * Erstellt die Konfigurationsdateien (mitsamt Ordner) sofern nicht vorhanden
     * <p>
     * Creates the configuration files (with folder) if they do not exist
     *
     * @author ItsLeMax, Spigot (StorageTerminal)
     * @link <a href="https://spigotmc.org/wiki/config-files/">Spigot Wiki</a>
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void createConfigs(MobileCrafting plugin) {
        File languageFolder = new File(plugin.getDataFolder(), "languages");
        if (!languageFolder.exists()) languageFolder.mkdirs();

        for (String file : files.keySet()) {
            boolean isLanguageFile = file.contains("_");

            String filePath = isLanguageFile ? "languages/" + file + ".yml" : file + ".yml";
            File configFile = new File(plugin.getDataFolder(), filePath);

            put(file, new File(plugin.getDataFolder() + (isLanguageFile ? "/languages/" : "")));

            try {
                if (!configFile.exists()) {
                    if (isLanguageFile) {
                        Files.copy(Objects.requireNonNull(MobileCrafting.plugin.getResource(filePath)), configFile.toPath());
                    } else {
                        configFile.createNewFile();
                    }
                }
            } catch (IOException error) {
                throw new RuntimeException(error);
            }

            FileConfiguration cfg = YamlConfiguration.loadConfiguration(configFile);
            put(file, configFile);
            put(file, cfg);
        }
    }
}