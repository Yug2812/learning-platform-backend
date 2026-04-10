package com.learning.platform.config;

import com.learning.platform.model.ERole;
import com.learning.platform.model.Role;
import com.learning.platform.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0) {
            Role adminRole = new Role();
            adminRole.setName(ERole.ROLE_ADMIN);
            roleRepository.save(adminRole);

            Role studentRole = new Role();
            studentRole.setName(ERole.ROLE_STUDENT);
            roleRepository.save(studentRole);
            
            Role teacherRole = new Role();
            teacherRole.setName(ERole.ROLE_TEACHER);
            roleRepository.save(teacherRole);

            System.out.println("Default roles added to the database.");
        } else if (!roleRepository.findByName(ERole.ROLE_TEACHER).isPresent()) {
            Role teacherRole = new Role();
            teacherRole.setName(ERole.ROLE_TEACHER);
            roleRepository.save(teacherRole);
            System.out.println("Teacher role added to the database.");
        }
    }
}
