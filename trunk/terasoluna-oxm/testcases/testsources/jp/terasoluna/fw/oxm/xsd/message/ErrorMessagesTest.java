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

import java.util.ArrayList;
import java.util.List;

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.oxm.xsd.message.ErrorMessage;
import jp.terasoluna.fw.oxm.xsd.message.ErrorMessages;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.oxm.xsd.message.ErrorMessages} �N���X��
 * �u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �G���[���b�Z�[�W�̃��X�g��ێ�����N���X�B<br>
 * �O������Fthis.errorMessages������Null�l�ɂȂ�Ȃ�
 * <p>
 * 
 * @see jp.terasoluna.fw.oxm.xsd.message.ErrorMessages
 */
public class ErrorMessagesTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ErrorMessagesTest.class);
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
    public ErrorMessagesTest(String name) {
        super(name);
    }

    /**
     * testAdd01()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA,G
     * <br><br>
     * ���͒l�F(����) errorMessage:null<br>
     *         (���) this.errorMessage:not null<br>
     *                (��̃��X�g)<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException(
     *          "ErrorMessages instance cannot add null object.")<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ErrorMessages instance cannot add null object.<br>
     *         (��ԕω�) this.errorMessages:not null<br>
     *                    (null�̗v�f)<br>
     *         
     * <br>
     * errorMessages�ɗv�f����̏�ԂŁANull�̃I�u�W�F�N�g��ǉ�����e�X�g�B
     * ��O�𓊂���B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAdd01() throws Exception {
        // �O����
        ErrorMessages errorMessages = new ErrorMessages();
        UTUtil.setPrivateField(
                errorMessages, "errorMessages", new ArrayList<ErrorMessage>());

        // �e�X�g���{
        try {
            errorMessages.add(null);
            fail();
        } catch (IllegalArgumentException e) {
            // OK
            String expect = "ErrorMessages instance cannot add null object.";
            assertEquals(expect, e.getMessage());
            assertTrue(LogUTUtil.checkError(expect));
        }
    }

    /**
     * testAdd02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) errorMessage:not null<br>
     *                (key="key"<br>
     *                field="field"<br>
     *                replaceValues=<br>
     *                {"rv1","rv2","rv3"})<br>
     *         (���) this.errorMessage:not null<br>
     *                (��̃��X�g)<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.errorMessages:not null<br>
     *                    (key="key"<br>
     *                    field="field"<br>
     *                    replaceValues=<br>
     *                    {"rv1","rv2","rv3"},<br>
     *                    null�̗v�f)<br>
     *         
     * <br>
     * errorMessages�ɗv�f����̏�ԂŁANotNull�̃I�u�W�F�N�g��ǉ�����e�X�g�B
     * NotNull�̃I�u�W�F�N�g��errorMessages�ɒǉ������B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAdd02() throws Exception {
        // �O����
        ErrorMessages errorMessages = new ErrorMessages();
        UTUtil.setPrivateField(
                errorMessages, "errorMessages", new ArrayList<ErrorMessage>());

        ErrorMessage errorMessage = new ErrorMessage(
                "key", "field", new String[]{"rv1", "rv2", "rv3"});
        
        // �e�X�g���{
        errorMessages.add(errorMessage);

        // ����
        List results = (List) UTUtil.getPrivateField(
                errorMessages, "errorMessages");
        ErrorMessage result = (ErrorMessage) results.get(0);
        assertEquals(1, results.size());
        assertEquals("key", result.getKey());
        assertEquals("field", result.getField());
        assertEquals(3, result.getReplaceValues().length);
        assertEquals("rv1", result.getReplaceValues()[0]);
        assertEquals("rv2", result.getReplaceValues()[1]);
        assertEquals("rv3", result.getReplaceValues()[2]);
    }

    /**
     * testAdd03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) errorMessage:not null<br>
     *                (key="key2"<br>
     *                field="field2"<br>
     *                replaceValues=<br>
     *                {"2rv1","2rv2","2rv3"})<br>
     *         (���) this.errorMessage:not null<br>
     *                ({key="key1", field="field1", 
     *                replaceValues={"1rv1","1rv2","1rv3"}})<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.errorMessages:not null<br>
     *                    ({key="key1", field="field1", 
     *                    replaceValues={"1rv1","1rv2","1rv3"}},<br>
     *                    {key="key2", field="field2", 
     *                    replaceValues={"2rv1","2rv2","2rv3"}}<br>
     *         
     * <br>
     * errorMessages�ɗv�f���P�����ԂŁANotNull�̃I�u�W�F�N�g��
     * �ǉ�����e�X�g�B
     * NotNull�̃I�u�W�F�N�g��errorMessages�̍Ō�̗v�f�ɒǉ������B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAdd03() throws Exception {
        // �O���� --------------------------------------------------------------
        ErrorMessages errorMessages = new ErrorMessages();
        
        // ����
        List<ErrorMessage> errorList = new ArrayList<ErrorMessage>();
        errorList.add(new ErrorMessage(
                "key1", "field1", new String[]{"1rv1", "1rv2", "1rv3"}));
        UTUtil.setPrivateField(
                errorMessages, "errorMessages", errorList);

        // ���\�b�h�̈���
        ErrorMessage errorMessage = new ErrorMessage(
                "key2", "field2", new String[]{"2rv1", "2rv2", "2rv3"});
        
        // �e�X�g���{ ----------------------------------------------------------
        errorMessages.add(errorMessage);

        // ���� ----------------------------------------------------------------
        List results = (List) UTUtil.getPrivateField(
                errorMessages, "errorMessages");
        assertEquals(2, results.size());
        ErrorMessage result1 = (ErrorMessage) results.get(0);
        assertEquals("key1", result1.getKey());
        assertEquals("field1", result1.getField());
        assertEquals(3, result1.getReplaceValues().length);
        assertEquals("1rv1", result1.getReplaceValues()[0]);
        assertEquals("1rv2", result1.getReplaceValues()[1]);
        assertEquals("1rv3", result1.getReplaceValues()[2]);
        
        ErrorMessage result2 = (ErrorMessage) results.get(1);
        assertEquals("key2", result2.getKey());
        assertEquals("field2", result2.getField());
        assertEquals(3, result2.getReplaceValues().length);
        assertEquals("2rv1", result2.getReplaceValues()[0]);
        assertEquals("2rv2", result2.getReplaceValues()[1]);
        assertEquals("2rv3", result2.getReplaceValues()[2]);
    }

    /**
     * testAdd04()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) errorMessage:not null<br>
     *                (key="key4"<br>
     *                field="field4"<br>
     *                replaceValues=<br>
     *                {"4rv1","4rv2","4rv3"})<br>
     *         (���) this.errorMessage:not null<br>
     *                ({key="key1", field="field1", 
     *                replaceValues={"1rv1","1rv2","1rv3"}},
     *                {key="key2", field="field2", 
     *                replaceValues={"2rv1","2rv2","2rv3"}},<br>
     *                {key="key3", field="field3", 
     *                replaceValues={"3rv1","3rv2","3rv3"}}<br>
     *                )<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.errorMessages:not null<br>
     *                    ({key="key1", field="field1", 
     *                    replaceValues={"1rv1","1rv2","1rv3"},},<br>
     *                    {key="key2", field="field2", 
     *                    replaceValues={"2rv1","2rv2","2rv3"},},<br>
     *                    {key="key3", field="field3", 
     *                    replaceValues={"3rv1","3rv2","3rv3"}},<br>
     *                    {key="key4", field="field4", 
     *                    replaceValues={"4rv1","4rv2","4rv3"}}<br>
     *         
     * <br>
     * errorMessages�ɗv�f���R�����ԂŁA
     * NotNull�̃I�u�W�F�N�g��ǉ�����e�X�g�B
     * NotNull�̃I�u�W�F�N�g��errorMessages�̍Ō�̗v�f�ɒǉ������B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAdd04() throws Exception {
        // �O���� --------------------------------------------------------------
        ErrorMessages errorMessages = new ErrorMessages();
        
        // ����
        List<ErrorMessage> errorList = new ArrayList<ErrorMessage>();
        errorList.add(new ErrorMessage(
                "key1", "field1", new String[]{"1rv1", "1rv2", "1rv3"}));
        errorList.add(new ErrorMessage(
                "key2", "field2", new String[]{"2rv1", "2rv2", "2rv3"}));
        errorList.add(new ErrorMessage(
                "key3", "field3", new String[]{"3rv1", "3rv2", "3rv3"}));
        UTUtil.setPrivateField(
                errorMessages, "errorMessages", errorList);

        // ���\�b�h�̈���
        ErrorMessage errorMessage = new ErrorMessage(
                "key4", "field4", new String[]{"4rv1", "4rv2", "4rv3"});
        
        // �e�X�g���{ ----------------------------------------------------------
        errorMessages.add(errorMessage);

        // ���� ----------------------------------------------------------------
        List results = (List) UTUtil.getPrivateField(
                errorMessages, "errorMessages");
        assertEquals(4, results.size());
        ErrorMessage result1 = (ErrorMessage) results.get(0);
        assertEquals("key1", result1.getKey());
        assertEquals("field1", result1.getField());
        assertEquals(3, result1.getReplaceValues().length);
        assertEquals("1rv1", result1.getReplaceValues()[0]);
        assertEquals("1rv2", result1.getReplaceValues()[1]);
        assertEquals("1rv3", result1.getReplaceValues()[2]);
        
        ErrorMessage result2 = (ErrorMessage) results.get(1);
        assertEquals("key2", result2.getKey());
        assertEquals("field2", result2.getField());
        assertEquals(3, result2.getReplaceValues().length);
        assertEquals("2rv1", result2.getReplaceValues()[0]);
        assertEquals("2rv2", result2.getReplaceValues()[1]);
        assertEquals("2rv3", result2.getReplaceValues()[2]);
        
        ErrorMessage result3 = (ErrorMessage) results.get(2);
        assertEquals("key3", result3.getKey());
        assertEquals("field3", result3.getField());
        assertEquals(3, result3.getReplaceValues().length);
        assertEquals("3rv1", result3.getReplaceValues()[0]);
        assertEquals("3rv2", result3.getReplaceValues()[1]);
        assertEquals("3rv3", result3.getReplaceValues()[2]);
        
        ErrorMessage result4 = (ErrorMessage) results.get(3);
        assertEquals("key4", result4.getKey());
        assertEquals("field4", result4.getField());
        assertEquals(3, result4.getReplaceValues().length);
        assertEquals("4rv1", result4.getReplaceValues()[0]);
        assertEquals("4rv2", result4.getReplaceValues()[1]);
        assertEquals("4rv3", result4.getReplaceValues()[2]);
    }

    /**
     * testGetErrorMessages01()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) this.errorMessages:not null<br>
     *                ({key="key", field="field", 
     *                replaceValues={"rv1","rv2","rv3"}},<br>
     *                null�̗v�f)<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) List:not null<br>
     *                  ({key="key", field="field", 
     *                  replaceValues={"rv1","rv2","rv3"}},<br>
     *                  null�̗v�f)<br>
     *         
     * <br>
     * errorMessage������get���\�b�h�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetErrorMessages01() throws Exception {
        // �O����
        ErrorMessages errorMessages = new ErrorMessages();
        List<ErrorMessage> errorList = new ArrayList<ErrorMessage>();
        errorList.add(new ErrorMessage(
                "key", "field", new String[]{"rv1", "rv2", "rv3"}));
        UTUtil.setPrivateField(
                errorMessages, "errorMessages", errorList);
        
        // �e�X�g���{
        List<ErrorMessage> results = errorMessages.getErrorMessages();

        // ����
        assertEquals(1, results.size());
        ErrorMessage result = results.get(0);
        assertEquals("key", result.getKey());
        assertEquals("field", result.getField());
        assertEquals(3, result.getReplaceValues().length);
        assertEquals("rv1", result.getReplaceValues()[0]);
        assertEquals("rv2", result.getReplaceValues()[1]);
        assertEquals("rv3", result.getReplaceValues()[2]);
    }

    /**
     * testHasErrorMessage01()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) this.errorMessages:not null<br>
     *                (��̃��X�g)<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         
     * <br>
     * ���errorMessages�����̓G���[��ԂɂȂ邩�m�F����e�X�g�B<br>
     * FALSE���Ԃ����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testHasErrorMessage01() throws Exception {
        // �O����
        ErrorMessages errorMessages = new ErrorMessages();
        UTUtil.setPrivateField(
                errorMessages, "errorMessages", new ArrayList<ErrorMessage>());

        // �e�X�g���{
        boolean hasError = errorMessages.hasErrorMessage();

        // ����
        assertFalse(hasError);
    }

    /**
     * testHasErrorMessage02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) this.errorMessages:not null<br>
     *                ({key="key", field="field", 
     *                replaceValues={"rv1","rv2","rv3"}})<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         
     * <br>
     * �v�f��1����errorMessages�����̓G���[��ԂɂȂ邩�m�F����e�X�g�B
     * TRUE���Ԃ����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testHasErrorMessage02() throws Exception {
        // �O����
        ErrorMessages errorMessages = new ErrorMessages();
        List<ErrorMessage> errorList = new ArrayList<ErrorMessage>();
        errorList.add(new ErrorMessage(
                "key", "field", new String[]{"rv1", "rv2", "rv3"}));
        UTUtil.setPrivateField(
                errorMessages, "errorMessages", errorList);

        // �e�X�g���{
        boolean hasError = errorMessages.hasErrorMessage();

        // ����
        assertTrue(hasError);
    }

    /**
     * testHasErrorMessage03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) this.errorMessages:not null<br>
     *                ({key="key1", field="field1", 
     *                replaceValues={"1rv1","1rv2","1rv3"}},<br>
     *                {key="key2", field="field2", 
     *                replaceValues={"2rv1","2rv2","2rv3"}},<br>
     *                {key="key3", field="field3", 
     *                replaceValues={"3rv1","3rv2","3rv3"}})<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         
     * <br>
     * �v�f���R����errorMessages�����̓G���[��ԂɂȂ邩�m�F����e�X�g�B
     * TRUE���Ԃ����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testHasErrorMessage03() throws Exception {
        // �O����
        ErrorMessages errorMessages = new ErrorMessages();
        List<ErrorMessage> errorList = new ArrayList<ErrorMessage>();
        errorList.add(new ErrorMessage(
                "key1", "field1", new String[]{"1rv1", "1rv2", "1rv3"}));
        errorList.add(new ErrorMessage(
                "key2", "field2", new String[]{"2rv1", "2rv2", "2rv3"}));
        errorList.add(new ErrorMessage(
                "key3", "field3", new String[]{"3rv1", "3rv2", "3rv3"}));
        UTUtil.setPrivateField(
                errorMessages, "errorMessages", errorList);

        // �e�X�g���{
        boolean hasError = errorMessages.hasErrorMessage();

        // ����
        assertTrue(hasError);
    }
}
