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

package jp.terasoluna.fw.web.rich.springmvc.controller;

import jp.terasoluna.fw.service.rich.AbstractBLogic;

/**
 * BLogic�C���^�t�F�[�X�����N���X�̃X�^�u�B
 */
public class BLogicController_BLogicImplStub02
    extends AbstractBLogic<Integer, Long> {
    protected Object command = null;

    /**
     * execute���\�b�h�̃X�^�u�B
     * @param command �p�����[�^�I�u�W�F�N�g�B
     * @return ������Ԃ��B
     */
    public Long execute(@SuppressWarnings("hiding") Integer command) {
        this.command = command;
        return null;
    }
    
    /**
     * �_�~�[���\�b�h
     * @param dummy1
     * @param dummy2
     * @return
     */
    public Object execute(@SuppressWarnings("unused") Object dummy1, 
    		@SuppressWarnings("unused") Object dummy2) {
        return null;
    }
}