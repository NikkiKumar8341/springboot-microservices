package com.example.security_basic.dto.courseMaterial;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VideoDTO {

    private String videoId;
    private String title;
    private String url;
}

