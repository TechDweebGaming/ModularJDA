package io.github.techdweebgaming.modularjda.api.exceptions;

public class DefaultNotFoundException extends Exception {

    public DefaultNotFoundException() {
        super("Default config value not found.");
    }

}
