/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/10
 */

@file:Suppress("unused")

package cn.rtast.rob.satori.satori

public class MessageChain internal constructor(segmentString: StringBuilder) {

    public val segment: String = segmentString.toString()

    public class Builder {

        private val sb = StringBuilder()

        /**
         * 纯文本
         */
        public fun addText(text: String): Builder {
            sb.append("text")
            return this
        }

        /**
         * AT
         */
        public fun addAt(id: Long): Builder {
            sb.append("<at id=\"$id\">")
            return this
        }

        /**
         * 超链接
         */
        public fun addA(href: String): Builder {
            sb.append("<a href=\"$href\">")
            return this
        }

        /**
         * 图片
         */
        public fun addImage(
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
        public fun addAudio(src: String, title: String = "", duration: Int = 0, poster: String = ""): Builder {
            sb.append("<audio src=\"$src\" title=\"$title\" duration=$duration poster=\"$poster\">")
            return this
        }

        /**
         * 视频
         */
        public fun addVideo(src: String, title: String = "", duration: Int = 0, poster: String = ""): Builder {
            sb.append("<video src=\"$src\" title=\"$title\" duration=$duration poster=\"$poster\">")
            return this
        }

        /**
         * 文件
         */
        public fun addFile(src: String, title: String = "", duration: Int = 0, poster: String = ""): Builder {
            sb.append("<file src=\"$src\" title=\"$title\" duration=$duration poster=\"$poster\">")
            return this
        }

        /**
         * 斜体
         */
        public fun em(): Builder {
            sb.append("<em>")
            return this
        }

        /**
         * 斜体结束标签
         */
        public fun emEnd(): Builder {
            sb.append("</em>")
            return this
        }

        /**
         * 加粗
         */
        public fun b(): Builder {
            sb.append("<b>")
            return this
        }

        /**
         * 加粗结束标签
         */
        public fun bEnd(): Builder {
            sb.append("</b>")
            return this
        }

        /**
         * 下划线
         */
        public fun u(): Builder {
            sb.append("<u>")
            return this
        }

        /**
         * 下划线结束标签
         */
        public fun uEnd(): Builder {
            sb.append("</u>")
            return this
        }

        /**
         * 删除线
         */
        public fun s(): Builder {
            sb.append("<s>")
            return this
        }

        /**
         * 删除线结束标签
         */
        public fun sEnd(): Builder {
            sb.append("</s>")
            return this
        }

        /**
         * 剧透
         */
        public fun spl(): Builder {
            sb.append("<spl>")
            return this
        }

        /**
         * 剧透结束标签
         */
        public fun splEnd(): Builder {
            sb.append("</spl>")
            return this
        }

        /**
         * 代码片段
         */
        public fun code(): Builder {
            sb.append("<code>")
            return this
        }

        /**
         * 代码片段结束标签
         */
        public fun codeEnd(): Builder {
            sb.append("</code>")
            return this
        }

        /**
         * 上标
         */
        public fun sup(): Builder {
            sb.append("<sup>")
            return this
        }

        /**
         * 上标结束标签
         */
        public fun supEnd(): Builder {
            sb.append("</sup>")
            return this
        }

        /**
         * 下标
         */
        public fun sub(): Builder {
            sb.append("<sub>")
            return this
        }

        /**
         * 下标结束标签
         */
        public fun subEnd(): Builder {
            sb.append("</sub>")
            return this
        }

        /**
         * 换行
         */
        public fun br(): Builder {
            sb.append("<br>")
            return this
        }

        /**
         * 段落
         */
        public fun p(): Builder {
            sb.append("<p>")
            return this
        }

        /**
         * 段落结束标签
         */
        public fun pEnd(): Builder {
            sb.append("</p>")
            return this
        }

        /**
         * 自定义内容
         */
        public fun addCustom(payload: String): Builder {
            sb.append(payload)
            return this
        }

        public fun build(): MessageChain {
            return MessageChain(sb)
        }
    }
}