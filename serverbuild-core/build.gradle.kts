plugins {
    id("java")
    // shadow
    id("com.github.johnrengelman.shadow").version("8.1.1")
}

dependencies {
    // lombok
    compileOnly("org.projectlombok:lombok:1.18.38")
    annotationProcessor("org.projectlombok:lombok:1.18.38")

    // ServerBuild
    implementation(project(":serverbuild-common"))
    implementation(project(":serverbuild-api"))

    // bstats
    implementation("org.bstats:bstats-bukkit:3.0.0")
    // sqlfactory
    implementation("me.xiaoying:sqlfactory:1.0.0")
    // Byte-Buddy
    implementation("net.bytebuddy:byte-buddy:1.15.11")
}

tasks {
    processResources {
        val props = mapOf(
            "version" to project.version
        )
        inputs.properties(props)
        filesMatching("plugin.yml") {
            expand(props)
        }
    }

    build {
        dependsOn(shadowJar)
    }

    jar {
        enabled = false;
    }

    shadowJar {
        dependsOn(":serverbuild-api:shadowJar")

        archiveFileName.set("ServerBuild-V${rootProject.version}.jar")
        archiveClassifier.set("")

        relocate("org.bstats", rootProject.group.toString() + ".metrics")
    }
}