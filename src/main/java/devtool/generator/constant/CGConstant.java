package devtool.generator.constant;

/**
 * @description DB常量类
 * @author liangzhicheng
 * @since 2021-09-11
 */
public class CGConstant {

    //作者
    public static final String CG_AUTHOR = "liangzhicheng";

    //生成文件的输出目录
    public static String CG_PROJECT_PATH = System.getProperty("user.dir");

    //根路径
    public static String CG_ROOT_PATH = "/code-generator";

    //输出目录
    public static final String CG_OUTPUT_DIR = CG_PROJECT_PATH + CG_ROOT_PATH;

    //包名
    public static final String CG_PACKAGE_NAME = "com.liangzhicheng.modules";

    //数据库类型
    public static final String CG_MYSQL = "MySQL";
    public static final String CG_MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String CG_ORACLE = "Oracle";
    public static final String CG_ORACLE_DRIVER = "oracle.jdbc.OracleDriver";
    public static final String CG_SQL_SERVER = "SQL_Server";
    public static final String CG_SQL_SERVER_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    //数据库服务器
    public static final String CG_HOST = "127.0.0.1:3306";

    //数据库用户名
    public static final String CG_USERNAME = "root";

    //数据库密码
    public static final String CG_PASSWORD = "admin";

    //包名：controller
    public static final String CG_PACKAGE_NAME_CONTROLLER = "controller";

    //包名：dao
    public static final String CG_PACKAGE_NAME_DAO = "dao";

    //包名：dao.mapper(xml)
    public static final String CG_PACKAGE_NAME_XML = "dao.mapper";

    //包名：entity
    public static final String CG_PACKAGE_NAME_ENTITY = "entity";

    //包名：service
    public static final String CG_PACKAGE_NAME_SERVICE = "service";

    //包名：service.impl
    public static final String CG_PACKAGE_NAME_SERVICE_IMPL = "service.impl";

    //文件名后缀：Controller
    public static final String CG_FILE_NAME_CONTROLLER = "%sController";

    //文件名前后缀：I*Dao
    public static final String CG_FILE_NAME_DAO = "I%sDao";

    //文件名后缀：Dao
    public static final String CG_FILE_NAME_XML = "%sDao";

    //文件名后缀：Entity
    public static final String CG_FILE_NAME_ENTITY = "%sEntity";

    //文件名前后缀：I*Service
    public static final String CG_FILE_NAME_SERVICE = "I%sService";

    //文件名后缀：ServiceImpl
    public static final String CG_FILE_NAME_SERVICE_IMPL = "%sServiceImpl";

    //逻辑删除字段
    public static final String CG_DEL_USER = "del_user";

    //乐观锁字段名
    public static final String CG_VERSION = "version";

}
