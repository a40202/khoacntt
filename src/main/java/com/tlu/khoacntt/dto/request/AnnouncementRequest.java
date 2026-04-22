package com.tlu.khoacntt.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AnnouncementRequest {

    @NotBlank(message = "Tiêu đề không được để trống")
    @Size(max = 255, message = "Tiêu đề tối đa 255 ký tự")
    private String title;

    @NotBlank(message = "Nội dung không được để trống")
    private String content;

    private String status = "published";

    // hoc-tap | thi-lai | thuc-tap | hoc-phi | tot-nghiep | khac
    private String type = "khac";

    @NotBlank(message = "ID người đăng không được để trống")
    private String adminId;
}
