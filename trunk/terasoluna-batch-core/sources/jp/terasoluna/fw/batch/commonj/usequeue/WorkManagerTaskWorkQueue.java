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

package jp.terasoluna.fw.batch.commonj.usequeue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import jp.terasoluna.fw.batch.core.JobException;
import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.standard.StandardWorkQueue;

import commonj.work.WorkItem;
import commonj.work.WorkManager;

/**
 * <code>WorkQueue</code> �C���^�t�F�[�X��Commonj�p�����N���X�B
 * 
 * <p>
 * ���̎����ł́AJDK�� <code>BLockingQueue</code> �ɂ���ăL���[�C���O����������B
 * </p>
 * 
 * <p>
 * �L���[�ւ̗v�f�ǉ��A�L���[����̗v�f�擾�̍ۂɂ́A�u���b�N��������̂ł͂Ȃ��A
 * �^�C���A�E�g���Ԃ������s�������Ƃ�<code>JobStatus</code> ���`�F�b�N�� 
 * <code>QueueingException</code> ��O���X���[����B 
 * �L���[�ւ̗v�f�ǉ��A�L���[����̗v�f�擾�̍ۂɁA�X���b�h���C���^���v�g���ꂽ
 * �ۂɂ�<code>QueueingException</code> ��O���X���[����B
 * </p>
 * 
 */
public class WorkManagerTaskWorkQueue extends StandardWorkQueue {

    /**
     * �L���[�̗v�f�̏������ʂ̃��X�g�B
     */
    private List<WorkItem> workItemList = new ArrayList<WorkItem>();

    /**
     * ���[�N�}�l�[�W���B
     */
    private WorkManager workManager = null;

    /**
     * �W���u���J�n����܂ł̃^�C���A�E�g�i�~���b�j�B
     */
    private long workTimeout = WorkManager.INDEFINITE;

    /**
     * �R���X�g���N�^�B
     * 
     * @param queueLength �L���[�̒���
     * @param jobStatus �W���u�X�e�[�^�X
     */
    public WorkManagerTaskWorkQueue(int queueLength, JobStatus jobStatus) {
        super(queueLength, jobStatus);
    }

    /**
     * �L���[�̗v�f���������邷�ׂẴ��[�J�̏I����҂����킹��B
     */
    @Override
    public void waitForAllWorkers() {
        try {
            workManager.waitForAll(workItemList, workTimeout);
        } catch (IllegalArgumentException e) {
            throw new JobException(e);
        } catch (InterruptedException e) {
            throw new JobException(e);
        }
    }

    /**
     * ���[�J�̏������ʂɔ񓯊��ŃA�N�Z�X�����߂�WorkItem��ǉ�����B
     * 
     * @param workItem ���[�J�̏������ʂɔ񓯊��ŃA�N�Z�X�����߂�WorkItem
     */
    public void addWorkItem(WorkItem workItem) {
        workItemList.add(workItem);
    }

    /**
     * ���[�N�}�l�[�W����ݒ肷��B
     * 
     * @param workManager workManager ���[�N�}�l�[�W��
     */
    public void setWorkManager(WorkManager workManager) {
        this.workManager = workManager;
    }

    /**
     * �W���u���J�n����܂ł̃^�C���A�E�g�i�~���b�j�̐ݒ�B
     * 
     * @param workTimeout �^�C���A�E�g�i�~���b�j
     */
    public void setWorkTimeout(long workTimeout) {
        this.workTimeout = workTimeout;
    }

    /**
     * �������ʂɔ񓯊��ŃA�N�Z�X�����߂̃t���[�`���[��ǉ�����B
     * ���̃N���X�ł͎g�p���Ȃ����߁AUnsupportedOperationException���X���[����B
     *  
     * @param futureResult �������ʂɔ񓯊��ŃA�N�Z�X����t���[�`���[
     */
    @Override
    public void addFutureJobResult(Future< ? > futureResult) {
        throw new UnsupportedOperationException();
    }

}
