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

/**
 *  ClassUtil�N���X�Ŕ��������\�����ꂽ��O�����b�v���܂��B
 * 
 * <p>
 *  ���ۂɔ���������O�ɂ��ẮA getCause() ���\�b�h����擾����B
 * </p>
 * 
 * @see jp.terasoluna.fw.util.ClassUtil
 * 
 */
public class ClassLoadException extends Exception {

    /**
     * �V���A���o�[�W����ID�B
     */
    private static final long serialVersionUID = -7229283425927441452L;

    /**
     * �R���X�g���N�^
     * 
     * @param cause ���b�v�����O
     */
    public ClassLoadException(Throwable cause) {
        super(cause);
    }

}
