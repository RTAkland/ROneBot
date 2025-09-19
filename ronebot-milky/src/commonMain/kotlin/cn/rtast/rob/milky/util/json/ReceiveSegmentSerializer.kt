/*
 * Copyright © 2025 RTAkland
 * Date: 9/19/25, 7:30 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.milky.util.json

import cn.rtast.rob.milky.segment.RFaceSegment
import cn.rtast.rob.milky.segment.RForwardSegment
import cn.rtast.rob.milky.segment.RImageSegment
import cn.rtast.rob.milky.segment.RLightAppSegment
import cn.rtast.rob.milky.segment.RMarketFaceSegment
import cn.rtast.rob.milky.segment.RMentionSegment
import cn.rtast.rob.milky.segment.RRecordSegment
import cn.rtast.rob.milky.segment.RReplySegment
import cn.rtast.rob.milky.segment.RTextSegment
import cn.rtast.rob.milky.segment.RVideoSegment
import cn.rtast.rob.milky.segment.RXMLSegment
import cn.rtast.rob.milky.segment.ReceiveSegment
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

public object ReceiveSegmentSerializer : JsonContentPolymorphicSerializer<ReceiveSegment>(ReceiveSegment::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<ReceiveSegment> {
        return when (val type = element.jsonObject["type"]?.jsonPrimitive?.content) {
            "text" -> RTextSegment.serializer()
            "mention" -> RMentionSegment.serializer()
            "face" -> RFaceSegment.serializer()
            "reply" -> RReplySegment.serializer()
            "image" -> RImageSegment.serializer()
            "record" -> RRecordSegment.serializer()
            "video" -> RVideoSegment.serializer()
            "forward" -> RForwardSegment.serializer()
            "market_face" -> RMarketFaceSegment.serializer()
            "light_app" -> RLightAppSegment.serializer()
            "xml" -> RXMLSegment.serializer()
            else -> error("未知接收消息类型: $type")
        }
    }
}