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
 * �Ώۃf�[�^�̃n���h���C���^�t�F�[�X�B
 * 
 * <p><code>Collector</code> �Ŏ��W���ꂽ�Ώۃf�[�^�́A���̃C���^�t�F�[�X��
 * �ʂ��ēn�����B���̃C���^�t�F�[�X�̎����N���X�ł́A�擾�����Ώۃf�[�^�̎��
 * �i�r�W�l�X���W�b�N�ŏ�������f�[�^�ł��邩�A���邢�̓W���u�𕪊����邽�߂�
 * �����L�[�ł��邩�A���j�ɉ����ď�������B</p>
 * 
 */
public interface CollectedDataHandler {

    /**
     * �Ώۃf�[�^����������B
     * 
     * @param collectedData �R���N�^�Ŏ��W�����f�[�^
     * @param index �f�[�^�̃C���f�b�N�X
     */
    void handle(Object collectedData, int index);
    
    /**
     * �Ώۃf�[�^�̏������I������B
     *
     */
    void close();
}
