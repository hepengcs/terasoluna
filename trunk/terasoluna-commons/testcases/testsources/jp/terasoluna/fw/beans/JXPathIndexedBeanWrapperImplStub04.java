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

package jp.terasoluna.fw.beans;

import java.util.ArrayList;

/**
 *
 */
public class JXPathIndexedBeanWrapperImplStub04 extends
        JXPathIndexedBeanWrapperImpl {

    /**
     * ������ێ����鑮���B
     */
    public ArrayList<String> extractAttributeNameArg1 = new ArrayList<String>();

    /**
     * ������ێ����鑮���B
     */
    public ArrayList<String> extractMapAttributeNameArg1 = new ArrayList<String>();
    
    private String[] extractMapAttributeNameReturnValue = { "aaa", "ddd" };
    
    private int extractMapAttributeNameInnerCount = 0;

    /**
     * ������ێ����鑮���B
     */
    public ArrayList<String> extractMapKeyArg1 = new ArrayList<String>();
    private String[] extractMapKeyReturnValue = { "bbb", "eee" };
    private int extractMapKeyInnerCount = 0;

    /**
     * ������ێ����鑮���B
     */
    public ArrayList<String> extractDecrementIndexArg1 = new ArrayList<String>();
    
    /**
     * �߂�l��ێ����鑮���B
     */
    public String extractDecrementIndexReturnValue = null;

    /**
     * ������ێ����鑮���B
     */
    public ArrayList<String> isMapAttributeArg1 = new ArrayList<String>();
    
    /**
     * �߂�l��ێ����鑮���B
     */
    public boolean isMapAttributeReturnValue = false;

    /**
     * �߂�l��ێ����鑮���B
     */
    public ArrayList<String> isMapObjectArg1 = new ArrayList<String>();
    
    /**
     * �߂�l��ێ����鑮���B
     */
    public boolean isMapObjectReturnValue = false;
    
    /**
     * �R���X�g���N�^
     * @param target �^�[�Q�b�g��JavaBean
     */
    public JXPathIndexedBeanWrapperImplStub04(Object target) {
        super(target);
    }

    /**
     * 
     */
    @Override
    protected String extractAttributeName(String node) {
        extractAttributeNameArg1.add(node);
        return "aaa";
    }

    /**
     * �����ӁF�Ăяo���񐔂ɂ�� �߂�l���ς��B
     */
    @Override
    protected String extractMapAttributeName(String node) {
        String returnValue = null;

        extractMapAttributeNameArg1.add(node);
        returnValue = extractMapAttributeNameReturnValue[extractMapAttributeNameInnerCount];

        extractMapAttributeNameInnerCount++;

        return returnValue;
    }

    /**
     * �����ӁF�Ăяo���񐔂ɂ�� �߂�l���ς��B
     */
    @Override
    protected String extractMapKey(String node) {
        String returnValue = null;

        extractMapKeyArg1.add(node);
        returnValue = extractMapKeyReturnValue[extractMapKeyInnerCount];

        extractMapKeyInnerCount++;

        return returnValue;
    }

    /**
     * �y�Ăяo�����@�z
     * extractDecrementIndexReturnValue �ɖ߂�l��ݒ肵����ŌĂяo���B
     */
    @Override
    protected String extractDecrementIndex(String node) {
        extractDecrementIndexArg1.add(node);
        return extractDecrementIndexReturnValue;
    }

    /**
     * �y�Ăяo�����@�z
     *  isMapAttributeReturnValue �ɖ߂�l��ݒ肵����ŌĂяo���B
     */
    @Override
    protected boolean isMapAttribute(String node) {
        isMapAttributeArg1.add(node);
        return isMapAttributeReturnValue;
    }

    /**
     * �y�Ăяo�����@�z
     *  isMapObjectReturnValue �ɖ߂�l��ݒ肵����ŌĂяo���B
     */
    @Override
    protected boolean isMapObject(String node) {
        isMapObjectArg1.add(node);
        return isMapObjectReturnValue;
    }

}
