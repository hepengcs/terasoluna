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

package jp.terasoluna.fw.validation.springmodules;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.collections.FastHashMap;
import org.apache.commons.validator.Arg;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.Msg;
import org.apache.commons.validator.ValidatorAction;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.validation.Errors;

import jp.terasoluna.fw.validation.springmodules.SpringValidationErrors;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.validation.springmodules.SpringValidationErrors}
 * �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * Spring�t���[�����[�N��org.springframework.validation.Errors�C���^�t�F�[�X��
 * �G���[����ǉ����邽�߂̃N���X�B<br>
 * ��addError�̈���Field��ValidatorAction��null�͓���Ȃ��B
 * <p>
 * 
 * @see jp.terasoluna.fw.validation.springmodules.SpringValidationErrors
 */
public class SpringValidationErrorsTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(SpringValidationErrorsTest.class);
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
    public SpringValidationErrorsTest(String name) {
        super(name);
    }

    /**
     * testSetErrors01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) errors:Errors�C���X�^���X<br>
     *         (���) errors:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) errors:�����Ɠ����Errors�C���X�^���X<br>
     *         
     * <br>
     * �����̒l�������ɐ���ɐݒ肳��邱��
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetErrors01() throws Exception {
        // �O����
        SpringValidationErrors validation = new SpringValidationErrors();
        UTUtil.setPrivateField(validation, "errors", null);
        
        Errors errors = new ErrorsImpl01();

        // �e�X�g���{
        validation.setErrors(errors);

        // ����
        assertSame(errors, UTUtil.getPrivateField(validation, "errors"));
    }

    /**
     * testGetErrors01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) �Ȃ�:�|<br>
     *         (���) errors:Errors�C���X�^���X<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Errors:Errors�C���X�^���X<br>
     *         
     * <br>
     * �����ɐݒ肳��Ă���l�𐳏�Ɏ擾���邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetErrors01() throws Exception {
        // �O����
        SpringValidationErrors validation = new SpringValidationErrors();
        Errors errors = new ErrorsImpl01();
        UTUtil.setPrivateField(validation, "errors", errors);

        // �e�X�g���{
        Errors result = validation.getErrors();

        // ����
        assertSame(errors, result);
    }

    /**
     * testAddErrors01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) bean:Object<br>
     *         (����) field:Field�C���X�^���X<br>
     *                field.getKey()="key"<br>
     *                field.getMsg("name")="messageKey"<br>
     *                field.getArg("name", 0)="arg0"<br>
     *                field.getArg("name", 1)="arg1"<br>
     *                field.getArg("name", 2)="arg2"<br>
     *                field.getArg("name", 3)="arg3"<br>
     *         (����) va:ValidationAction�C���X�^���X<br>
     *                va.getName()="name"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) rejectValue():�Ăяo���m�F�ƈ����̊m�F�F<br>
     *                    fieldCode="key"<br>
     *                    errorCode="messageKey"<br>
     *                    args={<br>
     *                    MessageSourceResolvable�C���X�^���X{<br>
     *                    codes[0]={"arg0"}, arguments=null, defaultMessage="arg0"}, <br>
     *                    MessageSourceResolvable�C���X�^���X{<br>
     *                    codes[1]={"arg1"}, arguments=null, defaultMessage="arg1"}, <br>
     *                    MessageSourceResolvable�C���X�^���X{<br>
     *                    codes[2]={"arg2"}, arguments=null, defaultMessage="arg2"}, <br>
     *                    MessageSourceResolvable�C���X�^���X{<br>
     *                    codes[3]={"arg3"}, arguments=null, defaultMessage="arg3"}, <br>
     *                    }<br>
     *         
     * <br>
     * ������not null�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddErrors01() throws Exception {
        // �O����
        // ����bean
        Object bean = new Object();
        // ����field
        Field field = new Field();
        // field.getKey() : "key"
        field.setKey("key");
        
        // field.getMsg("name")�̒l��ݒ�ierrorCode�擾�̂��߁j
        FastHashMap hMsgs = new FastHashMap();
        Msg msg = new Msg();
        msg.setKey("messageKey");
        hMsgs.put("name", msg);
        UTUtil.setPrivateField(field, "hMsgs", hMsgs);
        
        // �iObject[] args�擾�̂��߁j
        Map[] args = new HashMap[4];
        
        // args[0]
        Arg arg = new Arg();
        arg.setKey("arg0");
        Map<String, Arg> hMap01 = new HashMap<String, Arg>();
        hMap01.put("name", arg);
        args[0] = hMap01;
        
        // args[1]
        arg = new Arg();
        arg.setKey("arg1");
        Map<String, Arg> hMap02 = new HashMap<String, Arg>();
        hMap02.put("name", arg);
        args[1] = hMap02;
        
        // args[2]
        arg = new Arg();
        arg.setKey("arg2");
        Map<String, Arg> hMap03 = new HashMap<String, Arg>();
        hMap03.put("name", arg);
        args[2] = hMap03;
        
        // args[3]
        arg = new Arg();
        arg.setKey("arg3");
        Map<String, Arg> hMap04 = new HashMap<String, Arg>();
        hMap04.put("name", arg);
        args[3] = hMap04;
        
        UTUtil.setPrivateField(field, "args", args);
        
        // ����va
        ValidatorAction va = new ValidatorAction();
        
        // va.getName : "name"
        va.setName("name");
        
        // SpringValidationErrors�C���X�^���X����
        SpringValidationErrors validation = new SpringValidationErrors();
        
        // Errors�̐ݒ� : ErrorsImpl01 - ���\�b�hrejectValue�ƈ����̌ďo�m�F
        ErrorsImpl01 errors = new ErrorsImpl01();
        UTUtil.setPrivateField(validation, "errors", errors);


        // �e�X�g���{
        validation.addError(bean, field, va);

        
        // ����
        ErrorsImpl01 assertErrors =
            (ErrorsImpl01) UTUtil.getPrivateField(validation, "errors");
        // rejectValue�ďo�m�F
        assertTrue(assertErrors.isRejectValue);
        
        // ����field�m�F
        assertEquals("key", assertErrors.field);
        
        // ����errorCode�m�F
        assertEquals("messageKey", assertErrors.errorCode);
        
        // assertSame(args, assertErrors.errorArgs);
        // ����errorArgs�m�F
        Object[] objs = assertErrors.errorArgs;
        MessageSourceResolvable msr = null;
        for(int i=0; i<objs.length; i++) {
            msr = (MessageSourceResolvable) objs[i];
            
            String[] strs = msr.getCodes();
            // codes[0] : "arg" + i
            assertEquals("arg" + i, strs[0]);
            // arguments : null
            assertNull(msr.getArguments());
            // defaultMessage : "arg" + i
            assertEquals("arg" + i, msr.getDefaultMessage());
        }
        
        // ����defaultMessage�m�F
        assertEquals("messageKey", assertErrors.defaultMessage);
    }
}