package com.education.repository;


import com.education.entity.AuthUser;
import com.education.entity.Education;
import com.education.entity.Group;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group,Long>, AbstractRepository {

    boolean existsByNameAndEducation(String name, Education education);

    @Query(value = "select g.* from education_groupone.public.groups g where not g.deleted and g.id=:id", nativeQuery = true)
    Optional<Group> getNotDelete(@NotNull Long id);




    /*@Query( nativeQuery = true, value = "insert into education_groupone.public.groups_users (group_id, users_id) values ( :groupId,:userId)")
    void addUser(@Param("groupId") Long groupId,@Param("userId") Long userId);*/

    @Query(value = "select g.users from Group g where g.id= ?1")
    List<AuthUser> findByUsers(Long groupId);


    /*@Query(value = "SELECT * FROM education_groupone.public.groups g where g.education_id=:educationId and not g.deleted ORDER BY id \n-- #pageable\n",
            countQuery = "SELECT count(*) FROM education_groupone.public.groups",
            nativeQuery = true)*/
    List<Group> findByEducationIdAndDeletedFalse(@Param("educationId") Long educationId);





}
