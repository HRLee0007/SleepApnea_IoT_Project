package com.service.sleepapneaiotserver.domain.user;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<UserToken, Long> {
    Optional<UserToken> findByUsername(String username);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE UserToken m SET m.token = :token where m.username = :username")
    int renewToken(@Param(value="token") String token, @Param(value="username") String username);
    // 토큰 값 갱신


}
