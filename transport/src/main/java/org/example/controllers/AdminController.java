package org.example.controllers;


import org.example.DtoS.AdminDTO;
import org.example.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping
    public ResponseEntity<AdminDTO> createAdmin(@RequestBody AdminDTO adminDTO) {
        // Logique de création d'un administrateur
        AdminDTO admin = adminService.createAdmin(adminDTO);
        return ResponseEntity.ok(admin);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminDTO> getAdmin(@PathVariable Long id) {
        // Logique de récupération d'un administrateur
        AdminDTO admin = adminService.getAdmin(id);
        return ResponseEntity.ok(admin);
    }
    @GetMapping
    public ResponseEntity<List<AdminDTO>> getAllAdmins() {
        List<AdminDTO> admins = adminService.getAllAdmins();
        return ResponseEntity.ok(admins);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdminDTO> updateAdmin(@PathVariable Long id, @RequestBody AdminDTO adminDTO) {
        // Logique de mise à jour d'un administrateur
        AdminDTO admin = adminService.updateAdmin(id, adminDTO);
        return ResponseEntity.ok(admin);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        // Logique de suppression d'un administrateur
        adminService.deleteAdmin(id);
        return ResponseEntity.noContent().build();

    }


}
