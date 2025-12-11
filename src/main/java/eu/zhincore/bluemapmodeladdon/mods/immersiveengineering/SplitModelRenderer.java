package eu.zhincore.bluemapmodeladdon.mods.immersiveengineering;

import de.bluecolored.bluemap.core.logger.Logger;
import de.bluecolored.bluemap.core.map.TextureGallery;
import de.bluecolored.bluemap.core.map.hires.RenderSettings;
import de.bluecolored.bluemap.core.map.hires.TileModelView;
import de.bluecolored.bluemap.core.map.hires.block.BlockRendererType;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.ResourcePack;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.blockstate.Variant;
import de.bluecolored.bluemap.core.util.Key;
import de.bluecolored.bluemap.core.util.math.Color;
import de.bluecolored.bluemap.core.world.block.BlockNeighborhood;
import eu.zhincore.bluemapmodeladdon.resources.AddonLoaderTypes;
import me.owies.bluemapmodelloaders.renderer.ObjModelRenderer;
import me.owies.bluemapmodelloaders.resources.ExtendedModel;
import me.owies.bluemapmodelloaders.resources.ModelLoaderResourcePack;
import me.owies.bluemapmodelloaders.resources.ModelLoaderResourcePackFactory;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.model.Model;

public class SplitModelRenderer extends ObjModelRenderer {

  public static final BlockRendererType TYPE = new BlockRendererType.Impl(new Key("bluemapmodeladdon", "split_model"),
      SplitModelRenderer::new);

  private ResourcePack resourcePack;
  private ModelLoaderResourcePack modelResourcePack;

  public SplitModelRenderer(ResourcePack resourcePack, TextureGallery textureGallery, RenderSettings renderSettings) {
    super(resourcePack, textureGallery, renderSettings);
    this.resourcePack = resourcePack;

    modelResourcePack = resourcePack.getExtension(ModelLoaderResourcePackFactory.INSTANCE);
  }

  @Override
  public void render(BlockNeighborhood block, Variant variant, TileModelView blockModel, Color color) {
    Model modelResource = variant.getModel().getResource(resourcePack.getModels()::get);
    ExtendedModel modelLoaderResource = modelResourcePack.getModels().get(variant.getModel());

    if (modelLoaderResource == null) {
      Logger.global.logWarning("SplitModelRenderer: Model loader resource not found");
      return;
    }

    renderModel(
        block,
        variant,
        modelResource,
        modelLoaderResource.getExtension(AddonLoaderTypes.BASIC_SPLIT).getExtendedModel(),
        blockModel,
        color);
  }
}
