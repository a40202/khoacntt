package com.tlu.khoacntt.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {

    @NotBlank(message = "Tiêu đề không được để trống")
    @Size(max = 255, message = "Tiêu đề không được quá 255 ký tự")
    private String title;

    private String slug;

    @NotBlank(message = "Nội dung bài viết không được để trống")
    private String content;

    private String thumbnail;

    private String status = "published";

    @NotNull(message = "Vui lòng chọn danh mục bài viết")
    private Integer categoryId;

    @NotBlank(message = "ID người đăng không được để trống")
    private String adminId;


}