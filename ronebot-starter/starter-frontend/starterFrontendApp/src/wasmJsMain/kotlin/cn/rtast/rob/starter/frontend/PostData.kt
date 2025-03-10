/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/11
 */

package cn.rtast.rob.starter.frontend

import kotlinx.browser.document
import org.w3c.dom.HTMLAnchorElement
import org.w3c.dom.url.URL
import org.w3c.dom.url.URLSearchParams
import org.w3c.files.Blob
import org.w3c.xhr.BLOB
import org.w3c.xhr.XMLHttpRequest
import org.w3c.xhr.XMLHttpRequestResponseType

fun submitFormData(data: Map<String, String>) {
    val xhr = XMLHttpRequest()
    xhr.open("POST", "https://test-rob-starter.345867.xyz/api/generate")
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded")
    val params = URLSearchParams()
    for ((key, value) in data) {
        params.append(key, value)
    }
    xhr.send(params.toString())
    xhr.responseType = XMLHttpRequestResponseType.BLOB

    xhr.onload = {
        if (xhr.status.toInt() == 200) {
            val blob = xhr.response as Blob
            val downloadUrl = URL.createObjectURL(blob)
            val a = document.createElement("a") as HTMLAnchorElement
            a.href = downloadUrl
            a.download = "ronebot-example-onebot-v11.zip"
            a.click()
            URL.revokeObjectURL(downloadUrl)
        }
    }
}