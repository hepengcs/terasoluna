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

package jp.terasoluna.fw.web.taglib;

import java.util.Date;
import java.sql.Timestamp;

import jp.terasoluna.utlib.TagUTUtil;

import junit.framework.TestCase;

/**
 * DateTag �u���b�N�{�b�N�X�e�X�g�B<br>
 * �O�����<br>
 * �Ȃ�<br>
 * <br>
 */
public class DateTagTest extends TestCase {

    // �e�X�g�Ώ�
    DateTag tag = null;

    /**
     * Constructor for DateTagTest.
     * @param arg0
     */
    public DateTagTest(String arg0) {
        super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        tag = (DateTag) TagUTUtil.create(DateTag.class);
    }

    /*
     * @see TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * testDoFormat01�B<br>
     * <br>
     * (����n)<br>
     * �ϓ_�FE<br>
     * <br>
     * ���͒l<br>
     * date="2004-11-24 10:31:00.000000000"<br>
     * pattern="yyyy.MM.dd"<br>
     * <br>
     * ���Ғl<br>
     * �߂�l:String="2004.11.24"<br>
     * <br>
     * ��������сA�t�H�[�}�b�g�p�^�[��������ȏꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoFormat01() throws Exception {

        // �e�X�g�f�[�^�ݒ�
        Timestamp time = Timestamp.valueOf("2004-11-24 10:31:00.000000000");
        Date date = new Date(time.getTime());
        tag.pattern = "yyyy.MM.dd";

        // �e�X�g���s
        String result = tag.doFormat(date);

        // �e�X�g���ʊm�F
        assertEquals("2004.11.24", result);

    } /* testDoFormat01 End */

    /**
     * testDoFormat02�B<br>
     * <br>
     * (�ُ�n)<br>
     * �ϓ_�FG<br>
     * <br>
     * ���͒l<br>
     * date=Null<br>
     * pattern="yyyy.MM.dd"<br>
     * <br>
     * ���Ғl<br>
     * �߂�l:String=NullPointerException<br>
     * <br>
     * ������Null�̏ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoFormat02() throws Exception {

        // �e�X�g�f�[�^�ݒ�
        tag.pattern = "yyyy.MM.dd";

        // �e�X�g���s
        try{
            tag.doFormat(null);
        }catch(NullPointerException ex){

            // �e�X�g���ʊm�F
            assertEquals(NullPointerException.class.getName(), ex.getClass()
                    .getName());
            return;
        }

        fail();

    } /* testDoFormat02 End */

    /**
     * testDoFormat03�B<br>
     * <br>
     * (�ُ�n)<br>
     * �ϓ_�FG<br>
     * <br>
     * ���͒l<br>
     * date="2004-11-24 10:31:00.000000000"<br>
     * pattern=Null<br>
     * <br>
     * ���Ғl<br>
     * �߂�l:String=NullPointerException<br>
     * <br>
     * �p�^�[����Null�̏ꍇ�̃e�X�g�P�[�X<br>
     */
    public void testDoFormat03() {

        // �e�X�g�f�[�^�ݒ�
        Timestamp time = Timestamp.valueOf("2004-11-24 10:31:00.000000000");
        Date date = new Date(time.getTime());
        tag.pattern = null;

        // �e�X�g���s
        try{
            tag.doFormat(date);
        }catch(NullPointerException ex){

            // �e�X�g���ʊm�F
            assertEquals(NullPointerException.class.getName(), ex.getClass()
                    .getName());
            return;
        }

        fail();

    } /* testDoFormat03 End */

} /* DateTagTest Class End */
