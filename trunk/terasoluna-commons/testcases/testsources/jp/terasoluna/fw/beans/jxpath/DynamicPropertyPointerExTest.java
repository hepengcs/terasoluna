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

package jp.terasoluna.fw.beans.jxpath;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.jxpath.DynamicPropertyHandler;
import org.apache.commons.jxpath.MapDynamicPropertyHandler;
import org.apache.commons.jxpath.ri.QName;
import org.apache.commons.jxpath.ri.model.NodePointer;

import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.beans.jxpath.DynamicPropertyPointerEx} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * null�l���������߂�Map�p�v���p�e�B�|�C���^�g���N���X�B<br>
 * �O������F
 * <p>
 * 
 * @see jp.terasoluna.fw.beans.jxpath.DynamicPropertyPointerEx
 */
public class DynamicPropertyPointerExTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(DynamicPropertyPointerExTest.class);
    }

    /**
     * �������������s���B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * �I���������s���B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * �R���X�g���N�^�B
     * 
     * @param name ���̃e�X�g�P�[�X�̖��O�B
     */
    public DynamicPropertyPointerExTest(String name) {
        super(name);
    }

    /**
     * testGetLength01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) getBaseValue():null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) -:1<br>
     *         
     * <br>
     * �v�f�̒l��null�̏ꍇ�̃e�X�g�B�P��Ԃ��B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
	public void testGetLength01() throws Exception {
        // �O����
    	QName qName = new QName("name");
    	Map map = new HashMap();
    	map.put("key", null);
    	DynamicPropertyHandler handler = new MapDynamicPropertyHandler();
    	Locale locale = new Locale("ja");
    	NodePointer nodePointer = NodePointer.newNodePointer(qName, map, locale);
    	DynamicPropertyPointerEx pointer 
    		= new DynamicPropertyPointerEx(nodePointer, handler);
    	pointer.setPropertyName("key");

        // �e�X�g���{
    	assertEquals(1, pointer.getLength());
    }

    /**
     * testGetLength02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) getBaseValue():not null<br>
     *         (���) ValueUtils.getLength(value):�Ăяo���m�F���s�Ȃ�<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) -:ValueUtils.getLength(value)�̌���<br>
     *         
     * <br>
     * �v�f��null�ł͂Ȃ��ꍇ�̃e�X�g�BValueUtils.getLength()��Ԃ��B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("unchecked")
	public void testGetLength02() throws Exception {
        // �O����
    	QName qName = new QName("name");
    	Map map = new HashMap();
    	map.put("key", new String[]{"a", "b", "c"});
    	DynamicPropertyHandler handler = new MapDynamicPropertyHandler();
    	Locale locale = new Locale("ja");
    	NodePointer nodePointer = NodePointer.newNodePointer(qName, map, locale);
    	DynamicPropertyPointerEx pointer 
    		= new DynamicPropertyPointerEx(nodePointer, handler);
    	pointer.setPropertyName("key");

        // �e�X�g���{
    	assertEquals(3, pointer.getLength());
    }

}
