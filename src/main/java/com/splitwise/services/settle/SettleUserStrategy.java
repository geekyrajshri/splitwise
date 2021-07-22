package com.splitwise.services.settle;

import com.splitwise.models.User;

public interface SettleUserStrategy {
    String settle(User user);
}
