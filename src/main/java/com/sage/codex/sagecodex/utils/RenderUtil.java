package com.sage.codex.sagecodex.utils;

import com.intellij.openapi.editor.markup.EffectType;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ui.paint.EffectPainter2D;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RenderUtil {
    public RenderUtil() {
    }

    public static void renderEffects(@NotNull Graphics2D g, double x, double baseline, double width, int charHeight, int descent, @NotNull TextAttributes textAttributes, @Nullable Font font) {
        Color effectColor = textAttributes.getEffectColor();
        if (effectColor != null) {
            EffectType effectType = textAttributes.getEffectType();
            if (effectType != null) {
                g.setColor(effectColor);
                switch (effectType) {
                    case LINE_UNDERSCORE:
                        EffectPainter2D.LINE_UNDERSCORE.paint(g, x, baseline, width, (double) descent, font);
                        break;
                    case BOLD_LINE_UNDERSCORE:
                        EffectPainter2D.BOLD_LINE_UNDERSCORE.paint(g, x, baseline, width, (double) descent, font);
                        break;
                    case STRIKEOUT:
                        EffectPainter2D.STRIKE_THROUGH.paint(g, x, baseline, width, (double) charHeight, font);
                        break;
                    case WAVE_UNDERSCORE:
                        EffectPainter2D.WAVE_UNDERSCORE.paint(g, x, baseline, width, (double) descent, font);
                        break;
                    case BOLD_DOTTED_LINE:
                        EffectPainter2D.BOLD_DOTTED_UNDERSCORE.paint(g, x, baseline, width, (double) descent, font);
                }
            }
        }

    }

    public static void renderBackground(@NotNull Graphics2D g, @NotNull TextAttributes attributes, double x, double y, double width, double height) {
        Color color = attributes.getBackgroundColor();
        if (color != null) {
            g.setColor(color);
            g.fillRoundRect((int) x, (int) y, (int) width, (int) height, 1, 1);
        }

    }


    public static List<String> replaceLeadingTabs(@NotNull String content, @NotNull int tabWidth) {
        List<String> lines = Arrays.asList(content.split("\\n"));
        return lines.stream().filter(e -> !StringUtil.isEmpty(e)).map((line) -> {
            int tabCount = StringUtil.countChars(line, '\t', 0, true);
            if (tabCount > 0) {
                String tabSpaces = StringUtil.repeatSymbol(' ', tabCount * tabWidth);
                return tabSpaces + line.substring(tabCount);
            } else {
                return line;
            }
        }).collect(Collectors.toList());
    }
}
