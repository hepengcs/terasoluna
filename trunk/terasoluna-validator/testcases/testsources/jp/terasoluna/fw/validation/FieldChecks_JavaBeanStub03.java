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

import java.lang.reflect.InvocationTargetException;

/**
 * {@link FieldChecksExtend}���e�X�g���邽�߂�JavaBean�B
 * 
 *
 */
public class FieldChecks_JavaBeanStub03 {

    /**
     * field1�B
     */
    @SuppressWarnings("unused")
    private String field1 = null;

    /**
     * field2�B
     */
    @SuppressWarnings("unused")
    private String field2 = null;

    /**
     * InvocationTargetException ���X���[����B
     * 
     * @return field1�B
     * @throws InvocationTargetException �X���[������O�B
     */
    public String getField1() throws InvocationTargetException {
        throw new InvocationTargetException(new RuntimeException());
    }

    /**
     * field1 ��ݒ肷��B
     * 
     * @param field1 �ݒ肷�� field1�B
     */
    public void setField1(String field1) {
        this.field1 = field1;
    }

    /**
     * InvocationTargetException ���X���[����B
     * 
     * @return field2�B
     * @throws InvocationTargetException �X���[������O�B
     */
    public String getField2() throws InvocationTargetException {
        throw new InvocationTargetException(new RuntimeException());
    }

    /**
     * field2 ��ݒ肷��B
     * 
     * @param field2 �ݒ肷�� field2�B
     */
    public void setField2(String field2) {
        this.field2 = field2;
    }
}
