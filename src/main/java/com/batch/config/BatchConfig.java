package com.batch.config;

import com.batch.job.AbstractJob;
import com.batch.job.impl.FirstJob;
import com.batch.job.impl.SecondJob;
import com.batch.model.JobRequest;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * @author sunil
 * @project spring-batch-app
 * @created 2021/06/20 4:46 PM
 */

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Bean
    @Scope("prototype")
    public AbstractJob job(JobRequest jobRequest) {
        AbstractJob job = null;

        if (jobRequest.getName().equalsIgnoreCase("FIRST"))
            job = new FirstJob(JobName.FIRST_JOB.name(), JobType.FIRST.name());
        else if (jobRequest.getName().equalsIgnoreCase("SECOND"))
        job = new SecondJob(JobName.FIRST_JOB.name(), JobType.FIRST.name());
        return job;

    }

    @Bean
    @ConfigurationProperties("oracle")
    public DataSource oracleDBConnection(@Value("${oracle.db.url}") String dbUrl,
                                          @Value("${oracle.db.username}") String dbUser,
                                          @Value("${oracle.db.password}") String dbPassword,
                                          @Value("${oracle.db.driver}") String driver) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUser);
        dataSource.setPassword(dbPassword);

        return dataSource;
    }
//
//    @Bean
//    @ConfigurationProperties("postgres")
//    public DataSource postgresDBConnection(@Value("${postgres.db.url}") String dbUrl,
//                                         @Value("${postgres.db.username}") String dbUser,
//                                         @Value("${postgres.db.password}") String dbPassword,
//                                         @Value("${postgres.db.driver}") String driver) {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(driver);
//        dataSource.setUrl(dbUrl);
//        dataSource.setUsername(dbUser);
//        dataSource.setPassword(dbPassword);
//
//        return dataSource;
//    }

    public enum JobType {
        FIRST
    }

    public enum JobName {
        FIRST_JOB
    }
}
