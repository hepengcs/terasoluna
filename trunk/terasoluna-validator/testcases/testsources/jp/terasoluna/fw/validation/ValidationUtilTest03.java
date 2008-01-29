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
public class ValidationUtilTest03 extends PropertyTestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     *
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ValidationUtilTest03.class);
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
    public ValidationUtilTest03(String name) {
        super(name);
    }

    /**
     * testCheckNumberFigures01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) length:3<br>
     *         (����) checkLength:0<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *
     * <br>
     * length��checkLength���傫���ꍇ�Afalse���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCheckNumberFigures01() throws Exception {
        // �e�X�g���{�E����
        assertFalse(ValidationUtil.checkNumberFigures(3, 0, false));
    }

    /**
     * testCheckNumberFigures02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) length:0<br>
     *         (����) checkLength:3<br>
     *         (����) isAccorded:false<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * isAccorded��false�̏ꍇ�Alength��checkLength�ȉ��ł���΁Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCheckNumberFigures02() throws Exception {
        // �e�X�g���{�E����
        assertTrue(ValidationUtil.checkNumberFigures(0, 3, false));
    }

    /**
     * testCheckNumberFigures03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) length:3<br>
     *         (����) checkLength:3<br>
     *         (����) isAccorded:false<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * isAccorded��false�̏ꍇ�Alength��checkLength�ȉ��ł���΁Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCheckNumberFigures03() throws Exception {
        // �e�X�g���{�E����
        assertTrue(ValidationUtil.checkNumberFigures(3, 3, false));
    }

    /**
     * testCheckNumberFigures04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) length:3<br>
     *         (����) checkLength:3<br>
     *         (����) isAccorded:true<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * isAccorded��true�̏ꍇ�Alength��checkLength�Ɠ�������΁Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCheckNumberFigures04() throws Exception {
        // �e�X�g���{�E����
        assertTrue(ValidationUtil.checkNumberFigures(3, 3, true));
    }

    /**
     * testCheckNumberFigures05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) length:0<br>
     *         (����) checkLength:3<br>
     *         (����) isAccorded:true<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *
     * <br>
     * isAccorded��true�̏ꍇ�Alength��checkLength�Ɠ������Ȃ���΁Afalse���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testCheckNumberFigures05() throws Exception {
        // �e�X�g���{�E����
        assertFalse(ValidationUtil.checkNumberFigures(0, 3, true));
    }

    /**
     * testIsHankakuKanaString01()
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
    public void testIsHankakuKanaString01() throws Exception {
        // �e�X�g���{�E����
        assertTrue(ValidationUtil.isHankakuKanaString(null));
    }

    /**
     * testIsHankakuKanaString02()
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
    public void testIsHankakuKanaString02() throws Exception {
        // �e�X�g���{�E����
        assertTrue(ValidationUtil.isHankakuKanaString(""));
    }

    /**
     * testIsHankakuKanaString03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:"�A"<br>
     *         (���) hankakuKanaList:����������������������¯�������������������֬�������ܦ��ް�����<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *
     * <br>
     * ����value�����p�J�i�����łȂ��ꍇ�Afalse���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsHankakuKanaString03() throws Exception {
        // �e�X�g���{�E����
        assertFalse(ValidationUtil.isHankakuKanaString("�A"));
    }

    /**
     * testIsHankakuKanaString04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:"�"<br>
     *         (���) hankakuKanaList:����������������������¯�������������������֬�������ܦ��ް�����<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * ����value�����p�J�i�����̏ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsHankakuKanaString04() throws Exception {
        // �e�X�g���{�E����
        assertTrue(ValidationUtil.isHankakuKanaString("�"));
    }

    /**
     * testIsHankakuKanaString05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:"��A"<br>
     *         (���) hankakuKanaList:����������������������¯�������������������֬�������ܦ��ް�����<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *
     * <br>
     * ����value�����������Ŕ��p�J�i�����ȊO���܂܂��ꍇ�Afalse���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsHankakuKanaString05() throws Exception {
        // �e�X�g���{�E����
        assertFalse(ValidationUtil.isHankakuKanaString("��A"));
    }

    /**
     * testIsHankakuKanaString06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:"���"<br>
     *         (���) hankakuKanaList:����������������������¯�������������������֬�������ܦ��ް�����<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * ����value�����������Ŕ��p�J�i�����݂̂ō\������Ă���ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsHankakuKanaString06() throws Exception {
        // �e�X�g���{�E����
        assertTrue(ValidationUtil.isHankakuKanaString("���"));
    }

    /**
     * testIsHankakuString01()
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
    public void testIsHankakuString01() throws Exception {
        // �e�X�g���{�E����
        assertTrue(ValidationUtil.isHankakuString(null));
    }

    /**
     * testIsHankakuString02()
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
    public void testIsHankakuString02() throws Exception {
        // �e�X�g���{�E����
        assertTrue(ValidationUtil.isHankakuString(""));
    }

    /**
     * testIsHankakuString03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:"��"<br>
     *         (���) hankakuKanaList:����������������������¯�������������������֬�������ܦ��ް�����<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *
     * <br>
     * ����value�����p�����łȂ��ꍇ�Afalse���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsHankakuString03() throws Exception {
        // �e�X�g���{�E����
        assertFalse(ValidationUtil.isHankakuString("��"));
    }

    /**
     * testIsHankakuString04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:"a"<br>
     *         (���) hankakuKanaList:����������������������¯�������������������֬�������ܦ��ް�����<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * ����value�����p�����̏ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsHankakuString04() throws Exception {
        // �e�X�g���{�E����
        assertTrue(ValidationUtil.isHankakuString("a"));
    }

    /**
     * testIsHankakuString05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:"ab��"<br>
     *         (���) hankakuKanaList:����������������������¯�������������������֬�������ܦ��ް�����<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *
     * <br>
     * ����value�����������Ŕ��p�����ȊO���܂܂��ꍇ�Afalse���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsHankakuString05() throws Exception {
        // �e�X�g���{�E����
        assertFalse(ValidationUtil.isHankakuString("ab��"));
    }

    /**
     * testIsHankakuString06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:"1a�"<br>
     *         (���) hankakuKanaList:����������������������¯�������������������֬�������ܦ��ް�����<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * ����value�����������Ŕ��p�����݂̂ō\������Ă���ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsHankakuString06() throws Exception {
        // �e�X�g���{�E����
        assertTrue(ValidationUtil.isHankakuString("1a�"));
    }

    /**
     * testIsZenkakuString01()
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
    public void testIsZenkakuString01() throws Exception {
        // �e�X�g���{�E����
        assertTrue(ValidationUtil.isZenkakuString(null));
    }

    /**
     * testIsZenkakuString02()
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
    public void testIsZenkakuString02() throws Exception {
        // �e�X�g���{�E����
        assertTrue(ValidationUtil.isZenkakuString(""));
    }

    /**
     * testIsZenkakuString03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:"a"<br>
     *         (���) zenkakuKanaList:�A�C�E���G�I�@�B�D�F�H�J�L�N�P�R�����K�M�O�Q�S�T�V�X�Z�\�U�W�Y�[�]�^�`�c�e�g�_�a�d�f�h�i�j�k�l�m�n�q�t�w�z�o�r�u�x�{�p�s�v�y�|�}�~���������������������������������������b���[<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *
     * <br>
     * ����value���S�p�����łȂ��ꍇ�Afalse���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsZenkakuString03() throws Exception {
        // �e�X�g���{�E����
        assertFalse(ValidationUtil.isZenkakuString("a"));
    }

    /**
     * testIsZenkakuString04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:"��"<br>
     *         (���) zenkakuKanaList:�A�C�E���G�I�@�B�D�F�H�J�L�N�P�R�����K�M�O�Q�S�T�V�X�Z�\�U�W�Y�[�]�^�`�c�e�g�_�a�d�f�h�i�j�k�l�m�n�q�t�w�z�o�r�u�x�{�p�s�v�y�|�}�~���������������������������������������b���[<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * ����value���S�p�����̏ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsZenkakuString04() throws Exception {
        // �e�X�g���{�E����
        assertTrue(ValidationUtil.isZenkakuString("��"));
    }

    /**
     * testIsZenkakuString05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:"�`���"<br>
     *         (���) zenkakuKanaList:�A�C�E���G�I�@�B�D�F�H�J�L�N�P�R�����K�M�O�Q�S�T�V�X�Z�\�U�W�Y�[�]�^�`�c�e�g�_�a�d�f�h�i�j�k�l�m�n�q�t�w�z�o�r�u�x�{�p�s�v�y�|�}�~���������������������������������������b���[<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *
     * <br>
     * ����value�����������őS�p�����ȊO���܂܂��ꍇ�Afalse���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsZenkakuString05() throws Exception {
        // �e�X�g���{�E����
        assertFalse(ValidationUtil.isZenkakuString("�`���"));
    }

    /**
     * testIsZenkakuString06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:"�`���S�p"<br>
     *         (���) zenkakuKanaList:�A�C�E���G�I�@�B�D�F�H�J�L�N�P�R�����K�M�O�Q�S�T�V�X�Z�\�U�W�Y�[�]�^�`�c�e�g�_�a�d�f�h�i�j�k�l�m�n�q�t�w�z�o�r�u�x�{�p�s�v�y�|�}�~���������������������������������������b���[<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * ����value�����������őS�p�����݂̂ō\������Ă���ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsZenkakuString06() throws Exception {
        // �e�X�g���{�E����
        assertTrue(ValidationUtil.isZenkakuString("�`���S�p"));
    }

    /**
     * testIsZenkakuKanaString01()
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
    public void testIsZenkakuKanaString01() throws Exception {
        // �e�X�g���{�E����
        assertTrue(ValidationUtil.isZenkakuKanaString(null));
    }

    /**
     * testIsZenkakuKanaString02()
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
    public void testIsZenkakuKanaString02() throws Exception {
        // �e�X�g���{�E����
        assertTrue(ValidationUtil.isZenkakuKanaString(""));
    }

    /**
     * testIsZenkakuKanaString03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:"A"<br>
     *         (���) isZenkakuKanaChar(char):�A�C�E���G�I�@�B�D�F�H�J�L�N�P�R�����K�M�O�Q�S�T�V�X�Z�\�U�W�Y�[�]�^�`�c�e�g�_�a�d�f�h�i�j�k�l�m�n�q�t�w�z�o�r�u�x�{�p�s�v�y�|�}�~���������������������������������������b���[<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *
     * <br>
     * ����value���S�p�J�i�����łȂ��ꍇ�Afalse���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsZenkakuKanaString03() throws Exception {
        // �e�X�g���{�E����
        assertFalse(ValidationUtil.isZenkakuKanaString("A"));
    }

    /**
     * testIsZenkakuKanaString04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:"�A"<br>
     *         (���) isZenkakuKanaChar(char):�A�C�E���G�I�@�B�D�F�H�J�L�N�P�R�����K�M�O�Q�S�T�V�X�Z�\�U�W�Y�[�]�^�`�c�e�g�_�a�d�f�h�i�j�k�l�m�n�q�t�w�z�o�r�u�x�{�p�s�v�y�|�}�~���������������������������������������b���[<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * ����value���S�p�J�i�����̏ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsZenkakuKanaString04() throws Exception {
        // �e�X�g���{�E����
        assertTrue(ValidationUtil.isZenkakuKanaString("�A"));
    }

    /**
     * testIsZenkakuKanaString05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:"�A�C�"<br>
     *         (���) isZenkakuKanaChar(char):�A�C�E���G�I�@�B�D�F�H�J�L�N�P�R�����K�M�O�Q�S�T�V�X�Z�\�U�W�Y�[�]�^�`�c�e�g�_�a�d�f�h�i�j�k�l�m�n�q�t�w�z�o�r�u�x�{�p�s�v�y�|�}�~���������������������������������������b���[<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *
     * <br>
     * ����value�����������őS�p�J�i�����ȊO���܂܂��ꍇ�Afalse���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsZenkakuKanaString05() throws Exception {
        // �e�X�g���{�E����
        assertFalse(ValidationUtil.isZenkakuKanaString("�A�C�"));
    }

    /**
     * testIsZenkakuKanaString06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:"�A�C�E"<br>
     *         (���) isZenkakuKanaChar(char):�A�C�E���G�I�@�B�D�F�H�J�L�N�P�R�����K�M�O�Q�S�T�V�X�Z�\�U�W�Y�[�]�^�`�c�e�g�_�a�d�f�h�i�j�k�l�m�n�q�t�w�z�o�r�u�x�{�p�s�v�y�|�}�~���������������������������������������b���[<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * ����value�����������őS�p�J�i�����݂̂ō\������Ă���ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsZenkakuKanaString06() throws Exception {
        // �e�X�g���{�E����
        assertTrue(ValidationUtil.isZenkakuKanaString("�A�C�E"));
    }

}
