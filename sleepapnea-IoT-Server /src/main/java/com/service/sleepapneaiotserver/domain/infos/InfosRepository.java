package com.service.sleepapneaiotserver.domain.infos;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InfosRepository extends JpaRepository<Infos, Long> {
    List<Infos> findAllByUserId(Long userId);
}
