package org.locationtech.rpe;

/**
 * Parameter used for RPE OperationDispatch, can be optional
 */
public class Parameter {
    final String name;
    final Object value;
    final boolean optional;

    public Parameter(String name, Object value, boolean optional) {
        this.name = name;
        this.value = value;
        this.optional = optional;
    }

    public Parameter(String name, Object value) {
        this.name = name;
        this.value = value;
        this.optional = false;
    }
}