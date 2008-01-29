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

package jp.terasoluna.fw.web.rich.context;

import java.util.HashMap;
import java.util.Map;

import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.web.rich.context.RequestContext;

import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.rich.context.RequestContext}
 * �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 *
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * ���N�G�X�g���E�Ɩ��v���p�e�B��ێ����邽�߂̃N���X�B<br>
 * �O������F
 * <p>
 *
 * @see jp.terasoluna.fw.web.rich.context.RequestContext
 */
public class RequestContextTest extends TestCase {

    private RequestContext context = null;

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     *
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(RequestContextTest.class);
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
        context = new RequestContext();
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
    public RequestContextTest(String name) {
        super(name);
    }

    /**
     * testGetRequestName01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(���) this.requestName:"sum"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) String:this.requestName<br>
     *
     * <br>
     * requestName������getter���\�b�h�̃e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetRequestName01() throws Exception {
        // �O����
        UTUtil.setPrivateField(context, "requestName", "sum");

        // �e�X�g���{
        Object result = context.getRequestName();

        // ����
        assertEquals("sum", result);
    }

    /**
     * testSetRequestName01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) requestName:"sum"<br>
     *         (���) this.requestName:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) this.requestName:�����Őݒ肵���l<br>
     *
     * <br>
     * requestName������setter���\�b�h�̃e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetRequestName01() throws Exception {
        // �O����
        UTUtil.setPrivateField(context, "requestName", null);

        // �e�X�g���{
        context.setRequestName("sum");

        // ����
        assertEquals("sum", UTUtil.getPrivateField(context, "requestName"));
    }

    /**
     * testGetProperty01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) key:"sampleKey"<br>
     *         (���) this.propertyMap:HashMap<br>
     *                (sampleKey=sampleValue)<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) String:"sampleValue"<br>
     *
     * <br>
     * properties������getter���\�b�h�̃e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetProperty01() throws Exception {
        // �O����
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sampleKey", "sampleValue");
        UTUtil.setPrivateField(context, "propertyMap", map);

        // �e�X�g���{
        // ����
        assertEquals("sampleValue", context.getProperty("sampleKey"));
    }

    /**
     * testSetProperty01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) key:"sampleKey"<br>
     *         (����) value:new Object()<br>
     *         (���) this.propertyMap:HashMap<br>
     *                ()<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) this.propertyMap:HashMap<br>
     *                    (sampleKey=sampleValue)<br>
     *
     * <br>
     * properties������setter���\�b�h�̃e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetProperty01() throws Exception {
        // �O����
    	Map<String, Object> map = new HashMap<String, Object>();
        UTUtil.setPrivateField(context, "propertyMap", map);
        Object object = new Object();

        // �e�X�g���{
        context.setProperty("sampleKey", object);

        // ����
        Map result =
            (Map) UTUtil.getPrivateField(context, "propertyMap");
        assertSame(object, result.get("sampleKey"));
    }

    /**
     * testGetPropertyString01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) key:"sampleKey"<br>
     *         (���) getProperty(String):"sampleValue"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) String:"sampleValue"<br>
     * �@�@�@�@(��ԕω�) key:"sampleKey"���n���ꂽ���Ƃ��m�F����B<br>
     *
     * <br>
     * getProperty(String)�Ăяo���ƒl�̎󂯎��̃e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetPropertyString01() throws Exception {
        // �O����
    	RequestContextStub01 target = new RequestContextStub01();
    	
        // �X�^�u�ԋp�l�̐ݒ�
    	target.value = "sampleValue";
    	
        // �e�X�g���{
        String resulet = target.getPropertyString("sampleKey");

        // ����
        assertEquals("sampleKey",target.key);
        assertEquals("sampleValue",resulet);
    }
    
    /**
     * testGetPropertyString02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) key:"sampleKey"<br>
     *         (���) getProperty(String):new Object()<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) String:null<br>
     * �@�@�@�@(��ԕω�) key:"sampleKey"���n���ꂽ���Ƃ��m�F����B<br>
     * <br>
     * �v���p�e�B�I�u�W�F�N�g��String�^�łȂ��ꍇ�̃e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetPropertyString02() throws Exception {
        // �O����
    	RequestContextStub01 target = new RequestContextStub01();
    	
        // �X�^�u�ԋp�l�̐ݒ�
    	target.value = new Object();
    	
        // �e�X�g���{
        String resulet = target.getPropertyString("sampleKey");

        // ����
        assertEquals("sampleKey",target.key);
        assertNull(resulet);
    }
    
    /**
     * testSetPropertyString01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) key:"sampleKey"<br>
     *         (����) value:"sampleValue"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) key:"sampleKey"���n���ꂽ���Ƃ��m�F����B<br>
     *                    value:"sampleValue"���n���ꂽ���Ƃ��m�F����B<br>
     * <br>
     * setProperty(String,String)�Ăяo���̃e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetPropertyString01() throws Exception {
        // �O����
    	RequestContextStub01 target = new RequestContextStub01();

        // �e�X�g���{
        target.setPropertyString("sampleKey", "sampleValue");

        // ����
        assertEquals("sampleKey",target.key);
        assertEquals("sampleValue",target.value);
    }
    
    /**
     * testToString01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(���) this.requestName:"command"<br>
     *         (���) this.properties:Properties<br>
     *                (sampleKey1=sampleValue1<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) String:"requestName:command�i���s�j<br>
     *                  properties:{sampleKey1=sampleValue1}"<br>
     *
     * <br>
     * toString�̃e�X�g�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToString01() throws Exception {
        // �O����
        UTUtil.setPrivateField(context, "requestName", "command");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sampleKey1", "sampleValue1");
        UTUtil.setPrivateField(context, "propertyMap", map);

        // �e�X�g���{
        String result = context.toString();

        // ����
        String expect = "requestName:command" +
                ",properties:{sampleKey1=sampleValue1}";
        assertEquals(expect, result);
    }

}
