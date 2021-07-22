package com.splitwise.services.settle;

import com.splitwise.models.Group;

public interface SettleGroupStrategy {
    String settle(Group group);
}
