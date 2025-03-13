rootProject.name = "ROneBot"

listOf(
    ":ronebot-common",
    ":ronebot-common-http",
    ":ronebot-onebot-v11",
    ":ronebot-qqbot-webhook",
    ":ronebot-permission",
    ":ronebot-string-format",
    ":ronebot-kook-common",
    ":ronebot-kook-ws",
    ":ronebot-kook-webhook",
    ":ronebot-kook-webhook-url-validator",
    ":ronebot-starter:starter-backend",
    ":ronebot-benchmark",
).forEach {
    include(it)
}