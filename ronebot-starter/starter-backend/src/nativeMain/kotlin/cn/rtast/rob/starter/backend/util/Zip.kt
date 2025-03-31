/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/11
 */

package cn.rtast.rob.starter.backend.util

import de.jonasbroeckmann.kzip.Zip
import de.jonasbroeckmann.kzip.compressFrom
import de.jonasbroeckmann.kzip.open
import kotlinx.io.files.Path

fun zipDirectory(inputDir: Path, outputZip: Path) {
    Zip.open(
        path = outputZip,
        mode = Zip.Mode.Write,
        level = Zip.CompressionLevel.BetterCompression
    ).use { zip ->
        zip.compressFrom(inputDir)
    }
}