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
import io.kritor.common.*
import io.kritor.common.Element.ElementType

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

        fun addMusic(id: String, platform: MusicElement.MusicPlatform): Builder {
            elements.add(element {
                type = ElementType.MUSIC
                this.music = musicElement {
                    this.id = id
                    this.platform = platform
                }
            })
            return this
        }

        fun addCustomMusic(url: String, title: String, author: String, cover: String, audio: String): Builder {
            elements.add(element {
                type = ElementType.MUSIC
                this.music = musicElement {
                    this.custom = customMusicData {
                        this.url = url
                        this.title = title
                        this.author = author
                        this.pic = cover
                        this.audio = audio
                    }
                }
            })
            return this
        }

        fun addLocation(lat: Float, lon: Float, title: String, address: String): Builder {
            elements.add(element {
                type = ElementType.LOCATION
                this.location = locationElement {
                    this.lat = lat
                    this.lon = lon
                    this.title = title
                    this.address = address
                }
            })
            return this
        }

        fun addShare(url: String, title: String, image: String, content: String): Builder {
            elements.add(element {
                type = ElementType.SHARE
                this.share = shareElement {
                    this.title = title
                    this.url = url
                    this.image = image
                    this.content = content
                }
            })
            return this
        }

        fun addWeather(code: String, city: String): Builder {
            elements.add(element {
                type = ElementType.WEATHER
                this.weather = weatherElement {
                    this.code = code
                    this.city = city
                }
            })
            return this
        }

        fun addMarketFace(id: String): Builder {
            elements.add(element {
                type = ElementType.MARKET_FACE
                this.marketFace = marketFaceElement {
                    this.id = id
                }
            })
            return this
        }

        fun addForward(description: String, uniSeq: String, resId: String, summary: String): Builder {
            elements.add(element {
                type = ElementType.FORWARD
                this.forward = forwardElement {
                    this.description = description
                    this.uniseq = uniSeq
                    this.resId = resId
                    this.summary = summary
                }
            })
            return this
        }

        fun addContact(peer: String, scene: Scene): Builder {
            elements.add(element {
                type = ElementType.CONTACT
                this.contact = contactElement {
                    this.peer = peer
                    this.scene = scene
                }
            })
            return this
        }

        fun addJSON(json: String): Builder {
            elements.add(element {
                type = ElementType.JSON
                this.json = jsonElement {
                    this.json = json
                }
            })
            return this
        }

        fun addXML(xml: String): Builder {
            elements.add(element {
                type = ElementType.XML
                this.xml = xmlElement {
                    this.xml = xml
                }
            })
            return this
        }

        fun addFile(url: String, name: String): Builder {
            elements.add(element {
                type = ElementType.FILE
                this.file = fileElement {
                    this.url = url
                    this.name = name
                }
            })
            return this
        }

        fun addMarkdown(markdown: String): Builder {
            elements.add(element {
                type = ElementType.MARKDOWN
                this.markdown = markdownElement {
                    this.markdown = markdown
                }
            })
            return this
        }

        fun addKeyboard(botAppId: Long): Builder {
            elements.add(element {
                type = ElementType.KEYBOARD
                this.keyboard = keyboardElement {
                    this.botAppid = botAppId
                }
            })
            return this
        }

        fun build() = MessageChain(elements)
    }
}