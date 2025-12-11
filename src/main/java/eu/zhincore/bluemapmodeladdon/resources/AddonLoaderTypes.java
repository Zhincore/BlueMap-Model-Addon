package eu.zhincore.bluemapmodeladdon.resources;

import de.bluecolored.bluemap.core.util.Key;
import eu.zhincore.bluemapmodeladdon.mods.immersiveengineering.SplitModelExtension;
import eu.zhincore.bluemapmodeladdon.mods.immersiveengineering.SplitModelRenderer;

import me.owies.bluemapmodelloaders.resources.LoaderType;

public final class AddonLoaderTypes {
  public static LoaderType<SplitModelExtension> BASIC_SPLIT = new LoaderType.Imp<>(
      new Key("bluemapmodeladdon", "ie_basic_split"),
      SplitModelRenderer.TYPE,
      new String[] { "immersiveengineering:basic_split" },
      SplitModelExtension.class);

  public static void register() {
    LoaderType.REGISTRY.register(BASIC_SPLIT);
  }
}
