/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/29
 */


package cn.rtast.rob.kritor.util.msg

import cn.rtast.rob.enums.QQFace
import com.google.protobuf.ByteString
import io.kritor.common.Element
import io.kritor.common.Element.ElementType
import io.kritor.common.atElement
import io.kritor.common.element
import io.kritor.common.faceElement
import io.kritor.common.imageElement
import io.kritor.common.replyElement
import io.kritor.common.textElement
import java.util.Base64

class MessageChain internal constructor(val elements: List<Element>) {

    class Builder {
        private val elements = mutableListOf<Element>()

        fun addText(text: String): Builder {
            elements.add(element {
                type = ElementType.TEXT
                this.text = textElement { this.text = text }
            })
            return this
        }

        fun addFace(id: QQFace): Builder {
            elements.add(
                element {
                    type = ElementType.FACE
                    this.face = faceElement { this.id = 1 }
                }
            )
            return this
        }

        fun addAt(userId: Long): Builder {
            elements.add(
                element {
                    type = ElementType.AT
                    this.at = atElement { this.uin = userId }
                }
            )
            return this
        }

        fun addReply(id: Long): Builder {
            elements.add(
                element {
                    type = ElementType.REPLY
                    this.reply = replyElement { this.messageId = id.toString() }
                }
            )
            return this
        }

        fun addImage(image: String, base64: Boolean = false): Builder {
            val ele = if (!base64) element {
                type = ElementType.IMAGE
                this.image = imageElement { this.fileUrl = image }
            } else {
                element {
                    type = ElementType.IMAGE
                    this.image = imageElement { this.file = ByteString.copyFrom(Base64.getDecoder().decode(image)) }
                }
            }
            elements.add(ele)
            return this
        }

        fun build() = MessageChain(elements)
    }
}