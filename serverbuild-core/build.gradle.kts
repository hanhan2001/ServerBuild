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
    // serverbuild-api
    implementation(project(":serverbuild-api"))
    // sqlfactory
    implementation("me.xiaoying:sqlfactory:1.0.0")
    // spigot-api
    implementation("org.spigotmc:spigot-api:1.21-R0.1-SNAPSHOT")
    // placeholder-api
    implementation("me.clip:placeholderapi:2.11.6")
    // bstats
    implementation("org.bstats:bstats-bukkit:3.0.0")
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
    relocate("org.bstats", rootProject.group.toString() + ".metrics")

    from(sourceSets.main.get().output)
    from(project(":serverbuild-api").sourceSets.main.get().output)
    from(project(":serverbuild-common").sourceSets.main.get().output)

    dependencies {
        exclude(dependency("me.clip:placeholderapi:2.11.6"))
        exclude(dependency("org.spigotmc:spigot-api:1.21-R0.1-SNAPSHOT"))
    }

    exclude("org/checkerframework/**")
    exclude("org/joml/**")
    exclude("org/yaml/**")
    exclude("org/sqlite/**")
    exclude("org/bukkit/**")
    exclude("org/jetbrains/**")
    exclude("org/intellij/**")
    exclude("org/spigotmc/**")
    exclude("net/md_5/**")
    exclude("net/kyori/**")
    exclude("com/**")
    exclude("javax/**")
    exclude("google/**")
    exclude("mojang-translations/**")
    exclude("INFO_BIN")
    exclude("INFO_SRC")
    exclude("LICENSE")
    exclude("README")
    exclude("META-INF/maven/**")
    exclude("META-INF/versions/**")
    exclude("META-INF/proguard/**")
    exclude("META-INF/services/**")
    exclude("META-INF/native-image/**")
    exclude("META-INF/NOTICE")
    exclude("META-INF/LICENSE")
    exclude("META-INF/licenses/**")
    exclude("META-INF/LICENSE.txt")
    exclude("META-INF/LGPL2.1")
    exclude("sqlite-jdbc.properties")
    exclude("classpath.index")
}

tasks.build {
    dependsOn(tasks.shadowJar)
}