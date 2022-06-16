package fr.kohei.common.utils;

import fr.kohei.common.cache.rank.Grant;

import java.util.Comparator;


public class GrantComparator implements Comparator<Grant> {

    @Override
    public int compare(Grant o1, Grant o2) {
        return Integer.compare(o1.getPriority(), o2.getPriority());
    }
}