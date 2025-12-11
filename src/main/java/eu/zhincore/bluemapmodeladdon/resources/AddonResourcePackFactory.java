package eu.zhincore.bluemapmodeladdon.resources;

import de.bluecolored.bluemap.core.resources.pack.resourcepack.ResourcePack;
import de.bluecolored.bluemap.core.util.Key;

public class AddonResourcePackFactory
    implements ResourcePack.Extension<AddonResourcePack> {

  public static final AddonResourcePackFactory INSTANCE = new AddonResourcePackFactory();

  @Override
  public AddonResourcePack create(ResourcePack pack) {
    return new AddonResourcePack(pack);
  }

  @Override
  public Key getKey() {
    return new Key("bluemapmodeladdon", "resourcepack");
  }
}
