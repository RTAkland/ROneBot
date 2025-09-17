# 对接API

生成器API端点: `/api/generate` 使用 POST 方法提交JSON

JSON结构体如下:

```kotlin
public data class GeneratorProperty(
    val protocol: String,
    val groupId: String,
    val plugins: List<GradlePlugins>,
    val projectName: String,
    val packageName: String,
    val robVersion: String,
    val gradleVersion: String,
    val kotlinVersion: String,
    val language: Language,
    val isMultiplatform: Boolean,
    val platforms: List<ROneBotPlatform>,
    val javaVersion: String? = null,
)

public enum class Language(public val languageName: String, public val colorClass: String) {
    Kotlin("kotlin", "kotlin-color"), Java("java", "java-color");

    public companion object {
        public fun fromName(name: String): Language {
            return when (name.lowercase()) {
                "kotlin" -> Kotlin
                "java" -> Java
                else -> Kotlin
            }
        }
    }
}

public enum class ROneBotPlatform(
    public val targetName: String,
    public val targetDisplayName: String,
    public val replaceName: String,
) {
    Jvm("jvm", "Jvm", "{{JVM}}"),
    LinuxX64("linuxX64", "Linux", "{{LINUX}}"),
    MingwX64("mingwX64", "WindowsX64", "{{MINGW}}"),
    LinuxArm64("linuxArm64", "Linux ARM64", "LINUX_ARM"),
    MacOSArm64("macosArm64", "MacOS ARM64", "{{MACOS_ARM}}");

    public companion object {
        public fun fromString(string: String): ROneBotPlatform {
            return when (string) {
                Jvm.targetName -> Jvm
                LinuxX64.targetName -> LinuxX64
                MingwX64.targetName -> MingwX64
                else -> throw IllegalStateException("没有这种类型的平台: $string")
            }
        }
    }
}
```

下面给出示例JSON文本

```json
{
    "groupId": "com.example.project",
    "plugins": [
        "Shadow",
        "Java",
        "KotlinJvm",
        "KotlinMultiplatform"
    ],
    "projectName": "TestProject",
    "packageName": "com.example.test",
    "robVersion": "1.0.0",
    "gradleVersion": "8.2",
    "kotlinVersion": "1.9.0",
    "language": "Kotlin",
    "isMultiplatform": true,
    "platforms": [
        "Jvm",
        "LinuxX64",
        "MingwX64",
        "LinuxArm64",
        "MacOSArm64"
    ],
    "javaVersion": "17",
    "protocol": "milky"
}
```

> `javaVersion`字段可空

# 响应结构体

```kotlin
public data class GeneratedFileResponse(
    val filename: String,
    val content: String,
    val fileType: GeneratedFileType,
    val path: String = ""
)

public enum class GeneratedFileType {
    ByteArray, PlainText
}
```

> `path`字段保留备用, 始终为空字符串, `content`为base64编码后的文件内容, `fileType`的两个
> 枚举类型分别对应了文件的类型, 可以是二进制文件或者纯文本文件