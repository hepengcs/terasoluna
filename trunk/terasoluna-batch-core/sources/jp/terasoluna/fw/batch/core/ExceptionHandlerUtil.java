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

package jp.terasoluna.fw.batch.core;

import java.util.Map;


/**
 * ��O�n���h���̃��[�e�B���e�B�N���X�B
 * 
 * <p>�t���[�����[�N���A�K�v�ȗ�O�n���h�����擾����ۂɃ��[�e�B���e�B�Ƃ���
 * ���p����B</p>
 * 
 * <p><code>JobExceptionHandler</code> �C���^�t�F�[�X������������O�n���h������
 * ���p���邱�Ƃ�z�肵�����[�e�B���e�B�ł͂Ȃ����Ƃɗ��ӂ��邱�ƁB</p>
 * 
 */
public final class ExceptionHandlerUtil {

    /**
     * �R���X�g���N�^�B
     *
     */
    private ExceptionHandlerUtil() {
    }

    /**
     * �w�肳�ꂽ��O�ɑΉ������O�n���h�����擾����B
     *
     * @param e �n���h�����O�Ώۂ̗�O
     * @param exceptionHandlerMap ��O�n���h�����i�[����Map
     * @param defaultJobExceptionHandler �f�t�H���g��O�n���h��
     * @return �n���h�����O�Ώۂ̗�O�ɑΉ�������O�n���h��
     */
    public static JobExceptionHandler getJobExceptionHandler(Exception e,
            Map<JobException, JobExceptionHandler> exceptionHandlerMap,
            JobExceptionHandler defaultJobExceptionHandler) {
        JobExceptionHandler handler = null;
        for (Map.Entry<JobException, JobExceptionHandler> entry
                : exceptionHandlerMap.entrySet()) {
            JobException entryJobException = entry.getKey();
            if (entryJobException.getClass().isAssignableFrom(e.getClass())) {
                handler = entry.getValue();
                break;
            }
        }
        if (handler == null) {
            handler = defaultJobExceptionHandler;
        }
        return handler;
    }

}
