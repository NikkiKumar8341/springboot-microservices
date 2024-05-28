package com.example.coursematerial.dto;


import lombok.*;

import java.util.UUID;

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
