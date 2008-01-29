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

package jp.terasoluna.fw.web.rich.context.support;

import javax.servlet.http.HttpServletRequest;

import jp.terasoluna.fw.web.rich.context.RequestContext;
import jp.terasoluna.fw.web.rich.context.RequestContextManager;
import jp.terasoluna.fw.web.rich.context.exception.IllegalContextPropertyClassTypeException;

/**
 * ��������������߂̕⏕���W�b�N�C���^�t�F�[�X���p���������ۃN���X�B
 * 
 * <p>
 * �Ɩ��R���e�L�X�g�}�l�[�W�����Ɩ��R���e�L�X�g���擾���A�K�v�ȏ���ԋp����B
 * </p>
 * 
 * <p>
 * RequestContext, RequestContextManager�𗘗p����
 * �����������������񋟂��Ă���B
 * �ڍׂ́ARequestContext, RequestContextManager���Q�Ƃ��邱�ƁB
 * </P>
 * 
 * <p>
 * ������ɐݒ肷��l��HTTP���N�G�X�g����擾����B
 * HTTP���N�G�X�g����擾���鍀�ڂ́A�V�X�e���̗v���ɂ���ĕω����邽�߁A
 * ���N�G�X�g�������N�G�X�g�w�b�_�ȊO�̉ӏ�����擾����ꍇ��A
 * �Ɩ��v���p�e�B��ێ��������ꍇ���́A
 * �K�v�ɉ����ăV�X�e�����Ƃɖ{�N���X�̎����N���X���쐬���A
 * �����񏉊����̒��ۃ��\�b�h���������邱�ƁB
 * </p>
 * 
 * <p>
 * �V�X�e���Ƃ��ē��ɏ�L�̂悤�ȗv�����Ȃ��ꍇ�́A�f�t�H���g�����ł���A
 * DefaultRequestContextSupportImpl�𗘗p���邱�ƁB
 * </p>
 * 
 * <p>
 * �{�N���X�𗘗p����ꍇ�A�����N���X��Bean��`���邱�ƁB
 * ��`��́A�����N���X���Q�Ƃ��邱�ƁB
 * �܂��A�ȉ��̃v���p�e�B��K���ݒ肷�邱�ƁB
 * </p>
 * 
 * @see jp.terasoluna.fw.web.rich.context.RequestContext
 * @see jp.terasoluna.fw.web.rich.context.RequestContextManager
 * 
 */
public abstract class AbstractRequestContextSupport
        implements RequestContextSupport {

    /**
     * ���s����Ɩ����W�b�N�����ʂ��郊�N�G�X�g�����擾����B
     *
     * @return ���N�G�X�g��
     */
    public String getRequestName() {
        return getRequestContext().getRequestName();
    }

    /**
     * �Ɩ��v���p�e�B���擾����B
     * �Ɩ����ƂɓƎ��ɕێ����������́A���̃��\�b�h�Ŏ擾����B
     *
     * @param key �L�[
     * @return �Ɩ��v���p�e�B
     */
    public Object getProperty(String key) {
        return getRequestContext().getProperty(key);
    }
    
    /**
     * �ԋp�l�̌^���w�肵�āA�Ɩ��v���p�e�B���擾����B
     * �Ɩ����ƂɓƎ��ɕێ����������́A���̃��\�b�h�Ŏ擾����B
     * �擾����Ɩ��v���p�e�B�̌^�Ǝw�肳�ꂽ�^��������ꍇ�́A
     * ���s����O�𔭐�������B
     *
     * @param key �L�[
     * @param <E> �ԋp�l�̌^
     * @return �Ɩ��v���p�e�B
     */
    public <E> E getProperty(String key, Class<E> clazz) {
        E propertyObject = null;
        
        if (clazz == null) {            
        	// �^�p�����[�^���w�肳��Ă��Ȃ�
            String message = "Must not use null for clazz of an argument.";
        	throw new IllegalArgumentException(message);
        }
        
        try {
        	propertyObject = clazz.cast(getProperty(key));
        } catch (ClassCastException e) {
            throw new IllegalContextPropertyClassTypeException(e);
        }
    	
        return propertyObject;
    }
    
    /**
     * String�^�̋Ɩ��v���p�e�B���擾����B
     * �Ɩ����ƂɓƎ��ɕێ����������́A���̃��\�b�h�Ŏ擾����B
     *
     * @param key �L�[
     * @return �Ɩ��v���p�e�B
     */
    public String getPropertyString(String key) {
        return getProperty(key,String.class);
    }
    
    /**
     * �Ɩ��R���e�L�X�g�𐶐�����B
     * @param request HTTP���N�G�X�g
     */
    public void generateContext(HttpServletRequest request) {
        RequestContext ctx = doGenerateContext(request);
        RequestContextManager.bindRequestContext(ctx);
    }
    
    /**
     * ������𐶐��E���������ĕԋp����B
     * @param request HTTP���N�G�X�g
     * @return ����������̐�����B
     */
    protected abstract RequestContext doGenerateContext(
                                    HttpServletRequest request);
    
    /**
     * �������j������B
     *
     */
    public void destroyContext() {
        if (RequestContextManager.hasRequestContext()) {
            RequestContextManager.unbindRequestContext();
        }
    }
    
    /**
     * ��������}�l�[�W�����擾����B
     * @return ������
     */
    protected RequestContext getRequestContext() {
        return RequestContextManager.getRequestContext();
    }
}
