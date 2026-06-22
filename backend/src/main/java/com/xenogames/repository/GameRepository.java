package com.xenogames.repository;

import com.xenogames.entity.Game;
import com.xenogames.entity.GameStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    @Query("SELECT g FROM Game g " +
           "LEFT JOIN g.tags t " +
           "WHERE (:keyword IS NULL OR g.gameTitle LIKE %:keyword%) " +
           "AND (:platform IS NULL OR g.platform = :platform) " +
           "AND (:status IS NULL OR g.status = :status) " +
           "AND (:tagId IS NULL OR t.id = :tagId) " +
           "GROUP BY g.id")
    Page<Game> findWithFilters(
            @Param("keyword") String keyword,
            @Param("platform") String platform,
            @Param("status") GameStatus status,
            @Param("tagId") Long tagId,
            Pageable pageable);
}
