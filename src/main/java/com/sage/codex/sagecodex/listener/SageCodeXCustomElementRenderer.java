package com.sage.codex.sagecodex.listener;

import com.intellij.ide.ui.AntialiasingType;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorCustomElementRenderer;
import com.intellij.openapi.editor.Inlay;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.editor.colors.EditorFontType;
import com.intellij.openapi.editor.impl.FontInfo;
import com.intellij.openapi.editor.markup.EffectType;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.ui.paint.EffectPainter2D;
import com.intellij.util.ui.GraphicsUtil;
import com.intellij.util.ui.UIUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.util.Iterator;
import java.util.List;

/**
 * @Description：
 * @Author: xionghao
 * @Date: 2023/12/12 14:24
 */
public class SageCodeXCustomElementRenderer implements EditorCustomElementRenderer {

    private Editor editor;
    private String content;

    private List<String> lines;

    public SageCodeXCustomElementRenderer(String content, List<String> lines, Editor editor) {
        this.lines = lines;
        this.content = content;
        this.editor = editor;
    }

    public Editor getEditor() {
        return editor;
    }

    public void setEditor(Editor editor) {
        this.editor = editor;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getLines() {
        return lines;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }

    @Override
    public int calcWidthInPixels(@NotNull Inlay inlay) {
        // 获取当前编辑器实例
        Graphics2D g = (Graphics2D) editor.getContentComponent().getGraphics();
        FontMetrics fontMetrics = g.getFontMetrics(editor.getColorsScheme().getFont(EditorFontType.PLAIN));
        // 计算文本的宽度
        int width = fontMetrics.stringWidth(content);
        // 返回文本的宽度
        return width;
    }

    @Override
    public int calcHeightInPixels(@NotNull Inlay inlay) {
        return EditorCustomElementRenderer.super.calcHeightInPixels(inlay);
    }

    @NotNull
    private static Font getFont(@NotNull Editor editor, @NotNull String text) {
        Font font = editor.getColorsScheme().getFont(EditorFontType.PLAIN).deriveFont(2);
        Font fallbackFont = UIUtil.getFontWithFallbackIfNeeded(font, text);
        return fallbackFont.deriveFont(fontSize(editor));
    }

    static float fontSize(@NotNull Editor editor) {
        EditorColorsScheme scheme = editor.getColorsScheme();
        return (float) scheme.getEditorFontSize();
    }

    @Override
    public void paint(@NotNull Inlay inlay, @NotNull Graphics g, @NotNull Rectangle region, @NotNull TextAttributes attributes) {
        Editor editor = inlay.getEditor();
        if (!editor.isDisposed()) {
            if (!content.isEmpty() && !lines.isEmpty()) {
                Rectangle clipBounds = g.getClipBounds();
                Graphics2D g2 = (Graphics2D) g.create();
                GraphicsUtil.setupAAPainting(g2);
                Font font = getFont(editor, content);
                g2.setFont(font);
                FontRenderContext editorContext = FontInfo.getFontRenderContext(editor.getContentComponent());
                FontRenderContext context = new FontRenderContext(editorContext.getTransform(), AntialiasingType.getKeyForCurrentScope(false), editorContext.getFractionalMetricsHint());
                FontMetrics metrics = FontInfo.getFontMetrics(font, context);
                double lineHeight = (double) editor.getLineHeight();
                int size = font.getSize();
                double fontBaseline = Math.ceil(font.createGlyphVector(metrics.getFontRenderContext(), "Alb").getVisualBounds().getHeight());
                double linePadding = (lineHeight - fontBaseline) / 2.0D;
                double offsetX = region.getX();
                double offsetY = region.getY() + fontBaseline + linePadding;
                int lineOffset = 0;
//                g2.setClip(clipBounds != null && !clipBounds.equals(region) ? region.createIntersection(clipBounds) : region);

                for (Iterator var21 = lines.iterator(); var21.hasNext(); lineOffset += (int) lineHeight) {
                    String line = (String) var21.next();
//                    renderBackground(g2, attributes, offsetX, region.getY() + (double) lineOffset, region.getWidth(), lineHeight);
                    g2.setColor(Color.GRAY);
                    g2.drawString(line, (float) offsetX, (float) (offsetY + (double) lineOffset));
//                    if (editor instanceof EditorImpl) {
//                        renderEffects(g2, offsetX, offsetY + (double) lineOffset, metrics.stringWidth(line), ((EditorImpl) editor).getCharHeight(), ((EditorImpl) editor).getDescent(), attributes, font);
//                    }
                }
//                g2.dispose();

            }
        }
    }

    private static void renderBackground(@NotNull Graphics2D g, @NotNull TextAttributes attributes, double x, double y, double width, double height) {
        Color color = attributes.getBackgroundColor();
        if (color != null) {
            g.setColor(color);
            g.fillRoundRect((int) x, (int) y, (int) width, (int) height, 1, 1);
        }

    }

    private static void renderEffects(@NotNull Graphics2D g, double x, double baseline, double width, int charHeight, int descent, @NotNull TextAttributes textAttributes, @Nullable Font font) {
        Color effectColor = textAttributes.getEffectColor();
        if (effectColor != null) {
            EffectType effectType = textAttributes.getEffectType();
            if (effectType != null) {
                g.setColor(effectColor);
                switch (effectType) {
                    case LINE_UNDERSCORE:
                        EffectPainter2D.LINE_UNDERSCORE.paint(g, x, baseline, width, descent, font);
                        break;
                    case BOLD_LINE_UNDERSCORE:
                        EffectPainter2D.BOLD_LINE_UNDERSCORE.paint(g, x, baseline, width, descent, font);
                        break;
                    case STRIKEOUT:
                        EffectPainter2D.STRIKE_THROUGH.paint(g, x, baseline, width, charHeight, font);
                        break;
                    case WAVE_UNDERSCORE:
                        EffectPainter2D.WAVE_UNDERSCORE.paint(g, x, baseline, width, descent, font);
                        break;
                    case BOLD_DOTTED_LINE:
                        EffectPainter2D.BOLD_DOTTED_UNDERSCORE.paint(g, x, baseline, width, descent, font);
                    case BOXED:
                }
            }
        }

    }


}
