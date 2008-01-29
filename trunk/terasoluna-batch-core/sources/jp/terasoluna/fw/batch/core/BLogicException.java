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

import jp.terasoluna.fw.batch.openapi.BLogicResult;

/**
 * �r�W�l�X���W�b�N��O�N���X�B
 *
 * <p>�r�W�l�X���W�b�N�A���邢�̓r�W�l�X���W�b�N�������ʃn���h�������O��
 * �X���[���ꂽ�ꍇ�ɁA�t���[�����[�N�ɂ���Ă��̃N���X�Ń��b�v�����͓���
 * �K�v�ȏ�񂪒ǉ������B
 * ���̃N���X�ł́A������O�A��O�������Ɏ��s���Ă������̓f�[�^�A
 * ����уr�W�l�X���W�b�N�������ʂ�ێ�����B</p>
 * 
 * <p>�r�W�l�X���W�b�N�������ʂ́A������O�̔��������r�W�l�X���W�b�N�������ʃn��
 * �h���ł���ꍇ�ɂ̂ݐݒ肳���B������O�̔��������r�W�l�X���W�b�N�ł���ꍇ
 * �ɂ́A�r�W�l�X���W�b�N�������ʂɂ� <code>null</code> ���ݒ肳���B</p>
 * 
 * <p>��O�n���h���}�b�v�ɂ����āA�L�[�Ƃ��ēo�^���邱�Ƃ��ł���B
 * �L�[�Ƃ��ēo�^���邽�߂ɁA���̗�O�N���X�́A�����̂Ȃ� <code>public</code> 
 * �ȃR���X�g���N�^�����B</p>
 *
 */
public class BLogicException extends JobException {

    /**
     * Serializable�p�o�[�W����ID
     */
    private static final long serialVersionUID = -6698989068077090127L;

    /**
     * �����Ώۃf�[�^�B
     */
    private Object blogicInputData = null;

    /**
     * �r�W�l�X���W�b�N�������ʁB
     */
    private BLogicResult blogicResult = null;

    /**
     * �R���X�g���N�^�B
     *
     */
    public BLogicException() {
    }
    
    /**
     * �R���X�g���N�^�B
     *
     * @param e ��O
     * @param blogicInputData �����Ώۃf�[�^
     * @param blogicResult �r�W�l�X���W�b�N�������ʁB<code>null</code> �B
     */
    public BLogicException(Exception e, Object blogicInputData,
            BLogicResult blogicResult) {
        super(e);
        this.blogicInputData = blogicInputData;
        this.blogicResult = blogicResult;
    }

    /**
     * �����Ώۃf�[�^���擾����B
     *
     * @return �����Ώۃf�[�^
     */
    public Object getBlogicInputData() {
        return blogicInputData;
    }

    /**
     * �r�W�l�X���W�b�N�������ʂ��擾����B
     *
     * @return �r�W�l�X���W�b�N�������ʁB
     *          ������O�̔��������r�W�l�X���W�b�N�ł���ꍇ�ɂ́A
     *          <code>null</code>�B
     */
    public BLogicResult getBlogicResult() {
        return blogicResult;
    }
}
