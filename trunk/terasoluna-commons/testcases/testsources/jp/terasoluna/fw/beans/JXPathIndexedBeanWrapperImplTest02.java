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

import jp.terasoluna.utlib.LogUTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.beans.JXPathIndexedBeanWrapperImpl} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * JavaBean�̔z��E�R���N�V�����^�����ɃA�N�Z�X�ł���N���X�B<br>
 * �O������F<br>
 * �utoXPath�v���\�b�h�̈����ɂ�null�͓���Ȃ��B<br>
 * �uextractIncrementIndex(String)�v���\�b�h�̈����ɂ�null�͓���Ȃ��B<br>
 * �uextractIncrementIndex(String,int)�v���\�b�h�̑�P�����ɂ�null�͓���Ȃ��B<br>
 * �uextractIncrementIndex(String,int)�v���\�b�h�̑�Q�����ɂ�1��-1��������Ȃ��B<br>
 * �uextractIndex�v���\�b�h�̈����ɂ�null�͓���Ȃ��B<br>
 * �uescapeMapProperty�v���\�b�h�̈����ɂ�null�͓���Ȃ��B<br>
 * �uextractMapPropertyName�v���\�b�h�̈����ɂ�null�͓���Ȃ��B<br>
 * �uextractMapPropertyKey�v���\�b�h�̈����ɂ�null�͓���Ȃ��B<br>
 * �uisMapProperty�v���\�b�h�̈����ɂ�null�͓���Ȃ��B
 * <p>
 * 
 * @see jp.terasoluna.fw.beans.JXPathIndexedBeanWrapperImpl
 */
public class JXPathIndexedBeanWrapperImplTest02 extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(JXPathIndexedBeanWrapperImplTest02.class);
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
    public JXPathIndexedBeanWrapperImplTest02(String name) {
        super(name);
    }

    /**
     * testToXPath01()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) property:""�i�󕶎��j<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    �@���b�Z�[�W�FProperty name is null or blank.<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    Property name is null or blank.<br>
     *         
     * <br>
     * �y�󕶎��̃e�X�g�z<br>
     * ������property���󕶎��̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToXPath01() throws Exception {
        // �O����
        Object object = new Object();
        JXPathIndexedBeanWrapperImplStub03 test = 
            new JXPathIndexedBeanWrapperImplStub03(object);
        
        // ���͒l�ݒ�
        String property = "";
        
        // �e�X�g���{
        try{
            test.toXPath(property);
            fail();
        }catch(IllegalArgumentException e){
            // ����
            assertEquals("Property name is null or blank.", 
                        e.getMessage());
            assertTrue(LogUTUtil.checkError(
                            "Property name is null or blank."));
            
        }
    }

    /**
     * testToXPath02()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) property:"."<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    �@���b�Z�[�W�FProperty name is null or blank.<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    Property name is null or blank.<br>
     *         
     * <br>
     * �y�����̃e�X�g�z<br>
     * ������property��"."�݂̂̃p�^�[���̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToXPath02() throws Exception {
        // �O����
        Object object = new Object();
        JXPathIndexedBeanWrapperImplStub03 test = 
            new JXPathIndexedBeanWrapperImplStub03(object);
        
        // ���͒l�ݒ�
        String property = ".";
        
        // �e�X�g���{
        try{
            test.toXPath(property);
            fail();
        }catch(IllegalArgumentException e){
            // ����
            assertEquals("Property name is null or blank.", 
                        e.getMessage());
            assertTrue(LogUTUtil.checkError(
                            "Property name is null or blank."));
            
        }
    }

    /**
     * testToXPath03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) property:"abc"<br>
     *         (���) isMapProperty:false��Ԃ��B<br>
     *         (���) extractAttributeName:property+"Attribute"��Ԃ��B<br>
     *         (���) extractIncrementIndex:"[3]"��Ԃ��B<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:/abcAttribute[3]<br>
     *         (��ԕω�) isMapProperty:����"abc"���n���ꂽ���Ƃ��m�F����B<br>
     *         (��ԕω�) extractAttributeName:�����\�b�h�͈���(property="abc")�Ŗ߂�lproperty+"Attribute"�Ƃ���B<br>
     *         (��ԕω�) extractIncrementIndex:����"abc"���n���ꂽ���Ƃ��m�F����B<br>
     *         
     * <br>
     * �yJavaBean or Primitive�̃e�X�g�z<br>
     * for��1����s�̏ꍇ�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToXPath03() throws Exception {
        // �O����
        Object object = new Object();
        JXPathIndexedBeanWrapperImplStub03 test = 
            new JXPathIndexedBeanWrapperImplStub03(object);
        
        // ���͒l�ݒ�
        String property = "abc";
        
        // �O��i�X�^�u�j�ݒ�
        test.isMapPropertyResult = false;
        test.extractIncrementIndexResult = "[3]";
        
        // �e�X�g���{
        String result = test.toXPath(property);
        // ����
        assertEquals("abc",test.isMapPropertyParam1);
        assertEquals("abc",test.extractIncrementIndexParam1);
        assertEquals("/abcAttribute[3]",result);
            
    }

    /**
     * testToXPath04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) property:"abc.def.ghi"<br>
     *         (���) isMapProperty:false��Ԃ��B<br>
     *         (���) extractAttributeName:property+"Attribute"��Ԃ��B<br>
     *         (���) extractIncrementIndex:""��Ԃ��B<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:/abcAttribute/defAttribute/ghiAttribute<br>
     *         (��ԕω�) isMapProperty:����"ghi"���n���ꂽ���Ƃ��m�F����B<br>
     *         (��ԕω�) extractAttributeName:�����\�b�h�͈���(property="abc")�Ŗ߂�lproperty+"Attribute"�Ƃ���B<br>
     *         (��ԕω�) extractIncrementIndex:����"ghi"���n���ꂽ���Ƃ��m�F����B<br>
     *         
     * <br>
     * �yJavaBean or Primitive�̃e�X�g�z<br>
     * for����������s�̏ꍇ�B<br>
     * �i������property��"."���܂ޕ�����̃p�^�[���j
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToXPath04() throws Exception {
        // �O����
        Object object = new Object();
        JXPathIndexedBeanWrapperImplStub03 test = 
            new JXPathIndexedBeanWrapperImplStub03(object);
        
        // ���͒l�ݒ�
        String property = "abc.def.ghi";
        
        // �O��i�X�^�u�j�ݒ�
        test.isMapPropertyResult = false;
        test.extractIncrementIndexResult = "";
        
        // �e�X�g���{
        String result = test.toXPath(property);
        
        // ����
        assertEquals("ghi",test.isMapPropertyParam1);
        assertEquals("ghi",test.extractIncrementIndexParam1);
        assertEquals("/abcAttribute/defAttribute/ghiAttribute",result);
    }

    /**
     * testToXPath05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) property:"abc"<br>
     *         (���) isMapProperty:true��Ԃ��B<br>
     *         (���) escapeMapProperty:property+"[@name='key']"��Ԃ��B<br>
     *         (���) extractIncrementIndex:"[3]"��Ԃ��B<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:/abc[@name='key'][3]<br>
     *         (��ԕω�) isMapProperty:����"abc"���n���ꂽ���Ƃ��m�F����B<br>
     *         (��ԕω�) escapeMapProperty:�����\�b�h�͈���(property="abc")�Ŗ߂�lproperty+"[@name='key']"�Ƃ���B<br>
     *         (��ԕω�) extractIncrementIndex:����"abc"���n���ꂽ���Ƃ��m�F����B<br>
     *         
     * <br>
     * �yMap�̃e�X�g�z<br>
     * for��1����s�̏ꍇ�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToXPath05() throws Exception {
        // �O����
        Object object = new Object();
        JXPathIndexedBeanWrapperImplStub03 test = 
            new JXPathIndexedBeanWrapperImplStub03(object);
        
        // ���͒l�ݒ�
        String property = "abc";
        
        // �O��i�X�^�u�j�ݒ�
        test.isMapPropertyResult = true;
        test.extractIncrementIndexResult = "[3]";
        
        // �e�X�g���{
        String result = test.toXPath(property);
        
        // ����
        assertEquals("abc",test.isMapPropertyParam1);
        assertEquals("abc",test.extractIncrementIndexParam1);
        assertEquals("/abc[@name='key'][3]",result);
    }

    /**
     * testToXPath06()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) property:"abc.def..ghi"<br>
     *         (���) isMapProperty:true��Ԃ��B<br>
     *         (���) escapeMapProperty:property+"[@name='key']"��Ԃ��B<br>
     *         (���) extractIncrementIndex:""��Ԃ��B<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:/abc[@name='key']/def[@name='key']/ghi[@name='key']<br>
     *         (��ԕω�) isMapProperty:����"ghi"���n���ꂽ���Ƃ��m�F����B<br>
     *         (��ԕω�) escapeMapProperty:�����\�b�h�͈���(property="abc")�Ŗ߂�lproperty+"[@name='key']"�Ƃ���B<br>
     *         (��ԕω�) extractIncrementIndex:����"ghi"���n���ꂽ���Ƃ��m�F����B<br>
     *         
     * <br>
     * �yMap�̃e�X�g�z<br>
     * for����������s�̏ꍇ�B<br>
     * �i������property��".."���܂ޕ�����̃p�^�[���j
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToXPath06() throws Exception {
        // �O����
        Object object = new Object();
        JXPathIndexedBeanWrapperImplStub03 test = 
            new JXPathIndexedBeanWrapperImplStub03(object);
        
        // ���͒l�ݒ�
        String property = "abc.def..ghi";
        
        // �O��i�X�^�u�j�ݒ�
        test.isMapPropertyResult = true;
        test.extractIncrementIndexResult = "";
        
        // �e�X�g���{
        String result = test.toXPath(property);
        
        // ����
        assertEquals("ghi",test.isMapPropertyParam1);
        assertEquals("ghi",test.extractIncrementIndexParam1);
        assertEquals("/abc[@name='key']/def[@name='key']/ghi[@name='key']",
                result);
    }

    /**
     * testToXPath07()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) property:"abc.def..ghi"<br>
     *         (���) isMapProperty:false��Ԃ��B<br>
     *         (���) extractAttributeName:property+"Attribute"��Ԃ��B<br>
     *         (���) extractIncrementIndex:""��Ԃ��B<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:/abcAttribute/defAttribute/ghiAttribute<br>
     *         (��ԕω�) isMapProperty:����"ghi"���n���ꂽ���Ƃ��m�F����B<br>
     *         (��ԕω�) extractAttributeName:�����\�b�h�͈���(property="abc")�Ŗ߂�lproperty+"Attribute"�Ƃ���B<br>
     *         (��ԕω�) extractIncrementIndex:����"ghi"���n���ꂽ���Ƃ��m�F����B<br>
     *         
     * <br>
     * �y�s�������̃e�X�g�z<br>
     * ������property��".."�i"."2��A���j���܂ޕ�����̃p�^�[���̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToXPath07() throws Exception {
        // �O����
        Object object = new Object();
        JXPathIndexedBeanWrapperImplStub03 test = 
            new JXPathIndexedBeanWrapperImplStub03(object);
        
        // ���͒l�ݒ�
        String property = "abc.def..ghi";
        
        // �O��i�X�^�u�j�ݒ�
        test.isMapPropertyResult = false;
        test.extractIncrementIndexResult = "";
        
        // �e�X�g���{
        String result = test.toXPath(property);
        
        // ����
        assertEquals("ghi",test.isMapPropertyParam1);
        assertEquals("ghi",test.extractIncrementIndexParam1);
        assertEquals("/abcAttribute/defAttribute/ghiAttribute",result);
    }

    /**
     * testExtractIncrementIndexString01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) property:"abc"<br>
     *         (���) extractIncremantIndex(String,int):�uproperty+":"+increment�v��Ԃ��B<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:"abc:1"<br>
     *         (��ԕω�) extractIncremantIndex(String,int):�����\�b�h�͈���(property="abc",increment=1)�Ŗ߂�lproperty+":"+increment�Ƃ���B<br>
     *         
     * <br>
     * �yextractIncremantIndex(String,int)�@�@�Ăяo���̃e�X�g�z
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExtractIncrementIndexString01() throws Exception {
        // �O����
        Object object = new Object();
        JXPathIndexedBeanWrapperImplStub02 test = 
            new JXPathIndexedBeanWrapperImplStub02(object);
        
        // ���͒l�ݒ�
        String property = "abc";
        
        // �e�X�g���{
        String result = test.extractIncrementIndex(property);
        
        // ����
        assertEquals("abc:1",result);
    }

    /**
     * testExtractIncremantIndexStringint01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) property:"abc"<br>
     *         (����) increment:1<br>
     *         (���) extractIndex:""��Ԃ��B<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:""<br>
     *         (��ԕω�) extractIndex:����"abc"���n���ꂽ���Ƃ��m�F����B<br>
     *         
     * <br>
     * �y������̃e�X�g�z<br>
     * increment��1��<br>
     * extractIndex��""��Ԃ��p�^�[���̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExtractIncremantIndexStringint01() throws Exception {
        // �O����
        Object object = new Object();
        JXPathIndexedBeanWrapperImplStub01 test = 
            new JXPathIndexedBeanWrapperImplStub01(object);
        
        // ���͒l
        String property = "abc";
        int increment = 1;
        
        // �O��i�X�^�u�j
        test.extractIndexResult = "";
        
        // �e�X�g���{
        String result = test.extractIncrementIndex(property,increment);
        
        // ����
        assertEquals("abc",test.extractIndexParam1);
        assertEquals("",result);
    }

    /**
     * testExtractIncremantIndexStringint02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) property:"abc"<br>
     *         (����) increment:1<br>
     *         (���) extractIndex:"2"��Ԃ��B<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"[3]"<br>
     *         (��ԕω�) extractIndex:����"abc"���n���ꂽ���Ƃ��m�F����B<br>
     *         
     * <br>
     * �y������̃e�X�g�z<br>
     * increment��1��<br>
     * extractIndex��"2"��Ԃ��p�^�[���̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExtractIncremantIndexStringint02() throws Exception {
        // �O����
        Object object = new Object();
        JXPathIndexedBeanWrapperImplStub01 test = 
            new JXPathIndexedBeanWrapperImplStub01(object);
        
        // ���͒l
        String property = "abc";
        int increment = 1;
        
        // �O��i�X�^�u�j
        test.extractIndexResult = "2";
        
        // �e�X�g���{
        String result = test.extractIncrementIndex(property,increment);
        
        // ����
        assertEquals("abc",test.extractIndexParam1);
        assertEquals("[3]",result);
    }

    /**
     * testExtractIncremantIndexStringint03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) property:"abc"<br>
     *         (����) increment:-1<br>
     *         (���) extractIndex:"2"��Ԃ��B<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"[1]"<br>
     *         (��ԕω�) extractIndex:����"abc"���n���ꂽ���Ƃ��m�F����B<br>
     *         
     * <br>
     * �y������̃e�X�g�z<br>
     * increment��-1��<br>
     * extractIndex��"2"��Ԃ��p�^�[���̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExtractIncremantIndexStringint03() throws Exception {
        // �O����
        Object object = new Object();
        JXPathIndexedBeanWrapperImplStub01 test = 
            new JXPathIndexedBeanWrapperImplStub01(object);
        
        // ���͒l
        String property = "abc";
        int increment = -1;
        
        // �O��i�X�^�u�j
        test.extractIndexResult = "2";
        
        // �e�X�g���{
        String result = test.extractIncrementIndex(property,increment);
        
        // ����
        assertEquals("abc",test.extractIndexParam1);
        assertEquals("[1]",result);
    }

    /**
     * testExtractIndex01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) property:""[�󕶎�]<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:""<br>
     *         
     * <br>
     * �y�󕶎��̃e�X�g�z<br>
     * ������property���󕶎��̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExtractIndex01() throws Exception {
        // �O����
        Object object = new Object();
        JXPathIndexedBeanWrapperImpl test = 
            new JXPathIndexedBeanWrapperImpl(object);
        
        // ���͒l
        String property = "";
                
        // �e�X�g���{
        String result = test.extractIndex(property);
        
        // ����
        assertEquals("",result);
    }

    /**
     * testExtractIndex02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) property:"abc"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:""<br>
     *         
     * <br>
     * �y�����̃e�X�g�z<br>
     * ������property��"["�܂���"]"�������Ă��Ȃ��p�^�[���̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExtractIndex02() throws Exception {
        // �O����
        Object object = new Object();
        JXPathIndexedBeanWrapperImpl test = 
            new JXPathIndexedBeanWrapperImpl(object);
        
        // ���͒l
        String property = "abc";
                
        // �e�X�g���{
        String result = test.extractIndex(property);
        
        // ����
        assertEquals("",result);
    }

    /**
     * testExtractIndex03()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) property:"["<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    �@���b�Z�[�W�FCannot get Index. Invalid property name. '['<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    Cannot get Index. Invalid property name. '['<br>
     *         
     * <br>
     * �y�s�������̃e�X�g�z<br>
     * ������property��"["�̃p�^�[���̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExtractIndex03() throws Exception {
        // �O����
        Object object = new Object();
        JXPathIndexedBeanWrapperImpl test = 
            new JXPathIndexedBeanWrapperImpl(object);
        
        // ���͒l
        String property = "[";
        
        // �e�X�g���{
        try{
            test.extractIndex(property);
            fail();
        }catch(IllegalArgumentException e){
            // ����
            assertEquals("Cannot get Index. Invalid property name. '['", 
                        e.getMessage());
            assertTrue(LogUTUtil.checkError(
                            "Cannot get Index. Invalid property name. '['"));
            
        }
    }

    /**
     * testExtractIndex04()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) property:"]"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    �@���b�Z�[�W�FCannot get Index. Invalid property name. ']'<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    Cannot get Index. Invalid property name. ']'<br>
     *         
     * <br>
     * �y�s�������̃e�X�g�z<br>
     * ������property��"]"�̃p�^�[���̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExtractIndex04() throws Exception {
        // �O����
        Object object = new Object();
        JXPathIndexedBeanWrapperImpl test = 
            new JXPathIndexedBeanWrapperImpl(object);
        
        // ���͒l
        String property = "]";
        
        // �e�X�g���{
        try{
            test.extractIndex(property);
            fail();
        }catch(IllegalArgumentException e){
            // ����
            assertEquals("Cannot get Index. Invalid property name. ']'", 
                        e.getMessage());
            assertTrue(LogUTUtil.checkError(
                            "Cannot get Index. Invalid property name. ']'"));
            
        }
    }

    /**
     * testExtractIndex05()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) property:"]["<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    �@���b�Z�[�W�FCannot get Index. Invalid property name. ']['<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    Cannot get Index. Invalid property name. ']['<br>
     *         
     * <br>
     * �y�s�������̃e�X�g�z<br>
     * ������property��"]["�̃p�^�[���̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExtractIndex05() throws Exception {
        // �O����
        Object object = new Object();
        JXPathIndexedBeanWrapperImpl test = 
            new JXPathIndexedBeanWrapperImpl(object);
        
        // ���͒l
        String property = "][";
        
        // �e�X�g���{
        try{
            test.extractIndex(property);
            fail();
        }catch(IllegalArgumentException e){
            // ����
            assertEquals("Cannot get Index. Invalid property name. ']['", 
                        e.getMessage());
            assertTrue(LogUTUtil.checkError(
                            "Cannot get Index. Invalid property name. ']['"));
            
        }
    }

    /**
     * testExtractIndex06()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) property:"[]"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:""<br>
     *         
     * <br>
     * �y�����̃e�X�g�z<br>
     * ������property��"[]"�̃p�^�[���̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExtractIndex06() throws Exception {
        // �O����
        Object object = new Object();
        JXPathIndexedBeanWrapperImpl test = 
            new JXPathIndexedBeanWrapperImpl(object);
        
        // ���͒l
        String property = "[]";
                
        // �e�X�g���{
        String result = test.extractIndex(property);
        
        // ����
        assertEquals("",result);
    }

    /**
     * testExtractIndex07()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) property:"ab[c]d[3]"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"3"<br>
     *         
     * <br>
     * �y�����̃e�X�g�z<br>
     * ������property��"[]"��������Ă���p�^�[���̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExtractIndex07() throws Exception {
        // �O����
        Object object = new Object();
        JXPathIndexedBeanWrapperImpl test = 
            new JXPathIndexedBeanWrapperImpl(object);
        
        // ���͒l
        String property = "ab[c]d[3]";
                
        // �e�X�g���{
        String result = test.extractIndex(property);
        
        // ����
        assertEquals("3",result);
    }

    /**
     * testEscapeMapProperty01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) property:"abc"<br>
     *         (���) extractMapPropertyName:property+"Name"��Ԃ��B<br>
     *         (���) extractMapPropertyKey:property+"Key"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:abcName/abcKey<br>
     *         (��ԕω�) extractMapPropertyName:�����\�b�h�͈���(property="abc")�Ŗ߂�lproperty+"Name"�Ƃ���B<br>
     *         (��ԕω�) extractMapPropertyKey:�����\�b�h�͈���(property="abc")�Ŗ߂�lproperty+"Key"�Ƃ���B<br>
     *         
     * <br>
     * �y���\�b�h�̌Ăяo���ƌ��ʂ̐��`�̃e�X�g�z
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testEscapeMapProperty01() throws Exception {
        // �O����
        Object object = new Object();
        JXPathIndexedBeanWrapperImplStub01 test = 
            new JXPathIndexedBeanWrapperImplStub01(object);
        
        // ���͒l�ݒ�
        String property = "abc";
        
        // �e�X�g���{
        String result = test.escapeMapProperty(property);
        
        // ����
        assertEquals("abcName/abcKey",result);
    }

    /**
     * testExtractMapPropertyName01()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) property:""�i�󕶎��j<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    �@���b�Z�[�W�FCannot get Map attribute. Invalid property name. ''<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    Cannot get Map attribute. Invalid property name. ''<br>
     *         
     * <br>
     * �y�󕶎��̃e�X�g�z<br>
     * ������property���󕶎��̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExtractMapPropertyName01() throws Exception {
        // �O����
        Object object = new Object();
        JXPathIndexedBeanWrapperImpl test = 
            new JXPathIndexedBeanWrapperImpl(object);
        
        // ���͒l
        String property = "";
        
        // �e�X�g���{
        try{
            test.extractMapPropertyName(property);
            fail();
        }catch(IllegalArgumentException e){
            // ����
            assertEquals(
                    "Cannot get Map attribute. Invalid property name. ''", 
                        e.getMessage());
            assertTrue(LogUTUtil.checkError(
                    "Cannot get Map attribute. Invalid property name. ''"));
            
        }
    }

    /**
     * testExtractMapPropertyName02()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) property:"abc"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    �@���b�Z�[�W�FCannot get Map attribute. Invalid property name. 'abc'<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    Cannot get Map attribute. Invalid property name. 'abc'<br>
     *         
     * <br>
     * �y�s�������̃e�X�g�z<br>
     * ������property��"("�܂���")"�������Ă��Ȃ��p�^�[���̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExtractMapPropertyName02() throws Exception {
        // �O����
        Object object = new Object();
        JXPathIndexedBeanWrapperImpl test = 
            new JXPathIndexedBeanWrapperImpl(object);
        
        // ���͒l
        String property = "abc";
        
        // �e�X�g���{
        try{
            test.extractMapPropertyName(property);
            fail();
        }catch(IllegalArgumentException e){
            // ����
            assertEquals("Cannot get Map attribute. Invalid property name. 'abc'", 
                        e.getMessage());
            assertTrue(LogUTUtil.checkError(
                            "Cannot get Map attribute. Invalid property name. 'abc'"));
            
        }
    }

    /**
     * testExtractMapPropertyName03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) property:"("<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:""<br>
     *         
     * <br>
     * �y�����̃e�X�g�z<br>
     * ������property��"("�̃p�^�[���̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExtractMapPropertyName03() throws Exception {
        // �O����
        Object object = new Object();
        JXPathIndexedBeanWrapperImpl test = 
            new JXPathIndexedBeanWrapperImpl(object);
        
        // ���͒l�ݒ�
        String property = "(";
        
        // �e�X�g���{
        String result = test.extractMapPropertyName(property);
        
        // ����
        assertEquals("",result);
    }

    /**
     * testExtractMapPropertyName04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) property:"ab()"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"ab"<br>
     *         
     * <br>
     * �y�����̃e�X�g�z<br>
     * ������property��"()"�̑O�ɂ��������������Ă���p�^�[���̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExtractMapPropertyName04() throws Exception {
        // �O����
        Object object = new Object();
        JXPathIndexedBeanWrapperImpl test = 
            new JXPathIndexedBeanWrapperImpl(object);
        
        // ���͒l�ݒ�
        String property = "ab()";
        
        // �e�X�g���{
        String result = test.extractMapPropertyName(property);
        
        // ����
        assertEquals("ab",result);
    }

    /**
     * testExtractMapPropertyKey01()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) property:""�i�󕶎��j<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    �@���b�Z�[�W�FCannot get Map key. Invalid property name. ''<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    Cannot get Map key. Invalid property name. ''<br>
     *         
     * <br>
     * �y�󕶎��̃e�X�g�z<br>
     * ������property���󕶎��̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExtractMapPropertyKey01() throws Exception {
        // �O����
        Object object = new Object();
        JXPathIndexedBeanWrapperImpl test = 
            new JXPathIndexedBeanWrapperImpl(object);
        
        // ���͒l
        String property = "";
        
        // �e�X�g���{
        try{
            test.extractMapPropertyKey(property);
            fail();
        }catch(IllegalArgumentException e){
            // ����
            assertEquals("Cannot get Map key. Invalid property name. ''", 
                        e.getMessage());
            assertTrue(LogUTUtil.checkError(
                            "Cannot get Map key. Invalid property name. ''"));
            
        }
    }

    /**
     * testExtractMapPropertyKey02()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) property:"abc"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    �@���b�Z�[�W�FCannot get Map key. Invalid property name. 'abc'<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    Cannot get Map key. Invalid property name. 'abc'<br>
     *         
     * <br>
     * �y�s�������̃e�X�g�z<br>
     * ������property��"("�܂���")"�������Ă��Ȃ��p�^�[���̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExtractMapPropertyKey02() throws Exception {
        // �O����
        Object object = new Object();
        JXPathIndexedBeanWrapperImpl test = 
            new JXPathIndexedBeanWrapperImpl(object);
        
        // ���͒l
        String property = "abc";
        
        // �e�X�g���{
        try{
            test.extractMapPropertyKey(property);
            fail();
        }catch(IllegalArgumentException e){
            // ����
            assertEquals("Cannot get Map key. Invalid property name. 'abc'", 
                        e.getMessage());
            assertTrue(LogUTUtil.checkError(
                            "Cannot get Map key. Invalid property name. 'abc'"));
            
        }
    }

    /**
     * testExtractMapPropertyKey03()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) property:"("<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    �@���b�Z�[�W�FCannot get Map key. Invalid property name. '('<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    Cannot get Map key. Invalid property name. '('<br>
     *         
     * <br>
     * �y�s�������̃e�X�g�z<br>
     * ������property��"("�̃p�^�[���̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExtractMapPropertyKey03() throws Exception {
        // �O����
        Object object = new Object();
        JXPathIndexedBeanWrapperImpl test = 
            new JXPathIndexedBeanWrapperImpl(object);
        
        // ���͒l
        String property = "(";
        
        // �e�X�g���{
        try{
            test.extractMapPropertyKey(property);
            fail();
        }catch(IllegalArgumentException e){
            // ����
            assertEquals("Cannot get Map key. Invalid property name. '('", 
                        e.getMessage());
            assertTrue(LogUTUtil.checkError(
                            "Cannot get Map key. Invalid property name. '('"));
            
        }
    }

    /**
     * testExtractMapPropertyKey04()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) property:")"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    �@���b�Z�[�W�FCannot get Map key. Invalid property name. ')'<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    Cannot get Map key. Invalid property name. ')'<br>
     *         
     * <br>
     * �y�s�������̃e�X�g�z<br>
     * ������property��")"�̃p�^�[���̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExtractMapPropertyKey04() throws Exception {
        // �O����
        Object object = new Object();
        JXPathIndexedBeanWrapperImpl test = 
            new JXPathIndexedBeanWrapperImpl(object);
        
        // ���͒l
        String property = ")";
        
        // �e�X�g���{
        try{
            test.extractMapPropertyKey(property);
            fail();
        }catch(IllegalArgumentException e){
            // ����
            assertEquals("Cannot get Map key. Invalid property name. ')'", 
                        e.getMessage());
            assertTrue(LogUTUtil.checkError(
                            "Cannot get Map key. Invalid property name. ')'"));
            
        }
    }

    /**
     * testExtractMapPropertyKey05()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) property:")("<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    �@���b�Z�[�W�FCannot get Map key. Invalid property name. ')('<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    Cannot get Map key. Invalid property name. ')('<br>
     *         
     * <br>
     * �y�s�������̃e�X�g�z<br>
     * ������property��")("�̃p�^�[���̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExtractMapPropertyKey05() throws Exception {
        // �O����
        Object object = new Object();
        JXPathIndexedBeanWrapperImpl test = 
            new JXPathIndexedBeanWrapperImpl(object);
        
        // ���͒l
        String property = ")(";
        
        // �e�X�g���{
        try{
            test.extractMapPropertyKey(property);
            fail();
        }catch(IllegalArgumentException e){
            // ����
            assertEquals("Cannot get Map key. Invalid property name. ')('", 
                        e.getMessage());
            assertTrue(LogUTUtil.checkError(
                            "Cannot get Map key. Invalid property name. ')('"));
            
        }
    }

    /**
     * testExtractMapPropertyKey06()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) property:"()"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:""<br>
     *         
     * <br>
     * �y�����̃e�X�g�z<br>
     * ������property��"()"�̃p�^�[���̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExtractMapPropertyKey06() throws Exception {
        // �O����
        Object object = new Object();
        JXPathIndexedBeanWrapperImpl test = 
            new JXPathIndexedBeanWrapperImpl(object);
        
        // ���͒l�ݒ�
        String property = "()";
        
        // �e�X�g���{
        String result = test.extractMapPropertyKey(property);
        
        // ����
        assertEquals("",result);
    }

    /**
     * testExtractMapPropertyKey07()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) property:"ab(cd)e"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"cd"<br>
     *         
     * <br>
     * �y�����̃e�X�g�z<br>
     * ������property��"("��")"�̑O��ɕ����������Ă���p�^�[���̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExtractMapPropertyKey07() throws Exception {
        // �O����
        Object object = new Object();
        JXPathIndexedBeanWrapperImpl test = 
            new JXPathIndexedBeanWrapperImpl(object);
        
        // ���͒l�ݒ�
        String property = "ab(cd)e";
        
        // �e�X�g���{
        String result = test.extractMapPropertyKey(property);
        
        // ����
        assertEquals("cd",result);
    }

    /**
     * testIsMapProperty01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) property:""�i�󕶎��j<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         
     * <br>
     * �y�󕶎��̃e�X�g�z<br>
     * ������property���󕶎��̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsMapProperty01() throws Exception {
        // �O����
        Object object = new Object();
        JXPathIndexedBeanWrapperImplStub01 test = 
            new JXPathIndexedBeanWrapperImplStub01(object);
        
        // ���͒l�ݒ�
        String property = "";
        
        // �e�X�g���{
        boolean result = test.isMapProperty(property);
        
        // ����
        assertFalse(result);
    }

    /**
     * testIsMapProperty02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) property:"abc"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         
     * <br>
     * �y�����̃e�X�g�z<br>
     * ������property��"("�܂���")"�������Ă��Ȃ��p�^�[���̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsMapProperty02() throws Exception {
        // �O����
        Object object = new Object();
        JXPathIndexedBeanWrapperImplStub01 test = 
            new JXPathIndexedBeanWrapperImplStub01(object);
        
        // ���͒l�ݒ�
        String property = "abc";
        
        // �e�X�g���{
        boolean result = test.isMapProperty(property);
        
        // ����
        assertFalse(result);
    }

    /**
     * testIsMapProperty03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) property:"("<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         
     * <br>
     * �y�����̃e�X�g�z<br>
     * ������property��"("�̃p�^�[���̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsMapProperty03() throws Exception {
        // �O����
        Object object = new Object();
        JXPathIndexedBeanWrapperImplStub01 test = 
            new JXPathIndexedBeanWrapperImplStub01(object);
        
        // ���͒l�ݒ�
        String property = "(";
        
        // �e�X�g���{
        boolean result = test.isMapProperty(property);
        
        // ����
        assertFalse(result);
    }

    /**
     * testIsMapProperty04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) property:")"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         
     * <br>
     * �y�����̃e�X�g�z<br>
     * ������property��")"�̃p�^�[���̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsMapProperty04() throws Exception {
        // �O����
        Object object = new Object();
        JXPathIndexedBeanWrapperImplStub01 test = 
            new JXPathIndexedBeanWrapperImplStub01(object);
        
        // ���͒l�ݒ�
        String property = ")";
        
        // �e�X�g���{
        boolean result = test.isMapProperty(property);
        
        // ����
        assertFalse(result);
    }

    /**
     * testIsMapProperty05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) property:")("<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         
     * <br>
     * �y�����̃e�X�g�z<br>
     * ������property��")("�̃p�^�[���̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsMapProperty05() throws Exception {
        // �O����
        Object object = new Object();
        JXPathIndexedBeanWrapperImplStub01 test = 
            new JXPathIndexedBeanWrapperImplStub01(object);
        
        // ���͒l�ݒ�
        String property = ")(";
        
        // �e�X�g���{
        boolean result = test.isMapProperty(property);
        
        // ����
        assertTrue(result);
    }

    /**
     * testIsMapProperty06()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) property:"()"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         
     * <br>
     * �y�����̃e�X�g�z<br>
     * ������property��"()"�̃p�^�[���̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsMapProperty06() throws Exception {
        // �O����
        Object object = new Object();
        JXPathIndexedBeanWrapperImplStub01 test = 
            new JXPathIndexedBeanWrapperImplStub01(object);
        
        // ���͒l�ݒ�
        String property = "()";
        
        // �e�X�g���{
        boolean result = test.isMapProperty(property);
        
        // ����
        assertTrue(result);
    }

}
