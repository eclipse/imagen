package org.locationtech.rpe;

public enum Interpolation implements KeyedHint {
    NEAREST("NearestNeighbor"), 
    BILINEAR("Bilinear"), 
    BICUBIC("Bicubic");

    public static String KEY = "Interpoliation";

    private final String value;

    Interpolation(String value) {
        this.value = value;        
    }

    public String key() {
        return Interpolation.KEY;
    }

    public String value() {
        return this.value;
    }
}