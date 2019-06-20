package com.bjjy.buildtalk.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author power
 * @date 2019/6/14 5:38 PM
 * @project BuildTalk
 * @description:
 */
public class PraiseEntity implements Serializable {

    /**
     * countpraise : 1
     * nickName : [{"name":"建谈新用户","user_id":145,"user_type":1}]
     */

    private int countpraise;
    private List<PariseNickNameBean> nickName;

    public int getCountpraise() {
        return countpraise;
    }

    public void setCountpraise(int countpraise) {
        this.countpraise = countpraise;
    }

    public List<PariseNickNameBean> getNickName() {
        return nickName;
    }

    public void setNickName(List<PariseNickNameBean> nickName) {
        this.nickName = nickName;
    }

}
