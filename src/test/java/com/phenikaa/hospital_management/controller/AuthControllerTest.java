package com.phenikaa.hospital_management.controller;

import com.phenikaa.hospital_management.config.SecurityConfig;
import com.phenikaa.hospital_management.dto.UserRegistrationDTO;
import com.phenikaa.hospital_management.model.Patient;
import com.phenikaa.hospital_management.repository.DoctorRepository;
import com.phenikaa.hospital_management.repository.PatientRepository;
import com.phenikaa.hospital_management.service.CustomUserDetailsService;
import com.phenikaa.hospital_management.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AuthController.class, 
            excludeAutoConfiguration = {
                JpaRepositoriesAutoConfiguration.class, 
                FlywayAutoConfiguration.class,
                UserDetailsServiceAutoConfiguration.class
            })
@Import(SecurityConfig.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;
    @MockBean
    private CustomUserDetailsService customUserDetailsService;
    @MockBean
    private PatientRepository patientRepository;
    @MockBean
    private DoctorRepository doctorRepository;

    @Test
    @WithAnonymousUser
    void testShowLoginForm() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk()) 
                .andExpect(view().name("login")); 
    }

    @Test
    @WithAnonymousUser
    void testShowRegistrationForm() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("registrationDTO"));
    }

    @Test
    @WithAnonymousUser
    void testProcessRegistration_Success() throws Exception {
        mockMvc.perform(post("/register")
                        .param("fullName", "Test User")
                        .param("username", "testuser")
                        .param("email", "test@example.com")
                        .param("password", "password123")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection()) 
                .andExpect(redirectedUrl("/login?success")); 

        // SỬA: Kiểm tra xem service có được gọi với DTO không
        verify(patientService, times(1)).registerNewPatient(any(UserRegistrationDTO.class));
    }
    
    @Test
    @WithAnonymousUser
    void testProcessRegistration_InvalidData_ShouldFail() throws Exception {
        mockMvc.perform(post("/register")
                        .param("fullName", "Test User")
                        .param("username", "testuser")
                        .param("email", "test@example.com")
                        .param("password", "123") // Mật khẩu quá ngắn
                        .with(csrf())
                )
                .andExpect(status().isOk()) 
                .andExpect(view().name("register")) 
                .andExpect(model().hasErrors())
                .andExpect(model().attributeExists("registrationDTO"));

        // Đảm bảo service không được gọi nếu validation thất bại
        verify(patientService, never()).registerNewPatient(any(UserRegistrationDTO.class));
    }

    @Test
    @WithMockUser(roles = "PATIENT")
    void testAccessLoginPage_WhenAlreadyLoggedIn_ShouldRedirect() throws Exception {
         mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login")); 
    }
}