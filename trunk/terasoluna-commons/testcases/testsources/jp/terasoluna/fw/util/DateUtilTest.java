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

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.PropertyTestCase;
import jp.terasoluna.utlib.UTUtil;

/**
 * DateUtil �u���b�N�{�b�N�X�e�X�g�B<br>
 * �O�����<br>
 * �Etest.properties���N���X�p�X�ɐݒ肳��Ă���K�v������<br>
 * �E�v���p�e�B�t�@�C���Ɉȉ��̂悤�Ȑݒ�����Ă���<br>
 * �@wareki.gengo.0.name = ����<br>
 * �@wareki.gengo.0.roman = H<br>
 * �@wareki.gengo.0.startDate = 1989/01/08<br>
 * �@wareki.gengo.1.name = ���a<br>
 * �@wareki.gengo.1.roman = S<br>
 * �@wareki.gengo.1.startDate = 1926/12/25<br>
 * �@wareki.gengo.2.name = �吳<br>
 * �@wareki.gengo.2.roman = T<br>
 * �@wareki.gengo.2.startDate = 1912/07/30<br>
 * �@wareki.gengo.3.name = ����<br>
 * �@wareki.gengo.3.roman = M<br>
 * �@wareki.gengo.3.startDate = 1868/09/04<br>
 * �@wareki.gengo.4.name = ����<br>
 * �@wareki.gengo.4.roman = H<br>
 * �@wareki.gengo.5.name = ����<br>
 * �@wareki.gengo.5.roman = H<br>
 * �@wareki.gengo.5.startDate = asdf<br>
 * 
 */
@SuppressWarnings("unused")
public class DateUtilTest extends PropertyTestCase {

    /**
     * ������ݒ肷�邽�߂̃t�B�[���h
     */
    private SimpleDateFormat df = null;

    /**
     * ������ݒ肷�邽�߂̃t�B�[���h
     */
    private Date date = null;

    /**
     * Constructor for DateUtilTest.
     * @param arg0
     */
    public DateUtilTest(String arg0) {
        super(arg0);
    }

    @Override
    protected void setUpData() throws Exception {
        addProperty("wareki.gengo.0.name", "����");
        addProperty("wareki.gengo.0.roman", "H");
        addProperty("wareki.gengo.0.startDate", "1989/01/08");
        addProperty("wareki.gengo.1.name", "���a");
        addProperty("wareki.gengo.1.roman", "S");
        addProperty("wareki.gengo.1.startDate", "1926/12/25");
        addProperty("wareki.gengo.2.name", "�吳");
        addProperty("wareki.gengo.2.roman", "T");
        addProperty("wareki.gengo.2.startDate", "1912/07/30");
        addProperty("wareki.gengo.3.name", "����");
        addProperty("wareki.gengo.3.roman", "M");
        addProperty("wareki.gengo.3.startDate", "1868/09/04");
        addProperty("wareki.gengo.4.name", "����");
        addProperty("wareki.gengo.4.roman", "H");
        addProperty("wareki.gengo.5.name", "����");
        addProperty("wareki.gengo.5.roman", "H");
        addProperty("wareki.gengo.5.startDate", "asdf");
    }

    @Override
    protected void cleanUpData() throws Exception {
        clearProperty();
    }

    /**
     * testStatic01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) �v���p�e�B:wareki.gengo.0.name = ����<br>
     *                 �@�@wareki.gengo.0.roman = H<br>
     *                 �@�@wareki.gengo.0.startDate = 1989/01/08<br>
     *                 �@�@wareki.gengo.1.name = ���a<br>
     *                 �@�@wareki.gengo.1.roman = S<br>
     *                 �@�@wareki.gengo.1.startDate = 1926/12/25<br>
     *                 �@�@wareki.gengo.2.name = �吳<br>
     *                 �@�@wareki.gengo.2.roman = T<br>
     *                 �@�@wareki.gengo.2.startDate = 1912/07/30<br>
     *                 �@�@wareki.gengo.3.name = ����<br>
     *                 �@�@wareki.gengo.3.roman = M<br>
     *                 �@�@wareki.gengo.3.startDate = 1868/09/04<br>
     *                 �@�@wareki.gengo.4.name = ����<br>
     *                 �@�@wareki.gengo.4.roman = H<br>
     *                 �@�@wareki.gengo.5.name = ����<br>
     *                 �@�@wareki.gengo.5.roman = H<br>
     *                 �@�@wareki.gengo.5.startDate = asdf<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) �v���C�x�[�g�t�B�[���h:�v���C�x�[�g�t�B�[���h�ł���uGENGO_NAME�v�uGENGO_ROMAN�v�uGENGO_BEGIN_DATES�v�uGENGO_BEGIN_YEARS�v�̃T�C�Y���S�ł��邱�ƁB<br>
     *         (��ԕω�) ���O:<error���x��><br>
     *                    ���b�Z�[�W�Fwareki.gengo.4.startDate not found<br>
     *                    <error���x��><br>
     *                    ���b�Z�[�W�FUnparseable date: "asdf"<br>
     *         
     * <br>
     * ���ׂẴp�^�[����ԗ�����e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testStatic01() throws Exception {

        // ���ʊm�F
        //�v���C�x�[�g�t�B�[���h�̌�����4���ł��邱�Ƃ��m�F����B
        Map GENGO_NAME
            = (Map) UTUtil.getPrivateField(DateUtil.class, "GENGO_NAME");
        Map GENGO_ROMAN
            = (Map) UTUtil.getPrivateField(DateUtil.class, "GENGO_ROMAN");
        Date[] GENGO_BEGIN_DATES
            = (Date[]) UTUtil.getPrivateField(DateUtil.class, "GENGO_BEGIN_DATES");
        int[] GENGO_BEGIN_YEARS
            = (int[]) UTUtil.getPrivateField(DateUtil.class, "GENGO_BEGIN_YEARS");
        assertEquals(4, GENGO_NAME.size());
        assertEquals(4, GENGO_ROMAN.size());
        assertEquals(4, GENGO_BEGIN_DATES.length);
        assertEquals(4, GENGO_BEGIN_YEARS.length);
        assertTrue(LogUTUtil.checkError("wareki.gengo.4.startDate not found"));
        assertTrue(LogUTUtil.checkError("Unparseable date: \"asdf\""));
    }

    /**
     * testGetSystemTime01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l�F�Ȃ�<br>
     * ���Ғl�F�e�X�g���s���̃V�X�e������<br>
     * 
     * �E�V�X�e���������擾�ł��邱�Ƃ��m�F����B<br>
     * �@���ʊm�F�ł̓e�X�g�Ώۂ̓��e�Ɠ������Ƃ����Ă���B<br>
     * @throws Exception ��O
     */
    public void testGetSystemTime01() throws Exception {
        // ���͒l�̐ݒ�
        // �V�X�e�������̎擾�̂��߁A���͂Ȃ�

        // �e�X�g�Ώۂ̎��s
        Date result = DateUtil.getSystemTime();

        // ���ʊm�F
        Date hope = Calendar.getInstance().getTime();
        assertEquals(hope, result);
    }

    // ************************************************************************
    //  �a��ϊ��p���\�b�h�Ɋւ���m�F
    // ************************************************************************

    // ************************************************************************
    //  �����̃t�H�[�}�b�g�uG�v�Ɋւ���m�F
    // ************************************************************************

    /**
     * testDateToWarekiString01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l�Fformat="G"<br>
     *         date="2001.01.01 00:00:00"<br>
     * 
     * ���Ғl�Fdate="H"<br>
     * 
     * �E�����̃t�H�[�}�b�g���uG�v��1�����ɂ��A�����̏o�͌`�����m�F����B<br>
     * @throws Exception ��O
     */
    public void testDateToWarekiString01() throws Exception {
        // ���͒l�̐ݒ�
        String format = "G";
        // �����̐ݒ�
        df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        date = new Date(df.parse("2001.01.01 00:00:00").getTime());

        // �e�X�g�Ώۂ̎��s
        String str = DateUtil.dateToWarekiString(format, date);

        // ���ʊm�F
        assertEquals("H", str);
    }

    /**
     * testDateToWarekiString02�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l�Fformat="G"<br>
     * �@�@�@�@currentTime="1980�N1��1�� 0��0��0�b"<br>
     * ���Ғl�F"S"<br>
     * 
     * �E�����̃t�H�[�}�b�g���uG�v��1�����ɂ��A�����̏o�͌`�����m�F����B<br>
     * @throws Exception ��O
     */
    public void testDateToWarekiString02() throws Exception {
        // ���͒l�̐ݒ�
        String format = "G";
        // �����̐ݒ�
        df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        date = new Date(df.parse("1980.01.01 00:00:00").getTime());

        // �e�X�g�Ώۂ̎��s
        String str = DateUtil.dateToWarekiString(format, date);

        // ���ʊm�F
        assertEquals("S", str);
    }

    /**
     * testDateToWarekiString03�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l�Fformat="GGG"<br>
     * �@�@�@�@currentTime="2001�N1��1�� 0��0��0�b"<br>
     * ���Ғl�F"H"<br>
     * 
     * �E�����̃t�H�[�}�b�g���uGGG�v��3�����ɂ��A�����̏o�͌`�����m�F����B<br>
     * @throws Exception ��O
     */
    public void testDateToWarekiString03() throws Exception {
        // ���͒l�̐ݒ�
        String format = "GGG";
        // �����̐ݒ�
        df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        date = new Date(df.parse("2001.01.01 00:00:00").getTime());

        // �e�X�g�Ώۂ̎��s
        String str = DateUtil.dateToWarekiString(format, date);

        // ���ʊm�F
        assertEquals("H", str);
    }

    /**
     * testDateToWarekiString04�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l�Fformat="GGGG"<br>
     * �@�@�@�@currentTime="2001�N1��1�� 0��0��0�b"<br>
     * ���Ғl�F"����"<br>
     * 
     * �E�����̃t�H�[�}�b�g���uGGGG�v��4�����ɂ��A�����̏o�͌`�����m�F����B<br>
     * @throws Exception ��O
     */
    public void testDateToWarekiString04() throws Exception {
        // ���͒l�̐ݒ�
        String format = "GGGG";
        // �����̐ݒ�
        df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        date = new Date(df.parse("2001.01.01 00:00:00").getTime());

        // �e�X�g�Ώۂ̎��s
        String str = DateUtil.dateToWarekiString(format, date);

        // ���ʊm�F
        assertEquals("����", str);
    }

    /**
     * testDateToWarekiString05�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l�Fformat="GGGGGGGGGG"<br>
     * �@�@�@�@currentTime="2001�N1��1�� 0��0��0�b"<br>
     * ���Ғl�F"����"<br>
     * 
     * �E�����̃t�H�[�}�b�g���uGGGGGGGGGG�v��10�����ɂ��A
     * �����̏o�͌`�����m�F����B<br>
     * @throws Exception ��O
     */
    public void testDateToWarekiString05() throws Exception {
        // ���͒l�̐ݒ�
        String format = "GGGGGGGGGG";
        // �����̐ݒ�
        df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        date = new Date(df.parse("2001.01.01 00:00:00").getTime());

        // �e�X�g�Ώۂ̎��s
        String str = DateUtil.dateToWarekiString(format, date);

        // ���ʊm�F
        assertEquals("����", str);
    }

    // ************************************************************************
    //  �N�̃t�H�[�}�b�g�uy�v�Ɋւ���m�F
    // ************************************************************************

    /**
     * testDateToWarekiString06�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l�Fformat="y"<br>
     * �@�@�@�@currentTime="2001�N1��1�� 0��0��0�b"<br>
     * ���Ғl�F"13"<br>
     * 
     * �E�����̃t�H�[�}�b�g���uy�v��1�����ɂ��A�a��N���o�͂���邱�Ƃ��m�F����B<br>
     * @throws Exception ��O
     */
    public void testDateToWarekiString06() throws Exception {
        // ���͒l�̐ݒ�
        String format = "y";
        // �����̐ݒ�
        df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        date = new Date(df.parse("2001.01.01 00:00:00").getTime());

        // �e�X�g�Ώۂ̎��s
        String str = DateUtil.dateToWarekiString(format, date);

        // ���ʊm�F
        assertEquals("13", str);
    }

    /**
     * testDateToWarekiString07�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l�Fformat="y"<br>
     * �@�@�@�@currentTime="1869�N1��1�� 0��0��0�b"<br>
     * ���Ғl�F"2"<br>
     * 
     * �E�����̃t�H�[�}�b�g���uy�v��1�����ɂ��A�a��N���o�͂���邱�Ƃ��m�F����B<br>
     * @throws Exception ��O
     */
    public void testDateToWarekiString07() throws Exception {
        // ���͒l�̐ݒ�
        String format = "y";
        // �����̐ݒ�
        df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        date = new Date(df.parse("1869.01.01 00:00:00").getTime());

        // �e�X�g�Ώۂ̎��s
        String str = DateUtil.dateToWarekiString(format, date);

        // ���ʊm�F
        assertEquals("2", str);
    }

    /**
     * testDateToWarekiString08�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l�Fformat="yy"<br>
     * �@�@�@�@currentTime="2001�N1��1�� 0��0��0�b"<br>
     * ���Ғl�F"13"<br>
     * 
     * �E�����̃t�H�[�}�b�g���uyy�v��2�����ɂ��A�a��N���o�͂���邱�Ƃ��m�F����B<br>
     * @throws Exception ��O
     */
    public void testDateToWarekiString08() throws Exception {
        // ���͒l�̐ݒ�
        String format = "yy";
        // �����̐ݒ�
        df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        date = new Date(df.parse("2001.01.01 00:00:00").getTime());

        // �e�X�g�Ώۂ̎��s
        String str = DateUtil.dateToWarekiString(format, date);

        // ���ʊm�F
        assertEquals("13", str);
    }

    /**
     * testDateToWarekiString09�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l�Fformat="yyyyyyyyyy"<br>
     * �@�@�@�@currentTime="2001�N1��1�� 0��0��0�b"<br>
     * ���Ғl�F"13"<br>
     * 
     * �E�����̃t�H�[�}�b�g���uyyyyyyyyyy�v��10�����ɂ��A�a��N���o�͂���邱�Ƃ��m�F����B<br>
     * @throws Exception ��O
     */
    public void testDateToWarekiString09() throws Exception {
        // ���͒l�̐ݒ�
        String format = "yyyyyyyyyy";
        // �����̐ݒ�
        df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        date = new Date(df.parse("2001.01.01 00:00:00").getTime());

        // �e�X�g�Ώۂ̎��s
        String str = DateUtil.dateToWarekiString(format, date);

        // ���ʊm�F
        assertEquals("13", str);
    }

    // ************************************************************************
    //  �j���̃t�H�[�}�b�g�uE�v�Ɋւ���m�F
    // ************************************************************************

    /**
     * testDateToWarekiString10�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l�Fformat="E"<br>
     * �@�@�@�@currentTime="2001�N1��1�� 0��0��0�b"<br>
     * ���Ғl�F"��"<br>
     * 
     * �E�j���̃t�H�[�}�b�g���uE�v��1�����ɂ����ꍇ���m�F����B<br>
     * @throws Exception ��O
     */
    public void testDateToWarekiString10() throws Exception {
        // ���͒l�̐ݒ�
        String format = "E";
        // �����̐ݒ�
        df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        date = new Date(df.parse("2001.01.01 00:00:00").getTime());

        // �e�X�g�Ώۂ̎��s
        String str = DateUtil.dateToWarekiString(format, date);

        // ���ʊm�F
        assertEquals("��", str);
    }

    /**
     * testDateToWarekiString11�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l�Fformat="E"<br>
     * �@�@�@�@currentTime="1868�N9��3�� 0��0��0�b"<br>
     * ���Ғl�F"��"<br>
     * 
     * �E�j���̃t�H�[�}�b�g���uE�v��1�����ɂ��A���͂�����t��<br>
     * �����ŏ��̓��̑O���̏ꍇ���m�F����B<br>
     * @throws Exception ��O
     */
    public void testDateToWarekiString11() throws Exception {
        // ���͒l�̐ݒ�
        String format = "E";
        // �����̐ݒ�
        df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        date = new Date(df.parse("1868.09.03 00:00:00").getTime());

        // �e�X�g�Ώۂ̎��s
        String str = DateUtil.dateToWarekiString(format, date);

        // ���ʊm�F
        assertEquals("��", str);
    }

    /**
     * testDateToWarekiString12�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l�Fformat="EEE"<br>
     * �@�@�@�@currentTime="2001�N1��1�� 0��0��0�b"<br>
     * ���Ғl�F"��"<br>
     * 
     * �E�j���̃t�H�[�}�b�g���uEEE�v��3�����ɂ����ꍇ���m�F����B<br>
     * @throws Exception ��O
     */
    public void testDateToWarekiString12() throws Exception {
        // ���͒l�̐ݒ�
        String format = "EEE";
        // �����̐ݒ�
        df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        date = new Date(df.parse("2001.01.01 00:00:00").getTime());

        // �e�X�g�Ώۂ̎��s
        String str = DateUtil.dateToWarekiString(format, date);

        // ���ʊm�F
        assertEquals("��", str);
    }

    /**
     * testDateToWarekiString13�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l�Fformat="EEEE"<br>
     * �@�@�@�@currentTime="2001�N1��1�� 0��0��0�b"<br>
     * ���Ғl�F"���j��"<br>
     * 
     * �E�j���̃t�H�[�}�b�g���uEEEE�v��4�����ɂ����ꍇ���m�F����B<br>
     * @throws Exception ��O
     */
    public void testDateToWarekiString13() throws Exception {
        // ���͒l�̐ݒ�
        String format = "EEEE";
        // �����̐ݒ�
        df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        date = new Date(df.parse("2001.01.01 00:00:00").getTime());

        // �e�X�g�Ώۂ̎��s
        String str = DateUtil.dateToWarekiString(format, date);

        // ���ʊm�F
        assertEquals("���j��", str);
    }

    /**
     * testDateToWarekiString14�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l�Fformat="EEEEEEEEEE"<br>
     * �@�@�@�@currentTime="2001�N1��2�� 0��0��0�b"<br>
     * ���Ғl�F"�Ηj��"<br>
     * 
     * �E�j���̃t�H�[�}�b�g���uEEEEEEEEEE�v��10�����ɂ����ꍇ���m�F����B<br>
     * @throws Exception ��O
     */
    public void testDateToWarekiString14() throws Exception {
        // ���͒l�̐ݒ�
        String format = "EEEEEEEEEE";
        // �����̐ݒ�
        df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        date = new Date(df.parse("2001.01.02 00:00:00").getTime());

        // �e�X�g�Ώۂ̎��s
        String str = DateUtil.dateToWarekiString(format, date);

        // ���ʊm�F
        assertEquals("�Ηj��", str);
    }

    // ************************************************************************
    //  �����ƔN�̏o�͏��Ɋւ���m�F
    // ************************************************************************

    /**
     * testDateToWarekiString15�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l�Fformat="Gy"<br>
     * �@�@�@�@currentTime="2001�N1��1�� 0��0��0�b"<br>
     * ���Ғl�F"H13"<br>
     * 
     * �E�t�H�[�}�b�g���uGy�v�Ƃ��A����(���[�}��)�{�N�̏��ɏo�͂���邱�Ƃ��m�F����B<br>
     * @throws Exception ��O
     */
    public void testDateToWarekiString15() throws Exception {
        // ���͒l�̐ݒ�
        String format = "Gy";
        // �����̐ݒ�
        df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        date = new Date(df.parse("2001.01.01 00:00:00").getTime());

        // �e�X�g�Ώۂ̎��s
        String str = DateUtil.dateToWarekiString(format, date);

        // ���ʊm�F
        assertEquals("H13", str);
    }

    /**
     * testDateToWarekiString16�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l�Fformat="yG"<br>
     * �@�@�@�@currentTime="2001�N1��1�� 0��0��0�b"<br>
     * ���Ғl�F"13H"<br>
     * 
     * �E�t�H�[�}�b�g���uyG�v�Ƃ��A�N�{����(���[�}��)�̏��ɏo�͂���邱�Ƃ��m�F����B<br>
     * @throws Exception ��O
     */
    public void testDateToWarekiString16() throws Exception {
        // ���͒l�̐ݒ�
        String format = "yG";
        // �����̐ݒ�
        df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        date = new Date(df.parse("2001.01.01 00:00:00").getTime());

        // �e�X�g�Ώۂ̎��s
        String str = DateUtil.dateToWarekiString(format, date);

        // ���ʊm�F
        assertEquals("13H", str);
    }

    /**
     * testDateToWarekiString17�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l�Fformat="GGGGy"<br>
     * �@�@�@�@currentTime="2001�N1��1�� 0��0��0�b"<br>
     * ���Ғl�F"����13"<br>
     * 
     * �E�t�H�[�}�b�g���uGGGGy�v�Ƃ��A����(����)�{�N�̏��ɏo�͂���邱�Ƃ��m�F����B<br>
     * @throws Exception ��O
     */
    public void testDateToWarekiString17() throws Exception {
        // ���͒l�̐ݒ�
        String format = "GGGGy";
        // �����̐ݒ�
        df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        date = new Date(df.parse("2001.01.01 00:00:00").getTime());

        // �e�X�g�Ώۂ̎��s
        String str = DateUtil.dateToWarekiString(format, date);

        // ���ʊm�F
        assertEquals("����13", str);
    }

    /**
     * testDateToWarekiString18�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l�Fformat="yGGGG"<br>
     * �@�@�@�@currentTime="2001�N1��1�� 0��0��0�b"<br>
     * ���Ғl�F"13����"<br>
     * 
     * �E�t�H�[�}�b�g���uyGGGG�v�Ƃ��A�N�{����(����)�̏��ɏo�͂���邱�Ƃ��m�F����B<br>
     * @throws Exception ��O
     */
    public void testDateToWarekiString18() throws Exception {
        // ���͒l�̐ݒ�
        String format = "yGGGG";
        // �����̐ݒ�
        df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        date = new Date(df.parse("2001.01.01 00:00:00").getTime());

        // �e�X�g�Ώۂ̎��s
        String str = DateUtil.dateToWarekiString(format, date);

        // ���ʊm�F
        assertEquals("13����", str);
    }

    /**
     * testDateToWarekiString19�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l�Fformat="G GGGG"<br>
     * �@�@�@�@currentTime="2001�N1��1�� 0��0��0�b"<br>
     * ���Ғl�F"H ����"<br>
     * 
     * �E�t�H�[�}�b�g���uG GGGG�v�Ƃ��A����(���[�}��)�{����(����)�̏���<br>
     * �o�͂���邱�Ƃ��m�F����B<br>
     * @throws Exception ��O
     */
    public void testDateToWarekiString19() throws Exception {
        // ���͒l�̐ݒ�
        String format = "G GGGG";
        // �����̐ݒ�
        df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        date = new Date(df.parse("2001.01.01 00:00:00").getTime());

        // �e�X�g�Ώۂ̎��s
        String str = DateUtil.dateToWarekiString(format, date);

        // ���ʊm�F
        assertEquals("H ����", str);
    }

    /**
     * testDateToWarekiString20�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l�Fformat="GGGG G"<br>
     * �@�@�@�@currentTime="2001�N1��1�� 0��0��0�b"<br>
     * ���Ғl�F"���� H"<br>
     * 
     * �E�t�H�[�}�b�g���uGGGG G�v�Ƃ��A����(����)�{����(���[�}��)�̏���<br>
     * �o�͂���邱�Ƃ��m�F����B<br>
     * @throws Exception ��O
     */
    public void testDateToWarekiString20() throws Exception {
        // ���͒l�̐ݒ�
        String format = "GGGG G";
        // �����̐ݒ�
        df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        date = new Date(df.parse("2001.01.01 00:00:00").getTime());

        // �e�X�g�Ώۂ̎��s
        String str = DateUtil.dateToWarekiString(format, date);

        // ���ʊm�F
        assertEquals("���� H", str);
    }

    /**
     * testDateToWarekiString21�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l�Fformat="GGGGG E"<br>
     * �@�@�@�@currentTime="2001�N1��1�� 0��0��0�b"<br>
     * ���Ғl�F"���� ��"<br>
     * 
     * �E�t�H�[�}�b�g���uGGGGG E�v�Ƃ��A����(����)�{�j���̏���<br>
     * �o�͂���邱�Ƃ��m�F����B<br>
     * @throws Exception ��O
     */
    public void testDateToWarekiString21() throws Exception {
        // ���͒l�̐ݒ�
        String format = "GGGGG E";
        // �����̐ݒ�
        df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        date = new Date(df.parse("2001.01.01 00:00:00").getTime());

        // �e�X�g�Ώۂ̎��s
        String str = DateUtil.dateToWarekiString(format, date);

        // ���ʊm�F
        assertEquals("���� ��", str);
    }

    /**
     * testDateToWarekiString22�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l�Fformat="MM.dd HH:mm:ss z"<br>
     * �@�@�@�@currentTime="2001�N2��1�� 3��4��5�b"<br>
     * ���Ғl�F"02.01 03:04:05 JST"<br>
     * 
     * �Ey,G,E���܂܂Ȃ��t�H�[�}�b�g�̏ꍇ���m�F����B<br>
     * @throws Exception ��O
     */
    public void testDateToWarekiString22() throws Exception {
        // ���͒l�̐ݒ�
        String format = "MM.dd HH:mm:ss z";
        // �����̐ݒ�
        df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        date = new Date(df.parse("2001.02.01 03:04:05").getTime());

        // �e�X�g�Ώۂ̎��s
        String str = DateUtil.dateToWarekiString(format, date);

        // ���ʊm�F
        assertEquals("02.01 03:04:05 " + this.getTimeZoneName(date), str);
    }
    
    /**
     * �^�C���]�[���̗������擾�B SimpleDateFormat�N���X�Ɠ��l�̕��@�Ń^�C���]�[���̗������擾���Ă���B
     * 
     * @param dt �����f�[�^
     * @return �^�C���]�[���̗���
     * @throws Exception ��O
     */
    private String getTimeZoneName(Date dt) throws Exception {
        Calendar calender = Calendar.getInstance();
        calender.setTime(dt);

        // DateFormatSymbols �́A���A�j���A�^�C���]�[���f�[�^�ȂǁA
        // �n��Ή����\�ȓ��t/�����t�H�[�}�b�g�f�[�^���J�v�Z�������邽�߂� public �N���X�B
        DateFormatSymbols formatData = new DateFormatSymbols(Locale
                .getDefault());
        // �^�C���]�[��������̎擾
        String zoneStrings[][] = formatData.getZoneStrings();

        // zoneIndex�̎擾
        int zoneIndex = -1;
        String zoneID = calender.getTimeZone().getID();
        for (int index = 0; index < zoneStrings.length; index++) {
            if (zoneID.equalsIgnoreCase(zoneStrings[index][0])) {
                zoneIndex = index;
                break;
            }
        }

        // ���ʂ̗����͑��݂��Ȃ���
        if (zoneIndex == -1) {
            return zoneID;
        }

        // �����̃C���f�b�N�X�̎擾
        int index = -1;
        if(calender.get(Calendar.DST_OFFSET) == 0 ){
            index = 2;
        } else {
            index = 4;
        }

        return zoneStrings[zoneIndex][index];
    }

    // ************************************************************************
    //  ���͂��s���ȏꍇ�̊m�F
    // ************************************************************************

    /**
     * testDateToWarekiString23�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA,C<br>
     * 
     * ���͒l�Fformat=""<br>
     * �@�@�@�@currentTime="2001�N1��1�� 0��0��0�b"<br>
     * ���Ғl�F""<br>
     * 
     * �E�t�H�[�}�b�g���󕶎��ɂ��A�󕶎����o�͂���邱�Ƃ��m�F����B<br>
     * @throws Exception ��O
     */
    public void testDateToWarekiString23() throws Exception {
        // ���͒l�̐ݒ�
        String format = "";
        // �����̐ݒ�
        df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        date = new Date(df.parse("2001.01.01 00:00:00").getTime());

        // �e�X�g�Ώۂ̎��s
        String str = DateUtil.dateToWarekiString(format, date);

        // ���ʊm�F
        assertEquals("", str);
    }

    /**
     * testDateToWarekiString24�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l�Fformat="#!--0"<br>
     * �@�@�@�@currentTime="2001�N1��1�� 0��0��0�b"<br>
     * ���Ғl�F"#!--0"<br>
     * 
     * �E�t�H�[�}�b�g���p�^�[�������ȊO�ɂ��A<br>
     * ���̂܂܏o�͂���邱�Ƃ��m�F����B<br>
     * @throws Exception ��O
     */
    public void testDateToWarekiString24() throws Exception {
        // ���͒l�̐ݒ�
        String format = "#!--0";
        // �����̐ݒ�
        df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        date = new Date(df.parse("2001.01.01 00:00:00").getTime());

        // �e�X�g�Ώۂ̎��s
        String str = DateUtil.dateToWarekiString(format, date);

        // ���ʊm�F
        assertEquals("#!--0", str);
    }

    /**
     * testDateToWarekiString25�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l�Fformat="'GGGG' G 'dd' dd"<br>
     * �@�@�@�@currentTime="2001�N1��1�� 0��0��0�b"<br>
     * ���Ғl�F"GGGG H dd 01"<br>
     * 
     * �E�t�H�[�}�b�g�L�����N�^���V���O���N�H�[�e�[�V�����ň͂񂾏ꍇ�A<br>
     * ������Ƃ��ăG�X�P�[�v����邱�Ƃ��m�F����B<br>
     * @throws Exception ��O
     */
    public void testDateToWarekiString25() throws Exception {
        // ���͒l�̐ݒ�
        String format = "'GGGG' G 'dd' dd";
        // �����̐ݒ�
        df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        date = new Date(df.parse("2001.01.01 00:00:00").getTime());

        // �e�X�g�Ώۂ̎��s
        String str = DateUtil.dateToWarekiString(format, date);

        // ���ʊm�F
        assertEquals("GGGG H dd 01", str);
    }

    /**
     * testDateToWarekiString26�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l�Fformat="''y'' y ''E'' E"<br>
     * �@�@�@�@currentTime="2001�N1��1�� 0��0��0�b"<br>
     * ���Ғl�F13 13 E ��<br>
     * 
     * �E�t�H�[�}�b�g�L�����N�^���V���O���N�H�[�e�[�V����2�ň͂񂾏ꍇ�A<br>
     * "E"�͕�����Ƃ��ăG�X�P�[�v����A"y"�͕ϊ�����邱�Ƃ��m�F����B<br>
     * @throws Exception ��O
     */
    public void testDateToWarekiString26() throws Exception {
        // ���͒l�̐ݒ�
        String format = "''y'' y ''E'' E";
        // �����̐ݒ�
        df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        date = new Date(df.parse("2001.01.01 00:00:00").getTime());

        // �e�X�g�Ώۂ̎��s
        String str = DateUtil.dateToWarekiString(format, date);

        // ���ʊm�F
        assertEquals("13 13 E ��", str);
    }

    /**
     * testDateToWarekiString27�B<br>
     * 
     * (�ُ�n)<br>
     * �ϓ_�FG<br>
     * 
     * ���͒l�Fformat="A"<br>
     * �@�@�@�@currentTime="2001�N1��1�� 0��0��0�b"<br>
     * ���Ғl�FIllegalArgumentException���X���[�����<br>
     * 
     * �E�t�H�[�}�b�g�����ɂȂ��uA�v���w�肵���ꍇ�A<br>
     * IllegalArgumentException���X���[����邱�Ƃ��m�F����B<br>
     * @throws Exception ��O
     */
    public void testDateToWarekiString27() throws Exception {
        // ���͒l�̐ݒ�
        String format = "A";
        // �����̐ݒ�
        df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        date = new Date(df.parse("2001.01.01 00:00:00").getTime());

        // �e�X�g�Ώۂ̎��s
        try {
            String str = DateUtil.dateToWarekiString(format, date);
            fail();
        } catch (IllegalArgumentException e) {
            return;
        }
    }

    /**
     * testDateToWarekiString28�B<br>
     * 
     * (�ُ�n)<br>
     * �ϓ_�FG<br>
     * 
     * ���͒l�Fformat="G"<br>
     * �@�@�@�@currentTime="1868�N9��3�� 0��0��0�b"<br>
     * ���Ғl�FIllegalArgumentException���X���[�����<br>
     * 
     * �E�v���p�e�B�t�@�C���Ŏw�肳�ꂽ�ŌÓ��t�ȑO�̓��t��date�œn�����ꍇ�A<br>
     * �t�H�[�}�b�g��"G"���w�肷���IllegalArgumentException���X���[����邱�Ƃ��m�F����B<br>
     * @throws Exception ��O
     */
    public void testDateToWarekiString28() throws Exception {
        // ���͒l�̐ݒ�
        String format = "G";
        // �����̐ݒ�
        df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        date = new Date(df.parse("1868.09.03 00:00:00").getTime());

        // �e�X�g�Ώۂ̎��s
        try {
            String str = DateUtil.dateToWarekiString(format, date);
            fail();
        } catch (IllegalArgumentException e) {
            return;
        }
    }

    /**
     * testDateToWarekiString29�B<br>
     * 
     * (�ُ�n)<br>
     * �ϓ_�FG<br>
     * 
     * ���͒l�Fformat="y"<br>
     * �@�@�@�@currentTime="1868�N9��3�� 0��0��0�b"<br>
     * ���Ғl�FIllegalArgumentException���X���[�����<br>
     * 
     * �E�v���p�e�B�t�@�C���Ŏw�肳�ꂽ�ŌÓ��t�ȑO�̓��t��date�œn�����ꍇ�A<br>
     * �t�H�[�}�b�g��"y"���w�肷���IllegalArgumentException���X���[����邱�Ƃ��m�F����B<br>
     * @throws Exception ��O
     */
    public void testDateToWarekiString29() throws Exception {
        // ���͒l�̐ݒ�
        String format = "y";
        // �����̐ݒ�
        df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        date = new Date(df.parse("1868.09.03 00:00:00").getTime());

        // �e�X�g�Ώۂ̎��s
        try {
            String str = DateUtil.dateToWarekiString(format, date);
            fail();
        } catch (IllegalArgumentException e) {
            return;
        }
    }

    /**
     * testDateToWarekiString30�B<br>
     * 
     * (�ُ�n)<br>
     * �ϓ_�FC,G<br>
     * 
     * ���͒l�Fformat=null<br>
     * �@�@�@�@currentTime=*<br>
     * ���Ғl�FNullPointerException���X���[�����<br>
     * 
     * �E�t�H�[�}�b�g��null���w�肵���ꍇ�A<br>
     * NullPointerException���X���[����邱�Ƃ��m�F����B<br>
     * @throws Exception ��O
     */
    public void testDateToWarekiString30() throws Exception {
        // ���͒l�̐ݒ�
        String format = null;
        // �����̐ݒ�
        df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        date = new Date(df.parse("2001.01.01 00:00:00").getTime());

        // �e�X�g�Ώۂ̎��s
        try {
            String str = DateUtil.dateToWarekiString(format, date);
            fail();
        } catch (NullPointerException e) {
            return;
        }
    }

    /**
     * testDateToWarekiString31�B<br>
     * 
     * (�ُ�n)<br>
     * �ϓ_�FC,G<br>
     * 
     * ���͒l�Fformat=not null<br>
     * �@�@�@�@currentTime=null<br>
     * ���Ғl�FNullPointerException���X���[�����<br>
     * 
     * �E���t��null���w�肵���ꍇ�A<br>
     * NullPointerException���X���[����邱�Ƃ��m�F����B<br>
     * @throws Exception ��O
     */
    public void testDateToWarekiString31() throws Exception {
        // ���͒l�̐ݒ�
        String format = "G";
        date = null;

        // �e�X�g�Ώۂ̎��s
        try {
            String str = DateUtil.dateToWarekiString(format, date);
            fail();
        } catch (NullPointerException e) {
            return;
        }
    }

    // ************************************************************************
    //  getWarekiGengoName
    // ************************************************************************

    /**
     * testGetWarekiGengoName01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l�Fdate=1989�N1��8��0��0��0�b<br>
     * ���Ғl�F"����"<br>
     * 
     * �E�����ŏ��̓��̌ߑO0�����傤�ǂ��m�F����B<br>
     * @throws Exception ��O
     */
    public void testGetWarekiGengoName01() throws Exception {
        // ���͒l�̐ݒ�
        df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        date = new Date(df.parse("1989.01.08 00:00:00").getTime());

        // �e�X�g�Ώۂ̎��s
        String str = DateUtil.getWarekiGengoName(date);

        // ���ʊm�F
        assertEquals("����", str);
    }

    /**
     * testGetWarekiGengoName02�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l�Fdate=1989�N1��7��23��59��59�b<br>
     * ���Ғl�F"���a"<br>
     * 
     * �E�����ŏ��̓��̌ߑO0����1�b�O���m�F����B<br>
     * @throws Exception ��O
     */
    public void testGetWarekiGengoName02() throws Exception {
        // ���͒l�̐ݒ�
        df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        date = new Date(df.parse("1989.01.07 23:59:59").getTime());

        // �e�X�g�Ώۂ̎��s
        String str = DateUtil.getWarekiGengoName(date);

        // ���ʊm�F
        assertEquals("���a", str);
    }

    /**
     * testGetWarekiGengoName03�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l�Fdate=1989�N1��8��0��0��1�b<br>
     * ���Ғl�F"����"<br>
     * 
     * �E�����ŏ��̓��̌ߑO0����1�b����m�F����B<br>
     * @throws Exception ��O
     */
    public void testGetWarekiGengoName03() throws Exception {
        // ���͒l�̐ݒ�
        df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        date = new Date(df.parse("1989.01.08 00:00:01").getTime());

        // �e�X�g�Ώۂ̎��s
        String str = DateUtil.getWarekiGengoName(date);

        // ���ʊm�F
        assertEquals("����", str);
    }

    /**
     * testGetWarekiGengoName04�B<br>
     * 
     * (�ُ�n)<br>
     * �ϓ_�FG<br>
     * 
     * ���͒l�Fdate=1868�N9��3��<br>
     * ���Ғl�FIllegalArgumentException<br>
     * 
     * �E���͂�����t�������ŏ��̓��̑O���̏ꍇ���m�F����B<br>
     * @throws Exception ��O
     */
    public void testGetWarekiGengoName04() throws Exception {
        // ���͒l�̐ݒ�
        df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        date = new Date(df.parse("1868.09.03 00:00:00").getTime());

        // �e�X�g�Ώۂ̎��s
        try {
            String str = DateUtil.getWarekiGengoName(date);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Wareki Gengo Name not found for "
                    + date, e.getMessage());
            return;
        }
    }

    /**
     * testGetWarekiGengoName05�B<br>
     * 
     * (�ُ�n)<br>
     * �ϓ_�FG<br>
     * 
     * ���͒l�Fdate=null<br>
     * ���Ғl�FNullPointerException���X���[�����<br>
     * 
     * �E���͂�����t��null�̎��ANullPointerException����������B<br>
     * @throws Exception ��O
     */
    public void testGetWarekiGengoName05() throws Exception {
        // ���͒l�̐ݒ�
        date = null;

        // �e�X�g�Ώۂ̎��s
        try {
            String str = DateUtil.getWarekiGengoName(date);
            fail();
        } catch (NullPointerException e) {
            return;
        }
    }

    // ************************************************************************
    //  getWarekiGengoRoman
    // ************************************************************************

    /**
     * testGetWarekiGengoRoman01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l�Fdate=1989�N1��8��0��0��0�b<br>
     * ���Ғl�F"����"<br>
     * 
     * �E�����ŏ��̓��̌ߑO0�����傤�ǂ��m�F����B<br>
     * @throws Exception ��O
     */
    public void testGetWarekiGengoRoman01() throws Exception {
        // ���͒l�̐ݒ�
        df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        date = new Date(df.parse("1989.01.08 00:00:00").getTime());

        // �e�X�g�Ώۂ̎��s
        String str = DateUtil.getWarekiGengoRoman(date);

        // ���ʊm�F
        assertEquals("H", str);
    }

    /**
     * testGetWarekiGengoRoman02�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l�Fdate=1989�N1��7��23��59��59�b<br>
     * ���Ғl�F"���a"<br>
     * 
     * �E�����ŏ��̓��̌ߑO0����1�b�O���m�F����B<br>
     * @throws Exception ��O
     */
    public void testGetWarekiGengoRoman02() throws Exception {
        // ���͒l�̐ݒ�
        df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        date = new Date(df.parse("1989.01.07 23:59:59").getTime());

        // �e�X�g�Ώۂ̎��s
        String str = DateUtil.getWarekiGengoRoman(date);

        // ���ʊm�F
        assertEquals("S", str);
    }

    /**
     * testGetWarekiGengoRoman03�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l�Fdate=1989�N1��8��0��0��1�b<br>
     * ���Ғl�F"����"<br>
     * 
     * �E�����ŏ��̓��̌ߑO0����1�b����m�F����B<br>
     * @throws Exception ��O
     */
    public void testGetWarekiGengoRoman03() throws Exception {
        // ���͒l�̐ݒ�
        df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        date = new Date(df.parse("1989.01.08 00:00:01").getTime());

        // �e�X�g�Ώۂ̎��s
        String str = DateUtil.getWarekiGengoRoman(date);

        // ���ʊm�F
        assertEquals("H", str);
    }

    /**
     * testGetWarekiGengoRoman04�B<br>
     * 
     * (�ُ�n)<br>
     * �ϓ_�FG<br>
     * 
     * ���͒l�Fdate=1868�N9��3��<br>
     * ���Ғl�FIllegalArgumentException���X���[�����<br>
     * 
     * �E���͂�����t�������ŏ��̓��̑O���̏ꍇ���m�F����B<br>
     * @throws Exception ��O
     */
    public void testGetWarekiGengoRoman04() throws Exception {
        // ���͒l�̐ݒ�
        df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        date = new Date(df.parse("1868.09.03 00:00:00").getTime());

        // �e�X�g�Ώۂ̎��s
        try {
            String str = DateUtil.getWarekiGengoRoman(date);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Wareki Gengo Roman not found for "
                    + date, e.getMessage());
            return;
        }
    }

    /**
     * testGetWarekiGengoRoman05�B<br>
     * 
     * (�ُ�n)<br>
     * �ϓ_�FG<br>
     * 
     * ���͒l�Fdate=null<br>
     * ���Ғl�FNullPointerException���X���[�����<br>
     * 
     * �E���͂�����t��Null�̎��ANullPointerException����������<br>
     * @throws Exception ��O
     */
    public void testGetWarekiGengoRoman05() throws Exception {
        // ���͒l�̐ݒ�
        date = null;

        // �e�X�g�Ώۂ̎��s
        try {
            String str = DateUtil.getWarekiGengoRoman(date);
            fail();
        } catch (NullPointerException e) {
            return;
        }
    }

    // ************************************************************************
    //  getWarekiGengoYear
    // ************************************************************************

    /**
     * testGetWarekiYear01�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l�Fdate=1989�N1��8��0��0��0�b<br>
     * ���Ғl�F1<br>
     * 
     * �E�����ŏ��̓��̌ߑO0�����傤�ǂ��m�F����B<br>
     * @throws Exception ��O
     */
    public void testGetWarekiYear01() throws Exception {
        // ���͒l�̐ݒ�
        df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        date = new Date(df.parse("1989.01.08 00:00:00").getTime());

        // �e�X�g�Ώۂ̎��s
        int year = DateUtil.getWarekiYear(date);

        // ���ʊm�F
        assertEquals(1, year);
    }

    /**
     * testGetWarekiYear02�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l�Fdate=1989�N1��7��23��59��59�b<br>
     * ���Ғl�F64<br>
     * 
     * �E�����ŏ��̓��̌ߑO0����1�b�O���m�F����B<br>
     * @throws Exception ��O
     */
    public void testGetWarekiYear02() throws Exception {
        // ���͒l�̐ݒ�
        df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        date = new Date(df.parse("1989.01.07 23:59:59").getTime());

        // �e�X�g�Ώۂ̎��s
        int year = DateUtil.getWarekiYear(date);

        // ���ʊm�F
        assertEquals(64, year);
    }

    /**
     * testGetWarekiYear03�B<br>
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l�Fdate=1989�N1��8��0��0��1�b<br>
     * ���Ғl�F1<br>
     * 
     * �E�����ŏ��̓��̌ߑO0����1�b����m�F����B<br>
     * @throws Exception ��O
     */
    public void testGetWarekiYear03() throws Exception {
        // ���͒l�̐ݒ�
        df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        date = new Date(df.parse("1989.01.08 00:00:01").getTime());

        // �e�X�g�Ώۂ̎��s
        int year = DateUtil.getWarekiYear(date);

        // ���ʊm�F
        assertEquals(1, year);
    }

    /**
     * testGetWarekiYear04�B<br>
     * 
     * (�ُ�n)<br>
     * �ϓ_�FG<br>
     * 
     * ���͒l�Fdate=1868�N9��3��<br>
     * ���Ғl�FIllegalArgumentException���X���[�����<br>
     * 
     * �E���͂�����t�������ŏ��̓��̑O���̏ꍇ���m�F����B<br>
     * @throws Exception ��O
     */
    public void testGetWarekiYear04() throws Exception {
        // ���͒l�̐ݒ�
        df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        date = new Date(df.parse("1868.09.03 00:00:00").getTime());

        // �e�X�g�Ώۂ̎��s
        try {
            int year = DateUtil.getWarekiYear(date);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Wareki Gengo not found for "
                    + date, e.getMessage());
            return;
        }
    }

    /**
     * testGetWarekiYear05�B<br>
     * 
     * (�ُ�n)<br>
     * �ϓ_�FG<br>
     * 
     * ���͒l�Fdate=null<br>
     * ���Ғl�FNullPointerException���X���[�����<br>
     * 
     * �ENullPointerException����������<br>
     * @throws Exception ��O
     */
    public void testGetWarekiYear05() throws Exception {
        // ���͒l�̐ݒ�

        try {
            // �e�X�g�Ώۂ̎��s
            int year = DateUtil.getWarekiYear(null);
            fail();
        } catch (NullPointerException e) {
            return;
        }

    }

}
