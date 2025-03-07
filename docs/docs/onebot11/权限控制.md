权限控制需要额外引入新的模块`ronebot-permission`

```kotlin
dependencies {
    // 这里的版本替换成最新版本
    implementation("cn.rtast:ronebot-permission:${version}")
}
```
# 设置权限

> 权限以3中方式呈现, 分别是 `BasicPermission 枚举` `level(Int) 权限等级` `权限节点(String)`

```kotlin
    OneBotFactory.getPermissionManager().apply {
//        setUserPermissionLevel(3458671395.toString(), BasicPermission.User)
//        setUserPermission(3458671395.toString(), BasicPermission.User)
//        setUserPermission(3458671395.toString(), "command.test.main")
    // 只要大于3就拥有所有权限, 所有权限指的是BasicPermission和Int level的所有权限
    // 权限节点需要单独配置
    setUserPermission(3458671395.toString(), 114514)
}
```

# 在继承实现的命令中使用

> 你只需要使用hasPermission这个多个类型的重载函数然后判断返回值就能判断是否有权限了

# 在Brigadier中使用

```kotlin
suspend fun main() {
    OneBotFactory.brigadierCommandManager.register(
        Commands.literal("main")
//            .requires { it.hasPermission(BasicPermission.Admin) }
//            .requires { it.hasPermission("command.test.main") }
            .requires { it.hasPermission(114514) }
            .then(
                Commands.argument("test", CharArgumentType.char())
                    .executes {
                        println(it.getChar("test"))
                        Command.SINGLE_SUCCESS
                    }
            ), listOf("main1", "1111")
    )
}
```