package eu.zhincore.bluemapmodeladdon.mods.immersiveengineering;

import de.bluecolored.bluemap.core.logger.Logger;
import de.bluecolored.bluemap.core.map.TextureGallery;
import de.bluecolored.bluemap.core.map.hires.RenderSettings;
import de.bluecolored.bluemap.core.map.hires.TileModelView;
import de.bluecolored.bluemap.core.map.hires.block.BlockRendererType;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.ResourcePack;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.blockstate.Variant;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.model.Model;
import de.bluecolored.bluemap.core.util.Key;
import de.bluecolored.bluemap.core.util.math.Color;
import de.bluecolored.bluemap.core.util.math.MatrixM4f;
import de.bluecolored.bluemap.core.world.block.BlockNeighborhood;
import eu.zhincore.bluemapmodeladdon.resources.AddonLoaderTypes;
import me.owies.bluemapmodelloaders.renderer.CompositeModelRenderer;
import me.owies.bluemapmodelloaders.resources.ExtendedModel;
import me.owies.bluemapmodelloaders.resources.ModelLoaderResourcePack;
import me.owies.bluemapmodelloaders.resources.ModelLoaderResourcePackFactory;

public class MirrorModelRenderer extends CompositeModelRenderer {
  public static final BlockRendererType TYPE = new BlockRendererType.Impl(new Key("bluemapmodeladdon", "mirror_model"),
      MirrorModelRenderer::new);

  private ResourcePack resourcePack;
  private ModelLoaderResourcePack modelResourcePack;

  public MirrorModelRenderer(ResourcePack resourcePack, TextureGallery textureGallery, RenderSettings renderSettings) {
    super(resourcePack, textureGallery, renderSettings);
    this.resourcePack = resourcePack;

    modelResourcePack = resourcePack.getExtension(ModelLoaderResourcePackFactory.INSTANCE);
  }

  @Override
  public void renderModel(BlockNeighborhood block, Variant variant, Model modelResource, ExtendedModel modelLoaderResource, TileModelView blockModel, Color color) {
    SplitModelExtension modelExtension = modelLoaderResource.getExtension(AddonLoaderTypes.BASIC_SPLIT);

    int modelStart = blockModel.getStart();
    renderCompositeChildModel(
            block,
            variant,
            modelResource,
            modelLoaderResource,
            blockModel,
            color,
            modelExtension.getInnerModel()
    );
    blockModel.initialize(modelStart);

    MatrixM4f mirrorMatrix = new MatrixM4f();
    mirrorMatrix.translate(-0.5f, -0.5f, -0.5f);
    if (variant.getY() == 90 || variant.getY() == 270) {
      mirrorMatrix.scale(1f, 1f, -1f);
    } else {
      mirrorMatrix.scale(-1f, 1f, 1f);
    }
    mirrorMatrix.translate(0.5f, 0.5f, 0.5f);

    blockModel.transform(mirrorMatrix);
    // All the faces are inside out after scaling with a negative number so invert them:
    blockModel.invertOrientation();
  }
}
