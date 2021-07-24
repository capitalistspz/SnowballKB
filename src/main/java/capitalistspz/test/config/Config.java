package capitalistspz.test.config;

import capitalistspz.test.SnowballKB;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Config{
    public float snowKbMultiplier = 0.0f;
    public float snowDamage = 0.001f;
    public float eggKbMultiplier = 0.0f;
    public float eggDamage = 0.001f;
    public float fishingRodPullMultiplier = 0.1f;

    private static final File configFile = new File("config/snowballkb.json");
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();

    public static void save(Config config) {
        try {
            FileWriter fw = new FileWriter(configFile);
            fw.append(gson.toJson(config));
            fw.close();
        } catch(Exception ignored){ }

    }
    public static Config load() {
        try {
            FileReader fr = new FileReader(configFile);
            Config config = gson.fromJson(fr, Config.class);
            fr.close();
            SnowballKB.logger.log(Level.INFO, "Config loaded.");
            return config;

        } catch(Exception e) {
            SnowballKB.logger.log(Level.WARN, "Failed to load config, using default values.");
        }
        return new Config();
    }
}
