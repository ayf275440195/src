package com.ayf.springboot_datasources_switch.config;

import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.ayf.springboot_datasources_switch.entity.DataSourceEntity;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : heibaiying
 * @description : 多数据源配置
 */
@Configuration
@MapperScan(basePackages = DataSourceFactory.BASE_PACKAGES, sqlSessionTemplateRef = "sqlSessionTemplate")
public class DataSourceFactory {

    static final String BASE_PACKAGES = "com.ayf.springboot_datasources_switch.mapper*";

    private static final String MAPPER_LOCATION = "classpath:mybatis/**/**/*.xml";


    /***
     * 创建 DruidXADataSource 1 用@ConfigurationProperties自动配置属性
     */
    @Bean
    @ConfigurationProperties("spring.datasource.druid.primary")
    public DruidXADataSource druidDataSourceOne() {
        return new DruidXADataSource();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.druid.common")
    public DruidXADataSource commonDataSource() {
        return new DruidXADataSource();
    }
    /***
     * 创建 DruidXADataSource 2
     */
    @Bean
    @ConfigurationProperties("spring.datasource.druid.secondary")
    public DataSource druidDataSourceTwo() {
        return new DruidXADataSource();
    }

    /**
     * 创建支持XA事务的Atomikos数据源1
     */
    @Bean
    public DataSource dataSourceOne(DataSource druidDataSourceOne) {
        AtomikosDataSourceBean sourceBean = new AtomikosDataSourceBean();
        sourceBean.setXaDataSource((DruidXADataSource) druidDataSourceOne);
        // 必须为数据源指定唯一标识
        sourceBean.setUniqueResourceName("primary");
        sourceBean.setXaDataSourceClassName("com.alibaba.druid.pool.xa.DruidXADataSource");
        sourceBean.setPoolSize(10);
        sourceBean.setTestQuery("SELECT 1");
        sourceBean.setReapTimeout(20000);
        sourceBean.setMaxLifetime(60);
        return sourceBean;
    }

    /**
     * 创建支持XA事务的Atomikos数据源2
     */
    @Bean
    public DataSource dataSourceTwo(DataSource druidDataSourceTwo) {
        AtomikosDataSourceBean sourceBean = new AtomikosDataSourceBean();
        sourceBean.setXaDataSource((DruidXADataSource) druidDataSourceTwo);
        sourceBean.setUniqueResourceName("secondary");
        sourceBean.setXaDataSourceClassName("com.alibaba.druid.pool.xa.DruidXADataSource");
        sourceBean.setPoolSize(10);
        sourceBean.setTestQuery("SELECT 1");
        sourceBean.setReapTimeout(20000);
        sourceBean.setMaxLifetime(60);
        return sourceBean;
    }


    /**
     * @param dataSourceOne 数据源1
     * @return 数据源1的会话工厂
     */
    @Bean
    public SqlSessionFactory sqlSessionFactoryOne(DataSource dataSourceOne)
            throws Exception {
        return createSqlSessionFactory(dataSourceOne);
    }


    /**
     * @param dataSourceTwo 数据源2
     * @return 数据源2的会话工厂
     */
    @Bean
    public SqlSessionFactory sqlSessionFactoryTwo(DataSource dataSourceTwo)
            throws Exception {
        return createSqlSessionFactory(dataSourceTwo);
    }


    /***
     * sqlSessionTemplate与Spring事务管理一起使用，以确保使用的实际SqlSession是与当前Spring事务关联的,
     * 此外它还管理会话生命周期，包括根据Spring事务配置根据需要关闭，提交或回滚会话
     * @param sqlSessionFactoryOne 数据源1
     * @param sqlSessionFactoryTwo 数据源2
     */
    @Bean
    public CustomSqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactoryOne,
                                                       SqlSessionFactory sqlSessionFactoryTwo) {
        Map<Object, SqlSessionFactory> sqlSessionFactoryMap = new HashMap<>();

        /**
         * 配置文件里取数据源
         */
        sqlSessionFactoryMap.put("adatasource", sqlSessionFactoryOne);
        sqlSessionFactoryMap.put("bdatasource", sqlSessionFactoryTwo);

        /**
         * 查询配置数据源
         */
        setsqlSessionFactory(sqlSessionFactoryMap);


        CustomSqlSessionTemplate customSqlSessionTemplate = new CustomSqlSessionTemplate(sqlSessionFactoryOne);
        customSqlSessionTemplate.setTargetSqlSessionFactories(sqlSessionFactoryMap);
        return customSqlSessionTemplate;
    }

    private void setsqlSessionFactory(Map<Object, SqlSessionFactory> sqlSessionFactoryMap) {
        List<DataSourceEntity> list = new ArrayList<>();
        for (DataSourceEntity dataSourceEntity : list){
            DruidXADataSource dataSource =  commonDataSource();
            dataSource.setUrl(dataSourceEntity.getUrl());
            dataSource.setUsername(dataSourceEntity.getUserName());
            dataSource.setPassword(dataSourceEntity.getPassword());
        }
    }


    /***
     * 自定义会话工厂
     * @param dataSource 数据源
     * @return :自定义的会话工厂
     */
    private SqlSessionFactory createSqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));
        // 其他可配置项(包括是否打印sql,是否开启驼峰命名等)
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setLogImpl(StdOutImpl.class);
        factoryBean.setConfiguration(configuration);
        /* *
         * 采用个如下方式配置属性的时候一定要保证已经进行数据源的配置(setDataSource)和数据源和MapperLocation配置(setMapperLocations)
         * factoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
         * factoryBean.getObject().getConfiguration().setLogImpl(StdOutImpl.class);
         **/
        return factoryBean.getObject();
    }

/*    @Bean
    public DataSourceTransactionManager transactionManager() {

        return new DataSourceTransactionManager();
    }*/




}
