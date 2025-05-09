plugins {
    `maven-publish`
    `java-library`

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
    // spigot-api
    compileOnly("org.spigotmc:spigot-api:1.21-R0.1-SNAPSHOT")
    // placeholder-api
    compileOnly("me.clip:placeholderapi:2.11.6")
    // sqlfactory
    compileOnly("me.xiaoying:sqlfactory:1.0.0")
    // Byte-Buddy
    compileOnly("net.bytebuddy:byte-buddy:1.15.11")

    // serverbuild-common
    implementation(project(":serverbuild-common"))
}

java {
    withSourcesJar()
    withJavadocJar()

    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc> {
    options {
        encoding = "UTF-8"
        (this as StandardJavadocDocletOptions).addStringOption("Xdoclint:none", "-quiet")
    }
}

tasks.jar {
    enabled = false
}

tasks.shadowJar {
    archiveClassifier.set("")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = rootProject.group.toString()
            artifactId = project.name
            version = rootProject.version.toString()

            artifact(tasks.shadowJar) {
                classifier = ""
            }

            artifact(tasks["sourcesJar"]) {
                classifier = "sources"
            }

            artifact(tasks["javadocJar"]) {
                classifier = "javadoc"
            }
        }
    }

    repositories {
        mavenLocal()
    }
}

tasks.build {
    dependsOn(tasks.shadowJar, tasks.javadoc, tasks["sourcesJar"])
}

tasks.publishToMavenLocal {
    dependsOn(tasks.build)
}