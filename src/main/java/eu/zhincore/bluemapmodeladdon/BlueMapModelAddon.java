package eu.zhincore.bluemapmodeladdon;

import de.bluecolored.bluemap.core.logger.Logger;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.ResourcePack;
import eu.zhincore.bluemapmodeladdon.resources.AddonLoaderTypes;
import eu.zhincore.bluemapmodeladdon.resources.AddonResourcePackFactory;

public class BlueMapModelAddon implements Runnable {

  public void run() {
    register();
    Logger.global.logInfo("BlueMap Model Addon initialized");
    AddonLoaderTypes.register();
  }

  private void register() {
    ResourcePack.Extension.REGISTRY.register(AddonResourcePackFactory.INSTANCE);
  }
}
