package {{APP_PACKAGE}}

import cn.rtast.rob.qqbot.QBotFactory
import cn.rtast.rob.qqbot.entity.inbound.GroupAtMessageCreateEvent
import cn.rtast.rob.qqbot.qbot.QQBotListener

class Bot : QQBotListener {
    override suspend fun onGroupMessage(message: GroupAtMessageCreateEvent) {
        println(message)
    }
}

suspend fun main() {
    val appId = System.getenv("QQ_APP_ID")
    val clientSecret = System.getenv("QQ_APP_SECRET")
    QBotFactory.createServer(8080, appId, clientSecret, Bot())
}