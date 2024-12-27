module com.ShadowedHunter {
    // Core modules
    requires java.desktop; // For Swing and AWT

    // External dependencies
    requires jlayer; // For MP3 playback
    requires org.slf4j; // For logging API
    requires ch.qos.logback.classic; // Logback implementation (if used)
    requires ch.qos.logback.core;

    // Exporting your package
    exports com.ShadowedHunter; // Export main package for use by other modules
}