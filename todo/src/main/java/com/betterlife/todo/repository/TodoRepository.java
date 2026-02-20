package com.betterlife.todo.repository;

import com.betterlife.todo.domain.TodoEntity;
import com.betterlife.todo.enums.TodoStatus;
import com.betterlife.todo.enums.TodoType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
    List<TodoEntity> findAllByUserId(Long userId);
    Optional<TodoEntity> findByTitle(String title);
    void deleteAllByUserId(Long userId);

    List<TodoEntity> findAllByRecurTaskId(Long recurTaskId);

    List<TodoEntity> findAllByRecurTaskIdAndOccurrenceDateAfter(Long recurTaskId, LocalDate occurrenceDateAfter);

    List<TodoEntity> findAllByRecurTaskIdAndOccurrenceDateGreaterThanEqual(Long recurTaskId, LocalDate occurrenceDateIsGreaterThan);
}
