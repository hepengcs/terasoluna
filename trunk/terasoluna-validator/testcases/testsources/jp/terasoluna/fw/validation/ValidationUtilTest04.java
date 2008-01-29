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

import java.util.ArrayList;
import java.util.List;

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
public class ValidationUtilTest04 extends PropertyTestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     *
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ValidationUtilTest04.class);
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
    public ValidationUtilTest04(String name) {
        super(name);
    }

    /**
     * testHasNotProhibitedChar01()
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
    public void testHasNotProhibitedChar01() throws Exception {
        // �e�X�g���{�E����
        assertTrue(ValidationUtil.hasNotProhibitedChar(null, "abc"));
    }

    /**
     * testHasNotProhibitedChar02()
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
    public void testHasNotProhibitedChar02() throws Exception {
        // �e�X�g���{�E����
        assertTrue(ValidationUtil.hasNotProhibitedChar("", "abc"));
    }

    /**
     * testHasNotProhibitedChar03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) value:"abc"<br>
     *         (����) prohibitedChars:null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * ����prohibitedChars��null�̏ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testHasNotProhibitedChar03() throws Exception {
        // �e�X�g���{�E����
        assertTrue(ValidationUtil.hasNotProhibitedChar("abc", null));
    }

    /**
     * testHasNotProhibitedChar04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) value:"abc"<br>
     *         (����) prohibitedChars:""�i�󕶎��j<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * ����prohibitedChars���󕶎��̏ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testHasNotProhibitedChar04() throws Exception {
        // �e�X�g���{�E����
        assertTrue(ValidationUtil.hasNotProhibitedChar("abc", ""));
    }

    /**
     * testHasNotProhibitedChar05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:"a"<br>
     *         (����) prohibitedChars:"abc"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *
     * <br>
     * ����value��1�����ł��ꂪ�֎~�����̏ꍇ�Afalse���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testHasNotProhibitedChar05() throws Exception {
        // �e�X�g���{�E����
        assertFalse(ValidationUtil.hasNotProhibitedChar("a", "abc"));
    }

    /**
     * testHasNotProhibitedChar06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:"d"<br>
     *         (����) prohibitedChars:"abc"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * ����value��1�����ł��ꂪ�֎~�����łȂ��ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testHasNotProhibitedChar06() throws Exception {
        // �e�X�g���{�E����
        assertTrue(ValidationUtil.hasNotProhibitedChar("d", "abc"));
    }

    /**
     * testHasNotProhibitedChar07()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:"abc"<br>
     *         (����) prohibitedChars:"cde"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *
     * <br>
     * ����value�����������ł��ꂪ�֎~�������܂ޏꍇ�Afalse���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testHasNotProhibitedChar07() throws Exception {
        // �e�X�g���{�E����
        assertFalse(ValidationUtil.hasNotProhibitedChar("abc", "cde"));
    }

    /**
     * testHasNotProhibitedChar08()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:"abc"<br>
     *         (����) prohibitedChars:"def"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * ����value�����������ł��ꂪ�֎~�������܂܂Ȃ��ꍇ�Atrue���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testHasNotProhibitedChar08() throws Exception {
        // �e�X�g���{�E����
        assertTrue(ValidationUtil.hasNotProhibitedChar("abc", "def"));
    }

    /**
     * testHasNotProhibitedChar09()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) value:"ab\""<br>
     *         (����) prohibitedChars:"cd\""<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *
     * <br>
     * ����value�ɃG�X�P�[�v���K�v�ȕ������܂݂��ꂪ�֎~�����̏ꍇ�Afalse���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testHasNotProhibitedChar09() throws Exception {
        // �e�X�g���{�E����
        assertFalse(ValidationUtil.hasNotProhibitedChar("ab\"", "cd\""));
    }

    /**
     * testHasNotProhibitedChar10()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) value:"ab\""<br>
     *         (����) prohibitedChars:"de\\"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * ����value�ɃG�X�P�[�v���K�v�ȕ������܂݂��ꂪ�֎~�����łȂ��ꍇ�A
     * true���擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testHasNotProhibitedChar10() throws Exception {
        // �e�X�g���{�E����
        assertTrue(ValidationUtil.hasNotProhibitedChar("ab\"", "cd\\"));
    }

    /**
     * testIsArrayInRange01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) obj:null<br>
     *         (����) min:1<br>
     *         (����) max:5<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *
     * <br>
     * ����obj��null�̏ꍇ��min���P�ȏ�̏ꍇ�Afalse���ԋp����邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsArrayInRange01() throws Exception {
        // �O����
        Object obj = null;
        int min = 1;
        int max = 5;

        // �e�X�g���{
        boolean result = ValidationUtil.isArrayInRange(obj, min, max);

        // ����
        assertFalse(result);
    }

    /**
     * testIsArrayInRange02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) obj:""(String)<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     * ��ԕω��F(��O) IllegalArgumentException<br>
     *                  ���b�Z�[�W�Fjava.lang.String is neither Array nor Collection.
     *
     * <br>
     * ����obj���z��ECollection�^�ł͂Ȃ��ꍇ�A
     * IllegalArgumentException���������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsArrayInRange02() throws Exception {
        // �O����
        Object obj = "";
        int min = 0;
        int max = 0;

        // �e�X�g���{
        try {
            ValidationUtil.isArrayInRange(obj, min, max);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("java.lang.String is neither Array nor Collection.",
                    e.getMessage());
        }
    }

    /**
     * testIsArrayInRange03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) obj:{"a","b","c"}<br>
     *                �i�z��j<br>
     *         (����) min:0<br>
     *         (����) max:10<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * ����obj���z��ŁA�͈͓��̏ꍇ
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsArrayInRange03() throws Exception {
        // �O����
        Object obj = new String[]{"a","b","c"};
        int min = 0;
        int max = 10;

        // �e�X�g���{
        boolean result = ValidationUtil.isArrayInRange(obj, min, max);

        // ����
        assertTrue(result);
    }

    /**
     * testIsArrayInRange04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) obj:ArrayList<br>
     *                [1="a"]<br>
     *                [2="b"]<br>
     *                [3="c"]<br>
     *         (����) min:5<br>
     *         (����) max:10<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *
     * <br>
     * ����obj���R���N�V�����ŁA�͈͊O�̏ꍇ
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsArrayInRange04() throws Exception {
        // �O����
        List<String> obj = new ArrayList<String>();
        obj.add("a");
        obj.add("b");
        obj.add("c");
        int min = 5;
        int max = 10;

        // �e�X�g���{
        boolean result = ValidationUtil.isArrayInRange(obj, min, max);

        // ����
        assertFalse(result);
    }

    /**
     * testIsArrayInRange05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) obj:int[] {<br>
     *                  1,2,3<br>
     *                };<br>
     *         (����) min:0<br>
     *         (����) max:2<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *
     * <br>
     * �������v���~�e�B�u�z��^�ŁA�͈͊O�̏ꍇ
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsArrayInRange05() throws Exception {
        // �O����
        int[] array = new int[]{
            1, 2, 3
        };

        // �e�X�g���{
        boolean result = ValidationUtil.isArrayInRange(array, 0, 2);

        // ����
        assertFalse(result);
    }

    /**
     * testIsUrl01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * ����value��null�̏ꍇ
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsUrl01() throws Exception {
        // �O����
        String value = null;
        boolean allowallschemes = false;
        boolean allow2slashes = false;
        boolean nofragments = false;
        String schemesVar = null;

        // �e�X�g���{
        boolean result = ValidationUtil.isUrl(value, allowallschemes,
                allow2slashes, nofragments, schemesVar);

        // ����
        assertTrue(result);
    }

    /**
     * testIsUrl02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:""<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * ����value���󔒂̏ꍇ
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsUrl02() throws Exception {
        // �O����
        String value = "";
        boolean allowallschemes = false;
        boolean allow2slashes = false;
        boolean nofragments = false;
        String schemesVar = null;

        // �e�X�g���{
        boolean result = ValidationUtil.isUrl(value, allowallschemes,
                allow2slashes, nofragments, schemesVar);

        // ����
        assertTrue(result);
    }

    /**
     * testIsUrl03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:http://www.nttdata.co.jp/index.html<br>
     *         (����) allowallschemes:false<br>
     *         (����) allow2slashes:false<br>
     *         (����) nofragments:false<br>
     *         (����) schemesVar:null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * ����value�������URL�ŁA�I�v�V������false�AschemesVar��null�̏ꍇ
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsUrl03() throws Exception {
        // �O����
        String value = "http://www.nttdata.co.jp/index.html";
        boolean allowallschemes = false;
        boolean allow2slashes = false;
        boolean nofragments = false;
        String schemesVar = null;

        // �e�X�g���{
        boolean result = ValidationUtil.isUrl(value, allowallschemes,
                allow2slashes, nofragments, schemesVar);

        // ����
        assertTrue(result);
    }

    /**
     * testIsUrl04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:http://www.nttdata.co.jp<br>
     *         (����) allowallschemes:false<br>
     *         (����) allow2slashes:false<br>
     *         (����) nofragments:false<br>
     *         (����) schemesVar:null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * ����value�������URL�ŁA�I�v�V������false�AschemesVar��null�̏ꍇ
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsUrl04() throws Exception {
        // �O����
        String value = "http://www.nttdata.co.jp";
        boolean allowallschemes = false;
        boolean allow2slashes = false;
        boolean nofragments = false;
        String schemesVar = null;

        // �e�X�g���{
        boolean result = ValidationUtil.isUrl(value, allowallschemes,
                allow2slashes, nofragments, schemesVar);

        // ����
        assertTrue(result);
    }

    /**
     * testIsUrl05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:http://www.nttdata.co.jp/index.html<br>
     *         (����) allowallschemes:false<br>
     *         (����) allow2slashes:false<br>
     *         (����) nofragments:false<br>
     *         (����) schemesVar:""<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:false<br>
     *
     * <br>
     * ����value��URL�ł͂Ȃ�������ŁA�I�v�V������false�AschemesVar���󔒂̏ꍇ
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsUrl05() throws Exception {
        // �O����
        String value = "http://www.nttdata.co.jp/index.html";
        boolean allowallschemes = false;
        boolean allow2slashes = false;
        boolean nofragments = false;
        String schemesVar = "";

        // �e�X�g���{
        boolean result = ValidationUtil.isUrl(value, allowallschemes,
                allow2slashes, nofragments, schemesVar);

        // ����
        assertFalse(result);
    }

    /**
     * testIsUrl06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:http://www.nttdata.co.jp/<br>
     *         (����) allowallschemes:true<br>
     *         (����) allow2slashes:true<br>
     *         (����) nofragments:true<br>
     *         (����) schemesVar:"http"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * ����value�������URL�ŁA�I�v�V������true�AschemesVar��NotNull�̏ꍇ
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsUrl06() throws Exception {
        // �O����
        String value = "http://www.nttdata.co.jp/";
        boolean allowallschemes = true;
        boolean allow2slashes = true;
        boolean nofragments = true;
        String schemesVar = "http";

        // �e�X�g���{
        boolean result = ValidationUtil.isUrl(value, allowallschemes,
                allow2slashes, nofragments, schemesVar);

        // ����
        assertTrue(result);
    }

    /**
     * testIsUrl07()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FF
     * <br><br>
     * ���͒l�F(����) value:https://www.nttdata.co.jp/index.html<br>
     *         (����) allowallschemes:true<br>
     *         (����) allow2slashes:true<br>
     *         (����) nofragments:true<br>
     *         (����) schemesVar:"http,ftp,https"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) boolean:true<br>
     *
     * <br>
     * ����value�������URL�ŁA�I�v�V������true�AschemesVar��NotNull(�J���}��؂�̕���)�̏ꍇ
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsUrl07() throws Exception {
        // �O����
        String value = "https://www.nttdata.co.jp/index.html";
        boolean allowallschemes = true;
        boolean allow2slashes = true;
        boolean nofragments = true;
        String schemesVar = "http,ftp,https";

        // �e�X�g���{
        boolean result = ValidationUtil.isUrl(value, allowallschemes,
                allow2slashes, nofragments, schemesVar);

        // ����
        assertTrue(result);
    }

}
