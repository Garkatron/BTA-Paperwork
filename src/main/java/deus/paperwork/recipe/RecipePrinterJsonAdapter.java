package deus.paperwork.recipe;

import com.google.gson.*;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.data.registry.recipe.adapter.RecipeJsonAdapter;
import net.minecraft.core.item.ItemStack;

import java.lang.reflect.Type;

public class RecipePrinterJsonAdapter implements RecipeJsonAdapter<RecipeEntryPrinter> {
	@Override
	public RecipeEntryPrinter deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject obj = json.getAsJsonObject();

		JsonArray inputsJson = obj.getAsJsonArray("inputs");
		ItemStack[] inputs = new ItemStack[2];
		for (int i = 0; i < 2; i++) {
			inputs[i] = context.deserialize(inputsJson.get(i).getAsJsonObject(), ItemStack.class);
		}

		ItemStack output = context.deserialize(obj.get("output").getAsJsonObject(), ItemStack.class);

		String processType = obj.get("process_type").getAsString();
		return new RecipeEntryPrinter(inputs, output, processType);
	}

	@Override
	public JsonElement serialize(RecipeEntryPrinter src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject obj = new JsonObject();
		obj.addProperty("name", src.toString());
		obj.addProperty("type", Registries.RECIPE_TYPES.getKey(src.getClass()));

		JsonArray inputsJson = new JsonArray();
		for (ItemStack input : src.getInput()) {
			inputsJson.add(context.serialize(input));
		}
		obj.add("inputs", inputsJson);

		obj.add("output", context.serialize(src.getOutput()));

		obj.addProperty("process_type", src.getData());
		return obj;
	}


}
