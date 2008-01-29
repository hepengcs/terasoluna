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

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.StringTokenizer;

import jp.terasoluna.fw.util.PropertyUtil;
import jp.terasoluna.fw.util.StringUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericTypeValidator;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.UrlValidator;

/**
 * ���؃��W�b�N�̃��[�e�B���e�B�N���X�B
 *
 *
 */
public class ValidationUtil {
    /**
     * ���p�J�i�̃`�F�b�N�Ɏg�p���镶����B
     */
    protected static String hankakuKanaList =
        "����������������������¯�������������������֬�������ܦ��ް�����";

    /**
     * �S�p�J�i�̃`�F�b�N�Ɏg�p���镶����B
     */
    protected static String zenkakuKanaList =
        "�A�C�E���G�I�@�B�D�F�H�J�L�N�P�R�����K�M�O�Q�S�T�V�X�Z�\"
            + "�U�W�Y�[�]�^�`�c�e�g�_�a�d�f�h�i�j�k�l�m�n�q�t�w�z"
            + "�o�r�u�x�{�p�s�v�y�|�}�~����������������������������"
            + "�����������b���[";

    /**
     * <code>ApplicationResources</code>
     * �t�@�C���ɒ�`���ꂽ���p�����e�[�u�����擾����L�[�B
     */
    protected static final String HANKAKU_KANA_LIST_KEY
        = "validation.hankaku.kana.list";

    /**
     * <code>ApplicationResources</code>
     * �t�@�C���ɒ�`���ꂽ�S�p�����e�[�u�����擾����L�[�B
     */
    protected static final String ZENKAKU_KANA_LIST_KEY
        = "validation.zenkaku.kana.list";

    /**
     * UNICODE�����R�[�h'&#165;u0000'����'&#165;u00ff'��
     * �͈͓��ɑ��݂���S�p������B
     */
    protected static final String ZENKAKU_BEGIN_U00_LIST =
        "�_�������N�ʁ��}�L���~��";

    static {
        // ���p�J�i�E�S�p�J�i��`�̕ύX
        setHankakuKanaList();
        setZenkakuKanaList();
    }

    /**
     * ���p�J�i��`��ݒ肷��B
     */
    protected static void setHankakuKanaList() {
        String value = null;
        // ���p�J�i������e�[�u�����擾
        value = PropertyUtil.getProperty(HANKAKU_KANA_LIST_KEY);
        if (value != null) {
            hankakuKanaList = value;
        }
    }

    /**
     * �S�p�J�i��`��ݒ肷��B
     */
    protected static void setZenkakuKanaList() {
        // �S�p�J�i������e�[�u�����擾
        String value = null;
        value = PropertyUtil.getProperty(ZENKAKU_KANA_LIST_KEY);
        if (value != null) {
            zenkakuKanaList = value;
        }
    }

    /**
     * �w�肳�ꂽ���������p�J�i�����ł��邱�Ƃ��`�F�b�N����B
     *
     * @param c ����
     * @return ���p�J�i�����ł���� true
     */
    protected static boolean isHankakuKanaChar(char c) {
        return hankakuKanaList.indexOf(c) >= 0;
    }

    /**
     * �w�肳�ꂽ���������p�����ł��邱�Ƃ��`�F�b�N����B
     *
     * @param c ����
     * @return ���p�����ł���� true
     */
    protected static boolean isHankakuChar(char c) {
        return (c <= '\u00ff' && ZENKAKU_BEGIN_U00_LIST.indexOf(c) < 0)
                || isHankakuKanaChar(c);
    }

    /**
     * �w�肳�ꂽ�������S�p�����ł��邱�Ƃ��`�F�b�N����B
     *
     * @param c ����
     * @return �S�p�����ł���� true
     */
    protected static boolean isZenkakuChar(char c) {
        return !isHankakuChar(c);
    }

    /**
     * �w�肳�ꂽ�������S�p�J�i�����ł��邱�Ƃ��`�F�b�N����B
     *
     * @param c ����
     * @return �S�p�J�i�����ł���� true
     */
    protected static boolean isZenkakuKanaChar(char c) {
        return zenkakuKanaList.indexOf(c) >= 0;
    }

    /**
     * ���ؒl�����K�\���ɍ��v���邱�Ƃ��`�F�b�N����B
     *
     * @param value ���ؒl
     * @param mask ���K�\��
     * @return
     *            ���ؒl�����K�\���ɍ��v����Ȃ��
     *            <code>true</code>��Ԃ��B
     *            ����ȊO�̏ꍇ�A<code>false</code>��Ԃ��B
     */
    public static boolean matchRegexp(String value, String mask) {
        if (!StringUtils.isEmpty(value)
                && !GenericValidator.matchRegexp(value, mask)) {
            return false;
        }
        return true;
    }

    /**
     * ���ؒl���p�����݂̂���Ȃ镶����ł��邱�Ƃ��`�F�b�N����B
     *
     * <code>null</code> ������A�󕶎���́A�����Ƃ݂Ȃ����B
     *
     * @param value ���ؒl
     * @return
     *            ���ؒl���p�����݂̂���Ȃ镶����ł���Ȃ��
     *            <code>true</code>��Ԃ��B
     *            ����ȊO�̏ꍇ�A<code>false</code>��Ԃ��B
     */
    public static boolean isAlphaNumericString(String value) {
        return matchRegexp(value, "^([0-9]|[a-z]|[A-Z])*$");
    }

    /**
     * ���ؒl���啶���p�����݂̂���Ȃ镶����ł��邱�Ƃ��`�F�b�N����B
     *
     * <code>null</code> ������A�󕶎���́A�����Ƃ݂Ȃ����B
     *
     * @param value ���ؒl
     * @return
     *            ���ؒl���啶���p�����݂̂���Ȃ镶����ł���Ȃ��
     *            <code>true</code>��Ԃ��B
     *            ����ȊO�̏ꍇ�A<code>false</code>��Ԃ��B
     */
    public static boolean isUpperAlphaNumericString(String value) {
        return matchRegexp(value, "^([0-9]|[A-Z])*$");
    }

    /**
     * ���ؒl�������݂̂���Ȃ镶����ł��邱�Ƃ��`�F�b�N����B
     *
     * <code>null</code> ������A�󕶎���́A�����Ƃ݂Ȃ����B
     *
     * @param value ���ؒl
     * @return
     *            ���ؒl�������݂̂���Ȃ镶����ł���Ȃ��
     *            <code>true</code>��Ԃ��B
     *            ����ȊO�̏ꍇ�A<code>false</code>��Ԃ��B
     */
    public static boolean isNumericString(String value) {
        return matchRegexp(value, "^([0-9])*$");
    }

    /**
     * ���ؒl���w�肳�ꂽ�����ł��邱�Ƃ��`�F�b�N����B
     * <br>
     * �����`�F�b�N�́A�������Ə������ɕ����āA�ȉ��̂悤�ɍs���B
     * <ul>
     * <li>�������̌����`�F�b�N
     * <ol>
     * <li><code>isAccordedInteger</code>��<code>true</code>�̏ꍇ�A
     * �������̌������A<code>integerLength</code>�̒l��
     * ���v���邩�ǂ������`�F�b�N����B
     *
     * <li><code>isAccordedInteger</code>��<code>false</code>�̏ꍇ�A
     * �������̌������A<code>integerLength</code>�̒l�ȉ��ł��邱�Ƃ�
     * �`�F�b�N����B
     * </ol>
     *
     * <li>�������̌����`�F�b�N
     * <ol>
     * <li><code>isAccordedScale</code>��<code>true</code>�̏ꍇ�A
     * �������̌������A<code>scaleLength</code>�̒l��
     * ���v���邩�ǂ������`�F�b�N����B
     *
     * <li><code>isAccordedScale</code>��<code>true</code>�̏ꍇ�A
     * �������̌������A<code>scaleLength</code>�̒l�ȉ��ł��邱�Ƃ�
     * �`�F�b�N����B
     * </ol>
     * </ul>
     *
     * @param value ���ؒl
     * @param integerLength �������̌���
     * @param isAccordedInteger
     *           �������̌�����v�`�F�b�N���s���ꍇ�A
     *           <code>true</code>���w�肷��B
     *           �������̌����ȓ��`�F�b�N���s���ꍇ�A
     *           <code>false</code>���w�肷��B
     * @param scaleLength �������̌���
     * @param isAccordedScale
     *           �������̌�����v�`�F�b�N���s���ꍇ�A
     *           <code>true</code>���w�肷��B
     *           �������̌����ȓ��`�F�b�N���s���ꍇ�A
     *           <code>false</code>���w�肷��B
     *
     * @return
     *            ���ؒl���w�肳�ꂽ�����ł���Ȃ��
     *            <code>true</code>��Ԃ��B
     *            ����ȊO�̏ꍇ�A<code>false</code>��Ԃ��B
     */
    public static boolean isNumber(
            BigDecimal value, int integerLength, boolean isAccordedInteger,
            int scaleLength, boolean isAccordedScale) {

        // ���ؒl��null�̎��Atrue�ԋp
        if (value == null) {
            return true;
        }

        // �������`�F�b�N���s��
        // ��������Βl�̂ݒ��o
        BigInteger bi = value.toBigInteger().abs();
        // ��������
        int length = bi.toString().length();
        if (!checkNumberFigures(length, integerLength, isAccordedInteger)) {
            return false;
        }

        // �������`�F�b�N���s��
        int scale = value.scale();
        if (!checkNumberFigures(scale, scaleLength, isAccordedScale)) {
            return false;
        }

        return true;
    }

    /**
     * �����`�F�b�N���s�����߂̃w���p���\�b�h�B
     *
     * @param length ���ۂ̌���
     * @param checkLength ��r���s�����߂̌���
     * @param isAccorded
     *           ������v�`�F�b�N���s���ꍇ�A<code>true</code>���w�肷��B
     *           �����ȓ��`�F�b�N���s���ꍇ�A<code>false</code>���w�肷��B
     * @return
     *            ���ۂ̌������w�肳�ꂽ�����ł���Ȃ��
     *            <code>true</code>��Ԃ��B
     *            ����ȊO�̏ꍇ�A<code>false</code>��Ԃ��B
     */
    protected static boolean checkNumberFigures(
            int length, int checkLength, boolean isAccorded) {
        // �����I�[�o���́Afalse��ԋp
        if (length > checkLength) {
            return false;
        }

        // ��v�w�肳��Ă���Ƃ�
        if (isAccorded) {
            // �����s��v�́Afalse��ԋp
            if (length != checkLength) {
                return false;
            }
        }
        return true;
    }

    /**
     * ���ؒl�����p�J�i�����݂̂���Ȃ镶����ł��邩�ǂ�����
     * �`�F�b�N����B
     *
     * <code>null</code> ������A�󕶎���́A�����Ƃ݂Ȃ����B
     *
     * @param value ���ؒl
     * @return
     *            ���ؒl�����p�J�i�����݂̂���Ȃ镶����ł���Ȃ��
     *            <code>true</code>��Ԃ��B
     *            ����ȊO�̏ꍇ�A<code>false</code>��Ԃ��B
     */
    public static boolean isHankakuKanaString(String value) {

        // ���ؒl��null�܂��͋󕶎��̎��Atrue�ԋp
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (!isHankakuKanaChar(chars[i])) {
                return false;
            }
        }
        return true;

    }

    /**
     * ���ؒl�����p�����݂̂���Ȃ镶����ł��邩�ǂ�����
     * �`�F�b�N����B
     *
     * <code>null</code> ������A�󕶎���́A�����Ƃ݂Ȃ����B
     *
     * @param value ���ؒl
     * @return
     *            ���ؒl�����p�����݂̂���Ȃ镶����ł���Ȃ��
     *            <code>true</code>��Ԃ��B
     *            ����ȊO�̏ꍇ�A<code>false</code>��Ԃ��B
     */
    public static boolean isHankakuString(String value) {

        // ���ؒl��null�܂��͋󕶎��̎��Atrue�ԋp
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (!isHankakuChar(chars[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * ���ؒl���S�p�����݂̂���Ȃ镶����ł��邩�ǂ�����
     * �`�F�b�N����B
     *
     * <code>null</code> ������A�󕶎���́A�����Ƃ݂Ȃ����B
     *
     * @param value ���ؒl
     * @return
     *            ���ؒl���S�p�����݂̂���Ȃ镶����ł���Ȃ��
     *            <code>true</code>��Ԃ��B
     *            ����ȊO�̏ꍇ�A<code>false</code>��Ԃ��B
     */
    public static boolean isZenkakuString(String value) {

        // ���ؒl��null�܂��͋󕶎��̎��Atrue�ԋp
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (!isZenkakuChar(chars[i])) {
                return false;
            }
        }
        return true;

    }

    /**
     * ���ؒl���S�p�J�i�����݂̂���Ȃ镶����ł��邩�ǂ�����
     * �`�F�b�N����B
     *
     * <code>null</code> ������A�󕶎���́A�����Ƃ݂Ȃ����B
     *
     * @param value ���ؒl
     * @return
     *            ���ؒl���S�p�J�i�����݂̂���Ȃ镶����ł���Ȃ��
     *            <code>true</code>��Ԃ��B
     *            ����ȊO�̏ꍇ�A<code>false</code>��Ԃ��B
    */
    public static boolean isZenkakuKanaString(String value) {

        // ���ؒl��null�܂��͋󕶎��̎��Atrue�ԋp
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (!isZenkakuKanaChar(chars[i])) {
                return false;
            }
        }
        return true;

    }

    /**
     * ���ؒl���֎~�������܂܂Ȃ����Ƃ��`�F�b�N����B
     * �G�X�P�[�v���K�v�ȕ����́u\�v�������t������B
     * �Ⴆ�΃_�u���R�[�e�[�V�����u"�v���֎~�����ɂ���ꍇ�A
     * �u\"�v�ƃG�X�P�[�v�������t������K�v������B
     *
     * �֎~������<code>null</code>������A�܂��͋󕶎���͐����Ƃ݂Ȃ����B
     *
     * @param value ���ؒl
     * @param prohibitedChars �֎~�����̕�����
     * @return ���ؒl���֎~�������܂�ł��Ȃ����<code>true</code>�A
     * ����ȊO��<code>false</code>��Ԃ��B
     */
    public static boolean hasNotProhibitedChar(
            String value, String prohibitedChars) {

        // ���ؒl��null�܂��͋󕶎��̎��Atrue�ԋp
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        char[] chars = value.toCharArray();

        // ���͋֎~�����񂪖��ݒ�̏ꍇ�A�`�F�b�N���s��Ȃ�
        if (prohibitedChars == null || "".equals(prohibitedChars)) {
            return true;
        }

        // ����
        for (int i = 0; i < chars.length; i++) {
            if (prohibitedChars.indexOf(chars[i]) >= 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * �z��A�܂��́A<code>Collection</code>�̗v�f�����A
     * �w�肳�ꂽ�ő�l�E�ŏ��l�͈͓̔��ł��邩�ǂ������`�F�b�N����B
     *
     * �����Ώۂ̔z��E<code>Collection</code>��<code>null</code>��
     * �ꍇ�́A�v�f��0�Ƃ��ď������s����B�܂��A�����Ώۂ̒l���z��A
     * Collection�ł͂Ȃ��ꍇ�́AIllegalArgumentException���X���[�����B
     *
     * @param obj �����Ώۂ̔z��E<code>Collection</code>
     * @param min �v�f���̍ŏ��l
     * @param max �v�f���̍ő�l
     * @return
     *            �����Ώۂ̔z��E<code>Collection</code>��
     *            �w�肳�ꂽ�ő�l�E�ŏ��l�͈͓̔��ł���Ȃ��
     *            <code>true</code>��Ԃ��B
     *            ����ȊO�̏ꍇ�A<code>false</code>��Ԃ��B
     */
    public static boolean isArrayInRange(Object obj, int min, int max) {

        // ���ؒl�̔z��
        int targetLength = 0;
        if (obj == null) {
            targetLength = 0;
        } else if (obj instanceof Collection) {
            targetLength = ((Collection) obj).size();
        } else if (obj.getClass().isArray()) {
            targetLength = Array.getLength(obj);
        } else {
            // ���ؒl���z��^�ł͂Ȃ��ꍇ�AIllegalArgumentException���X���[
            throw new IllegalArgumentException(obj.getClass().getName() +
                    " is neither Array nor Collection.");
        }

        // ���͂��ꂽ�v�f�����w��͈͈ȊO�Ȃ��false��ԋp
        if (!GenericValidator.isInRange(targetLength, min, max)) {
            return false;
        }
        return true;
    }


    /**
     * ���ؒl��URL�`���̕�����ł��邩�ǂ������`�F�b�N����B
     *
     * <code>null</code> ������A�󕶎���́A�����Ƃ݂Ȃ����B
     *
     * @param value ���ؒl
     * @param allowallschemes �S�ẴX�L�[���������邩�ǂ������w��
     * @param allow2slashes �_�u���X���b�V���������邩�ǂ������w��
     * @param nofragments URL�������֎~���邩�ǂ������w��
     * @param schemesVar ������X�L�[���B
     * ��������ꍇ�̓J���}��؂�Ŏw�肷��B
     * @return
     *            ���ؒl��URL�`���̕�����ł���Ȃ��
     *            <code>true</code>��Ԃ��B
     *            ����ȊO�̏ꍇ�A<code>false</code>��Ԃ��B
     */
    public static boolean isUrl(
            String value, boolean allowallschemes, boolean allow2slashes,
            boolean nofragments, String schemesVar) {

        if (StringUtils.isEmpty(value)) {
            return true;
        }

        // �I�v�V�����̐ݒ�
        int options = 0;
        if (allowallschemes) {
            options += UrlValidator.ALLOW_ALL_SCHEMES ;
        }
        if (allow2slashes) {
            options += UrlValidator.ALLOW_2_SLASHES;
        }
        if (nofragments) {
            options += UrlValidator.NO_FRAGMENTS;
        }

        // �I�v�V�������Ȃ��ꍇ�̓f�t�H���g��GenericValidator���g�p
        if (options == 0 && schemesVar == null) {
            if (GenericValidator.isUrl(value)) {
                return true;
            }
            return false;
        }

        // �X�L�[����String[]�ɕϊ�
        String[] schemes = null;
        if (schemesVar != null) {

            StringTokenizer st = new StringTokenizer(schemesVar, ",");
            schemes = new String[st.countTokens()];

            int i = 0;
            while (st.hasMoreTokens()) {
                schemes[i++] = st.nextToken().trim();
            }
        }

        // �I�v�V��������̏ꍇ��UrlValidator���g�p
        UrlValidator urlValidator = new UrlValidator(schemes, options);
        if (urlValidator.isValid(value)) {
            return true;
        }
        return false;
    }

    /**
     * ���ؒl�̃o�C�g�񒷂��w�肵���ő�l�E�ŏ��l�͈͓̔��ł��邩�ǂ�����
     * �`�F�b�N����B�T�|�[�g����Ă��Ȃ��G���R�[�f�B���O���w�肵���ꍇ�A
     * ��O����������B
     *
     * <code>null</code> ������A�󕶎���́A�����Ƃ݂Ȃ����B
     *
     * @param value ���ؒl
     * @param encoding �`�F�b�N���̕������<code>encoding</code>��
     * @param min �ő�l
     * @param max �ŏ��l
     * @return
     *            ���ؒl�̃o�C�g�񒷂��w�肵���ő�l�E�ŏ��l��
     *            �͈͓��ł���Ȃ��<code>true</code>��Ԃ��B
     *            ����ȊO�̏ꍇ�A<code>false</code>��Ԃ��B
     */
    public static boolean isByteInRange(
            String value, String encoding, int min, int max) {

        // ���ؒl��null�܂��͋󕶎��̎��Atrue�ԋp
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        // �w��G���R�[�f�B���O�Ńo�C�g�����擾
        int bytesLength = 0;
        try {
            bytesLength = StringUtil.getByteLength(value, encoding);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        // �o�C�g���`�F�b�N
        if (!GenericValidator.isInRange(bytesLength, min, max)) {
            return false;
        }
        return true;
    }

    /**
     * ���t���w�肵���͈͓��ł��邩�ǂ����`�F�b�N����B
     *
     * <code>null</code> ������A�󕶎���́A�����Ƃ݂Ȃ����B
     *
     * @param value ���ؒl
     * @param startDateStr
     *            ���t�͈͂̊J�n��臒l�ƂȂ���t�B
     *            <code>datePattern</code>�A�܂��́A
     *            <code>datePatternStrict</code>�Ŏw�肵���`���Őݒ肷�邱�ƁB
     * @param endDateStr
     *            ���t�͈͂̏I����臒l�ƂȂ���t�B
     *            <code>datePattern</code>�A�܂��́A
     *            <code>datePatternStrict</code>�Ŏw�肵���`���Őݒ肷�邱�ƁB
     * @param datePattern �t�H�[�}�b�g������t�p�^�[���B
     * @param datePatternStrict �t�H�[�}�b�g������t�p�^�[���B
     * @return
     *            ���ؒl�����p�J�i�����݂̂���Ȃ镶����ł���Ȃ��
     *            <code>true</code>��Ԃ��B
     *            ����ȊO�̏ꍇ�A<code>false</code>��Ԃ��B
     */
    public static boolean isDateInRange(
            String value, String startDateStr, String endDateStr,
            String datePattern, String datePatternStrict) {

        // ���ؒl��null�܂��͋󕶎��̎��Atrue�ԋp
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        // ���͓��t�̑Ó����`�F�b�N
        Date result = toDate(value, datePattern, datePatternStrict);
        if (result == null) {
            return false;
        }

        if (GenericValidator.isBlankOrNull(startDateStr)
                && GenericValidator.isBlankOrNull(endDateStr)) {
            // ���t�͈͂��w�肳��Ă��Ȃ��ꍇ�͐���Ƃ݂Ȃ�
            return true;
        }

        // �J�n���t�ȍ~���ǂ����`�F�b�N
        if (!GenericValidator.isBlankOrNull(startDateStr)) {
            Date startDate =
                toDate(startDateStr, datePattern, datePatternStrict);

            if (startDate == null) {
                throw new IllegalArgumentException("startDate is unparseable["
                    + startDateStr + "]");
            }

            if (result.before(startDate)) {
                return false;
            }
        }

        // �I�����t�ȑO���ǂ����`�F�b�N
        if (!GenericValidator.isBlankOrNull(endDateStr)) {
            Date endDate = toDate(endDateStr, datePattern, datePatternStrict);

            if (endDate == null) {
                throw new IllegalArgumentException("endDate is unparseable["
                    + endDateStr + "]");
            }

            if (result.after(endDate)) {
                return false;
            }
        }

        return true;
    }

    /**
     * �������Date�^�ɕϊ�����B
     * <br>
     * �ϊ��͈ȉ��̂悤�ɍs����B
     * ������̏ꍇ���A���i�ȓ��t�`�F�b�N���s���邽�߁A
     * 2000/02/31�̂悤�ȁA���݂��Ȃ����t��<code>value</code>
     * �Ɏw�肳�ꂽ�ꍇ�A<code>null</code>���ԋp�����B
     * <ul>
     * <li>
     * <code>datePattern</code>��<code>null</code>�A����сA
     * �󕶎��łȂ��ꍇ<br>
     * ���������l�����Ȃ����t�ϊ����s����B
     * ���Ƃ��΁A<code>datePattern</code>��yyyy/MM/dd�̏ꍇ�A
     * 2000/1/1��ϊ�����ƁA2000/01/01��\��Date�^���ԋp�����B
     * <li>
     * <code>datePatternStrict</code>��<code>null</code>�A����сA
     * �󕶎��łȂ��ꍇ<br>
     * ���������l���������t�ϊ����s����B
     * ���Ƃ��΁A<code>datePattern</code>��yyyy/MM/dd�̏ꍇ�A
     * 2000/1/1��ϊ�����ƁAnull���ԋp�����B
     * <li>
     * <code>datePattern</code>��<code>datePatternStrict</code>��
     * ����������A<code>null</code>�A����сA �󕶎��łȂ��ꍇ<br>
     * <code>datePattern</code>���D�悵�ė��p�����B
     * <li>
     * <code>datePattern</code>��<code>datePatternStrict</code>��
     * ���������<code>null</code>�A�܂��́A�󕶎��̏ꍇ<br>
     * ��O���ԋp�����B
     * </ul>
     * <li>
     * <code>value</code>��<code>null</code>�A����сA
     * �󕶎��̏ꍇ�A<code>null</code>���ԋp�����B
     *
     * @param value �ϊ��Ώۂ̕�����
     * @param datePattern ���t�p�^�[���i���������l�����Ȃ��p�^�[���w��j
     * @param datePatternStrict ���t�p�^�[���i���������l�������p�^�[���w��j
     * @return �����񂩂�ϊ����ꂽDate�C���X�^���X�B�ϊ����s�\�ȏꍇ��null�B
     */
    public static Date toDate(String value, String datePattern,
            String datePatternStrict) {

        if (StringUtils.isEmpty(value)) {
            return null;
        }

        Date result = null;
        
        // �����`�F�b�N�Ȃ��̕ϊ�
        if (datePattern != null && datePattern.length() > 0) {
            result = GenericTypeValidator.formatDate(value,
                            datePattern, false);

        // �����`�F�b�N����̕ϊ�
        } else if (datePatternStrict != null
                && datePatternStrict.length() > 0) {
            result = GenericTypeValidator.formatDate(value,
                            datePatternStrict, true);

        // ���t�p�^�[�����ݒ肳��Ă��Ȃ�
        } else {
            throw new IllegalArgumentException(
                    "datePattern or datePatternStrict must be specified.");
        }
        
        return result;
    }
}
