package org.courtesilol.dto;

/**
 *
 * @author Javier
 */
public record SampleTime(String name, String temp_c, Condition condition, String wind_kph, String feelslike_c, String uv) {
    
    @Override
    public String toString() {
        return String.format("""
                             Name: %s
                             Temp centigrad: %s
                             %sWind KPH: %s
                             FeelsLike centigrad: %s
                             UV: %s
                             """, name, temp_c, condition.toString(), wind_kph, feelslike_c, uv);
    }
}
