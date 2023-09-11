plugin {
    'maven-publish'
    signing
}

afterEvaluate {
    publishing {
        publications {
            register<MavenPublication>("mavenRelease") {
                groupId = "com.mcgrady.xarch"
                artifactId = "viewbinding"
                version = "0.0.1"

                afterEvaluate {
                    from(components["release"])
                }
            }
            repositories {
                maven {
                    name = "myrepo"
                    url = uri("${project.buildDir}/repo")
                }
            }
        }
    }
}