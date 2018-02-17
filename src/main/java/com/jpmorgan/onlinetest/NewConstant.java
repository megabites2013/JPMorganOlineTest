package com.jpmorgan.onlinetest;

import java.util.Arrays;
import java.util.List;

public class NewConstant {
    public static final String STROK="OK";
    public static final String STRPAUSE="PAUSE";
    public static final String STRBYE="BYE";

    public static final String MSGTYPE="msgtype";
    public static final String PNAME="pname";
    public static final String PRICE="price";
    public static final String VOLUME="volume";
    public static final String ADJUST="adjust";

    public static final String ADD="ADD";
    public static final String SUB="SUB";
    public static final String MULTI="MULTI";

    public static final String STRHOST="127.0.0.1";
    public static final int INTPORT=2018;
    public static final int INTTIMEOUT=60000;
    public static final int INTWAIT=1000;
    public static final int INTPAUSE=10000;

    public static final int INTA=10;
    public static final int INTB=50;
    public static final int INTX=100;

    public static final int INTBUFSIZE=250;

    public static final List<String> FIELDS = Arrays.asList(MSGTYPE, PNAME, PRICE, VOLUME,ADJUST);
    public static final List<String> PRODUCTS = Arrays.asList("Apple", "Orange", "Kiwi", "Cherry");
    public static final List<String> ADJUSTS = Arrays.asList(ADD, SUB, MULTI);
    public static final List<String> TYPES = Arrays.asList("TYP1", "TYP2", "TYP3");
    public static final List<Integer> VALUES = Arrays.asList(10, 8, 12, 15,17);






}
