package deus.paperwork.entry_points;

import deus.paperwork.recipe.RecipeEntryPrinter;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.data.registry.recipe.RecipeNamespace;
import net.minecraft.core.data.registry.recipe.RecipeGroup;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.item.Items;
import turniplabs.halplibe.util.RecipeEntrypoint;

import static deus.paperwork.Paperwork.MOD_ID;
import static net.minecraft.core.data.registry.Registries.stackListOf;

public class PaperworkRecipes implements RecipeEntrypoint {

	public static final RecipeNamespace PAPERWORK_RECIPE_NAMESPACE = new RecipeNamespace();

	@Override
	public void initNamespaces() {
		Registries.RECIPES.register(MOD_ID, PAPERWORK_RECIPE_NAMESPACE);
		Registries.RECIPE_TYPES.register(MOD_ID + ":printer", RecipeEntryPrinter.class);
		Registries.ITEM_GROUPS.register(MOD_ID+":dyes", stackListOf(Items.DYE));

		PAPERWORK_RECIPE_NAMESPACE.register("printer", new RecipeGroup<RecipeEntryPrinter>(new RecipeSymbol(MOD_ID+":dyes")));
	}

	@Override
	public void onRecipesReady() {
		RecipeGroup group = PAPERWORK_RECIPE_NAMESPACE.getItem("printer");
		if (group == null) {
			System.out.println("Error: El grupo 'printer' no está registrado.");
			return;
		}

		System.out.println("Recetas cargadas en el grupo 'printer':");
		for (Object recipe : group.getAllRecipes()) {
			System.out.println("Receta: " + group.getKey(recipe));

		}
	}
}
