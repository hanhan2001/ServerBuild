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
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // serverbuild-common
    implementation(project(":serverbuild-common"))
    // sqlfactory
    implementation("me.xiaoying:sqlfactory:1.0.0")
    // spigot-api
    implementation("org.spigotmc:spigot-api:1.21-R0.1-SNAPSHOT")
    // placeholder-api
    implementation("me.clip:placeholderapi:2.11.6")
    // Byte-Buddy
    implementation("net.bytebuddy:byte-buddy:1.15.11")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    sourceCompatibility = "1.8"
    targetCompatibility = "1.8"
}

tasks.shadowJar {
    from(sourceSets.main.get().output)
    from(sourceSets.main.get().resources)
    from(project(":serverbuild-common").sourceSets.main.get().output)

    include("me/xiaoying/**")
}

tasks.build {
    dependsOn(tasks.shadowJar)
}