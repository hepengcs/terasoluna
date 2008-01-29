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

import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.init.JobRequestInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �W���u�N���p�N���X�B</p>
 * 
 * <p>���̃N���X <code>main()</code> ���\�b�h�ŃW���u���N�����邱�Ƃ��ł���B</p>
 * 
 * <p>���̃N���X�ŃW���u���N������ۂɂ́A�ȉ��������Ŏw�肷��B</p>
 *
 * <ul>
 * <li>��P�����F�W���u<code>ID</code>�i�K�{�j</li>
 *   <p>���s�ΏۃW���u����ӂɎ��ʂ���W���uID���w�肷��B</p>
 * <li>��Q�����F�W���uBean��`�t�@�C���̑��΃p�X�i�K�{�j</li>
 *   <p>���s�ΏۃW���u�̏�񂪐ݒ肳��Ă���W���u<code>Bean</code>
 *   ��`�t�@�C���̑��΃p�X�B</p>
 *   <p>�W���uBean��`�t�@�C���̓N���X���[�_�œǂݍ��܂�邽�߁A�N���X�p�X�Ƃ���
 *   ���w�肷��B
 *   <p>���Ƃ��΁A�ubatchapps�v�Ƃ����t�H���_�z���ɃW���uBean��`�t�@�C�����u��
 *   ��Ă��āA�ubatchapps�v����̑��΃p�X���w�肷��B</p>
 *   <p>�w��̃W���uBean��`�t�@�C�������݂��Ȃ��ꍇ�̓G���[�Ƃ��ďI������B</p>
 * <li>��R�����ȍ~�F�p�����[�^�l<td>�i�C�Ӂj</li>
 *   <p>�w�肳�ꂽ�l�̓W���u�ݒ莞�Ɏw�肵���W���u�R���e�L�X�g�Ɋi�[�����B</p>
 *   <p>�����̑O�Ɂu-p�v���w�肷�邱�ƂŋN���W���u�̃W���u�v���Z�XID���w�肷��
 *   ���Ƃ��ł���B</p>
 * </ul>
 * 
 * <p>�N����</p>
 * <code>java jp.terasoluna....JobStarter 
 * JOB0001 UD001/JOB0001.xml PARM01 PARM02 PARM03 PARM04 -p POS0001</code>
 *
 * <p>�W���u�R���e�L�X�g������<p>
 * <pre><code>public class SampleJobParameter extends JobContext {
 *     private String company = null;
 *     private Date startDay = null;
 *     private Date endDay = null;
 *     private List&lt;String&gt; fileData = null;
 *     public void setParameter(String[] arg) {
 *         company = arg[0];
 *         startDay = DateFormat.getTimeInstance().parse(arg[1]);
 *         endDay = DateFormat.getTimeInstance().parse(arg[2]);
 *         Properties p = new Properties();
 *         FileInputStream fis = new FileInputStream(fileName);
 *         p.load(fis);
 *     }
 *     
 *     public String getCompany(){
 *         return company;
 *     }
 *     public Date getStartDay(){
 *            return startDay;
 *     }
 *     public Date getEndDay(){
 *         return endDay;
 *     }
 *     public List&lt;String&gt; getFileData(){
 *         return fileData;
 *     }
 *}</code></pre>
 * </ul>
 *
 */
public class JobStarter {

    /**
     * ���O�C���X�^���X�B
     */
    private static Log log = LogFactory.getLog(JobStarter.class);
    
    /**
     * �����W���u�N���pMain���\�b�h�B
     *
     * @param args 
     *            �N�����̈���<BR>
     *            ��P�����F�W���uID<BR>
     *            ��Q�����F�W���uBean��`�t�@�C���̑��΃p�X<BR>
     *            ��R�����ȍ~�F�W���u�R���e�L�X�g�i�[�p����<BR>
     *            -p�ȍ~�̈����̓W���u�v���Z�XID<BR>
     */
    public static void main(String[] args) {
        if (log.isDebugEnabled()) {
            log.debug("start Batch");
        }
        JobStarter jobStarter = new JobStarter();
        int exitCode = jobStarter.execute(args);

        if (log.isDebugEnabled()) {
            log.debug("end Batch");
        }
        System.exit(exitCode);
    }


    /**
     * �����Ŏw�肳�ꂽ�p�����[�^�����ɃW���u�����s����B
     *
     * @param args 
     *            �N�����̈���<BR>
     *            ��P�����F�W���uID<BR>
     *            ��Q�����F�W���uBean��`�t�@�C���̑��΃p�X<BR>
     *            ��R�����ȍ~�F�W���u�R���e�L�X�g�i�[�p����<BR>
     *            -p�ȍ~�̈����̓W���u�v���Z�XID<BR>
     * @return �W���u�I���R�[�h
     */
    protected int execute(String[] args) {
        int exitCode = 0;

        JobExecutor jobExecutor = new JobExecutor();

        JobStatus jobStatus = null;
        try {

            JobRequestInfo jobInfo = new JobRequestInfo(args);
            jobInfo.init();

            jobStatus = jobExecutor.execute(jobInfo);

            exitCode = jobStatus.getJobExitCode();
        } catch (RuntimeException e) {
            log.error(e.getMessage(), e);
            throw e;
        } finally {
            jobExecutor.destroy();
        }
        return exitCode;
    }
}
