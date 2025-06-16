package com.cbf.system.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户和岗位关联 sys_user_post
 *
 * @author Frank
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value ="sys_user_post")
public class SysUserPost {
    /**
     * 用户ID
     */
    @TableId(value = "user_id")
    private Long userId;

    /**
     * 岗位ID
     */
    @TableId(value = "post_id")
    private Long postId;

}
