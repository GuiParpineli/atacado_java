package com.wf.apiwf;

import com.wf.apiwf.entity.SystemUser;
import com.wf.apiwf.repository.IUserRepository;
import com.wf.apiwf.security.SystemUserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private final IUserRepository userRepository;

    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public DataLoader(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        bCryptPasswordEncoder = new BCryptPasswordEncoder();

        SystemUser userVendor = SystemUser.builder()
                .name("vendor")
                .username("vendor")
                .email("vendor@email.com")
                .password(bCryptPasswordEncoder.encode("vendor"))
                .systemUserRoles(SystemUserRoles.ROLE_VENDOR)
                .build();

        SystemUser userCustomer= SystemUser.builder()
                .name("customer")
                .username("customer")
                .email("customer@email.com")
                .password(bCryptPasswordEncoder.encode("customer"))
                .systemUserRoles(SystemUserRoles.ROLE_CLIENT)
                .build();

        SystemUser userAdmin= SystemUser.builder()
                .name("admin")
                .username("admin")
                .email("admin@email.com")
                .password(bCryptPasswordEncoder.encode("admin"))
                .systemUserRoles(SystemUserRoles.ROLE_ADMIN)
                .build();

        userRepository.save(userVendor);
        userRepository.save(userCustomer);
        userRepository.save(userAdmin);

    }

}
