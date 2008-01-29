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
 * �T�|�[�g������O�N���X�B
 * 
 * <p>�T�|�[�g�����A���邢�̓T�|�[�g�����̎��s���ʃn���h�������O���X���[���ꂽ
 * �ꍇ�ɁA�t���[�����[�N�ɂ���Ă��̃N���X�Ń��b�v�����͓��ɕK�v�ȏ�񂪒�
 * �������B���̃N���X�ł́A������O�A����уT�|�[�g�����̎��s���ʂ�ێ�����B
 * </p>
 * 
 * <p>�T�|�[�g�����̎��s���ʂ́A������O�̔��������T�|�[�g�����̎��s���ʃn���h��
 * �ł���ꍇ�ɂ̂ݐݒ肳���B������O�̔��������T�|�[�g�����ł���ꍇ�ɂ́A
 * �T�|�[�g�����̎��s���ʂɂ� <code>null</code> ���ݒ肳���B</p>
 * 
 * <p>��O�n���h���}�b�v�ɂ����āA�L�[�Ƃ��ēo�^���邱�Ƃ��ł���B�L�[�Ƃ��ēo�^
 * ���邽�߂ɁA���̗�O�N���X�́A�����̂Ȃ� <code>public</code> �ȃR���X�g���N�^
 * �����B</p>
 */
public class SupportLogicException extends JobException {

    /**
     * Serializable�p�o�[�W����ID
     */
    private static final long serialVersionUID = 3772461298792124177L;

    /**
     * �T�|�[�g�����̎��s���ʁB
     */
    private BLogicResult blogicResult = null;

    /**
     * �������B
     */
    private String supportProcessorName = null;

    /**
     * �R���X�g���N�^�B
     */
    public SupportLogicException() {
    }
    
    /**
     * �R���X�g���N�^�B
     *
     * @param e ������O
     * @param blogicResult �T�|�[�g�����̎��s���ʁB<code>null</code> �B
     * @param supportProcessorName �T�|�[�g�����N���X�ɐݒ肳�ꂽ���O
     */
    public SupportLogicException(Exception e, BLogicResult blogicResult,
            String supportProcessorName) {
        super(e);
        this.blogicResult = blogicResult;
        this.supportProcessorName = supportProcessorName;
    }

    /**
     * �T�|�[�g�����̎��s���ʂ��擾����B
     *
     * @return �T�|�[�g�����̎��s���ʁB
     *          ������O�̔��������T�|�[�g�����ł���ꍇ�ɂ́A<code>null</code>�B
     */
    public BLogicResult getBlogicResult() {
        return blogicResult;
    }
    
    /**
     * ���������擾����B
     *
     * @return �������B
     */
    public String getSupportProcessorName() {
        return supportProcessorName;
    }
}
