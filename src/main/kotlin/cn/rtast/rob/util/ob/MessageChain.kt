/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/2
 */


package cn.rtast.rob.util.ob

import cn.rtast.rob.segment.AT
import cn.rtast.rob.segment.BaseSegment
import cn.rtast.rob.segment.Contact
import cn.rtast.rob.segment.CustomMusicShare
import cn.rtast.rob.segment.DICE
import cn.rtast.rob.segment.Face
import cn.rtast.rob.segment.Image
import cn.rtast.rob.segment.JSON
import cn.rtast.rob.segment.Location
import cn.rtast.rob.segment.MusicShare
import cn.rtast.rob.segment.PlainText
import cn.rtast.rob.segment.Poke
import cn.rtast.rob.segment.RPS
import cn.rtast.rob.segment.Record
import cn.rtast.rob.segment.Reply
import cn.rtast.rob.segment.Shake
import cn.rtast.rob.segment.Share
import cn.rtast.rob.segment.Video
import cn.rtast.rob.segment.XML
import cn.rtast.rob.enums.ContactType
import cn.rtast.rob.enums.MusicShareType
import cn.rtast.rob.enums.PokeMessage

class MessageChain internal constructor(arrayMessageList: MutableList<BaseSegment>) {

    internal val finalArrayMsgList = arrayMessageList

    fun asNode(userId: Long): NodeMessageChain {
        val node = NodeMessageChain.Builder()
            .addMessageChain(this, userId).build()
        return node
    }

    class Builder {
        private val arrayMessageList = mutableListOf<BaseSegment>()

        fun addText(text: String): Builder {
            arrayMessageList.add(PlainText(PlainText.Data(text)))
            return this
        }

        fun addFace(id: Int): Builder {
            arrayMessageList.add(Face(Face.Data(id.toString())))
            return this
        }

        fun addImage(file: String, base64: Boolean = false): Builder {
            if (base64) {
                val rawB64 = file.replace("data:image/png;base64,", "")
                arrayMessageList.add(Image(Image.Data("base64://$rawB64")))
            } else {
                arrayMessageList.add(Image(Image.Data(file)))
            }
            return this
        }

        fun addRecord(file: String): Builder {
            arrayMessageList.add(Record(Record.Data(file)))
            return this
        }

        fun addVideo(file: String): Builder {
            arrayMessageList.add(Video(Video.Data(file)))
            return this
        }

        fun addAt(qq: Long): Builder {
            arrayMessageList.add(AT(AT.Data(qq.toString())))
            return this
        }

        fun addRPS(): Builder {
            arrayMessageList.add(RPS())
            return this
        }

        fun addDice(): Builder {
            arrayMessageList.add(DICE())
            return this
        }

        fun addShake(): Builder {
            arrayMessageList.add(Shake())
            return this
        }

        fun addPoke(poke: PokeMessage): Builder {
            arrayMessageList.add(Poke(Poke.Data(poke.type.toString(), poke.id.toString())))
            return this
        }

        fun addShare(url: String, title: String, content: String? = null, image: String? = null): Builder {
            arrayMessageList.add(Share(Share.Data(url, title, content, image)))
            return this
        }

        fun addContactFriend(id: Long): Builder {
            arrayMessageList.add(Contact(Contact.Data(ContactType.qq, id.toString())))
            return this
        }

        fun addContactGroup(id: Long): Builder {
            arrayMessageList.add(Contact(Contact.Data(ContactType.group, id.toString())))
            return this
        }

        fun addLocation(lat: Double, lon: Double, title: String? = null, content: String? = null): Builder {
            arrayMessageList.add(Location(Location.Data(lat.toString(), lon.toString(), title, content)))
            return this
        }

        fun addMusicShare(type: MusicShareType, id: String): Builder {
            arrayMessageList.add(MusicShare(MusicShare.Data(type.type, id)))
            return this
        }

        fun addCustomMusicShare(
            url: String,
            audio: String,
            title: String,
            content: String? = null,
            image: String? = null
        ): Builder {
            arrayMessageList.add(CustomMusicShare(CustomMusicShare.Data(url, audio, title, content, image)))
            return this
        }

        fun addReply(id: Long): Builder {
            arrayMessageList.add(Reply(Reply.Data(id.toString())))
            return this
        }

        fun addXML(xml: String): Builder {
            arrayMessageList.add(XML(XML.Data(xml)))
            return this
        }

        fun addJSON(json: String): Builder {
            arrayMessageList.add(JSON(JSON.Data(json)))
            return this
        }

        fun addNewLine(times: Int = 1): Builder {
            arrayMessageList.add(PlainText(PlainText.Data("\n")))
            return this
        }

        internal fun addRawArrayMessage(content: List<BaseSegment>): Builder {
            arrayMessageList.addAll(content)
            return this
        }

        fun build(): MessageChain {
            return MessageChain(arrayMessageList)
        }
    }
}