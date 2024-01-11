
plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.0"
    id("org.jetbrains.intellij") version "1.15.0"
}

group = "com.sage.codex"
version = "1.0.0"

dependencies {
    implementation("org.apache.httpcomponents:httpclient:4.5.13")
    implementation("com.alibaba:fastjson:2.0.42")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    implementation("commons-collections:commons-collections:3.2.2")
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("com.vladsch.flexmark:flexmark-all:0.62.2")
    implementation("com.google.guava:guava:28.1-jre")
//    implementation("com.jetbrains.intellij.idea:ideaIC:2023.1.5")



}
repositories {
    mavenLocal {
        setUrl("file:///Users/4paradigm/software/maven/pms-repository")
    };
    mavenCentral();
}

// 提示jar包冲突
//configurations.all {
//    resolutionStrategy.failOnVersionConflict()
//}


intellij {
    version.set("2023.2.5")
    type.set("IU") // IC  IU
    plugins.set(listOf())
}




tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    patchPluginXml {
        sinceBuild.set("221")
        untilBuild.set("233.*")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
//    shadowJar {
//        archiveBaseName.set("sagegpt-codex-idea-plugin")
//        archiveClassifier.set("")
//        archiveVersion.set("v1.0.0")
//    }

}

tasks.register("createDistribution", Zip::class) {
    archiveFileName.set("myapp.zip")
    destinationDirectory.set(file("$buildDir/distribution"))
}

