package deus.paperwork.entry_points;


import deus.paperwork.block.confetti.ModelConfetti;
import deus.paperwork.block.file_cabinet.BlockModelFileCabinet;
import deus.paperwork.block.paperpile.layer.painted.ModelPaperLayerPainted;
import deus.paperwork.block.paperpile.regular.BlockModelPaperPile;
import deus.paperwork.block.paperpile.layer.ModelPaperLayer;
import deus.paperwork.block.paperpile.painted.ModelPaperPilePainted;
import deus.paperwork.entities.big_paperplane.BigPaperPlaneRenderer;
import deus.paperwork.entities.big_paperplane.EntityBigPaperPlane;
import deus.paperwork.entities.big_paperplane.ModelBigPaperPlane;
import deus.paperwork.entities.cardboard_box.EntityCardboardBox;
import deus.paperwork.entities.cardboard_box.ModelCardboardBox;
import deus.paperwork.entities.cardboard_box.RendererCardboardBox;
import deus.paperwork.entities.clippy.ClippyRenderer;
import deus.paperwork.entities.clippy.MobClippy;
import deus.paperwork.entities.clippy.Modelclippy;
import deus.paperwork.entities.gift_box.EntityGiftBox;
import deus.paperwork.entities.gift_box.ModelGiftBox;
import deus.paperwork.entities.gift_box.RendererGiftBox;
import deus.paperwork.entities.paperplane.EntityPaperPlane;
import deus.paperwork.entities.paperplane.ModelPaperPlane;
import deus.paperwork.entities.paperplane.PaperPlaneRenderer;
import deus.paperwork.entities.pigeon.EntityPigeon;
import deus.paperwork.entities.pigeon.ModelPigeon;
import deus.paperwork.entities.pigeon.PigeonRenderer;
import deus.paperwork.entities.stepler_projectile.SteplerProjectile;
import deus.paperwork.entities.stepler_projectile.SteplerProjectileRenderer;
import deus.paperwork.item.PaperworkItems;
import deus.paperwork.item.big_paperplane.ItemBigPaperplaneModel;
import deus.paperwork.item.custom_paper.CustomItemPaperModel;
import deus.paperwork.item.paperplane.ItemPaperplaneModel;
import deus.paperwork.mixin.IAEntityDispatcher;
import deus.paperwork.util.StaticFieldsExtractor;
import net.minecraft.client.render.EntityRenderDispatcher;
import net.minecraft.client.render.TileEntityRenderDispatcher;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.block.model.BlockModelHorizontalRotation;
import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.item.model.ItemModelCoal;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.helper.Side;
import org.jetbrains.annotations.NotNull;
import turniplabs.halplibe.helper.ModelHelper;
import turniplabs.halplibe.util.ModelEntrypoint;

import java.lang.reflect.Field;

import static deus.paperwork.Paperwork.MOD_ID;
import static deus.paperwork.block.PaperworkBlocks.*;

public class PaperworkModels implements ModelEntrypoint {
	@Override
	public void initBlockModels(BlockModelDispatcher blockModelDispatcher) {
		ModelHelper.setBlockModel(BLOCK_PRINTER, () -> new BlockModelHorizontalRotation<>(BLOCK_PRINTER)
			.setTex(0, MOD_ID + ":block/printer/sides", Side.sides)
			.setTex(0, MOD_ID + ":block/printer/top", Side.TOP)
			.setTex(0, MOD_ID + ":block/printer/front", Side.NORTH)
			.setTex(0, MOD_ID + ":block/printer/bottom", Side.BOTTOM)
		);

		ModelHelper.setBlockModel(BLOCK_PHOTOCOPIER, () -> new BlockModelHorizontalRotation<>(BLOCK_PHOTOCOPIER)
			.setTex(0, MOD_ID + ":block/photocopier/sides", Side.sides)
			.setTex(0, MOD_ID + ":block/photocopier/top", Side.TOP)
			.setTex(0, MOD_ID + ":block/photocopier/front", Side.NORTH)
			.setTex(0, MOD_ID + ":block/photocopier/bottom", Side.BOTTOM)
		);

		ModelHelper.setBlockModel(BLOCK_PAPER_PILE, () -> new BlockModelPaperPile<>(BLOCK_PAPER_PILE)
			.setTex(0, MOD_ID + ":block/paperpile/paper/sides", Side.sides)
			.setTex(0, MOD_ID + ":block/paperpile/paper/topbottom", Side.TOP)
			.setTex(0, MOD_ID + ":block/paperpile/paper/topbottom", Side.BOTTOM)
		);


		ModelHelper.setBlockModel(BLOCK_PAPER_PILE_PAINTED, () -> new ModelPaperPilePainted<>(BLOCK_PAPER_PILE_PAINTED)
			.setTex(0, MOD_ID + ":block/paperpile/paper/sides", Side.sides)
			.setTex(0, MOD_ID + ":block/paperpile/paper/topbottom", Side.TOP)
			.setTex(0, MOD_ID + ":block/paperpile/paper/topbottom", Side.BOTTOM)
		);


		ModelHelper.setBlockModel(BLOCK_PAPER_LAYER, () -> new ModelPaperLayer<>(BLOCK_PAPER_LAYER)
			.setTex(0, MOD_ID + ":block/paperpile/paper/sides", Side.sides)
			.setTex(0, MOD_ID + ":block/paperpile/paper/topbottom", Side.TOP)
			.setTex(0, MOD_ID + ":block/paperpile/paper/topbottom", Side.BOTTOM)
		);

		ModelHelper.setBlockModel(BLOCK_PAPER_LAYER_PAINTED, () -> new ModelPaperLayerPainted<>(BLOCK_PAPER_LAYER_PAINTED)
			.setTex(0, MOD_ID + ":block/paperpile/paper/sides", Side.sides)
			.setTex(0, MOD_ID + ":block/paperpile/paper/topbottom", Side.TOP)
			.setTex(0, MOD_ID + ":block/paperpile/paper/topbottom", Side.BOTTOM)
		);


		ModelHelper.setBlockModel(BLOCK_CONFETTI, () -> new ModelConfetti<>(BLOCK_CONFETTI)
			.setTex(0, MOD_ID + ":block/confetti/confetti", Side.sides)
			.setTex(0, MOD_ID + ":block/confetti/confetti", Side.TOP)
			.setTex(0, MOD_ID + ":block/confetti/confetti", Side.BOTTOM)
		);


		ModelHelper.setBlockModel(BLOCK_NEWSPRINT_PILE, () -> new BlockModelPaperPile<>(BLOCK_NEWSPRINT_PILE)
			.setTex(0, MOD_ID + ":block/newsprint/sides", Side.sides)
			.setTex(0, MOD_ID + ":block/newsprint/topbottom", Side.TOP)
			.setTex(0, MOD_ID + ":block/newsprint/topbottom", Side.BOTTOM)
		);

		ModelHelper.setBlockModel(BLOCK_NEWSPRINT_LAYER, () -> new ModelPaperLayer<>(BLOCK_NEWSPRINT_LAYER)
			.setTex(0, MOD_ID + ":block/newsprint/sides", Side.sides)
			.setTex(0, MOD_ID + ":block/newsprint/topbottom", Side.TOP)
			.setTex(0, MOD_ID + ":block/newsprint/topbottom", Side.BOTTOM)
		);

		ModelHelper.setBlockModel(BLOCK_CARDBOARD_PILE, () -> new BlockModelPaperPile<>(BLOCK_CARDBOARD_PILE)
			.setTex(0, MOD_ID + ":block/cardboard_pile/sides", Side.sides)
			.setTex(0, MOD_ID + ":block/cardboard_pile/topbottom", Side.TOP)
			.setTex(0, MOD_ID + ":block/cardboard_pile/topbottom", Side.BOTTOM)
		);

		ModelHelper.setBlockModel(BLOCK_CARDBOARD_LAYER, () -> new ModelPaperLayer<>(BLOCK_CARDBOARD_LAYER)
			.setTex(0, MOD_ID + ":block/cardboard_pile/sides", Side.sides)
			.setTex(0, MOD_ID + ":block/cardboard_pile/topbottom", Side.TOP)
			.setTex(0, MOD_ID + ":block/cardboard_pile/topbottom", Side.BOTTOM)
		);

		ModelHelper.setBlockModel(BLOCK_CORCKBOARD, () -> new BlockModelStandard<>(BLOCK_CORCKBOARD)
			.setTex(0, MOD_ID + ":block/corckboard/all", Side.sides)
		);

		ModelHelper.setBlockModel(BLOCK_CHALKBOARD, () -> new BlockModelStandard<>(BLOCK_CHALKBOARD)
			.setTex(0, MOD_ID + ":block/chalkboard/all", Side.sides)
		);

		ModelHelper.setBlockModel(BLOCK_FILE_CABINET_IRON, () -> new BlockModelFileCabinet<>(BLOCK_FILE_CABINET_IRON)
			.setTex(0, MOD_ID + ":block/file_cabinet/iron/sides", Side.sides)
			.setTex(0, MOD_ID + ":block/file_cabinet/iron/top", Side.TOP)
			.setTex(0, MOD_ID + ":block/file_cabinet/iron/front", Side.NORTH)
			.setTex(0, MOD_ID + ":block/file_cabinet/iron/bottom", Side.BOTTOM)
		);

		ModelHelper.setBlockModel(BLOCK_FILE_CABINET_BROWN_PLANKS, () -> new BlockModelFileCabinet<>(BLOCK_FILE_CABINET_BROWN_PLANKS)
			.setTex(0, MOD_ID + ":block/file_cabinet/planks/brown/sides", Side.sides)
			.setTex(0, MOD_ID + ":block/file_cabinet/planks/brown/top", Side.TOP)
			.setTex(0, MOD_ID + ":block/file_cabinet/planks/brown/front", Side.NORTH)
			.setTex(0, MOD_ID + ":block/file_cabinet/planks/brown/bottom", Side.BOTTOM)
		);


	}

	@Override
	public void initItemModels(ItemModelDispatcher itemModelDispatcher) {
		makeItemModels(PaperworkItems.class);
		ModelHelper.setItemModel(Items.PAPER,
			() -> {
				ItemModelStandard model = new CustomItemPaperModel(Items.PAPER, MOD_ID);
				model.icon = TextureRegistry.getTexture(Items.PAPER.namespaceID);
				return model;
			});
		ModelHelper.setItemModel(PaperworkItems.PAPERPLANE,
			() -> {
				ItemModelStandard model = new ItemPaperplaneModel(PaperworkItems.PAPERPLANE, MOD_ID);
				model.icon = TextureRegistry.getTexture(PaperworkItems.PAPERPLANE.namespaceID);
				return model;
			});
		ModelHelper.setItemModel(PaperworkItems.BIG_PAPERPLANE,
			() -> {
				ItemModelStandard model = new ItemBigPaperplaneModel(PaperworkItems.BIG_PAPERPLANE, MOD_ID);
				model.icon = TextureRegistry.getTexture(PaperworkItems.BIG_PAPERPLANE.namespaceID);
				return model;
			});

	}

	@Override
	public void initEntityModels(EntityRenderDispatcher dispatcher) {
		addEntityModel(dispatcher, EntityPaperPlane.class, new PaperPlaneRenderer(new ModelPaperPlane()));
		addEntityModel(dispatcher, EntityBigPaperPlane.class, new BigPaperPlaneRenderer(new ModelBigPaperPlane()));
		addEntityModel(dispatcher, EntityCardboardBox.class, new RendererCardboardBox());
		addEntityModel(dispatcher, EntityGiftBox.class, new RendererGiftBox(new ModelGiftBox()));
		addEntityModel(dispatcher, EntityPigeon.class, new PigeonRenderer(new ModelPigeon()));
		addEntityModel(dispatcher, SteplerProjectile.class, new SteplerProjectileRenderer());
		addEntityModel(dispatcher, MobClippy.class, new ClippyRenderer(new Modelclippy()));

	}

	public void addEntityModel(EntityRenderDispatcher dispatcher, @NotNull Class<? extends Entity> clazz, EntityRenderer<?> renderer){
		renderer.init(dispatcher);
		((IAEntityDispatcher)dispatcher).getRenderers().put(clazz, renderer);
	}

	@Override
	public void initTileEntityModels(TileEntityRenderDispatcher tileEntityRenderDispatcher) {

	}

	@Override
	public void initBlockColors(BlockColorDispatcher blockColorDispatcher) {

	}

	public static void makeItemModels(Class<?> c) {
		try {
			String[] staticFieldNames = StaticFieldsExtractor.extractor(c);
			for (String fieldName : staticFieldNames) {
				Field field = c.getDeclaredField(fieldName);
				field.setAccessible(true);
				Object value = field.get(null);

				if (value instanceof Item) {
					Item item = (Item) value;
					ModelHelper.setItemModel(item,
						() -> {
							ItemModelStandard model = new ItemModelStandard(item, MOD_ID);
							model.icon = TextureRegistry.getTexture(item.namespaceID);
							return model;
						});
				}
			}
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	public static void makeBlockModels(Class<?> c) {
		try {
			String[] staticFieldNames = StaticFieldsExtractor.extractor(c);
			for (String fieldName : staticFieldNames) {
				Field field = c.getDeclaredField(fieldName);
				field.setAccessible(true);
				Object value = field.get(null);

				if (value instanceof Block<?>) {
					Block<?> block = (Block<?>) value;
					ModelHelper.setBlockModel(block, () -> new BlockModelStandard<>(block));

				}

			}
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
