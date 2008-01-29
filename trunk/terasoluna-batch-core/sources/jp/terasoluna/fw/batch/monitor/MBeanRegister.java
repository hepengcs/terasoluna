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
 * ジョブをJMXで監視するための登録クラス。<br>
 * 
 * <p>
 * jp.terasoluna.fw.batch.monitor.MonitorableJobStatusMBeanを実装した
 *クラスが監視対象となる。
 * </p>
 * <strong>設定例</strong><br>
 * 本機能を使用する場合、フレームワークBean定義ファイルに以下の内容を設定する。
 * <br>以下の例では監視を行い、監視するジョブ（子ジョブ含む）の上限値が20である。
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
 * <strong>解説</strong><br>
 * jmxEnable属性に監視を行う場合はtrue、行わない場合はfalseを設定する。<br>
 * manageableJobSize属性に監視するジョブの上限値を設定する。<br>
 * 
 */
public class MBeanRegister {

    /**
     * ログインスタンス。
     */
    private Log log = LogFactory.getLog(MBeanRegister.class);

    /**
     * MBeanServerに登録するMBeanの上限値。
     */
    private int manageableJobSize = 0;

    /**
     * MBean を操作するためのインスタンス。
     */
    private MBeanServer server;

    /**
     * MBeanServerに登録されたMBean名を保持するマップ。
     */
    private Map<String, JobStatus> registerMBeanJobStatusMap;

    /**
     * ジョブ登録数のカウント。
     */
    private int mbeanCount = 0;

    /**
     * MBeanServerに登録された親ジョブのMBean名を保持するマップ。
     */
    private Queue<String> registerParentObjectName = null;
    
    /**
     * MBeanServerに登録された子ジョブのMBean名を保持するマップ。
     */
    private Queue<String> registerChildObjectName = null;
        
    /**
     * コンストラクタ。
     *
     */
    public MBeanRegister() {
        server = ManagementFactory.getPlatformMBeanServer();
        registerMBeanJobStatusMap = new ConcurrentHashMap<String, JobStatus>();
        
        registerParentObjectName = new ConcurrentLinkedQueue<String>();
        registerChildObjectName  = new ConcurrentLinkedQueue<String>();
    }

    /**
     * MBeanServerにJobResult(mbean)を登録する。
     *
     * @param jobStatus ジョブステータス
     */
    public void registerMBean(JobStatus jobStatus) {

        if (jobStatus.getJobId() == null) {
            return;
        }
        
        try {
            // 登録上限数を超えた場合は登録解除
            if (mbeanCount >= manageableJobSize) {
                unregisterMBean();
            }

            // 登録名を取得
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
     * MBean登録名を生成し、返却する。
     * 
     * <p>
     * 親ジョブの登録名は<br>
     * 「JOB:name=ジョブID」 または<br>
     * 「JOB:name=ジョブID_ジョブリクエストNo」（ジョブリクエストNoが空文字以外
     *  の場合）であり、 子ジョブの登録名は<br>
     * 「親ジョブの登録名,name2=分割キー」 となる。
     * </p>
     * 
     * @param jobStatus
     *            ジョブステータス
     * @return MBean登録名
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

        // 子ジョブの場合は登録名の階層を分ける
        if (jobStatus.getPartitionNo() != -1) {
            name.append(",name2=");
            name.append(jobStatus.getPartitionKey().replaceAll(":", ""));
            registerChildObjectName.add(name.toString());
        }
        
        return name.toString();
    }

    /**
     * MBeanの登録を解除する。
     * 
     * <p>
     * 登録数が<code>manageableJobSize</code>を超える場合、
     * 登録順が古くジョブ状態が終了のものから解除する。<br>
     * 終了状態のジョブが無い場合は、終了状態に関係なく
     * 登録順が古い子ジョブから解除する。
     * </p>
     * 
     * @throws NullPointerException
     *         ObjectNameの引数がnullの場合
     * @throws MalformedObjectNameException
     *         ObjectNameの引数の文字列の形式が正しくない場合
     * @throws ReflectionException 
     *         設定メソッドの呼び出し時にスローされる
     *         java.lang.Exception をラップする場合
     * @throws MBeanException 
     *         MBeanの取得メソッドによってスローされる例外をラップする場合 
     * @throws InstanceNotFoundException
     *         指定されたMBeanがMBeanサーバに登録されていない場合
     * @throws AttributeNotFoundException 
     *         MBean の指定された属性がアクセス不能である場合
     *
     */
    private void unregisterMBean() throws MalformedObjectNameException,
            NullPointerException, AttributeNotFoundException,
            InstanceNotFoundException, MBeanException, ReflectionException {

        if (registerMBeanJobStatusMap.isEmpty()) {
            return;
        }

        // 登録がなくなる または 削除が成功するまでループする
        LOOP : while (mbeanCount > 0) {

            // 子ジョブで終了状態のものがあるか
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

            // 親ジョブで終了状態のものがあるか
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

            // 子ジョブで終了状態のものがなければ古い子ジョブを削除
            if (!registerChildObjectName.isEmpty()) {
                if (deleteMBean(registerChildObjectName.poll())) {
                    break;
                }
            }

            // 親ジョブで終了状態のものがなければ古い親ジョブを削除
            if (!registerParentObjectName.isEmpty()) {
                if (deleteMBean(registerParentObjectName.poll())) {
                    break;
                }
            }
        }
    }


    /**
     * MBeanServerからMBeanを削除する。
     * 
     * @param registerName
     *            MBean登録名
     * @return 削除成功ならtrue
     * @throws InstanceNotFoundException 指定されたMBeanが存在しない場合の例外
     * @throws MBeanRegistrationException MBeanRegistrationで発生した例外
     * @throws MalformedObjectNameException
     *           文字列の形式が有効なObjectNameに対応していない場合の例外
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
     * MBeanServerへの登録上限値を設定する。
     * 
     * @param manageableJobSize
     *            登録上限値
     */
    public void setManageableJobSize(int manageableJobSize) {
        this.manageableJobSize = manageableJobSize;
    }
}
