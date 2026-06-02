# zrlog-plugin-oss

ZrLog 阿里云 OSS 插件。将文章附件、生成资源、模板静态资源和静态缓存文件上传到阿里云 OSS，可配置私有 Bucket 和 CDN 刷新。

## 功能

- 配置 OSS AccessKey、SecretKey、Bucket、访问域名和地域
- 上传文章附件和生成资源到 OSS
- 同步主题静态资源和静态缓存 HTML 文件
- 可配置私有 Bucket
- 可配置 CDN 刷新

## 构建

```shell
export JAVA_HOME=${HOME}/dev/graalvm-jdk-latest
export PATH=${JAVA_HOME}/bin:$PATH
```
