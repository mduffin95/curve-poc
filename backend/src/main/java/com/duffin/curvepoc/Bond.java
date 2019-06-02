package com.duffin.curvepoc;

import java.time.LocalDate;

public class Bond {

    public LocalDate maturityDate;
    public double yield;

    public Bond(LocalDate maturityDate, double yield) {
        this.maturityDate = maturityDate;
        this.yield = yield;
    }
}
