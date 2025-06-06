package com.budgettracker.controller;

import com.budgettracker.model.Budget;
import com.budgettracker.model.Expense;
import com.budgettracker.model.User;
import com.budgettracker.repository.BudgetRepository;
import com.budgettracker.repository.ExpenseRepository;
import com.budgettracker.repository.UserRepository;
import com.budgettracker.security.UserDetailsImpl;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "*")
public class ExpenseController {

    @Autowired
    private ExpenseRepository expenseRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BudgetRepository budgetRepo;

    @GetMapping
    public ResponseEntity<List<Expense>> getExpenses(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userRepo.findById(userDetails.getId()).orElseThrow();
        List<Expense> expenses = expenseRepo.findByUser(user);
        return ResponseEntity.ok(expenses);
    }

    @PostMapping
    public ResponseEntity<Expense> createExpense(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                 @Valid @RequestBody Expense expense) {
        User user = userRepo.findById(userDetails.getId()).orElseThrow();
        expense.setUser(user);
        if (expense.getExpenseDate() == null) {
            expense.setExpenseDate(LocalDateTime.now());
        }
        if (expense.getBudget() != null) {
            Budget budget = budgetRepo.findById(expense.getBudget().getId()).orElse(null);
            expense.setBudget(budget);
        }
        Expense saved = expenseRepo.save(expense);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                 @PathVariable Long id,
                                                 @Valid @RequestBody Expense expenseDetails) {
        User user = userRepo.findById(userDetails.getId()).orElseThrow();
        Expense expense = expenseRepo.findById(id).orElseThrow();
        if (!expense.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(403).build();
        }
        expense.setDescription(expenseDetails.getDescription());
        expense.setAmount(expenseDetails.getAmount());
        expense.setExpenseDate(expenseDetails.getExpenseDate());
        if (expenseDetails.getBudget() != null) {
            Budget budget = budgetRepo.findById(expenseDetails.getBudget().getId()).orElse(null);
            expense.setBudget(budget);
        } else {
            expense.setBudget(null);
        }
        Expense updated = expenseRepo.save(expense);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExpense(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                           @PathVariable Long id) {
        User user = userRepo.findById(userDetails.getId()).orElseThrow();
        Expense expense = expenseRepo.findById(id).orElseThrow();
        if (!expense.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(403).build();
        }
        expenseRepo.delete(expense);
        return ResponseEntity.ok().build();
    }
}
