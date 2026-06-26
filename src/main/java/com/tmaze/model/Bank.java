package com.tmaze.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;

public class Bank implements Serializable {
    String nazwa;
    BigInteger aktywa;

    public Bank(String nazwa, BigInteger aktywa) {
        this.nazwa = nazwa;
        this.aktywa = aktywa;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public BigInteger getAktywa() {
        return aktywa;
    }

    public void setAktywa(BigInteger aktywa) {
        this.aktywa = aktywa;
    }

    @Override
    public String toString() {
        return "Bank{" +
                "nazwa='" + nazwa + '\'' +
                ", aktywa=" + aktywa +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Bank bank = (Bank) o;
        return Objects.equals(nazwa, bank.nazwa) && Objects.equals(aktywa, bank.aktywa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nazwa, aktywa);
    }
}
