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

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.object.MappingSqlQuery;

/**
 * ���b�Z�[�W���\�[�X���擾����RDBMS�I�y���[�V�����N���X�B<br>
 * DB����擾�������b�Z�[�W���\�[�X��DBMessage�I�u�W�F�N�g�Ɋi�[���A�ԋp����B
 * <br>
 * DBMessage�I�u�W�F�N�g���ɂ̓��b�Z�[�W�R�[�h�A����R�[�h�A���R�[�h�A
 * �o���A���g�R�[�h�A���b�Z�[�W�{�̂��i�[�����B�������A����R�[�h�A���R�[�h
 * �y�уo���A���g�R�[�h�͕K�{�ł͂Ȃ��B���݂��Ȃ��ꍇ�́ADBMessage�I�u�W�F�N�g
 * �ԋp���ɊY��������null��ݒ肷��B
 * 
 * @see jp.terasoluna.fw.message.DataSourceMessageSource
 * @see jp.terasoluna.fw.message.DBMessage
 * @see jp.terasoluna.fw.message.DBMessageResourceDAO
 * @see jp.terasoluna.fw.message.DBMessageResourceDAOImpl
 * 
 */
public class DBMessageQuery extends MappingSqlQuery {
  
    /**
     * ���b�Z�[�W�R�[�h���i�[�������ʃZ�b�g�̃J�������B
     */
    protected String rsCodeColumn = null;
    
    /**
     * ���b�Z�[�W�̌���R�[�h���i�[�������ʃZ�b�g�̃J�������B
     */
    protected String rsLanguageColumn = null;
    
    /**
     * ���b�Z�[�W�̍��R�[�h���i�[�������ʃZ�b�g�̃J�������B
     */
    protected String rsCountryColumn = null;
    
    /**
     * ���b�Z�[�W�̃o���A���g�R�[�h���i�[�������ʃZ�b�g�̃J�������B
     */
    protected String rsVariantColumn = null;
    
    /**
     * ���b�Z�[�W�{�̂��i�[�������ʃZ�b�g�̃J�������B
     */
    protected String rsMessageColumn = null;
    
    /**
     * ���O�N���X�B
     */
    private static Log log = LogFactory.getLog(DBMessageQuery.class);
    
    /**
     * �R���X�g���N�^���Őe�N���X��SQL����n���A�R���p�C������������B
     * �R���p�C�������O�ɃJ�������ɕs���Ȓl���n����Ă��Ȃ������`�F�b�N����B
     * �K�{�J�������i���b�Z�[�W�R�[�h�A���b�Z�[�W�{�́j��null�`�F�b�N�y�ы󕶎�
     * �`�F�b�N�����{����B���̑��̃J�������͋󕶎��`�F�b�N�݂̂����{����B
     * 
     * @param ds
     *            ���b�Z�[�W���\�[�X���i�[�����f�[�^�Z�b�g�B
     * @param sql
     *            DB���烁�b�Z�[�W���\�[�X���擾����SQL���B
     * @param codeColumn
     *            ���b�Z�[�W�R�[�h���i�[���ꂽDB���̃J�������B
     *            ���݂��Ȃ��ꍇ�͌x�����o���B
     * @param languageColumn
     *            ���b�Z�[�W�̌���R�[�h���i�[���ꂽDB���̃J�������B
     *            �����ΏۂƂ��Ȃ��ꍇ��null�Ƃ���B
     * @param countryColumn
     *            ���b�Z�[�W�̍��R�[�h���i�[���ꂽDB���̃J�������B
     *            �����ΏۂƂ��Ȃ��ꍇ��null�Ƃ���B
     * @param variantColumn
     *            ���b�Z�[�W�̃o���A���g�R�[�h���i�[���ꂽDB���̃J�������B
     *            �����ΏۂƂ��Ȃ��ꍇ��null�Ƃ���B
     * @param messageColumn
     *            ���b�Z�[�W�{�̂��i�[���ꂽDB���̃J�������B
     *            ���݂��Ȃ��ꍇ�͌x�����o���B
     * 
     */
    public DBMessageQuery(DataSource ds, String sql, String codeColumn,
            String languageColumn, String countryColumn, String variantColumn,
            String messageColumn) {
        super(ds, sql);
        rsCodeColumn = codeColumn;
        rsLanguageColumn = languageColumn;
        rsCountryColumn = countryColumn;
        rsVariantColumn = variantColumn;
        rsMessageColumn = messageColumn;
        compile();
    }
    
    /**
     * DB����擾�������b�Z�[�W���\�[�X��DBMessage�I�u�W�F�N�g�Ɋi�[�A�ԋp����B
     * �����Ƃ��ēn���ꂽ���ʃZ�b�g�̌��ݍs�̓��e�����ɂ��č쐬����DBMessage
     * �I�u�W�F�N�g��Ԃ��B
     * 
     * @return ���b�Z�[�W���\�[�X���i�[����DBMessage�I�u�W�F�N�g
     * 
     * @param rs
     *            DB����擾�����l��ێ����錋�ʃZ�b�g
     * @param rowNum
     *            �������Ă��錋�ʃZ�b�g�̍s�ԍ�
     * 
     * @throws SQLException
     *             SQL��O
     */
    @Override
    protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        // ���b�Z�[�W�R�[�h�B
        String code = null;
        // ���b�Z�[�W�̌���R�[�h�B
        String language = null;
        // ���b�Z�[�W�̍��R�[�h�B
        String country = null;
        // ���b�Z�[�W�̃o���A���g�R�[�h�B
        String variant = null;
        // ���b�Z�[�W�{�́B
        String message = null;

        // ���b�Z�[�W�R�[�h�����݂��Ȃ��ꍇ�A�x������B
        code = rs.getString(rsCodeColumn);
        if (code == null) {
            code = "";
            if (log.isWarnEnabled()) {
                log.warn("MessageCode is null");
            }
        }

        // ����R�[�h�J���������݂��邪�A����R�[�h�����݂��Ȃ��ꍇ�A�󕶎�������B
        if (rsLanguageColumn != null) {
            language = rs.getString(rsLanguageColumn);
            if (language == null) {
                language = "";
            }
        }

        // ���R�[�h�J���������݂��邪�A���R�[�h�����݂��Ȃ��ꍇ�A�󕶎�������B
        if (rsCountryColumn != null) {
            country = rs.getString(rsCountryColumn);
            if (country == null) {
                country = "";
            }
        }

        // �o���A���g�R�[�h�J���������݂��邪�A�o���A���g�R�[�h�����݂��Ȃ��ꍇ�A
        // �󕶎�������B
        if (rsVariantColumn != null) {
            variant = rs.getString(rsVariantColumn);
            if (variant == null) {
                variant = "";
            }
        }

        // ���b�Z�[�W�{�̂����݂��Ȃ��ꍇ�A�󕶎�������B
        message = rs.getString(rsMessageColumn);
        if (message == null) {
            message = "";
        }
        
        if (log.isDebugEnabled()) {
            log.debug(code + "," + language + "," + country + "," + variant
                    + "," + message);
        }
        return new DBMessage(code, language, country, variant, message);
    }
}