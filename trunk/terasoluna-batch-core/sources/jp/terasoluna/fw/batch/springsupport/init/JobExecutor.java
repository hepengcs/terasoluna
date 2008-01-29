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

package jp.terasoluna.fw.batch.springsupport.init;

import java.util.HashMap;
import java.util.Map;

import jp.terasoluna.fw.batch.core.JobException;
import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.core.JobStatusSummarizer;
import jp.terasoluna.fw.batch.core.SupportProcessor;
import jp.terasoluna.fw.batch.core.WorkUnit;
import jp.terasoluna.fw.batch.core.Workable;
import jp.terasoluna.fw.batch.init.AbstractJobControlInfo;
import jp.terasoluna.fw.batch.init.EndFileChecker;
import jp.terasoluna.fw.batch.init.JobInfo;
import jp.terasoluna.fw.batch.openapi.JobContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * �W���u�N���p���C���N���X�B<BR>
 * �W���u�N���O��<code>JobBeanFactory</code>�̐�����j���������s���B<BR>
 * �������ꂽ<code>JobBeanFactory</code>�̓L���b�V�����ꓯ���W���u�����s���ꂽ
 * �ꍇ�� �L���b�V������<code>JobBeanFactory</code>��Ԃ��B
 * 
 */
public class JobExecutor implements DisposableBean,
        Workable<AbstractJobControlInfo> {

    /**
     * ���O�C���X�^���X�B
     */
    private static Log log = LogFactory.getLog(JobExecutor.class);

    /**
     * �t���[�����[�NBean��`�t�@�C�����B
     */
    private static final String FRAMEWORK_BEAN_DEFINITION_NAME = 
        "/common/FrameworkBean.xml";
    
    /**
     * �f�[�^�A�N�Z�X�pBean��`�t�@�C�����B
     */
    protected static final String DATA_ACCESS_CONTEXT_DEFINITION_NAME = 
        "/common/dataAccessContext-batch.xml";

    /**
     * �X���b�h�v�[���pBean��`�t�@�C�����B
     */
    private static final String THREAD_POOL_DEFINITION_NAME = 
        "/common/ThreadPoolContext-batch.xml";

    /**
     * �X���b�h�v�[���pBean��`�t�@�C�����B
     */
    protected static final String VALIDATE_DEFINITION_NAME = 
        "/common/ValidationContext-batch.xml";

    /**
     * �L���b�V���L���pBean���B
     */
    protected static final String USECACHE_NAME = "useCache";
    
    /**
     * �W���u�R���e�L�X�gBean���B
     */
    private static final String JOBCONTEXT_NAME = "jobContext";
    
    /**
     * �W���u�}�l�[�W��Bean���B
     */
    private static final String JOBMANAGER_NAME = "jobManager";
    
    /**
     * �W���u�󋵗pBean���B
     */
    protected static final String JOBSTATUS_NAME = "JobStatus";

    /**
     * ���j�^�[�p�W���u�󋵗pBean���B
     */
    protected static final String MONITORABLE_JOBSTATUS_NAME = 
        "MonitorableJobStatus";

    /**
     * �W���u�I���Ď��pBean���B
     */
    private static final String ENDFILECHECKER_NAME = "endFileChecker";
    
    /**
     * �W���u�I���Ď��pBean���B
     */
    protected static final String USE_MONITORABLE = "useMonitorable";
    
    /**
     * ������Ԃ̃T�}���C�U�B
     */
    protected static final String JOBSTATUS_SUMMARIZER = "JobStatusSummarizer";

    /**
     * �f�t�H���g�I���R�[�h�B
     */
    private static final String EXITCODE_MAP = "exitCodeMap";

    /**
     * �t���[�����[�NBeanFactory�B
     */
    private ConfigurableApplicationContext frameworkFactory = null;

    /**
     * �W���uBeanFactory�̃L���b�V���pMap�B
     */
    private Map<String, ConfigurableApplicationContext> jobFactoryMap = 
        new HashMap<String, ConfigurableApplicationContext>();

    /**
     * �񓯊��N���L���t���O�B
     */
    private JobContext.START_TYPE async = JobContext.START_TYPE.SYNC;

    /**
     * �W���u�O�����p�C���X�^���X�B
     */
    private SupportProcessor preJobProcessor = null;

    /**
     * �W���u�㏈���p�C���X�^���X�B
     */
    private SupportProcessor postJobProcessor = null;

    /**
     * �f�t�H���g�̃W���u�I���R�[�h�̃}�b�v�B
     */
    private Map<String, String> jobExitCodeMap = null;
    
    /**
     * �W���u�p�����[�^�̋�؂蕶���B
     */
    private String jobParametersSplitStr = null;
    
    /**
     * �R���X�g���N�^�B
     * 
     * <p><code>FrameworkFactory</code> �������������s���B</p>
     * 
     */
    protected JobExecutor() {
        frameworkFactory = new ClassPathXmlApplicationContext(
                FRAMEWORK_BEAN_DEFINITION_NAME);
        initDefaultJobExitCodeMap();
    }

    /**
     * �R���X�g���N�^�B(�񓯊��W���u�N���p)
     * 
     * <p><code>FrameworkFactory</code> �������������s���B</p>
     * 
     * @param beanFileName Bean��`�t�@�C��
     */
    protected JobExecutor(String[] beanFileName) {
        frameworkFactory = new ClassPathXmlApplicationContext(beanFileName);
        initDefaultJobExitCodeMap();
    }
    
    /**
     * �W���u����<code>BeanFactory</code>���擾���郁�\�b�h�B<BR>
     * �������ꂽ<code>BeanFactory</code>�̓L���b�V�����ė��p�����B
     * 
     * @param jobInfo �W���u�˗����
     * @return �W���u����<code>BeanFactory</code>
     */
    protected synchronized ConfigurableApplicationContext getJobBeanFactory(
            JobInfo jobInfo) {
        ConfigurableApplicationContext jobFactory = null;
        if (isAsync()) {
            // �L���b�V�����ꂽ��񂪂��邩�m�F
            if (jobFactoryMap.containsKey(jobInfo.getJobDiscriptorPath())) {
                if (log.isDebugEnabled()) {
                    log.debug("get the cashed JobBeanFactory");
                }
                jobFactory = jobFactoryMap.get(jobInfo.getJobDiscriptorPath());
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("get the newly created JobBeanFactory");
                }
                jobFactory = new ClassPathXmlApplicationContext(
                        new String[] { jobInfo.getJobDiscriptorPath() },
                        frameworkFactory);
                if ((Boolean) jobFactory.getBean(USECACHE_NAME)) {
                    // �W���uBeanFactory�̃L���b�V��
                    jobFactoryMap.put(jobInfo.getJobDiscriptorPath(),
                            jobFactory);
                }
            }
        } else {
            jobFactory = new ClassPathXmlApplicationContext(new String[] {
                    jobInfo.getJobDiscriptorPath(),
                    DATA_ACCESS_CONTEXT_DEFINITION_NAME,
                    THREAD_POOL_DEFINITION_NAME, VALIDATE_DEFINITION_NAME },
                    frameworkFactory);
        }
        return jobFactory;
    }

    /**
     * �񓯊��W���u�N���ł��邩��]������B
     * 
     * @return �񓯊��W���u�̋N���ł����<code>true</code>��Ԃ��B
     */
    protected boolean isAsync() {
        return async == JobContext.START_TYPE.ASYNC;
    }

    /**
     * �W���u�}�l�[�W�����擾����B
     * 
     * @param jobFactory �W���u����<code>BeanFactory</code>
     * @return �W���u�}�l�[�W��
     */
    @SuppressWarnings("unchecked")
    protected Workable<WorkUnit> getJobManager(
            ConfigurableApplicationContext jobFactory) {
        Workable<WorkUnit> jobManager = (Workable<WorkUnit>) jobFactory
                .getBean(JOBMANAGER_NAME);
        return jobManager;
    }

    /**
     * �W���u�R���e�L�X�g�̎擾�B<BR>
     * �W���u�R���e�L�X�g���擾���A�����l��ݒ肷��B
     * 
     * @param jobFactory �W���u����<code>BeanFactory</code>
     * @param jobInfo �W���u�˗����
     * @return �W���u�R���e�L�X�g
     */
    protected JobContext getJobContext(
            ConfigurableApplicationContext jobFactory, JobInfo jobInfo) {
        JobContext jobContext = (JobContext) jobFactory
                .getBean(JOBCONTEXT_NAME);
        jobContext.setStartType(async);
        jobContext.setPartitionNo(-1);
        jobContext.setJobId(jobInfo.getJobId());
        jobContext.setJobRequestNo(jobInfo.getJobRequestNo());
        jobContext.setParameter(jobInfo.getJobParameters());
        return jobContext;
    }

    /**
     * �W���u������Ԋi�[�p<code>JobStatus</code>���擾����B<BR>
     * <code>JobStatus</code>���擾���A�����l��ݒ肷��B
     * 
     * @param jobFactory �W���u����<code>BeanFactory</code>
     * @param jobInfo �W���u�˗����
     * @return �W���u������Ԋi�[�p<code>JobStatus</code>
     */
    protected JobStatus getJobStatus(ConfigurableApplicationContext jobFactory,
            JobInfo jobInfo) {
        JobStatus jobStatus = null;
        if ((Boolean) jobFactory.getBean(USE_MONITORABLE)) {
            jobStatus = (JobStatus) jobFactory.getBean(
                    MONITORABLE_JOBSTATUS_NAME);
        } else {
            jobStatus = (JobStatus) jobFactory.getBean(JOBSTATUS_NAME);
        }

        jobStatus.setJobState(JobStatus.STATE.STARTED);
        jobStatus.setJobId(jobInfo.getJobId());
        jobStatus.setJobRequestNo(jobInfo.getJobRequestNo());
        return jobStatus;
    }

    /**
     * <code>WorkUnit</code>�𐶐����A�W���u�R���e�L�X�g��ݒ肷��B<BR>
     * 
     * @param jobContext �W���u�R���e�L�X�g
     * @return <code>WorkUnit</code>
     */
    protected WorkUnit getWorkUnit(JobContext jobContext) {
        WorkUnit rootWorkQueueElement = new WorkUnit() {

            private JobContext workUnitJobContext;

            public boolean isEndMark() {
                return false;
            }

            public JobContext getJobContext() {
                return workUnitJobContext;
            }

            public void setJobContext(JobContext jobContext) {
                this.workUnitJobContext = jobContext;
            }
        };
        rootWorkQueueElement.setJobContext(jobContext);
        return rootWorkQueueElement;
    }

    /**
     * �I���t�@�C���Ď��X���b�h�̓o�^���s���B
     * 
     * @param jobStatus �W���u�̏������
     * @param jobContext �W���u�R���e�L�X�g
     */
    protected void initEndFileChecker(JobStatus jobStatus,
            JobContext jobContext) {
        EndFileChecker endFileChecker
            = (EndFileChecker) frameworkFactory.getBean(ENDFILECHECKER_NAME);
        if (endFileChecker != null) {
            endFileChecker.addParentJobStatus(jobStatus, jobContext.getJobId(),
                    jobContext.getJobRequestNo());
        }
    }

    /**
     * �W���u�����s����B<BR>
     * �W���u�˗�����p���Ďw�肳�ꂽ�W���u���N������B<BR>
     * 
     * @param jobInfo �W���u�˗����
     * @return �W���u���s����
     */
    protected JobStatus execute(JobInfo jobInfo) {
        //�J�n���O�o��
        printStartLog(jobInfo);
        
        //�W���u�N�����̃`�F�b�N
        checkJobInfo(jobInfo);
        
        // �W���uBeanFactory
        ConfigurableApplicationContext jobFactory = getJobBeanFactory(jobInfo);

        // �}�l�W���[�擾
        Workable<WorkUnit> jobManager = getJobManager(jobFactory);

        // �W���u�R���e�L�X�g������
        JobContext jobContext = getJobContext(jobFactory, jobInfo);
        
        // �W���u��Ԃ̏�����
        JobStatus jobStatus = getJobStatus(jobFactory, jobInfo);

        // �I���t�@�C���Ď��X���b�h�ɓo�^
        initEndFileChecker(jobStatus, jobContext);
        
        WorkUnit rootWorkQueueElement = getWorkUnit(jobContext);
        
        jobManager.work(rootWorkQueueElement, jobStatus);

        ((JobStatusSummarizer) jobFactory.getBean(JOBSTATUS_SUMMARIZER))
                .summarize(jobStatus);

        setDefaultJobExitCode(jobStatus);
        
        if (!(Boolean) jobFactory.getBean(USECACHE_NAME)) {
            jobFactory.close();
        }

        //�W���u�̏I�����O�o��
        printEndLog(jobStatus);
        
        return jobStatus;
    }

    /**
     * �W���u�̊J�n���O���o�͂���B
     * @param jobInfo �W���u�˗����
     */
    protected void printStartLog(JobInfo jobInfo) {
        if (log.isDebugEnabled()) {
            StringBuilder logStr = new StringBuilder();
            logStr.append("�ySTART�z");
            logStr.append(" [jobId=");
            logStr.append(jobInfo.getJobId());
            logStr.append("]");
            logStr.append(" [jobRequestNo=");
            logStr.append(jobInfo.getJobRequestNo());
            logStr.append("]");
            logStr.append(" [StartType=");
            logStr.append(async);
            logStr.append("]");
            log.debug(logStr.toString());
        }
    }

    /**
     * �W���u�̊J�n���O���o�͂���B
     * @param jobStatus �W���u�̏������
     */
    protected void printEndLog(JobStatus jobStatus) {
        if (log.isDebugEnabled()) {
            StringBuilder logStr = new StringBuilder();
            logStr.append("�y END �z");
            logStr.append(" [jobId=");
            logStr.append(jobStatus.getJobId());
            logStr.append("]");
            logStr.append(" [jobRequestNo=");
            logStr.append(jobStatus.getJobRequestNo());
            logStr.append("]");
            logStr.append(" [StartType=");
            logStr.append(async);
            logStr.append("]");
            logStr.append(" [jobExitCode=");
            logStr.append(jobStatus.getJobExitCode());
            logStr.append("]");
            log.debug(logStr.toString());
        }
    }

    /**
     * �p����������ł��邩���`�F�b�N����B
     * 
     * @param jobInfo �`�F�b�N�Ώۂ̕�����
     */
    private void checkJobInfo(JobInfo jobInfo) {
        for (char c : jobInfo.getJobId().toCharArray()) {
            if (!(('0' <= c && c <= '9')
                    || ('a' <= c && c <= 'z')
                    || ('A' <= c && c <= 'Z')
                    || c == '-'
                    || c == '_')
                    ) {
                throw new JobException("JobID should use only an "
                        + "alphanumeric character");
            } 
        }
    }


    /**
     * �W���u�I���R�[�h��ݒ肷��B
     * �I���R�[�h�̐ݒ肪Null�̏ꍇ�ABean��`�̏�ԕʏI���R�[�h��
     * �f�t�H���g�I���R�[�h�Ƃ��Đݒ肷��B
     * 
     * @param jobStatus
     *            �����Ώۂ̏�����
     */
    protected void setDefaultJobExitCode(JobStatus jobStatus) {
        if (jobStatus.getJobExitCode() != null) {
            return;
        }
        String code = jobExitCodeMap.get(jobStatus.getJobStateStr());
        jobStatus.setJobExitCode(Integer.valueOf(code));
    }

    /**
     * �W���u�I���R�[�h�̃}�b�v������������B
     *
     */
    @SuppressWarnings("unchecked")
    private void initDefaultJobExitCodeMap() {
        jobExitCodeMap = 
            (Map<String, String>) frameworkFactory.getBean(EXITCODE_MAP);

        // �I���R�[�h���ݒ肳��Ė����ꍇ�̓G���[�Ƃ���B
        if (jobExitCodeMap == null || jobExitCodeMap.size() < 0) {
            throw new JobException("There is no setup of Default exitCodeMap");
        }
    }
    
    /**
     * �񓯊��^�W���u�N�����̃��[�J���\�b�h�B
     * 
     * @param jobInfo �W���u�˗����
     * @param jobStatus �W���u�X�e�[�^�X
     */
    public void work(AbstractJobControlInfo jobInfo, JobStatus jobStatus) {
        jobInfo.setJobParametersSplitStr(jobParametersSplitStr);
        JobStatus workJobStatus = jobStatus.getChild(new JobContext());
        workJobStatus.setJobState(JobStatus.STATE.STARTED);

        AsyncJobContext asyncJobContext = new AsyncJobContext(jobInfo);

        // �W���u�J�n�X�V����
        preJobProcessor.process(asyncJobContext, workJobStatus);

        if (workJobStatus.getJobState() != JobStatus.STATE.STARTED) {
            return;
        }

        JobStatus resultJobStatus = null;
        try {
            resultJobStatus = execute(jobInfo);
        } catch (RuntimeException e) {
            log.error(e.getMessage(), e);
            resultJobStatus = new JobStatus();
            resultJobStatus.setJobState(JobStatus.STATE.ENDING_ABNORMALLY);
            
            String exitCode = 
                jobExitCodeMap.get(resultJobStatus.getJobStateStr());
            resultJobStatus.setJobExitCode(Integer.valueOf(exitCode));
            
        }
        
        // �W���u�������ʂ̐ݒ�
        jobInfo.setJobExitCode(
                String.valueOf(resultJobStatus.getJobExitCode()));
        // �W���u�I����Ԑݒ�
        jobInfo.setJobState(String.valueOf(
                resultJobStatus.getJobState().ordinal()));
        
        // �W���u���s����DB���f
        postJobProcessor.process(asyncJobContext, workJobStatus);
    }

    /**
     * �쐬����<code>BeanFactory</code>�̔j���������s���B<BR>
     * �L���b�V������<code>JobBeanFactory</code>�y��<code>FrameworkBeanFactory
     * </code>��j������B
     * 
     */
    public void destroy() {
        // �W���uBeanFactroy�̏I��
        for (ConfigurableApplicationContext context : jobFactoryMap.values()) {
            context.close();
        }
        // �t���[�����[�NBeanFactroy�̏I��
        if (frameworkFactory != null) {
            frameworkFactory.close();
        }
    }

    /**
     * �񓯊��N���L���t���O��ݒ肷��B<BR>
     * Spring����̒l���C���W�F�N�V��������B
     * 
     * @param async �񓯊��N���L���t���O
     */
    public void setAsync(boolean async) {
        if (async) {
            this.async = JobContext.START_TYPE.ASYNC;
        } else {
            this.async = JobContext.START_TYPE.SYNC;
        }
    }

    /**
     * �W���u�N���㏈����ݒ肷��B
     * 
     * @param preJobProcessor �W���u�N���O����
     */
    public void setPreJobProcessor(SupportProcessor preJobProcessor) {
        this.preJobProcessor = preJobProcessor;
    }

    /**
     * �W���u�N���O������ݒ肷��B
     * 
     * @param postJobProcessor �W���u�N���O����
     */
    public void setPostJobProcessor(SupportProcessor postJobProcessor) {
        this.postJobProcessor = postJobProcessor;
    }

    /**
     * �W���u�p�����[�^�̋�؂蕶����ݒ肷��B
     * 
     * @param jobParametersSplitStr �W���u�p�����[�^�̋�؂蕶��
     */
    public void setJobParametersSplitStr(String jobParametersSplitStr) {
        this.jobParametersSplitStr = jobParametersSplitStr;
    }
    
    /**
     * �t���[�����[�NBeanFactory�̎擾
     * 
     * @return frameworkFactory �t���[�����[�NBeanFactory
     */
    protected ConfigurableApplicationContext getFrameworkFactory() {
        return frameworkFactory;
    }
}
