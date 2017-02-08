package com.jund.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;


@Configuration
@EnableAspectJAutoProxy
@EnableTransactionManagement
@ComponentScan(basePackages = "com.jund", nameGenerator = AnnotationBeanNameGenerator.class)
@PropertySource({"classpath:/jdbc.properties"})
public class AppConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppConfig.class);

    @Autowired
    private Environment environment;

    @Bean(destroyMethod = "close")
    public DataSource dataSourceProvider() {
        String poolType = environment.getProperty("pool.type");
        if ("dbcp".equals(poolType)) {
            return dbcpDataSource();
        }
        return comboPooledDataSource();
    }

    private DataSource comboPooledDataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();

        try {
            dataSource.setDriverClass(environment.getProperty("jdbc.driverClass"));
        } catch (PropertyVetoException e) {
            LOGGER.error(e.getMessage());
        }

        dataSource.setJdbcUrl(environment.getProperty("jdbc.jdbcUrl"));
        dataSource.setUser(environment.getProperty("jdbc.username"));
        dataSource.setPassword(environment.getProperty("jdbc.password"));
        dataSource.setAutoCommitOnClose(false);
        dataSource.setAcquireIncrement(environment.getProperty("c3p0.acquireIncrement", Integer.class));
        dataSource.setMaxIdleTime(environment.getProperty("c3p0.maxIdleTime", Integer.class));
        dataSource.setMaxPoolSize(environment.getProperty("c3p0.maxPoolSize", Integer.class));
        dataSource.setMinPoolSize(environment.getProperty("c3p0.minPoolSize", Integer.class));
        dataSource.setNumHelperThreads(environment.getProperty("c3p0.numHelperThreads", Integer.class));

        // 检查checkout的连接超时值
        dataSource.setUnreturnedConnectionTimeout(10);
        //  检查checkout的连接时间超时，打印日志
        dataSource.setDebugUnreturnedConnectionStackTraces(true);

        return dataSource;
    }

    private DataSource dbcpDataSource() {
        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setDriverClassName(environment.getProperty("jdbc.driverClass"));
        dataSource.setUrl(environment.getProperty("jdbc.jdbcUrl"));
        dataSource.setUsername(environment.getProperty("jdbc.username"));
        dataSource.setPassword(environment.getProperty("jdbc.password"));
        dataSource.setDefaultAutoCommit(true);

        dataSource.setInitialSize(environment.getProperty("dbcp.initialSize", Integer.class));
        dataSource.setMaxActive(environment.getProperty("dbcp.maxActive", Integer.class));
        dataSource.setMaxIdle(environment.getProperty("dbcp.maxIdle", Integer.class));
        dataSource.setMaxWait(environment.getProperty("dbcp.maxWait", Integer.class));
        dataSource.setRemoveAbandoned(environment.getProperty("dbcp.removeAbandoned", Boolean.class));
        dataSource.setRemoveAbandonedTimeout(environment.getProperty("dbcp.removeAbandonedTimeout", Integer.class));
        dataSource.setLogAbandoned(true);
        dataSource.setValidationQuery(environment.getProperty("dbcp.validationQuery"));
        return dataSource;
    }

    @Bean
    public DataSource dataSource() {
        return dataSourceProvider();
    }

    @Bean
    public AnnotationSessionFactoryBean sessionFactoryBean() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
        properties.put("hibernate.format_sql", environment.getProperty("hibernate.format_sql"));

        AnnotationSessionFactoryBean sessionFactoryBean = new AnnotationSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setHibernateProperties(properties);

        String[] packagesToScan = {"com.jund.**.entity"};
        sessionFactoryBean.setPackagesToScan(packagesToScan);
        return sessionFactoryBean;
    }

    @Bean
    public SessionFactory sessionFactory() {
        return sessionFactoryBean().getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new HibernateTransactionManager(sessionFactoryBean().getObject());
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new StandardPasswordEncoder();
    }
}
