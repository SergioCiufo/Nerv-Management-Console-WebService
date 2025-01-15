package com.company.NervManagementConsoleREST.utils;

import com.company.NervManagementConsoleREST.model.Levelable;

public class LevelUpUtils {
	public <T extends Levelable> T levelUp(T entity, Integer newExp) {
	    if (entity == null || newExp == null) {
	        throw new IllegalArgumentException("Entity and newExp cannot be null");
	    }
	    if (entity.getExp() == null) {
	        entity.setExp(0);
	    }
	    if (entity.getLevel() == null) {
	        entity.setLevel(1);
	    }
	    
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
