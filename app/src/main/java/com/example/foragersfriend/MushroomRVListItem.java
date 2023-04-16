package com.example.foragersfriend;

import java.util.LinkedList;
import java.util.List;

public class MushroomRVListItem {

    private String name;
    private String lastSeen;

    private int image;

    public MushroomRVListItem(String name, String lastSeen, int image) {
        this.name = name;
        this.lastSeen = lastSeen;
        this.image = image;
    }

    public static List<MushroomRVListItem> getDummyList() {
        MushroomRVListItem mushroomRVListItem = new MushroomRVListItem("Mushroom 1", "Last seen 1", 0);
        MushroomRVListItem mushroomRVListItem2 = new MushroomRVListItem("Mushroom 2", "Last seen 2", 0);
        MushroomRVListItem mushroomRVListItem3 = new MushroomRVListItem("Mushroom 3", "Last seen 3", 0);
        MushroomRVListItem mushroomRVListItem4 = new MushroomRVListItem("Mushroom 4", "Last seen 4", 0);
        MushroomRVListItem mushroomRVListItem5 = new MushroomRVListItem("Mushroom 5", "Last seen 5", 0);
        MushroomRVListItem mushroomRVListItem6 = new MushroomRVListItem("Mushroom 6", "Last seen 6", 0);
        MushroomRVListItem mushroomRVListItem7 = new MushroomRVListItem("Mushroom 7", "Last seen 7", 0);
        MushroomRVListItem mushroomRVListItem8 = new MushroomRVListItem("Mushroom 8", "Last seen 8", 0);
        MushroomRVListItem mushroomRVListItem9 = new MushroomRVListItem("Mushroom 9", "Last seen 9", 0);
        MushroomRVListItem mushroomRVListItem10 = new MushroomRVListItem("Mushroom 10", "Last seen 10", 0);
        MushroomRVListItem mushroomRVListItem11 = new MushroomRVListItem("Mushroom 11", "Last seen 11", 0);
        MushroomRVListItem mushroomRVListItem12 = new MushroomRVListItem("Mushroom 12", "Last seen 12", 0);
        MushroomRVListItem mushroomRVListItem13 = new MushroomRVListItem("Mushroom 13", "Last seen 13", 0);
        MushroomRVListItem mushroomRVListItem14 = new MushroomRVListItem("Mushroom 14", "Last seen 14", 0);
        MushroomRVListItem mushroomRVListItem15 = new MushroomRVListItem("Mushroom 15", "Last seen 15", 0);
        MushroomRVListItem mushroomRVListItem16 = new MushroomRVListItem("Mushroom 16", "Last seen 16", 0);
        MushroomRVListItem mushroomRVListItem17 = new MushroomRVListItem("Mushroom 17", "Last seen 17", 0);
        MushroomRVListItem mushroomRVListItem18 = new MushroomRVListItem("Mushroom 18", "Last seen 18", 0);
        MushroomRVListItem mushroomRVListItem19 = new MushroomRVListItem("Mushroom 19", "Last seen 19", 0);
        MushroomRVListItem mushroomRVListItem20 = new MushroomRVListItem("Mushroom 20", "Last seen 20", 0);
        MushroomRVListItem mushroomRVListItem21 = new MushroomRVListItem("Mushroom 21", "Last seen 21", 0);
        MushroomRVListItem mushroomRVListItem22 = new MushroomRVListItem("Mushroom 22", "Last seen 22", 0);
        MushroomRVListItem mushroomRVListItem23 = new MushroomRVListItem("Mushroom 23", "Last seen 23", 0);
        MushroomRVListItem mushroomRVListItem24 = new MushroomRVListItem("Mushroom 24", "Last seen 24", 0);
        MushroomRVListItem mushroomRVListItem25 = new MushroomRVListItem("Mushroom 25", "Last seen 25", 0);

        LinkedList<MushroomRVListItem> dummyList = new LinkedList<>();
        dummyList.add(mushroomRVListItem);
        dummyList.add(mushroomRVListItem2);
        dummyList.add(mushroomRVListItem3);
        dummyList.add(mushroomRVListItem4);
        dummyList.add(mushroomRVListItem5);
        dummyList.add(mushroomRVListItem6);
        dummyList.add(mushroomRVListItem7);
        dummyList.add(mushroomRVListItem8);
        dummyList.add(mushroomRVListItem9);
        dummyList.add(mushroomRVListItem10);
        dummyList.add(mushroomRVListItem11);
        dummyList.add(mushroomRVListItem12);
        dummyList.add(mushroomRVListItem13);
        dummyList.add(mushroomRVListItem14);
        dummyList.add(mushroomRVListItem15);
        dummyList.add(mushroomRVListItem16);
        dummyList.add(mushroomRVListItem17);
        dummyList.add(mushroomRVListItem18);
        dummyList.add(mushroomRVListItem19);
        dummyList.add(mushroomRVListItem20);
        dummyList.add(mushroomRVListItem21);
        dummyList.add(mushroomRVListItem22);
        dummyList.add(mushroomRVListItem23);
        dummyList.add(mushroomRVListItem24);
        dummyList.add(mushroomRVListItem25);

        return dummyList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
