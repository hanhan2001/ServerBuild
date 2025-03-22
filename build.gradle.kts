plugins {
    id("java")
}


repositories {
    mavenLocal()
    mavenCentral()
}

group = "me.xiaoying.serverbuild"
version = "1.0.1"

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}