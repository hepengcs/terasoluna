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

package jp.terasoluna.fw.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * FieldChecksTest�Ŏg�p����X�^�u�N���X�B
 *
 */
public class FieldChecks_JavaBeanStub01 {

    /**
     * �e�X�g�p�v���p�e�B�B
     */
    private String field = "testProperty";

    /**
     * �e�X�g�p�z��v���p�e�B�B
     */
    private String[] array = null;

    /**
     * �e�X�g�pList�^�v���p�e�B�B
     */
    private List list = new ArrayList();

    /**
     * �e�X�g�pint�z��v���p�e�B�B
     */
    private int[] intArray = null;

    /**
     * �e�X�g�pJavaBean�z��v���p�e�B�B
     */
    private Object[] beanArray = null;

    /**
     * �e�X�g�p�z��v���p�e�B�B
     */
    private String[] field1 = null;

    /**
     * �e�X�g�pString�^�v���p�e�B�B
     */
    private String field2 = null;

    /**
     * �e�X�g�pCollection�^�v���p�e�B�B
     */
    private Collection field3 = null;

    /**
     * �e�X�g�p�v���~�e�B�u�z��^�v���p�e�B�B
     */
    private int[] field4 = null;

    /**
     * �e�X�g�p�z��v���p�e�B�B
     */
    @SuppressWarnings("unused")
    private String[] field5 = null;

    /**
     * @return field ��߂��܂��B
     */
    public String getField() {
        return field;
    }

    /**
     * @param field �ݒ肷�� field�B
     */
    public void setField(String field) {
        this.field = field;
    }

    /**
     * @return array ��߂��܂��B
     */
    public String[] getArray() {
        return array;
    }

    /**
     * @param array �ݒ肷�� array�B
     */
    public void setArray(String[] array) {
        this.array = array;
    }

    /**
     * @return list ��߂��܂��B
     */
    public List getList() {
        return list;
    }

    /**
     * @param list �ݒ肷�� list�B
     */
    public void setList(List list) {
        this.list = list;
    }

    /**
     * @return intArray ��߂��܂��B
     */
    public int[] getIntArray() {
        return intArray;
    }

    /**
     * @param intArray �ݒ肷�� intArray�B
     */
    public void setIntArray(int[] intArray) {
        this.intArray = intArray;
    }


    /**
     * @return beanArray ��߂��܂��B
     */
    public Object[] getBeanArray() {
        return beanArray;
    }

    /**
     * @param beanArray �ݒ肷�� beanArray�B
     */
    public void setBeanArray(Object[] beanArray) {
        this.beanArray = beanArray;
    }

    /**
     * @return field1 ��߂��܂��B
     */
    public String[] getField1() {
        return field1;
    }

    /**
     * @param field1 �ݒ肷�� field1�B
     */
    public void setField1(String[] field1) {
        this.field1 = field1;
    }

    /**
     * @return field2 ��߂��܂��B
     */
    public String getField2() {
        return field2;
    }

    /**
     * @param field2 �ݒ肷�� field2�B
     */
    public void setField2(String field2) {
        this.field2 = field2;
    }

    /**
     * @return field3 ��߂��܂��B
     */
    public Collection getField3() {
        return field3;
    }

    /**
     * @param field3 �ݒ肷�� field3�B
     */
    public void setField3(Collection field3) {
        this.field3 = field3;
    }

    /**
     * @return field4 ��߂��܂��B
     */
    public int[] getField4() {
        return field4;
    }

    /**
     * @param field4 �ݒ肷�� field4�B
     */
    public void setField4(int[] field4) {
        this.field4 = field4;
    }

    /**
     * @return field5 ��߂��܂��B
     */
    public String[] getField5() {
        throw new RuntimeException();
    }

    /**
     * @param field5 �ݒ肷�� field5�B
     */
    public void setField5(String[] field5) {
        this.field5 = field5;
    }

}
