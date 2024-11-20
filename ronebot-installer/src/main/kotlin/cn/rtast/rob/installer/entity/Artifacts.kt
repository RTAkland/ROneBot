/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/20
 */


package cn.rtast.rob.installer.entity

import com.google.gson.annotations.SerializedName

data class Artifacts(
    val artifacts: List<Artifact>
) {
    data class Artifact(
        @SerializedName("archive_download_url")
        val archiveDownloadUrl: String,
    )
}