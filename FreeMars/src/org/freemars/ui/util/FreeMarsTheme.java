package org.freemars.ui.util;

import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;

public class FreeMarsTheme extends DefaultMetalTheme {

    @Override
    public String getName() {
        return "Free Mars";
    }
    private final ColorUIResource primary1 =
            new ColorUIResource(192, 53, 53);
    private final ColorUIResource primary2 =
            new ColorUIResource(202, 63, 63);
    private final ColorUIResource primary3 =
            new ColorUIResource(212, 123, 123);
    private final ColorUIResource secondary1 =
            new ColorUIResource(255, 255, 255);
    private final ColorUIResource secondary2 =
            new ColorUIResource(255, 255, 255);
    private final ColorUIResource secondary3 =
            new ColorUIResource(225, 225, 225);

    @Override
    protected ColorUIResource getPrimary1() {
        return primary1;
    }

    @Override
    protected ColorUIResource getPrimary2() {
        return primary2;
    }

    @Override
    protected ColorUIResource getPrimary3() {
        return primary3;
    }

    @Override
    protected ColorUIResource getSecondary1() {
        return super.getSecondary1();
    }

    @Override
    protected ColorUIResource getSecondary2() {
        return super.getSecondary2();
    }

    @Override
    protected ColorUIResource getSecondary3() {
        return secondary3;
    }
}
