package my.playground.office.util;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.json.JSONArray;
import org.json.JSONObject;

import static java.util.Spliterator.SIZED;

public class JsonUtils {

    public static Stream<JSONObject> stream(JSONArray array) {
        JsonArrayIterator<JSONObject> iter = new JsonArrayIterator<>(array);
        Spliterator<JSONObject> split = Spliterators.spliterator(iter, array.length(), SIZED);
        return StreamSupport.stream(split, false);
    }

}
