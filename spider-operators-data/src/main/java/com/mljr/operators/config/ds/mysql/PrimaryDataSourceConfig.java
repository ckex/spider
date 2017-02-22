package com.mljr.operators.config.ds.mysql;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * @author gaoxi
 * @time 2017/2/22
 */
@Configuration
@MapperScan(basePackages = PrimaryDataSourceConfig.MYBATIS_DAO_PACKAGE_PATH, sqlSessionFactoryRef = "primarySqlSessionFactory")
public class PrimaryDataSourceConfig {

    static final String MYBATIS_DAO_PACKAGE_PATH = "com.mljr.operators.dao.primary";

    static final String MYBATIS_MAPPER_XML_PATH = "classpath:mapper/mybatis/primary/**/*Mapper.xml";

    static final String MYBATIS_CONFIG_LOCATION_PATH = "classpath:mybatis/mybatis-config.xml";

    @Value("${datasource.primary.driverClassName}")

    private String driverClassName;

    @Value("${datasource.primary.url}")
    private String url;

    @Value("${datasource.primary.username}")
    private String username;

    @Value("${datasource.primary.password}")
    private String password;

    @Bean(initMethod = "init", destroyMethod = "close", name = "primaryDataSource")
    @Primary
    public DruidDataSource primaryDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setInitialSize(5);
        dataSource.setMinIdle(5);
        dataSource.setMaxActive(200);
        dataSource.setMaxWait(60000);
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        dataSource.setMinEvictableIdleTimeMillis(300000);
        dataSource.setValidationQuery("SELECT 'x'");
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setPoolPreparedStatements(true);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
        return dataSource;
    }

    @Bean(name = "primaryTransactionManager")
    @Primary
    public DataSourceTransactionManager primaryTransactionManager(@Qualifier("primaryDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "primarySqlSessionFactory")
    @Primary
    public SqlSessionFactoryBean primarySqlSessionFactory(@Qualifier("primaryDataSource") DataSource dataSource) throws IOException {
        final SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactoryBean.setConfigLocation(resolver.getResource(MYBATIS_CONFIG_LOCATION_PATH));
        sessionFactoryBean.setMapperLocations(resolver.getResources(MYBATIS_MAPPER_XML_PATH));
        return sessionFactoryBean;
    }
}
