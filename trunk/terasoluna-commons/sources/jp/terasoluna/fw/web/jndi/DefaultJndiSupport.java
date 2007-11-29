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

package jp.terasoluna.fw.web.jndi;

import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jndi.JndiLocatorSupport;

/**
 * <p>TERASOLUNA���񋟂���JNDI�֘A�̃��[�e�B���e�B�f�t�H���g�����N���X�B</p>
 *
 * <p>
 * WebAP�R���e�i��JNDI���\�[�X���������[�e�B���e�B�ł���B<br>
 * JNDI�̔F�؏�񂪕K�v�ȏꍇ�́ABean��`�t�@�C���ɕK�v�ȃv���p�e�B��
 * �ȉ��̂悤�ɐݒ肵�Ainitialize���\�b�h�����s���邱�ƁB
 * Bean��`�t�@�C���o�R�ł��̃N���X�̃C���X�^���X�𐶐�����ꍇ��
 * init-method������initialize���\�b�h���w�肷�邱�ƁB
 * <br>
 * <table border="1">
 * <caption><strong>Bean��`�t�@�C���̐ݒ�</strong></caption>
 * <tr>
 *   <td><center><strong>�F�؏��</strong></center></td>
 *   <td><center><strong>����</strong></center></td>
 *   <td><center><strong>��</strong></center></td>
 * </tr>
 * <tr>
 *   <td>factory</td>
 *   <td>JNDI�t�@�N�g���N���X�����w�肷��B</td>
 *   <td>weblogic.jndi.WLInitialContextFactory</td>
 * </tr>
 * <tr>
 *   <td>url</td>
 *   <td>JNDI�v���o�C�_��������Ă���URI���w�肷��B</td>
 *   <td>t3://localhost:7001</td>
 * </tr>
 * <tr>
 *   <td>username</td>
 *   <td>JNDI�T�[�o�̃��[�U�����w�肷��B</td>
 *   <td>weblogic</td>
 * </tr>
 * <tr>
 *   <td>password</td>
 *   <td>JNDI�T�[�o�̃p�X���[�h���w�肷��B</td>
 *   <td>password</td>
 * </tr>
 * </table>
 * </p>
 * <br>
 *
 * WebLogic�̂悤��JNDI���\�[�X���Ƀv���t�B�b�N�X�ujava:comp/env/�v��t���Ă�
 * �����Ȃ��ꍇ�A�v���p�e�B�ujndiPrefix�v��false�ɐݒ肷��B<br>
 * �f�t�H���g��false�ł���B<br>
 * <br>
 *
 * <strong>WebLogic��Bean��`�t�@�C���ݒ��</strong>
 * <code><pre>
 * &lt;bean id=&quot;jndiSupport&quot; singleton="false"
 *       class=&quot;jp.terasoluna.fw.web.jndi.DefaultJndiSupport&quot;&gt;
 *       init-method="initialize"&gt;
 *   &lt;!-- �Z�b�^�C���W�F�N�V�����ŔF�؏��ݒ� --&gt;
 *   &lt;property name="jndiEnvironmentMap"&gt;
 *     &lt;map&gt;
 *       &lt;entry key="factory"&gt;
 *         &lt;value&gt;weblogic.jndi.WLInitialContextFactory&lt;/value&gt;
 *       &lt;/entry&gt;
 *       &lt;entry key="url"&gt;
 *         &lt;value&gt;t3://localhost:7001&lt;/value&gt;
 *       &lt;/entry&gt;
 *       &lt;entry key="username"&gt;
 *         &lt;value&gt;weblogic&lt;/value&gt;
 *       &lt;/entry&gt;
 *       &lt;entry key="password"&gt;
 *         &lt;value&gt;password&lt;/value&gt;
 *       &lt;/entry&gt;
 *     &lt;/map&gt;
 *   &lt;/property&gt;
 *   &lt;!-- �v���p�e�BjndiPrefix�̐ݒ� --&gt;
 *   &lt;property name="jndiPrefix"&gt;&lt;value&gt;<strong>false</strong>&lt;/value&gt;&lt;/property&gt;
 * &lt;/bean&gt;
 * </pre></code>
 * </p>
 * <strong>Tomcat��Bean��`�t�@�C���ݒ��</strong>
 * <code><pre>
 * &lt;bean id=&quot;jndiSupport&quot; singleton="false"
 *       class=&quot;jp.terasoluna.fw.web.jndi.DefaultJndiSupport&quot; &gt;<br>
 *   &lt;!-- �v���p�e�BjndiPrefix�̐ݒ�i�f�t�H���g�l��false�j --&gt;
 *   &lt;property name="jndiPrefix"&gt;&lt;value&gt;<strong>false</strong>&lt;/value&gt;&lt;/property&gt;
 * &lt;/bean&gt;
 * </pre></code>
 * </p>
 * <strong>�g�p���@</strong>
 * <p>
 * Bean��`�t�@�C���ɃT�[�r�X���W�b�N�̐ݒ���ȉ��̂悤�ɍs���B
 * <code><pre>
 * &lt;bean id="jndiLogic" singleton="false"
 *   class="jp.sample.JndiLogic"&gt;
 *   &lt;property name=<strong>"jndiSupport"</strong>&gt;
 *     &lt;ref bean=<strong>"jndiSupport"</strong> /&gt;
 *   &lt;/property&gt;
 * &lt;/bean&gt;<br>
 * &lt;!-- JndiSupport�ݒ� --&gt;
 * &lt;bean id=<strong>"jndiSupport"</strong>  singleton="false"
 *   class="jp.terasoluna.fw.web.jndi.DefaultJndiSupport" /&gt;
 * </code></pre>
 *
 * �T�[�r�X���W�b�N�ňȉ��̂悤��{@link DefaultJndiSupport}���擾����B<br>
 *
 * <code><pre>
 * public class JndiLogic {
 *   private JndiSupport <strong>jndiSupport</strong> = null;
 *
 *   public void setJndiSupport(jndiSupport) {
 *     this.jndiSupport = jndiSupport;
 *   }
 *
 *   public Object jndiLookup(String name) {
 *     return <strong>jndiSupport.lookup(name)</strong>;
 *   }
 * }
 * </code></pre>
 * </p>
 *
 */
public class DefaultJndiSupport extends JndiLocatorSupport implements
        JndiSupport {

    /**
     * ���O�N���X�B
     */
    private static Log log = LogFactory.getLog(DefaultJndiSupport.class);

    /**
     * JNDI�t�@�N�g���N���X����jndiEnvironmentMap���炩��擾����Ƃ��̃L�[�B
     */
    private static final String JNDI_FACTORY_KEY = "factory";

    /**
     * JNDI�v���o�C�_��URL��jndiEnvironmentMap���炩��擾����Ƃ��̃L�[�B
     */
    private static final String JNDI_URL_KEY = "url";

    /**
     * JNDI���[�U����jndiEnvironmentMap���炩��擾����Ƃ��̃L�[�B
     */
    private static final String JNDI_USERNAME_KEY = "username";

    /**
     * JNDI�p�X���[�h����jndiEnvironmentMap���炩��擾����Ƃ��̃L�[�B
     */
    private static final String JNDI_PASSWORD_KEY = "password";

    /**
     * JNDI�F�؏����i�[����<code>Map</code>�B
     */
    private Map<String, String> jndiEnvironmentMap = null;

    /**
     * ���\�[�X���̃v���t�B�b�N�X�̃Z�b�^�[�B
     * �X�[�p�[�N���X��resourceRef�̒l��ݒ肷��B
     * ���̑�����true�̏ꍇ�A�v���t�B�b�N�X"java:comp/env/"�����\�[�X���ɂ���B
     *
     * @param jndiPrefix ���\�[�X���̃v���t�B�b�N�X�t���t���O
     */
    public void setJndiPrefix(boolean jndiPrefix) {
        super.setResourceRef(jndiPrefix);
    }

    /**
     * ���\�[�X���̃v���t�B�b�N�X�̃Q�b�^�[�B
     * �X�[�p�[�N���X��resourceRef�̒l���擾����B
     * ���̑�����true�̏ꍇ�A�v���t�B�b�N�X"java:comp/env/"�����\�[�X���ɂ���B
     *
     * @return jndiPrefix ���\�[�X���̃v���t�B�b�N�X�t���t���O
     */
    public boolean isJndiPrefix() {
        return super.isResourceRef();
    }

    /**
     * jndiEnvironmentMap���擾����B
     * @return JNDI�F�؏����i�[����<code>Map</code>�B
     */
    public Map<String, String> getJndiEnvironmentMap() {
        return jndiEnvironmentMap;
    }

    /**
     * jndiEnvironmentMap��ݒ肷��B
     * @param jndiEnvironmentMap JNDI�F�؏����i�[����<code>Map</code>�B
     */
    public void setJndiEnvironmentMap(Map<String, String> jndiEnvironmentMap) {
        this.jndiEnvironmentMap = jndiEnvironmentMap;
    }

    /**
     * �R���X�g���N�^�B
     */
    public DefaultJndiSupport() {
    }

    /**
     * JndiTemplate�̊��ݒ���s���B
     */
    public void initialize() {

        // JNDI���ݒ肪����Ă���ꍇ�̂݁iWeblogic�̏ꍇ�j
        if (jndiEnvironmentMap != null) {

            // jndiEnvironmentMap����F�؏����擾
            String factory = jndiEnvironmentMap.get(JNDI_FACTORY_KEY);
            String url = jndiEnvironmentMap.get(JNDI_URL_KEY);
            String username = jndiEnvironmentMap.get(JNDI_USERNAME_KEY);
            String password = jndiEnvironmentMap.get(JNDI_PASSWORD_KEY);

            Properties environment = new Properties();
            environment.put(Context.INITIAL_CONTEXT_FACTORY, factory);
            environment.put(Context.PROVIDER_URL, url);

            if (!"".equals(username) && username != null) {
                environment.put(Context.SECURITY_PRINCIPAL, username);
                if (password == null) {
                    password = "";
                }
                environment.put(Context.SECURITY_CREDENTIALS, password);
            }

            // �F�؏��v���p�e�B�̐ݒ�
            getJndiTemplate().setEnvironment(environment);

            // ���O�o��
            if (log.isInfoEnabled()) {
                log.info("Initialize Weblogic JNDI Resource");
                log.info(Context.INITIAL_CONTEXT_FACTORY + " = " + factory);
                log.info(Context.PROVIDER_URL + " = " + url);
                log.info(Context.SECURITY_PRINCIPAL + " = " + username);
                log.info(Context.SECURITY_CREDENTIALS + " = " + password);
            }
        }
    }

    /**
     * ���O���I�u�W�F�N�g�Ƀo�C���h���āA
     * �����̃o�C���f�B���O���㏑������B
     *
     * @param name �I�u�W�F�N�g��
     * @param obj �o�C���h�����I�u�W�F�N�g
     */
    public void rebind(String name, Object obj) {
        if (name == null || obj == null) {
            log.error("Illegal arguments error : name="
                    + name + ", obj=" + obj);
            throw new IllegalArgumentException();
        }
        // ���\�[�X���̃v���t�B�b�N�X�ݒ�
        String jndiNameToUse = convertJndiName(name);
        try {
            getJndiTemplate().rebind(jndiNameToUse, obj);
        } catch (NamingException e) {
            log.error("Illegal JNDI context name.");
            throw new JndiException(e);
        }
    }

    /**
     * �w�肳�ꂽ�I�u�W�F�N�g���A���o�C���h����B
     *
     * @param name �I�u�W�F�N�g��
     */
    public void unbind(String name) {
        if (name == null) {
            log.error("Illegal arguments error : name=" + name);
            throw new IllegalArgumentException();
        }
        // ���\�[�X���̃v���t�B�b�N�X�ݒ�
        String jndiNameToUse = convertJndiName(name);
        try {
            getJndiTemplate().unbind(jndiNameToUse);
        } catch (NamingException e) {
            log.error("Illegal JNDI context name.");
            throw new JndiException(e);
        }
    }

    /**
     * �w�肳�ꂽ�I�u�W�F�N�g���擾����B
     *
     * @param name �I�u�W�F�N�g��
     * @return �I�u�W�F�N�g
     */
    @Override
    public Object lookup(String name) {
        if (name == null) {
            log.error("Illegal arguments error : name=" + name);
            throw new IllegalArgumentException();
        }
        // ���\�[�X���̃v���t�B�b�N�X�ݒ�
        String jndiNameToUse = convertJndiName(name);
        try {
            return getJndiTemplate().lookup(jndiNameToUse);
        } catch (NamingException e) {
            log.error("Illegal JNDI context name.");
            throw new JndiException(e);
        }
    }
}