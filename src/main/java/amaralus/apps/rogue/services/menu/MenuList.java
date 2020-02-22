package amaralus.apps.rogue.services.menu;

import java.util.Arrays;
import java.util.List;

public class MenuList {

    private final List<MenuElement> elementList;
    private MenuElement current;

    public MenuList(MenuElement... elements) {
        elementList = Arrays.asList(elements);

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
        return elementList.get(0);
    }

    public MenuElement last() {
        return elementList.get(elementList.size() - 1);
    }

    public MenuElement current() {
        return current;
    }

    public MenuElement next() {
        return current.getNext();
    }

    public MenuElement previous() {
        return current.getPrevious();
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
