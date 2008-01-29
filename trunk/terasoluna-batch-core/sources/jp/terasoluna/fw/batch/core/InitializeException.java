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

package jp.terasoluna.fw.batch.core;



/**
 * ����������O�N���X�B
 *
 */
public class InitializeException extends JobException {


    /**
     * Serializable�p�o�[�W����ID�B
     */
    private static final long serialVersionUID = 8090708522690528976L;
    
    /**
     * �R���X�g���N�^�B
     *
     */
    public InitializeException(){
        
    }

    /**
     * �R���X�g���N�^�B
     *
     * @param message ��O���b�Z�[�W
     */
    public InitializeException(String message) {
        super(message);
    }

}
