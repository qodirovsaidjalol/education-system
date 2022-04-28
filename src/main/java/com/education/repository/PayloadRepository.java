package com.education.repository;

import com.education.entity.Payload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PayloadRepository extends JpaRepository<Payload, Long>, AbstractRepository {

    @Query(value = "select a.id from education.public.payload a where not a.deleted and a.student_id=:studentId", nativeQuery = true)
    List<Long> findAllByStudentAndDeletedFalse(Long studentId);
    
    @Query(value = "select a.id from education.public.payload a where not a.deleted and a.student_id =:studentId and a.group_id =:groupId", nativeQuery = true)
    List<Long> findAllByStudentAndGroupAndDeletedFalse(Long studentId, Long groupId);


    @Query(value = "select a.id from education.public.payload a where not a.deleted", nativeQuery = true)
    List<Long> findAllByDeletedFalse();

    @Query(value = "select p.id from education.public.payload p inner join education.public.groups g on p.group_id = g.id and g.education_id =:education and  p.deleted = false ORDER BY p.id;", nativeQuery = true)
    List<Long> findAllByEducation(Long education);

    @Query(value = "select a.* from education.public.payload a where not a.deleted and a.id =:id", nativeQuery = true)
    Optional<Payload> findByIdAndDeletedFalse(Long id);
}
