plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

tasks {
    val ensureSecretsExist by registering {
        val secretFile = File("$projectDir/src/main/kotlin/Secrets.kt")
        description = "Ensures that '$secretFile' exists"

        outputs.file(secretFile)
        doFirst {
            if (!secretFile.exists()) {
                secretFile.writeText(
                    """
object Secrets {
    object Sonatype {
        const val USER = ""
        const val API_KEY = ""
    }
}

""".trimIndent()
                )
            }
        }
    }
    named("assemble") { dependsOn(ensureSecretsExist) }
}
