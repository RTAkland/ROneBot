/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/10
 */

@file:Suppress("unused")

package cn.rtast.rob.satori.satori

class MessageChain internal constructor(segmentString: StringBuilder) {

    val segment = segmentString.toString()

    class Builder {

        private val sb = StringBuilder()

        /**
         * 纯文本
         */
        fun addText(text: String): Builder {
            sb.append("text")
            return this
        }

        /**
         * AT
         */
        fun addAt(id: Long): Builder {
            sb.append("<at id=\"$id\">")
            return this
        }

        /**
         * 超链接
         */
        fun addA(href: String): Builder {
            sb.append("<a href=\"$href\">")
            return this
        }

        /**
         * 图片
         */
        fun addImage(
            src: String,
            title: String = "",
            duration: Int = 0,
            poster: String = "",
            base64: Boolean = false
        ): Builder {
            sb.append(
                "<img src=\"${if (base64) "base64://$src" else src}\" " +
                        "title=$title> " +
                        "duration=$duration " +
                        "poster=\"$poster\""
            )
            return this
        }

        /**
         * 音频
         */
        fun addAudio(src: String, title: String = "", duration: Int = 0, poster: String = ""): Builder {
            sb.append("<audio src=\"$src\" title=\"$title\" duration=$duration poster=\"$poster\">")
            return this
        }

        /**
         * 视频
         */
        fun addVideo(src: String, title: String = "", duration: Int = 0, poster: String = ""): Builder {
            sb.append("<video src=\"$src\" title=\"$title\" duration=$duration poster=\"$poster\">")
            return this
        }

        /**
         * 文件
         */
        fun addFile(src: String, title: String = "", duration: Int = 0, poster: String = ""): Builder {
            sb.append("<file src=\"$src\" title=\"$title\" duration=$duration poster=\"$poster\">")
            return this
        }

        /**
         * 斜体
         */
        fun em(): Builder {
            sb.append("<em>")
            return this
        }

        /**
         * 斜体结束标签
         */
        fun emEnd(): Builder {
            sb.append("</em>")
            return this
        }

        /**
         * 加粗
         */
        fun b(): Builder {
            sb.append("<b>")
            return this
        }

        /**
         * 加粗结束标签
         */
        fun bEnd(): Builder {
            sb.append("</b>")
            return this
        }

        /**
         * 下划线
         */
        fun u(): Builder {
            sb.append("<u>")
            return this
        }

        /**
         * 下划线结束标签
         */
        fun uEnd(): Builder {
            sb.append("</u>")
            return this
        }

        /**
         * 删除线
         */
        fun s(): Builder {
            sb.append("<s>")
            return this
        }

        /**
         * 删除线结束标签
         */
        fun sEnd(): Builder {
            sb.append("</s>")
            return this
        }

        /**
         * 剧透
         */
        fun spl(): Builder {
            sb.append("<spl>")
            return this
        }

        /**
         * 剧透结束标签
         */
        fun splEnd(): Builder {
            sb.append("</spl>")
            return this
        }

        /**
         * 代码片段
         */
        fun code(): Builder {
            sb.append("<code>")
            return this
        }

        /**
         * 代码片段结束标签
         */
        fun codeEnd(): Builder {
            sb.append("</code>")
            return this
        }

        /**
         * 上标
         */
        fun sup(): Builder {
            sb.append("<sup>")
            return this
        }

        /**
         * 上标结束标签
         */
        fun supEnd(): Builder {
            sb.append("</sup>")
            return this
        }

        /**
         * 下标
         */
        fun sub(): Builder {
            sb.append("<sub>")
            return this
        }

        /**
         * 下标结束标签
         */
        fun subEnd(): Builder {
            sb.append("</sub>")
            return this
        }

        /**
         * 换行
         */
        fun br(): Builder {
            sb.append("<br>")
            return this
        }

        /**
         * 段落
         */
        fun p(): Builder {
            sb.append("<p>")
            return this
        }

        /**
         * 段落结束标签
         */
        fun pEnd(): Builder {
            sb.append("</p>")
            return this
        }

        /**
         * 自定义内容
         */
        fun addCustom(payload: String): Builder {
            sb.append(payload)
            return this
        }

        fun build(): MessageChain {
            return MessageChain(sb)
        }
    }
}