package ru.seknera.project.kursovaya.controller;

import ru.seknera.project.kursovaya.model.Budget;
import ru.seknera.project.kursovaya.model.BudgetHistory;
import ru.seknera.project.kursovaya.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/budgets")
public class BudgetController {

    private final BudgetService budgetService;

    @Autowired
    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    // Метод для получения текущего бюджета
    @GetMapping("/current")
    public ResponseEntity<Budget> getCurrentBudget() {
        Budget budget = budgetService.getCurrentBudget();
        return ResponseEntity.ok(budget);
    }

    // Метод для получения истории изменений бюджетов
    @GetMapping("/history")
    public ResponseEntity<List<Budget>> getBudgetHistory() {
        List<Budget> budgetHistory = budgetService.getBudgetHistory();
        return ResponseEntity.ok(budgetHistory);
    }

    // Метод для получения истории изменений бюджета по ID бюджета
    @GetMapping("/history/{budgetId}")
    public ResponseEntity<List<BudgetHistory>> getBudgetHistoryByBudgetId(@PathVariable Long budgetId) {
        List<BudgetHistory> budgetHistory = budgetService.getBudgetHistoryByBudgetId(budgetId);
        return ResponseEntity.ok(budgetHistory);
    }

    // Метод для получения бюджета по ID
    @GetMapping("/{id}")
    public ResponseEntity<Budget> getBudgetById(@PathVariable Long id) {
        Budget budget = budgetService.getBudgetById(id);
        return ResponseEntity.ok(budget);
    }

    // Метод для публикации нового семейного бюджета
    @PostMapping
    public ResponseEntity<Budget> createBudget(@RequestParam double amount) {
        Budget createdBudget = budgetService.createBudget(amount);
        return ResponseEntity.ok().body(createdBudget);
    }

    // Метод для изменения бюджета и указания причины
    @PutMapping("/{id}")
    public ResponseEntity<Budget> updateBudget(
            @PathVariable Long id,
            @RequestParam("amount") double amount,
            @RequestParam("reason") String reason) {
        Budget updatedBudget = budgetService.updateBudget(id, amount, reason);
        return ResponseEntity.ok(updatedBudget);
    }

    // Метод для удаления бюджета
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudget(@PathVariable Long id) {
        budgetService.deleteBudget(id);
        return ResponseEntity.noContent().build();
    }
}
