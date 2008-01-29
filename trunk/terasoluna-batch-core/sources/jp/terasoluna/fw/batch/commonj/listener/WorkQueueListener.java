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

package jp.terasoluna.fw.batch.commonj.listener;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import commonj.work.Work;
import commonj.work.WorkEvent;
import commonj.work.WorkItem;

/**
 * CommonJ���[�N�}�l�[�W����Ŏ��s���Ă��郏�[�N�L���[�̏�Ԃ��Ď�����N���X�B
 * 
 * 
 */
public class WorkQueueListener implements WorkMapListener {

    /**
     * ���O�C���X�^���X�B
     */
    private static Log log = LogFactory.getLog(WorkQueueListener.class);

    /**
     * �X�P�W���[�����ꂽ���[�N��ێ�����}�b�v�B
     */
    private Map<WorkItem, Work> map = Collections
            .synchronizedMap(new HashMap<WorkItem, Work>());

    /**
     * �X�P�W���[�����ꂽ���[�N�̓o�^�B
     * 
     * @param workItem ���[�N�����[�N�}�l�W���[�ŃX�P�W���[�������Ƃ��̕ԋp�l
     * @param work �X�P�W���[���������[�N
     */
    public void addWork(WorkItem workItem, Work work) {
        map.put(workItem, work);
    }

    /**
     * �X�P�W���[������Ă��郏�[�N�̎擾�B
     * 
     * @param workItem ���[�N�����[�N�}�l�W���[�ŃX�P�W���[�������Ƃ��̕ԋp�l
     * @return �X�P�W���[������Ă��郏�[�N
     */
    public Work getWork(WorkItem workItem) {
        return (Work) map.get(workItem);
    }

    /**
     * ��Ƃ������������[�N���폜����B
     * 
     * @param workItem ���[�N�����[�N�}�l�W���[�ŃX�P�W���[�������Ƃ��̕ԋp�l
     * @return �X�P�W���[������Ă��郏�[�N
     */
    public Work removeWork(WorkItem workItem) {
        return (Work) map.remove(workItem);
    }

    /**
     * ���[�N�̎�t���Ɏ��s����郁�\�b�h�B
     * 
     * @param we ���[�N�C�x���g
     */
    public void workAccepted(WorkEvent we) {
        printSimpleLog("Work accepted: ", getWork(we.getWorkItem()));
    }

    /**
     * ���[�N�̊������Ɏ��s����郁�\�b�h�B
     * 
     * @param we ���[�N�C�x���g
     */
    public void workCompleted(WorkEvent we) {
        printSimpleLog("Work completed: ", removeWork(we.getWorkItem()));
    }

    /**
     * ���[�N�̎�t���ێ��Ɏ��s����郁�\�b�h�B
     * 
     * @param we ���[�N�C�x���g
     */
    public void workRejected(WorkEvent we) {
        printSimpleLog("Work rejected: ", removeWork(we.getWorkItem()));
    }

    /**
     * ���[�N�̊J�n���Ɏ��s����郁�\�b�h�B
     * 
     * @param we ���[�N�C�x���g
     */
    public void workStarted(WorkEvent we) {
        printSimpleLog("Work started: ", getWork(we.getWorkItem()));
    }

    /**
     * �ȒP�ȃ��O�o�́B
     * 
     * @param message ���O���b�Z�[�W
     * @param object �o�̓I�u�W�F�N�g
     */
    private void printSimpleLog(String message, Object object) {
        if (log.isDebugEnabled()) {
            StringBuilder builder = new StringBuilder(message);
            builder.append("[");
            builder.append(object);
            builder.append("]");
            log.debug(builder.toString());
        }
    }

}
