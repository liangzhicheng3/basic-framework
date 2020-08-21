# basic-framework

SpringBoot基础框架搭建

项目结构
 src
 -main
 --java
 ---com
 ----liangzhicheng
 -----common(公用函数类定义，工具类定义)
 -----config(框架相关配置)
 -----modules(增删改查)
 ---devtool(开发中使用的类)
 
上下文
 打开api.html页面并找到这个位置
 window.onload = function() {
 const contextPath = "/liangzhicheng"; //这里需要和appliction.yml配置文件中的context-path的值对应
 方式1
  访问路径：http://localhost:8080/liangzhicheng/doc.html(集成swagger-boostrap-ui)
 方式2
  入口页面路径：resources/static/api_doc/api.html
  访问路径2：http://localhost:8080/liangzhicheng/api_doc/api.html(集成swagger)
 
启动
 修改appliction.yml配置，包括数据库配置、redis配置等...
 启动类：com、liangzhicheng/Run.java

代码生成
 找到generator包下的CodeGenerator类，generate()函数，修改自己所需要的参数(数据库参数，路径参数)，并右键直接运行即可(注释在代码中有体现)
 resources下提供了用于测试的表test_person.sql
