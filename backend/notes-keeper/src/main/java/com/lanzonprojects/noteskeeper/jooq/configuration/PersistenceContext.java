package com.lanzonprojects.noteskeeper.jooq.configuration;

import org.jooq.SQLDialect;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Documentation of Java-based configuration:
 * - https://www.petrikainulainen.net/programming/jooq/using-jooq-with-spring-configuration/
 *
 * @author lanzon-projects.
 */
@Configuration
@ComponentScan({"com.lanzonprojects.noteskeeper"})
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class PersistenceContext {

    @Autowired
    private Environment env;

    /**
     * Using standard Tomcat dataSource to set the connection to the one provided in `application.properties`.
     *
     * @return DataSource bean configured to connect to the specified connection.
     */
    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();

        dataSource.setUrl(env.getRequiredProperty("db.url"));
        dataSource.setUsername(env.getRequiredProperty("db.user"));
        dataSource.setPassword(env.getRequiredProperty("db.password"));
        dataSource.setDriverClassName(env.getRequiredProperty("db.driver"));

        return dataSource;
    }

    /**
     * This bean ensures that the database connection are fetched lazily (i.e. when the first statement is created).
     *
     * @return Bean to ensure lazy connection fetching.
     */
    @Bean
    public LazyConnectionDataSourceProxy lazyConnectionDataSource() {
        return new LazyConnectionDataSourceProxy(dataSource());
    }

    /**
     * This bean ensures that all JDBC connection are aware of Spring-managed transactions. In other words,
     * JDBC connections participate in thread-bound transactions.
     *
     * @return Spring-managed transactions for all JDBC connections.
     */
    @Bean
    public TransactionAwareDataSourceProxy transactionAwareDataSource() {
        return new TransactionAwareDataSourceProxy(lazyConnectionDataSource());
    }

    /**
     * We must pass the LazyConnectionDataSourceProxy bean as as constructor argument when we create a new
     * DataSourceTransactionManager object.
     *
     * @return Interface to handle transactional queries against the dataSource.
     */
    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(lazyConnectionDataSource());
    }

    /**
     * We must pass the LazyConnectionDataSourceProxy bean as as constructor argument when we create a new
     * DataSourceTransactionManager object.
     *
     * @return Interface to get used connections from the DataSource.
     */
    @Bean
    public DataSourceConnectionProvider connectionProvider() {
        return new DataSourceConnectionProvider(transactionAwareDataSource());
    }

    /**
     * Transformer errors sent out by jOOQ which is in our control.
     *
     * @return Transformer bean.
     */
    @Bean
    public JOOQToSpringExceptionTransformer jooqToSpringExceptionTransformer() {
        return new JOOQToSpringExceptionTransformer();
    }

    /**
     * This class is the default implementation of the Configuration interface, and we can use it to configure jOOQ.
     * We have to configure three things:
     * <p>
     * 1. We have to set the ConnectionProvider which is used to obtain and release database connections.
     * 2. We have to configure the custom execute listeners. In other words, we have to add
     * JOOQToSpringExceptionTransformer bean to the created DefaultConfiguration object. This ensures that the
     * exceptions thrown by jOOQ are transformed into Spring DataAccessExceptions.
     * 3. We have to configure the used SQL dialect.
     *
     * @return Bean containing the configuration properties which is used to setup the connection to the database.
     */
    @Bean
    public DefaultConfiguration configuration() {
        DefaultConfiguration jooqConfiguration = new DefaultConfiguration();

        jooqConfiguration.set(connectionProvider());
        jooqConfiguration.set(new DefaultExecuteListenerProvider(jooqToSpringExceptionTransformer()));

        String sqlDialectName = env.getRequiredProperty("spring.jooq.sql-dialect");
        SQLDialect dialect = SQLDialect.valueOf(sqlDialectName);
        jooqConfiguration.set(dialect);

        return jooqConfiguration;
    }

    /**
     * We use this bean when we are creating database queries with jOOQ.
     *
     * @return Interface (connection) to the Database.
     */
    @Bean
    public DefaultDSLContext dsl() {
        return new DefaultDSLContext(configuration());
    }

    /**
     * We use this bean to create the database schema of the H2 database when our application is started (If you don’t
     * use an embedded database, you don’t have to configure this bean).
     *
     * @return Bean to execute all the scripts stored in the resources folder to be used in the embedded database.
     */
    @Bean
    public DataSourceInitializer dataSourceInitializer() {
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource());

        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(
            new ClassPathResource(env.getRequiredProperty("db.schema.script")),
            new ClassPathResource(env.getRequiredProperty("db.data.script"))
        );

        initializer.setDatabasePopulator(populator);
        return initializer;
    }
}
