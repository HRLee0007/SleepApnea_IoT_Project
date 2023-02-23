package com.service.sleepapneaiotserver.domain.infos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InfosRepository extends JpaRepository<Infos, Long> , JpaSpecificationExecutor<Infos> {
    List<Infos> findAllByUserId(Long userId);

    List<Infos> findCountByUserId(Long userId);


//    @Query(value =
//            "select a.username as username, b.count as count, b.createDate as date " +
//            "from User a, Infos b " +
//            "where a.id = b.userId AND a.username=" + username +
//            " limit 7",
//            countQuery = "SELECT count(*) from Infos",
//            nativeQuery = true)
//    List<Infos> findInfosByUsername(@Param(value="username") String username);

}
