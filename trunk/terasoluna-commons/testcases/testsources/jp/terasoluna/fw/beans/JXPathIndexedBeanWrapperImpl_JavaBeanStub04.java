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
import java.util.List;

/**
 * IndexedBeanWrapperImplTest#testGetIndexedPropertyNameList04�Ŏg�p����
 * �X�^�u�N���X�B
 */
public class JXPathIndexedBeanWrapperImpl_JavaBeanStub04 {

    /**
     * �e�X�g�p�v���p�e�B�B
     */
    private List<Foo> foo = new ArrayList<Foo>();
    
    /**
     * �e�X�g�p�v���p�e�B�B
     */
    private Foo[] foos = null;

    /**
     * @return foo ��߂��܂��B
     */
    public List<Foo> getFoo() {
        return foo;
    }

    /**
     * @param foo �ݒ肷�� foo�B
     */
    public void setFoo(List<Foo> foo) {
        this.foo = foo;
    }
    
    /**
     * foos���擾����B
     * @return foos�B
     */
    public Foo[] getFoos() {
        return foos;
    }

    /**
     * foos��ݒ肷��
     * @param foos foos�B
     */
    public void setFoos(Foo[] foos) {
        this.foos = foos;
    }

    /**
     * �e�X�g�p�v���p�e�B�N���X�B
     */
    public static class Foo {

        /**
         * �e�X�g�p�v���p�e�B�B
         */
        private Bar bar = null;

        /**
         * �e�X�g�p�v���p�e�B�B
         */
        private String property = "";
        
        /**
         * @return bar ��߂��܂��B
         */
        public Bar getBar() {
            return bar;
        }

        /**
         * @param bar �ݒ肷�� bar�B
         */
        public void setBar(Bar bar) {
            this.bar = bar;
        }

        /**
         * property���擾����B
         * @return property�B
         */
        public String getProperty() {
            return property;
        }

        /**
         * property��ݒ肷��
         * @param property property�B
         */
        public void setProperty(String property) {
            this.property = property;
        }

    }

    /**
     * �e�X�g�p�v���p�e�B�N���X�B
     */
    public static class Bar {

        /**
         * �e�X�g�p�v���p�e�B�B
         */
        private String hoge = null;
        
        /**
         * �e�X�g�p�v���p�e�B�B
         */
        private String[] hogeArray = null;

        /**
         * @return hoge ��߂��܂��B
         */
        public String getHoge() {
            return hoge;
        }

        /**
         * @param hoge �ݒ肷�� hoge�B
         */
        public void setHoge(String hoge) {
            this.hoge = hoge;
        }

        /**
         * hogeArray���擾����B
         * @return hogeArray
         */
        public String[] getHogeArray() {
            return hogeArray;
        }

        /**
         * hogeArray��ݒ肷��B
         * @param hogeArray hogeArray
         */
        public void setHogeArray(String[] hogeArray) {
            this.hogeArray = hogeArray;
        }

    }

    

}
