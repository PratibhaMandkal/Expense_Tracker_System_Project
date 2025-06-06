package com.budgettracker.repository;

import com.budgettracker.model.Expense;
import com.budgettracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUser(User user);

    List<Expense> findByBudgetId(Long budgetId);
}
