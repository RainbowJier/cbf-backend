package com.cbf.common.utils.easyexcel.handler;


import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.handler.context.RowWriteHandlerContext;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.apache.poi.ss.usermodel.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 高级样式处理器：列宽、字体、边框、行高
 *
 * @author : Liuqijie
 * @Date: 2025/6/20 17:26
 */

public class AdvancedStyleHandler extends HorizontalCellStyleStrategy {

    public AdvancedStyleHandler() {
        super(createHeadStyle(), createContentStyle());
        // 自定义列宽配置
        Map<Integer, Integer> columnWidthMap = new HashMap<>();
        columnWidthMap.put(0, 5000);  // 第1列宽度（以字符数 * 256计）
        columnWidthMap.put(1, 8000);  // 第2列
        columnWidthMap.put(2, 10000); // 第3列
    }

    // /**
    //  * 设置列宽（Sheet 创建后立即设置）
    //  */
    // @Override
    // public void afterSheetCreate(WriteSheetHolder writeSheetHolder) {
    //     Sheet sheet = writeSheetHolder.getSheet();
    //     columnWidthMap.forEach(sheet::setColumnWidth);
    // }
    //
    // /**
    //  * 设置行高（每行写入前设置）
    //  */
    // @Override
    // public void beforeRowCreate(RowWriteHandlerContext context) {
    //     Row row = context.getSheet().createRow(context.getRowIndex());
    //     row.setHeight((short) (20 * 20));  // 设置行为 20 磅
    // }

    /**
     * 统一设置边框
     */
    @Override
    public void afterCellDispose(CellWriteHandlerContext context) {
        Cell cell = context.getCell();
        CellStyle cellStyle = cell.getCellStyle();

        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
    }

    /**
     * 表头样式
     */
    private static WriteCellStyle createHeadStyle() {
        WriteCellStyle style = new WriteCellStyle();
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());

        WriteFont font = new WriteFont();
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);
        style.setWriteFont(font);

        return style;
    }

    /**
     * 内容样式
     */
    private static WriteCellStyle createContentStyle() {
        WriteCellStyle style = new WriteCellStyle();
        WriteFont font = new WriteFont();
        font.setFontHeightInPoints((short) 11);
        style.setWriteFont(font);

        return style;
    }
}
