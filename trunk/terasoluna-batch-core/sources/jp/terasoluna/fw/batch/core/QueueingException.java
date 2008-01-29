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
 * �L���[�C���O��O�N���X�B
 * 
 * <p>�f�[�^���L���[�Ɋi�[�ł��Ȃ��Ȃ����ꍇ�A���邢�̓f�[�^���L���[����擾�ł�
 * �Ȃ������ꍇ��
 * �X���[�����</p>
 *
 */
public class QueueingException extends JobException {

    /**
     * Serializable�p�o�[�W����ID
     */
    private static final long serialVersionUID = -3089389826940034290L;

    /**
     * �R���X�g���N�^�B 
     *
     */
    public QueueingException() {
    }

    /**
     * �R���X�g���N�^�B
     * 
     * @param e ������O
     */
    public QueueingException(Exception e) {
        super(e);
    }
}
