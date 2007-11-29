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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ���t�E�����E�J�����_�[�֘A�̃��[�e�B���e�B�N���X�B
 * 
 */
public class DateUtil {

    /**
     * ���O�N���X
     */
    private static Log log = LogFactory.getLog(DateUtil.class);

    /**
     * �V�X�e���������擾����B
     *
     * <p>Web�T�[�o��AP�T�[�o�𕪗�������N���X�^�\���ɂ����ꍇ�́A�}�V����
     * ���V�X�e�����t���قȂ�\��������B���������邽�߁A�V�X�e�����t
     * �擾�ɂ͕K�����̃��\�b�h�𗘗p���A�K�v�ɉ����ē���}�V���̓��t���擾
     * ����Ȃǂ̑[�u���Ƃ��悤�ɂ��Ă����B</p>
     *
     * @return �V�X�e������
     */
    public static java.util.Date getSystemTime() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    /**
     * java.util.Date�C���X�^���X��a��Ƃ��Ďw��̃t�H�[�}�b�g��
     * �ϊ�����B
     * 
     * <p>
     *  ApplicationResources.properties 
     *  �Ŏw�肳�ꂽ���t�t�H�[�}�b�g��p���āA����A�a��̕ϊ���
     *  �s�����Ƃ��ł���B<br>
     *  ���L�́A�a��̋��E�ƂȂ���t�A�a��A�A���t�@�x�b�g��������
     *  �ݒ��ł���B
     *  <strong> ApplicationResources.properties �ɂ��
     *  �a��̐ݒ��</strong><br>
     *  <code><pre>
     *  wareki.gengo.0.name = ����
     *  wareki.gengo.0.roman = H
     *  wareki.gengo.0.startDate = 1989/01/08
     *  wareki.gengo.1.name = ���a
     *  wareki.gengo.1.roman = S
     *  wareki.gengo.1.startDate = 1926/12/25
     *  wareki.gengo.2.name = �吳
     *  wareki.gengo.2.roman = T
     *  wareki.gengo.2.startDate = 1912/07/30
     *  wareki.gengo.3.name = ����
     *  wareki.gengo.3.roman = M
     *  wareki.gengo.3.startDate = 1868/09/04
     *  </pre></code>
     * </p>
     * 
     * <strong>�t�H�[�}�b�g������</strong><br>
     * <p>�t�H�[�}�b�g�́Ajava.text.SimpleDateFormat �N���X��
     * <i>�����p�^�[��������</i> �Ƃ��ĉ��߂���邪�A�ȉ��̃p�^�[�������̉��߂�
     * �i�f�t�H���g���P�[���́j SimpleDateFormat �N���X�ƈقȂ�B
     * </p>
     *
     * <div width="90%" align="center">
     *  <table border="1">
     *   <tr>
     *    <th>�L��</th>
     *    <th><code>&nbsp;SimpleDateFormat</code>&nbsp;</th>
     *    <th><code>&nbsp;dateToWarekiString()</code>&nbsp;</th>
     *   </tr>
     *   <tr>
     *    <td>G</td>
     *    <td align="left">�I��<br><br>��F<br>AD</td>
     *    <td align="left">�a���<br><br>
     *                     ��F<br>
     *                    �i4�ȏ�̘A�������p�^�[�������j<br>
     *                     �����A�吳�A���a�A����<br>
     *                     �i3�ȉ��̘A�������p�^�[�������j<br>
     *                     M�AT�AS�AH</td>
     *   </tr>
     *   <tr>
     *    <td>y</td>
     *    <td align="left">�N�i����j<br><br>��F<br>2002</td>
     *    <td align="left">�N�i�a��j<br><br>��F<br>14</td>
     *   </tr>
     *   <tr>
     *    <td>E</td>
     *    <td align="left">�j��<br><br>��F<br>Tuesday</td>
     *    <td align="left">�j���i���{��\�L�j<br><br>
     *                     ��F<br>
     *                    �i4�ȏ�̘A�������p�^�[�������j<br>
     *                     ���j���A�Ηj���A���j��<br>
     *                     �i3�ȉ��̘A�������p�^�[�������j<br>
     *                     ���A�΁A��</td>
     *   </tr>
     * </table>
     * </div>
     *
     * <p>�����̂����A�j���iE�j�ɂ��Ă� SimpleDateFotmat
     * �̃C���X�^���X�쐬���ɁA���P�[���� &quot;ja&quot;
     * �Ɏw�肷�邱�Ƃŕϊ������B</p>
     *
     * <p>�a������A����јa��N�ɂ��ẮAgetWarekiGengoName()�A
     * getWarekiGengoRoman()�AgetWarekiYear() ���\�b�h�ɂ���Ď擾����B
     * �����̃��\�b�h�ŎQ�Ƃ���a��̐ݒ�́AAplicationResources �t�@�C����
     * �ȉ��̏����ōs���B</p>
     *
     * <p><code><pre>
     * wareki.gengo.<i>ID</i>.name=<i>������</i>
     * wareki.gengo.<i>ID</i>.roman=<i>�����̃��[�}���\�L</i>
     * wareki.gengo.<i>ID</i>.startDate=<i>�����@�{�s���i����:yyyy/MM/dd�`���j</i>
     * </pre></code></p>
     *
     * <p>ID�́A��L�̎O�̐ݒ���֘A�t�����邽�߂̂��̂ł���A�C�ӂ̕������
     * �w��ł���B</p>
     *
     * @param format �t�H�[�}�b�g
     * @param date ������ɕϊ����鎞���f�[�^
     * @return �a��Ƃ��ăt�H�[�}�b�g���ꂽ������
     */
    public static String dateToWarekiString(String format,
                                            java.util.Date date) {

        // SimpleDateFormat�ɂ��t�H�[�}�b�g�̑O�Ɍ���'G'�A����єN'y'��
        // �p�^�[��������a��ɒu������
        StringBuilder sb = new StringBuilder();
        boolean inQuote = false; // �V���O���N�H�[�g�̒��ł��邩�ǂ���
        char prevCh = 0;
        int count = 0;
        for (int i = 0; i < format.length(); i++) {
            char ch = format.charAt(i);
            if (ch != prevCh && count > 0) {
                if (prevCh == 'G' && count >= 4) {
                    sb.append(getWarekiGengoName(date));
                } else if (prevCh == 'G') {
                    // �����̃��[�}���\�L�̏ꍇ�́A�N�H�[�g���Ă���
                    sb.append('\'');
                    sb.append(getWarekiGengoRoman(date));
                    sb.append('\'');
                } else if (prevCh == 'y') {
                    sb.append(getWarekiYear(date));
                }
                count = 0;
            }

            if (ch == '\'') {
                // �A�������V���O���N�H�[�g�ł���΃V���O���N�H�[�g�̃��e����
                // �Ƃ��ĉ��߂���B
                if ((i + 1) < format.length()
                        && format.charAt(i + 1) == '\'') {
                    sb.append('\'');
                    ++i;
                } else {
                    sb.append('\'');
                    inQuote = !inQuote;
                }
            } else if (!inQuote && (ch == 'G' || ch == 'y')) {
                // ch�͘a��ϊ��œƎ��ɉ��߂���t�H�[�}�b�g�����ł���ꍇ�́A
                // �J��Ԃ��񐔂��J�E���g����B
                prevCh = ch;
                ++count;
            } else {
                // ���̑��̕����́A�f�ʂ�����
                sb.append(ch);
            }
        }

        // �t�H�[�}�b�g���̍Ō�̃A�C�e������������B
        if (count > 0) {
            if (prevCh == 'G' && count >= 4) {
                sb.append(getWarekiGengoName(date));
            } else if (prevCh == 'G') {
                sb.append('\'');
                sb.append(getWarekiGengoRoman(date));
                sb.append('\'');
            } else if (prevCh == 'y') {
                sb.append(getWarekiYear(date));
            }
        }

        SimpleDateFormat sdf = new SimpleDateFormat(sb.toString(),
                                                    Locale.JAPAN);

        sdf.getCalendar().setLenient(false);
        sdf.setLenient(false);
        return sdf.format(date);
    }

    /**
     * ApplicationResources �t�@�C���ɂ����Ęa��֘A�̐ݒ��
     * �擾����ۂ̃L�[�̃v���t�B�b�N�X�B
     */
    private static final String GENGO_KEY = "wareki.gengo.";

    /**
     * �����{�s�����猳�����ւ̃}�b�v�B
     */
    private static final Map<Date, String> GENGO_NAME
            = new HashMap<Date, String>();
    
    /**
     * �����{�s�����猳���̃��[�}���\�L�i�Z�k�`�j�ւ̃}�b�v�B
     */
    private static final Map<Date, String> GENGO_ROMAN =
            new HashMap<Date, String>();
    
    /**
     * �a��̊e�������{�s���ꂽ������t�̃��X�g�B���X�g�̐擪����A
     * �Â����ɕ��ׂ���B
     */
    private static final Date[] GENGO_BEGIN_DATES;

    /**
     * �a��̊e�������{�s���ꂽ����N�̃��X�g�B���X�g�̐擪����A
     * �Â����ɕ��ׂ���B
     */
    private static final int[] GENGO_BEGIN_YEARS;

    /**
     * �N���X���[�h���ɁAApplicationResources �t�@�C���Ŏw�肳�ꂽ
     * �Ƃ���ɘa��f�[�^������������B
     */
    static {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        // �v���p�e�B����u�����̊J�n���v�Ɓu�����v�̃}�b�v���쐬����
        Enumeration<String> enumaration = 
            PropertyUtil.getPropertyNames(GENGO_KEY);
        Set<String> ids = new HashSet<String>();

        GENGO_LOOP:
        while (enumaration.hasMoreElements()) {
            String key = enumaration.nextElement();
            String id = key.substring(GENGO_KEY.length(),
                                        key.lastIndexOf("."));
            if (!ids.contains(id)) {
                String name
                    = PropertyUtil.getProperty(GENGO_KEY + id + ".name", "");
                String roman
                    = PropertyUtil.getProperty(GENGO_KEY + id + ".roman", "");

                String start
                    = PropertyUtil.getProperty(GENGO_KEY + id + ".startDate");
                if (start == null) {
                    log.error(GENGO_KEY + id + ".startDate not found");
                    continue GENGO_LOOP;
                }

                try {
                    Date date = sdf.parse(start);

                    GENGO_NAME.put(date, name);
                    GENGO_ROMAN.put(date, roman);
                    log.info("registerd: "
                                + date + ", " + name + ", " + roman);
                } catch (ParseException e) {
                    log.error(e.getMessage());
                }

                ids.add(id);
            }
        }

        // �����̊J�n���̔z����쐬���A�\�[�g���Ă���
        Set<Date> keySet = GENGO_NAME.keySet();
        int size = keySet.size();
        GENGO_BEGIN_DATES = keySet.toArray(new Date[size]);
        Arrays.sort(GENGO_BEGIN_DATES);

        // �u�����̊J�n���̔z��v�ƑΉ�����悤�Ɍ����̊J�n�N�̔z����쐬����
        GENGO_BEGIN_YEARS = new int[size];
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < GENGO_BEGIN_DATES.length; i++) {
            calendar.setTime(GENGO_BEGIN_DATES[i]);
            GENGO_BEGIN_YEARS[i] = calendar.get(Calendar.YEAR);
        }
    }

    /**
     * �w�肳�ꂽ���t�̘a������擾����B
     *
     * <p>
     * �a����́AApplicationResources �t�@�C���Ŏw�肷��B
     * </p>
     *
     * @param date ���t
     * @return �a���
     */
    public static String getWarekiGengoName(Date date) {
        for (int i = GENGO_BEGIN_DATES.length - 1; i >= 0; i--) {
            if (!date.before(GENGO_BEGIN_DATES[i])) {
                return GENGO_NAME.get(GENGO_BEGIN_DATES[i]);
            }
        }
        throw new IllegalArgumentException("Wareki Gengo Name not found for "
                                            + date);
    }

    /**
     * �w�肳�ꂽ���t�̘a����̃��[�}���\�L�i�Z�k�`�j���擾����B
     *
     * <p>�a����̃��[�}���\�L�́AApplicationResources�t�@�C���Ŏw�肷��B</p>
     *
     * @param date ���t
     * @return �a����̃��[�}���\�L
     */
    public static String getWarekiGengoRoman(Date date) {
        for (int i = GENGO_BEGIN_DATES.length - 1; i >= 0; i--) {
            if (!date.before(GENGO_BEGIN_DATES[i])) {
                return GENGO_ROMAN.get(GENGO_BEGIN_DATES[i]);
            }
        }
        throw new IllegalArgumentException("Wareki Gengo Roman not found for "
                                            + date);
    }

    /**
     * �w�肳�ꂽ���t�̘a��N���擾����B
     *
     * @param date ���t
     * @return �a��N
     */
    public static int getWarekiYear(Date date) {
        for (int i = GENGO_BEGIN_DATES.length - 1; i >= 0; i--) {
            if (!date.before(GENGO_BEGIN_DATES[i])) {
                Calendar calendar = Calendar.getInstance();

                calendar.setTime(date);
                int year = calendar.get(Calendar.YEAR);

                return year - GENGO_BEGIN_YEARS[i] + 1;
            }
        }
        throw new IllegalArgumentException("Wareki Gengo not found for "
                                            + date);
    }

}
