package deus.paperwork.recipe;

import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.data.registry.recipe.HasJsonAdapter;
import net.minecraft.core.data.registry.recipe.RecipeEntryBase;
import net.minecraft.core.data.registry.recipe.RecipeGroup;
import net.minecraft.core.data.registry.recipe.RecipeNamespace;
import net.minecraft.core.data.registry.recipe.SearchQuery;
import net.minecraft.core.data.registry.recipe.SearchQuery.QueryType;
import net.minecraft.core.data.registry.recipe.SearchQuery.SearchScope;
import net.minecraft.core.data.registry.recipe.adapter.RecipeJsonAdapter;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.Container;

import java.util.Objects;

public class RecipeEntryPrinter extends RecipeEntryBase<ItemStack[], ItemStack, String> implements HasJsonAdapter {

	// Constructor completo
	public RecipeEntryPrinter(ItemStack[] inputs, ItemStack output, String processType) {
		super(inputs, output, processType);
		if (inputs.length != 2) {
			throw new IllegalArgumentException("Se requieren exactamente 2 entradas (molde y material).");
		}
	}

	// Constructor por defecto
	public RecipeEntryPrinter() {
		super(new ItemStack[2], null, null);
	}

	// Verifica si los ítems en el contenedor coinciden con la receta
	public boolean matches(Container container) {
		ItemStack[] inputs = getInput();

		// Verifica el slot 0 (molde)
		ItemStack containerMold = container.getItem(0);
		if (containerMold == null || inputs[0] == null || !containerMold.isItemEqual(inputs[0])) {
			return false;
		}

		// Verifica el slot 1 (material)
		ItemStack containerMaterial = container.getItem(1);
		if (containerMaterial == null || inputs[1] == null || !containerMaterial.isItemEqual(inputs[1])) {
			return false;
		}

		// Nota: El combustible se maneja externamente (por ejemplo, en el TileEntity del bloque).
		return true;
	}

	// Devuelve el resultado de la receta (una salida)
	public ItemStack getCraftingResult(Container container) {
		ItemStack output = getOutput();
		return output != null ? output.copy() : null;
	}

	// Maneja la lógica después de fabricar
	public ItemStack[] onCraftResult(Container container) {
		ItemStack[] returnStack = new ItemStack[container.getContainerSize()];

		// Consume el molde (slot 0)
		ItemStack mold = container.getItem(0);
		if (mold != null) {
			container.removeItem(0, 1);
			if (mold.getItem().hasContainerItem()) {
				returnStack[0] = new ItemStack(mold.getItem().getContainerItem());
			}
		}

		// Consume el material (slot 1)
		ItemStack material = container.getItem(1);
		if (material != null) {
			container.removeItem(1, 1);
			if (material.getItem().hasContainerItem()) {
				returnStack[1] = new ItemStack(material.getItem().getContainerItem());
			}
		}

		// Coloca la salida en el slot 2
		ItemStack output = getCraftingResult(container);
		if (output != null) {
			container.setItem(2, output); // Coloca la salida en el slot 2
		}

		// Nota: El combustible no se consume aquí, ya que se maneja externamente.
		return returnStack;
	}

	// Soporte para búsqueda
	public boolean matchesQuery(SearchQuery query) {
		switch (query.mode) {
			case ALL:
				return (matchesRecipe(query) || matchesUsage(query)) && matchesScope(query);
			case RECIPE:
				return matchesRecipe(query) && matchesScope(query);
			case USAGE:
				return matchesUsage(query) && matchesScope(query);
			default:
				return false;
		}
	}

	private boolean matchesRecipe(SearchQuery query) {
		ItemStack output = getOutput();
		if (output != null && query.query.getLeft() == QueryType.NAME) {
			String outputName = output.getDisplayName();
			String queryValue = (String) query.query.getRight();
			if (query.strict && outputName.equalsIgnoreCase(queryValue)) {
				return true;
			}
			if (!query.strict && outputName.toLowerCase().contains(queryValue.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	private boolean matchesUsage(SearchQuery query) {
		// Verifica las entradas (molde y material)
		ItemStack[] inputs = getInput();
		for (ItemStack stack : inputs) {
			if (stack != null && query.query.getLeft() == QueryType.NAME) {
				String stackName = stack.getDisplayName();
				String queryValue = (String) query.query.getRight();
				if (query.strict && stackName.equalsIgnoreCase(queryValue)) {
					return true;
				}
				if (!query.strict && stackName.toLowerCase().contains(queryValue.toLowerCase())) {
					return true;
				}
			}
		}
		// Nota: No se verifica el combustible, ya que se maneja externamente.
		return false;
	}

	private boolean matchesScope(SearchQuery query) {
		if (query.scope.getLeft() == SearchScope.NONE) {
			return true;
		}
		if (query.scope.getLeft() == SearchScope.NAMESPACE) {
			RecipeNamespace namespace = (RecipeNamespace) Registries.RECIPES.getItem((String) query.scope.getRight());
			return namespace == parent.getParent();
		} else if (query.scope.getLeft() == SearchScope.NAMESPACE_GROUP) {
			try {
				RecipeGroup group = Registries.RECIPES.getGroupFromKey((String) query.scope.getRight());
				return group == parent;
			} catch (IllegalArgumentException e) {
				return false;
			}
		}
		return false;
	}

	// Adaptador JSON para serialización
	public RecipeJsonAdapter<?> getAdapter() {
		return new RecipePrinterJsonAdapter();
	}
}
