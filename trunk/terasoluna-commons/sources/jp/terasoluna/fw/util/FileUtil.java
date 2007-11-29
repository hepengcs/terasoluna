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

/**
 * �t�@�C������֘A�̃��[�e�B���e�B�N���X�B
 *
 * <p>
 *  �Z�b�V����ID�ɑΉ������f�B���N�g���̍쐬�A�擾�A�폜���s���B
 *  �e�Z�b�V����ID�ɑΉ������f�B���N�g���́A ApplicationRecoures 
 *  �t�@�C���� &quot;session.dir.base&quot; �Ŏ����ꂽ�f�B���N�g���̒���
 *  �쐬�����B<br>
 *  �Z�b�V�����ɑΉ������f�B���N�g���́A�T�[�o���ō쐬����PDF�̒��[�Ȃ�
 *  �Z�b�V�����ɕR�t�����ꎞ�I�ȃf�[�^���i�[����ۂɗ��p�ł���B<br>
 *  �Z�b�V�����ɕR�t�����f�B���N�g���́A HttpSession 
 *  �I�u�W�F�N�g�����������ꂽ�ۂɁA���̃��[�e�B���e�B�N���X�̃��\�b�h��
 *  �p���č폜����B<br>
 *  �Z�b�V�����̐����E�j�����Ď�����@�\�ɂ��ẮA
 *  HttpSessionListener���Q�ƁB
 * </p>
 *
 */
public class FileUtil {

    /**
     * �Z�b�V�����ɑΉ��t�����ꂽ�f�B���N�g�����쐬����ۂɁA�e�f�B���N�g����
     * �i�[����e�f�B���N�g������  ApplicationResource 
     * �t�@�C������擾���邽�߂̃L�[�B
     */
    private static final String SESSION_DIR_BASE_KEY = "session.dir.base";

    /**
     * �w�肳�ꂽ�Z�b�V����ID�ɑΉ�����f�B���N�g�������擾����B
     *
     * @param sessionId �Z�b�V����ID
     * @return �Z�b�V����ID�̃n�b�V���l�Ƃ��Đ������ꂽ�f�B���N�g����
     */
    public static String getSessionDirectoryName(String sessionId) {
        byte[] hash = HashUtil.hashSHA1(sessionId);
        return StringUtil.toHexString(hash, "");
    }

    /**
     * �w�肳�ꂽ�Z�b�V����ID�ɑΉ�����f�B���N�g�����擾����B
     * 
     * <p>
     * �v���p�e�B�L�[�̐ݒ���s�Ȃ�Ȃ������ꍇ�A
     * �������͋󕶎��̏ꍇ�� temp�f�B���N�g����p����B
     * </p>
     * @param sessionId �Z�b�V����ID
     * @return �Z�b�V����ID�ɑΉ�����f�B���N�g���ƂȂ�t�@�C���I�u�W�F�N�g
     */
    public static File getSessionDirectory(String sessionId) {
        String dirBase = PropertyUtil.getProperty(SESSION_DIR_BASE_KEY);

        if (dirBase == null || "".equals(dirBase)) {
            dirBase = File.separator + "temp";
        }

        String dirName = getSessionDirectoryName(sessionId);
        return new File(dirBase + File.separator + dirName);
    }

    /**
     * �w�肳�ꂽ�Z�b�V����ID�ɑΉ�����f�B���N�g�����쐬����B
     *
     * <p>�쐬�����������ꍇ�ɂ́Atrue ��Ԃ��B</p>
     *
     * @param sessionId �Z�b�V����ID
     * @return �f�B���N�g���̍쐬�ɐ�������� true
     */
    public static boolean makeSessionDirectory(String sessionId) {
        if (sessionId == null || "".equals(sessionId)) {
            return false;
        }
        return getSessionDirectory(sessionId).mkdirs();
    }

    /**
     * �w�肳�ꂽ�Z�b�V����ID�ɑΉ�����f�B���N�g�����폜����B
     *
     * <p>�폜�����������ꍇ�ɂ́Atrue ��Ԃ��B</p>
     *
     * @param sessionId �Z�b�V����ID
     * @return �f�B���N�g���̍폜�ɐ�������� true 
     */
    public static boolean removeSessionDirectory(String sessionId) {
        return rmdirs(getSessionDirectory(sessionId));
    }

    /**
     * �w�肳�ꂽ�f�B���N�g�����폜����B
     *
     * <p>�f�B���N�g�����Ƀt�@�C���A�f�B���N�g����
     * ����ꍇ�ł��A�ċA�I�ɍ폜�����B</p>
     *
     * @param dir �폜����f�B���N�g��
     * @return �f�B���N�g���̍폜�ɐ�������� true
     */
    public static boolean rmdirs(File dir) {
        if (dir == null) {
            return false;
        }
        File[] files = dir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    rmdirs(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return dir.delete();
    }
}
