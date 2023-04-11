
import org.gradle.api.Project
import java.io.ByteArrayOutputStream

data class Version(
    var major: Int?,
    var minor: Int?,
    var patch: Int?,
    var subPatch: Int?
)

fun Project.generateVersionName(): String {
    val (tag) = gitDescribe()
    val (major, minor, patch) = tag.split(".").map { it.toInt() }
    return "$major.$minor.${patch}"
}

fun Project.generateVersionCode(): Int {
    val (tag, commits) = gitDescribe()
    val (major, minor, patch, subPatch) = tag.split(".").map { it.toInt() }.run {
        Version(getOrNull(0), getOrNull(1), getOrNull(2), getOrNull(3))
    }
    var code = major?.let { it * 1000000 } ?: 0
    code += (minor?.let { it * 10000 } ?: 0)
    code += (patch?.let { it * 100 } ?: 0)
    code += subPatch ?: 0
    code += commits?.toInt() ?: 0
    return code
}

private fun Project.gitDescribe(): Pair<String, String?> {
    val byte = ByteArrayOutputStream()
    project.exec {
        commandLine = listOf("git", "describe")
        standardOutput = byte
    }
    val result = String(byte.toByteArray()).trim().split("-")
    return Pair(result.first(), result.getOrNull(1))
}