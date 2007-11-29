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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.support.AbstractMessageSource;

/**
 * DAO����擾�������b�Z�[�W���\�[�X���A���b�Z�[�W�R�[�h�y�у��P�[�����L�[
 * �Ƃ��āA���b�Z�[�W�������̓��b�Z�[�W�t�H�[�}�b�g�����肷��N���X�B
 * 
 * <p>
 * �{�N���X�̓N���X���[�h����DB���Q�Ƃ��ADB���̃��b�Z�[�W���\�[�X���烁�b�Z�[�W
 * �������̓��b�Z�[�W�t�H�[�}�b�g�����肷��N���X�ł���B
 * �܂��A���ۉ��ɑΉ����Ă���A����R�[�h�A���R�[�h�A�o���A���g�R�[�h�ɂ��
 * ���P�[�����ʂ��\�ł���B
 * </p>
 * <strong>�g�p���@</strong><br>
 * ���̃N���X�𗘗p����ɂ̓A�v���P�[�V�����R���e�L�X�g�N������MessageSource
 * �Ƃ��Đݒ肵�A�܂����b�Z�[�W���\�[�X���i�[����DB�Ƃ̐ڑ�������
 * DAO�I�u�W�F�N�g�Ƃ��Đݒ肷��K�v������B<br>
 * <br>
 * <strong>�ݒ��</strong><br>
 * Bean��`�t�@�C���Ɉȉ��̓��e�̋L�q������B<br>
 * DAO�Ƃ���DBMessageResourceDAO�𗘗p�����ꍇ<br>
 * 
 * <pre>
 * &lt;bean id = &quot;messageSource&quot;
 *   class = &quot;jp.terasoluna.fw.message.DataSourceMessageSource&quot;&gt;
 *   &lt;property name = &quot;DBMessageResourceDAO&quot;&gt;
 *     &lt;ref bean = &quot;dBMessageResourceDAO&quot;&gt;&lt;/ref&gt;
 *   &lt;/property&gt;
 * &lt;/bean&gt;
 * </pre>
 * 
 * <strong>���</strong><br>
 * &lt;bean&gt;�v�f��id������"messageSource" ���w�肷�邱�Ƃ�MessageSource
 * �Ƃ��ĔF�������B<br>
 * &lt;bean>�v�f��&lt;property&gt;�v�f�ɂ�DAO�̐ݒ���L�q����B<br>
 * <br>
 * 
 * <br>
 * �f�t�H���g���P�[���̕ύX<br>
 * �f�t�H���g���P�[���́A���b�Z�[�W���\�[�X�̃��P�[�����ݒ肳��Ă��Ȃ��ꍇ�A
 * �������͐ݒ肳��Ă��Ă����������P�[�����ݒ肳��Ă��Ȃ��ꍇ�Ɏw�肳���
 * ���P�[���ł���B<br>
 * �f�t�H���g���P�[���̏����ݒ�́A�N���C�A���g��VM�Ŏg�p����郍�P�[���ł���B
 * <br>
 * �f�t�H���g���P�[���͖{�N���X���Ɏ�������Ă���setDefaultLocale�𗘗p����
 * ���ƂŕύX���邱�Ƃ��o����B <br>
 * <br>
 * <strong>�ݒ��</strong><br>
 * Bean��`�t�@�C�����Ɉȉ��̓��e�̋L�q������B<br>
 * �f�t�H���g���P�[������{��i����R�[�h�uja�v)�ɂ���ꍇ�B<br>
 * <br>
 * 
 * <pre>
 * &lt;bean id = &quot;messageSource&quot;
 *   class = &quot;jp.terasoluna.fw.message.DataSourceMessageSource&quot;&gt;
 *   &lt;property name = &quot;DBMessageResourceDAO&quot;&gt;
 *     &lt;ref bean = &quot;dBMessageResourceDAO&quot;&gt;&lt;/ref&gt;
 *   &lt;/property&gt;
 *   &lt;property name = &quot;defaultLocale&quot;&gt;
 *     &lt;value&gt;ja&lt;/value&gt;
 *   &lt;/property&gt;
 * &lt;/bean&gt;
 * </pre>
 * 
 * <strong>���</strong><br>
 * &lt;bean&gt;�v�f��&lt;properities&gt;�v�f��name������defaultLocale���w�肵�A
 * value�����ɂĐݒ肵�����l���w�肷��B
 * 
 * @see jp.terasoluna.fw.message.DBMessage
 * @see jp.terasoluna.fw.message.DBMessageQuery
 * @see jp.terasoluna.fw.message.DBMessageResourceDAO
 * @see jp.terasoluna.fw.message.DBMessageResourceDAOImpl
 * 
 * 
 */
public class DataSourceMessageSource extends AbstractMessageSource implements
        InitializingBean {

    /**
     * ���b�Z�[�W�R�[�h���Ƀ��P�[���ƃ��b�Z�[�W�t�H�[�}�b�g���}�b�v�ŕێ�����B
     * <br>
     * Map &lt;Code, Map &lt;Locale, MessageFormat&gt;&gt;
     */
    protected final Map<String, Map<Locale, MessageFormat>> cachedMessageFormats
                            = new HashMap<String, Map<Locale, MessageFormat>>();

    /**
     * ���P�[�����Ƀ��b�Z�[�W�R�[�h�ƃ��b�Z�[�W���}�b�v�ŕێ�����B
     * <br/> Map &lt;Locale, Properties&gt;
     */
    protected Map<Locale, Properties> cachedMergedProperties
                            = new HashMap<Locale, Properties>();

    /**
     * ���O�N���X�B
     */
    private static Log log = LogFactory.getLog(DataSourceMessageSource.class);
    
    /**
     * ���P�[�����w�肳��Ă��Ȃ��ꍇ�̃f�t�H���g���P�[���B ���b�Z�[�W���\�[�X��
     * �Ń��P�[�����w�肳��Ă��Ȃ��ꍇ�A ���̃��P�[�����ݒ肳���B
     * �f�t�H���g�ł̓T�[�o�[��JVM�̌���R�[�h�݂̂����P�[���Ƃ��Ďg�p����B
     */
    protected Locale defaultLocale
                            = new Locale(Locale.getDefault().getLanguage());
    
    /**
     * ���b�Z�[�W���\�[�X���擾����DAO�B
     */
    protected DBMessageResourceDAO dbMessageResourceDAO = null;

    /**
     * �f�t�H���g���P�[����ݒ肷��B�ݒ肵�Ȃ��ꍇ�̓N���C�A���g��VM�̃��P�[��
     * ���ݒ肳���BVM�̃��P�[�����F���ł��Ȃ��ꍇ�͉p�ꂪ�ݒ肳���B
     * 
     * @see #getMessageInternal
     * @see java.util.Locale#getDefault
     * 
     * @param defaultLocale
     *            �f�t�H���g�̃��P�[���B
     */
    public void setDefaultLocale(Locale defaultLocale) {
        this.defaultLocale = defaultLocale;
    }

    /**
     * DBMessageResourceDAO��ݒ肷��B
     * 
     * @param dbMessageResourceDAO
     *            �S�Ẵ��b�Z�[�W���\�[�X���擾����DAO
     */
    public void setDbMessageResourceDAO(
            DBMessageResourceDAO dbMessageResourceDAO) {
        this.dbMessageResourceDAO = dbMessageResourceDAO;
    }

    /**
     * Web�A�v���P�[�V�����R���e�L�X�g�N�����Ɏ��s�����B<br>
     * ���b�Z�[�W���\�[�X���烁�b�Z�[�W�R�[�h�A���P�[���A���b�Z�[�W
     * �i���b�Z�[�W�t�H�[�}�b�g�܂ށj�̂R���ڂŕ��ނ��A�L���b�V���ɕێ�����B
     * 
     * @see #cachedMergedProperties
     * 
     */
    public void afterPropertiesSet() {
        if (log.isDebugEnabled()) {
            log.debug("afterPropertiesSet");
        }
        readMessagesFromDataSource();
    }

    /**
     * ���b�Z�[�W���\�[�X�������[�h����B
     * ���̃��\�b�h�𖾎��I�ɌĂяo�����Ƃ�DB���瓮�I�Ƀ��b�Z�[�W���\�[�X��
     * �����[�h����BDB�̍X�V���������ꍇ�A���̃��\�b�h���Ăяo�����Ƃ�
     * ���b�Z�[�W���\�[�X�������[�h���邱�Ƃ��\�B
     */
    public synchronized void reloadDataSourceMessage() {
        readMessagesFromDataSource();
    }
    
    /**
     * DAO���烁�b�Z�[�W���\�[�X���擾���A��������B���b�Z�[�W���\�[�X�����P�[��
     * �ʂɂ܂Ƃ߁A���b�Z�[�W�R�[�h�ƃ��b�Z�[�W�{�̂��Z�b�g�ɂ��Ċi�[����B
     * �擾�����S�Ẵ��b�Z�[�W���\�[�X�ɑ΂��Ď��{����B<br>
     * ���b�Z�[�W���\�[�X�Ƃ́A���b�Z�[�W�R�[�h�A����R�[�h�A���R�[�h�A
     * �o���A���g�R�[�h�A���b�Z�[�W�{�̂ł���B
     */
     protected synchronized void readMessagesFromDataSource() {
        if (log.isDebugEnabled()) {
            log.debug("readMessageFromDataSource");
        }
        cachedMergedProperties.clear();
        cachedMessageFormats.clear();
        // DAO���烁�b�Z�[�W���\�[�X���擾����
        List<DBMessage> messages = dbMessageResourceDAO.findDBMessages();
        //���b�Z�[�W�R�[�h�ƃ��b�Z�[�W���e��null�ł͂Ȃ��ꍇ�A
        //�L���b�V���ɓǂݍ���
        for (DBMessage message : messages) {
            if (message.code != null && message.message != null) {
                mapMessage(message);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("get MessageResource from DAO.");
        }
    }

    /**
     * ���b�Z�[�W���\�[�X�����P�[���ʂɐ������A���b�Z�[�W�R�[�h�ƃ��b�Z�[�W�{��
     * ���Z�b�g�ɂ��āA�n�b�V���e�[�u���Ɋi�[����B
     * 
     * @param message
     *            ���b�Z�[�W���\�[�X���i�[����DBMessage�I�u�W�F�N�g�B
     */
    protected void mapMessage(DBMessage message) {
        // ���P�[���I�u�W�F�N�g������R�[�h�A���R�[�h�A�o���A���g�R�[�h����
        // ��������B
        Locale locale = createLocale(message);
        // ���P�[���ɑΉ�����S�Ẵ��b�Z�[�W���擾����B
        Properties messages = getMessages(locale);
        // �擾�����S�Ẵ��b�Z�[�W�ɐV�K���b�Z�[�W��ǉ�����B
        messages.setProperty(message.getCode(), message.getMessage());
        if (log.isDebugEnabled()) {
            log.debug("add Message[" + message.getMessage() + "] (code["
                    + message.getCode() + "], locale[" + locale + "])");
        }
    }

    /**
     * Locale�I�u�W�F�N�g�𐶐�����B<br>
     * ����R�[�h�A���R�[�h�A�o���A���g�R�[�h����Locale�I�u�W�F�N�g�𐶐�����B
     * ����R�[�h���^�����Ă��Ȃ��ꍇ�́A�f�t�H���g���P�[���̌���R�[�h�̂�
     * ���i�[���ALocale�I�u�W�F�N�g�𐶐�����B
     * 
     * @param message ���b�Z�[�W���\�[�X
     * 
     * @return
     *      ����R�[�h�A���R�[�h�A�o���A���g�R�[�h���i�[����Locale�I�u�W�F�N�g�B
     *
     * @throws IllegalArgumentException
     *      ���b�Z�[�W�R�[�h�y�у��b�Z�[�W�����݂��郁�b�Z�[�W���\�[�X��
     *      ���P�[�����ݒ肳��Ă��Ȃ��B���A�f�t�H���g���P�[�����ݒ�o���Ȃ�
     *      �ꍇ�̃G���[�B
     */
    protected Locale createLocale(DBMessage message) {
        if (message.getLanguage() == null) {
            if (defaultLocale != null) {
                return defaultLocale;
            }
            if (log.isErrorEnabled()) {
                log.error("Can't resolve Locale.Define Locale"
                        + " in MessageSource or Defaultlocale.");
            }
            throw new IllegalArgumentException("Can't resolve Locale."
                    + "Define Locale in MessageSource or Defaultlocale.");
        }
        if (message.getCountry() == null) {
            return new Locale(message.getLanguage());
        }
        if (message.getVariant() == null) {
            return new Locale(message.getLanguage(), message.getCountry());
        }
        return new Locale(message.getLanguage(), message.getCountry(),
                          message.getVariant());
    }

    /**
     * ���P�[���ɑΉ�����S�Ẵ��b�Z�[�W��ԋp����B �w�肳�ꂽ���P�[����
     * ���b�Z�[�W�����݂��Ȃ��ꍇ�͐V���ɐ������Anull��ԋp���Ȃ��B
     * 
     * @param locale
     *            ���b�Z�[�W�̃��P�[���B
     * 
     * @return ���P�[���ɑΉ������S�Ẵ��b�Z�[�W�B ���b�Z�[�W�R�[�h��
     * ���b�Z�[�W�{�̂��֘A�t�����A�i�[����Ă���B
     */
    protected Properties getMessages(Locale locale) {
        // ���P�[�����L�[�Ƃ��A�S�Ẵ��b�Z�[�W���擾���� �B
        Properties messages = cachedMergedProperties.get(locale);
        // ���P�[���ɑΉ������S�Ẵ��b�Z�[�W�����݂��Ȃ������ꍇ�A
        // �V���ɍ쐬���AcachedMergedProperties���Ɋi�[����B
        if (messages == null) {
            messages = new Properties();
            cachedMergedProperties.put(locale, messages);
        }
        return messages;
    }

    /**
     * �����Ƃ��ēn���ꂽ���b�Z�[�W�R�[�h�ƃ��P�[�����烁�b�Z�[�W�����肵�A
     * ���b�Z�[�W��ԋp����B�e�N���X����Ăяo����郁�\�b�h�B
     * AbstractMessageSource�̃��\�b�h���I�[�o�[���C�h���Ă���B
     * 
     * @param code
     *            ���b�Z�[�W�R�[�h
     * @param locale
     *            ���b�Z�[�W�̃��P�[��
     * 
     * @return ���b�Z�[�W�{��
     */
    @Override
    protected synchronized String resolveCodeWithoutArguments(
            String code,
            Locale locale) {
        String msg = internalResolveCodeWithoutArguments(code, locale);
        if (msg == null) {
            if (log.isDebugEnabled()) {
                log.debug("could not resolve [" + code + "] for locale ["
                        + locale + "]");
            }
        }
        return msg;
    }

    /**
     * ���b�Z�[�W�R�[�h�ƃ��P�[�����烁�b�Z�[�W�����肷��B �����Ƃ��ė^����ꂽ
     * ���P�[���Ń��b�Z�[�W�̌��肪�o���Ȃ������ꍇ�A���P�[����ω������A
     * ���b�Z�[�W�̎擾�����݂�B
     * �܂��A�f�t�H���g���P�[�����^�����Ă����ꍇ�A�f�t�H���g���P�[���ł�
     * ���b�Z�[�W�̌�����Ō�Ɏ��݂�B
     * 
     * @param code
     *            ���b�Z�[�W�R�[�h
     * @param locale
     *            ���b�Z�[�W�̃��P�[��
     * 
     * @return ���b�Z�[�W�{��
     */
    protected String internalResolveCodeWithoutArguments(
            String code,
            Locale locale) {
        // ���b�Z�[�W�R�[�h�ƃ��P�[���ɑΉ��������b�Z�[�W�{�̂�msg�Ɋi�[����B
        String msg = getMessages(locale).getProperty(code);
        // ���b�Z�[�W�{�̂̎擾���o�����ꍇ�A���b�Z�[�W�{�̂�ԋp����B
        if (msg != null) {
            return msg;
        }
        // ���b�Z�[�W�{�̂̎擾���o���Ȃ������ꍇ�A���P�[����ω�������
        // ���b�Z�[�W�{�̂̎擾�����݂�B

        // ���P�[���I�u�W�F�N�g�̃p�^�[���̐���
        List<Locale> locales = getAlternativeLocales(locale);
        // ���b�Z�[�W�R�[�h�ƐV���ɐ����������P�[���ɑΉ��������b�Z�[�W�����肵�A
        // ���b�Z�[�W�{�̂�ԋp���܂��B
        for (int i = 0; i < locales.size(); i++) {
            msg = getMessages(locales.get(i)).getProperty(code);
            if (msg != null) {
                return msg;
            }
        }
        // ���b�Z�[�W���擾�ł��Ȃ����ꍇ��null��ԋp����B
        return null;
    }

    /**
     * ���b�Z�[�W�����肷��ۂ̃L�[�𐶐�����B ���P�[���̒l����
     * ���P�[���I�u�W�F�N�g�𐶐����A���X�g�Ɋi�[�A�ԋp����B
     * �P�D����locale�̌���R�[�h�A���R�[�h�������́B�i�o���A���g�R�[�h���폜�B�j
     * �Q�D����locale�̌���R�[�h�������́B�i���R�[�h�A�o���A���g�R�[�h���폜�B�j
     * �R�D�f�t�H���g���P�[���̌���R�[�h�A���R�[�h�A�o���A���g�R�[�h�������́B
     * �S�D�f�t�H���g���P�[���̌���R�[�h�A���R�[�h�������́B
     * �T�D�f�t�H���g���P�[���̌���R�[�h�������́B
     * 
     * @param locale
     *            ���P�[���I�u�W�F�N�g
     * 
     * @return ���b�Z�[�W����̃L�[�ƂȂ郍�P�[���I�u�W�F�N�g
     */
    protected List<Locale> getAlternativeLocales(Locale locale) {
        List<Locale> locales = new ArrayList<Locale>();
        // ���P�[�����Ƀo���A���g�R�[�h�����݂���ꍇ
        if (locale.getVariant().length() > 0) {
            // Locale(language,country,"")��ݒ�
            locales.add(new Locale(locale.getLanguage(), locale.getCountry()));
        }
        // ���P�[�����ɍ��R�[�h�����݂���ꍇ
        if (locale.getCountry().length() > 0) {
            // Locale(language,"","")��ݒ�
            locales.add(new Locale(locale.getLanguage()));
        }
        // �f�t�H���g���P�[�����ݒ肳��Ă���ꍇ
        if (defaultLocale != null && !locale.equals(defaultLocale)) {
            if (defaultLocale.getVariant().length() > 0) {
                // Locale(language,country,"")��ݒ�
                locales.add(defaultLocale);
            }
            if (defaultLocale.getCountry().length() > 0) {
                // Locale(language,country,"")��ݒ�
                locales.add(new Locale(defaultLocale.getLanguage(),
                        defaultLocale.getCountry()));
            }
            // ���P�[�����ɍ��R�[�h�����݂���ꍇ
            if (defaultLocale.getLanguage().length() > 0) {
                // Locale(language,"","")��ݒ�
                locales.add(new Locale(defaultLocale.getLanguage()));
            }
        }
        return locales;
    }

    /**
     * �����Ƃ��ēn���ꂽ���b�Z�[�W�R�[�h�ƃ��P�[�����烁�b�Z�[�W�t�H�[�}�b�g��
     * ���肵�A���b�Z�[�W�t�H�[�}�b�g��ԋp����B
     * �e�N���X����Ăяo����郁�\�b�h�BAbstractMessageSource�̃��\�b�h��
     * �I�[�o�[���C�h���Ă���B
     * 
     * @param code
     *            ���b�Z�[�W�R�[�h
     * @param locale
     *            ���b�Z�[�W�̃��P�[��
     * 
     * @return ���b�Z�[�W�t�H�[�}�b�g
     */
    @Override
    protected synchronized MessageFormat resolveCode(
            String code,
            Locale locale) {
        // ���b�Z�[�W�R�[�h�ƃ��P�[���ɑΉ��������b�Z�[�W�{�̂�messageFormat��
        // �i�[����B
        MessageFormat messageFormat = getMessageFormat(code, locale);
        // ���b�Z�[�W�{�̂̎擾���o�����ꍇ�A���b�Z�[�W�t�H�[�}�b�g��ԋp����
        if (messageFormat != null) {
            if (log.isDebugEnabled()) {
                log.debug("resolved [" + code + "] for locale [" + locale
                        + "] => [" + messageFormat + "]");
            }
            return messageFormat;
        }
        // ���b�Z�[�W�t�H�[�}�b�g�̎擾���o���Ȃ������ꍇ�A���P�[����ω�������
        // ���b�Z�[�W�t�H�[�}�b�g�̎擾�����݂�B

        // ���P�[���I�u�W�F�N�g�̃p�^�[���̐���
        List<Locale> locales = getAlternativeLocales(locale);
        // ���b�Z�[�W�R�[�h�ƐV���ɐ����������P�[���ɑΉ�����
        // ���b�Z�[�W�t�H�[�}�b�g�����肵�A���b�Z�[�W�t�H�[�}�b�g��ԋp���܂��B
        for (int i = 0; i < locales.size(); i++) {
            messageFormat = getMessageFormat(code, locales.get(i));
            if (messageFormat != null) {
                if (log.isDebugEnabled()) {
                    log.debug("resolved [" + code + "] for locale [" + locale
                            + "] => [" + messageFormat + "]");
                }
                return messageFormat;
            }
        }
        if (messageFormat == null) {
            if (log.isDebugEnabled()) {
                log.debug("could not resolve [" + code + "] for locale ["
                        + locale + "]");
            }
        }
        // ���b�Z�[�W�t�H�[�}�b�g���擾�o���Ȃ������ꍇ��null��ԋp����B
        return null;
    }

    /**
     * �����Ƃ��ēn���ꂽ���b�Z�[�W�R�[�h�ƃ��P�[�����烁�b�Z�[�W�t�H�[�}�b�g
     * �����肷��B
     * 
     * @param code
     *            ���b�Z�[�W�R�[�h
     * @param locale
     *            ���b�Z�[�W�̃��P�[��
     * 
     * @return ���肳�ꂽ���b�Z�[�W�t�H�[�}�b�g
     */
    protected MessageFormat getMessageFormat(String code, Locale locale) {
        // ���b�Z�[�W�R�[�h�ɑΉ��������P�[���}�b�v���擾����B
        Map<Locale, MessageFormat> localeMap
                                   = this.cachedMessageFormats.get(code);
        // ���P�[���}�b�v�����݂����ꍇ�A���P�[���}�b�v��胍�P�[���ɑΉ�����
        // ���b�Z�[�W�t�H�[�}�b�g���擾�A�ԋp����B
        if (localeMap != null) {
            MessageFormat result = localeMap.get(locale);
            if (result != null) {
                return result;
            }
        }
        
        String msg = getMessages(locale).getProperty(code);

        // ���b�Z�[�W�����݂���ꍇ
        if (msg != null) {

            // ���P�[���}�b�v�����݂��Ȃ��ꍇ�A�V���Ƀ��P�[���}�b�v�𐶐����A
            // ���b�Z�[�W�t�H�[�}�b�g��ԋp����B
            if (localeMap == null) {
                localeMap = new HashMap<Locale, MessageFormat>();
                this.cachedMessageFormats.put(code, localeMap);
            }
            // ���b�Z�[�W�ƃ��P�[����胁�b�Z�[�W�t�H�[�}�b�g���쐬����B
            MessageFormat result = createMessageFormat(msg, locale);
            localeMap.put(locale, result);
            return result;
        }
        // ���b�Z�[�W�t�H�[�}�b�g���擾�o���Ȃ������ꍇ��null��ԋp����B
        return null;
    }
}