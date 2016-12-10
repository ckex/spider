package com.mljr.spider.dao;

import java.util.List;

public interface SpiderBankCardLocationDao {

    List<String> listById(String lastId, int limit);
}