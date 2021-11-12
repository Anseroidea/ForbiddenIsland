plugins {
    java
    id("org.openjfx.javafxplugin") version "0.0.10"
}

group = "org.ansere"
version = ""

repositories {
    mavenCentral()
}

javafx {
    version = "17"
    modules("javafx.controls", "javafx.fxml", "javafx.base", "javafx.graphics", "javafx.media", "javafx.web", "javafx.swing")
}

dependencies {
    implementation("org.apache.pdfbox:pdfbox:3.0.0-alpha2")
    implementation("eu.hansolo:Medusa:5.7")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}