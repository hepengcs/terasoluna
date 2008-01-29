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

package jp.terasoluna.fw.file.util;

import java.util.List;

/**
 * �t�@�C������@�\�Œ񋟂��鏈���̃C���^�t�F�[�X.
 *
 * @see FileControlImpl
 * @see FileUtility
 */
public interface FileControl {

    /**
     * �t�@�C���̃R�s�[.
     *
     * @param srcFile �R�s�[���̃t�@�C���̃p�X
     * @param newFile �R�s�[��̃t�@�C���̃p�X
     */
    void copyFile(String srcFile, String newFile);

    /**
     * �t�@�C���̍폜.
     *
     * @param srcFile �폜����t�@�C���̃p�X
     */
    void deleteFile(String srcFile);

    /**
     * �t�@�C������.
     *
     * @param fileList ��������t�@�C���̃��X�g
     * @param newFile �������Ăł���t�@�C���̃p�X
     */
    void mergeFile(List<String> fileList, String newFile);

    /**
     * �t�@�C�����̕ύX��t�@�C���̈ړ�.
     *
     * @param srcFile �ړ��O�̃p�X
     * @param newFile �ړ���̃p�X
     */
    void renameFile(String srcFile, String newFile);

}
