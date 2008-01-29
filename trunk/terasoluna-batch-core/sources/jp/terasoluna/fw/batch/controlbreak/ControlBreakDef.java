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

package jp.terasoluna.fw.batch.controlbreak;

import java.util.List;

import jp.terasoluna.fw.batch.core.InitializeException;
import jp.terasoluna.fw.batch.openapi.ControlBreakHandler;
import jp.terasoluna.fw.batch.openapi.JobContext;

/**
 * <P>�R���g���[���u���C�N�L�[��`���B</P>
 * <P>���[�U����`����R���g���[���u���C�N�̃u���C�N�L�[�����i�[����N���X�B</P>
 * <P>�R���g���[���u���C�N�L�[���X�g�̓`�����N�R���g���[���u���C�N�L�[���X�g��
 *  �͈͂𒴂��邱�Ƃ͂ł��Ȃ��B</P>
 * <P>�g�����X�R���g���[���u���C�N�L�[���X�g�̓`�����N�R���g���[���u���C�N�L�[
 * ���X�g�� �܂ޒ�`�ɂ���K�v������B</P>
 * 
 */
public class ControlBreakDef {

    /**
     * �R���g���[���u���C�N�́u�u���C�N�L�[�A�n���h���v���X�g�B
     * �i�R���g���[���u���C�N�͈́F���j
     */
    private List<ControlBreakDefItem> controlBreakDefItemList = null;

    /**
     * �`�����N�R���g���[���u���C�N�́u�u���C�N�L�[�A�n���h���v�B
     * �i�R���g���[���u���C�N�͈́F���j
     */
    private ControlBreakDefItem chunkControlBreakDefItem = null;

    /**
     * �g�����X�`�����N�R���g���[���u���C�N�́u�u���C�N�L�[�A�n���h���v���X�g�B
     * �i�R���g���[���u���C�N�͈́F��j
     */
    private List<ControlBreakDefItem> transChunkControlBreakDefItemList = null;

    /**
     * <P>�����������B</P>
     * <P>�u���C�N�L�[�̐ݒ���e���`�F�b�N����B</P>
     * <P>�R���g���[���u���C�N�L�[���X�g���`�����N�R���g���[���u���C�N�L�[���X�g
     * ���܂߂ĂȂ��ꍇ��<code>InitializeException</code>�𔭐����������𒆒f��
     * ��B</P>
     * <P>�`�����N�R���g���[���u���C�N�L�[���X�g�̒�`������ꍇ�A 
     * �`���b�N�R���g���[���u���C�N�L�[���X�g���`�����N�R���g���[���u���C�N�L�[
     * ���X�g���܂߂ĂȂ��ꍇ��<code>InitializeException</code>�𔭐�����������
     * ���f����B</P>
     */
    public void init() {
        
        List breakKeyList = null;
        
        //�y�`�����N�R���g���[���u���C�N�L�[�z�̐ݒ肪�����A
        //�y�`�����N�R���g���[���u���C�N�n���h���z�̐ݒ肪�����ꍇ��
        //�y�g�����X�R���g���[���u���C�N�L�[�z�͐ݒ�ł��Ȃ�
        if (chunkControlBreakDefItem == null
                || (chunkControlBreakDefItem.getBreakKey().size() == 0 
                && chunkControlBreakDefItem.getControlBreakHandler() == null)) {
            if (transChunkControlBreakDefItemList != null
                    && transChunkControlBreakDefItemList.size() != 0) {
                throw new InitializeException(
                        "TransChunkControlBreakDefItemList setup of "
                        + "ChunkControlBreakDefItem is required to set up "
                        + "TransChunkControlBreakDefItemList.");
            }
        } else {
            breakKeyList = chunkControlBreakDefItem.getBreakKey();
        }

        // �y�`�����N�R���g���[���u���C�N�L�[�z�ɐݒ肳���ĂȂ��L�[���y�g�����X
        //  �R���g���[���u���C�N�L�[�z�ɐݒ肷�邱�Ƃ͂ł��Ȃ��B
        //  �g�����X�R���g���[���u���C�N�L�[�̐ݒ�͕�܊֌W�ł���K�v������B
        for (int i = 0; breakKeyList != null
                && transChunkControlBreakDefItemList != null
                && i < transChunkControlBreakDefItemList.size(); i++) {
            List checkBreakKeyList = transChunkControlBreakDefItemList.get(i)
                    .getBreakKey();

            List nextBreakKeyList = null;
            if (i + 1 < transChunkControlBreakDefItemList.size()) {
                nextBreakKeyList = transChunkControlBreakDefItemList.get(i + 1)
                        .getBreakKey();
            }

            for (int k = 0; k < checkBreakKeyList.size(); k++) {
                if (k >= breakKeyList.size()
                        || !checkBreakKeyList.get(k)
                                .equals(breakKeyList.get(k))) {
                    throw new InitializeException(
                            "It is necessary to define TransChunkControlBreak"
                            + "DefItemList within the limits of ChunkControl"
                            + "BreakDefItem");
                }
            }

            for (int k = 0; nextBreakKeyList != null
                    && k < nextBreakKeyList.size(); k++) {
                if (k >= checkBreakKeyList.size()) {
                    throw new InitializeException(
                            "A transChunkControlBreakDefItemList needs to"
                            + " define it as becoming the break of a higher "
                            + "rank from a low-ranking break.");
                }
                if (!checkBreakKeyList.get(k).equals(nextBreakKeyList.get(k))) {
                    throw new InitializeException(
                            "A setup of a BreakKey needs to be an inclusive "
                            + "relation.");
                }
            }

            // ����̃u���C�N�L�[��2�ȏ�R���g���[������`����Ă��邩�`�F�b�N
            if (nextBreakKeyList != null
                    && checkBreakKeyList.size() == nextBreakKeyList.size()) {
                int sameCount = 0;
                for (int m = 0; m < checkBreakKeyList.size(); m++) {
                    for (int n = 0; n < nextBreakKeyList.size(); n++) {
                        if (checkBreakKeyList.get(m).equals(
                                nextBreakKeyList.get(n))) {
                            sameCount++;
                            break;
                        }
                    }
                }
                if (checkBreakKeyList.size() == sameCount) {
                    throw new InitializeException(
                            "Two or more TransChunkControlBreak cannot"
                            + " be defined as the same Break key.");
                }
            }
        }

        // �y�R���g���[���u���C�N�L�[�z�́y�`�����N�R���g���[���u���C�N�L�[�z��
        //  ��܂����L�[���X�g�ł���K�v������B
        for (int i = 0; breakKeyList != null 
                && controlBreakDefItemList != null
                && i < controlBreakDefItemList.size(); i++) {
            List checkBreakKeyList = controlBreakDefItemList.get(i)
                    .getBreakKey();
            for (int k = 0; k < breakKeyList.size(); k++) {
                if (k >= checkBreakKeyList.size()
                        || !checkBreakKeyList.get(k)
                                .equals(breakKeyList.get(k))) {
                    throw new InitializeException(
                            "It is necessary to define ChunkControl"
                            + "BreakDefItem within the limits of Control"
                            + "BreakDefItemList");
                }
            }
        }

        // �R���g���[���u���C�N�L�[�̐ݒ�͕�܊֌W�ł���K�v������B
        for (int i = 0; controlBreakDefItemList != null
                && i < controlBreakDefItemList.size(); i++) {
            List checkBreakKeyList = controlBreakDefItemList.get(i)
                    .getBreakKey();
            List nextBreakKeyList = null;
            if (i + 1 < controlBreakDefItemList.size()) {
                nextBreakKeyList = controlBreakDefItemList.get(i + 1)
                        .getBreakKey();
            }

            for (int k = 0; nextBreakKeyList != null
                    && k < nextBreakKeyList.size(); k++) {
                if (k >= checkBreakKeyList.size()) {
                    throw new InitializeException(
                            "A controlBreakDefItemList needs to define"
                            + " it as becoming the break of a higher rank"
                            + " from a low-ranking break.");
                }
                if (!checkBreakKeyList.get(k).equals(nextBreakKeyList.get(k))) {
                    throw new InitializeException(
                            "A setup of a BreakKey needs to be an inclusive"
                            + " relation.");
                }
            }

            // ����̃u���C�N�L�[��2�ȏ�R���g���[������`����Ă��邩�`�F�b�N
            if (nextBreakKeyList != null
                    && checkBreakKeyList.size() == nextBreakKeyList.size()) {
                int sameCount = 0;
                for (int m = 0; m < checkBreakKeyList.size(); m++) {
                    for (int n = 0; n < nextBreakKeyList.size(); n++) {
                        if (checkBreakKeyList.get(m).equals(
                                nextBreakKeyList.get(n))) {
                            sameCount++;
                            break;
                        }
                    }
                }
                if (checkBreakKeyList.size() == sameCount) {
                    throw new InitializeException(
                            "Two or more ControlBreak cannot be defined"
                            + " as the same Break key.");
                }
            }
        }

    }

    /**
     * �g�����U�N�V���i���R���g���[���u���C�N�L�[���X�g���擾����B
     * 
     * @return �g�����U�N�V���i���R���g���[���u���C�N�L�[���X�g�B
     */
    public ControlBreakDefItem getChunkControlBreakDefItem() {
        return chunkControlBreakDefItem;
    }

    /**
     * �`�F�b�N�ʃR���g���[���u���C�N�L�[���X�g��ݒ肷��B
     * 
     * @param chunkControlBreakDefItem
     *            �`�F�b�N�ʃR���g���[���u���C�N�L�[���X�g�B
     */
    public void setChunkControlBreakDefItem(
            ControlBreakDefItem chunkControlBreakDefItem) {
        this.chunkControlBreakDefItem = chunkControlBreakDefItem;
    }

    /**
     * �P��R���g���[���u���C�N�L�[���X�g���擾����B
     * 
     * @return �`�F�b�N�ʃR���g���[���u���C�N�L�[���X�g�B
     */
    public List<ControlBreakDefItem> getControlBreakDefItemList() {
        return controlBreakDefItemList;
    }

    /**
     * �P��R���g���[���u���C�N�L�[���X�g���擾����B
     * 
     * @param controlBreakDefItemList
     *            �P��R���g���[���u���C�N�L�[���X�g�B
     */
    public void setControlBreakDefItemList(
            List<ControlBreakDefItem> controlBreakDefItemList) {
        this.controlBreakDefItemList = controlBreakDefItemList;
    }

    /**
     * �g�����R���g���[���u���C�N�L�[���X�g���擾����B
     * 
     * @return �g�����R���g���[���u���C�N�L�[���X�g�B
     */
    public List<ControlBreakDefItem> getTransChunkControlBreakDefItemList() {
        return transChunkControlBreakDefItemList;
    }

    /**
     * �g�����R���g���[���u���C�N�L�[���X�g���擾����B
     * 
     * @param transChunkControlBreakDefItemList
     *            �g�����R���g���[���u���C�N�L�[���X�g�B
     */
    public void setTransChunkControlBreakDefItemList(
            List<ControlBreakDefItem> transChunkControlBreakDefItemList) {
        this.transChunkControlBreakDefItemList = 
            transChunkControlBreakDefItemList;
    }

    /**
     * �g�����R���g���[���u���C�N�L�[���X�g�̃L�[���X�g�l���擾����B
     * 
     * @param transChunkControlBreakkey �g�����R���g���[���u���C�N�L�[���X�g��
     * �L�[
     * @return �g�����R���g���[���u���C�N�L�[���X�g�̃n���h�����X�g�l�B
     */
    public ControlBreakHandler<JobContext> getTransChunkControlBreakHandler(
            List<String> transChunkControlBreakkey) {
        if (transChunkControlBreakkey == null
                || transChunkControlBreakkey.size() == 0) {
            throw new NullPointerException("TransChunkControlBreakkey is Null");
        }

        if (transChunkControlBreakDefItemList == null
                || transChunkControlBreakDefItemList.size() == 0) {
            throw new IllegalArgumentException(
                    "ControlBreakHandler not found: "
                            + transChunkControlBreakkey.toString());
        }

        for (ControlBreakDefItem controlBreakDefItem
                : transChunkControlBreakDefItemList) {
            if (controlBreakDefItem.getBreakKey().equals(
                    transChunkControlBreakkey)) {
                return controlBreakDefItem.getControlBreakHandler();
            }
        }

        throw new IllegalArgumentException("ControlBreakHandler not found: "
                + transChunkControlBreakkey.toString());
    }

    /**
     * �g�����R���g���[���u���C�N�L�[���X�g�̃L�[���X�g�l���擾����B
     * 
     * @param controlBreakkey �g�����R���g���[���u���C�N�L�[���X�g�̃L�[
     * @return �g�����R���g���[���u���C�N�L�[���X�g�̃n���h�����X�g�l�B
     */
    public ControlBreakHandler<JobContext> getControlBreakHandler(
            List<String> controlBreakkey) {
        if (controlBreakkey == null || controlBreakkey.size() == 0) {
            throw new NullPointerException("ControlBreakkey is Null");
        }

        if (controlBreakDefItemList == null
                || controlBreakDefItemList.size() == 0) {
            throw new IllegalArgumentException(
                    "ControlBreakHandler not found: "
                            + controlBreakkey.toString());
        }

        for (ControlBreakDefItem controlBreakDefItem : controlBreakDefItemList)
        {
            if (controlBreakDefItem.getBreakKey().equals(controlBreakkey)) {
                return controlBreakDefItem.getControlBreakHandler();
            }
        }

        throw new IllegalArgumentException("ControlBreakHandler not found: "
                + controlBreakkey.toString());
    }
}
