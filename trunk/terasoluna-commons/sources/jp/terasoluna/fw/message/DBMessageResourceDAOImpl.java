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

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * DB���烁�b�Z�[�W���\�[�X���擾����DBMessageResourceDAO�̎����N���X�B
 * �{�N���X�́A���b�Z�[�W���\�[�X���i�[����DB���猟��SQL�����g�p���A
 * ���b�Z�[�W���\�[�X��DBMessage�I�u�W�F�N�g�̃��X�g�Ƃ��Ă܂Ƃ߁A�ԋp����B
 * <br><br>
 * <strong>�g�p���@</strong>
 * <br>
 * ���̃N���X���g�p����ɂ̓A�v���P�[�V�����R���e�L�X�g�N������DAO�Ƃ���
 * �F��������K�v������B<br>
 * <br>
 * <strong>�ݒ��</strong><br>
 * DAO�̎����N���X�Ƃ��Ė{�N���X���g�p����ꍇ�ABean��`�t�@�C����
 * �ȉ��̋L�q������B<br>
 * <pre>
 * &lt;bean id = &quot;dBMessageResourceDAO&quot;
 *   class = &quot;jp.terasoluna.fw.message.DBMessageResourceDAOImpl&quot;&gt;
 *   &lt;property name = &quot;dataSource&quot;&gt;
 *     &lt;ref bean = &quot;dataSource&quot;&gt;&lt;/ref&gt;
 *   &lt;/property&gt;
 * &lt;/bean&gt;
 * </pre>
 * 
 * <strong>���</strong><br>
 * &lt;bean&gt;�v�f��id������DBMessageResourceDAO���w�肵�A&lt;bean&gt;�v�f��
 * &lt;property&gt;�v�f�ɂ�dataSource��ݒ肷��B<br> 
 * 
 * <h3>����SQL���ɂ���</h3>
 * 
 * DB���烁�b�Z�[�W���\�[�X���擾���錟��SQL���ɂ͏����l���^�����Ă���B
 * �f�t�H���g�̌���SQL����
 * <pre>SELECT CODE,MESSAGE FROM MESSAGES</pre>
 * �ł���B
 * �f�t�H���g�̌���SQL�����g�p����ꍇ�ADB�̃J�������͈ȉ��̒ʂ�ƂȂ�B<br>
 * �e�[�u���� = MESSAGES<br>
 * ���b�Z�[�W�R�[�h���i�[����J������ = CODE<br>
 * ���b�Z�[�W�{�����i�[����J������ = MESSAGE<br>
 * ���A�f�t�H���g�̌���SQL�����g�p����ꍇ�A���P�[���Ή��͍s���Ȃ��B
 * ���P�[���Ή�����ꍇ�́A���L�A����SQL���̕ύX���K�v�ƂȂ�B
 * 
 * 
 * <h4>����SQL���̕ύX1</h4>
 * ���̕ύX���@�͌���SQL���̃t�H�[�}�b�g�ɂ��������āA�e�[�u�����y�ъe�J��������
 * �Ǝ��Ɏw�肵�A�{�N���X�̋@�\�ɂ���āA����SQL���𐶐�������@�ł���B���̕��@
 * �����{���邱�ƂŁA�ȉ��̂��Ƃ��\�ɂȂ�B<br>
 * �P�DDB�̃e�[�u�����y�ъe�J�������̎��R�Ȑݒ�<br>
 * �Q�D���P�[���Ή�<br>
 * ����SQL���̃t�H�[�}�b�g
 * <pre>SELECT ���b�Z�[�W�R�[�h�̃J������ , ����R�[�h�̃J������ , ���R�[�h�̃J������, �o���A���g�R�[�h�̃J������ ,���b�Z�[�W�{�̂̃J������ FROM �e�[�u����
 * </pre>
 * �e�[�u�����y�ъe�J�������̑S�Ă������͈ꕔ���w�肷�邱�Ƃ�DB�̃e�[�u�����y��
 * �J�����������R�ɐݒ�o����B�ꕔ�̒l�݂̂��w�肵���ꍇ�A�w�肳��Ă��Ȃ��l��
 * ��L�f�t�H���g�̌���SQL���̒l���g�p�����B<br>
 * ���A����R�[�h�̃J�������A���R�[�h�̃J�������A�o���A���g�R�[�h�̃J��������
 * �w�肷�邵�A�e�J������L���ɂ��邱�Ƃɂ��A�����̃R�[�h�ɂ�郍�P�[����
 * ���ʂ��\�ƂȂ�B
 * �����̒l�͖{�N���X���Ɏ�������Ă���e�X�̃Z�b�^�[�𗘗p���鎖�ŕύX�o����B
 * <br>
 * <br>
 * <strong>�ݒ��</strong><br>
 * Bean��`�t�@�C�����ňȉ��̂悤�ȋL�q������B<br>
 * �e�[�u�����y�ъe�J�������̑S�Ă�Ǝ��ɐݒ肷��ꍇ�B
 * 
 * <pre>
 * &lt;bean id = &quot;DBMessageResourceDAO&quot;
 *   class = &quot;jp.terasoluna.fw.message.DBMessageResourceDAOImpl&quot;&gt;
 *     &lt;ref bean = &quot;dataSource&quot;&gt;&lt;/ref&gt;
 *   &lt;/property&gt;
 *   &lt;property name = &quot;tableName&quot;&gt;
 *     &lt;value&gt;DBMESSAGES&lt;/value&gt;
 *   &lt;/property&gt;
 *   &lt;property name = &quot;codeColumn&quot;&gt;
 *     &lt;value&gt;BANGOU&lt;/value&gt;
 *   &lt;/property&gt;
 *   &lt;property name = &quot;languageColumn&quot;&gt;
 *     &lt;value&gt;GENGO&lt;/value&gt;
 *   &lt;/property&gt;
 *   &lt;property name = &quot;countryColumn&quot;&gt;
 *     &lt;value&gt;KUNI&lt;/value&gt;
 *   &lt;/property&gt;
 *   &lt;property name = &quot;variantColumn&quot;&gt;
 *     &lt;value&gt;HOUGEN&lt;/value&gt;
 *   &lt;/property&gt;
 *   &lt;property name = &quot;messageColumn&quot;&gt;
 *     &lt;value&gt;MESSAGE&lt;/value&gt;
 *   &lt;/property&gt;
 * &lt;/bean&gt;
 * </pre>
 * 
 * &lt;bean&gt;�v�f��&lt;properities&gt;�v�f��name�����ɕύX�������e�[�u������
 * �J���������w�肵�Avalue�����ɂĐݒ肵�����l���w�肷��B<br>
 * <br>
 * ��L�ݒ�ɂ�茟��SQL����
 * <pre>SELECT BANGOU,GENGO,KUNI,HOUGEN,HONBUN FROM DBMESSAGES</pre>
 * �ƂȂ�B<br>
 * �܂�DB�̃e�[�u�����y�уJ�������͈ȉ��̒ʂ�ƂȂ�B <br>
 * �e�[�u���� = DBMESSAGES<br>
 * ���b�Z�[�W�R�[�h���i�[����J������ = BANGOU<br>
 * ���b�Z�[�W�̌���R�[�h���i�[����J������ = GENGO<br>
 * ���b�Z�[�W�̍��R�[�h���i�[����J������ = KUNI<br>
 * ���b�Z�[�W�̃o���A���g�R�[�h���i�[����J������ = HOUGEN<br>
 * ���b�Z�[�W�{�����i�[����J������ = HONBUN<br>
 * 
 * 
 * <h4>����SQL���̕ύX2</h4>
 * ���̕ύX���@�͖{�N���X�̋@�\�ɂ�錟��SQL���̐������s�킸�ɁA����SQL����Ǝ�
 * �Ɏw�肷����@�ł���BWHERE��Ȃǌ���SQL���̃t�H�[�}�b�g�ł͑Ή��o���Ȃ�
 * �N�G���𗘗p����ꍇ�ɗL���ł���B<br>
 * <br>
 * <strong>�ݒ��</strong><br>
 * Bean��`�t�@�C�����ňȉ��̂悤�ȋL�q������B
 * ����SQL���y�уe�[�u�����A�e�J�������̑S�Ă�Ǝ��ɐݒ肷��ꍇ�B<br>
 * <br>
 * 
 * <pre>
 * &lt;bean id = &quot;DBMessageResourceDAO&quot;
 *   class = &quot;jp.terasoluna.fw.message.DBMessageResourceDAOImpl&quot;&gt;
 *   &lt;property name = &quot;dataSource&quot;&gt;
 *     &lt;ref bean = &quot;dataSource&quot;&gt;&lt;/ref&gt;
 *   &lt;/property&gt;
 *   &lt;property name = &quot;findMessageSql&quot;&gt;
 *     &lt;value&gt;
 *       SELECT BANGOU as CODE,HONBUN as MESSAGE FROM DBDATA WHERE CATEGORY = "DBMESSAGE"
 *     &lt;/value&gt;
 *   &lt;/property&gt;
 * &lt;/bean&gt;
 * </pre>
 * 
 * &lt;bean&gt;�v�f��&lt;properities&gt;�v�f��name�����Ɍ���SQL���Ǝg�p����
 * �J���������w�肵�Avalue�����ɂĐݒ肵����SQL�����w�肷��B<br>
 * <br>
 * SQL����<br>
 * �V�K�J������ as �f�t�H���g�̃J������<br>
 * �Ƃ��邱�Ƃ�DB�̃J��������ύX���邱�Ƃ��o����B
 * �������A�f�t�H���g�Œl���n����Ă��郁�b�Z�[�W�R�[�h�y�у��b�Z�[�W�̂Q�J����
 * �݂̂̑Ή��ƂȂ�B���P�[���Ή�����ꍇ�A���̑��̃J������ݒ肷��K�v������B
 * ���̏ꍇ�͌���SQL���̕ύX1�ɕ킢�A�J������L���ɂ���K�v������B<br>
 * <br>
 * ��L�ݒ�ɂ�茟��SQL����
 * <pre>SELECT BANGOU as CODE,HONBUN as MESSAGE FROM DBDATA WHERE CATEGORY = "DBMESSAGE"
 * </pre>
 * �ƂȂ�B<br>
 * 
 * �܂�DB�̃e�[�u�����y�уJ�������͈ȉ��̒ʂ�ƂȂ�B<br>
 * �e�[�u���� = DBDATA<br>
 * ���b�Z�[�W�R�[�h���i�[����J������ = BANGOU<br>
 * ���b�Z�[�W�̌���R�[�h���i�[����J������ = null<br>
 * ���b�Z�[�W�̍��R�[�h���i�[����J������ = null<br>
 * ���b�Z�[�W�̃o���A���g�R�[�h���i�[����J������ = null<br>
 * ���b�Z�[�W�{�����i�[����J������ = HONBUN<br>
 * 
 * @see jp.terasoluna.fw.message.DataSourceMessageSource
 * @see jp.terasoluna.fw.message.DBMessage
 * @see jp.terasoluna.fw.message.DBMessageQuery
 * @see jp.terasoluna.fw.message.DBMessageResourceDAO
 * 
 */
public class DBMessageResourceDAOImpl extends JdbcDaoSupport implements
        DBMessageResourceDAO {
    /**
     * ���b�Z�[�W���i�[����DB�̃e�[�u�����B�f�t�H���g��MESSAGES�B
     */
    protected String tableName = "MESSAGES";

    /**
     * ���b�Z�[�W�R�[�h���i�[����DB�̃J�������B�f�t�H���g��CODE�B
     */
    protected String codeColumn = "CODE";

    /**
     * ����R�[�h���i�[����DB�̃J�������B�f�t�H���g��null�B
     */
    protected String languageColumn = null;

    /**
     * ���R�[�h���i�[����DB�̃J�������B�f�t�H���g��null�B
     */
    protected String countryColumn = null;

    /**
     * �o���A���g�R�[�h���i�[����DB�̃J�������B�f�t�H���g��null�B
     */
    protected String variantColumn = null;

    /**
     * ���b�Z�[�W���i�[����DB�̃J�������B�f�t�H���g��MESSAGE�B
     */
    protected String messageColumn = "MESSAGE";

    /**
     * �O������ݒ肳���DB�������Ɏg�p�����SQL���B
     * �ݒ肳��Ă���ꍇ�A�����炪���s�����B
     */
    protected String findMessageSql = null;

    /**
     * ���O�N���X�B
     */
    private static Log log = LogFactory.getLog(DBMessageResourceDAOImpl.class);

    /**
     * ���b�Z�[�W���i�[����DB�̃e�[�u������ݒ肷��B�ݒ肳��Ă��Ȃ��ꍇ��
     * �f�t�H���g�̒lMESSAGES���g�p�����B
     * 
     * @param tableName
     *            ���b�Z�[�W���i�[����DB�̃e�[�u�����B
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * ���b�Z�[�W�R�[�h���i�[����DB�̃J��������ݒ肷��B�ݒ肳��Ă��Ȃ��ꍇ��
     * �f�t�H���g�̒lCODE���g�p�����B
     * 
     * @param codeColumn
     *            ���b�Z�[�W�R�[�h���i�[����DB�̃J�������B
     */
    public void setCodeColumn(String codeColumn) {
        this.codeColumn = codeColumn;
    }

    /**
     * ����R�[�h���i�[����DB�̃J��������ݒ肷��B�ݒ肳��Ă��Ȃ��ꍇ��
     * �f�t�H���g�̒lnull���g�p�����B
     * 
     * @param languageColumn
     *            ����R�[�h���i�[����DB�̃J�������B
     */
    public void setLanguageColumn(String languageColumn) {
        this.languageColumn = languageColumn;
    }

    /**
     * ���R�[�h���i�[����DB�̃J��������ݒ肷��B�ݒ肳��Ă��Ȃ��ꍇ��
     * �f�t�H���g�̒lnull���g�p�����B
     * 
     * @param countryColumn
     *            ���R�[�h���i�[����DB�̃J�������B
     */
    public void setCountryColumn(String countryColumn) {
        this.countryColumn = countryColumn;
    }

    /**
     * �o���A���g�R�[�h���i�[����DB�̃J��������ݒ肷��B�ݒ肳��Ă��Ȃ��ꍇ��
     * �f�t�H���g�̒lnull���g�p�����B
     * 
     * @param variantColumn
     *            �o���A���g�R�[�h���i�[����DB�̃J�������B
     */
    public void setVariantColumn(String variantColumn) {
        this.variantColumn = variantColumn;
    }

    /**
     * ���b�Z�[�W���i�[����DB�̃J��������ݒ肷��B�ݒ肳��Ă��Ȃ��ꍇ��
     * �f�t�H���g�̒lMESSAGE���g�p�����B
     * 
     * @param messageColumn
     *            ���b�Z�[�W���i�[����DB�̃J�������B
     */
    public void setMessageColumn(String messageColumn) {
        this.messageColumn = messageColumn;
    }

    /**
     * DB���烁�b�Z�[�W���\�[�X����������SQL����ݒ肷��B�ݒ肳��Ă��Ȃ��ꍇ��
     * makeSql���\�b�h�ɂč쐬���ꂽSQL�������s�����B
     * 
     * @param findMessageSql
     *            �O������ݒ肳���DB�������Ɏg�p�����SQL���B
     */
    public void setFindMessageSql(String findMessageSql) {
        this.findMessageSql = findMessageSql;
    }

    /**
     * ���b�Z�[�W���\�[�X���擾����RDBMS�I�y���[�V�����N���X�B
     */
    protected DBMessageQuery dBMessageQuery = null;
    
    /**
     * DBMessageResourceDAOImpl�𐶐�����B
     */
    protected DBMessageResourceDAOImpl() {
        super();
    }

    /**
     * DB��胁�b�Z�[�W���\�[�X���擾����DBMessageQuery�𐶐�����B
     * �R���X�g���N�^�ɓn�����l�̂����A���b�Z�[�W�R�[�h�̃J�������A
     * ����R�[�h�̃J�������A���R�[�h�̃J��������null�̏ꍇ������B<br>
     * null���n���ꂽ�ꍇ�A�����̃J������DB�ɑ��݂��Ȃ����̂Ƃ��ď��������B
     * 
     * @throws IllegalArgumentException
     *             DB�Ƃ̐ڑ����擾�ł��Ȃ������ꍇ
     */
    @Override
    protected void initDao() {
        DataSource dataSource = getDataSource();
        if (dataSource == null) {
            log.error("Missing dataSource in spring configuration file.");
            throw new IllegalArgumentException("Missing dataSource in spring"
                    + " configuration file.");
        }
        this.dBMessageQuery = new DBMessageQuery(dataSource, makeSql(),
                codeColumn, languageColumn, countryColumn, variantColumn,
                messageColumn);
    }

    /**
     * DB����擾�������b�Z�[�W���\�[�X��DBMessage�I�u�W�F�N�g�Ɋi�[���A���X�g�^
     * �ŕԋp����B
     * 
     * @return ���b�Z�[�W���\�[�X�̃��X�g
     */
    @SuppressWarnings("unchecked")
    public List<DBMessage> findDBMessages() {
        // JDBCDaoSupport�ɂ�DBMessageQuery���K����������邽�߁A
        // null�ɂ͂Ȃ�Ȃ��B
        return dBMessageQuery.execute();
    }

    /**
     * DB���烁�b�Z�[�W���\�[�X���擾����SQL���𐶐�����B
     * SQL�������O�ɃJ�������y�уe�[�u�����ɕs���Ȓl���n����Ă��Ȃ�����
     * �`�F�b�N������B�K�{�J�������i���b�Z�[�W�R�[�h�A���b�Z�[�W�{�́j
     * �ƃe�[�u������null�`�F�b�N�y�ы󕶎��`�F�b�N�����{����B
     * ���̑��̃J�������͋󕶎��`�F�b�N�݂̂����{����B
     * 
     * @return DB���烁�b�Z�[�W���\�[�X���擾����SQL���B
     *          null�͕ԋp���Ȃ��B
     * 
     */
    protected String makeSql() {
        // �J�������`�F�b�N
        checkRequiredColumnName(codeColumn, "codeColumn");
        checkNotRequiredColumnName(languageColumn, "languageColumn");
        checkNotRequiredColumnName(countryColumn, "countryColumn");
        checkNotRequiredColumnName(variantColumn, "variantColumn");
        checkRequiredColumnName(messageColumn, "messageColumn");
        checkRequiredColumnName(tableName, "tableName");
        // �O������SQL�����w�肳�ꂽ�ꍇ�A��������g�p����B
        StringBuilder sql = null;
        if (findMessageSql != null) {
            sql = new StringBuilder(findMessageSql);
        } else {
            // SQL���̎w�肪�Ȃ��ꍇ�A�V���ɐ�������B
            sql = new StringBuilder("SELECT ");
            sql.append(codeColumn);
            sql.append(",");
            // ����R�[�h�̃J���������A�ݒ肳��Ă��Ȃ��ꍇ��
            // ����R�[�h���������Ȃ��B
            if (languageColumn != null) {
                sql.append(languageColumn);
                sql.append(",");
            }
            // ���R�[�h�̃J���������A�ݒ肳��Ă��Ȃ��ꍇ�͍��R�[�h���������Ȃ��B
            if (countryColumn != null) {
                sql.append(countryColumn);
                sql.append(",");
            }
            // �o���A���g�R�[�h�̃J���������A�ݒ肳��Ă��Ȃ��ꍇ��
            // �o���A���g�R�[�h���������Ȃ��B
            if (variantColumn != null) {
                sql.append(variantColumn);
                sql.append(",");
            }
            sql.append(messageColumn);
            sql.append(" FROM ");
            sql.append(tableName);
        }
        if (log.isDebugEnabled()) {
            log.debug("sql=[" + sql + "]");
        }
        return sql.toString();
    }

    /**
     * �K�{�J�����̃J�������y�уe�[�u�������`�F�b�N����B
     * null�`�F�b�N�y�ы󕶎��`�F�b�N�����{����B
     * 
     * @param value
     *            DB�ł̃J�������������̓e�[�u����
     * @param columnName
     *            �����Ώۂ̃J�����������̓e�[�u��
     */
    protected void checkRequiredColumnName(String value, String columnName) {
        // �J�������̃G���[�`�F�b�N�B
        // ���b�Z�[�W�R�[�h�̃J��������null�������͋󕶎��̏ꍇ�A�G���[��Ԃ��B
        if (value == null || "".equals(value)) {
            log.error("illegalArgument: " + columnName + " is null or empty.");
            throw new IllegalArgumentException("illegalArgument: " + columnName
                    + " is null or empty.");
        }
    }

    /**
     * �K�{�J�����ȊO�̃J���������`�F�b�N����B �󕶎��`�F�b�N�����{����B
     * 
     * @param value
     *            DB�ł̃J������
     * @param columnName
     *            �����Ώۂ̃J����
     */
    protected void checkNotRequiredColumnName(String value, String columnName) {
        // �J�������̃G���[�`�F�b�N�B
        // ���b�Z�[�W�R�[�h�̃J���������󕶎��̏ꍇ�A�G���[��Ԃ��B
        if ("".equals(value)) {
            log.error("illegalArgument: " + columnName + " is empty.");
            throw new IllegalArgumentException("illegalArgument: " + columnName
                    + " is empty.");
        }
    }
}
