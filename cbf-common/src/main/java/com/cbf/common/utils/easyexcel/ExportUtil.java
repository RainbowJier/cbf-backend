package com.cbf.common.utils.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.metadata.WriteSheet;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Common EasyExcel Export Util
 */
public class ExportUtil {

    /**
     * Export single sheet with style
     *
     */
    public static <T> void export(HttpServletResponse response,
                                  List<T> dataList,
                                  Class<T> clazz,
                                  String fileName,
                                  String sheetName,
                                  List<WriteHandler> writeHandlers) {
        try {
            // 重置响应头
            resetResponse(fileName, response);

            // 构建写入器
            ExcelWriterBuilder writerBuilder = EasyExcel.write(response.getOutputStream(), clazz);
            if (writeHandlers != null) {
                for (WriteHandler handler : writeHandlers) {
                    writerBuilder.registerWriteHandler(handler);
                }
            }

            writerBuilder.sheet(sheetName).doWrite(dataList);
        } catch (Exception e) {
            throw new RuntimeException("导出失败：" + e.getMessage(), e);
        }
    }

    /**
     * 多 Sheet 导出
     * 示例：
     * List<Course> listA = courseService.getCoursesByClass("A");
     * List<Course> listB = courseService.getCoursesByClass("B");
     * List<ExportUtil.SheetData<Course>> sheets = List.of(
     *      new ExportUtil.SheetData<>("sheetName-A", listA, Course.class),
     *      new ExportUtil.SheetData<>("sheetName-B", listB, Course.class)
     * );
     */
    public static <T> void exportMultipleSheets(HttpServletResponse response,
                                                String fileName,
                                                List<SheetData<T>> sheetDataList) {
        try {
            // reset response
            resetResponse(fileName, response);

            // write excel
            ExcelWriter writer = EasyExcel.write(response.getOutputStream()).build();
            for (int i = 0; i < sheetDataList.size(); i++) {
                SheetData<T> sheet = sheetDataList.get(i);
                WriteSheet writeSheet = EasyExcel.writerSheet(i, sheet.getSheetName())
                        .head(sheet.getClazz())
                        .build();
                writer.write(sheet.getData(), writeSheet);
            }
            writer.finish();
        } catch (Exception e) {
            throw new RuntimeException("Export multi sheets Error: " + e.getMessage(), e);
        }
    }


    /**
     * reset response
     *
     * @param fileName file name
     * @param response response
     */
    private static void resetResponse(String fileName, HttpServletResponse response) throws UnsupportedEncodingException {
        String encodedFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + encodedFileName + ".xlsx");
    }


    /**
     * SheetData inner class: support multi sheets
     */
    @Data
    @AllArgsConstructor
    public static class SheetData<T> {
        private String sheetName;
        private List<T> data;
        private Class<T> clazz;
    }
}
