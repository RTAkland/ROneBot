# Starter frontend

这是项目模板生成器的前端, 你可以fork仓库后修改`resources/config/config.json`内的`backend`地址来使用自己部署的后端.

# 本地运行

```shell
./gradlew :ronebot-starter:starter-frontend:wasmJsBrowserRun
```

编译完成之后会自动打开浏览器

# 构建静态产物

```shell
./gradlew :ronebot-starter:starter-frontend:wasmJsBrowserDevelopmentExecutableDistribution
```

生成的静态页面在`ronebot-starter/starter-frontend/build/dist/wasmJs/developmentExecutable/`路径下

# 部署

推荐使用actions部署到cloudflare pages上, 可以参考`.github/workflows/deploy_frontend_cf_pages.yml`这个工作流文件