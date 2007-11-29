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

import org.apache.commons.jxpath.ri.QName;
import org.apache.commons.jxpath.ri.model.NodePointer;
import org.apache.commons.jxpath.ri.model.beans.NullPointer;

import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.beans.jxpath.BeanPointerFactoryEx} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * Bean�|�C���^�t�@�N�g���̊g���N���X�B<br>
 * �O������F
 * <p>
 * 
 * @see jp.terasoluna.fw.beans.jxpath.BeanPointerFactoryEx
 */
public class BeanPointerFactoryExTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(BeanPointerFactoryExTest.class);
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
    public BeanPointerFactoryExTest(String name) {
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
     * ���Ғl�F(�߂�l) -:850<br>
     *         
     * <br>
     * �\�[�g�����擾���郁�\�b�h�B�Œ�l��Ԃ��B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetOrder01() throws Exception {
        // �O����
    	BeanPointerFactoryEx factory = new BeanPointerFactoryEx();

        // �e�X�g���{
    	assertEquals(850, factory.getOrder());
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
     *         (����) bean:new Object()<br>
     *         (����) locale:Locale("ja")<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) NodePointer:new BeanPointerEX {<br>
     *                      locale=������locale<br>
     *                      name=������name<br>
     *                      bean=������bean<br>
     *                  }<br>
     *         
     * <br>
     * �m�[�h�|�C���^���擾����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateNodePointerQname01() throws Exception {
        // �O����
    	BeanPointerFactoryEx factory = new BeanPointerFactoryEx();
    	QName qName = new QName("name");
    	Object bean = new Object();
    	Locale locale = new Locale("ja");

        // �e�X�g���{
    	NodePointer result = factory.createNodePointer(qName, bean, locale);

        // ����
    	assertSame(BeanPointerEx.class, result.getClass());
    	assertSame(locale, result.getLocale());
    	assertSame(qName, result.getName());
    	assertSame(bean, UTUtil.getPrivateField(result, "bean"));
    }

    /**
     * testCreateNodePointerNodePointer01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
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
    	BeanPointerFactoryEx factory = new BeanPointerFactoryEx();
    	QName qName = new QName("name");
    	Locale locale = new Locale("ja");
    	NodePointer nodePointer = NodePointer.newNodePointer(qName, null, locale);

        // �e�X�g���{
    	NodePointer result = factory.createNodePointer(nodePointer, qName, null);

        // ����
    	assertSame(NullPointer.class, result.getClass());
    	assertSame(nodePointer, result.getParent());
    	assertSame(qName, result.getName());
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
     *         (����) bean:new Object()<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) NodePointer:new BeanPointerEX {<br>
     *                      parent=������parent<br>
     *                      name=������name<br>
     *                      bean=������bean<br>
     *                  }<br>
     *         
     * <br>
     * �^�[�Q�b�g��null�ł͂Ȃ��ꍇ�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreateNodePointerNodePointer02() throws Exception {
        // �O����
    	BeanPointerFactoryEx factory = new BeanPointerFactoryEx();
    	QName qName = new QName("name");
    	Object bean = new Object();
    	Locale locale = new Locale("ja");
    	NodePointer nodePointer = NodePointer.newNodePointer(qName, bean, locale);

        // �e�X�g���{
    	NodePointer result = factory.createNodePointer(nodePointer, qName, bean);

        // ����
    	assertSame(BeanPointerEx.class, result.getClass());
    	assertSame(nodePointer, result.getParent());
    	assertSame(qName, result.getName());
    	assertSame(bean, UTUtil.getPrivateField(result, "bean"));
    }
}
