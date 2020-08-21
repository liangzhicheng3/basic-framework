# basic-framework

SpringBoot基础框架搭建

项目结构

src

-main

--java

---com

----liangzhicheng

-----common(公用函数定义、工具类定义)

-----config(应用相关类配置)

-----modules(逻辑业务相关类)

---devtool(应用开发中相关代码生成处理类)

--resources(数据库链接、代码生成模板相关)

启动

    修改appliction.yml配置，包括数据库链接，redis链接等...
    需启动redis客户端(集成一些技术需要用到redis)
    启动类：com/liangzhicheng/Run.java

swagger接口文档

    找到api.html(路径：resources/static/api_doc/api.html)，并找到这个位置
    const contextPath = "/liangzhicheng"; //这里需要和appliction.yml配置文件中的context-path的值对应

    方式1->访问路径：http://localhost:8080/liangzhicheng/api_doc/api.html(swagger)
    方式2->访问路径：http://localhost:8080/liangzhicheng/doc.html(swagger-boostrap-ui)

    根据自己项目所需的端口和命名方式配置appliction.yml配置中的port和context-path的值


代码生成

    找到devtool->generator->CodeGenerator类，修改数据库链接、路径等，直接运行generate()函数(详细注释说明类中有写)
    resources下提供了一个用于测试的表test_person.sql
