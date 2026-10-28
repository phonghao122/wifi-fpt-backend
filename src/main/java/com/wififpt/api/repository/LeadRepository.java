
package com.wififpt.api.repository;

import com.wififpt.api.entity.Lead;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LeadRepository
        extends JpaRepository<Lead, Long> {

    // 1. Tìm kiếm khách hàng theo tên hoặc số điện thoại
    @Query("""
        SELECT l
        FROM Lead l
        WHERE LOWER(l.fullName) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR l.phone LIKE CONCAT('%', :keyword, '%')
        """)
    Page<Lead> searchLeads(
            @Param("keyword") String keyword,
            Pageable pageable
    );

    // 2. Lọc khách hàng theo trạng thái
    Page<Lead> findByStatus(
            String status,
            Pageable pageable
    );

    // 3. Tìm kiếm kết hợp lọc trạng thái
    @Query("""
        SELECT l
        FROM Lead l
        WHERE (
            LOWER(l.fullName) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR l.phone LIKE CONCAT('%', :keyword, '%')
        )
        AND l.status = :status
        """)
    Page<Lead> searchByStatus(
            @Param("keyword") String keyword,
            @Param("status") String status,
            Pageable pageable
    );

    // 4. Thống kê số khách hàng theo trạng thái
    long countByStatus(String status);
}
