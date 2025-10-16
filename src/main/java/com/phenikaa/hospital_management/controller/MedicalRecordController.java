package com.phenikaa.hospital_management.controller;

import com.phenikaa.hospital_management.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    /**
     * Phương thức này xử lý yêu cầu GET để HIỂN THỊ form tạo bệnh án.
     * @param appointmentId ID của cuộc hẹn đã hoàn thành.
     * @param model Dùng để truyền dữ liệu sang cho View (trang HTML).
     * @return Tên của file HTML template.
     */
    @GetMapping("/records/create-for-appointment/{appointmentId}")
    public String showCreateRecordForm(@PathVariable Long appointmentId, Model model) {
        // Truyền appointmentId sang cho view để form biết cần tạo bệnh án cho cuộc hẹn nào
        model.addAttribute("appointmentId", appointmentId);
        return "create-medical-record"; // Trả về file create-medical-record.html
    }

    /**
     * Phương thức này xử lý yêu cầu POST khi người dùng SUBMIT form.
     * @param appointmentId ID của cuộc hẹn.
     * @param diagnosis Dữ liệu chẩn đoán từ form.
     * @param prescription Dữ liệu đơn thuốc từ form.
     * @param redirectAttributes Dùng để gửi thông báo sau khi chuyển hướng.
     * @return Chuyển hướng người dùng đến một trang khác.
     */
    @PostMapping("/records/create-for-appointment/{appointmentId}")
    public String processCreateRecord(@PathVariable Long appointmentId,
                                      @RequestParam String diagnosis,
                                      @RequestParam String prescription,
                                      RedirectAttributes redirectAttributes) {
        try {
            medicalRecordService.createMedicalRecord(appointmentId, diagnosis, prescription);
            // Thêm một thông báo thành công để hiển thị ở trang tiếp theo
            redirectAttributes.addFlashAttribute("successMessage", "Tạo bệnh án thành công!");
        } catch (Exception e) {
            // Thêm thông báo lỗi
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi: " + e.getMessage());
        }
        // Sau khi tạo xong, chuyển hướng về trang dashboard (ví dụ)
        return "redirect:/doctor/dashboard";
    }
}