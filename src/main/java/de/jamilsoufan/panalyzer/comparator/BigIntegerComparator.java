package de.jamilsoufan.panalyzer.comparator;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.Map;

/**
 * Comparator for BigIntegers
 */
public class BigIntegerComparator implements Comparator<String>, Serializable {

    private Map<String, BigInteger> base;

    /**
     * Constructor
     *  
     * @param base The base map
     */
    public BigIntegerComparator(Map<String, BigInteger> base) {
        this.base = base;
    }

    /**
     * Compare two BigIntegers
     *
     * @param a Key of the first value
     * @param b Key of the second value
     * @return Which value is greater
     */
    public final int compare(String a, String b) {
        if (base.get(a).compareTo(base.get(b)) > 0) {
            return -1;
        }
        else {
            return 1;
        }
    }
}
