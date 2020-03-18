package com.newpao.modules.print;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SimpleTest {
    /**
     * Possible values:
     * 0 - numeric float;
     * 1 - character;
     * 2 - log;
     * 3 - numeric unsigned;
     * 4 - text.
     */
    private static final int VALUE_TYPE_FLOAT = 0;
    private static final int VALUE_TYPE_STRING = 1;
    private static final int VALUE_TYPE_INT = 3;
    private static final Integer[] ITEM_VALUE_TYPE_ARRAYS = {VALUE_TYPE_FLOAT, VALUE_TYPE_STRING, VALUE_TYPE_INT};

    @Test
    public void test() throws IllegalAccessException, InstantiationException {
        System.out.println("sssssss");

//
//        List<Integer> groupList = new ArrayList<>();
//        groupList.add(1);
//        groupList.add(2);
//        groupList.add(3);
//        groupList.add(3);
//        groupList.add(5);
//
//        List<Integer> groupidList = groupList.stream().distinct()
//                .collect(Collectors.toList());
//
//
//        System.out.println(groupidList);
//        System.out.println(ITEM_VALUE_TYPE_ARRAYS.length);
//
//        List<Integer> integers = Arrays.asList(ITEM_VALUE_TYPE_ARRAYS);
//
//        integers.forEach(item -> {
//            System.out.println(item);
//        });


//        Instant time = Instant.now();
//
//        Instant instant = Instant.ofEpochSecond(1583572793L);
//
//
//        History item = new History();
//        item.setClock(1583572793L);
//        item.setItemid(1L);
//        item.setValue(100F);
//
//
//        HistoryInt t = HistoryInt.class.newInstance();
//        BeanUtil.copyProperties(item, t);
//        Long clock = (Long) ReflectUtil.getFieldValue(item, "clock");
//        ReflectUtil.setFieldValue(t, "time", Instant.ofEpochSecond(clock));
//
//        System.out.println(time);
//        System.out.println(instant);
//        System.out.println(item);
//        System.out.println(t);


        String[] itemids = {"1", "2", "3"};

        if (ObjectUtil.isNotEmpty(itemids)) {
            List<String> filter = new ArrayList<>();
            for (String itemid : itemids) {
                filter.add("r.itemid == \"" + itemid + "\"");
            }


            String join = "|> filter(fn: (r) => " +  CollUtil.join(filter, " or ") + " )";
            System.out.println(join);
        }
    }
}