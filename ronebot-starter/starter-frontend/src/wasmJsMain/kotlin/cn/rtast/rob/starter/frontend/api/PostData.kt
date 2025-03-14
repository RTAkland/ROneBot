/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/11
 */

package cn.rtast.rob.starter.frontend.api

import org.w3c.dom.url.URLSearchParams
import org.w3c.xhr.BLOB
import org.w3c.xhr.XMLHttpRequest
import org.w3c.xhr.XMLHttpRequestResponseType
import kotlin.collections.iterator

public fun submitFormData(data: Map<String, String>, callback: ((XMLHttpRequest) -> Unit)) {
    val xhr = XMLHttpRequest()
    xhr.open("POST", "https://rob-starter-backend.rtast.cn/api/generate")
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded")
    val params = URLSearchParams()
    for ((key, value) in data) {
        params.append(key, value)
    }
    xhr.send(params.toString())
    xhr.responseType = XMLHttpRequestResponseType.BLOB
    callback(xhr)
}