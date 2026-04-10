plugins {
    id("java")
    id("org.jetbrains.intellij.platform") version "2.10.2"
}

group = "com.xrosstools"
version = "2.7.1"

val sandbox  : String by project

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

// Read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin.html
dependencies {
    intellijPlatform {
        intellijIdeaUltimate("2025.3.3")
        bundledPlugin("com.intellij.java")
    }

    compileOnly(files("$sandbox/com.xrosstools.idea.gef.zip"))
    implementation(files("libs/dom4j-1.6.1.jar"))
}

intellijPlatform {
    instrumentCode = true
    buildSearchableOptions = false

    pluginConfiguration {
        //<idea-version since-build="193.6911.18"/>
        ideaVersion {
            sinceBuild = "183.6156.11"
        }

        changeNotes = """
            <em>2.7.1</em> Fix "Slow operations are prohibited on EDT" warnings.<br>
            <em>2.7.0</em> Support generate behavior and structure unit code.<br>
            <em>2.6.2</em> Migrate to Xross IDEA GEF, optimize code generation and fix reordering bug for container.<br>
            <em>2.6.1</em> Fix editor can not be opened on MAC for IDEA 2024.<br>
            <em>2.6.0</em> Optimize generate factory and unit combination.<br>
            <em>2.5.2</em> Add "-" sign before private method in chose method context menu.<br>
            <em>2.5.1</em> Minor fix for class and method reference implementation.<br>
            <em>2.5.0</em> Support find usage/rename actions for class and method used in model file.<br>
            <em>2.4.1</em> Optimize unit test skeleton.<br>
            <em>2.4.0</em> Support generate helper class and unit test skeleton.<br>
            <em>2.3.0</em> Support delegate unit behavior to noe-default method.<br>
            <em>2.2.1</em> Fix undo and redo bug and optimize branch label location when it is too long.<br>
            <em>2.2.0</em> Add toolbar to support undo and redo.<br>
            <em>2.1.0</em> Support new attribute: "key" for branch and parallel branch. Allowing connection selection via label.<br>
            <em>2.0.7</em> Fix NPE when file extension is empty; Fix ParallelBranch default implementation reference<br>
            <em>2.0.6</em> Allow assign implementation class from not only current project, but also from maven dependency.<br>
            <em>2.0.5</em> Fix deprecated API reference and using build-in icon for select menu.<br>
        """.trimIndent()
    }

    pluginVerification {
        ides {
            // 验证最老和最新的目标版本
            ide("IC-2018.3.6")
            ide("IC-2020.3.4")
            ide("IC-2025.3.3")
        }
    }
}

intellijPlatformTesting {
    runIde {
        register("runWithLocalPlugins") {
            plugins {
                val pluginFiles = file(sandbox).listFiles()
                pluginFiles.forEach { file ->
                    if (!file.name.contains(project.name)) {
                        localPlugin(file.absolutePath)
                    }
                }
            }
        }
    }
}

tasks.named("runIde") {
    dependsOn("runWithLocalPlugins")
}

tasks {
    buildPlugin {
        archiveFileName.set("${project.name}.zip")
    }
}
