package de.jamilsoufan.panalyzer.comparator;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;

/**
 * Comparator for Longs
 */
public class LongComparator implements Comparator<String>, Serializable {

    private Map<String, Long> base;

    /**
     * Constructor
     *
     * @param base The base map
     */
    public LongComparator(Map<String, Long> base) {
        this.base = base;
    }

    /**
     * Compare two Longs
     *
     * @param a Key of the first value
     * @param b Key of the second value
     * @return Which value is greater
     */
    public final int compare(String a, String b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        }
        else {
            return 1;
        }
    }
}
