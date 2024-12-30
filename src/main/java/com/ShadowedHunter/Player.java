package com.ShadowedHunter;

import java.util.HashMap;
import java.util.Map;

public class Player {
    private final Position position;
    public Map<String, Integer> inventory;

    public Player(int x, int y) {
        this.position = new Position(x, y);
        this.inventory = new HashMap<>();
    }

    public Position getPosition() {
        return position;
    }

    public void loadInventory(Map<String, Integer> savedInventory) {
        this.inventory.putAll(savedInventory);
    }

    public void moveUp() {
        this.position.setY(this.position.getY() - 1);
    }

    public void moveDown() {
        this.position.setY(this.position.getY() + 1);
    }

    public void moveLeft() {
        this.position.setX(this.position.getX() - 1);
    }

    public void moveRight() {
        this.position.setX(this.position.getX() + 1);
    }

    public void pickUpItem(String item) {
        inventory.put(item, inventory.getOrDefault(item, 0) + 1);
    }

    public void removeItem(String item) {
        inventory.put(item, inventory.getOrDefault(item, 0) - 1);
    }

    public int getItemCount(String item) {
        return inventory.getOrDefault(item, 0);
    }

    public void setItemCount(String item, int num) {
        this.inventory.put(item, num);
    }
}
