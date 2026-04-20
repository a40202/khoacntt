package com.tlu.khoacntt.service;

import com.tlu.khoacntt.dto.request.AdminRequest;
import com.tlu.khoacntt.dto.response.AdminResponse;

import java.util.List;

public interface AdminService {
    // Trả về AdminResponse để giấu mật khẩu, nhận vào AdminRequest để bảo mật đầu vào
    AdminResponse createAdmin(AdminRequest request);

    // Cập nhật Admin dựa trên ID và dữ liệu từ Request
    AdminResponse updateAdmin(String id, AdminRequest request);

    // Xóa Admin
    void deleteAdmin(String id);

    // Lấy chi tiết 1 Admin theo ID
    AdminResponse getAdminById(String id);

    // Lấy danh sách toàn bộ Admin
    List<AdminResponse> getAllAdmins();

    // Tìm kiếm theo Username
    AdminResponse getAdminByUsername(String username);
}