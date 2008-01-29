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

package jp.terasoluna.fw.web.rich;

import java.util.HashSet;
import java.util.Set;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.web.rich.ForbiddenURICheckerImpl;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.rich.ForbiddenURICheckerImpl}
 * �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �����񂪋�����URI�����肷��`�F�b�J�̃f�t�H���g�����N���X�B<br>
 * isAllowedURI�̈�������not null�Ƃ���B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.rich.ForbiddenURICheckerImpl
 */
public class ForbiddenURICheckerImplTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ForbiddenURICheckerImplTest.class);
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
    public ForbiddenURICheckerImplTest(String name) {
        super(name);
    }

    /**
     * testSetAllowedURISet01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(����) allowedURISet:Set�C���X�^���X<br>
     *         (���) allowedURISet:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) allowedURISet:�������Ɠ���̃C���X�^���X<br>
     *         
     * <br>
     * �������̒l�𐳏�ɑ����ɐݒ肷�邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetAllowedURISet01() throws Exception {
        // �O����
        // allowedURISet
        Set<String> allowedURISet = new HashSet<String>();
        
        ForbiddenURICheckerImpl impl = new ForbiddenURICheckerImpl();
        UTUtil.setPrivateField(impl, "allowedURISet", null);

        // �e�X�g���{
        impl.setAllowedURISet(allowedURISet);

        // ����
        assertSame(allowedURISet, UTUtil.getPrivateField(impl, "allowedURISet"));
    }

    /**
     * testIsAllowedURI01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) requestURI:"AAA"<br>
     *         (���) allowedURISet:{"AAA", "BBB", "CCC"}<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         
     * <br>
     * �����̃Z�b�g�Ɉ������̕����񂪊܂܂�Ă���ꍇ�Atrue��ԋp���邱�Ƃ̃e�X�g�B�i�����v�f�����j
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsAllowedURI01() throws Exception {
        // �O����
        // requestURI : "AAA"
        String requestURI = "AAA";
        
        // allowedURISet : "AAA", "BBB", "CCC"
        Set<String> allowedURISet = new HashSet<String>();
        allowedURISet.add("AAA");
        allowedURISet.add("BBB");
        allowedURISet.add("CCC");
        
        ForbiddenURICheckerImpl impl = new ForbiddenURICheckerImpl();
        UTUtil.setPrivateField(impl, "allowedURISet", allowedURISet);

        // �e�X�g���{
        boolean b = impl.isAllowedURI(requestURI);

        // ����
        assertTrue(b);
    }

    /**
     * testIsAllowedURI02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) requestURI:"AAA"<br>
     *         (���) allowedURISet:{"AAA"}<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         
     * <br>
     * �����̃Z�b�g�Ɉ������̕����񂪊܂܂�Ă���ꍇ�Atrue��ԋp���邱�Ƃ̃e�X�g�B�i�����v�f�P���j
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsAllowedURI02() throws Exception {
        // �O����
        // requestURI : "AAA"
        String requestURI = "AAA";
        
        // allowedURISet : "AAA"
        Set<String> allowedURISet = new HashSet<String>();
        allowedURISet.add("AAA");
        
        ForbiddenURICheckerImpl impl = new ForbiddenURICheckerImpl();
        UTUtil.setPrivateField(impl, "allowedURISet", allowedURISet);

        // �e�X�g���{
        boolean b = impl.isAllowedURI(requestURI);

        // ����
        assertTrue(b);
    }

    /**
     * testIsAllowedURI03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) requestURI:"AAA"<br>
     *         (���) allowedURISet:{}<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         
     * <br>
     * false��ԋp���邱�Ƃ̃e�X�g�B�i�������J���j
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsAllowedURI03() throws Exception {
        // �O����
        // requestURI : "AAA"
        String requestURI = "AAA";
        
        // allowedURISet : {}
        Set<String> allowedURISet = new HashSet<String>();
        
        ForbiddenURICheckerImpl impl = new ForbiddenURICheckerImpl();
        UTUtil.setPrivateField(impl, "allowedURISet", allowedURISet);

        // �e�X�g���{
        boolean b = impl.isAllowedURI(requestURI);

        // ����
        assertFalse(b);
    }

    /**
     * testIsAllowedURI04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) requestURI:""<br>
     *         (���) allowedURISet:{"", "BBB", "CCC"}<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         
     * <br>
     * �����̃Z�b�g�Ɉ������̕����񂪊܂܂�Ă���ꍇ�Atrue��ԋp���邱�Ƃ̃e�X�g�B�i�������󕶎��j
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsAllowedURI04() throws Exception {
        // �O����
        // requestURI : ""
        String requestURI = "";
        
        // allowedURISet : "", "BBB", "CCC"
        Set<String> allowedURISet = new HashSet<String>();
        allowedURISet.add("");
        allowedURISet.add("BBB");
        allowedURISet.add("CCC");
        
        ForbiddenURICheckerImpl impl = new ForbiddenURICheckerImpl();
        UTUtil.setPrivateField(impl, "allowedURISet", allowedURISet);

        // �e�X�g���{
        boolean b = impl.isAllowedURI(requestURI);

        // ����
        assertTrue(b);
    }

    /**
     * testIsAllowedURI05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) requestURI:"AAA"<br>
     *         (���) allowedURISet:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         
     * <br>
     * false��ԋp���邱�Ƃ̃e�X�g�B�i������null�j
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsAllowedURI05() throws Exception {
        // �O����
        // requestURI : "AAA"
        String requestURI = "AAA";
        
        ForbiddenURICheckerImpl impl = new ForbiddenURICheckerImpl();
        UTUtil.setPrivateField(impl, "allowedURISet", null);

        // �e�X�g���{
        boolean b = impl.isAllowedURI(requestURI);

        // ����
        assertFalse(b);
    }

}
