package com.education.repository;


import com.education.entity.Education;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public interface EducationRepository extends JpaRepository<Education, Long>, AbstractRepository {

    @Modifying
    @Query(value = "update education e set  e.isBlock  = case when e.isBlock=true then false when e.isBlock=false then true else true  end ", nativeQuery = true)
    Void block(@NotNull Long id);

    @Query(value = "select e.* from education e where not e.deleted and e.id=:id", nativeQuery = true)
    Education getNotDelete(@NotNull Long id);

    @Query(value = "select e.* from education e where  not e.deleted  order by e.name limit :size offset :page ;", nativeQuery = true)
    List<Education> getAll(Integer page, Integer size);

    List<Education> getAllByDeletedIsFalse();

    @Query(value = "select exists(select e.* from education e where not e.deleted and e.phone=:s)", nativeQuery = true)
    boolean existPhone(String s);

}
