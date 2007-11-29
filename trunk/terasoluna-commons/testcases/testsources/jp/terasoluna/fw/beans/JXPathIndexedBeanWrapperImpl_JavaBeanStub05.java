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
 * IndexedBeanWrapperImplTest#testGetIndexedPropertyNameList06�Ŏg�p����
 * �X�^�u�N���X�B
 */
public class JXPathIndexedBeanWrapperImpl_JavaBeanStub05 {

    /**
     * �e�X�g�p�v���p�e�B�B
     */
    private Foo foo = null;

    /**
     * @return foo ��߂��܂��B
     */
    public Foo getFoo() {
        return foo;
    }

    /**
     * @param foo �ݒ肷�� foo�B
     */
    public void setFoo(Foo foo) {
        this.foo = foo;
    }

    /**
     * �e�X�g�p�v���p�e�B�N���X�B
     */
    public static class Foo {

        /**
         * �e�X�g�p�v���p�e�B�B
         */
        private List<Bar> bar = new ArrayList<Bar>();

        /**
         * @return bar ��߂��܂��B
         */
        public List<Bar> getBar() {
            return bar;
        }

        /**
         * @param bar �ݒ肷�� bar�B
         */
        public void setBar(List<Bar> bar) {
            this.bar = bar;
        }

    }

    /**
     * �e�X�g�p�v���p�e�B�N���X�B
     */
    public static class Bar {

        /**
         * �e�X�g�p�v���p�e�B�B
         */
        private List<String> hoge = new ArrayList<String>();

        /**
         * @return hoge ��߂��܂��B
         */
        public List<String> getHoge() {
            return hoge;
        }

        /**
         * @param hoge �ݒ肷�� hoge�B
         */
        public void setHoge(List<String> hoge) {
            this.hoge = hoge;
        }

    }

}
