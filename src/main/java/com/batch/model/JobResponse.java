package com.batch.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author sunil
 * @project spring-batch-app
 * @created 2021/06/20 5:01 PM
 */


@Data
@Builder
public class JobResponse {
    private Long jobId;
    private Long executionId;
    private String code;
    private String status;
    private String message;
}
