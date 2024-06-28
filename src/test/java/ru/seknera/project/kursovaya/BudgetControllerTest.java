package ru.seknera.project.kursovaya;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.seknera.project.kursovaya.controller.BudgetController;
import ru.seknera.project.kursovaya.model.Budget;
import ru.seknera.project.kursovaya.model.BudgetHistory;
import ru.seknera.project.kursovaya.service.BudgetService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BudgetControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BudgetService budgetService;

    @InjectMocks
    private BudgetController budgetController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(budgetController).build();
    }

    @Test
    public void testGetCurrentBudget() throws Exception {
        Budget budget = new Budget();
        budget.setId(1L);
        budget.setAmount(1000.0);
        budget.setReason("Test budget");
        when(budgetService.getCurrentBudget()).thenReturn(budget);

        mockMvc.perform(get("/api/budgets/current"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.amount").value(1000.0))
                .andExpect(jsonPath("$.reason").value("Test budget"));

        verify(budgetService, times(1)).getCurrentBudget();
        verifyNoMoreInteractions(budgetService);
    }

    @Test
    public void testGetBudgetHistory() throws Exception {
        Budget budget1 = new Budget();
        budget1.setId(1L);
        budget1.setAmount(1000.0);
        budget1.setReason("Budget 1");

        Budget budget2 = new Budget();
        budget2.setId(2L);
        budget2.setAmount(1500.0);
        budget2.setReason("Budget 2");

        List<Budget> budgets = Arrays.asList(budget1, budget2);
        when(budgetService.getBudgetHistory()).thenReturn(budgets);

        mockMvc.perform(get("/api/budgets/history"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].amount").value(1000.0))
                .andExpect(jsonPath("$[0].reason").value("Budget 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].amount").value(1500.0))
                .andExpect(jsonPath("$[1].reason").value("Budget 2"));

        verify(budgetService, times(1)).getBudgetHistory();
        verifyNoMoreInteractions(budgetService);
    }

    @Test
    public void testGetBudgetHistoryByBudgetId() throws Exception {
        BudgetHistory history1 = new BudgetHistory();
        history1.setId(1L);
        history1.setAmount(100.0);
        history1.setReason("History 1");

        BudgetHistory history2 = new BudgetHistory();
        history2.setId(2L);
        history2.setAmount(150.0);
        history2.setReason("History 2");

        List<BudgetHistory> histories = Arrays.asList(history1, history2);
        when(budgetService.getBudgetHistoryByBudgetId(anyLong())).thenReturn(histories);

        mockMvc.perform(get("/api/budgets/history/budget/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].amount").value(100.0))
                .andExpect(jsonPath("$[0].reason").value("History 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].amount").value(150.0))
                .andExpect(jsonPath("$[1].reason").value("History 2"));

        verify(budgetService, times(1)).getBudgetHistoryByBudgetId(anyLong());
        verifyNoMoreInteractions(budgetService);
    }

    @Test
    public void testGetBudgetById() throws Exception {
        Budget budget = new Budget();
        budget.setId(1L);
        budget.setAmount(1000.0);
        budget.setReason("Test budget");
        when(budgetService.getBudgetById(anyLong())).thenReturn(budget);

        mockMvc.perform(get("/api/budgets/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.amount").value(1000.0))
                .andExpect(jsonPath("$.reason").value("Test budget"));

        verify(budgetService, times(1)).getBudgetById(anyLong());
        verifyNoMoreInteractions(budgetService);
    }

    @Test
    public void testCreateBudget() throws Exception {
        Budget budget = new Budget();
        budget.setId(1L);
        budget.setAmount(1000.0);
        budget.setReason("New budget");

        when(budgetService.createBudget(any())).thenReturn(budget);

        mockMvc.perform(post("/api/budgets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(budget)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.amount").value(1000.0))
                .andExpect(jsonPath("$.reason").value("New budget"));

        verify(budgetService, times(1)).createBudget(any());
        verifyNoMoreInteractions(budgetService);
    }

    @Test
    public void testUpdateBudget() throws Exception {
        Budget updatedBudget = new Budget();
        updatedBudget.setId(1L);
        updatedBudget.setAmount(1200.0);
        updatedBudget.setReason("Updated budget");

        when(budgetService.updateBudget(anyLong(), anyDouble(), anyString())).thenReturn(updatedBudget);

        mockMvc.perform(put("/api/budgets/1")
                        .param("amount", "1200.0")
                        .param("reason", "Updated budget"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.amount").value(1200.0))
                .andExpect(jsonPath("$.reason").value("Updated budget"));

        verify(budgetService, times(1)).updateBudget(anyLong(), anyDouble(), anyString());
        verifyNoMoreInteractions(budgetService);
    }

    @Test
    public void testDeleteBudget() throws Exception {
        mockMvc.perform(delete("/api/budgets/1"))
                .andExpect(status().isNoContent());

        verify(budgetService, times(1)).deleteBudget(anyLong());
        verifyNoMoreInteractions(budgetService);
    }
}
