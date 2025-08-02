package deus.paperwork;

import net.fabricmc.api.DedicatedServerModInitializer;

import static deus.paperwork.Paperwork.LOGGER;
import static deus.paperwork.Paperwork.MOD_ID;

public class PaperworkServer implements DedicatedServerModInitializer {
	@Override
	public void onInitializeServer() {
		LOGGER.info(MOD_ID+" Server Initialized");

	}
}
