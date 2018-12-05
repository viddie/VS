import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;

public class Thermometer {

    private static final String API_KEY = "722920868a0a0266c859a174da690bc1";
    private static String cityId = "2871039";

    public Thermometer(){

    }


    public static void main(String[] args) throws IOException {
        URL jsnUrl = new URL("http://api.openweathermap.org/data/2.5/weather?id=" + cityId + "&APPID=" + API_KEY);
        //JsonFactory factory = new JsonFactory();
        // configure, if necessary:
        //factory.enable(JsonParser.Feature.ALLOW_COMMENTS);
        System.out.println(jsnUrl);
        ObjectMapper mapper = new ObjectMapper();
        //mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        WeatherForecast weatherForecast = null;

        weatherForecast = mapper.readValue(jsnUrl, WeatherForecast.class);
        System.out.println("Min. Temp.: " + weatherForecast.getMain().getTempMin());
        System.out.println("Max. Temp.: " + weatherForecast.getMain().getTempMax());
    }
}
