package com.batch.model;

import lombok.*;

/**
 * @author sunil
 * @project spring-batch-app
 * @created 2021/06/20 6:00 PM
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class JobRequest {
    String name;
    String desc;
}
