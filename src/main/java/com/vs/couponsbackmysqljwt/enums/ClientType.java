package com.vs.couponsbackmysqljwt.enums;


/**
 *provides all client types and builds menu to choose one
 */
public enum ClientType {

    ADMINISTRATOR(new StringBuffer("Administrator")),
    COMPANY(new StringBuffer("Company")),
    CUSTOMER(new StringBuffer("Customer"));
    private StringBuffer description;

    ClientType(StringBuffer description) {
        this.description = description;
    }

    public StringBuffer getDescription() {
        return description;
    }

    /**
     *@return stringBuffer with all client types nmerated starting from one
     */
    public static StringBuffer createMenu() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ClientType.values().length; i++) {
            sb.append((i + 1) + "." + ClientType.values()[i].getDescription() + " ");
        }
        return new StringBuffer(sb);
    }
}
