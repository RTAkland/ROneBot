# 发送消息

> 在开始之前我已经假设你已经看完了`构造消息`这一章节, 如果你还没看可以点[这里](https://github.com/RTAkland/ROneBot/wiki/%E6%9E%84%E9%80%A0%E6%B6%88%E6%81%AF)来阅读

发送消息需要使用`action`对象来进行操作, 现在任何的事件中都会存在一个`action`字段来获取当前Bot实例的`API操作器`

# 获取action
现在先从一个`GroupMessage`对象中获取一个`action`对象

```kotlin
fun main() {
    val message: GroupMessage = ...
    val action = message.action
}
```

这里的action变量就是这个消息所属的Bot实例的API操作器

# 发送消息

```kotlin
fun main() {
    val message: GroupMessage = ...
    val action = message.action
    action.sendGroupMessage(114514L, messageChain { 
        text("测试消息")
    })
}
```

这里向群号为`114514`的群聊中发送了一条纯文本消息: `测试消息`

还可以更方便的从消息对象中直接发送消息就像下面这样

```kotlin
suspend fun main() {
    val instance1 = OneBotFactory.createClient(...)
    instance1.onEvent<GroupMessageEvent> { 
        it.message.sendMessage("这是一段纯文本")
        it.message.sendMessage(Text("这是一个消息段纯文本"))
        it.message.sendMessage(messageChain { 
            text("这是一段消息链纯文本")
        })
    }
}
```

***注意: 虽然这是从消息对象发出来的消息但是并不是回复某个消息而是直接发送消息, 如果你需要回复消息请直接对消息使用`reply`方法***

# 其他API操作

还是一样先获取action对象， 然后进行API调用

```kotlin
val login = action.getLoginInfo()
```

这里获取到Bot的登录信息: Bot昵称和QQ号

## 异步操作

大部分的有返回值的API都添加了一个以`Async`结尾的函数用于异步操作

# 调用没有定义的API

```kotlin
// 这里以同步的方式调用API有返回值, 返回值是OneBot实现返回的json数据
val apiResult: String = message.action.callApi(
    "some_api_that_has_not_contain_in_action", mapOf("key" to "value"))
// 这里是异步调用无返回值
val apiAsyncResult: Unit = message.action.callApiAsync(
"some_api_that_has_not_contain_in_action", mapOf("key" to "value"))
```

# 撤回消息

撤回消息只需要使用action.revokeMessage(...) 这里传入消息ID即可撤回消息, 撤回自己的消息就传入自己发送的消息ID，撤回别人的消息就撤回别人的消息ID


