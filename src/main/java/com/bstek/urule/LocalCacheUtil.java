package com.bstek.urule;

import cn.hutool.db.Entity;
import cn.hutool.db.handler.EntityListHandler;
import cn.hutool.db.sql.SqlExecutor;
import com.bstek.urule.console.repository.RepositoryBuilder;
import com.bstek.urule.model.library.Datatype;
import com.bstek.urule.model.library.variable.Act;
import com.bstek.urule.model.library.variable.CategoryType;
import com.bstek.urule.model.library.variable.Variable;
import com.bstek.urule.model.library.variable.VariableCategory;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author duhongming
 * @version 1.0
 * @description 本地缓存
 * @date 2021/5/11 16:04
 */
public class LocalCacheUtil {

    /**
     * 初始容量
     */
    private static final Integer INITIAL_CAPACITY = 8;

    /**
     * 缓存在1分钟内没有更新则重新读取数据
     */
    private static final Integer EXPIRE_TIME = 1;


    private static LoadingCache<String, List<VariableCategory>> localCache = CacheBuilder.newBuilder()
            .initialCapacity(INITIAL_CAPACITY)
            .maximumSize(INITIAL_CAPACITY)
            //缓存在1分钟内没有更新则重新读取数据
            .expireAfterWrite(EXPIRE_TIME, TimeUnit.MINUTES)
            .recordStats()
            .build(new CacheLoader<String, List<VariableCategory>>() {
                @Override
                public List<VariableCategory> load(String key) {
                    return getVariableCategories();
                }
            });

    @SneakyThrows
    public static List<VariableCategory> getVariableCategories() {
        Connection connection = RepositoryBuilder.datasource.getConnection();
        List<Entity> entities = SqlExecutor.query(connection, "SELECT * FROM urule_signal_lib", new EntityListHandler(), new HashMap<>());
        List<Variable> variables = new ArrayList<>();
        for (Entity entity : entities) {
            Variable variable = new Variable();
            variable.setName(entity.getStr("signal_name"));
            variable.setType(Datatype.String);
            variable.setLabel(entity.getStr("description"));
            variable.setAct(Act.Internal);
            variables.add(variable);
        }

        List<VariableCategory> variableCategories = new ArrayList<>();
        VariableCategory variableCategory = new VariableCategory();
        variableCategory.setName("内置信号库");
        variableCategory.setType(CategoryType.BuildIn);
        variableCategory.setClazz("urule.signal.lib");
        variableCategory.setVariables(variables);
        variableCategories.add(variableCategory);

        variableCategory = new VariableCategory();
        variableCategory.setName("派生信号库");
        variableCategory.setType(CategoryType.Custom);
        variableCategory.setClazz("urule.signal.derive");
        variableCategories.add(variableCategory);
        return variableCategories;
    }

    @SneakyThrows
    public static List<VariableCategory> getLocalCache(String key) {
        return localCache.get(key);
    }
}