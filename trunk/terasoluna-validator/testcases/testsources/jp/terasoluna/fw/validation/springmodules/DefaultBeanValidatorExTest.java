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

import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorException;
import org.apache.commons.validator.ValidatorResources;

import jp.terasoluna.fw.validation.springmodules.CommonsValidatorEx;
import jp.terasoluna.fw.validation.springmodules.DefaultBeanValidatorEx;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.validation.springmodules.DefaultBeanValidatorEx}
 * �N���X�̃e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * Spring-Modules��DefaultBeanValidator���ۃN���X�B
 * <p>
 * 
 * @see jp.terasoluna.fw.validation.springmodules.DefaultBeanValidatorEx
 */
public class DefaultBeanValidatorExTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * 
     * @param args
     *            java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(DefaultBeanValidatorExTest.class);
    }

    /**
     * �������������s���B
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * �I���������s���B
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * �R���X�g���N�^�B
     * 
     * @param name
     *            ���̃e�X�g�P�[�X�̖��O�B
     */
    public DefaultBeanValidatorExTest(String name) {
        super(name);
    }

    /**
     * testCleanupValidator01() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA,E <br>
     * <br>
     * ���͒l�F(����) validator:CommonsValidatorEx�C���X�^���X<br>
     * (�O�����) validator.getValidatorException():ValidatorException�C���X�^���X<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ValidatorException�iterasoluna-spring-validator)<br>
     * �E������O�]ValidatorException(commons)<br>
     * 
     * <br>
     * ����validator��ValidatorException���ݒ肳��Ă���ꍇ�A�����^�C����O�Ƀ��b�v���ăX���[���邱�Ƃ̃e�X�g�B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testCleanupValidator01() throws Exception {
        // �O����
        ValidatorResources resources = new ValidatorResources();
        CommonsValidatorEx commonsValidatorEx = new CommonsValidatorEx(
                resources, null);
        ValidatorException validatorException = new ValidatorException();
        UTUtil.setPrivateField(commonsValidatorEx, "validatorException",
                validatorException);

        DefaultBeanValidatorEx defaultBeanValidatorEx = new DefaultBeanValidatorEx();
        try {
            // �e�X�g���{
            defaultBeanValidatorEx.cleanupValidator(commonsValidatorEx);
            fail();
        } catch (jp.terasoluna.fw.validation.springmodules.ValidatorException e) {
            // ����
            assertSame(validatorException, e.getCause());
        }
    }

    /**
     * testCleanupValidator02() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA,E <br>
     * <br>
     * ���͒l�F(����) validator:CommonsValidatorEx�C���X�^���X<br>
     * (�O�����) validator.getValidatorException():null<br>
     * 
     * <br>
     * ���Ғl�F <br>
     * ����validator��ValidatorException���ݒ肳��Ă��Ȃ��ꍇ�A��O���X���[�����ɏ������I�����邱�Ƃ̃e�X�g�B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testCleanupValidator02() throws Exception {
        // �O����
        ValidatorResources resources = new ValidatorResources();
        CommonsValidatorEx commonsValidatorEx = new CommonsValidatorEx(
                resources, null);
        UTUtil.setPrivateField(commonsValidatorEx, "validatorException", null);

        DefaultBeanValidatorEx defaultBeanValidatorEx = new DefaultBeanValidatorEx();
        try {
            // �e�X�g���{
            defaultBeanValidatorEx.cleanupValidator(commonsValidatorEx);
        } catch (jp.terasoluna.fw.validation.springmodules.ValidatorException e) {
            // ����
            fail();
        }
    }

    /**
     * testCleanupValidator03() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FA,E <br>
     * <br>
     * ���͒l�F(����) validator:CommonsValidatorEx�ȊO�̃C���X�^���X<br>
     * 
     * <br>
     * ���Ғl�F <br>
     * ����validator��validatorCommonsValidatorEx�C���X�^���X�łȂ��ꍇ�A�O���X���[�����ɏ������I�����邱�Ƃ̃e�X�g�B
     * <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testCleanupValidator03() throws Exception {
        // �O����
        ValidatorResources resources = new ValidatorResources();
        Validator validator = new Validator(resources);

        DefaultBeanValidatorEx defaultBeanValidatorEx = new DefaultBeanValidatorEx();
        try {

            defaultBeanValidatorEx.cleanupValidator(validator);
        } catch (jp.terasoluna.fw.validation.springmodules.ValidatorException e) {
            // ����
            fail();
        }
    }

}
