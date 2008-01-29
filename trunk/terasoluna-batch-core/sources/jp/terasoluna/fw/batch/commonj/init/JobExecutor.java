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

package jp.terasoluna.fw.batch.commonj.init;

import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.init.JobInfo;
import jp.terasoluna.fw.batch.init.JobRequestInfo;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import commonj.work.Work;

/**
 * CommonJ�p�W���u�N���N���X�B<br>
 * �X���b�h�Ǘ������[�N�}�l�[�W���ɐݒ������B<br>
 * commonj.worker.Work��implment���邱�ƂŁA���[�N�}�l�[�W������̋N���ɑΉ�����B
 * 
 * 
 */
public class JobExecutor extends
        jp.terasoluna.fw.batch.springsupport.init.JobExecutor
        implements Work {

    /**
     * CommonJ�pBean��`�t�@�C�����B
     */
    private static final String COMMONJ_DEFINITION_NAME 
            = "/common/WorkManagerTaskContext-batch.xml";

    /**
     * �W���u�˗����B
     */
    private JobRequestInfo jobInfo = null;

    /**
     * �W���u�X�e�[�^�X�B
     */
    protected JobStatus jobStatus = null;

    /**
     * �R���X�g���N�^�B
     * 
     * <p>
     * <code>FrameworkFactory</code> �������������s���B
     * </p>
     * 
     */
    protected JobExecutor() {
        super();
    }

    /**
     * �R���X�g���N�^�B(�񓯊��W���u�N���p)
     * 
     * <p>
     * <code>FrameworkFactory</code> �������������s���B
     * </p>
     * 
     * @param beanFileName Bean��`�t�@�C����
     */
    protected JobExecutor(String[] beanFileName) {
        super(beanFileName);
    }

    /**
     * �W���u�����s����B<BR>
     * �W���u�˗�����p���Ďw�肳�ꂽ�W���u���N������B<BR>
     * 
     * @param jobInfo �W���u�˗����
     * @return �W���u���s����
     */
    @Override
    protected JobStatus execute(JobInfo jobInfo) {
        return super.execute(jobInfo);
    }

    /**
     * �W���u����<code>BeanFactory</code>���擾���郁�\�b�h�B<BR>
     * �������ꂽ<code>BeanFactory</code>�̓L���b�V�����ė��p�����B
     * 
     * @param jobInfo �W���u�˗����
     * @return �W���u����<code>BeanFactory</code>
     */
    @Override
    protected synchronized ConfigurableApplicationContext getJobBeanFactory(
            JobInfo jobInfo) {
        ConfigurableApplicationContext jobFactory = null;
        if (isAsync()) {
            jobFactory = super.getJobBeanFactory(jobInfo);
        } else {
            jobFactory = new ClassPathXmlApplicationContext(new String[] {
                    jobInfo.getJobDiscriptorPath(),
                    DATA_ACCESS_CONTEXT_DEFINITION_NAME,
                    COMMONJ_DEFINITION_NAME, VALIDATE_DEFINITION_NAME }, this
                    .getFrameworkFactory());
        }

        return jobFactory;
    }

    /**
     * �W���u�˗����̎擾�B
     * 
     * @return jobInfo �W���u�˗����
     */
    public JobRequestInfo getJobInfo() {
        return jobInfo;
    }

    /**
     * �W���u�˗����̐ݒ�B
     * 
     * @param jobInfo �W���u�˗����
     */
    public void setJobInfo(JobRequestInfo jobInfo) {
        this.jobInfo = jobInfo;
    }

    /**
     * �W���u�X�e�[�^�X�̎擾�B
     * 
     * @return jobStatus �W���u�X�e�[�^�X
     */
    public JobStatus getJobStatus() {
        return jobStatus;
    }

    /**
     * Work�I�u�W�F�N�g�̃��C�t�X�^�C�������肷��B<br>
     * false��Ԃ��F���̏ꍇ�A��f�[�����^��Work�I�u�W�F�N�g�ƂȂ�B 
     * ���I�u�W�F�N�g�͌Ăяo�����̃T�[�u���b�g�̃��N�G�X�g������
     * EJB�R���|�[�l���g�̃r�W�l�X�E���\�b�h�������I������܂ő�������B<br>
     * true��Ԃ��F���̏ꍇ�A�f�[�����^��Work�I�u�W�F�N�g�ƂȂ�B<br>
     * ���I�u�W�F�N�g�́A�Ăяo������Java EE�R���|�[�l���g�̃��C�t�E�T�C�N��
     * �i�T�[�u���b�g�̃��N�G�X�g������EJB�R���|�[�l���g�̃r�W�l�X�E���\�b�h�����j
     * �Ƃ� �֌W�Ȃ��������邱�Ƃ��ł���B<br>
     * �������A�i�����͂Ȃ����߁A�A�v���P�[�V�����E�T�[�o���I������ƁA
     * ���I�u�W�F�N�g�͏�������B<br>
     * �Ȃ��A���\�b�hisDamon��true��Ԃ��ꍇ�AWorkManager�I�u�W�F�N�g���Ǘ�����B
     * �X���b�h�E�v�[���̃X���b�h�E�I�u�W�F�N�g�͎g�p����Ȃ��B<br>
     * 
     * @return Work�I�u�W�F�N�g�̃��C�t�X�^�C��
     * 
     * @see commonj.work.Work#isDaemon()
     */
    public boolean isDaemon() {
        return false;
    }

    /**
     * ���[�N�̏I�����̏����B
     * 
     * @see commonj.work.Work#release()
     */
    public void release() {
    }

    /**
     * �񓯊�������s���s���B
     */
    public void run() {
        try {
            jobStatus = this.execute(jobInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
