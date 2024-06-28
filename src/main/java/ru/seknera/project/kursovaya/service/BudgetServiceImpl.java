package ru.seknera.project.kursovaya.service;

import ru.seknera.project.kursovaya.model.Budget;
import ru.seknera.project.kursovaya.repository.BudgetRepository;
import ru.seknera.project.kursovaya.model.BudgetHistory;
import ru.seknera.project.kursovaya.repository.BudgetHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;
    private final BudgetHistoryRepository budgetHistoryRepository;

    @Autowired
    public BudgetServiceImpl(BudgetRepository budgetRepository, BudgetHistoryRepository budgetHistoryRepository) {
        this.budgetRepository = budgetRepository;
        this.budgetHistoryRepository = budgetHistoryRepository;
    }

    @Override
    public Budget getCurrentBudget() {
        return budgetRepository.findAll()
                .stream()
                .reduce((first, second) -> second)
                .orElseThrow(() -> new RuntimeException("No budgets available"));
    }

    @Override
    public List<Budget> getBudgetHistory() {
        // Реализация получения всей истории изменений бюджета
        return budgetRepository.findAll();
    }

    @Override
    public List<BudgetHistory> getBudgetHistoryByBudgetId(Long budgetId) {
        return budgetHistoryRepository.findByBudgetId(budgetId);
    }

    @Override
    public Budget getBudgetById(Long id) {
        return budgetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Budget not found with id: " + id));
    }

    @Override
    public Budget createBudget(double amount) {
        Budget budget = new Budget();
        budget.setAmount(amount);
        budget.setTimestamp(LocalDateTime.now());

        Budget savedBudget = budgetRepository.save(budget);

        BudgetHistory budgetHistory = new BudgetHistory();
        budgetHistory.setBudget(savedBudget);
        budgetHistory.setAmount(amount);
        budgetHistory.setTimestamp(LocalDateTime.now());
        budgetHistoryRepository.save(budgetHistory);

        return savedBudget;
    }

    @Override
    public Budget updateBudget(Long id, double amount, String reason) {
        Budget budget = getBudgetById(id);
        budget.setAmount(amount);
        budget.setReason(reason);
        budget.setTimestamp(LocalDateTime.now());

        BudgetHistory budgetHistory = new BudgetHistory();
        budgetHistory.setBudget(budget);
        budgetHistory.setAmount(amount);
        budgetHistory.setReason(reason);
        budgetHistory.setTimestamp(LocalDateTime.now());
        budgetHistoryRepository.save(budgetHistory);

        return budgetRepository.save(budget);
    }

    @Override
    public void deleteBudget(Long id) {
        List<BudgetHistory> budgetHistories = budgetHistoryRepository.findByBudgetId(id);
        if (budgetHistories != null && !budgetHistories.isEmpty()) {
            budgetHistoryRepository.deleteAll(budgetHistories);
        }
        budgetRepository.deleteById(id);
    }
}



