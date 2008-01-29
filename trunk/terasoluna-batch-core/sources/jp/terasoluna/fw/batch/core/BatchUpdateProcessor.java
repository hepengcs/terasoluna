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

import java.util.LinkedHashMap;
import java.util.List;

/**
 * �o�b�`�X�V�v���Z�b�T�̃C���^�t�F�[�X�B
 * 
 * <p>BLogic����˗����ꂽ�o�b�`�X�V�������s���o�b�`�X�V�v���Z�b�T����������
 * �C���^�t�F�[�X�B</p>
 * 
 * <p>�o�b�`�X�V�̎��s�Ɏ��s�����ꍇ�ɂ́A��O <code>BatchUpdateException</code>
 * ���X���[����B</p>
 *
 */
public interface BatchUpdateProcessor {

    /**
     * �����Ŏw�肳�ꂽ�o�b�`�X�V���X�g����������B
     * 
     * @param batchUpdateMapList �o�b�`�X�V���X�g
     */
    void processBatchUpdate(
            List<LinkedHashMap<String, Object>> batchUpdateMapList);
}
