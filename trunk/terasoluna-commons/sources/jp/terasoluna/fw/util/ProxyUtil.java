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

package jp.terasoluna.fw.util;

import java.lang.reflect.Proxy;

import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;

/**
 *  �v���L�V�֘A�̃��[�e�B���e�B�N���X�B
 *
 */
public class ProxyUtil {
    /**
     * �v���L�V�̃^�[�Q�b�g�N���X���擾����B
     * @param proxy �v���L�V�I�u�W�F�N�g�B
     * @return �w�肵���v���L�V�̃^�[�Q�b�g�N���X�B
     */
    public static Class getTargetClass(Object proxy) {
        // Null�`�F�b�N
        if (proxy == null) {
            throw new IllegalArgumentException("Proxy object is null.");
        }
        if (AopUtils.isCglibProxy(proxy)) {
            return proxy.getClass().getSuperclass();
        }
        if (proxy instanceof Advised) {
            // �擾�����^�[�Q�b�g���v���L�V�̏ꍇ�A�l�X�g�����^�[�Q�b�g���擾����
            do {
                proxy = getAdvisedTarget((Advised) proxy);
            } while (Proxy.isProxyClass(proxy.getClass()));
        }
        return proxy.getClass();
    }
    /**
     * �v���L�V�̃^�[�Q�b�g���擾����B
     * @param advised �I�u�W�F�N�g�B
     * @return �w�肵���v���L�V�̃^�[�Q�b�g�I�u�W�F�N�g�B
     * @throws CannotGetTargetException 
     */
    private static Object getAdvisedTarget(Advised advised) {
        try {
            return advised.getTargetSource().getTarget();
        } catch (Exception e) {
            // �^�[�Q�b�g���擾�ł��Ȃ������ꍇ�̗�O�����B
            throw new CannotGetTargetException(e);
        }
    }
}
