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

package jp.terasoluna.fw.oxm.xsd.message;

import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.oxm.xsd.message.ErrorMessage;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.oxm.xsd.message.ErrorMessage} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �G���[���b�Z�[�W��ێ�����N���X�B
 * <p>
 * 
 * @see jp.terasoluna.fw.oxm.xsd.message.ErrorMessage
 */
public class ErrorMessageTest extends TestCase {

    /**
     * �e�X�g�p��ErrorMessage
     */
    ErrorMessage testErrorMessage = null;
    
    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ErrorMessageTest.class);
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
        
        // ������
        testErrorMessage = new ErrorMessage(null, null, (String[]) null);        
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

        testErrorMessage = null;        
        
    }

    /**
     * �R���X�g���N�^�B
     * 
     * @param name ���̃e�X�g�P�[�X�̖��O�B
     */
    public ErrorMessageTest(String name) {
        super(name);
    }

    /**
     * testErrorMessageStringStringStringArray01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,D
     * <br><br>
     * ���͒l�F(����) key:"abc"<br>
     *         (����) field:"123"<br>
     *         (����) values:�v�f��0<br>
     *                String[]{}<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) key:"abc"<br>
     *         (��ԕω�) replaceValues:�v�f��0<br>
     *                    String[]{}<br>
     *         (��ԕω�) field:"123"<br>
     *         
     * <br>
     * key�Afield�Avalues�������������ɐݒ肳��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testErrorMessageStringStringStringArray01() throws Exception {
        
        // �O�����̐ݒ�B
        String key = "abc";
        String field = "123";
        String[] values = new String[]{};
        
        // �e�X�g���{�B
        ErrorMessage message = new ErrorMessage(key, field, values);

        // ����B
        assertEquals("abc", UTUtil.getPrivateField(message, "key"));
        assertEquals("123", UTUtil.getPrivateField(message, "field"));
        
        String[] replaceValues = (String[]) UTUtil.getPrivateField(message, "replaceValues");
        assertEquals(0, replaceValues.length);

    }

    /**
     * testErrorMessageStringStringStringArray02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,C,D
     * <br><br>
     * ���͒l�F(����) key:""<br>
     *         (����) field:not null<br>
     *         (����) values:�v�f��1<br>
     *                String[]{"a"}<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) key:""<br>
     *         (��ԕω�) replaceValues:�v�f��1<br>
     *                    String[]{"a"}<br>
     *         (��ԕω�) field:not null<br>
     *         
     * <br>
     * key���󕶎��̏ꍇ�A�����ɋ󕶎����ݒ肳��邱�Ƃ��m�F����<br>
     * values�������������ɐݒ肳��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testErrorMessageStringStringStringArray02() throws Exception {
        
        // �O�����̐ݒ�B
        String key = "";
        String field = "123";
        String[] values = new String[]{"a"};
        
        // �e�X�g���{�B
        ErrorMessage message = new ErrorMessage(key, field, values);

        // ����B
        assertEquals("", UTUtil.getPrivateField(message, "key"));
        assertEquals("123", UTUtil.getPrivateField(message, "field"));
        
        String[] replaceValues = (String[]) UTUtil.getPrivateField(message, "replaceValues");
        assertEquals("a", replaceValues[0]);
        assertEquals(1, replaceValues.length);

    }

    /**
     * testErrorMessageStringStringStringArray03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,C,D
     * <br><br>
     * ���͒l�F(����) key:null<br>
     *         (����) field:not null<br>
     *         (����) values:�v�f��3<br>
     *                {"a","b","c"}<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) key:null<br>
     *         (��ԕω�) replaceValues:�v�f��3<br>
     *                    {"a","b","c"}<br>
     *         (��ԕω�) field:not null<br>
     *         
     * <br>
     * key��null�̏ꍇ�A������null���ݒ肳��邱�Ƃ��m�F����B<br>
     * values�������������ɐݒ肳��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testErrorMessageStringStringStringArray03() throws Exception {
        
        // �O�����̐ݒ�B
        String key = null;
        String field = "123";
        String[] values = new String[]{"a","b","c"};
        
        // �e�X�g���{�B
        ErrorMessage message = new ErrorMessage(key, field, values);

        // ����B
        assertNull(UTUtil.getPrivateField(message, "key"));
        assertEquals("123", UTUtil.getPrivateField(message, "field"));
        
        String[] replaceValues = (String[]) UTUtil.getPrivateField(message, "replaceValues");
        assertEquals("a", replaceValues[0]);
        assertEquals("b", replaceValues[1]);
        assertEquals("c", replaceValues[2]);
        assertEquals(3, replaceValues.length);

    }

    /**
     * testErrorMessageStringStringStringArray04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,C
     * <br><br>
     * ���͒l�F(����) key:not null<br>
     *         (����) field:""<br>
     *         (����) values:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) key:not null<br>
     *         (��ԕω�) replaceValues:null<br>
     *         (��ԕω�) field:""<br>
     *         
     * <br>
     * field���󕶎��̏ꍇ�A�����ɋ󕶎����ݒ肳��邱�Ƃ��m�F����<br>
     * values�������������ɐݒ肳��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testErrorMessageStringStringStringArray04() throws Exception {
        
        // �O�����̐ݒ�B
        String key = "abc";
        String field = "";
        String[] values = null;
        
        // �e�X�g���{�B
        ErrorMessage message = new ErrorMessage(key, field, values);

        // ����B
        assertEquals("abc", UTUtil.getPrivateField(message, "key"));
        assertEquals("", UTUtil.getPrivateField(message, "field"));
        
        String[] replaceValues = (String[]) UTUtil.getPrivateField(message, "replaceValues");
        assertNull(replaceValues);

    }

    /**
     * testErrorMessageStringStringStringArray05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,C,D
     * <br><br>
     * ���͒l�F(����) key:not null<br>
     *         (����) field:null<br>
     *         (����) values:�v�f�Ȃ��i�ϒ������̌���0�j<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) key:not null<br>
     *         (��ԕω�) replaceValues:�v�f��0<br>
     *                    String[]{}<br>
     *         (��ԕω�) field:null<br>
     *         
     * <br>
     * field��null�̏ꍇ�A������null���ݒ肳��邱�Ƃ��m�F����B
     * values�̈������Ȃ��ꍇ�AreplaceValue�ɋ�̃��X�g���i�[����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testErrorMessageStringStringStringArray05() throws Exception {
        
        // �O�����̐ݒ�B
        String key = "abc";
        String field = null;
        
        // �e�X�g���{�B
        ErrorMessage message = new ErrorMessage(key, field);

        // ����B
        assertEquals("abc", UTUtil.getPrivateField(message, "key"));
        assertNull(UTUtil.getPrivateField(message, "field"));
        
        String[] replaceValues = (String[]) UTUtil.getPrivateField(message, "replaceValues");
        assertEquals(0, replaceValues.length);

    }

    /**
     * testGetKey01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) key:"abc"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) this.key:"abc"<br>
     *         
     * <br>
     * �����ɐݒ肳��Ă��镶���񂪐������ԋp����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetKey01() throws Exception {
        
        // �O����
        String key = "abc";
        UTUtil.setPrivateField(testErrorMessage, "key", key);
        
        // �e�X�g���{
        String result = testErrorMessage.getKey();
        
        // ����
        assertEquals("abc", result);
    }

    /**
     * testSetKey01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) key:"key"<br>
     *         (����) this.key:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.key:"key"<br>
     *         
     * <br>
     * �����̒l�������ɐ������ݒ肳��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetKey01() throws Exception {
        
        // �O����
        
        // �e�X�g���{
        testErrorMessage.setKey("abc");
        
        // ����
        assertEquals("abc", UTUtil.getPrivateField(testErrorMessage, "key"));
        
    }

    /**
     * testGetReplaceValues01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) this.replaceValues:�v�f��3<br>
     *                String[]{"a","b","c"}<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) this.replaceValues:�v�f��3<br>
     *                  String[]{"a","b","c"}<br>
     *         
     * <br>
     * �����ɐݒ肳�ꂽ�z�񂪐������ԋp����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetReplaceValues01() throws Exception {
        
        // �O����
        String[] replaceValues = new String[]{"a","b","c"};
        UTUtil.setPrivateField(testErrorMessage, "replaceValues", replaceValues);
        
        // �e�X�g���{
        String[] result = testErrorMessage.getReplaceValues();
        
        // ����
        assertEquals(3, result.length);
        assertEquals("a", result[0]);
        assertEquals("b", result[1]);
        assertEquals("c", result[2]);
    }

    /**
     * testSetReplaceValues01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) replaceValues:�v�f��3<br>
     *                String[]{"a","b","c"}<br>
     *         (���) replaceValues:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.replaceValues:�v�f��3<br>
     *                    String[]{"a","b","c"}<br>
     *         
     * <br>
     * �����̒l�������ɐ������ݒ肳��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetReplaceValues01() throws Exception {
        
        // �O����
        
        // �e�X�g���{
        testErrorMessage.setReplaceValues(new String[]{"a","b","c"});
        
        // ����
        String[] replaceValues = (String[]) UTUtil.getPrivateField(testErrorMessage, "replaceValues");
        assertEquals("a", replaceValues[0]);
        assertEquals("b", replaceValues[1]);
        assertEquals("c", replaceValues[2]);
        assertEquals(3, replaceValues.length);
        
    }

    /**
     * testGetField01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) this.field:"123"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) field:"123"<br>
     *         
     * <br>
     * �����ɐݒ肳�ꂽ�z�񂪐������ԋp����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetField01() throws Exception {
        
        // �O����
        String inputField = "123";
        UTUtil.setPrivateField(testErrorMessage, "field", inputField);
        
        // �e�X�g���{
        String result = testErrorMessage.getField();
        
        // ����
        assertEquals("123", result);
    }

    /**
     * testSetField01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) field:"123"<br>
     *         (���) this.field:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.field:"123"<br>
     *         
     * <br>
     * �����̒l�������ɐ������ݒ肳��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetField01() throws Exception {
        
        // �O����
        
        // �e�X�g���{
        testErrorMessage.setField("123");
        
        // ����
        assertEquals("123", UTUtil.getPrivateField(testErrorMessage, "field"));
        
    }

}
