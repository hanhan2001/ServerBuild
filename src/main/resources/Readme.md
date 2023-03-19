# 前言

本插件的每个功能都由独立文本控制，避免了功能杂糅的情况.
当在 `Config.yml` 文件中启用了全局配置，其他功能的设置都将同步.

可能面临未开启某功能时，占用其他插件命令.
解决方法 -> 在命令前加上 插件名.
例子 -> essentials:tpa.

# 实现功能

- [x] NotBuild
- [x] LoginTP
- [x] WelcomeMessage
- [ ] ChatFormat

| 英文名         | 中文名         | 描述                                |
| -------------- | -------------- | ----------------------------------- |
| NotBuild       | 禁止建筑       | 禁止建筑                            |
| LoginTP        | 固定登录点     | 固定玩家每次上线位置                |
| WelcomeMessage | 服务器欢迎消息 | 更改原版玩家进入/退出服务器消息格式 |
| ChatFormat     | 聊天格式       | 更改游戏原版聊天格式                |

# 功能配置文件

## NotBuild:

```yaml
#禁止建筑

#开关
Enable: false

#设置
Set:
  #日期格式，作用于变量 %date%
  DateFormat: "yyyy/MM/dd-HH:mm:ss"

#阻止建筑方块
Build:
  #开关
  Enable: false
  #提示信息(删除""及其内的内容则不提示)
  Message:
    - "&b[%date%]&f-&eNotBuild"
    - "   &c你没权限这样做"
    - ""
  #启用世界
  World:
    - world
    - world_nether
    - world_the_end

#阻止建筑方块
Destruction:
  #开关
  Enable: false
  #提示信息(删除""及其内的内容则不提示)
  Message:
    - "&b[%date%]&f-&eNotBuild"
    - "   &c你没权限这样做"
    - ""
  #启用世界
  World:
    - world
    - world_nether
    - world_the_end

#词条
Message:
  #前缀
  Prefix: "&a[&bNotBuild&a]&e>> "
  #重载
  Reload: "&a已重载！"
  #无权限提示
  NoPermission: "&c你无法这样做！"

#指令提示--谁改谁憨憨，反正只是用来提示用的，出现问题我才不负责呢，哼！
Use-Help:
  - ""
  - "&a<-=-=-=-=-=-=-=-=-=-= &e&lNotBuild &a=-=-=-=-=-=-=-=-=-=->"
  - "&b/nb reload &7重载 禁止建筑"
  - ""
```

## WelcomeMessage:

```yaml
#自定义 进入/退出服务器 消息

#开关
Enable: false

#设置(如若无法更改请查看是否在 Config.yml 文件中启用了全局配置)
Set:
  #日期格式，作用于变量 %date%
  DateFormat: "yyyy/MM/dd-HH:mm:ss"

#默认
#第一组设置
#可自行添加，只需要照着 default 的模板复制一个就可(不可以漏掉任何配置节点)
#当优先级相同，优先执行处于文本下方的组
default:
  #优先级
  Priority: 0
  #所需权限
  Permission: "sb.wm.use"
  #进入服务器
  Join:
    #聊天框
    Chat:
      #开关
      Enable: true
      #内容
      Message:
        - ""
        - "&7[&a+&7]&b%player% - &7[%date%]"
        - ""
    #公告
    Title:
      #开关
      Enable: false
      #是否发送给所有玩家
      AllPlayer: false
      #主标题
      Title: "&a欢迎加入服务器"
      #副标题
      SubTitle: "%b%player%"
    #动作消息
    ActionBar:
      #开关
      Enable: false
      #是否发送给所有玩家
      AllPlayer: false
      #内容
      Message: "&6欢迎！！o((>ω< ))o"
  #退出服务器消息
  #退出消息没有单独控制是否发送给所有玩家，将会直接发送给所有在线玩家
  Quit:
    #聊天框
    Chat:
      #开关
      Enable: true
      #内容
      Message:
        - ""
        - "&7[&c-&7]&b%player% - &7[%date%]"
        - ""
    #公告
    Title:
      #开关
      Enable: false
      #主标题
      Title: "&a等待您下一次光临(ノへ￣、)"
      #副标题
      SubTitle: "%b%player%"
    #动作消息
    ActionBar:
      #开关
      Enable: false
      #内容(此处只可一行)
      Message: "&c再见~~இ௰இ"
#第二组设置
admin:
  #优先级
  Priority: 0
  #所需权限
  Permission: "sb.wm.admin"
  #进入服务器
  Join:
    #聊天框
    Chat:
      #开关
      Enable: true
      #是否发送给所有玩家
      AllPlayer: true
      #内容
      Message:
        - ""
        - "&b[%date%] &6大佬进入服务器啦！ヾ(•ω•`)o"
        - "   &e欢迎: &b%player%"
        - ""
    #公告
    Title:
      #开关
      Enable: false
      #是否发送给所有玩家
      AllPlayer: false
      #主标题
      Title: "&a大佬来啦！"
      #副标题
      SubTitle: "%b%player%"
    #动作消息
    ActionBar:
      #开关
      Enable: false
      #是否发送给所有玩家
      AllPlayer: false
      #内容
      Message: "&6欢迎！！o((>ω< ))o"
  #退出服务器消息
  #退出消息直接发送给所有在线玩家
  Quit:
    #聊天框
    Chat:
      #开关
      Enable: true
      #内容
      Message:
        - ""
        - "&b[%date%] &6大佬走了~~(;´༎ຶД༎ຶ`)"
        - "   &c拜拜: &b%player%"
        - ""
    #公告
    Title:
      #开关
      Enable: false
      #主标题
      Title: "&a常来看看(ノへ￣、)"
      #副标题
      SubTitle: "%b%player%"
    #动作消息
    ActionBar:
      #开关
      Enable: false
      #内容(此处只可一行)
      Message: "&c再见~~இ௰இ"

#词条
Message:
  #前缀
  Prefix: "&a[&bWelcomeMessage&a]&e>> "
  #重载
  Reload: "&a已重载！"
  #无权限提示
  NoPermission: "&c你无法这样做！"

#指令提示--谁改谁憨憨，反正只是用来提示用的，出现问题我才不负责呢，哼！
Use-Help:
  - ""
  - "&a<-=-=-=-=-=-=-=-=-=-= &e&lWelcomeMessage &a=-=-=-=-=-=-=-=-=-=->"
  - "&b/wm reload &7重载 进入/退出服务器消息"
  - ""
```

## LoginTP:

```yaml
#固定点登录

#开关
Enable: false

#固定点登录
Location:
  #位置
  World: world
  X: 0.0
  Y: 0.0
  Z: 0.0
  Yaw: 0.0
  Pitch: 0.0

#词条
Message:
  #前缀
  Prefix: "&a[&bLoginTP&a]&e>> "
  #重载插件
  Reload: "&a已重载！"
  #无权限提示
  NoPermission: "&c你无法这样做！"
  #玩家身份
  UsePlayer: "&c只能在游戏内使用"

#指令提示--谁改谁憨憨，反正只是用来提示用的，出现问题我才不负责呢，哼！
Use-Help:
  - ""
  - "&a<-=-=-=-=-=-=-=-=-=-= &e&lLoginTP &a=-=-=-=-=-=-=-=-=-=->"
  - "&b/ltp reload &7重载 自动重生"
  - "&b/ltp set &7设置固定登录点"
  - ""
```

## ChatFormat:

```yaml
#聊天格式
#(不想做的太复杂就没有写点击聊天框的东西)

#开关
Enable: false

#格式
#注: 优先级相同时匹配到的是位居下方的格式
#    如 admin 优先级为 0, vip 优先级为 0, 而 vip 写在 admin 的后几行，则匹配到的是 vip 格式
Formats:
  #格式一
  default:
    #数值越低优先级越高
    Priority: 1
    #权限
    Permission: "cf.default"
    #格式
    Format: "&b[%date%] &f%player%&8>> &f%message%"
  #格式二
  admin:
    #数值越低优先级越高
    Priority: 0
    #权限
    Permission: "cf.admin"
    #格式
    Format: "&b[%date%] &c[管理员] &f%player%&8>> &f%message%"

#聊天字符个数限制(每次聊天发送的字符最大数)
CharacterLimit:
  #开关
  Enable: false
  #限制字符
  Limit: 32
  #消息提示(删除内容及""则不提示消息)
  Message: "&c嚷嚷什么呢，说这么一大串"

#聊天呼叫（@某某）
Call:
  #开关
  Enable: false
  #呼叫关键词
  Key: "@"
  #被呼叫者游戏声音提示(删除内容及""则不提示游戏声音)
  Sound: "entity.generic.explode"

#屏蔽关键词
BlackTerms:
  #开关
  Enable: false
  #是否惩治所有类型玩家(包含拥有屏蔽权限及OP玩家)
  ForEveryBody: false
  #是否阻止玩家发送此条消息
  Cancel: false
  #让触发玩家做什么
  #让玩家执行指令 -> command: <指令>
  #让玩家以op身份执行指令 -> opcommand: <指令>（注: op身份是给玩家op再去除op，可能会出现卡op的情况）
  #让玩家发送消息 -> send: <内容>
  #无任何标头代指操作(如command、opcommand)则默认使用send(删除""内信息则不提示)
  Todo:
    - 'command: suicide'
    - 'send: 我是sb'
  #触发提示(删除""内信息则不提示)
  Message: "&c素质青年,从我做起"
  #关键词
  Terms:
    - 傻逼
    - 草泥马
    - 你妈死了
    - sb
    - nmsl

#词条
#如若无法编辑修改，请检查是否在 Config.yml 开启了全局配置
Message:
  #前缀
  Prefix: "&a[&bChatFormat&a]&e>> "
  #重载插件
  Reload: "&a插件已重载！"
  #无权限提示
  NoPermission: "&c你无法这样做！"

#日期格式
#设置 %date% 变量格式(如若无法编辑修改，请检查是否在 Config.yml 开启了全局配置)
DateFormat: "yyyy/MM/dd-HH:mm:ss"

#指令提示--谁改谁憨憨，反正只是用来提示用的，出现问题我才不负责呢，哼！
Use-Help:
  - ""
  - "&a<-=-=-=-=-=-=-=-=-=-= &e&lChatFormat &a=-=-=-=-=-=-=-=-=-=->"
  - "&b/cf reload &7重载 聊天格式"
  - "&b/cf mute [player] &7操作 禁言玩家"
  - ""
```

# 功能指令

## NotBuild:

| 指令       | 例子       | 描述 |
| ---------- | ---------- | ---- |
| /nb reload | /nb reload | 重载 |

## WelcomeMessage:

| 指令       | 例子       | 描述 |
| ---------- | ---------- | ---- |
| /wm reload | /wm reload | 重载 |

## LoginTP:

| 指令       | 例子       | 描述         |
| ---------- | ---------- | ------------ |
| /lt set    | /lt set    | 设置登录点   |
| /lt reload | /lt reload | 重载 LoginTP |

# 功能权限

## LoginTP:

| 权限名称        | 描述     | 默认作用组 |
|-------------| -------- | ---------- |
| sb.lt.admin | 管理权限 | op         |

## WelcomeMessage:

| 权限名称    | 描述     | 默认作用组 |
| ----------- | -------- | ---------- |
| sb.wm.admin | 管理权限 | op         |

## ChatFormat:

| 权限名称             | 描述               | 默认作用组 |
|------------------| ------------------ | ---------- |
| sb.cf.call       | 聊天呼唤           | op         |
| sb.cf.color      | 聊天色彩           | op         |
| sb.cf.blackterms | 跳过聊天屏蔽词检测 | op         |

