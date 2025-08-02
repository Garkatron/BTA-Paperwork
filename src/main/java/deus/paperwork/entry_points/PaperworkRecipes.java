package deus.paperwork.entry_points;

import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.data.registry.recipe.RecipeNamespace;
import net.minecraft.core.item.Items;
import turniplabs.halplibe.helper.RecipeBuilder;
import turniplabs.halplibe.util.RecipeEntrypoint;

import static deus.paperwork.Paperwork.MOD_ID;

public class PaperworkRecipes implements RecipeEntrypoint {

	public static final RecipeNamespace PAPERWORK_RECIPE_NAMESPACE = new RecipeNamespace();

	@Override
	public void onRecipesReady() {
//		RecipeBuilder.Shaped(MOD_ID)
//			.setShape("###")
//			.create(MOD_ID+":", RUNE_SOWILO.getDefaultStack());

	}

	@Override
	public void initNamespaces() {
		Registries.RECIPES.register(MOD_ID, PAPERWORK_RECIPE_NAMESPACE);

	}
}
