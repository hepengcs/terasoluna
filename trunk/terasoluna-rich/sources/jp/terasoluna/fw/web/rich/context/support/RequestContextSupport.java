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

/**
 * ������������N���X���������ׂ��C���^�t�F�[�X�B
 * 
 * <p>
 * �t���[�����[�N��T�[�r�X�w�̃N���X�́A�{�C���^�t�F�[�X��
 * �Ăяo���āA��������Q�Ƃ��邱�Ƃ��o����B
 * </p>
 * 
 * <p>
 * �{�C���^�t�F�[�X���Ăяo���ۂ́A�{�C���^�t�F�[�X�̎����N���X��DI�R���e�i���ݒ肵�ė��p����B
 * �ʏ�͎����N���X�Ƃ��āADefaultRequestContextSupportImpl�𗘗p����΂悢�B
 * DefaultRequestContextSupportImpl�ł͋Ɩ��̗v�����������Ȃ��ꍇ�ɂ̂݁A
 * ���ۃN���XAbstractRequestContextSupport�܂��͖{�C���^�t�F�[�X����������
 * �Ɩ��v���𖞂����N���X���쐬���A���p���邱�ƁB
 * </p>
 * 
 * <p>
 * ��������Q�Ƃ���N���X�́A�ȉ��̂悤��Bean��`���邱�ƁB
 * </p>
 * 
 * <p>
 * �y<code>Bean��`�t�@�C��</code>�̐ݒ��z<br>
 * <code><pre>
 *  &lt;bean id="xxxx" class="xxxx.Xxx"&gt;
 *    &lt;property name="ctxSupport" ref="ctxSupport"/&gt;
 *  &lt;/bean&gt;
 *  ��ctxSupport�́ARequestContextSupport�����N���X��Bean��`�B
 *  �ڍׂ́A�{�C���^�t�F�[�X�̎����N���X���Q�Ƃ��邱�ƁB
 * </pre></code>
 * </p>
 * 
 * <p>
 * ��������Q�Ƃ���ۂ̎������A�ȉ��ɂ��߂��B
 * </p>
 * 
 * <p>
 * �y<code>Xxx.java</code>�̎�����z<br>
 * <code><pre>
 * public class Xxx {
 *   // �����Œ�`
 *   RequestContextSupport ctxSupport;
 *   // �Z�b�^�[��p��(DI�R���e�i�ɂ����s�����)
 *   public void setCtxSupport(RequestContextSupport ctxSupport) {
 *       this.ctxSupport = ctxSupport;
 *   }
 *   // ��������Q�Ƃ��郍�W�b�N
 *   protected void execute(String urlPath) {
 *   �@        �E�E�E�E
 *       String requestName = ctxSupport.getRequestName();
 *       �E�E�E�E
 *   }
 * }
 * </pre></code>
 * </p>
 */
public interface RequestContextSupport {
    /**
     *�@���N�G�X�g�����擾����B
     *
     * @return ���N�G�X�g��
     */
    String getRequestName();

    /**
     * �Ɩ��v���p�e�B���擾����B
     * �V�X�e�����ƂɓƎ��ɕێ����������́A���̃��\�b�h�Ŏ擾����B
     *
     * @param key �L�[
     * @return �Ɩ��v���p�e�B
     */
    Object getProperty(String key);


    /**
     * �ԋp�l�̌^���w�肵�āA�Ɩ��v���p�e�B���擾����B
     * �Ɩ����ƂɓƎ��ɕێ����������́A���̃��\�b�h�Ŏ擾����B
     *
     * @param key �L�[
     * @param <E> �ԋp�l�̌^
     * @return �Ɩ��v���p�e�B
     */
    <E> E getProperty(String key, Class<E> clazz);
    
    /**
     * String�^�̋Ɩ��v���p�e�B���擾����B
     * �Ɩ����ƂɓƎ��ɕێ����������́A���̃��\�b�h�Ŏ擾����B
     *
     * @param key �L�[
     * @return �Ɩ��v���p�e�B
     */
    String getPropertyString(String key);
    
    
    /**
     * ������𐶐�����B
     * @param request HTTP���N�G�X�g
     */
    void generateContext(HttpServletRequest request);
    
    /**
     * �������j������B
     */
    void destroyContext();
}
