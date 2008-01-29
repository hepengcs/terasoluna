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

package jp.terasoluna.fw.batch.validation;

import jp.terasoluna.fw.batch.core.CollectedDataHandler;

import org.springframework.validation.BindException;
import org.springframework.validation.Validator;

/**
 * ���̓`�F�b�N���s�p�A�Ώۃf�[�^�̃n���h���N���X�B
 * 
 * <p>���̓`�F�b�N������A�Ώۃf�[�^�p�`�����N���쐬����B</p>
 *
 */
public class ValidationExecutor implements CollectedDataHandler {

    /**
     * �o���f�[�^�B
     */
    private Validator validator = null;
    
    /**
     * �o���f�[�^���ʃn���h���B
     */
    private ValidationResultHandler validationResultHandler = null;

    /**
     * �Ώۃf�[�^�p�`�����N�쐬�p�N���X�B
     */
    private CollectedDataHandler collectedDataHandler;
    
    /**
     * �R���X�g���N�^�B
     * 
     * @param collectedDataHandler �Ώۃf�[�^�p�`�����N�쐬�p�N���X
     * @param validator �o���f�[�^
     * @param validationResultHandler �o���f�[�^���ʃn���h��
     */
    public ValidationExecutor(CollectedDataHandler collectedDataHandler,
            Validator validator, 
            ValidationResultHandler validationResultHandler) {
        this.collectedDataHandler = collectedDataHandler;
        this.validator = validator;
        this.validationResultHandler = validationResultHandler;
    }
    
    /**
     * ���̓`�F�b�N��A�����Ώۃf�[�^���L���[�ɒǉ�����B
     * �o���f�[�^���ʃn���h������<code>false</code>���ԋp���ꂽ�ꍇ�̓L���[�ւ�
     * �ǉ������͍s��Ȃ��B
     *
     * @param collectedData �����Ώۃf�[�^
     * @param index �����Ώۃf�[�^�̃C���f�b�N�X
     */
    public void handle(Object collectedData, int index) {
        BindException bindException = new BindException(collectedData,
                collectedData.getClass().getName());
        validator.validate(collectedData, bindException);
        
        if (validationResultHandler.handle(bindException, collectedData)) {
            collectedDataHandler.handle(collectedData, index);
        }
    }

    /**
     * �N���[�Y�������s���B
     *
     */
    public void close() {
        collectedDataHandler.close();
    }
}
