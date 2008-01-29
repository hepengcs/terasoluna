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

package jp.terasoluna.fw.util;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

import jp.terasoluna.fw.util.ProxyUtil;
import junit.framework.TestCase;


/**
 * {@link jp.terasoluna.fw.util.ProxyUtil} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �v���L�V�֘A�̃��[�e�B���e�B�N���X�B
 * <p>
 * 
 * @see jp.terasoluna.fw.util.ProxyUtil
 */
public class ProxyUtilTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ProxyUtilTest.class);
    }

    /**
     * �������������s���B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Override
    protected void setUp() throws Exception {
    }

    /**
     * �I���������s���B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @Override
    protected void tearDown() throws Exception {
    }

    /**
     * �R���X�g���N�^�B
     * 
     * @param name ���̃e�X�g�P�[�X�̖��O�B
     */
    public ProxyUtilTest(String name) {
        super(name);
    }

    /**
     * testGetTargetClass01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) proxy:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    "Proxy object is null."<br>
     *         
     * <br>
     * �v���L�V�I�u�W�F�N�g��null�̏ꍇ�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetTargetClass01() throws Exception {
    	// �e�X�g���{
    	try {
    		ProxyUtil.getTargetClass(null);
    		fail();
    	} catch (IllegalArgumentException e) {
    		// OK
    		assertEquals("Proxy object is null.", e.getMessage());
    	}
    }

    /**
     * testGetTargetClass02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) proxy:Cglib2AopProxy<br>
     *                �@��JavaBean�I�u�W�F�N�g<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Class:JavaBean.class<br>
     *         
     * <br>
     * �v���L�V�I�u�W�F�N�g��CGLIB�ō쐬���ꂽ�ꍇ�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetTargetClass02() throws Exception {
        // �O����
    	ProxyFactory pf = new ProxyFactory(new ProxyUtil_JavaBeanStub01());
    	pf.setProxyTargetClass(true);
        Object proxy = pf.getProxy();
        assertTrue(AopUtils.isCglibProxy(proxy));

        // �e�X�g���{
    	Class result = ProxyUtil.getTargetClass(proxy);

        // ����
    	assertSame(ProxyUtil_JavaBeanStub01.class, result);
    }

    /**
     * testGetTargetClass03()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) proxy:JavaBean<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Class:JavaBean.class<br>
     *         
     * <br>
     * �^�[�Q�b�g�I�u�W�F�N�g�Ƀv���L�V���������Ă��Ȃ��ꍇ�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetTargetClass03() throws Exception {
        // �e�X�g���{
    	Class result = ProxyUtil.getTargetClass(new ProxyUtil_JavaBeanStub01());

        // ����
    	assertSame(ProxyUtil_JavaBeanStub01.class, result);
    }

    /**
     * testGetTargetClass04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) proxy:JdkDynamicAopProxy<br>
     *                �@��JavaBean<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Class:JavaBean.class<br>
     *         
     * <br>
     * �v���L�V�I�u�W�F�N�g��Proxy�ō쐬���ꂽ�ꍇ�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetTargetClass04() throws Exception {
    	// �O����
    	ProxyFactory pf = new ProxyFactory(new ProxyUtil_JavaBeanStub01());
        Object proxy = pf.getProxy();

        assertTrue(AopUtils.isJdkDynamicProxy(proxy));

        // �e�X�g���{
    	Class result = ProxyUtil.getTargetClass(proxy);

        // ����
    	assertSame(ProxyUtil_JavaBeanStub01.class, result);
    }

    /**
     * testGetTargetClass05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) proxy:JdkDynamicAopProxy<br>
     *                �@��JdkDynamicAopProxy<br>
     *                �@�@�@�@��JavaBean<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Class:JavaBean.class<br>
     *         
     * <br>
     * �v���L�V�I�u�W�F�N�g���l�X�g����Proxy�ō쐬���ꂽ�ꍇ�̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetTargetClass05() throws Exception {
        // �O����
    	ProxyFactory parentPf = new ProxyFactory(new ProxyUtil_JavaBeanStub01());
        Object parent = parentPf.getProxy();
        
        ProxyFactory pf = new ProxyFactory(parent);
        Object proxy = pf.getProxy();

        assertTrue(AopUtils.isJdkDynamicProxy(proxy));

        // �e�X�g���{
    	Class result = ProxyUtil.getTargetClass(proxy);

        // ����
    	assertSame(ProxyUtil_JavaBeanStub01.class, result);
    }
}
