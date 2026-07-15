package com.doryann.flowpilot.shared;

public enum AuthProvider {
    GOOGLE,
    MICROSOFT,
    YAHOO;

    public static AuthProvider fromRegistrationId(String registrationId) {
        return switch (registrationId.toLowerCase()) {
            case "google" -> GOOGLE;
            case "microsoft" -> MICROSOFT;
            case "yahoo" -> YAHOO;
            default -> throw new IllegalArgumentException(
                    "Unsupported auth provider: " + registrationId
            );
        };
    }
}