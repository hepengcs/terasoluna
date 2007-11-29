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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;

/**
 * �����񑀍���s�����[�e�B���e�B�N���X�B
 *
 * <p>
 *  ���p�E�S�p�ϊ��AHTML���ꕶ���G�X�P�[�v�ASQL��LIKE��
 *  �G�X�P�[�v���A�����񑀍�ɕK�v�ȋ@�\��񋟂���B
 * </p>
 * 
 */
public class StringUtil {

    /**
     * ���s����OS�ŗp��������s�R�[�h���擾����B
     */
    public static final String LINE_SEP
        = System.getProperty("line.separator");

    /**
     * �S�p�������X�g�B
     */
    private static final String ZENKAKU_LIST = 
        "�I�h���������f�i�j���{�C�|�D�^�O�P�Q�R�S"
        + "�T�U�V�W�X�F�G�������H���`�a�b�c�d�e�f�g"
        + "�h�i�j�k�l�m�n�o�p�q�r�s�t�u�v�w�x�y�m��"
        + "�n�O�Q�M��������������������������������"
        + "���������������������o�b�p�P�B�u�v�A�E"
        + "�@�B�D�F�H�������b�[�A�C�G�I�i�j�k�l�m"
        + "�}�~�������������������������J�K�@";


    /**
     * �S�p�J�i���X�g(�J�A�T�A�^�A�n)�s�ƃE�B
     */
    private static final String ZENKAKU_KASATAHA_LIST = 
        "�J�L�N�P�R�T�V�X�Z�\�^�`�c�e�g�n�q�t�w�z�E";

    /**
     * �S�p�J�i���X�g(�K�A�U�A�_�A�o)�s�ƃ��B
     */
    private static final String ZENKAKU_GAZADABA_LIST = 
        "�K�M�O�Q�S�U�W�Y�[�]�_�a�d�f�h�o�r�u�x�{��";

    /**
     * �S�p�J�i(��"[&yen;30f7])�B
     */
    private static final Character ZENKAKU_WA_DAKUTEN = 
        new Character('\u30f7');

    /**
     * �S�p�J�i(��"[&yen;30fa])�B
     */
    private static final Character ZENKAKU_WO_DAKUTEN = 
        new Character('\u30fa');

    /**
     * �S�p�J�i���X�g(�p)�s�B
     */
    private static final String ZENKAKU_PA_LIST = "�p�s�v�y�|";

    /**
     * ���p�������X�g�B
     */
    private static final String HANKAKU_LIST = 
        "!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGH"
      + "IJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnop"
      + "qrstuvwxyz{|}~������������������������"
      + "���������������� ";

    /**
     * ���p�J�i���X�g(�������)�s�Ƴ�B
     */
    private static final String HANKAKU_KASATAHA_LIST
        = "�������������������γ";
    /**
     * ���p�J�i���X�g(�)�s�B
     */
    private static final String HANKAKU_HA_LIST = "�����";

    /**
     * �w�肳�ꂽ���������p�X�y�[�X���ǂ����𔻕ʂ���B
     * ���� StringUtil �̃g�����n���\�b�h�ŋ��ʂŗ��p����B
     *
     * @param c �Ώە���
     * @return �z���C�g�X�y�[�X�ł���Ƃ��� true
     */
    public static boolean isWhitespace(char c) {
        return c == ' ';
    }

    /**
     * �w�肳�ꂽ�������S�p�܂��͔��p�X�y�[�X���ǂ����𔻕ʂ���B
     * ���� StringUtil �̃g�����n���\�b�h�ŋ��ʂŗ��p����B
     *
     * @param c �Ώە���
     * @return �S�p�܂��͔��p�X�y�[�X�ł���Ƃ��� true
     */
    public static boolean isZenHankakuSpace(char c) {
        return (c == '�@' || c == ' ');
    }

    /**
     * ������̉E���̃z���C�g�X�y�[�X���폜����B������
     * null �̂Ƃ��� null ��Ԃ��B
     *
     * <p>
     * �Ⴆ�� Oracle �̏ꍇ�A CHAR �^�̗�̒l��
     * ResultSet.getString() �Ŏ擾����ƁA��`���܂ŃX�y�[�X��
     * �p�f�B���O���ꂽ�����񂪕Ԃ����B����A VARCHAR2 �̏ꍇ��
     * �E�[�̃X�y�[�X�̓g���~���O����邽�߁A���̂܂܂ł͗��҂𐳂���������
     * ��r���邱�Ƃ��o���Ȃ��B�܂��A��ʓ��͂��ꂽ������̉E�[�ɃX�y�[�X��
     * �܂܂�Ă���ꍇ�ɁA����� VARCHAR2 �̗�ɑ}�������
     * �X�y�[�X�����̂܂܊i�[����邪�A�E�[�̃X�y�[�X���g���~���O���铮�삪
     * �Ó��ȏꍇ�������B���̂悤�ȂƂ��ɂ��̃��\�b�h�𗘗p����B
     * </p>
     * �� �S�p�X�y�[�X�̓g���~���O����Ȃ��B
     *
     * @param str �ϊ��O�̕�����
     * @return �ϊ���̕�����
     */
    public static String rtrim(String str) {
        if (str == null) {
            return null;
        }

        int length = str.length();
        while ((0 < length) && isWhitespace(str.charAt(length - 1))) {
            length--;
        }

        return length < str.length() ? str.substring(0, length) : str;
    }

    /**
     * ������̍����̃z���C�g�X�y�[�X���폜����B
     * 
     * ������ null �̂Ƃ��� null ��Ԃ��B<br>
     * �� �S�p�X�y�[�X�̓g���~���O����Ȃ��B
     *
     * @param str �ϊ��O�̕�����
     * @return �ϊ���̕�����
     */
    public static String ltrim(String str) {
        if (str == null) {
            return null;
        }

        int start = 0;
        int length = str.length();
        while ((start < length) && isWhitespace(str.charAt(start))) {
            start++;
        }

        return start > 0 ? str.substring(start, length) : str;
    }

    /**
     * ������̗����̃z���C�g�X�y�[�X���폜����B
     * 
     * ������ null �̂Ƃ��� null ��Ԃ��B<br>
     * �� �S�p�X�y�[�X�̓g���~���O����Ȃ��B
     *
     * @param str �ϊ��O�̕�����
     * @return �ϊ���̕�����
     */
    public static String trim(String str) {
        return StringUtils.trim(str);
    }


    /**
     * ������̉E���̑S�p����є��p�X�y�[�X���폜����B������
     * null �̂Ƃ��� null ��Ԃ��B
     *
     * <p>
     * �Ⴆ�� Oracle �̏ꍇ�A CHAR �^�̗�̒l��
     * ResultSet.getString() �Ŏ擾����ƁA��`���܂ŃX�y�[�X��
     * �p�f�B���O���ꂽ�����񂪕Ԃ����B����A VARCHAR2 �̏ꍇ��
     * �E�[�̃X�y�[�X�̓g���~���O����邽�߁A���̂܂܂ł͗��҂𐳂���������
     * ��r���邱�Ƃ��o���Ȃ��B�܂��A��ʓ��͂��ꂽ������̉E�[�ɃX�y�[�X��
     * �܂܂�Ă���ꍇ�ɁA����� VARCHAR2 �̗�ɑ}�������
     * �X�y�[�X�����̂܂܊i�[����邪�A�E�[�̃X�y�[�X���g���~���O���铮�삪
     * �Ó��ȏꍇ�������B���̂悤�ȂƂ��ɂ��̃��\�b�h�𗘗p����B
     * </p>
     *
     * @param str �ϊ��O�̕�����
     * @return �ϊ���̕�����
     */
    public static String rtrimZ(String str) {
        if (str == null) {
            return null;
        }

        int length = str.length();
        while ((0 < length) && isZenHankakuSpace(str.charAt(length - 1))) {
            length--;
        }

        return length < str.length() ? str.substring(0, length) : str;
    }

    /**
     * ������̍����̑S�p����є��p�X�y�[�X���폜����B
     * 
     * ������ null �̂Ƃ��� null ��Ԃ��B<br>
     *
     * @param str �ϊ��O�̕�����
     * @return �ϊ���̕�����
     */
    public static String ltrimZ(String str) {
        if (str == null) {
            return null;
        }

        int start = 0;
        int length = str.length();
        while ((start < length) && isZenHankakuSpace(str.charAt(start))) {
            start++;
        }

        return start > 0 ? str.substring(start, length) : str;
    }

    /**
     * ������̗����̑S�p����є��p�X�y�[�X���폜����B
     * 
     * ������ null �̂Ƃ��� null ��Ԃ��B<br>
     *
     * @param str �ϊ��O�̕�����
     * @return �ϊ���̕�����
     */
    public static String trimZ(String str) {
        return ltrimZ(rtrimZ(str));
    }

    /**
     * �w�肳�ꂽ�N���X������Z�k�N���X���i�p�b�P�[�W�C���Ȃ��j���擾����B
     *
     * @param longClassName �N���X��
     * @return �Z�k�N���X��
     */
    public static String toShortClassName(String longClassName) {
        return ClassUtils.getShortClassName(longClassName);
    }

    /**
     * �w�肳�ꂽ�����񂩂疖���̊g���q���擾����B
     * 
     * �g���q���Ȃ��ꍇ�͋󕶎����Ԃ��B
     *
     * @param name �g���q���̖��O
     * @return �g���q
     */
    public static String getExtension(String name) {
        int index = name.lastIndexOf('.');
        return (index < 0) ? "" : name.substring(index);
    }

    /**
     * �o�C�g�z���16�i������ɕϊ�����B
     *
     * @param byteArray �o�C�g�z��
     * @param delim �f���~�^
     * @return 16�i������
     */
    public static String toHexString(byte[] byteArray, String delim) {
        if (delim == null) {
            delim = "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < byteArray.length; i++) {
            if (i > 0) {
                sb.append(delim);
            }
            String hex = Integer.toHexString(byteArray[i] & 0x00ff)
                            .toUpperCase();
            if (hex.length() < 2) {
                sb.append("0");
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * �w�肳�ꂽ������̓�������啶���ɂ���B
     *
     * @param str �ϊ��O�̕�����
     * @return �ϊ���̕�����
     */
    public static String capitalizeInitial(String str) {
        if (str == null || "".equals(str)) {
            return str;
        }
        char[] chars = str.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return new String(chars);
    }

    /**
     * CSV�`���̕�����𕶎���̔z��ɕϊ�����B
     * 
     * <p>
     * ������̐擪���J���}��
     * �n�܂��Ă�����A������̍Ōオ�J���}�ŏI����Ă���ꍇ�ɂ́A
     * ���ꂼ��ϊ����ʂ̔z��̍ŏ����A���邢�͍Ō�̗v�f���󕶎���ƂȂ�悤��
     * �ϊ������B</p>
     * <p>�J���}���A�����Ă���ꍇ�ɂ́A�󕶎���Ƃ��ĕϊ������B</p>
     * <p>csvString �� null �������ꍇ�ɂ́A
     * �v�f��0�̔z��ɕϊ������B
     *
     * @param csvString CSV�`���̕�����
     * @return �J���}�ŕ������ꂽ�������v�f�Ɏ��z��
     */
    public static String[] parseCSV(String csvString) {
        if (csvString == null) {
            return new String [0];
        }
        if ("".equals(csvString)) {
            return new String [] {csvString};
        }

        Collection<String> result = new ArrayList<String>();

        char [] chars = csvString.toCharArray();
        int prevCommaIndex = -1;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == ',') {
                if (i == prevCommaIndex + 1) {
                    result.add("");
                } else {
                    result.add(new String(chars,
                                        prevCommaIndex + 1,
                                        i - (prevCommaIndex + 1)));
                }
                if (i == chars.length - 1) {
                    result.add("");
                }
                prevCommaIndex = i;
            } else if (i == chars.length - 1) {
                result.add(new String(chars,
                                    prevCommaIndex + 1,
                                    i - (prevCommaIndex + 1) + 1));
            }
        }

        return result.toArray(new String[0]);
    }
    
    /**
     * CSV�`���̕�����𕶎���̔z��ɕϊ�����B
     * 
     * <p>
     * ������̐擪���J���}��
     * �n�܂��Ă�����A������̍Ōオ�J���}�ŏI����Ă���ꍇ�ɂ́A
     * ���ꂼ��ϊ����ʂ̔z��̍ŏ����A���邢�͍Ō�̗v�f���󕶎���ƂȂ�悤��
     * �ϊ������B</p>
     * <p>�J���}���A�����Ă���ꍇ�ɂ́A�󕶎���Ƃ��ĕϊ������B</p>
     * <p>csvString �� null �������ꍇ�ɂ́A
     * �v�f��0�̔z��ɕϊ������B<br>
     * �G�X�P�[�v������ɐݒ肳�ꂽ������̎��ɂ���J���}�͋�؂蕶��
     * �Ƃ��Ă͔F�����Ȃ��B<br>
     * �G�X�P�[�v������̌�̃G�X�P�[�v������̓G�X�P�[�v�����Ƃ���
     * �F�����Ȃ��B
     * </p>
     * 
     * @param csvString CSV�`���̕�����
     * @param escapeString �G�X�P�[�v������
     * @return �J���}�ŕ������ꂽ�������v�f�Ɏ��z��
     */
    public static String[] parseCSV(String csvString, String escapeString) {
        if (csvString == null) {
            return new String [0];
        }
        if ("".equals(csvString)) {
            return new String [] {csvString};
        }

        Collection<String> result = new ArrayList<String>();

        char [] chars = csvString.toCharArray();
        StringBuilder str = new StringBuilder();
        boolean escape = false;
        for (int i = 0; i < chars.length; i++) {
            if (!escape && chars[i] == ',') {
                result.add(str.toString());
                str = new StringBuilder();
                escape = false;
            } else {
                if (escapeString != null
                    && escapeString.indexOf(chars[i]) >= 0) {
                    // �G�X�P�[�v�����̌�̃G�X�P�[�v�����͒ʏ�̕������
                    // �F������B
                    if (escape) {
                        str.append(chars[i]);
                        escape = false;
                    } else {
                        escape = true;
                    }
                } else {
                    escape = false;
                    str.append(chars[i]);
                }
            }
        }
        result.add(str.toString());
        return result.toArray(new String[0]);
    }

    /**
     * �����̃}�b�v�̃_���v���擾����B
     * 
     * �l�I�u�W�F�N�g�ɔz�񂪊܂܂�Ă���ꍇ�A�e�v�f�I�u�W�F�N�g��
     * toString()���s���A��������o�͂���B
     *
     * @param map ���o�̓}�b�v
     * @return �_���v������
     */
    public static String dump(Map<?, ?> map) {

        if (map == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(LINE_SEP);
        sb.append("Map{");
        sb.append(LINE_SEP);

        Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
            Object key = it.next();
            // �L�[�I�u�W�F�N�g
            String appendKey = null;
            if (key == null) {
                appendKey = "null";
            } else {
                appendKey = key.toString();
            }
            sb.append(appendKey);
            sb.append('=');
            Object valueObj = map.get(key);
            if (valueObj == null) {
                sb.append("null");
            } else if (valueObj.getClass().isArray()) {
                // �z��^�Ȃ�Ίe�v�f���擾����
                sb.append(getArraysStr((Object[]) valueObj));
            } else {
                sb.append(valueObj.toString());
            }
            sb.append(LINE_SEP);
        }
        sb.append("}");
        return sb.toString();
    }

    /**
     * �_���v�Ώۂ̒l�I�u�W�F�N�g���z��`���̏ꍇ�A
     * �z��v�f�łȂ��Ȃ�܂ŌJ��Ԃ��l���擾����B
     *
     * @param arrayObj �z��^�I�u�W�F�N�g
     * @return �z��_���v�Ώە�����
     */
    public static String getArraysStr(Object[] arrayObj) {
        return ArrayUtils.toString(arrayObj, null);
    }

    /**
     * ���p�������S�p������ɕϊ�����B
     * 
     * <p>
     * �J�i�����ɑ��_�܂��͔����_�������ꍇ�́A�\�Ȍ���P�����ɕϊ�����B<br>
     * (��) '�' + '�' =&gt; '�K'</p>
     *
     * <p>�܂����̕ϊ������ł͈ȉ��̑S�p�������ϊ��敶���Ƃ����B</p>
     *
     * <p><ul>
     *  <li>�u���v</li>
     *  <li>�u��"�v('��'�ɑ��_�F&yen;u30f7)</li>
     *  <li>�u��"�v('��'�ɑ��_�F&yen;30fa)</li>
     * </ul></p>
     *
     * @param value ���p������
     * @return �S�p������
     */
    public static String hankakuToZenkaku(String value) {

        if (value == null || "".equals(value)) {
            return value;
        }

        char[] chars = value.toCharArray();
        StringBuilder returnValue = new StringBuilder();
        String getValue = null;
        Character nextvalue = null;

        for (int i = 0; i < chars.length; i++) {

            getValue = getZenkakuMoji(chars[i]);

            if (getValue != null) {
                returnValue.append(getValue);
            } else if (i == (chars.length - 1)) {
                // �Ō�̕���
                getValue = getZenkakuKasatahaMoji(chars[i]);
                if (getValue != null) {
                    // �������������������γ
                    returnValue.append(getValue);
                } else if (new Character(chars[i]).equals(
                        new Character('�'))) {
                        returnValue.append("��");
                } else if (new Character(chars[i]).equals(
                        new Character('�'))) {
                        returnValue.append("��");
                } else {
                    returnValue.append(String.valueOf(chars[i]));
                }
            } else {
                nextvalue = new Character(chars[i + 1]);
                if (nextvalue.equals(new Character('�'))) {
                    getValue = getZenkakuDakuMoji(chars[i]);
                    if (getValue != null) {
                        // �޷޸޹޺޻޼޽޾޿��������������������޳�
                        returnValue.append(getValue);
                        i++;
                    } else if (new Character(chars[i]).equals(
                            new Character('�'))) {
                        // ��
                        returnValue.append(ZENKAKU_WA_DAKUTEN);
                        i++;
                    } else if (new Character(chars[i]).equals(
                            new Character('�'))) {
                        // ��
                        returnValue.append(ZENKAKU_WO_DAKUTEN);
                        i++;
                    } else {
                        returnValue.append((String.valueOf(chars[i]) + "�J"));
                        i++;
                    }
                } else if (nextvalue.equals(new Character('�'))) {
                    getValue = getZenkakuHandakuMoji(chars[i]);
                    if (getValue != null) {
                        // ����������
                        returnValue.append(getValue);
                        i++;
                    } else {
                        // �߷߸߹ߺ߻߼߽߾߿����������߳�
                        getValue = getZenkakuKasatahaMoji(chars[i]);
                        returnValue.append((String.valueOf(getValue) + "�K"));
                        i++;
                    }
                } else {
                    getValue = getZenkakuKasatahaMoji(chars[i]);
                    if (getValue != null) {
                        // �������������������γ
                        returnValue.append(getValue);
                    } else if (new Character(chars[i]).equals(
                            new Character('�'))) {
                            returnValue.append("��");
                    } else if (new Character(chars[i]).equals(
                            new Character('�'))) {
                            returnValue.append("��");
                    } else {
                        returnValue.append(String.valueOf(chars[i]));
                    }
                }
            }
        }
        return returnValue.toString();
    }

    /**
     * �S�p������𔼊p������ɕϊ�����B
     * 
     * <p>
     * ���_�܂��͔����_�����J�i�����́A�Q�����ɕ��������B<br>
     * (��) '�K' =&gt; '�' + '�'</p>
     *
     * <p>�܂����̕ϊ������ł͈ȉ��̑S�p�������ϊ��������Ǝ󂯕t����B</p>
     *
     * <p><ul>
     *  <li>�u���v</li>
     *  <li>�u��"�v('��'�ɑ��_�F&yen;u30f7)</li>
     *  <li>�u��"�v('��'�ɑ��_�F&yen;30fa)</li>
     * </ul></p>
     *
     * @param value �S�p������
     * @return ���p������
     */
    public static String zenkakuToHankaku(String value) {

        if (value == null || "".equals(value)) {
            return value;
        }

        char[] chars = value.toCharArray();
        StringBuilder returnValue = new StringBuilder();
        String getValue = null;

        for (int i = 0; i < chars.length; i++) {

            getValue = getHankakuMoji(chars[i]);

            if (getValue != null) {
                returnValue.append(getValue);
            } else {
                returnValue.append(String.valueOf(chars[i]));
            }
        }
        return returnValue.toString();
    }

    /**
     * ���p������S�p�����ɕϊ�����B
     * 
     * �S�p�������X�g�̕ϊ��������s���B
     * 
     * @param c ���p����
     * @return �S�p����
     */
    private static String getZenkakuMoji(char c) {

        int index = HANKAKU_LIST.indexOf(c);
        
        if (index >= 0) {
            return String.valueOf(ZENKAKU_LIST.charAt(index));
        }
        return null;
    }

    /**
     * ���p������S�p�����ɕϊ�����B
     * 
     * �S�p�J�i(�K�A�U�A�_�A�o)�s�ƃ��̕ϊ��������s���B
     * 
     * @param c ���p����
     * @return �S�p����
     */
    private static String getZenkakuDakuMoji(char c) {

        int index = HANKAKU_KASATAHA_LIST.indexOf(c);
        if (index >= 0) {
            return String.valueOf(ZENKAKU_GAZADABA_LIST.charAt(index));
        }
        return null;
    }

    /**
     * ���p������S�p�����ɕϊ�����B
     * 
     * �S�p�J�i(�p)�s�̕ϊ��������s���B
     * 
     * @param c ���p����
     * @return �S�p����
     */
    private static String getZenkakuHandakuMoji(char c) {

        int index = HANKAKU_HA_LIST.indexOf(c);
        if (index >= 0) {
            return String.valueOf(ZENKAKU_PA_LIST.charAt(index));
        }
        return null;        
    }

    /**
     * ���p������S�p�����ɕϊ�����B
     * 
     * �S�p�J�i(�J�A�T�A�^�A�n)�s�ƃE�̕ϊ��������s���B
     * 
     * @param c ���p����
     * @return �S�p����
     */
    private static String getZenkakuKasatahaMoji(char c) {

        int index = HANKAKU_KASATAHA_LIST.indexOf(c);
        if (index >= 0) {
            return String.valueOf(ZENKAKU_KASATAHA_LIST.charAt(index));
        }
        return null;
    }

    /**
     * �S�p�����𔼊p�����ɕϊ�����B
     * 
     * ���̃��\�b�h�ł͈ȉ��̕�����ΏۂƂ����ϊ��������s���B<br>
     *
     * <p><ul>
     *  <li>���p�������X�g</li>
     *  <li>���p�J�i(�������)�s�Ƴ</li>
     *  <li>���p�J�i(�ޤ�ޤ�ޤ��)�s�Ƴ�</li>
     *  <li>���p�J�i(��)�s</li>
     *  <li>���p�J�i(�ޤ��)</li>
     * </ul></p>
     * 
     * @param c �S�p����
     * @return ���p����
     */
    private static String getHankakuMoji(char c) {

        int index = 0;
        String value = null;

        index = ZENKAKU_LIST.indexOf(c);
        if (index >= 0) {
            return String.valueOf(HANKAKU_LIST.charAt(index));
        }
        
        index = ZENKAKU_KASATAHA_LIST.indexOf(c);
        if (index >= 0) {
            // �J�L�N�P�R�T�V�X�Z�\�^�`�c�e�g�n�q�t�w�z�E
            return String.valueOf(HANKAKU_KASATAHA_LIST.charAt(index));
        }
        
        index = ZENKAKU_GAZADABA_LIST.indexOf(c);
        if (index >= 0) {
            // �K�M�O�Q�S�U�W�Y�[�]"�_�a�d�f�h�o�r�u�x�{��
            value = String.valueOf(HANKAKU_KASATAHA_LIST.charAt(index));
            return value + "�";
        }
        
        index = ZENKAKU_PA_LIST.indexOf(c);
        if (index >= 0) {
            // �p�s�v�y�|
            value = String.valueOf(HANKAKU_HA_LIST.charAt(index));
            return value + "�";
        } else if ((new Character(c)).equals(new Character('��'))) {
            // ��
            return "�";
        } else if ((new Character(c)).equals(new Character('��'))) {
            // ��
            return "�";
        } else if ((new Character(c)).equals(ZENKAKU_WA_DAKUTEN)) {
            // ��"[\u30f7]
            return "��";
        } else if ((new Character(c)).equals(ZENKAKU_WO_DAKUTEN)) {
            // ��"[\u30fa]
            return "��";
        } else {
            // �Y���Ȃ�
            return null;
        }
    }

    /**
     * HTML���^������ϊ��B
     * 
     * <p>
     *  "&lt;"�A"&gt;"�A"&amp;"�A"&quot;"�Ƃ������AHTML����
     *  ���̂܂܏o�͂���Ɩ�肪���镶����
     *  "&amp;lt;"�A"&amp;gt;"�A"&amp;amp;"�A"&amp;quot;"
     *  �ɕϊ�����B
     * </p>
     *
     * @param str �ϊ����镶����
     * @return �ϊ���̕�����
     */
    public static String filter(String str) {
        char[] cbuf = str.toCharArray();
        StringBuilder sbui = new StringBuilder();
        for (int i = 0; i < cbuf.length; i++) {
            if (cbuf[i] == '&') {
                sbui.append("&amp;");
            } else if (cbuf[i] == '<') {
                sbui.append("&lt;");
            } else if (cbuf[i] == '>') {
                sbui.append("&gt;");
            } else if (cbuf[i] == '"') {
                sbui.append("&quot;");
            } else {
                sbui.append(cbuf[i]);
            }
        }
        return sbui.toString();
    }

    /**
     * LIKE�q��̃p�^�[��������ŗp����G�X�P�[�v�����B
     */
    public static final String LIKE_ESC_CHAR = "~";

    /**
     * <p>���������������LIKE�q��̃p�^�[��������ɕϊ�����B</p>
     *
     * <p>�ϊ����[���͈ȉ��̒ʂ�B</p>
     *
     * <ol>
     *   <li><code>LIKE_ESC_CHAR</code> �� <code>LIKE_ESC_CHAR</code> ��
     *       �G�X�P�[�v����B</li>
     *   <li>'%'��'_'�� <code>LIKE_ESC_CHAR</code> �ŃG�X�P�[�v����B</li>
     *   <li>������'%'��ǉ�����B</li>
     * </ol>
     *
     * @param condition ��������������
     * @return �ϊ���̌�������������
     */
    public static String toLikeCondition(String condition) {
        final char esc = (LIKE_ESC_CHAR.toCharArray())[0];
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < condition.length(); i++) {
            char c = condition.charAt(i);
            if (c == esc) {
                result.append(esc);
                result.append(esc);
            } else if (c == '%' || c == '_') {
                result.append(esc);
                result.append(c);
            } else {
                result.append(c);
            }
        }
        result.append('%');
        return result.toString();
    }
    
    /**
     * �w�肳�ꂽ������̃o�C�g�񒷂��擾����B
     * �������̃G���R�[�f�B���O�Ńo�C�g��ɕϊ����邪�A
     * �G���R�[�h���w�肳��Ă��Ȃ������ꍇ�̓f�t�H���g�̃G���R�[�f�B���O��
     * �o�C�g��ɕϊ����s���B
     *
     * @param value �o�C�g�񒷂��擾����Ώۂ̕�����
     * @param encoding �����G���R�[�f�B���O
     * @return �o�C�g��
     * @throws UnsupportedEncodingException �T�|�[�g����Ă��Ȃ�
     * �G���R�[�f�B���O���w�肵���Ƃ����������O�B
     */
    public static int getByteLength(String value, String encoding) 
        throws UnsupportedEncodingException {
        if (value == null || "".equals(value)) {
            return 0;
        }
        
        byte[] bytes = null;
        if (encoding == null || "".equals(encoding)) {
            bytes = value.getBytes();
        } else {
            try {
                bytes = value.getBytes(encoding);
            } catch (UnsupportedEncodingException e) {
                throw e;
            }
        }
        return bytes == null ? 0 : bytes.length;
    }
}
