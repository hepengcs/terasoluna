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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import jp.terasoluna.fw.batch.core.JobException;
import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.core.QueueingException;
import jp.terasoluna.fw.batch.core.WorkQueue;
import jp.terasoluna.fw.batch.core.WorkUnit;
import jp.terasoluna.fw.batch.openapi.JobContext;

/**
 * <code>WorkQueue</code> �C���^�t�F�[�X�̕W�������N���X�B
 * 
 * <p>���̎����ł́AJDK�� <code>BLockingQueue</code> �ɂ���ăL���[�C���O������
 * ����B</p>
 * 
 * <p>�L���[�ւ̗v�f�ǉ��A�L���[����̗v�f�擾�̍ۂɂ́A�u���b�N��������̂ł�
 * �Ȃ��A�^�C���A�E�g���Ԃ������s�������Ƃ� <code>JobStatus</code> ���`�F�b�N��
 * <code>QueueStoppedException</code> ��O���X���[����B
 * �L���[�ւ̗v�f�ǉ��A�L���[����̗v�f�擾�̍ۂɁA�X���b�h���C���^���v�g���ꂽ
 * �ۂɂ�<code>QueueStoppedException</code> ��O���X���[����B
 * </p>
 *
 */
public class StandardWorkQueue implements WorkQueue {

    /**
     * �L���[���`�F�b�N����ۂ̃^�C���A�E�g�B�i�~���b�j
     */
    private int queueCheckTimeout = 10000;
    
    /**
     * �L���[�B
     */
    private BlockingQueue<WorkUnit> queue;

    /**
     * �W���u�X�e�[�^�X�B
     */
    private JobStatus jobStatus;

    /**
     * �L���[�̗v�f�̏������ʂ̃��X�g�B
     */
    private List<Future< ? >> futureResultList = new ArrayList<Future< ? >>();

    /**
     * �L���[�̏I�[�������}�[�N�B
     */
    public static final WorkUnit END_MARK = new WorkUnit() {
        public boolean isEndMark() {
            return true;
        }

        public JobContext getJobContext() {
            throw new UnsupportedOperationException();
        }

        public void setJobContext(JobContext jobContext) {
            throw new UnsupportedOperationException();
        }
    };

    /**
     * �R���X�g���N�^�B
     *
     * @param queueLength �L���[�̒���
     * @param jobStatus �W���u�X�e�[�^�X
     */
    public StandardWorkQueue(int queueLength, JobStatus jobStatus) {
        queue = new ArrayBlockingQueue<WorkUnit>(queueLength);
        this.jobStatus = jobStatus;
    }

    /**
     * �����Ώۃf�[�^���L���[�ɒǉ�����B
     *
     * @param element �L���[�ɒǉ�����v�f
     */
    public void put(WorkUnit element) {
        try {
            while (!queue.offer(element, queueCheckTimeout, 
                    TimeUnit.MILLISECONDS)) {
                checkJobStatus();
            }
        } catch (InterruptedException e) {
            throw new QueueingException(e);
        }
    }

    /**
     * �W���u�X�e�[�^�X���`�F�b�N����B
     *
     * <p>�V���b�g�_�E�����v������Ă���Ƃ��ɂ́A<code>JobStatus<code>
     * ���V���b�g�_�E���ɍX�V������ŁA<code>QueueingStoppedException</code>
     * ��O���X���[����B</p>
     * 
     * <p>����ɁA�W���u�X�e�[�^�X�� <code>JobStatus.STATE.STARTED</code>�A
     * ���邢�� <code>JobStatus.STATE.RESTARTED</code>
     * �̂ǂ���ł��Ȃ��Ƃ��ɂ́A<code>QueueingStoppedException</code>
     * ��O���X���[����B</p>
     */
    private void checkJobStatus() {
        if (jobStatus.isShutdownGraceful() || jobStatus.isShutdownImmediate()) {
            jobStatus.suspend();
            throw new QueueingException();
        }
        if (!jobStatus.isExecuting()) {
            throw new QueueingException();
        }
    }

    /**
     * �L���[���I������B
     * 
     * <p>�L���[�̏I�[�ł���C���X�^���X���L���[�ɒǉ�����B</p>
     */
    public void close() {
        put(END_MARK);
    }

    /**
     * �L���[�̗v�f���擾����B
     *
     * @return �L���[�̗v�f
     */
    public WorkUnit take() {

        //�S�X���b�h���ҋ@��Ԏ��AEND_MARK��take���ꂽ�ꍇ��
        //END_MARK��queue����폜����邽�ߍē������K�v
        try {
            while (true) {
                WorkUnit workUnit = 
                    queue.poll(queueCheckTimeout, TimeUnit.MILLISECONDS);
                if (workUnit == END_MARK) {
                    put(END_MARK);
                    return END_MARK;
                }
                if (workUnit != null) {
                    return workUnit;
                }
                checkJobStatus();
            }
        } catch (InterruptedException e) {
            throw new QueueingException(e);
        }
    }

    /**
     * �L���[�̗v�f���������邷�ׂẴ��[�J�[�̏I����҂����킹��B
     */
    public void waitForAllWorkers() {
        for (Future< ? > futureResult : futureResultList) {
            try {
                futureResult.get();
            } catch (ExecutionException e) {
                throw new JobException(e);
            } catch (InterruptedException e) {
                throw new JobException(e);
            }
        }
    }
    
    /**
     * ���[�J�̏������ʂɔ񓯊��ŃA�N�Z�X�����߂̃t���[�`���[��ǉ�����B
     * 
     * @param futureResult ���[�J�̏������ʂɔ񓯊��ŃA�N�Z�X�����߂̃t���[�`
     * ���[
     */
    public void addFutureJobResult(Future< ? > futureResult) {
        futureResultList.add(futureResult);
    }

    /**
     * �^�C���A�E�g�ݒ肷��B
     * 
     * @param timeout �^�C���A�E�g(�~���b)
     */
    public void setQueueCheckTimeout(int timeout) {
        queueCheckTimeout = timeout;
    }
}
