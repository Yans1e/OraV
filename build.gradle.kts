import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.20-RC2"
    id("io.papermc.paperweight.userdev") version "1.5.5"
    id("xyz.jpenilla.run-paper") version "2.2.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "de.yansie"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://papermc.io/repo/repository/maven-public/net/dv8tion/JDA/5.0.0-beta.2")
    maven("https://repo.codemc.io/repository/maven-snapshots/")

}

dependencies {
    paperweightDevelopmentBundle("io.papermc.paper:dev-bundle:1.20.1-R0.1-SNAPSHOT")
    implementation("net.axay:kspigot:1.20.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation ("fr.mrmicky:fastboard:2.0.1")
    implementation("net.dv8tion:JDA:5.0.0-beta.16")
}

tasks {
    build {
        dependsOn(reobfJar)
    }
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
    runPaper {
        version = "1.20.1"
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

