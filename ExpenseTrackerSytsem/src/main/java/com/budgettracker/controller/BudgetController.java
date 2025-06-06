package com.budgettracker.controller;

import com.budgettracker.model.Budget;
import com.budgettracker.model.User;
import com.budgettracker.repository.BudgetRepository;
import com.budgettracker.repository.UserRepository;
import com.budgettracker.security.UserDetailsImpl;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
@CrossOrigin(origins = "*")
public class BudgetController {

    @Autowired
    private BudgetRepository budgetRepo;
    @Autowired
    private UserRepository userRepo;

    @GetMapping
    public ResponseEntity<List<Budget>> getBudgets(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userRepo.findById(userDetails.getId()).orElseThrow();
        List<Budget> budgets = budgetRepo.findByUser(user);
        return ResponseEntity.ok(budgets);
    }

    @PostMapping
    public ResponseEntity<Budget> createBudget(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                          @Valid @RequestBody Budget budget) {
        User user = userRepo.findById(userDetails.getId()).orElseThrow();
        budget.setUser(user);
        Budget saved = budgetRepo.save(budget);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Budget> updateBudget(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                               @PathVariable Long id,
                                               @Valid @RequestBody Budget budgetDetails) {
        User user = userRepo.findById(userDetails.getId()).orElseThrow();
        Budget budget = budgetRepo.findById(id).orElseThrow();

        if (!budget.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(403).build();
        }

        budget.setName(budgetDetails.getName());
        budget.setAmount(budgetDetails.getAmount());
        budget.setStartDate(budgetDetails.getStartDate());
        budget.setEndDate(budgetDetails.getEndDate());

        Budget updated = budgetRepo.save(budget);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBudget(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                          @PathVariable Long id) {
        User user = userRepo.findById(userDetails.getId()).orElseThrow();
        Budget budget = budgetRepo.findById(id).orElseThrow();

        if (!budget.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(403).build();
        }
        budgetRepo.delete(budget);
        return ResponseEntity.ok().build();
    }
}
