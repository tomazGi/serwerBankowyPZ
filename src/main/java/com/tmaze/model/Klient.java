package com.tmaze.model;

import java.util.Objects;

public class Klient {
    String wlasciciel;
    Integer nrKlienta;

    public Klient(String wlasciciel, Integer nrKlienta) {
        this.wlasciciel = wlasciciel;
        this.nrKlienta = nrKlienta;
    }

    public String getWlasciciel() {
        return wlasciciel;
    }

    public void setWlasciciel(String wlasciciel) {
        this.wlasciciel = wlasciciel;
    }

    public Integer getNrKlienta() {
        return nrKlienta;
    }

    public void setNrKlienta(Integer nrKlienta) {
        this.nrKlienta = nrKlienta;
    }

    @Override
    public String toString() {
        return "Klient{" +
                "wlasciciel='" + wlasciciel + '\'' +
                ", nrKlienta=" + nrKlienta +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Klient klient = (Klient) o;
        return Objects.equals(wlasciciel, klient.wlasciciel) && Objects.equals(nrKlienta, klient.nrKlienta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wlasciciel, nrKlienta);
    }
}
