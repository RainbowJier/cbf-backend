package com.cbf.generator.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cbf.common.constant.GenConstants;
import com.cbf.common.core.domain.BaseEntity;
import com.cbf.common.utils.StringUtils;
import org.apache.commons.lang3.ArrayUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 业务表 gen_table
 *
 * @author Frank
 */
@TableName(value ="gen_table")
public class GenTable extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "table_id")
    private Long tableId;

    /**
     * 表名称
     */
    @NotBlank(message = "表名称不能为空")
    @TableField(value = "table_name")
    private String tableName;

    /**
     * 表描述
     */
    @NotBlank(message = "表描述不能为空")
    @TableField(value = "table_comment")
    private String tableComment;

    /**
     * 关联父表的表名
     */
    @TableField(value = "sub_table_name")
    private String subTableName;

    /**
     * 本表关联父表的外键名
     */
    @TableField(value = "sub_table_fk_name")
    private String subTableFkName;

    /**
     * 实体类名称(首字母大写)
     */
    @NotBlank(message = "实体类名称不能为空")
    @TableField(value = "class_name")
    private String className;

    /**
     * 使用的模板（crud单表操作 tree树表操作 sub主子表操作）
     */
    @TableField(value = "tpl_category")
    private String tplCategory;

    /**
     * 前端类型（element-ui模版 element-plus模版）
     */
    @TableField(value = "tpl_web_type")
    private String tplWebType;

    /**
     * 生成包路径
     */
    @NotBlank(message = "生成包路径不能为空")
    @TableField(value = "package_name")
    private String packageName;

    /**
     * 生成模块名
     */
    @NotBlank(message = "生成模块名不能为空")
    @TableField(value = "module_name")
    private String moduleName;

    /**
     * 生成业务名
     */
    @NotBlank(message = "生成业务名不能为空")
    @TableField(value = "business_name")
    private String businessName;

    /**
     * 生成功能名
     */
    @NotBlank(message = "生成功能名不能为空")
    @TableField(value = "function_name")
    private String functionName;

    /**
     * 生成作者
     */
    @NotBlank(message = "作者不能为空")
    @TableField(value = "function_author")
    private String functionAuthor;

    /**
     * 生成代码方式（0zip压缩包 1自定义路径）
     */
    @TableField(value = "gen_type")
    private String genType;

    /**
     * 生成路径（不填默认项目路径）
     */
    @TableField(value = "gen_path")
    private String genPath;

    /**
     * 其它生成选项
     */
    @TableField(value = "options")
    private String options;

    /**
     * 主键信息
     */
    private GenTableColumn pkColumn;

    /**
     * 子表信息
     */
    private GenTable subTable;

    /**
     * 表列信息
     */
    @Valid
    private List<GenTableColumn> columns;


    /**
     * 树编码字段
     */
    private String treeCode;

    /**
     * 树父编码字段
     */
    private String treeParentCode;

    /**
     * 树名称字段
     */
    private String treeName;

    /**
     * 上级菜单ID字段
     */
    private Long parentMenuId;

    /**
     * 上级菜单名称字段
     */
    private String parentMenuName;

    public static boolean isSub(String tplCategory) {
        return tplCategory != null && StringUtils.equals(GenConstants.TPL_SUB, tplCategory);
    }

    public static boolean isTree(String tplCategory) {
        return tplCategory != null && StringUtils.equals(GenConstants.TPL_TREE, tplCategory);
    }

    public static boolean isCrud(String tplCategory) {
        return tplCategory != null && StringUtils.equals(GenConstants.TPL_CRUD, tplCategory);
    }

    public static boolean isSuperColumn(String tplCategory, String javaField) {
        if (isTree(tplCategory)) {
            return StringUtils.equalsAnyIgnoreCase(javaField,
                    ArrayUtils.addAll(GenConstants.TREE_ENTITY, GenConstants.BASE_ENTITY));
        }
        return StringUtils.equalsAnyIgnoreCase(javaField, GenConstants.BASE_ENTITY);
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public String getSubTableName() {
        return subTableName;
    }

    public void setSubTableName(String subTableName) {
        this.subTableName = subTableName;
    }

    public String getSubTableFkName() {
        return subTableFkName;
    }

    public void setSubTableFkName(String subTableFkName) {
        this.subTableFkName = subTableFkName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTplCategory() {
        return tplCategory;
    }

    public void setTplCategory(String tplCategory) {
        this.tplCategory = tplCategory;
    }

    public String getTplWebType() {
        return tplWebType;
    }

    public void setTplWebType(String tplWebType) {
        this.tplWebType = tplWebType;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getFunctionAuthor() {
        return functionAuthor;
    }

    public void setFunctionAuthor(String functionAuthor) {
        this.functionAuthor = functionAuthor;
    }

    public String getGenType() {
        return genType;
    }

    public void setGenType(String genType) {
        this.genType = genType;
    }

    public String getGenPath() {
        return genPath;
    }

    public void setGenPath(String genPath) {
        this.genPath = genPath;
    }

    public GenTableColumn getPkColumn() {
        return pkColumn;
    }

    public void setPkColumn(GenTableColumn pkColumn) {
        this.pkColumn = pkColumn;
    }

    public GenTable getSubTable() {
        return subTable;
    }

    public void setSubTable(GenTable subTable) {
        this.subTable = subTable;
    }

    public List<GenTableColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<GenTableColumn> columns) {
        this.columns = columns;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getTreeCode() {
        return treeCode;
    }

    public void setTreeCode(String treeCode) {
        this.treeCode = treeCode;
    }

    public String getTreeParentCode() {
        return treeParentCode;
    }

    public void setTreeParentCode(String treeParentCode) {
        this.treeParentCode = treeParentCode;
    }

    public String getTreeName() {
        return treeName;
    }

    public void setTreeName(String treeName) {
        this.treeName = treeName;
    }

    public Long getParentMenuId() {
        return parentMenuId;
    }

    public void setParentMenuId(Long parentMenuId) {
        this.parentMenuId = parentMenuId;
    }

    public String getParentMenuName() {
        return parentMenuName;
    }

    public void setParentMenuName(String parentMenuName) {
        this.parentMenuName = parentMenuName;
    }

    public boolean isSub() {
        return isSub(this.tplCategory);
    }

    public boolean isTree() {
        return isTree(this.tplCategory);
    }

    public boolean isCrud() {
        return isCrud(this.tplCategory);
    }

    public boolean isSuperColumn(String javaField) {
        return isSuperColumn(this.tplCategory, javaField);
    }
}