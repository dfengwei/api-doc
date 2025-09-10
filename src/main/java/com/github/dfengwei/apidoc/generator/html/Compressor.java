package com.github.dfengwei.apidoc.generator.html;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

import com.github.dfengwei.apidoc.manager.Logger;
import com.googlecode.htmlcompressor.compressor.HtmlCompressor;
import com.yahoo.platform.yui.compressor.CssCompressor;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;

/**
 * 压缩器
 *
 * @author dfengwei@163.com
 */
public class Compressor {

    private static final HtmlCompressor HTML_COMPRESSOR = new HtmlCompressor();

    static {
        // removes iter-tag whitespace characters
        HTML_COMPRESSOR.setRemoveIntertagSpaces(true);

        // removes unnecessary tag attribute quotes
        HTML_COMPRESSOR.setRemoveQuotes(true);

        // remove spaces around provided tags
        HTML_COMPRESSOR.setRemoveSurroundingSpaces("br,p");
    }

    /**
     * 压缩HTML
     * 
     * @param html HTML原始文本
     * @return 压缩后的HTML文本
     */
    public static String compressHtml(String html) {
        return HTML_COMPRESSOR.compress(html);
    }

    /**
     * 压缩CSS
     * 
     * @param css css原始文本
     * @return 压缩后的css文本
     */
    public static String compressCss(String css) {
        try {
            CssCompressor compressor = new CssCompressor(new StringReader(css));

            // 一行多少个字符，-1表示无限
            int lineBreakPos = -1;

            Writer writer = new StringWriter();
            compressor.compress(writer, lineBreakPos);
            return writer.toString();
        } catch (IOException e) {
            Logger.error(e.getMessage(), e);
            return css;
        }
    }

    /**
     * 压缩js
     * 
     * @param js js原始文本
     * @return 压缩后的js文本
     */
    public static String compressJs(String js) {
        try {
            JavaScriptCompressor compressor = new JavaScriptCompressor(new StringReader(js), new ErrorReporter() {
                @Override
                public void warning(String message, String sourceName, int line, String lineSource, int lineOffset) {
                    Logger.warn("js压缩警告: ", message);
                }

                @Override
                public void error(String message, String sourceName, int line, String lineSource, int lineOffset) {
                    Logger.error("s压缩错误: ", message);
                }

                @Override
                public EvaluatorException runtimeError(String message, String sourceName, int line, String lineSource,
                    int lineOffset) {
                    error(message, sourceName, line, lineSource, lineOffset);
                    return new EvaluatorException(message);
                }
            });

            // 一行多少个字符，-1表示无限
            int lineBreakPos = -1;
            // 是否混淆
            boolean munge = true;
            // 是否输出详细日志
            boolean verbose = true;
            // 是否保留所有分号
            boolean preserveAllSemiColons = false;
            // 禁用优化
            boolean disableOptimizations = false;

            Writer writer = new StringWriter();
            compressor.compress(writer, lineBreakPos, munge, verbose, preserveAllSemiColons, disableOptimizations);
            return writer.toString();
        } catch (IOException e) {
            Logger.error(e.getMessage(), e);
            return js;
        }
    }

}
