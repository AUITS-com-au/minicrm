package com.sh.crm.security.util;

public enum Consts {

    NAT_ID("ID"), IQAMA("IQ"), PASSPORT("PP"), CORPORATES("CR"), FAMILY_BOOK("FB"), DIPLOMATIC_CARD(
            "DC"), REGISTERATION_BOOK("RG"), HAFEEZA("HN"), GCC_PASSPORT("GP"), BUSINESS_LICENSE(
            "BL");

    public String getValue() {
        return value;
    }

    String value;

    private Consts(String v) {
        value = v;
    }
}