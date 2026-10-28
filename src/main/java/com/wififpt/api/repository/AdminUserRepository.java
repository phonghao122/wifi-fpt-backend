
package com.wififpt.api.repository;

import com.wififpt.api.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminUserRepository
        extends JpaRepository<AdminUser, Long> {

    Optional<AdminUser> findByUsername(String username);
}
