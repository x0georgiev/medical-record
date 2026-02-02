package com.cscb869.medical_record.data.repo;

import com.cscb869.medical_record.data.entity.SickLeave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SickLeaveRepository extends JpaRepository<SickLeave, Long> {

    SickLeave findByExaminationId(Long examinationId);

    // Report: Get month with most sick leaves issued
    @Query(value = "SELECT YEAR(issue_date) as year, MONTH(issue_date) as month, COUNT(*) as count " +
           "FROM sick_leaves " +
           "GROUP BY YEAR(issue_date), MONTH(issue_date) " +
           "ORDER BY count DESC " +
           "LIMIT 1",
           nativeQuery = true)
    Object[] getMonthWithMostSickLeaves();

    // Report: Get sick leave statistics by month
    @Query(value = "SELECT YEAR(issue_date) as year, MONTH(issue_date) as month, COUNT(*) as count " +
           "FROM sick_leaves " +
           "GROUP BY YEAR(issue_date), MONTH(issue_date) " +
           "ORDER BY year DESC, month DESC",
           nativeQuery = true)
    List<Object[]> getSickLeavesByMonth();
}
