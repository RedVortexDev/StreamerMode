plugins {
    alias(libs.plugins.loom)
    `maven-publish`
}

val modVersion = providers.gradleProperty("modVersion").get()
val mavenGroup = providers.gradleProperty("mavenGroup").get()
val archivesBaseName = providers.gradleProperty("archivesBaseName").get()

if (version == Project.DEFAULT_VERSION) {
    version = modVersion
}
group = mavenGroup

base {
    archivesName = archivesBaseName
}

repositories {
    maven("https://maven.isxander.dev/releases") {
        name = "Xander Maven"
    }
    maven("https://maven.terraformersmc.com/") {
        name = "Terraformers"
    }
}

dependencies {
    minecraft(libs.minecraft)
    implementation(libs.fabric.loader)
    implementation(libs.fabric.api)
    implementation(libs.yacl)
    implementation(libs.modmenu)

    implementation(libs.adventure.platform.fabric)
    include(libs.adventure.platform.fabric)

    api(libs.twitch4j) {
        exclude(group = "org.slf4j", module = "slf4j-api")
    }
    include(libs.twitch4j)
    include(libs.bundles.twitch4j)
    include(libs.bundles.twitch4j.libraries)
    include(libs.fabric.language.kotlin)
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(25)
    withSourcesJar()
}

tasks {
    withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
        options.release = 25
    }

    processResources {
        val properties = mapOf(
            "version" to project.version,
            "minecraft_version" to libs.versions.minecraft.get(),
            "loader_version" to libs.versions.fabric.loader.get(),
        )
        inputs.properties(properties)
        filteringCharset = "UTF-8"
        filesMatching("fabric.mod.json") {
            expand(properties)
        }
    }

    jar {
        val baseName = archiveBaseName.get()
        from("LICENSE") {
            rename { "${it}_$baseName" }
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = archivesBaseName
            from(components["java"])
        }
    }
}
