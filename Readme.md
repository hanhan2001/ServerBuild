# ServerBuild

## 描述

本插件将大多数小插件整合成在一起，并在原基础上开放更多可自定义设置.

插件的每个功能都由独立文本控制，避免了功能杂糅的情况. 当在 `Config.yml` 文件中启用了全局配置，其他功能的设置都将同步.

未开启相应功能也不会占用其他插件指令，也就意味着本插件可以和cmi, ess之类的插件同时使用. 插件自动检测文件更改，可以无需用指令重载(不需要自动检测可在 FileMonitor 关闭此功能).

## 重构进度

- [x] AutoReSpawn
- [ ] Back
- [ ] BanPlayer
- [ ] BookContent
- [ ] CommandBan
- [ ] ChatFormat
- [ ] ClearEntity
- [ ] FileMonitor
- [ ] Fix
- [ ] FileProtect
- [ ] Fly
- [ ] Gamemode
- [ ] GroupSpawn
- [ ] Home
- [ ] LoginTP
- [ ] MessageAnnouncer
- [ ] NotBuild
- [ ] PlayerShout
- [ ] PlayerStatus
- [ ] Scoreboard
- [ ] ServerStatus
- [ ] Suicide
- [ ] Teleport
- [ ] WelcomeMessage

## 功能

| 英文名           | 中文名         | 描述                                                         |
| ---------------- | -------------- | ------------------------------------------------------------ |
| AutoReSpawn      | 自动重生       | 自动重生玩家.<br />Auto re spawn player.                     |
| BookContent      | 书本文章       | 生成一个带文章的书本<br />Give a book whit articles.         |
| ChatFormat       | 聊天格式       | 更改游戏原版聊天格式<br />Replace minecraft default chat message format. |
| ClearEntity      | 清理实体       | 清理服务器内实体<br />Clear server's entities.               |
| FileMonitor      | 文件检测       | 检测文件变动并自动重载对应功能(默认开启)<br />Auto reload handle when the file changed. |
| Home             | 家             | 家<br />Home.                                                |
| LoginTP          | 固定登录点     | 固定玩家每次上线位置<br />Fixed player login server location. |
| MessageAnnouncer | 公告           | 定时公告<br />Auto message.                                  |
| NotBuild         | 禁止建筑       | 禁止建筑<br />Prohibit player from building.                 |
| PlayerShout      | 喊话           | 玩家喊话<br />Player shout.                                  |
| Scoreboard       | 计分板         | 玩家计分板<br />new Scoreboard                               |
| Teleport         | 传送           | 传送<br />Player teleport                                    |
| WelcomeMessage   | 服务器欢迎消息 | 更改原版玩家进入/退出服务器消息格式<br />Replace minecraft default login/quit message format. |

## 配置文件

- [AutoReSpawn](https://github.com/hanhan2001/ServerBuild/blob/master/src/main/resources/AutoReSpawn.yml)
- [BookContent](https://github.com/hanhan2001/ServerBuild/blob/master/src/main/resources/AutoReSpawn.yml)
- [ChatFormat](https://github.com/hanhan2001/ServerBuild/blob/master/src/main/resources/ChatFormat.yml)
- [ClearEntity](https://github.com/hanhan2001/ServerBuild/blob/master/src/main/resources/ClearEntity.yml)
- [FileMonitor](https://github.com/hanhan2001/ServerBuild/blob/master/src/main/resources/FileMonitor.yml)
- [Home](https://github.com/hanhan2001/ServerBuild/blob/master/src/main/resources/Home.yml)
- [LoginTP](https://github.com/hanhan2001/ServerBuild/blob/master/src/main/resources/LoginTP.yml)
- [MessageAnnouncer](https://github.com/hanhan2001/ServerBuild/blob/master/src/main/resources/MessageAnnouncer.yml)
- [NotBuild](https://github.com/hanhan2001/ServerBuild/blob/master/src/main/resources/NotBuild.yml)
- [PlayerShout](https://github.com/hanhan2001/ServerBuild/blob/master/src/main/resources/PlayerShout.yml)
- [Scoreboard](https://github.com/hanhan2001/ServerBuild/blob/master/src/main/resources/Scoreboard.yml)
- [Teleport](https://github.com/hanhan2001/ServerBuild/blob/master/src/main/resources/Teleport.yml)
- [WelcomeMessage](https://github.com/hanhan2001/ServerBuild/blob/master/src/main/resources/WelcomeMessgae.yml)

## 权限

[不想写了](/Permission.md)

## 指令

[又困又不困，受不了了](/Command.md)
