# bilibili

### 背景

想获取一个b站用户的所有收藏夹内容（后添加发布视频，发布专栏图片），并下载。

下载部分可以使用[annie](https://github.com/iawia002/annie)(一个很强的下载器)

本项目运行后通过提示输入b站用户uid，输出该用户的发布所有收藏夹（后添加发布视频，发布专栏视频）txt文本



### 开发、测试环境

jdk1.8

macos



### 文件说明

| 文件名                               | 作用                                   |
| ------------------------------------ | -------------------------------------- |
| Main.java                            | 项目的主类（启动类）                   |
| chu.bilibili.type.DownloadCollection | 获取收藏夹的类                         |
| chu.bilibili.type.DownloadColumn     | 获取专栏文集的类                       |
| chu.bilibili.type.DownloadColumnPic  | 获取专栏文集内图片的类                 |
| chu.bilibili.type.DownloadRelease    | 获取发布视频的类                       |
| chu.bilibili.util.HttpClientDownPage | （工具类）发送请求得到响应             |
| chu.bilibili.util.JacksonUtil        | （工具类）解析json输出字符串/整型/列表 |



### 稳定性

目前使用过程中没有发现任何问题



### 不足

没有进度显示，需要获取内容庞大时不能实时看到数据

不支持选择多个内容（目前只实现选择单个或者全部）

没有内置的下载器，下载部分需要通过annie实现
