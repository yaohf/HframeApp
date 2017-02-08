# HframeAp

1. 网络架构 职责分工：

  api model　负责接口定义，网络api更换

  model model 负责本地sqlite,sdcard数据处理

  core model 负责逻辑代码处理

  app model 只负责ui展示
  
  
2. UI架构 Activity Fragment 之间的管理<br/>
  fragemnt stack 的处理<br/>
  Tevent 多消息处理替换EventBus,只需要两个类，简便，易操作，学习成本低。<br/>
  panel 在线涂鸦板<br/>
  hook 反射隐式操作内部代码<br/>
  CountDwon 提供倒计时控件<br/>
  widget model 为自定义view框架<br/>
  
