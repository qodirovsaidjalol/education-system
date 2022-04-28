package com.education.repository;

import com.education.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Long>, AbstractRepository {

    @Query(value = "select a.* from edu.public.auth_user a where  not a.deleted and a.education_id=:educationId and a.id=:id", nativeQuery = true)
    AuthUser getNotDelete(@NotNull Long id);

    @Modifying
    @Query(value = " select  a.* from edu.public.auth_user a where not a.deleted and a.education_id=:id limit :l offset :l1", nativeQuery = true)
    List<AuthUser> getAllNotDelete(int l, int l1,Long id);

    @Modifying
    @Query(value = "update AuthUser set deleted=true ")
    Void deleteByIdNotDelete(Long id);

    Optional<AuthUser> findByUsernameAndDeletedFalse(String username);

    @Query(value = " select exists(select * from edu.public.auth_user au where not au.deleted  and au.username=:s);",nativeQuery = true)
    boolean existsByUsername(String s);

    @Query(value = " select exists(select * from edu.public.auth_user au where not au.deleted  and au.phone=:s);",nativeQuery = true)
    boolean existPhone(String s);

    Optional<Object> findByUsername(String phone);
}
