package eu.zhincore.bluemapmodeladdon.mods.immersiveengineering;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import org.jetbrains.annotations.Nullable;

import com.google.gson.annotations.SerializedName;

import de.bluecolored.bluemap.core.resources.ResourcePath;
import de.bluecolored.bluemap.core.resources.pack.ResourcePool;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.ResourcePack;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.model.Model;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.model.TextureVariable;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.texture.Texture;
import eu.zhincore.bluemapmodeladdon.resources.AddonLoaderTypes;
import eu.zhincore.bluemapmodeladdon.resources.ChildModel;
import lombok.Getter;
import me.owies.bluemapmodelloaders.resources.ExtendedModel;
import me.owies.bluemapmodelloaders.resources.ModelExtension;
import me.owies.bluemapmodelloaders.resources.ModelLoaderResourcePack;

@Getter
public class SplitModelExtension extends Model implements ModelExtension {
  @SerializedName("inner_model")
  private ChildModel innerModel;

  @Override
  public synchronized void applyParent(ExtendedModel parent) {
    SplitModelExtension parentObj = parent.getExtension(AddonLoaderTypes.BASIC_SPLIT);

    if (this.innerModel == null) {
      this.innerModel = parentObj.innerModel;
    }
  }

  @Override
  public synchronized void applyParent(ResourcePool<Model> modelPool) {
    innerModel.getModel().applyParent(modelPool);
  }

  @Override
  public Map<String, TextureVariable> getTextures() {
    return innerModel.getModel().getTextures();
  }

  @Override
  public @Nullable ResourcePath<Model> getParent() {
    return innerModel.getModel().getParent();
  }

  @Override
  public synchronized void optimize(ResourcePool<Texture> texturePool) {
    innerModel.getModel().optimize(texturePool);
  }

  public ExtendedModel getExtendedModel() {
    return innerModel.getExtendedModel();
  }

  @Override
  public void bake(ResourcePack blueMapResourcePack, ModelLoaderResourcePack modelLoaderResourcePack) {
    if (this.innerModel == null) {
      return;
    }

    var model = innerModel.getModel();
    var extendedModel = innerModel.getExtendedModel();

    super.applyParent(blueMapResourcePack.getModels());

    model.optimize(blueMapResourcePack.getTextures());

    model.applyParent(blueMapResourcePack.getModels());
    extendedModel.applyParent(modelLoaderResourcePack);

    model.calculateProperties(blueMapResourcePack.getTextures());
    extendedModel.bake(blueMapResourcePack, modelLoaderResourcePack);
  }

  @Override
  public Stream<ResourcePath<Texture>> getUsedTextures() {
    if (this.innerModel == null) {
      return Stream.empty();
    }
    return Stream.concat(
        innerModel.getExtendedModel().getUsedTextures(),
        innerModel.getModel()
            .getTextures()
            .values()
            .stream()
            .map(TextureVariable::getTexturePath)
            .filter(Objects::nonNull));
  }
}
