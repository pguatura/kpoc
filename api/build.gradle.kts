import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("kotlinx-serialization")
}



kotlin {
    //select iOS target platform depending on the Xcode environment variables
    val iOSTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget =
        if (System.getenv("SDK_NAME")?.startsWith("iphoneos") == true)
            ::iosArm64
        else
            ::iosX64

    iOSTarget("ios") {
        binaries {
            framework {
                baseName = "api" +
                        ""
            }
        }
    }

    jvm("android")

    sourceSets["commonMain"].dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-common")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:1.3.0")
        implementation("io.ktor:ktor-client-core:1.2.4")
        implementation("io.ktor:ktor-client-json:1.2.4")
        implementation("io.ktor:ktor-client-gson:1.2.4")
        implementation("io.ktor:ktor-client-logging:1.2.4")
    }

    sourceSets["androidMain"].dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib")
        implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.2.2")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.11.1")
        implementation ("io.ktor:ktor-client-android:1.2.4")
        implementation("io.ktor:ktor-client-logging-jvm:1.2.4")
    }

    sourceSets["iosMain"].dependencies {
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-native:1.2.2")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-native:0.11.1")
        implementation("io.ktor:ktor-client-ios:1.2.4")
        implementation("io.ktor:ktor-client-json-native:1.2.4")
        implementation("io.ktor:ktor-client-logging-native:1.2.4")
    }

}



val packForXcode by tasks.creating(Sync::class) {
    group = "Build"
    val targetDir = File(buildDir, "xcode-frameworks")

    /// selecting the right configuration for the iOS
    /// framework depending on the environment
    /// variables set by Xcode build
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val framework = kotlin.targets
        .getByName<KotlinNativeTarget>("ios")
        .binaries.getFramework(mode)
    inputs.property("mode", mode)
    dependsOn(framework.linkTask)

    from({ framework.outputDirectory })
    into(targetDir)

    /// generate a helpful ./gradlew wrapper with embedded Java path
    doLast {
        val gradlew = File(targetDir, "gradlew")
        gradlew.writeText("#!/bin/bash\n"
                + "export 'JAVA_HOME=${System.getProperty("java.home")}'\n"
                + "cd '${rootProject.rootDir}'\n"
                + "./gradlew \$@\n")
        gradlew.setExecutable(true)
    }
}

tasks.getByName("build").dependsOn(packForXcode)