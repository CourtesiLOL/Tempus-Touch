package org.courtesilol;

/**
 *
 * @author Javier
 */
public class WeatherApiFactory {
    private static final String BASE_URL = "http://api.weatherapi.com/v1/current.xml?";
    private static final String API_KEY = "key=723e052512e44cbda1595152251810";
    
    private static void addParam(StringBuilder currentURL, String param) {
        currentURL.append("&");
        currentURL.append(param);
    }
    
    public static String sampleDataCity(City ciudad) {
        StringBuilder strBuild = new StringBuilder();
        
        strBuild.append(BASE_URL);
        strBuild.append(API_KEY);
        
        addParam(strBuild, "q=");
        
        strBuild.append(ciudad.getLatitud());
        strBuild.append(",");
        strBuild.append(ciudad.getLongitud());
        
        return strBuild.toString();
    }
}
