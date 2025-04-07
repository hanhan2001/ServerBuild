plugins {
    id("java")
}

group = rootProject.group
version = rootProject.version

repositories {
    mavenCentral()
}

dependencies {
}

tasks.withType<JavaCompile> {
    sourceCompatibility = "1.8"
    targetCompatibility = "1.8"
}