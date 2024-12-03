package com.company.NervManagementConsoleREST.utils;

import com.company.NervManagementConsoleREST.model.Levelable;

public class LevelUpUtils {
	 public static <T extends Levelable> T levelUp(T entity, Integer newExp) {
	        Integer expMax = 100;
	        Integer currentExp = newExp + entity.getExp();
	        Integer level = entity.getLevel();

	        while (currentExp >= expMax) {
	            level++;
	            currentExp -= expMax;
	            entity.setLevel(level);
	            entity.setExp(currentExp);
	        }

	        if (currentExp > 0) {
	            entity.setExp(currentExp);
	        }


	        if (entity.getLevel() > 99) {
	            entity.setLevel(100);
	            entity.setExp(0);
	        }

	        return entity;
	    }
}
