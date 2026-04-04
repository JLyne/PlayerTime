import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    java
    alias(libs.plugins.pluginYmlPaper)
}

group = "uk.co.notnull.playertime"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

repositories {
	maven {
		url = uri("https://repo.papermc.io/repository/maven-public/")
	}
    mavenLocal()
}

dependencies {
	compileOnly(libs.paperApi)
}

paper {
    main = "uk.co.notnull.playertime.PlayerTime"
    apiVersion = libs.versions.paperApi.get().replace(".build.+", "")
    authors = listOf("Jim (AnEnragedPigeon)")
    description = "Per player time"

    permissions {
        register("ptime.command") {
            description = "Allows use of /ptime"
            default = BukkitPluginDescription.Permission.Default.TRUE
        }
    }
}

tasks {
    compileJava {
        options.compilerArgs.addAll(listOf("-Xlint:all", "-Xlint:-processing"))
        options.encoding = "UTF-8"
    }
}
