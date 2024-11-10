/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/29
 */

@file:Suppress("unused")

package cn.rtast.rob.kritor.kritor

import cn.rtast.rob.enums.QQFace
import cn.rtast.rob.util.decodeToByteArray
import com.google.protobuf.ByteString
import io.kritor.common.Element
import io.kritor.common.Element.ElementType
import io.kritor.common.atElement
import io.kritor.common.element
import io.kritor.common.faceElement
import io.kritor.common.imageElement
import io.kritor.common.replyElement
import io.kritor.common.textElement
import io.kritor.common.videoElement
import io.kritor.common.voiceElement

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
                    this.image = imageElement { this.file = ByteString.copyFrom(image.decodeToByteArray()) }
                }
            }
            elements.add(ele)
            return this
        }

        fun addVoice(url: String): Builder {
            elements.add(element {
                type = ElementType.VOICE
                this.voice = voiceElement { this.fileUrl = url }
            })
            return this
        }

        fun addVideo(url: String): Builder {
            elements.add(element {
                type = ElementType.VIDEO
                this.video = videoElement { this.fileUrl = url }
            })
            return this
        }

        fun addRps(): Builder {
            elements.add(element {
                type = ElementType.RPS
            })
            return this
        }

        fun addPoke(id: Int): Builder {
            elements.add(element {
                type = ElementType.DICE
            })
            return this
        }

        fun addMusic(): Builder {
            return this
        }

        fun addLocation(): Builder {
            return this
        }

        fun addShare(): Builder {
            return this
        }

        fun addWeather(): Builder {
            return this
        }

        fun addMarketFace(): Builder {
            return this
        }

        fun addForward(): Builder {
            return this
        }

        fun addContact(): Builder {
            return this
        }

        fun addJSON(): Builder {
            return this
        }

        fun addXML(): Builder {
            return this
        }

        fun addFile(): Builder {
            return this
        }

        fun addMarkdown(): Builder {
            return this
        }

        fun addKeyboard(): Builder {
            return this
        }

        fun build() = MessageChain(elements)
    }
}