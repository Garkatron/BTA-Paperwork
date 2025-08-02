package deus.paperwork.entry_points;

import net.minecraft.core.sound.SoundTypes;

import static deus.paperwork.Paperwork.LOGGER;
import static deus.paperwork.Paperwork.MOD_ID;

public class PaperworkSounds {
	public static void initialize() {
		// SoundTypes.register(MOD_ID+".paper.put" );
		SoundTypes.loadSoundsJson(MOD_ID);
		LOGGER.info(MOD_ID+" Sounds Initialized.");

	}
}
