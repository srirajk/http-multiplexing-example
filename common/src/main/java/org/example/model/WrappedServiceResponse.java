package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WrappedServiceResponse {

    private SampleResponse sampleResponse;
    private boolean isError;
    private Exception exception;

}
