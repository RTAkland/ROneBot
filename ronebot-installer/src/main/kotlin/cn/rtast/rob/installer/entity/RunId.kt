/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/20
 */


package cn.rtast.rob.installer.entity

import com.google.gson.annotations.SerializedName

data class RunId(
    @SerializedName("workflow_runs")
    val workflowRuns: List<Run>
) {
    data class Run(
        val event: String,
        val name: String,
        @SerializedName("artifacts_url")
        val artifactsUrl: String,
    )
}