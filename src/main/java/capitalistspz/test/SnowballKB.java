package capitalistspz.test;

import capitalistspz.test.config.Config;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SnowballKB implements ModInitializer {
    public static Config config = new Config();
    public static Logger logger = LogManager.getLogger();
    public static final float DegToRad = (float)Math.PI / 180;

    @Override
    public void onInitialize() {
        config = Config.load();
    }
}
