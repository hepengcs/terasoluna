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
 * ���N���X��protected���\�b�h�̈���node�� not null �ł���B
 * <p>
 * 
 * @see jp.terasoluna.fw.beans.JXPathIndexedBeanWrapperImpl
 */
public class JXPathIndexedBeanWrapperImplTest03 extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(JXPathIndexedBeanWrapperImplTest03.class);
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
    public JXPathIndexedBeanWrapperImplTest03(String name) {
        super(name);
    }

    /**
     * testToPropertyName01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) xpath:".[@name='bbb']"<br>
     *         (���) �X�^�uisMapObject()�̖߂�l:true<br>
     *         (���) �X�^�uextractMapKey()�̖߂�l:"bbb"<br>
     *         (���) �X�^�uextractDecrementIndex()�̖߂�l:""<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"bbb"<br>
     *         (��ԕω�) isMapObject()�̈���:".[@name='bbb']"<br>
     *         (��ԕω�) extractMapKey()�̈���:".[@name='bbb']"<br>
     *         (��ԕω�) extractDecrementIndex()�̈���:".[@name='bbb']"<br>
     *         
     * <br>
     * �yMap�^�E�K�w�Ȃ��̎����z<br>
     * �z�肵�����͂̏ꍇ�̎����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToPropertyName01() throws Exception {
        // �O����
        String result = null;
        JXPathIndexedBeanWrapperImplStub04 target =
            new JXPathIndexedBeanWrapperImplStub04("");
        target.isMapObjectReturnValue = true;
        target.extractDecrementIndexReturnValue = "";

        // �e�X�g���{
        result = target.toPropertyName(".[@name='bbb']");

        // ����
        assertEquals("bbb",result);
        assertEquals(".[@name='bbb']", target.isMapObjectArg1.get(0));
        assertEquals(".[@name='bbb']", target.extractMapKeyArg1.get(0));
        assertEquals(".[@name='bbb']", target.extractDecrementIndexArg1.get(0));
    }

    /**
     * testToPropertyName02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) xpath:"aaa[@name='bbb']"<br>
     *         (���) �X�^�uisMapObject()�̖߂�l:false<br>
     *         (���) �X�^�uextractMapKey()�̖߂�l:"bbb"<br>
     *         (���) �X�^�uisMapAttribute()�̖߂�l:true<br>
     *         (���) �X�^�uextractMapAttributeName()�̖߂�l:"aaa"<br>
     *         (���) �X�^�uextractDecrementIndex()�̖߂�l:""<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"aaa(bbb)"<br>
     *         (��ԕω�) isMapObject()�̈���:"aaa[@name='bbb']"<br>
     *         (��ԕω�) extractMapKey()�̈���:"aaa[@name='bbb']"<br>
     *         (��ԕω�) isMapAttribute()�̈���:"aaa[@name='bbb']"<br>
     *         (��ԕω�) extractMapAttributeName()�̈���:"aaa[@name='bbb']"<br>
     *         (��ԕω�) extractDecrementIndex()�̈���:"aaa[@name='bbb']"<br>
     *         
     * <br>
     * �yMap�����E�K�w�Ȃ��̎����z<br>
     * �z�肵�����͂̏ꍇ�̎����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToPropertyName02() throws Exception {
        // �O����
        String result = null;
        JXPathIndexedBeanWrapperImplStub04 target =
            new JXPathIndexedBeanWrapperImplStub04("");
        target.isMapObjectReturnValue = false;
        target.isMapAttributeReturnValue = true;
        target.extractDecrementIndexReturnValue = "";

        // �e�X�g���{
        result = target.toPropertyName("aaa[@name='bbb']");

        // ����
        assertEquals("aaa(bbb)", result);
        assertEquals("aaa[@name='bbb']", target.isMapObjectArg1.get(0));
        assertEquals("aaa[@name='bbb']", target.extractMapKeyArg1.get(0));
        assertEquals("aaa[@name='bbb']", target.isMapAttributeArg1.get(0));
        assertEquals("aaa[@name='bbb']", target.extractMapAttributeNameArg1.get(0));
        assertEquals("aaa[@name='bbb']", target.extractDecrementIndexArg1.get(0));
    }

    /**
     * testToPropertyName03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) xpath:"aaa[@name='bbb'][10]"<br>
     *         (���) �X�^�uisMapObject()�̖߂�l:false<br>
     *         (���) �X�^�uextractMapKey()�̖߂�l:"bbb"<br>
     *         (���) �X�^�uisMapAttribute()�̖߂�l:true<br>
     *         (���) �X�^�uextractMapAttributeName()�̖߂�l:"aaa"<br>
     *         (���) �X�^�uextractDecrementIndex()�̖߂�l:"[9]"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"aaa(bbb)[9]"<br>
     *         (��ԕω�) isMapObject()�̈���:"aaa[@name='bbb'][10]"<br>
     *         (��ԕω�) extractMapKey()�̈���:"aaa[@name='bbb'][10]"<br>
     *         (��ԕω�) isMapAttribute()�̈���:"aaa[@name='bbb'][10]"<br>
     *         (��ԕω�) extractMapAttributeName()�̈���:"aaa[@name='bbb'][10]"<br>
     *         (��ԕω�) extractDecrementIndex()�̈���:"aaa[@name='bbb'][10]"<br>
     *         
     * <br>
     * �yMap�����z��E�K�w�Ȃ��̎����z<br>
     * �z�肵�����͂̏ꍇ�̎����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToPropertyName03() throws Exception {
        // �O����
        String result = null;
        JXPathIndexedBeanWrapperImplStub04 target =
            new JXPathIndexedBeanWrapperImplStub04("");
        target.isMapObjectReturnValue = false;
        target.isMapAttributeReturnValue = true;
        target.extractDecrementIndexReturnValue = "[9]";

        // �e�X�g���{
        result = target.toPropertyName("aaa[@name='bbb'][10]");

        // ����
        assertEquals("aaa(bbb)[9]", result);
        assertEquals("aaa[@name='bbb'][10]", target.isMapObjectArg1.get(0));
        assertEquals("aaa[@name='bbb'][10]", target.extractMapKeyArg1.get(0));
        assertEquals("aaa[@name='bbb'][10]", target.isMapAttributeArg1.get(0));
        assertEquals("aaa[@name='bbb'][10]", target.extractMapAttributeNameArg1.get(0));
        assertEquals("aaa[@name='bbb'][10]", target.extractDecrementIndexArg1.get(0));
    }

    /**
     * testToPropertyName04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) xpath:"aaa"<br>
     *         (���) �X�^�uisMapObject()�̖߂�l:false<br>
     *         (���) �X�^�uisMapAttribute()�̖߂�l:false<br>
     *         (���) �X�^�uextractAttributeName()�̖߂�l:"aaa"<br>
     *         (���) �X�^�uextractDecrementIndex()�̖߂�l:""<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"aaa"<br>
     *         (��ԕω�) isMapObject()�̈���:"aaa"<br>
     *         (��ԕω�) isMapAttribute()�̈���:"aaa"<br>
     *         (��ԕω�) extractAttributeName()�̈���:"aaa"<br>
     *         (��ԕω�) extractDecrementIndex()�̈���:"aaa"<br>
     *         
     * <br>
     * �ybean/�v���~�e�B�u�E�K�w�Ȃ��̎����z<br>
     * �z�肵�����͂̏ꍇ�̎����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToPropertyName04() throws Exception {
        // �O����
        String result = null;
        JXPathIndexedBeanWrapperImplStub04 target =
            new JXPathIndexedBeanWrapperImplStub04("");
        target.isMapObjectReturnValue = false;
        target.isMapAttributeReturnValue = false;
        target.extractDecrementIndexReturnValue = "";

        // �e�X�g���{
        result = target.toPropertyName("aaa");

        // ����
        assertEquals("aaa", result);
        assertEquals("aaa", target.isMapObjectArg1.get(0));
        assertEquals("aaa", target.isMapAttributeArg1.get(0));
        assertEquals("aaa", target.extractAttributeNameArg1.get(0));
        assertEquals("aaa", target.extractDecrementIndexArg1.get(0));
    }

    /**
     * testToPropertyName05()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) xpath:"aaa[100]"<br>
     *         (���) �X�^�uisMapObject()�̖߂�l:false<br>
     *         (���) �X�^�uisMapAttribute()�̖߂�l:false<br>
     *         (���) �X�^�uextractAttributeName()�̖߂�l:"aaa"<br>
     *         (���) �X�^�uextractDecrementIndex()�̖߂�l:"[99]"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"aaa[99]"<br>
     *         (��ԕω�) isMapObject()�̈���:"aaa[100]"<br>
     *         (��ԕω�) isMapAttribute()�̈���:"aaa[100]"<br>
     *         (��ԕω�) extractAttributeName()�̈���:"aaa[100]"<br>
     *         (��ԕω�) extractDecrementIndex()�̈���:"aaa[100]"<br>
     *         
     * <br>
     * �ybean/�v���~�e�B�u�z��E�K�w�Ȃ��̎����z<br>
     * �z�肵�����͂̏ꍇ�̎����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToPropertyName05() throws Exception {
        // �O����
        String result = null;
        JXPathIndexedBeanWrapperImplStub04 target =
            new JXPathIndexedBeanWrapperImplStub04("");
        target.isMapObjectReturnValue = false;
        target.isMapAttributeReturnValue = false;
        target.extractDecrementIndexReturnValue = "[99]";

        // �e�X�g���{
        result = target.toPropertyName("aaa[100]");

        // ����
        assertEquals("aaa[99]", result);
        assertEquals("aaa[100]", target.isMapObjectArg1.get(0));
        assertEquals("aaa[100]", target.isMapAttributeArg1.get(0));
        assertEquals("aaa[100]", target.extractAttributeNameArg1.get(0));
        assertEquals("aaa[100]", target.extractDecrementIndexArg1.get(0));
    }

    /**
     * testToPropertyName06()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) xpath:"aaa[@name='bbb']/ddd[@name='eee']"<br>
     *         (���) �X�^�uisMapObject()�̖߂�l:false<br>
     *         (���) �X�^�uextractMapKey()�̖߂�l:1���: "bbb"<br>
     *                2���: "eee"<br>
     *         (���) �X�^�uisMapAttribute()�̖߂�l:true<br>
     *         (���) �X�^�uextractMapAttributeName()�̖߂�l:1���: "aaa"<br>
     *                2���: "ddd"<br>
     *         (���) �X�^�uextractDecrementIndex()�̖߂�l:""<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"aaa(bbb).ddd(eee)"<br>
     *         (��ԕω�) isMapObject()�̈���:1���: "aaa[@name='bbb']"<br>
     *                    2���: "ddd[@name='eee']"<br>
     *         (��ԕω�) extractMapKey()�̈���:1���: "aaa[@name='bbb']"<br>
     *                    2���: "ddd[@name='eee']"<br>
     *         (��ԕω�) isMapAttribute()�̈���:1���: "aaa[@name='bbb']"<br>
     *                    2���: "ddd[@name='eee']"<br>
     *         (��ԕω�) extractMapAttributeName()�̈���:1���: "aaa[@name='bbb']"<br>
     *                    2���: "ddd[@name='eee']"<br>
     *         (��ԕω�) extractDecrementIndex()�̈���:1���: "aaa[@name='bbb']"<br>
     *                    2���: "ddd[@name='eee']"<br>
     *         
     * <br>
     * �yMap�����E�K�w����̎����z<br>
     * �z�肵�����͂̏ꍇ�̎����B�K�w�Ԃ̋�؂蕶������p�^�[���B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToPropertyName06() throws Exception {
        // �O����
        String result = null;
        JXPathIndexedBeanWrapperImplStub04 target =
            new JXPathIndexedBeanWrapperImplStub04("");
        target.isMapObjectReturnValue = false;
        target.isMapAttributeReturnValue = true;
        target.extractDecrementIndexReturnValue = "";

        // �e�X�g���{
        result = target.toPropertyName("aaa[@name='bbb']/ddd[@name='eee']");

        // ����
        assertEquals("aaa(bbb).ddd(eee)", result);
        assertEquals("aaa[@name='bbb']", target.isMapObjectArg1.get(0));
        assertEquals("aaa[@name='bbb']", target.extractMapKeyArg1.get(0));
        assertEquals("ddd[@name='eee']", target.extractMapKeyArg1.get(1));
        assertEquals("aaa[@name='bbb']", target.isMapAttributeArg1.get(0));
        assertEquals("ddd[@name='eee']", target.isMapAttributeArg1.get(1));
        assertEquals("aaa[@name='bbb']", target.extractMapAttributeNameArg1.get(0));
        assertEquals("ddd[@name='eee']", target.extractMapAttributeNameArg1.get(1));
        assertEquals("aaa[@name='bbb']", target.extractDecrementIndexArg1.get(0));
        assertEquals("ddd[@name='eee']", target.extractDecrementIndexArg1.get(1));
    }

    /**
     * testToPropertyName07()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) xpath:""<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException("XPath is null or blank.")<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    XPath is null or blank.<br>
     *         
     * <br>
     * xpath���󕶎��̏ꍇ�̎����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testToPropertyName07() throws Exception {
        // �O����
        JXPathIndexedBeanWrapperImpl target =
            new JXPathIndexedBeanWrapperImpl("");

        // �e�X�g���{
        try {
            target.toPropertyName("");
            fail();

        } catch (IllegalArgumentException e) {
            // ����
            assertEquals("XPath is null or blank.", e.getMessage());
            assertTrue(LogUTUtil.checkError("XPath is null or blank."));
        }
    }

    /**
     * testExtractAttributeName01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) node:"aaa[bbb]"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"aaa"<br>
     *         
     * <br>
     * node�̓��e�� �l�X�g�Ȃ��̑z�肵���`���̏ꍇ�̎����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExtractAttributeName01() throws Exception {
        // �O����
        String result = null;
        JXPathIndexedBeanWrapperImpl target =
            new JXPathIndexedBeanWrapperImpl("");

        // �e�X�g���{
        result = target.extractAttributeName("aaa[bbb]");

        // ����
        assertEquals("aaa", result);
    }

    /**
     * testExtractAttributeName02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) node:"aaa[bbb][ddd]"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"aaa[bbb]"<br>
     *         
     * <br>
     * node�̓��e���l�X�g�̂��� �z�肵���`���̏ꍇ�̎����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExtractAttributeName02() throws Exception {
        // �O����
        String result = null;
        JXPathIndexedBeanWrapperImpl target =
            new JXPathIndexedBeanWrapperImpl("");

        // �e�X�g���{
        result = target.extractAttributeName("aaa[bbb][ddd]");

        // ����
        assertEquals("aaa[bbb]", result);
    }

    /**
     * testExtractAttributeName03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) node:"aaa.ccc.eee"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"aaa.ccc.eee"<br>
     *         
     * <br>
     * node��"["���Ȃ��ꍇ�̎����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExtractAttributeName03() throws Exception {
        // �O����
        String result = null;
        JXPathIndexedBeanWrapperImpl target =
            new JXPathIndexedBeanWrapperImpl("");

        // �e�X�g���{
        result = target.extractAttributeName("aaa.ccc.eee");

        // ����
        assertEquals("aaa.ccc.eee", result);
    }

    /**
     * testExtractAttributeName04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) node:""<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:""<br>
     *         
     * <br>
     * node���󕶎��̏ꍇ�̎����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExtractAttributeName04() throws Exception {
        // �O����
        String result = null;
        JXPathIndexedBeanWrapperImpl target =
            new JXPathIndexedBeanWrapperImpl("");

        // �e�X�g���{
        result = target.extractAttributeName("");

        // ����
        assertEquals("", result);
    }

    /**
     * testExtractMapAttributeName01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) node:"aaa[bbb]"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"aaa"<br>
     *         
     * <br>
     * node�̓��e�� �z�肵���`���̏ꍇ�̎����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExtractMapAttributeName01() throws Exception {
        // �O����
        String result = null;
        JXPathIndexedBeanWrapperImpl target =
            new JXPathIndexedBeanWrapperImpl("");

        // �e�X�g���{
        result = target.extractMapAttributeName("aaa[bbb]");

        // ����
        assertEquals("aaa", result);
    }

    /**
     * testExtractMapAttributeName02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) node:"[bbb]"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:""<br>
     *         
     * <br>
     * node�̑��������Ȃ��ꍇ�̎����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExtractMapAttributeName02() throws Exception {
        // �O����
        String result = null;
        JXPathIndexedBeanWrapperImpl target =
            new JXPathIndexedBeanWrapperImpl("");

        // �e�X�g���{
        result = target.extractMapAttributeName("[bbb]");

        // ����
        assertEquals("", result);
    }

    /**
     * testExtractMapAttributeName03()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) node:"aaa"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException("Cannot get Map attribute. Invalid property name. 'aaa'")<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    Cannot get Map attribute. Invalid property name. 'aaa'<br>
     *         
     * <br>
     * node��"["���Ȃ��ꍇ�̎����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExtractMapAttributeName03() throws Exception {
        // �O����
        JXPathIndexedBeanWrapperImpl target =
            new JXPathIndexedBeanWrapperImpl("");

        // �e�X�g���{
        try {
            target.extractMapAttributeName("aaa");
            fail();

        } catch (IllegalArgumentException e) {
            // ����
            assertEquals("Cannot get Map attribute. Invalid property name. 'aaa'", e.getMessage());
            assertTrue(LogUTUtil.checkError("Cannot get Map attribute. Invalid property name. 'aaa'"));
        }
    }

    /**
     * testExtractMapAttributeName04()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) node:""<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgmentException("Cannot get Map attribute. Invalid property name. ''")<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    Cannot get Map attribute. Invalid property name. ''<br>
     *         
     * <br>
     * node���󕶎��̏ꍇ�̎����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExtractMapAttributeName04() throws Exception {
        // �O����
        JXPathIndexedBeanWrapperImpl target =
            new JXPathIndexedBeanWrapperImpl("");

        // �e�X�g���{
        try {
            target.extractMapAttributeName("");
            fail();

        } catch (IllegalArgumentException e) {
            // ����
            assertEquals("Cannot get Map attribute. Invalid property name. ''", e.getMessage());
            assertTrue(LogUTUtil.checkError("Cannot get Map attribute. Invalid property name. ''"));
        }
    }

    /**
     * testExtractMapKey01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) node:"aaa[@name='bbb']"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"bbb"<br>
     *         
     * <br>
     * node�̓��e�� �z�肵���`���̏ꍇ�̎����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExtractMapKey01() throws Exception {
        // �O����
        String result = null;
        JXPathIndexedBeanWrapperImpl target =
            new JXPathIndexedBeanWrapperImpl("");

        // �e�X�g���{
        result = target.extractMapKey("aaa[@name='bbb']");

        // ����
        assertEquals("bbb", result);
    }

    /**
     * testExtractMapKey02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) node:"aaa[@name='']"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:""<br>
     *         
     * <br>
     * node��Map�L�[���󕶎��̏ꍇ�̎����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExtractMapKey02() throws Exception {
        // �O����
        String result = null;
        JXPathIndexedBeanWrapperImpl target =
            new JXPathIndexedBeanWrapperImpl("");

        // �e�X�g���{
        result = target.extractMapKey("aaa[@name='']");

        // ����
        assertEquals("", result);
    }

    /**
     * testExtractMapKey03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) node:"aaa[@name='bbb'"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException("Cannot get Map key. Invalid property name. 'aaa[@name='bbb''")<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    Cannot get Map key. Invalid property name. 'aaa[@name='bbb''<br>
     *         
     * <br>
     * node��"]"���Ȃ��ꍇ�̎����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExtractMapKey03() throws Exception {
        // �O����
        JXPathIndexedBeanWrapperImpl target =
            new JXPathIndexedBeanWrapperImpl("");

        // �e�X�g���{
        try {
            target.extractMapKey("aaa[@name='bbb'");
            fail();

        } catch (IllegalArgumentException e) {
            // ����
            assertEquals("Cannot get Map key. Invalid property name. 'aaa[@name='bbb''", e.getMessage());
            assertTrue(LogUTUtil.checkError("Cannot get Map key. Invalid property name. 'aaa[@name='bbb''"));
        }
    }

    /**
     * testExtractMapKey04()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) node:"aaa@name='bbb']"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException("Cannot get Map key. Invalid property name. 'aaa@name='bbb']'")<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    Cannot get Map key. Invalid property name. 'aaa@name='bbb']'<br>
     *         
     * <br>
     * node��"["���Ȃ��ꍇ�̎����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExtractMapKey04() throws Exception {
        // �O����
        JXPathIndexedBeanWrapperImpl target =
            new JXPathIndexedBeanWrapperImpl("");

        // �e�X�g���{
        try {
            target.extractMapKey("aaa@name='bbb']");
            fail();

        } catch (IllegalArgumentException e) {
            // ����
            assertEquals("Cannot get Map key. Invalid property name. 'aaa@name='bbb']'", e.getMessage());
            assertTrue(LogUTUtil.checkError("Cannot get Map key. Invalid property name. 'aaa@name='bbb']'"));
        }
    }

    /**
     * testExtractMapKey05()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) node:"aaa"<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException("Cannot get Map key. Invalid property name. 'aaa'")<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    Cannot get Map key. Invalid property name. 'aaa'<br>
     *         
     * <br>
     * node��[]���Ȃ��ꍇ�̎����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExtractMapKey05() throws Exception {
        // �O����
        JXPathIndexedBeanWrapperImpl target =
            new JXPathIndexedBeanWrapperImpl("");

        // �e�X�g���{
        try {
            target.extractMapKey("aaa");
            fail();

        } catch (IllegalArgumentException e) {
            // ����
            assertEquals("Cannot get Map key. Invalid property name. 'aaa'", e.getMessage());
            assertTrue(LogUTUtil.checkError("Cannot get Map key. Invalid property name. 'aaa'"));
        }
    }

    /**
     * testExtractMapKey06()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) node:"aaa]@name='bbb'["<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException("Cannot get Map key. Invalid property name. 'aaa]@name='bbb'['")<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    Cannot get Map key. Invalid property name. 'aaa]@name='bbb'['<br>
     *         
     * <br>
     * node��[]���t�����̏ꍇ�̎����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExtractMapKey06() throws Exception {
        // �O����
        JXPathIndexedBeanWrapperImpl target =
            new JXPathIndexedBeanWrapperImpl("");

        // �e�X�g���{
        try {
            target.extractMapKey("aaa]@name='bbb'[");
            fail();

        } catch (IllegalArgumentException e) {
            // ����
            assertEquals("Cannot get Map key. Invalid property name. 'aaa]@name='bbb'['", e.getMessage());
            assertTrue(LogUTUtil.checkError("Cannot get Map key. Invalid property name. 'aaa]@name='bbb'['"));
        }
    }

    /**
     * testExtractMapKey07()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) node:""<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException("Cannot get Map key. Invalid property name. ''")<br>
     *         (��ԕω�) ���O:���O���x���F�G���[<br>
     *                    Cannot get Map key. Invalid property name. ''<br>
     *         
     * <br>
     * node���󕶎��̏ꍇ�̎����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExtractMapKey07() throws Exception {
        // �O����
        JXPathIndexedBeanWrapperImpl target =
            new JXPathIndexedBeanWrapperImpl("");

        // �e�X�g���{
        try {
            target.extractMapKey("");
            fail();

        } catch (IllegalArgumentException e) {
            // ����
            assertEquals("Cannot get Map key. Invalid property name. ''", e.getMessage());
            assertTrue(LogUTUtil.checkError("Cannot get Map key. Invalid property name. ''"));
        }
    }

    /**
     * testExtractDecrementIndex01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) node:"[3]"<br>
     *         (���) �X�^�uextractIncrementIndex()�̖߂�l:"test[3]&-1"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) String:"[3]:-1"<br>
     *         (��ԕω�) extractIncrementIndex()<br>
     *                    �̈���:property="[3]"<br>
     *                    increment=-1<br>
     *                    (property+":"+increment�𓖃��\�b�h�̖߂�l�Ƃ���)<br>
     *         
     * <br>
     * extractIncrementIndex()�𐳂����Ăяo���Ă��邱�Ƃ̎����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExtractDecrementIndex01() throws Exception {
        // �O����
        String result = null;
        JXPathIndexedBeanWrapperImplStub02 target =
            new JXPathIndexedBeanWrapperImplStub02("");

        // �e�X�g���{
        result = target.extractDecrementIndex("[3]");

        // ����
        assertEquals("[3]:-1", result);
    }

    /**
     * testIsMapAttribute01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) node:"z[@name]"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         
     * <br>
     * Map�I�u�W�F�N�g�̏ꍇ�̎����Bnode�̓r����"[@name"
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsMapAttribute01() throws Exception {
        // �O����
        boolean result = false;
        JXPathIndexedBeanWrapperImpl target =
            new JXPathIndexedBeanWrapperImpl("");

        // �e�X�g���{
        result = target.isMapAttribute("z[@name]");

        // ����
        assertTrue(result);
    }

    /**
     * testIsMapAttribute02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) node:"@name"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         
     * <br>
     * Map�I�u�W�F�N�g�ȊO�̏ꍇ�̎����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsMapAttribute02() throws Exception {
        // �O����
        boolean result = true;
        JXPathIndexedBeanWrapperImpl target =
            new JXPathIndexedBeanWrapperImpl("");

        // �e�X�g���{
        result = target.isMapAttribute("@name");

        // ����
        assertFalse(result);
    }

    /**
     * testIsMapAttribute03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) node:""<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         
     * <br>
     * node���󕶎���̏ꍇ�̎����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsMapAttribute03() throws Exception {
        // �O����
        boolean result = true;
        JXPathIndexedBeanWrapperImpl target =
            new JXPathIndexedBeanWrapperImpl("");

        // �e�X�g���{
        result = target.isMapAttribute("");

        // ����
        assertFalse(result);
    }

    /**
     * testIsMapObject01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) node:".[@name"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *         
     * <br>
     * Map�I�u�W�F�N�g�̏ꍇ�̎����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsMapObject01() throws Exception {
        // �O����
        boolean result = false;
        JXPathIndexedBeanWrapperImpl target =
            new JXPathIndexedBeanWrapperImpl("");

        // �e�X�g���{
        result = target.isMapObject(".[@name");

        // ����
        assertTrue(result);
    }

    /**
     * testIsMapObject02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) node:"a.[@name=]"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         
     * <br>
     * Map�I�u�W�F�N�g�ȊO�̏ꍇ�̎����B".[@name"���擪����n�܂�Ȃ��B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsMapObject02() throws Exception {
        // �O����
        boolean result = true;
        JXPathIndexedBeanWrapperImpl target =
            new JXPathIndexedBeanWrapperImpl("");

        // �e�X�g���{
        result = target.isMapObject("a.[@name=]");

        // ����
        assertFalse(result);
    }

    /**
     * testIsMapObject03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) node:""<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *         
     * <br>
     * node���󕶎���̏ꍇ�̎����B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsMapObject03() throws Exception {
        // �O����
        boolean result = true;
        JXPathIndexedBeanWrapperImpl target =
            new JXPathIndexedBeanWrapperImpl("");

        // �e�X�g���{
        result = target.isMapObject("");

        // ����
        assertFalse(result);
    }

}
