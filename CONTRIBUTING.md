# 🎯 贡献指南

感谢你对本项目的兴趣！请阅读以下指南，以确保你的贡献符合项目规范。

---

## 🛠️ 代码贡献流程

1. 克隆仓库之后需要将文档子模块一并拉取
   ```shell
   git submodule update --init --recursive
   ```
2. 请确认你的代码为Kotlin语言, 本项目仅接受Kotlin语言的代码提交
3. 添加新的模块/拓展功能时请参考`ronebot-onebot-v11`的代码设计风格进行设计
4. 如果你只想更新文档请前往: https://github.com/RTAkland/ROB-Docs 提交PR
5. 贡献代码之前请先在IDEA中设置文件头
   ```kotlin
   /*
   * Copyright © 2025 RTAkland
   * Author: RTAkland
   * Date: 2025/3/11
   */
   ```
6. 这是贡献指南, 不是使用指南, 使用文档请前往: https://rob.rtast.cn/