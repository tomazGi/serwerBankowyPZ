package com.tmaze.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Konto {
    String wlasciciel;
    BigDecimal bilans;


    public Konto(BigDecimal bilans, String wlasciciel) {
        this.bilans = bilans;
        this.wlasciciel = wlasciciel;
    }

    public BigDecimal getBilans() {
        return bilans;
    }

    public void setBilans(BigDecimal bilans) {
        this.bilans = bilans;
    }

    public String getWlasciciel() {
        return wlasciciel;
    }

    public void setWlasciciel(String wlasciciel) {
        this.wlasciciel = wlasciciel;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Konto konto = (Konto) o;
        return Objects.equals(wlasciciel, konto.wlasciciel) && Objects.equals(bilans, konto.bilans);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wlasciciel, bilans);
    }

    @Override
    public String toString() {
        return "Konto{" +
                "wlasciciel='" + wlasciciel + '\'' +
                ", bilans=" + bilans +
                '}';
    }
}
