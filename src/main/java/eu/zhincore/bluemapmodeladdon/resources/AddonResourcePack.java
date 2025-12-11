package eu.zhincore.bluemapmodeladdon.resources;

import java.io.IOException;

import de.bluecolored.bluemap.core.resources.pack.resourcepack.ResourcePack;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.ResourcePackExtension;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.blockstate.BlockStateCondition;

import me.owies.bluemapmodelloaders.resources.ExtendedModel;
import me.owies.bluemapmodelloaders.resources.LoaderType;
import me.owies.bluemapmodelloaders.resources.ModelLoaderResourcePackFactory;

public class AddonResourcePack implements ResourcePackExtension {

  private ResourcePack blueMapResourcePack;

  AddonResourcePack(ResourcePack pack) {
    this.blueMapResourcePack = pack;
  }

  @Override
  public void bake() throws IOException {
    var modelResourcePack = blueMapResourcePack.getExtension(ModelLoaderResourcePackFactory.INSTANCE);

    blueMapResourcePack
        .getBlockStates()
        .values()
        .forEach(blockState -> {
          var variants = blockState.getVariants();
          if (variants == null)
            return;

          for (var variantSet : variants.getVariants()) {
            var isImmersiveMultiblock = false;

            for (var variant : variantSet.getVariants()) {
              ExtendedModel model = modelResourcePack.getModels().get(variant.getModel());
              if (model == null)
                continue;

              LoaderType<?> loader = model.getLoader();
              if (loader != null && loader.equals(AddonLoaderTypes.BASIC_SPLIT)) {
                isImmersiveMultiblock = true;
                blueMapResourcePack.getModels().put(variant.getModel(),
                    model.getExtension(AddonLoaderTypes.BASIC_SPLIT));
                continue;
              }
            }

            if (isImmersiveMultiblock) {
              var condition = BlockStateCondition.and(
                  variantSet.getCondition(),
                  BlockStateCondition.property("multiblockslave", "false"));

              variantSet.setCondition(condition);
            }
          }
        });
  }
}
