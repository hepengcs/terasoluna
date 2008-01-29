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



/**
 * DAO�̃A�N�Z�T���\�b�h������BLogic�̒��ۃN���X�B
 * 
 * <p>
 * DAO��getter/setter���\�b�h�����B
 * �����N���X�́A�{�N���X���p�ӂ���getter���\�b�h�𗘗p���āA
 * DAO���Q�Ƃ��邱�Ƃ��o����B
 * </p>
 * 
 * <p>
 * �ʏ�͖{�N���X�𗘗p�����A
 * BLogic�C���^�t�F�[�X�𒼐ڎ�������BLogic�N���X���쐬���A
 * DI�R���e�i���K�v��DAO����ѐ�����T�|�[�g�N���X��ݒ肷�邱�ƁB
 * DAO��getter/setter���\�b�h���Ɩ��J���҂ɋL�q���������Ȃ��ꍇ�̂݁A
 * �{�N���X����������BLogic���쐬�����p���邱�ƁB
 * </p>
 * 
 * <p>
 * �{�N���X�𗘗p����ۂ�Bean��`���K�v�ł���B
 * ������DAO��DI�R���e�i���ݒ肷�邱�ƁB
 * �P��̃f�[�^�x�[�X�𗘗p���邱�Ƃ�O��Ƃ��Ă��邽�߁A�ݒ�ł���DAO�͊e��ł���B
 * </p>
 * 
 * @param <P> �r�W�l�X���W�b�N�̈����̌^�B
 * @param <R> �r�W�l�X���W�b�N�̖߂�l�̌^�B
 *
 */
public abstract class AbstractBLogic<P, R> implements BLogic<P, R> {
    /**
     * �Q�ƌnDAO�B
     */
    private QueryDAO queryDAO = null;
    
    /**
     * �X�V�nDAO
     */
    private UpdateDAO updateDAO = null;

    /**
     * �X�g�A�h�v���V�[�W��DAO�B
     */
    private StoredProcedureDAO storedProcedureDAO = null;

    /**
     * �Q�ƌnDAO��ݒ肷��B
     * @param queryDAO �Q�ƌnDAO�B
     */
    public void setQueryDAO(QueryDAO queryDAO) {
        this.queryDAO = queryDAO;
    }

    /**
     * �Q�ƌnDAO���擾����B
     * @return queryDAO �Q�ƌnDAO�B
     */
    protected QueryDAO getQueryDAO() {
        return queryDAO;
    }

    /**
     * �X�V�nDAO��ݒ肷��B
     * @param updateDAO �X�V�nDAO�B
     */
    public void setUpdateDAO(UpdateDAO updateDAO) {
        this.updateDAO = updateDAO;
    }

    /**
     * �X�V�nDAO���擾����B
     * @return updateDAO �X�V�nDAO�B
     */
    protected UpdateDAO getUpdateDAO() {
        return updateDAO;
    }

    /**
     * �X�g�A�h�v���V�[�W��DAO��ݒ肷��B
     * @param storedProcedureDAO �X�g�A�h�v���V�[�W��DAO�B
     */
    public void setStoredProcedureDAO(StoredProcedureDAO storedProcedureDAO) {
        this.storedProcedureDAO = storedProcedureDAO;
    }

    /**
     * �X�g�A�h�v���V�[�W��DAO���擾����B
     * @return storedProcedureDAO �X�g�A�h�v���V�[�W��DAO�B
     */
    protected StoredProcedureDAO getStoredProcedureDAO() {
        return storedProcedureDAO;
    }
}
