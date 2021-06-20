package com.batch.service;

import com.batch.job.AbstractJob;
import com.batch.model.JobRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ResourceLoader;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import java.util.UUID;

/**
 * @author sunil
 * @project spring-batch-app
 * @created 2021/06/20 5:02 PM
 */
@Slf4j
@Service
public class JobService {

    private ApplicationContext ctx;
    private ResourceLoader resourceLoader;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final JobLauncher jobLauncher;

    @Autowired
    public JobService(ApplicationContext ctx, ResourceLoader resourceLoader,
                      JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
                      JobLauncher jobLauncher) {
        this.ctx = ctx;
        this.resourceLoader = resourceLoader;
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.jobLauncher = jobLauncher;
    }


    public JobExecution runJob(JobRequest jobRequest) {
        AbstractJob job = ctx.getBean(AbstractJob.class, jobRequest);
        return  execute(job.getName(), (stepContribution, chunkContext) -> {
            runStep(job);
            return null;
        });
    }

    void runStep(AbstractJob job) {
        job.run(resourceLoader);
    }


    private JobExecution execute(String jobName, Tasklet tasklet) {

        Step step = stepBuilderFactory.get("step").tasklet(tasklet).build();
        Job job = jobBuilderFactory.get(jobName).start(step).build();
        JobExecution jobExecution = null;
        try {
            JobParametersBuilder parametersBuilder = new JobParametersBuilder();
            parametersBuilder.addString ("uuid",  UUID.randomUUID().toString());

            jobExecution =jobLauncher.run(job, parametersBuilder.toJobParameters());

            return jobExecution;

        } catch (Exception e) {
            log.error("Job Execution Failed", e);
            return null;
        }
    }

}
