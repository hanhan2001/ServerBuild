package me.xiaoying.serverbuild.configuration;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.*;

public class YamlConfiguration {
    private static String file;
    private HashMap<Object, Object> properties = new HashMap<>();

    private Object get(String key) {
        String separator = ".";
        String[] separatorKeys = null;
        if (key.contains(separator))
            separatorKeys = key.split("\\.");
        else
            return properties.get(key);

        Map<String, Map<String, Object>> finalValue = new HashMap<>();
        for (int i = 0; i < separatorKeys.length - 1; i++) {
            if (i == 0) {
                finalValue = (Map) properties.get(separatorKeys[i]);
                continue;
            }
            if (finalValue == null)
                break;

            finalValue = (Map) finalValue.get(separatorKeys[i]);
        }
        return finalValue == null ? null : finalValue.get(separatorKeys[separatorKeys.length - 1]);
    }

    public String getString(String key) {
        try {
            return get(key).toString();
        } catch (Exception e) {
            return null;
        }
    }

    public List<String> getStringList(String key) {
        if (get(key) == null)
            return null;
        if (!(get(key) instanceof List))
            return null;
        return (List<String>) get(key);
    }

    public int getInt(String key) {
        return Integer.parseInt(String.valueOf(get(key)));
    }

    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(String.valueOf(get(key)));
    }

    public double getDouble(String key) {
        return Double.parseDouble(String.valueOf(get(key)));
    }

    public long getLong(String key) {
        return Long.parseLong(String.valueOf(get(key)));
    }

    public void load(File file) {
        try (FileInputStream in = new FileInputStream(file)) {
            try {
                YamlConfiguration.file = file.getPath();
                this.properties = new Yaml().loadAs(in, HashMap.class);

                for (Object o : this.properties.keySet()) {
                    Object value = null;
                    if (o instanceof Long || o instanceof Integer)
                        value = this.properties.get(o);

                    if (value == null)
                        continue;

                    this.properties.remove(o);
                    this.properties.put(o.toString(), value);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load(String file) {
        Yaml yaml = new Yaml();
        this.properties = yaml.loadAs(file, HashMap.class);

        if (this.properties == null)
            return;

        for (Object o : this.properties.keySet()) {
            Object value = null;
            if (o instanceof Long || o instanceof Integer)
                value = this.properties.get(o);

            if (value == null)
                continue;

            this.properties.remove(o);
            this.properties.put(o.toString(), value);
        }
    }

    public static YamlConfiguration loadConfiguration(File file) {
        YamlConfiguration configuration = new YamlConfiguration();
        configuration.load(file);
        return configuration;
    }

    public static YamlConfiguration loadConfiguration(String file) {
        YamlConfiguration configuration = new YamlConfiguration();
        configuration.load(file);
        return configuration;
    }
}
