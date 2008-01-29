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

/**
 * BLogic�C���^�t�F�[�X�����N���X�̃X�^�u�B
 */
public class BLogicController_BLogicImplStub06
    extends BLogicController_BLogicImplStub05<Integer, Long> {
    protected Object commandObj = null;

    /**
     * execute���\�b�h�̃X�^�u�B
     * @param command �p�����[�^�I�u�W�F�N�g�B
     * @return ������Ԃ��B
     */
    @Override
    public Long execute(Integer command) {
        this.commandObj = command;
        return null;
    }

}
