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

package jp.terasoluna.fw.web.rich.springmvc.bind.creator;

import java.util.Map;

import org.springframework.beans.BeanWrapper;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.web.rich.springmvc.bind.creator.QueryServletRequestDataBinderCreator;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.rich.springmvc.bind.creator.
 * QueryServletRequestDataBinderCreator} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �N�G���`���̃��N�G�X�g�f�[�^�ɑΉ�����ServletRequestDataBinder�����N���X��
 * �ԋp����B<br>
 * �O������F������request�Acommand�ArequestName��Null�l�ɂȂ�Ȃ��B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.bind.creator.
 * QueryServletRequestDataBinderCreator
 */
public class QueryServletRequestDataBinderCreatorTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(
                QueryServletRequestDataBinderCreatorTest.class);
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
    public QueryServletRequestDataBinderCreatorTest(String name) {
        super(name);
    }

    /**
     * testCreate01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) command:not null<br>
     *         (����) requestName:not null<br>
     *                �i"sum"�j<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) binder:ServletRequestDataBinder���Ԃ����B<br>
     *                  bindResult.target������command���ݒ肳���B<br>
     *                  bindResult.objectName������requestName���ݒ肳���B<br>
     *                  bindResult.beanWrapper�����ɃJ�X�^���G�f�B�^�[��
     *                  �ݒ肳���B<br>
     *                  �ibyte[]�AByteArrayMultipartFileEditor)<br>
     *         
     * <br>
     * �N�G���`���̃o�C���_�𐶐�����e�X�g�B���N�G�X�g����"sum"�B
     * ServletRequestDataBinder�����������B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreate01() throws Exception {
        // �O����
        QueryServletRequestDataBinderCreator creator = 
            new QueryServletRequestDataBinderCreator();
        Object command = new Object();
        String requestName = "sum";
        MockHttpServletRequest request = new MockHttpServletRequest();

        // �e�X�g���{
        ServletRequestDataBinder binder = creator.create(
                request, command, requestName);

        // ����
        Object target = UTUtil.getPrivateField(binder.getBindingResult(), "target");
        assertSame(command, target);
        Object objectName = UTUtil.getPrivateField(
                binder.getBindingResult(), "objectName");
        assertEquals(requestName, objectName);
        BeanWrapper bw = (BeanWrapper)UTUtil.getPrivateField(
                binder.getBindingResult(), "beanWrapper");
        Map editorMap = (Map)UTUtil.getPrivateField(bw, "customEditors");
        assertEquals((editorMap.get(byte[].class)).getClass().getName(),
                ByteArrayMultipartFileEditor.class.getName());
    }

    /**
     * testCreate02()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) command:not null<br>
     *         (����) requestName:not null<br>
     *                �i�󕶎��j<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) binder:ServletRequestDataBinder���Ԃ����B<br>
     *                  bindResult.target������command���ݒ肳���B<br>
     *                  bindResult.objectName������requestName���ݒ肳���B<br>
     *                  bindResult.beanWrapper�����ɃJ�X�^���G�f�B�^�[��
     *                  �ݒ肳���B<br>
     *                  �ibyte[]�AByteArrayMultipartFileEditor)<br>
     *         
     * <br>
     * �N�G���`���̃o�C���_�𐶐�����e�X�g�B���N�G�X�g������̏ꍇ�̃e�X�g�B
     * ServletRequestDataBinder�����������B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCreate02() throws Exception {
        // �O����
        QueryServletRequestDataBinderCreator creator = 
            new QueryServletRequestDataBinderCreator();
        Object command = new Object();
        String requestName = "";
        MockHttpServletRequest request = new MockHttpServletRequest();

        // �e�X�g���{
        ServletRequestDataBinder binder = creator.create(
                request, command, requestName);

        // ����
        Object target = UTUtil.getPrivateField(binder.getBindingResult(), "target");
        assertSame(command, target);
        Object objectName = UTUtil.getPrivateField(
                binder.getBindingResult(), "objectName");
        assertEquals(requestName, objectName);
        BeanWrapper bw = (BeanWrapper)UTUtil.getPrivateField(
                binder.getBindingResult(), "beanWrapper");
        Map editorMap = (Map)UTUtil.getPrivateField(bw, "customEditors");
        assertEquals((editorMap.get(byte[].class)).getClass().getName(),
                ByteArrayMultipartFileEditor.class.getName());
    }

}
