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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jp.terasoluna.fw.batch.openapi.JobContext;

/**
 * �`�����N�N���X�B
 * 
 * <p>�r�W�l�X���W�b�N�̓��̓f�[�^�ƂȂ�I�u�W�F�N�g����萔��
 * �i���邢�͓���̃��[���ɏ]���āj�ێ�����R���e�i�N���X�ł���A
 * {@link JobWorker} �̓��͂ƂȂ�B</p>
 * 
 * <p>�W���u�����s����ۂ̏����P�ʂł���A�g�����U�N�V������A
 * �o�b�`�X�V�v���Z�b�T{@link BatchUpdateProcessor} �ɂ��o�b�`�X�V�Ȃǂ̒P�ʂ�
 * �Ȃ�B</p>
 * 
 * <p>�`�����N���f�[�^���Ŏw�肷�鏈�����f���̃W���u�̏ꍇ�ɂ́A�W���uBean��`
 * �t�@�C���ɂ����Ĉȉ��̂悤�Ȑݒ���s�����ƂŃ`�����N�T�C�Y�̎w����s�����Ƃ�
 * �ł���B</p>
 * 
 * <p><strong>�ݒ��</strong></p>
 * <pre><code>
 *  &lt;!-- �`�����N�T�C�Y�̎w�� --&gt;
 *  &lt;bean id="chunkSize" class="java.lang.Integer"&gt;
 *      &lt;constructor-arg>&lt;value>50&lt;/value&gt;&lt;/constructor-arg&gt;
 *  &lt;/bean&gt;
 * </code></pre>
 * 
 * <p>�W���uBean��`�t�@�C���ɂ����āA�`�����N�T�C�Y���w�肵�Ȃ������ꍇ�ɂ́A
 * �t���[�����[�NBean��`�t�@�C���ɐݒ肳��Ă���`�����N�T�C�Y���g�p�����B
 * </p>
 * 
 */
public class Chunk implements Iterable, WorkUnit {

    /**
     * �f�[�^���i�[���郊�X�g�B
     */
    private List<Object> data;

    /**
     * �W���u�R���e�N�X�g�B
     */
    private JobContext jobContext;
    
    /**
     * �R���X�g���N�^�B
     * �����œn���ꂽ�`�����N�T�C�Y��p���ăf�[�^���i�[���郊�X�g�I�u�W�F�N�g��
     * ����������B
     *
     * @param chunkSize �`�����N�T�C�Y
     * @param jobContext �W���u�R���e�N�X�g
     */
    public Chunk(int chunkSize, JobContext jobContext) {
        this.data = new ArrayList<Object>(chunkSize);
        this.jobContext = jobContext;
    }

    /**
     * �R���X�g���N�^�B
     */
    public Chunk() {
        this.data = new ArrayList<Object>();
        this.jobContext = null;
    }

    /**
     * �`�����N�T�C�Y�̃T�C�Y���擾����B
     *
     * @return �`�����N�T�C�Y
     */
    public int size() {
        return data.size();
    }

    /**
     * �`�����N�Ƀf�[�^��ǉ�����B
     *
     * @param obj �ǉ��Ώۂ̃f�[�^
     */
    public void add(Object obj) {
        data.add(obj);
    }

    /**
     * �`�����N�ŕێ�����Ă���f�[�^�̔����q��Ԃ��B
     *
     * @return �����q
     */
    public Iterator<Object> iterator() {
        return data.iterator();
    }
    
    /**
     * �`�����N�̏I�[�ł��邩��]������B
     *
     * @return �]������
     */
    public boolean isEndMark() {
        return false;
    }

    /**
     * �W���u�R���e�N�X�g���擾����B
     * 
     * @return ���̃`�����N�Ɋ֘A�t�����Ă���W���u�R���e�N�X�g
     */
    public JobContext getJobContext() {
        return jobContext;
    }

    /**
     * �W���u�R���e�N�X�g��ݒ肷��B
     * 
     * @param jobContext ���̃`�����N�Ɗ֘A�t����W���u�R���e�N�X�g
     */
    public void setJobContext(JobContext jobContext) {
        this.jobContext = jobContext;
    }
}
