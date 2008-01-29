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
 * ジョブ起動用クラス。</p>
 * 
 * <p>このクラス <code>main()</code> メソッドでジョブを起動することができる。</p>
 * 
 * <p>このクラスでジョブを起動する際には、以下を引数で指定する。</p>
 *
 * <ul>
 * <li>第１引数：ジョブ<code>ID</code>（必須）</li>
 *   <p>実行対象ジョブを一意に識別するジョブIDを指定する。</p>
 * <li>第２引数：ジョブBean定義ファイルの相対パス（必須）</li>
 *   <p>実行対象ジョブの情報が設定されているジョブ<code>Bean</code>
 *   定義ファイルの相対パス。</p>
 *   <p>ジョブBean定義ファイルはクラスローダで読み込まれるため、クラスパスとして
 *   を指定する。
 *   <p>たとえば、「batchapps」というフォルダ配下にジョブBean定義ファイルが置か
 *   れていて、「batchapps」からの相対パスを指定する。</p>
 *   <p>指定のジョブBean定義ファイルが存在しない場合はエラーとして終了する。</p>
 * <li>第３引数以降：パラメータ値<td>（任意）</li>
 *   <p>指定された値はジョブ設定時に指定したジョブコンテキストに格納される。</p>
 *   <p>引数の前に「-p」を指定することで起動ジョブのジョブプロセスIDを指定する
 *   ことができる。</p>
 * </ul>
 * 
 * <p>起動例</p>
 * <code>java jp.terasoluna....JobStarter 
 * JOB0001 UD001/JOB0001.xml PARM01 PARM02 PARM03 PARM04 -p POS0001</code>
 *
 * <p>ジョブコンテキスト実装例<p>
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
     * ログインスタンス。
     */
    private static Log log = LogFactory.getLog(JobStarter.class);
    
    /**
     * 同期ジョブ起動用Mainメソッド。
     *
     * @param args 
     *            起動時の引数<BR>
     *            第１引数：ジョブID<BR>
     *            第２引数：ジョブBean定義ファイルの相対パス<BR>
     *            第３引数以降：ジョブコンテキスト格納用引数<BR>
     *            -p以降の引数はジョブプロセスID<BR>
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
     * 引数で指定されたパラメータを元にジョブを実行する。
     *
     * @param args 
     *            起動時の引数<BR>
     *            第１引数：ジョブID<BR>
     *            第２引数：ジョブBean定義ファイルの相対パス<BR>
     *            第３引数以降：ジョブコンテキスト格納用引数<BR>
     *            -p以降の引数はジョブプロセスID<BR>
     * @return ジョブ終了コード
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
