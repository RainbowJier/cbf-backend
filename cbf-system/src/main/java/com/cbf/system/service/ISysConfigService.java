package com.cbf.system.service;

import com.cbf.system.domain.SysConfig;

import java.util.List;

/**
 * 参数配置 服务层
 *
 * @author Frank
 */
public interface ISysConfigService {
    /**
     * 查询参数配置信息
     *
     * @param configId 参数配置ID
     * @return 参数配置信息
     */
    SysConfig selectConfigById(Long configId);

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数键名
     * @return 参数键值
     */
    String selectConfigByKey(String configKey);

    /**
     * 获取验证码开关
     *
     * @return true开启，false关闭
     */
    boolean selectCaptchaEnabled();

    /**
     * 查询参数配置列表
     *
     * @param config 参数配置信息
     * @return 参数配置集合
     */
    List<SysConfig> selectConfigList(SysConfig config);

    /**
     * 新增参数配置
     *
     * @param config 参数配置信息
     * @return 结果
     */
    int insertConfig(SysConfig config);

    /**
     * 修改参数配置
     *
     * @param config 参数配置信息
     * @return 结果
     */
    int updateConfig(SysConfig config);

    /**
     * 批量删除参数信息
     *
     * @param configIds 需要删除的参数ID
     */
     void deleteConfigByIds(Long[] configIds);

    /**
     * 加载参数缓存数据
     */
     void loadingConfigCache();

    /**
     * 清空参数缓存数据
     */
     void clearConfigCache();

    /**
     * 重置参数缓存数据
     */
     void resetConfigCache();

    /**
     * 校验参数键名是否唯一
     *
     * @param config 参数信息
     * @return 结果
     */
     boolean checkConfigKeyUnique(SysConfig config);
}
