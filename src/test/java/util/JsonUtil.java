package util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.spi.json.GsonJsonProvider;

import java.io.File;
import java.io.IOException;

public class JsonUtil {
    public JsonUtil(){}

    public JsonObject fetchJsonObjectFromFile(File jsonFile, String jsonPath) {
        JsonArray ja = null;

        try {
            Configuration conf = Configuration.builder().jsonProvider(new GsonJsonProvider()).build();
            ja = JsonPath.using(conf).parse(jsonFile).read(jsonPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (JsonObject) ja.get(0);
    }

    public String fetchJsonStringFromFile(File jsonFile, String jsonPath) throws IOException {
        Configuration conf = Configuration.builder().jsonProvider(new GsonJsonProvider()).build();
        return JsonPath.using(conf).parse(jsonFile).read(jsonPath).toString();
    }

    public String getValueFromJsonObject(JsonObject jo, String key) {
        CustomStringUtil customStringUtil = new CustomStringUtil();
        return customStringUtil.getPureString(jo.getAsJsonObject().get(key).toString());
    }
}
