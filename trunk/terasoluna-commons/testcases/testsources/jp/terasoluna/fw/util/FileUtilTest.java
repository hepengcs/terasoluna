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

import java.io.File;

import jp.terasoluna.utlib.PropertyTestCase;
import jp.terasoluna.utlib.UTUtil;

/**
 * 
 * FileUtil �u���b�N�{�b�N�X�e�X�g�B<br>
 *
 * (�O�����)<br>
 * �@�E�v���p�e�B�t�@�C��(system.properties)�ɂāA�Z�b�V����
 * �@�@�f�B���N�g���̃x�[�X�����ȉ��̂悤�Ɏw�肳��Ă��邱��<br>
 * �@�@�i�f�t�H���g��ԂȂ�OK�j<br>
 * �@�@�@session.dir.base=/tmp/sessions<br>
 * �@�E�v���p�e�B�t�@�C��(test.properties)�ɁA�폜�e�X�g�p��
 * �@�@�f�B���N�g���̃x�[�X�����ȉ��̂悤�ɒǉ��w�肷�邱��<br>
 * �@�@�@fileutiltest.dir.base=/tmp/test<br>
 *
 */
@SuppressWarnings("unused")
public class FileUtilTest extends PropertyTestCase {

    /**
     * �Z�b�V�����f�B���N�g�������v���p�e�B����擾����L�[�l�B
     */
    public static final String SESSIONS_DIR = "session.dir.base";

    /*
     * �Z�b�V�����f�B���N�g���̐�΃p�X�B
     */
    String TMP_SESSIONS_DIR = "/tmp/sessions/";

    /*
     * FileUtil�e�X�g�p�f�B���N�g���̐�΃p�X�B
     */
    String TMP_TEST_DIR = "/tmp/test/";

    /**
     * Constructor for FileUtilTest.
     * @param arg0
     */
    public FileUtilTest(String arg0) {
        super(arg0);
    }

    @Override
    protected void setUpData() throws Exception {
        addProperty("session.dir.base", "/tmp/sessions");
    }

    @Override
    protected void cleanUpData() throws Exception {
        deleteProperty("session.dir.base");
    }

    /**
     * getSessionDirectoryName(1)
     * 
     * (����n)<br>
     * �ϓ_�F<br>
     * 
     * ���͒l�F16���̉p����<br>
     * ���Ғl�F���͂̃n�b�V���l��16�i�ϊ���������<br>
     * @throws Exception ��O  */
    public void testGetSessionDirectoryName01() throws Exception {
        // ���͒l�̐ݒ�
        String input = "01234567abcdefgh";

        // �e�X�g�Ώۂ̎��s
        String result = FileUtil.getSessionDirectoryName(input);

        // ���ʂ̊m�F
        String hope = StringUtil.toHexString(HashUtil.hashSHA1(input), "");
        assertEquals(hope, result);
    }

    /**
     * getSessionDirectoryName(2)
     * 
     * (�ُ�n)<br>
     * �ϓ_�F<br>
     * 
     * ���͒l�Fnull<br>
     * ���Ғl�FNullPointerException<br>
     * @throws Exception ��O
     */
    public void testGetSessionDirectoryName02() throws Exception {
        // ���͒l�̐ݒ�
        String input = null;

        // �e�X�g�Ώۂ̎��s
        try {
            String result = FileUtil.getSessionDirectoryName(input);
            fail();
        } catch (NullPointerException e) {
            return;
        }
    }

    /**
     * getSessionDirectoryName(3)
     * 
     * (����n)<br>
     * �ϓ_�F<br>
     * 
     * ���͒l�F""(�󕶎�)<br>
     * ���Ғl�F���͂̃n�b�V���l��16�i�ϊ���������<br>
     * @throws Exception ��O */
    public void testGetSessionDirectoryName03() throws Exception {
        // ���͒l�̐ݒ�
        String input = "";

        // �e�X�g�Ώۂ̎��s
        String result = FileUtil.getSessionDirectoryName(input);

        // ���ʂ̊m�F
        String hope = StringUtil.toHexString(HashUtil.hashSHA1(input), "");
        assertEquals(hope, result);
    }

    /**
     * getSessionDirectory(1)
     * 
     * (����n)<br>
     * �ϓ_�F<br>
     * 
     * ���͒l�F16���̉p����<br>
     * ���Ғl�F���͂ɑΉ�����f�B���N�g�����̃f�B���N�g��<br>
     * @throws Exception ��O */
    public void testGetSessionDirectory01() throws Exception {
        // ���͒l�̐ݒ�
        String input = "01234567abcdefgh";

        // �e�X�g�Ώۂ̎��s
        File result = FileUtil.getSessionDirectory(input);

        // ���ʂ̊m�F
        String dirName = StringUtil.toHexString(HashUtil.hashSHA1(input), "");
        File hope = new File(TMP_SESSIONS_DIR + dirName);
        assertEquals(hope, result);
    }

    /**
     * getSessionDirectory(2)
     * 
     * (�ُ�n)<br>
     * �ϓ_�F<br>
     * 
     * ���͒l�Fnull<br>
     * ���Ғl�FNullPointerException<br>
     * @throws Exception ��O */
    public void testGetSessionDirectory02() throws Exception {
        // ���͒l�̐ݒ�
        String input = null;

        // �e�X�g�Ώۂ̎��s
        try {
            File result = FileUtil.getSessionDirectory(input);
            fail();
        } catch (NullPointerException e) {
            return;
        }
    }

    /**
     * getSessionDirectory(3)
     * 
     * (����n)<br>
     * �ϓ_�F<br>
     * 
     * ���͒l�F""(�󕶎�)<br>
     * ���Ғl�F���͂ɑΉ�����f�B���N�g�����̃f�B���N�g��<br>
     * @throws Exception ��O */
    public void testGetSessionDirectory03() throws Exception {
        // ���͒l�̐ݒ�
        String input = "";

        // �e�X�g�Ώۂ̎��s
        File result = FileUtil.getSessionDirectory(input);

        // ���ʂ̊m�F
        String dirName = StringUtil.toHexString(HashUtil.hashSHA1(input), "");
        File hope = new File(TMP_SESSIONS_DIR + dirName);
        assertEquals(hope, result);
    }

    /**
     * getSessionDirectory(4)
     * 
     * (����n)<br>
     * �ϓ_�F<br>
     * 
     * �v���p�e�B��session.dir.base���ݒ肳��Ă��Ȃ����A
     * �f�t�H���g�̃f�B���N�g�����Ԃ��ė��鎖���m�F����B
     * @throws Exception ��O */
    public void testGetSessionDirectory04() throws Exception {
        // ���͒l�̐ݒ�
        String input = "01234567abcdefgh";

        // �v���p�e�B�폜
        deleteProperty("session.dir.base");

        // �e�X�g�Ώۂ̎��s
        File result = FileUtil.getSessionDirectory(input);

        // ���ʂ̊m�F
        String dirName = StringUtil.toHexString(HashUtil.hashSHA1(input), "");
        File hope = new File(File.separator + "temp" + File.separator + dirName);
        assertEquals(hope, result);
    }

    /**
     * getSessionDirectory(5)
     * 
     * (����n)<br>
     * �ϓ_�F<br>
     * 
     * �v���p�e�B��session.dir.base�̒l���󕶎��̎��A
     * �f�t�H���g�̃f�B���N�g�����Ԃ��ė��鎖���m�F����B
     * @throws Exception ��O */
    @SuppressWarnings("unchecked")
    public void testGetSessionDirectory05() throws Exception {
        // ���͒l�̐ݒ�
        String input = "01234567abcdefgh";

        // �v���p�e�B�̒l���󕶎��ɐݒ�
        deleteProperty("session.dir.base");
        addProperty("session.dir.base", "");

        // �e�X�g�Ώۂ̎��s
        File result = FileUtil.getSessionDirectory(input);

        // ���ʂ̊m�F
        String dirName = StringUtil.toHexString(HashUtil.hashSHA1(input), "");
        File hope = new File(File.separator + "temp" + File.separator + dirName);
        assertEquals(hope, result);
    }
    
    /**
     * makeSessionDirectory(1)
     * 
     * (����n)<br>
     * �ϓ_�F<br>
     * 
     * ���͒l�F16���̉p����<br>
     * ���Ғl�F�e�X�g�Ώۂ̎��s���������A���͂ɑΉ�����f�B���N�g������
     * �@�@�@�@�f�B���N�g�����쐬����Ă��邱��<br>
     * @throws Exception ��O */
    public void testMakeSessionDirectory01() throws Exception {
        // ���͒l�̐ݒ�
        String input = "01234567abcdefgh";

        // ���O����
        String dirName = StringUtil.toHexString(HashUtil.hashSHA1(input), "");
        File dir = new File(TMP_SESSIONS_DIR + dirName);
        // �쐬����f�B���N�g�������݂���ꍇ�A�폜
        if (dir.exists()) {
            dir.delete();
        }

        // �e�X�g�Ώۂ̎��s
        boolean result = FileUtil.makeSessionDirectory(input);

        // ���ʂ̊m�F
        assertTrue(result);
        // �f�B���N�g�����쐬����Ă��邱�Ƃ̊m�F
        if (!dir.exists()) {
            fail();
        }
    }

    /**
     * makeSessionDirectory(2)
     * 
     * (����n)<br>
     * �ϓ_�F<br>
     * 
     * ���͒l�F16���̉p�����i���ɑΉ�����f�B���N�g�������݂���j<br>
     * ���Ғl�F�e�X�g�Ώۂ̎��s�����s���A���͂ɑΉ�����f�B���N�g������
     * �@�@�@�@�f�B���N�g�����쐬����Ă��邱��<br>
     * @throws Exception ��O */
    public void testMakeSessionDirectory02() throws Exception {
        // ���͒l�̐ݒ�
        String input = "01234567abcdefgh";

        // ���O����
        String dirName = StringUtil.toHexString(HashUtil.hashSHA1(input), "");
        File dir = new File(TMP_SESSIONS_DIR + dirName);
        // �쐬�����f�B���N�g�������݂���ꍇ�A�폜
        if (dir.exists()) {
            dir.delete();
        }

        // �e�X�g�Ώۂ̎��s�i2��J��Ԃ��j
        FileUtil.makeSessionDirectory(input);
        boolean result = FileUtil.makeSessionDirectory(input);

        // ���ʂ̊m�F
        assertFalse(result);
        // �f�B���N�g�����쐬����Ă��邱�Ƃ̊m�F
        if (!dir.exists()) {
            fail();
        }
    }

    /**
     * makeSessionDirectory(3)
     * 
     * (����n)<br>
     * �ϓ_�F<br>
     * 
     * ���͒l�Fnull<br>
     * ���Ғl�Ffalse<br>
     * @throws Exception ��O */
    public void testMakeSessionDirectory03() throws Exception {
        // ���͒l�̐ݒ�
        String input = null;

        // �e�X�g�Ώۂ̎��s
        boolean result = FileUtil.makeSessionDirectory(input);

        // ���ʂ̊m�F
        assertFalse(result);
    }

    /**
     * makeSessionDirectory(4)
     * 
     * (����n)<br>
     * �ϓ_�F<br>
     * 
     * ���͒l�F""(�󕶎�)<br>
     * ���Ғl�Ffalse<br>
     * @throws Exception ��O */
    public void testMakeSessionDirectory04() throws Exception {
        // ���͒l�̐ݒ�
        String input = "";

        // ���O����

        // �e�X�g�Ώۂ̎��s
        boolean result = FileUtil.makeSessionDirectory(input);

        // ���ʂ̊m�F
        assertFalse(result);
    }

    /**
     * rmdirs(1)
     * 
     * (����n)<br>
     * �ϓ_�F<br>
     * 
     * ���͒l�F�C�ӂ̃f�B���N�g���i�T�u�f�B���N�g���A�t�@�C���Ȃ��j<br>
     * ���Ғl�F�e�X�g�Ώۂ̎��s���������A�f�B���N�g�����폜����邱��<br>
     * @throws Exception ��O */
    public void testRmdirs01() throws Exception {
        // ���͒l�̐ݒ�
        File dir = new File(TMP_TEST_DIR + "rmdirs1");

        // ���O����
        // �폜����f�B���N�g�������݂��Ȃ��ꍇ�A�쐬
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // �e�X�g�Ώۂ̎��s
        boolean result = FileUtil.rmdirs(dir);

        // ���ʂ̊m�F
        assertTrue(result);
        // �f�B���N�g�����폜����Ă��邱�Ƃ̊m�F
        if (dir.exists()) {
            fail();
        }
    }

    /**
     * rmdirs(2)
     * 
     * (����n)<br>
     * �ϓ_�F<br>
     * 
     * ���͒l�F���݂��Ȃ��f�B���N�g��<br>
     * ���Ғl�F�e�X�g�Ώۂ̎��s�����s���A�f�B���N�g�������݂��Ȃ�����<br>
     * @throws Exception ��O */
    public void testRmdirs02() throws Exception {
        // ���͒l�̐ݒ�
        File dir = new File(TMP_TEST_DIR + "rmdirs2");

        // ���O����
        // �f�B���N�g�������݂���ꍇ�A�폜
        if (dir.exists()) {
            dir.delete();
        }

        // �e�X�g�Ώۂ̎��s
        boolean result = FileUtil.rmdirs(dir);

        // ���ʂ̊m�F
        assertFalse(result);
        // �f�B���N�g�������݂��Ȃ����Ƃ̊m�F
        if (dir.exists()) {
            fail();
        }
    }

    /**
     * rmdirs(3)
     * 
     * (����n)<br>
     * �ϓ_�F<br>
     * 
     * ���͒l�F�C�ӂ̃f�B���N�g���i�T�u�f�B���N�g���A�t�@�C������j<br>
     * ���Ғl�F�e�X�g�Ώۂ̎��s���������A�f�B���N�g�����폜����邱��<br>
     * @throws Exception ��O */
    public void testRmdirs03() throws Exception {
        // ���͒l�̐ݒ�
        File dir = new File(TMP_TEST_DIR + "rmdirs3");

        // ���O����
        // �T�u�f�B���N�g���A�t�@�C���̐ݒ�
        File subdir = new File(TMP_TEST_DIR + "rmdirs3/tmp");
        File file = new File(TMP_TEST_DIR + "rmdirs3/tmp.txt");
        // �폜����f�B���N�g���A�t�@�C�������݂��Ȃ��ꍇ�A�쐬
        if (!dir.exists()) {
            dir.mkdirs();
        }
        if (!subdir.exists()) {
            subdir.mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }

        // �e�X�g�Ώۂ̎��s
        boolean result = FileUtil.rmdirs(dir);

        // ���ʂ̊m�F
        assertTrue(result);
        // �e�f�B���N�g�������݂��Ȃ����Ƃ̊m�F
        if (dir.exists()) {
            fail();
        }
    }

    /**
     * rmdirs(4)
     * 
     * (����n)<br>
     * �ϓ_�F<br>
     * 
     * ���͒l�Fnull<br>
     * ���Ғl�Ffalse<br>
     * @throws Exception ��O */
    public void testRmdirs04() throws Exception {
        // ���͒l�̐ݒ�
        File dir = null;

        // �e�X�g�Ώۂ̎��s
        boolean result = FileUtil.rmdirs(dir);

        // ���ʂ̊m�F
        assertFalse(result);
    }

    /**
     * rmdirs(5)
     * 
     * (����n)<br>
     * �ϓ_�F<br>
     * 
     * ���͒l�F""(�󕶎�)�̃p�X��File�I�u�W�F�N�g<br>
     * ���Ғl�Ffalse<br>
     * @throws Exception ��O */
    public void testRmdirs05() throws Exception {
        // ���͒l�̐ݒ�
        File dir = new File("");

        // �e�X�g�Ώۂ̎��s
        boolean result = FileUtil.rmdirs(dir);

        // ���ʂ̊m�F
        assertFalse(result);
    }

    /**
     * rmdirs(6)
     * 
     * (����n)<br>
     * �ϓ_�F<br>
     * 
     * ���͒l�F�ǂݎ���p�̃f�B���N�g���i�T�u�f�B���N�g���A�t�@�C���Ȃ��j<br>
     * ���Ғl�F�e�X�g�Ώۂ̎��s���������A�f�B���N�g�����폜����邱��<br>
     * @throws Exception ��O */
    public void testRmdirs06() throws Exception {
        // ���͒l�̐ݒ�
        File dir = new File(TMP_TEST_DIR + "rmdirs6");

        // ���O����
        // �f�B���N�g���͍쐬���Ă���
        // �ǂݎ���p�����������Őݒ肷��
        if(!dir.exists()){
            dir.mkdir();
            dir.setReadOnly();
        }

        // �e�X�g�Ώۂ̎��s
        boolean result = FileUtil.rmdirs(dir);

        // ���ʂ̊m�F
        assertTrue(result);
        // �f�B���N�g�����폜����Ă��邱�Ƃ̊m�F
        if (dir.exists()) {
            fail();
        }
    }

    /**
     * removeSessionDirectory01()
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l�FsessionID="01234567abcdefgh"<br>
     * ���Ғl�Ftrue<br>
     * 
     * �Z�b�V����ID�ɑΉ�����f�B���N�g�������݂��A�f�B���N�g���̍폜�ɐ����������A
     * true���ԋp����邱�Ƃ��m�F����B
     * 
     * @throws Exception ��O */
    public void testRemoveSessionDirectory01() throws Exception {
        // �����ݒ�
        String input = "01234567abcdefgh";
        UTUtil.invokePrivate(
            FileUtil.class,
            "makeSessionDirectory",
            String.class,
            input);
        // �e�X�g���s
        // ���ʊm�F
        assertTrue(FileUtil.removeSessionDirectory(input));
    }

    /**
     * removeSessionDirectory02()
     * 
     * (����n)<br>
     * �ϓ_�FA<br>
     * 
     * ���͒l�FsessionID="01234567abcdefgh"<br>
     * ���Ғl�Ffalse<br>
     * 
     * �Z�b�V����ID�ɑΉ�����f�B���N�g�������݂��Ȃ��Ƃ��A
     * �f�B���N�g���̍폜�Ɏ��s��false���ԋp����邱�Ƃ��m�F����B
     * 
     * @throws Exception ��O */
    public void testRemoveSessionDirectory02() throws Exception {
        // �����ݒ�
        String input = "01234567abcdefgh";
        File dir = (File) UTUtil.invokePrivate(FileUtil.class,
                "getSessionDirectory", String.class, input);
        dir.delete();

        // �e�X�g���s
        // ���ʊm�F
        assertFalse(FileUtil.removeSessionDirectory(input));
    }

    /**
     * removeSessionDirectory03()
     * 
     * (�ُ�n)<br>
     * �ϓ_�FC,G<br>
     * 
     * ���͒l�FsessionID=null<br>
     * ���Ғl�FNullPOinterException<br>
     * 
     * �Z�b�V����ID��null�̎��ANullPointerException���������邱�Ƃ��m�F����B
     * 
     * @throws Exception ��O */
    public void testRemoveSessionDirectory03() throws Exception {
        try {
            // �e�X�g�Ώۂ̎��s
            FileUtil.removeSessionDirectory(null);
            fail();
        } catch (NullPointerException e) {
            return;
        }
        // ���ʊm�F
        fail();
    }
}
