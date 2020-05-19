package amaralus.apps.rogue.graphics.drawers;

import amaralus.apps.rogue.services.menu.MenuList;
import amaralus.apps.rogue.services.screens.MenuScreen;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MenuScreenDrawer extends ScreenDrawer {

    MenuScreen menuScreen;

    public MenuScreenDrawer(MenuScreen menuScreen) {
        this.menuScreen = menuScreen;
    }

    @Override
    public void draw() {
        MenuList menuList = menuScreen.getMenuList();
        List<Text> menuTexts = new ArrayList<>(menuList.getElementList().size() + 1);

        menuTexts.add(createPlainText(" " + menuScreen.getMenuTitle() + "\n\n"));

        menuTexts.addAll(menuList.getElementList().stream()
                .map(menuElement -> menuElement.equals(menuList.current()) ? " -> " + menuElement.getText() : "    " + menuElement.getText())
                .map(menuText -> createPlainText(menuText + "\n"))
                .collect(Collectors.toList()));

        updateTexts(menuTexts);
    }
}
