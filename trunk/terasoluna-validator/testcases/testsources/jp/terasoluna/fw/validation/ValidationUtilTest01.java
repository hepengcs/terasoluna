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
public class ValidationUtilTest01 extends PropertyTestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     *
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ValidationUtilTest01.class);
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
    public ValidationUtilTest01(String name) {
        super(name);
    }

    /**
     * testSetHankakuKanaList01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(���) hankakuKanaList:����������������������¯�������������������֬�������ܦ��ް�����<br>
     *         (���) �v���p�e�B�t�@�C��:validation.hankaku.kana.list�����݂��Ȃ����ƁB<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) hankakuKanaList:����������������������¯�������������������֬�������ܦ��ް�����<br>
     *
     * <br>
     * validation.hankaku.kana.list�ɒl�����ݒ�̏ꍇ�AhankakuKanaList�̓f�t�H���g�̂܂܂ł��邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetHankakuKanaList01() throws Exception {
        // �e�X�g���{
        ValidationUtil.setHankakuKanaList();

        // ����
        assertEquals(
                "����������������������¯�������������������֬�������ܦ��ް�����",
                UTUtil.getPrivateField(ValidationUtil.class, "hankakuKanaList"));
    }

    /**
     * testSetHankakuKanaList02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(���) hankakuKanaList:����������������������¯�������������������֬�������ܦ��ް�����<br>
     *         (���) �v���p�e�B�t�@�C��:validation.hankaku.kana.list=�<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) hankakuKanaList:�<br>
     *
     * <br>
     * validation.hankaku.kana.list�ɒl���ݒ肳��Ă����ꍇ�AhankakuKanaList�͐ݒ�l�ƂȂ邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetHankakuKanaList02() throws Exception {
        // �O����
        addProperty("validation.hankaku.kana.list", "�");

        // �e�X�g���{
        ValidationUtil.setHankakuKanaList();

        // ����
        assertEquals("�", UTUtil.getPrivateField(ValidationUtil.class,
                "hankakuKanaList"));
    }

    /**
     * testSetHankakuKanaList03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(���) hankakuKanaList:����������������������¯�������������������֬�������ܦ��ް�����<br>
     *         (���) �v���p�e�B�t�@�C��:validation.hankaku.kana.list=<br>
     *                ���󕶎�<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) hankakuKanaList:""<br>
     *
     * <br>
     * validation.hankaku.kana.list�ɒl���ݒ肳��Ă����ꍇ�AhankakuKanaList�͐ݒ�l�ƂȂ邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetHankakuKanaList03() throws Exception {
        // �O����
        addProperty("validation.hankaku.kana.list", "");

        // �e�X�g���{
        ValidationUtil.setHankakuKanaList();

        // ����
        assertEquals("", UTUtil.getPrivateField(ValidationUtil.class,
                "hankakuKanaList"));
    }

    /**
     * testSetZenkakuKanaList01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(���) zenkakuKanaList:�A�C�E���G�I�@�B�D�F�H�J�L�N�P�R�����K�M�O�Q�S�T�V�X�Z�\�U�W�Y�[�]�^�`�c�e�g�_�a�d�f�h�i�j�k�l�m�n�q�t�w�z�o�r�u�x�{�p�s�v�y�|�}�~���������������������������������������b���[<br>
     *         (���) �v���p�e�B�t�@�C��:validation.zenkaku.kana.list�����݂��Ȃ����ƁB<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) zenkakuKanaList:�A�C�E���G�I�@�B�D�F�H�J�L�N�P�R�����K�M�O�Q�S�T�V�X�Z�\�U�W�Y�[�]�^�`�c�e�g�_�a�d�f�h�i�j�k�l�m�n�q�t�w�z�o�r�u�x�{�p�s�v�y�|�}�~���������������������������������������b���[<br>
     *
     * <br>
     * validation.zenkaku.kana.list�ɒl�����ݒ�̏ꍇ�AzenkakuKanaList�̓f�t�H���g�̂܂܂ł��邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetZenkakuKanaList01() throws Exception {
        // �e�X�g���{
        ValidationUtil.setZenkakuKanaList();

        // ����
        assertEquals(
                "�A�C�E���G�I�@�B�D�F�H�J�L�N�P�R�����K�M�O�Q�S�T�V�X�Z�\�U�W�Y�[�]�^�`�c�e�g�_�a�d�f�h�i�j�k�l�m�n�q�t�w�z�o�r�u�x�{�p�s�v�y�|�}�~���������������������������������������b���[",
                UTUtil.getPrivateField(ValidationUtil.class, "zenkakuKanaList"));
    }

    /**
     * testSetZenkakuKanaList02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(���) zenkakuKanaList:�A�C�E���G�I�@�B�D�F�H�J�L�N�P�R�����K�M�O�Q�S�T�V�X�Z�\�U�W�Y�[�]�^�`�c�e�g�_�a�d�f�h�i�j�k�l�m�n�q�t�w�z�o�r�u�x�{�p�s�v�y�|�}�~���������������������������������������b���[<br>
     *         (���) �v���p�e�B�t�@�C��:validation.zenkaku.kana.list=�A<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) zenkakuKanaList:�A<br>
     *
     * <br>
     * validation.zenkaku.kana.list�ɒl���ݒ肳��Ă����ꍇ�AzenkakuKanaList�͐ݒ�l�ƂȂ邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetZenkakuKanaList02() throws Exception {
        // �O����
        addProperty("validation.zenkaku.kana.list", "�A");

        // �e�X�g���{
        ValidationUtil.setZenkakuKanaList();

        // ����
        assertEquals("�A", UTUtil.getPrivateField(ValidationUtil.class,
                "zenkakuKanaList"));
    }

    /**
     * testSetZenkakuKanaList03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(���) zenkakuKanaList:�A�C�E���G�I�@�B�D�F�H�J�L�N�P�R�����K�M�O�Q�S�T�V�X�Z�\�U�W�Y�[�]�^�`�c�e�g�_�a�d�f�h�i�j�k�l�m�n�q�t�w�z�o�r�u�x�{�p�s�v�y�|�}�~���������������������������������������b���[<br>
     *         (���) �v���p�e�B�t�@�C��:validation.zenkaku.kana.list=<br>
     *                ���󕶎�<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) zenkakuKanaList:""<br>
     *
     * <br>
     * validation.zenkaku.kana.list�ɒl���ݒ肳��Ă����ꍇ�AzenkakuKanaList�͐ݒ�l�ƂȂ邱�ƁB
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetZenkakuKanaList03() throws Exception {
        // �O����
        addProperty("validation.zenkaku.kana.list", "");

        // �e�X�g���{
        ValidationUtil.setZenkakuKanaList();

        // ����
        assertEquals("", UTUtil.getPrivateField(ValidationUtil.class,
                "zenkakuKanaList"));
    }

    /**
     * testIsHankakuKanaChar01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) c:����������������������¯�������������������֬�������ܦ��ް�����<br>
     *                ���ꕶ�����m�F<br>
     *         (���) hankakuKanaList:����������������������¯�������������������֬�������ܦ��ް�����<br>
     *         (���) �v���p�e�B�t�@�C��:validation.hankaku.kana.list<br>
     *                �����݂��Ȃ����ƁB<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:�S�Ă̕����ɂ���true<br>
     *
     * <br>
     * �����Ɏw�肵��������hankakuKanaList�Ɋ܂܂��ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsHankakuKanaChar01() throws Exception {
        // �O����
        String hankakuKanaList = "����������������������¯�������������������֬�������ܦ��ް�����";

        // �e�X�g���{�E����
        for (int i = 0; i < hankakuKanaList.length(); i++) {
            assertTrue(ValidationUtil.isHankakuKanaChar(hankakuKanaList
                    .charAt(i)));
        }
    }

    /**
     * testIsHankakuKanaChar02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) c:'�'-1<br>
     *                '�'+1<br>
     *                ���ꕶ�����m�F<br>
     *         (���) hankakuKanaList:����������������������¯�������������������֬�������ܦ��ް�����<br>
     *         (���) �v���p�e�B�t�@�C��:validation.hankaku.kana.list<br>
     *                �����݂��Ȃ����ƁB<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:�S�Ă̕����ɂ���false<br>
     *
     * <br>
     * �����Ɏw�肵��������hankakuKanaList�Ɋ܂܂�Ȃ��ꍇ�Afalse���擾�ł��邱�Ƃ��m�F����B�i���p�J�i�̋��E�e�X�g�j
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsHankakuKanaChar02() throws Exception {
        // �O����
        char chStart = '�' - 1;
        char chEnd = '�' + 1;

        // �e�X�g���{�E����
        assertFalse(ValidationUtil.isHankakuKanaChar(chStart));
        assertFalse(ValidationUtil.isHankakuKanaChar(chEnd));
    }

    /**
     * testIsHankakuKanaChar03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) c:'�S'<br>
     *         (���) hankakuKanaList:����������������������¯�������������������֬�������ܦ��ް�����<br>
     *         (���) �v���p�e�B�t�@�C��:validation.hankaku.kana.list<br>
     *                �����݂��Ȃ����ƁB<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *
     * <br>
     * �����Ɏw�肵��������hankakuKanaList�Ɋ܂܂�Ȃ��ꍇ�Afalse���擾�ł��邱�Ƃ��m�F����B�i�S�p�����j
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsHankakuKanaChar03() throws Exception {
        // �O����
        char chZenkaku = '�S';

        // �e�X�g���{�E����
        assertFalse(ValidationUtil.isHankakuKanaChar(chZenkaku));
    }

    /**
     * testIsHankakuChar01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) c:'\u00ff'<br>
     *                '�'<br>
     *                '�'<br>
     *                ���ꕶ�����m�F<br>
     *         (���) hankakuKanaList:����������������������¯�������������������֬�������ܦ��ް�����<br>
     *         (���) �v���p�e�B�t�@�C��:validation.hankaku.kana.list<br>
     *                �����݂��Ȃ����ƁB<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:�S�Ă̕����ɂ���true<br>
     *
     * <br>
     * �����Ɏw�肵�������������R�[�h'\00ff'�ȉ����A"�_�������N�ʁ��}�L���~��"�ł͂Ȃ��AhankakuKanaList�Ɋ܂܂��ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsHankakuChar01() throws Exception {
        // �O����
        char chHankakuMax = '\u00ff';
        char chHankakuKanaStart = '�';
        char chHankakuKanaEnd = '�';

        // �e�X�g���{�E����
        // ���p�������ݒ肳�ꂽ�Ƃ��Atrue���ԋp����邱��
        assertTrue(ValidationUtil.isHankakuChar(chHankakuMax));
        assertTrue(ValidationUtil.isHankakuChar(chHankakuKanaStart));
        assertTrue(ValidationUtil.isHankakuChar(chHankakuKanaEnd));
    }

    /**
     * testIsHankakuChar02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) c:'\u0100'<br>
     *                '�'-1<br>
     *                '�'+1<br>
     *                ���ꕶ�����m�F<br>
     *         (���) hankakuKanaList:����������������������¯�������������������֬�������ܦ��ް�����<br>
     *         (���) �v���p�e�B�t�@�C��:validation.hankaku.kana.list<br>
     *                �����݂��Ȃ����ƁB<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:�S�Ă̕����ɂ���false<br>
     *
     * <br>
     * �����Ɏw�肵�������������R�[�h'\00ff'�ȏ�A�܂��́A"�_�������N�ʁ��}�L���~��"�Ɋ܂܂��A�܂��́AhankakuKanaList�Ɋ܂܂�Ȃ��ꍇ�Afalse���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsHankakuChar02() throws Exception {
        // �O����
        char chUpperff = '\u0100';
        char chKanaStart = '�' - 1;
        char chKanaEnd = '�' + 1;

        // �e�X�g���{�E����
        assertFalse(ValidationUtil.isHankakuChar(chUpperff));
        assertFalse(ValidationUtil.isHankakuChar(chKanaStart));
        assertFalse(ValidationUtil.isHankakuChar(chKanaEnd));
    }

    /**
     * testIsHankakuChar03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) c:'�A'<br>
     *                '�U'<br>
     *                '��'<br>
     *                '��'<br>
     *                '��'<br>
     *                '�`'<br>
     *                '�y'<br>
     *         (���) hankakuKanaList:����������������������¯�������������������֬�������ܦ��ް�����<br>
     *         (���) �v���p�e�B�t�@�C��:validation.hankaku.kana.list<br>
     *                �����݂��Ȃ����ƁB<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *
     * <br>
     * �����Ɏw�肵���������S�p�����ł���ꍇ�Afalse���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsHankakuChar03() throws Exception {
        // �O����
        char[] input = {
            '�A',
            '�U',
            '��',
            '��',
            '��',
            '�`',
            '�y'
        };

        // �e�X�g���{�E����
        // �S�p�������ݒ肳�ꂽ�Ƃ��Afalse���ԋp����邱��
        for (char c : input) {
            assertFalse(ValidationUtil.isHankakuChar(c));
        }
    }

    /**
     * testIsHankakuChar04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) c:"�_�������N�ʁ��}�L���~��"<br>
     *                ���ꕶ�����m�F<br>
     *         (���) hankakuKanaList:����������������������¯�������������������֬�������ܦ��ް�����<br>
     *         (���) �v���p�e�B�t�@�C��:validation.hankaku.kana.list<br>
     *                �����݂��Ȃ����ƁB<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *
     * <br>
     * �����Ɏw�肵�������������R�[�h'\00ff'�ȏ�A�܂��́A"�_�������N�ʁ��}�L���~��"�Ɋ܂܂��A�܂��́AhankakuKanaList�Ɋ܂܂�Ȃ��ꍇ�Afalse���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsHankakuChar04() throws Exception {
        // �O����
        String zenkakuBeginU00List = "�_�������N�ʁ��}�L���~��";

        // �e�X�g���{�E����
        for (int i = 0; i < zenkakuBeginU00List.length(); i++) {
            assertFalse(ValidationUtil.isHankakuChar(zenkakuBeginU00List
                    .charAt(i)));
        }
    }

    /**
     * testIsZenkakuChar01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) c:'\u0100'<br>
     *                '�'-1<br>
     *                '�'+1<br>
     *                ���ꕶ�����m�F<br>
     *         (���) hankakuKanaList:����������������������¯�������������������֬�������ܦ��ް�����<br>
     *         (���) �v���p�e�B�t�@�C��:validation.hankaku.kana.list<br>
     *                �����݂��Ȃ����ƁB<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:�S�Ă̕����ɂ���true<br>
     *
     * <br>
     * �����Ɏw�肵�������������R�[�h'\00ff'���傫���A���A"�_�������N�ʁ��}�L���~��"�Ɋ܂܂�邩�AhankakuKanaList�Ɋ܂܂�Ȃ��ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsZenkakuChar01() throws Exception {
        // �O����
        char chZenkakuMin = '\u0100';
        char chZenkakuKanaStart = '�' - 1;
        char chZenkakuKanaEnd = '�' + 1;

        // �e�X�g���{�E����
        // �S�p�����񂪐ݒ肳�ꂽ�Ƃ��Atrue���ԋp����邱��
        assertTrue(ValidationUtil.isZenkakuChar(chZenkakuMin));
        assertTrue(ValidationUtil.isZenkakuChar(chZenkakuKanaStart));
        assertTrue(ValidationUtil.isZenkakuChar(chZenkakuKanaEnd));
    }

    /**
     * testIsZenkakuChar02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) c:'\u00ff'<br>
     *                '�'<br>
     *                '�'<br>
     *                ���ꕶ�����m�F<br>
     *         (���) hankakuKanaList:����������������������¯�������������������֬�������ܦ��ް�����<br>
     *         (���) �v���p�e�B�t�@�C��:validation.hankaku.kana.list<br>
     *                �����݂��Ȃ����ƁB<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:�S�Ă̕����ɂ���false<br>
     *
     * <br>
     * �����Ɏw�肵�������������R�[�h'\00ff'�ȉ����A"�_�������N�ʁ��}�L���~��"�ł͂Ȃ��AhankakuKanaList�Ɋ܂܂��ꍇ�Afalse���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsZenkakuChar02() throws Exception {
        // �O����
        char chZenkakuMin = '\u00ff';
        char chZenkakuKanaStart = '�';
        char chZenkakuKanaEnd = '�';

        // �e�X�g���{�E����
        // ���p�������ݒ肳�ꂽ�Ƃ��Afalse���ԋp����邱��
        assertFalse(ValidationUtil.isZenkakuChar(chZenkakuMin));
        assertFalse(ValidationUtil.isZenkakuChar(chZenkakuKanaStart));
        assertFalse(ValidationUtil.isZenkakuChar(chZenkakuKanaEnd));
    }

    /**
     * testIsZenkakuChar03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) c:'�'<br>
     *                '6'<br>
     *                '&'<br>
     *                'a'<br>
     *                'z'<br>
     *                'A'<br>
     *                'Z'<br>
     *         (���) hankakuKanaList:����������������������¯�������������������֬�������ܦ��ް�����<br>
     *         (���) �v���p�e�B�t�@�C��:validation.hankaku.kana.list<br>
     *                �����݂��Ȃ����ƁB<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *
     * <br>
     * �����Ɏw�肵�����������p�����ł���ꍇ�Afalse���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsZenkakuChar03() throws Exception {
        // �O����
        char[] input = {
            '�',
            '6',
            '&',
            'a',
            'z',
            'A',
            'Z'
        };

        // �e�X�g���{�E����
        // ���p�������ݒ肳�ꂽ�Ƃ��Afalse���ԋp����邱��
        for (char c : input) {
            assertFalse(ValidationUtil.isZenkakuChar(c));
        }
    }

    /**
     * testIsZenkakuChar04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) c:"�_�������N�ʁ��}�L���~��"<br>
     *                ���ꕶ�����m�F<br>
     *         (���) hankakuKanaList:����������������������¯�������������������֬�������ܦ��ް�����<br>
     *         (���) �v���p�e�B�t�@�C��:validation.hankaku.kana.list<br>
     *                �����݂��Ȃ����ƁB<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * �����Ɏw�肵��������"�_�������N�ʁ��}�L���~��"�Ɋ܂܂��ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsZenkakuChar04() throws Exception {
        // �O����
        String zenkakuBeginU00List = "�_�������N�ʁ��}�L���~��";

        // �e�X�g���{�E����
        for (int i = 0; i < zenkakuBeginU00List.length(); i++) {
            assertTrue(ValidationUtil.isZenkakuChar(zenkakuBeginU00List
                    .charAt(i)));
        }
    }

    /**
     * testIsZenkakuKanaChar01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) c:�A�C�E���G�I�@�B�D�F�H�J�L�N�P�R�����K�M�O�Q�S�T�V�X�Z�\�U�W�Y�[�]�^�`�c�e�g�_�a�d�f�h�i�j�k�l�m�n�q�t�w�z�o�r�u�x�{�p�s�v�y�|�}�~���������������������������������������b���[<br>
     *                ���ꕶ�����m�F<br>
     *         (���) zenkakuKanaList:�A�C�E���G�I�@�B�D�F�H�J�L�N�P�R�����K�M�O�Q�S�T�V�X�Z�\�U�W�Y�[�]�^�`�c�e�g�_�a�d�f�h�i�j�k�l�m�n�q�t�w�z�o�r�u�x�{�p�s�v�y�|�}�~���������������������������������������b���[<br>
     *         (���) �v���p�e�B�t�@�C��:validation.zenkaku.kana.list<br>
     *                �����݂��Ȃ����ƁB<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:�S�Ă̕����ɂ���true<br>
     *
     * <br>
     * �����Ɏw�肵��������zenkakuKanaList�Ɋ܂܂��ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsZenkakuKanaChar01() throws Exception {
        // �O����
        String zenkakuKanaList = "�A�C�E���G�I�@�B�D�F�H�J�L�N�P�R" +
                "�����K�M�O�Q�S�T�V�X�Z�\�U�W�Y�[�]�^�`�c�e�g" +
                "�_�a�d�f�h�i�j�k�l�m�n�q�t�w�z�o�r�u�x�{" +
                "�p�s�v�y�|�}�~����������������������������" +
                "�����������b���[";

        // �e�X�g���{�E����
        for (int i = 0; i < zenkakuKanaList.length(); i++) {
            assertTrue(ValidationUtil.isZenkakuKanaChar(zenkakuKanaList
                    .charAt(i)));
        }
    }

    /**
     * testIsZenkakuKanaChar02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) c:'�@' - 1<br>
     *                '�[' + 1<br>
     *                ���ꕶ�����m�F<br>
     *         (���) zenkakuKanaList:�A�C�E���G�I�@�B�D�F�H�J�L�N�P�R�����K�M�O�Q�S�T�V�X�Z�\�U�W�Y�[�]�^�`�c�e�g�_�a�d�f�h�i�j�k�l�m�n�q�t�w�z�o�r�u�x�{�p�s�v�y�|�}�~���������������������������������������b���[<br>
     *         (���) �v���p�e�B�t�@�C��:validation.zenkaku.kana.list<br>
     *                �����݂��Ȃ����ƁB<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:�S�Ă̕����ɂ���false<br>
     *
     * <br>
     * �����Ɏw�肵��������zenkakuKanaList�Ɋ܂܂�Ȃ��ꍇ�Afalse���擾�ł��邱�Ƃ��m�F����B�i���p�J�i�̋��E�e�X�g�j
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsZenkakuKanaChar02() throws Exception {
        // �O����
        char chStart = '�@' - 1;
        char chEnd = '�[' + 1;

        // �e�X�g���{�E����
        assertFalse(ValidationUtil.isZenkakuKanaChar(chStart));
        assertFalse(ValidationUtil.isZenkakuKanaChar(chEnd));
    }

    /**
     * testIsZenkakuKanaChar03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) c:'��'<br>
     *         (���) zenkakuKanaList:�A�C�E���G�I�@�B�D�F�H�J�L�N�P�R�����K�M�O�Q�S�T�V�X�Z�\�U�W�Y�[�]�^�`�c�e�g�_�a�d�f�h�i�j�k�l�m�n�q�t�w�z�o�r�u�x�{�p�s�v�y�|�}�~���������������������������������������b���[<br>
     *         (���) �v���p�e�B�t�@�C��:validation.zenkaku.kana.list<br>
     *                �����݂��Ȃ����ƁB<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *
     * <br>
     * �����Ɏw�肵��������zenkakuKanaList�Ɋ܂܂�Ȃ��ꍇ�Afalse���擾�ł��邱�Ƃ��m�F����B�i�S�p�������j
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsZenkakuKanaChar03() throws Exception {
        // �O����
        char chHiragana = '��';

        // �e�X�g���{�E����
        assertFalse(ValidationUtil.isZenkakuKanaChar(chHiragana));
    }

}
