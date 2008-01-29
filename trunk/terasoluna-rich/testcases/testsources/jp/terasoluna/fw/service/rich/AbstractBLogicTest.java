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

package jp.terasoluna.fw.service.rich;

import jp.terasoluna.fw.dao.QueryDAO;
import jp.terasoluna.fw.dao.StoredProcedureDAO;
import jp.terasoluna.fw.dao.UpdateDAO;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.fw.service.rich.AbstractBLogic;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.service.rich.AbstractBLogic}
 * �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * DAO�̃A�N�Z�T���\�b�h������BLogic�̒��ۃN���X�B
 * <p>
 * 
 * @see jp.terasoluna.fw.service.rich.AbstractBLogic
 */
public class AbstractBLogicTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(AbstractBLogicTest.class);
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
    public AbstractBLogicTest(String name) {
        super(name);
    }

    /**
     * testSetQueryDAO01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) queryDAO:QueryDAO�C���X�^���X<br>
     *         (���) queryDAO:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) queryDAO:�������Ɠ���̃C���X�^���X<br>
     *         
     * <br>
     * �������̒l�𐳏�ɑ����ɐݒ肷�邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetQueryDAO01() throws Exception {
        // �O����
        // QueryDAO�����N���X
        QueryDAO dao = new QueryDAOImpl01();
        
        // AbstractBLogic�g���N���X
        AbstractBLogic blogic = new AbstractBLogic_AbstractBLogicStub01();
        UTUtil.setPrivateField(blogic, "queryDAO", null);
        
        // �e�X�g���{
        blogic.setQueryDAO(dao);

        // ����
        assertSame(dao, UTUtil.getPrivateField(blogic, "queryDAO"));
    }

    /**
     * testGetQueryDAO01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) �Ȃ�:�|<br>
     *         (���) queryDAO:QueryDAO�C���X�^���X<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) QueryDAO:�����Ɠ���̃C���X�^���X<br>
     *         
     * <br>
     * �����ɐݒ肳��Ă���l�𐳏�Ɏ擾���邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetQueryDAO01() throws Exception {
        // �O����
        // QueryDAO�����N���X
        QueryDAO dao = new QueryDAOImpl01();
        
        // AbstractBLogic�g���N���X
        AbstractBLogic blogic = new AbstractBLogic_AbstractBLogicStub01();
        UTUtil.setPrivateField(blogic, "queryDAO", dao);
        
        // �e�X�g���{
        QueryDAO getDao = blogic.getQueryDAO();

        // ����
        assertSame(dao, getDao);
    }

    /**
     * testSetUpdateDAO01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) updateDAO:UpdateDAO�C���X�^���X<br>
     *         (���) updateDAO:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) updateDAO:�������Ɠ���̃C���X�^���X<br>
     *         
     * <br>
     * �������̒l�𐳏�ɑ����ɐݒ肷�邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetUpdateDAO01() throws Exception {
        // �O����
        // UpdateDAO�����N���X
        UpdateDAO dao = new UpdateDAOImpl01();
        
        // AbstractBLogic�g���N���X
        AbstractBLogic blogic = new AbstractBLogic_AbstractBLogicStub01();
        UTUtil.setPrivateField(blogic, "updateDAO", null);
        
        // �e�X�g���{
        blogic.setUpdateDAO(dao);

        // ����
        assertSame(dao, UTUtil.getPrivateField(blogic, "updateDAO"));
    }

    /**
     * testGetUpdateDAO01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) �Ȃ�:�|<br>
     *         (���) updateDAO:UpdateDAO�C���X�^���X<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) UpdateDAO:�����Ɠ���̃C���X�^���X<br>
     *         
     * <br>
     * �����ɐݒ肳��Ă���l�𐳏�Ɏ擾���邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetUpdateDAO01() throws Exception {
        // �O����
        // UpdateDAO�����N���X
        UpdateDAO dao = new UpdateDAOImpl01();
        
        // AbstractBLogic�g���N���X
        AbstractBLogic blogic = new AbstractBLogic_AbstractBLogicStub01();
        UTUtil.setPrivateField(blogic, "updateDAO", dao);
        
        // �e�X�g���{
        UpdateDAO getDao = blogic.getUpdateDAO();

        // ����
        assertSame(dao, getDao);
    }

    /**
     * testSetStoredProcedureDAO01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) storedProcedureDAO:storedProcedureDAO�C���X�^���X<br>
     *         (���) storedProcedureDAO:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) storedProcedureDAO:�������Ɠ���̃C���X�^���X<br>
     *         
     * <br>
     * �������̒l�𐳏�ɑ����ɐݒ肷�邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetStoredProcedureDAO01() throws Exception {
        // �O����
        // StoredProcedureDAO�����N���X
        StoredProcedureDAO dao = new StoredProcedureDAOImpl01();
        
        // AbstractBLogic�g���N���X
        AbstractBLogic blogic = new AbstractBLogic_AbstractBLogicStub01();
        UTUtil.setPrivateField(blogic, "storedProcedureDAO", null);
        
        // �e�X�g���{
        blogic.setStoredProcedureDAO(dao);

        // ����
        assertSame(dao, UTUtil.getPrivateField(blogic, "storedProcedureDAO"));
    }

    /**
     * testGetStoredProcedureDAO01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) �Ȃ�:�|<br>
     *         (���) storedProcedureDAO:storedProcedureDAO�C���X�^���X<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) StoredProcedureDAO:�����Ɠ���̃C���X�^���X<br>
     *         
     * <br>
     * �����ɐݒ肳��Ă���l�𐳏�Ɏ擾���邱�ƁB
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetStoredProcedureDAO01() throws Exception {
        // �O����
        // StoredProcedureDAO�����N���X
        StoredProcedureDAO dao = new StoredProcedureDAOImpl01();
        
        // AbstractBLogic�g���N���X
        AbstractBLogic blogic = new AbstractBLogic_AbstractBLogicStub01();
        UTUtil.setPrivateField(blogic, "storedProcedureDAO", dao);
        
        // �e�X�g���{
        StoredProcedureDAO getDao = blogic.getStoredProcedureDAO();

        // ����
        assertSame(dao, getDao);
    }
}