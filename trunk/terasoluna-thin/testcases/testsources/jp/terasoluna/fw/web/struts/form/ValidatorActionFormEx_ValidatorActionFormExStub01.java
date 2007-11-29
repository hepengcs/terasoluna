/*
 * Copyright (c) 2007 NTT DATA Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.terasoluna.fw.web.struts.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link ValidatorActionFormExTest}にて使用するスタブクラス。
 * 
 */
@SuppressWarnings("serial")
public class ValidatorActionFormEx_ValidatorActionFormExStub01
    extends ValidatorActionFormEx {

    private int hogeInt = 123;

    private String hogeString = "data1";

    private String hogeString2 = null;

    private int[] hogeIntArray = new int[] { -100, 0, 10, 111 };

    private String[] hogeStringArray =
        new String[] { "data1", "data2", "data3", "data4" };

    private Object[] hogeObjectArray =
        new Object[] {
            new Integer(1),
            new Integer(2),
            new Integer(3),
            new Integer(4)};

    private List<String> hogeList = new ArrayList<String>();

    private Map<String, String> hogeMap = new HashMap<String, String>();

    private Runnable hogeRunnable = new Runnable() {
        public void run() {
        }
    };

    private boolean hogeBoolean = true;
    
    private byte hogeByte = 1;
    
    private char hogeChar = 'A';

    private double hogeDouble = 999.9;

    private float hogeFloat = 999;
    
    private short hogeShort = 9;
    
    private long hogeLong = 9;

    public ValidatorActionFormEx_ValidatorActionFormExStub01() {
        super();

        // initial
        for (int i = 0; i < 4; i++) {
            this.hogeList.add(i, "data" + (i + 1));
            this.hogeObjectArray[i] = new Integer(i + 1);
            this.hogeMap.put("field" + (i + 1), "data" + (i + 1));
        }
    }

    public int getHogeInt() {
        return hogeInt;
    }

    public int[] getHogeIntArray() {
        return hogeIntArray;
    }

    public List getHogeList() {
        return hogeList;
    }

    public Map getHogeMap() {
        return hogeMap;
    }

    public Object[] getHogeObjectArray() {
        return hogeObjectArray;
    }

    public Runnable getHogeRunnable() {
        return hogeRunnable;
    }

    public String getHogeString() {
        return hogeString;
    }

    public String getHogeString2() {
        return hogeString2;
    }

    public String[] getHogeStringArray() {
        return hogeStringArray;
    }

    public boolean getHogeBoolean() {
        return hogeBoolean;
    }

    public byte getHogeByte() {
        return hogeByte;
    }

    public char getHogeChar() {
        return hogeChar;
    }

    public double getHogeDouble() {
        return hogeDouble;
    }

    public float getHogeFloat() {
        return hogeFloat;
    }
    
    public short getHogeShort() {
        return hogeShort;
    }
    
    public long getHogeLong() {
        return hogeLong;
    }
    
    public void setHogeInt(int i) {
        hogeInt = i;
    }

    public void setHogeIntArray(int[] is) {
        hogeIntArray = is;
    }

    public void setHogeList(List<String> list) {
        hogeList = list;
    }

    public void setHogeMap(Map<String, String> map) {
        hogeMap = map;
    }

    public void setHogeObjectArray(Object[] objects) {
        hogeObjectArray = objects;
    }

    public void setHogeRunnable(Runnable runnable) {
        hogeRunnable = runnable;
    }

    public void setHogeString(String string) {
        hogeString = string;
    }

    public void setHogeString2(String string) {
        hogeString2 = string;
    }

    public void setHogeStringArray(String[] strings) {
        hogeStringArray = strings;
    }
    
    public void setBoolean(boolean bool) {
        hogeBoolean = bool;
    }

    public void setByte(byte byt) {
        hogeByte = byt;
    }

    public void setChar(char cha) {
        hogeChar = cha;
    }
    
    public void setDouble(double dbl) {
        hogeDouble = dbl;
    }
    
    public void setFloat(float flt) {
        hogeFloat = flt;
    }
    
    public void setShort(short shrt) {
        hogeShort = shrt;
    }
    
    public void setLong(long lng) {
        hogeLong = lng;
    }

}
