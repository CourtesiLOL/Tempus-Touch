package org.courtesilol.dto;

/**
 *
 * @author Javier
 */
public record Condition(String text, String icon, String code) {
    public static Condition of(String text, String icon, String code) {
        return new Condition(text, ("http:"+icon), code);
    }
    
    @Override
    public String toString() {
        return String.format("""
                             Text: %s
                             Icon: %s
                             Code: %s
                             """, text, icon, code);
    }
}
