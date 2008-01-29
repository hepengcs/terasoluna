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

package jp.terasoluna.fw.web.rich.springmvc.servlet.handler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletResponse;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.web.rich.springmvc.Constants;
import jp.terasoluna.fw.web.rich.springmvc.servlet.handler.ExceptionResolveDelegator;
import jp.terasoluna.fw.web.rich.springmvc.servlet.handler.ExceptionResolveDelegatorImpl;
import junit.framework.TestCase;

import org.springframework.web.servlet.ModelAndView;

/**
 * {@link jp.terasoluna.fw.web.rich.springmvc.servlet.handler.ExceptionResolveDelegatorImpl} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * ExceptionResolveDelegator�̃f�t�H���g�����N���X�B<br>
 * �O������F<br>
 * initMapping�N���X�̈���mappingKey��null�ɂȂ邱�Ƃ͂Ȃ��B<br>
 * setHeader���\�b�h�̈���response��null�ɂȂ邱�Ƃ͂Ȃ��B<br>
 * addObjectToModel���\�b�h�̈���mv��null�ɂȂ邱�Ƃ͂Ȃ��B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.servlet.handler.ExceptionResolveDelegatorImpl
 */
public class ExceptionResolveDelegatorImplTest extends TestCase {

    /**
     * initMapping���\�b�h��mappingKey�i�Œ�j
     */
    private static final String MAPPING_KEY = "mkey";
    
    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ExceptionResolveDelegatorImplTest.class);
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
    public ExceptionResolveDelegatorImplTest(String name) {
        super(name);
    }

    /**
     * testInitMapping01()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA,C,G
     * <br><br>
     * ���͒l�F(����) mappingKey:"mkey"<br>
     *         (����) mappingValues:null<br>
     *         (���) mappingKey:null<br>
     *         (���) mappingValues:null<br>
     *         (���) viewName:null<br>
     *         (���) errorType:null<br>
     *         (���) errorCode:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException{<br>
     *                      message= "linkedExceptionMappings[mkey] value is null. Check Spring Bean definition file."<br>
     *                    }<br>
     *         (��ԕω�) ���O:�G���[���x���F�G���[<br>
     *                    linkedExceptionMappings[mkey] value is null. Check Spring Bean definition file.<br>
     *         (��ԕω�) mappingKey:������mappingKey<br>
     *         (��ԕω�) mappingValues:������mappingValues<br>
     *         (��ԕω�) viewName:null<br>
     *         (��ԕω�) errorType:null<br>
     *         (��ԕω�) errorCode:null<br>
     *         
     * <br>
     * ����mappingValues��null�̏ꍇ�̃e�X�g�B<br>
     * ��O���X���[����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitMapping01() throws Exception {
        // �O����
        ExceptionResolveDelegator exceptionResolveDelegator = new ExceptionResolveDelegatorImpl();

        // ����
        String mappingValues = null;

        // �e�X�g���{
        try{
            exceptionResolveDelegator.initMapping(MAPPING_KEY, mappingValues, null);
            fail();
        } catch (IllegalStateException e) {
            // ����
            String message = "linkedExceptionMappings[" + MAPPING_KEY
                                                     + "] value is null. "
                                                     + "Check Spring Bean definition file.";
            assertEquals(message, e.getMessage());

            // ���O�m�F
            assertTrue(LogUTUtil.checkError(message));
        }        
        
        

    }

    /**
     * testInitMapping02()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA,E,G
     * <br><br>
     * ���͒l�F(����) mappingKey:"mkey"<br>
     *         (����) mappingValues:java.util.Date�^�̃C���X�^���X<br>
     *         (���) mappingKey:null<br>
     *         (���) mappingValues:null<br>
     *         (���) viewName:null<br>
     *         (���) errorType:null<br>
     *         (���) errorCode:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException{<br>
     *                      message= "linkedExceptionMappings[mkey] value is not String type. Check Spring Bean definition file."<br>
     *                    }<br>
     *         (��ԕω�) ���O:�G���[���x���F�G���[<br>
     *                    linkedExceptionMappings[mkey] value is not String type. Check Spring Bean definition file.<br>
     *         (��ԕω�) mappingKey:������mappingKey<br>
     *         (��ԕω�) mappingValues:������mappingValues<br>
     *         (��ԕω�) viewName:null<br>
     *         (��ԕω�) errorType:null<br>
     *         (��ԕω�) errorCode:null<br>
     *         
     * <br>
     * ����mappingValues��String�^�łȂ��ꍇ�̃e�X�g�B<br>
     * ��O���X���[����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitMapping02() throws Exception {
        // �O����
        ExceptionResolveDelegator exceptionResolveDelegator = new ExceptionResolveDelegatorImpl();

        // ����
        Date mappingValues = new Date();

        // �e�X�g���{
        try {
            exceptionResolveDelegator.initMapping(MAPPING_KEY, mappingValues, null);
            fail();
        } catch (IllegalStateException e) {

            // ����
            String message = "linkedExceptionMappings[mkey] value is not String type. Check Spring Bean definition file.";
            assertEquals(message, e.getMessage());

            // ���O�m�F
            assertTrue(LogUTUtil.checkError(message));
        }

    }

    /**
     * testInitMapping03()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA,B,C,G
     * <br><br>
     * ���͒l�F(����) mappingKey:"mkey"<br>
     *         (����) mappingValues:""<br>
     *         (���) mappingKey:null<br>
     *         (���) mappingValues:null<br>
     *         (���) viewName:null<br>
     *         (���) errorType:null<br>
     *         (���) errorCode:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException{<br>
     *                      message= "linkedExceptionMappings[mkey] value is insufficient. Two values are necessary. Check Spring Bean definition file."<br>
     *                    }<br>
     *         (��ԕω�) ���O:�G���[���x���F�G���[<br>
     *                    linkedExceptionMappings[mkey] value is insufficient. Two values are necessary. Check Spring Bean definition file.<br>
     *         (��ԕω�) mappingKey:������mappingKey<br>
     *         (��ԕω�) mappingValues:������mappingValues<br>
     *         (��ԕω�) viewName:null<br>
     *         (��ԕω�) errorType:null<br>
     *         (��ԕω�) errorCode:null<br>
     *         
     * <br>
     * ����mappingValues���󕶎��̏ꍇ�̃e�X�g�B<br>
     * ��O���X���[����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitMapping03() throws Exception {
        // �O����
        ExceptionResolveDelegator exceptionResolveDelegator = new ExceptionResolveDelegatorImpl();

        // ����
        String mappingValues = "";

        // �e�X�g���{
        try {
            exceptionResolveDelegator.initMapping(MAPPING_KEY, mappingValues, null);
            fail();
        } catch (IllegalStateException e) {

            // ����
            String message = "linkedExceptionMappings[mkey] value is insufficient. Two values are necessary. Check Spring Bean definition file.";
            assertEquals(message, e.getMessage());

            // ���O�m�F
            assertTrue(LogUTUtil.checkError(message));
        }

    }

    /**
     * testInitMapping04()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA,B,G
     * <br><br>
     * ���͒l�F(����) mappingKey:"mkey"<br>
     *         (����) mappingValues:"abc"<br>
     *         (���) mappingKey:null<br>
     *         (���) mappingValues:null<br>
     *         (���) viewName:null<br>
     *         (���) errorType:null<br>
     *         (���) errorCode:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException{<br>
     *                      message= "linkedExceptionMappings[mkey] value is insufficient. Two values are necessary. Check Spring Bean definition file."<br>
     *                    }<br>
     *         (��ԕω�) ���O:�G���[���x���F�G���[<br>
     *                    linkedExceptionMappings[mkey] value is insufficient. Two values are necessary. Check Spring Bean definition file.<br>
     *         (��ԕω�) mappingKey:������mappingKey<br>
     *         (��ԕω�) mappingValues:������mappingValues<br>
     *         (��ԕω�) viewName:null<br>
     *         (��ԕω�) errorType:null<br>
     *         (��ԕω�) errorCode:null<br>
     *         
     * <br>
     * ����mappingValues���J���}�ŕ��������l��1�����Ȃ��ꍇ�̃e�X�g�B<br>
     * ��O���X���[����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitMapping04() throws Exception {
        // �O����
        ExceptionResolveDelegator exceptionResolveDelegator = new ExceptionResolveDelegatorImpl();

        // ����
        String mappingValues = "abc";

        // �e�X�g���{
        try {
            exceptionResolveDelegator.initMapping(MAPPING_KEY, mappingValues, null);
            fail();
        } catch (IllegalStateException e) {

            // ����
            String message = "linkedExceptionMappings[mkey] value is insufficient. Two values are necessary. Check Spring Bean definition file.";
            assertEquals(message, e.getMessage());

            // ���O�m�F
            assertTrue(LogUTUtil.checkError(message));
        }

    }

    /**
     * testInitMapping05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,B
     * <br><br>
     * ���͒l�F(����) mappingKey:"mkey"<br>
     *         (����) mappingValues:"abc,def"<br>
     *         (���) mappingKey:null<br>
     *         (���) mappingValues:null<br>
     *         (���) viewName:null<br>
     *         (���) errorType:null<br>
     *         (���) errorCode:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) mappingKey:������mappingKey<br>
     *         (��ԕω�) mappingValues:������mappingValues<br>
     *         (��ԕω�) viewName:"abc"<br>
     *         (��ԕω�) errorType:"def"<br>
     *         (��ԕω�) errorCode:null<br>
     *         
     * <br>
     * ����mappingValues���J���}�ŕ��������l��2����ꍇ�̃e�X�g�B<br>
     * ����Ɉ����̒l�������Ƀ}�b�s���O�����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitMapping05() throws Exception {
        // �O����
        ExceptionResolveDelegator exceptionResolveDelegator = new ExceptionResolveDelegatorImpl();

        // ����
        String mappingValues = "abc,def";

        // �e�X�g���{
        exceptionResolveDelegator.initMapping(MAPPING_KEY, mappingValues, null);
        
        // ����
        assertEquals(MAPPING_KEY, UTUtil.getPrivateField(exceptionResolveDelegator, "mappingKey"));
        assertEquals(mappingValues, UTUtil.getPrivateField(exceptionResolveDelegator, "mappingValues"));
        assertEquals("abc", UTUtil.getPrivateField(exceptionResolveDelegator, "viewName"));
        assertEquals("def", UTUtil.getPrivateField(exceptionResolveDelegator, "errorType"));

    }

    /**
     * testInitMapping06()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,B
     * <br><br>
     * ���͒l�F(����) mappingKey:"mkey"<br>
     *         (����) mappingValues:"abc , def,ghi"<br>
     *         (���) mappingKey:null<br>
     *         (���) mappingValues:null<br>
     *         (���) viewName:null<br>
     *         (���) errorType:null<br>
     *         (���) errorCode:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) mappingKey:������mappingKey<br>
     *         (��ԕω�) mappingValues:������mappingValues<br>
     *         (��ԕω�) viewName:"abc"<br>
     *         (��ԕω�) errorType:"def"<br>
     *         (��ԕω�) errorCode:"ghi"<br>
     *         
     * <br>
     * ����mappingValues���J���}�ŕ��������l��3����ꍇ�̃e�X�g�B<br>
     * ����Ɉ����̒l�������Ƀ}�b�s���O�����B�G���[�R�[�h���ݒ肳���B<br>
     * �J���}�ŕ��������l�̑O��ɋ󔒂�����ꍇ�A�폜�����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitMapping06() throws Exception {
        // �O����
        ExceptionResolveDelegator exceptionResolveDelegator = new ExceptionResolveDelegatorImpl();

        // ����
        String mappingValues = "abc , def,ghi";

        // �e�X�g���{
        exceptionResolveDelegator.initMapping(MAPPING_KEY, mappingValues, null);
        
        // ����
        assertEquals(MAPPING_KEY, UTUtil.getPrivateField(exceptionResolveDelegator, "mappingKey"));
        assertEquals(mappingValues, UTUtil.getPrivateField(exceptionResolveDelegator, "mappingValues"));
        assertEquals("abc", UTUtil.getPrivateField(exceptionResolveDelegator, "viewName"));
        assertEquals("def", UTUtil.getPrivateField(exceptionResolveDelegator, "errorType"));
        assertEquals("ghi", UTUtil.getPrivateField(exceptionResolveDelegator, "errorCode"));
    }

    /**
     * testInitMapping07()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA,C,G
     * <br><br>
     * ���͒l�F(����) mappingKey:"mkey"<br>
     *         (����) mappingValues:",def,ghi"<br>
     *         (���) mappingKey:null<br>
     *         (���) mappingValues:null<br>
     *         (���) viewName:null<br>
     *         (���) errorType:null<br>
     *         (���) errorCode:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException{<br>
     *                      message= "linkedExceptionMappings[mkey] value[0] is empty. Check Spring Bean definition file."<br>
     *                    }<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    linkedExceptionMappings[mkey] value[0] is empty. Check Spring Bean definition file.<br>
     *         (��ԕω�) mappingKey:������mappingKey<br>
     *         (��ԕω�) mappingValues:������mappingValues<br>
     *         (��ԕω�) viewName:null<br>
     *         (��ԕω�) errorType:null<br>
     *         (��ԕω�) errorCode:null<br>
     *         
     * <br>
     * ����mappingValues���J���}�ŕ�������1�ڂ̒l�ɋ󕶎����ݒ肳��Ă���ꍇ�̃e�X�g�B��O���X���[����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitMapping07() throws Exception {
        // �O����
        ExceptionResolveDelegator exceptionResolveDelegator = new ExceptionResolveDelegatorImpl();

        // ����
        String mappingValues = ",def,ghi";

        // �e�X�g���{
        try {
            exceptionResolveDelegator.initMapping(MAPPING_KEY, mappingValues, null);
            fail();
        } catch (IllegalStateException e) {

            // ����
            String message = "linkedExceptionMappings[mkey] value[0] is empty. Check Spring Bean definition file.";
            assertEquals(message, e.getMessage());

            // ���O�m�F
            assertTrue(LogUTUtil.checkError(message));
        }
    }

    /**
     * testInitMapping08()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA,C,G
     * <br><br>
     * ���͒l�F(����) mappingKey:"mkey"<br>
     *         (����) mappingValues:"abc, ,ghi"<br>
     *         (���) mappingKey:null<br>
     *         (���) mappingValues:null<br>
     *         (���) viewName:null<br>
     *         (���) errorType:null<br>
     *         (���) errorCode:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException{<br>
     *                      message= "linkedExceptionMappings[mkey] value[1] is empty. Check Spring Bean definition file."<br>
     *                    }<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    linkedExceptionMappings[mkey] value[1] is empty. Check Spring Bean definition file.<br>
     *         (��ԕω�) mappingKey:������mappingKey<br>
     *         (��ԕω�) mappingValues:������mappingValues<br>
     *         (��ԕω�) viewName:"abc"<br>
     *         (��ԕω�) errorType:"def"<br>
     *         (��ԕω�) errorCode:"ghi"<br>
     *         
     * <br>
     * ����mappingValues���J���}�ŕ�������2�ڂ̒l�ɋ󕶎��i�X�y�[�X�����̒l�j���ݒ肳��Ă���ꍇ�̃e�X�g�B��O���X���[����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitMapping08() throws Exception {
        // �O����
        ExceptionResolveDelegator exceptionResolveDelegator = new ExceptionResolveDelegatorImpl();

        // ����
        String mappingValues = "abc, ,ghi";

        // �e�X�g���{
        try {
            exceptionResolveDelegator.initMapping(MAPPING_KEY, mappingValues, null);
            fail();
        } catch (IllegalStateException e) {

            // ����
            String message = "linkedExceptionMappings[mkey] value[1] is empty. Check Spring Bean definition file.";
            assertEquals(message, e.getMessage());

            // ���O�m�F
            assertTrue(LogUTUtil.checkError(message));
        }
    }

    /**
     * testInitMapping09()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA,C,G
     * <br><br>
     * ���͒l�F(����) mappingKey:"mkey"<br>
     *         (����) mappingValues:"abc,def,"<br>
     *         (���) mappingKey:null<br>
     *         (���) mappingValues:null<br>
     *         (���) viewName:null<br>
     *         (���) errorType:null<br>
     *         (���) errorCode:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException{<br>
     *                      message= "linkedExceptionMappings[mkey] value[2] is empty. Check Spring Bean definition file."<br>
     *                    }<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    linkedExceptionMappings[mkey] value[2] is empty. Check Spring Bean definition file.<br>
     *         (��ԕω�) mappingKey:������mappingKey<br>
     *         (��ԕω�) mappingValues:������mappingValues<br>
     *         (��ԕω�) viewName:"abc"<br>
     *         (��ԕω�) errorType:"def"<br>
     *         (��ԕω�) errorCode:"ghi"<br>
     *         
     * <br>
     * ����mappingValues���J���}�ŕ�������3�ڂ̒l�ɋ󕶎����ݒ肳��Ă���ꍇ�̃e�X�g�B��O���X���[����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitMapping09() throws Exception {
        // �O����
        ExceptionResolveDelegator exceptionResolveDelegator = new ExceptionResolveDelegatorImpl();

        // ����
        String mappingValues = "abc,def,";

        // �e�X�g���{
        try {
            exceptionResolveDelegator.initMapping(MAPPING_KEY, mappingValues, null);
            fail();
        } catch (IllegalStateException e) {

            // ����
            String message = "linkedExceptionMappings[mkey] value[2] is empty. Check Spring Bean definition file.";
            assertEquals(message, e.getMessage());

            // ���O�m�F
            assertTrue(LogUTUtil.checkError(message));
        }
    }

    /**
     * testInitMapping10()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,B
     * <br><br>
     * ���͒l�F(����) mappingKey:"mkey"<br>
     *         (����) mappingValues:"abc,def"<br>
     *         (����) params:null<br>
     *         (���) mappingKey:null<br>
     *         (���) mappingValues:null<br>
     *         (���) viewName:null<br>
     *         (���) errorType:null<br>
     *         (���) errorCode:null<br>
     *         (���) errorTypeHeaderName:�f�t�H���g�l("exception")<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) mappingKey:������mappingKey<br>
     *         (��ԕω�) mappingValues:������mappingValues<br>
     *         (��ԕω�) viewName:"abc"<br>
     *         (��ԕω�) errorType:"def"<br>
     *         (��ԕω�) errorCode:null<br>
     *         (��ԕω�) errorTypeHeaderName:"exception"<br>
     *         
     * <br>
     * ����params��null�ꍇ�̃e�X�g�B<br>
     * ����errorTypeHeaderName�̓f�t�H���g�l"exception"�ł��邱�Ɗm�F�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitMapping10() throws Exception {
        // �O����
        ExceptionResolveDelegator exceptionResolveDelegator = new ExceptionResolveDelegatorImpl();
        Map<String,String> params = null;

        // ����
        String mappingValues = "abc,def";

        // �e�X�g���{
        exceptionResolveDelegator.initMapping(MAPPING_KEY, mappingValues, params);
        
        // ����
        assertEquals(MAPPING_KEY, UTUtil.getPrivateField(exceptionResolveDelegator, "mappingKey"));
        assertEquals(mappingValues, UTUtil.getPrivateField(exceptionResolveDelegator, "mappingValues"));
        assertEquals("abc", UTUtil.getPrivateField(exceptionResolveDelegator, "viewName"));
        assertEquals("def", UTUtil.getPrivateField(exceptionResolveDelegator, "errorType"));
        assertEquals("exception", UTUtil.getPrivateField(exceptionResolveDelegator, "errorTypeHeaderName"));

    }

    /**
     * testInitMapping11()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,B
     * <br><br>
     * ���͒l�F(����) mappingKey:"mkey"<br>
     *         (����) mappingValues:"abc,def"<br>
     *         (����) params:{"key","value"}<br>
     *         (���) mappingKey:null<br>
     *         (���) mappingValues:null<br>
     *         (���) viewName:null<br>
     *         (���) errorType:null<br>
     *         (���) errorCode:null<br>
     *         (���) errorTypeHeaderName:�f�t�H���g�l("exception")<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) mappingKey:������mappingKey<br>
     *         (��ԕω�) mappingValues:������mappingValues<br>
     *         (��ԕω�) viewName:"abc"<br>
     *         (��ԕω�) errorType:"def"<br>
     *         (��ԕω�) errorCode:null<br>
     *         (��ԕω�) errorTypeHeaderName:"exceptionName"<br>
     *         
     * <br>
     * ����params�ɃL�["errorTypeHeaderName"�����݂��Ȃ��ꍇ�̃e�X�g�B<br>
     * ����errorTypeHeaderName�̓f�t�H���g�l"exception"�ł��邱�Ɗm�F�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitMapping11() throws Exception {
        // �O����
        ExceptionResolveDelegator exceptionResolveDelegator = new ExceptionResolveDelegatorImpl();
        Map<String,String> params = new HashMap<String,String>();
        params.put("key","value");

        // ����
        String mappingValues = "abc,def";

        // �e�X�g���{
        exceptionResolveDelegator.initMapping(MAPPING_KEY, mappingValues, params);
        
        // ����
        assertEquals(MAPPING_KEY, UTUtil.getPrivateField(exceptionResolveDelegator, "mappingKey"));
        assertEquals(mappingValues, UTUtil.getPrivateField(exceptionResolveDelegator, "mappingValues"));
        assertEquals("abc", UTUtil.getPrivateField(exceptionResolveDelegator, "viewName"));
        assertEquals("def", UTUtil.getPrivateField(exceptionResolveDelegator, "errorType"));
        assertEquals("exception", UTUtil.getPrivateField(exceptionResolveDelegator, "errorTypeHeaderName"));

    }
    

    /**
     * testInitMapping12()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,C
     * <br><br>
     * ���͒l�F(����) mappingKey:"mkey"<br>
     *         (����) mappingValues:"abc,def"<br>
     *         (����) params:{"errorTypeHeaderName",null}<br>
     *         (���) mappingKey:null<br>
     *         (���) mappingValues:null<br>
     *         (���) viewName:null<br>
     *         (���) errorType:null<br>
     *         (���) errorCode:null<br>
     *         (���) errorTypeHeaderName:�f�t�H���g�l("exception")<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) mappingKey:������mappingKey<br>
     *         (��ԕω�) mappingValues:������mappingValues<br>
     *         (��ԕω�) viewName:"abc"<br>
     *         (��ԕω�) errorType:"def"<br>
     *         (��ԕω�) errorCode:null<br>
     *         (��ԕω�) errorTypeHeaderName:"exception"<br>
     *         
     * <br>
     * ����params�ɃL�["errorTypeHeaderName"�����݂�,���̒l��null�ꍇ�̃e�X�g�B<br>
     * ����errorTypeHeaderName�̓f�t�H���g�l"exception"�ł��邱�Ɗm�F�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitMapping12() throws Exception {
        // �O����
        ExceptionResolveDelegator exceptionResolveDelegator = new ExceptionResolveDelegatorImpl();
        Map<String,String> params = new HashMap<String,String>();
        params.put("errorTypeHeaderName", null);

        // ����
        String mappingValues = "abc,def";

        // �e�X�g���{
        exceptionResolveDelegator.initMapping(MAPPING_KEY, mappingValues, params);
        
        // ����
        assertEquals(MAPPING_KEY, UTUtil.getPrivateField(exceptionResolveDelegator, "mappingKey"));
        assertEquals(mappingValues, UTUtil.getPrivateField(exceptionResolveDelegator, "mappingValues"));
        assertEquals("abc", UTUtil.getPrivateField(exceptionResolveDelegator, "viewName"));
        assertEquals("def", UTUtil.getPrivateField(exceptionResolveDelegator, "errorType"));
        assertEquals("exception", UTUtil.getPrivateField(exceptionResolveDelegator, "errorTypeHeaderName"));

    }
    
    /**
     * testInitMapping13()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,C
     * <br><br>
     * ���͒l�F(����) mappingKey:"mkey"<br>
     *         (����) mappingValues:"abc,def"<br>
     *         (����) params:{"errorTypeHeaderName",""}<br>
     *         (���) mappingKey:null<br>
     *         (���) mappingValues:null<br>
     *         (���) viewName:null<br>
     *         (���) errorType:null<br>
     *         (���) errorCode:null<br>
     *         (���) errorTypeHeaderName:�f�t�H���g�l("exception")<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) mappingKey:������mappingKey<br>
     *         (��ԕω�) mappingValues:������mappingValues<br>
     *         (��ԕω�) viewName:"abc"<br>
     *         (��ԕω�) errorType:"def"<br>
     *         (��ԕω�) errorCode:null<br>
     *         (��ԕω�) errorTypeHeaderName:"exception"<br>
     *         
     * <br>
     * ����params�ɃL�["errorTypeHeaderName"�����݂�,���̒l���󕶎��ꍇ�̃e�X�g�B<br>
     * ����errorTypeHeaderName�̓f�t�H���g�l"exception"�ł��邱�Ɗm�F�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitMapping13() throws Exception {
        // �O����
        ExceptionResolveDelegator exceptionResolveDelegator = new ExceptionResolveDelegatorImpl();
        Map<String,String> params = new HashMap<String,String>();
        params.put("errorTypeHeaderName", "");

        // ����
        String mappingValues = "abc,def";

        // �e�X�g���{
        exceptionResolveDelegator.initMapping(MAPPING_KEY, mappingValues, params);
        
        // ����
        assertEquals(MAPPING_KEY, UTUtil.getPrivateField(exceptionResolveDelegator, "mappingKey"));
        assertEquals(mappingValues, UTUtil.getPrivateField(exceptionResolveDelegator, "mappingValues"));
        assertEquals("abc", UTUtil.getPrivateField(exceptionResolveDelegator, "viewName"));
        assertEquals("def", UTUtil.getPrivateField(exceptionResolveDelegator, "errorType"));
        assertEquals("exception", UTUtil.getPrivateField(exceptionResolveDelegator, "errorTypeHeaderName"));

    }
    
    /**
     * testInitMapping14()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,B
     * <br><br>
     * ���͒l�F(����) mappingKey:"mkey"<br>
     *         (����) mappingValues:"abc,def"<br>
     *         (����) params:{"errorTypeHeaderName","exceptionName"}<br>
     *         (���) mappingKey:null<br>
     *         (���) mappingValues:null<br>
     *         (���) viewName:null<br>
     *         (���) errorType:null<br>
     *         (���) errorCode:null<br>
     *         (���) errorTypeHeaderName:�f�t�H���g�l("exception")<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) mappingKey:������mappingKey<br>
     *         (��ԕω�) mappingValues:������mappingValues<br>
     *         (��ԕω�) viewName:"abc"<br>
     *         (��ԕω�) errorType:"def"<br>
     *         (��ԕω�) errorCode:null<br>
     *         (��ԕω�) errorTypeHeaderName:"exceptionName"<br>
     *         
     * <br>
     * ����params�ɃL�["errorTypeHeaderName"�����݂�,���̒l��null�Ƌ󕶎��ȊO�ꍇ�̃e�X�g�B<br>
     * ����errorTypeHeaderName�ɂ��̒l��ݒ肷�邱�Ɗm�F�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitMapping14() throws Exception {
        // �O����
        ExceptionResolveDelegator exceptionResolveDelegator = new ExceptionResolveDelegatorImpl();
        Map<String,String> params = new HashMap<String,String>();
        params.put("errorTypeHeaderName", "exceptionName");

        // ����
        String mappingValues = "abc,def";

        // �e�X�g���{
        exceptionResolveDelegator.initMapping(MAPPING_KEY, mappingValues, params);
        
        // ����
        assertEquals(MAPPING_KEY, UTUtil.getPrivateField(exceptionResolveDelegator, "mappingKey"));
        assertEquals(mappingValues, UTUtil.getPrivateField(exceptionResolveDelegator, "mappingValues"));
        assertEquals("abc", UTUtil.getPrivateField(exceptionResolveDelegator, "viewName"));
        assertEquals("def", UTUtil.getPrivateField(exceptionResolveDelegator, "errorType"));
        assertEquals("exceptionName", UTUtil.getPrivateField(exceptionResolveDelegator, "errorTypeHeaderName"));

    }
    
    /**
     * testSetHeader01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) response:HttpResponse�C���X�^���X<br>
     *         (���) this.exceptionKey:EXCEPTION_KEY = "exception"<br>
     *         (���) this.errorType:"validation"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) response:�w�b�_�� exception������"validation"���ݒ肳��邱�Ƃ��m�F����B<br>
     *         
     * <br>
     * �����̃��X�|���X�Ƀw�b�_���ݒ肳��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetHeader01() throws Exception {
        // �O����
        ExceptionResolveDelegator exceptionResolveDelegator = new ExceptionResolveDelegatorImpl();
        UTUtil.setPrivateField(exceptionResolveDelegator, "errorType", "validation");

        // ����
        MockHttpServletResponse response = new MockHttpServletResponse();

        // �e�X�g���{
        exceptionResolveDelegator.setHeader(response);

        // ����
        assertEquals("validation", ((Map)UTUtil.getPrivateField(response, "httpHeaderMap")).get(ExceptionResolveDelegatorImpl.EXCEPTION_KEY));            
    }

    /**
     * testAddObjectToModel01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,C
     * <br><br>
     * ���͒l�F(����) mv:ModelAndView�C���X�^���X<br>
     *         (���) this.errorCode:null<br>
     *         (���) Constants.ERRORCODE_KEY:"errorCode"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) mv:errorCode= null<br>
     *         
     * <br>
     * this.errorCode�����̒l��null�̏ꍇ�AModelAndView��errorCode�����ɃG���[�R�[�h���ݒ肳��Ȃ����Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddObjectToModel01() throws Exception {
        // �O����
        ExceptionResolveDelegator exceptionResolveDelegator = new ExceptionResolveDelegatorImpl();
        UTUtil.setPrivateField(exceptionResolveDelegator, "errorCode", null);

        // ����
        ModelAndView mv = new ModelAndView();

        
        // �e�X�g���{
        exceptionResolveDelegator.addObjectToModel(mv);

        // ����
        assertNull(mv.getModel().get(Constants.ERRORCODE_KEY));            
            
    }

    /**
     * testAddObjectToModel02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) mv:ModelAndView�C���X�^���X<br>
     *         (���) this.errorCode:"abc"<br>
     *         (���) Constants.ERRORCODE_KEY:errorCode<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) mv:errorCode= "abc"<br>
     *         
     * <br>
     * this.errorCode�����̒l��"abc"�̏ꍇ�AModelAndView��errorCode�����ɃG���[�R�[�h"abc"���ݒ肳��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAddObjectToModel02() throws Exception {
        // �O����
        ExceptionResolveDelegator exceptionResolveDelegator = new ExceptionResolveDelegatorImpl();
        UTUtil.setPrivateField(exceptionResolveDelegator, "errorCode", "abc");

        // ����
        ModelAndView mv = new ModelAndView();

        
        // �e�X�g���{
        exceptionResolveDelegator.addObjectToModel(mv);

        // ����
        assertEquals("abc", mv.getModel().get(Constants.ERRORCODE_KEY));            
            
    }

    /**
     * testGetViewName01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) this.viewName:"abc"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"abc"<br>
     *         
     * <br>
     * viewName�����̒l���������ԋp����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetViewName01() throws Exception {
        // �O����
        ExceptionResolveDelegator exceptionResolveDelegator = new ExceptionResolveDelegatorImpl();
        UTUtil.setPrivateField(exceptionResolveDelegator, "viewName", "abc");
        
        // �e�X�g���{������
        assertEquals("abc", exceptionResolveDelegator.getViewName());
            
    }

}
