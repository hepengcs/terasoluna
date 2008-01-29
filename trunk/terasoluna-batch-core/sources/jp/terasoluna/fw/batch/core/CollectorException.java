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
 * �Ώۃf�[�^��O�N���X�B
 * 
 * <p>�Ώۃf�[�^�擾����(<code>Collector</code>)�ŗ�O�����������ꍇ�ɁA
 * �t���[�����[�N�ɂ���Ă��̃N���X�Ń��b�v����A��͓��ɕK�v�ȏ��
 * �ǉ������B</p>
 * ���̃N���X�ł́A������O�A����ёΏۃf�[�^�擾���ʂ�ێ�����B</p>
 * 
 * <p>�Ώۃf�[�^�擾���ʂ́A������O�̔��������Ώۃf�[�^�擾���ʃn���h���ł���
 * �ꍇ�ɂ̂ݐݒ肳���B������O�̔��������Ώۃf�[�^�擾�����ł���ꍇ�ɂ́A
 * �Ώۃf�[�^�擾���ʂɂ�<code>null</code> ���ݒ肳���B</p>
 * 
 * <p>��O�n���h���}�b�v�ɂ����āA�L�[�Ƃ��ēo�^���邱�Ƃ��ł���B�L�[�Ƃ���
 * �o�^���邽�߂ɁA���̗�O�N���X�́A�����̂Ȃ� <code>public</code> ��
 * �R���X�g���N�^�����B</p>
 *
 */
public class CollectorException extends JobException {

    /**
     * Serializable�p�o�[�W����ID�B
     */
    private static final long serialVersionUID = 2520093984053973902L;

    /**
     * �R���N�^�̏������ʁB
     */
    private CollectorResult collectorResult = null;
    
    /**
     * �R���X�g���N�^�B
     */
    public CollectorException() {
    }

    /**
     * �R���X�g���N�^�B
     *
     * @param e ����������O
     * @param collectorResult �R���N�^�̏������ʁB<code>null</code> �B
     */
    public CollectorException(Exception e, CollectorResult collectorResult) {
        super(e);
        this.collectorResult = collectorResult;
    }

    /**
     * �R���N�^�̏������ʂ��擾����B
     *
     * @return �R���N�^�̏�������
     */
    public CollectorResult getCollectorResult() {
        return collectorResult;
    }
}
