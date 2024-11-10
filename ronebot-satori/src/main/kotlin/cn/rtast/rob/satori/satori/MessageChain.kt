/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/10
 */


package cn.rtast.rob.satori.satori

class MessageChain internal constructor(segmentString: StringBuilder) {

    val segment = segmentString.toString()

    class Builder {

        private val sb = StringBuilder()

        fun addText(text: String): Builder {
            sb.append("text")
            return this
        }

        fun addNewLine(): Builder {
            sb.append("\n")
            return this
        }

        fun addAt(id: Long): Builder {
            sb.append("<at id=\"$id\">")
            return this
        }

        fun addA(href: String): Builder {
            sb.append("<a href=\"$href\">")
            return this
        }

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

        fun addAudio(src: String, title: String = "", duration: Int = 0, poster: String = ""): Builder {
            sb.append("<audio src=\"$src\" title=\"$title\" duration=$duration poster=\"$poster\">")
            return this
        }

        fun addVideo(src: String, title: String = "", duration: Int = 0, poster: String = ""): Builder {
            sb.append("<video src=\"$src\" title=\"$title\" duration=$duration poster=\"$poster\">")
            return this
        }

        fun addFile(src: String, title: String = "", duration: Int = 0, poster: String = ""): Builder {
            sb.append("<file src=\"$src\" title=\"$title\" duration=$duration poster=\"$poster\">")
            return this
        }

        fun addCustom(payload: String): Builder {
            sb.append(payload)
            return this
        }

        fun build(): MessageChain {
            return MessageChain(sb)
        }
    }
}