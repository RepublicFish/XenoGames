package com.gamevault.repository;

import com.gamevault.entity.Screenshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScreenshotRepository extends JpaRepository<Screenshot, Long> {

    List<Screenshot> findByGameIdOrderBySortOrderAsc(Long gameId);

    void deleteByGameId(Long gameId);
}
