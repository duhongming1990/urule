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
import org.apache.commons.lang.StringUtils;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    /**
     * 信号库
     *
     * @return
     */
    @SneakyThrows
    public static List<VariableCategory> getVariableCategories() {
        Connection connection = RepositoryBuilder.datasource.getConnection();
        List<Entity> entities = SqlExecutor.query(connection, "SELECT * FROM urule_signal_lib", new EntityListHandler(), new HashMap<>());
        List<VariableCategory> variableCategories = new ArrayList<>();
        VariableCategory variableCategory = null;
        for (Entity entity : entities) {
            String signalName = entity.getStr("signal_name");
            String description = entity.getStr("description");
            String dateType = entity.getStr("data_type");
            if (StringUtils.isBlank(signalName) && StringUtils.isNotBlank(description) && StringUtils.isBlank(dateType)) {
                variableCategory = new VariableCategory();
                variableCategory.setName(description);
                variableCategory.setType(CategoryType.BuildIn);
                variableCategory.setClazz("urule.signal.lib");
                List<Variable> variables = new ArrayList<>();
                variableCategory.setVariables(variables);
                variableCategories.add(variableCategory);
            } else {
                Variable variable = new Variable();
                variable.setName(signalName);
                variable.setType(Datatype.String);
                variable.setLabel(signalName);
                variable.setTitle(description);
                variable.setAct(Act.Internal);
                variableCategory.getVariables().add(variable);
            }
        }

        variableCategory = new VariableCategory();
        variableCategory.setName("派生信号库");
        variableCategory.setType(CategoryType.Custom);
        variableCategory.setClazz("urule.signal.derive");

        List<Variable> variables = new ArrayList<>();
        Variable variable = new Variable();
        variable.setName("尾门计时器");
        variable.setType(Datatype.String);
        variable.setLabel("尾门计时器");
        variable.setTitle("尾门计时器");
        variable.setAct(Act.Internal);
        variables.add(variable);
        variable = new Variable();
        variable.setName("油箱口盖计时器");
        variable.setType(Datatype.String);
        variable.setLabel("油箱口盖计时器");
        variable.setTitle("油箱口盖计时器");
        variable.setAct(Act.Internal);
        variables.add(variable);

        variableCategory.setVariables(variables);
        variableCategories.add(variableCategory);

        return variableCategories;
    }

    @SneakyThrows
    public static List<VariableCategory> getLocalCache(String key) {
        return localCache.get(key);
    }
}