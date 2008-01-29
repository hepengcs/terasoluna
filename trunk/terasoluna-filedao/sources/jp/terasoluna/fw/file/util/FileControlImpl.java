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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * FileControl�C���^�t�F�[�X����������N���X.
 * <p>
 * ���̃N���X�́A�t�@�C�����쏈�������s����FileUtility�N���X�����b�v���Ă���B<br/>
 * FileControl�N���X��DI�R���e�i�ɂ���ăr�W�l�X���W�b�N�𐶐�����ہA�r�W�l�X���W�b�N�̑����Ƃ��Đݒ肷��B<br/>
 * FileControl�N���X�͑����ɁA�t�@�C��������s���ۂɊ�ƂȂ�p�X(��p�X)�����B<br/>
 * FileUtility�N���X�͈ȉ��̋@�\���������Ă���B
 * <ul>
 * <li>�t�@�C�����̕ύX��t�@�C���̈ړ�</li>
 * <li>�t�@�C���̃R�s�[</li>
 * <li>�t�@�C���̍폜</li>
 * <li>�t�@�C���̌���</li>
 * </ul>
 * �Ȃ��A�t�@�C���@�\�Ŏg�p����p�X�͑��΃p�X�A��΃p�X�̗������w���B
 * </p>
 * 
 * 
 * @see FileUtility
 */
public class FileControlImpl implements FileControl {

    /**
     * ��p�X.
     * <p>
     * ��p�X���g�p���邱�Ƃɂ��A�t�@�C���A�N�Z�X���ɔ�������t�@�C���p�X�̊��ˑ��̖���������邱�Ƃ��ł���B
     * </p>
     */
    private String basePath = "";

    /**
     * �t�@�C���̃R�s�[�B
     * 
     * @param srcFile �R�s�[���̃t�@�C���̃p�X
     * @param newFile �R�s�[��̃t�@�C���̃p�X
     */
    public void copyFile(String srcFile, String newFile) {
        
        FileUtility.copyFile(getAbsolutePath(srcFile),
                getAbsolutePath(newFile));
    }

    /**
     * �t�@�C���̍폜�B
     * 
     * @param srcFile �폜����t�@�C���̃p�X
     */
    public void deleteFile(String srcFile) {
        
        FileUtility.deleteFile(getAbsolutePath(srcFile));
    }

    /**
     * �t�@�C�������B
     * 
     * @param fileList ��������t�@�C���̃��X�g
     * @param newFile �������Ăł���t�@�C���̃p�X
     */
    public void mergeFile(List<String> fileList, String newFile) {
        
        List<String> srcFileList = new ArrayList<String>();
        for (String fileName : fileList) {
            srcFileList.add(getAbsolutePath(fileName));
        }

        FileUtility.mergeFile(srcFileList, getAbsolutePath(newFile));
    }

    /**
     * �t�@�C�����̕ύX��t�@�C���̈ړ��B
     * 
     * @param srcFile �ړ��O�̃p�X
     * @param newFile �ړ���̃p�X
     */
    public void renameFile(String srcFile, String newFile) {

        FileUtility.renameFile(getAbsolutePath(srcFile),
                getAbsolutePath(newFile));
    }

    /**
     * FileControl�Ŏg�p����t�@�C�������΃p�X���ǂ������m�F���āA��΃p�X��ԋp����B
     * 
     * @param fileName �e���\�b�h�̈����Ƃ��Ď󂯂��t�@�C����
     * @return fileName�̐�΃p�X
     */
    private String getAbsolutePath(String fileName) {
        
        File newFileObject = new File(fileName);
        
        if (!newFileObject.isAbsolute()) {
            StringBuilder newFilePath = new StringBuilder(basePath);
            return newFilePath.append(fileName).toString();
        } else {
            return fileName;
        }
    }
    
    /**
     * ��p�X���擾����B
     * 
     * @return ��p�X
     */
    public String getBasePath() {
        return basePath;
    }

    /**
     * ��p�X��ݒ肷��B
     * 
     * @param basePath ��p�X
     */
    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    /**
     * FileUtility�̃t�@�C���̏㏑���t���O��ݒ肷��B
     * 
     * @param checkFileExist �㏑���t���O
     */
    public void setCheckFileExist(boolean checkFileExist) {
        FileUtility.setCheckFileExist(checkFileExist);
    }

}
