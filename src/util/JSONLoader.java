package util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class JSONLoader {
  private static final JSONLoader loader = new JSONLoader();
  
  public static JSONLoader getLoader() {
    return loader;
  }
  
  private Gson g;
  public JSONLoader() {
    g = new Gson();
  }
  
  public <T> T loadJSONFromFile(String path, Class<T> type) {
    try {
      return g.fromJson(new FileReader(path), type);
    } catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
      return null;
    }
  }
}
