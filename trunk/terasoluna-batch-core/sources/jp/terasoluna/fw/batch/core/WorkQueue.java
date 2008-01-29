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


/**
 * ��ƃL���[�C���^�t�F�[�X�B
 * 
 * <p>��ƃL���[�ɂ́A�`�����N�i�r�W�l�X���W�b�N�̓��̓f�[�^�̏W�܂�j�A���邢��
 * �W���u�𕪊����邽�߂̕����L�[�Ȃǂ���ƒP�ʂƂ��ăL���[�C���O�����B</p>
 * 
 * <p>��ƃL���[�́A<code>JobManger</code> �ɂ���ăt�@�N�g����ʂ��č쐬����A
 * <code>Collector</code> �ɂ���Ď擾����ē��͂��A����<code>JobManger</code> 
 * �z����<code>Workable</code> �I�u�W�F�N�g�i<code>JobWorker</code> �Ȃǁj�ɓn��
 * ���������B</p>
 * 
 * <p>��ƃL���[�ɃG���L���[����̂́A�P��� <code>Collector</code> �ł��邪�A
 * ��ƃL���[����f�L���[����̂́A������ <code>Workable</code> �I�u�W�F�N�g��
 * �Ȃ�\��������B</p>
 * 
 */
public interface WorkQueue {

    /**
     * ��ƃL���[�����ƒP�ʂ��擾����B
     *
     * @return �L���[����擾������ƒP��
     */
    WorkUnit take();

    /**
     * ��ƃL���[���I������B
     *
     */
    void close();

    /**
     * ��ƃL���[�ɍ�ƒP�ʂ�ǉ�����B
     *
     * @param workUnit �ǉ������ƒP��
     */
    void put(WorkUnit workUnit);

    /**
     * ���̍�ƃL���[�̍�ƒP�ʂ��������郏�[�J�̏I����҂����킹��B
     */
    void waitForAllWorkers();
}
