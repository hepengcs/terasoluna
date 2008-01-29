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

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorResources;
import org.springframework.validation.Errors;

import jp.terasoluna.fw.validation.springmodules.DefaultValidatorFactoryEx;
import jp.terasoluna.fw.validation.springmodules.SpringValidationErrors;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.validation.springmodules.DefaultValidatorFactoryEx}
 * �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * FieldChecks�N���X�ɂĎg�p�����G���[�C���^�t�F�[�X�̎�����
 * Spring�t���[�����[�N��Errors�N���X���������߂̃N���X�B
 * <p>
 * 
 * @see jp.terasoluna.fw.validation.springmodules.DefaultValidatorFactoryEx
 */
public class DefaultValidatorFactoryExTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(DefaultValidatorFactoryExTest.class);
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
    public DefaultValidatorFactoryExTest(String name) {
        super(name);
    }

    /**
     * testGetValidator01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC, D
     * <br><br>
     * ���͒l�F(����) beanName:null<br>
     *         (����) bean:null<br>
     *         (����) errors:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Validator:Validator�C���X�^���X<br>
     *                  validator.getParameterValue("jp.terasoluna.fw.validation.ValidationErrors")=<br>
     *                  SpringValidatorErrors.getErrors()�Fnull<br>
     *                  validator.getParameterValue("java.lang.Object")=null<br>
     *                  validator.getFormName()=null<br>
     *         
     * <br>
     * ������null�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetValidator01() throws Exception {
        // �O����
        // beanName : null
        String beanName = null;
        
        // bean : null
        Object bean = null;
        
        // errors : null
        Errors errors = null;

        DefaultValidatorFactoryEx factory = new DefaultValidatorFactoryEx();
        
        // DefaultValidatorFactory��validatorResources��ݒ肷��B
        ValidatorResources resources = new ValidatorResources();
        UTUtil.setPrivateField(factory, "validatorResources", resources);
        
        Validator validator = null;
        
        // �e�X�g���{
        validator = factory.getValidator(beanName, bean, errors);

        // ����
        // errors : null
        SpringValidationErrors resultErorrs = 
            (SpringValidationErrors) validator.getParameterValue(
                    "jp.terasoluna.fw.validation.ValidationErrors");
        assertNull(resultErorrs.getErrors());
        
        // bean : null
        assertNull(validator.getParameterValue("java.lang.Object"));
        
        // beanName : null
        assertNull(validator.getFormName());
    }

    /**
     * testGetValidator02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC, D
     * <br><br>
     * ���͒l�F(����) beanName:""<br>
     *         (����) bean:String("bean")<br>
     *         (����) errors:���Errors�C���X�^���X<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Validator:Validator�C���X�^���X<br>
     *                  validator.getParameterValue
     *                  ("jp.terasoluna.fw.validation.ValidationErrors")=<br>
     *                  SpringValidatorErrors.getErrors()�F���Errors�C���X�^���X<br>
     *                  validator.getParameterValue("java.lang.Object")=String("bean")<br>
     *                  validator.getFormName()=""<br>
     *         
     * <br>
     * ����beanName���󕶎��ł���Abean��not null�ł���Aerrors�����Errors�C���X�^���X�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetValidator02() throws Exception {
        // �O����
        // beanName : ""
        String beanName = "";
        
        // bean : String("bean")
        Object bean = "bean";
        
        // errors : ���Errors�C���X�^���X
        Errors errors = new ErrorsImpl01();

        DefaultValidatorFactoryEx factory = new DefaultValidatorFactoryEx();
        
        // DefaultValidatorFactory��validatorResources��ݒ肷��B
        ValidatorResources resources = new ValidatorResources();
        UTUtil.setPrivateField(factory, "validatorResources", resources);
        
        Validator validator = null;
        
        // �e�X�g���{
        validator = factory.getValidator(beanName, bean, errors);

        // ����
        // errors : �����C���X�^���X
        SpringValidationErrors resultErorrs = 
            (SpringValidationErrors) validator.getParameterValue(
                    "jp.terasoluna.fw.validation.ValidationErrors");
        assertSame(errors, resultErorrs.getErrors());
        
        // bean : new String("bean")
        assertSame(bean, validator.getParameterValue("java.lang.Object"));
        
        // beanName : ""
        assertEquals(beanName, validator.getFormName());
    }

    /**
     * testGetValidator03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC, D
     * <br><br>
     * ���͒l�F(����) beanName:"beanName"<br>
     *         (����) bean:String("bean")<br>
     *         (����) errors:�v�f1��Errors�C���X�^���X<br>
     *                {Object[0]=new Object}<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Validator:Validator�C���X�^���X<br>
     *                  validator.getParameterValue
     *                  ("jp.terasoluna.fw.validation.ValidationErrors")=<br>
     *                  SpringValidatorErrors.getErrors()�F<br>
     *                  �v�f1��Errors�C���X�^���X{Object[0]=new Object}<br>
     *                  validator.getParameterValue("java.lang.Object")=String("bean")<br>
     *                  validator.getFormName()="beanName"<br>
     *         
     * <br>
     * ����beanName��������ł���Abean��not null�ł���Aerrors���v�f��1��Errors�C���X�^���X�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetValidator03() throws Exception {
        // �O����
        // beanName : "beanName"
        String beanName = "beanName";
        
        // bean : String("bean")
        Object bean = "bean";
        
        // errors : �v�f1��Errors�C���X�^���X
        Errors errors = new ErrorsImpl01();
        List<Object> list = new ArrayList<Object>();
        list.add(0, new Object());
        UTUtil.setPrivateField(errors, "errors", list);

        DefaultValidatorFactoryEx factory = new DefaultValidatorFactoryEx();
        
        // DefaultValidatorFactory��validatorResources��ݒ肷��B
        ValidatorResources resources = new ValidatorResources();
        UTUtil.setPrivateField(factory, "validatorResources", resources);
        
        Validator validator = null;
        
        // �e�X�g���{
        validator = factory.getValidator(beanName, bean, errors);

        // ����
        // errors : �����C���X�^���X
        SpringValidationErrors resultErorrs = 
            (SpringValidationErrors) validator.getParameterValue(
                    "jp.terasoluna.fw.validation.ValidationErrors");
        assertSame(errors, resultErorrs.getErrors());

        // bean : new String("bean")
        assertSame(bean, validator.getParameterValue("java.lang.Object"));
        
        // beanName : "beanName"
        assertEquals(beanName, validator.getFormName());
    }

    /**
     * testGetValidator04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC, D
     * <br><br>
     * ���͒l�F(����) beanName:"beanName"<br>
     *         (����) bean:String("bean")<br>
     *         (����) errors:�v�f3��Errors�C���X�^���X<br>
     *                {Object[0]=new Object,<br>
     *                Object[1]=new Object,<br>
     *                Object[2]=new Object}<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Validator:Validator�C���X�^���X<br>
     *                  validator.getParameterValue
     *                  ("jp.terasoluna.fw.validation.ValidationErrors")=<br>
     *                  SpringValidatorErrors.getErrors()�F�v�f1��Errors�C���X�^���X<br>
     *                  {Object[0]=new Object,Object[1]=new Object,Object[2]=new Object}<br>
     *                  validator.getParameterValue("java.lang.Object")=String("bean")<br>
     *                  validator.getFormName()="beanName"<br>
     *         
     * <br>
     * ����beanName��������ł���Abean��not null�ł���Aerrors���v�f��3��Errors�C���X�^���X�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetValidator04() throws Exception {
        // �O����
        // beanName : "beanName"
        String beanName = "beanName";
        
        // bean : String("bean")
        Object bean = "bean";
        
        // errors : �v�f3��Errors�C���X�^���X
        Errors errors = new ErrorsImpl01();
        List<Object> list = new ArrayList<Object>();
        list.add(0, new Object());
        list.add(1, new Object());
        list.add(2, new Object());
        
        UTUtil.setPrivateField(errors, "errors", list);

        DefaultValidatorFactoryEx factory = new DefaultValidatorFactoryEx();
        
        // DefaultValidatorFactory��validatorResources��ݒ肷��B
        ValidatorResources resources = new ValidatorResources();
        UTUtil.setPrivateField(factory, "validatorResources", resources);
        
        Validator validator = null;
        
        // �e�X�g���{
        validator = factory.getValidator(beanName, bean, errors);

        // ����
        // errors : �����C���X�^���X
        SpringValidationErrors resultErorrs = 
            (SpringValidationErrors) validator.getParameterValue(
                    "jp.terasoluna.fw.validation.ValidationErrors");
        assertSame(errors, resultErorrs.getErrors());

        // bean : new String("bean")
        assertSame(bean, validator.getParameterValue("java.lang.Object"));
        
        // beanName : "beanName"
        assertEquals(beanName, validator.getFormName());
    }
}