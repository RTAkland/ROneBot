/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/2
 */


package cn.rtast.rob.util.ob

import cn.rtast.rob.entity.segment.AT
import cn.rtast.rob.entity.segment.BaseArrayMessage
import cn.rtast.rob.entity.segment.Contact
import cn.rtast.rob.entity.segment.CustomMusicShare
import cn.rtast.rob.entity.segment.DICE
import cn.rtast.rob.entity.segment.Face
import cn.rtast.rob.entity.segment.Image
import cn.rtast.rob.entity.segment.JSON
import cn.rtast.rob.entity.segment.Location
import cn.rtast.rob.entity.segment.MusicShare
import cn.rtast.rob.entity.segment.PlainText
import cn.rtast.rob.entity.segment.Poke
import cn.rtast.rob.entity.segment.RPS
import cn.rtast.rob.entity.segment.Record
import cn.rtast.rob.entity.segment.Reply
import cn.rtast.rob.entity.segment.Shake
import cn.rtast.rob.entity.segment.Share
import cn.rtast.rob.entity.segment.Video
import cn.rtast.rob.entity.segment.XML
import cn.rtast.rob.enums.ContactType
import cn.rtast.rob.enums.MusicShareType
import cn.rtast.rob.enums.PokeMessage

class MessageChain internal constructor(arrayMessageList: List<BaseArrayMessage>) {

    internal val finalArrayMsgList = arrayMessageList

    class Builder {
        private val arrayMessageList = mutableListOf<BaseArrayMessage>()

        fun addText(text: String): Builder {
            arrayMessageList.add(PlainText(PlainText.Data(text)))
            return this
        }

        fun addFace(id: Int): Builder {
            arrayMessageList.add(Face(Face.Data(id.toString())))
            return this
        }

        fun addImage(file: String): Builder {
            arrayMessageList.add(Image(Image.Data(file)))
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

        fun addNewLine(times: Int = 1) : Builder {
            arrayMessageList.add(PlainText(PlainText.Data("\n")))
            return this
        }

        fun build(): MessageChain {
            return MessageChain(arrayMessageList)
        }
    }
}