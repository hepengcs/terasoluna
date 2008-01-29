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

package jp.terasoluna.fw.file.dao;

/**
 * �t�@�C���A�N�Z�X�@�\�Ŕ���������O�����b�v����N���X�B
 * <p>
 * �t�@�C���A�N�Z�X�@�\�̏��������ɔ���������O�����b�v����N���X�B
 * </p>
 */
public class FileException extends RuntimeException {
    
    /**
     * �V���A���o�[�W����UID�B
     */
    private static final long serialVersionUID = 3532998688369543117L;

    /**
     * �t�@�C�����B
     */
    private final String fileName;
    
    /**
     * �R���X�g���N�^�B
     * @param e ������O
     */
    public FileException(Exception e) {
        super(e);
        fileName = null;
    }

    /**
     * �R���X�g���N�^�B
     * @param e ������O
     * @param fileName �t�@�C����
     */
    public FileException(Exception e, String fileName) {
        super(e);
        this.fileName = fileName;
    }

    /**
     * �R���X�g���N�^�B
     * @param message ���b�Z�[�W
     */
    public FileException(String message) {
        super(message);
        fileName = null;
    }

    /**
     * �R���X�g���N�^�B
     * @param message ���b�Z�[�W
     * @param fileName �t�@�C����
     */
    public FileException(String message, String fileName) {
        super(message);
        this.fileName = fileName;
    }

    /**
     * �R���X�g���N�^�B
     * @param message ���b�Z�[�W
     * @param e ������O
     */
    public FileException(String message, Exception e) {
        super(message, e);
        fileName = null;
    }

    /**
     * �R���X�g���N�^�B
     * @param message ���b�Z�[�W
     * @param e ������O
     * @param fileName �t�@�C����
     */
    public FileException(String message, Exception e, String fileName) {
        super(message, e);
        this.fileName = fileName;
    }

    /**
     * �t�@�C�������擾����B
     * 
     * @return �t�@�C����
     */
    public String getFileName() {
        return fileName;
    }
}
