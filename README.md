![](https://github.com/hanhan2001/ServerBuild/blob/master/images/ServerBuild.png)
# ServerBuild

> 此插件将大多数小插件整合成在一起做到真正的简化插件，并在原基础上开放更多可自定义设置.
>
> 插件的每个功能都由独立文本控制，避免了功能杂糅的情况. 当在 `Config.yml` 文件中启用了全局配置，其他功能的设置都将同步.
>
> 未开启相应功能也不会占用其他插件指令，这意味着 ServerBuild 可以和 CMI, Ess 之类的插件搭配使用. 插件自动检测文件更改，可以无需用指令重载(不需要自动检测可在 FileMonitor 关闭此功能).

## 📄文档

- **使用教程:** [首页 - 飞书云文档](https://jn0frpwf93.feishu.cn/wiki/XLRmwVXfYiKZ1dkvPFvcbu4cnYf?fromScene=spaceOverview)
- **开发教程:** [首页 - 飞书云文档](https://jn0frpwf93.feishu.cn/wiki/Mhh2whpzJii9tukNNR2chrAGnhg)

## 🌳项目结构

```
.
├─images				-> 图片截图
├─serverbuild-api		-> ServerBuild API 接口
├─serverbuild-common	-> ServerBuild 公用常量(事实上这种方式并不合理，可能在未来重构)
└─serverbuild-core		-> ServerBuild 核心代码
```

## 🪧技术栈

- SqlFactory -> 数据库操作工具
- SProxy -> 字节码反射工具(等待重构)

## 🛠️集成工具

> ServerBuild 提供了大部分常用功能，简化开发步骤，缩减开发周期

- GUI工具(使用原版Inventory实现，或将更改为发包)
  - 事件控制
  - 刷新控制
- 命令管理工具
- 模块管理工具
- 任务管理工具
- 插件管理工具
- 脚本命令工具
- 跨版本数据包管理工具(SProxy)

## 🕒重构进度

> 重构过程中可能新增更多功能

- [x] AutoReSpawn
- [ ] BookContent
- [x] ChatFormat
- [x] ResolveLag
- [x] FileMonitor
- [ ] Home
- [ ] LoginTP
- [x] MessageAnnouncer
- [ ] NotBuild
- [ ] PlayerShout
- [ ] PlayerEdit
- [ ] Scoreboard
- [ ] Tab
- [ ] Teleport
- [x] WelcomeMessage

## 📁配置文件

暂无

## 🤚权限

暂无

## ⌨️指令

暂无

## 🔌附属开发

插件提供 serverbuild-api 模块，可以通过编辑此模块并作为开发依赖进行 附属插件 或 module 的开发.
