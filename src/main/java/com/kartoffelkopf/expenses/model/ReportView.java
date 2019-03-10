package com.kartoffelkopf.expenses.model;


import org.hibernate.annotations.Immutable;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "report_view")
@Immutable
public class ReportView {

    @Id
    private long id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dueDate;
    private boolean submitted;
    private String title;
    private String type;
    private String symbol;
    private double total;

    public ReportView() {
    }

    public long getId() {
        return id;
    }


    public LocalDate getDueDate() {
        return dueDate;
    }


    public boolean isSubmitted() {
        return submitted;
    }


    public String getTitle() {
        return title;
    }


    public String getType() {
        return type;
    }


    public double getTotal() {
        return total;
    }

    public String getSymbol() {
        return symbol;
    }

}
