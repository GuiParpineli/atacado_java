package com.wf.apiwf.repository;

import com.wf.apiwf.entity.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface IUserRepository extends JpaRepository<SystemUser, Long> {
    Optional<SystemUser> findByUsername(String username);

    @Query("select u from SystemUser u where u.username= ?1 order by u.username")
    SystemUser findByName(String name);

    SystemUser findByEmail(String email);
}
