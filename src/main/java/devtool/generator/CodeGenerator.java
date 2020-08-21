package devtool.generator;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 代码生成器
 * @author liangzhicheng
 * @since 2020-07-31
 */
public class CodeGenerator {

    @Test
    public void generate(){
        //String url = "jdbc:mysql://127.0.0.1:3306/basic_framework?characterEncoding=utf8&serverTimezone=Hongkong";
        String url = "jdbc:mysql://127.0.0.1:3306/basic_framework?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&serverTimezone=Hongkong";
        String username = "root";
        String password = "admin";
        String dir = "D://GeneratorCode"; //代码生成在哪个位置，一般是项目工作目录以外的位置，以防错误覆盖

        String author = "liangzhicheng"; //作者
        String parent = "com.liangzhicheng.modules"; //父级路径
        String moduleName = "department"; //在哪个包下生成，代码最后会生成在parent.moduleName下，如：com.liangzhicheng.project
        String tablePrefix = ""; //表前缀，生成的java类名会去掉前缀
        String[] tables = new String[] { "test_department_person" }; //生成哪个表

        AutoGenerator mpg = new AutoGenerator();
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        //全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(dir); //代码生成在哪个位置，一般是项目工作目录以外的位置，以防错误覆盖
        gc.setFileOverride(true);
        gc.setActiveRecord(true); //不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false); //XML 二级缓存
        gc.setBaseResultMap(true); //XML ResultMap
        gc.setBaseColumnList(false); //XML ColumList
        gc.setAuthor(author); //设置作者
        //gc.setSwagger2(true); //开启Swagger注解

        //设置各个层的类名
        gc.setServiceName("I%sService");
        gc.setControllerName("%sWebController");
        gc.setEntityName("%sEntity");
        gc.setMapperName("I%sDao");
        gc.setXmlName("%sDao");

        //数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername(username);
        dsc.setPassword(password);
        dsc.setUrl(url);
        IDbQuery dbQuery = new MySqlQuery();
        dbQuery.fieldCustom();
        dsc.setDbQuery(dbQuery);

        //自动填充字段
        List<TableFill> tableFillList = new ArrayList<>();
        tableFillList.add(new TableFill("create_date",FieldFill.INSERT));
        tableFillList.add(new TableFill("update_date",FieldFill.INSERT_UPDATE));
        //策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setTableFillList(tableFillList);
        strategy.setRestControllerStyle(true); //用@RestController注解代替@Controller注解
        strategy.setSuperControllerClass("com.liangzhicheng.common.basic.BaseController"); //设置Controller的父类
        strategy.setTablePrefix(new String[] { tablePrefix }); //表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel); //表名生成策略
        strategy.setInclude(tables); //生成哪个表的代码
        strategy.setEntityLombokModel(true); //开启lombok

        //包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(parent); //父级路径
        pc.setModuleName(moduleName); //在哪个包下生成
        pc.setMapper("dao"); //生成Dao类
        pc.setXml("dao.mapper"); //生成Dao mapper文件
        pc.setController("controller"); //生成Controller类

        mpg.setGlobalConfig(gc); //全局配置
        mpg.setDataSource(dsc); //数据源配置
        mpg.setStrategy(strategy); //策略配置
        mpg.setPackageInfo(pc); //包配置

        //自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                /*Map<String, Object> map = new HashMap<>();
                map.put("aaa","3333");
                setMap(map);*/
            }
        };

        //添加文件
        List<FileOutConfig> focList = new ArrayList<>();
        //vo类
        focList.add(new FileOutConfig("/templates/vo.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                //自定义输入文件名称
                String fileName = dir  + pc.getParent().replace(".","/") + "/" + pc.getEntity() + "/vo/" + tableInfo.getEntityName().replace("Entity","VO") + StringPool.DOT_JAVA;
                return fileName;
            }
        });
//        //vue的form
//        focList.add(new FileOutConfig("/templates/form.vue.ftl") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                //自定义输入文件名称
//                String fileName = dir + StrUtil.lowerFirst(tableInfo.getEntityName().replace("Entity","")) + "/form.vue";
//                return fileName;
//            }
//        });
//        //vue的list
//        focList.add(new FileOutConfig("/templates/list.vue.ftl") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                //自定义输入文件名称
//                String fileName = dir + StrUtil.lowerFirst(tableInfo.getEntityName().replace("Entity","")) + "/list.vue";
//                return fileName;
//            }
//        });
//        //vue的router
//        focList.add(new FileOutConfig("/templates/router.json.ftl") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                //自定义输入文件名称
//                String fileName = dir + StrUtil.lowerFirst(tableInfo.getEntityName().replace("Entity","")) + "/router.json";
//                return fileName;
//            }
//        });
        cfg.setFileOutConfigList(focList);
        //添加自定义生成配置
        mpg.setCfg(cfg);
        //执行生成
        mpg.execute();
    }

}
