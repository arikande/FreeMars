package org.freemars.ui.util;

/**
 *
 * @author Deniz ARIKAN
 */
public class TurnToDateConverter {

    int numberOfTurns;
    private static final String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    public TurnToDateConverter(int numberOfTurns) {
        this.numberOfTurns = numberOfTurns;
    }

    public String gateDateString() {
        int year = 2077 + (numberOfTurns / 12);
        String month = months[numberOfTurns % 12];
        return month + ", " + year;
    }
}
