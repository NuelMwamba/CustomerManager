plugins {
    application
    id("org.openjfx.javafxplugin") version "0.1.0"
}
repositories {
    mavenCentral()
}
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}
javafx {
    version = "21.0.1"
    modules = listOf("javafx.controls")
}
application {
    mainClass = "com.example.customermanager.Main"
}
