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

import java.util.Locale;

import org.apache.commons.jxpath.JXPathBasicBeanInfo;
import org.apache.commons.jxpath.ri.QName;
import org.apache.commons.jxpath.ri.model.NodePointer;

import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.beans.jxpath.BeanPropertyPointerEx} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * null�l���������߂�Bean�v���p�e�B�|�C���^�g���N���X�B<br>
 * �O������F
 * <p>
 * 
 * @see jp.terasoluna.fw.beans.jxpath.BeanPropertyPointerEx
 */
public class BeanPropertyPointerExTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(BeanPropertyPointerExTest.class);
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
    public BeanPropertyPointerExTest(String name) {
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
     * ���͒l�F(���) super.getLength():1<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) -:1<br>
     *         
     * <br>
     * �v�f���P��Ԃ��p�^�[���̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetLength01() throws Exception {
        // �O����
    	QName qName = new QName("property");
    	BeanPropertyPointerEx_JavaBeanStub01 bean 
    		= new BeanPropertyPointerEx_JavaBeanStub01();
    	Locale locale = new Locale("ja");
    	NodePointer nodePointer = NodePointer.newNodePointer(qName, bean, locale);
    	
    	JXPathBasicBeanInfo beanInfo = new JXPathBasicBeanInfo(bean.getClass());
    	BeanPropertyPointerEx pointer = new BeanPropertyPointerEx(nodePointer, beanInfo);
    	pointer.setPropertyName("property");

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
     * ���͒l�F(���) super.getLength():0<br>
     *         (���) getBaseValue():null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) -:1<br>
     *         
     * <br>
     * �v�f���O���l��null�̃p�^�[���̃e�X�g�B�P��Ԃ��B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetLength02() throws Exception {
        // �O����
    	QName qName = new QName("property");
    	BeanPropertyPointerEx_JavaBeanStub01 bean 
    		= new BeanPropertyPointerEx_JavaBeanStub01();
    	bean.setListProperty(null);
    	Locale locale = new Locale("ja");
    	NodePointer nodePointer = NodePointer.newNodePointer(qName, bean, locale);
    	
    	JXPathBasicBeanInfo beanInfo = new JXPathBasicBeanInfo(bean.getClass());
    	BeanPropertyPointerEx pointer = new BeanPropertyPointerEx(nodePointer, beanInfo);
    	pointer.setPropertyName("listProperty");

        // �e�X�g���{
    	assertEquals(1, pointer.getLength());
    }

    /**
     * testGetLength03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) super.getLength():0<br>
     *         (���) getBaseValue():not null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) -:0<br>
     *         
     * <br>
     * �v�f���O���l��not null�̃p�^�[���̃e�X�g�B�O��Ԃ��B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetLength03() throws Exception {
        // �O����
    	QName qName = new QName("property");
    	BeanPropertyPointerEx_JavaBeanStub01 bean 
    		= new BeanPropertyPointerEx_JavaBeanStub01();
    	Locale locale = new Locale("ja");
    	NodePointer nodePointer = NodePointer.newNodePointer(qName, bean, locale);
    	
    	JXPathBasicBeanInfo beanInfo = new JXPathBasicBeanInfo(bean.getClass());
    	BeanPropertyPointerEx pointer = new BeanPropertyPointerEx(nodePointer, beanInfo);
    	pointer.setPropertyName("listProperty");

        // �e�X�g���{
    	assertEquals(0, pointer.getLength());
    }

    /**
     * testIsCollection01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>s
     * ���͒l�F(���) getBaseValue():null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         
     * <br>
     * �l��null�̏ꍇ�̃e�X�g�BFalse��Ԃ��B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsCollection01() throws Exception {
        // �O����
    	QName qName = new QName("property");
    	BeanPropertyPointerEx_JavaBeanStub01 bean 
    		= new BeanPropertyPointerEx_JavaBeanStub01();
    	Locale locale = new Locale("ja");
    	NodePointer nodePointer = NodePointer.newNodePointer(qName, bean, locale);
    	
    	JXPathBasicBeanInfo beanInfo = new JXPathBasicBeanInfo(bean.getClass());
    	BeanPropertyPointerEx pointer = new BeanPropertyPointerEx(nodePointer, beanInfo);
    	pointer.setPropertyName("property");

        // �e�X�g���{
    	assertFalse(pointer.isCollection());
    }

    /**
     * testIsCollection02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) getBaseValue():not null<br>
     *         (���) super.isCollection():�Ăяo���m�F���s�Ȃ�<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:super.isCollection()�̌���<br>
     *         
     * <br>
     * �l��null�ł͂Ȃ��ꍇ�̃e�X�g�BSuper.isCollection()�̖߂�l��Ԃ��B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsCollection02() throws Exception {
        // �O����
    	QName qName = new QName("property");
    	BeanPropertyPointerEx_JavaBeanStub01 bean 
    		= new BeanPropertyPointerEx_JavaBeanStub01();
    	Locale locale = new Locale("ja");
    	NodePointer nodePointer = NodePointer.newNodePointer(qName, bean, locale);
    	
    	JXPathBasicBeanInfo beanInfo = new JXPathBasicBeanInfo(bean.getClass());
    	BeanPropertyPointerEx pointer = new BeanPropertyPointerEx(nodePointer, beanInfo);
    	pointer.setPropertyName("listProperty");

        // �e�X�g���{
    	assertTrue(pointer.isCollection());
    }

}
