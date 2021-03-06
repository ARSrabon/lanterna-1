package com.googlecode.lanterna.gui2;

import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TerminalTextUtils;
import com.googlecode.lanterna.graphics.ThemeDefinition;

/**
 *
 */
public class FatWindowDecorationRenderer implements WindowDecorationRenderer {
    @Override
    public TextGUIGraphics draw(TextGUI textGUI, TextGUIGraphics graphics, Window window) {
        String title = window.getTitle();
        if(title == null) {
            title = "";
        }
        title = " " + title.trim() + " ";

        ThemeDefinition themeDefinition = window.getTheme().getDefinition(FatWindowDecorationRenderer.class);
        char horizontalLine = themeDefinition.getCharacter("HORIZONTAL_LINE", Symbols.SINGLE_LINE_HORIZONTAL);
        char verticalLine = themeDefinition.getCharacter("VERTICAL_LINE", Symbols.SINGLE_LINE_VERTICAL);
        char bottomLeftCorner = themeDefinition.getCharacter("BOTTOM_LEFT_CORNER", Symbols.SINGLE_LINE_BOTTOM_LEFT_CORNER);
        char topLeftCorner = themeDefinition.getCharacter("TOP_LEFT_CORNER", Symbols.SINGLE_LINE_TOP_LEFT_CORNER);
        char bottomRightCorner = themeDefinition.getCharacter("BOTTOM_RIGHT_CORNER", Symbols.SINGLE_LINE_BOTTOM_RIGHT_CORNER);
        char topRightCorner = themeDefinition.getCharacter("TOP_RIGHT_CORNER", Symbols.SINGLE_LINE_TOP_RIGHT_CORNER);
        char leftJunction = themeDefinition.getCharacter("LEFT_JUNCTION", Symbols.SINGLE_LINE_T_RIGHT);
        char rightJunction = themeDefinition.getCharacter("RIGHT_JUNCTION", Symbols.SINGLE_LINE_T_LEFT);

        TerminalSize drawableArea = graphics.getSize();
        graphics.applyThemeStyle(themeDefinition.getPreLight());
        graphics.drawLine(0, drawableArea.getRows() - 2, 0, 1, verticalLine);
        graphics.drawLine(1, 0, drawableArea.getColumns() - 2, 0, horizontalLine);
        graphics.drawLine(1, 2, drawableArea.getColumns() - 2, 2, horizontalLine);
        graphics.setCharacter(0, 0, topLeftCorner);
        graphics.setCharacter(0, 2, leftJunction);
        graphics.setCharacter(0, drawableArea.getRows() - 1, bottomLeftCorner);

        graphics.applyThemeStyle(themeDefinition.getNormal());
        graphics.drawLine(
                drawableArea.getColumns() - 1, 1,
                drawableArea.getColumns() - 1, drawableArea.getRows() - 2,
                verticalLine);
        graphics.drawLine(
                1, drawableArea.getRows() - 1,
                drawableArea.getColumns() - 2, drawableArea.getRows() - 1,
                horizontalLine);

        graphics.setCharacter(drawableArea.getColumns() - 1, 0, topRightCorner);
        graphics.setCharacter(drawableArea.getColumns() - 1, 2, rightJunction);
        graphics.setCharacter(drawableArea.getColumns() - 1, drawableArea.getRows() - 1, bottomRightCorner);

        graphics.applyThemeStyle(themeDefinition.getActive());
        graphics.drawLine(1, 1, drawableArea.getColumns() - 2, 1, ' ');
        graphics.putString(1, 1, TerminalTextUtils.fitString(title, drawableArea.getColumns() - 3));

        return graphics.newTextGraphics(OFFSET, graphics.getSize().withRelativeColumns(-2).withRelativeRows(-4));
    }

    @Override
    public TerminalSize getDecoratedSize(Window window, TerminalSize contentAreaSize) {
        return contentAreaSize
                .withRelativeColumns(2)
                .withRelativeRows(4)
                .max(new TerminalSize(TerminalTextUtils.getColumnWidth(window.getTitle()) + 4, 1));  //Make sure the title fits!
    }

    private static final TerminalPosition OFFSET = new TerminalPosition(1, 3);

    @Override
    public TerminalPosition getOffset(Window window) {
        return OFFSET;
    }
}
