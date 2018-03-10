package com.purchases.my.controller;

import com.purchases.my.db.entity.IncomeEntity;
import com.purchases.my.db.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Calendar;
import java.util.Date;

@Controller
public class IncomeController {
    private IncomeService incomeService;

    @Autowired
    @Qualifier(value = "incomeService")
    public void setIncomeService(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @RequestMapping(value = "incomes", method = RequestMethod.GET)
    public String listIncomes(Model model) {
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.set(Calendar.DAY_OF_MONTH, calendarStart.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date startDate = calendarStart.getTime();
        Date endDate = new Date();
        model.addAttribute("allIncome", this.incomeService.getFullIncome());
        model.addAttribute("income", new IncomeEntity());
        model.addAttribute("listIncomes", this.incomeService.getIncomeEntities(startDate, endDate));
        return "incomes";
    }

    @RequestMapping(value = "/income/add-income", method = RequestMethod.POST)
    public String addIncome(@ModelAttribute("income") IncomeEntity incomeEntity, Model model,
                            RedirectAttributes attributes) {
        if (incomeEntity.getQuantity() <= 0) {
            attributes.addFlashAttribute("errorMSG", "Wrong income value!Quantity should be more than '0'");
            return "redirect:/incomes";
//            return "incomes";
        }
        if (incomeEntity.getId() == 0) {
            incomeEntity.setDate(new Date());
            incomeService.addIncome(incomeEntity);
            attributes.addFlashAttribute("successMSG", "Added new income");
        } else {
            incomeService.updateIncome(incomeEntity);
            attributes.addFlashAttribute("successMSG", "The income updated");
        }
        return "redirect:/incomes";
//        return "redirect:/purchases";
    }

    @RequestMapping(value = "/remove-income/{id}")
    public String removeIncome(@PathVariable("id") int id, RedirectAttributes attributes) {
        this.incomeService.removeIncome(id);
        attributes.addFlashAttribute("successMSG", "Income deleted");
        return "redirect:/incomes";
//        return "redirect:/purchases";
    }

    @RequestMapping(value = "/edit-income/{id}")
    public String editIncome(@PathVariable("id") int id, Model model) {
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.set(Calendar.DAY_OF_MONTH, calendarStart.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date startDate = calendarStart.getTime();
        Date endDate = new Date();
        model.addAttribute("income", this.incomeService.getIncomeEntity(id));
        model.addAttribute("listIncomes", this.incomeService.getIncomeEntities(startDate, endDate));
        return "incomes";
    }
}
