package com.main.libridex.service;

import java.util.List;

import com.main.libridex.entity.Lending;
import com.main.libridex.entity.User;

public interface LendingService {
    List<Lending> findByBookId(Integer id);
    boolean createLending(Integer bookId);
    void endLending(Integer id);
    List<Lending> getAllLendings();
    int countUserActiveLendings(User user, int bookId);
    boolean isLendByUser(Integer bookId);
    Lending findBookCurrentLending(Integer id);
}
