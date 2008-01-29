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

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.terasoluna.fw.batch.core.Chunk;
import jp.terasoluna.fw.batch.core.JobException;
import jp.terasoluna.fw.batch.openapi.JobContext;

import org.apache.commons.collections.FastHashMap;
import org.apache.commons.lang.ArrayUtils;

/**
 * <p>�R���g���[���u���C�N�p�`�����N�N���X�B</p>
 * <p>�`�����N�R���g���[���u���C�N�L�[���̃`�����N�𐶐����A�L���[�ɒǉ�����B
 * ���̃N���X�ł͐ݒ肳�ꂽ�L�[����͂��A�`�����N�R���g���[���u���C�N��
 * �������Ă��邩�ǂ����𔻒肷��B</p>
 * <p>���f�������ʂ��`�����N��R���g���[���u���C�NRow�f�[�^�Ɏ������A
 * <code>ControlBreakWorker</code>�A
 * <code>ControlBreakBLogicExecutor</code>���ł��ꂼ��u���C�N�������N��������B
 * 
 * 
 */
public class ControlBreakChunk extends Chunk {

    /**
     * �`�����N�̏I�[�p�I�u�W�F�N�g�B
     */
    public static final Object END_MARK = new Object();
    
    
    /**
     * �`�����N�̃u���C�N�}�[�N�p�I�u�W�F�N�g�B
     */
    public static final Object CHUNK_BREAK_MARK = new Object();
    
    /**
     * �R���g���[���u���C�N�L�[���X�g�B�i���j
     */
    private final List<ControlBreakDefItem> controlBreakDefItemList;

    /**
     * �`�����N�R���g���[���u���C�N�L�[���X�g�B�i���j
     */
    private final ControlBreakDefItem chunkControlBreakDefItem;

    /**
     * �g�����X�R���g���[���u���C�N�L�[���X�g�B�i��j
     */
    private final List<ControlBreakDefItem> transChunkControlBreakDefItemList;
    
    /**
     * �������̃f�[�^�ŁA�`�����N�R���g���[���u���C�N���������Ă��邩�ǂ�����
     * �����B
     */
    private boolean chunkControlBreak = false;
    
    /**
     * �`�����N�R���g���[���u���C�N�L�[�}�b�v�B
     * �ΏۃI�u�W�F�N�g�Ɋւ��āA�������̃I�u�W�F�N�g�̑�����ێ�����}�b�v�B
     * �`�����N�R���g���[���u���C�N�L�[���\������v���p�e�B�݂̂�ێ�����B
     */
    private Map<String, Object> chunkControlBreakMap = null;

    /**
     * �ΏۃI�u�W�F�N�g�Ɋւ��āA���̃I�u�W�F�N�g�̑�����ێ�����}�b�v�B
     * �`�����N�R���g���[���u���C�N�L�[���\������v���p�e�B�݂̂�ێ�����B
     */
    private Map<String, Object> chunkNextData = null;

    /**
     * �R���g���[���u���C�N�L�[�}�b�v�B
     * �ΏۃI�u�W�F�N�g�Ɋւ��āA�������̃I�u�W�F�N�g�̑�����ێ�����}�b�v�B
     * �R���g���[���u���C�N�L�[���\������v���p�e�B�݂̂�ێ�����B
     */
    private Map<String, Object> controlBreakMap = null;

    /**
     * �ΏۃI�u�W�F�N�g�Ɋւ��āA���̃I�u�W�F�N�g�̑�����ێ�����}�b�v�B
     * �R���g���[���u���C�N�L�[���\������v���p�e�B�݂̂�ێ�����B
     */
    private Map<String, Object> nextData = null;

    /**
     * �g�����X�R���g���[���u���C�N�L�[�B
     */
    private List<List<String>> transChunkControlBreakKeyList;
    
    /**
     * �ΏۃI�u�W�F�N�g�Ɋւ��āA���̃I�u�W�F�N�g�̑�����ێ�����}�b�v�B
     * �g�����X�R���g���[���u���C�N�L�[���\������v���p�e�B�݂̂�ێ�����B
     */
    private LinkedHashMap<String, Object> transNextData;

    /**
     * �ŏI�`�����N�t���O�B
     */
    private boolean endChunk = false;
    
    /**
     * Method��cache
     */
    private FastHashMap methodMap = null;
    
    /**
     * �R���X�g���N�^
     *  
     * @param jobContext �W���u�R���e�L�X�g
     * @param controlBreakDef �R���g���[���u���C�N�̒�`���
     * @param chunkSize �`�����N�T�C�Y
     */
    public ControlBreakChunk(JobContext jobContext,
            ControlBreakDef controlBreakDef, int chunkSize) {
        super(chunkSize, jobContext);
        this.controlBreakDefItemList = controlBreakDef
                .getControlBreakDefItemList();
        this.chunkControlBreakDefItem = controlBreakDef
                .getChunkControlBreakDefItem();
        this.transChunkControlBreakDefItemList = controlBreakDef
                .getTransChunkControlBreakDefItemList();
        this.transChunkControlBreakKeyList = new ArrayList<List<String>>(
                transChunkControlBreakDefItemList.size());

        chunkNextData = new LinkedHashMap<String, Object>(
                chunkControlBreakDefItem.getBreakKey().size());
        transNextData = new LinkedHashMap<String, Object>(chunkNextData.size());

    }

    /**
     * �R���X�g���N�^ 
     * 
     * @param jobContext JobContext
     * @param controlBreakDef �R���g���[���u���C�N��`
     * @param chunkSize �`�����N�T�C�Y
     * @param nextData �O�`�����N�̃R���g���[���u���C�N�L�[�̍\�����
     * @param chunkNextData �O�`�����N�̃`�����N�R���g���[���u���C�N�L�[�̍\��
     * ���
     */
    public ControlBreakChunk(JobContext jobContext,
            ControlBreakDef controlBreakDef, int chunkSize,
            Map<String, Object> nextData, Map<String, Object> chunkNextData) {
        super(chunkSize, jobContext);
        this.controlBreakDefItemList = controlBreakDef
                .getControlBreakDefItemList();
        this.chunkControlBreakDefItem = controlBreakDef
                .getChunkControlBreakDefItem();
        this.transChunkControlBreakDefItemList = controlBreakDef
                .getTransChunkControlBreakDefItemList();
        this.transChunkControlBreakKeyList = new ArrayList<List<String>>(
                transChunkControlBreakDefItemList.size());

        this.nextData = nextData;
        controlBreakMap = nextData;

        chunkControlBreakMap = chunkNextData;
        this.chunkNextData = new LinkedHashMap<String, Object>(
                chunkControlBreakDefItem.getBreakKey().size());
        transNextData = new LinkedHashMap<String, Object>(chunkNextData.size());

    }
    
    /**
     * ���f�[�^��ݒ肷��B
     * 
     * @param next ���f�[�^
     * @return �R���g���[���u���C�N�pRow�I�u�W�F�N�g�N���X
     */
    public ControlBreakRowObject setNext(Object next) {
        controlBreakMap = nextData;

        transChunkControlBreakKeyList.clear();

        // �`�����N�R���g���[���u���C�N�L�[�}�b�v�̏�����
        chunkNextData.clear();

        List<List<String>> controlBreakKeyList = new ArrayList<List<String>>(
                controlBreakDefItemList.size());

        // �g�����X�`�����N�R���g���[���u���C�N�̃u���C�N�L�`�F�b�N�p
        transNextData.clear();
        
        //�`�����N�R���g���[���u���C�N�̔����𔻒f���A���ʂ��`�����N�ɂɒǉ�����B
        if (chunkControlBreakDefItem.getBreakKey().size() > 0) {
            for (String propertyName : chunkControlBreakDefItem.getBreakKey()) {
                Object value = getBreakKeyValue(next, propertyName);
                chunkNextData.put(propertyName, value);
                
                // �`�����N�R���g���[���u���C�N�L�[���\������v���p�e�B����
                // �ǂꂩ�ЂƂł��l���ς���Ă���ꍇ�ɂ́A
                // �`�����N�R���g���[���u���C�N�L�[���������Ă���B
                if (chunkControlBreakMap != null && value != null
                        && !value.equals(
                                chunkControlBreakMap.get(propertyName))) {
                    chunkControlBreak = true;
                    //�g�����X�`�����N�R���g���[���̃u���C�N���
                    transNextData.put(propertyName, value);
                }
            }
        }
        // �`�����N�R���g���[���u���C�N������ʂ̃R���g���[���u���C�N��
        // �������Ă��邩�m�F����B
        if (chunkControlBreak) {
            checkTransChunkControlBreak(transNextData.keySet());
        }
        
        if (chunkControlBreakMap == null) {
            chunkControlBreakMap = chunkNextData;
            chunkNextData = new LinkedHashMap<String, Object>(
                    chunkControlBreakDefItem.getBreakKey().size());
        }
        
        // �R���g���[���u���C�N�̔����𔻒f���A���ʂ�Row�f�[�^�ɒǉ�����B
        if (controlBreakDefItemList.size() > 0) {
            nextData = new LinkedHashMap<String, Object>(
                    controlBreakDefItemList.size());

            for (ControlBreakDefItem controlBreakDefItem
                    : controlBreakDefItemList) {
                boolean controlBreak = false;
                for (String propertyName : controlBreakDefItem.getBreakKey()) {
                    Object value = getBreakKeyValue(next, propertyName);
                    nextData.put(propertyName, value);
                    // �`�����N�R���g���[���u���C�N�L�[���\������v���p�e�B����
                    // �ǂꂩ�ЂƂł��l���ς���Ă���ꍇ�ɂ́A
                    // �`�����N�R���g���[���u���C�N�L�[���������Ă���B
                    if (!controlBreak && controlBreakMap != null
                            && value != null
                            && !value.equals(controlBreakMap.get(propertyName)))
                    {
                        controlBreak = true;
                    }
                }
                if (controlBreak) {
                    controlBreakKeyList.add(controlBreakDefItem.getBreakKey());
                }
            }
        }

        return new ControlBreakRowObject(next, controlBreakKeyList,
                controlBreakMap);
    }
    
    /**
     * �ŏI�f�[�^�̃R���g���[���u���C�N���̍Đݒ�
     * @param last �ŏI�f�[�^
     * @return �R���g���[���u���C�N�pRow�I�u�W�F�N�g�N���X
     */
    public ControlBreakRowObject setLastData(Object last) {
        transChunkControlBreakKeyList.clear();

        // �`�����N�R���g���[���u���C�N�L�[�}�b�v�̏�����
        chunkNextData = new LinkedHashMap<String, Object>(
                chunkControlBreakDefItem.getBreakKey().size());
        nextData = new LinkedHashMap<String, Object>(controlBreakDefItemList
                .size());
        chunkControlBreak = true;

        List<List<String>> controlBreakKeyList = new ArrayList<List<String>>(
                controlBreakDefItemList.size());

        // �`�����N�R���g���[���u���C�N�̔����𔻒f���A���ʂ��`�����N�ɂɒǉ�����B
        for (String propertyName : chunkControlBreakDefItem.getBreakKey()) {
            Object value = getBreakKeyValue(last, propertyName);
            chunkNextData.put(propertyName, value);
        }
        checkTransChunkControlBreak(chunkNextData.keySet());

        // �R���g���[���u���C�N�̔����𔻒f���A���ʂ�Row�f�[�^�ɒǉ�����B
        if (controlBreakDefItemList.size() > 0) {
            for (ControlBreakDefItem controlBreakDefItem
                    : controlBreakDefItemList) {
                for (String propertyName : controlBreakDefItem.getBreakKey()) {
                    Object value = getBreakKeyValue(last, propertyName);
                    nextData.put(propertyName, value);
                    controlBreakKeyList.add(controlBreakDefItem.getBreakKey());
                }
            }
        }
        return new ControlBreakRowObject(ControlBreakChunk.END_MARK,
                controlBreakKeyList, nextData);
    }
    
    
    /**
     * �w�肳�ꂽ�v���p�e�B�̒l�̐؂�ւ��ɂ���āA�`�����N�R���g���[���u���C
     * �N������ʂ̃R���g���[���u���C�N���������Ă��邩�m�F����B
     *  
     * @param propertyNameSet �l�̐؂�ւ�肪���������v���p�e�B��
     */
    private void checkTransChunkControlBreak(Set<String> propertyNameSet) {
        if (transChunkControlBreakDefItemList == null) {
            return;
        }
        // �`�����N�œo�^���ꂽ�u���C�N�L�[���t���ɂ��Ă���`�F�b�N���s��
        String[] propertyNames =
            propertyNameSet.toArray(new String[propertyNameSet.size()]);
        ArrayUtils.reverse(propertyNames);
        for (ControlBreakDefItem transChunkControlBreakDefItem
                : transChunkControlBreakDefItemList) {
            for (String propertyName : propertyNames) {
                if (transChunkControlBreakDefItem.getBreakKey().contains(
                        propertyName)) {
                    transChunkControlBreakKeyList
                            .add(transChunkControlBreakDefItem.getBreakKey());
                    break;
                }
            }
        }
    }
    
    /**
     * �`�����N�R���g���[���u���C�N�̃u���C�N�L�[�̒l���擾����B
     * 
     * @return �`�����N�R���g���[���u���C�N�̃u���C�N�L�[�̒l
     */
    public Map<String, Object> getChunkControlBreakMap() {
        return chunkControlBreakMap;
    }

    /**
     * �g�����X�R���g���[���u���C�N�L�[�̃��X�g���擾����B
     * 
     * @return �g�����X�R���g���[���u���C�N�L�[�̃��X�g
     */
    public List<List<String>> getTransChunkControlBreakKeyList() {
        return transChunkControlBreakKeyList;
    }

    
    /**
     * �`�����N�R���g���[���u���C�N���������Ă��邩�ǂ����𔻒肷��B
     * 
     * @return �`�����N�R���g���[���u���C�N���������Ă���ꍇ�ɂ́A
     * <code>true</code>
     */
    public boolean isChunkControlBreak() {
        return chunkControlBreak;
    }

    /**
     * �ŏI�`�����N�ł��邩��]������B
     * 
     * @return �ŏI�`�F�b�N�̂̏ꍇ�A<code>true</code>
     */
    public boolean isEndChunk() {
        return endChunk;
    }

    /**
     * �ŏI�`�����N�t���O��ݒ肷��B
     * 
     * @param endChunk �ŏI�`�����N�t���O
     */
    public void setEndChunk(boolean endChunk) {
        this.endChunk = endChunk;
    }

    
    /**
     * �R���g���[���u���C�N�̃u���C�N�L�[�̒l���擾����B
     * @return controlBreakMap �R���g���[���u���C�N�̃u���C�N�L�[�̒l
     */
    public Map<String, Object> getNextData() {
        return nextData;
    }

    /**
     * �`�����N�R���g���[���u���C�N�̃u���C�N�L�[�̒l���擾����B
     * @return chunkNextData �`�����N�R���g���[���u���C�N�̃u���C�N�L�[�̒l
     */
    public Map<String, Object> getChunkNextData() {
        return chunkNextData;
    }
    
    /**
     * �u���C�N�L�[�̒l���擾����
     * @param object RowObject 
     * @param breakKey �u���C�N�L�[
     * @return �u���C�N�L�[�̒l
     */
    private Object getBreakKeyValue(Object object, String breakKey) {
        // ��������
        if (methodMap == null) {
            try {
                // BreakKeySet
                HashSet<String> keySet = new HashSet<String>();
                for (String propertyName : chunkControlBreakDefItem
                        .getBreakKey()) {
                    keySet.add(propertyName);
                }
                for (ControlBreakDefItem controlBreakDefItem
                        : controlBreakDefItemList) {
                    for (String propertyName : controlBreakDefItem
                            .getBreakKey()) {
                        if (!keySet.contains(propertyName)) {
                            keySet.add(propertyName);
                        }
                    }
                }
                // Method Map
                methodMap = new FastHashMap();
                BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass());
                PropertyDescriptor[] descriptors = beanInfo
                        .getPropertyDescriptors();
                for (PropertyDescriptor descriptor : descriptors) {
                    if (keySet.contains(descriptor.getName())) {
                        methodMap.put(descriptor.getName(), descriptor
                                .getReadMethod());
                    }
                }
            } catch (IntrospectionException e) {
                throw new JobException(e);
            }
        }

        try {
            Method method = (Method) methodMap.get(breakKey);
            return method.invoke(object, new Object[0]);
        } catch (IllegalAccessException e) {
            throw new JobException(e);
        } catch (InvocationTargetException e) {
            throw new JobException(e);
        }
    }

    /**
     * @return methodMap ��߂��܂��B
     */
    public FastHashMap getMethodMap() {
        return methodMap;
    }

    /**
     * @param methodMap �ݒ肷�� methodMap�B
     */
    public void setMethodMap(FastHashMap methodMap) {
        this.methodMap = methodMap;
    }
}
