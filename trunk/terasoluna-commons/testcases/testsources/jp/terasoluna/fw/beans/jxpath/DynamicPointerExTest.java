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

import org.apache.commons.jxpath.DynamicPropertyHandler;
import org.apache.commons.jxpath.MapDynamicPropertyHandler;
import org.apache.commons.jxpath.ri.QName;
import org.apache.commons.jxpath.ri.model.NodePointer;

import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.beans.jxpath.DynamicPointerEx} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * null�l���������߂�Map�|�C���^�g���N���X�B<br>
 * �O������F
 * <p>
 * 
 * @see jp.terasoluna.fw.beans.jxpath.DynamicPointerEx
 */
public class DynamicPointerExTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(DynamicPointerExTest.class);
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
    public DynamicPointerExTest(String name) {
        super(name);
    }

    /**
     * testDynamicPointerExQname01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) name:not null<br>
     *         (����) bean:new Object()<br>
     *         (����) handler:not null<br>
     *         (����) locale:Locale("ja")<br>
     *         (���) this.handler:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.handler:�����Őݒ肳�ꂽ�l�B<br>
     *         
     * <br>
     * �R���X�g���N�^���Ăяo���e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDynamicPointerExQname01() throws Exception {
        // �O����
    	QName qName = new QName("name");
    	Object bean = new Object();
    	DynamicPropertyHandler handler = new MapDynamicPropertyHandler();
    	Locale locale = new Locale("ja");

        // �e�X�g���{
    	DynamicPointerEx result = new DynamicPointerEx(qName, bean, handler,locale);

        // ����
    	assertSame(handler, UTUtil.getPrivateField(result, "handler"));
    }

    /**
     * testDynamicPointerExNodePointer01()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) parent:not null<br>
     *         (����) name:not null<br>
     *         (����) bean:new Object()<br>
     *         (����) handler:not null<br>
     *         (���) this.handler:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.handler:�����Őݒ肳�ꂽ�l�B<br>
     *         
     * <br>
     * �R���X�g���N�^���Ăяo���e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDynamicPointerExNodePointer01() throws Exception {
        // �O����
    	QName qName = new QName("name");
    	Object bean = new Object();
    	DynamicPropertyHandler handler = new MapDynamicPropertyHandler();
    	Locale locale = new Locale("ja");
    	NodePointer nodePointer = NodePointer.newNodePointer(qName, bean, locale);

        // �e�X�g���{
    	DynamicPointerEx result = new DynamicPointerEx(nodePointer, qName, bean, handler);

        // ����
    	assertSame(handler, UTUtil.getPrivateField(result, "handler"));
    }

    /**
     * testGetPropertyPointer01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) this.handler:not null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) PropertyPointer:new DynamicPropertyPointerEx{<br>
     *                      parent=this<br>
     *                      handler=�O�������handler<br>
     *                  }<br>
     *         
     * <br>
     * �v���p�e�B�|�C���^���擾���郁�\�b�h�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetPropertyPointer01() throws Exception {
        // �O����
    	QName qName = new QName("name");
    	Object bean = new Object();
    	DynamicPropertyHandler handler = new MapDynamicPropertyHandler();
    	Locale locale = new Locale("ja");
    	DynamicPointerEx pointer = new DynamicPointerEx(qName, bean, handler,locale);

        // �e�X�g���{
    	Object result = pointer.getPropertyPointer();

        // ����
    	assertSame(DynamicPropertyPointerEx.class, result.getClass());
    	assertSame(pointer, ((DynamicPropertyPointerEx) result).getParent());
    	assertSame(handler, UTUtil.getPrivateField(result, "handler"));
    }

}
