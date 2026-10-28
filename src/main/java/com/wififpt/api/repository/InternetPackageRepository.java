
package com.wififpt.api.repository;

import com.wififpt.api.entity.InternetPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InternetPackageRepository
        extends JpaRepository<InternetPackage, Long> {

    List<InternetPackage> findByActiveTrueOrderByDisplayOrderAsc();

    List<InternetPackage> findAllByOrderByDisplayOrderAsc();

    boolean existsByCode(String code);
}
