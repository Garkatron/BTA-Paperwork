package deus.paperwork.entry_points;

import deus.paperwork.block.paperpile.BlockModelPaper;
import deus.paperwork.block.paperpile.BlockModelPaperLayer;
import deus.paperwork.item.PaperworkItems;
import deus.paperwork.util.StaticFieldsExtractor;
import net.minecraft.client.render.EntityRenderDispatcher;
import net.minecraft.client.render.TileEntityRenderDispatcher;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.block.model.BlockModelRotatable;
import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.item.Item;
import net.minecraft.core.util.helper.Side;
import turniplabs.halplibe.helper.ModelHelper;
import turniplabs.halplibe.util.ModelEntrypoint;

import java.lang.reflect.Field;

import static deus.paperwork.Paperwork.MOD_ID;
import static deus.paperwork.block.PaperworkBlocks.*;

public class PaperworkModels implements ModelEntrypoint {
	@Override
	public void initBlockModels(BlockModelDispatcher blockModelDispatcher) {
		ModelHelper.setBlockModel(BLOCK_PRINTER, () -> new BlockModelRotatable<>(BLOCK_PRINTER)
			.setTex(0,MOD_ID+":block/printer/sides", Side.sides)
			.setTex(0,MOD_ID+":block/printer/top", Side.TOP)
			.setTex(0,MOD_ID+":block/printer/front", Side.NORTH)
			.setTex(0,MOD_ID+":block/printer/bottom", Side.BOTTOM)
		);

		ModelHelper.setBlockModel(BLOCK_PHOTOCOPIER, () -> new BlockModelRotatable<>(BLOCK_PHOTOCOPIER)
			.setTex(0,MOD_ID+":block/photocopier/sides", Side.sides)
			.setTex(0,MOD_ID+":block/photocopier/top", Side.TOP)
			.setTex(0,MOD_ID+":block/photocopier/bottom", Side.BOTTOM)
			.setTex(0,MOD_ID+":block/photocopier/front", Side.SOUTH)
		);

		ModelHelper.setBlockModel(BLOCK_PAPER_PILE, () -> new BlockModelPaper<>(BLOCK_PAPER_PILE)
			.setTex(0,MOD_ID+":block/paperpile/sides", Side.sides)
			.setTex(0,MOD_ID+":block/paperpile/topbottom", Side.TOP)
			.setTex(0,MOD_ID+":block/paperpile/topbottom", Side.BOTTOM)
		);

		ModelHelper.setBlockModel(BLOCK_PAPER_LAYER, () -> new BlockModelPaperLayer<>(BLOCK_PAPER_LAYER)
			.setTex(0,MOD_ID+":block/paperpile/sides", Side.sides)
			.setTex(0,MOD_ID+":block/paperpile/topbottom", Side.TOP)
			.setTex(0,MOD_ID+":block/paperpile/topbottom", Side.BOTTOM)
		);


		ModelHelper.setBlockModel(BLOCK_NEWSPRINT_PILE, () -> new BlockModelPaper<>(BLOCK_NEWSPRINT_PILE)
			.setTex(0,MOD_ID+":block/newsprint/sides", Side.sides)
			.setTex(0,MOD_ID+":block/newsprint/topbottom", Side.TOP)
			.setTex(0,MOD_ID+":block/newsprint/topbottom", Side.BOTTOM)
		);

		ModelHelper.setBlockModel(BLOCK_NEWSPRINT_LAYER, () -> new BlockModelPaperLayer<>(BLOCK_NEWSPRINT_LAYER)
			.setTex(0,MOD_ID+":block/newsprint/sides", Side.sides)
			.setTex(0,MOD_ID+":block/newsprint/topbottom", Side.TOP)
			.setTex(0,MOD_ID+":block/newsprint/topbottom", Side.BOTTOM)
		);


		ModelHelper.setBlockModel(BLOCK_CARTON_BOX, () -> new BlockModelStandard<>(BLOCK_CARTON_BOX)
			.setTex(0,MOD_ID+":block/cartonbox/sides", Side.sides)
			.setTex(0,MOD_ID+":block/cartonbox/top", Side.TOP)
			.setTex(0,MOD_ID+":block/cartonbox/bottom", Side.BOTTOM)
		);

		ModelHelper.setBlockModel(BLOCK_CORCKBOARD, () -> new BlockModelStandard<>(BLOCK_CORCKBOARD)
			.setTex(0,MOD_ID+":block/corckboard/all", Side.sides)
		);

		ModelHelper.setBlockModel(BLOCK_CHALKBOARD, () -> new BlockModelStandard<>(BLOCK_CHALKBOARD)
			.setTex(0,MOD_ID+":block/chalkboard/all", Side.sides)
		);

		ModelHelper.setBlockModel(BLOCK_FILE_CABINET_IRON, () -> new BlockModelStandard<>(BLOCK_FILE_CABINET_IRON)
			.setTex(0,MOD_ID+":block/file_cabinet/iron/sides", Side.sides)
			.setTex(0,MOD_ID+":block/file_cabinet/iron/bottom", Side.BOTTOM)
			.setTex(0,MOD_ID+":block/file_cabinet/iron/front", Side.SOUTH)
			.setTex(0,MOD_ID+":block/file_cabinet/iron/top", Side.TOP)
		);


		ModelHelper.setBlockModel(BLOCK_FILE_CABINET_BROWN_PLANKS, () -> new BlockModelStandard<>(BLOCK_FILE_CABINET_BROWN_PLANKS)
			.setTex(0,MOD_ID+":block/file_cabinet/planks/brown/sides", Side.sides)
			.setTex(0,MOD_ID+":block/file_cabinet/planks/brown/front", Side.SOUTH)
			.setTex(0,MOD_ID+":block/file_cabinet/planks/brown/top", Side.TOP)
			.setTex(0,MOD_ID+":block/file_cabinet/planks/brown/bottom", Side.BOTTOM)
		);



	}

	@Override
	public void initItemModels(ItemModelDispatcher itemModelDispatcher) {
		makeItemModels(PaperworkItems.class);
	}

	@Override
	public void initEntityModels(EntityRenderDispatcher entityRenderDispatcher) {

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
