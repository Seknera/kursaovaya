package ru.seknera.project.kursovaya.service;

import ru.seknera.project.kursovaya.model.Budget;
import ru.seknera.project.kursovaya.model.BudgetHistory;

import java.util.List;

public interface BudgetService {
    Budget getCurrentBudget();
    List<Budget> getBudgetHistory();
    List<BudgetHistory> getBudgetHistoryByBudgetId(Long budgetId);
    Budget getBudgetById(Long id);
    Budget createBudget(double amount);
    Budget updateBudget(Long id, double amount, String reason);
    void deleteBudget(Long id);
}

