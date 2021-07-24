package capitalistspz.test;

import capitalistspz.test.config.Config;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SnowballKB implements ModInitializer {
    public static Config config;
    public static Logger logger = LogManager.getLogger();
    @Override
    public void onInitialize() {
        config = Config.load();
    }
}
