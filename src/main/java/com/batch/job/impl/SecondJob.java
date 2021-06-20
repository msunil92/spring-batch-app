package com.batch.job.impl;

import com.batch.job.AbstractJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ResourceLoader;

/**
 * @author sunil
 * @project spring-batch-app
 * @created 2021/06/20 4:53 PM
 */

@Slf4j
public class SecondJob extends AbstractJob {

    public SecondJob(String name) {
        super(name);
    }

    public SecondJob(String name, String jobType) {
        super(name, jobType);
    }

    @Override
    protected void execute(ResourceLoader resourceLoader) throws Exception {

        System.out.println("Second Job How are you??!!");
        // write logic

    }
}
