package com.batch.api;

import com.batch.model.JobRequest;
import com.batch.model.JobResponse;
import com.batch.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sunil
 * @project spring-batch-app
 * @created 2021/06/20 4:51 PM
 */

@Slf4j
@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    private JobService jobService;

    @GetMapping("/first")
    public JobResponse firstJob() {
        JobRequest jobRequest = new JobRequest();
        jobRequest.setDesc("First Job");
        jobRequest.setName("FIRST");

        JobExecution jobExecution = jobService.runJob(jobRequest);
        return finalResponse(jobExecution);
    }

    @GetMapping("/second")
    public JobResponse secondJob() {
        JobRequest jobRequest = new JobRequest();
        jobRequest.setDesc("Second Job");
        jobRequest.setName("second");

        JobExecution jobExecution = jobService.runJob(jobRequest);
        return finalResponse(jobExecution);
    }

    JobResponse finalResponse(JobExecution execution) {
        return JobResponse.builder()
                .jobId(execution.getJobId())
                .executionId(execution.getId())
                .code(execution.getExitStatus().getExitCode())
                .status(execution.getStatus().toString())
                .message(execution.getExitStatus().getExitDescription())
                .build();
    }

}
