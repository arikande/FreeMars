package org.freemars.player;

/**
 *
 * @author Deniz ARIKAN
 */
public class ResourceTradeData {

    private int quantityExported;
    private int quantityImported;
    private int incomeBeforeTaxes;
    private int tax;
    private int netIncome;
    private int expenditure;

    public int getQuantityExported() {
        return quantityExported;
    }

    public void setQuantityExported(int quantityExported) {
        this.quantityExported = quantityExported;
    }

    public int getQuantityImported() {
        return quantityImported;
    }

    public void setQuantityImported(int quantityImported) {
        this.quantityImported = quantityImported;
    }

    public int getIncomeBeforeTaxes() {
        return incomeBeforeTaxes;
    }

    public void setIncomeBeforeTaxes(int income) {
        this.incomeBeforeTaxes = income;
    }

    public int getTax() {
        return tax;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }

    public int getNetIncome() {
        return netIncome;
    }

    public void setNetIncome(int netIncome) {
        this.netIncome = netIncome;
    }

    public int getExpenditure() {
        return expenditure;
    }

    public void setExpenditure(int expenditure) {
        this.expenditure = expenditure;
    }
}
