package com.tlu.khoacntt.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LecturerRequest {

    @NotBlank(message = "Tên giảng viên không được để trống")
    @Size(max = 100, message = "Tên không được quá 100 ký tự")
    @NonNull
    private String name;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    @Size(max = 100, message = "Email không được quá 100 ký tự")
    private String email;

    private String avatar;

    @NotBlank(message = "Học vị không được để trống")
    private String degree;

    @NotBlank(message = "Chức vụ không được để trống")
    private String position;

    private String specialization;

    private Integer departmentId;

    @Pattern(regexp = "^\\d{10,11}$", message = "Số điện thoại không hợp lệ")
    private String phone;

    private String researchArea;

    private String bio;
}