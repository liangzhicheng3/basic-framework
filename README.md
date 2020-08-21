# basic-framework

项目结构

├─com
│ │
│ ├─liqihua
│ │ │
│ │ ├─common----------------公用函数、工具类
│ │ │
│ │ ├─config----------------spring boot配置
│ │ │
│ │ └─crud------------------增删查改
│
└─generator-------------------生成代码
启动

修改好appliction.yml的数据库配置即可启动
启动类：com/liqihua/Run.java
swagger文档
入口文件

resources/static/api_doc/api.html
上下文

打开这个api.html并找到这个位置
window.onload = function() {
const contextPath = "/liqihua";//这里需要和appliction.yml的context-path的值对应
访问路径

http://localhost:9001/liqihua/api_doc/api.html
每个项目一般有自己的访问路径，liqihua是appliction.yml的context-path的值，9001是appliction.yml的port的值
代码生成

找到generator包下的CodeGenerator类，看make()函数
替换数据库url等变量的值，运行即可生成代码
resources下提供了一个用于测试的表test_person.sql
Pages 2

    Home
    快速使用

Clone this wiki locally
