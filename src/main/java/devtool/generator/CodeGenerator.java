package devtool.generator;

import com.liangzhicheng.common.constant.ApiConstant;
import com.liangzhicheng.common.exception.CustomizeException;
import devtool.generator.auto.AutoGenerator;
import devtool.generator.bo.CodeGeneratorBO;
import devtool.generator.constant.CGConstant;
import devtool.generator.dto.CodeGeneratorDTO;
import devtool.generator.enums.DbTypeEnum;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 代码生成类
 * @author liangzhicheng
 * @since 2021-09-11
 */
public class CodeGenerator {

    /**
     * @description 代码生成
     */
    @Test
    public void generator() {
        String dbName = "basic_framework"; //数据库名称
        String tableNames = "test_person,test_department"; //表名称
        String tablePrefixes = ""; //表前缀
        CodeGeneratorDTO generatorDTO = new CodeGeneratorDTO(
                CGConstant.CG_AUTHOR, CGConstant.CG_ROOT_PATH, CGConstant.CG_PACKAGE_NAME,
                CGConstant.CG_MYSQL, dbName, CGConstant.CG_HOST, CGConstant.CG_USERNAME,
                CGConstant.CG_PASSWORD, tableNames, tablePrefixes, CGConstant.CG_PACKAGE_NAME_CONTROLLER,
                CGConstant.CG_PACKAGE_NAME_DAO, CGConstant.CG_PACKAGE_NAME_XML, CGConstant.CG_PACKAGE_NAME_ENTITY,
                CGConstant.CG_PACKAGE_NAME_SERVICE, CGConstant.CG_PACKAGE_NAME_SERVICE_IMPL,
                CGConstant.CG_FILE_NAME_CONTROLLER, CGConstant.CG_FILE_NAME_DAO, CGConstant.CG_FILE_NAME_XML,
                CGConstant.CG_FILE_NAME_ENTITY, CGConstant.CG_FILE_NAME_SERVICE, CGConstant.CG_FILE_NAME_SERVICE_IMPL,
                null, null);
        CodeGeneratorBO generatorBO = new CodeGeneratorBO();
        BeanUtils.copyProperties(generatorDTO, generatorBO);
        this.handleDb(generatorDTO, generatorBO);
        this.handleTable(generatorDTO, generatorBO);
        try{
            new AutoGenerator(generatorBO).execute();
        }catch(Exception e){
            e.printStackTrace();
            throw new CustomizeException(ApiConstant.BASE_FAIL_CODE, "生成失败");
        }
    }

    /**
     * @description 数据库处理
     * @param generatorDTO
     * @param generatorBO
     */
    private void handleDb(CodeGeneratorDTO generatorDTO, CodeGeneratorBO generatorBO) {
        DbTypeEnum dbTypeEnum;
        String dbUrl = "";
        String dbDriver = "";
        if (!StringUtils.hasText(generatorDTO.getDbType())
                || DbTypeEnum.getDbType(generatorDTO.getDbType()) == DbTypeEnum.MYSQL) {
            //mysql
            dbTypeEnum = DbTypeEnum.MYSQL;
            dbUrl = "jdbc:mysql://" + generatorDTO.getDbHost() + "/" + generatorDTO.getDbName()
                    + "?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&serverTimezone=Hongkong";
            dbDriver = CGConstant.CG_MYSQL_DRIVER;
        } else if (DbTypeEnum.getDbType(generatorDTO.getDbType()) == DbTypeEnum.ORACLE) {
            //oracle
            dbTypeEnum = DbTypeEnum.ORACLE;
            dbUrl = "jdbc:oracle:thin:@" + generatorDTO.getDbHost() + ":" + generatorDTO.getDbName();
            dbDriver = CGConstant.CG_ORACLE_DRIVER;
        } else if (DbTypeEnum.getDbType(generatorDTO.getDbType()) == DbTypeEnum.SQL_SERVER){
            //sql server
            dbTypeEnum = DbTypeEnum.SQL_SERVER;
            dbUrl = "jdbc:sqlserver://" + generatorDTO.getDbHost() + ";DatabaseName=" + generatorDTO.getDbName();
            dbDriver = CGConstant.CG_SQL_SERVER_DRIVER;
        } else {
            throw new CustomizeException(ApiConstant.BASE_FAIL_CODE, "暂不支持的数据库类型");
        }
        generatorBO.setDbUrl(dbUrl)
                .setDbDriver(dbDriver)
                .setDbUsername(generatorDTO.getDbUsername())
                .setDbPassword(generatorDTO.getDbPassword());
    }

    /**
     * @description 表处理
     * @param generatorDTO
     * @param generatorBO
     */
    private void handleTable(CodeGeneratorDTO generatorDTO, CodeGeneratorBO generatorBO) {
        generatorBO.setTableNames(split(generatorDTO.getTableNames()))
                .setTablePrefixes(split(generatorDTO.getTablePrefixes()));
//                .setFieldPrefixes(split(generatorDTO.getFieldPrefixes()))
//                .setExcludeTableNames(split(generatorDTO.getExcludeTableNames()))
//                .setIgnoreColumns(split(generatorDTO.getIgnoreColumns()));
    }

    /**
     * @description 多表截取
     * @param value
     * @return String[]
     */
    private String[] split(String value) {
        if (!StringUtils.hasText(value)) {
            return new String[]{};
        }
        List<String> valueList = new ArrayList<>();
        String[] values;
        if (value.contains(",")) {
            values = value.split(",");
        } else if (value.contains("\n")) {
            values = value.split("\n");
        } else {
            values = value.split(" ");
        }
        for (String str : values) {
            str = str.trim();
            if (StringUtils.hasText(str)) {
                valueList.add(str);
            }
        }
        String[] result = new String[valueList.size()];
        return  valueList.toArray(result);
    }

}
