package com.liangzhicheng.config.db;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description 数据库连接配置
 * @author liangzhicheng
 * @since 2020-07-28
 */
@Configuration
@MapperScan(basePackages = {"com.liangzhicheng.**.dao*"}, sqlSessionFactoryRef = "sqlSessionFactory")
public class DBConfig {

    @Autowired
    Environment environment;
    /**
     * mapper的xml位置
     */
    public static final String[] DATASOURCE_MAPPER_LOACTIONS = {"classpath*:com/liangzhicheng/**/*Dao.xml"};

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClass;
    @Value("${spring.datasource.type}")
    private String type;
    @Value("${spring.datasource.initial-size}")
    private int initialSize;
    @Value("${spring.datasource.min-idle}")
    private int minIdle;
    @Value("${spring.datasource.max-active}")
    private int maxActive;
    @Value("${spring.datasource.max-wait}")
    private int maxWait;
    @Value("${spring.datasource.time-between-eviction-runs-millis}")
    private int timeBetweenEvictionRunsMillis;
    @Value("${spring.datasource.min-evictable-idle-time-millis}")
    private int minEvictableIdleTimeMillis;
    @Value("${spring.datasource.validation-query}")
    private String validationQuery;
    @Value("${spring.datasource.test-while-idle}")
    private boolean testWhileIdle;
    @Value("${spring.datasource.test-on-borrow}")
    private boolean testOnBorrow;
    @Value("${spring.datasource.test-on-return}")
    private boolean testOnReturn;
    @Value("${spring.datasource.filters}")
    private String filters;
    @Value("${spring.datasource.log-slow-sql}")
    private String logSlowSql;

    @Bean
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDbType(type);
        dataSource.setInitialSize(initialSize);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxActive(maxActive);
        dataSource.setMaxWait(maxWait);
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        dataSource.setValidationQuery(validationQuery);
        dataSource.setTestWhileIdle(testWhileIdle);
        dataSource.setTestOnBorrow(testOnBorrow);
        dataSource.setTestOnReturn(testOnReturn);
        try {
            dataSource.setFilters(filters);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    @Bean
    public DataSourceTransactionManager transactionManagerManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //设置mapper的xml路径
        Resource[] resources = getMapperResource();
        if(resources != null){
            bean.setMapperLocations(resources);
        }
        //mybatis plus配置
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        //锁机制
        mybatisPlusInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        //分页机制
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        Interceptor[] interceptors = {mybatisPlusInterceptor};
        bean.setPlugins(interceptors);
        GlobalConfig config = new GlobalConfig();
        //插入数据字段预处理
        config.setMetaObjectHandler(new MyBatisPlusObjectHandler());
        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        //主键策略
        dbConfig.setIdType(IdType.AUTO);
//        dbConfig.setDbType(DbType.MYSQL);
        config.setDbConfig(dbConfig);
        bean.setGlobalConfig(config);
        //字段下划线映射bean以驼峰模式
        bean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        return bean.getObject();
    }

    /**
     * @description 注册druid过滤器
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        bean.addUrlPatterns("/*");
        bean.addInitParameter("exclusions", "*.html,*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        bean.addInitParameter("profileEnable", "true");
        bean.addInitParameter("principalCookieName", "USER_COOKIE");
        bean.addInitParameter("principalSessionName", "USER_SESSION");
        return bean;
    }

    /**
     * @description 注册druid的统计Servlet
     * @return ServletRegistrationBean
     */
    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean reg = new ServletRegistrationBean();
        reg.setServlet(new StatViewServlet());
        reg.addUrlMappings("/druid/*");
        Map<String, Object> initParams = new HashMap<String, Object>();
        initParams.put("loginUsername", username);
        initParams.put("loginPassword", password);
        initParams.put("logSlowSql", logSlowSql);
        reg.setInitParameters(initParams);
        return reg;
    }

    /**
     * @description mapper的xml路径处理，把String[]转Resource[]
     * @return Resource[]
     */
    protected Resource[] getMapperResource(){
        try {
            if(DATASOURCE_MAPPER_LOACTIONS != null && DATASOURCE_MAPPER_LOACTIONS.length > 0) {
                List<Resource> resourceList = new ArrayList<>();
                for (String location : DATASOURCE_MAPPER_LOACTIONS) {
                    Resource[] resourceArr = new Resource[0];
                    resourceArr = new PathMatchingResourcePatternResolver().getResources(location);
                    for (Resource resource : resourceArr) {
                        resourceList.add(resource);
                    }
                }
                Resource[] resources = new Resource[resourceList.size()];
                for (int i = 0; i < resourceList.size(); i++) {
                    resources[i] = resourceList.get(i);
                }
                return resources;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
