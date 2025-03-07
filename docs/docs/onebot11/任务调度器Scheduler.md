# 全局调度器

> 全局调度器可以访问到所有的Bot实例

```kotlin
suspend fun main() {
    OneBotFactory.globalScheduler.scheduleTask({
        it.forEach { bot ->
            println(bot.action.getLoginInfo())
        }
    }, 1.seconds, 10.seconds)
    // 或者下面这种方式,  两种方式都一样
    OneBotFactory.globalScheduler.scheduleTask(1.seconds, 1.seconds) {
        it.forEach { bot ->
            println(bot.action.getLoginInfo())
        }
    }
}
```

# Bot实例调度器

> Bot实例调度器只能访问到这个Bot实例本身

```kotlin
suspend fun main() {
    val instance1 = OneBotFactory.createClient(...)
    instance1.scheduler.scheduleTask({
        println(it.action.getLoginInfo())
    }, 1.seconds, 10.seconds)
    // 或者下面这种方式,  两种方式都一样
    instance1.scheduler.scheduleTask(1.seconds, 1.seconds) {
        println(it.action.getLoginInfo())
    }
}
```