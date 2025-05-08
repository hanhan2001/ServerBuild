plugins {
    id("java")
    // shadow
    id("com.github.johnrengelman.shadow").version("8.1.1")
}

group = rootProject.group
version = rootProject.version

repositories {
    mavenLocal()
    mavenCentral()

    // spigot-api
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    // placeholder-api
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
}

dependencies {
    // ServerBuild
    implementation(project(":serverbuild-common"))
    implementation(project(":serverbuild-api"))

    // spigot-api
    compileOnly("org.spigotmc:spigot-api:1.21-R0.1-SNAPSHOT")
    // placeholder-api
    compileOnly("me.clip:placeholderapi:2.11.6")

    // bstats
    implementation("org.bstats:bstats-bukkit:3.0.0")
    // sqlfactory
    implementation("me.xiaoying:sqlfactory:1.0.0")
    // Byte-Buddy
    implementation("net.bytebuddy:byte-buddy:1.15.11")
}

tasks.withType<JavaCompile> {
    sourceCompatibility = "1.8"
    targetCompatibility = "1.8"
}

tasks.build {
    dependsOn(tasks.shadowJar)
}

tasks.processResources {
    val props = mapOf(
        "version" to project.version
    )
    inputs.properties(props)
    filesMatching("plugin.yml") {
        expand(props)
    }
}

tasks.jar {
    enabled = false;
}

tasks.shadowJar {
    dependsOn(":serverbuild-api:shadowJar")

    archiveFileName.set("ServerBuild-V${rootProject.version}.jar")
    archiveClassifier.set("")

    relocate("org.bstats", rootProject.group.toString() + ".metrics")
}