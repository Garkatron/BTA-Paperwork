package deus.paperwork;

import deus.paperwork.block.PaperworkBlocks;
import deus.paperwork.entry_points.PaperworkEntities;
import deus.paperwork.entry_points.PaperworkSounds;
import deus.paperwork.item.PaperworkItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.GameStartEntrypoint;


public class Paperwork implements ModInitializer, GameStartEntrypoint {
    public static final String MOD_ID = "paperwork";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    @Override
    public void onInitialize() {
		PaperworkBlocks.initialize();
		PaperworkItems.initialize();
		PaperworkEntities.initialize();
		LOGGER.info(MOD_ID+" Core Initialized.");
    }


	@Override
	public void beforeGameStart() {
		PaperworkSounds.initialize();
		//SoundHelper.addSound(MOD_ID, "material/paper/paper.wav" );
	}

	@Override
	public void afterGameStart() {

	}
}
