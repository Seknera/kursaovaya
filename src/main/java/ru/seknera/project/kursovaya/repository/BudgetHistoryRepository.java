package ru.seknera.project.kursovaya.repository;

import ru.seknera.project.kursovaya.model.BudgetHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetHistoryRepository extends JpaRepository<BudgetHistory, Long> {
    List<BudgetHistory> findByBudgetId(Long budgetId);
    void deleteAll(Iterable<? extends BudgetHistory> entities);
}

