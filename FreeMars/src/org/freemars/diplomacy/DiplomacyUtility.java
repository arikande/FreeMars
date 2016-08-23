package org.freemars.diplomacy;

/**
 *
 * @author Deniz ARIKAN
 */
public class DiplomacyUtility {

    public static String getAttitudeString(int attitude) {
        if (attitude < 100) {
            return "Feud";
        }
        if (attitude < 150) {
            return "Feud/Hate";
        }
        if (attitude < 200) {
            return "Hate";
        }
        if (attitude < 250) {
            return "Hate/Hostile";
        }
        if (attitude < 300) {
            return "Hostile";
        }
        if (attitude < 350) {
            return "Hostile/Suspicious";
        }
        if (attitude < 400) {
            return "Suspicious";
        }
        if (attitude < 450) {
            return "Suspicious/Neutral";
        }
        if (attitude < 600) {
            return "Neutral";
        }
        if (attitude < 650) {
            return "Neutral/Friendly";
        }
        if (attitude < 700) {
            return "Friendly";
        }
        if (attitude < 750) {
            return "Friendly/Warm";
        }
        if (attitude < 800) {
            return "Warm";
        }
        if (attitude < 850) {
            return "Warm/Very close";
        }
        if (attitude < 900) {
            return "Very close";
        }
        if (attitude < 950) {
            return "Very close/Harmony";
        }
        return "Harmony";
    }
}
