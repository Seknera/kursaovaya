package ru.seknera.project.kursovaya;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.seknera.project.kursovaya.model.Budget;
import ru.seknera.project.kursovaya.model.BudgetHistory;
import ru.seknera.project.kursovaya.repository.BudgetHistoryRepository;
import ru.seknera.project.kursovaya.repository.BudgetRepository;
import ru.seknera.project.kursovaya.service.BudgetServiceImpl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BudgetServiceImplTest {

    @Mock
    private BudgetRepository budgetRepository;

    @Mock
    private BudgetHistoryRepository budgetHistoryRepository;

    @InjectMocks
    private BudgetServiceImpl budgetService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetCurrentBudget() {
        Budget budget = new Budget();
        budget.setId(1L);
        budget.setAmount(100.0);
        budget.setReason("Test");
        budget.setTimestamp(LocalDateTime.now());

        when(budgetRepository.findAll()).thenReturn(Collections.singletonList(budget));

        Budget result = budgetService.getCurrentBudget();

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(100.0, result.getAmount());
        assertEquals("Test", result.getReason());

        verify(budgetRepository, times(1)).findAll();
    }

    @Test
    public void testGetBudgetHistory() {
        Budget budget = new Budget();
        budget.setId(1L);
        budget.setAmount(100.0);
        budget.setReason("Test");
        budget.setTimestamp(LocalDateTime.now());

        when(budgetRepository.findAll()).thenReturn(Collections.singletonList(budget));

        List<Budget> result = budgetService.getBudgetHistory();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(100.0, result.get(0).getAmount());
        assertEquals("Test", result.get(0).getReason());

        verify(budgetRepository, times(1)).findAll();
    }

    @Test
    public void testUpdateBudget() {
        Long id = 1L;
        double newAmount = 300.0;
        String newReason = "Updated test";

        Budget existingBudget = new Budget();
        existingBudget.setId(id);
        existingBudget.setAmount(200.0);
        existingBudget.setReason("Test");
        existingBudget.setTimestamp(LocalDateTime.now());

        when(budgetRepository.findById(id)).thenReturn(Optional.of(existingBudget));
        when(budgetRepository.save(any(Budget.class))).thenReturn(existingBudget);

        Budget result = budgetService.updateBudget(id, newAmount, newReason);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(newAmount, result.getAmount());
        assertEquals(newReason, result.getReason());
        assertNotNull(result.getTimestamp());

        verify(budgetRepository, times(1)).findById(id);
        verify(budgetRepository, times(1)).save(any(Budget.class));
    }

    @Test
    public void testGetBudgetById() {
        Long id = 1L;
        Budget budget = new Budget();
        budget.setId(id);
        budget.setAmount(100.0);
        budget.setReason("Test");
        budget.setTimestamp(LocalDateTime.now());

        when(budgetRepository.findById(id)).thenReturn(Optional.of(budget));

        Budget result = budgetService.getBudgetById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(100.0, result.getAmount());
        assertEquals("Test", result.getReason());

        verify(budgetRepository, times(1)).findById(id);
    }

    @Test
    public void testDeleteBudget() {
        Long id = 1L;

        budgetService.deleteBudget(id);

        verify(budgetRepository, times(1)).deleteById(id);
    }

    @Test
    public void testGetBudgetHistoryByBudgetId() {
        Long budgetId = 1L;
        BudgetHistory history = new BudgetHistory();
        history.setId(1L);
        history.setAmount(100.0);
        history.setReason("Test");
        history.setTimestamp(LocalDateTime.now());

        when(budgetHistoryRepository.findByBudgetId(budgetId)).thenReturn(Collections.singletonList(history));

        List<BudgetHistory> result = budgetService.getBudgetHistoryByBudgetId(budgetId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(100.0, result.get(0).getAmount());
        assertEquals("Test", result.get(0).getReason());

        verify(budgetHistoryRepository, times(1)).findByBudgetId(budgetId);
    }
}


