@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    `maven-publish`
    signing
}

android {
    namespace = "com.mcgrady.xarch.viewbinding"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "io.github.mcgrady-dev"
            artifactId = "viewbinding"
            version = "1.0.0"

            afterEvaluate {
                from(components["release"])
            }
            pom {

            }
        }
//        debug(MavenPublication) {
//            afterEvaluate {
//                from(components["debug"])
//            }
//            groupId = "io.github.mcgrady-dev"
//            artifactId = "viewbinding-debug"
//            version = "1.0.0"
//        }
        repositories {
            maven {
                name = "nexus"
                url = uri("https://s01.oss.sonatype.org/content/repositories/releases/")
                credentials {
                    username = "mcgrady"
                    password = "mogui911@Maven"
                }
            }
        }
    }
}

signing {
    sign(publishing.publications["release"])
}