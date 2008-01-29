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

package jp.terasoluna.fw.batch.monitor;

import java.lang.management.ManagementFactory;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.ReflectionException;

import jp.terasoluna.fw.batch.core.JobStatus;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �W���u��JMX�ŊĎ����邽�߂̓o�^�N���X�B<br>
 * 
 * <p>
 * jp.terasoluna.fw.batch.monitor.MonitorableJobStatusMBean����������
 *�N���X���Ď��ΏۂƂȂ�B
 * </p>
 * <strong>�ݒ��</strong><br>
 * �{�@�\���g�p����ꍇ�A�t���[�����[�NBean��`�t�@�C���Ɉȉ��̓��e��ݒ肷��B
 * <br>�ȉ��̗�ł͊Ď����s���A�Ď�����W���u�i�q�W���u�܂ށj�̏���l��20�ł���B
 * <br>
 * 
 * <pre>
 *    &lt;bean id=&quot;MBeanRegister&quot;
 *        class=&quot;jp.terasoluna.fw.batch.monitor.MBeanRegister&quot;&gt;
 *        &lt;property name=&quot;jmxEnable&quot;&gt;
 *            &lt;value&gt;true&lt;/value&gt;
 *        &lt;/property&gt;
 *        &lt;property name=&quot;manageableJobSize&quot;&gt;
 *            &lt;value&gt;20&lt;/value&gt;
 *        &lt;/property&gt;
 *    &lt;/bean&gt;
 * </pre>
 *
 * <strong>���</strong><br>
 * jmxEnable�����ɊĎ����s���ꍇ��true�A�s��Ȃ��ꍇ��false��ݒ肷��B<br>
 * manageableJobSize�����ɊĎ�����W���u�̏���l��ݒ肷��B<br>
 * 
 */
public class MBeanRegister {

    /**
     * ���O�C���X�^���X�B
     */
    private Log log = LogFactory.getLog(MBeanRegister.class);

    /**
     * MBeanServer�ɓo�^����MBean�̏���l�B
     */
    private int manageableJobSize = 0;

    /**
     * MBean �𑀍삷�邽�߂̃C���X�^���X�B
     */
    private MBeanServer server;

    /**
     * MBeanServer�ɓo�^���ꂽMBean����ێ�����}�b�v�B
     */
    private Map<String, JobStatus> registerMBeanJobStatusMap;

    /**
     * �W���u�o�^���̃J�E���g�B
     */
    private int mbeanCount = 0;

    /**
     * MBeanServer�ɓo�^���ꂽ�e�W���u��MBean����ێ�����}�b�v�B
     */
    private Queue<String> registerParentObjectName = null;
    
    /**
     * MBeanServer�ɓo�^���ꂽ�q�W���u��MBean����ێ�����}�b�v�B
     */
    private Queue<String> registerChildObjectName = null;
        
    /**
     * �R���X�g���N�^�B
     *
     */
    public MBeanRegister() {
        server = ManagementFactory.getPlatformMBeanServer();
        registerMBeanJobStatusMap = new ConcurrentHashMap<String, JobStatus>();
        
        registerParentObjectName = new ConcurrentLinkedQueue<String>();
        registerChildObjectName  = new ConcurrentLinkedQueue<String>();
    }

    /**
     * MBeanServer��JobResult(mbean)��o�^����B
     *
     * @param jobStatus �W���u�X�e�[�^�X
     */
    public void registerMBean(JobStatus jobStatus) {

        if (jobStatus.getJobId() == null) {
            return;
        }
        
        try {
            // �o�^������𒴂����ꍇ�͓o�^����
            if (mbeanCount >= manageableJobSize) {
                unregisterMBean();
            }

            // �o�^�����擾
            String name = getRegistName(jobStatus);

            if (((ConcurrentMap<String, JobStatus>) registerMBeanJobStatusMap)
                    .putIfAbsent(name, jobStatus) == null) {
                server.registerMBean(jobStatus, ObjectName.getInstance(name));
                mbeanCount++;
            }

            if (log.isDebugEnabled()) {
                log.debug(name + " register");
            }

        } catch (MalformedObjectNameException e) {
            log.error(e.getMessage(), e);
        } catch (MBeanRegistrationException e) {
            log.error(e.getMessage(), e);
        } catch (NotCompliantMBeanException e) {
            log.error(e.getMessage(), e);
        } catch (InstanceAlreadyExistsException e) {
            log.error(e.getMessage(), e);
        } catch (AttributeNotFoundException e) {
            log.error(e.getMessage(), e);
        } catch (InstanceNotFoundException e) {
            log.error(e.getMessage(), e);
        } catch (MBeanException e) {
            log.error(e.getMessage(), e);
        } catch (ReflectionException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * MBean�o�^���𐶐����A�ԋp����B
     * 
     * <p>
     * �e�W���u�̓o�^����<br>
     * �uJOB:name=�W���uID�v �܂���<br>
     * �uJOB:name=�W���uID_�W���u���N�G�X�gNo�v�i�W���u���N�G�X�gNo���󕶎��ȊO
     *  �̏ꍇ�j�ł���A �q�W���u�̓o�^����<br>
     * �u�e�W���u�̓o�^��,name2=�����L�[�v �ƂȂ�B
     * </p>
     * 
     * @param jobStatus
     *            �W���u�X�e�[�^�X
     * @return MBean�o�^��
     */
    private String getRegistName(JobStatus jobStatus) {
    
        StringBuilder name = new StringBuilder();
        name.append("JOB:name=");
        name.append(jobStatus.getJobId());

        if (!"".equals(jobStatus.getJobRequestNo())) {
            name.append("_");
            name.append(jobStatus.getJobRequestNo());
        }

        if (!registerParentObjectName.contains(name.toString())) {
            registerParentObjectName.add(name.toString());
        }

        // �q�W���u�̏ꍇ�͓o�^���̊K�w�𕪂���
        if (jobStatus.getPartitionNo() != -1) {
            name.append(",name2=");
            name.append(jobStatus.getPartitionKey().replaceAll(":", ""));
            registerChildObjectName.add(name.toString());
        }
        
        return name.toString();
    }

    /**
     * MBean�̓o�^����������B
     * 
     * <p>
     * �o�^����<code>manageableJobSize</code>�𒴂���ꍇ�A
     * �o�^�����Â��W���u��Ԃ��I���̂��̂����������B<br>
     * �I����Ԃ̃W���u�������ꍇ�́A�I����ԂɊ֌W�Ȃ�
     * �o�^�����Â��q�W���u�����������B
     * </p>
     * 
     * @throws NullPointerException
     *         ObjectName�̈�����null�̏ꍇ
     * @throws MalformedObjectNameException
     *         ObjectName�̈����̕�����̌`�����������Ȃ��ꍇ
     * @throws ReflectionException 
     *         �ݒ胁�\�b�h�̌Ăяo�����ɃX���[�����
     *         java.lang.Exception �����b�v����ꍇ
     * @throws MBeanException 
     *         MBean�̎擾���\�b�h�ɂ���ăX���[������O�����b�v����ꍇ 
     * @throws InstanceNotFoundException
     *         �w�肳�ꂽMBean��MBean�T�[�o�ɓo�^����Ă��Ȃ��ꍇ
     * @throws AttributeNotFoundException 
     *         MBean �̎w�肳�ꂽ�������A�N�Z�X�s�\�ł���ꍇ
     *
     */
    private void unregisterMBean() throws MalformedObjectNameException,
            NullPointerException, AttributeNotFoundException,
            InstanceNotFoundException, MBeanException, ReflectionException {

        if (registerMBeanJobStatusMap.isEmpty()) {
            return;
        }

        // �o�^���Ȃ��Ȃ� �܂��� �폜����������܂Ń��[�v����
        LOOP : while (mbeanCount > 0) {

            // �q�W���u�ŏI����Ԃ̂��̂����邩
            for (String childName : registerChildObjectName) {
                JobStatus jobStatus = registerMBeanJobStatusMap.get(childName);
                if (jobStatus != null && jobStatus.getJobState().isEndStatus())
                {
                    if (deleteMBean(childName)) {
                        registerChildObjectName.remove(childName);
                        break LOOP;
                    }
                }
            }

            // �e�W���u�ŏI����Ԃ̂��̂����邩
            for (String parentName : registerParentObjectName) {
                JobStatus jobStatus = registerMBeanJobStatusMap.get(parentName);
                if (jobStatus != null && jobStatus.getJobState().isEndStatus())
                {
                    if (deleteMBean(parentName)) {
                        registerParentObjectName.remove(parentName);
                        break LOOP;
                    }
                }
            }

            // �q�W���u�ŏI����Ԃ̂��̂��Ȃ���ΌÂ��q�W���u���폜
            if (!registerChildObjectName.isEmpty()) {
                if (deleteMBean(registerChildObjectName.poll())) {
                    break;
                }
            }

            // �e�W���u�ŏI����Ԃ̂��̂��Ȃ���ΌÂ��e�W���u���폜
            if (!registerParentObjectName.isEmpty()) {
                if (deleteMBean(registerParentObjectName.poll())) {
                    break;
                }
            }
        }
    }


    /**
     * MBeanServer����MBean���폜����B
     * 
     * @param registerName
     *            MBean�o�^��
     * @return �폜�����Ȃ�true
     * @throws InstanceNotFoundException �w�肳�ꂽMBean�����݂��Ȃ��ꍇ�̗�O
     * @throws MBeanRegistrationException MBeanRegistration�Ŕ���������O
     * @throws MalformedObjectNameException
     *           ������̌`�����L����ObjectName�ɑΉ����Ă��Ȃ��ꍇ�̗�O
     * @throws NullPointerException NullPointerException
     */
    private boolean deleteMBean(String registerName)
            throws InstanceNotFoundException, MBeanRegistrationException,
            MalformedObjectNameException, NullPointerException {

        if (registerName == null) {
            return false;
        }

        ObjectName objectName = new ObjectName(registerName);

        if (server.isRegistered(objectName)) {
            server.unregisterMBean(objectName);
            mbeanCount--;
            registerMBeanJobStatusMap.remove(registerName);
            if (log.isDebugEnabled()) {
                log.debug(registerName + " unregister");
            }
            return true;
        }

        return false;
    }
    
    /**
     * MBeanServer�ւ̓o�^����l��ݒ肷��B
     * 
     * @param manageableJobSize
     *            �o�^����l
     */
    public void setManageableJobSize(int manageableJobSize) {
        this.manageableJobSize = manageableJobSize;
    }
}
