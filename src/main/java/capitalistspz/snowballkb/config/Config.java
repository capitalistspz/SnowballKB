package capitalistspz.snowballkb.config;

import capitalistspz.snowballkb.SnowballKB;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.Strictness;
import org.apache.logging.log4j.Level;

import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;

public class Config{
    public static class ThrownItemConfig {
        public boolean vanillaStyleKnockback;
        public float knockbackMultiplier;
        public float hitDamage;
    }

    public String version = "1.6";
    public ThrownItemConfig snowball =  new ThrownItemConfig();
    public ThrownItemConfig egg = new ThrownItemConfig();
    public ThrownItemConfig enderpearl = new ThrownItemConfig();
    public boolean applyToPlayers = true;
    public boolean applyToBlazes = false;
    public boolean applyToOtherEntities = false;

    public float fishingRodPullMultiplier = 0.1f;

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().setStrictness(Strictness.LENIENT).create();

    public static boolean save(Config config, Path path) {
        try {
            FileWriter fw = new FileWriter(path.toFile());
            fw.write(gson.toJson(config));
            fw.close();
            return true;
        } catch(Exception e){
            SnowballKB.logger.log(Level.WARN, "Failed to save config. {}", e.getMessage());
            return false;
        }

    }
    public static Config load(Path path) {
        try {
            FileReader fr = new FileReader(path.toFile());
            Config config = gson.fromJson(fr, Config.class);
            fr.close();

            // gson.fromJson() can return null if file is empty
            if(config == null) {
                SnowballKB.logger.log(Level.WARN, "Failed to load empty config, using default values.");
                return new Config();
            }

            SnowballKB.logger.log(Level.INFO, "Config loaded.");
            return config;
        } catch(Exception e) {
            SnowballKB.logger.log(Level.WARN, "Failed to load config, using default values: {}", e.getMessage());
        }
        return new Config();
    }
}
