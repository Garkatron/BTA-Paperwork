package deus.paperwork;

import deus.paperwork.block.PaperworkBlocks;
import deus.paperwork.entry_points.PaperworkSounds;
import deus.paperwork.item.PaperworkItems;
import deus.utils.initializers.EntityInitializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.GameStartEntrypoint;


public class Paperwork implements ModInitializer, GameStartEntrypoint {
    public static final String MOD_ID = "paperwork";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


	static {
		ConfigManager.makeConfig();
	}



	@Override
    public void onInitialize() {
		PaperworkBlocks.initialize();
		PaperworkItems.initialize();
		EntityInitializer.initialize("deus.paperwork");
		LOGGER.info(MOD_ID+" Core Initialized.");
    }


	@Override
	public void beforeGameStart() {
		PaperworkSounds.initialize();

		try {
			TextureRegistry.initializeAllFiles(MOD_ID, TextureRegistry.blockAtlas, true);
		} catch (Exception var2) {
			LOGGER.warn("PainScale: Failed to fully initialize assets, some issue may occur!", var2);
		}
		//SoundHelper.addSound(MOD_ID, "material/paper/paper.wav" );
	}

	@Override
	public void afterGameStart() {

	}
}
