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

import org.springframework.validation.Validator;

import jp.terasoluna.fw.batch.core.CollectedDataHandler;
import jp.terasoluna.fw.batch.core.CollectedDataHandlerFactory;
import jp.terasoluna.fw.batch.core.WorkQueue;
import jp.terasoluna.fw.batch.openapi.JobContext;

/**
 * ���̓`�F�b�N�p�`�����J�[�𐶐�����t�@�N�g���N���X�B
 *
 */
public class ValidationExecutorFactory 
    implements CollectedDataHandlerFactory {

    /**
     * �Ώۃf�[�^�p�`�����N�쐬�p�N���X�B
     */
    private CollectedDataHandlerFactory collectedDataHandlerFactory = null;
    
    /**
         * �o���f�[�^�B
         */
    private Validator validator = null;
    
        /**
        * �o���f�[�^���ʃn���h���B
        */
    private ValidationResultHandler validationResultHandler = null;
    
    /**
     * ���̓`�F�b�N�����p�`�����J�[�𐶐�����B
     *
     * @param workQueue �`�����J�[�����������`�����N���Z�b�g����L���[
     * @param jobContext �W���u�R���e�N�X�g
     * @return �`�����J�[
     */
    public CollectedDataHandler getHandler(WorkQueue workQueue,
            JobContext jobContext) {
        CollectedDataHandler handler =
            collectedDataHandlerFactory.getHandler(workQueue, jobContext);
        return 
          new ValidationExecutor(handler , validator , validationResultHandler);
    }

    /**
     * �o���f�[�^���ʃn���h����ݒ肷��B
     * 
     * @param validationResultHandler �o���f�[�^���ʃn���h��
     */
    public void setValidationResultHandler(
            ValidationResultHandler validationResultHandler) {
        this.validationResultHandler = validationResultHandler;
    }

    /**
     * �o���f�[�^��ݒ肷��B
     * 
     * @param validator �o���f�[�^
     */
    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    /**
     * �Ώۃf�[�^�p�`�����N�쐬�p�N���X��ݒ肷��B
     * 
     * @param collectedDataHandlerFactory �Ώۃf�[�^�p�`�����N�쐬�p�N���X
     */
    public void setCollectedDataHandlerFactory(
            CollectedDataHandlerFactory collectedDataHandlerFactory) {
        this.collectedDataHandlerFactory = collectedDataHandlerFactory;
    }
}
