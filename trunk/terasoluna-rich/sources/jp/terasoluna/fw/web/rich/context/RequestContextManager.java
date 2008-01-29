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

package jp.terasoluna.fw.web.rich.context;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ��������Ǘ�����}�l�[�W���B
 * 
 * <p>
 * ��������X���b�h�P�ʂŊǗ�����B
 * �ʏ�AWeb�R���e�i�ł́A1�̃��N�G�X�g�ɑ΂��鏈����1�̃X���b�h���s�����߁A
 * �������N�G�X�g�X�R�[�v����Ăяo���ꂽ�ꍇ�A�K������������ɑ΂��ď������s�����Ƃ��o����B
 * </p>
 * 
 * @see jp.terasoluna.fw.web.rich.context.RequestContext
 * @see jp.terasoluna.fw.web.rich.context.support.AbstractRequestContextSupport
 *
 */
public class RequestContextManager {
    /**
     * ���O�B
     */
    private static Log logger
        = LogFactory.getLog(RequestContextManager.class);

    /**
     * �X���b�h�P�ʂŃR���e�L�X�g���Ǘ�����ThreadLocal�B
     */
    private static ThreadLocal<RequestContext> resources
        = new ThreadLocal<RequestContext>();

    /**
     * ���s�X���b�h�ɑΉ����鐧������擾����B
     * �����񂪐ݒ�Ȃ��ꍇ�A��O����������B
     * @return ������
     */
    public static RequestContext getRequestContext() {
        RequestContext ctx = resources.get(); 
        if (ctx == null) {
            logger.error("No RequestContext bound to thread!");
            throw new IllegalStateException(
                    "No RequestContext  bound to thread ["
                        + Thread.currentThread().getName() + "]");
        }
        return ctx;
    }

    /**
     * ���s�X���b�h�ɑΉ����鐧���񂪓o�^����Ă��邩���肷��B
     * @return �o�^����Ă�����true
     */
    public static boolean hasRequestContext() {
        return (resources.get() != null);
    }

    /**
     * ���s�X���b�h�ɑΉ����鐧�����o�^����B
     * @param ctx ������B
     */
    public static void bindRequestContext(
            RequestContext ctx) {
        if (ctx == null) {
            logger.error("RequestContext cannot set null.");
            throw new IllegalArgumentException(
                    "RequestContext cannot set null.");
        }
        
        RequestContext alreadyBoundCtx = resources.get();
        
        // �X���b�h�ɐ����񂪐ݒ肳��Ă��Ȃ���΁A�����̐������ݒ肷��
        if (alreadyBoundCtx == null) {
            resources.set(ctx);
            if (logger.isDebugEnabled()) {
                logger.debug("Bound RequestContext ["
                        + ctx + "] to thread ["
                        + Thread.currentThread().getName() + "]");
            }
        } else {
            logger.error("Already RequestContext bound to thread!");
            throw new IllegalStateException("Already RequestContext ["
                    + alreadyBoundCtx + "]" + "   ["
                    + Thread.currentThread().getName() + "]");
        }
    }

    /**
     * ���s�X���b�h�ɑΉ����鐧������폜����B
     */
    public static void unbindRequestContext() {
        RequestContext ctx = resources.get();
        if (ctx == null) {
            logger.error("No RequestContext bound to thread!");
            throw new IllegalStateException(
                    "No RequestContext  bound to thread ["
                        + Thread.currentThread().getName() + "]");
        }
        
        resources.remove();

        if (logger.isDebugEnabled()) {
            logger.debug("Removed RequestContext [" + ctx
                + "] from thread ["
                + Thread.currentThread().getName() + "]");
        }
    }
}
