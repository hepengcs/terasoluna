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

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.MockHttpServletRequest;
import jp.terasoluna.utlib.MockHttpServletResponse;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.web.rich.springmvc.servlet.handler.ExceptionResolveDelegator;
import jp.terasoluna.fw.web.rich.springmvc.servlet.handler.SimpleMappingExceptionResolverEx;
import junit.framework.TestCase;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.mvc.Controller;

/**
 * {@link jp.terasoluna.fw.web.rich.springmvc.servlet.handler.
 * SimpleMappingExceptionResolverEx} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * ��O�N���X���ƃr���[���̃}�b�s���O���s��Exception resolver�̊g���N���X�B<br>
 * �O������F
 * <p>
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.servlet.handler.
 * SimpleMappingExceptionResolverEx
 */
public class SimpleMappingExceptionResolverExTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(
                SimpleMappingExceptionResolverExTest.class);
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
    public SimpleMappingExceptionResolverExTest(String name) {
        super(name);
    }

    /**
     * testAfterPropertiesSet01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,C
     * <br><br>
     * ���͒l�F(���) this.linkedExceptionMappings:null<br>
     *         (���) this.exceptionResolveDelegatorClass:not null<br>
     *         (���) this.exceptionResolveDelegatorMap:���LinkedHashMap<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.exceptionResolveDelegatorMap:���LinkedHashMap<br>
     *         
     * <br>
     * this.linkedExceptionMappings��null�̏ꍇ�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAfterPropertiesSet01() throws Exception {
        // �O����
        SimpleMappingExceptionResolverEx simpleMappingExceptionResolverEx = new SimpleMappingExceptionResolverEx();
        LinkedHashMap linkedExceptionMappings = null;
        UTUtil.setPrivateField(simpleMappingExceptionResolverEx, "linkedExceptionMappings", linkedExceptionMappings);
        LinkedHashMap exceptionResolveDelegatorMap = (LinkedHashMap)UTUtil.getPrivateField(simpleMappingExceptionResolverEx, "exceptionResolveDelegatorMap");
        // �e�X�g���{
        simpleMappingExceptionResolverEx.afterPropertiesSet();
        
        // ����
        assertTrue(exceptionResolveDelegatorMap.isEmpty());
    }

    /**
     * testAfterPropertiesSet02()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA,C,G
     * <br><br>
     * ���͒l�F(���) this.linkedExceptionMappings:not null<br>
     *         (���) this.exceptionResolveDelegatorClass:null<br>
     *         (���) this.exceptionResolveDelegatorMap:���LinkedHashMap<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException{<br>
     *                      detailMessage= "SimpleMappingExceptionResolverEx must be set exceptionResolveDelegatorClass. Check Spring Bean definition file."<br>
     *                    }<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F"SimpleMappingExceptionResolverEx must be set exceptionResolveDelegatorClass. Check Spring Bean definition file."<br>
     *         (��ԕω�) this.exceptionResolveDelegatorMap:���LinkedHashMap<br>
     *         
     * <br>
     * this.exceptionResolveDelegatorClass��null�̏ꍇ�̃e�X�g�B��O���X���[����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAfterPropertiesSet02() throws Exception {
        // �O����
        SimpleMappingExceptionResolverEx simpleMappingExceptionResolverEx = new SimpleMappingExceptionResolverEx();
        LinkedHashMap<String, Object> linkedExceptionMappings = new LinkedHashMap<String, Object>();
        UTUtil.setPrivateField(simpleMappingExceptionResolverEx, "linkedExceptionMappings", linkedExceptionMappings);
        
        Class cl = null;
        UTUtil.setPrivateField(simpleMappingExceptionResolverEx, "exceptionResolveDelegatorClass", cl);
        
        // �e�X�g���{
        try {
            simpleMappingExceptionResolverEx.afterPropertiesSet();
            fail();
        } catch (IllegalStateException e) {

            // ����
            String message = "SimpleMappingExceptionResolverEx must be set exceptionResolveDelegatorClass. Check Spring Bean definition file.";
            assertEquals(message, e.getMessage());

            // ���O�m�F
            assertTrue(LogUTUtil.checkError(message));
        }
    }

    /**
     * testAfterPropertiesSet03()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA,E,G
     * <br><br>
     * ���͒l�F(���) this.linkedExceptionMappings:not null<br>
     *         (���) this.exceptionResolveDelegatorClass:java.util.Date�N���X<br>
     *         (���) this.exceptionResolveDelegatorMap:���LinkedHashMap<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException{<br>
     *                      detailMessage= "java.util.Date is not ExceptionResolveDelegator type. Check Spring Bean definition file."<br>
     *                    }<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�F"java.util.Date is not ExceptionResolveDelegator type. Check Spring Bean definition file."<br>
     *         (��ԕω�) this.exceptionResolveDelegatorMap:���LinkedHashMap<br>
     *         
     * <br>
     * this.exceptionResolveDelegatorClass�̌^��ExceptionResolveDelegator�^�łȂ��ꍇ�̃e�X�g�B��O���X���[����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAfterPropertiesSet03() throws Exception {
        // �O����
        SimpleMappingExceptionResolverEx simpleMappingExceptionResolverEx = new SimpleMappingExceptionResolverEx();
        LinkedHashMap<String, Object> linkedExceptionMappings = new LinkedHashMap<String, Object>();
        linkedExceptionMappings.put("abc","123");
        UTUtil.setPrivateField(simpleMappingExceptionResolverEx, "linkedExceptionMappings", linkedExceptionMappings);
        
        Class cl = java.util.Date.class;
        UTUtil.setPrivateField(simpleMappingExceptionResolverEx, "exceptionResolveDelegatorClass", cl);
        
        // �e�X�g���{
        try {
            simpleMappingExceptionResolverEx.afterPropertiesSet();
            fail();
        } catch (IllegalStateException e) {

            // ����
            String message = cl.getName() + " is not ExceptionResolveDelegator type. Check Spring Bean definition file.";
            assertEquals(message, e.getMessage());

            // ���O�m�F
            assertTrue(LogUTUtil.checkError(message));
        }        
    }

    /**
     * testAfterPropertiesSet04()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA,G
     * <br><br>
     * ���͒l�F(���) this.linkedExceptionMappings:not null<br>
     *         (���) this.exceptionResolveDelegatorClass:����������R���X�g���N������ExceptionResolveDelegator�����N���X<br>
     *         (���) this.exceptionResolveDelegatorMap:���LinkedHashMap<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException{<br>
     *                      detailMessage= this.exceptionResolveDelegatorClass.getName() + " cannot be instantiated. Check Spring Bean definition file.",<br>
     *                      cause= InstantiationException<br>
     *                    }<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�Fthis.exceptionResolveDelegatorClass.getName() + " cannot be instantiated. Check Spring Bean definition file.<br>
     *                    ��O�FInstantiationException<br>
     *         (��ԕω�) this.exceptionResolveDelegatorMap:���LinkedHashMap<br>
     *         
     * <br>
     * this.exceptionResolveDelegatorClass�̃C���X�^���X���Ɏ��s�����ꍇ�̃e�X�g�B��O���X���[����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAfterPropertiesSet04() throws Exception {
        // �O����
        SimpleMappingExceptionResolverEx simpleMappingExceptionResolverEx = new SimpleMappingExceptionResolverEx();
        LinkedHashMap<String, Object> linkedExceptionMappings = new LinkedHashMap<String, Object>();
        linkedExceptionMappings.put("abc","123");
        UTUtil.setPrivateField(simpleMappingExceptionResolverEx, "linkedExceptionMappings", linkedExceptionMappings);
        
        // exceptionResolveDelegatorMap
        LinkedHashMap<String, ExceptionResolveDelegator>exceptionResolveDelegatorMap = new LinkedHashMap<String, ExceptionResolveDelegator>();
        UTUtil.setPrivateField(simpleMappingExceptionResolverEx, "exceptionResolveDelegatorMap", exceptionResolveDelegatorMap);

        Class cl = SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub02.class;
        UTUtil.setPrivateField(simpleMappingExceptionResolverEx, "exceptionResolveDelegatorClass", cl);
        
        // �e�X�g���{
        try {
            simpleMappingExceptionResolverEx.afterPropertiesSet();
            fail();
        } catch (IllegalStateException e) {

            // ����
            String message = cl.getName() + " cannot be instantiated. Check Spring Bean definition file.";
            assertEquals(message, e.getMessage());

            // ���O�m�F
            assertTrue(LogUTUtil.checkError(message, e.getCause()));
        }        
    }

    /**
     * testAfterPropertiesSet05()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA,G
     * <br><br>
     * ���͒l�F(���) this.linkedExceptionMappings:not null<br>
     *         (���) this.exceptionResolveDelegatorClass:private�ȃR���X�g���N�^������ExceptionResolveDelegator�����N���X<br>
     *         (���) this.exceptionResolveDelegatorMap:���LinkedHashMap<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException{<br>
     *                      detailMessage= this.exceptionResolveDelegatorClass.getName() + " cannot be instantiated. Check Spring Bean definition file.",<br>
     *                      cause= IllegalAccessException<br>
     *                    }<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    ���b�Z�[�W�Fthis.exceptionResolveDelegatorClass.getName() + " cannot be instantiated. Check Spring Bean definition file.<br>
     *                    ��O�FInstantiationException<br>
     *         (��ԕω�) this.exceptionResolveDelegatorMap:���LinkedHashMap<br>
     *         
     * <br>
     * this.exceptionResolveDelegatorClass�̃C���X�^���X���Ɏ��s�����ꍇ�̃e�X�g�B��O���X���[����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAfterPropertiesSet05() throws Exception {
        // �O����
        SimpleMappingExceptionResolverEx simpleMappingExceptionResolverEx = new SimpleMappingExceptionResolverEx();
        LinkedHashMap<String, Object> linkedExceptionMappings = new LinkedHashMap<String, Object>();
        linkedExceptionMappings.put("abc","def");
        UTUtil.setPrivateField(simpleMappingExceptionResolverEx, "linkedExceptionMappings", linkedExceptionMappings);
        
        // exceptionResolveDelegatorMap
        LinkedHashMap<String, ExceptionResolveDelegator>exceptionResolveDelegatorMap = new LinkedHashMap<String, ExceptionResolveDelegator>();
        UTUtil.setPrivateField(simpleMappingExceptionResolverEx, "exceptionResolveDelegatorMap", exceptionResolveDelegatorMap);

        Class cl = SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub03.class;
        UTUtil.setPrivateField(simpleMappingExceptionResolverEx, "exceptionResolveDelegatorClass", cl);
        
        // �e�X�g���{
        try {
            simpleMappingExceptionResolverEx.afterPropertiesSet();
            fail();
        } catch (IllegalStateException e) {
            // ����
            String message = cl.getName() + " cannot be instantiated. "
                        + "Check Spring Bean definition file.";
            assertEquals(message, e.getMessage());

            // ���O�m�F
            assertTrue(LogUTUtil.checkError(message, e.getCause()));
        }        
    }

    /**
     * testAfterPropertiesSet06()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,D
     * <br><br>
     * ���͒l�F(���) this.linkedExceptionMappings:���LinkedHashMap<br>
     *         (���) this.exceptionResolveDelegatorClass:�����Ȃ��̃R���X�g���N�^������ExceptionResolveDelegator�����N���X<br>
     *         (���) this.exceptionResolveDelegatorMap:���LinkedHashMap<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.exceptionResolveDelegatorMap:���LinkedHashMap<br>
     *         
     * <br>
     * this.linkedExceptionMappings����̏ꍇ�̃e�X�g�B�����s���Ȃ����Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAfterPropertiesSet06() throws Exception {
        // �O����
        SimpleMappingExceptionResolverEx simpleMappingExceptionResolverEx = new SimpleMappingExceptionResolverEx();
        LinkedHashMap<String, Object> linkedExceptionMappings = new LinkedHashMap<String, Object>();
        UTUtil.setPrivateField(simpleMappingExceptionResolverEx, "linkedExceptionMappings", linkedExceptionMappings);
        
        // exceptionResolveDelegatorMap
        LinkedHashMap<String, ExceptionResolveDelegator>exceptionResolveDelegatorMap = new LinkedHashMap<String, ExceptionResolveDelegator>();
        UTUtil.setPrivateField(simpleMappingExceptionResolverEx, "exceptionResolveDelegatorMap", exceptionResolveDelegatorMap);

        Class cl = jp.terasoluna.fw.web.rich.springmvc.servlet.handler.ExceptionResolveDelegatorImpl.class;
        UTUtil.setPrivateField(simpleMappingExceptionResolverEx, "exceptionResolveDelegatorClass", cl);
        
        // �e�X�g���{
        simpleMappingExceptionResolverEx.afterPropertiesSet();
        
        // ����
        assertTrue(exceptionResolveDelegatorMap.isEmpty());
    }

    /**
     * testAfterPropertiesSet07()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,D
     * <br><br>
     * ���͒l�F(���) this.linkedExceptionMappings:LinkedHashMap {<br>
     *                  "abc"= "123"<br>
     *                }<br>
     *         (���) this.exceptionResolveDelegatorClass:�����Ȃ��̃R���X�g���N�^������ExceptionResolveDelegator�����N���X<br>
     *         (���) this.exceptionResolveDelegatorMap:���LinkedHashMap<br>
     *         (���) this.exceptionResolveDelegatorParams:HashMap{<br>
     *                 "errorTypeHeaderName"="errorType"<br>
     *                }<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.exceptionResolveDelegatorMap:LinkedHashMap{<br>
     *                      "abc"= ExceptionResolveDelegator�C���X�^���X{<br>
     *                        mappingKey= "abc"<br>
     *                        mappingValues= "123"<br>
     *                        paramsMap=HashMap{<br>
     *                          "errorTypeHeaderName"="errorType"<br>
     *                        }<br>
     *                      }<br>
     *                    }<br>
     *         
     * <br>
     * this.linkedExceptionMappings�̗v�f���P�̏ꍇ�̃e�X�g�Bthis.exceptionResolveDelegatorMap�ɗv�f��1�ǉ�����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAfterPropertiesSet07() throws Exception {
        // �O����
        SimpleMappingExceptionResolverEx simpleMappingExceptionResolverEx = new SimpleMappingExceptionResolverEx();
        LinkedHashMap<String, Object> linkedExceptionMappings = new LinkedHashMap<String, Object>();
        linkedExceptionMappings.put("abc", "123");
        UTUtil.setPrivateField(simpleMappingExceptionResolverEx, "linkedExceptionMappings", linkedExceptionMappings);
        
        // exceptionResolveDelegatorMap
        LinkedHashMap<String, ExceptionResolveDelegator>exceptionResolveDelegatorMap = new LinkedHashMap<String, ExceptionResolveDelegator>();
        UTUtil.setPrivateField(simpleMappingExceptionResolverEx, "exceptionResolveDelegatorMap", exceptionResolveDelegatorMap);

        Class cl = SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01.class;
        UTUtil.setPrivateField(simpleMappingExceptionResolverEx, "exceptionResolveDelegatorClass", cl);

        Map<String, String> map = new HashMap<String, String>();
        map.put("errorTypeHeaderName","errorType");
        UTUtil.setPrivateField(simpleMappingExceptionResolverEx, "exceptionResolveDelegatorParams", map);
        
        // �e�X�g���{
        simpleMappingExceptionResolverEx.afterPropertiesSet();
        
        // ����
        SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01 result = (SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01)exceptionResolveDelegatorMap.get("abc");
        assertEquals("abc", result.mappingKey);
        assertEquals("123", result.mappingValues);
        assertSame(map, result.paramsMap);
    }

    /**
     * testAfterPropertiesSet08()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,D
     * <br><br>
     * ���͒l�F(���) this.linkedExceptionMappings:LinkedHashMap {<br>
     *                  "abc"= "123",<br>
     *                  "def"= "456",<br>
     *                  "ghi"= "789"<br>
     *                }<br>
     *         (���) this.exceptionResolveDelegatorClass:�����Ȃ��̃R���X�g���N�^������ExceptionResolveDelegator�����N���X<br>
     *         (���) this.exceptionResolveDelegatorMap:���LinkedHashMap<br>
     *         (���) this.exceptionResolveDelegatorParams:HashMap{<br>
     *                 "errorTypeHeaderName"="errorType"<br>
     *                }<br>
     *                         
     * <br>
     * ���Ғl�F(��ԕω�) this.exceptionResolveDelegatorMap:LinkedHashMap{<br>
     *                      "abc"= ExceptionResolveDelegator�����N���X{<br>
     *                        mappingKey= "abc"<br>
     *                        mappingValues= "123"<br>
     *                        paramsMap=HashMap{<br>
     *                          "errorTypeHeaderName"="errorType"<br>
     *                        }<br>
     *                      }<br>
     *                      "abc"= ExceptionResolveDelegator�����N���X{<br>
     *                        mappingKey= "def"<br>
     *                        mappingValues= "456"<br>
     *                        paramsMap=HashMap{<br>
     *                          "errorTypeHeaderName"="errorType"<br>
     *                        }<br>                        
     *                      }<br>
     *                      "abc"= ExceptionResolveDelegator�����N���X{<br>
     *                        mappingKey= "ghi"<br>
     *                        mappingValues= "789"<br>
     *                        paramsMap=HashMap{<br>
     *                          "errorTypeHeaderName"="errorType"<br>
     *                        }<br>
     *                      }<br>
     *                    }<br>
     *         
     * <br>
     * this.linkedExceptionMappings�̗v�f���P�̏ꍇ�̃e�X�g�Bthis.exceptionResolveDelegatorMap�ɗv�f��3�ǉ�����邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testAfterPropertiesSet08() throws Exception {
        // �O����
        SimpleMappingExceptionResolverEx simpleMappingExceptionResolverEx = new SimpleMappingExceptionResolverEx();
        LinkedHashMap<String, Object> linkedExceptionMappings = new LinkedHashMap<String, Object>();
        linkedExceptionMappings.put("abc", "123");
        linkedExceptionMappings.put("def", "456");
        linkedExceptionMappings.put("ghi", "789");
        UTUtil.setPrivateField(simpleMappingExceptionResolverEx, "linkedExceptionMappings", linkedExceptionMappings);
        
        // exceptionResolveDelegatorMap
        LinkedHashMap<String, ExceptionResolveDelegator>exceptionResolveDelegatorMap = new LinkedHashMap<String, ExceptionResolveDelegator>();
        UTUtil.setPrivateField(simpleMappingExceptionResolverEx, "exceptionResolveDelegatorMap", exceptionResolveDelegatorMap);

        Class cl = SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01.class;
        UTUtil.setPrivateField(simpleMappingExceptionResolverEx, "exceptionResolveDelegatorClass", cl);
        
        Map<String, String> map = new HashMap<String, String>();
        map.put("errorTypeHeaderName","errorType");
        UTUtil.setPrivateField(simpleMappingExceptionResolverEx, "exceptionResolveDelegatorParams", map);
        
        // �e�X�g���{
        simpleMappingExceptionResolverEx.afterPropertiesSet();
        
        // ����
        SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01 result01 = (SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01)exceptionResolveDelegatorMap.get("abc");
        assertEquals("abc", result01.mappingKey);
        assertEquals("123", result01.mappingValues);
        assertSame(map, result01.paramsMap);

        SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01 result02 = (SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01)exceptionResolveDelegatorMap.get("def");
        assertEquals("def", result02.mappingKey);
        assertEquals("456", result02.mappingValues);
        assertSame(map, result02.paramsMap);

        SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01 result03 = (SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01)exceptionResolveDelegatorMap.get("ghi");
        assertEquals("ghi", result03.mappingKey);
        assertEquals("789", result03.mappingValues);
        assertSame(map, result03.paramsMap);

    }

    /**
     * testSetExceptionResolveDelegatorClass01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) exceptionResolveDelegatorClass:exceptionResolveDelegatorClass�N���X<br>
     *         (���) this.exceptionResolveDelegatorClass:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.exceptionResolveDelegatorClass:exceptionResolveDelegatorClass�N���X<br>
     *         
     * <br>
     * �����̒l�������������ɐݒ肳��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetExceptionResolveDelegatorClass01() throws Exception {
        // �O����
        SimpleMappingExceptionResolverEx simpleMappingExceptionResolverEx = new SimpleMappingExceptionResolverEx();
        UTUtil.setPrivateField(simpleMappingExceptionResolverEx, "exceptionResolveDelegatorClass", null);

        // ����
        Class<? extends ExceptionResolveDelegator> cl = jp.terasoluna.fw.web.rich.springmvc.servlet.handler.ExceptionResolveDelegatorImpl.class;
        
        // �e�X�g���{
        simpleMappingExceptionResolverEx.setExceptionResolveDelegatorClass(cl);

        // ����
        assertEquals(cl, UTUtil.getPrivateField(simpleMappingExceptionResolverEx, "exceptionResolveDelegatorClass"));

    }
    
    /**
     * testSetExceptionResolveDelegatorParams01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) exceptionResolveDelegatorParams:not null<br>
     *         (���) this.exceptionResolveDelegatorParams:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.exceptionResolveDelegatorParams:�����Őݒ肳�ꂽ�l���ݒ肳��Ă���B<br>
     *         
     * <br>
     * �����̒l�������������ɐݒ肳��邱�Ƃ��m�F����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetExceptionResolveDelegatorParams01() throws Exception {
        // �O����
        SimpleMappingExceptionResolverEx simpleMappingExceptionResolverEx = new SimpleMappingExceptionResolverEx();
        UTUtil.setPrivateField(simpleMappingExceptionResolverEx, "exceptionResolveDelegatorParams", null);

        // ����
        Map<String, String> map = new HashMap<String, String>();
        
        // �e�X�g���{
        simpleMappingExceptionResolverEx.setExceptionResolveDelegatorParams(map);

        // ����
        assertEquals(map, 
                UTUtil.getPrivateField(simpleMappingExceptionResolverEx, "exceptionResolveDelegatorParams"));

    }
    
    /**
     * testSetDefaultErrorView01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) defaultErrorView:null<br>
     *         (���) super.setDefaultErrorView�i�j:
     *         super�N���X��defaultErrorView������
     *         ����defaultErrorView��ݒ肷��B<br>
     *         (���) logger.isInfoEnabled():true<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.defaultErrorView:null<br>
     *         (��ԕω�) ���O:Default error view is ' 
     *         + this.defaultErrorView + "'"<br>
     *         
     * <br>
     * defaultErrorView������setter���\�b�h�̃e�X�g�B
     * ������Null�̏ꍇ�ANull���Z�b�g����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetDefaultErrorView01() throws Exception {
        // �O����
        SimpleMappingExceptionResolverEx exceptionResolver = 
            new SimpleMappingExceptionResolverEx();
        UTUtil.setPrivateField(exceptionResolver, "defaultErrorView", "");
        setField(SimpleMappingExceptionResolver.class, "defaultErrorView", 
                exceptionResolver, "");

        // �e�X�g���{
        exceptionResolver.setDefaultErrorView(null);

        // ����
        Object result = UTUtil.getPrivateField(
                exceptionResolver, "defaultErrorView");
        Object superResult = getField(
                SimpleMappingExceptionResolver.class, 
                "defaultErrorView", exceptionResolver);
        assertNull(result);
        assertNull(superResult);
        assertTrue(LogUTUtil.checkInfo("Default error view is 'null'"));
    }

    /**
     * testSetDefaultErrorView02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) defaultErrorView:�󕶎�<br>
     *         (���) super.setDefaultErrorView�i�j:
     *         super�N���X��defaultErrorView������
     *         ����defaultErrorView��ݒ肷��B<br>
     *         (���) logger.isInfoEnabled():true<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.defaultErrorView:�󕶎�<br>
     *         (��ԕω�) ���O:Default error view is ' 
     *         + this.defaultErrorView + "'"<br>
     *         
     * <br>
     * defaultErrorView������setter���\�b�h�̃e�X�g�B
     * �������󕶎��̏ꍇ�A�󕶎��@���Z�b�g����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetDefaultErrorView02() throws Exception {
        // �O����
        SimpleMappingExceptionResolverEx exceptionResolver = 
            new SimpleMappingExceptionResolverEx();
        UTUtil.setPrivateField(exceptionResolver, "defaultErrorView", null);
        setField(SimpleMappingExceptionResolver.class, "defaultErrorView", 
                exceptionResolver, null);

        // �e�X�g���{
        String value = "";
        exceptionResolver.setDefaultErrorView(value);

        // ����
        Object result = UTUtil.getPrivateField(
                exceptionResolver, "defaultErrorView");
        Object superResult = getField(
                SimpleMappingExceptionResolver.class, 
                "defaultErrorView", exceptionResolver);
        assertSame(value, result);
        assertSame(value, superResult);
        assertTrue(LogUTUtil.checkInfo("Default error view is ''"));
    }

    /**
     * testSetDefaultErrorView03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) defaultErrorView:not null<br>
     *         (���) super.setDefaultErrorView�i�j:
     *         super�N���X��defaultErrorView������
     *         ����defaultErrorView��ݒ肷��B<br>
     *         (���) logger.isInfoEnabled():true<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.defaultErrorView:not null<br>
     *         (��ԕω�) ���O:Default error view is ' 
     *         + this.defaultErrorView + "'"<br>
     *         
     * <br>
     * defaultErrorView������setter���\�b�h�̃e�X�g�B
     * ������NotNull�̏ꍇ�A�������Z�b�g����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetDefaultErrorView03() throws Exception {
        // �O����
        SimpleMappingExceptionResolverEx exceptionResolver = 
            new SimpleMappingExceptionResolverEx();
        UTUtil.setPrivateField(exceptionResolver, "defaultErrorView", null);
        setField(SimpleMappingExceptionResolver.class, "defaultErrorView", 
                exceptionResolver, null);

        // �e�X�g���{
        String value = "test";
        exceptionResolver.setDefaultErrorView(value);

        // ����
        Object result = UTUtil.getPrivateField(
                exceptionResolver, "defaultErrorView");
        Object superResult = getField(
                SimpleMappingExceptionResolver.class, 
                "defaultErrorView", exceptionResolver);
        assertSame(value, result);
        assertSame(value, superResult);
        assertTrue(LogUTUtil.checkInfo("Default error view is 'test'"));
    }

    /**
     * testSetMappedHandlers01()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) mappedHandlers:null<br>
     *         (���) super.setMappedHandlers�i�j:
     *         super�N���X��mappedHandlers�����Ɉ���mappedHandlers��ݒ肷��B<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.mappedHandlers:null<br>
     *         (��ԕω�) super.mappedHandlers:null<br>
     *         
     * <br>
     * mappedHandlers������set���\�b�h�̃e�X�g�B
     * Null��Set�C���X�^���X�������Ƃ���B�w�肵���l���Z�b�g����Ă���B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetMappedHandlers01() throws Exception {
        // �O����
        SimpleMappingExceptionResolverEx exceptionResolver = 
            new SimpleMappingExceptionResolverEx();
        UTUtil.setPrivateField(
                exceptionResolver, "mappedHandlers", new HashSet());
        setField(SimpleMappingExceptionResolver.class, "mappedHandlers", 
                exceptionResolver, new HashSet());

        // �e�X�g���{
        exceptionResolver.setMappedHandlers(null);

        // ����
        Object result = UTUtil.getPrivateField(
                exceptionResolver, "mappedHandlers");
        Object superResult = getField(
                SimpleMappingExceptionResolver.class, 
                "mappedHandlers", exceptionResolver);
        assertNull(result);
        assertNull(superResult);
    }

    /**
     * testSetMappedHandlers02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) mappedHandlers:not null<br>
     *                (�v�f�R�j<br>
     *         (���) super.setMappedHandlers�i�j:
     *         super�N���X��mappedHandlers������
     *         ����mappedHandlers��ݒ肷��B<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.mappedHandlers:not null<br>
     *                    (�v�f�R�j<br>
     *         (��ԕω�) super.mappedHandlers:not null<br>
     *                    (�v�f�R�j<br>
     *         
     * <br>
     * mappedHandlers������set���\�b�h�̃e�X�g�B
     * �v�f���R��Set�C���X�^���X�������Ƃ���B�w�肵���l���Z�b�g����Ă���B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetMappedHandlers02() throws Exception {
        // �O����
        SimpleMappingExceptionResolverEx exceptionResolver = 
            new SimpleMappingExceptionResolverEx();
        UTUtil.setPrivateField(exceptionResolver, "mappedHandlers", null);
        setField(SimpleMappingExceptionResolver.class, "mappedHandlers", 
                exceptionResolver, null);
        Set<Object> set = new HashSet<Object>();
        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj3 = new Object();
        set.add(obj1);
        set.add(obj2);
        set.add(obj3);

        // �e�X�g���{
        exceptionResolver.setMappedHandlers(set);

        // ����
        Object result = UTUtil.getPrivateField(
                exceptionResolver, "mappedHandlers");
        Object superResult = getField(
                SimpleMappingExceptionResolver.class, 
                "mappedHandlers", exceptionResolver);
        assertSame(set, result);
        assertSame(set, superResult);
    }

    /**
     * testSetDefaultStatusCode01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) defaultStatusCode:not null<br>
     *                (�Q�O�O�j<br>
     *         (���) super.setDefaultStatusCode(�j:
     *         super�N���X��defaultStatusCode������
     *         ����defaultStatusCode��ݒ肷��B<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.defaultStatusCode:not null<br>
     *                    (�Q�O�O�j<br>
     *         (��ԕω�) super.defaultStatusCode:not null<br>
     *                    (�Q�O�O�j<br>
     *         
     * <br>
     * defaultStatusCode������set���\�b�h�̃e�X�g�B
     * ���l�Q�O�O��Integer�C���X�^���X�������Ƃ���B
     * �w�肵���l���Z�b�g����Ă���B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetDefaultStatusCode01() throws Exception {
        // �O����
        SimpleMappingExceptionResolverEx exceptionResolver = 
            new SimpleMappingExceptionResolverEx();
        UTUtil.setPrivateField(exceptionResolver, "defaultStatusCode", 0);
        setField(SimpleMappingExceptionResolver.class, "defaultStatusCode", 
                exceptionResolver, 0);

        // �e�X�g���{
        int value = 200;
        exceptionResolver.setDefaultStatusCode(value);
        
        // ����
        Object result = UTUtil.getPrivateField(
                exceptionResolver, "defaultStatusCode");
        Object superResult = getField(
                SimpleMappingExceptionResolver.class, 
                "defaultStatusCode", exceptionResolver);
        assertEquals(value, result);
        assertEquals(value, superResult);
    }

    /**
     * testSetExceptionMappings01()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) mappings:not null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:UnsupportedOperationException<br>
     *         
     * <br>
     * �T�|�[�g���Ă��Ȃ����\�b�h�̃e�X�g�B��O��Ԃ��B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("deprecation")
    public void testSetExceptionMappings01() throws Exception {
        // �O����
        SimpleMappingExceptionResolverEx exceptionResolver = 
            new SimpleMappingExceptionResolverEx();

        // �e�X�g���{
        try {
            exceptionResolver.setExceptionMappings(new Properties());
            fail();
        } catch (UnsupportedOperationException e) {
            // ���ʊm�F
        	return;
        }
    }

    /**
     * testSetLinkedExceptionMappings01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) linkedExceptionMappings:not null<br>
     *         (���) this.linkedExceptionMappings:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.linkedExceptionMappings:
     * �����Őݒ肳�ꂽ�l���ݒ肳��Ă���B<br>
     *         
     * <br>
     * linkedExceptionMappings������setter���\�b�h�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetLinkedExceptionMappings01() throws Exception {
        // �O����
        SimpleMappingExceptionResolverEx exceptionResolver = 
            new SimpleMappingExceptionResolverEx();
        UTUtil.setPrivateField(
                exceptionResolver, "linkedExceptionMappings", null);

        // �e�X�g���{
        Map<String, Object> map = new HashMap<String, Object>();
        exceptionResolver.setLinkedExceptionMappings(map);
        
        // ����
        Object result = UTUtil.getPrivateField(
                exceptionResolver, "linkedExceptionMappings");
        assertSame(map, result);
    }

    /**
     * testResolveException01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,C
     * <br><br>
     * ���͒l�F(����) request:�g�p���Ȃ�<br>
     *         (����) response:not null<br>
     *         (����) handler:not null<br>
     *                �i�R���g���[���̃C���X�^���X�j<br>
     *         (����) ex:Exception�C���X�^���X<br>
     *         (���) this.mappedHandlers:������handler�Ɠ����I�u�W�F�N�g��v�f�Ɏ����Ȃ�Set<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) ModelAndView:null<br>
     *         
     * <br>
     * �{�N���X�ŏ������Ȃ��n���h���i�R���g���[���j�̃e�X�g�BNull��Ԃ��B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testResolveException01() throws Exception {
        // �O����
        SimpleMappingExceptionResolverEx exceptionResolver = 
            new SimpleMappingExceptionResolverEx();
        Set<Object> set = new HashSet<Object>();
        set.add(new SimpleMappingExceptionResolverEx_ControllerImplStub01());
        UTUtil.setPrivateField(
                exceptionResolver, "mappedHandlers", set);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // �e�X�g���{
        Controller controller = 
            new SimpleMappingExceptionResolverEx_ControllerImplStub01();
        ModelAndView mv = exceptionResolver.resolveException(
                request, response, controller, new Exception());

        // ����
        assertNull(mv);
    }

    /**
     * testResolveException02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,C
     * <br><br>
     * ���͒l�F(����) request:�g�p���Ȃ�<br>
     *         (����) response:not null<br>
     *         (����) handler:null<br>
     *         (����) ex:Exception�C���X�^���X<br>
     *         (���) this.mappedHandlers:null<br>
     *         (���) exceptionResolveDelegatorMap:�v�f�����Map<br>
     *         (���) this.defaultErrorView:null<br>
     *         (���) this.defaultStatusCode:not null<br>
     *                (200)<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) ModelAndView:null<br>
     *         
     * <br>
     * �n���h�����O�����O���L�q���Ă��Ȃ��p�^�[���̃e�X�g�BNull��Ԃ��B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testResolveException02() throws Exception {
        // �O����
        SimpleMappingExceptionResolverEx exceptionResolver = 
            new SimpleMappingExceptionResolverEx();
        UTUtil.setPrivateField(exceptionResolver, "mappedHandlers", null);
        LinkedHashMap exceptionResolveDelegatorMap = new LinkedHashMap();
        UTUtil.setPrivateField(
                exceptionResolver, "exceptionResolveDelegatorMap", exceptionResolveDelegatorMap);
        String defaultErrorViewName = null;
        UTUtil.setPrivateField(exceptionResolver, "defaultErrorView", defaultErrorViewName);
        int statusCode = 200;
        UTUtil.setPrivateField(
                exceptionResolver, "defaultStatusCode", statusCode);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        Exception exception = new Exception();

        // �e�X�g���{
        ModelAndView mv = exceptionResolver.resolveException(
                request, response, null, exception);

        // ����
        assertNull(mv);
        
    }

    /**
     * testResolveException03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,C
     * <br><br>
     * ���͒l�F(����) request:�g�p���Ȃ�<br>
     *         (����) response:not null<br>
     *         (����) handler:null<br>
     *         (����) ex:Exception�C���X�^���X<br>
     *         (���) this.mappedHandlers:null<br>
     *         (���) exceptionResolveDelegatorMap:�v�f���P����Map<br>
     *                Exception= ExceptionResolveDelegator�C���X�^���X{ viewName= null}<br>
     *         (���) getDepth�i�j:�u�O�v<br>
     *         (���) this.defaultErrorView:"default"<br>
     *         (���) this.defaultStatusCode:not null<br>
     *                (200)<br>
     *         (���) getModelAndView():getModelAndView()���\�b�h���Ԃ��r���[<br>
     *         (���) exceptionResolveDelegator.setHeader():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     *         (���) exceptionResolveDelegator.addObjectToModel():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) ModelAndView:ModelAndView<br>
     *                  �EviewName�Fdefault<br>
     *                  �Emodel�iMap�^�j<br>
     *                  exception=������ex<br>
     *         (��ԕω�) response:�X�e�[�^�X�R�[�h���ݒ肳���B<br>
     *         
     * <br>
     * �n���h�����O�����O���P�L�q���Ă���A����O�ɑΉ�����ExceptionResolveDelegator��viewName������null���ݒ肳��Ă���p�^�[���̃e�X�g�B�f�t�H���g�G���[�r���[���ݒ肳�ꂽModelAndView��Ԃ��B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testResolveException03() throws Exception {
        // �O����
        // Delegator����
        SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01 exceptionResolveDelegatorImpl = new SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01();
        exceptionResolveDelegatorImpl.viewName = null;
        
        SimpleMappingExceptionResolverEx exceptionResolver = 
            new SimpleMappingExceptionResolverEx();
        UTUtil.setPrivateField(exceptionResolver, "mappedHandlers", null);
        LinkedHashMap<String, Object> exceptionResolveDelegatorMap = new LinkedHashMap<String, Object>();
        UTUtil.setPrivateField(
                exceptionResolver, "exceptionResolveDelegatorMap", exceptionResolveDelegatorMap);
        exceptionResolveDelegatorMap.put("java.lang.Exception",exceptionResolveDelegatorImpl);
        String defaultErrorViewName = "default";
        UTUtil.setPrivateField(exceptionResolver, "defaultErrorView", defaultErrorViewName);
        int statusCode = 200;
        UTUtil.setPrivateField(
                exceptionResolver, "defaultStatusCode", statusCode);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        Exception exception = new Exception();
        
        // �e�X�g���{
        ModelAndView mv = exceptionResolver.resolveException(
                request, response, null, exception);

        // ����
        assertEquals(defaultErrorViewName, mv.getViewName());
        assertSame(exception, mv.getModel().get("exception"));
        
        // �X�^�u�̌Ăяo���m�F
        assertTrue(exceptionResolveDelegatorImpl.isAddObjectToModel);
        assertTrue(exceptionResolveDelegatorImpl.isSetHeader);

        // ���O �m�F
        assertTrue(LogUTUtil.checkError("Handled the following exception.",
                exception));
    }

    /**
     * testResolveException04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,D
     * <br><br>
     * ���͒l�F(����) request:�g�p���Ȃ�<br>
     *         (����) response:not null<br>
     *         (����) handler:not null<br>
     *                �i�R���g���[���̃C���X�^���X�j<br>
     *         (����) ex:Exception�C���X�^���X<br>
     *         (���) this.mappedHandlers:������handler�Ɠ����I�u�W�F�N�g��v�f�Ɏ���Set<br>
     *         (���) exceptionResolveDelegatorMap:�v�f���P����Map<br>
     *                Exception= ExceptionResolveDelegator�C���X�^���X{ viewName= "exception"}<br>
     *         (���) getDepth�i�j:�u�O�v<br>
     *         (���) this.defaultErrorView:null<br>
     *         (���) this.defaultStatusCode:not null<br>
     *                (200)<br>
     *         (���) getModelAndView():getModelAndView()���\�b�h���Ԃ��r���[<br>
     *         (���) exceptionResolveDelegator.setHeader():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     *         (���) exceptionResolveDelegator.addObjectToModel():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) ModelAndView:ModelAndView<br>
     *                  �EviewName�Fexception<br>
     *                  �Emodel�iMap�^�j<br>
     *                  exception=������ex<br>
     *         (��ԕω�) response:�X�e�[�^�X�R�[�h���ݒ肳���B<br>
     *         
     * <br>
     * �n���h�����O�����O���P�L�q���Ă���A����O�ɑΉ�����ExceptionResolveDelegator��viewName�����ɒl���ݒ肳��Ă���p�^�[���̃e�X�g�B�����ModelAndView��Ԃ��B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testResolveException04() throws Exception {
        // �O����
        // Delegator����
        SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01 exceptionResolveDelegatorImpl = new SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01();
        exceptionResolveDelegatorImpl.viewName = "exception";
        
        SimpleMappingExceptionResolverEx exceptionResolver = 
            new SimpleMappingExceptionResolverEx();
        UTUtil.setPrivateField(exceptionResolver, "mappedHandlers", null);
        LinkedHashMap<String, ExceptionResolveDelegator> exceptionResolveDelegatorMap = new LinkedHashMap<String, ExceptionResolveDelegator>();
        UTUtil.setPrivateField(
                exceptionResolver, "exceptionResolveDelegatorMap", exceptionResolveDelegatorMap);
        exceptionResolveDelegatorMap.put("java.lang.Exception",exceptionResolveDelegatorImpl);
        String defaultErrorViewName = null;
        UTUtil.setPrivateField(exceptionResolver, "defaultErrorView", defaultErrorViewName);
        int statusCode = 200;
        UTUtil.setPrivateField(
                exceptionResolver, "defaultStatusCode", statusCode);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        Exception exception = new Exception();
        
        // �e�X�g���{
        ModelAndView mv = exceptionResolver.resolveException(
                request, response, null, exception);

        // ����
        assertEquals(exceptionResolveDelegatorImpl.viewName, mv.getViewName());
        assertSame(exception, mv.getModel().get("exception"));
        
        // �X�^�u�̌Ăяo���m�F
        assertTrue(exceptionResolveDelegatorImpl.isAddObjectToModel);
        assertTrue(exceptionResolveDelegatorImpl.isSetHeader);

        // ���O �m�F
        assertTrue(LogUTUtil.checkError("Handled the following exception.",
                exception));
    }

    /**
     * testResolveException05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,D
     * <br><br>
     * ���͒l�F(����) request:�g�p���Ȃ�<br>
     *         (����) response:not null<br>
     *         (����) handler:null<br>
     *         (����) ex:BindException�C���X�^���X<br>
     *         (���) this.mappedHandlers:null<br>
     *         (���) exceptionResolveDelegatorMap:�v�f���R����Map<br>
     *                OXMappingException= ExceptionResolveDelegator�C���X�^���X{ viewName= "oxmException"},<br>
     *                BindException= ExceptionResolveDelegator�C���X�^���X{ viewName= "bindException"},<br>
     *                Exception= ExceptionResolveDelegator�C���X�^���X{ viewName= "exception"}<br>
     *         (���) getDepth�i�j:�u�|�P�v<br>
     *                �u�O�v<br>
     *         (���) this.defaultErrorView:null<br>
     *         (���) this.defaultStatusCode:not null<br>
     *                (200)<br>
     *         (���) getModelAndView():getModelAndView()���\�b�h���Ԃ��r���[<br>
     *         (���) exceptionResolveDelegator.setHeader():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     *         (���) exceptionResolveDelegator.addObjectToModel():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) ModelAndView:ModelAndView<br>
     *                  �EviewName�FbindException<br>
     *                  �Emodel�iMap�^�j<br>
     *                  exception=������ex<br>
     *         (��ԕω�) response:�X�e�[�^�X�R�[�h���ݒ肳���B<br>
     *         
     * <br>
     * �n���h�����O�����O���R�L�q���Ă���p�^�[���̃e�X�g�B�����ModelAndView��Ԃ��B����������O���L�q������O�̂Q�ڈȍ~�Ō�����p�^�[���B�����ModelAndView��Ԃ��B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testResolveException05() throws Exception {
        // �O���� --------------------------------------------------------------
        // Delegator����
        SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01 exceptionResolveDelegatorImpl01 = new SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01();
        exceptionResolveDelegatorImpl01.viewName = "oxmException";
        SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01 exceptionResolveDelegatorImpl02 = new SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01();
        exceptionResolveDelegatorImpl02.viewName = "bindException";
        SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01 exceptionResolveDelegatorImpl03 = new SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01();
        exceptionResolveDelegatorImpl03.viewName = "exception";        
        
        SimpleMappingExceptionResolverEx exceptionResolver = 
            new SimpleMappingExceptionResolverEx();
        // ����
        Controller controller =
            new SimpleMappingExceptionResolverEx_ControllerImplStub01(); 
        Set<Object> set = new HashSet<Object>();
        set.add(controller);
        UTUtil.setPrivateField(exceptionResolver, "mappedHandlers", set);
        
        LinkedHashMap<String, ExceptionResolveDelegator> exceptionResolveDelegatorMap = new LinkedHashMap<String, ExceptionResolveDelegator>();
        UTUtil.setPrivateField(
                exceptionResolver, "exceptionResolveDelegatorMap", exceptionResolveDelegatorMap);
        exceptionResolveDelegatorMap.put("jp.terasoluna.fw.oxm.exception.OXMappingException", exceptionResolveDelegatorImpl01);
        exceptionResolveDelegatorMap.put("org.springframework.validation.BindException", exceptionResolveDelegatorImpl02);
        exceptionResolveDelegatorMap.put("java.lang.Exception", exceptionResolveDelegatorImpl03);
        String defaultErrorViewName = null;
        UTUtil.setPrivateField(exceptionResolver, "defaultErrorView", defaultErrorViewName);

        int statusCode = 200;
        UTUtil.setPrivateField(
                exceptionResolver, "defaultStatusCode", statusCode);
        
        // ���\�b�h���� 
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        Exception exception = new BindException(new Object(), "name");
        
        // �e�X�g���{ ----------------------------------------------------------
        ModelAndView mv = exceptionResolver.resolveException(
                request, response, controller, exception);

        // ����
        assertEquals(exceptionResolveDelegatorImpl02.viewName, mv.getViewName());
        assertSame(exception, mv.getModel().get("exception"));
        
        // �X�^�u�̌Ăяo���m�F
        assertTrue(exceptionResolveDelegatorImpl02.isAddObjectToModel);
        assertTrue(exceptionResolveDelegatorImpl02.isSetHeader);

        // ���O �m�F
        assertTrue(LogUTUtil.checkError("Handled the following exception.",
                exception));
    }

    /**
     * testResolveException06()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,D
     * <br><br>
     * ���͒l�F(����) request:�g�p���Ȃ�<br>
     *         (����) response:not null<br>
     *         (����) handler:null<br>
     *         (����) ex:BindException�C���X�^���X<br>
     *         (���) this.mappedHandlers:null<br>
     *         (���) exceptionResolveDelegatorMap:�v�f���P����Map<br>
     *                Exception= ExceptionResolveDelegator�C���X�^���X{ viewName= "exception"}<br>
     *         (���) getDepth�i�j:�u�P�v<br>
     *                �ilinkedExceptionMappings�Œ�`������O�̐e�N���X�̗�O���n���h�����O����j<br>
     *         (���) this.defaultErrorView:null<br>
     *         (���) this.defaultStatusCode:not null<br>
     *                (200)<br>
     *         (���) getModelAndView():getModelAndView()���\�b�h���Ԃ��r���[<br>
     *         (���) exceptionResolveDelegator.setHeader():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     *         (���) exceptionResolveDelegator.addObjectToModel():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) ModelAndView:ModelAndView<br>
     *                  �EviewName�Fexception<br>
     *                  �Emodel�iMap�^�j<br>
     *                  exception=������ex<br>
     *         (��ԕω�) response:�X�e�[�^�X�R�[�h���ݒ肳���B<br>
     *         
     * <br>
     * �n���h�����O�����O������ex�̐e�N���X�ł���ꍇ�̃p�^�[���̃e�X�g�B�����ModelAndView��Ԃ��B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testResolveException06() throws Exception {
        // �O����
        // Delegator����
        SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01 exceptionResolveDelegatorImpl = new SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01();
        exceptionResolveDelegatorImpl.viewName = "exception";
        
        SimpleMappingExceptionResolverEx exceptionResolver = 
            new SimpleMappingExceptionResolverEx();
        UTUtil.setPrivateField(exceptionResolver, "mappedHandlers", null);
        LinkedHashMap<String, ExceptionResolveDelegator> exceptionResolveDelegatorMap = new LinkedHashMap<String, ExceptionResolveDelegator>();
        UTUtil.setPrivateField(
                exceptionResolver, "exceptionResolveDelegatorMap", exceptionResolveDelegatorMap);
        exceptionResolveDelegatorMap.put("java.lang.Exception",exceptionResolveDelegatorImpl);
        String defaultErrorViewName = null;
        UTUtil.setPrivateField(exceptionResolver, "defaultErrorView", defaultErrorViewName);
        int statusCode = 200;
        UTUtil.setPrivateField(
                exceptionResolver, "defaultStatusCode", statusCode);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        Exception exception = new BindException(new Object(), "name");
        
        // �e�X�g���{
        ModelAndView mv = exceptionResolver.resolveException(
                request, response, null, exception);

        // ����
        assertEquals(exceptionResolveDelegatorImpl.viewName, mv.getViewName());
        assertSame(exception, mv.getModel().get("exception"));
        
        // �X�^�u�̌Ăяo���m�F
        assertTrue(exceptionResolveDelegatorImpl.isAddObjectToModel);
        assertTrue(exceptionResolveDelegatorImpl.isSetHeader);

        // ���O �m�F
        assertTrue(LogUTUtil.checkError("Handled the following exception.",
                exception));
    }

    /**
     * testResolveException07()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,D
     * <br><br>
     * ���͒l�F(����) request:�g�p���Ȃ�<br>
     *         (����) response:not null<br>
     *         (����) handler:null<br>
     *         (����) ex:BindException�C���X�^���X<br>
     *         (���) this.mappedHandlers:null<br>
     *         (���) exceptionResolveDelegatorMap:�v�f���P����Map<br>
     *                OXMappingException= ExceptionResolveDelegator�C���X�^���X{ viewName= "oxmException"}<br>
     *         (���) getDepth�i�j:�u�|�P�v<br>
     *         (���) this.defaultErrorView:null<br>
     *         (���) this.defaultStatusCode:not null<br>
     *                (200)<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) ModelAndView:null<br>
     *         (��ԕω�) response:�X�e�[�^�X�R�[�h���ݒ肳���B<br>
     *         
     * <br>
     * �n���h�����O�����O���P�L�q���Ă��邪�A����������O�ƈ�v���Ȃ��p�^�[���BNull��Ԃ��B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testResolveException07() throws Exception {
        // �O����
        // Delegator����
        SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01 exceptionResolveDelegatorImpl = new SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01();
        exceptionResolveDelegatorImpl.viewName = "oxmException";
        
        SimpleMappingExceptionResolverEx exceptionResolver = 
            new SimpleMappingExceptionResolverEx();
        UTUtil.setPrivateField(exceptionResolver, "mappedHandlers", null);
        LinkedHashMap<String, ExceptionResolveDelegator> exceptionResolveDelegatorMap = new LinkedHashMap<String, ExceptionResolveDelegator>();
        UTUtil.setPrivateField(
                exceptionResolver, "exceptionResolveDelegatorMap", exceptionResolveDelegatorMap);
        exceptionResolveDelegatorMap.put("jp.terasoluna.fw.oxm.exception.OXMappingException",exceptionResolveDelegatorImpl);
        String defaultErrorViewName = null;
        UTUtil.setPrivateField(exceptionResolver, "defaultErrorView", defaultErrorViewName);
        int statusCode = 200;
        UTUtil.setPrivateField(
                exceptionResolver, "defaultStatusCode", statusCode);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        Exception exception = new BindException(new Object(), "name");
        
        // �e�X�g���{
        ModelAndView mv = exceptionResolver.resolveException(
                request, response, null, exception);

        // ����
        assertNull(mv);
    }
    
    /**
     * testResolveException03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,C
     * <br><br>
     * ���͒l�F(����) request:�g�p���Ȃ�<br>
     *         (����) response:not null<br>
     *         (����) handler:null<br>
     *         (����) ex:Exception�C���X�^���X<br>
     *         (���) this.mappedHandlers:null<br>
     *         (���) exceptionResolveDelegatorMap:�v�f���P����Map<br>
     *                Exception= ExceptionResolveDelegator�C���X�^���X{ viewName= null}<br>
     *         (���) getDepth�i�j:�u�O�v<br>
     *         (���) this.defaultErrorView:"default"<br>
     *         (���) this.defaultStatusCode:not null<br>
     *                (200)<br>
     *         (���) getModelAndView():getModelAndView()���\�b�h���Ԃ��r���[<br>
     *         (���) exceptionResolveDelegator.setHeader():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     *         (���) exceptionResolveDelegator.addObjectToModel():�Ăяo���ꂽ���Ƃ��m�F����B<br>
     *         (���) ethis.outputErrorLogHandledException:false<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) ModelAndView:ModelAndView<br>
     *                  �EviewName�Fdefault<br>
     *                  �Emodel�iMap�^�j<br>
     *                  exception=������ex<br>
     *         (��ԕω�) response:�X�e�[�^�X�R�[�h���ݒ肳���B<br>
     *         
     * <br>
     * �n���h�����O������O�̏����G���[���O�o�͂��Ȃ��ݒ�ɂ����ꍇ�A
     * �G���[���O���o�͂���Ȃ����Ƃ��m�F����p�^�[���B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testResolveException08() throws Exception {
        // �O����
        // Delegator����
        SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01 exceptionResolveDelegatorImpl = new SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01();
        exceptionResolveDelegatorImpl.viewName = null;
        
        SimpleMappingExceptionResolverEx exceptionResolver = 
            new SimpleMappingExceptionResolverEx();
        UTUtil.setPrivateField(exceptionResolver, "mappedHandlers", null);
        LinkedHashMap<String, Object> exceptionResolveDelegatorMap = new LinkedHashMap<String, Object>();
        UTUtil.setPrivateField(
                exceptionResolver, "exceptionResolveDelegatorMap", exceptionResolveDelegatorMap);
        exceptionResolveDelegatorMap.put("java.lang.Exception",exceptionResolveDelegatorImpl);
        String defaultErrorViewName = "default";
        UTUtil.setPrivateField(exceptionResolver, "defaultErrorView", defaultErrorViewName);
        int statusCode = 200;
        UTUtil.setPrivateField(
                exceptionResolver, "defaultStatusCode", statusCode);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        Exception exception = new Exception();
        
        exceptionResolver.setOutputErrorLogHandledException(false);
        
        // �e�X�g���{
        ModelAndView mv = exceptionResolver.resolveException(
                request, response, null, exception);

        // ����
        assertEquals(defaultErrorViewName, mv.getViewName());
        assertSame(exception, mv.getModel().get("exception"));
        
        // �X�^�u�̌Ăяo���m�F
        assertTrue(exceptionResolveDelegatorImpl.isAddObjectToModel);
        assertTrue(exceptionResolveDelegatorImpl.isSetHeader);

        // ���O �m�F
        assertFalse(LogUTUtil.checkError("Handled the following exception.",
                exception));
    }

    /**
     * testResolveException09()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA,C
     * <br><br>
     * ���͒l�F(����) request:�g�p���Ȃ�<br>
     *         (����) response:not null<br>
     *         (����) handler:null<br>
     *         (����) ex:Exception�C���X�^���X<br>
     *         (���) this.mappedHandlers:null<br>
     *         (���) exceptionResolveDelegatorMap:�v�f���P����Map<br>
     *                Exception= ExceptionResolveDelegator�C���X�^���X{ viewName= null}<br>
     *         (���) getDepth�i�j:�u�O�v<br>
     *         (���) this.defaultErrorView:null<br>
     *         (���) this.defaultStatusCode:not null<br>
     *                (200)<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) ModelAndView:null<br>
     *         
     * <br>
     * �n���h�����O�����O���P�L�q���Ă���A����O�ɑΉ�����ExceptionResolveDelegator��viewName������null���ݒ肳��Ă���p�^�[���̃e�X�g�B�f�t�H���g�G���[�r���[���ݒ肳��Ă��Ȃ��̂�Null��Ԃ��B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testResolveException09() throws Exception {
        // �O����
        // Delegator����
        SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01 exceptionResolveDelegatorImpl = new SimpleMappingExceptionResolverEx_ExceptionResolveDelegatorImplStub01();
        exceptionResolveDelegatorImpl.viewName = null;
        
        SimpleMappingExceptionResolverEx exceptionResolver = 
            new SimpleMappingExceptionResolverEx();
        UTUtil.setPrivateField(exceptionResolver, "mappedHandlers", null);
        LinkedHashMap<String, Object> exceptionResolveDelegatorMap = new LinkedHashMap<String, Object>();
        UTUtil.setPrivateField(
                exceptionResolver, "exceptionResolveDelegatorMap", exceptionResolveDelegatorMap);
        exceptionResolveDelegatorMap.put("java.lang.Exception",exceptionResolveDelegatorImpl);
        String defaultErrorViewName = null;
        UTUtil.setPrivateField(exceptionResolver, "defaultErrorView", defaultErrorViewName);
        int statusCode = 200;
        UTUtil.setPrivateField(
                exceptionResolver, "defaultStatusCode", statusCode);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        Exception exception = new Exception();
        
        // �e�X�g���{
        ModelAndView mv = exceptionResolver.resolveException(
                request, response, null, exception);

        // ����
        assertNull(mv);
    }
    
    /**
     * ���t���N�V�������g�p���ăt�B�[���h�ɃA�N�Z�X���郁�\�b�h�B
     * @param cls �A�N�Z�X����N���X�B
     * @param fieldName �A�N�Z�X����t�B�[���h���B
     * @param target �A�N�Z�X����C���X�^���X�B
     * @return �t�B�[���h�l�B
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private Object getField(Class cls, String fieldName, Object target) 
            throws NoSuchFieldException, IllegalAccessException {
        Field field = cls.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(target);
    }
    
    /**
     * ���t���N�V�������g�p���ăt�B�[���h�ɃA�N�Z�X���郁�\�b�h�B
     * @param cls �A�N�Z�X����N���X�B
     * @param fieldName �A�N�Z�X����t�B�[���h���B
     * @param target �A�N�Z�X����C���X�^���X�B
     * @param value �ݒ肷��l�B
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private void setField(
            Class cls, String fieldName, Object target, Object value) 
            throws NoSuchFieldException, IllegalAccessException {
        Field field = cls.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

}
