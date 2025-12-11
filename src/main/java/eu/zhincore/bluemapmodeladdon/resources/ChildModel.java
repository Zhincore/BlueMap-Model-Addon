package eu.zhincore.bluemapmodeladdon.resources;

import java.io.IOException;

import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import de.bluecolored.bluemap.core.resources.adapter.ResourcesGson;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.model.Model;
import lombok.Getter;
import me.owies.bluemapmodelloaders.resources.ExtendedModel;

/**
 * @see https://github.com/Uiniel/BlueMapModelLoaders/blob/main/src/main/java/me/owies/bluemapmodelloaders/resources/composite/CompositeChildModel.java
 */
@JsonAdapter(ChildModel.Adapter.class)
@Getter
public class ChildModel {
  private ChildModel() {
  }

  public ChildModel(Model model, ExtendedModel extendedModel) {
    this.model = model;
    this.extendedModel = extendedModel;
  }

  protected Model model;
  protected ExtendedModel extendedModel;

  public static class Adapter extends TypeAdapter<ChildModel> {

    @Override
    public void write(JsonWriter out, ChildModel value) throws IOException {
      throw new UnsupportedOperationException();
    }

    @Override
    public ChildModel read(JsonReader in) throws IOException {
      JsonObject object = ResourcesGson.INSTANCE.fromJson(in, JsonObject.class);

      ChildModel model = new ChildModel();
      model.model = ResourcesGson.INSTANCE.fromJson(object, Model.class);
      model.extendedModel = ResourcesGson.INSTANCE.fromJson(object, ExtendedModel.class);

      return model;
    }
  }
}
