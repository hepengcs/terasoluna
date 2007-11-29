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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.WrapDynaBean;
import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.JXPathException;

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.beans.JXPathIndexedBeanWrapperImpl} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * JavaBean�̔z��E�R���N�V�����^�����ɃA�N�Z�X�ł���N���X�B<br>
 * �O������F
 * <p>
 * 
 * @see jp.terasoluna.fw.beans.JXPathIndexedBeanWrapperImpl
 */
public class JXPathIndexedBeanWrapperImplTest01 extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(JXPathIndexedBeanWrapperImplTest01.class);
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
    public JXPathIndexedBeanWrapperImplTest01(String name) {
        super(name);
    }

    /**
     * testJXPathIndexedBeanWrapperImpl01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) obj:not null<br>
     *         (���) this.context:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) this.context:�����Őݒ肳�ꂽ�l�B<br>
     *         
     * <br>
     * �^�[�Q�b�g�ƂȂ�JavaBean��context�����ɐݒ肷��R���X�g���N�^�̃e�X�g�B����P�[�X�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testJXPathIndexedBeanWrapperImpl01() throws Exception {
        // �O����
        Object obj = new Object();

        // �e�X�g���{
        JXPathIndexedBeanWrapperImpl bw 
            = new JXPathIndexedBeanWrapperImpl(obj);

        // ����
        JXPathContext context = 
            (JXPathContext) UTUtil.getPrivateField(bw, "context");
        assertSame(obj, context.getContextBean());
    }

    /**
     * testJXPathIndexedBeanWrapperImpl02()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) obj:null<br>
     *         (���) this.context:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException("TargetBean is null!")<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    TargetBean is null!<br>
     *         
     * <br>
     * ������JavaBean��Null�̏ꍇ�̃e�X�g�B��O�𓊂���B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testJXPathIndexedBeanWrapperImpl02() throws Exception {
        // �O����
        try {
            new JXPathIndexedBeanWrapperImpl(null);
            fail();
        } catch (IllegalArgumentException e) {
            // ����
            assertEquals("TargetBean is null!", e.getMessage());
            assertTrue(LogUTUtil.checkError("TargetBean is null!"));
        }
    }

    /**
     * testGetIndexedPropertyValuesJavaBean01()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) propertyName:null<br>
     *         (���) this.context:not null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException("PropertyName is empty!")<br>
     *         (��ԕω�) ���O:���O���x���F�G���[PropertyName is empty!<br>
     *         
     * <br>
     * �����̃v���p�e�B����Null�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedPropertyValuesJavaBean01() throws Exception {
        // �O����
        JXPathIndexedBeanWrapperImpl bw 
            = new JXPathIndexedBeanWrapperImpl(new Object());
        
        // �e�X�g���{
        try {
            bw.getIndexedPropertyValues(null);
            fail();
        } catch (IllegalArgumentException e) {
            // ����
            assertEquals("PropertyName is empty!", e.getMessage());
            assertTrue(LogUTUtil.checkError("PropertyName is empty!"));
        }
    }

    /**
     * testGetIndexedPropertyValuesJavaBean02()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) propertyName:""�i�󕶎��j<br>
     *         (���) this.context:not null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException("PropertyName is empty!")<br>
     *         (��ԕω�) ���O:���O���x���F�G���[PropertyName is empty!<br>
     *         
     * <br>
     * �����̃v���p�e�B�����󕶎��̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedPropertyValuesJavaBean02() throws Exception {
        // �O����
        JXPathIndexedBeanWrapperImpl bw 
        = new JXPathIndexedBeanWrapperImpl(new Object());
    
        // �e�X�g���{
        try {
            bw.getIndexedPropertyValues("");
            fail();
        } catch (IllegalArgumentException e) {
            // ����
            assertEquals("PropertyName is empty!", e.getMessage());
            assertTrue(LogUTUtil.checkError("PropertyName is empty!"));
        }
    }

    /**
     * testGetIndexedPropertyValuesJavaBean03()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) propertyName:"."(�h�b�g�P�j<br>
     *         (���) this.context:not null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    �@���b�Z�[�W�FProperty name is null or blank.<br>
     *                    �@������O�FJXPathException<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    Property name is null or blank.<br>
     *         
     * <br>
     * �s���ȃv���p�e�B���������ꂽ�ꍇ�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedPropertyValuesJavaBean03() throws Exception {
        // �O����
        JXPathIndexedBeanWrapperImpl bw 
        = new JXPathIndexedBeanWrapperImpl(new Object());
    
        // �e�X�g���{
        try {
            bw.getIndexedPropertyValues(".");
            fail();
        } catch (IllegalArgumentException e) {
            // ����
            assertEquals("Property name is null or blank.", e.getMessage());
            assertTrue(LogUTUtil.checkError("Property name is null or blank."));
        }
    }

    /**
     * testGetIndexedPropertyValuesJavaBean04()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) propertyName:"/"<br>
     *         (���) this.context:not null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    �@���b�Z�[�W�FInvalid character has found within property name. 
     *                    '/' Cannot use [ / " ' ]<br>
     *                    �@������O�FJXPathException<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    Invalid character has found within property name. 
     *                    '/' Cannot use [ / " ' ]<br>
     *         
     * <br>
     * �v���p�e�B����/�i�X���b�V���j�������Ă���o�^�[��
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedPropertyValuesJavaBean04() throws Exception {
        // �O����
        JXPathIndexedBeanWrapperImpl bw 
        = new JXPathIndexedBeanWrapperImpl(new Object());
    
        // �e�X�g���{
        try {
            bw.getIndexedPropertyValues("/");
            fail();
        } catch (IllegalArgumentException e) {
            // ����
            String expect = "Invalid character has found within property name. "
                + "'/' Cannot use [ / \" ' ]";
            assertEquals(expect, e.getMessage());
            assertTrue(LogUTUtil.checkError(expect));
        }
    }

    /**
     * testGetIndexedPropertyValuesJavaBean05()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) propertyName:"["<br>
     *         (���) this.context:not null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    �@���b�Z�[�W�FCannot get Index. Invalid property name. '['<br>
     *                    �@������O�FJXPathException<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    Cannot get Index. Invalid property name. '['<br>
     *         
     * <br>
     * �v���p�e�B����[�i�z��̋L���j�������Ă���o�^�[��
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedPropertyValuesJavaBean05() throws Exception {
        // �O����
        JXPathIndexedBeanWrapperImpl bw 
        = new JXPathIndexedBeanWrapperImpl(new Object());
    
        // �e�X�g���{
        try {
            bw.getIndexedPropertyValues("[");
            fail();
        } catch (IllegalArgumentException e) {
            // ����
            String expect = "Cannot get Index. Invalid property name. '['";
            assertEquals(expect, e.getMessage());
            assertTrue(LogUTUtil.checkError(expect));
        }
    }

    /**
     * testGetIndexedPropertyValuesJavaBean06()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) propertyName:"[a]"<br>
     *         (���) this.context:not null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    �@���b�Z�[�W�FInvalid character has found within property name. '[a]' Cannot use [ [] ]<br>
     *                    �@������O�FJXPathException<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    Invalid character has found within property name. '[a]' Cannot use [ [] ]<br>
     *         
     * <br>
     * �v���p�e�B����]�i�z��̋L���j�������Ă���o�^�[��
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedPropertyValuesJavaBean06() throws Exception {
        // �O����
        JXPathIndexedBeanWrapperImpl bw 
        = new JXPathIndexedBeanWrapperImpl(new Object());
    
        // �e�X�g���{
        try {
            bw.getIndexedPropertyValues("[a]");
            fail();
        } catch (IllegalArgumentException e) {
            // ����
            String expect = "Invalid character has found within property name. '[a]' Cannot use [ [] ]";
            assertEquals(expect, e.getMessage());
            assertTrue(LogUTUtil.checkError(expect));
        }
    }

    /**
     * testGetIndexedPropertyValuesJavaBean07()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) propertyName:"property"<br>
     *         (���) this.context:JavaBean�o<br>
     *                  (�v���p�e�B��=�l)<br>
     *                  property=null<br>
     *                �p<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Map<String, Object>:Map<String, Object><br>
     *                  property=null<br>
     *         
     * <br>
     * �yJavaBean�̃e�X�g�z<br>
     * null�l�̒l���擾����e�X�g�B(Object�^�j
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedPropertyValuesJavaBean07() throws Exception {
        // �O����
        JXPathIndexedBeanWrapperImpl_JavaBeanStub01 bean 
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub01();
        bean.setProperty(null);
        
        JXPathIndexedBeanWrapperImpl bw 
            = new JXPathIndexedBeanWrapperImpl(bean);

        // �e�X�g���{ 
        Map<String, Object> result = bw.getIndexedPropertyValues("property"); 

        // ����
        assertEquals(1, result.size());
        assertNull(result.get("property"));
        assertNull(PropertyUtils.getProperty(bean, "property"));
        assertNull(BeanUtils.getProperty(bean, "property"));
    }
    
    /**
     * testGetIndexedPropertyValuesJavaBean07()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) propertyName:"property"<br>
     *         (���) this.context:JavaBean�o<br>
     *                  (�v���p�e�B��=�l)<br>
     *                  property=null<br>
     *                �p<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Map<String, Object>:Map<String, Object><br>
     *                  property=null<br>
     *         
     * <br>
     * �yJavaBean�̃e�X�g�z<br>
     * null�l�̒l���擾����e�X�g�B(String�^�j
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedPropertyValuesJavaBean07_2() throws Exception {
        // �O����
        JXPathIndexedBeanWrapperImpl_JavaBeanStub01 bean 
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub01();
        bean.setProperty2(null);
        
        JXPathIndexedBeanWrapperImpl bw 
            = new JXPathIndexedBeanWrapperImpl(bean);

        // �e�X�g���{ 
        Map<String, Object> result = bw.getIndexedPropertyValues("property2"); 

        // ����
        assertEquals(1, result.size());
        assertTrue(result.containsKey("property2"));
        assertNull(result.get("property2"));
        assertNull(PropertyUtils.getProperty(bean, "property2"));
        assertNull(BeanUtils.getProperty(bean, "property2"));
    }
    
    /**
     * testGetIndexedPropertyValuesJavaBean07()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) propertyName:"property"<br>
     *         (���) this.context:JavaBean�o<br>
     *                  (�v���p�e�B��=�l)<br>
     *                  property=null<br>
     *                �p<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Map<String, Object>:Map<String, Object><br>
     *                  property=null<br>
     *         
     * <br>
     * �yJavaBean�̃e�X�g�z<br>
     * null�l�̒l���擾����e�X�g�B(Date�^�j
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedPropertyValuesJavaBean07_3() throws Exception {
        // �O����
        JXPathIndexedBeanWrapperImpl_JavaBeanStub01 bean 
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub01();
        bean.setProperty3(null);
        
        JXPathIndexedBeanWrapperImpl bw 
            = new JXPathIndexedBeanWrapperImpl(bean);

        // �e�X�g���{ 
        Map<String, Object> result = bw.getIndexedPropertyValues("property3"); 

        // ����
        assertEquals(1, result.size());
        assertTrue(result.containsKey("property3"));
        assertNull(result.get("property3"));
        assertNull(PropertyUtils.getProperty(bean, "property3"));
        assertNull(BeanUtils.getProperty(bean, "property3"));
    }
    
    /**
     * testGetIndexedPropertyValuesJavaBean07()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) propertyName:"property"<br>
     *         (���) this.context:JavaBean�o<br>
     *                  (�v���p�e�B��=�l)<br>
     *                  property=null<br>
     *                �p<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Map<String, Object>:Map<String, Object><br>
     *                  property=null<br>
     *         
     * <br>
     * �yJavaBean�̃e�X�g�z<br>
     * null�l�̒l���擾����e�X�g�B(List�^�j
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedPropertyValuesJavaBean07_4() throws Exception {
        // �O����
        JXPathIndexedBeanWrapperImpl_JavaBeanStub01 bean 
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub01();
        bean.setProperty4(null);
        
        JXPathIndexedBeanWrapperImpl bw 
            = new JXPathIndexedBeanWrapperImpl(bean);

        // �e�X�g���{ 
        Map<String, Object> result = bw.getIndexedPropertyValues("property4"); 

        // ����
        assertEquals(1, result.size());
        assertTrue(result.containsKey("property4"));
        assertNull(result.get("property4"));
        assertNull(PropertyUtils.getProperty(bean, "property4"));
        assertNull(BeanUtils.getProperty(bean, "property4"));
    }
    
    /**
     * testGetIndexedPropertyValuesJavaBean07()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) propertyName:"property"<br>
     *         (���) this.context:JavaBean�o<br>
     *                  (�v���p�e�B��=�l)<br>
     *                  property=null<br>
     *                �p<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Map<String, Object>:Map<String, Object><br>
     *                  property=null<br>
     *         
     * <br>
     * �yJavaBean�̃e�X�g�z<br>
     * null�l�̒l���擾����e�X�g�B(int[]�^�j
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedPropertyValuesJavaBean07_5() throws Exception {
        // �O����
        JXPathIndexedBeanWrapperImpl_JavaBeanStub01 bean 
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub01();
        bean.setProperty5(null);
        
        JXPathIndexedBeanWrapperImpl bw 
            = new JXPathIndexedBeanWrapperImpl(bean);

        // �e�X�g���{ 
        Map<String, Object> result = bw.getIndexedPropertyValues("property5"); 

        // ����
        assertEquals(1, result.size());
        assertTrue(result.containsKey("property5"));
        assertNull(result.get("property5"));
        assertNull(PropertyUtils.getProperty(bean, "property5"));
        assertNull(BeanUtils.getProperty(bean, "property5"));
    }
    
    /**
     * testGetIndexedPropertyValuesJavaBean07()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) propertyName:"property"<br>
     *         (���) this.context:JavaBean�o<br>
     *                  (�v���p�e�B��=�l)<br>
     *                  property=new ArrayList()<br>
     *                �p<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Map<String, Object>:Map<String, Object><br>
     *                  <br>
     *         
     * <br>
     * �yJavaBean�̃e�X�g�z<br>
     * ��̃��X�g�l���擾����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedPropertyValuesJavaBean07_6() throws Exception {
        // �O����
        JXPathIndexedBeanWrapperImpl_JavaBeanStub01 bean 
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub01();
        bean.setProperty4(new ArrayList());
        
        JXPathIndexedBeanWrapperImpl bw 
            = new JXPathIndexedBeanWrapperImpl(bean);

        // �e�X�g���{ 
        Map<String, Object> result = bw.getIndexedPropertyValues("property4"); 

        // ����
        assertEquals(0, result.size());
    }
    
    /**
     * testGetIndexedPropertyValuesJavaBean07()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) propertyName:"property"<br>
     *         (���) this.context:JavaBean�o<br>
     *                  (�v���p�e�B��=�l)<br>
     *                  property=new int[]<br>
     *                �p<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Map<String, Object>:Map<String, Object><br>
     *                  <br>
     *         
     * <br>
     * �yJavaBean�̃e�X�g�z<br>
     * ���int�z����擾����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedPropertyValuesJavaBean07_7() throws Exception {
        // �O����
        JXPathIndexedBeanWrapperImpl_JavaBeanStub01 bean 
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub01();
        bean.setProperty5(new int[]{});
        
        JXPathIndexedBeanWrapperImpl bw 
            = new JXPathIndexedBeanWrapperImpl(bean);

        // �e�X�g���{ 
        Map<String, Object> result = bw.getIndexedPropertyValues("property5"); 

        // ����
        assertEquals(0, result.size());
    }
    
    /**
     * testGetIndexedPropertyValuesJavaBean07()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) propertyName:"property"<br>
     *         (���) this.context:JavaBean�o<br>
     *                  (�v���p�e�B��=�l)<br>
     *                  property=null<br>
     *                �p<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Map<String, Object>:Map<String, Object><br>
     *                  property=null<br>
     *         
     * <br>
     * �yJavaBean�̃e�X�g�z<br>
     * null�l�̒l���擾����e�X�g�B(List�^�j
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedPropertyValuesJavaBean07_8() throws Exception {
        // �O����
        JXPathIndexedBeanWrapperImpl_JavaBeanStub01 bean 
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub01();
        bean.setObjectArray(null);
        
        JXPathIndexedBeanWrapperImpl bw 
            = new JXPathIndexedBeanWrapperImpl(bean);

        // �e�X�g���{ 
        Map<String, Object> result = bw.getIndexedPropertyValues("objectArray"); 

        // ����
        assertEquals(1, result.size());
        assertTrue(result.containsKey("objectArray"));
        assertNull(result.get("objectArray"));
        assertNull(PropertyUtils.getProperty(bean, "objectArray"));
        assertNull(BeanUtils.getProperty(bean, "objectArray"));
    }

    /**
     * testGetIndexedPropertyValuesJavaBean08()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) propertyName:"foo"<br>
     *         (���) this.context:JavaBean�o<br>
     *                  �i�v���p�e�B��=�l�j<br>
     *                �@foo="test"<br>
     *                �p<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Map<String, Object>:Map<String, Object><br>
     *                  foo="test"<br>
     *         
     * <br>
     * �yJavaBean�̃e�X�g�z<br>
     * String�^�̒l���擾����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedPropertyValuesJavaBean08() throws Exception {
        // �O����
        JXPathIndexedBeanWrapperImpl_JavaBeanStub01 bean 
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub01();
        bean.setProperty("test");
        
        JXPathIndexedBeanWrapperImpl bw 
            = new JXPathIndexedBeanWrapperImpl(bean);
    
        // �e�X�g���{
        Map<String, Object> map = bw.getIndexedPropertyValues("property"); 
    
        // ����
        assertEquals(1, map.size());
        assertTrue(map.containsKey("property"));
        assertEquals("test", map.get("property"));
        assertEquals("test", PropertyUtils.getProperty(bean, "property"));
        assertEquals("test", BeanUtils.getProperty(bean, "property"));
    }

    /**
     * testGetIndexedPropertyValuesJavaBean09()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) propertyName:"foo"<br>
     *         (���) this.context:JavaBean�o<br>
     *                  �i�v���p�e�B��=�l�j<br>
     *                �@foo=false<br>
     *                �p<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Map<String, Object>:Map<String, Object><br>
     *                  foo=false<br>
     *         
     * <br>
     * �yJavaBean�̃e�X�g�z<br>
     * boolean�^�̒l���擾����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedPropertyValuesJavaBean09() throws Exception {
        // �O����
        JXPathIndexedBeanWrapperImpl_JavaBeanStub01 bean 
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub01();
        bean.setBoolProperty(false);
        
        JXPathIndexedBeanWrapperImpl bw 
            = new JXPathIndexedBeanWrapperImpl(bean);
    
        // �e�X�g���{
        Map<String, Object> result = bw.getIndexedPropertyValues("boolProperty"); 
    
        // ����
        assertEquals(1, result.size());
        assertTrue(result.containsKey("boolProperty"));
        assertFalse((Boolean) result.get("boolProperty"));
        assertEquals("false", PropertyUtils.getProperty(bean, "boolProperty").toString());
        assertEquals("false", BeanUtils.getProperty(bean, "boolProperty"));
    }

    /**
     * testGetIndexedPropertyValuesJavaBean10()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) propertyName:"foo.bar.hoge"<br>
     *         (���) this.context:JavaBean�o<br>
     *                  �i�v���p�e�B��=�l�j<br>
     *                �@foo.bar.hoge="test"<br>
     *                �p<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Map<String, Object>:Map<String, Object><br>
     *                  foo.bar.hoge="test"<br>
     *         
     * <br>
     * �yJavaBean�̃e�X�g�z<br>
     * �l�X�g�����l���擾����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedPropertyValuesJavaBean10() throws Exception {
        // �O����
        JXPathIndexedBeanWrapperImpl_JavaBeanStub03 bean
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub03();
        // foo
        JXPathIndexedBeanWrapperImpl_JavaBeanStub03.Foo foo
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub03.Foo();
    
        // bar
        JXPathIndexedBeanWrapperImpl_JavaBeanStub03.Bar bar
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub03.Bar();
        // hoge
        bar.setHoge("test");
    
        // foo.bar.hoge
        foo.setBar(bar);
        bean.setFoo(foo);
        
        JXPathIndexedBeanWrapperImpl bw =
            new JXPathIndexedBeanWrapperImpl(bean);
    
        // �e�X�g���{
        Map<String, Object> result = bw.getIndexedPropertyValues("foo.bar.hoge");
    
        // ����
        assertEquals(1, result.size());
        assertTrue(result.containsKey("foo.bar.hoge"));
        assertEquals("test", result.get("foo.bar.hoge"));
        assertEquals("test", PropertyUtils.getProperty(bean, "foo.bar.hoge"));
        assertEquals("test", BeanUtils.getProperty(bean, "foo.bar.hoge"));
    }

    /**
     * testGetIndexedPropertyValuesJavaBean11()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) propertyName:"foo.bar.hoge"<br>
     *         (���) this.context:JavaBean�o<br>
     *                  �i�v���p�e�B��=�l�j<br>
     *                �@foo[0].bar.hoge="test0"<br>
     *                  foo[1].bar.hoge="test1"<br>
     *                  foo[2].bar.hoge="test2"<br>
     *                �p<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Map<String, Object>:Map<String, Object><br>
     *                  foo[0].bar.hoge="test0"<br>
     *                  foo[1].bar.hoge="test1"<br>
     *                  foo[2].bar.hoge="test2"<br>
     *         
     * <br>
     * �yJavaBean�̃e�X�g�z<br>
     * �I�u�W�F�N�g�z��̒l���擾����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedPropertyValuesJavaBean11() throws Exception {
        // �O����
        JXPathIndexedBeanWrapperImpl_JavaBeanStub04 bean =
            new JXPathIndexedBeanWrapperImpl_JavaBeanStub04();

        // foos[0]
        JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Foo foo1 =
            new JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Foo();

        // foos[1]
        JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Foo foo2
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Foo();

        // foos[2]
        JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Foo foo3
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Foo();
        
        // foos
        bean.setFoos(
                new JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Foo[]{foo1, foo2, foo3});

        // foos[0].bar
        JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Bar bar1
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Bar();
        foo1.setBar(bar1);

        // foos[1].bar
        JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Bar bar2
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Bar();
        foo2.setBar(bar2);

        // foos[2].bar
        JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Bar bar3
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Bar();
        foo3.setBar(bar3);

        // foos[0].bar.hoge="test0"
        bar1.setHoge("test0");
        // foos[1].bar.hoge="test1"
        bar2.setHoge("test1");
        // foos[2].bar.hoge="test2"
        bar3.setHoge("test2");
        
        JXPathIndexedBeanWrapperImpl bw =
            new JXPathIndexedBeanWrapperImpl(bean);

        // �e�X�g���{
        Map<String, Object> result = bw.getIndexedPropertyValues("foos.bar.hoge");
    
        // ����
        assertEquals(3, result.size());
        assertEquals("test0", result.get("foos[0].bar.hoge"));
        assertEquals("test1", result.get("foos[1].bar.hoge"));
        assertEquals("test2", result.get("foos[2].bar.hoge"));
        assertEquals("test0", PropertyUtils.getProperty(bean, "foos[0].bar.hoge"));
        assertEquals("test0", BeanUtils.getProperty(bean, "foos[0].bar.hoge"));
        assertEquals("test1", PropertyUtils.getProperty(bean, "foos[1].bar.hoge"));
        assertEquals("test1", BeanUtils.getProperty(bean, "foos[1].bar.hoge"));
        assertEquals("test2", PropertyUtils.getProperty(bean, "foos[2].bar.hoge"));
        assertEquals("test2", BeanUtils.getProperty(bean, "foos[2].bar.hoge"));
    }

    /**
     * testGetIndexedPropertyValuesJavaBean12()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) propertyName:"foo.bar.hoge"<br>
     *         (���) this.context:JavaBean�o<br>
     *                  �i�v���p�e�B��=�l�j<br>
     *                �@foo[0].bar.hoge="test0"<br>
     *                  foo[1].bar.hoge="test1"<br>
     *                  foo[2].bar.hoge="test2"<br>
     *                �p<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Map<String, Object>:Map<String, Object><br>
     *                  foo[0].bar.hoge="test0"<br>
     *                  foo[1].bar.hoge="test1"<br>
     *                  foo[2].bar.hoge="test2"<br>
     *         
     * <br>
     * �yJavaBean�̃e�X�g�z<br>
     * List�l���擾����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedPropertyValuesJavaBean12() throws Exception {
        // �O����
        JXPathIndexedBeanWrapperImpl_JavaBeanStub04 bean =
            new JXPathIndexedBeanWrapperImpl_JavaBeanStub04();

        // foo
        List<JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Foo> fooList =
            new ArrayList<JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Foo>();

        // foo[0],foo[1],foo[2]
        // foo[0]
        JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Foo foo1
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Foo();
        fooList.add(foo1);

        // foo[1]
        JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Foo foo2
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Foo();
        fooList.add(foo2);

        // foo[2]
        JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Foo foo3
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Foo();
        fooList.add(foo3);

        // foo[0].bar
        JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Bar bar1
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Bar();
        foo1.setBar(bar1);

        // foo[1].bar
        JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Bar bar2
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Bar();
        foo2.setBar(bar2);

        // foo[2].bar
        JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Bar bar3
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Bar();
        foo3.setBar(bar3);

        // foo[0].bar.hoge="test0"
        bar1.setHoge("test0");
        // foo[1].bar.hoge="test1"
        bar2.setHoge("test1");
        // foo[2].bar.hoge="test2"
        bar3.setHoge("test2");

        bean.setFoo(fooList);
        
        JXPathIndexedBeanWrapperImpl bw =
            new JXPathIndexedBeanWrapperImpl(bean);

        // �e�X�g���{
        Map<String, Object> result = bw.getIndexedPropertyValues("foo.bar.hoge");
    
        // ����
        assertEquals(3, result.size());
        assertEquals("test0", result.get("foo[0].bar.hoge"));
        assertEquals("test1", result.get("foo[1].bar.hoge"));
        assertEquals("test2", result.get("foo[2].bar.hoge"));
        assertEquals("test0", PropertyUtils.getProperty(bean, "foo[0].bar.hoge"));
        assertEquals("test0", BeanUtils.getProperty(bean, "foo[0].bar.hoge"));
        assertEquals("test1", PropertyUtils.getProperty(bean, "foo[1].bar.hoge"));
        assertEquals("test1", BeanUtils.getProperty(bean, "foo[1].bar.hoge"));
        assertEquals("test2", PropertyUtils.getProperty(bean, "foo[2].bar.hoge"));
        assertEquals("test2", BeanUtils.getProperty(bean, "foo[2].bar.hoge"));
    }

    /**
     * testGetIndexedPropertyValuesJavaBean13()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) propertyName:"foo.bar.hoge"<br>
     *         (���) this.context:JavaBean�o<br>
     *                  �i�v���p�e�B��=�l�j<br>
     *                �@foo[0].bar.hoge=null<br>
     *                  foo[1].bar.hoge="test1"<br>
     *                  foo[2].bar.hoge="test2"<br>
     *                �p<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Map<String, Object>:Map<String, Object><br>
     *                  foo[0].bar.hoge=null<br>
     *                  foo[1].bar.hoge="test1"<br>
     *                  foo[2].bar.hoge="test2"<br>
     *         
     * <br>
     * �yJavaBean�̃e�X�g�z<br>
     * �z��{null������̒l���擾����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedPropertyValuesJavaBean13() throws Exception {
        // �O����
        JXPathIndexedBeanWrapperImpl_JavaBeanStub04 bean =
            new JXPathIndexedBeanWrapperImpl_JavaBeanStub04();

        // foo
        List<JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Foo> fooList =
            new ArrayList<JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Foo>();

        // foo[0],foo[1],foo[2]
        // foo[0]
        JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Foo foo1
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Foo();
        fooList.add(foo1);

        // foo[1]
        JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Foo foo2
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Foo();
        fooList.add(foo2);

        // foo[2]
        JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Foo foo3
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Foo();
        fooList.add(foo3);

        // foo[0].bar
        JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Bar bar1
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Bar();
        foo1.setBar(bar1);

        // foo[1].bar
        JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Bar bar2
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Bar();
        foo2.setBar(bar2);

        // foo[2].bar
        JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Bar bar3
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Bar();
        foo3.setBar(bar3);

        // foo[0].bar.hoge=null
        bar1.setHoge(null);
        // foo[1].bar.hoge="test1"
        bar2.setHoge("test1");
        // foo[2].bar.hoge="test2"
        bar3.setHoge("test2");

        bean.setFoo(fooList);
        
        JXPathIndexedBeanWrapperImpl bw =
            new JXPathIndexedBeanWrapperImpl(bean);

        // �e�X�g���{
        Map<String, Object> result = bw.getIndexedPropertyValues("foo.bar.hoge");
    
        // ����
        assertEquals(3, result.size());
        assertNull(result.get("foo[0].bar.hoge"));
        assertEquals("test1", result.get("foo[1].bar.hoge"));
        assertEquals("test2", result.get("foo[2].bar.hoge"));
        assertNull(PropertyUtils.getProperty(bean, "foo[0].bar.hoge"));
        assertNull(BeanUtils.getProperty(bean, "foo[0].bar.hoge"));
        assertEquals("test1", PropertyUtils.getProperty(bean, "foo[1].bar.hoge"));
        assertEquals("test1", BeanUtils.getProperty(bean, "foo[1].bar.hoge"));
        assertEquals("test2", PropertyUtils.getProperty(bean, "foo[2].bar.hoge"));
        assertEquals("test2", BeanUtils.getProperty(bean, "foo[2].bar.hoge"));
    }

    /**
     * testGetIndexedPropertyValuesJavaBean14()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) propertyName:"foo.bar.hoge"<br>
     *         (���) this.context:JavaBean�o<br>
     *                  �i�v���p�e�B��=�l�j<br>
     *                �@foo.bar[0].hoge[0]="test0"<br>
     *                  foo.bar[1].hoge[0]="test1"<br>
     *                  foo.bar[2].hoge[0]="test2"<br>
     *                  foo.bar[2].hoge[1]="test3"<br>
     *                  foo.bar[2].hoge[2]="test4"<br>
     *                �p<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Map<String, Object>:Map<String, Object><br>
     *                  �@foo.bar[0].hoge[0]="test0"<br>
     *                    foo.bar[1].hoge[0]="test1"<br>
     *                    foo.bar[2].hoge[0]="test2"<br>
     *                    foo.bar[2].hoge[1]="test3"<br>
     *                    foo.bar[2].hoge[2]="test4"<br>
     *         
     * <br>
     * �yJavaBean�̃e�X�g�z<br>
     * �l�X�g�{�z��̒l���擾����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedPropertyValuesJavaBean14() throws Exception {
        // �O����
        JXPathIndexedBeanWrapperImpl_JavaBeanStub05 bean
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub05();
    
        // foo
        JXPathIndexedBeanWrapperImpl_JavaBeanStub05.Foo foo
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub05.Foo();
        bean.setFoo(foo);
    
        // foo.bar[0]
        JXPathIndexedBeanWrapperImpl_JavaBeanStub05.Bar bar0
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub05.Bar();
        // foo.bar[1]
        JXPathIndexedBeanWrapperImpl_JavaBeanStub05.Bar bar1
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub05.Bar();
        // foo.bar[2]
        JXPathIndexedBeanWrapperImpl_JavaBeanStub05.Bar bar2
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub05.Bar();
        // foo.bar[]
        List<JXPathIndexedBeanWrapperImpl_JavaBeanStub05.Bar> barList
            = new ArrayList<JXPathIndexedBeanWrapperImpl_JavaBeanStub05.Bar>();
        // foo.bar[0]
        barList.add(bar0);
        // foo.bar[1]
        barList.add(bar1);
        // foo.bar[2]
        barList.add(bar2);
    
        //foo.bar
        foo.setBar(barList);
    
        // foo.bar[0].hoge[0]="test0"
        List<String> hoge0 = new ArrayList<String>();
        hoge0.add("test0");
        bar0.setHoge(hoge0);
    
        // foo.bar[1].hoge[0]="test1"
        List<String> hoge1 = new ArrayList<String>();
        hoge1.add("test1");
        bar1.setHoge(hoge1);
    
        // foo.bar[2].hoge[0]="test2"
        // foo.bar[2].hoge[1]="test3"
        // foo.bar[2].hoge[2]="test4"
        List<String> hoge2 = new ArrayList<String>();
        hoge2.add("test2");
        hoge2.add("test3");
        hoge2.add("test4");
        bar2.setHoge(hoge2);

        JXPathIndexedBeanWrapperImpl bw =
            new JXPathIndexedBeanWrapperImpl(bean);

        // �e�X�g���{
        Map<String, Object> result = bw.getIndexedPropertyValues("foo.bar.hoge");
    
        // ����
        assertEquals(5, result.size());
        assertEquals("test0", result.get("foo.bar[0].hoge[0]"));
        assertEquals("test1", result.get("foo.bar[1].hoge[0]"));
        assertEquals("test2", result.get("foo.bar[2].hoge[0]"));
        assertEquals("test3", result.get("foo.bar[2].hoge[1]"));
        assertEquals("test4", result.get("foo.bar[2].hoge[2]"));
        assertEquals("test0", PropertyUtils.getProperty(bean, "foo.bar[0].hoge[0]"));
        assertEquals("test0", BeanUtils.getProperty(bean, "foo.bar[0].hoge[0]"));
        assertEquals("test1", PropertyUtils.getProperty(bean, "foo.bar[1].hoge[0]"));
        assertEquals("test1", BeanUtils.getProperty(bean, "foo.bar[1].hoge[0]"));
        assertEquals("test2", PropertyUtils.getProperty(bean, "foo.bar[2].hoge[0]"));
        assertEquals("test2", BeanUtils.getProperty(bean, "foo.bar[2].hoge[0]"));
        assertEquals("test3", PropertyUtils.getProperty(bean, "foo.bar[2].hoge[1]"));
        assertEquals("test3", BeanUtils.getProperty(bean, "foo.bar[2].hoge[1]"));
        assertEquals("test4", PropertyUtils.getProperty(bean, "foo.bar[2].hoge[2]"));
        assertEquals("test4", BeanUtils.getProperty(bean, "foo.bar[2].hoge[2]"));
    }

    /**
     * testGetIndexedPropertyValuesJavaBean15()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) propertyName:"foo.bar.hoge"<br>
     *         (���) this.context:JavaBean�o<br>
     *                  �i�v���p�e�B��=�l�j<br>
     *                �@foo.bar[0].hoge[0]="test0"<br>
     *                  foo.bar[1]=null<br>
     *                  foo.bar[2].hoge[0]="test2"<br>
     *                  foo.bar[2].hoge[1]="test3"<br>
     *                  foo.bar[2].hoge[2]="test4"<br>
     *                �p<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Map<String, Object>:Map<String, Object><br>
     *                  �@foo.bar[0].hoge[0]="test0"<br>
     *                    foo.bar[2].hoge[0]="test2"<br>
     *                    foo.bar[2].hoge[1]="test3"<br>
     *                    foo.bar[2].hoge[2]="test4"<br>
     *         
     * <br>
     * �yJavaBean�̃e�X�g�z<br>
     * �l�X�g�{�z��{null��������p�^�[���̒l���擾����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedPropertyValuesJavaBean15() throws Exception {
        // �O����
        JXPathIndexedBeanWrapperImpl_JavaBeanStub05 bean
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub05();
    
        // foo
        JXPathIndexedBeanWrapperImpl_JavaBeanStub05.Foo foo
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub05.Foo();
        bean.setFoo(foo);
    
        // foo.bar[0]
        JXPathIndexedBeanWrapperImpl_JavaBeanStub05.Bar bar0
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub05.Bar();
        // foo.bar[1]
        JXPathIndexedBeanWrapperImpl_JavaBeanStub05.Bar bar1 = null;
        // foo.bar[2]
        JXPathIndexedBeanWrapperImpl_JavaBeanStub05.Bar bar2
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub05.Bar();
        // foo.bar[]
        List<JXPathIndexedBeanWrapperImpl_JavaBeanStub05.Bar> barList
            = new ArrayList<JXPathIndexedBeanWrapperImpl_JavaBeanStub05.Bar>();
        // foo.bar[0]
        barList.add(bar0);
        // foo.bar[1]
        barList.add(bar1);
        // foo.bar[2]
        barList.add(bar2);
    
        //foo.bar
        foo.setBar(barList);
    
        // foo.bar[0].hoge[0]="test0"
        List<String> hoge0 = new ArrayList<String>();
        hoge0.add("test0");
        bar0.setHoge(hoge0);
    
        // foo.bar[1]=null
    
        // foo.bar[2].hoge[0]="test2"
        // foo.bar[2].hoge[1]="test3"
        // foo.bar[2].hoge[2]="test4"
        List<String> hoge2 = new ArrayList<String>();
        hoge2.add("test2");
        hoge2.add("test3");
        hoge2.add("test4");
        bar2.setHoge(hoge2);
    
        JXPathIndexedBeanWrapperImpl bw =
            new JXPathIndexedBeanWrapperImpl(bean);
    
        // �e�X�g���{
        Map<String, Object> result = bw.getIndexedPropertyValues("foo.bar.hoge");
    
        // ����
        assertEquals(4, result.size());
        assertEquals("test0", result.get("foo.bar[0].hoge[0]"));
        assertEquals("test2", result.get("foo.bar[2].hoge[0]"));
        assertEquals("test3", result.get("foo.bar[2].hoge[1]"));
        assertEquals("test4", result.get("foo.bar[2].hoge[2]"));
        
        assertEquals("test0", PropertyUtils.getProperty(bean, "foo.bar[0].hoge[0]"));
        assertEquals("test0", BeanUtils.getProperty(bean, "foo.bar[0].hoge[0]"));
        assertEquals("test2", PropertyUtils.getProperty(bean, "foo.bar[2].hoge[0]"));
        assertEquals("test2", BeanUtils.getProperty(bean, "foo.bar[2].hoge[0]"));
        assertEquals("test3", PropertyUtils.getProperty(bean, "foo.bar[2].hoge[1]"));
        assertEquals("test3", BeanUtils.getProperty(bean, "foo.bar[2].hoge[1]"));
        assertEquals("test4", PropertyUtils.getProperty(bean, "foo.bar[2].hoge[2]"));
        assertEquals("test4", BeanUtils.getProperty(bean, "foo.bar[2].hoge[2]"));
    }

    /**
     * testGetIndexedPropertyValuesJavaBean16()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) propertyName:"map�ikey�j"<br>
     *         (���) this.context:JavaBean�o<br>
     *                  �i�v���p�e�B��=�l�j<br>
     *                �@map=Map{<br>
     *                            key="test"<br>
     *                         }<br>
     *                �p<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Map<String, Object>:Map<String, Object><br>
     *                  map(key)="test"<br>
     *         
     * <br>
     * �yJavaBean�̃e�X�g�z<br>
     * Map�^�̑������擾����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedPropertyValuesJavaBean16() throws Exception {
        // �O����
        JXPathIndexedBeanWrapperImpl_JavaBeanStub01 bean 
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub01();
        Map<String, String> map = new HashMap<String, String>();
        map.put("key", "test");
//        map.put("key", null);
        bean.setMap(map);
        
        JXPathIndexedBeanWrapperImpl bw 
            = new JXPathIndexedBeanWrapperImpl(bean);
    
        // �e�X�g���{
        Map<String, Object> result = bw.getIndexedPropertyValues("map(key)"); 
    
        // ����
        assertEquals(1, result.size());
        assertEquals("test", result.get("map(key)"));
        assertEquals("test", PropertyUtils.getProperty(bean, "map(key)"));
        assertEquals("test", BeanUtils.getProperty(bean, "map(key)"));
    }

    /**
     * testGetIndexedPropertyValuesJavaBean17()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) propertyName:"map�ikey�j"<br>
     *         (���) this.context:JavaBean�o<br>
     *                  �i�v���p�e�B��=�l�j<br>
     *                �@map=Map{<br>
     *                            key=(List)<br>
     *                �@�@�@�@�@�@�@�@�@��[0] = 1<br>
     *                                        [1] = 2<br>
     *                                        [2] = 3<br>
     *                         }<br>
     *                �p<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Map<String, Object>:Map<String, Object><br>
     *                  map(key)[0]=1<br>
     *                  map(key)[1]=2<br>
     *                  map(key)[2]=3<br>
     *         
     * <br>
     * �yJavaBean�̃e�X�g�z<br>
     * Map�^�̑�������List�l���擾����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedPropertyValuesJavaBean17() throws Exception {
        // �O����
        JXPathIndexedBeanWrapperImpl_JavaBeanStub01 bean 
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub01();
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("key", list);
        bean.setMap(map);
        
        JXPathIndexedBeanWrapperImpl bw 
            = new JXPathIndexedBeanWrapperImpl(bean);
    
        // �e�X�g���{
        Map<String, Object> result = bw.getIndexedPropertyValues("map(key)"); 
    
        // ����
        assertEquals(3, result.size());
        assertEquals(1, result.get("map(key)[0]"));
        assertEquals(2, result.get("map(key)[1]"));
        assertEquals(3, result.get("map(key)[2]"));
    }

    /**
     * testGetIndexedPropertyValuesJavaBean18()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) propertyName:"map�ikey�j.value"<br>
     *         (���) this.context:JavaBean�o<br>
     *                  �i�v���p�e�B��=�l�j<br>
     *                �@map=Map{<br>
     *                            key=new Bean()<br>
     *                                            ��value="test"<br>
     *                         }<br>
     *                �p<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Map<String, Object>:Map<String, Object><br>
     *                  map(key).value="test"<br>
     *         
     * <br>
     * �yJavaBean�̃e�X�g�z<br>
     * �l�X�g����Map�^�̑������擾����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedPropertyValuesJavaBean18() throws Exception {
        // �O����
        JXPathIndexedBeanWrapperImpl_JavaBeanStub01 bean 
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub01();
        JXPathIndexedBeanWrapperImpl_JavaBeanStub01 subBean 
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub01();
        subBean.setProperty("test");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("key", subBean);
        bean.setMap(map);
        
        JXPathIndexedBeanWrapperImpl bw 
            = new JXPathIndexedBeanWrapperImpl(bean);
    
        // �e�X�g���{
        Map<String, Object> result = bw.getIndexedPropertyValues("map(key).property"); 
    
        // ����
        assertEquals(1, result.size());
        assertEquals("test", result.get("map(key).property"));
        assertEquals("test", PropertyUtils.getProperty(bean, "map(key).property"));
        assertEquals("test", BeanUtils.getProperty(bean, "map(key).property"));
    }

    /**
     * testGetIndexedPropertyValuesJavaBean19()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) propertyName:"aaa"<br>
     *         (���) this.context:JavaBean�o<br>
     *                  �i�v���p�e�B��=�l�j<br>
     *                �@foo="test"<br>
     *                �p<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Map<String, Object>:Map<String, Object><br>
     *                  ��̃}�b�v<br>
     *         
     * <br>
     * �yJavaBean�̃e�X�g�z<br>
     * ���݂��Ȃ��v���p�e�B���ɃA�N�Z�X�����Ƃ��̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedPropertyValuesJavaBean19() throws Exception {
        // �O����
        JXPathIndexedBeanWrapperImpl_JavaBeanStub01 bean 
        = new JXPathIndexedBeanWrapperImpl_JavaBeanStub01();
        bean.setProperty("test");
        
        JXPathIndexedBeanWrapperImpl bw 
            = new JXPathIndexedBeanWrapperImpl(bean);
    
        // �e�X�g���{
        Map<String, Object> result = bw.getIndexedPropertyValues("aaa"); 
    
        // ����
        assertEquals(0, result.size());
    }

    /**
     * testGetIndexedPropertyValuesMap01()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) propertyName:key<br>
     *         (���) this.context:Map<String, Object><br>
     *                key="value"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Map<String, Object>:Map<String, Object><br>
     *                  key="value"<br>
     *         
     * <br>
     * �yMap�^�̃e�X�g�z<br>
     * String�^�̒l���擾����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedPropertyValuesMap01() throws Exception {
        // �O����
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("key", "value");
        
        JXPathIndexedBeanWrapperImpl bw 
            = new JXPathIndexedBeanWrapperImpl(map);
    
        // �e�X�g���{
        Map<String, Object> result = bw.getIndexedPropertyValues("key"); 
    
        // ����
        assertEquals(1, result.size());
        assertEquals("value", result.get("key"));
        assertEquals("value", PropertyUtils.getProperty(map, "key"));
        assertEquals("value", BeanUtils.getProperty(map, "key"));
    }

    /**
     * testGetIndexedPropertyValuesMap02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) propertyName:key<br>
     *         (���) this.context:Map<String, Object><br>
     *                key=null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Map<String, Object>:Map<String, Object><br>
     *                  key=null<br>
     *         
     * <br>
     * �yMap�^�̃e�X�g�z<br>
     * null�l���擾����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedPropertyValuesMap02() throws Exception {
        // �O����
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("key", null);
        
        JXPathIndexedBeanWrapperImpl bw 
            = new JXPathIndexedBeanWrapperImpl(map);
    
        // �e�X�g���{
        Map<String, Object> result = bw.getIndexedPropertyValues("key"); 
    
        // ����
        assertEquals(1, result.size());
        assertNull(result.get("key"));
        assertNull(PropertyUtils.getProperty(map, "key"));
        assertNull(BeanUtils.getProperty(map, "key"));
    }

    /**
     * testGetIndexedPropertyValuesMap03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) propertyName:key<br>
     *         (���) this.context:Map<String, Object><br>
     *                ���Map<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Map<String, Object>:Map<String, Object><br>
     *                  ���Map<br>
     *         
     * <br>
     * �yMap�^�̃e�X�g�z<br>
     * ���݂��Ȃ��L�[�������ƂɃI�u�W�F�N�g���擾����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedPropertyValuesMap03() throws Exception {
        // �O����
        Map<String, Object> map = new HashMap<String, Object>();
        
        JXPathIndexedBeanWrapperImpl bw 
            = new JXPathIndexedBeanWrapperImpl(map);
    
        // �e�X�g���{
        Map<String, Object> result = bw.getIndexedPropertyValues("key"); 
    
        // ����
        assertEquals(0, result.size());
    }

    /**
     * testGetIndexedPropertyValuesMap04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) propertyName:ints<br>
     *         (���) this.context:Map<String, Object><br>
     *                ints=int[]{1,2,3}<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Map<String, Object>:Map<String, Object><br>
     *                  ints[0]=1<br>
     *                  ints[1]=2<br>
     *                  ints[2]=3<br>
     *         
     * <br>
     * �yMap�^�̃e�X�g�z<br>
     * int�^�z����擾����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedPropertyValuesMap04() throws Exception {
        // �O����
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ints", new int[]{1, 2, 3});
        
        JXPathIndexedBeanWrapperImpl bw 
            = new JXPathIndexedBeanWrapperImpl(map);
    
        // �e�X�g���{
        Map<String, Object> result = bw.getIndexedPropertyValues("ints"); 
    
        // ����
        assertEquals(3, result.size());
        assertEquals(1, result.get("ints[0]"));
        assertEquals(2, result.get("ints[1]"));
        assertEquals(3, result.get("ints[2]"));
    }

    /**
     * testGetIndexedPropertyValuesMap05()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) propertyName:numbers<br>
     *         (���) this.context:Map<String, Object><br>
     *                numbers=Long[]{1,2,3}<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Map<String, Object>:Map<String, Object><br>
     *                  numbers[0]=1<br>
     *                  numbers[1]=2<br>
     *                  numbers[2]=3<br>
     *         
     * <br>
     * �yMap�^�̃e�X�g�z<br>
     * Long�^�z����擾����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedPropertyValuesMap05() throws Exception {
        // �O����
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("numbers", new Long[]{new Long(1), new Long(2), new Long(3)});
        
        JXPathIndexedBeanWrapperImpl bw 
            = new JXPathIndexedBeanWrapperImpl(map);
    
        // �e�X�g���{
        Map<String, Object> result = bw.getIndexedPropertyValues("numbers"); 
    
        // ����
        assertEquals(3, result.size());
        assertEquals(new Long(1), result.get("numbers[0]"));
        assertEquals(new Long(2), result.get("numbers[1]"));
        assertEquals(new Long(3), result.get("numbers[2]"));
    }

    /**
     * testGetIndexedPropertyValuesMap06()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) propertyName:numbers<br>
     *         (���) this.context:Map<String, Object><br>
     *                numbers=List{1,2,3}<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Map<String, Object>:Map<String, Object><br>
     *                  numbers[0]=1<br>
     *                  numbers[1]=2<br>
     *                  numbers[2]=3<br>
     *         
     * <br>
     * �yMap�^�̃e�X�g�z<br>
     * Long�^List���擾����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedPropertyValuesMap06() throws Exception {
        // �O����
        Map<String, Object> map = new HashMap<String, Object>();
        List<Long> list = new ArrayList<Long>();
        list.add(new Long(1));
        list.add(new Long(2));
        list.add(new Long(3));
        map.put("numbers", list);
        
        JXPathIndexedBeanWrapperImpl bw 
            = new JXPathIndexedBeanWrapperImpl(map);
    
        // �e�X�g���{
        Map<String, Object> result = bw.getIndexedPropertyValues("numbers"); 
    
        // ����
        assertEquals(3, result.size());
        assertEquals(new Long(1), result.get("numbers[0]"));
        assertEquals(new Long(2), result.get("numbers[1]"));
        assertEquals(new Long(3), result.get("numbers[2]"));
    }

    /**
     * testGetIndexedPropertyValuesMap07()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) propertyName:bean.subBean.property<br>
     *         (���) this.context:Map<String, Object><br>
     *                foo=new Foo()<br>
     *                           ��bar=new Bar()<br>
     *                �@�@�@�@�@�@�@�@     ��hoge="value"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Map<String, Object>:Map<String, Object><br>
     *                  bean.subBean.property="value"<br>
     *         
     * <br>
     * �yMap�^�̃e�X�g�z<br>
     * �l�X�g�����v���p�e�B���擾����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedPropertyValuesMap07() throws Exception {
        // �O����
        Map<String, Object> map = new HashMap<String, Object>();
        
        // bar="value"
        JXPathIndexedBeanWrapperImpl_JavaBeanStub03.Bar bar
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub03.Bar();
        bar.setHoge("value");
        
        // foo
        JXPathIndexedBeanWrapperImpl_JavaBeanStub03.Foo foo
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub03.Foo();
        foo.setBar(bar);
        
        map.put("foo", foo);
        
        JXPathIndexedBeanWrapperImpl bw 
            = new JXPathIndexedBeanWrapperImpl(map);
    
        // �e�X�g���{
        Map<String, Object> result = bw.getIndexedPropertyValues("foo.bar.hoge"); 
    
        // ����
        assertEquals(1, result.size());
        assertEquals("value", result.get("foo.bar.hoge"));
        assertEquals("value", PropertyUtils.getProperty(map, "foo.bar.hoge"));
        assertEquals("value", BeanUtils.getProperty(map, "foo.bar.hoge"));
    }

    /**
     * testGetIndexedPropertyValuesMap08()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) propertyName:beans.subBean.property<br>
     *         (���) this.context:Map<String, Object><br>
     *                foo=new Foo[3]<br>
     *                           ��[0] new Foo()<br>
     *                                       ��bar=new Bar()<br>
     *                �@�@�@�@�@�@�@�@�@�@                ��hoge="value1"<br>
     *                           ��[1] null<br>
     *                           ��[2] new Foo()<br>
     *                                       ��bar=new Bar()<br>
     *                �@�@�@�@�@�@�@�@�@�@                ��hoge="value2"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Map<String, Object>:Map<String, Object><br>
     *                  foo[0].bar.hoge="value1"<br>
     *                  foo[2].bar.hoge="value3"<br>
     *         
     * <br>
     * �yMap�^�̃e�X�g�z<br>
     * �l�X�g�{�z��v���p�e�B���擾����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedPropertyValuesMap08() throws Exception {
        // �O����
        Map<String, Object> map = new HashMap<String, Object>();
        
        // foo
        List<JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Foo> fooList
            = new ArrayList<JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Foo>();
        
        // foo[0]
        JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Foo foo0
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Foo();
        fooList.add(foo0);
        
        // foo[1]
        JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Foo foo1
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Foo();
        fooList.add(foo1);
        
        // foo[2]
        JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Foo foo2
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Foo();
        fooList.add(foo2);
        
        // foo[0].bar.hoge="value"
        JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Bar bar0
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Bar();
        bar0.setHoge("value1");
        foo0.setBar(bar0);
        
        // foo[1].bar=null
        foo1.setBar(null);
        
        // foo[2].bar.hoge="value"
        JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Bar bar2
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Bar();
        bar2.setHoge("value3");
        foo2.setBar(bar2);
        
        map.put("foo", fooList);
        
        JXPathIndexedBeanWrapperImpl bw 
            = new JXPathIndexedBeanWrapperImpl(map);
    
        // �e�X�g���{
        Map<String, Object> result = bw.getIndexedPropertyValues("foo.bar.hoge"); 
    
        // ����
        assertEquals(2, result.size());
        assertEquals("value1", result.get("foo[0].bar.hoge"));
        assertEquals("value3", result.get("foo[2].bar.hoge"));
    }

    /**
     * testGetIndexedPropertyValuesMap09()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) propertyName:beans.property<br>
     *         (���) this.context:Map<String, Object><br>
     *                foo=new Bean[3]<br>
     *                                  ��[0] new Bean()<br>
     *                                           ��property="value1"<br>
     *                                  ��[1] new Bean()<br>
     *                                           ��property=null<br>
     *                                  ��[2] new Bean()<br>
     *                                           ��property="value3"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Map<String, Object>:Map<String, Object><br>
     *                  beans[0].property="value1"<br>
     *                  beans[1].property=null<br>
     *                  beans[2].property="value3"<br>
     *         
     * <br>
     * �yMap�^�̃e�X�g�z<br>
     * �z��{null������̃v���p�e�B���擾����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedPropertyValuesMap09() throws Exception {
        // �O����
        Map<String, Object> map = new HashMap<String, Object>();
        
        // foo
        List<JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Foo> fooList
            = new ArrayList<JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Foo>();
        
        // foo[0]
        JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Foo foo0
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Foo();
        foo0.setProperty("value1");
        fooList.add(foo0);
        
        // foo[1]
        JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Foo foo1
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Foo();
        foo1.setProperty(null);
        fooList.add(foo1);
        
        // foo[2]
        JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Foo foo2
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub04.Foo();
        foo2.setProperty("value3");
        fooList.add(foo2);
        
        map.put("foo", fooList);
        
        JXPathIndexedBeanWrapperImpl bw 
            = new JXPathIndexedBeanWrapperImpl(map);
    
        // �e�X�g���{
        Map<String, Object> result = bw.getIndexedPropertyValues("foo.property"); 
    
        // ����
        assertEquals(3, result.size());
        assertEquals("value1", result.get("foo[0].property"));
        assertNull(result.get("foo[1].property"));
        assertEquals("value3", result.get("foo[2].property"));
    }

    /**
     * testGetIndexedPropertyValuesMap10()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) propertyName:aa/aa.bbb<br>
     *         (���) this.context:Map<String, Object><br>
     *                ���Map<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    �@�@���b�Z�[�W�FInvalid character has found within property name. 'aa/aa.bbb' Cannot use [ / " ' ]<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    Invalid character has found within property name. 'aa/aa.bbb' Cannot use [ / " ' ]<br>
     *         
     * <br>
     * �yMap�^�̃e�X�g�z<br>
     * �s����Map�L�[�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedPropertyValuesMap10() throws Exception {
        // �O����
        JXPathIndexedBeanWrapperImpl bw 
            = new JXPathIndexedBeanWrapperImpl(new HashMap<String, Object>());
    
        // �e�X�g���{
        try {
            bw.getIndexedPropertyValues("aa/aa.bbb");
            fail();
        } catch (IllegalArgumentException e) {
            // OK
            String expect = "Invalid character has found within property name. 'aa/aa.bbb' Cannot use [ / \" ' ]";
            assertEquals(expect, e.getMessage());
            assertTrue(LogUTUtil.checkError(expect));
        }
    }

    /**
     * testGetIndexedPropertyValuesMap11()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) propertyName:aa[aa.bbb<br>
     *         (���) this.context:Map<String, Object><br>
     *                ���Map<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException<br>
     *                        ���b�Z�[�W�FCannot get Index. Invalid property name. 'aa[aa.bbb'<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    Cannot get Index. Invalid property name. 'aa[aa.bbb'<br>
     *         
     * <br>
     * �yMap�^�̃e�X�g�z<br>
     * �s����Map�L�[�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedPropertyValuesMap11() throws Exception {
        // �O����
        JXPathIndexedBeanWrapperImpl bw 
        = new JXPathIndexedBeanWrapperImpl(new HashMap<String, Object>());

        // �e�X�g���{
        try {
            bw.getIndexedPropertyValues("aa[aa.bbb");
            fail();
        } catch (IllegalArgumentException e) {
            // OK
            String expect = "Cannot get Index. Invalid property name. 'aa[aa.bbb'";
            assertEquals(expect, e.getMessage());
            assertTrue(LogUTUtil.checkError(expect));
        }
    }

    /**
     * testGetIndexedPropertyValuesMap12()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) propertyName:aa]aa.bbb<br>
     *         (���) this.context:Map<String, Object><br>
     *                ���Map<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException<br>
     *                        ���b�Z�[�W�FCannot get Index. Invalid property name. 'aa]aa.bbb'<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    Cannot get Index. Invalid property name. 'aa]aa.bbb'<br>
     *         
     * <br>
     * �yMap�^�̃e�X�g�z<br>
     * �s����Map�L�[�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedPropertyValuesMap12() throws Exception {
        // �O����
        JXPathIndexedBeanWrapperImpl bw 
        = new JXPathIndexedBeanWrapperImpl(new HashMap<String, Object>());

        // �e�X�g���{
        try {
            bw.getIndexedPropertyValues("aa]aa.bbb");
            fail();
        } catch (IllegalArgumentException e) {
            // OK
            String expect = "Cannot get Index. Invalid property name. 'aa]aa.bbb'";
            assertEquals(expect, e.getMessage());
            assertTrue(LogUTUtil.checkError(expect));
        }
    }

    /**
     * testGetIndexedPropertyValuesMap13()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) propertyName:aa.aa.bb<br>
     *         (���) this.context:Map<String, Object><br>
     *                aa.aa=new Bean()<br>
     *                            ��bb="test"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Map<String, Object>:Map<String, Object><br>
     *                  ���Map<br>
     *         
     * <br>
     * �yMap�^�̃e�X�g�z<br>
     * �s����Map�L�[�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedPropertyValuesMap13() throws Exception {
        // �O����
        Map<String, Object> map = new HashMap<String, Object>();
        JXPathIndexedBeanWrapperImpl_JavaBeanStub03.Bar bar
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub03.Bar();
        bar.setHoge("test");
        map.put("aa.aa", map);
        JXPathIndexedBeanWrapperImpl bw 
            = new JXPathIndexedBeanWrapperImpl(map);

        // �e�X�g���{
        Map<String, Object> result 
            = bw.getIndexedPropertyValues("aa.aa.bar.hoge");
        assertEquals(0, result.size());
    }

    /**
     * testGetIndexedPropertyValuesMap14()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) propertyName:aa'aa.bb<br>
     *         (���) this.context:Map<String, Object><br>
     *                ���Map<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    �@�@���b�Z�[�W�FInvalid character has found within property name. 'aa'aa.bb' Cannot use [ / " ' ]<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    Invalid character has found within property name. 'aa'aa.bb' Cannot use [ / " ' ]<br>
     *         
     * <br>
     * �yMap�^�̃e�X�g�z<br>
     * �s����Map�L�[�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedPropertyValuesMap14() throws Exception {
        // �O����
        JXPathIndexedBeanWrapperImpl bw 
            = new JXPathIndexedBeanWrapperImpl(new HashMap<String, Object>());

        // �e�X�g���{
        try {
            bw.getIndexedPropertyValues("aa'aa.bbb");
            fail();
        } catch (IllegalArgumentException e) {
            // OK
            String expect = "Invalid character has found within property name. 'aa'aa.bbb' Cannot use [ / \" ' ]";
            assertEquals(expect, e.getMessage());
            assertTrue(LogUTUtil.checkError(expect));
        }
    }

    /**
     * testGetIndexedPropertyValuesMap15()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) propertyName:aa"aa.bb<br>
     *         (���) this.context:Map<String, Object><br>
     *                ���Map<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    �@�@���b�Z�[�W�FInvalid character has found within property name. 'aa"aa.bb' Cannot use [ / " ' ]<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    Invalid character has found within property name. 'aa"aa.bb' Cannot use [ / " ' ]<br>
     *         
     * <br>
     * �yMap�^�̃e�X�g�z<br>
     * �s����Map�L�[�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedPropertyValuesMap15() throws Exception {
        // �O����
        JXPathIndexedBeanWrapperImpl bw 
        = new JXPathIndexedBeanWrapperImpl(new HashMap<String, Object>());

        // �e�X�g���{
        try {
            bw.getIndexedPropertyValues("aa\"aa.bbb");
            fail();
        } catch (IllegalArgumentException e) {
            // OK
            String expect = "Invalid character has found within property name. 'aa\"aa.bbb' Cannot use [ / \" ' ]";
            assertEquals(expect, e.getMessage());
            assertTrue(LogUTUtil.checkError(expect));
        }
    }

    /**
     * testGetIndexedPropertyValuesMap16()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) propertyName:aa(aa.bb<br>
     *         (���) this.context:Map<String, Object><br>
     *                ���Map<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    �@�@���b�Z�[�W�FInvalid property name. PropertyName: 'aa(aa.bbb'XPath: '/aa(aa/bbb'<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    Invalid property name. PropertyName: 'aa(aa.bbb'XPath: '/aa(aa/bbb'<br>
     *         
     * <br>
     * �yMap�^�̃e�X�g�z<br>
     * �s����Map�L�[�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedPropertyValuesMap16() throws Exception {
        // �O����
        JXPathIndexedBeanWrapperImpl bw 
        = new JXPathIndexedBeanWrapperImpl(new HashMap<String, Object>());

        // �e�X�g���{
        try {
            bw.getIndexedPropertyValues("aa(aa.bbb");
            fail();
        } catch (IllegalArgumentException e) {
            // OK
            String expect = "Invalid property name. PropertyName: 'aa(aa.bbb'XPath: '/aa(aa/bbb'";
            assertEquals(expect, e.getMessage());
            assertSame(JXPathException.class, e.getCause().getClass());
        }
    }

    /**
     * testGetIndexedPropertyValuesMap17()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) propertyName:aa)aa.bb<br>
     *         (���) this.context:Map<String, Object><br>
     *                ���Map<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    �@�@���b�Z�[�W�FInvalid property name. PropertyName: 'aa)aa.bbb'XPath: '/aa)aa/bbb'<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    Invalid property name. PropertyName: 'aa)aa.bbb'XPath: '/aa)aa/bbb'<br>
     *         
     * <br>
     * �yMap�^�̃e�X�g�z<br>
     * �s����Map�L�[�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedPropertyValuesMap17() throws Exception {
        // �O����
        JXPathIndexedBeanWrapperImpl bw 
        = new JXPathIndexedBeanWrapperImpl(new HashMap<String, Object>());

        // �e�X�g���{
        try {
            bw.getIndexedPropertyValues("aa)aa.bbb");
            fail();
        } catch (IllegalArgumentException e) {
            // OK
            String expect = "Invalid property name. PropertyName: 'aa)aa.bbb'XPath: '/aa)aa/bbb'";
            assertEquals(expect, e.getMessage());
            assertSame(JXPathException.class, e.getCause().getClass());
        }
    }

    /**
     * testGetIndexedPropertyValuesJavaBean01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) propertyName:"foo.bar.hoge"<br>
     *         (���) this.context:DynaBean�o<br>
     *                  �i�v���p�e�B��=�l�j<br>
     *                �@foo.bar.hoge="test"<br>
     *                �p<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Map<String, Object>:Map<String, Object><br>
     *                  foo.bar.hoge="test"<br>
     *         
     * <br>
     * �yDynaBean�̃e�X�g�z<br>
     * �l�X�g�����l���擾����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedPropertyValuesDynaBean01() throws Exception {
        // �O����
        JXPathIndexedBeanWrapperImpl_JavaBeanStub03 bean
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub03();
        // foo
        JXPathIndexedBeanWrapperImpl_JavaBeanStub03.Foo foo
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub03.Foo();
    
        // bar
        JXPathIndexedBeanWrapperImpl_JavaBeanStub03.Bar bar
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub03.Bar();
        // hoge
        bar.setHoge("test");
    
        // foo.bar.hoge
        foo.setBar(bar);
        bean.setFoo(foo);
        
        WrapDynaBean dynaBean = new WrapDynaBean(bean);
        
        JXPathIndexedBeanWrapperImpl bw =
            new JXPathIndexedBeanWrapperImpl(dynaBean);
    
        // �e�X�g���{
        Map<String, Object> result = bw.getIndexedPropertyValues("foo.bar.hoge");
    
        // ����
        assertEquals(1, result.size());
        assertTrue(result.containsKey("foo.bar.hoge"));
        assertEquals("test", result.get("foo.bar.hoge"));
        assertEquals("test", PropertyUtils.getProperty(dynaBean, "foo.bar.hoge"));
        assertEquals("test", BeanUtils.getProperty(dynaBean, "foo.bar.hoge"));
    }

    /**
     * testGetIndexedPropertyValuesJavaBean02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) propertyName:"foo.bar.hoge"<br>
     *         (���) this.context:DynaBean�o<br>
     *                  �i�v���p�e�B��=�l�j<br>
     *                �@foo.bar[0].hoge[0]="test0"<br>
     *                  foo.bar[1]=null<br>
     *                  foo.bar[2].hoge[0]="test2"<br>
     *                  foo.bar[2].hoge[1]="test3"<br>
     *                  foo.bar[2].hoge[2]="test4"<br>
     *                �p<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Map<String, Object>:Map<String, Object><br>
     *                  �@foo.bar[0].hoge[0]="test0"<br>
     *                    foo.bar[2].hoge[0]="test2"<br>
     *                    foo.bar[2].hoge[1]="test3"<br>
     *                    foo.bar[2].hoge[2]="test4"<br>
     *         
     * <br>
     * �yDynaBean�̃e�X�g�z<br>
     * �l�X�g�{�z��{null��������p�^�[���̒l���擾����e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetIndexedPropertyValuesDynaBean02() throws Exception {
        // �O����
        JXPathIndexedBeanWrapperImpl_JavaBeanStub05 bean
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub05();
    
        // foo
        JXPathIndexedBeanWrapperImpl_JavaBeanStub05.Foo foo
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub05.Foo();
        bean.setFoo(foo);
    
        // foo.bar[0]
        JXPathIndexedBeanWrapperImpl_JavaBeanStub05.Bar bar0
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub05.Bar();
        // foo.bar[1]
        JXPathIndexedBeanWrapperImpl_JavaBeanStub05.Bar bar1 = null;
        // foo.bar[2]
        JXPathIndexedBeanWrapperImpl_JavaBeanStub05.Bar bar2
            = new JXPathIndexedBeanWrapperImpl_JavaBeanStub05.Bar();
        // foo.bar[]
        List<JXPathIndexedBeanWrapperImpl_JavaBeanStub05.Bar> barList
            = new ArrayList<JXPathIndexedBeanWrapperImpl_JavaBeanStub05.Bar>();
        // foo.bar[0]
        barList.add(bar0);
        // foo.bar[1]
        barList.add(bar1);
        // foo.bar[2]
        barList.add(bar2);
    
        //foo.bar
        foo.setBar(barList);
    
        // foo.bar[0].hoge[0]="test0"
        List<String> hoge0 = new ArrayList<String>();
        hoge0.add("test0");
        bar0.setHoge(hoge0);
    
        // foo.bar[1]=null
    
        // foo.bar[2].hoge[0]="test2"
        // foo.bar[2].hoge[1]="test3"
        // foo.bar[2].hoge[2]="test4"
        List<String> hoge2 = new ArrayList<String>();
        hoge2.add("test2");
        hoge2.add("test3");
        hoge2.add("test4");
        bar2.setHoge(hoge2);
        
        WrapDynaBean dynaBean = new WrapDynaBean(bean);
    
        JXPathIndexedBeanWrapperImpl bw =
            new JXPathIndexedBeanWrapperImpl(dynaBean);
    
        // �e�X�g���{
        Map<String, Object> result = bw.getIndexedPropertyValues("foo.bar.hoge");
    
        // ����
        assertEquals(4, result.size());
        assertEquals("test0", result.get("foo.bar[0].hoge[0]"));
        assertEquals("test2", result.get("foo.bar[2].hoge[0]"));
        assertEquals("test3", result.get("foo.bar[2].hoge[1]"));
        assertEquals("test4", result.get("foo.bar[2].hoge[2]"));
        assertEquals("test0", PropertyUtils.getProperty(dynaBean, "foo.bar[0].hoge[0]"));
        assertEquals("test0", BeanUtils.getProperty(dynaBean, "foo.bar[0].hoge[0]"));
    }
}
