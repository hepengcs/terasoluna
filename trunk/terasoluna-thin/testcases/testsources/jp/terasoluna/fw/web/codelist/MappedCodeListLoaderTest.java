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

package jp.terasoluna.fw.web.codelist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.web.codelist.MappedCodeListLoader} �N���X
 * �̃u���b�N�{�b�N�X�e�X�g�B
 *
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * �R�[�h���X�g���̏�������Map�ōs���A
 * jp.terasoluna.fw.web.codelist.CodeListLoader�����N���X�B
 * <p>
 *
 * @see jp.terasoluna.fw.web.codelist.MappedCodeListLoader
 */
public class MappedCodeListLoaderTest extends TestCase {

    /**
     * �e�X�g�ΏہB
     */
    private MappedCodeListLoader test = null;

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     *
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(MappedCodeListLoaderTest.class);
    }

    /**
     * �������������s���B
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        test = new MappedCodeListLoader();
    }

    /**
     * �I���������s���B
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * �R���X�g���N�^�B
     *
     * @param name ���̃e�X�g�P�[�X�̖��O�B
     */
    public MappedCodeListLoaderTest(String name) {
        super(name);
    }

    /**
     * testGetCodeListMap01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) codeListMap:not null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) Map:�C���X�^���X�ϐ���codeListMap<br>
     *
     * <br>
     * �C���X�^���X�ϐ���codeListMap���擾�ł��邱�Ƃ��m�F����B<br>
     * ������n1���̂݊m�F�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetCodeListMap01() throws Exception {
        // �O����
        Map map = new HashMap();
        UTUtil.setPrivateField(test, "codeListMap", map);

        // �e�X�g���{
        Object result = test.getCodeListMap();

        // ����
        assertSame(map, result);
    }

    /**
     * testSetCodeListMap01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) codeListMap:not null<br>
     *         (���) codeListMap:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) codeListMap:not null�i�����Ɏw�肵��Map�j<br>
     *
     * <br>
     * �����Ɏw�肵��Map���C���X�^���X�ϐ�codeListMap�ɐݒ肳��邱�Ƃ��m�F����B<br>
     * ������n1���̂݊m�F�B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetCodeListMap01() throws Exception {
        // �O����
        UTUtil.setPrivateField(test, "codeListMap", null);

        // �e�X�g���{
        Map<String, String> map = new HashMap<String, String>();
        test.setCodeListMap(map);

        // ����
        Object result = UTUtil.getPrivateField(test, "codeListMap");
        assertSame(map, result);
    }

    /**
     * testLoad01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) codeLists:not null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) codeLists:�ω��Ȃ�<br>
     *
     * <br>
     * �C���X�^���X�ϐ���codeLists��not null�̏ꍇ�A�����s��ꂸ�������I�����邱�Ƃ��m�F����BcodeLists�ϐ��ɕω����Ȃ����Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLoad01() throws Exception {
        // �O����
        CodeBean cb = new CodeBean();
        cb.setId("id");
        cb.setName("name");
        List<CodeBean> list = new ArrayList<CodeBean>();
        list.add(cb);
        UTUtil.setPrivateField(test, "codeLists", list);

        // �e�X�g���{
        test.load();

        // ����
        List codeLists = (List) UTUtil.getPrivateField(test, "codeLists");
        assertSame(list, codeLists);
        assertEquals(1, codeLists.size());
        CodeBean codebean = (CodeBean) codeLists.get(0);
        assertEquals("id", codebean.getId());
        assertEquals("name", codebean.getName());
    }

    /**
     * testLoad02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) codeLists:null<br>
     *         (���) codeListMap:null<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) codeLists:�v�f����0��List<br>
     *
     * <br>
     * codeListMap��null�̏ꍇ�AcodeLists���v�f��0��List�Ƃ��Đݒ肳��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLoad02() throws Exception {
        // �O����
        UTUtil.setPrivateField(test, "codeLists", null);
        UTUtil.setPrivateField(test, "codeListMap", null);

        // �e�X�g���{
        test.load();

        // ����
        List result = (List) UTUtil.getPrivateField(test, "codeLists");
        assertTrue(result.isEmpty());
    }

    /**
     * testLoad03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(���) codeLists:null<br>
     *         (���) codeListMap:�v�f��0��Map<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) codeLists:�v�f����0��List<br>
     *
     * <br>
     * codeListMap�̗v�f����̏ꍇ�AcodeLists���v�f��0��List�Ƃ��Đݒ肳��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLoad03() throws Exception {
        // �O����
        UTUtil.setPrivateField(test, "codeLists", null);
        Map<String, String> map = new HashMap<String, String>();
        UTUtil.setPrivateField(test, "codeListMap", map);

        // �e�X�g���{
        test.load();

        // ����
        List result = (List) UTUtil.getPrivateField(test, "codeLists");
        assertTrue(result.isEmpty());
    }

    /**
     * testLoad04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(���) codeLists:null<br>
     *         (���) codeListMap:�v�f��1��Map<br>
     *                {"id","name"}<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) codeLists:�v�f����1��List<br>
     *                    CodeBean{"id","name"}<br>
     *
     * <br>
     * codeListMap��1���̗v�f������ꍇ�A����Map�̓��e��codeLists������������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLoad04() throws Exception {
        // �O����
        UTUtil.setPrivateField(test, "codeLists", null);
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", "name");
        UTUtil.setPrivateField(test, "codeListMap", map);

        // �e�X�g���{
        test.load();

        // ����
        List result = (List) UTUtil.getPrivateField(test, "codeLists");
        assertEquals(1, result.size());
        CodeBean codebean = (CodeBean) result.get(0);
        assertEquals("id", codebean.getId());
        assertEquals("name", codebean.getName());
    }

    /**
     * testLoad05()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(���) codeLists:null<br>
     *         (���) codeListMap:�v�f��3��Map<br>
     *                {"id1","name1"},<br>
     *                {"id2","name2"},<br>
     *                {"id3","name3"}<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) codeLists:�v�f����1��List<br>
     *                    CodeBean{"id1","name1"},<br>
     *                    CodeBean{"id2","name2"},<br>
     *                    CodeBean{"id3","name3"},<br>
     *
     * <br>
     * codeListMap�ɕ������̗v�f������ꍇ�A����Map�̓��e��codeLists������������邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testLoad05() throws Exception {
        // �O����
        UTUtil.setPrivateField(test, "codeLists", null);
        Map<String, String> map = new TreeMap<String, String>();
        map.put("id1", "name1");
        map.put("id2", "name2");
        map.put("id3", "name3");
        UTUtil.setPrivateField(test, "codeListMap", map);

        // �e�X�g���{
        test.load();

        // ����
        List result = (List) UTUtil.getPrivateField(test, "codeLists");
        assertEquals(3, result.size());

        // 1����
        CodeBean codebean = (CodeBean) result.get(0);
        assertEquals("id1", codebean.getId());
        assertEquals("name1", codebean.getName());

        // 2����
        codebean = (CodeBean) result.get(1);
        assertEquals("id2", codebean.getId());
        assertEquals("name2", codebean.getName());

        // 3����
        codebean = (CodeBean) result.get(2);
        assertEquals("id3", codebean.getId());
        assertEquals("name3", codebean.getName());
    }

    /**
     * testGetCodeBeans01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) codeLists:null<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) CodeBean[]:�v�f0��CodeBean[]<br>
     *
     * <br>
     * �C���X�^���X�ϐ�codeLists��null�̏ꍇ�A����0��CodeBean�z�񂪎擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetCodeBeans01() throws Exception {
        // �O����
        UTUtil.setPrivateField(test, "codeLists", null);

        // �e�X�g���{
        CodeBean[] result = test.getCodeBeans();

        // ����
        assertEquals(0, result.length);
    }

    /**
     * testGetCodeBeans02()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(���) codeLists:�v�f��0��List<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) CodeBean[]:�v�f0��CodeBean[]<br>
     *
     * <br>
     * �C���X�^���X�ϐ�codeLists������0��List�̏ꍇ�A����0��CodeBean�z�񂪎擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetCodeBeans02() throws Exception {
        // �O����
        List<CodeBean> list = new ArrayList<CodeBean>();
        UTUtil.setPrivateField(test, "codeLists", list);

        // �e�X�g���{
        CodeBean[] result = test.getCodeBeans();

        // ����
        assertEquals(0, result.length);
    }

    /**
     * testGetCodeBeans03()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(���) codeLists:�v�f��1<br>
     *                CodeBean{"id","name"}<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) CodeBean[]:�v�f1��CodeBean[]<br>
     *                  CodeBean{"id","name"}<br>
     *
     * <br>
     * �C���X�^���X�ϐ�codeLists������1��List�̏ꍇ�A���̗v�f��CodeBean�z�񂪎擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetCodeBeans03() throws Exception {
        // �O����
        List<CodeBean> list = new ArrayList<CodeBean>();
        CodeBean cb = new CodeBean();
        cb.setId("id");
        cb.setName("name");
        list.add(cb);
        UTUtil.setPrivateField(test, "codeLists", list);

        // �e�X�g���{
        CodeBean[] result = test.getCodeBeans();

        // ����
        assertEquals(1, result.length);
        assertEquals("id", result[0].getId());
        assertEquals("name", result[0].getName());
    }

    /**
     * testGetCodeBeans04()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FD
     * <br><br>
     * ���͒l�F(���) codeLists:�v�f��3<br>
     *                CodeBean{"id1","name1"},<br>
     *                CodeBean{"id2","name2"},<br>
     *                CodeBean{"id3","name3"}<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) CodeBean[]:�v�f3��CodeBean[]<br>
     *                  CodeBean{"id1","name1"},<br>
     *                  CodeBean{"id2","name2"},<br>
     *                  CodeBean{"id3","name3"}<br>
     *
     * <br>
     * �C���X�^���X�ϐ�codeLists�������̗v�f�����ꍇ�A���̗v�f��CodeBean�z�񂪎擾�ł��邱�Ƃ��m�F����B
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetCodeBeans04() throws Exception {
        // �O����
        List<CodeBean> list = new ArrayList<CodeBean>();
        CodeBean cb = new CodeBean();
        cb.setId("id1");
        cb.setName("name1");
        list.add(cb);
        cb = new CodeBean();
        cb.setId("id2");
        cb.setName("name2");
        list.add(cb);
        cb = new CodeBean();
        cb.setId("id3");
        cb.setName("name3");
        list.add(cb);
        UTUtil.setPrivateField(test, "codeLists", list);

        // �e�X�g���{
        CodeBean[] result = test.getCodeBeans();

        // ����
        assertEquals(3, result.length);

        //1����
        assertEquals("id1", result[0].getId());
        assertEquals("name1", result[0].getName());

        //2����
        assertEquals("id2", result[1].getId());
        assertEquals("name2", result[1].getName());

        //3����
        assertEquals("id3", result[2].getId());
        assertEquals("name3", result[2].getName());
    }

}
