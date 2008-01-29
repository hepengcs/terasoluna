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

package jp.terasoluna.fw.validation;

import java.math.BigDecimal;
import jp.terasoluna.utlib.PropertyTestCase;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.validation.ValidationUtil;

/**
 * {@link jp.terasoluna.fw.validation.ValidationUtil} �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 *
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * ���؃��W�b�N�̃��[�e�B���e�B�N���X�B
 * <p>
 *
 * @see jp.terasoluna.fw.validation.ValidationUtil
 */
public class ValidationUtilTest02 extends PropertyTestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     *
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ValidationUtilTest02.class);
    }

    /**
     * �������������s���B
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see jp.terasoluna.utlib.spring.PropertyTestCase#setUpData()
     */
    @Override
    protected void setUpData() throws Exception {
        UTUtil.setPrivateField(ValidationUtil.class, "hankakuKanaList",
        "����������������������¯�������������������֬�������ܦ��ް�����");
    UTUtil.setPrivateField(ValidationUtil.class, "zenkakuKanaList",
        "�A�C�E���G�I�@�B�D�F�H�J�L�N�P�R�����K�M�O�Q�S�T�V�X�Z�\" +
        "�U�W�Y�[�]�^�`�c�e�g�_�a�d�f�h�i�j�k�l�m�n�q�t�w�z" +
        "�o�r�u�x�{�p�s�v�y�|�}�~����������������������������" +
        "�����������b���[");
}

    /**
     * �I���������s���B
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see jp.terasoluna.utlib.spring.PropertyTestCase#cleanUpData()
     */
    @Override
    protected void cleanUpData() throws Exception {
    }

    /**
     * �R���X�g���N�^�B
     *
     * @param name ���̃e�X�g�P�[�X�̖��O�B
     */
    public ValidationUtilTest02(String name) {
        super(name);
    }

    /**
     * testMatchRegexp01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) value:null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * ����value��null�̏ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMatchRegexp01() throws Exception {
        // �e�X�g���{�E����
        assertTrue(ValidationUtil.matchRegexp(null, "^([0-9])*$"));
    }

    /**
     * testMatchRegexp02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) value:""<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * ����value���󕶎��̏ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMatchRegexp02() throws Exception {
        // �e�X�g���{�E����
        assertTrue(ValidationUtil.matchRegexp("", "^([0-9])*$"));
    }

    /**
     * testMatchRegexp03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:"123"<br>
     *         (����) mask:"^([0-9])*$"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * ����value������mask�̐��K�\���ɊY������ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B<br>
     * ���`�F�b�N������GenericValidator.matchRegexp(String, String)���s�Ȃ����߁A�e���K�\���ɑ΂���ڍׂȊm�F�͍s�Ȃ�Ȃ�
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMatchRegexp03() throws Exception {
        // �e�X�g���{�E����
        assertTrue(ValidationUtil.matchRegexp("123", "^([0-9])*$"));
    }

    /**
     * testMatchRegexp04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:"a12"<br>
     *         (����) mask:"^([0-9])*$"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *
     * <br>
     * ����value������mask�̐��K�\���ɊY�����Ȃ��ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B<br>
     * ���`�F�b�N������GenericValidator.matchRegexp(String, String)���s�Ȃ����߁A�e���K�\���ɑ΂���ڍׂȊm�F�͍s�Ȃ�Ȃ�
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testMatchRegexp04() throws Exception {
        // �e�X�g���{�E����
        assertFalse(ValidationUtil.matchRegexp("a12", "^([0-9])*$"));
    }

    /**
     * testIsAlphaNumericString01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) value:null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * ����value��null�̏ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsAlphaNumericString01() throws Exception {
        // �e�X�g���{�E����
        assertTrue(ValidationUtil.isAlphaNumericString(null));
    }

    /**
     * testIsAlphaNumericString02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) value:""<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * ����value���󕶎��̏ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsAlphaNumericString02() throws Exception {
        // �e�X�g���{�E����
        assertTrue(ValidationUtil.isAlphaNumericString(""));
    }

    /**
     * testIsAlphaNumericString03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:"0aA"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * ����value�����p�p�����݂̂ō\�������ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsAlphaNumericString03() throws Exception {
        // �e�X�g���{�E����
        assertTrue(ValidationUtil.isAlphaNumericString("0aA"));
    }

    /**
     * testIsAlphaNumericString04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:"Zg3%"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *
     * <br>
     * ����value�����p�p�����ȊO���܂ޏꍇ�Afalse���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsAlphaNumericString04() throws Exception {
        // �e�X�g���{�E����
        assertFalse(ValidationUtil.isAlphaNumericString("Zg3%"));
    }

    /**
     * testIsUpperAlphaNumericString01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) value:null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * ����value��null�̏ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsUpperAlphaNumericString01() throws Exception {
        // �e�X�g���{�E����
        assertTrue(ValidationUtil.isUpperAlphaNumericString(null));
    }

    /**
     * testIsUpperAlphaNumericString02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) value:""<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * ����value���󕶎��̏ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsUpperAlphaNumericString02() throws Exception {
        // �e�X�g���{�E����
        assertTrue(ValidationUtil.isUpperAlphaNumericString(""));
    }

    /**
     * testIsUpperAlphaNumericString03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:"A0"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * ����value���啶�����p�p�����݂̂ō\�������ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsUpperAlphaNumericString03() throws Exception {
        // �e�X�g���{�E����
        assertTrue(ValidationUtil.isUpperAlphaNumericString("A0"));
    }

    /**
     * testIsUpperAlphaNumericString04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:"Aa0"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *
     * <br>
     * ����value���啶�����p�p�����ȊO���܂ޏꍇ�Afalse���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsUpperAlphaNumericString04() throws Exception {
        // �e�X�g���{�E����
        assertFalse(ValidationUtil.isUpperAlphaNumericString("Aa0"));
    }

    /**
     * testIsNumericString01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) value:null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * ����value��null�̏ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsNumericString01() throws Exception {
        // �e�X�g���{�E����
        assertTrue(ValidationUtil.isNumericString(null));
    }

    /**
     * testIsNumericString02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) value:""<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * ����value���󕶎��̏ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsNumericString02() throws Exception {
        // �e�X�g���{�E����
        assertTrue(ValidationUtil.isNumericString(""));
    }

    /**
     * testIsNumericString03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:"9876"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * ����value�������݂̂ō\�������ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsNumericString03() throws Exception {
        // �e�X�g���{�E����
        assertTrue(ValidationUtil.isNumericString("9876"));
    }

    /**
     * testIsNumericString04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:"Aa0"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *
     * <br>
     * ����value�������ȊO���܂ޏꍇ�Afalse���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsNumericString04() throws Exception {
        // �e�X�g���{�E����
        assertFalse(ValidationUtil.isNumericString("Aa0"));
    }

    /**
     * testIsNumber01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) value:null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * value��null�̏ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsNumber01() throws Exception {
        // �e�X�g���{�E����
        assertTrue(ValidationUtil.isNumber(null, 3, false, 3, false));
    }

    /**
     * testIsNumber02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:BigDecimal("123.45")<br>
     *         (����) integerLength:1<br>
     *         (����) isAccordedInteger:false<br>
     *         (����) scaleLength:3<br>
     *         (����) isAccordedScale:false<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *
     * <br>
     * isAccordedInteger��false�ŁA������������integerLength���傫���ꍇ�Afalse���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsNumber02() throws Exception {
        // �e�X�g���{�E����
        assertFalse(ValidationUtil.isNumber(new BigDecimal("123.45"), 1, false,
                3, false));
    }

    /**
     * testIsNumber03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:BigDecimal("123.45")<br>
     *         (����) integerLength:5<br>
     *         (����) isAccordedInteger:true<br>
     *         (����) scaleLength:3<br>
     *         (����) isAccordedScale:false<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *
     * <br>
     * isAccordedInteger��true�ŁA������������integerLength�Ɠ������Ȃ��ꍇ�Afalse���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsNumber03() throws Exception {
        // �e�X�g���{�E����
        assertFalse(ValidationUtil.isNumber(new BigDecimal("123.45"), 5, true,
                3, false));
    }

    /**
     * testIsNumber04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:BigDecimal("123.45")<br>
     *         (����) integerLength:5<br>
     *         (����) isAccordedInteger:false<br>
     *         (����) scaleLength:1<br>
     *         (����) isAccordedScale:false<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *
     * <br>
     * �����������͏����𖞂����AAccordedScale��false�ŁA������������scaleLength���傫���ꍇ�Afalse���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsNumber04() throws Exception {
        // �e�X�g���{�E����
        assertFalse(ValidationUtil.isNumber(new BigDecimal("123.45"), 5, false,
                1, false));
    }

    /**
     * testIsNumber05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:BigDecimal("123.45")<br>
     *         (����) integerLength:3<br>
     *         (����) isAccordedInteger:true<br>
     *         (����) scaleLength:3<br>
     *         (����) isAccordedScale:true<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *
     * <br>
     * �����������͏����𖞂����AisAccordedScale��true�ŁA������������scaleLength�Ɠ������Ȃ��ꍇ�Afalse���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsNumber05() throws Exception {
        // �e�X�g���{�E����
        assertFalse(ValidationUtil.isNumber(new BigDecimal("123.45"), 3, true,
                3, true));
    }

    /**
     * testIsNumber06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:BigDecimal("123.45")<br>
     *         (����) integerLength:5<br>
     *         (����) isAccordedInteger:false<br>
     *         (����) scaleLength:3<br>
     *         (����) isAccordedScale:false<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * isAccordedInteger�EscaleLength��false�̏ꍇ�A�����������E������������integerLength�EscaleLength��菬�������true���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsNumber06() throws Exception {
        // �e�X�g���{�E����
        assertTrue(ValidationUtil.isNumber(new BigDecimal("123.45"), 5, false,
                3, false));
    }

    /**
     * testIsNumber07()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:BigDecimal("123.45")<br>
     *         (����) integerLength:3<br>
     *         (����) isAccordedInteger:true<br>
     *         (����) scaleLength:2<br>
     *         (����) isAccordedScale:true<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * isAccordedInteger�EscaleLength��true�̏ꍇ�A�����������E������������integerLength�EscaleLength�Ɠ��������true���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsNumber07() throws Exception {
        // �e�X�g���{�E����
        assertTrue(ValidationUtil.isNumber(new BigDecimal("123.45"), 3, true,
                2, true));
    }
    /**
     * testIsNumber08()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:BigDecimal("123.45")<br>
     *         (����) integerLength:3<br>
     *         (����) isAccordedInteger:false<br>
     *         (����) scaleLength:2<br>
     *         (����) isAccordedScale:false<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * isAccordedInteger�EscaleLength��false�̏ꍇ�A�����������E������������integerLength�EscaleLength�Ɠ��������true���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsNumber08() throws Exception {
        // �e�X�g���{�E����
        assertTrue(ValidationUtil.isNumber(new BigDecimal("123.45"), 3, false,
                2, false));
    }

    /**
     * testIsNumber09()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:BigDecimal("123.00000")<br>
     *         (����) integerLength:3<br>
     *         (����) isAccordedInteger:false<br>
     *         (����) scaleLength:2<br>
     *         (����) isAccordedScale:false<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *
     * <br>
     * �����������͏����𖞂����AAccordedScale��false�ŁA������������scaleLength���傫���ꍇ�Afalse���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsNumber09() throws Exception {
        // �e�X�g���{�E����
        assertFalse(ValidationUtil.isNumber(new BigDecimal("123.00000"), 3,
                false, 2, false));
    }


}
