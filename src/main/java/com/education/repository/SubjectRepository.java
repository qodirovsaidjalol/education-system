package com.education.repository;

import com.education.entity.Education;
import com.education.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long>, AbstractRepository {

    @Query(value = "select a.* from public.subject a where not a.deleted and a.id =:aLong and a.education_id =:education", nativeQuery = true)
    Optional<Subject> findByIdAndEducationAndDeletedFalse(Long aLong, Long education);

    @Query(value = "select a.id from public.subject a where not a.deleted and a.education_id =:id", nativeQuery = true)
    List<Long> findAllByEducationAndDeletedFalse(Long id);

    @Query(value = "select a.id from public.subject a where not a.deleted", nativeQuery = true)
    List<Long> findAllByNotDeleted();

    @Query(value = "select a.* from public.subject a where not a.deleted and a.id =:id", nativeQuery = true)
    Optional<Subject> findByIdAndDeletedFalse(Long id);

    boolean existsByNameAndEducation(String name, Education education);
}
