/*
 * Copyright © 2025 RTAkland
 * Date: 9/27/25, 3:28 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.buildSrc

import cn.rtast.rob.buildSrc.util.Http
import com.google.gson.annotations.SerializedName

data class ListArtifact(
    @SerializedName("total_count")
    val totalCount: Int,
    val artifacts: List<GithubArtifact>
) {
    data class GithubArtifact(
        val name: String,
        @SerializedName("archive_download_url")
        val downloadUrl: String,
        @SerializedName("workflow_run")
        val workflow: Workflow
    )

    data class Workflow(
        val id: Long
    )
}

const val LIST_ARTIFACT_BASE_URL = "https://api.github.com/repos/LagrangeDev/LagrangeV2/actions/artifacts"


