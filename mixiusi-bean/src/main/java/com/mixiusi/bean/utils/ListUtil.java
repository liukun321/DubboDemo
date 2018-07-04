package com.mixiusi.bean.utils;

import java.util.List;

/**
 * 
 * @author liukun
 *
 */
public class ListUtil {

  public static Integer[] list2array(List<Integer> list) {
    Integer[] arr = new Integer[list.size()];
    int i = 0;
    for (Integer item : list) {
      arr[i] = list.get(i);
      i++;
    }
    return arr;
  }
}
