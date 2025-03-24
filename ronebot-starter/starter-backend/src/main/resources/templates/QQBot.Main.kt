{{APP_PACKAGE}}

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import cn.rtast.rob.qqbot.QBotFactory
import cn.rtast.rob.qqbot.entity.inbound.GroupAtMessageCreateEvent
import cn.rtast.rob.qqbot.qbot.QQBotListener

class Bot : QQBotListener {
    override suspend fun onGroupMessage(message: GroupAtMessageCreateEvent) {
        println(message)
    }
}

fun main() {
    GlobalScope.launch {
        val appId = "{{QQ_APP_ID}}"
        val clientSecret = "{{QQ_CLIENT_SECRET}}"
        QBotFactory.createServer(8080, appId, clientSecret, Bot())
    }
}