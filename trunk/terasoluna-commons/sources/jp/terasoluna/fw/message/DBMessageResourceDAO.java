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

package jp.terasoluna.fw.message;

import java.util.List;

/**
 * DB�Ɋi�[����Ă��郁�b�Z�[�W���\�[�X�ɃA�N�Z�X���邽�߂�DAO�C���^�t�F�[�X�B
 * <br>
 * TERASOLUNA�ł͖{�N���X�̎����N���XDBMessageDAOImpl��񋟂���B
 * <br><br>
 * DAO��DB���烁�b�Z�[�W���\�[�X����������@�\��񋟂���B
 * 
 * @see jp.terasoluna.fw.message.DataSourceMessageSource
 * @see jp.terasoluna.fw.message.DBMessage
 * @see jp.terasoluna.fw.message.DBMessageResourceDAOImpl
 * @see jp.terasoluna.fw.message.DBMessageQuery
 *  
 */
public interface DBMessageResourceDAO {

    /**
     * DB���̑S�Ẵ��b�Z�[�W���\�[�X�����X�g�ŕԋp����B���X�g�̃p�����[�^�^��
     * DBMessage�ł���B���������ă��X�g������o���΂��̂܂�DBMessage
     * �I�u�W�F�N�g�Ƃ��ė��p�ł���B�ڂ�����DBMessage�N���X���Q�Ƃ̂��ƁB
     * 
     * @return ���b�Z�[�W���\�[�X�̃��X�g�B
     */
    List<DBMessage> findDBMessages();

}