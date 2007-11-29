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


import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingException;

import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.web.jndi.DefaultJndiSupport;
import jp.terasoluna.fw.web.jndi.JndiException;
import junit.framework.TestCase;
/**
 * {@link jp.terasoluna.fw.web.jndi.DefaultJndiSupport}
 * �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 *
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * TERASOLUNA���񋟂���JNDI�֘A�̃��[�e�B���e�B�f�t�H���g�����N���X�B
 * <p>
 *
 * @see jp.terasoluna.fw.web.jndi.DefaultJndiSupport
 */
public class DefaultJndiSupportTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     *
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(DefaultJndiSupportTest.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        LogUTUtil.flush();
    }

    @Override
    protected void tearDown() throws Exception {

        super.tearDown();
    }

    /**
     * testSetJndiPrefix01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) super.resourceRef:true<br>
     *         (���) super.resourceRef:false<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) super.resourceRef:true<br>
     *
     * <br>
     * �����Ɏw�肵���l���X�[�p�[�N���X��resourceRef�ɐ���Ɋi�[����邱�Ƃ��m�F
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetJndiPrefix01() throws Exception {
        // �O����
        DefaultJndiSupport support = new DefaultJndiSupport();
        support.setResourceRef(false);

        // �e�X�g���{
        support.setJndiPrefix(true);

        // ����
        // �X�[�p�[�N���X�̃��\�b�h�Ăяo��
        boolean b = support.isResourceRef();
        assertTrue(b);
    }

    /**
     * testIsJndiPrefix01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) super.resourceRef:true<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) super.resourceRef:true<br>
     *
     * <br>
     * DefaultJndiSupport�Ɋi�[����Ă���super.resourceRef�𐳏�Ɏ擾����
     * ���Ƃ��m�F
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testIsJndiPrefix01() throws Exception {
        // �O����
        DefaultJndiSupport support = new DefaultJndiSupport();
        // �X�[�p�[�N���X�̃��\�b�h�őO������ݒ�
        support.setResourceRef(true);

        // �e�X�g���{
        boolean b = support.isJndiPrefix();

        // ����
        assertTrue(b);
    }
    
    /**
     * testGetJndiEnvironmentMap01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) jndiEnvironmentMap:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Map:�C���X�^���X�ϐ���jndiEnvironmentMap<br>
     *
     * <br>
     * �C���X�^���X�ϐ���jndiEnvironmentMap���擾�ł��邱�Ƃ��m�F����B<br>
     * ������n1���̂݊m�F�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetJndiEnvironmentMap01() throws Exception {
        
        // �O����
        DefaultJndiSupport support = new DefaultJndiSupport();
        Map map = new HashMap();
        
        UTUtil.setPrivateField(support, "jndiEnvironmentMap", map);
        
        // �e�X�g���{
        Object result = support.getJndiEnvironmentMap();

        // ���蔻��
        assertSame(map, result);
    }
    
    /**
     * testSetJndiEnvironmentMap01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) jndiEnvironmentMap:not null<br>
     *         (���) jndiEnvironmentMap:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) jndiEnvironmentMap:not null�i�����Ɏw�肵��Map�j<br>
     *
     * <br>
     * �����Ɏw�肵��Map���C���X�^���X�ϐ�jndiEnvironmentMap�ɐݒ肳��邱�Ƃ��m�F����B<br>
     * ������n1���̂݊m�F�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetJndiEnvironmentMap01() throws Exception {
        
        // �O����
        DefaultJndiSupport support = new DefaultJndiSupport();
        Map<String, String> map = new HashMap<String, String>();
        
        // �e�X�g���{
        support.setJndiEnvironmentMap(map);
        
        // ���蔻��
        Object result = UTUtil.getPrivateField(support, "jndiEnvironmentMap");
        assertSame(map, result);
    }

    /**
     * testInitialize01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) jndiEnvironmentMap:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) jndiEnvironmentMap:null<br>
     * <br>
     * jndiEnvironmentMap��null�������ꍇ�AJndiTemplate�̊��v���p�e�B�͐ݒ肳��Ȃ����Ƃ��m�F
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitialize01() throws Exception {

        // �O����
        DefaultJndiSupport support = new DefaultJndiSupport();
        UTUtil.setPrivateField(support, "jndiEnvironmentMap", null);
        
        // �e�X�g���{
        support.initialize();
        
        // ����
        Object result = UTUtil.getPrivateField(support, "jndiEnvironmentMap");
        assertNull(result);
    }

    /**
     * testInitialize02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(���) jndiEnvironmentMap.get("factory")�F"factory"<br>
     *         (���) jndiEnvironmentMap.get("url")�F"url"<br>
     *         (���) jndiEnvironmentMap.get("username")�Fnull<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) jndiEnvironmentMap:not null<br>
     *         (��ԕω�) getJndiTemplate().get(<br>
     *                    Context.INITIAL_CONTEXT_FACTORY):"factory"<br>
     *         (��ԕω�) getJndiTemplate().get(<br>
     *                    Context.PROVIDER_URL):"url"<br>
     *         (��ԕω�) getJndiTemplate().get(<br>
     *                    Context.SECURITY_PRINCIPAL):null<br>
     *         (��ԕω�) getJndiTemplate().get(<br>
     *                    Context.SECURITY_CREDENTIALS):null<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �C���t�H���O�F<br>
     *                    "java.naming.factory.initial = factory"<br>
     *                    "java.naming.provider.url = url"<br>
     *                    "java.naming.security.principal = null"<br>
     *                    "java.naming.security.credentials = null"<br>
     *
     * <br>
     * jndiEnvironmentMap.get("username")��null�������ꍇ�A
     * JndiTemplate�̊��v���p�e�B��jndiFactory��jndiUrl�������ݒ肳��Ă��邱�Ƃ��m�F
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitialize02() throws Exception {
        // �O����
        Map<String, String> map = new HashMap<String, String>();
        map.put("factory", "factory");
        map.put("url", "url");
        
        DefaultJndiSupport support = new DefaultJndiSupport();
        UTUtil.setPrivateField(support, "jndiEnvironmentMap", map);

        // �e�X�g���{
        support.initialize();
        
        // ����
        Object result = UTUtil.getPrivateField(support, "jndiEnvironmentMap");
        assertSame(map, result);
        
        assertTrue(LogUTUtil.checkInfo(
                "java.naming.factory.initial = factory"));
        assertTrue(LogUTUtil.checkInfo("java.naming.provider.url = url"));
        assertTrue(LogUTUtil.checkInfo(
                "java.naming.security.principal = null"));
        assertTrue(LogUTUtil.checkInfo(
                "java.naming.security.credentials = null"));

        Properties props = support.getJndiEnvironment();
        assertEquals("factory", props.get(Context.INITIAL_CONTEXT_FACTORY));
        assertEquals("url", props.get(Context.PROVIDER_URL));
        assertNull(props.get(Context.SECURITY_PRINCIPAL));
        assertNull(props.get(Context.SECURITY_CREDENTIALS));
    }

    /**
     * testInitialize03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(���) jndiEnvironmentMap.get("factory")�F"factory"<br>
     *         (���) jndiEnvironmentMap.get("url")�F"url"<br>
     *         (���) jndiEnvironmentMap.get("username")�F""<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) jndiEnvironmentMap:not null<br>
     *         (��ԕω�) getJndiTemplate().get(<br>
     *                    Context.INITIAL_CONTEXT_FACTORY):"factory"<br>
     *         (��ԕω�) getJndiTemplate().get(<br>
     *                    Context.PROVIDER_URL):"url"<br>
     *         (��ԕω�) getJndiTemplate().get(<br>
     *                    Context.SECURITY_PRINCIPAL):null<br>
     *         (��ԕω�) getJndiTemplate().get(<br>
     *                    Context.SECURITY_CREDENTIALS):null<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �C���t�H���O�F<br>
     *                    "java.naming.factory.initial = factory"<br>
     *                    "java.naming.provider.url = url"<br>
     *                    "java.naming.security.principal = "<br>
     *                    "java.naming.security.credentials = null"<br>
     *
     * <br>
     * jndiEnvironmentMap.get("username")���󕶎��������ꍇ�AJndiTemplate�̊�
     * �v���p�e�B��jndiFactory��jndiUrl�������ݒ肳��Ă��邱�Ƃ��m�F
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitialize03() throws Exception {
        
        // �O����
        Map<String, String> map = new HashMap<String, String>();
        map.put("factory", "factory");
        map.put("url", "url");
        map.put("username", "");
        
        DefaultJndiSupport support = new DefaultJndiSupport();
        UTUtil.setPrivateField(support, "jndiEnvironmentMap", map);

        // �e�X�g���{
        support.initialize();
        
        // ����
        Object result = UTUtil.getPrivateField(support, "jndiEnvironmentMap");
        assertSame(map, result);
        
        assertTrue(LogUTUtil.checkInfo("java.naming.factory.initial = factory"));
        assertTrue(LogUTUtil.checkInfo("java.naming.provider.url = url"));
        assertTrue(LogUTUtil.checkInfo("java.naming.security.principal = "));
        assertTrue(LogUTUtil.checkInfo(
                "java.naming.security.credentials = null"));

        Properties props = support.getJndiEnvironment();
        assertEquals("factory", props.get(Context.INITIAL_CONTEXT_FACTORY));
        assertEquals("url", props.get(Context.PROVIDER_URL));
        assertNull(props.get(Context.SECURITY_PRINCIPAL));
        assertNull(props.get(Context.SECURITY_CREDENTIALS));
    }

    /**
     * testInitialize04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(���) jndiEnvironmentMap.get("factory")�F"factory"<br>
     *         (���) jndiEnvironmentMap.get("url")�F"url"<br>
     *         (���) jndiEnvironmentMap.get("username")�F"username"<br>
     *         (���) jndiEnvironmentMap.get("password")�Fnull<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) jndiEnvironmentMap:not null<br>
     *         (��ԕω�) getJndiTemplate().get(<br>
     *                    Context.INITIAL_CONTEXT_FACTORY):"factory"<br>
     *         (��ԕω�) getJndiTemplate().get(<br>
     *                    Context.PROVIDER_URL):"url"<br>
     *         (��ԕω�) getJndiTemplate().get(<br>
     *                    Context.SECURITY_PRINCIPAL):"username"<br>
     *         (��ԕω�) getJndiTemplate().get(<br>
     *                    Context.SECURITY_CREDENTIALS):""<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �C���t�H���O�F<br>
     *                    "java.naming.factory.initial = factory"<br>
     *                    "java.naming.provider.url = url"<br>
     *                    "java.naming.security.principal = username"<br>
     *                    "java.naming.security.credentials = "<br>
     *
     * <br>
     * jndiEnvironmentMap.get("password")��null�������ꍇ�AJndiTemplate�̊�
     * �v���p�e�B��jndiFactory��jndiUrl��jndiUsername�Ƌ󕶎���jndiPassword���ݒ肳��Ă��邱�Ƃ��m�F
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitialize04() throws Exception {
        // �O����
        Map<String, String> map = new HashMap<String, String>();
        map.put("factory", "factory");
        map.put("url", "url");
        map.put("username", "username");
        
        DefaultJndiSupport support = new DefaultJndiSupport();
        UTUtil.setPrivateField(support, "jndiEnvironmentMap", map);

        // �e�X�g���{
        support.initialize();
        
        // ����
        Object result = UTUtil.getPrivateField(support, "jndiEnvironmentMap");
        assertSame(map, result);
        
        assertTrue(LogUTUtil.checkInfo(
                "java.naming.factory.initial = factory"));
        assertTrue(LogUTUtil.checkInfo("java.naming.provider.url = url"));
        assertTrue(LogUTUtil.checkInfo(
                "java.naming.security.principal = username"));
        assertTrue(LogUTUtil.checkInfo("java.naming.security.credentials = "));

        Properties props = support.getJndiEnvironment();
        assertEquals("factory", props.get(Context.INITIAL_CONTEXT_FACTORY));
        assertEquals("url", props.get(Context.PROVIDER_URL));
        assertEquals("username", props.get(Context.SECURITY_PRINCIPAL));
        assertEquals("", props.get(Context.SECURITY_CREDENTIALS));
    }

    /**
     * testInitialize05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(���) jndiEnvironmentMap.get("factory")�F"factory"<br>
     *         (���) jndiEnvironmentMap.get("url")�F"url"<br>
     *         (���) jndiEnvironmentMap.get("username")�F"username"<br>
     *         (���) jndiEnvironmentMap.get("password")�F""<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) jndiEnvironmentMap:not null<br>
     *         (��ԕω�) getJndiTemplate().get(<br>
     *                    Context.INITIAL_CONTEXT_FACTORY):"factory"<br>
     *         (��ԕω�) getJndiTemplate().get(<br>
     *                    Context.PROVIDER_URL):"url"<br>
     *         (��ԕω�) getJndiTemplate().get(<br>
     *                    Context.SECURITY_PRINCIPAL):"username"<br>
     *         (��ԕω�) getJndiTemplate().get(<br>
     *                    Context.SECURITY_CREDENTIALS):""<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �C���t�H���O�F<br>
     *                    "java.naming.factory.initial = factory"<br>
     *                    "java.naming.provider.url = url"<br>
     *                    "java.naming.security.principal = username"<br>
     *                    "java.naming.security.credentials = "<br>
     *
     * <br>
     * jndiEnvironmentMap.get("password")���󕶎��������ꍇ�AJndiTemplate�̊�
     * �v���p�e�B��jndiFactory��jndiUrl��jndiUsername��jndiPassword���ݒ肳��Ă��邱�Ƃ��m�F
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitialize05() throws Exception {
        // �O����
        Map<String, String> map = new HashMap<String, String>();
        map.put("factory", "factory");
        map.put("url", "url");
        map.put("username", "username");
        map.put("password", "");
        
        DefaultJndiSupport support = new DefaultJndiSupport();
        UTUtil.setPrivateField(support, "jndiEnvironmentMap", map);

        // �e�X�g���{
        support.initialize();
        
        // ����
        Object result = UTUtil.getPrivateField(support, "jndiEnvironmentMap");
        assertSame(map, result);
        
        assertTrue(LogUTUtil.checkInfo(
                "java.naming.factory.initial = factory"));
        assertTrue(LogUTUtil.checkInfo("java.naming.provider.url = url"));
        assertTrue(LogUTUtil.checkInfo(
                "java.naming.security.principal = username"));
        assertTrue(LogUTUtil.checkInfo("java.naming.security.credentials = "));

        Properties props = support.getJndiEnvironment();
        assertEquals("factory", props.get(Context.INITIAL_CONTEXT_FACTORY));
        assertEquals("url", props.get(Context.PROVIDER_URL));
        assertEquals("username", props.get(Context.SECURITY_PRINCIPAL));
        assertEquals("", props.get(Context.SECURITY_CREDENTIALS));
    }

    /**
     * testInitialize06()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(���) jndiEnvironmentMap.get("factory")�F"factory"<br>
     *         (���) jndiEnvironmentMap.get("url")�F"url"<br>
     *         (���) jndiEnvironmentMap.get("username")�F"username"<br>
     *         (���) jndiEnvironmentMap.get("password")�F"password"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) jndiEnvironmentMap:not null<br>
     *         (��ԕω�) getJndiTemplate().get(<br>
     *                    Context.INITIAL_CONTEXT_FACTORY):"factory"<br>
     *         (��ԕω�) getJndiTemplate().get(<br>
     *                    Context.PROVIDER_URL):"url"<br>
     *         (��ԕω�) getJndiTemplate().get(<br>
     *                    Context.SECURITY_PRINCIPAL):"username"<br>
     *         (��ԕω�) getJndiTemplate().get(<br>
     *                    Context.SECURITY_CREDENTIALS):"password"<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �C���t�H���O�F<br>
     *                    "java.naming.factory.initial = factory"<br>
     *                    "java.naming.provider.url = url"<br>
     *                    "java.naming.security.principal = username"<br>
     *                    "java.naming.security.credentials = password"<br>
     *
     * <br>
     * jndiEnvironmentMap�Ɋi�[����Ă���"factory", "url", "username", 
     * "password"��null�ł��󕶎��ł��Ȃ������ꍇ�AJndiTemplate�̊�
     * �v���p�e�B��jndiFactory�AjndiUrl�AjndiUsername�AjndiPassword���ݒ肳��Ă��邱�Ƃ��m�F
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testInitialize06() throws Exception {
        // �O����
        Map<String, String> map = new HashMap<String, String>();
        map.put("factory", "factory");
        map.put("url", "url");
        map.put("username", "username");
        map.put("password", "password");
        
        DefaultJndiSupport support = new DefaultJndiSupport();
        UTUtil.setPrivateField(support, "jndiEnvironmentMap", map);

        // �e�X�g���{
        support.initialize();
        
        // ����
        Object result = UTUtil.getPrivateField(support, "jndiEnvironmentMap");
        assertSame(map, result);
        
        assertTrue(LogUTUtil.checkInfo("java.naming.factory.initial = factory"));
        assertTrue(LogUTUtil.checkInfo("java.naming.provider.url = url"));
        assertTrue(LogUTUtil.checkInfo(
                "java.naming.security.principal = username"));
        assertTrue(LogUTUtil.checkInfo(
                "java.naming.security.credentials = password"));

        Properties props = support.getJndiEnvironment();
        assertEquals("factory", props.get(Context.INITIAL_CONTEXT_FACTORY));
        assertEquals("url", props.get(Context.PROVIDER_URL));
        assertEquals("username", props.get(Context.SECURITY_PRINCIPAL));
        assertEquals("password", props.get(Context.SECURITY_CREDENTIALS));
    }

    /**
     * testRebind01()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) name:null<br>
     *         (����) obj:"abc"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F<br>
     *                    "Illegal arguments error : name=" + name + ", obj=" + obj<br>
     *
     * <br>
     * ����name��null�̏ꍇ�A��O���N�������Ƃ��m�F����
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testRebind01() throws Exception {
        // �O����
        DefaultJndiSupport support = new DefaultJndiSupport();

        String name = null;
        Object obj = "abc";

        try {
            // �e�X�g���{
            support.rebind(name, obj);
            fail();
        } catch (IllegalArgumentException e) {
            // ����
            assertTrue(LogUTUtil.checkError(
                    "Illegal arguments error : name=null, obj=abc"));
        }
    }

    /**
     * testRebind02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:""<br>
     *         (����) obj:"abc"<br>
     *         (���) JndiTemplate:DefaultJndiSupport_JndiTemplate_Stub01<br>
     *         (���) super.<br>
     *                resourceRef:false<br>
     *         (���) DefaultJndiSupport_JndiTemplate_Stub01.<br>
     *                isCallRebind:false<br>
     *         (���) DefaultJndiSupport_JndiTemplate_Stub01.<br>
     *                jndiNameToUse:null<br>
     *         (���) DefaultJndiSupport_JndiTemplate_Stub01.<br>
     *                obj:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) DefaultJndiSupport_JndiTemplate_Stub01.<br>
     *                    isCallRebind:true<br>
     *         (��ԕω�) DefaultJndiSupport_JndiTemplate_Stub01.<br>
     *                    jndiNameToUse:""<br>
     *         (��ԕω�) DefaultJndiSupport_JndiTemplate_Stub01.<br>
     *                    obj:"abc"<br>
     *
     * <br>
     * ����name���󕶎��ł���A����obj��not null�̏ꍇ�AJndiTemplate.rebind()�̌Ăяo���m�F���s��
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testRebind02() throws Exception {
        // �O����
        DefaultJndiSupport support = new DefaultJndiSupport();

        String name = "";
        Object obj = "abc";


        // JndiTemplate�擾
        DefaultJndiSupport_JndiTemplateStub01 template =
            new DefaultJndiSupport_JndiTemplateStub01();
        template.setCallRebind(false);
        template.setJndiNameToUse(null);
        template.setObj(null);
        support.setJndiTemplate(template);

        // super.resourceRef = false;
        support.setResourceRef(false);

        // �e�X�g���{
        support.rebind(name, obj);

        // ����
        boolean b = template.isCallRebind();
        assertTrue(b);
        assertEquals("", template.getJndiNameToUse());
        assertEquals("abc", template.getObj());
    }

    /**
     * testRebind03()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) name:"abc"<br>
     *         (����) obj:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F<br>
     *                    "Illegal arguments error : name=" + name + ", obj=" + obj<br>
     *
     * <br>
     * ����obj��null�̏ꍇ�A��O���N�������Ƃ��m�F����
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testRebind03() throws Exception {
        // �O����
        DefaultJndiSupport support = new DefaultJndiSupport();

        String name = "abc";
        Object obj = null;

        try {
            // �e�X�g���{
            support.rebind(name, obj);
            fail();
        } catch (IllegalArgumentException e) {
            // ����
            assertTrue(LogUTUtil.checkError(
                    "Illegal arguments error : name=abc, obj=null"));
        }
    }

    /**
     * testRebind04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:"abc"<br>
     *         (����) obj:"abc"<br>
     *         (���) JndiTemplate:DefaultJndiSupport_JndiTemplate_Stub01<br>
     *         (���) super.<br>
     *                resourceRef:true<br>
     *         (���) DefaultJndiSupport_JndiTemplate_Stub01.<br>
     *                isCallRebind:false<br>
     *         (���) DefaultJndiSupport_JndiTemplate_Stub01.<br>
     *                jndiNameToUse:null<br>
     *         (���) DefaultJndiSupport_JndiTemplate_Stub01.<br>
     *                obj:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) DefaultJndiSupport_JndiTemplate_Stub01.<br>
     *                    isCallRebind:true<br>
     *         (��ԕω�) DefaultJndiSupport_JndiTemplate_Stub01.<br>
     *                    jndiNameToUse:"java:comp/env/abc"<br>
     *         (��ԕω�) DefaultJndiSupport_JndiTemplate_Stub01.<br>
     *                    obj:"abc"<br>
     *
     * <br>
     * jndiPrefix��true�ł���A����name��"java:comp/env/"�Ŏn�܂�Ȃ������ꍇ�A
     * "java:comp/env/"������name�ɉ�����JndiTemplate.rebind()�̌Ăяo����
     * �s���Ă��邱�Ƃ��m�F���s��
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testRebind04() throws Exception {
        // �O����
        DefaultJndiSupport support = new DefaultJndiSupport();

        String name = "abc";
        Object obj = "abc";

        // JndiTemplate�擾
        DefaultJndiSupport_JndiTemplateStub01 template =
            new DefaultJndiSupport_JndiTemplateStub01();
        template.setCallRebind(false);
        template.setJndiNameToUse(null);
        template.setObj(null);
        support.setJndiTemplate(template);

        // super.resourceRef = true;
        support.setResourceRef(true);

        // �e�X�g���{
        support.rebind(name, obj);

        // ����
        boolean b = template.isCallRebind();
        assertTrue(b);
        assertEquals("java:comp/env/abc", template.getJndiNameToUse());
        assertEquals("abc", template.getObj());
    }

    /**
     * testRebind05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:"java:comp/env/abc"<br>
     *         (����) obj:"abc"<br>
     *         (���) JndiTemplate:DefaultJndiSupport_JndiTemplate_Stub02<br>
     *         (���) super.<br>
     *                resourceRef:false<br>
     *         (���) DefaultJndiSupport_JndiTemplate_Stub01.<br>
     *                isCallRebind:false<br>
     *         (���) DefaultJndiSupport_JndiTemplate_Stub01.<br>
     *                jndiNameToUse:null<br>
     *         (���) DefaultJndiSupport_JndiTemplate_Stub01.<br>
     *                obj:null<br>
     *         (���) NamingException:����<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) DefaultJndiSupport_JndiTemplate_Stub01.<br>
     *                    isCallRebind:true<br>
     *         (��ԕω�) DefaultJndiSupport_JndiTemplate_Stub01.<br>
     *                    jndiNameToUse:"java:comp/env/abc"<br>
     *         (��ԕω�) DefaultJndiSupport_JndiTemplate_Stub01.<br>
     *                    obj:"abc"<br>
     *         (��ԕω�) ��O:JndiException�F<br>
     *                    ���b�v������O�FNamingException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F<br>
     *                    "Illegal JNDI context name."<br>
     *
     * <br>
     * JndiTemplate.rebind()��NamingException�����������ꍇ�AJndiException��
     * �N�������Ƃ��m�F����
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testRebind05() throws Exception {
        // �O����
        DefaultJndiSupport support = new DefaultJndiSupport();

        String name = "java:comp/env/abc";
        Object obj = "abc";

        // JndiTemplate�擾
        DefaultJndiSupport_JndiTemplateStub02 template =
            new DefaultJndiSupport_JndiTemplateStub02();
        template.setCallRebind(false);
        template.setJndiNameToUse(null);
        template.setObj(null);
        support.setJndiTemplate(template);

        // super.resourceRef = false;
        support.setResourceRef(false);

        try {
            // �e�X�g���{
            support.rebind(name, obj);
            fail();
        } catch (JndiException e) {
            boolean b = template.isCallRebind();
            assertTrue(b);
            assertEquals("java:comp/env/abc", template.getJndiNameToUse());
            assertEquals("abc", template.getObj());

            // ����
            assertEquals(NamingException.class.getName(),
                    e.getCause().getClass().getName());
            assertTrue(LogUTUtil.checkError("Illegal JNDI context name."));
        }
    }

    /**
     * testUnbind01()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) name:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F<br>
     *                    "Illegal arguments error : name=" + name<br>
     *
     * <br>
     * ����name��null�̏ꍇ�A��O���N�������Ƃ��m�F����
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testUnbind01() throws Exception {
        // �O����
        DefaultJndiSupport support = new DefaultJndiSupport();

        String name = null;

        try {
            // �e�X�g���{
            support.unbind(name);
            fail();
        } catch (IllegalArgumentException e) {
            // ����
            assertTrue(LogUTUtil.checkError(
                    "Illegal arguments error : name=null"));
        }
    }

    /**
     * testUnbind02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:""<br>
     *         (���) JndiTemplate:DefaultJndiSupport_JndiTemplate_Stub01<br>
     *         (���) super.<br>
     *                resourceRef:false<br>
     *         (���) DefaultJndiSupport_JndiTemplate_Stub01.<br>
     *                isCallUnbind:false<br>
     *         (���) DefaultJndiSupport_JndiTemplate_Stub01.<br>
     *                jndiNameToUse:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) DefaultJndiSupport_JndiTemplate_Stub01.<br>
     *                    isCallUnbind:true<br>
     *         (��ԕω�) DefaultJndiSupport_JndiTemplate_Stub01.<br>
     *                    jndiNameToUse:""<br>
     *
     * <br>
     * ����name���󕶎��̏ꍇ�AJndiTemplate.unbind()�̌Ăяo���m�F���s��
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testUnbind02() throws Exception {
        // �O����
        DefaultJndiSupport support = new DefaultJndiSupport();

        String name = "";

        // JndiTemplate�擾
        DefaultJndiSupport_JndiTemplateStub01 template =
            new DefaultJndiSupport_JndiTemplateStub01();
        template.setCallUnbind(false);
        template.setJndiNameToUse(null);

        support.setJndiTemplate(template);

        // super.resourceRef = false;
        support.setResourceRef(false);

        // �e�X�g���{
        support.unbind(name);

        // ����
        boolean b = template.isCallUnbind();
        assertTrue(b);
        assertEquals("", template.getJndiNameToUse());
    }

    /**
     * testUnbind03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:"abc"<br>
     *         (���) JndiTemplate:DefaultJndiSupport_JndiTemplate_Stub01<br>
     *         (���) super.<br>
     *                resourceRef:true<br>
     *         (���) DefaultJndiSupport_JndiTemplate_Stub01.<br>
     *                isCallUnbind:false<br>
     *         (���) DefaultJndiSupport_JndiTemplate_Stub01.<br>
     *                jndiNameToUse:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) DefaultJndiSupport_JndiTemplate_Stub01.<br>
     *                    isCallUnbind:true<br>
     *         (��ԕω�) DefaultJndiSupport_JndiTemplate_Stub01.<br>
     *                    jndiNameToUse:"java:comp/env/abc"<br>
     *
     * <br>
     * super.resourceRef��true�ł���A����name��"java:comp/env/"��
     * �n�܂�Ȃ������ꍇ�A"java:comp/env/"������name�ɉ�����
     * JndiTemplate.unbind()�̌Ăяo�����s���Ă��邱�Ƃ��m�F���s��
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testUnbind03() throws Exception {
        // �O����
        DefaultJndiSupport support = new DefaultJndiSupport();

        String name = "abc";

        // JndiTemplate�擾
        DefaultJndiSupport_JndiTemplateStub01 template =
            new DefaultJndiSupport_JndiTemplateStub01();
        template.setCallRebind(false);
        template.setJndiNameToUse(null);

        support.setJndiTemplate(template);

        // super.resourceRef = true;
        support.setResourceRef(true);

        // �e�X�g���{
        support.unbind(name);

        // ����
        boolean b = template.isCallUnbind();
        assertTrue(b);
        assertEquals("java:comp/env/abc", template.getJndiNameToUse());
    }

    /**
     * testUnbind04()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) name:"java:comp/env/abc"<br>
     *         (���) JndiTemplate:DefaultJndiSupport_JndiTemplate_Stub02<br>
     *         (���) super.<br>
     *                resourceRef:false<br>
     *         (���) DefaultJndiSupport_JndiTemplate_Stub01.<br>
     *                isCallUnbind:false<br>
     *         (���) DefaultJndiSupport_JndiTemplate_Stub01.<br>
     *                jndiNameToUse:null<br>
     *         (���) NamingException:����<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) DefaultJndiSupport_JndiTemplate_Stub01.<br>
     *                    isCallUnbind:true<br>
     *         (��ԕω�) DefaultJndiSupport_JndiTemplate_Stub01.<br>
     *                    jndiNameToUse:"java:comp/env/abc"<br>
     *         (��ԕω�) ��O:JndiException�F<br>
     *                    ���b�v������O�FNamingException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F<br>
     *                    "Illegal JNDI context name."<br>
     *
     * <br>
     * JndiTemplate.unbind()��NamingException�����������ꍇ�AJndiException��
     * �N�������Ƃ��m�F����
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testUnbind04() throws Exception {
        // �O����
        DefaultJndiSupport support = new DefaultJndiSupport();

        String name = "java:comp/env/abc";

        // JndiTemplate�擾
        DefaultJndiSupport_JndiTemplateStub02 template =
            new DefaultJndiSupport_JndiTemplateStub02();
        template.setCallRebind(false);
        template.setJndiNameToUse(null);

        support.setJndiTemplate(template);

        // super.resourceRef = false;
        support.setResourceRef(false);

        try {
            // �e�X�g���{
            support.unbind(name);
            fail();
        } catch (JndiException e) {
            boolean b = template.isCallUnbind();
            assertTrue(b);
            assertEquals("java:comp/env/abc", template.getJndiNameToUse());

            // ����
            assertTrue(LogUTUtil.checkError("Illegal JNDI context name."));
            assertEquals(NamingException.class.getName(),
                    e.getCause().getClass().getName());
        }
    }

    /**
     * testLookup01()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) name:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F<br>
     *                    "Illegal arguments error : name=" + name<br>
     *
     * <br>
     * ����name��null�̏ꍇ�A��O���N�������Ƃ��m�F����
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLookup01() throws Exception {
        // �O����
        DefaultJndiSupport support = new DefaultJndiSupport();

        String name = null;

        try {
            // �e�X�g���{
            support.lookup(name);
            fail();
        } catch (IllegalArgumentException e) {
            // ����
            assertTrue(LogUTUtil.checkError(
                    "Illegal arguments error : name=null"));
        }
    }

    /**
     * testLookup02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:""<br>
     *         (���) JndiTemplate:DefaultJndiSupport_JndiTemplate_Stub01<br>
     *         (���) super.<br>
     *                resourceRef:false<br>
     *         (���) DefaultJndiSupport_JndiTemplate_Stub01.<br>
     *                isCallLookup:false<br>
     *         (���) DefaultJndiSupport_JndiTemplate_Stub01.<br>
     *                jndiNameToUse:null<br>
     *         (���) DefaultJndiSupport_JndiTemplate_Stub01.<br>
     *                lookup()�߂�l:"return"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Object:"return"<br>
     *         (��ԕω�) DefaultJndiSupport_JndiTemplate_Stub01.<br>
     *                    isCallLookup:true<br>
     *         (��ԕω�) DefaultJndiSupport_JndiTemplate_Stub01.<br>
     *                    jndiNameToUse:""<br>
     *
     * <br>
     * ����name���󕶎��̏ꍇ�AJndiTemplate.lookup()�̌Ăяo���m�F���s��
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLookup02() throws Exception {
        // �O����
        DefaultJndiSupport support = new DefaultJndiSupport();

        String name = "";

        // JndiTemplate�擾
        DefaultJndiSupport_JndiTemplateStub01 template =
            new DefaultJndiSupport_JndiTemplateStub01();
        template.setCallLookup(false);
        template.setJndiNameToUse(null);

        support.setJndiTemplate(template);

        // super.resourceRef = false;
        support.setResourceRef(false);

        // �e�X�g���{
        Object result = support.lookup(name);

        // ����
        boolean b = template.isCallLookup();
        assertTrue(b);
        assertEquals("", template.getJndiNameToUse());
        assertEquals("return", result);
    }

    /**
     * testLookup03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) name:"abc"<br>
     *         (���) JndiTemplate:DefaultJndiSupport_JndiTemplate_Stub01<br>
     *         (���) super.<br>
     *                resourceRef:true<br>
     *         (���) DefaultJndiSupport_JndiTemplate_Stub01.<br>
     *                isCallLookup:false<br>
     *         (���) DefaultJndiSupport_JndiTemplate_Stub01.<br>
     *                jndiNameToUse:null<br>
     *         (���) DefaultJndiSupport_JndiTemplate_Stub01.<br>
     *                lookup()�߂�l:"return"<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Object:"return"<br>
     *         (��ԕω�) DefaultJndiSupport_JndiTemplate_Stub01.<br>
     *                    isCallLookup:true<br>
     *         (��ԕω�) DefaultJndiSupport_JndiTemplate_Stub01.<br>
     *                    jndiNameToUse:"java:comp/env/abc"<br>
     *
     * <br>
     * jndiPrefix��true�ł���A����name��"java:comp/env/"�Ŏn�܂�Ȃ������ꍇ�A
     * "java:comp/env/"������name�ɉ�����JndiTemplate.lookup()�̌Ăяo����
     * �s���Ă��邱�Ƃ��m�F���s��
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLookup03() throws Exception {
        // �O����
        DefaultJndiSupport support = new DefaultJndiSupport();

        String name = "abc";

        // JndiTemplate�擾
        DefaultJndiSupport_JndiTemplateStub01 template =
            new DefaultJndiSupport_JndiTemplateStub01();
        template.setCallLookup(false);
        template.setJndiNameToUse(null);

        support.setJndiTemplate(template);

        // super.resourceRef = true;
        support.setResourceRef(true);

        // �e�X�g���{
        Object result = support.lookup(name);

        // ����
        boolean b = template.isCallLookup();
        assertTrue(b);
        assertEquals("java:comp/env/abc", template.getJndiNameToUse());
        assertEquals("return", result);
    }

    /**
     * testLookup04()
     * <br><br>
     *
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) name:"java:comp/env/abc"<br>
     *         (���) JndiTemplate:DefaultJndiSupport_JndiTemplate_Stub02<br>
     *         (���) super.<br>
     *                resourceRef:false<br>
     *         (���) DefaultJndiSupport_JndiTemplate_Stub01.<br>
     *                isCallLookup:false<br>
     *         (���) DefaultJndiSupport_JndiTemplate_Stub01.<br>
     *                jndiNameToUse:null<br>
     *         (���) DefaultJndiSupport_JndiTemplate_Stub01.<br>
     *                lookup()�߂�l:"return"<br>
     *         (���) NamingException:����<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) DefaultJndiSupport_JndiTemplate_Stub01.<br>
     *                    isCallLookup:true<br>
     *         (��ԕω�) DefaultJndiSupport_JndiTemplate_Stub01.<br>
     *                    jndiNameToUse:"java:comp/env/abc"<br>
     *         (��ԕω�) ��O:JndiException�F<br>
     *                    ���b�v������O�FNamingException<br>
     *         (��ԕω�) ���O:�����O��<br>
     *                    �G���[���O�F<br>
     *                    "Illegal JNDI context name."<br>
     *
     * <br>
     * JndiTemplate.lookup()��NamingException�����������ꍇ�A
     * JndiException���N�������Ƃ��m�F����
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLookup04() throws Exception {
        // �O����
        DefaultJndiSupport support = new DefaultJndiSupport();

        String name = "java:comp/env/abc";

        // JndiTemplate�擾
        DefaultJndiSupport_JndiTemplateStub02 template =
            new DefaultJndiSupport_JndiTemplateStub02();
        template.setCallLookup(false);
        template.setJndiNameToUse(null);

        support.setJndiTemplate(template);

        // super.resourceRef = false;
        support.setResourceRef(false);

        try {
            // �e�X�g���{
            support.lookup(name);
            fail();
        } catch (JndiException e) {
            boolean b = template.isCallLookup();
            assertTrue(b);
            assertEquals("java:comp/env/abc", template.getJndiNameToUse());

            // ����
            assertTrue(LogUTUtil.checkError("Illegal JNDI context name."));
            assertEquals(NamingException.class.getName(),
                    e.getCause().getClass().getName());
        }
    }
}