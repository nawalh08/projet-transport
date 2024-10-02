package org.example.service;

import org.example.DtoS.AdminDTO;
import org.example.entite.Admin;
import org.example.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AdminDTO createAdmin(AdminDTO adminDTO) {
        // Logique de création d'un administrateur
        Admin admin = new Admin();
        admin.setFirstName(adminDTO.getFirstName());
        admin.setLastName(adminDTO.getLastName());
        admin.setEmail(adminDTO.getEmail());
        admin.setPassword(passwordEncoder.encode(adminDTO.getPassword()));

        Admin savedAdmin = adminRepository.save(admin);
        return convertToDTO(savedAdmin);
    }

    public AdminDTO getAdmin(Long id) {
        // Logique de récupération d'un administrateur
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new ExpressionException("Admin not found with id: " + id));
        return convertToDTO(admin);
    }
    public List<AdminDTO> getAllAdmins() {
        List<Admin> admins = adminRepository.findAll();
        return admins.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public AdminDTO updateAdmin(Long id, AdminDTO adminDTO) {
        // Logique de mise à jour d'un administrateur
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new ExpressionException("Admin not found with id: " + id));

        admin.setFirstName(adminDTO.getFirstName());
        admin.setLastName(adminDTO.getLastName());
        admin.setEmail(adminDTO.getEmail());
        if (adminDTO.getPassword() != null) {
            admin.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
        }

        Admin updatedAdmin = adminRepository.save(admin);
        return convertToDTO(updatedAdmin);
    }

    public void deleteAdmin(Long id) {
        // Logique de suppression d'un administrateur
        if (!adminRepository.existsById(id)) {
            throw new ExpressionException("Admin not found with id: " + id);
        }
        adminRepository.deleteById(id);
    }

    public boolean login(String email, String password) {
        // Logique de connexion
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new ExpressionException("Admin not found with email: " + email));
        return passwordEncoder.matches(password, admin.getPassword());
    }

    private AdminDTO convertToDTO(Admin admin) {
        AdminDTO dto = new AdminDTO();
        dto.setId(admin.getId());
        dto.setFirstName(admin.getFirstName());
        dto.setLastName(admin.getLastName());
        dto.setEmail(admin.getEmail());
        return dto;
    }

}
