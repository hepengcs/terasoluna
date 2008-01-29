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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;

import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.beans.DatePropertyEditorRegistrar;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.beans.DatePropertyEditorRegistrar} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * Date�^�̃v���p�e�B�G�f�B�^�𐶐�����N���X�B
 * <p>
 * 
 * @see jp.terasoluna.fw.beans.DatePropertyEditorRegistrar
 */
public class DatePropertyEditorRegistrarTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(DatePropertyEditorRegistrarTest.class);
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
    public DatePropertyEditorRegistrarTest(String name) {
        super(name);
    }

    /**
     * testSetDateFormat01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) dateFormat:SimpleDateFormat("yyyyMMdd")<br>
     *         (���) this.dateFormat:SimpleDateFormat("yyyy/MM/dd")<br>
     *         (���) �Ȃ�:�[<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.dateFormat:SimpleDateFormat("yyyyMMdd")<br>
     *         
     * <br>
     * dateFormate������set���\�b�h�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetDateFormat01() throws Exception {
        // �O����
    	DatePropertyEditorRegistrar registrar
    		= new DatePropertyEditorRegistrar();
    	DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        // �e�X�g���{
    	registrar.setDateFormat(dateFormat);

        // ����
    	assertSame(dateFormat, UTUtil.getPrivateField(registrar, "dateFormat"));
    }

    /**
     * testRegisterCustomEditors01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) registry:PropertyEditorRegistry�̃��b�N<br>
     *         (���) this.dateFormat:SimpleDateFormat("yyyy/MM/dd")<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) registry.registerCustomEditor�i�j:�������󂯎�������Ƃ��m�F����B<br>
     *         
     * <br>
     * �v���p�e�B�G�f�B�^�������\�b�h���Ăяo���e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testRegisterCustomEditors01() throws Exception {
        // �O����
    	DatePropertyEditorRegistrar registrar
            = new DatePropertyEditorRegistrar();
    	PropertyEditorRegistrar_PropertyEditorRegistryStub01 registry
    		= new PropertyEditorRegistrar_PropertyEditorRegistryStub01();

        // �e�X�g���{
    	registrar.registerCustomEditors(registry);

        // ����
    	assertSame(Date.class, registry.clazz);
    	assertSame(CustomDateEditor.class, registry.editor.getClass());
    	SimpleDateFormat resultDateFormat 
    		= (SimpleDateFormat) UTUtil.getPrivateField(registry.editor, "dateFormat");
    	assertSame("yyyy/MM/dd", UTUtil.getPrivateField(resultDateFormat, "pattern"));
    }

}
