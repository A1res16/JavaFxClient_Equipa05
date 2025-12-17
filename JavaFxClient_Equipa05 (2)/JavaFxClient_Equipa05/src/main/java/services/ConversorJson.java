package services;
/**@author aires
 * @version 1
 * 
 */
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Collections;

public class ConversorJson 
{
    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> List<T> parseList(String json, Class<T> clazz) 
    {
        try 
        {
            if (json == null || json.trim().isEmpty() || json.startsWith("ERROR")) 
            {
                return Collections.emptyList();
            }
            return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public static String toJson(Object obj) 
    {
        try 
        {
            return mapper.writeValueAsString(obj);
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return "{}";
        }
    }
}
