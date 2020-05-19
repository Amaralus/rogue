package amaralus.apps.rogue.services.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuList {

    private List<MenuElement> elementList = new ArrayList<>();
    private MenuElement current;
    private boolean emptyMenu = false;

    public MenuList(MenuElement... elements) {
        this(Arrays.asList(elements));
    }

    public MenuList(List<MenuElement> elements) {
        setUpMenuList(elements);
    }

    public void setUpMenuList(MenuElement... elements) {
        setUpMenuList(Arrays.asList(elements));
    }

    public void setUpMenuList(List<MenuElement> elements) {
        if (elements == null || elements.isEmpty()) {
            elementList.clear();
            emptyMenu = true;
            return;
        }

        emptyMenu = false;
        elementList = elements;

        for (int i = 0; i < elementList.size() - 1; i++) {
            MenuElement currentElement = elementList.get(i);
            MenuElement next = elementList.get(i + 1);

            currentElement.setNext(next);
            next.setPrevious(currentElement);
        }

        first().setPrevious(last());
        last().setNext(first());
        current = first();
    }

    public MenuElement first() {
        return emptyMenu ? null : elementList.get(0);
    }

    public MenuElement last() {
        return emptyMenu ? null :  elementList.get(elementList.size() - 1);
    }

    public MenuElement current() {
        return emptyMenu ? null :  current;
    }

    public MenuElement next() {
        return emptyMenu ? null :  current.getNext();
    }

    public MenuElement previous() {
        return emptyMenu ? null :  current.getPrevious();
    }

    public void shiftToNext() {
        current = next();
    }

    public void shiftToPrevious() {
        current = previous();
    }

    public List<MenuElement> getElementList() {
        return elementList;
    }
}
