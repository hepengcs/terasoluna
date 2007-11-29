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

import org.apache.commons.jxpath.ri.QName;
import org.apache.commons.jxpath.ri.model.NodePointer;
import org.apache.commons.jxpath.ri.model.beans.NullPointer;

import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.beans.jxpath.DynamicPointerFactoryEx} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * Map�p�|�C���^�t�@�N�g���̊g���N���X�B<br>
 * �O������F
 * <p>
 * 
 * @see jp.terasoluna.fw.beans.jxpath.DynamicPointerFactoryEx
 */
public class DynamicPointerFactoryExTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(DynamicPointerFactoryExTest.class);
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
    public DynamicPointerFactoryExTest(String name) {
        super(name);
    }

    /**
     * testGetOrder01()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F
     * <br>
     * ���Ғl�F(�߂�l) -:750<br>
     *         
     * <br>
     * �\�[�g�����擾���郁�\�b�h�B�Œ�l��Ԃ��B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetOrder01() throws Exception {
        // �O����
    	DynamicPointerFactoryEx factory = new DynamicPointerFactoryEx();

        // �e�X�g���{
    	assertEquals(750, factory.getOrder());
    }

    /**
     * testCreateNodePointerQname01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) name:not null<br>
     *         (����) bean:new HashMap() {<br>
     *                    key="value"<br>
     *                }<br>
     *         (����) locale:Locale("ja")<br>
     *         (���) bi.isDynamic():true<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) NodePointer:new DynamicPointerEX {<br>
     *                      locale=������locale<br>
     *                      name=������name<br>
     *                      bean=������bean<br>
     *                  }<br>
     *         
     * <br>
     * Map�^�̃^�[�Q�b�g�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateNodePointerQname01() throws Exception {
        // �O����
    	DynamicPointerFactoryEx factory = new DynamicPointerFactoryEx();
    	QName qName = new QName("name");
    	Object bean = new HashMap();
    	Locale locale = new Locale("ja");

        // �e�X�g���{
    	NodePointer result = factory.createNodePointer(qName, bean, locale);

        // ����
    	assertSame(DynamicPointerEx.class, result.getClass());
    	assertSame(locale, result.getLocale());
    	assertSame(qName, result.getName());
    	assertSame(bean, UTUtil.getPrivateField(result, "bean"));
    }

    /**
     * testCreateNodePointerQname02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) name:not null<br>
     *         (����) bean:new Object()<br>
     *         (����) locale:Locale("ja")<br>
     *         (���) bi.isDynamic():false<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) NodePointer:null<br>
     *         
     * <br>
     * Map�^�ł͂Ȃ��^�[�Q�b�g�̃e�X�g�BNull��Ԃ��B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateNodePointerQname02() throws Exception {
        // �O����
    	DynamicPointerFactoryEx factory = new DynamicPointerFactoryEx();
    	QName qName = new QName("name");
    	Object bean = new Object();
    	Locale locale = new Locale("ja");

        // �e�X�g���{
    	assertNull(factory.createNodePointer(qName, bean, locale));
    }

    /**
     * testCreateNodePointerNodePointer01()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) parent:not null<br>
     *         (����) name:not null<br>
     *         (����) bean:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) NodePointer:new NullPointer {<br>
     *                      parent=������parent<br>
     *                      name=������name<br>
     *                  }<br>
     *         
     * <br>
     * �^�[�Q�b�g��null�̏ꍇ�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateNodePointerNodePointer01() throws Exception {
        // �O����
    	DynamicPointerFactoryEx factory = new DynamicPointerFactoryEx();
    	QName qName = new QName("name");
    	Locale locale = new Locale("ja");
    	NodePointer nodePointer = NodePointer.newNodePointer(qName, null, locale);

        // �e�X�g���{
    	NodePointer result = factory.createNodePointer(nodePointer, qName, null);

        // ����
    	assertSame(NullPointer.class, result.getClass());
    	assertSame(qName, result.getName());
    	assertSame(nodePointer, result.getParent());
    }

    /**
     * testCreateNodePointerNodePointer02()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) parent:not null<br>
     *         (����) name:not null<br>
     *         (����) bean:new HashMap() {<br>
     *                    key="value"<br>
     *                }<br>
     *         (���) bi.isDynamic():true<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) NodePointer:new DynamicPointerEX {<br>
     *                      parent=������parent<br>
     *                      name=������name<br>
     *                      bean=������bean<br>
     *                  }<br>
     *         
     * <br>
     * Map�^�̃^�[�Q�b�g�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateNodePointerNodePointer02() throws Exception {
        // �O����
    	DynamicPointerFactoryEx factory = new DynamicPointerFactoryEx();
    	QName qName = new QName("name");
    	Object bean = new HashMap();
    	
    	Locale locale = new Locale("ja");
    	NodePointer nodePointer = NodePointer.newNodePointer(qName, bean, locale);

        // �e�X�g���{
    	NodePointer result = factory.createNodePointer(nodePointer, qName, bean);

        // ����
    	assertSame(DynamicPointerEx.class, result.getClass());
    	assertSame(nodePointer, result.getParent());
    	assertSame(qName, result.getName());
    	assertSame(bean, UTUtil.getPrivateField(result, "bean"));
    }

    /**
     * testCreateNodePointerNodePointer03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) parent:not null<br>
     *         (����) name:not null<br>
     *         (����) bean:new Object()<br>
     *         (���) bi.isDynamic():false<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) NodePointer:null<br>
     *         
     * <br>
     * Map�^�ł͂Ȃ��^�[�Q�b�g�̃e�X�g�BNull��Ԃ��B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateNodePointerNodePointer03() throws Exception {
        // �O����
    	DynamicPointerFactoryEx factory = new DynamicPointerFactoryEx();
    	QName qName = new QName("name");
    	Object bean = new Object();
    	
    	Locale locale = new Locale("ja");
    	NodePointer nodePointer = NodePointer.newNodePointer(qName, bean, locale);

        // �e�X�g���{
    	assertNull(factory.createNodePointer(nodePointer, qName, bean));
    }

}
