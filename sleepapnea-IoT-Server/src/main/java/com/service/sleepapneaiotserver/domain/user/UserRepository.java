package com.service.sleepapneaiotserver.domain.user;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE User m SET m.status = :status where m.username = :username")
    int statusOnChange(@Param(value="status") int status, @Param(value="username") String username);
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE User m SET m.sign = :sign where m.username = :username")
    int signChange(@Param(value="sign")int sign, @Param(value="username") String username);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE User m SET m.statusStartAt = :now where m.username = :username")
    void changeStartTime(@Param(value="now") String now,@Param(value="username") String username);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE User m SET m.statusEndAt = :now where m.username = :username")
    void changeEndTime(@Param(value="now") String now,@Param(value="username") String username);


}