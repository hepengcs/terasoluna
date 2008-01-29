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

package jp.terasoluna.fw.batch.standard;

import jp.terasoluna.fw.batch.core.AbstractCollector;
import jp.terasoluna.fw.batch.core.CollectedDataHandler;
import jp.terasoluna.fw.batch.core.CollectorResult;
import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.openapi.JobContext;
import jp.terasoluna.fw.batch.openapi.ReturnCode;

/**
 * �����ɐݒ肳�ꂽ������z�񂩂�A�擾�������s���R���N�^�B
 * 
 * <p>Spring�̒�`�t�@�C���ŁA�擾�f�[�^�����̃R���N�^�̑����l�Ƃ��ăC���W�F�N�V
 * �������Ďg�����Ƃ��ł���B</p>
 * <p>Bean��`�t�@�C���Ƀv���p�e�B�ݒ肪���݂��Ȃ������ꍇ�ɂ͗�O�Ƃ��ď�����
 * ���B</p>
 * 
 */
public class StringArrayPropertyCollector extends AbstractCollector {

    /**
     * �擾�Ώۂ̔z��B
     */
    private String[] dataArray = null;
    
    /**
     * �擾�������s���B
     * 
     * <p>�擾�Ώۂ̔z�񂩂�f�[�^���擾����B</p>
     * 
     * @param jobContext �W���u�R���e�N�X�g
     * @param collectedDataHandler �擾�f�[�^����������n���h��
     * @param jobStatus �W���u�X�e�[�^�X
     * @return �擾��������
     */
    @Override
    protected CollectorResult doCollect(JobContext jobContext,
            CollectedDataHandler collectedDataHandler, JobStatus jobStatus) {
        int collected = 0;
        for (Object listItem : dataArray) {
            collectedDataHandler.handle(listItem, collected++);
            jobStatus.incrementCollected();
        }
        return new CollectorResult(ReturnCode.NORMAL_END, collected);
    }
    
    /**
     * �擾�Ώۂ̔z���ݒ肷��B
     * 
     * @param dataArray �擾�Ώۂ̔z��
     */
    public void setDataArray(String[] dataArray) {
        this.dataArray = dataArray;
    }
}
