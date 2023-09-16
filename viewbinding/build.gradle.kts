import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    `maven-publish`
    signing
}

val versionCode: String = project.findProperty("VERSION_CODE") as String
val versionName = project.findProperty("VERSION_NAME") as String

android {
    namespace = "com.mcgrady.xarch.viewbinding"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        buildConfigField("int", "VERSION_CODE", versionCode)
        buildConfigField("String", "VERSION_NAME", "\"$versionName\"")

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
        buildConfig = true
    }
    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
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

val groupName = project.findProperty("GROUP") as String
val artifactName = project.findProperty("ARTIFACT_NAME") as String

val pomName = project.findProperty("POM_NAME") as String
val pomDescription = project.findProperty("POM_DESCRIPTION") as String
val projectUrl = project.findProperty("POM_URL") as String

val licenseName = project.findProperty("LICENCE_NAME") as String
val licenseUrl = project.findProperty("LICENCE_URL") as String

val developerId = project.findProperty("DEVELOPER_ID") as String
val developerName = project.findProperty("DEVELOPER_NAME") as String
val developerEmail = project.findProperty("DEVELOPER_EMAIL") as String

val scmConnection = project.findProperty("SCM_CONNECTION") as String
val scmDevConnection = project.findProperty("SCM_DEV_CONNECTION") as String

val properties = gradleLocalProperties(rootDir)
val repositoryUsername: String = properties.getProperty("mavenCentralUsername") ?: ""
val repositoryPassword: String = properties.getProperty("mavenCentralPassword") ?: ""

val releasesRepoUrl = project.findProperty("MAVEN_CENTRAL_URL") as String
val snapshotsRepoUrl = project.findProperty("MAVEN_SNAPSHOT_URL") as String
val snapshotIdentifier = "-SNAPSHOT"

project.ext["signing.keyId"] = properties.getProperty("signing.keyId")
project.ext["signing.password"] = properties.getProperty("signing.password")
project.ext["signing.secretKeyRingFile"] = properties.getProperty("signing.secretKeyRingFile")

publishing {
    //配置maven出版物
    publications {
        register<MavenPublication>("release") {
            groupId = groupName
            artifactId = artifactName
            version = versionName

            afterEvaluate {
                from(components["release"])
            }
            //maven包是由pom(Project Object Model)所定义的文件包格式，而maven包集中存放的地方就是maven仓库
            pom {
                name.set(pomName)
                description.set(pomDescription)
                url.set(projectUrl)
                /*properties.set(mapOf(
                    "a" to "value",
                    "b" to "anotherValue"
                ))*/
                licenses {
                    license {
                        name.set(licenseName)
                        url.set(licenseUrl)
                    }
                }
                developers {
                    developer {
                        id.set(developerId)
                        name.set(developerName)
                        email.set(developerEmail)
                    }
                }
                scm {
                    connection.set(scmConnection)
                    developerConnection.set(scmDevConnection)
                    url.set(projectUrl)
                }
            }
        }
        //配置maven仓库
        repositories {
            maven {
                url = uri(
                    if (versionName.endsWith(snapshotIdentifier)) snapshotsRepoUrl else releasesRepoUrl
                )
                credentials {
                    username = repositoryUsername
                    password = repositoryPassword
                }
            }
        }
    }
}

signing {
    sign(publishing.publications["release"])
}

//tasks.register<Zip>("generateRepo") {
//    val publishTask = tasks.named(
//        "publishReleasePublicationToMyRepoRepository",
//        PublishToMavenRepository::class.java
//    )
//    from(publishTask.map { it.repository.url })
//    into("viewbinding")
//    archiveFileName.set("viewbinding.zip")
//}