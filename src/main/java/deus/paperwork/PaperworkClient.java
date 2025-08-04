package deus.paperwork;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.sound.SoundRepository;
import net.minecraft.core.util.collection.NamespaceID;
import turniplabs.halplibe.helper.EntityHelper;
import turniplabs.halplibe.util.ClientStartEntrypoint;

import static deus.paperwork.Paperwork.LOGGER;
import static deus.paperwork.Paperwork.MOD_ID;

public class PaperworkClient implements ClientStartEntrypoint, ClientModInitializer {
	@Override
	public void onInitializeClient() {
		SoundRepository.registerNamespace(MOD_ID);
		LOGGER.info(MOD_ID+" Client Initialized");
	}

	@Override
	public void beforeClientStart() {

	}

	@Override
	public void afterClientStart() {

	}
}
