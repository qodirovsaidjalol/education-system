package com.education.repository;

import com.education.entity.AuthUser;
import com.education.entity.Daily;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Repository
@EnableJpaRepositories
public interface DailyRepository extends JpaRepository<Daily,Long>,AbstractRepository {

    @Modifying
    @Transactional
    @Query("update Daily d set d.deleted=true where d.id=:id")
    void deleteSoft(Long id);

    List<Daily> findAllByStudentIdAndGroupIdAndDeletedFalse(Long id,Long id1);

    List<Daily> findAllByTimeAndDeleted(LocalDate date, Boolean deleted);

    @Modifying
    @Query(value = " select  a.* from daily a where not a.deleted limit :l offset :l1", nativeQuery = true)
    List<Daily> getAllNotDelete(int l, int l1);


    Page<Daily> findAllByDeletedFalse(Pageable pageable);

    Optional<Daily> findByIdAndDeletedFalse(Long id);
}