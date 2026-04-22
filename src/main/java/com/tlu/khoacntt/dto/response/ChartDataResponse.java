package com.tlu.khoacntt.dto.response;

import lombok.*;
import java.util.List;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ChartDataResponse {
    private List<String> labels;   
    private List<Long> postCounts; 
}
