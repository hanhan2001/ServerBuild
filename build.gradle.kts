plugins {
    id("java")
}

allprojects {
    group = "me.xiaoying.serverbuild"
    version = "1.0.2"
}

subprojects {
    apply(plugin = "java")

    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    repositories {
        mavenLocal()
        mavenCentral()

        // PlaceholderAPI
        maven("https://repo.papermc.io/repository/maven-public/")
        // spigot-api
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }

    dependencies {
        // spigot-api
        compileOnly("org.spigotmc:spigot-api:1.21-R0.1-SNAPSHOT")
        // placeholder-api
        compileOnly("me.clip:placeholderapi:2.11.6")
    }
}