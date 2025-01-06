package capitalistspz.test.config;

import capitalistspz.test.SnowballKB;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.Strictness;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Config{
    public float snowKbMultiplier = 0.0f;
    public float snowDamage = 0.001f;
    public boolean snowTraditionalKb = true;
    public float eggKbMultiplier = 0.0f;
    public float eggDamage = 0.001f;
    public boolean eggTraditionalKb = true;

    public float fishingRodPullMultiplier = 0.1f;

    private static final File configFile = new File("config" + File.separator + "snowballkb.json");
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().setStrictness(Strictness.LENIENT).create();

    public static boolean save(Config config) {
        try {
            if (!configFile.getParentFile().exists())
                configFile.getParentFile().mkdirs();
            FileWriter fw = new FileWriter(configFile);
            fw.write(gson.toJson(config));
            fw.close();
            return true;
        } catch(Exception e){
            SnowballKB.logger.log(Level.WARN, "Failed to save config. {}", e.getMessage());
            return false;
        }

    }
    public static Config load() {
        try {
            FileReader fr = new FileReader(configFile);
            Config config = gson.fromJson(fr, Config.class);
            fr.close();
            SnowballKB.logger.log(Level.INFO, "Config loaded.");
            return config;

        } catch(Exception e) {
            SnowballKB.logger.log(Level.WARN, "Failed to load config, using default values: {}", e.getMessage());
        }
        return new Config();
    }
}
