package com.duffin.curvepoc;

import java.time.LocalDate;

public class Bond {

    public long id;
    public LocalDate maturityDate;
    public double yield;

    public Bond(long id, LocalDate maturityDate, double yield) {
        this.id = id;
        this.maturityDate = maturityDate;
        this.yield = yield;
    }
}
