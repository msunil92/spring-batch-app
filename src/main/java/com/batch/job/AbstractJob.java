package com.batch.job;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ResourceLoader;

/**
 * @author sunil
 * @project spring-batch-app
 * @created 2021/06/20 4:52 PM
 */

@Slf4j
@Getter
@Setter
public abstract class AbstractJob {

    private String name;
    private String jobType;

    public AbstractJob(String name) {
        this.name = name;
    }

    public AbstractJob(String name, String jobType) {
        this.name = name;
        this.jobType = jobType;
    }

    public void run(ResourceLoader resourceLoader) {

        try {
            execute(resourceLoader);
            log.info("Heap Size after execution" + Runtime.getRuntime().totalMemory());
        } catch (Exception e) {
            throw new RuntimeException("Can't execute job " + name, e);
        } finally {
        }
    }

    protected abstract void execute(ResourceLoader resourceLoader) throws Exception;

}
