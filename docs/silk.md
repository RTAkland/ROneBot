# Silk Codec

> 简单封装了Silk codec

## 解码

```kotlin
fun main() {
    val inputStream: InputStream = ...
    val inputStreamSilkBytes: ByteArray = inputStream.decodeToSilk()

    val file: File = File("path/to/silk")
    val fileSilkBytes: ByteArray = file.decodeToSilk()
}
```

## 编码

```kotlin
fun main() {
    val sampleRate = 8000  // 采样率
    val inputStream: InputStream = ...
    val inputStreamSilkBytes: ByteArray = inputStream.encodeToSilk(sampleRate)

    val file: File = File("path/to/silk")
    val fileSilkBytes: ByteArray = file.encodeToSilk(sampleRate)
}
```
